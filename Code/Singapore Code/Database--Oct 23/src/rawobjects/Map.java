package rawobjects;


import java.util.Vector;
import java.awt.Point;
import java.awt.image.BufferedImage;


public class Map implements java.io.Serializable{
    private int mapID;                       //stores map identifier
    private String mapName;
    private int[] serializedMapImage;           //stores map image
    private int width;                          //map image width
    private int height;                         //map image height
    /**
     * Vector of Routes
     */
    public Vector <Route> routes;               //stores routes
    /**
     * Vector of BusStops
     */
    public Vector <BusStop> busStops;           //stores bus stops
    /**
     * Vector of TrafficLights
     */
    public Vector <TrafficLight> trafficLights; //stores traffic lights
    /**
     * Vector of Junctions
     */
    public Vector <Point> junctions;            //stores junctions
    /**
     * Vector of Depots
     */
    public Vector <Depot> depots;               //stores depots
    /**
     * Vector of road area coordinates
     */
    public Vector <Point> roadArea;             //stores coordinates of road area
    /**
     * Vector of building area coordinates
     */
    public Vector <Building> buildings;         //stores buildings
    /**
     * Vector of congestion area coordinates
     */
    public Vector <Congestion> congestion;      //store congestion location
    /**
     *
     * @param ID    Map ID
     * @param bi    Map image as BufferedImage
     */
    public Map(int ID,String mapName, BufferedImage bi){
        this.mapID = ID;
        this.mapName = mapName;
        this.width = bi.getWidth(); //initialise map image width and height
        this.height = bi.getHeight();
        serializedMapImage = serialiseBufferedImage(bi);
        routes = new Vector(); //a list of routes for every map
        busStops = new Vector(); //a list of bus stops for every map. all routes share the same bus stops
        trafficLights = new Vector(); //a list of traffic lights every map. all routes share the same traffic lights
        junctions = new Vector(); //a list of junctions on the map
        depots = new Vector();
        roadArea = new Vector();
        buildings = new Vector();
        congestion = new Vector();
    }

    /**
     * Set Map ID
     * @param ID    Map ID
     */
    public void setMapName(String Name){
        this.mapName = Name;
    }

    /**
     * Return Map ID
     * @return Map ID
     */
    public String getMapName(){
        return this.mapName;
    }

    public void setMapID(int ID){
        this.mapID = ID;
    }
    public int getMapID(){
        return mapID;
    }
    /**
     * Serialises the map image
     * @param bi    BufferedImage to be serialise
     * @return  serialised image as integer array
     */
    private int[] serialiseBufferedImage(BufferedImage bi) {
        return ImageSerializer.serialiseBufferedImage(bi);
    }

    /**
     * Return the map image as a BufferedImage
     * @return  BufferedImage of the map
     */
    public BufferedImage getMapInBufferedImage() {
        return ImageSerializer.intArrayToBufferedImage(serializedMapImage, width, height);
    }
}