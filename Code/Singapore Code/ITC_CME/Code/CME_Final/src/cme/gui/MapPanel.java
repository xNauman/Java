package cme.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import cme.control.BusStopControl;
import cme.interfaces.BusStopInterface;
import cme.control.DepotControl;
import cme.interfaces.DepotInterface;
import cme.control.RouteControl;
import cme.interfaces.RouteInterface;
import cme.control.TrafficJunctionControl;
import cme.dataengine.FlatFileEngine;
import cme.interfaces.TrafficJunctionInterface;
import cme.filter.GoogleMapFilter;
import holder.BusStop;
import holder.Depot;
import holder.Map;
import holder.Route;
import holder.TrafficLight;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Panel which displays the map and the 
 * @author CME Team
 */
public class MapPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener, ListSelectionListener {

    private CityMapEditor cme;
    private BufferedImage mapImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private BufferedImage filterImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private BufferedImage busStopImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private BufferedImage junctionImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private BufferedImage depotImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    private Map selectedMap = null;
    private Vector<Point> tempRoute = new Vector();
    private boolean[][] roadArray = new boolean[1][1];
    private boolean validRoute = false;
    private boolean boldRoute = false;
    private boolean showRouteOnly = false;
    private int mouseX = 0;
    private int mouseY = 0;
    private TrafficJunctionInterface controlTrafficJunction = new TrafficJunctionControl();
    private BusStopInterface controlBS = new BusStopControl();
    private DepotInterface controlDP = new DepotControl();
    private RouteInterface controlRoute = new RouteControl();
    public boolean routeEnd = false;
    private boolean routeSave = false;

