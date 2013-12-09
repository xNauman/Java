package server;

import cc.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming.*;

import cc.registry.*;

public class server extends UnicastRemoteObject{

    private RegistryServer ccRegistry;
    public static RMIServer rmiServer;

    public server() throws Exception {

        try {
            ccRegistry = new RegistryServer(this);
            rmiServer = new RMIServer(this, ccRegistry);
        }catch(Exception e){
            System.err.print(e);
         }

        new Thread(ccRegistry).start();
        new Thread(rmiServer).start();
    }

    public static void main(String[] args) throws Exception {
        new server();
    }


}