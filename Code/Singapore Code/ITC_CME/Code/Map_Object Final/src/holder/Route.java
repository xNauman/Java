package holder;

import java.util.Vector;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Route Holder
 * @author  Poon Wen Jie
 */
public class Route implements java.io.Serializable {

    private String RouteID;
    private int[] thumbnail; //stores thumbnail of route
    private int width;       //map image width
    private int height;      //map image height
    private Vector<Point> points;
    private double distance;

    /**
     * 
     */
    public Route() {
        this.RouteID = null;
        this.points = null;
        this.thumbnail = null;
    }

    /**
     * 
     * @param ID    Route ID
     * @param routePoints   Points of the Route
     * @param bi    Thumbnail of Route in BufferedImage
     */
    public Route(String ID, Vector routePoints, BufferedImage bi) {
        this.RouteID = ID;
        this.points = routePoints;
        this.thumbnail = serialiseBufferedImage(bi);
        this.width = bi.getWidth();
        this.height = bi.getHeight();
    }

    /**
     * Return the Route ID
     * @return  Route ID
     */
    public String getRouteID() {
        return RouteID;
    }

    /**
     * Return the Route Points
     * @return  Points of the Route
     */
    public Vector getPoints() {
        return points;
    }

    /**
     * Set the Distance of the Route
     * @param dist  Route distance in multiples of 10metres
     */
    public void setDistance(double dist) {
        this.distance = dist;
    }

    /**
     * Return the Distance of the Route as N, where N * 0.01 = distance in kilometres
     * @return  Route distance in multiples of 10metres
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Serialise a BufferedImage
     * @param bi    BufferedImage to be serialised
     * @return  serialised image in integer array
     */
    private int[] serialiseBufferedImage(BufferedImage bi) {
        return ImageSerializer.serialiseBufferedImage(bi);
    }

    /**
     * Return the route thumnail
     * @return  Route thumbnail in BufferedImage
     */
    public BufferedImage getThumbnailInBufferedImage() {
        return ImageSerializer.intArrayToBufferedImage(thumbnail, width, height);
    }
}
