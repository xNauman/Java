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
import java.util.Scanner;
//import java.
/**
 *
 * @author Tracker Subsystem
 */
public class BusStopT {
    private static Repository repository;
    private ImagePanel iP;
    private Image busStopImage;
    private int phyX[];
    private int phyY[];
    private int logX[];
    private int logY[];
    private double angle[];
    private int passenger[];
    private Image tBusStop1;
    private Image tBusStop2;
    private Vector busStopVect;

    public BusStopT(ImagePanel iP,Repository repository){
        this.iP = iP;
        this.repository = repository;
        System.out.println("BusStop Started");
        //initialize this vector 1st just in case if DB give us nothing
        busStopVect = new Vector();

        //comment this during testing
        //staticBusPoints();
        //setBusStopValue();
        //rotationAngle();
        //setstaticBusStopPassenger();

    }

    public void staticBusIDVect(){
        Vector v= new Vector();
        int [] busArray = new int[2];
        busArray[0]=845;
        busArray[1]=580;
        v.add(busArray);
        busArray = new int[2];
        busArray[0]=450;
        busArray[1]=380;
        v.add(busArray);
        //busStopVect = v;
        busArray = new int[2];
        busArray[0]=660;
        busArray[1]=100;
        v.add(busArray);
        busStopVect = v;
    }

    public void staticBusPoints(){
        Vector v= new Vector();
        rawobjects.BusStop a=new rawobjects.BusStop();
        Point phy=new Point();
        phy.setLocation(100, 200);
        Point log=new Point();
        log.setLocation(115, 150);
        a.setPhysPoint(phy);
        a.setLogPoint(log);
        a.setPX_Count(10);
        v.add(a);

        a=new rawobjects.BusStop();
        phy=new Point();
        log=new Point();
        phy.setLocation(350, 565);
        log.setLocation(330, 545);
        a.setPhysPoint(phy);
        a.setLogPoint(log);
        a.setPX_Count(10);
        v.add(a);

        a=new rawobjects.BusStop();
        phy=new Point();
        log=new Point();
        phy.setLocation(700, 200);
        log.setLocation(850, 250);
        a.setPhysPoint(phy);
        a.setLogPoint(log);
        a.setPX_Count(10); 
        v.add(a);
        busStopVect=v;
    }

    public void setBusStopVector(Vector c){
        System.out.println("Initializing Bus stops location");
        busStopVect=c;        
       	setBusStopValue();
        rotationAngle();
        setBusStopPassenger();
        
    }


    public Vector getBusStopVect(){
        return busStopVect;
    }

    public int[] getPhyX(){
        return phyX;
    }

    public int[] getPhyY(){
        return phyY;
    }

    public double[] getAngle(){
        return angle;
    }

    public void setBusStopValue(){
        //Vector bsCoord = (Vector)busStop.getBSCoord();
        int size=busStopVect.size();
        phyX=new int[size];
        phyY=new int[size];
        logX=new int[size];
        logY=new int[size];
        for (int i=0;i<busStopVect.size();i++){
            rawobjects.BusStop bus=(rawobjects.BusStop)busStopVect.get(i);
//            for (int j=0;j<busStopArray.length;j++){
//                System.out.println(busStopArray[0]);
//            }


            phyX[i] = (int)bus.getPhysPoint().getX(); //- ((busImage.getWidth(this)/2));
            phyY[i] = (int)bus.getPhysPoint().getY(); //- ((busImage.getHeight(this)/2));
            logX[i] = (int)bus.getLogPoint().getX();
            logY[i] = (int)bus.getLogPoint().getY();


        }
    }

    public void rotationAngle(){
        angle=new double[busStopVect.size()];
        double degree=0;
        double opposite;
        double adjacent;
        double rad=0;
        for (int i=0;i<phyX.length;i++){
            if (phyX[i]==logX[i] && phyY[i]>logY[i]){
                degree=Math.toRadians(0);
                rad=Math.toRadians(degree);
            }else if (phyX[i]<logX[i] && phyY[i]>logY[i]){
                opposite=logX[i]-phyX[i];
                adjacent=phyY[i]-logY[i];
                degree=Math.toRadians(0)+Math.atan2(opposite, adjacent);
                rad=Math.toRadians(degree);
            }else if(phyY[i]==logY[i] && phyX[i]<logX[i]){
                degree=Math.toRadians(90);
                rad=Math.toRadians(degree);
            }else if (phyX[i]<logX[i] && phyY[i]<logY[i]){
                opposite=logY[i]-phyY[i];
                adjacent=logX[i]-phyX[i];
                degree=Math.toRadians(90)+Math.atan2(opposite, adjacent);
                rad=Math.toRadians(degree);
            }else if(phyX[i]==logX[i] && phyY[i]<logY[i]){
                degree=Math.toRadians(180);
                rad=Math.toRadians(degree);
            }else if (phyX[i]>logX[i] && phyY[i]<logY[i]){
                opposite=phyX[i]-logX[i];
                adjacent=logY[i]-phyY[i];
                degree=Math.toRadians(180)+Math.atan2(opposite, adjacent);
                rad=Math.toRadians(degree);
            }else if(phyY[i]==logY[i] && phyX[i]>logX[i]){
                degree=Math.toRadians(270);
                rad=Math.toRadians(degree);
            }
            else if (phyX[i]>logX[i] && phyY[i]>logY[i]){
                opposite=phyY[i]-logY[i];
                adjacent=phyX[i]-logX[i];
                degree=Math.toRadians(270)+Math.atan2(opposite, adjacent);
                rad=Math.toRadians(degree);
            }
            angle[i]=rad;
        }

    }

