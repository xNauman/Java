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
public class BuildingT {
    private ImagePanel iP;
    private static Repository repository;
    private static Vector buildingVector;
    private static Vector depotVector;
    private Image building_image;
    private Image depot_image;
    private Image resized_building;
    private int w;
    private int h;
    private int factor=3;
    int startX=0;
    int startY=0;
    int endX=0;
    int endY=0;

    public BuildingT(ImagePanel iP,Repository repository){
        this.iP = iP;
        this.repository = repository;
        System.out.println("Building Started");
        //initialize this vector 1st just in case if DB give us nothing
        buildingVector = new Vector();
        depotVector = new Vector();

        //comment this during testing
        //staticVectorEx();

        building_image = new ImageIcon("D:/Tracker/images/Building.png").getImage();
        depot_image = new ImageIcon("D:/Tracker/images/Depot.png").getImage();
        w=building_image.getWidth(this.iP);
        h=building_image.getHeight(this.iP);
        resized_building=building_image.getScaledInstance(w/factor, h/factor,0);
    }

    public void draw(Graphics g){

        Vector temp = buildingVector;
        //getDBBuilding();//this is for example will return the building Vector given by david
        //next you will get the Building Objects.

        //using for loop
        for(int i=0;i<buildingVector.size();i++){
            Building building = (Building)temp.get(i);// the Building came from the Building.java from the rawobjects package that I have imported for you.
            int width = ((int)building.getWidth());//the width of the building
            int height = ((int)building.getHeight());//the height of the building
            int xValueOfBuilding = ((int)building.getX());//the x of the building
            int yValueOfBuilding = ((int)building.getY());//the y of the building
            startX=xValueOfBuilding;
            startY=yValueOfBuilding;
            endX=startX+width;
            endY=startY+height;
            //g.drawRect(xValueOfBuilding, yValueOfBuilding, width, height); // Guage where the rectangle is, can be romoved
            //g.drawImage(building_image, xValueOfBuilding+25, yValueOfBuilding+25, width-50, height-50,iP); //Draw Method 1

            while(xValueOfBuilding+w/factor<endX){
                while(yValueOfBuilding+h/factor<endY){
                    g.drawImage(resized_building, xValueOfBuilding, yValueOfBuilding, null); //Draw Method 2
                    yValueOfBuilding=yValueOfBuilding+h/factor+20;
                }
                yValueOfBuilding=startY;
                xValueOfBuilding=xValueOfBuilding+w/factor+20;
            }

            //System.out.println("X "+xValueOfBuilding+" Y "+yValueOfBuilding);
            //System.out.println("Width "+width+" Height "+height);
       }
       for (int i=0;i<depotVector.size();i++){
           Depot tempDepot = (Depot)depotVector.get(i);
           int PhyX = ((int)(tempDepot.getPhysPoint()).getX());
           int PhyY = ((int)(tempDepot.getPhysPoint()).getY());
           int LogX = ((int)(tempDepot.getLogPoint()).getX());
           int LogY = ((int)(tempDepot.getLogPoint()).getY());
           //g.fillRect(PhyX, PhyY, 50, 50);
           //System.out.println("X "+PhyX+" Y "+PhyY+" I "+i);
           g.drawImage(depot_image, PhyX, PhyY-50, null); //Draw Method 2
       }
        //use the variable to draw or plot the buildings.
    }

    public void staticVectorEx(){//use this to assume the vector given by david
        //int ID,int MapID, int x, int y, int width, int height
        Building b1 = new Building(1,10101,80,30,100,150);
        buildingVector.add(b1);

        Building b12 = new Building(1,10102,300,60,100,120);
        buildingVector.add(b12);

        Building b13 = new Building(1,10102,800,60,100,130);
        buildingVector.add(b13);

        Point p = new Point(170,390);
        Point p1 = new Point(170,390);
        Depot d1 = new Depot(0,1,p,p1);
        depotVector.add(d1);

        p = new Point(850,400);
        p1 = new Point(580,400);
        d1 = new Depot(0,1,p,p1);
        depotVector.add(d1);

        p = new Point(900,580);
        p1 = new Point(900,580);
        d1 = new Depot(0,1,p,p1);
        depotVector.add(d1);

        p = new Point(450,260);
        p1 = new Point(450,260);
        d1 = new Depot(0,1,p,p1);
        depotVector.add(d1);

        p = new Point(0,0);
        p1 = new Point(0,0);
        d1 = new Depot(0,1,p,p1);
        depotVector.add(d1);

        p = new Point(0,580);
        p1 = new Point(0,580);
        d1 = new Depot(0,1,p,p1);
        depotVector.add(d1);
    }

    public static void setBuildingVect(Vector buildingVect,Vector depotVect){
        System.out.println("Initializing Building & Depot location");
        buildingVector = buildingVect;
        depotVector = depotVect;
    }


}
