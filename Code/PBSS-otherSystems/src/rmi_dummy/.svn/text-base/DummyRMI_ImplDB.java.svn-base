package rmi_dummy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi_engine.SCHClientImplBase;
import rmi_interfaces.DBServer;

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
public class DummyRMI_ImplDB extends SCHClientImplBase<DBServer>{

	protected DBServer mSrvObject;

	public DummyRMI_ImplDB()
	throws RemoteException, NotBoundException{

		super(DummyRMI_IPList.getIP_DB(), 
				25001, 
				"DB");
				
		this.mSrvObject = this.getServerObject();
	}

	public DummyRMI_ImplDB(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		this.mSrvObject = this.getServerObject();
	}

	public DBServer getSrvObject(){
		return mSrvObject;		
	}

}