    public int[] getPassenger(){
        return passenger;
    }

    public void setBusStopPassenger(){
        passenger=new int[busStopVect.size()];
        for (int i=0;i<busStopVect.size();i++){
            rawobjects.BusStop bus=(rawobjects.BusStop)busStopVect.get(i);
            //System.out.println("++++++++++++++++++++++++++++++++++++++++++Bus Stop ID from DB "+bus.getPX_Count());
            passenger[i]=(int)bus.getPX_Count();

            //passenger[i]= Passenger.getBusStopPassenger(i).getNumber();
        }
    }
    public void updateBusStopPassenger(BusStop busstop){

        passenger=new int[busStopVect.size()];
        for (int i=0;i<busStopVect.size();i++){
            rawobjects.BusStop bus=(rawobjects.BusStop)busStopVect.get(i);
            if(busstop.getBusStopID()==bus.getBusStopID()){
                passenger[i]=(int)busstop.getPX_Count();
            }
            else{
                passenger[i]=(int)bus.getPX_Count();
            }
            //passenger[i]= Passenger.getBusStopPassenger(i).getNumber();
        }
    }
    public void setstaticBusStopPassenger(){
        passenger=new int[busStopVect.size()];
        for (int i=0;i<busStopVect.size();i++){
            rawobjects.BusStop bus=(rawobjects.BusStop)busStopVect.get(i);
             passenger[i]=(int)bus.getPX_Count();
            //passenger[i]= Passenger.getBusStopPassenger(i).getNumber();
        }
    }

//    public void Backup(){
//        Repository a=new Respository();
//        a.setBusStopVector(phyX,phyY,angle);
//    }

//    public Vector getBSStopFile(){
//
//        Vector v= new Vector();
//        boolean idChecker=false;
//        boolean xChecker=false;
//        boolean yChecker=false;
//        boolean idTempChecker=false;
//        boolean xTempChecker=false;
//        boolean yTempChecker=false;
//        boolean flag=false;
//
//        File busStopFile=new File("busStop");
//        try{
//            Scanner insideFile=new Scanner(busStopFile);
//            do{
//                String data;
//                int id=0;
//                int x=0;
//                int y=0;
//
//                if (insideFile.hasNextInt()){
//
//                    if (idTempChecker==true && xTempChecker==true && yTempChecker==true){
//                        flag=true;
//                    }
//
//                    if (idChecker==true){
//                        data=insideFile.next();
//                        id = Integer.parseInt(data);
//                        idTempChecker=true;
//                    }else if (xChecker==true){
//                        data=insideFile.next();
//                        x = Integer.parseInt(data);
//                        xTempChecker=true;
//                    }else if (yChecker==true){
//                        data=insideFile.next();
//                        y = Integer.parseInt(data);
//                        yTempChecker=true;
//                    }else{
//                        if (flag=true){
//                            int [] busArray = new int[3];
//                            busArray[0] = id;
//                            busArray[1] = x;
//                            busArray[2] = y;
//                            v.add(busArray);
//                            flag=false;
//                        }
//                    }
//                }else{
//                    data=insideFile.next();
//
//                    //Checks for appears of "id"
//                    if (data.equalsIgnoreCase("ID")){
//                        idChecker = true;
//                    }
//                    //Checks for appears of "x"
//                    if (data.contains("X")){
//                        xChecker = true;
//                    }
//                    //Checks for appears of "y"
//                    if (data.equalsIgnoreCase("Y")){
//                        yChecker = true;
//                    }
//                }
//        }while(insideFile.hasNext());
//            if(!insideFile.hasNext()) insideFile.close();
//        }
//        catch(IOException ex){
//            System.out.println("File not found\n");
//        }
//
//        busStopVect = v;
//        return busStopVect;
//    }

//    public void getStopCoord(){
//        //int j=0;
//        for (int i=0;i<busStopVect.size();i++){
//            int [] busIDArray = (int[])busStopVect.get(i);
//            x1V[i]=busIDArray[1];
//            y1V[i]=busIDArray[2];
//            System.out.println("Start "+x1V[i]+" "+y1V[i]);
//            //System.out.println("HERE"+busIDArray.length);
//        }
//    }



}
