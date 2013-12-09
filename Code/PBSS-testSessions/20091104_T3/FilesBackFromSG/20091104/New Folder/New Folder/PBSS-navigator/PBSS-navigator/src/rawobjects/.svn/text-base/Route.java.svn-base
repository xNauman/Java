package rawobjects;

import java.util.Vector;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Route implements java.io.Serializable {

    	private static final long serialVersionUID = -5541418918389235746L;
    private int RouteID;
    private int MapID;
    private String RouteNbr;
    private String RouteName;
    private short DepartEvery;
    private int StartDepotID;
    private int StopDepotID;
    private Vector<ROUTE_POINT> points;


    /**
     *
     */
    public Route() {
        this.RouteID = 0;
        this.MapID = 0;
        this.RouteName = null;
        this.RouteNbr = null;
        this.DepartEvery = 0;
        this.StartDepotID = 0;
        this.StopDepotID = 0;
        this.points = new Vector<ROUTE_POINT>();
    }

        @SuppressWarnings("unchecked")
    public Route(int RouteId,int MapID,String Name,String Nbr,short DepartEvery,int StartDepotId,int StopDepotID,Vector point){
        this.RouteID = RouteId;
        this.MapID = MapID;
        this.RouteName = Name;
        this.RouteNbr = Nbr;
        this.DepartEvery = DepartEvery;
        this.StartDepotID = StartDepotId;
        this.StopDepotID = StopDepotID;
        this.points = point;
    }
    public int getMapID(){
        return this.MapID;
    }
    public void setMapID(int iMapID){
        this.MapID = iMapID;
    }
    public String getRouteNbr(){
        return RouteNbr;
    }
    public void setRouteNbr(String pRouteNbr){
        this.RouteNbr = pRouteNbr;
    }
    public short getDepartEvery(){
        return this.DepartEvery;
    }
    public void setDepartEvery(short pDepartEvery){
        this.DepartEvery = pDepartEvery;
    }
    public int getStartDepotID(){
        return this.StartDepotID;
    }
    public void setStartDepotID(int iStartDepotID){
        this.StartDepotID = iStartDepotID;
    }
    public int getStopDepotID(){
        return this.StopDepotID;
    }
    public void setStopDepotID(int iStopDepotID){
        this.StopDepotID = iStopDepotID;
    }
    public String getRouteName() {
        return RouteName;
    }
    public void setRouteName(String pRouteName){
        this.RouteName = pRouteName;
    }
    public int getRouteID() {
        return RouteID;
    }
    public void setRouteID(int pRouteID){
        this.RouteID = pRouteID;
    }

    public Vector getPoints() {
        return points;
    }
    public void setPoints(Vector pPoints){
        this.points = pPoints;
    }


}
