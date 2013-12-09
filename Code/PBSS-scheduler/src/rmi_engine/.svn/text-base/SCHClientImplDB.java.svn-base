package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import config.SysConfig;

import rawobjectsPriv.EnumModName;
import rmi_interfaces.DBServer;

/**
 * Creates an object that will provide an RMI connection to DATABASE
 * @author Ben
 *
 */

public class SCHClientImplDB extends SCHClientImplBase<DBServer>{

	protected DBServer mSrvObject;

	public SCHClientImplDB()
	throws RemoteException, NotBoundException{

		super(SysConfig.getIPInfo(EnumModName.DB).getIPAddress(), 
				SysConfig.getIPInfo(EnumModName.DB).getPort(), 
				SysConfig.getIPInfo(EnumModName.DB).getRMIName());
				
		this.mSrvObject = this.getServerObject();
	}

	public SCHClientImplDB(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		this.mSrvObject = this.getServerObject();
	}

	public DBServer getSrvObject(){
		return mSrvObject;		
	}
}
