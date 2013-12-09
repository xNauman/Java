
//Custom DBServer.java for MQ


import java.awt.image.*;
import java.rmi.*;
import java.sql.SQLException;
import java.util.Vector;
//import java.io.*;
//import rawobjects.*;
//need to import some packages here to get the object type from the packages

public interface DBServer extends Remote{

    //public void updateMapIntoDB(byte[] buffer) throws RemoteException,SQLException;
    //methods for TRK

    public int[] getSelectedMapPic() throws RemoteException,SQLException;

    public Vector<rawobjects.Bus> getSelectedMapBusDetails() throws RemoteException,SQLException;

    public rawobjects.Map getSelectedObj() throws RemoteException,SQLException;

    //methods for SCH
    public rawobjects.Map SCHgetMapSavedInDB(int MapID) throws RemoteException,SQLException;
                                                  
    public void updateLog(rawobjects.SYS_LOG log) throws RemoteException,SQLException;

    public boolean updatePAX(rawobjects.Pax_Stat pax) throws RemoteException,SQLException;

    public void saveBusObjectIntoDB(rawobjects.Bus bus) throws RemoteException,SQLException;  //also used for NAV

    public Vector<rawobjects.TrafficLight> getListOfTrafficLights(int pSimRunID) throws RemoteException,SQLException;

    public Vector<rawobjects.Route> getListOfRoutes(int pSimRunID) throws RemoteException,SQLException;

    public Vector<rawobjects.BusStop> getListOfStops(int pSimRunID) throws RemoteException,SQLException;

    public Vector<rawobjects.Bus> getListOfBuses(int pSimRunID,int pRouteID) throws RemoteException,SQLException;

    public Vector<rawobjects.Congestion> getListOfCongAreas(int pSimRunID) throws RemoteException,SQLException;

    public  boolean updateTrafficLight(rawobjects.TrafficLight pTrafficLight) throws RemoteException,SQLException;

    public  boolean updateBUS(rawobjects.Bus pBus) throws RemoteException,SQLException;

    public  boolean updateBusStop(rawobjects.BusStop pBusStop) throws RemoteException,SQLException;

    public rawobjects.Bus getBus(int pBUSID) throws RemoteException,SQLException;

    public rawobjects.TrafficLight getTrafficLight(int pTrafficLight) throws RemoteException,SQLException;

    public rawobjects.BusStop getStop(int pStopID) throws RemoteException,SQLException;

    public rawobjects.Depot getDepot(int pDepotID) throws RemoteException,SQLException;


    //methods for CC
    //public String getLog(String str) throws RemoteException,SQLException; //DB is client

    public void startupDB() throws RemoteException,SQLException;//DB is server

    //public void Pause() throws RemoteException,SQLException;

    public void stopDB() throws RemoteException,SQLException;//DB is server

    //public void updateStatus(String ID,int state) throws RemoteException,SQLException;//DB is client

    //public String getIPOfDB() throws RemoteException,SQLException;//DB is server

    //public long SimTimeShare() throws RemoteException;//DB is client

    //public String getIP(String ID) throws RemoteException,SQLException;//DB is client

    public void saveIPFromCCIntoDB(String ID,String IP) throws RemoteException,SQLException;//DB is server

    //methods for NAV
    public void saveTrafficLightsIntoDB(rawobjects.TrafficLight light) throws RemoteException,SQLException;

    public void saveBusObjectIntoDB(Vector<rawobjects.Bus> bus) throws RemoteException,SQLException;

    public Vector<rawobjects.Bus> getBusesFromDB()throws RemoteException,SQLException; //get a vector that contains all the
                                //buses in the database
    public Vector<rawobjects.TrafficLight> getTrafficLightsSavedInDB()throws RemoteException,SQLException; //get a vector that
                                //contains all of the traffic light objects in the database
    //methods for sybsystem needs it lol 
    public rawobjects.SYS_SIMULATOR_RUN getSYS_SIMULATOR_RUN()throws RemoteException,SQLException;;   //return an object
    
    //public rawobjects.Sys_Setting getSYS_SETTING()throws RemoteException,SQLException;;   //return an object

    //method for SU

    public Vector<rawobjects.Map> getListOfMapSavedInDB() throws RemoteException,SQLException;

    public void SUsaveMapToDB(byte[] buffer) throws RemoteException,SQLException;

    public Vector<rawobjects.Pax_Stat> getPAX_STAT() throws RemoteException,SQLException;

    public void sendBus(Vector<rawobjects.Bus> v)  throws RemoteException,SQLException;


}
