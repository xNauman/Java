package cme.control;

import cme.interfaces.TrafficJunctionInterface;
import java.awt.Point;
import java.util.Vector;
import holder.Map;
import holder.TrafficLight;

/**
 * TrafficLight and Junction Control to add, delete and validate taffic lights
 * and junctions
 * @author ZheN CoNG
 */
public class TrafficJunctionControl implements TrafficJunctionInterface {

    private final int checkRange = 20;

    /**
     * Validate the current location and identify the type of junction
     * If a T or cross junction is identify, proceed with placing the traffic light
     * Also mark this point as a junction if it has not been set
     * @param map           Map Holder
     * @param roadArray     boolean of road location
     * @param posX          Coordinate x of the placement
     * @param posY          Coordinate y of the placement
     * @return true if placing of traffic light is sucessful, false if otherwise
     */
    public boolean addTrafficLight(Map map, boolean[][] roadArray, int posX, int posY) {
        if (checkIsRoad(roadArray, posX, posY)) {
            Vector roadJunction = checkJunction(roadArray, posX, posY, checkRange, checkRange, 1);
            Vector midRoadPoints = new Vector();
            Point midRoadPoint = null;
            //check for minimum a T-junction and proceed getting coordinates
            if (roadJunction.size() >= 6) {
                //Get the middle point of each road
                if (roadJunction.size() % 2 == 0) {
                    for (int i = 0; i < roadJunction.size();) {
                        midRoadPoint = findMidPoint((Point) roadJunction.get(i), (Point) roadJunction.get(i + 1));
                        if (midRoadPoint != null) {
                            midRoadPoints.add(midRoadPoint);
                        }
                        i += 2;
                    }
                } else {
                    for (int i = 2; i < roadJunction.size();) {
                        if (i == (roadJunction.size() - 1)) {
                            midRoadPoint = findMidPoint((Point) roadJunction.get(i), (Point) roadJunction.get(1));
                        } else {
                            midRoadPoint = findMidPoint((Point) roadJunction.get(i), (Point) roadJunction.get(i + 1));
                        }
                        if (midRoadPoint != null) {
                            midRoadPoints.add(midRoadPoint);
                        }
                        i += 2;
                    }
                }

                //Find the mid intersection point
                Point intersection = findJunctionIntersection(midRoadPoints, roadArray);
                
                Vector roadAngle = new Vector();
                //Get angle relative to SOUTH where the road is coming from
                for (int i = 0; i < midRoadPoints.size(); i++) {
                    roadAngle.add(calculateAngle(intersection, (Point) midRoadPoints.get(i)) - Math.toDegrees(Math.PI / 2));
                }

                //Add the intersection point into the vector
                if (intersection != null) {
                    if (checkIsExist(map.trafficLights, intersection.x, intersection.y) == -1) {
                        int id = getLastTrafficLightID(map);
                        id+=1;
                        TrafficLight trafficLight = new TrafficLight(id, intersection.x, intersection.y, roadAngle);
                        map.trafficLights.add(trafficLight);
                        int junctionIndex = checkIsExist(map.junctions, intersection.x, intersection.y);
                        if (junctionIndex >= 0) {
                            map.junctions.removeElementAt(junctionIndex);
                        }
                        System.out.println("Valid Traffic Light: "+id);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if it is a traffic light and proceed with deleting the instance
     * @param map   Map Holder
     * @param posX  Coordinate x of the traffic light
     * @param posY  Coordinate y of the traffic light
     * @return      true if the traffic light has be deleted, false if otherwise
     */
    public boolean deleteTrafficLight(Map map, int posX, int posY) {
        int index = checkIsExist(map.trafficLights, posX, posY);
        if (index >= 0) {
            map.trafficLights.removeElementAt(index);
            return true;
        }
        return false;
    }

    /**
     * Validate the current location and place a junction maker
     * @param map           Map Holder
     * @param roadArray     boolean of road location
     * @param posX          Coordinate x of the placement
     * @param posY          Coordinate y of the placement
     * @return true if placing of junction is sucessful, false if otherwise
     */
    public boolean addJunction(Map map, boolean[][] roadArray, int posX, int posY) {
        if (checkIsRoad(roadArray, posX, posY)) {
            Vector roadJunction = checkJunction(roadArray, posX, posY, checkRange, checkRange, 1);
            Vector midRoadPoints = new Vector();
            Point midRoadPoint = null;
            //check for minimum a T-junction and proceed getting coordinates
            if (roadJunction.size() >= 6) {
                //Get the middle point of each road
                if (roadJunction.size() % 2 == 0) {
                    for (int i = 0; i < roadJunction.size();) {
                        midRoadPoint = findMidPoint((Point) roadJunction.get(i), (Point) roadJunction.get(i + 1));
                        if (midRoadPoint != null) {
                            midRoadPoints.add(midRoadPoint);
                        }
                        i += 2;
                    }
                } else {
                    for (int i = 2; i < roadJunction.size();) {
                        if (i == (roadJunction.size() - 1)) {
                            midRoadPoint = findMidPoint((Point) roadJunction.get(i), (Point) roadJunction.get(1));
                        } else {
                            midRoadPoint = findMidPoint((Point) roadJunction.get(i), (Point) roadJunction.get(i + 1));
                        }
                        if (midRoadPoint != null) {
                            midRoadPoints.add(midRoadPoint);
                        }
                        i += 2;
                    }
                }

                //Find the mid intersection point
                Point intersection = findJunctionIntersection(midRoadPoints, roadArray);

                //Add the intersection point into the vector
                if (intersection != null) {
                    if (checkIsExist(map.junctions, intersection.x, intersection.y) == -1) {
                        Point junction = new Point(intersection.x, intersection.y);
                        map.junctions.add(junction);
                        int trafficIndex = checkIsExist(map.trafficLights, intersection.x, intersection.y);
                        if (trafficIndex >= 0) {
                            map.trafficLights.removeElementAt(trafficIndex);
                        }
                        System.out.println("Valid Junction");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if it is a junction and proceed with deleting the instance
     * also remove instance of traffic light if exist
     * @param map   Map Holder
     * @param posX  Coordinate x of the junction
     * @param posY  Coordinate y of the junction
     * @return      true if the junction has be deleted, false if otherwise
     */
    public boolean deleteJunction(Map map, int posX, int posY) {
        int index = checkIsExist(map.junctions, posX, posY);
        if (index >= 0) {
            map.junctions.removeElementAt(index);
            return true;
        }
        return false;
    }

    //Delete check whether the coordinate is a valid road
    private boolean checkIsRoad(boolean[][] roadArray, int posX, int posY) {
        for (int i = posX - 5; i < posX + 5; i++) {
            for (int j = posY - 5; j < posY + 5; j++) {
                if (i >= 0 && j >= 0 && i < roadArray.length && j < roadArray[0].length) {
                    if (roadArray[i][j] != true) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //return the list of road point tt is branching from the junction
    private Vector checkJunction(boolean[][] roadArray, int posX, int posY, int height, int width, int loop) {
        height = height * 3 / 2;
        width = width * 3 / 2;
        int tempX = posX - height / 2;
        int tempY = posY - width / 2;
        int prevRoadX = 0;
        int prevRoadY = 0;
        Vector roadJunction = new Vector();
        boolean detectedRoad = false;

        //Search the parameter to location instance of a road
        int i = 0, j = 0;
        while (i <= width && j <= height) {
            if (tempX >= 0 && tempX <= roadArray.length && tempY >= 0 && tempY <= roadArray[0].length) {
                if (roadArray[tempX][tempY] == true) {
                    if (!detectedRoad) {
                        roadJunction.add(new Point(tempX, tempY));
                        detectedRoad = true;
                    }
                    prevRoadX = tempX;
                    prevRoadY = tempY;
                } else {
                    if (detectedRoad) {
                        roadJunction.add(new Point(prevRoadX, prevRoadY));
                        detectedRoad = false;
                    }
                }
            }
            if (i < width && j == 0) {
                i++;
                tempX++;
            } else if (i == width && j < height) {
                j++;
                tempY++;
            } else if (i > 0 && j == height) {
                i--;
                tempX--;
            } else if (i == 0 && j > 0) {
                j--;
                tempY--;
            }
            if (i == 0 && j == 0) {
                break;
            }
        }

        //Loop the method again to double check the parameter
        if (loop >= 1) {
            Vector roadJunction2 = new Vector();
            roadJunction2 = checkJunction(roadArray, posX, posY, height, width, loop - 1);
            if (roadJunction.size() >= 6 && roadJunction2.size() >= 6) {
                if (roadJunction.size() > roadJunction2.size()) {
                    return roadJunction;
                } else {
                    return roadJunction2;
                }
            }
        }
        return roadJunction;
    }

    //Check whether a traffic light or junction exist at thie coordinate
    private int checkIsExist(Vector trafficOrJunction, int posX, int posY) {
        for (int i = 0; i < trafficOrJunction.size(); i++) {
            Point point = new Point();
            if (trafficOrJunction.get(i).getClass().isInstance(point)) {
                point = (Point) trafficOrJunction.get(i);
            } else {
                TrafficLight trafficLight = (TrafficLight) trafficOrJunction.get(i);
                point = new Point(trafficLight.x, trafficLight.y);
            }
            if ((posX - checkRange / 2) <= point.x && (posX + checkRange / 2) >= point.x && (posY - checkRange / 2) <= point.y && (posY + checkRange / 2) >= point.y) {
                return i;
            }
        }
        return -1;
    }

    //Return the last traffic light ID
    private int getLastTrafficLightID(Map map) {
        if (map.trafficLights.size() == 0) {
            return 0;
        }

        return ((TrafficLight) map.trafficLights.lastElement()).getTrafficLightID();
    }

    //Return the mid coordinate of 2 points
    private Point findMidPoint(Point point1, Point point2) {
        Point newPoint1 = (Point) point1.clone();
        Point newPoint2 = (Point) point2.clone();
        double[] lineEqn = new double[2];
        double m = 0;
        double c = 0;
        lineEqn = findLineEqn(newPoint1, newPoint2);
        m = lineEqn[0];
        c = lineEqn[1];
        Point midPoint = null;
        if (point1.x == point2.x) {
            int midY = (newPoint1.y + newPoint2.y) / 2;
            midPoint = new Point(newPoint1.x, midY);
        } else {
            int midX = (newPoint1.x + newPoint2.x) / 2;
            Double midY = m * midX + c;
            midPoint = new Point(midX, midY.intValue());
        }
        return midPoint;
    }

    //find the intersection of the roads
    private Point findJunctionIntersection(Vector roadPoints, boolean[][] roadArray) {
        double[] lineEqnMC1 = new double[2];
        double[] lineEqnMC2 = new double[2];
        //For T junction
        if (roadPoints.size() == 3) {
            Point mid01 = findMidPoint((Point) roadPoints.get(0), (Point) roadPoints.get(1));
            Point mid02 = findMidPoint((Point) roadPoints.get(0), (Point) roadPoints.get(2));
            Point mid12 = findMidPoint((Point) roadPoints.get(1), (Point) roadPoints.get(2));
            if (roadArray[mid01.x][mid01.y] == true) {
                Point point2 = (Point) roadPoints.get(2);
                lineEqnMC1 = findLineEqn((Point) roadPoints.get(0), (Point) roadPoints.get(1));
                lineEqnMC2[0] = (1 / lineEqnMC1[0]) * -1;
                lineEqnMC2[1] = point2.y - lineEqnMC2[0] * point2.x;
            } else if (roadArray[mid02.x][mid02.y] == true) {
                Point point1 = (Point) roadPoints.get(1);
                lineEqnMC1 = findLineEqn((Point) roadPoints.get(0), (Point) roadPoints.get(2));
                lineEqnMC2[0] = (1 / lineEqnMC1[0]) * -1;
                lineEqnMC2[1] = point1.y - lineEqnMC2[0] * point1.x;
            } else if (roadArray[mid12.x][mid12.y] == true) {
                Point point0 = (Point) roadPoints.get(0);
                lineEqnMC1 = findLineEqn((Point) roadPoints.get(1), (Point) roadPoints.get(2));
                lineEqnMC2[0] = (1 / lineEqnMC1[0]) * -1;
                lineEqnMC2[1] = point0.y - lineEqnMC2[0] * point0.x;
            }
        }
        //For Cross junction
        else {
            lineEqnMC1 = findLineEqn((Point) roadPoints.get(0), (Point) roadPoints.get(2));
            lineEqnMC2 = findLineEqn((Point) roadPoints.get(1), (Point) roadPoints.get(3));
        }
        Double intersectionX = (lineEqnMC2[1] - lineEqnMC1[1]) / (lineEqnMC1[0] - lineEqnMC2[0]);
        Double intersectionY = lineEqnMC1[0] * intersectionX + lineEqnMC1[1];
        return new Point(intersectionX.intValue(), intersectionY.intValue());
    }

    //Return the Gradient(M) and y-intercept(C) of a line
    private double[] findLineEqn(Point point1, Point point2) {
        double[] lineEqnMC = new double[2];
        if (point2.x - point1.x == 0) {
            point2.x = point2.x - 1;
        }
        lineEqnMC[0] = (double) (point2.y - point1.y) / (double) (point2.x - point1.x);
        lineEqnMC[1] = point1.y - point1.x * lineEqnMC[0];
        //System.out.println(""+point1+" "+point2);
        //System.out.println("m:"+lineEqnMC[0]+" c:"+lineEqnMC[1]);
        return lineEqnMC;
    }

    //Find the angle of the road form the junction intersection
    private Double calculateAngle(Point point1, Point point2) {
        int diffX, diffY;
        diffX = point2.x - point1.x;
        diffY = point2.y - point1.y;

        //angle is 90 or 270 degree
        if (diffY == 0) {
            if (diffX > 0) {
                return Math.toDegrees(Math.PI / 2);
            } else {
                return Math.toDegrees(Math.PI * 1.5);
            }
        } //angle is 0 or 180 degree
        else if (diffX == 0) {
            if (diffY > 0) {
                return Math.toDegrees(0);
            } else {
                return Math.toDegrees(Math.PI);
            }
        }

        //angle is between 1 to 89 degree
        if (diffX > 0 && diffY > 0) {
            return Math.toDegrees(Math.atan2(diffX, diffY));
        } //angle is between 91 to 179 degree
        else if (diffX > 0 && diffY < 0) {
            return Math.toDegrees(Math.PI / 2 + Math.atan2(Math.abs(diffY), diffX));
        } //angle is between 181 to 269 degree
        else if (diffX < 0 && diffY < 0) {
            return Math.toDegrees(Math.PI + Math.atan2(Math.abs(diffX), Math.abs(diffY)));
        } //angle is between 271 to 359 degree
        else if (diffX < 0 && diffY > 0) {
            return Math.toDegrees(Math.PI * 1.5 + Math.atan2(diffY, Math.abs(diffX)));
        } else {
            return Math.toDegrees(0);
        }
    }
}