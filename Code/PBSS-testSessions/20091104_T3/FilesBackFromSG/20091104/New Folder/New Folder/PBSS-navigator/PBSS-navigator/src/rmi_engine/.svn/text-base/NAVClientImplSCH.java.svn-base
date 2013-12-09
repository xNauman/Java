package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import config.SysConfig;

import rawobjectsPriv.EnumModName;
import rmi_interfaces.SCHServer;

/**
 * Creates an object that will provide an RMI connection to DATABASE
 * @author Ben
 *
 */

public class NAVClientImplSCH extends NAVClientImplBase<SCHServer>{

	protected SCHServer mSrvObject;

	public NAVClientImplSCH()
	throws RemoteException, NotBoundException{

		super(SysConfig.getIPInfo(EnumModName.SCHEDULER).getIPAddress(), 
				SysConfig.getIPInfo(EnumModName.SCHEDULER).getPort(), 
				SysConfig.getIPInfo(EnumModName.SCHEDULER).getRMIName());
				
		this.mSrvObject = this.getServerObject();
	}

	public NAVClientImplSCH(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		this.mSrvObject = this.getServerObject();
	}

	public SCHServer getSrvObject(){
		return mSrvObject;		
	}
}
