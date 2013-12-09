package rawobjects;

import java.awt.Rectangle;

public class Building extends Rectangle implements java.io.Serializable {
    private int BuildingID;
    private int MapID;

    /**
     *
     * @param ID    Building ID
     * @param x     Coordinate x of top left corner building location
     * @param y     Coordinate y of top left corner building location
     * @param width   width of building location
     * @param height   height of building location
     */

    public Building (int ID,int MapID, int x, int y, int width, int height){
        super(x,y,width,height);
        this.BuildingID=ID;
        this.MapID = MapID;
    }

    public int getBuildingID(){
        return this.BuildingID;
    }

    public void setBuildingID(int ID){
        this.BuildingID=ID;
    }
    public int getMapID(){
        return this.MapID;
    }

    public void setMapID(int ID){
        this.MapID=ID;
    }
}
