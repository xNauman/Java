package cme.control;

import cme.dataengine.FlatFileEngine;
import java.awt.Point;
import cme.interfaces.BrowserInterface;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.swing.JOptionPane;
import cme.filter.GoogleMapFilter;
import cme.gui.CityMapEditor;
import holder.Map;
import cme.util.Constants;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Browser Control handles all browser function
 * @author Wong Zhen Cong
 */
public class BrowserControl implements BrowserInterface {

    /**
     * Capture the current Google map and save it, followed by loading it into
     * the map editor
     * @param cme   Main Program
     */
    public void captureGoogleMap(CityMapEditor cme) {
        try {
            Robot robot = new Robot();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            //check the application is within screen
            if (cme.getX() + 174 > 0 && cme.getY() + 30 > 0) {
                BufferedImage mapImage = robot.createScreenCapture(new Rectangle(cme.getX() + 174, cme.getY() + 30, 1024, 768));
                String mapID = (String) JOptionPane.showInputDialog(
                        null, "Enter New Map ID:", "Save Map",
                        JOptionPane.INFORMATION_MESSAGE, null, null, "");
                if (mapID == null) {
                    JOptionPane.showMessageDialog(null, "Map not saved",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (mapID.equals("")) {
                    JOptionPane.showMessageDialog(null, "Invalid Map ID",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (mapID.contains(" ")) {
                    JOptionPane.showMessageDialog(null, "Spaces not allowed",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                BufferedImage filterImage = GoogleMapFilter.filter(mapImage);
                Vector roadCoordinates = GoogleMapFilter.getRoadCoordinates(filterImage);
                Map map = new Map(mapID, mapImage);
                map.roadArea = (Vector<Point>) roadCoordinates.clone();
                boolean saved = FlatFileEngine.saveMap(map, false);
                if(saved) {
                    cme.mapPanel.loadMap(map, filterImage);
                    cme.btnPanel.setMapView();
                    cme.showMapPanel();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Please move the application within the screen", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Return whether the necessary files for Browser to work exists.
     * gMapCustom.html, jdic.jar, jdic_stub_windows.jar and jdic_native_applet.jar
     * @return true if necessary files for Browser to work exists
     */
    public boolean checkRequirements() {
        File html = new File("gMapCustom.html");
        File lib1 = new File("lib"+Constants.FILESEPARTOR+"jdic.jar");
        File lib2 = new File("lib"+Constants.FILESEPARTOR+"jdic_stub_windows.jar");
        File lib3 = new File("lib"+Constants.FILESEPARTOR+"jdic_native_applet.jar");
        if(html.exists() && lib1.exists() && lib2.exists() && lib3.exists())
            return true;
        else
            JOptionPane.showMessageDialog(null, "Missing Files, Browser function is disabled.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);

        return false;
    }
}