/**
 * 
 */
package pbss.rmi.nav;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//import RMI.SCHServer;

/**
 * @author Raja Noman
 *
 */
public class NAVMethodsCaller {
 
	Registry mRemoteRegistry;
	//protected  DBServer mDBServerObject;
	private String mDBServerAddress = "127.0.0.1";
	private String mDBServerPort = "1099";

 /**
  *  DB Methods to be called.
  */
	public void DBMethodHandler(){
		//TODO DB method calls
		//methods yet to be received from DB side.
		
		try{
			
	
			//finding the registry.
			mRemoteRegistry = LocateRegistry.getRegistry(
					mDBServerAddress,
					(new Integer(mDBServerPort).intValue()));
			
			System.out.println("Creating Remote-Object");
			/**
			 * get the object from the remote-registry
			 */		

	//DBServerObject = (DBServer)mRemoteRegistry.lookup("SCHServer");
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
		
}
