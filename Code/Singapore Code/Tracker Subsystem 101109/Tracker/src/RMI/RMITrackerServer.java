/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LOHS0019
 */
//import java.rmi.*;

package RMI;

import tracker.*;

import rmi_interfaces.*;
import java.awt.*;
import java.io.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.Naming.*;
import java.net.UnknownHostException;
import java.net.InetAddress;
import javax.imageio.*;
import java.net.*;
import java.awt.image.BufferedImage;
import rawobjects.*;
import tracker.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;

public class RMITrackerServer extends UnicastRemoteObject implements TKRServer{

    private RMITrackerClient RMIClient;
    private ImagePanel trackerPanel;
    private Repository repository;
    private int rmiPort = 4445; //RMI Port
    String thisAddress;
    private Registry registry; //
    private int opState;
    /*
* Operational States:
*     0: Not Operational
*     1: Pre-Start Completed
*     2: Ready to Start
*     3: Started, and Operational
*/

    public RMITrackerServer(RMITrackerClient RMIClient,ImagePanel trackerPanel,Repository repository) throws RemoteException {  //constructor
           this.trackerPanel = trackerPanel;
           this.RMIClient = RMIClient;
           this.repository = repository;
         try{
            //RMI
            thisAddress = (InetAddress.getLocalHost()).toString();
            //prints out the server's ip address
            System.out.println("Tracker RMI server address= "+ thisAddress + ":"+rmiPort);
            //register the trackerserver as remote object into the registry
            registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("TKR", this);

            opState = 0;
        }catch(UnknownHostException e){
            e.printStackTrace();
        } catch(RemoteException e){
             e.printStackTrace();
          //   run();
        }
      
    }


//
//   public static void main(String[] args){
//        try{
//
//            RMITrackerServer svr = new RMITrackerServer(null,null);
//           // new Thread(svr).start();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }

  
        

        //	 Returns the contents of the file in a byte array.
   
    
    public byte[] getImage(){
        byte[] imageData;
       // System.out.println("Getting Image");
        try{
             ImagePanel.hasStarted = true;
             if(ImagePanel.saveDone){
                File file = new File("screenshot.jpg");
             //   System.out.println(file.toString()+" 's size is "+file.length()/1024+"kb");
                imageData = getBytesFromFile(new File("screenshot.jpg"));
                ImagePanel.saveDone = false;
              //  System.out.println("picture Byte[] size is "+imageData.length);
                return imageData;
            }
           
             //Thread.sleep(200);
            // Send to client via RMI
             
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }


    public boolean moveBus(Bus reqBus, boolean isCongested){
        System.out.println("Scheduler has call moveBus");
        if(opState==2){
            opState = 3;
             RMIClient.updateStatus(opState);
             System.out.println("Entering state "+opState);
        }
        if(opState==3){
            System.out.println("Scheduler Move Bus "+reqBus.getBusID());
            trackerPanel.bus.setMovementVector(reqBus, isCongested);
            return true;
        }
        return false;
    }

    public boolean updateTL(TrafficLight reqTrafficLight){
        System.out.println("Scheduler has call updateTL");
        if(opState==2) {
            opState = 3;
             RMIClient.updateStatus(opState);
             System.out.println("Entering state "+opState);
        }
        if(opState==3){
            System.out.println("Tracker enter updateTL main method");
            System.out.println("Scheduler update Traffic Light "+reqTrafficLight.getTrafficLightId());
            trackerPanel.traffic.updateTL(reqTrafficLight);
            return true;
        }
        return false;
    }

    public boolean updateStop(BusStop reqBusStop){
        System.out.println("Scheduler has call updateStop");
        if(opState==2) {
            opState = 3;
             RMIClient.updateStatus(opState);
             System.out.println("Entering state "+opState);
        }
        if(opState==3){
            System.out.println("Scheduler update Bus Stop passenger"+reqBusStop.getPX_Count());
            trackerPanel.busStop.updateBusStopPassenger(reqBusStop);
            return true;
        }
        return false;
    }

    public boolean startupTKR(){ // will go from from 0 ~ 1 immediately then go 2 PRE-START
        if(opState == 0||opState == 1){
            System.out.println("CC called for startup");
            opState = 1; //taking data
            RMIClient.updateStatus(opState); //update CC TKR is 1
            preStartInit();
            //RMIClient.getOfflineMapData(repository.readMapObj("D:/Tracker/mapObject.tkr"));
         //   if(!RMIClient.connectToDB()) return false;

                System.out.println("calling trackerPanel.initializeTracker()");
            if(trackerPanel.initializeTracker()){
                opState =2;
                RMIClient.updateStatus(opState);
                System.out.println("Tracker entering state "+opState);
                System.out.println("**************************************************");
                System.out.println("System Fully Initialized");
                System.out.println("**************************************************");
                return true;
            }
             //  if(!RMIClient.getMapData()) return false;
                else return false;

           // return true;
        }

     
        else return false;
        
    }

    private void preStartInit(){
        RMIClient.connectToDB();
        RMIClient.getMapData();
      //  return false;
    }


    public int tkrState(){
        return opState;
    }

    
    public boolean stopTKR(){

       // if(opState == 3 || opState == 2){
            System.out.println("stopTKR() called");
            opState = 0;
            RMIClient.updateStatus(opState); //inform CC TKR state = 0 now
            //set init done to false (will show logo)
            trackerPanel.toggleInitDone();
            
            return true;
       // }
        
    }



    public long SimTimeShare(){
        long SimTime;
          SimTime = RMIClient.getSimTime();
          return SimTime;
       }

    public String debugEcho(String pFromModule, String pMessageToEcho){
    
        String msg = "received from "+pFromModule+" with "+pMessageToEcho;
       //try{

        System.out.println(msg);
        //}catch(Exception e){}
      //  Thread.sleep(10000);
 
        return msg;
    }

     private static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            System.out.println("File Size too large");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
//    public static rawobjects.Map readMapObj(String filename) {
//        try {
//            ObjectInputStream in = new ObjectInputStream(
//            new FileInputStream(new File(filename)));
//            rawobjects.Map c = (rawobjects.Map) in.readObject();
//            in.close();
//            return c;
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return  null;
//    }
//
}

