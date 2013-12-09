/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RMI;

/**
 *
 * @author David
 */

import rmi_interfaces.RMITrackerInterface;
import java.awt.*;
import java.io.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.Naming.*;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.*;
import java.util.Vector;
import rmi_interfaces.*;
import rawobjects.*;

public class RMItest {

    public static void main(String args[]){
            try{
                //create a server instance
                MulticastClient mc = new MulticastClient();
                String ccIP = mc.getCC_IP();
                System.out.println("CC IP is "+ccIP);
                RMITrackerClient RMIClient = new RMITrackerClient(ccIP);
               // System.out.println(RMIClient.connectToDB());
                RMITrackerServer server = new RMITrackerServer(RMIClient,null);
             //   while(true){
                  // server.run();
               // }

            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
}
