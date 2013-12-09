package rmi_engine;
/**
 * Server RMI implementation for navigator
 * 
 * This code provides access to the interfaces via RMI and contains the methods to be called
 * 
 * @author Original code: Raja, modifications: David 
 * 
 */

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;



import rawobjects.bus;
import rmi_interfaces.SCHServer;



public class SCHServerImpl 
			extends UnicastRemoteObject
				implements SCHServer, Serializable {
	/**
	 * 
	 */
	private String mStr1;
	private int mPort;
	private String mHost;
	private Registry mLocalRegistry;
	
	
	//heart of the RMI server
	public SCHServerImpl()throws RemoteException{
		super();
		mStr1 = "ThisI5Server";
		
		String vName = "SCHServer";
		
/**
* getting the local IP-Address.
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
		 //security manager disabled for now as its too messy to use and we 
		 //don't require a secure implementation
		 //System.setSecurityManager(new RMISecurityManager());
		try{
		mLocalRegistry =  LocateRegistry.createRegistry(mPort);
		mLocalRegistry.rebind(vName, this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		mLocalRegistry.rebind(vName, this);
		
	};


	public static void main(String[] args){
		
//		System.setSecurityManager(new RMISecurityManager());
		try{
			
			SCHServer vSchedulerRMIServer = new SCHServerImpl();
			
		}catch(Exception e){
			System.out.println("Exception invoked :SCHServerImpl Main \n");
			e.printStackTrace();
		}		
	}

	
	/*
	 * This is where the Methods that RMI server makes available are. 
	 * 
	 */
	
	//test method
	public String sendToNAV() throws RemoteException {
		// todo Auto-generated method stub
		return mStr1;		
	}
		
	
		
}
