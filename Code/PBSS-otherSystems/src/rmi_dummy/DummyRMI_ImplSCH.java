package rmi_dummy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi_engine.SCHClientImplBase;
import rmi_interfaces.SCHServer;

//!!!!!!!!!!!!!!!!!!!!!! IMPORTANT !!!!!!!!!!!!!!!!!!!!!!
/*
 * This uses rmi_engine and rmi_interface classes directly.
 * When actually implemented, it will not have such
 * access.	
 * It is done since these are dummy/test classes, and of
 * importance here is speed.
*/
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

/**
 * Used to obtain the remote information for scheduler.
 * 
 */
public class DummyRMI_ImplSCH extends SCHClientImplBase<SCHServer>{

	protected SCHServer mSrvObject;

	public DummyRMI_ImplSCH()
	throws RemoteException, NotBoundException{

		super(DummyRMI_IPList.getIP_SCH(), 
				25005, 
				"SCH");
				
		this.mSrvObject = this.getServerObject();
	}

	public DummyRMI_ImplSCH(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		this.mSrvObject = this.getServerObject();
	}

	public SCHServer getSrvObject(){
		return mSrvObject;		
	}

}
