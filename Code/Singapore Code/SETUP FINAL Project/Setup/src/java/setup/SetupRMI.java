/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package setup;

import java.rmi.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import cme.model.*;
import pbssdbpackage.DatabaseClass;
import ccPackage.ControlRMI;

/**
 *
 * @author CHAK0003
 */
public class SetupRMI {
    //ClientIP ip;
    String dbip, ccip;
    cme.model.List mapList;
    // Implementations must have an 
    //explicit constructor 
    // in order to declare the 
    //RemoteException exception 
    
    public SetupRMI(String dbip, String ccip)
            throws java.rmi.RemoteException {
        //ip = new ClientIP();
        this.dbip = dbip;
        this.ccip = ccip;

        System.out.println("DB IP: " + dbip);
        System.out.println("CC IP: " + ccip);
    }

    public void sendToDB(setup.SetupDataObj setup, int mapID) {
        try {
            System.out.println("Sending Object:" + dbip);
            DatabaseClass dbc = (DatabaseClass) Naming.lookup("rmi://" + dbip + "/DatabaseService");
            dbc.init_DB_Connection();
            ControlRMI ctrRMI = (ControlRMI) Naming.lookup("rmi://" + ccip + "/ControlService");
            ctrRMI.setSuConfirm(dbc.upload_SU_SetUp_Data(setup, mapID));
            System.out.println("Upload Complete");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getMapList()
            throws java.rmi.RemoteException {
        int[] numMaps = new int[0];
        try {
            DatabaseClass dbc = (DatabaseClass) Naming.lookup("rmi://" + dbip + "/DatabaseService");
            dbc.init_DB_Connection();
            mapList = (cme.model.List) dbc.download_SU_Map_Objects();
            numMaps = new int[mapList.size()];
            for (int i = 0; i < numMaps.length; i++) {
                numMaps[i] = ((cme.model.Map) mapList.getItem(i + 1)).getBusRouteList().size();
            }
        
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return numMaps;
    }
    
    public String[] getRouteNameList(int mapID) {
        Map map = (Map) mapList.getItem(mapID);
        String[] routeNameList = new String[map.getBusRouteList().size()];
        for (int i = 0; i < routeNameList.length; i++) {
            cme.model.BusRoute route = (cme.model.BusRoute) map.getBusRouteList().getItem(i + 1);
            routeNameList[i] = route.getBRouteName();
        }
        return routeNameList;
    }

    public void setDBIP(String db) {
        dbip = db;
        System.out.println("Set DBIP: " + dbip);
    }

    public void setCCIP(String cc) {
        ccip = cc;
        System.out.println("Set CCIP: " + ccip);
    }

    public int getMapID() {
        try {
            DatabaseClass dbc = (DatabaseClass) Naming.lookup("rmi://" + dbip + "/DatabaseService");
            dbc.init_DB_Connection();
            return dbc.download_SCH_SetUp_Data().getMapID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int[] getNumOfPassenger() {
        int[] numOfPassenger = new int[0];
        try {
            Scheduler.Passengers pg;
            DatabaseClass dbc = (DatabaseClass) Naming.lookup("rmi://" + dbip + "/DatabaseService");
            dbc.init_DB_Connection();
            pg = dbc.download_SU_Passenger_Count();
            numOfPassenger = pg.getPass();
            System.out.println("Passenger object obtained");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numOfPassenger;
    }
}
