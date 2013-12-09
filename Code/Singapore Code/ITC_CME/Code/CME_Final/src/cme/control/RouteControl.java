package cme.control;

import cme.interfaces.RouteInterface;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import cme.gui.CityMapEditor;
import holder.Map;
import holder.Route;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Control for route plotting, saving and deleting
 * @author Yin Ming Jun Kevin
 */
public class RouteControl implements RouteInterface {
    private double distance=0,tempDist=0;
    private int depotIndex;

    //
    /**
     * adding points to Vector route points
     * @param routePoints of selected map
     * @param posX X co-ordinates of mouse pressed
     * @param posY Y co-ordinates of mouse pressed
     */
    public void addWayPoint(Vector routePoints, int posX, int posY) {
        //System.out.println(posX + "   " + posY);
        Point point = new Point(posX, posY);
        routePoints.add(point);
    }

    /**
     * deleteing last element of Vector route points
     * @param routePoints of selected map
     */
    public void deleteWayPoint(Vector routePoints) {
        routePoints.remove(routePoints.lastElement());
    }

    /**
     * validate if route is plotted on depot and on road
     * @param cme city map editor
     * @param map map of which point is added to
     * @param roadArray points of the road
     * @param routePoints points of route
     * @return true if validation pass else false
     */
    public boolean validateRoute(CityMapEditor cme,Map map, boolean[][] roadArray, Vector routePoints) {
        int calX, calY;
        double tempY, tempX;
        double[] lineEqn = new double[2];
        //first point
        if (routePoints.size() == 1) {
            //depot more than 2
            if (map.depots.size() >= 2) {
                //check if first point is on depot
                for (int i = 0; i < map.depots.size(); i++) {
                    if (((Point) routePoints.firstElement()).x < map.depots.get(i).x + 10 && ((Point) routePoints.firstElement()).x > map.depots.get(i).x - 10) {
                        if (((Point) routePoints.firstElement()).y < map.depots.get(i).y + 10 && ((Point) routePoints.firstElement()).y > map.depots.get(i).y - 10) {
                            depotIndex = i;
                            routePoints.remove(0);
                            Point point = new Point(map.depots.get(i).getRoadPoint().x, map.depots.get(i).getRoadPoint().y);
                            routePoints.add(point);
                            return true;
                        }
                    }
                }
            }
            //first point not on depot
            return false;
        }
        
        //get line equation
        lineEqn = findLineEqn((Point) routePoints.get(routePoints.size() - 2), (Point) routePoints.lastElement());
        calX = ((Point) routePoints.lastElement()).x - ((Point) routePoints.get(routePoints.size() - 2)).x;
        calY = ((Point) routePoints.lastElement()).y - ((Point) routePoints.get(routePoints.size() - 2)).y;
        //absolute difference in X and Y
        calX = Math.abs(calX);
        calY = Math.abs(calY);
        //straightline
        //horizontal
        if (calX == 0) {
            //loopY
            //below second last point
            if (((Point) routePoints.lastElement()).y > ((Point) routePoints.get(routePoints.size() - 2)).y) {
                for (int i = ((Point) routePoints.lastElement()).y; i > ((Point) routePoints.get(routePoints.size() - 2)).y; i--) {
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, ((Point) routePoints.lastElement()).x, i)) {
                        return false;
                    }
                }
            }
            //above second last point
            else {
                for (int i = ((Point) routePoints.lastElement()).y; i < ((Point) routePoints.get(routePoints.size() - 2)).y; i++) {
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, ((Point) routePoints.lastElement()).x, i)) {
                        return false;
                    }
                }
            }
        }
        //vertical
        if (calY == 0) {
            //loopX
            //towards the right
            if (((Point) routePoints.lastElement()).x > ((Point) routePoints.get(routePoints.size() - 2)).x) {
                for (int i = ((Point) routePoints.lastElement()).x; i > ((Point) routePoints.get(routePoints.size() - 2)).x; i--) {
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, i, ((Point) routePoints.lastElement()).y)) {
                        return false;
                    }
                }
            }
            //towards the left
            else {
                for (int i = ((Point) routePoints.lastElement()).x; i < ((Point) routePoints.get(routePoints.size() - 2)).x; i++) {
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, i, ((Point) routePoints.lastElement()).y)) {
                        return false;
                    }
                }
            }
        }
        //X difference is bigger than or equal to Y difference
        if (calX >= calY) {
            //loop x compare y
            //towards the right
            if (((Point) routePoints.lastElement()).x > ((Point) routePoints.get(routePoints.size() - 2)).x) {
                for (int i = ((Point) routePoints.lastElement()).x; i > ((Point) routePoints.get(routePoints.size() - 2)).x; i--) {
                    //get value of Y
                    tempY = lineEqn[0] * i + lineEqn[1];
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, i, (int) tempY)) {
                        return false;
                    }
                }
            }
            //towards the left
            else {
                for (int i = ((Point) routePoints.lastElement()).x; i < ((Point) routePoints.get(routePoints.size() - 2)).x; i++) {
                    //get value of Y
                    tempY = lineEqn[0] * i + lineEqn[1];
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, i, (int) tempY)) {
                        return false;
                    }
                }
            }
        }
        if (calX < calY) {
            //loop y compare x
            //bottom
            if (((Point) routePoints.lastElement()).y > ((Point) routePoints.get(routePoints.size() - 2)).y) {
                for (int i = ((Point) routePoints.lastElement()).y; i > ((Point) routePoints.get(routePoints.size() - 2)).y; i--) {
                    //get value of X
                    tempX = (i - lineEqn[1]) / lineEqn[0];
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, (int) tempX, i)) {
                        return false;
                    }
                }
            }
            //above
            else {
                for (int i = ((Point) routePoints.lastElement()).y; i < ((Point) routePoints.get(routePoints.size() - 2)).y; i++) {
                    //get value of X
                    tempX = (i - lineEqn[1]) / lineEqn[0];
                    //check if plotted point is on the road
                    if (!checkIsRoad(roadArray, (int) tempX, i)) {
                        return false;
                    }
                }
            }
        }
        //check if last point is clicked by validating of depot points is pressed
        for (int i = 0; i < map.depots.size(); i++) {
            if (((Point) routePoints.lastElement()).x < map.depots.get(i).getRoadPoint().x + 10 && ((Point) routePoints.lastElement()).x > map.depots.get(i).getRoadPoint().x - 10) {
                if (((Point) routePoints.lastElement()).y < map.depots.get(i).getRoadPoint().y + 10 && ((Point) routePoints.lastElement()).y > map.depots.get(i).getRoadPoint().y - 10) {
                    //first depot is not equals to second depot
                    if (depotIndex != i) {
                        //Confirm last point
                        int last = JOptionPane.showConfirmDialog(null,
                                "Is this the end point for this route?",
                                "Route Menu",
                                JOptionPane.YES_NO_OPTION);
                        //Last point = YES
                        if (last == 0) {
                            routePoints.remove(routePoints.lastElement());
                            Point point = new Point(map.depots.get(i).getRoadPoint().x, map.depots.get(i).getRoadPoint().y);
                            routePoints.add(point);
                            depotIndex = -1;
                            cme.mapPanel.routeEnd=true;
                        }
                        //Last point = NO
                        else {
                            routePoints.remove(routePoints.lastElement());
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean checkIsRoad(boolean[][] roadArray, int posX, int posY) {
        if (posX >= 0 && posY >= 0 && posX < roadArray.length && posY < roadArray[0].length) {
            if (roadArray[posX][posY] != true) {
                return false;
            }
        }
        return true;
    }

    private double[] findLineEqn(Point point1, Point point2) {
        double[] lineEqnMC = new double[2];
        if ((point2.x - point1.x) != 0) {
            //Gradient
            lineEqnMC[0] = (double) (point2.y - point1.y) / (double) (point2.x - point1.x);
            //Intercept
            lineEqnMC[1] = point1.y - point1.x * lineEqnMC[0];
        }
        return lineEqnMC;
    }

    /**
     *
     * @param cme city map editor
     * @param map map of which route is going to save into
     * @param routePoints points of routes
     * @return true if save pass else false
     */
    public boolean saveRoute(CityMapEditor cme, Map map, Vector routePoints) {
        try {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            //check the application is within screen
            if (cme.getX() + 174 > 0 && cme.getY() + 30 > 0) {
                //saved route ID
                String routeID = (String) JOptionPane.showInputDialog(
                        null, "Enter Route ID:", "Save Route",
                        JOptionPane.INFORMATION_MESSAGE, null, null, "");
                //never enter proper name
                if (routeID == null) {
                    JOptionPane.showMessageDialog(null, "Route not saved",
                            "INFOMATION", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }
                if (routeID.equals("")) {
                    JOptionPane.showMessageDialog(null, "Invalid Route ID",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                if (routeID.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Spaces not allowed",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                //check if route ID exist
                for(int i=0;i<map.routes.size();i++){
                    if(routeID.equalsIgnoreCase(map.routes.get(i).getRouteID())){
                        JOptionPane.showMessageDialog(null, "Route ID exist!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
                //screenshot
                Robot robot = new Robot();
                BufferedImage mapImage = robot.createScreenCapture(new Rectangle(cme.getX() + 174, cme.getY() + 30, 1024, 768));
                Image thumbImage = mapImage.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                BufferedImage thumbnail = toBufferedImage(thumbImage);
                //ImageIO.write(thumbnail, "png", new File("tempThumb.png"));
                distance=0;
                //calculate distance
                for(int i=0;i<routePoints.size()-1;i++){
                    distance += ((Point)routePoints.get(i+1)).distance(((Point)routePoints.get(i)));
                }
                //System.out.println("Total Distance" + distance);
                Route route = new Route(routeID, (Vector) routePoints.clone(), thumbnail);
                route.setDistance(distance);
                //add route into selected map
                map.routes.add(route);
                //update route list box
                cme.btnPanel.routeListModel.addElement(route.getRouteID());
                System.out.println("Valid Route: "+routeID);
            }
            else {
                JOptionPane.showMessageDialog(null, "Please move the application within the screen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * refresh map with no routes
     * @param routePoints points of routes
     */
    public void clearRoute(Vector routePoints) {
        int clear = JOptionPane.showConfirmDialog(null,
                "Clear current route?",
                "Clear Route",
                JOptionPane.YES_NO_OPTION);
        if (clear == 0) {
            routePoints.removeAllElements();
        }
    }

    /**
     * delete selected route index
     * @param cme city map editor
     * @param map map of which route is created on
     * @param routeIndex get route index from route list box
     */
    public void deleteRoute(CityMapEditor cme, Map map, int routeIndex) {
        //delete route from map
        map.routes.remove(routeIndex);
        //refresh
        updateRouteList(cme, map);
    }

    /**
     *
     * @param cme city map editor
     * @param map selected map of routes
     */
    public void updateRouteList(CityMapEditor cme, Map map) {
        cme.btnPanel.routeListModel.removeAllElements();
        for (int i = 0; i < map.routes.size(); i++) {
            Route route = (Route) map.routes.get(i);
            cme.btnPanel.routeListModel.addElement(route.getRouteID());
        }
    }

    private BufferedImage toBufferedImage(Image image) {
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Copy image to buffered image and paint the image onto the buffered image
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }
}
