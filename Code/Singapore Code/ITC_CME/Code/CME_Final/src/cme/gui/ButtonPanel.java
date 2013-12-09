package cme.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import cme.dataengine.DataEngine;
import cme.dataengine.FlatFileEngine;
import cme.dataengine.RMI_Client;
import holder.Map;
import cme.interfaces.DataEngineInterface;

/**
 * Panel for all the buttons
 * @author Desmond Yeo Choon An
 */
public class ButtonPanel extends JPanel implements ActionListener {

    /**
     * This is the ploting of route toggle button
     */
    protected JToggleButton plotTBtn;
    /**
     * this is the adding of bus stop toggle button
     */
    protected JToggleButton addBusTBtn;
    /**
     * this is the of the delete bus stop toggle button
     */
    protected JToggleButton delBusTBtn;
    /**
     * This is the toggle button of adding the traffic lights
     */
    protected JToggleButton addTrafficTBtn;
    /**
     * This is the toggle button of the deletion of traffic lights
     */
    protected JToggleButton delTrafficTBtn;
    /**
     * this is the toggle button of the addition of the bus depot
     */
    protected JToggleButton addDepotTBtn;
    /**
     * this is the toggle button of the deletion of the bus depot
     */
    protected JToggleButton delDepotTBtn;
    /**
     * this is the toggle button of the addition of the junction of road 
     */
    protected JToggleButton addJunctionTBtn;
    /**
     * this is the toggle button of the deletion of the junction of the road
     */
    protected JToggleButton delJunctionTBtn;
    /**
     * This is the button of the reseting of the route
     */
    protected JButton resetRouteBtn;
    /**
     * this is the button where it's funciton is to delete the existing route of
     * the map which is selected on the route list 
     */
    protected JButton deleteRouteBtn;
    /**
     * this button is used to save the map and all the edited features
     */
    protected JButton saveMapBtn;
    /**
     * this button is used to let user select new map where the browser panel 
     * will appear
     */
    protected JButton newMapBtn;
    /**
     * this is the button where the saved map will be edited
     */
    protected JButton editorBtn;
    /**
     * this is the button where the browsed map will be saved
     */
    public JButton googleMapBtn;

    //Local
    /**
     * This is the button to referesh the local map list
     */
    protected JButton refreshLocalBtn;
    /**
     * This is the button to load the local map list into the list box
     */
    protected JButton loadLocalBtn;
    /**
     * This button is used to rename the map name of the local map list
     */
    protected JButton renameLocalBtn;
    /**
     * This is the button to delete the map from the local list
     */
    protected JButton deleteLocalBtn;

    //Database
    /**
     * this button is used to referesh the map list of data base
     */
    protected JButton refreshDBBtn;
    /**
     * this button is to load the maps from the database
     */
    protected JButton loadDBBtn;
    /**
     * this button is used to delete the map from the data base
     */
    //protected JButton deleteDBBtn;
    /**
     * this button is use to get the map and let it be displayed on the map 
     * panel to be edited
     */
    protected JButton doneDBBtn;

    //Others
    /**
     * This button is used to upload the local map list into the database
     */
    protected JButton uploadMapBtn;
    /**
     * this button is used to download the map from the data base to local
     * machine
     */
    protected JButton downloadMapBtn;
    /**
     * this button is used to exit the program
     */
    protected JButton quitBtn;

    //List Box
    /**
     * This is the list model which is later mounted to the map list of the 
     * local maps
     */
    public DefaultListModel localListModel = new DefaultListModel();
    /**
     * This is the list of the local map
     */
    protected JList localList = new JList(localListModel);
    private JScrollPane localScrollPane = new JScrollPane(localList);
    /**
     * This is the list model which is later mounted to the map list of the 
     * database maps
     */
    public DefaultListModel databaseListModel = new DefaultListModel();
    /**
     * This is the list of the database map
     */
    protected JList databaseList = new JList(databaseListModel);
    private JScrollPane databaseScrollPane = new JScrollPane(databaseList);
    /**
     * This is the list model which is later mounted to the route list of the 
     * routes of a map
     */
    public DefaultListModel routeListModel = new DefaultListModel();
    /**
     * This is the list of the route of a particular
     */
    protected JList routeList = new JList(routeListModel);
    private JScrollPane routeScrollPane = new JScrollPane(routeList);

