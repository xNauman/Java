package cme.interfaces;

import java.awt.image.BufferedImage;
import java.util.Vector;
import cme.gui.CityMapEditor;
import holder.Map;

/**
 * Interface class for RouteControl
 * @author Yin Ming Jun Kevin
 */
public interface RouteInterface {
    public void addWayPoint(Vector routePoints, int posX, int posY);
    public void deleteWayPoint(Vector routePoints);
    public boolean validateRoute(CityMapEditor cme, Map map, boolean[][] roadArray,Vector routePoints);
    public boolean saveRoute(CityMapEditor cme, Map map, Vector routePoints);
    public void clearRoute(Vector routePoints);
    public void deleteRoute(CityMapEditor cme, Map map, int routeIndex);
    public void updateRouteList(CityMapEditor cme, Map map);
}
