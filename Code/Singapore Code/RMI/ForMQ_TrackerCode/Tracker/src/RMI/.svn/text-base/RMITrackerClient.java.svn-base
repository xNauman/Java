/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RMI;

/**
 *
 * @author David
 */

import rmi_interfaces.CCRMIInterface;
import rmi_interfaces.RMITrackerInterface;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.Vector;


import clock.*;
import rawobjects.*;
import rmi_interfaces.*;

public class RMITrackerClient {

    private String ccIP;
    private String dbIP;
    private Registry ccReg, dbReg;
    private int serverPort = 4444;
    CCRMIInterface CC;
    rmi_interfaces DB;
    private Clock TKRClock;

    //private long ccTime;
    private Map map;
    private BufferedImage mapPic;
    private static Vector busStop;
    private static Vector routes;
    private static Vector trafficLights;
    private static Vector junctions;
    private static Vector roadArea;
    private static Vector depots;
    private static Vector buildings;
    private static Vector congestion;
    private static Vector busVector;

    //Clock variables
    long CCclock;
    int modifier;

    public RMITrackerClient(String ip){
        try{
           
            ccIP = ip;
            ccReg = LocateRegistry.getRegistry(ccIP,serverPort); //retrieve registry from CC
            CC = (CCRMIInterface)(ccReg.lookup("CC"));
            System.out.println("CC connection established");
            TKRClock = new Clock(null,0,0,0);
             //Starting our Simulation Clock
            TKRClock.StartClock();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean connectToDB(){
        try{
            dbIP = CC.getIP("DB");
            System.out.println("DB IP is "+dbIP);
            if(dbIP.equals(null)) return false; //return false if fail to get IP address; chance of this happening = strike TOTO
            dbReg = LocateRegistry.getRegistry(dbIP,serverPort);
           DB = (rmi_interfaces)(dbReg.lookup("DB"));
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
           //public String getIP(String ID) throws RemoteException;
    }

    public String returnDBIP(){
        try{
        dbIP = CC.getIP("DB");
        }catch(Exception e){
            e.printStackTrace();
        }
        return dbIP;
    }

    public boolean getMapData(){
        try{
            //get Map object from DB
            map = DB.TRKgetMapSavedInDB();
            if(map == null) return false;
            mapPic = map.getMapInBufferedImage();
            File file = new File("image.jpg");
            if(!ImageIO.write(mapPic, "jpg", file)) return false;
            busStop = map.busStops;
            if(busStop.size() ==0) return false;
            routes = map.routes;
            if(routes.size() ==0) return false;
            trafficLights = map.trafficLights;
            if(trafficLights.size() ==0) return false;
            junctions = map.junctions;
            if(junctions.size() ==0) return false;
            roadArea = map.roadArea;
            if(roadArea.size() ==0) return false;
            depots = map.depots;
            if(depots.size() ==0) return false;
            buildings = map.buildings;
            if(buildings.size() ==0) return false;
            congestion = map.congestion;
            if(congestion.size() ==0) return false;

            //get Bus object from DB
            busVector = DB.getBusSavedInDB();
            if(busVector.size() ==0) return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Vector getBusStopVector(){
        return busStop;
    }
    public static Vector getRoutesVector(){
        return routes;
    }
    public static Vector getTrafficLightsVector(){
        return trafficLights;
    }
    public static Vector getJunctionsVector(){
        return junctions;
    }
    public static Vector getRoadAreaVector(){
        return roadArea;
    }
    public static Vector getDepotsVector(){
        return depots;
    }
    public static Vector getBuildingsVector(){
        return buildings;
    }
    public static Vector getCongestionVector(){
        return congestion;
    }

    public static Vector getBusVector(){
        return busVector;
    }



       //Getting CC simulation time

       private void Setclock(){
           try{
       CCclock = CC.SimTimeShare();}
           catch(Exception e){
            e.printStackTrace();
           }
       //Creating our own clock object
       
       //Get offset after commpare with CC clock
       modifier = TKRClock.compairTime(CCclock);
       //Modify our own clock as of the offset value
       TKRClock.modifySimTime(modifier);
       

       }

       public long getSimTime(){
           //returns TKR simulation clock
           return TKRClock.getSimTime();
       }



}
