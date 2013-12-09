package holder;


import java.util.Vector;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Map Holder
 * @author  Poon Wen Jie
 */
public class Map implements java.io.Serializable{
    private String mapID;                       //stores map identifier
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
     *
     * @param ID    Map ID
     * @param bi    Map image as BufferedImage
     */
    public Map(String ID, BufferedImage bi){
        this.mapID = ID;
        this.width = bi.getWidth(); //initialise map image width and height
        this.height = bi.getHeight();
        serializedMapImage = serialiseBufferedImage(bi);
        routes = new Vector(); //a list of routes for every map
        busStops = new Vector(); //a list of bus stops for every map. all routes share the same bus stops
        trafficLights = new Vector(); //a list of traffic lights every map. all routes share the same traffic lights
        junctions = new Vector(); //a list of junctions on the map
        depots = new Vector();
        roadArea = new Vector();
    }

    /**
     * Set Map ID
     * @param ID    Map ID
     */
    public void setMapID(String ID){
        this.mapID = ID;
    }

    /**
     * Return Map ID
     * @return Map ID
     */
    public String getMapID(){
        return this.mapID;
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