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

public class SCHClientImplCC extends SCHClientImplBase<CCServer>{

	protected CCServer mSrvObject;

	public SCHClientImplCC()
	throws RemoteException, NotBoundException{

		
		super(SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE).getIPAddress(), 
				SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE).getPort(), 
				SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE).getRMIName());
		
		//System.out.println("at b3");
		
		this.mSrvObject = this.getServerObject();
		
		//System.out.println("at b4");
	}

	public SCHClientImplCC(String pServerIP, int pServerPort, String pRegName)
	throws RemoteException, NotBoundException{
		
		super(pServerIP, pServerPort, pRegName);
		
		//System.out.println("at b1");
		
		this.mSrvObject = this.getServerObject();
	}
	
	public SCHClientImplCC(NetIPInfo pNetInfo)
	throws RemoteException, NotBoundException{
		
		super(pNetInfo.getIPAddress(), pNetInfo.getPort(), pNetInfo.getRMIName());
		
		//System.out.println("at b1");
		
		this.mSrvObject = this.getServerObject();
	}

	public CCServer getSrvObject(){
		//System.out.println("at b2");
		return mSrvObject;		
	}
}
