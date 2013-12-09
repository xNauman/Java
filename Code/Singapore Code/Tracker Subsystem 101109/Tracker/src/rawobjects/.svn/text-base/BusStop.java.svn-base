package rawobjects;

import java.awt.Point;

public class BusStop implements java.io.Serializable {
    private static final long serialVersionUID = -1553214800962760846L;
    private int MapID;
    private int BusStopID;
    private String BusStopName;
    private Point PhysPoint;
    private Point LogPoint;
    private int PX_Start; //Pax_Count_Start
    private int PX_Min;//Pax_Count_Min
    private int PX_Max;//Pax_Count_Max
    private int PX_Count;//Pax_Count_Count

    /*x,y will be the physical point. The point that tracker will use to display.
    */
    public BusStop(){
        MapID =0;
        BusStopID =0;
        BusStopName = null;
        PhysPoint = new Point();
        LogPoint = new Point();
        PX_Start = 0;
        PX_Min =0;
        PX_Max =0;
        PX_Count =0;
    }
    public BusStop(int ID,String name,int mapID,int x,int y,int LogX,int LogY,int start,int min,int max,int count) {
        PhysPoint = new Point(x,y);
        this.MapID = mapID;
        this.BusStopID = ID;
        this.BusStopName = name;
        LogPoint = new Point(LogX,LogY);
        this.PX_Start = start;
        this.PX_Min = min;
        this.PX_Max = max;
        this.PX_Count = count;
    }


    public void setPhysPoint(Point PhysPoint){
        this.PhysPoint = PhysPoint;
    }
    public Point getPhysPoint() {
        return PhysPoint;
    }

    public void setLogPoint(Point logPoint){
        this.LogPoint = logPoint;
    }


    public Point getLogPoint() {
        return LogPoint;
    }


    public int getBusStopID() {
        return this.BusStopID;
    }


    public void setBusStopID(int ID) {
        this.BusStopID = ID;
    }
    public int getMapID(){
        return MapID;
    }
    public void setMapID(int mapID){
        this.MapID = mapID;
    }
    public String getBusStopName(){
        return this.BusStopName;
    }
    public void setBusStopName(String busStopName){
        this.BusStopName = busStopName;
    }
    public int getPX_Start(){
        return PX_Start;
    }
    public void setPX_Start(int PX_Start){
        this.PX_Start = PX_Start;
    }
    public int getPX_Min(){
        return PX_Min;
    }
    public void setPX_Min(int PX_Min){
        this.PX_Min = PX_Min;
    }
    public int getPX_Max(){
        return PX_Max;
    }
    public void setPX_Max(int PX_Max){
        this.PX_Max = PX_Max;
    }
    public int getPX_Count(){
        return PX_Count;
    }
    public void setPX_Count(int PX_Count){
        this.PX_Count = PX_Count;
    }
}