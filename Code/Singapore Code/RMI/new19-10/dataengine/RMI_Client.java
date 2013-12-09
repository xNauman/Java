package cme.dataengine;

import RMI.DB;
import holder.Map;
import java.rmi.*;
import java.net.InetAddress.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI Client to communuicate with server
 * @author Guan Mei Ting
 */
public class RMI_Client {

    private static Registry reg;
    private static String rmi_ip;
    private static DB rmiDB = null;

    /**
     *
     */
    public RMI_Client() {    //constructor
    }

    /**
     * Send map to database for storage
     * @param map the whole map object to be send to database.
     */
    public static boolean send_map(Map map) {

        System.setSecurityManager(new RMISecurityManager());
        try {
            //RMIRegistry ip may have to be hardcoded
            reg = LocateRegistry.getRegistry(rmi_ip);
            rmiDB = (DB) reg.lookup("DB");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return false;
        }

        try {
            return rmiDB.storeMap(map, map.getMapID());

        }
        catch (RemoteException ex) {
            System.out.println("Error: Remote Exception Occured");
        }
        return false;
    }

    /**
     * Call database to return the map object
     * @param id the map id of the that we call
     * @return map of the id
     */
    public static Map get_map(String id) {
        System.setSecurityManager(new RMISecurityManager());
        try {
            //RMIRegistry ip may have to be hardcoded
            reg = LocateRegistry.getRegistry(rmi_ip);
            rmiDB = (DB) reg.lookup("DB");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return null;
        }

        try {
            return rmiDB.getMapObject(id);
        }
        catch (RemoteException ex) {
            System.out.println("Error: Remote Exception Occured");
        }
        return null;
    }


    /**
     * Call database to return the map list
     * @return map the whole map list
     */
    public static String[] getMapList() {

        System.setSecurityManager(new RMISecurityManager());
        try {
            //RMIRegistry ip may have to be hardcoded
            reg = LocateRegistry.getRegistry(rmi_ip);
            rmiDB = (DB) reg.lookup("DB");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            return null;
        }

        try {
            return rmiDB.getMapList();
        }
        catch (RemoteException ex) {
            System.out.println("Error: Remote Exception Occured");
        }
        return null;
    }

    //----------------- Template -----------------------------
    /**
     * Send RMI IP address
     * @param ip the address of rmi server
     */
    public void setIP(String ip) {
        rmi_ip = ip;
    //System.out.println(ip);
    }

    private void connectReg() throws Exception {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        if (rmi_ip == null) {
            System.out.println("Not connected! Check connections!");
            // need to create an error box in application.........Exception handler?
            return;
        }
        reg = LocateRegistry.getRegistry(rmi_ip);
    }

    private void disconnectReg() {
        reg = null;
    }
}