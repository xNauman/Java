package rmi_interfaces;

import java.rmi.*;
import java.sql.SQLException;
import java.util.Vector;
//import java.io.*;
import rawobjects.*;
//need to import some packages here to get the object type from the packages

public interface rmi_interfaces extends Remote{
    //methods for CME

    public void saveMapIntoDB(byte[] buffer) throws RemoteException,SQLException; //CME should be a client and they have a method
                                                                        //converts file into byte[](I can provide if they cant make
                                                                        //it) and I finished saving map into DB with this method

    public Map CMEgetMapSavedInDB(String filename) throws RemoteException,SQLException;
    //methods for TRK

    public byte[] TRKgetMapFileSavedInDB() throws RemoteException,SQLException;

    public Vector getBusSavedInDB() throws RemoteException,SQLException;

    public Map TRKgetMapSavedInDB() throws RemoteException,SQLException;

    //methods for SCH
    public Map SCHgetMapSavedInDB(int MapID) throws RemoteException,SQLException;
                                                  
    public boolean updateLog(SYS_LOG log) throws RemoteException;

    public boolean updatePAX(Pax_Stat pax) throws RemoteException,SQLException;

    public boolean saveBusObjectIntoDB(Bus bus) throws RemoteException,SQLException;  //also used for NAV

    public Vector getListOfTrafficLights(int pSimRunID) throws RemoteException,SQLException;

    public Vector getListOfRoutes(int pSimRunID) throws RemoteException,SQLException;

    public Vector getListOfStops(int pSimRunID) throws RemoteException,SQLException;

    public Vector getListOfBuses(int pSimRunID,int pRouteID) throws RemoteException,SQLException;

    public Vector getListOfCongAreas(int pSimRunID) throws RemoteException,SQLException;

    public  boolean updateTrafficLight(TrafficLight pTrafficLight) throws RemoteException,SQLException;

    public  boolean updateBUS(Bus pBus) throws RemoteException,SQLException;

    public  boolean updateBusStop(BusStop pBusStop) throws RemoteException,SQLException;

    public Bus getBus(int pBUSID);

    public TrafficLight getTrafficLight(int pTrafficLight);

    public BusStop getStop(int pStopID);

    public Depot getDepot(int pDepotID);


    //methods for CC
    public String getLog() throws RemoteException,SQLException;

    public void startDB() throws RemoteException,SQLException;

    //public void Pause() throws RemoteException,SQLException;

    public void stopDB() throws RemoteException,SQLException;

    public boolean DBState() throws RemoteException,SQLException;

    public String getIPFromDB() throws RemoteException,SQLException;

    public void saveIPFromCCIntoDB(String ip) throws RemoteException,SQLException;

    //methods for NAV
    public void saveTrafficLightsIntoDB(TrafficLight light) throws RemoteException,SQLException;

    //public void saveBusObjectIntoDB(database.Bus bus) throws RemoteException,SQLException;

    //public Vector getBusesFromDB()throws RemoteException,SQLException; //get a vector that contains all the
                                //buses in the database
    public Vector getTrafficLightsSavedInDB()throws RemoteException,SQLException;; //get a vector that
                                //contains all of the traffic light objects in the database
    //methods for sybsystem needs it lol 
    public SYS_SIMULATOR_RUN getSYS_SIMULATOR_RUN()throws RemoteException,SQLException;;   //return an object 
    
    public Sys_Setting getSYS_SETTING()throws RemoteException,SQLException;;   //return an object
    
    public BusStop getBusStop()throws RemoteException,SQLException;
    
    public Depot getDepot() throws RemoteException,SQLException;

    //method for SU

    public Vector getListOfMapSavedInDB() throws RemoteException,SQLException;
}
