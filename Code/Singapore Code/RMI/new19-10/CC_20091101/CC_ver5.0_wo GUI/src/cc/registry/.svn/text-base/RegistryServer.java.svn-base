package cc.registry;

import server.server;
import java.io.*;
import java.net.*;


public class RegistryServer extends RegistrySettings implements Runnable{

    private DatagramSocket rSocket = new DatagramSocket(port-1);
    private String[] clientIP = new String[6];
    private server sim;

    public RegistryServer(server sim) throws Exception {
        super();
        this.sim = sim;
        // start broadcasting
        RegistryBroadcast broadcast = new RegistryBroadcast();
        new Thread(broadcast).start();
    }

    public void run() {

        try {
            // start receiving
            while(true) {
                receivePacket();
            }
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }

    // RECEIVE INCOMING PACKET
    private void receivePacket() throws IOException {
        
//        System.out.println("receiving...");

        byte[] buf = new byte[3];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // note: method gets blocked here if no packet to receive
        rSocket.receive(packet);

        // extract IP address from packet
        InetAddress IP = packet.getAddress();
        String data = new String(packet.getData()).trim();

//        System.out.print(data);
//        System.out.println(" received.");

        // save IP address of specified client
        /*
         *By Yufeng
         *add a timers.restart() here to restart timer everytime receive from a subsystem,
         * to ensure the timer won't exceed 3s,otherwise this subsystem will be considered
         * as disconnected and romved from registry.
         * NO affects to anyone else
         */
        if(data.equals("CME")){
            saveIP(data, IP);
            sim.rmiServer.tCME.restart();
        }
        else if(data.equals("DB")){
            saveIP(data, IP);
            sim.rmiServer.tDB.restart();
        }
        else if(data.equals("NAV")){
            saveIP(data, IP);
            sim.rmiServer.tNAV.restart();
        }
        else if(data.equals("SCH")){
            saveIP(data, IP);
            sim.rmiServer.tSCH.restart();
        }
        else if(data.equals("SU")){
            saveIP(data, IP);
            sim.rmiServer.tSU.restart();
        }
        else if(data.equals("TKR")){
            saveIP(data, IP);
            sim.rmiServer.tTKR.restart();
        }

    }

    // METHOD TO GET INDEX OF SPECIFIED SUBSYSTEM
    private int getIndex(String ID) {

        if(ID.equals("CME")) return 0;
        if(ID.equals("DB"))  return 1;
        if(ID.equals("NAV")) return 2;
        if(ID.equals("SCH")) return 3;
        if(ID.equals("SU"))  return 4;
        if(ID.equals("TKR")) return 5;
        return -1;
    }

    // SAVE IP ADDRESS RECEIVED FROM SPECIFIED CLIENT
    private void saveIP(String ID, InetAddress IP) {

        clientIP[getIndex(ID)] = IP.getHostAddress();
//        System.out.println(ID + " IP SAVED.");
    }

    // GET METHOD TO EXTERNALLY RETRIEVE CLIENT IP ADDRESS
    public String getClientIP(String ID) {

        if(ID.equals("CME")) return clientIP[0];
        if(ID.equals("DB"))  return clientIP[1];
        if(ID.equals("NAV")) return clientIP[2];
        if(ID.equals("SCH")) return clientIP[3];
        if(ID.equals("SU"))  return clientIP[4];
        if(ID.equals("TKR")) return clientIP[5];
        return null;
    }

    // METHOD TO REMOVE CLIENT FROM REGISTRY
    /*
     *By Yufeng:
     *this method will be called to disconnect from a subsystem
     *if server does not received any feedbk in 3s
     * (here the time for decide whether a subsystem is disconnected must be longer the 2s,
     *  as they can only respose every 2s)
     * AGAIN, no affects to anyone else except CC
     */
    public void dcClient(String ID) {
        if(clientIP[getIndex(ID)] != null){
            clientIP[getIndex(ID)] = null;
            System.out.println(ID+" Disconnected ....");
        }
    }
}
