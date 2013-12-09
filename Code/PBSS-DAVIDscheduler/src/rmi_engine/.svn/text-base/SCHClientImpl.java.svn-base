/**
 * 
 */
package rmi_engine;
/**
 * RMI client package, this will be the client navigator uses to talk to other modules via RMI
 * More to come on this one
 * 
 */

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi_interfaces.SCHServer;


/**
 * @author Raja Noman Saeed
 *
 */
public class SCHClientImpl    {

	Registry mRemoteRegistry;
	protected  SCHServer mServerObject;
	String mServerAddress = "127.0.0.1";
	String mServerPort = "1234";
	
	
	private SCHClientImpl() throws RemoteException, NotBoundException {
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
		
		// connect to the rmi server
		mServerObject = (SCHServer)mRemoteRegistry.lookup("SCHServer");
		
		// execute remote method call!
		vMessage  =  mServerObject.sendToNAV();
		
		//show the results
		System.out.println(	"message fromt server:"+ vMessage);
}catch(RemoteException e){
	 e.printStackTrace();
}

	}

	public static void main(String[] srgs){
		
		try{
		   SCHClientImpl vClient = new SCHClientImpl();
		   }catch(Exception e){
			   
		   }
	}
}