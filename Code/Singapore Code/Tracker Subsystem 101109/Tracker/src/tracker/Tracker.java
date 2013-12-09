/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tracker;
import RMI.*;
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

import cc.registry.RegistryClient;
/**
 *
 * @author Tracker Subsystem
 */
public class Tracker implements ComponentListener{
    private static JFrame frame;
    private static RMITrackerServer server;
    private static Repository repository;
    private int x;
    private int y;

    public Tracker(){

        frame = new JFrame();
        repository = new Repository();
        //java.net.URL imageURL = tracker.Tracker.class.getResource("images/bg1.jpg");
        //ImagePanel panel = new ImagePanel(new ImageIcon("D:/images/bg1.jpg").getImage());
        //ImagePanel panel = new ImagePanel(new ImageIcon(imageURL).getImage());
        ImagePanel panel;
        try{

            RegistryClient multicastClient = new RegistryClient("TKR");
            RMITrackerClient RMIClient = new RMITrackerClient(multicastClient,repository);
            panel = new ImagePanel(RMIClient,repository);
            RMITrackerServer server = new RMITrackerServer(RMIClient,panel,repository);
            RMIClient.setServeR(server); //for testing only !



            new Thread(panel).start();
            new Thread(multicastClient).start();
            new Thread(RMIClient).start();

            frame.getContentPane().add(panel);
            frame.setTitle("Rorke's Drift Software - Tracker");
            frame.setResizable(false);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension size = frame.getSize();
            screenSize.height = screenSize.height/2;
            screenSize.width = screenSize.width/2;
            size.height = size.height/2;
            size.width = size.width/2;
            y = screenSize.height - size.height;
            x = screenSize.width - size.width;
            frame.setLocation(x, y);
            frame.addComponentListener(this);
            //frame.setAlwaysOnTop(true);
            frame.setVisible(true);


        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Tracker();
    }
    public void componentResized(ComponentEvent e)
    {
    }
    public void componentMoved(ComponentEvent e)
    {
        //frame.setLocation(x,y);
    }
    public void componentShown(ComponentEvent e)
    {
    }
    public void componentHidden(ComponentEvent e)
    {

    }
    public static JFrame getFrame(){
        return frame;
    }

}


