package cme.control;

import java.awt.Point;
import java.util.Vector;
import holder.Depot;
import holder.BusStop;

/**
 * BusDepotControl includes functions and validations of bus stops/depots.The 2
 * main functions are adding and deleting of bus stops/depots with validations
 * on the map.
 * @author Lai Sze Chuan
 */
public abstract class BusDepotControl {

    final int mapHeight = 767;
    final int mapWidth = 1023;
    final int iconLength = 20;  //length of the bus/depot image

    /**
     * addBusDepot method is abstact and will be called by the child class,
     * BusStopControl and DepotControl.
     *
     * @param busDepotList  the bus/depot list where the newly created bus/depot
     *                      will be added
     * @param roadArray     the 2D boolean array of the filtered map image, true
     *                      for road and false for land
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              true if the bus/depot is added, else false
     */
    public abstract boolean addBusDepot(Vector busDepotList,boolean[][] roadArray, int cursorX, int cursorY);

    /**
     * getDepotLastID method retrieves the ID of the last depot added.
     *
     * @param busDepotList   the selected map where depot list is in
     * @return              the index of the last depot, else 0 if depot list is
     *                      empty
     */
    protected int getBusDepotLastID(Vector busDepotList){
        //if depot list is empty
        if(busDepotList.size()==0)
            return 0;
        else{
            if(busDepotList.get(0) instanceof Depot)
                return ((Depot)busDepotList.lastElement()).getDepotID();
            else
                return ((BusStop)busDepotList.lastElement()).getBusStopID();
        }
    }

