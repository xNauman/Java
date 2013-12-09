package rmi_interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CCRMIInterface extends Remote {


    public String getIP(String ID) throws RemoteException;
    public void updateStatus(String ID, int state) throws RemoteException;
    //public void setupComplete(boolean saved) throws RemoteException;
    public long SimTimeShare() throws java.rmi.RemoteException;
    public void sendImage() throws java.rmi.RemoteException;
    public void sendLog(String str) throws java.rmi.RemoteException;

  


}