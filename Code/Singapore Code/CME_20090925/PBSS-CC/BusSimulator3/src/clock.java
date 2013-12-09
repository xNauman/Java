

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class clock extends JPanel implements ActionListener {
    protected javax.swing.Timer timer;
    private int speed;
    private int xCoord,yCoord;
    public int hour,minute,second;
    private double acHour,acMin,acSec;
    private String tStr;
    Image img = new ImageIcon("images/clock.png").getImage();
    //Image img = new ImageIcon("/Resources/"+"clock.png").getImage();
    //Image img = Toolkit.getDefaultToolkit().getImage("/Resources/"+"clock.png");

    public clock(int speed) {
       this.speed = speed;
       timer = new javax.swing.Timer(1000/speed, this);
       initCurrentTime();
       initClock();
       
    }

    public clock(int hour, int minute, int second,int speed) {
       this.speed = speed;
       this.hour = hour;
       this.minute = minute;
       this.second = second;
       timer = new javax.swing.Timer(1000/speed, this);
       initClock();
    }

    public int getHour() {
       return hour;
    }

    public int getMinute() {
       return minute;
    }

    public int getSecond() {
       return second;
    }

    public void setHour(int hour) {
       this.hour = hour;
       repaint();
    }

    public void setMinute(int minute) {
       this.minute = minute;
       repaint();
    }

    public void setSecond(int second) {
       this.second = second;
       repaint();
    }

    public void setSpeed(int speed) {
       this.speed = speed;
       repaint();
    }

    public void actionPerformed(ActionEvent e) {
        setCurrentTime();
        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

	int clockRadius = (int)(Math.min(getWidth(),getHeight())*0.8*0.5);
        int xCenter = getWidth()/2;
        int yCenter = getHeight()/2;

	if(img != null){
            g.drawImage(img,0,0,getSize().width,getSize().height,this);

	    int sLength = (int)(clockRadius*0.8);
            int xSecond = (int)(xCenter+sLength*Math.sin(second*(2*Math.PI/60)));
            int ySecond = (int)(yCenter-sLength*Math.cos(second*(2*Math.PI/60)));
            g.setColor(Color.red);
            g.drawLine(xCenter,yCenter,xSecond,ySecond);

            int mLength = (int)(clockRadius*0.65);
            int xMinute = (int)(xCenter+mLength*Math.sin(minute*(2*Math.PI/60)));
            int yMinute = (int)(yCenter-mLength*Math.cos(minute*(2*Math.PI/60)));
            g.setColor(Color.red);
            g.drawLine(xCenter,yCenter,xMinute,yMinute);

            int hLength = (int)(clockRadius*0.5);
            int xHour = (int)(xCenter+hLength*Math.sin((hour%12+minute/60.0)*(2*Math.PI/12)));
            int yHour = (int)(yCenter-hLength*Math.cos((hour%12+minute/60.0)*(2*Math.PI/12)));
            g.setColor(Color.red);
            g.drawLine(xCenter,yCenter,xHour,yHour);

            tStr = hour+":"+String.format("%02d",minute)+":"+String.format("%02d",second);

            FontMetrics fm = g.getFontMetrics();
            int strWidth = fm.stringWidth(tStr);
            int strAscent = fm.getAscent();

            xCoord = getWidth()/2-strWidth/2;
            yCoord = getHeight()/2+(int)(1.1*clockRadius)+strAscent/2;

	    g.setColor(Color.black);
            g.drawString(tStr,xCoord,yCoord);
            }
	}

    private void initClock(){
        acSec = second;
        acMin = minute+second/60.0;
        acHour = hour+minute/60.0+second/3600.0;
        
        timer.start();
        setSize(200,200);
        setOpaque(false);
        setVisible(true);
    }

    private void initCurrentTime(){
        Calendar calendar = new GregorianCalendar();
    	this.hour = calendar.get(Calendar.HOUR_OF_DAY);
    	this.minute = calendar.get(Calendar.MINUTE);
    	this.second = calendar.get(Calendar.SECOND);
    }

    private void setCurrentTime(){
        acSec = (acSec+1)%60;
        acMin = (acMin+(1/60.0))%60;
        acHour = (acHour+(1/3600.0))%12;
        second = (int)Math.floor(acSec);
        minute = (int)Math.floor(acMin);
        hour = (int)Math.floor(acHour);
    }

    public Dimension getPreferredSize(){
    	return new Dimension(200, 200);
    }
}

