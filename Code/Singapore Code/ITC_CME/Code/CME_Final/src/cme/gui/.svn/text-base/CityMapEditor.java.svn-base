package cme.gui;

import cme.dataengine.RMI_Client;
import cme.dataengine.RMI_Server;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 * City Map Editor is the main gui frame
 * @author Desmond Yeo Choon An
 */
public class CityMapEditor extends JFrame{
    /**
     * the button control panel that display all the button that is used within 
     * the system
     */
    public ButtonPanel btnPanel; //the button control panel
    /**
     * the main panel where the map panel and the browser panel is mounted
     */
    public JPanel mainPanel;
    /**
     * is the panel that mount the map which is later to be drawn and edited 
     * by user
     */
    public MapPanel mapPanel;
    /**
     * the browser panel is used to display the selection of map to edit from 
     * google map
     */
    public Browser browserPanel;
    protected boolean browserEnable = false;
    protected static RMI_Server server;
    protected static RMI_Client client;
    /**
     * the default contructor of the city map editor which initialize all the 
     * panels and also the RMI client and server it will also set the the 
     * properties of the panels
     */
    public CityMapEditor() {
        this.setTitle("City Map Editor");
        mainPanel = new JPanel();
        mapPanel= new MapPanel(this);
        browserPanel = new Browser(this);
        btnPanel = new ButtonPanel(this);
        btnPanel.setPreferredSize(new Dimension(170,768));
        client = new RMI_Client();
        server = new RMI_Server(this, client);
        
        //Enable Browser
        if(!browserEnable)
            btnPanel.newMapBtn.setEnabled(false);
        
        Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        btnPanel.setBorder(border);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(mapPanel,BorderLayout.CENTER);
        mainPanel.add(browserPanel,BorderLayout.NORTH);
        showMapPanel();

        this.setLayout(new BorderLayout());
        this.setResizable(false);
        //this.add(statusLabel,BorderLayout.NORTH);
        this.add(btnPanel,BorderLayout.WEST);
        this.add(mainPanel,BorderLayout.CENTER);
    }

    /**
     * this method is to show make the map panel visible and browser panel
     * invisible
     */
    public void showMapPanel() {
        mapPanel.setVisible(true);
        browserPanel.setVisible(false);
        this.setSize(1200, 800);
    }

    /**
     * this method is to show make the browser panel visible and map panel 
     * invisible
     */
    public void showBrowserPanel() {
        mapPanel.setVisible(false);
        browserPanel.setVisible(true);
        this.setSize(1200, 856);
    }

    /**
     * The main method which runs the application
     * @param args
     */
    public static void main(String[] args) {
        CityMapEditor cme= new CityMapEditor();
        cme.setSize(1200, 800);
        cme.setResizable(false);
        cme.setVisible(true);
        cme.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        (new Thread(server)).start();
    }
}