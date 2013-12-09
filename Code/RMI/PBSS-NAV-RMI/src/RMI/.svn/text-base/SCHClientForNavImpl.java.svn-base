/**
 * 
 */
package RMI;


import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

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
                vMessage  =  mServerObject.sendToNAV();
		System.out.println(	"message fromt server:"+ vMessage);
}catch(RemoteException e){
	 e.printStackTrace();
}
	    
	    /**
	     * call the remote method
	     */	    
	    

	}

	public static void main(String[] srgs){
		System.setSecurityManager(new RMISecurityManager());
		try{
		   SCHClientForNavImpl vClient = new SCHClientForNavImpl();
		   }catch(Exception e){
			   
		   }
	}
}
