/**
 * 
 */
package rmi_engine;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rawobjects.bus;
import rmi_interfaces.SCHServer;

/**
 * @author Raja Noman Saeed
 *
 */
public class SCHClientForNavImpl    {

	Registry mRemoteRegistry;
	protected  SCHServer mServerObject;
	String mServerAddress = "127.0.0.1";
	String mServerPort = "1234";
	
	
	private SCHClientForNavImpl() throws RemoteException, NotBoundException {
		super();
		/**
	     * get registry location
	     * */	
		String vMessage = "";
		System.out.println("Registering server");
		try{
			mRemoteRegistry = LocateRegistry.getRegistry(
					mServerAddress,
					(new Integer(mServerPort).intValue()));
		/**
		 * get the object from the remote-registry
		 */		
		System.out.println("Creating Remote-Object");
		

		mServerObject = (SCHServer)mRemoteRegistry.lookup("SCHServer");
		
		bus pBus = new rawobjects.bus();
		int pRequestID = 1;
		
		System.out.println(	"here");
		String vMessageString = mServerObject.sendToNAV();
		System.out.println(	"message fromt server:"+ vMessageString);
		
		vMessage = mServerObject.CreateNavigationInstance(pBus,pRequestID);
		System.out.println(	"here");
		System.out.println(	"message fromt server:"+ vMessage);
		
}catch(RemoteException e){
	 e.printStackTrace();
}
	    
	    /**
	     * call the remote method
	     */	    
	    

	}

	public static void main(String[] srgs){
		
		try{
		   SCHClientForNavImpl vClient = new SCHClientForNavImpl();
		   }catch(Exception e){
			   e.printStackTrace();
		   }
	}
}