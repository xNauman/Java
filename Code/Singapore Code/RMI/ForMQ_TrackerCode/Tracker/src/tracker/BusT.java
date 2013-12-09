/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tracker;
//import holder.*;
import RMI.*;
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
public class BusT implements ActionListener{
   // private Image busImage;

    private static Vector xyValue = new Vector();//use for bus static movement to simulate data from Schedular
    private static Vector xyValue1 = new Vector();//use for bus static movement to simulate data from Schedular
    private static Vector xyValue2 = new Vector();//use for bus static movement to simulate data from Schedular
    private static Vector value;//use for increment
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int congestedSoln = 0;//use for congession
    private double gradient = 0;//use for gradient
    public static boolean move = true;
    private static Timer tm;
    private ImagePanel iP;
    //information getting from DB/RMI
    private static Vector busIDVect;
    //Movement Vector detail of Vector added by Scheduler.
    private static Vector moveVect;
    
    public BusT(ImagePanel iP){
        this.iP = iP;
        System.out.println("Bus Started");
        tm = new Timer(20, this);
        //the following 3 methods are dubugging only
        staticBusIDVect();
        getBusIDVect();
        staticBusLoc();

    }
    public static void startTimer(){
        tm.start();
    }
    public void updateBus(){   
        int ID=0,busID=0;
        int increment=0;
        int congested=0;
        
        for(int i=0;i<moveVect.size();i++){
            
            Vector movement = (Vector)moveVect.get(i);
            if(movement.size()!=0){
                //System.out.println("Size "+movement.size());
                int [] temp1 = (int[])movement.get(0);
                busID=temp1[0];
                Vector tempVect = iP.getXYVector();
                for(int j=0;j<tempVect.size();j++){
                    int temp[] = (int[])tempVect.get(j);
                    ID = temp[0];
                    if(ID == busID){
                        x1 = temp[1];
                        y1 = temp[2];
                        //System.out.println(x1+" "+y1);
                        break;
                    }

                }

                for(int j=0;j<value.size();j++){//get the increment value to get the movement vect
                    int temp[] = (int[])value.get(j);
                    ID = temp[0];
                    if(ID == busID){
                        increment = temp[1];
                        //System.out.println(ID+" "+increment);
                        break;
                    }
                }
                    //System.out.println(ID+" "+busID);
                    //if(value1[i]<movement.size()){
                if(increment<movement.size()){
                    //System.out.println(increment+" "+movement.size());
                    //int [] newXY = (int[])movement.get(value1[i]);
                    int [] newXY = (int[])movement.get(increment);
                    //int x1 = x1V[i];
                    congested = newXY[3];
                    if(congested==0 || congestedSoln==2){
                        if(congested==1)congestedSoln=0;

                        //System.out.println("BudID "+ID+" Point "+x1+" "+y1);
                        //System.out.println("ID"+newXY[]);
                        x2 = newXY[1];y2 = newXY[2];
                        //System.out.println(x2+" "+y2);
                        if(x2-x1!=0){
                            try{
                                gradient=(((double)y2-(double)y1)/((double)x2-(double)x1));
                                //System.out.println("gradient "+gradient);
                            }
                            catch(Exception e){
                                System.out.println("error");
                            }
                            //right down
                            if(gradient>0 && gradient<1 && ((x2-x1)>(y2-y1))){
                                if(x2-x1>0){
                                    x1+=1;//x1V[i]=x1;
                                    iP.setX1(x1,ID);
                                    //System.out.println("right down +X "+ID+" "+x1);
                                }
                            }
                            if(gradient>0 && gradient>1 && ((x2-x1)<(y2-y1))){
                                if(y2-y1>0){
                                    y1+=1;//y1V[i]=y1;
                                    iP.setY1(y1,ID);
                                    //System.out.println("right down +Y"+ID+" "+y1);
                                }
                            }
                            //right up
                            if(gradient<0 && gradient>-1 && ((x2-x1)>(-(y2-y1)))){
                                if(x2-x1>0){
                                    x1+=1;//x1V[i]=x1;
                                    iP.setX1(x1,ID);
                                    //System.out.println("right up +X"+ID+" "+x1);
                                }
                            }
                            if(gradient<0 && gradient<-1 && ((x2-x1)<(-(y2-y1)))){
                                if(y2-y1<0){
                                    y1-=1;//y1V[i]=y1;
                                    iP.setY1(y1,ID);
                                    //System.out.println("right up -Y"+ID+" "+x1);
                                }
                            }
                            //left down
                            if(gradient<0 && gradient>-1 && ((-(x2-x1))>(y2-y1))){
                                if(x2-x1<0){
                                    x1-=1;//x1V[i]=x1;
                                    iP.setX1(x1,ID);
                                    //System.out.println("left down -X"+ID+" "+x1);
                                }
                            }
                            if(gradient<0 && gradient<-1 && ((-(x2-x1))<(y2-y1))){
                                if(y2-y1>0){
                                    y1+=1;//y1V[i]=y1;
                                    iP.setY1(y1,ID);
                                    //System.out.println("left down +Y"+ID+" "+x1);
                                }
                            }
                            //left up
                            if(gradient>0 && gradient<1 && ((-(x2-x1))>(-(y2-y1)))){
                                if(x2-x1<0){
                                    x1-=1;//x1V[i]=x1;
                                    iP.setX1(x1,ID);
                                    //System.out.println("left up -X"+ID+" "+x1);
                                }
                            }
                            if(gradient>0 && gradient>1 && ((-(x2-x1))<(-(y2-y1)))){
                                if(y2-y1<0){
                                    y1-=1;//y1V[i]=y1;
                                    iP.setY1(y1,ID);
                                    //System.out.println("left up -Y"+ID+" "+x1);
                                }
                            }
                        }
                        if(x2-x1>0){x1+=1;iP.setX1(x1,ID);/*x1V[i]=x1;*/}
                        if(y2-y1>0){y1+=1;iP.setY1(y1,ID);/*y1V[i]=y1;*/}
                        if(x2-x1<0){x1-=1;iP.setX1(x1,ID);/*x1V[i]=x1;*/}
                        if(y2-y1<0){y1-=1;iP.setY1(y1,ID);/*y1V[i]=y1;*/}



                        if(x2==x1 && y2==y1){
                            //value1[i]++;
                            increment++;
                            for(int j=0;j<value.size();j++){
                                int temp[] = (int[])value.get(j);
                                ID = temp[0];
                                if(ID == busID){
                                    temp[1]=increment;
                                    value.set(j, temp);
                                    //System.out.println(ID+" "+increment);
                                    break;
                                }
                            }

                        } 
                    }
                    else{
                        congestedSoln++;
                    }
                }
            }//end movementsize
        }//end for
    }
    //scheduler movement
    public static void setMovementVector(Bus tempBus,boolean congestedBool){
        boolean exists = false;
        if(moveVect.size()==0){
            Vector temp = new Vector();
            int [] xy = new int[4];
            xy[0]=tempBus.getBusID();//bus ID
            xy[1]=tempBus.getMoveToPointX();//X coordinate
            xy[2]=tempBus.getMoveToPointY();//Y coordinate
            if(congestedBool==true){
                xy[3]=1;//true that it is congested
            }
            else{
                xy[3]=0;//false that it is NOT congested
            }
            temp.add(xy);
            moveVect.add(temp);
        }
        else{
            for(int i=0;i<moveVect.size();i++){
                Vector temp = (Vector)moveVect.get(i);
                int tempArr[]=((int[])temp.get(0));
                if(tempArr[0]==tempBus.getBusID()){
                    int [] xy = new int[4];
                    xy[0]=tempBus.getBusID();//bus ID
                    xy[1]=tempBus.getMoveToPointX();//X coordinate
                    xy[2]=tempBus.getMoveToPointY();//Y coordinate
                    if(congestedBool==true){
                        xy[3]=1;//true that it is congested
                    }
                    else{
                        xy[3]=0;//false that it is NOT congested
                    }
                    temp.add(xy);
                    exists = true;
                    break;
                }
            }
            if(exists == false){
                Vector temp = new Vector();
                int [] xy = new int[4];
                xy[0]=tempBus.getBusID();//bus ID
                xy[1]=tempBus.getMoveToPointX();//X coordinate
                xy[2]=tempBus.getMoveToPointY();//Y coordinate
                if(congestedBool==true){
                    xy[3]=1;//true that it is congested
                }
                else{
                    xy[3]=0;//false that it is NOT congested
                }
                temp.add(xy);
                moveVect.add(temp);
            }
        }
        exists = false; //clear teh status
    }
    /*
     *  @param Static bus location for debugging
     * */
    public void staticBusLoc(){
       moveVect = new Vector();
       int [] xy = new int[4];
//       xy[1]=170;xy[2]=410;xy[0]=11111;xy[3]=0;
//       xyValue.add(xy);

       xy = new int[4];
       xy[1]=200;xy[2]=420;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=350;xy[2]=550;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=405;xy[2]=585;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=460;xy[2]=600;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=560;xy[2]=600;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=630;xy[2]=580;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=700;xy[2]=520;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=850;xy[2]=435;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       xy = new int[4];
       xy[1]=850;xy[2]=420;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);

