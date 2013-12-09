package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Provides the parent RMI Implementation
 * @author Original by Raja, Parent Relationship by Ben
 * @param <InterfaceObject>
 *
 */
public class SCHClientImplBase<InterfaceObject> {
	private Registry mRemoteRegistry;
	private String mRegistryAddress = "";
	private int mRegistryPort = 0;
	private String mRegistryName = "";

	public SCHClientImplBase(String pRegistryHost, int pRegistryPort, String pRegName) 
	throws RemoteException, NotBoundException {
		super();

		this.mRegistryAddress=pRegistryHost;
		this.mRegistryPort=pRegistryPort;
		this.mRegistryName=pRegName;
	}

	@SuppressWarnings("unchecked")
	protected InterfaceObject getServerObject()
	throws RemoteException, NotBoundException{

		//System.out.println("at c1:SCHCLientImplBase");
		
		InterfaceObject objServerObject;

		//System.out.println("at c3:SCHCLientImplBase");
		
		
		//System.out.println("USING ADDR/PORT:" + this.mRegistryAddress + "/" + this.mRegistryPort + " @ " + this.mRegistryName);
		mRemoteRegistry = LocateRegistry.getRegistry(this.mRegistryAddress,this.mRegistryPort);
		//IFOK, REM mRemoteRegistry = LocateRegistry.getRegistry("127.0.0.1",25006);
		//System.out.println("at c4.1:SCHCLientImplBase");
		
		// Connect and obtain object from Remote Server
		try {
			//System.out.println("at c4.2:SCHCLientImplBase");
			objServerObject = (InterfaceObject)mRemoteRegistry.lookup(this.mRegistryName);
		}
		catch (NotBoundException ex){
			ex.printStackTrace();
			return null;
		}

		//System.out.println("at c2:SCHCLientImplBase");
		
		return objServerObject;
	}

}
