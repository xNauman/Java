package holder;

import java.awt.Rectangle;
/**
 *
 * Building holder
 * @author Benjamin Wong
 */
public class Building extends Rectangle implements java.io.Serializable {
    private int BuildingID;

    /**
     *
     * @param ID    Building ID
     * @param x     Coordinate x of top left corner building location
     * @param y     Coordinate y of top left corner building location
     * @param width   width of building location
     * @param height   height of building location
     */

    public Building (int ID, int x, int y, int width, int height){
        super(x,y,width,height);
        this.BuildingID=ID;
    }

    public int getBuildingID(){
        return this.BuildingID;
    }

    public void setBuildingID(int ID){
        this.BuildingID=ID;
    }
}
