package setup.rmi;

//implementation methods to be called by other subsystems
import java.rmi.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import cme.model.*;
import pbssdbpackage.DatabaseClass;

public class SetupImp
        extends java.rmi.server.UnicastRemoteObject
        implements SetupInterface {

    private SetupServer setup;
    String dbip, ccip;
    private static boolean readyForCC = false; //changed to true when map is obtained

    // Implementations must have an 
    //explicit constructor 
    // in order to declare the 
    //RemoteException exception 
    public SetupImp(SetupServer setup)
            throws java.rmi.RemoteException {
        super();
        this.setup = setup;
    }

    public void goGetMap() //To be called by Control Centre to inform Setup to obtain map from Subsystem Database
            throws java.rmi.RemoteException {
        Image img;
        cme.model.Map map;
        cme.model.List mapList;
        BufferedImage ig;
        System.out.println("Getting Map: " + dbip);
        try {
            DatabaseClass dbc = (DatabaseClass) Naming.lookup("rmi://" + dbip + "/DatabaseService");
            dbc.init_DB_Connection();
            mapList = (cme.model.List) dbc.download_SU_Map_Objects();
            for (int i = 1; i <= mapList.size(); i++) {
                map = (cme.model.Map) mapList.getItem(i);
                img = map.getMapImageSent().getImage();
                ig = new BufferedImage(map.getMapImageSent().getIconWidth(), map.getMapImageSent().getIconHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = ig.createGraphics();
                g.drawImage(img, 0, 0, null);
                ImageIO.write(ig, "jpg", new File("C:\\Documents and Settings\\chak0003\\Desktop\\Setup\\build\\web\\image\\Map" + i + ".jpg"));
                //ImageIO.write(ig, "jpg", new File("/image/Map" + i + ".jpg"));
                System.out.println("Map " + i + " Downloaded");
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        readyForCC = true;
    }

    public void systemStop(Boolean flag)//Method to inform Setup that stop button has been pressed and all activities to be stopped
            throws java.rmi.RemoteException {
        System.out.println("systemStop() called!");
        return;
    }

    public void setDBIP(String db)//Method to inform Setup that stop button has been pressed and all activities to be stopped
            throws java.rmi.RemoteException {
        dbip = db;
        System.out.println("Set DBIP: " + dbip);
    }

    public boolean setRdySetup()
            throws java.rmi.RemoteException {
        return readyForCC;
    }
}
