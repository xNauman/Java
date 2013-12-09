import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming.*;

import cc.registry.*;
import rmi_interfaces.*;

public class server extends UnicastRemoteObject implements CCInterface {

    private RegistryServer server;

    public server() throws Exception {

        initRMI();

        server = new RegistryServer();
        new Thread(server).start();

        print p = new print(server);
        new Thread(p).start();
    }

    public static void main(String[] args) throws Exception {
        new server();
    }

    public void initRMI() throws RemoteException {

        System.out.println("creating RMI....");

        Registry registry = LocateRegistry.createRegistry(4445);
        registry.rebind("CC", this);

        System.out.println("CC RMI Server created.");
    }

    // REMOTE METHOD PROVIDED FOR OTHER SUBSYSTEMS TO REQUEST FOR IP ADDRESS OF
    // SPECIFIED SUBSYSTEM FROM CC SERVER
    // (THIS IS JUST A SAMPLE TO DEMONSTARTE RMI INTEGRATION)
    public String getIP(String ID) throws RemoteException {
        return server.getClientIP(ID);
    }
}