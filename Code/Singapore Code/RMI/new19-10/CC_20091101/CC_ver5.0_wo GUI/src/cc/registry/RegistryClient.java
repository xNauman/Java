package cc.registry;

import java.io.*;
import java.net.*;

public class RegistryClient extends RegistrySettings implements Runnable {

    private MulticastSocket rSocket = new MulticastSocket(port);
    private DatagramSocket sSocket = new DatagramSocket(port-1);
    private String ID;
    private InetAddress serverIP;

    public RegistryClient(String ID) throws Exception {

        super();
        this.ID = ID;
    }

    public void run() {

        try {
            // join multicast group
            rSocket.joinGroup(group);

            while(true) {
                receivePacket();
                sendIP();
            }
        }
        catch(Exception e) {}
    }

    // RECEIVE INCOMING PACKET
    private void receivePacket() throws IOException {

        System.out.println("receiving...");

        byte[] buf = new byte[3];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        // note: method gets blocked here if no packet to receive
        rSocket.receive(packet);

        // extract data from packet
        String data = new String(packet.getData()).trim();
        // receive server (CC) IP address
        if(data.equals("CC")) serverIP = packet.getAddress();

        System.out.print(data);
        System.out.println(" IP RECEIVED.");
    }

    // SEND LOCAL IP ADDRESS TO SERVER (CC)
    private void sendIP() throws IOException {

        String data = ID;
        byte[] buf = data.getBytes();
        // send local IP directly to server (CC)
        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverIP, port-1);
        sSocket.send(packet);

        System.out.println("LOCAL IP SENT.");
    }

    // GET METHOD TO EXTERNALLY RETRIEVE SERVER (CC) IP ADDRESS
    public String getServerIP() {

        return serverIP.getHostAddress();
    }
}