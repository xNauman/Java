/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tracker;
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
import java.io.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.awt.geom.AffineTransform;
import java.io.Serializable;


/**
 *
 * @author Tracker Subsystem
 */
public class ImagePanel extends JPanel implements Runnable{
    private Image img;
    private Image RDSLogo;
    private Image singleImage;
    private Image bendyImage;
    private Image tempImage;
    private Image busStopImage;
    private Vector BufferImageVector;
//    private Vector xyValue = new Vector();
//    private int value = 0;
//    private int x11 = 170;
//    private int y11 = 410;
//    private int tL = 0;
//    private double gradient = 0;
    public static boolean move = false;
    public BusT bus;
    public BuildingT building;
    public TrafficLightT traffic;
    public BusStopT busStop;
    public Repository repository;
   // public RMI rmi;
    //private int [] x;
    private Vector xy;
    //private int [] y;
    private int [] stopx;
    private int [] stopy;
    private double [] stopangle;
    private int[] passenger;
    
    private AffineTransform transform = new AffineTransform();
    private RMITrackerClient client;

    private int [] trafficLightID;
    private int [] trafficLightID_angle;
    private int [] xTraffic;
    private int [] yTraffic;
    private int [] angelID;
    private int [] direction;
    private int [] currDirection;
    private int [] inDirection;
    private double [] angle;

    private int count =0;

    private boolean initializedDone = false;
    public static boolean saveDone = false;
    public static boolean hasStarted = false;
    private Robot robot;
    private int buffVectCounter = 0;
    private int altBuffVectCounter = 0;
    private int getBuffVectCounter = 0;
    public ImagePanel(RMITrackerClient client,Repository repository) {
        this.client = client;
        this.repository = repository;

        BufferImageVector = new Vector();
        RDSLogo = new ImageIcon("D:/Tracker/images/RDSLogo.png").getImage();
        //initializeTracker();
        //check RMI status
        //getStatusOfRMI();

    }



    public void paintComponent(Graphics g) {
        if(initializedDone){
            super.paintComponent(g);
            //drawing of the background
            g.drawImage(img, 0, 0, null);
            //drawing of the bus stop
            drawBusStop(g);
            //drawing of the traffic light
            drawTrafficLight(g);
            //drawing of the bus
            drawBus(g);
            //drawing of the building
            building.draw(g);
        }
        else{
            g.drawImage(RDSLogo,0,0,null);
        }

    }

    public void run(){
        try{
            if(hasStarted){
                if(!saveDone){
                   // System.out.println("I entered ImagePanel.run()");
                    BufferedImage bi = screenCapture();
                    int type = bi.getType() == 0? BufferedImage.TYPE_INT_ARGB : bi.getType();
                    BufferedImage resizeImageJpg = resizeImage(bi, type);
                    ImageIO.write(resizeImageJpg, "jpg", new File("screenshot.jpg"));
                    Thread.sleep(50);
                    saveDone = true; //inform RMI server, pic ready to send
                }
            }
            new Thread(this).start();
        }catch(Exception e){
            new Thread(this).start();
        }
    }


    public void setBusPassenger(int busID, int count){
        for(int i=0;i<xy.size();i++){
            int [] busXY = (int[])xy.get(i);
            if(busID == busXY[0]){
                busXY[6] = count;
                xy.set(i, busXY);
                break;
            }
        }
    }
    public void setX1(int x1,int busID,int xMinus,int yMinus){
        for(int i=0;i<xy.size();i++){
            int [] busXY = (int[])xy.get(i);
            if(busID == busXY[0]){
                busXY[1] = x1;
                busXY[4] = xMinus;
                busXY[5] = yMinus;
                xy.set(i, busXY);
                break;
            }
        }
    }
    public void setY1(int y1,int busID,int xMinus,int yMinus){
        for(int i=0;i<xy.size();i++){
            int [] busXY = (int[])xy.get(i);
            if(busID == busXY[0]){
                busXY[2] = y1;
                busXY[4] = xMinus;
                busXY[5] = yMinus;
                xy.set(i, busXY);
                break;
            }
        }
    }
    public void setBusStartValue(){
        for (int i=0;i<bus.getNumberOfBuses();i++){
            int [] busArray = bus.getBus(i);
            xy.add(busArray);
        }
    }
    public void drawBus(Graphics g){
        for (int i=0;i<xy.size();i++){
            int [] busXY = (int[])xy.get(i);//get the type and draw here
            if(busXY[3] == 1){
                tempImage = singleImage;
            }
            else{
                tempImage = bendyImage;
            }
            g.drawImage(tempImage, busXY[1]+busXY[4], busXY[2]+busXY[5], null);
            g.drawString(String.valueOf(busXY[6]),busXY[1]+busXY[4],busXY[2]+busXY[5]);
        }
    }

