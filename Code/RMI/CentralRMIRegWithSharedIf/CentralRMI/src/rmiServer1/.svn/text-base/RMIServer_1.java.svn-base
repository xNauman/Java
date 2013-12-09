package rmiServer1;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmiInterface.RMIServer_1_INT;

/**
 * Implementation of RMI for Server 1
 * 
 * @author Ben
 *
 */


@SuppressWarnings("unused")
public class RMIServer_1
	extends UnicastRemoteObject 
	implements RMIServer_1_INT {
	private static final long serialVersionUID = 2897918669229935985L;
	private int mPort = 25000;
	private String mHost = "127.0.0.1";
	private String vName = "Server1";
	private Registry mLocalRegistry;
	
	@Override
	public String helloFromServer1() throws RemoteException {
		return "Hello from Server 1";
	}

	
	//--------- RMI SERVER CODE
	public RMIServer_1()throws RemoteException{
		super();
		
		System.out.println("RMIServer 1 - Rebinding to Registry: " + mHost + ":" + mPort);

		try{
			mLocalRegistry = LocateRegistry.getRegistry(mHost, mPort);
			mLocalRegistry.rebind(vName, this);
		}catch(Exception e){
			e.printStackTrace();
		}

	};


	public static void main(String[] args){

		try{
			RMIServer_1_INT vDummyRMIServer = new RMIServer_1();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	


}
