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
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.Vector;
import java.awt.geom.AffineTransform;
/**
 *
 * @author Tracker Subsystem
 */
public class ImagePanel extends JPanel{
    private Image img;
    private Image singleImage;
    private Image bendyImage;
    private Image tempImage;
    private Image busStopImage;
    public static boolean move = false;
    public BusT bus;
    public BuildingT building;
    public TrafficLightT traffic;
    public BusStopT busStop;
    public Repository repository;

    private Vector xy;
    private int [] stopx;
    private int [] stopy;
    private double [] stopangle;
    private AffineTransform transform = new AffineTransform();
    private RMITrackerClient client;

    private int [] trafficLightID;
    private int [] trafficLightID_angle;
    private int [] xTraffic;
    private int [] yTraffic;
    private int [] angelID;
    private int [] direction;
    private double [] angle;
    private int [] light;

    public ImagePanel(RMITrackerClient client) {
        this.client = client;
        initializeTracker();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        //drawing of the bus stop
        drawBusStop(g);
        //drawing of the bus
        drawTrafficLight(g);
        drawBus(g);
        //drawing of the building
        building.draw(g);
    }
    public void setX1(int x1,int busID){
        for(int i=0;i<xy.size();i++){
            int [] busXY = (int[])xy.get(i);
            if(busID == busXY[0]){
                busXY[1] = x1;
                xy.set(i, busXY);
                break;
            }
        }
    }
    public void setY1(int y1,int busID){
        for(int i=0;i<xy.size();i++){
            int [] busXY = (int[])xy.get(i);
            if(busID == busXY[0]){
                busXY[2] = y1;
                xy.set(i, busXY);
                break;
            }
        }
    }
    public void setColor(){
        //tL = 1;
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
            if(busXY[3]==0){
                tempImage = singleImage;
            }
            else{
                tempImage = bendyImage;
            }
            g.drawImage(tempImage, busXY[1], busXY[2], null);
        }
    }

    public Vector getXYVector(){
        return xy;
    }
   public void setBusStopValue(){
        stopx=busStop.getPhyX();
        stopy=busStop.getPhyY();
        stopangle=busStop.getAngle();
    }

    public void drawBusStop(Graphics g){
        for (int i=0;i<stopx.length;i++){
            Graphics2D g2d = (Graphics2D)g;
            AffineTransform trans = new AffineTransform();
            int xRot = busStopImage.getWidth(this)/2;
            int yRot = busStopImage.getHeight(this)/2;
            g2d.rotate( stopangle[i],stopx[i]+xRot,stopy[i]+yRot);

            g2d.drawImage(busStopImage, stopx[i], stopy[i], this);
            g2d.setTransform(trans);
        }
    }
    public void drawTrafficLight(Graphics g){
        for (int i=0;i<traffic.getNumberOfTL();i++){
            int [] trafficArray = traffic.getTL(i);
            trafficLightID[i] = trafficArray[0];
            xTraffic[i] = trafficArray[1];
            yTraffic[i] = trafficArray[2];

            for (int a=0;a<traffic.getNumberOfAngle();a++){
                double [] angleArray = traffic.getAngle(a);
                trafficLightID_angle[a] = (int)angleArray[0];

                if(trafficLightID_angle[a] == trafficLightID[i]){ //if same ID
                    angelID[i] = (int)angleArray[1];
                    direction[a] = (int)angleArray[2];
                    angle[a] = angleArray[3];
                    light[a] = (int)angleArray[4];

                    double xp = ((15*Math.cos(angle[a])) + xTraffic[i]);
                    int newX = (int)Math.rint(xp);
                    //System.out.println("NEW XXXXXXxx"+newX);
                    double yp = ((15*Math.sin(angle[a])) + yTraffic[i]);
                    int newY = (int)Math.rint(yp);
                    //System.out.println("NEW YYYYYYY"+newY);

                    if(light[a] == 0){
                        g.setColor(Color.GREEN);
                        g.fillRect(newX,newY,10,10);
                    }
                    else{
                        g.setColor(Color.RED);
                        g.fillRect(newX,newY,10,10);
                    }
                }
            }
        }
    }
    /*
     * capture screen
     */
    public static void screenCapture(){
        JFrame frame = Tracker.getFrame();
        try {
            Robot robot = new Robot();

            //
            // Capture screen from the top left in 200 by 200 pixel size.
            //
            BufferedImage bufferedImage = robot.createScreenCapture(
            new Rectangle( frame.getX()+5, frame.getY()+25,
            frame.getWidth()-8, frame.getHeight()-30));

            //
            // The captured image will the writen into a file called
            // screenshot.png
            //
            File imageFile = new File("screenshot.png");
            ImageIO.write(bufferedImage, "png", imageFile);
        } catch (AWTException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
    /*
     * uncomment the bus etc
     * and comment the static vectors in each class
     */
    public boolean initializeTracker(){
        img = new ImageIcon("C:/Tracker/images/bg1.jpg").getImage();
        //java.net.URL imageURL = tracker.Tracker.class.getResource("images/icon_bus.jpg");
        singleImage = new ImageIcon("C:/Tracker/images/single.png").getImage();
        bendyImage = new ImageIcon("C:/Tracker/images/bendy.png").getImage();
        //busImage = new ImageIcon(imageURL).getImage();
        busStopImage = new ImageIcon("C:/Tracker/images/busstop.jpg").getImage();

        bus = new BusT(this);
        //bus.setBusVect(client.getBusVector());
        building = new BuildingT(this);
        //building.setBuildingVect(client.getBuildingsVector());
        traffic = new TrafficLightT(this);
        //traffic.setTrafficVect(client.getTrafficLightsVector());
        busStop = new BusStopT(this);
        //busStop.setBusStopVector(client.getBusStopVector());
        repository = new Repository(this);
        //rmi = new RMI(this);

        //use to set the X and Y for updating the location
        xy = new Vector();

        setBusStartValue();
        stopx = new int[busStop.getBusStopVect().size()];
        stopy = new int[busStop.getBusStopVect().size()];
        setBusStopValue();

        xTraffic = new int[traffic.getNumberOfTL()];
        yTraffic = new int[traffic.getNumberOfTL()];
        trafficLightID = new int[traffic.getNumberOfTL()];
        trafficLightID_angle = new int[traffic.getNumberOfAngle()];
        angelID = new int[traffic.getNumberOfAngle()];
        direction = new int[traffic.getNumberOfAngle()];
        angle = new double[traffic.getNumberOfAngle()];
        light = new int[traffic.getNumberOfAngle()];

        //start the movement
        BusT.startTimer();
        System.out.println("System Fully Initialized");
        
        return true;
    }
}
