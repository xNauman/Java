package rmiClient1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmiInterface.RMIServer_1_INT;


public class RMIClient_1 {
	private static Registry mRemoteRegistry;
	private static String mRegistryAddress = "127.0.0.1"; //location of RMI registry
	private static int mRegistryPort = 25000; //The port RMI is operating on
	private static String mRegistryName = "Server1"; //The name of the binded server object

	public RMIClient_1() 
	throws RemoteException, NotBoundException {
		super();

	}

	private static RMIServer_1_INT getServerObject()
		throws RemoteException, NotBoundException{

		RMIServer_1_INT objServerObject;

		mRemoteRegistry = LocateRegistry.getRegistry(mRegistryAddress,mRegistryPort);
			
		// Connect and obtain object from Remote Server
		try {
			objServerObject = (RMIServer_1_INT)mRemoteRegistry.lookup(mRegistryName);
		}
		catch (NotBoundException ex){
			ex.printStackTrace();
			return null;
		}

		return objServerObject;
	}

	public static void main(String args[]){
		RMIServer_1_INT obj;
		try {
			obj = getServerObject();
			System.out.println(obj.helloFromServer1());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}

