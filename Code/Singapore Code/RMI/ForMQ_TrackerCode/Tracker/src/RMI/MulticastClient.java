/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RMI;

import java.io.*;
import java.net.*;
/**
 *
 * @author David
 */
public class MulticastClient {

    private static final String SUBSYSTEM = "TKR";

    private int port = 4444;
    private InetAddress group = InetAddress.getByName("230.0.0.1");
    private MulticastSocket socket = new MulticastSocket(port);
    private InetAddress CC_IP;

    private boolean registered;

    public MulticastClient() throws Exception{
        socket.joinGroup(group);
        while(!receivePacket()) {
            if(!registered) sendIP();
        }
        socket.leaveGroup(group);
    }

     private boolean receivePacket() throws IOException {

        System.out.println("receiving...");

        byte[] buf = new byte[6];

        // retrieve incoming packet
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        // extract data from packet
        String data = new String(packet.getData()).trim();

        // receive CC's IP
        if(data.equals("CC")) CC_IP = packet.getAddress();
        // receive acknowledgement on successful registration
        else if(data.equals(SUBSYSTEM.toLowerCase())) registered = true;
        // receive confirmation that all registrations have been completed
        else if(data.equals("ONLINE")) return true;

        System.out.println(data + " RECEIVED.");

        return false;
    }

    private void sendIP() throws IOException {

        System.out.println("sending...");

        String data = SUBSYSTEM;
        byte[] buf = data.getBytes();
        // send local IP directly to CC to complete registration
        DatagramPacket packet = new DatagramPacket(buf, buf.length, CC_IP, port);
        socket.send(packet);

        System.out.println(data + " SENT.");
    }

    public String getCC_IP(){
        return CC_IP.getHostAddress();
    }
}
