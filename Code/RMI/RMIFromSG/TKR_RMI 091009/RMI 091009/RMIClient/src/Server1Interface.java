/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lohs0019
 */
import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public interface Server1Interface extends Remote {

    //at the client side, we can choose which service can be use by this 
    //particular client...
    public String say(String msg) throws RemoteException;
    public byte[] getImage() throws RemoteException;
    public boolean request() throws RemoteException;
    public void sendPicViaSocket() throws RemoteException;
}

