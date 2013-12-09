package cme.interfaces;

import java.util.Vector;

/**
 * Interface class for BusStopControl
 * @author Lai Sze Chuan
 */
public interface BusStopInterface {
    public boolean addBusDepot(Vector busDepotList,boolean[][] roadArray, int cursorX, int cursorY);
    public boolean deleteBusStop(Vector busDepotList, int cursorX, int cursorY);
}
