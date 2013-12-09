/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mainBase;

import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author s4074209
 */
public class sharedUtil {

    //START SETTINGS: (common 'global' settings)
    public static final int LIGHT_SEQUENCE_FREQ = 3; //sec
    //END SETTINGS
    
    private static boolean bDebugMode = true;

    

    public static void writeDebugMsg(String sClass, String sMethodCall, String sText){
        if (bDebugMode) {
            //Date d = new Date();

            System.out.println(sharedUtil.formatDate(new Date()) + " " + sClass + "::" + sMethodCall + ": " +  sText);
        }
    }

    public static String formatDate(Date d)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(d);
                
    }

    public static String formatDate(Calendar c)
    {
        return formatDate(c.getTime());
    }

    public static void centreFrame(JFrame frame){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width-frame.getWidth())/2, (d.height-frame.getHeight())/2);
	}

	public static void centreFrame(JWindow window){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation((d.width-window.getWidth())/2, (d.height-window.getHeight())/2);
	}

	public static void centrePanel(JPanel panel) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		panel.setLocation((d.width-panel.getWidth())/2, (d.height-panel.getHeight())/2);
	}


    public static String pathDataFile()
    {
        return System.getProperty("user.dir") + "/DAT/";
    }
}
