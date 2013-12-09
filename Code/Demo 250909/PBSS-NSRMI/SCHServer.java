package RMITest;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Admin
 */
public interface SCHServer extends Remote {
 void tellString() throws RemoteException;
}
