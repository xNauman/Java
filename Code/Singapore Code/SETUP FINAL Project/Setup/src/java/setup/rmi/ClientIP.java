package setup.rmi;





import java.net.*;
import java.io.*;
import java.util.*;
/*
 * ClientIP.java
 *
 * Created on February 3, 2008, 10:22 PM
 */

/**
 *
 * @author  YuHui @ yuhui84@gmail.com
 */
public class ClientIP {

    private static String subSysGrp = "ControlCenter1234";
    private String MulticastAdd = "224.0.0.1";
    private int portUsed = 6789;
    private InetAddress add;
    private boolean ccStatusConfirmed = false; //if true, means cc has acknowlege this subsystem le
    /* Subsystem 2D array - 1 Password
     *                    - 1 Status (true - Connected, false - Not Connected)
     *                    - 1 IP
     **/
    private String[][] subSysInfo = new String[7][3];

    /**
     * Creates new form ServerIP
     */
    public ClientIP() {
        System.out.println("Localhost IP Address: " + getIPAdd());
        displayInfo(); //for debug use

        MulticastReceive mcR = new MulticastReceive();
        Thread mcRt = new Thread(mcR);
        mcRt.start();    
    }
    public String getIPAdd() {
        try {
            return InetAddress.getLocalHost().toString().split("/")[1];
        } 
        catch (Exception e) {
        }
        return "";
    }
    /**
     * @param args the command line arguments
     */
    //public static void main(String args[]) {
        //ClientIP client = new ClientIP();
   // }

    /**
     * setSubInfo - used by RMI to set all subsystem information(eg. IPs)
     * getSubInfo - used by Subsys to get all subsystem information(eg. IPs) 
     */
    public void setSubInfo(String[][] subSysIn) {
        subSysInfo = subSysIn;
    }

    public String[][] getSubInfo() {
        return subSysInfo;
    }

    private void displayInfo() {
        System.out.println("subSysGrp: " + subSysGrp);
        System.out.println("MulticastAdd: " + MulticastAdd);
        System.out.println("Port: " + portUsed);
    }

    /*
     * Multicast Code - used for receiving Server multicast msg, check it and send response
     */
    private class MulticastReceive implements Runnable {
                
        public void run() {
            
            
            
            try {
                while (true) {
                    if (ccStatusConfirmed == false) {
                        // join a Multicast group(MulticastGrp) and send own subsystem group
                        InetAddress group = InetAddress.getByName(MulticastAdd);

                        MulticastSocket mcS = new MulticastSocket(portUsed);
                        // s.setLoopbackMode(false);
                        mcS.joinGroup(group);

                        //subsystem receive CC msg and response
                        System.out.println("* Client - receiving MC msg");

                        byte[] buf = new byte[1000];
                        DatagramPacket recv = new DatagramPacket(buf, buf.length);
                        mcS.receive(recv);
                        System.out.println(recv.getAddress().toString());

                        //msg in datagram
                        String msgIn = new String(buf, 0, recv.getLength());
                        System.out.println("* Message received: " + msgIn);

                        //check if msg is from CC
                        if (msgIn.equalsIgnoreCase(subSysGrp)) {

                            System.out.println("* Msg is from CC");

                            //get sender address
                            add = recv.getAddress(); //get CC address
                            subSysInfo[0][2] = add.toString();   
                            System.out.println("* Control Center IP: " + add.toString());                  
                            
                            // Codes for authentication here
                            System.out.println("* Client responsing to CC");
                            ClientAuth ca = new ClientAuth();
                            ca.auth();
                        } else {
                            System.out.println("* Msg is NOT from CC");
                        }

                        //Add codes to send a msg to CC straightaway.
                        mcS.leaveGroup(group); // leave the group
                        Thread.sleep(10000); //sleep for 10seconds, shld be enough
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    Auth Code
     */
    private class ClientAuth {

        public void auth() {
            try {
                String clientAuth = readKey();
                DatagramSocket ds = new DatagramSocket();
                sendClientAuth(clientAuth, add, ds);
                receiveServerReply(ds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private String readKey() {
            /*
            Scanner sc = null;
            String reader = "", name = "", pass = "";

            try {
                File fileIn = new File("key.txt"); // Create file object to read
                sc = new Scanner(fileIn);

                while (sc.hasNextLine()) {
                    reader = sc.nextLine().trim();
                    if (reader.startsWith("Subsystem = ")) {
                        name = reader.substring(12).trim().toString();
                        System.out.println("Subsystem = " + name);
                    } else if (reader.startsWith("Password = ")) {
                        pass = reader.substring(11).trim().toString();
                        System.out.println("Password = " + pass);
                    }
                }
                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
             */
            String name = "SETUP";
            String pass = "1234";
                    
            return name.concat(pass);
        }

        private void sendClientAuth(String clientAuth, InetAddress add, DatagramSocket socket) {
            byte[] buf = new byte[1000];

            try {
                DatagramPacket sendPacketC = new DatagramPacket(buf, buf.length, add, 8000);
                sendPacketC.setData(clientAuth.getBytes());
                socket.send(sendPacketC);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void receiveServerReply(DatagramSocket socket) {
            byte[] buf = new byte[1000];
            String replyMsg;
            try {
                // Create a packet for receiving data
                DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);

                socket.receive(receivePacket);

                replyMsg = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(replyMsg);
                /******************************************/
                /******************************************/
                //if msg is correct then set to true.. else no set
                if (replyMsg.compareToIgnoreCase("Authentication Pass") == 0) {
                    ccStatusConfirmed = true;
                } else {
                    ccStatusConfirmed = false;
                }
            /******************************************/
            /******************************************/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new ClientIP();
    }
}
