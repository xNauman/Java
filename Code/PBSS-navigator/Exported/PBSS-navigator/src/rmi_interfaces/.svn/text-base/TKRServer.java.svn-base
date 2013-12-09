/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LOHS0019
 */

package rmi_interfaces;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

import rawobjects.*;
public interface TKRServer extends Remote {
    //this interface will have the methods that the server wants to provide


    public byte[] getImage() throws RemoteException;

    public boolean moveBus(Bus reqBus, boolean isCongestedArea) throws java.rmi.RemoteException; //move selected bus

    public boolean updateTL(TrafficLight reqTrafficLight) throws java.rmi.RemoteException; //toggle selected traffic light

    public boolean updateStop(BusStop reqBusStop) throws java.rmi.RemoteException; //updated pax of passenger

    public boolean startupTKR() throws java.rmi.RemoteException;

    public int tkrState() throws java.rmi.RemoteException;

    public long SimTimeShare()  throws java.rmi.RemoteException;

    public boolean stopTKR() throws java.rmi.RemoteException;
    
    //ADDED: Ben as this wasn't added by NTU
    String debugEcho(String pFromModule, String pMessageToEcho) throws java.rmi.RemoteException;

}
