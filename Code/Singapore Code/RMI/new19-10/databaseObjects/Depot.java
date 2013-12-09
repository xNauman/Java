package rawobjects;

import java.awt.Point;

public class Depot
{
  private int MapID;
  private Point PhysPoint;
  private Point LogPoint;

  public Depot(){
      MapID =0;
      PhysPoint = null;
      LogPoint = null;
  }
  public Depot(int MapID,Point Phys,Point Log){
      this.MapID = MapID;
      this.PhysPoint = Phys;
      this.LogPoint = Log;
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