    public MapPanel(CityMapEditor cme) {
        this.cme = cme;
        initMap();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void initMap() {
        try {
            mapImage = ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/backgrd.png"));
            busStopImage = ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/busstop20.png"));
            junctionImage = ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/junction20.png"));
            depotImage = ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/depot.png"));
            repaint();
            selectedMap = null;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    //Browser load
    public void loadMap(Map map) {
        selectedMap = map;
        mapImage = map.getMapInBufferedImage();
        filterImage = GoogleMapFilter.filter(mapImage);
        roadArray = GoogleMapFilter.getRoadArray(filterImage);
        controlRoute.updateRouteList(cme, map);
        resetPanel();
        repaint();
    }
    
    //listbox load
    public void loadMap(Map map, BufferedImage filterImage) {
        selectedMap = map;
        mapImage = map.getMapInBufferedImage();
        this.filterImage = filterImage;
        roadArray = GoogleMapFilter.getRoadArray(filterImage);
        controlRoute.updateRouteList(cme, map);
        resetPanel();
        repaint();
    }

    private void resetPanel() {
        tempRoute.removeAllElements();
        validRoute = false;
        boldRoute = false;
        showRouteOnly = false;
        mouseX = 0;
        mouseY = 0;
        routeEnd = false;
        routeSave = false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(mapImage, 0, 0, null);
        if (selectedMap != null) {
            //Route
            if (tempRoute.size() != 0) {
                for (int i = 1; i < tempRoute.size(); i++) {
                    Point pointA = (Point) tempRoute.get((i - 1));
                    Point pointB = (Point) tempRoute.get(i);
                    g.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
                }

                if (cme.btnPanel.plotTBtn.isSelected()) {
                    Point point = tempRoute.lastElement();
                    g.drawLine(point.x, point.y, mouseX, mouseY);
                    g.fillRoundRect(point.x - 4, point.y - 4, 8, 8, 7, 7);
                }
            }
            if (!showRouteOnly) {
                //Bus Stop
                if (cme.btnPanel.addBusTBtn.isSelected()) {
                    g.drawImage(busStopImage, mouseX - busStopImage.getWidth() / 2, mouseY - busStopImage.getHeight() / 2, null);
                }
                for (int i = 0; i < selectedMap.busStops.size(); i++) {
                    BusStop busStop = (BusStop) selectedMap.busStops.get(i);
                    g.drawImage(busStopImage, busStop.x - busStopImage.getWidth() / 2, busStop.y - busStopImage.getHeight() / 2, null);
                }

                //Depot
                if (cme.btnPanel.addDepotTBtn.isSelected()) {
                    g.drawImage(depotImage, mouseX - depotImage.getWidth() / 2, mouseY - depotImage.getHeight() / 2, null);
                }
                for (int i = 0; i < selectedMap.depots.size(); i++) {
                    Depot tempDepot = (Depot) selectedMap.depots.get(i);
                    g.drawImage(depotImage, tempDepot.x - depotImage.getWidth() / 2, tempDepot.y - depotImage.getHeight() / 2, null);
                }

                //Bus Stop line on road
                for (int i = 0; i < selectedMap.busStops.size(); i++) {
                    BusStop busStop = (BusStop) selectedMap.busStops.get(i);
                    g.setColor(Color.BLACK);
                    g.drawLine(busStop.x, busStop.y, busStop.getOppPoint().x, busStop.getOppPoint().y);
                }

                //Depot line on road
                for (int i = 0; i < selectedMap.depots.size(); i++) {
                    Depot depot = (Depot) selectedMap.depots.get(i);
                    g.setColor(Color.BLACK);
                    g.drawLine(depot.x, depot.y, depot.getRoadPoint().x, depot.getRoadPoint().y);
                }

                //Traffic Light
                for (int i = 0; i < selectedMap.trafficLights.size(); i++) {
                    TrafficLight trafficLight = (TrafficLight) selectedMap.trafficLights.get(i);
                    for (int j = 0; j < trafficLight.getRoadAngle().size(); j++) {
                        if (j % 2 == 1) {
                            g.setColor(Color.RED);
                        } else {
                            g.setColor(Color.GREEN);
                        }
                        g.fillArc(trafficLight.x - 15, trafficLight.y - 15, 30, 30, ((Double) trafficLight.getRoadAngle().get(j)).intValue() - 25, 50);
                    }
                }

                //Junction
                for (int i = 0; i < selectedMap.junctions.size(); i++) {
                    Point junction = (Point) selectedMap.junctions.get(i);
                    g.drawImage(junctionImage, junction.x - junctionImage.getWidth() / 2, junction.y - junctionImage.getHeight() / 2, null);
                }
            }
            //Bold Route for thumbnail
            if (boldRoute) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(5));
                for (int i = 1; i < tempRoute.size(); i++) {
                    g2d.setColor(Color.BLACK);
                    Point pointA = (Point) tempRoute.get((i - 1));
                    Point pointB = (Point) tempRoute.get(i);
                    g2d.drawLine(pointA.x, pointA.y, pointB.x, pointB.y);
                    if(i==1) {
                        g2d.setColor(Color.GREEN);
                        g2d.fillOval(pointA.x-6, pointA.y-6, 12, 12);
                    }
                    if(i==tempRoute.size()-1) {
                        g2d.setColor(Color.RED);
                        g2d.fillOval(pointB.x-6, pointB.y-6, 12, 12);
                    }
                }
                showRouteOnly = false;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        //Route
        if (cme.btnPanel.plotTBtn.isSelected()) {//Right Click

            if (e.isMetaDown()) {
                if (!tempRoute.isEmpty()) {
                    controlRoute.deleteWayPoint(tempRoute);
                    repaint();
                    mouseX = e.getX();
                    mouseY = e.getY();
                    routeEnd = false;
                }
            } else {
                if (!routeEnd) {
                    if(boldRoute)
                        boldRoute = false;
                    controlRoute.addWayPoint(tempRoute, e.getX(), e.getY());
                    validRoute = controlRoute.validateRoute(cme, selectedMap, roadArray, tempRoute);
                    //System.out.println(validRoute);
                    if (!validRoute) {
                        controlRoute.deleteWayPoint(tempRoute);
                    }
                    if (routeEnd) {
                        boldRoute = true;
                        showRouteOnly = true;
                        repaint();
                        routeSave = controlRoute.saveRoute(cme, selectedMap, tempRoute);
                        if(!routeSave) {
                            routeEnd=false;
                            boldRoute = false;
                            tempRoute.remove(tempRoute.lastElement());
                        }
                        //System.out.println(routeSave);
                        repaint();
                    }
                }
//                else {
//                    routeEnd = false;
//                    routeSave =false;
//                }
            }
        }

        //Bus Stop
        if (cme.btnPanel.addBusTBtn.isSelected()) {
            controlBS.addBusDepot(selectedMap.busStops, roadArray, e.getX(), e.getY());
            repaint();
        }
        if (cme.btnPanel.delBusTBtn.isSelected()) {
            controlBS.deleteBusStop(selectedMap.busStops, e.getX(), e.getY());
            repaint();
        }

        //Depot
        if (cme.btnPanel.addDepotTBtn.isSelected()) {
            controlDP.addBusDepot(selectedMap.depots, roadArray, e.getX(), e.getY());
            repaint();
        }
        if (cme.btnPanel.delDepotTBtn.isSelected()) {
            controlDP.deleteDepot(cme, selectedMap, controlRoute, e.getX(), e.getY());
            tempRoute.removeAllElements();
            repaint();
        }

        //Junction
        if (cme.btnPanel.addJunctionTBtn.isSelected()) {
            controlTrafficJunction.addJunction(selectedMap, roadArray, e.getX(), e.getY());
            repaint();
        }
        if (cme.btnPanel.delJunctionTBtn.isSelected()) {
            controlTrafficJunction.deleteJunction(selectedMap, e.getX(), e.getY());
            repaint();
        }

        //Traffic Light
        if (cme.btnPanel.addTrafficTBtn.isSelected()) {
            controlTrafficJunction.addTrafficLight(selectedMap, roadArray, e.getX(), e.getY());
            repaint();
        }
        if (cme.btnPanel.delTrafficTBtn.isSelected()) {
            controlTrafficJunction.deleteTrafficLight(selectedMap, e.getX(), e.getY());
            repaint();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        if (cme.btnPanel.plotTBtn.isSelected()) {
            if (tempRoute.size() != 0) {
                mouseX = tempRoute.lastElement().x;
                mouseY = tempRoute.lastElement().y;
                repaint();
            }
        }

        if (cme.btnPanel.addBusTBtn.isSelected()) {
            mouseX = -50;
            mouseY = 50;
            repaint();
        }

        if (cme.btnPanel.addDepotTBtn.isSelected()) {
            mouseX = -50;
            mouseY = 50;
            repaint();
        }
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        if (cme.btnPanel.plotTBtn.isSelected()) {
            if (routeSave) {
                tempRoute.removeAllElements();
                routeSave = false;
                routeEnd = false;
                repaint();
            }
            if (!routeEnd) {
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }
        }

        if (cme.btnPanel.addBusTBtn.isSelected()) {
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }

        if (cme.btnPanel.addDepotTBtn.isSelected()) {
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cme.btnPanel.plotTBtn) {
            cme.btnPanel.routeList.clearSelection();
            boldRoute = false;
            if(routeEnd || routeSave) {
                tempRoute.removeAllElements();
                routeEnd = false;
                routeSave =false;
            }
            repaint();
        }

        if (e.getSource() == cme.btnPanel.resetRouteBtn) {
            cme.btnPanel.routeList.clearSelection();
            controlRoute.clearRoute(tempRoute);
            routeSave = false;
            routeEnd = false;
            repaint();
        }

        if (e.getSource() == cme.btnPanel.saveMapBtn) {
            boolean saved = FlatFileEngine.saveMap(selectedMap, true);
            if (saved) {
                JOptionPane.showMessageDialog(null, "Map Saved");
            }
        }

        if (e.getSource() == cme.btnPanel.deleteRouteBtn) {
            if (!cme.btnPanel.routeList.isSelectionEmpty()) {
                controlRoute.deleteRoute(cme, selectedMap, cme.btnPanel.routeList.getSelectedIndex());
                tempRoute.removeAllElements();
                repaint();
            }
        }
//        if(routeEnd) {
//            tempRoute.removeAllElements();
//            mouseX = -50;
//            mouseY = -50;
//            repaint();
//        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (!cme.btnPanel.routeList.isSelectionEmpty()) {
            Route temp = ((Route) selectedMap.routes.get(cme.btnPanel.routeList.getSelectedIndex()));
            tempRoute = (Vector) temp.getPoints().clone();
            boldRoute = true;
            routeSave = true;
            routeEnd = true;
            mouseX = ((Point) tempRoute.lastElement()).x;
            mouseY = ((Point) tempRoute.lastElement()).y;
            cme.btnPanel.resetAllTBtn();
            repaint();
        }
    }
}
