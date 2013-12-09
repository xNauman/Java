package cme.control;

import cme.interfaces.BusStopInterface;
import java.awt.Point;
import holder.BusStop;
import java.util.Vector;

/**
 * BusStopControl class consists of methods that handles the adding/deleting of
 * bus stop and all validations of bus stop.
 * @author Lai Sze Chuan
 */
public class BusStopControl extends BusDepotControl implements BusStopInterface{

    /**
     * addBusStop method handles the add bus stop function and check if the location
     * is suitable to add a bus stop.
     *
     * @param busDepotList  the bus/depot list where the newly created bus/depot
     *                      will be added
     * @param roadArray     the 2D array of the filtered Image
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              true if the bus stop is added, else false.
     */
    public boolean addBusDepot(Vector busDepotList,boolean[][] roadArray, int cursorX, int cursorY){
        
        Point nearestPoint = new Point(-1,-1);
        Point roadPoint = new Point(-1,-1);
        
        //Check the land whether it's on road and check whether the location has a bus stop already
        if(checkIsExist(busDepotList, cursorX, cursorY)== -1 &&
                checkLand(roadArray, cursorX, cursorY)==true){
            //Get the nearest point of road from the bus stop
            nearestPoint = nearestRoad(roadArray, cursorX, cursorY);
            //If the nearest point is valid
            if(nearestPoint.x!=-1){
                //Get the point across the road from the bus stop using the nearest point
                roadPoint = calRoadPoint(cursorX, cursorY, nearestPoint, roadArray);
                //If the point across the road is valid
                if(roadPoint.x!=-1){
                    int id = getBusDepotLastID(busDepotList);
                    id+=1;
                    busDepotList.add(new BusStop(id, cursorX, cursorY, roadPoint.x , roadPoint.y));
                    System.out.println("Valid Bus Stop: "+id);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * deleteBusStop method deletes an existing bus stop from the map. It will call
     * checkIsExist function to check for existing bus stop before deleting.
     *
     * @param busStopList   the bus stop list where the newly created bus/depot
     *                      will be added
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              true if the bus/depot is deleted, else false
     */
    public boolean deleteBusStop(Vector busStopList, int cursorX, int cursorY){

        //If bus stop list is empty
        if(busStopList.size()==0)
            return false;

        //check if the mouse click point exists a bus stop and assign the index to i
        int i = checkIsExist(busStopList, cursorX, cursorY);

        //if the index of bus stop is more than 0
        if(i>=0){
            busStopList.remove(i); //delete depot
            return true;
        }
        else
            return false;
    }
}
