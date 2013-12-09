package cme.interfaces;

import holder.Map;

/**
 * Interface class for TrafficLightControl
 * @author Wong Zhen Cong
 */
public interface TrafficJunctionInterface {
    public boolean addTrafficLight(Map map, boolean[][] roadArray, int posX, int posY);
    public boolean deleteTrafficLight(Map map, int posX, int posY);
    public boolean addJunction(Map map, boolean[][] roadArray, int posX, int posY);
    public boolean deleteJunction(Map map, int posX, int posY);
}