    //Panels
    private JPanel[] btnPanel = new JPanel[12];
    private JPanel mainBtnPanel = new JPanel();
    private JPanel localListBtnPanel = new JPanel();
    private JPanel databaseListBtnPanel = new JPanel();
    private CityMapEditor cme;

    //Controls and interfaces
    private DataEngineInterface controlDE = new DataEngine();

    /**
     * This is the Default constructor of the button panel which will initilize
     * all the buttons and add the action listener to all the different buttons
     * @param cme Main program
     */
    public ButtonPanel(CityMapEditor cme) {
        this.cme = cme;
        this.setLayout(new BorderLayout(1, 1));

        localListBtnPanel = new JPanel();
        localListBtnPanel.setLayout(new GridLayout(3, 1));

        databaseListBtnPanel = new JPanel();
        databaseListBtnPanel.setLayout(new GridLayout(2, 1));

        //Local List
        localList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        localList.setSelectedIndex(0);
        localList.setVisibleRowCount(5);
        controlDE.populateLocalMapList(localListModel);


        //Database List
        databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        databaseList.setSelectedIndex(0);
        databaseList.setVisibleRowCount(5);

        //Route List
        routeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        routeList.setSelectedIndex(0);
        routeList.setVisibleRowCount(5);

        initButton();
        //Map Buttons
        plotTBtn.setToolTipText("Plot Route");
        resetRouteBtn.setToolTipText("Clear Current Route");
        addBusTBtn.setToolTipText("Add Bus Stop");
        delBusTBtn.setToolTipText("Delete Bus Stop");
        addTrafficTBtn.setToolTipText("Add Traffic Light");
        delTrafficTBtn.setToolTipText("Delete Traffic Light");
        addDepotTBtn.setToolTipText("Add Bus Depot");
        delDepotTBtn.setToolTipText("Delete Bus Depot");
        addJunctionTBtn.setToolTipText("Add Junction");
        delJunctionTBtn.setToolTipText("Delete Junction");
        deleteRouteBtn.setToolTipText("Delete Route");
        saveMapBtn.setToolTipText("Save the Map, Save the WORLD");

        //Buttons
        newMapBtn.setToolTipText("New Map");
        googleMapBtn.setToolTipText("Use This Map");
        editorBtn.setToolTipText("Load Existing Map");
        uploadMapBtn.setToolTipText("Upload Map To Database");
        downloadMapBtn.setToolTipText("Download Maps");
        quitBtn.setToolTipText("Quit Program");

        //Local
        refreshLocalBtn.setToolTipText("Refresh List");
        loadLocalBtn.setToolTipText("Load Local Map");
        renameLocalBtn.setToolTipText("Rename Local Map");
        deleteLocalBtn.setToolTipText("Delete Local Map");

        //Database
        refreshDBBtn.setToolTipText("Refresh List");
        loadDBBtn.setToolTipText("Load Database Map");
        //deleteDBBtn.setToolTipText("Delete Database Map");
        doneDBBtn.setToolTipText("Done");

        //Local List
        localListBtnPanel.add(loadLocalBtn);
        localListBtnPanel.add(renameLocalBtn);
        localListBtnPanel.add(deleteLocalBtn);

        //Database List
        databaseListBtnPanel.add(loadDBBtn);
        //databaseListBtnPanel.add(deleteDBBtn);
        databaseListBtnPanel.add(doneDBBtn);

        //Main Panel
        btnPanel[0] = new JPanel(new GridLayout(1, 2));
        btnPanel[0].add(newMapBtn);
        btnPanel[0].add(editorBtn);
        btnPanel[0].setPreferredSize(new Dimension(170, 60));
        btnPanel[0].setBorder(BorderFactory.createTitledBorder("City Map Editor"));

        //Depot Panel
        btnPanel[1] = new JPanel(new GridLayout(1, 4));
        btnPanel[1].add(addDepotTBtn);
        btnPanel[1].add(delDepotTBtn);
        btnPanel[1].setPreferredSize(new Dimension(170, 60));
        btnPanel[1].setBorder(BorderFactory.createTitledBorder("Depot"));

        //Plot Panel
        btnPanel[2] = new JPanel(new GridLayout(1, 2));
        btnPanel[2].add(plotTBtn);
        btnPanel[2].add(resetRouteBtn);
        btnPanel[2].setPreferredSize(new Dimension(170, 60));
        btnPanel[2].setBorder(BorderFactory.createTitledBorder("Route"));

        //Bus Stop Panel
        btnPanel[3] = new JPanel(new GridLayout(1, 2));
        btnPanel[3].add(addBusTBtn);
        btnPanel[3].add(delBusTBtn);
        btnPanel[3].setPreferredSize(new Dimension(170, 60));
        btnPanel[3].setBorder(BorderFactory.createTitledBorder("Bus Stop"));

        //Junction Panel
        btnPanel[4] = new JPanel(new GridLayout(1, 2));
        btnPanel[4].add(addJunctionTBtn);
        btnPanel[4].add(delJunctionTBtn);
        btnPanel[4].setPreferredSize(new Dimension(170, 60));
        btnPanel[4].setBorder(BorderFactory.createTitledBorder("Junction"));

        //Traffic Panel
        btnPanel[5] = new JPanel(new GridLayout(1, 2));
        btnPanel[5].add(addTrafficTBtn);
        btnPanel[5].add(delTrafficTBtn);
        btnPanel[5].setPreferredSize(new Dimension(170, 60));
        btnPanel[5].setBorder(BorderFactory.createTitledBorder("Traffic Light"));

        //Save Map Panel
        btnPanel[6] = new JPanel(new GridLayout(1, 1));
        btnPanel[6].add(saveMapBtn);
        btnPanel[6].setPreferredSize(new Dimension(170, 60));
        btnPanel[6].setBorder(BorderFactory.createTitledBorder("Save Map"));

        //Route Panel
        btnPanel[7] = new JPanel(new BorderLayout(0, 5));
        btnPanel[7].add(routeScrollPane, BorderLayout.CENTER);
        btnPanel[7].add(deleteRouteBtn, BorderLayout.SOUTH);
        btnPanel[7].setPreferredSize(new Dimension(170, 170));
        btnPanel[7].setBorder(BorderFactory.createTitledBorder("Existing Route"));

        //Browser Panel
        btnPanel[8] = new JPanel(new GridLayout(1, 1));
        btnPanel[8].add(googleMapBtn);
        btnPanel[8].setPreferredSize(new Dimension(170, 60));
        btnPanel[8].setBorder(BorderFactory.createTitledBorder("Browser"));

        //Database Panel
        btnPanel[9] = new JPanel(new BorderLayout(0, 5));
        btnPanel[9].add(refreshDBBtn, BorderLayout.NORTH);
        btnPanel[9].add(databaseScrollPane, BorderLayout.CENTER);
        btnPanel[9].add(databaseListBtnPanel, BorderLayout.SOUTH);
        btnPanel[9].setPreferredSize(new Dimension(170, 280));
        btnPanel[9].setBorder(BorderFactory.createTitledBorder("Maps (Database)"));

        //Local Panel
        btnPanel[10] = new JPanel(new BorderLayout(0, 5));
        btnPanel[10].add(refreshLocalBtn, BorderLayout.NORTH);
        btnPanel[10].add(localScrollPane, BorderLayout.CENTER);
        btnPanel[10].add(localListBtnPanel, BorderLayout.SOUTH);
        btnPanel[10].setPreferredSize(new Dimension(170, 280));
        btnPanel[10].setBorder(BorderFactory.createTitledBorder("Maps (Local)"));

        //Editor Database Panel
        btnPanel[11] = new JPanel(new GridLayout(2, 1));
        btnPanel[11].add(uploadMapBtn);
        btnPanel[11].add(downloadMapBtn);
        btnPanel[11].setPreferredSize(new Dimension(170, 90));
        btnPanel[11].setBorder(BorderFactory.createTitledBorder("Database"));


        for (int i = 0; i < btnPanel.length; i++) {
            mainBtnPanel.add(btnPanel[i]);
        }

        this.add(mainBtnPanel, BorderLayout.CENTER);
        this.add(quitBtn, BorderLayout.SOUTH);

        //MapView
        plotTBtn.addActionListener(this);
        plotTBtn.addActionListener(cme.mapPanel);
        resetRouteBtn.addActionListener(this);
        resetRouteBtn.addActionListener(cme.mapPanel);
        addBusTBtn.addActionListener(this);
        addBusTBtn.addActionListener(cme.mapPanel);
        delBusTBtn.addActionListener(this);
        delBusTBtn.addActionListener(cme.mapPanel);
        addTrafficTBtn.addActionListener(this);
        addTrafficTBtn.addActionListener(cme.mapPanel);
        delTrafficTBtn.addActionListener(this);
        delTrafficTBtn.addActionListener(cme.mapPanel);
        addDepotTBtn.addActionListener(this);
        addDepotTBtn.addActionListener(cme.mapPanel);
        delDepotTBtn.addActionListener(this);
        delDepotTBtn.addActionListener(cme.mapPanel);
        addJunctionTBtn.addActionListener(this);
        addJunctionTBtn.addActionListener(cme.mapPanel);
        delJunctionTBtn.addActionListener(this);
        delJunctionTBtn.addActionListener(cme.mapPanel);
        deleteRouteBtn.addActionListener(this);
        deleteRouteBtn.addActionListener(cme.mapPanel);
        saveMapBtn.addActionListener(this);
        saveMapBtn.addActionListener(cme.mapPanel);

        //Browser View
        newMapBtn.addActionListener(this);
        googleMapBtn.addActionListener(this);
        if (cme.browserEnable) {
            googleMapBtn.addActionListener(cme.browserPanel);
        }

        //Editor View
        editorBtn.addActionListener(this);
        uploadMapBtn.addActionListener(this);
        downloadMapBtn.addActionListener(this);
        quitBtn.addActionListener(this);

        //Local View
        refreshLocalBtn.addActionListener(this);
        loadLocalBtn.addActionListener(this);
        renameLocalBtn.addActionListener(this);
        deleteLocalBtn.addActionListener(this);
        routeList.addListSelectionListener(cme.mapPanel);

        //Database View
        refreshDBBtn.addActionListener(this);
        loadDBBtn.addActionListener(this);
        //deleteDBBtn.addActionListener(this);
        doneDBBtn.addActionListener(this);

        setBtnSize();
        showMainPanelOnly();
    }

