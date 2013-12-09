package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import config.SysConfig;
import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;
import rmi_interfaces.CCServer;

/**
 * Creates an object that will provide an RMI connection to DATABASE
 * @author Ben
 *
 */

public class NAVClientImplCC extends NAVClientImplBase<CCServer>{

	protected CCServer mSrvObject;

	public NAVClientImplCC()
	throws RemoteException, NotBoundException{

		
		super(SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE).getIPAddress(), 
				SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE).getPort(), 
				SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE).getRMIName());
		
		
		this.mSrvObject = this.getServerObject();
		
	}

	public NAVClientImplCC(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		
		this.mSrvObject = this.getServerObject();
	}
	
	public NAVClientImplCC(NetIPInfo pNetInfo)
	throws RemoteException, NotBoundException{
		
		super(pNetInfo.getIPAddress(), pNetInfo.getPort(), pNetInfo.getRMIName());
		
		
		this.mSrvObject = this.getServerObject();
	}

	public CCServer getSrvObject(){
		return mSrvObject;		
	}
}
