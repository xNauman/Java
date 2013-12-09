import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming.*;

public class CC_Server extends UnicastRemoteObject implements RMI_Interface {

    public enum Subsystem { CME, DB, NAV, SCH, SU, TKR }

    // specify no. of active subsystems (CLIENTS ONLY) used for testing
    private static final int ACTIVE_SUBSYSTEMS = 1;

    private int port = 4444;
    private InetAddress group = InetAddress.getByName("230.0.0.1");
    private MulticastSocket socket = new MulticastSocket(port);

    private String[] IP = new String[6];
    private int count;

    public CC_Server() throws IOException {

        // start subsystem registration
        socket.joinGroup(group);
        
        while(!broadcast()) {
            receivePacket();
            // wait for 2 seconds before next broadcast
            try { Thread.sleep(2000); }
            catch(InterruptedException e){}
        }
        socket.leaveGroup(group);
        
        Registry registry = LocateRegistry.createRegistry(port);
        registry.rebind("CC", this);

        System.out.println("CC RMI Server created.");
    }

    // CC TO BROADCAST LOCAL IP ADDRESS TO ALL SUBSYSTEMS
    private boolean broadcast() throws IOException {

        System.out.println("sending...");

        String data;

        if(count == ACTIVE_SUBSYSTEMS) data = "ONLINE";
        else data = "CC";

        byte[] buf = data.getBytes();

        // broadcast across the network
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
        socket.send(packet);
        System.out.println(data + " SENT.");

        if(data.equals("ONLINE")) return true;  // all registrations completed

        return false;
    }

    // RECEIVE INCOMING PACKET
    private void receivePacket() throws IOException {

        System.out.println("receiving...");

        byte[] buf = new byte[6];

        // retrieve incoming packet
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        // extract IP address from packet
        InetAddress clientIP = packet.getAddress();
        String data = new String(packet.getData()).trim();

        if(data.equals("CME")){
            sendAck(data, clientIP);
        }
        else if(data.equals("DB")){
            sendAck(data, clientIP);
        }
        else if(data.equals("NAV")){
            sendAck(data, clientIP);
        }
        else if(data.equals("SCH")){
            sendAck(data, clientIP);
        }
        else if(data.equals("SU")){
            sendAck(data, clientIP);
        }
        else if(data.equals("TKR")){
            sendAck(data, clientIP);
        }
    }

    // SEND ACKNOWLEDGEMENT (IN LOWER CASE) DIRECTLY BACK TO SUBSYSTEM
    private void sendAck(String ID, InetAddress clientIP) throws IOException {

        byte[] buf = ID.toLowerCase().getBytes();
        socket.send(new DatagramPacket(buf, buf.length, clientIP, port));
        System.out.println(ID + " ack");

        setIP(ID, clientIP);
    }

    // SAVE IP ADDRESS RECEIVED FROM SPECIFIED SUBSYSTEM
    private void setIP(String ID, InetAddress newIP) {

        // increment registration counter
        if(IP[getID(ID)]==null) count++;
        System.out.println("count = " + count);
        // save IP address of specified subsystem
        IP[getID(ID)] = newIP.getHostAddress();

        System.out.println(ID);
        System.out.println(newIP);
    }

    // METHOD TO GET NUMERICAL ID OF SPECIFIED SUBSYSTEM TO BE USED AS AN INDEX
    private int getID(String ID) {

        Subsystem id = Subsystem.valueOf(ID);

        switch(id) {
            case CME:  return 0;
            case DB:   return 1;
            case NAV:  return 2;
            case SCH:  return 3;
            case SU:   return 4;
            case TKR:  return 5;
            default:   return -1;
        }
    }

    // REMOTE METHOD PROVIDED FOR OTHER SUBSYSTEMS TO REQUEST FOR IP ADDRESS OF
    // SPECIFIED SUBSYSTEM FROM CC SERVER
    // (THIS IS JUST A SAMPLE TO DEMONSTARTE RMI INTEGRATION)
    public String getIP(String ID) throws RemoteException {
        return IP[getID(ID)];
    }

    public static void main(String args[]) throws IOException {
        new CC_Server();
    }
}