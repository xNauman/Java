package holder;

import java.awt.Rectangle;
/**
 *
 * Congestion holder
 * @author Benjamin Wong
 */
public class Congestion extends Rectangle implements java.io.Serializable {
    private int CongestionID;

    /**
     *
     * @param ID    Congestion ID
     * @param x     Coordinate x of top left corner congestion location
     * @param y     Coordinate y of top left corner congestion location
     * @param width   width of congestion location
     * @param height   height of congestion location
     */

    public Congestion (int ID, int x, int y, int width, int height){
        super(x,y,width,height);
        this.CongestionID=ID;
    }

    public int getCongestionID(){
        return this.CongestionID;
    }

    public void setCongestionID(int ID){
        this.CongestionID=ID;
    }
}