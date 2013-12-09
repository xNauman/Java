import database.Map;
import java.rmi.Remote;
import java.rmi.RemoteException;
public interface SU_Interface extends Remote{
    //DB calls this method to save initialization map(with preset values)
    public Map saveMapToDB() throws RemoteException;
    //CC calls this method to know whether SU finishes initialization phase.
    public boolean SU_ready() throws RemoteException;
}