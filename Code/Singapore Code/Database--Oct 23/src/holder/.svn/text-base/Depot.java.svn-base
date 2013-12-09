package holder;

import java.awt.Point;

/**
 * Depot Holder
 * @author  Poon Wen Jie
 */
public class Depot extends Point implements java.io.Serializable{
    private int DepotID;
    private Point onRoadPoint;

    /**
     *
     * @param ID    Depot ID
     * @param x     Coordinate x of the Depot location
     * @param y     Coordinate y of the Depot location
     * @param onRoadX   Coordinate x of the point on road
     * @param onRoadY   Coordinate y of the point on road
     */
    public Depot(int ID, int x, int y, int onRoadX, int onRoadY){
        super(x, y); //point off road
        onRoadPoint = new Point(onRoadX, onRoadY); //point on road
        this.DepotID = ID;
    }

    /**
     * Return the Depot ID
     * @return  Depot ID
     */
    public int getDepotID(){
        return this.DepotID;
    }

    /**
     * Return the coordinates on road representing the Depot entry/exit point
     * @return  coordinates of the depot point on road as Point
     */
    public Point getRoadPoint() {
        return onRoadPoint;
    }
}