    /**
     * checkIsExist method will check if a depot exists at the selected point.
     *
     * @param busDepotList     the selected map where depot is deleted from its
     *                      depot list
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              index of the bus stop or depot in the list if it
     *                      exists, else return -1
     */
     protected int checkIsExist(Vector busDepotList, int cursorX, int cursorY){

        //If validation exceeds array range
        if(cursorX + iconLength > mapWidth || cursorX - iconLength/2 < 0 || cursorY + iconLength/2 > mapHeight || cursorY - iconLength/2 <0)
            return -1;

        //If bus or depot list is empty
        if(busDepotList.size()==0)
            return -1;

        for(int i=0; i<busDepotList.size(); i++){
            if(busDepotList.get(0) instanceof Depot){
                if((cursorX + iconLength/2 >= ((Depot)busDepotList.get(i)).x - iconLength/2 &&
                        cursorX + iconLength/2 <= ((Depot)busDepotList.get(i)).x + iconLength/2)||
                        (cursorX - iconLength/2 >= ((Depot)busDepotList.get(i)).x - iconLength/2 &&
                        cursorX - iconLength/2 <= ((Depot)busDepotList.get(i)).x + iconLength/2)){
                    if((cursorY + iconLength/2 >= ((Depot)busDepotList.get(i)).y - iconLength/2 &&
                        cursorY + iconLength/2 <= ((Depot)busDepotList.get(i)).y + iconLength/2)||
                        (cursorY - iconLength/2 >= ((Depot)busDepotList.get(i)).y - iconLength/2 &&
                        cursorY - iconLength/2 <= ((Depot)busDepotList.get(i)).y + iconLength/2)){
                        return i;
                    }
                }
            }
            else{
                if((cursorX + iconLength/2 >= ((BusStop)busDepotList.get(i)).x - iconLength/2 &&
                        cursorX + iconLength/2 <= ((BusStop)busDepotList.get(i)).x + iconLength/2)||
                        (cursorX - iconLength/2 >= ((BusStop)busDepotList.get(i)).x - iconLength/2 &&
                        cursorX - iconLength/2 <= ((BusStop)busDepotList.get(i)).x + iconLength/2)){
                    if((cursorY + iconLength/2 >= ((BusStop)busDepotList.get(i)).y - iconLength/2 &&
                        cursorY + iconLength/2 <= ((BusStop)busDepotList.get(i)).y + iconLength/2)||
                        (cursorY - iconLength/2 >= ((BusStop)busDepotList.get(i)).y - iconLength/2 &&
                        cursorY - iconLength/2 <= ((BusStop)busDepotList.get(i)).y + iconLength/2)){
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * checkLand method will check if the land is suitable to add a depot.
     *
     * @param roadArray     the 2D boolean array of the filtered map image, true
     *                      for road and false for land
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              true if the land is suitable, else false
     */
    protected boolean checkLand(boolean[][] roadArray, int cursorX, int cursorY){

        //If validation exceeds array range
        if(cursorX-iconLength/2 < 0 || cursorX+iconLength/2 > mapWidth ||
                cursorY-iconLength/2 <0 || cursorY+iconLength/2 > mapHeight)
            return false;

        int counter =0; //counter to count number of road pixels

        //check if the image area has any road pixels
        for(int a = cursorX- iconLength/2; a<= cursorX+iconLength/2; a++ ){
            for(int b = cursorY - iconLength/2; b<= cursorY+iconLength/2; b++){
                if(roadArray[a][b]==true)
                    counter++;
            }
        }

        //if road pixels is more than 10
        if (counter>10)
            return false;
        else
            return true;
    }

    /**
     * nearestRoad method calculates the nearest road point from the bus/depot.
     *
     * @param roadArray     the 2D boolean array of the filtered map image, true
     *                      for road and false for land
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              the nearest road point, else it will return a point
     *                      of (-1,-1)
     */
    protected Point nearestRoad(boolean[][] roadArray, int cursorX, int cursorY){

        Point nearestPoint = new Point(-1,-1);
        final int validationDiff = 15;

        //If validation exceeds array range
        if(cursorX - validationDiff < 0 || cursorX + validationDiff > mapWidth
                || cursorY -validationDiff <0 || cursorY + validationDiff> mapHeight)
            return nearestPoint;

        double distance=9999;

        //Check for the nearest road point
        for(int i=11; i<validationDiff; i++){
            for(int a = cursorX-i; a<=cursorX+i; a++ ){
                if(a == cursorX-i || a== cursorX+i){
                    for(int b = cursorY-i; b<= cursorY +i ; b++){
                        //If point is road
                        if(roadArray[a][b]==true){
                            //Check whether the distance is lesser
                            if(distance >calculateDist(cursorX, cursorY, a, b)){
                                distance = calculateDist(cursorX, cursorY, a, b);
                                nearestPoint.x = a;
                                nearestPoint.y = b;
                            }
                        }
                    }
                }
                else{
                    //If point is road
                    if(roadArray[a][cursorY-i] == true){
                        //Check whether the distance is lesser
                        if(distance > calculateDist(cursorX, cursorY, a, cursorY-i)){
                            distance = calculateDist(cursorX, cursorY, a, cursorY-i);
                            nearestPoint.x = a;
                            nearestPoint.y = cursorY-i;
                        }
                    }
                    //If point is road
                    if(roadArray[a][cursorY+i] == true){
                        //Check whether the distance is lesser
                        if(distance > calculateDist(cursorX, cursorY, a, cursorY+i)){
                            distance = calculateDist(cursorX, cursorY, a, cursorY+i);
                            nearestPoint.x = a;
                            nearestPoint.y = cursorY+i;
                        }
                    }
                }
            }
        }

        if(distance > validationDiff)
            nearestPoint = new Point(-1,-1);
        //System.out.println(nearestPoint.x + " " + nearestPoint.y + " " + distance);
        return nearestPoint;
    }

    /**
     * calRoadPoint method calculates the point across the road.
     *
     * @param cursorX           the x coordinate of the mouse click
     * @param cursorY           the y coordinate of the mouse click
     * @param nearestPoint      the coordinates of the nearest road point
     * @param roadArray         the 2D boolean array of the filtered map image, true
     *                          for road and false for land
     * @return                  the point across the road, else it will return a
     *                          point (-1,-1)
     */
    protected Point calRoadPoint(int cursorX, int cursorY, Point nearestPoint, boolean[][] roadArray){
        double angle = calculateAngle(cursorX, cursorY, nearestPoint);
        Point roadPoint = new Point();
        double distance=1;

        do{
            distance++;
            roadPoint.x = nearestPoint.x + Integer.parseInt(String.valueOf(Math.round(Math.sin(angle) * distance)));
            roadPoint.y = nearestPoint.y + Integer.parseInt(String.valueOf(Math.round(Math.cos(angle) * distance)));
             if(roadPoint.x > mapWidth || roadPoint.y > mapHeight || roadPoint.x < 0 || roadPoint.y <0){
                roadPoint.x=-1;
                roadPoint.y=-1;
                return roadPoint;
            }
        }
        while(roadArray[roadPoint.x][roadPoint.y]);

        //System.out.println(distance);

        //Validation of distance
        if(distance>5 && distance <30)
            return roadPoint;
        else{
            roadPoint.x = -1;
            return roadPoint;
        }
    }

    /**
     * calculateDist method will calculate the distance between 2 points given.
     *
     * @param x1        the x coordinate of point 1
     * @param y1        the y coordinate of point 1
     * @param x2        the x coordinate of point 2
     * @param y2        the y coordinate of point 2
     * @return          the distance value of the 2 points
     */
    protected double calculateDist(int x1, int y1, int x2, int y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    /**
     * calculateAngle method will calculate the angle of the 2 points. The angle
     * will be in radian. The angle will be calculated with reference to the
     * origin point.
     *
     * @param originX       the x coordiante of the origin point
     * @param originY       the y coordiante of the origin point
     * @param nearestPoint  the coordinates of the nearest road point
     * @return              the angle in radians , else -1
     */
    protected double calculateAngle(int originX, int originY, Point nearestPoint){
        int diffX, diffY;
        diffX =  nearestPoint.x - originX;
        diffY =  nearestPoint.y - originY;

        //angle is PI/2 or 1.5*PI
        if(diffY == 0){
            if(diffX > 0){
                return Math.PI/2;
            }
            else{
                return Math.PI*1.5;
            }
        }
        //angle is 0 or PI
        else if(diffX == 0){
            if(diffY > 0){
                return 0;
            }
            else{
                return Math.PI;
            }
        }

        //angle is more than 0 and less than PI/2
        if(diffX > 0 && diffY > 0){
            return Math.atan2(diffX, diffY);
        }
        //angle is more than PI/2 and less than PI
        else if (diffX > 0 && diffY < 0){
            return Math.PI/2 + Math.atan2(Math.abs(diffY), diffX);
        }
        //angle is more than PI and less than 1.5*PI
        else if(diffX < 0 && diffY < 0){
            return Math.PI+Math.atan2(Math.abs(diffX), Math.abs(diffY));
        }
        //angle is more than 1.5*PI and less than 2*PI
        else if(diffX < 0 && diffY > 0){
            return Math.PI*1.5 + Math.atan2(diffY, Math.abs(diffX));
        }
        else
            return -1;
    }
}
