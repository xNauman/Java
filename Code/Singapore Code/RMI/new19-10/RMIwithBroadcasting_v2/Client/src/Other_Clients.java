import java.io.*;
import java.net.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Other_Clients {

    // specify your subsystem name here (CME, DB, NAV, SCH, SU, TKR)
    private static final String SUBSYSTEM = "NAV";

    private int port = 4444;
    private InetAddress group = InetAddress.getByName("230.0.0.1");
    private MulticastSocket socket = new MulticastSocket(port);
    private InetAddress CC_IP;

    private boolean registered;

    public Other_Clients() throws Exception {

        socket.joinGroup(group);
        while(!receivePacket()) {
            if(!registered) sendIP();
        }
        socket.leaveGroup(group);

        /* INVOKE REMOTE METHODS HERE */
        getIP("NAV");        // get IP address of the specified subsystem
    }

    // OTHER SUBSYSTEMS TO RETRIEVE CC'S IP ADDRESS FROM THE BROADCAST
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
    
    private void getIP(String ID) throws Exception {
        
        System.out.println("RMI");
        
        String serverIP = CC_IP.getHostAddress();
        // get registry for the providing host and port
        Registry registry = LocateRegistry.getRegistry(serverIP, port);
        RMI_Interface CC = (RMI_Interface) registry.lookup("CC");
        
        System.out.println("IP retrieved:");
        System.out.println(CC.getIP(ID));
    }

    public static void main(String[] args) throws Exception {
        new Other_Clients();
    }
}