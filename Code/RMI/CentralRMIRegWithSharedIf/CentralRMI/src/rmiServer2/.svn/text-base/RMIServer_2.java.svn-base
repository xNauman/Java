package rmiServer2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rmiInterface.RMIServer_2_INT;

/**
 * Implementation of RMI for Server 2
 * 
 * @author Ben
 *
 */

@SuppressWarnings("unused")
public class RMIServer_2
	extends UnicastRemoteObject 
	implements RMIServer_2_INT {
	private static final long serialVersionUID = 2897918669229935985L;
	private int mPort = 25000;
	private String mHost = "127.0.0.1";
	private String vName = "Server2";
	private Registry mLocalRegistry;
	
	@Override
	public String helloFromServer2() throws RemoteException {
		return "Hello from Server 2";
	}

	
	//--------- RMI SERVER CODE
	public RMIServer_2()throws RemoteException{
		super();
		
		System.out.println("RMIServer 2 - Rebinding to Registry: " + mHost + ":" + mPort);

		try{
			mLocalRegistry = LocateRegistry.getRegistry(mHost, mPort);
			mLocalRegistry.rebind(vName, this);
		}catch(Exception e){
			e.printStackTrace();
		}

	};


	public static void main(String[] args){

		try{
			RMIServer_2_INT vDummyRMIServer = new RMIServer_2();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	


}
