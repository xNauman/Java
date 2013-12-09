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
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.Vector;
/**
 *
 * @author Tracker Subsystem
 */
public class TrafficLightT {
    private static ImagePanel iP;
    private Vector TrafficLightVect;
    private Vector TrafficAngleVect;
    private Vector TLVector;
    private Vector temp;
    public TrafficLightT(ImagePanel iP){
        this.iP = iP;

        staticTLPoints();
        staticAngleTLPoints();
    }

    public static void setLight(){
        iP.repaint();
    }

    public void setTLVector(Vector TLVect){
        TLVector = TLVect;
        temp = new Vector();
    }
    //-----dynamic code to obtain different angles of traffic light
    // Need to change angleArray2 to angleArray
    public void AngleTLPoints(){

        // = new Vector(); //given by David RMI.getTLAngleVect()
        //rawobjects.Traffic_Light_Angle angleVect = new rawobjects.Traffic_Light_Angle();
        //rawobjects.TrafficLight traffic = new rawobjects.TrafficLight();
        Vector temp1=new Vector();
        for(int i=0; i<TLVector.size(); i++){
            TrafficLight traffic = (TrafficLight)TLVector.get(i);
            Vector vAngle =new Vector();//traffic.getTLAngle();//remove later

            for (int j=0;j<vAngle.size();j++){
                Traffic_Light_Angle angleVect = (Traffic_Light_Angle)vAngle.get(j);

                double [] angleArray2 = new double[5];
                double degree = angleVect.getAngle(); //get angle from database
                double radian = Math.toRadians(degree); // convert degree to radian

                angleArray2[0]= angleVect.getTrafficLightID(); //TrafficLightID
                angleArray2[1]= angleVect.getAngleID(); //AngleID
                angleArray2[2]= angleVect.getDirection(); //Direction
                angleArray2[3]= radian; //Angle in radian
                //angleArray2[4]= traffic.; //Color of trafficLight
                temp1.add(angleArray2);
            }
        }
        TrafficAngleVect = temp1;
    }

    //-----static code for testing, storing of different angles
    public void staticAngleTLPoints(){
        Vector vAngle = new Vector(); //given by David RMI.getTLVect()
        rawobjects.Traffic_Light_Angle angleVect = new rawobjects.Traffic_Light_Angle();

        double a = Math.toRadians(0); //angleVect.getAngle();
        double b = Math.toRadians(90); //angleVect.getAngle();
        double c = Math.toRadians(180); //angleVect.getAngle();
        double d = Math.toRadians(270); //angleVect.getAngle();

        double [] angleArray = new double[5];
        angleArray[0] = 0; //traffic.getTrafficLightId();
        angleArray[1] = 0; //angleVect.getAngleID();
        angleArray[2] = 0; //angleVect.getDirection;
        angleArray[3] = a;
        angleArray[4] = 0; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        angleArray = new double[5];
        angleArray[0] = 0; //traffic.getTrafficLightId();
        angleArray[1] = 1; //traffic.getAngleID();
        angleArray[2] = 1; //getDirection;
        angleArray[3] = b;
        angleArray[4] = 1; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        angleArray = new double[5];
        angleArray[0] = 0; //traffic.getTrafficLightId();
        angleArray[1] = 2; //traffic.getAngleID();
        angleArray[2] = 2; //getDirection;
        angleArray[3] = c;
        angleArray[4] = 0; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        angleArray = new double[5];
        angleArray[0] = 0; //traffic.getTrafficLightId();
        angleArray[1] = 3; //traffic.getAngleID();
        angleArray[2] = 3; //getDirection;
        angleArray[3] = d;
        angleArray[4] = 1; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        double e = Math.toRadians(0); //angleVect.getAngle();
        double f = Math.toRadians(90); //angleVect.getAngle();
        double g = Math.toRadians(180); //angleVect.getAngle();
        double h = Math.toRadians(270); //angleVect.getAngle();

        angleArray = new double[5];
        angleArray[0] = 1; //traffic.getTrafficLightId();
        angleArray[1] = 4; //angleVect.getAngleID();
        angleArray[2] = 0; //angleVect.getDirection;
        angleArray[3] = e;
        angleArray[4] = 1; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        angleArray = new double[5];
        angleArray[0] = 1; //traffic.getTrafficLightId();
        angleArray[1] = 5; //traffic.getAngleID();
        angleArray[2] = 1; //getDirection;
        angleArray[3] = f;
        angleArray[4] = 0; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        angleArray = new double[5];
        angleArray[0] = 1; //traffic.getTrafficLightId();
        angleArray[1] = 6; //traffic.getAngleID();
        angleArray[2] = 2; //getDirection;
        angleArray[3] = g;
        angleArray[4] = 1; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;

        angleArray = new double[5];
        angleArray[0] = 1; //traffic.getTrafficLightId();
        angleArray[1] = 6; //traffic.getAngleID();
        angleArray[2] = 3; //getDirection;
        angleArray[3] = h;
        angleArray[4] = 0; //traffic.getTLColor();
        vAngle.add(angleArray);
        TrafficAngleVect = vAngle;
    }

    //-----dynamic code for Traffic Light. x,y
    //-----Need to change TrafficLightArray2 to TrafficLightArray
    public void TLPoints(){
        Vector vTL = TLVector;
        Vector temp2 = new Vector();

        for(int i=0; i<vTL.size(); i++){
            TrafficLight traffic = (TrafficLight)vTL.get(i);
            int [] TrafficLightArray2 = new int[3];
            TrafficLightArray2[0]= traffic.getTrafficLightId();
            TrafficLightArray2[1]= traffic.getTrafficLightLocationX();
            TrafficLightArray2[2]= traffic.getTrafficLightLocationY();
            temp2.add(TrafficLightArray2);

        }
        TrafficLightVect = temp2;
    }

    //-----static code for testing, storing of traffic light x,y
    public void staticTLPoints(){
        Vector v= new Vector();
        rawobjects.TrafficLight traffic = new rawobjects.TrafficLight();

        int [] TrafficLightArray = new int[3];
        TrafficLightArray[0]= 0;//traffic.getTrafficLightId();
        TrafficLightArray[1]= 90;//traffic.getTrafficLightLocationX();
        TrafficLightArray[2]= 395;//traffic.getTrafficLightLocationY();
        v.add(TrafficLightArray);
        TrafficLightVect = v;

//        TrafficLightArray = new int[3];
//        TrafficLightArray[0]= 1;//traffic.getTrafficLightId();
//        TrafficLightArray[1]= 300;//traffic.getTrafficLightLocationX();
//        TrafficLightArray[2]= 400;//traffic.getTrafficLightLocationY();
//        v.add(TrafficLightArray);
        TrafficLightVect = v;
    }

    public int[] getTL(int number){
        int [] TrafficLightArray = (int[])TrafficLightVect.get(number);
        return TrafficLightArray;
    }
    public int getNumberOfTL(){
        return TrafficLightVect.size();
    }

    public double[] getAngle(int number){
        double [] angleArray = (double[])TrafficAngleVect.get(number);
        return angleArray;
    }
    public int getNumberOfAngle(){
        return TrafficAngleVect.size();
    }
}
