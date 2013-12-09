package rmi_interfaces;

import java.rmi.*;

public interface SUInterface extends Remote {

    public boolean startupSU() throws RemoteException;
    public boolean stopSU() throws RemoteException;
}