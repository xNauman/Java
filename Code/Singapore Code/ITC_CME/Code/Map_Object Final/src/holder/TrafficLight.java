package holder;

import java.awt.Point;
import java.util.Vector;

/**
 * TrafficLight Holder
 * @author Poon Wen Jie
 */
public class TrafficLight extends Point implements java.io.Serializable {

    private int trafficLightID;
    private Vector<Double> roadAngle;
    /*stores angles of roads connected to this TrafficLight, anti-clockwise
    relative to south*/

    /**
     *
     * @param x     Coordinate x of the TrafficLight location
     * @param y     Coordinate y of the TrafficLight location
     */
    public TrafficLight(int x, int y) {     // For NavTrafficLight.java
        super(x,y);
    }

    /**
     *
     * @param ID    TrafficLight ID
     */
    public TrafficLight(int ID) {  // For TrkTrafficLight.java
        super();
        this.trafficLightID = ID;
    }

    /**
     *
     * @param ID    TrafficLight ID
     * @param x     Coordinate x of the TrafficLight location
     * @param y     Coordinate x of the TrafficLight location
     * @param roadAngle   vector list of angles of the roads branching out from this TrafficLight, degrees anti-clockwise relative to south
     */
    public TrafficLight(int ID, int x, int y, Vector roadAngle) {
        super(x, y);
        this.trafficLightID = ID;
        this.roadAngle = roadAngle;
    }

    /**
     * Return the TrafficLight ID
     * @return TrafficLight ID
     */
    public int getTrafficLightID() {
        return trafficLightID;
    }

    /**
     * Return the Vector list of angles of roads branching out from this TrafficLight
     * @return  angles of roads branching out from this TrafficLight in a vector list, degrees anti-clockwise relative to south
     */
    public Vector getRoadAngle() {
        return roadAngle;
    }

    /**
     * Return the number of roads branching out from this TrafficLight
     * @return  number of roads branching out from this TrafficLight
     */
    public int getNoOfIntersections() {
        return roadAngle.size();
    }

    /**
     * Set the TrafficLight ID
     * @param ID    TrafficLight ID
     */
    public void setTrafficLightID(int ID) {
        this.trafficLightID = ID;
    }
}
