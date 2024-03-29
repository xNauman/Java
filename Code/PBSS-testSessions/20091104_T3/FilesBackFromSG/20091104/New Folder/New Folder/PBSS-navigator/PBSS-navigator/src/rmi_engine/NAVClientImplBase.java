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
public class NAVClientImplBase<InterfaceObject> {
	private Registry mRemoteRegistry;
	private String mRegistryAddress = "";
	private int mRegistryPort = 0;
	private String mRegistryName = "";

	public NAVClientImplBase(String pRegistryHost, int pRegistryPort, String pRegName) 
	throws RemoteException, NotBoundException {
		super();

		this.mRegistryAddress=pRegistryHost;
		this.mRegistryPort=pRegistryPort;
		this.mRegistryName=pRegName;
	}

	@SuppressWarnings("unchecked")
	protected InterfaceObject getServerObject()
	throws RemoteException, NotBoundException{

		
		InterfaceObject objServerObject;

		
		//TODO 1URG broken line here, but unsure why? 
		mRemoteRegistry = LocateRegistry.getRegistry(this.mRegistryAddress,this.mRegistryPort);
		//IFOK, REM mRemoteRegistry = LocateRegistry.getRegistry("127.0.0.1",25006);
		
		// Connect and obtain object from Remote Server
		try {
			objServerObject = (InterfaceObject)mRemoteRegistry.lookup(this.mRegistryName);
		}
		catch (NotBoundException ex){
			ex.printStackTrace();
			return null;
		}

		
		return objServerObject;
	}

}
