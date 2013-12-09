/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LOHS0019
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server1Interface extends Remote {
    //this interface will have the methods that the server wants to provide
    public String say(String msg) throws RemoteException;
    public byte[] getImage() throws RemoteException;
    public boolean request() throws RemoteException;
    public void sendPicViaSocket() throws RemoteException;
}
