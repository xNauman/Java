package cc.rmi;

import java.sql.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import server.*;
import cc.registry.*;
import rmi_interfaces.*;
import javax.swing.Timer;
import java.awt.event.*;

public class RMIServer extends UnicastRemoteObject implements CCInterface, Runnable, ActionListener {

    private static Registry registry;
    private RegistryServer regServer;
    private server sim;
    private int port = 4445;

    private CMEInterface CME;
    private DBInterface DB;
    private NAVInterface NAV;
    private SCHInterface SCH;
    private SUInterface SU;
    private TKRInterface TKR;

    public Timer tCME = new Timer(3000,this);
    public Timer tDB = new Timer(3000,this);
    public Timer tNAV = new Timer(3000,this);
    public Timer tSCH = new Timer(3000,this);
    public Timer tSU = new Timer(3000,this);
    public Timer tTKR = new Timer(3000,this);


    private int countP = 0; //control the frequency of print(), for test only


    public RMIServer(server sim, RegistryServer regServer) throws RemoteException {

        this.regServer = regServer;
        this.sim = sim;

        //these timers will handle the case when some subsystems become offline
        tCME.setActionCommand("CME");
        tDB.setActionCommand("DB");
        tNAV.setActionCommand("NAV");
        tSCH.setActionCommand("SCH");
        tSU.setActionCommand("SU");
        tTKR.setActionCommand("TKR");

        tCME.start();
        tDB.start();
        tNAV.start();
        tSCH.start();
        tSU.start();
        tTKR.start();
    }

    public void run() {
        try {
            // create RMI server
            registry = LocateRegistry.createRegistry(port);
            registry.rebind("CC", this);

//            Registry reg = LocateRegistry.getRegistry("localhost", port);
//            CCInterface CC = (CCInterface) reg.lookup("CC");

            System.out.println("CC RMI Server created.");

            Thread.sleep(2000);
        }
        catch(Exception e) {
            System.err.println(e);
        }
    }

    // all subsystems can call this method to get IP address of another
    // subsystem with the specified ID - CME, DB, NAV, SCH, SU, TKR
    public String getIP(String ID) throws RemoteException {
       return regServer.getClientIP(ID);
    }

    // all subsystems to call this method to update their status
    // ID - CME, DB, NAV, SCH, SU, TKR
    public void updateStatus(String ID, int state) throws RemoteException{
        try{
            if(ID.equals("CME")) updateSt(0,state);
            if(ID.equals("DB"))  updateSt(1,state);
            if(ID.equals("NAV")) updateSt(2,state);
            if(ID.equals("SCH")) updateSt(3,state);
            if(ID.equals("SU"))  updateSt(4,state);
            if(ID.equals("TKR")) updateSt(5,state);
        }catch(SQLException e){
            System.err.println(e);
        }
    }

    protected void updateSt(int ID, int state) throws RemoteException,SQLException {

        int count = 0;
        boolean rdyDB = false;
        boolean rdyCME= false;
        boolean rdySU = false;

        switch(state){
            case 0://online
                switch(ID){//update on status panel
                    case 1:
                        DB.startupDB();
                        System.out.println("DB is online~");
//                        sim.rightpane.statuspane.updateStatus(ID, 0);
                        break;
                    case 0:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
//                        sim.rightpane.statuspane.updateStatus(ID, 0);
                        //sim.startEnable = true;//for test only, should be removed later...
                        //System.out.println("Start button enabled");
                        count++;//count how many ss has registered
                        if(count==6){
                            //System.out.println("All subsystems are online");
                            //DB.startDB();
                            CME.startupCME();
                            count=0;//will be used again in following status
                        }
                        break;
                    default:
                }
                break;
            case 1://processing startup;only for debuging purposes
                switch(ID){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
//                        sim.rightpane.statuspane.updateStatus(ID, 1);
                        break;
                    default:
                }
                break;
            case 2://ready
                switch(ID){
                    case 0:
                        rdyCME=true;
//                        sim.rightpane.statuspane.updateStatus(ID, 2);
                        count++;
                        if(rdyDB)
                            SU.startupSU();//call SU to start up if DB and CME are ready
                        break;
                    case 1:
                        rdyDB=true;
//                        sim.rightpane.statuspane.updateStatus(ID, 2);
                        count++;
                        if(rdyCME)
                            SU.startupSU();//call SU to start up if DB and CME are ready
                        break;
                    case 4:
                        rdySU=true;
//                        sim.rightpane.statuspane.updateStatus(ID, 2);
//                        sim.brEnable = true;//enable browser,since su finish loading maps from DB
                        count++;
                        break;
                    case 2:
                    case 3:
                    case 5:
//                        sim.rightpane.statuspane.updateStatus(ID, 2);
                        count++;
                        System.out.println("Start button enabled");
                        if(count==6){//all ss are ready,start simulation *here SU has finished
                            /*enable start button here*/
//                            sim.startEnable = true;
                        }
                        break;
                    default:
                }
                break;
            case 3://processing simulation;only for debuging purposes
                switch(ID){
                    case 0:
                    case 1:
                        break;
                    case 4:
//                        sim.rightpane.statuspane.updateStatus(ID, 3);//user inputs finish
                        NAV.startupNAV();
                        SCH.startUpSCH(1);//?
                        TKR.startupTKR();
                        break;
                    case 2:
                    case 3:
                    case 5:
//                        sim.rightpane.statuspane.updateStatus(ID, 3);
                        break;
                    default:
                }
                break;
            case 4://terminating;only for debuging purposes
                switch(ID){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
//                        sim.rightpane.statuspane.updateStatus(ID, 4);
                        break;
                    default:
                }
                break;
            case 5://terminated /*not sure about the sequence of terminating*/
                switch(ID){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
//                        sim.rightpane.statuspane.updateStatus(ID, 4);
                        break;
                    default:
                }
                break;
            default:
        }
    }

    // provide the PBSS time for all ss to resynchronise
    public long SimTimeShare() throws RemoteException{
//        SynClock clk = new SynClock();
//        return clk.getSimTime();
        return 0;
    }

    public void updateLog(String str) throws RemoteException {
        writeFile(str);
    }

    private void writeFile(String str) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("log.txt", true));
            out.write(str);
            out.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    /*
     *By Yufeng
     * only will be called if a subsystem didn't response for more than 3s;
     * this method will call to remove the registral information from server
     */
    public void actionPerformed(ActionEvent e){
        String subs = e.getActionCommand();
        regServer.dcClient(subs);

        countP++;
        if(countP==4){
            print();
            countP=0;
        }
    }

    /*
     *Edited by Yufeng
     *print out current registered client, for test only
     */
    public void print() {
            System.out.println("CURRENTLY REGISTERED:");

            if(regServer.getClientIP("CME")!=null) {
                System.out.print("CME ");
                System.out.println(regServer.getClientIP("CME"));
            }

            if(regServer.getClientIP("DB")!=null) {
                System.out.print("DB ");
                System.out.println(regServer.getClientIP("DB"));
            }

            if(regServer.getClientIP("NAV")!=null) {
                System.out.print("NAV ");
                System.out.println(regServer.getClientIP("NAV"));
            }

            if(regServer.getClientIP("SCH")!=null) {
                System.out.print("SCH ");
                System.out.println(regServer.getClientIP("SCH"));
            }

            if(regServer.getClientIP("SU")!=null) {
                System.out.print("SU ");
                System.out.println(regServer.getClientIP("SU"));
            }

            if(regServer.getClientIP("TKR")!=null) {
                System.out.print("TKR ");
                System.out.println(regServer.getClientIP("TKR"));
            }
    }
}
