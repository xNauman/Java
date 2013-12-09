package cme.control;

import cme.gui.CityMapEditor;
import cme.interfaces.DepotInterface;
import cme.interfaces.RouteInterface;
import java.awt.Point;
import java.util.Vector;
import holder.Depot;
import holder.Map;
import holder.Route;
import javax.swing.JOptionPane;

/**
 * DepotControl class consists of methods that handles the adding/deleting of 
 * depots and all validations of depot.   
 * @author Lai Sze Chuan
 */
public class DepotControl extends BusDepotControl implements DepotInterface{
    
    /**
     * addBusDepot method handles the add depot function and validates if the
     * location is suitable to add a depot.
     *
     * @param busDepotList  the bus/depot list where the newly created bus/depot
     *                      will be added
     * @param roadArray     the 2D boolean array of the filtered map image, true
     *                      for road and false for land
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              true if the depot is added, else false
     */
    public boolean addBusDepot(Vector busDepotList,boolean[][] roadArray, int cursorX, int cursorY){
        
        if(busDepotList.size()<4){
            Point nearestPoint = new Point(-1,-1);
            Point roadPoint = new Point(-1,-1);
            //Check the land whether it's on road and check whether the location has a depot already
            if(checkIsExist(busDepotList, cursorX, cursorY)== -1 &&
                    checkLand(roadArray, cursorX, cursorY)==true){
                //Get the nearest point of road from the bus stop
                nearestPoint = nearestRoad(roadArray, cursorX, cursorY);
                //If the nearest point is valid
                if(nearestPoint.x!=-1){
                    //Get the center point of the road from the depot using the nearest point
                    roadPoint = calRoadPoint(cursorX, cursorY, nearestPoint, roadArray);
                    //If the center point of the road is valid
                    if(roadPoint.x!=-1){
                        //get the id of last added depot
                        int id = getBusDepotLastID(busDepotList);
                        roadPoint.x = (roadPoint.x + nearestPoint.x)/2;
                        roadPoint.y = (roadPoint.y + nearestPoint.y)/2;
                        //add depot
                        id+=1;
                        busDepotList.add(new Depot(id, cursorX, cursorY,
                                roadPoint.x , roadPoint.y));
                        System.out.println("Valid Depot: "+id);
                        return true; 
                    }
                }
            }
        }
        return false;
    }

    /**
     * deleteDepot method deletes an existing depot from the map. It will call
     * checkIsExist function to check for existing depot before deleting. The
     * routes that are linked to the depot will also be deleted.
     *
     * @param cme           the City Map Editor application main class
     * @param map           the selected map to be updated
     * @param routeCtrl     the route interface to update the routes
     * @param cursorX       the x coordinate of the mouse click
     * @param cursorY       the y coordinate of the mouse click
     * @return              true if the bus/depot is deleted, else false
     */
    public boolean deleteDepot(CityMapEditor cme, Map map, RouteInterface routeCtrl, int cursorX, int cursorY){
        //If bus or depot list is empty
        if(map.depots.size()==0)
            return false;

        //check if the mouse click point exists a depot and assign the index of depot to i
        int i = checkIsExist(map.depots, cursorX, cursorY);

        //if the index of depot is more than 0
        if(i>=0){
            Point temp = (Point)(((Depot)map.depots.get(i)).getRoadPoint());
            StringBuffer routeAffected = new StringBuffer();
            for(int j=0; j<map.routes.size(); j++){
                Route tempRoute = (Route)map.routes.get(j);
                if(((Point)tempRoute.getPoints().get(0)).equals(temp)==true ||
                        ((Point)tempRoute.getPoints().get(tempRoute.getPoints().size()-1)).equals(temp)==true){
                    routeAffected.append(tempRoute.getRouteID());
                    routeAffected.append(", ");
                }
            }
            if(routeAffected.length()==0) {
                map.depots.remove(i); //delete depot
                routeCtrl.updateRouteList(cme, map);
            }
            else {
                int delete = JOptionPane.showConfirmDialog(null,
                        "The following affected route(s): <" +
                        routeAffected.substring(0, routeAffected.length()-2) +
                        "> will be removed. Do you want to delete this depot?",
                        "Delete Depot",
                        JOptionPane.YES_NO_OPTION);
                if (delete == 0) {
                    for(int j=map.routes.size()-1; j>=0; j--){
                        Route tempRoute = (Route)map.routes.get(j);
                        if(((Point)tempRoute.getPoints().get(0)).equals(temp)==true ||
                                ((Point)tempRoute.getPoints().get(tempRoute.getPoints().size()-1)).equals(temp)==true){
                            map.routes.remove(j);
                        }
                    }
                    map.depots.remove(i); //delete depot
                    routeCtrl.updateRouteList(cme, map);
                }
            }
            return true;
        }
        else
            return false;
    }
}
