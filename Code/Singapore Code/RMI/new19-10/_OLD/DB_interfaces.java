import java.rmi.*;
import java.sql.SQLException;
import java.util.Vector;
import java.io.*;
//need to import some packages here to get the object type from the packages

public interface rmi_interfaces extends Remote{
    //methods for CME

    public void saveMapIntoDB(byte[] buffer) throws RemoteException,SQLException; //CME should be a client and they have a method
                                                                        //converts file into byte[](I can provide if they cant make
                                                                        //it) and I finished saving map into DB with this method

    public holder.Map CMEgetMapSavedInDB(String filename) throws RemoteException,SQLException;
    //methods for TRK

    public Vector getBusObject() throws RemoteException,SQLException;

    public holder.Map TRKgetMapSavedInDB() throws RemoteException,SQLException;

    //methods for SCH
    public holder.Map SCHgetMapSavedInDB(int MapID) throws RemoteException,SQLException;
                                                  
    public void updateLog(database.SYS_LOG log) throws RemoteException;

    public void updatePAX(database.Pax_Stat pax) throws RemoteException,SQLException;

    public void saveBusObjectIntoDB(database.Bus bus) throws RemoteException,SQLException;  //also used for NAV

    //methods for CC
    public String getLog() throws RemoteException,SQLException;

    public void Start() throws RemoteException,SQLException;

    public void Pause() throws RemoteException,SQLException;

    public void Stop() throws RemoteException,SQLException;

    public String getIPFromDB() throws RemoteException,SQLException;

    public void saveIPFromCCIntoDB(String ip) throws RemoteException,SQLException;

    //methods for NAV
    public void saveTrafficLightsIntoDB(database.TrafficLight light) throws RemoteException,SQLException;

    //public void saveBusObjectIntoDB(database.Bus bus) throws RemoteException,SQLException;

    public Vector getBusesFromDB()throws RemoteException,SQLException; //get a vector that contains all the
                                //buses in the database
    public Vector getTrafficLightsFromDB()throws RemoteException,SQLException;; //get a vector that
                                //contains all of the traffic light objects in the database
    //methods for sybsystem needs it lol 
    public database.SYS_SIMULATOR_RUN getSYS_SIMULATOR_RUN()throws RemoteException,SQLException;;   //return an object 
    
    public database.Sys_Setting getSYS_SETTING()throws RemoteException,SQLException;;   //return an object
    
    public database.BusStop getBusStop()throws RemoteException,SQLException;
    
    public database.Depot getDepot() throws RemoteException,SQLException;

    /*public Bus getBus(int pBUSID)
    public TrafficLight getTrafficLight(int pTrafficLight)
    public Bus_Stop getStop(int pStopID)
    public Depot getDepot(int pDepotID)*/

}
