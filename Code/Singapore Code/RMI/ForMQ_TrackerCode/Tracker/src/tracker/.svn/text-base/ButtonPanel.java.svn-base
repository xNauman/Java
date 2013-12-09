/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tracker;
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
public class ButtonPanel extends JPanel implements ActionListener{
    JButton start = new JButton("START SUBSYSTEM");

    JButton capture = new JButton("CAPTURE");
    JButton setTrafficLight = new JButton("TOGGLE TRAFFIC LIGHT");
    JButton moveBus = new JButton("MOVE/STOP BUS");
    JButton stopAtBusStop = new JButton("STOP AT BUS STOP");
    public ButtonPanel(){
        this.setLayout(new GridLayout(2,4));
        this.add(start);
        this.add(moveBus);
        this.add(setTrafficLight);
        this.add(stopAtBusStop);
        this.add(capture);
        stopAtBusStop.addActionListener(this);
        start.addActionListener(this);
        capture.addActionListener(this);
        setTrafficLight.addActionListener(this);
        moveBus.addActionListener(this);
        start.setToolTipText("Start Subsystem");
        moveBus.setToolTipText("Move the bus");
        setTrafficLight.setToolTipText("Toggle light status");
        stopAtBusStop.setToolTipText("Stop a bus at a bus stop");
        capture.setToolTipText("Capture image");
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start){
            BusT.startTimer();
        }
//        if(e.getSource()==capture){
//            ImagePanel.captureScreen();
//        }
        if(e.getSource()==setTrafficLight){
           TrafficLightT.setLight();
        }
        if(e.getSource()==moveBus){
            if(BusT.move == true){
                BusT.move=false;
            }
            else{
                BusT.move = true;
                //Bus.bstop = false;
            }
        }
        if(e.getSource()==stopAtBusStop){
            //ImagePanel.bstop=true;
            BusT.addNewVect();
        }
        if(e.getSource()==capture){
            //ImagePanel.bstop=true;
            BusT.addNewVect2();
        }
    }
}