    public Vector getXYVector(){
        return xy;
    }
   public void setBusStopValue(){
        stopx=busStop.getPhyX();
        stopy=busStop.getPhyY();
        stopangle=busStop.getAngle();
//        Vector bsCoord = (Vector)busStop.getBSCoord();
//        angle=busStop.getAngle();
//        for (int i=0;i<bsCoord.size();i++){
//            int [] busStopArray = (int[])(bsCoord.get(i));
////            for (int j=0;j<busStopArray.length;j++){
////                System.out.println(busStopArray[0]);
////            }
//            stopx[i] = busStopArray[0] - ((busImage.getWidth(this)/2));
//            stopy[i] = busStopArray[1] - ((busImage.getHeight(this)/2));
//        }
    }

//    public void drawBusStop(Graphics g){
//        for (int i=0;i<stopx.length;i++){
//            System.out.println(stopx[i]);
//            System.out.println(stopy[i]);
//            System.out.println(stopangle[i]);
//            g.drawImage(busStopImage, stopx[i], stopy[i], null);
//        }
//    }

    public void drawBusStop(Graphics g){
        for (int i=0;i<stopx.length;i++){
            //transform.rotate(angle[i], busImage.getWidth()/2, busImage.getHeight()/2);
            //System.out.println(stopx[i]);
            //System.out.println(stopy[i]);
            //System.out.println(stopangle[i]);
            Graphics2D g2d = (Graphics2D)g;
            //AffineTransform origXform = g2d.getTransform();
            //AffineTransform newXform = (AffineTransform)(origXform.clone());
            AffineTransform trans = new AffineTransform();
            int xRot = busStopImage.getWidth(this)/2;
            int yRot = busStopImage.getHeight(this)/2;
            g2d.rotate( stopangle[i],stopx[i]-xRot,stopy[i]-yRot);
            //center of rotation is center of the panel


            g2d.drawImage(busStopImage, stopx[i]-xRot, stopy[i]-yRot, this);
            //g2d.drawImage(img1, x1, y1, null);
            g2d.setTransform(trans);
            //busStop.setBusStopPassenger();
            passenger=busStop.getPassenger();
            g2d.drawString(Integer.toString(passenger[i]), stopx[i], stopy[i]);
//            newXform.rotate(Math.toRadians(stopangle[i]), xRot, yRot);
//            g2d.setTransform(newXform);
//            //draw image centered in panel
//            int xest = (stopx[i] - busImage.getWidth(this))/2;
//            int yest = (stopy[i] - busImage.getHeight(this))/2;
//            g2d.drawImage(busImage, xest, yest, this);
//            g2d.setTransform(origXform);
//
//            newXform.setTransform(origXform);
//            //g.drawImage(busStopImage, stopx[i], stopy[i], null);
//
//            newXform.rotate( Math.toRadians(stopangle[i]) );
//            g2d.drawImage(busStopImage, newXform, this);
        }
    }
     public void drawTrafficLight(Graphics g){
         //System.out.println("No of TrafficLight .........................."+traffic.getNumberOfTL());
        for (int i=0;i<traffic.getNumberOfTL();i++){
            int [] trafficArray = traffic.getTL(i);
            trafficLightID[i] = trafficArray[0];
            xTraffic[i] = trafficArray[1];
            yTraffic[i] = trafficArray[2];
            currDirection[i] = trafficArray[3];

            for (int a=0;a<traffic.getNumberOfAngle();a++){
                double [] angleArray = traffic.getAngle(a);
                trafficLightID_angle[a] = (int)angleArray[0];
                //System.out.println("***************************TrafficLight ID "+trafficLightID_angle[a]+" "+trafficLightID[i]);
                if(trafficLightID_angle[a] == trafficLightID[i]){ //if same ID
                    angelID[i] = (int)angleArray[1];
                    direction[a] = (int)angleArray[2];
                    angle[a] = angleArray[3];
                    inDirection[a] = (int)angleArray[4];

                    double xp = ((15*Math.cos(angle[a])) + xTraffic[i]);
                    int newX = (int)Math.rint(xp);
                    double yp = ((15*Math.sin(angle[a])) + yTraffic[i]);
                    int newY = (int)Math.rint(yp);

                    Graphics2D g2d = (Graphics2D)g;
                    AffineTransform trans = new AffineTransform();

                    int xRot = ((newX-xTraffic[i])/2);
                    int yRot = ((newY-yTraffic[i])/2);
                    //g2d.rotate(angle[i],newX+xRot,newY+yRot);
                    //center of rotation is center of the panel

                    if( direction[a] == currDirection[i] || inDirection[a] == currDirection[i]){
                        g2d.setColor(Color.GREEN);
                        g2d.fillRect(newX,newY,10,10);

                    }
                    else {
                        g2d.setColor(Color.RED);
                        g2d.fillRect(newX,newY,10,10);
                    }
                     g2d.setTransform(trans);


                    //g2d.drawImage(busStopImage, stopx[i], stopy[i], this);
                    //g2d.drawImage(img1, x1, y1, null);

                }
            }
        }
    }
    /*
     * Capture screen shot for sending to CC
     *
     */
    public BufferedImage screenCapture(){
        JFrame frame = Tracker.getFrame();
        try {
            robot = new Robot();
            BufferedImage bufferedImage = robot.createScreenCapture(
            new Rectangle( frame.getX()+5, frame.getY()+30,
            frame.getWidth()-8, frame.getHeight()-35));
            return bufferedImage;
        } catch (Exception e){
        }
       return null;
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
	BufferedImage resizedImage = new BufferedImage(850, 650, type);
	Graphics2D g = resizedImage.createGraphics();
	g.drawImage(originalImage, 0, 0, 850, 650, null);
	g.dispose();

	return resizedImage;
    }
    /*
     * initialize the tracker
     */
    public boolean initializeTracker(){

        img = new ImageIcon("D:/Tracker/images/map.jpg").getImage();
        //java.net.URL imageURL = tracker.Tracker.class.getResource("images/icon_bus.jpg");
        singleImage = new ImageIcon("D:/Tracker/images/single.png").getImage();
        bendyImage = new ImageIcon("D:/Tracker/images/bendy.png").getImage();
        //busImage = new ImageIcon(imageURL).getImage();
        busStopImage = new ImageIcon("D:/Tracker/images/busstopImage.jpg").getImage();

        bus = new BusT(this,repository);
        bus.setBusVect(client.getBusVector(),client.getRoutesVector());
        building = new BuildingT(this,repository);
        building.setBuildingVect(client.getBuildingsVector(),client.getDepotsVector());
        traffic = new TrafficLightT(this,repository);
        traffic.setTLVector(client.getTrafficLightsVector());
        busStop = new BusStopT(this,repository);
        busStop.setBusStopVector(client.getBusStopVector());
        

        //use to set the X and Y for updating the location for bus
        xy = new Vector();
        setBusStartValue();

        //for busstop
        stopx = new int[busStop.getBusStopVect().size()];
        stopy = new int[busStop.getBusStopVect().size()];
        setBusStopValue();

        //for traffic light
        xTraffic = new int[traffic.getNumberOfTL()];
        yTraffic = new int[traffic.getNumberOfTL()];
        currDirection = new int[traffic.getNumberOfTL()];
        trafficLightID = new int[traffic.getNumberOfTL()];
        trafficLightID_angle = new int[traffic.getNumberOfAngle()];
        angelID = new int[traffic.getNumberOfAngle()];
        direction = new int[traffic.getNumberOfAngle()];
        inDirection = new int[traffic.getNumberOfAngle()];
        angle = new double[traffic.getNumberOfAngle()];

        initializedDone = true;
        //start the movement
        BusT.startTimer();
        return true;
    }
//    public void updateTL(){
//        Vector v = new Vector();
//        /*int mapID,int pTrafficLightId,int pTrafficLightLocationX,
//			int pTrafficLightLocationY,int pChangeFreq, boolean pIsJunction,
//			long pLastChange, int pCurrDirection, int pMaxDirection, Vector pangle*/
//        Vector temp = new Vector();
//        TrafficLight t = new TrafficLight(0,0,0,0,0,true,0,2,0,temp);
//        TrafficLightT.updateTL(t);
//    }

    public void toggleInitDone(){ //for stop state
        initializedDone = false;
    }
    public void addBufferImage(BufferedImage image){
        if(buffVectCounter<=50){
            BufferImageVector.add(new ImageIcon(image));
            buffVectCounter++;
        }
        else{
            BufferImageVector.set(altBuffVectCounter,new ImageIcon(image));
            altBuffVectCounter++;
            if(altBuffVectCounter==50){
                altBuffVectCounter=0;
            }
        }
    }
    public ImageIcon getBufferImage(){
        ImageIcon temp = null;
        if(BufferImageVector.size()-1>getBuffVectCounter){
            temp = (ImageIcon)BufferImageVector.get(getBuffVectCounter);
            getBuffVectCounter++;
            
        }
        if(getBuffVectCounter==50)getBuffVectCounter = 0;
        return temp;
    }

}
