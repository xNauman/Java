package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import config.SysConfig;


import rawobjectsPriv.EnumModName;
import rmi_interfaces.NAVServer;

/**
 * Creates an object that will provide an RMI connection to NAVIGATOR
 * @author Ben
 *
 */

public class SCHClientImplNAV extends SCHClientImplBase<NAVServer>{

	protected NAVServer mSrvObject;
	
	public SCHClientImplNAV()
	throws RemoteException, NotBoundException{

		super(SysConfig.getIPInfo(EnumModName.NAVIGATOR).getIPAddress(), 
				SysConfig.getIPInfo(EnumModName.NAVIGATOR).getPort(), 
				SysConfig.getIPInfo(EnumModName.NAVIGATOR).getRMIName());
				
		this.mSrvObject = this.getServerObject();
	}
	
	public SCHClientImplNAV(String pServerIP, int pServerPort, String pRegName)
		throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		this.mSrvObject = this.getServerObject();
	}
	
	public NAVServer getSrvObject(){
		return mSrvObject;		
	}
}
