import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMI_Interface extends Remote {

    public String getID(int subsystem) throws RemoteException;
    public String getIP(int subsystem) throws RemoteException;

    public void updateStatus_CME(int state) throws RemoteException;
    public void updateStatus_DB(int state) throws RemoteException;
    public void updateStatus_NAV(int state) throws RemoteException;
    public void updateStatus_SCH(int state) throws RemoteException;
    public void updateStatus_SU(int state) throws RemoteException;
    public void updateStatus_TKR(int state) throws RemoteException;

    public void setupComplete(boolean done) throws RemoteException;

    //public boolean running() throws RemoteException;
    //public Object getTime() throws RemoteException;
}