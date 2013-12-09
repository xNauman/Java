package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import config.SysConfig;


import rawobjectsPriv.EnumModName;
import rmi_interfaces.TKRServer;

/**
 * Creates an object that will provide an RMI connection to TRACKER
 * @author Ben
 *
 */

public class SCHClientImplTKR extends SCHClientImplBase<TKRServer>{

	protected TKRServer mSrvObject;

	public SCHClientImplTKR()
	throws RemoteException, NotBoundException{

		super(SysConfig.getIPInfo(EnumModName.TRACKER).getIPAddress(), 
				SysConfig.getIPInfo(EnumModName.TRACKER).getPort(), 
				SysConfig.getIPInfo(EnumModName.TRACKER).getRMIName());
				
		this.mSrvObject = this.getServerObject();
	}

	public SCHClientImplTKR(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		this.mSrvObject = this.getServerObject();
	}

	public TKRServer getSrvObject(){
		return mSrvObject;		
	}
}