    private void initButton() {
        try {
            //Map
            plotTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/road.gif"))));
            addBusTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/busStop.png"))));
            delBusTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/busCross.png"))));
            addTrafficTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/traffic.png"))));
            delTrafficTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/crossTraffic.png"))));
            addDepotTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/depot.png"))));
            delDepotTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/crossDepot.png"))));
            addJunctionTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/junction.png"))));
            delJunctionTBtn = new JToggleButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/crossJunction.png"))));
            resetRouteBtn = new JButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/crossRoad.png"))));
            deleteRouteBtn = new JButton("Delete");
            saveMapBtn = new JButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/saveMap.png"))));

            newMapBtn = new JButton(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/cme/resources/new.png"))));
            editorBtn = new JButton("Editor");
            googleMapBtn = new JButton("Use This Map");

            //Local
            refreshLocalBtn = new JButton("Refresh");
            loadLocalBtn = new JButton("Load Map");
            renameLocalBtn = new JButton("Rename");
            deleteLocalBtn = new JButton("Delete");

            //Database
            refreshDBBtn = new JButton("Refresh");
            loadDBBtn = new JButton("Load Map");
            //deleteDBBtn = new JButton("Delete");
            doneDBBtn = new JButton("Done");

            //Others
            uploadMapBtn = new JButton("Upload");
            downloadMapBtn = new JButton("Download DB Maps");
            quitBtn = new JButton("Exit Program");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setBtnSize() {
        plotTBtn.setPreferredSize(new Dimension(30, 30));
        resetRouteBtn.setPreferredSize(new Dimension(30, 30));
        addBusTBtn.setPreferredSize(new Dimension(30, 30));
        delBusTBtn.setPreferredSize(new Dimension(30, 30));
        addTrafficTBtn.setPreferredSize(new Dimension(30, 30));
        delTrafficTBtn.setPreferredSize(new Dimension(30, 30));
        addDepotTBtn.setPreferredSize(new Dimension(30, 30));
        delDepotTBtn.setPreferredSize(new Dimension(30, 30));
        addJunctionTBtn.setPreferredSize(new Dimension(30, 30));
        delJunctionTBtn.setPreferredSize(new Dimension(30, 30));
        deleteRouteBtn.setPreferredSize(new Dimension(150, 30));
        saveMapBtn.setPreferredSize(new Dimension(150, 30));
        quitBtn.setPreferredSize(new Dimension(150, 30));
        //Local
        refreshLocalBtn.setPreferredSize(new Dimension(150, 30));
        loadLocalBtn.setPreferredSize(new Dimension(150, 30));
        renameLocalBtn.setPreferredSize(new Dimension(150, 30));
        deleteLocalBtn.setPreferredSize(new Dimension(150, 30));
        //Database
        refreshDBBtn.setPreferredSize(new Dimension(150, 30));
        loadDBBtn.setPreferredSize(new Dimension(150, 30));
        //deleteDBBtn.setPreferredSize(new Dimension(150, 30));
        doneDBBtn.setPreferredSize(new Dimension(150, 30));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(quitBtn)) {
            int quit = JOptionPane.showConfirmDialog(null,
                    "Exit Program? All unsaved work will be lost",
                    "Quit Menu",
                    JOptionPane.YES_NO_OPTION);
            if (quit == 0) {
                System.exit(0);
            }
        }

        //Toggle Button
        if (e.getSource().equals(plotTBtn)) {
            resetAllTBtn();
            plotTBtn.setSelected(true);
        } else if (e.getSource().equals(addBusTBtn)) {
            resetAllTBtn();
            addBusTBtn.setSelected(true);
        } else if (e.getSource().equals(delBusTBtn)) {
            resetAllTBtn();
            delBusTBtn.setSelected(true);
        } else if (e.getSource().equals(addTrafficTBtn)) {
            resetAllTBtn();
            addTrafficTBtn.setSelected(true);
        } else if (e.getSource().equals(delTrafficTBtn)) {
            resetAllTBtn();
            delTrafficTBtn.setSelected(true);
        } else if (e.getSource().equals(addDepotTBtn)) {
            resetAllTBtn();
            addDepotTBtn.setSelected(true);
        } else if (e.getSource().equals(delDepotTBtn)) {
            resetAllTBtn();
            delDepotTBtn.setSelected(true);
        } else if (e.getSource().equals(addJunctionTBtn)) {
            resetAllTBtn();
            addJunctionTBtn.setSelected(true);
        } else if (e.getSource().equals(delJunctionTBtn)) {
            resetAllTBtn();
            delJunctionTBtn.setSelected(true);
        } else {
            //Indicate a non toggle btn is press
            resetAllTBtn();
        }

        //View Change
        if (e.getSource().equals(newMapBtn)) {
            cme.showBrowserPanel();
            setBrowserView();
        }
        if (e.getSource().equals(editorBtn)) {
            cme.showMapPanel();
            controlDE.populateLocalMapList(localListModel);
            setEditorView();
        }
        if (e.getSource().equals(doneDBBtn)) {
            cme.showMapPanel();
            controlDE.populateLocalMapList(localListModel);
            setEditorView();
        }

        if (e.getSource().equals(loadLocalBtn)) {
            Map map = null;
            map = FlatFileEngine.readMap((String) localList.getSelectedValue());
            if (map != null) {
                cme.mapPanel.loadMap(map);
                setMapView();
            }
        }
        if (e.getSource().equals(renameLocalBtn)) {
            FlatFileEngine.renameMap((String) localList.getSelectedValue());
            controlDE.populateLocalMapList(localListModel);
        }
        if (e.getSource().equals(deleteLocalBtn)) {
            FlatFileEngine.deleteMap((String) localList.getSelectedValue());
            controlDE.populateLocalMapList(localListModel);
        }
        if (e.getSource().equals(uploadMapBtn)) {
            Map map = FlatFileEngine.readMap((String) localList.getSelectedValue());
            if (map != null) {
                boolean uploaded = RMI_Client.send_map(map);
                if (uploaded) {
                    JOptionPane.showMessageDialog(null, map.getMapID() +" has been uploaded");
                } else {
                    JOptionPane.showMessageDialog(null, map.getMapID() +" could not be uploaded");
                }
            }
        }
        if (e.getSource().equals(downloadMapBtn)) {
            boolean connected = controlDE.populateDbMapList(databaseListModel);
            if (connected) {
                cme.showMapPanel();
                setDatabaseView();
            } 
        }
        if (e.getSource().equals(loadDBBtn)) {
            if (FlatFileEngine.mapSelected((String)databaseList.getSelectedValue())){
                Map map = RMI_Client.get_map((String) databaseList.getSelectedValue());
                if (map == null) {
                    JOptionPane.showMessageDialog(null, "Map could not be downloaded");
                } else {
                    boolean saved = FlatFileEngine.saveMap(map, false);
                    if (saved) {
                        cme.mapPanel.loadMap(map);
                        setMapView();
                    }
                }
            }
        }
        if (e.getSource().equals(refreshLocalBtn)) {
            controlDE.populateLocalMapList(localListModel);
        }
        if (e.getSource().equals(refreshDBBtn)) {
            controlDE.populateDbMapList(databaseListModel);
        }
    }

    /**
     * this method is to reset all toggle button selection to false
     */
    public void resetAllTBtn() {
        plotTBtn.setSelected(false);
        addBusTBtn.setSelected(false);
        delBusTBtn.setSelected(false);
        addTrafficTBtn.setSelected(false);
        delTrafficTBtn.setSelected(false);
        addDepotTBtn.setSelected(false);
        delDepotTBtn.setSelected(false);
        addJunctionTBtn.setSelected(false);
        delJunctionTBtn.setSelected(false);
    }

    private void showMainPanelOnly() {
        for (int i = 1; i < btnPanel.length; i++) {
            btnPanel[i].setVisible(false);
        }
    }

    /**
     * this is to set the buttons of the browsing to be visible 
     */
    public void setBrowserView() {
        showMainPanelOnly();
        btnPanel[8].setVisible(true);
    }

    /**
     * this method is used to set the buttons of the map editing to be visible
     */
    public void setMapView() {
        showMainPanelOnly();
        btnPanel[1].setVisible(true);
        btnPanel[2].setVisible(true);
        btnPanel[3].setVisible(true);
        btnPanel[4].setVisible(true);
        btnPanel[5].setVisible(true);
        btnPanel[6].setVisible(true);
        btnPanel[7].setVisible(true);
    }

    /**
     * this method is used to set the button of the downloading of map and 
     * uploading to the database be visible also the local map list
     */
    public void setEditorView() {
        showMainPanelOnly();
        cme.mapPanel.initMap();
        btnPanel[10].setVisible(true);
        btnPanel[11].setVisible(true);
    }

    /**
     * this method is to show the database manipulation
     */
    public void setDatabaseView() {
        showMainPanelOnly();
        btnPanel[9].setVisible(true);
    }
}
