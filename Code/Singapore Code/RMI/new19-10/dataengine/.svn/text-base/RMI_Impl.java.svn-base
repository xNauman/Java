package cme.dataengine;

import cme.interfaces.RMI_CME;
import cme.gui.CityMapEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import RMI.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.Timer;

/**
 * methods to inform server that client is online
 * @author Guan Mei Ting
 */
public class RMI_Impl implements RMI_CME, ActionListener {

    private String mySystemName = "temp";
    Timer statusUpdateTm = new Timer(2500, this);
    private static CityMapEditor app;
    private static RMI_Client client;

    /**
     * Implement the RMI method
     * @param name name of system
     * @param app main program
     * @param client RMI client files
     * @throws java.rmi.RemoteException
     */
    public RMI_Impl(String name, CityMapEditor app, RMI_Client client) throws RemoteException {
        super();
        this.app = app;
        this.client = client;
        mySystemName = name;
        try {
            waitForConnection();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

//define server side methods here
    /**
     * Server provide methods for cilents to call
     * @param Parameters
     * @param Parameters2
     * @return
     */

//----------------------------- No need to edit after this part ------------------------------

    //******************** Initialise Required Variables *******************************
    private String RegistryIP = "";
    private Registry Reg = null;
    private RMI RMIsvr = null;
    private CC CCRemoteObj = null;
    private MulticastSocket mSocket;
    private boolean IMRegistered = false;
    private static boolean IMConnected2CC = false;
    private static boolean RMISConnectErrPrinted = false;
    private static boolean CCConnectErrPrinted = false;

    //******************** Timer Action for Updating status to CC and Checking Connections *******************
    public void actionPerformed(ActionEvent event) {            //timer action event

        if (event.getActionCommand().equals("update")) {        // if timer is in ------- UPDATE MODE
            statusUpdate();
        } else if (event.getActionCommand().equals("wait4CC")) {     // if timer is in ------- WAIT FOR CC CONNECTION MODE
            statusUpdateTm.stop();                                 // stop timer so timer wont keep calling actionPerformed.
            IMConnected2CC = false;
            IMRegistered = false;
            try {
                // stop timer so timer wont keep calling actionPerformed.

                waitForConnection();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    //---------------------------------------------------------------------------------------------------------

    //******************** Method to Listen for RMI IP Broadcast Packet *******************
    private boolean listenForRMIaddr() {
        boolean result = true;
        try {
            // Which port should we listen to
            int port = 5000;
            // Which address
            String group = "225.4.5.6";

            // Create the socket and bind it to port 'port'.
            mSocket = new MulticastSocket(port);

            // join the multicast group
            mSocket.joinGroup(InetAddress.getByName(group));
            // Now the socket is set up and we are ready to receive packets

            // Create a DatagramPacket and do a receive
            byte buf[] = new byte[1024];
            DatagramPacket pack = new DatagramPacket(buf, buf.length);

            mSocket.receive(pack);

            String recvIP = pack.getAddress().toString();
            recvIP = recvIP.substring(1);
            //System.out.println("RMIRegistry is at " + recvIP);

            // And when we have finished receiving data leave the multicast group and
            // close the socket
            mSocket.leaveGroup(InetAddress.getByName(group));
            if (recvIP.equals(RegistryIP)) {
                result = false;               // RMI IP still valid
            } else {

                System.out.print("Received new registry IP..");
                RegistryIP = recvIP;
                client.setIP(recvIP);       // update client's rmi ip to new ip
                result = true;               // RMI IP has changed
            }
        } catch (Exception e) {
            System.out.println("Listen Broadcast Error!");
            System.out.println(e);
        } finally {
            mSocket.close();
        }
        return result;        // by logic, we shld never be able to reach this point.
    }
    //-------------------------------------------------------------------------------------

    //********** In the case of shutting down the server, we can run this method to unregister from the RMIRegistry *******
    /**
     *
     */
    protected void unReg() {
        try {
            Reg = LocateRegistry.getRegistry(RegistryIP);
            RMI sv = (RMI) Reg.lookup("RMIServer");
            sv.unbind("CC");
        } catch (Exception e) {
            System.out.println("Failed to unbind CC from Registry on Exit");
        }
    }

    private void statusUpdate() {
        try {
            CCRemoteObj.updateStatus(mySystemName);          //try connecting to CC to update status
        } catch (Exception e) {                            //if this exception has occurred alrdy, dont print multiple error msg
            System.out.println(e);
            System.out.println("Update Status Failed, Unable to Connect to Control Centre. ");
            IMConnected2CC = false;
            statusUpdateTm.setActionCommand("wait4CC");             //set timer to attempt above connection again
        }
    }
    private RMI_CME stub;

    private void waitForConnection() throws InterruptedException {

        /* Since connection to CC is down, possible that CC IP has changed?
         * Check with RMIRegistry if CC has updated the RMIRegistry */
        System.out.print("Waiting to receive RMI Registry broadcast...");
        boolean RMIIPchanged = listenForRMIaddr();
        System.out.println("Ok!");
        if (RMIIPchanged) {
            try {
                Reg = LocateRegistry.getRegistry(RegistryIP);
            } catch (Exception e) { // If theres exception here, ip is correct but connection still cannot be made.
                System.out.println();
                System.out.println("Network Error. Broadcast receieved but connection to RMIRegistry cannot be established.");
                System.exit(0);
            }
        }
        if (!IMRegistered) {
            System.out.print("Trying to register to RMI Registry...");
            RMISConnectErrPrinted = false;
            try {
                if (stub == null) {
                    stub = (RMI_CME) UnicastRemoteObject.exportObject(this, 0);
                }
                RMIsvr = (RMI) Reg.lookup("RMIServer");


                RMIsvr.registerMe(mySystemName, stub);
                IMRegistered = true;
                System.out.println("Ok!");
            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
                System.out.println("Failed.");
            }

        }

        if (IMRegistered) {

            System.out.print("Trying Connection to ControlCentre...");
            try {
                CCRemoteObj = (CC) Reg.lookup("CC");
                System.out.println("Ok!");
                IMConnected2CC = true;
            } catch (Exception e) {
                //if (!CCConnectErrPrinted) {                                 // if this exception has occurred alrdy, dont print multiple error msg
                //System.out.println(e);
                System.out.println("Failed.");
            //CCConnectErrPrinted = true;
            //}
            }
        }

        if (IMRegistered && IMConnected2CC) {
            //If we reach this point, means connection to CC is successful. We can continue status update
            statusUpdateTm.setActionCommand("update");
            statusUpdateTm.start();
        } else {
            statusUpdateTm.setActionCommand("wait4CC");
            statusUpdateTm.start();
        }
    }

    //-------------------------------------------------------------------------------------
}

