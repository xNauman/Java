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
/**
 *
 * @author Tracker Subsystem
 */
public class Tracker {
    private static JFrame frame;
    private static RMITrackerServer server;

    public static void main(String[] args) {

        frame = new JFrame();
        ImagePanel panel;
        try{
            //create a server instance
//            MulticastClient mc = new MulticastClient();
//            String ccIP = mc.getCC_IP();
//            System.out.println("CC IP is "+ccIP);
//
//            RMITrackerClient RMIClient = new RMITrackerClient(ccIP);
//            Tracker track = new Tracker();
//            panel = new ImagePanel(RMIClient);
            panel = new ImagePanel(null);
//            RMITrackerServer server = new RMITrackerServer(RMIClient,panel);
//            while(true){
//                server.run();
//            }

            frame.getContentPane().add(panel);
            frame.setTitle("Tracker Subsystem");
            frame.setResizable(false);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1024, 768);
            frame.setVisible(true);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static JFrame getFrame(){
        return frame;
    }
}


