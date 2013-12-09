package rmi_interfaces;

import java.rmi.*;
import rawobjects.*;

public interface TKRInterface extends Remote {

    //this interface will have the methods that the server wants to provide

    public byte[] getImage() throws RemoteException;

    public boolean moveBus(Bus reqBus, boolean isCongestedArea) throws RemoteException; //move selected bus

    public boolean updateTL(TrafficLight reqTrafficLight) throws RemoteException; //toggle selected traffic light

    public boolean updateStop(BusStop reqBusStop) throws RemoteException; //updated pax of passenger

    public boolean startupTKR() throws RemoteException;

    public int tkrState() throws RemoteException;

    public long SimTimeShare()  throws RemoteException;

    public boolean stopTKR() throws RemoteException;
    
    //ADDED: Ben as this wasn't added by NTU
    String debugEcho(String pFromModule, String pMessageToEcho) throws RemoteException;
}
