package rmi;

/**
 * Creates the Central RMI Registry - shared by all machines
 */
import java.net.SocketPermission;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIRegistry {

	@SuppressWarnings("unused")
	private static Registry mLocalRegistry;
	
	public static void main(String args[]){
		int mPort = 25000; //The port the RMI registry will operate on
		
		System.out.println("Opening the registry on port:"+mPort);

		new SocketPermission("localhost", "accept, connect, listen");

		/**
		 * Create the Registry 
		 */
		try{
			mLocalRegistry =  LocateRegistry.createRegistry(mPort);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		boolean bStop = false;
		
		while (!bStop){
			try {
				Thread.sleep(10000); //Keeps the registry operational without causing to much CPU usage
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
