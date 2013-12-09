package rmiInterface;

import java.rmi.RemoteException;

/**
 * Interface file for Server 1
 * @author Ben
 *
 */
public interface RMIServer_1_INT extends java.rmi.Remote {

	String helloFromServer1() throws RemoteException;
	
}
