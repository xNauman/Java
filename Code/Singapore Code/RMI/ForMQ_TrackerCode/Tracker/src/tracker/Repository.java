/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tracker;
//import holder.*;
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
public class Repository {
    private ImagePanel iP;
    public Repository(ImagePanel iP){
        this.iP = iP;
    }
}
