package cc.registry;

import java.io.*;
import java.net.*;

public class Client extends Settings {

    private String ID;
    private InetAddress serverIP;
    private boolean registered;

    public Client(String ID) throws Exception {

        super();
        this.ID = ID;

        socket.joinGroup(group);
        while(!receivePacket()) {
            if(!registered) sendIP();
        }
        socket.leaveGroup(group);
    }

    // RECEIVE INCOMING PACKET
    private boolean receivePacket() throws IOException {

        byte[] buf = new byte[6];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // note: method gets blocked here if no packet to receive
        socket.receive(packet);

        // extract data from packet
        String data = new String(packet.getData()).trim();

        // receive server (CC) IP address
        if(data.equals("CC")) serverIP = packet.getAddress();
        // receive acknowledgement on successful registration
        else if(data.equals(ID.toLowerCase())) registered = true;
        // receive confirmation that all registrations have been completed
        else if(data.equals("ONLINE")) return true;

        return false;
    }

    // SEND LOCAL IP ADDRESS TO SERVER (CC)
    private void sendIP() throws IOException {

        String data = ID;
        byte[] buf = data.getBytes();
        // send local IP directly to server (CC)
        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverIP, port);
        socket.send(packet);
    }

    // GET METHOD TO EXTERNALLY RETRIEVE SERVER (CC) IP ADDRESS
    public String getServerIP() {
        return serverIP.getHostAddress();
    }
}