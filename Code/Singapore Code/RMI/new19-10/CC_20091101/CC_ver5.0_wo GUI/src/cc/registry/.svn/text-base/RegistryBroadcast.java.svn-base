package cc.registry;

import java.io.*;
import java.net.*;

public class RegistryBroadcast extends RegistrySettings implements Runnable {

    private DatagramSocket sSocket = new DatagramSocket(port);
    private int count = 1;

    public RegistryBroadcast() throws Exception {
        super();
    }

    public void run() {
        broadcastIP();
    }

    // SERVER (CC) TO BROADCAST LOCAL IP ADDRESS TO ALL CLIENTS IN THE GROUP
    private void broadcastIP() {

        String data = "CC";
        byte[] buf = data.getBytes();
        // broadcast across the network
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);

        while(true) {

//            System.out.print(count++);
//            System.out.println(" broadcasting...");

            // send packet
            try {
                sSocket.send(packet);
            }
            catch(IOException e) {
                System.err.println(e);
            }
            // wait for 2 seconds before next broadcast
            try {
                Thread.sleep(2000);
            }
            catch(InterruptedException e) { }
        }
    }
}