import java.rmi.*;
import java.sql.SQLException;
import java.util.Vector;
//need to import some packages here to get the object type from the packages

public interface DB_Interface extends Remote{
    //methods for CME

    public void saveMapIntoDB() throws RemoteException,SQLException; //need to put some argument that CME wants
                                                                //then break down the mapobject into vectors and save in DB
                                                                //this method for SU also, it should return a Map Type

    public Vector<String> getMapSavedInDB() throws RemoteException,SQLException; //need to break down the vector and
                                                                  //TRK calls respectively (improved later)
    //methods for TRK to load a map from database
    public Vector<String> getMapFromDB(String name) throws RemoteException,SQLException; //mentioned above, TRK will have vector
                                                                    //methods to get the data

    public int getNoOfBuses() throws RemoteException,SQLException;

    public int getNoOfPassengers() throws RemoteException,SQLException;

    //methods for SCH
    public void getMapInfo(int MapID) throws RemoteException,SQLException; // may needs to be broken into many submethods
                                                            //to return stops,route,route points...so void is not correct here
    public void updateLog(byte[] log) throws RemoteException;

    public void updatePAX() throws RemoteException,SQLException;

    public void saveBusObjectIntoDB() throws RemoteException,SQLException;  //also used for NAV

    //methods for CC
    public byte[] getLog() throws RemoteException,SQLException;

    public void Start() throws RemoteException,SQLException;

    public void Pause() throws RemoteException,SQLException;

    public void Stop() throws RemoteException,SQLException;

    public int getIPFromDB() throws RemoteException,SQLException;

    public int saveIPFromCCIntoDB() throws RemoteException,SQLException;

    //methods for NAV
    public void saveTrafficLightsIntoDB() throws RemoteException,SQLException;

    //public void saveBusObjectIntoDB() throws RemoteException,SQLException;
}
