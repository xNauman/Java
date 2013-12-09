package RMI;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;


public class SCHServerImpl 
			extends UnicastRemoteObject
				implements SCHServer,Serializable {
	/**
	 * 
	 */
	private String mStrToSend;
	private int mPort;
	private String mHost;
	private Registry mLocalRegistry;
	private Scanner mCInput;
	
	public SCHServerImpl()throws RemoteException{
		super();
		mStrToSend = "";
		
		String vName = "SCHServer";
		
		this.mCInput = new Scanner(System.in);
		
		/**
		 *getting the local IP-Address.
		 */
		
		 try{
			 this.mHost = (InetAddress.getLocalHost()).toString();
		 }catch(Exception e){
			 throw new RemoteException("Can not get inet address");
		 }
		 mPort = 1234;
		 System.out.println("opening the registry on Host:"+mHost+"port:"+mPort);
/**
* Creating the registry and binding the name.
*/
		
		try{
		mLocalRegistry =  LocateRegistry.createRegistry(mPort);
		mLocalRegistry.rebind(vName, this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		mLocalRegistry.rebind(vName, this);
		
	}
/**
 * @param String from the client
 * <br> method get the input from the console <br> and sends to client.
 */
	public String sendToNAV(String mMsg) throws RemoteException {
		
		
	System.out.println("From Client>"+mMsg);
	System.out.println("type Message:");
		return this.mCInput.nextLine();
				
	}
	
	public static void main(String[] args){
		
	/*if(System.getSecurityManager() == null)
		System.setSecurityManager(new RMISecurityManager());
		*/
		try{
			
			SCHServer vSchedulerRMIServer = new SCHServerImpl();
			
		}catch(Exception e){
			System.out.println("Exception invoked : SCHServerImpl Main \n");
			e.printStackTrace();
		}		
	}
}
