package rmi_interfaces;

import java.rmi.*;

public interface CMEInterface extends Remote {

    public boolean startupCME() throws RemoteException;
    public boolean stopCME() throws RemoteException;
}