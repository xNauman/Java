/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
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
/**
*
* @author Jian Hui
*/
public class TFrame {

    private static JFrame frame;

    public static JFrame getFrame(){
        return frame;
    }

    public static void main(String[] args) {

        frame = new JFrame();
        ImagePanel panel = new ImagePanel(new ImageIcon("images/bg1.jpg").getImage());
        ButtonPanel bp = new ButtonPanel();
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.getContentPane().add(bp,BorderLayout.SOUTH);
        frame.setTitle("Tracker");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setVisible(true);
    }       
}
class ButtonPanel extends JPanel implements ActionListener{
    JButton start = new JButton("START");
    JButton capture = new JButton("CAPTURE");
    
    public ButtonPanel(){
        this.setLayout(new GridLayout(1,5)); 
        this.add(start);
        this.add(capture);
        start.addActionListener(this);
        capture.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start){
            ImagePanel.startTimer();
            System.out.println("Here");
        }
        if(e.getSource()==capture){
            ImagePanel.captureScreen();
            System.out.println("Here");
        }
    }
}
class ImagePanel extends JPanel implements ActionListener{
    int x1=845;
    int y1=580;
    boolean check=false;
    boolean test = true;
    static JFrame frame;
    static Timer tm;
    private Image img;
    private Image img1;
    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }
    public static void startTimer(){
        tm.start();
    }
    public ImagePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
        tm = new Timer(20, this);
        img1 = new ImageIcon("images/icon_bus.jpg").getImage();
        frame = TFrame.getFrame();

    }
    public static void captureScreen(){
        try {
        Robot robot = new Robot();

        //
        // Capture screen from the top left in 200 by 200 pixel size.
        //
        BufferedImage bufferedImage = robot.createScreenCapture(
        new Rectangle( frame.getX()+10, frame.getY()+40, 
        frame.getWidth()-30, frame.getHeight()-90 ) );

        //new Rectangle( 0, 0, 900, 700));

        //new Rectangle(new Dimension(800, 600)));

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
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(img, 0, 0, null);
        g.drawImage(img1, x1, y1, null);
        //g.fillRect(x1,y1,20,20);
        //g.fillRect(425,270,20,20);
    }
    public void actionPerformed(ActionEvent e) {
        if(y1!=160 && !check) {
            if(y1==300){
                System.out.println("Helsslo");
                try{
                    Thread.sleep(5000);
                }
                catch(Exception e1){}
            }
            y1--;
        }
        if(y1==160){
            x1--;
        }
        if(x1==425){
            if(y1==270){
                try{
                    Thread.sleep(5000);
                }
                catch(Exception e1){}
            }
            check = true;
            y1++;
        }
        if(y1==570){
            x1--;
            if(x1==140){
                tm.stop();
            }
        }
        repaint();
    }
}
