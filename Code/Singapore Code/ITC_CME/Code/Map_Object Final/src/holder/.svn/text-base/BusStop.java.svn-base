package holder;

import java.awt.Point;

/**
 * Bus Stop Holder
 * @author  Poon Wen Jie
 */
public class BusStop extends Point implements java.io.Serializable {

    private int BusStopID;
    private Point oppPoint;

    /**
     *
     * @param ID    BusStop ID
     */
    public BusStop(int ID) {   //For Scheduler.java
        super(0,0);
        this.BusStopID = ID;
    }
    /**
     *
     * @param x     Coordinate x of the BusStop location
     * @param y     Coordinate y of the BusStop location
     */
    public BusStop(int x,int y) {   //For NavBusStop.java
        super(x,y);
    }

    /**
     *
     * @param ID    BusStop ID
     * @param p     Coordinates of the BusStop location
     */
    public BusStop(int ID, Point p) {   //For TrkBusStop.java
        super(p);
        this.BusStopID = ID;
    }

    /**
     *
     * @param ID    BusStop ID
     * @param x     Coordinate x of the BusStop location
     * @param y     Coordinate y of the BusStop location
     * @param offroadX      Coordinate x of the point opposite the BusStop
     * @param offroadY      Coordinate y of the point opposite the BusStop
     */
    public BusStop(int ID, int x, int y, int offroadX, int offroadY) {
        super(x, y); //point to paint bus stop
        oppPoint = new Point(offroadX, offroadY); //point of bus stop across the road
        this.BusStopID = ID;
    }

    /**
     * Return the BusStop point across the road
     * @return  Point across the road
     */
    public Point getOppPoint() {
        return oppPoint;
    }

    /**
     * Return the BusStop ID
     * @return  BusStop ID
     */
    public int getBusStopID() {
        return this.BusStopID;
    }

    /**
     * Set the BusStop ID
     * @param ID    BusStop ID
     */
    public void setBusStopID(int ID) {
        this.BusStopID = ID;
    }
}