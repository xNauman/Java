/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;

import java.rmi.*;

/**
 *
 * @author pingz
 */
public class SetupControl {

    /** Creates a new instance of Setup_Control */
    SetupRMI rmi;
    DBConnection con;

    public SetupControl(String dbip, String ccip) {
        con = new DBConnection("DB");
        try {
            rmi = new SetupRMI(dbip, ccip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getMapList() {

        try {
            return rmi.getMapList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[0];
//    
//    int[] mapList = {4,2,3};
//    return mapList; 
    }

    public String[] getRouteNameList(int mapID) {
        return rmi.getRouteNameList(mapID);
//    
//    String[] routeName = {"AAA", "BBB", "CCC", "DDD"};
//    return routeName;
     
    }

    public SetupDataObj getUserConfig(int mapID) {
        return con.getUserConfig(mapID);
    }

    public SetupDataObj getSystemConfig(int mapID) {
        return con.getSystemConfig(mapID);
    }

    public void setUserConfig(SetupDataObj su) {
        con.setUserConfig(su);
    }

    public float[] getFare(int mapID) {
        return con.getFare(mapID);
    }

    public void setFare(float[] fare, int mapID) {
        con.setFare(fare, mapID);
    }

    public int getMapID() {
//        return 1;
        return rmi.getMapID();
    }

    public void sendtoDB(SetupDataObj su, int mapID) {
        try {
            rmi.sendToDB(su, mapID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getNumOfPassenger() {

        try {
            return rmi.getNumOfPassenger();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[0];
//    
//    int[] test = {23,5,78,32};
//    return test; 
    }
}
