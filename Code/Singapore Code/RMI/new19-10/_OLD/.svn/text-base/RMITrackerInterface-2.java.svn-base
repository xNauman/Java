/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LOHS0019
 */

package RMI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

import RMIData.*;
public interface RMITrackerInterface extends Remote {
    //this interface will have the methods that the server wants to provide
    public String say(String msg) throws RemoteException;
    public byte[] getImage() throws RemoteException;
   public boolean moveBus(Object reqBus) throws java.rmi.RemoteException;

    public boolean updateTL(Object reqTrafficLight) throws java.rmi.RemoteException;

    public boolean updateStop(Object reqBusStop) throws java.rmi.RemoteException;

}
