/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RMI;

/**
 *
 * @author David
 */
import tracker.*;
import java.awt.*;
import java.awt.image.*;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.util.Calendar;
import java.util.Vector;


import clock.*;
import rawobjects.*;
import rmi_interfaces.*;
import cc.registry.RegistryClient;

public class RMITrackerClient implements Runnable{

    private RegistryClient multicastClient;
    private String ccIP = null;
    private String dbIP = null;
    private Registry ccReg, dbReg;
    private int serverPort = 4445;
    CCServer CC;
    DBServer DB;
    private Clock TKRClock;
    private RMITrackerServer server; //for testing only
    private Repository repository;
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

    public RMITrackerClient(RegistryClient multicastClient,Repository repository){
        this.multicastClient = multicastClient;
        this.repository = repository;
        //Starting our Simulation Clock
         Calendar cal = Calendar.getInstance();
        TKRClock = new Clock(cal,0,0,0);
        TKRClock.StartClock();
    }

    public RMITrackerClient(){
        ccIP = "155.69.147.183";
        connectToCC();
        System.out.println("getting map data from DB!!");
        getMapData();
    }

    public void run(){
        getCCIP();
        connectToCC();
    //    server.startupTKR();
//        if(!server.startupTKR()){
//            server.startupTKR();
//            sleep("sleeping in forced run in Client");
//            System.out.println("Attempting to startup");
//        }
    }

    //fast testing

    public void setServeR(RMITrackerServer server){ //for testing only( will be removed)
        this.server = server;
    }
    //end of fast test

    private void getCCIP(){
        try{
            ccIP = multicastClient.getServerIP();
            System.out.println("ccIP is "+ccIP);
        }catch(NullPointerException e){
           //  e.printStackTrace();
            sleep("sleeping after failure to get CC");
            getCCIP();
        //    System.out.println("CC IP not found; call clement!");
        }
    }
    public void connectToCC(){
        try{
            System.out.println("attempting to connect to CC");
            System.out.println("connecting to "+ccIP+":"+serverPort);
            ccReg = LocateRegistry.getRegistry(ccIP,serverPort); //retrieve registry from CC
            CC = (CCServer)(ccReg.lookup("CC"));
            System.out.println("CC connection established");
        }catch(Exception e){
           e.printStackTrace();
           System.out.println("Cant connect to CC");
            sleep("sleeping after failure to connect to CC");
            run();
        }
    }

    private void sleep(String msg){
        try{
            Thread.sleep(2000);
             System.out.println(msg);
            }catch(InterruptedException e){}
    }

    public boolean getDBIP(){
        try{
            
            System.out.println("Attempting to get DB's ip address");
            dbIP = CC.getIP("DB");
            System.out.println("DB IP is "+dbIP);
            sleep("");
            if(dbIP!=null) return true;
            else getDBIP();
        
        }
        catch(RemoteException e){
            System.err.println("Fail to get DB IP");
            getDBIP();
        }
        return false;
    }

    private void reconnect(){
        run();
        connectToDB();
    }

    public void connectToDB(){
        try{
            getDBIP();
            System.out.println("Attempting to connect to DB");
            dbReg = LocateRegistry.getRegistry(dbIP,serverPort);
            DB = (DBServer)(dbReg.lookup("DB"));
            System.out.println("DB connection established");
        }
        catch(Exception e){
            sleep("Sleeping due to fail to connect to DB");
            reconnect();
  
        }
           //public String getIP(String ID) throws RemoteException;
    }

    public void updateStatus(int state){
        try{
            CC.updateStatus("TKR", state);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

  

    public boolean getMapData(){
        try{
            //get Map object from DB

            System.out.println("Attempting to get Map Data from DB");
            map = DB.getSelectedObj();
            if(map == null) return false;

            repository.writeMapObj(map);
            repository.readMap_ID(map.getMapID());
            mapPic = map.getMapInBufferedImage();
            if(!ImageIO.write(mapPic, "jpg", new File("D:/Tracker/images/map.jpg"))) return false;
            busStop = map.busStops;
            System.out.println("There are "+busStop.size()+" bus stops");
            //if(busStop.size() ==0) return false;
            routes = map.routes;
            System.out.println("There are "+routes.size()+"  routes");
          //  if(routes.size() ==0) return false;
            trafficLights = map.trafficLights;
            System.out.println("There are "+trafficLights.size()+"  traffic lights");
         //   if(trafficLights.size() ==0) return false;
//            junctions = map.junctions;
//            System.out.println("There are "+junctions.size()+"  junctions");
          //  if(depots.size() ==0) return false;
            buildings = map.buildings;
            System.out.println("There are "+buildings.size()+"  buildings");
         //   if(buildings.size() ==0) return false;
            congestion = map.congestion;
            System.out.println("There are "+congestion.size()+"  congestion");
//            if(congestion.size() ==0) return false;

//            if(junctions.size() ==0) return false;
//            roadArea = map.roadArea;
//            if(roadArea.size() ==0) return false;
            depots = map.depots;
            System.out.println("There are "+depots.size()+" depots");

            //get Bus object from DB
            busVector = DB.getSelectedMapBusDetails();
            repository.writeVector(busVector,"D:/Tracker/BusVector.TKR");
            System.out.println("There are "+busVector.size()+" buses");
            //if(busVector.size() ==0) return false;
            sleep("exiting end of getMapData()");
        }catch(Exception e){
            e.printStackTrace();
            reconnect();
            getMapData();
            return false;
        }
        
        return true;
    }

    public boolean getOfflineMapData(Map map){
           System.out.println("Using RMI Client's getOfflineMapData()");
        this.map = map;
           busStop = map.busStops;
            System.out.println("There are "+busStop.size()+" bus stops");
            routes = map.routes;
            System.out.println("There are "+routes.size()+"  routes");
            trafficLights = map.trafficLights;
            System.out.println("There are "+trafficLights.size()+"  traffic lights");

            buildings = map.buildings;
            System.out.println("There are "+buildings.size()+"  buildings");
            congestion = map.congestion;
            System.out.println("There are "+congestion.size()+"  congestion");
            depots = map.depots;
            System.out.println("There are "+depots.size()+" depots");
            busVector = repository.readVector("D:/Tracker/BusVector.TKR");
            System.out.println("There are "+busVector.size()+" buses");
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

//        public static void writeMapObj(Map map)
//        {
//            try {
//                File data = new File("D:/Tracker/mapObject.tkr");
//                ObjectOutputStream out = new ObjectOutputStream(
//                new FileOutputStream(data));
//                out.writeObject(map);
//                out.close();
//                System.out.println("Map Data saved");
//                } catch (Exception e) {
//                    e.printStackTrace();
//            }
//        }



}
