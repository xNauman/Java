package cme.dataengine;

import cme.gui.CityMapEditor;

/**
 *
 * @author Guan Mei Ting
 */
public class RMI_Server implements Runnable {

    private static RMI_Impl Server;
    private static CityMapEditor app;
    private static RMI_Client client;

    /**
     * RMI server class
     * @param app Main files
     * @param client rmi client name
     */
    public RMI_Server(CityMapEditor app, RMI_Client client) {
        this.app = app;
        this.client = client;
    }

    public void run() {
        try {
            Server = new RMI_Impl("CME", app, client);

        } catch (Exception e) {
            System.out.println("Exception occurred: Error Creating Server! ");
            e.printStackTrace();
        }
    }
}