
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author DuongDangChien
 */
public class SUServer extends UnicastRemoteObject implements SUInterface{
    private int hostPort;
    private String hostIP;
    private Registry localRegistry;


    public SUServer() throws RemoteException{
        super();
        String serverName = "SUServer";

        //get the local IP address
        try{
            this.hostIP = (InetAddress.getLocalHost()).toString();
        }
        catch(Exception e){
            throw new RemoteException("Cannot get the InetAddress.");
        }
        hostPort = 1234;
        System.out.println("Opening connection to Host: "+hostIP
                +" on port: "+hostPort);
        //create registry and bind the Name
        try{
            localRegistry = LocateRegistry.createRegistry(hostPort);
            localRegistry.bind(serverName, this);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        localRegistry.rebind(serverName, this);
    }

    public int[] getArray() throws RemoteException{
        int[] a = {0,1,2,3,4,5};
        System.out.println("The array from SU: ");
        return a;
    }

    public void main(String args[]){
        try{
            SUInterface SU = new SUServer();
        }
        catch(Exception e){
            e.printStackTrace();
        }           
    }
}
