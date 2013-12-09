import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CCRMIInterface extends Remote {

    // all subsystems can call this method to get IP address of another
    // subsystem with the specified ID - CME, DB, NAV, SCH, SU, TKR
    public String getIP(String ID) throws RemoteException;

    // all subsystems to call this method to update their status
    // ID - CME, DB, NAV, SCH, SU, TKR
    // 0: Not Operational
    // 1: Pre-Start Completed
    // 2: Ready to Start
    // 3: Started, and Operational
    public void updateStatus(String ID, int state) throws RemoteException;

    /* may not be needed. to be confirmed with SU again */
    // applicable only to SU
    public void setupComplete(boolean saved) throws RemoteException;

    // provide the PBSS time to synchronise all actions of each subsystem
    public String getTime() throws RemoteException;
}