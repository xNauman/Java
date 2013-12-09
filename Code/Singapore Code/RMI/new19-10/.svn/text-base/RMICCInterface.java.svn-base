import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Interface extends Remote {

    /* METHODS PROVIDED */

    // applicable to all subsystems
    // ID - CME, DB, NAV, SCH, SU, TKR
    public String getIP(String ID) throws RemoteException;

    // applicable to all subsystems
    // ID - CME, DB, NAV, SCH, SU, TKR
    // state - 0:offline, 1:initialising, 2:ready
    public void updateStatus(String ID, int state) throws RemoteException;

    // applicable only to SU
    public void setupComplete(boolean saved) throws RemoteException;

    /* METHODS TO BE REQUESTED */

    // applicable to all subsystems
    public void prestart() throws RemoteException;
    public void start() throws RemoteException;
    public void stop() throws RemoteException;

    // applicable only to TKR
    public void getImage() throws RemoteException;
}