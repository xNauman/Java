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

import rmi_interfaces.*;
import java.awt.*;
import java.io.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.Naming.*;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.*;

import rawobjects.*;
import tracker.*;


public class RMITrackerServer extends UnicastRemoteObject implements RMITrackerInterface{

    private RMITrackerClient RMIClient;
    private ImagePanel trackerPanel;
    private static Image img;
    private int rmiPort = 4444; //RMI Port
    String thisAddress;
    private Registry registry; //
    private boolean canSend = false;
    private int opState;
    private boolean dataReceived = false;
    String name = "screenshot";
    int no = 1;

    /*
* Operational States:
*     0: Not Operational
*     1: Pre-Start Completed
*     2: Ready to Start
*     3: Started, and Operational
*/

    public RMITrackerServer(RMITrackerClient RMIClient,ImagePanel trackerPanel) throws RemoteException {  //constructor
        try{
            this.trackerPanel = trackerPanel;
            this.RMIClient = RMIClient;
            //RMI
            thisAddress = (InetAddress.getLocalHost()).toString();
            //prints out the server's ip address
            System.out.println("This address= "+ thisAddress + ":"+rmiPort);
            //register the trackerserver as remote object into the registry
            registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("TKR", this);

            opState = 0;
        }catch(UnknownHostException e){
            e.printStackTrace();
            throw new RemoteException("cant get inet address");
        } catch(RemoteException e){
            throw e;
        }catch(IOException e){
            e.printStackTrace();
        }
    }


   public static void main(String[] args){
        try{

            RMITrackerServer svr = new RMITrackerServer(null,null);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
   
    
        

        //	 Returns the contents of the file in a byte array.
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
    
    public byte[] getImage(){
            byte[] imageData;
         //   String a = name+no+".jpg";
          //  System.out.println(a);
            File file = new File("screenshot.jpg");
            no ++;
            if(no == 5) no = 1;

            //over here, state the name of the picture that you wish to
            //send

            try{
                    //convert picture into byte array
                    imageData = getBytesFromFile(file);
                    // Send to client via RMI
                    return imageData;

            }catch(IOException ioe){
                   // Handle exception
                   ioe.printStackTrace();
                   return null; // or whatever you want..
            }
    }


    public boolean moveBus(Bus reqBus, boolean isCongested){
      /* System.out.println("I got reach here");

        Bus bus = reqBus;
        System.out.println("Bus ID: "+bus.getBusID());
        System.out.println("Current Loc: "+bus.getCurrentPointX()+","+bus.getCurrentPointY());
        System.out.println("Going to: "+bus.getMoveToPointX()+","+bus.getMoveToPointY());
        System.out.println("Is this a congested? "); //lack
        System.out.println("The default load is "+bus.getDefaultLoad());
        //System.out.println(reqBus);*/
        //tracker.BusT.setMovementVector(reqBus, isCongested);
        trackerPanel.bus.setMovementVector(reqBus, isCongested);

        return true;
    }

    public boolean updateTL(TrafficLight reqTrafficLight){
        return true;
    }

    public boolean updateStop(BusStop reqBusStop){
        return true;
    }

    public boolean startupTKR(){ // will go from from 0 ~ 1 immediately then go 2 PRE-START
        if(opState == 0){
            opState = 1; //taking data
            preStartInit();
         //   if(!RMIClient.connectToDB()) return false;
            if(trackerPanel.initializeTracker()){
                opState =2;
                return true;
            }
         //  if(!RMIClient.getMapData()) return false;
            else return false;

           // return true;
        }
        else return false;
        
    }

    private void preStartInit(){

        if(!RMIClient.connectToDB()) dataReceived = false;
        if(!RMIClient.getMapData()) dataReceived = false;

      //  return false;
    }


    public int tkrState(){
        return opState;
    }

    public void setOpState(int state){
        opState = state;
    }

    public boolean stopTKR(){
        if(opState == 3){
            opState = 0;
            return true;
        }
        return false;
    }


    public boolean isDataReady(){
        return dataReceived;
    }

    public long SimTimeShare(){
        long SimTime;
          SimTime = RMIClient.getSimTime();
          return SimTime;
       }

}

