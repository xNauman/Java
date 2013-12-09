package cc.registry;

import java.io.*;
import java.net.*;

public class Server extends Settings {

    private int activeClients;
    private int registeredClients;
    private String[] clientIP = new String[6];

    public Server(int activeClients) throws Exception {

        super();
        this.activeClients = activeClients;

        socket.joinGroup(group);
        while(!broadcast()) {
            receivePacket();
            // wait for 2 seconds before next broadcast
            try { Thread.sleep(2000); }
            catch(InterruptedException e){}
        }
        socket.leaveGroup(group);
    }

    // SERVER (CC) TO BROADCAST LOCAL IP ADDRESS TO ALL CLIENTS IN THE GROUP
    private boolean broadcast() throws IOException {

        String data;
        if(registeredClients == activeClients) data = "ONLINE";
        else data = "CC";

        byte[] buf = data.getBytes();
        // broadcast across the network
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
        socket.send(packet);

        // all clients registered
        if(data.equals("ONLINE")) return true;

        return false;
    }

    // RECEIVE INCOMING PACKET
    private void receivePacket() throws IOException {

        byte[] buf = new byte[6];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // note: method gets blocked here if no packet to receive
        socket.receive(packet);

        // extract IP address from packet
        InetAddress IP = packet.getAddress();
        String data = new String(packet.getData()).trim();

        // send acknowledgement back to client
        if(data.equals("CME"))      sendAck(data, IP);
        else if(data.equals("DB"))  sendAck(data, IP);
        else if(data.equals("NAV")) sendAck(data, IP);
        else if(data.equals("SCH")) sendAck(data, IP);
        else if(data.equals("SU"))  sendAck(data, IP);
        else if(data.equals("TKR")) sendAck(data, IP);
    }

    // SEND ACKNOWLEDGEMENT (IN LOWER CASE) DIRECTLY BACK TO CLIENT
    private void sendAck(String ID, InetAddress IP) throws IOException {

        byte[] buf = ID.toLowerCase().getBytes();
        socket.send(new DatagramPacket(buf, buf.length, IP, port));
        saveIP(ID, IP);
    }

    // SAVE IP ADDRESS RECEIVED FROM SPECIFIED CLIENT
    private void saveIP(String ID, InetAddress IP) {

        if(clientIP[getIndex(ID)]==null) {
            clientIP[getIndex(ID)] = IP.getHostAddress();
            registeredClients++;        // increment no. of registered clients
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
}