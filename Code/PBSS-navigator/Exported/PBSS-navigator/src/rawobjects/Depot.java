package rawobjects;

import java.awt.Point;

public class Depot implements java.io.Serializable
{
  private static final long serialVersionUID = 9185586868113458080L;
  private int DepotID;
  private int MapID;
  private Point PhysPoint;
  private Point LogPoint;

  public Depot(){
      DepotID=0;
      MapID =0;
      PhysPoint = null;
      LogPoint = null;
  }
  public Depot(int ID,int MapID,Point Phys,Point Log){
      this.DepotID = ID;
      this.MapID = MapID;
      this.PhysPoint = Phys;
      this.LogPoint = Log;
  }
   public int getDepotID(){
    return DepotID;
   }
   public void setDepotID(int pDepotID){
    DepotID=pDepotID;
   }
   public int getMapID(){
    return MapID;
   }
   public void setMapID(int pMapID){
    MapID=pMapID;
   }

    public Point getPhysPoint(){
    return PhysPoint;
   }

   public void setPhysPoint(Point pPhysPoint){
    PhysPoint=pPhysPoint;
    }

   public Point getLogPoint(){
    return LogPoint;
   }

   public void setLogPoint(Point pLogPoint){
    LogPoint=pLogPoint;
    }
}