       //bus2
       xy = new int[4];
       xy[1]=0;xy[2]=100;xy[0]=22222;xy[3]=0;
       xyValue1.add(xy);
       xy = new int[4];
       xy[1]=0;xy[2]=200;xy[0]=22222;xy[3]=0;
       xyValue1.add(xy);
       xy = new int[4];
       xy[1]=0;xy[2]=300;xy[0]=22222;xy[3]=1;
       xyValue1.add(xy);
       xy = new int[4];
       xy[1]=0;xy[2]=400;xy[0]=22222;xy[3]=1;
       xyValue1.add(xy);
       xy = new int[4];
       xy[1]=0;xy[2]=500;xy[0]=22222;xy[3]=0;
       xyValue1.add(xy);
       xy = new int[4];
       xy[1]=0;xy[2]=600;xy[0]=22222;xy[3]=0;
       xyValue1.add(xy);
       //bus 3
       xy = new int[4];
       xy[1]=900;xy[2]=300;xy[0]=33333;xy[3]=0;
       xyValue2.add(xy);

       xy = new int[4];
       xy[1]=500;xy[2]=300;xy[0]=33333;xy[3]=0;
       xyValue2.add(xy);

       moveVect.add(xyValue);
       //moveVect.add(xyValue1);
       //moveVect.add(xyValue2);
    }
    //use for simulating that got the datafrom DB
    public void staticBusIDVect(){
        Vector v= new Vector();
        int [] busArray = new int[4];
        busArray[0]=11111;//bus id
        busArray[1]=170;//startx
        busArray[2]=410;//starty
        busArray[3]=1;//bendy
        v.add(busArray);

        busArray = new int[4];
        busArray[0]=22222;
        busArray[1]=0;
        busArray[2]=0;
        busArray[3]=1;//single
        //v.add(busArray);

        busArray = new int[4];
        busArray[0]=33333;
        busArray[1]=900;
        busArray[2]=600;
        busArray[3]=0;//single
        //v.add(busArray);

        busIDVect = v;
    }
    public void actionPerformed(ActionEvent e) {
        if(move){
            updateBus();
        }
        iP.repaint();
    }
    //getting the number of buses and the starting point of each bus
    public static void setBusVect(Vector vect){
        Vector temp = vect;
        busIDVect = new Vector();

        for(int i=0;i<temp.size();i++){
            rawobjects.Bus tempBus = (rawobjects.Bus)temp.get(i);
            int [] busArray = new int[4];
            busArray[0]=tempBus.getBusID();//bus id
            busArray[1]=tempBus.getCurrentPointX();//startx
            busArray[2]=tempBus.getCurrentPointY();//starty
            rawobjects.Bus_Type tempBusType = tempBus.getBusType();
            String busType = tempBusType.getBusType();
            if(busType.equals("single")){
                busArray[3]=0;//type
            }
            else{
              busArray[3]=1;//type
            }
            busIDVect.add(busArray);
        }


        value = new Vector();
        for(int i=0;i<busIDVect.size();i++){
            int [] valueArray = new int[2];
            valueArray[0]=((int[])busIDVect.get(i))[0];//ID of bus
            valueArray[1]=0;//default value 0;
            value.add(valueArray);
        }
    }
    public void getBusIDVect(){//debugging only
        value = new Vector();
        for(int i=0;i<busIDVect.size();i++){
            int [] valueArray = new int[2];
            valueArray[0]=((int[])busIDVect.get(i))[0];//ID of bus
            valueArray[1]=0;//default value 0;
            value.add(valueArray);
        }
    }
    public int[] getBus(int number){//if given is an object array got to convert to int arrray
        int [] busIDArray = (int[])busIDVect.get(number);
        return busIDArray;

    }
    public int getNumberOfBuses(){
        return busIDVect.size();
    }

    //for simulation
    public static void addNewVect2(){
       int [] xy = new int[4];
       xy[1]=0;xy[2]=200;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);
    }
    public static void addNewVect(){
       int [] xy = new int[4];
       xy[1]=850;xy[2]=200;xy[0]=11111;xy[3]=0;
       xyValue.add(xy);
    }
}