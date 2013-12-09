/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tracker;
import rawobjects.*;
import java.awt.image.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.util.*;
/**
 *
 * @author Tracker Subsystem
 */
public class Repository {
    private Frame frame;
    private int Map_ID;
    private int [] temp ;
    private String drive = "D:\\Tracker\\";
    private String drive1 = "D:/Tracker/";
    
    public Repository(){
    }
    
    public Repository(Frame frame){
        this.frame = frame;
    }
    
    //create directory of the repository is going to reside on
    public void readMap_ID(int Map_No) {
       // Create a directory; all ancestor directories must exist
        Map_ID = Map_No;
        boolean success = (new File(drive+"Repository")).mkdir();
        if (!success) {
            // Directory creation failed
        }
        success = (new File(drive+"Repository\\Screen Shot " + Map_No)).mkdir();
        if (!success) {
            // Directory creation failed
        }
        success = (new File(drive+"Repository\\Map " + Map_No)).mkdir();
        if (!success) {
            // Directory creation failed
        }
    }// end of readMap_ID

    //read id,x,y of bendy and single deck buses
     public void readBusVector(Vector busVector) {

        for(int i=0;i<busVector.size();i++){
            int [] temp = (int[])busVector.get(i);
        
            int id = temp[0];
            int x = temp[1];
            int y = temp[2];
            int type = temp[3];

            writeBus(id,x,y,type);
            }

    }// end of readBusVector

    // read id,x,y of bus stop
    public void readBusStopVector(int[] x, int[] y, double[] angle) {
        int a = x.length;
        for(int i=0;i<a;i++){
            int temp1 = x[i];
            int temp2 = y[i];
            double temp3 = angle[i];
            writeBusStop(temp1,temp2,temp3);
        }

    }// end of readBusStopVector

    // read id,x,y of buildings
    public void readBuildingVector(Vector buildingVector) {

        for(int i=0;i<buildingVector.size();i++){
            Building b = (Building) buildingVector.get(i);
            int Bid = b.getBuildingID();
            int Bw = ((int)b.getWidth());//the width of the building
            int Bh = ((int)b.getHeight());//the height of the building
            int Bx = ((int)b.getX());//the x of the building
            int By = ((int)b.getY());//the y of the building
//            int Bx = (int) b.getX();
//            int By = (int) b.getY();
//            int Bw = (int) b.getWidth();
//            int Bh = (int) b.getHeight();
            writeBuilding(Bid,Bx,By,Bw,Bh);
        }
                
    }// end of readBuildingVector

    //read id,x,y of trafficlight
    public void readTrafficLightVector(Vector trafficLightVector) {

        for(int i=0;i<trafficLightVector.size();i++){
            TrafficLight TL = (TrafficLight)trafficLightVector.get(i);
            int Tid = TL.getTrafficLightId();
            int Tx = TL.getTrafficLightLocationX();
            int Ty = TL.getTrafficLightLocationY();
            int Tcd = TL.getCurrDirection();
            writeTrafficLight(Tid,Tx,Ty,Tcd);
        }
    } // end of readTrafficLightVector

    //read id,x,y of trafficlight
    public void readTrafficLightAngleVector(Vector trafficLightAngleVector) {

        for(int i=0;i<trafficLightAngleVector.size();i++){
            Traffic_Light_Angle tla = (Traffic_Light_Angle)trafficLightAngleVector.get(i);
            int tlaAngleID = tla.getAngleID();
            int tlaTLID = tla.getTrafficLightID();
            int tlaDirection = tla.getDirection();
            int tlaAngle = (int)tla.getAngle();
            writeTrafficLightAngle(tlaAngleID,tlaTLID,tlaDirection,tlaAngle);
        }
    }// end of readTrafficLightAngleVector
   
     // read id,x,y of depot
     public void readDepotVector(Vector depotVector) {
         
        for(int i=0;i<depotVector.size();i++){
            Depot d = (Depot)depotVector.get(i);
            Point PhyXY = d.getPhysPoint();
            int Px = (int)PhyXY.getX();
            int Py = (int)PhyXY.getY();
            Point LogXY = d.getLogPoint();
            int Lx = (int)LogXY.getX();
            int Ly = (int)LogXY.getY();
            writeDepot(Px,Py,Lx,Ly);
        }
    }// end of readDepotVector

      public void writeBus(int id , int x, int y, int type){
      try{
           if(type == 0){
                PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/BendyBus.txt");
                writeIn.append(id+" "+x+" "+y);
               
                writeIn.flush(); writeIn.close();
      }
           else{
                PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/SingleDeckBus.txt");
                writeIn.append(id+" "+x+" "+y);
                writeIn.flush(); writeIn.close();
           }
      }
      catch (IOException ex){
             System.err.println(ex);
        }
      
      } //end of writeBus

     public void writeBusStop(int x , int y, double Angle){
        try {
            PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/Bus_Stop.txt");
            writeIn.println(x+" "+y+" "+Angle);
        } 
        catch (IOException ex){
             System.err.println(ex);
        }
     }// end of writeBusStop

     public void writeBuilding(int id, int x, int y , int width , int height){
        try {
            PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/Building.txt");
            writeIn.println(id+" "+x+" "+y+" "+width+" "+height);
        }
        catch (IOException ex){
             System.err.println(ex);
        }
     }// end of writeBuilding

     public void writeTrafficLight(int id , int x, int y , int direction){
        try{
            PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/Trafficlight.txt");
                writeIn.println(id+" "+x+" "+y+" "+direction);
           }
        catch(IOException ex){
             System.err.println(ex);
        }
     }//end of writeTrafficLight
     
     public void writeTrafficLightAngle(int angleID , int trafficLightID , int direction, int angle){
        try{
            PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/TrafficlightAngle.txt");
                writeIn.println(angleID+" "+trafficLightID+" "+direction+" "+angle);
           }
        catch(IOException ex){
             System.err.println(ex);
        }
     }//end of writeTrafficLightAngle

     public void writeDepot(int Px, int Py, int Lx, int Ly){
        try{
            PrintWriter writeIn = new PrintWriter(drive1+"Repository/Map " + Map_ID + "/Depot.txt");
                writeIn.println(Px+" "+Py+" "+Lx+" "+Ly);
           }
        catch(IOException ex){
             System.err.println(ex);
        }
     }//end of writeDepot
    public static void writeVector(Vector c, String filename)
    {
        try {
            File data = new File(filename);
            ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(data));
            out.writeObject(c);
            out.close();
            } catch (Exception e) {
        }
    }

    public static Vector readVector(String filename) {
        try {
            ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(new File(filename)));
            Vector c = (Vector) in.readObject();
            in.close();
            return c;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static rawobjects.Map readMapObj(String filename) {
        try {
            ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(new File(filename)));
            rawobjects.Map c = (rawobjects.Map) in.readObject();
            in.close();
            return c;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static void writeMapObj(rawobjects.Map map)
    {
        try {
            File data = new File("D:/Tracker/mapObject.tkr");
            ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(data));
            out.writeObject(map);
            out.close();
            System.out.println("Map Data saved");
            } catch (Exception e) {
                e.printStackTrace();
        }
    }
}


