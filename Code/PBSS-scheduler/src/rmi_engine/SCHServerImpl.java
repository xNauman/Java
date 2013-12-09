package rmi_engine;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import config.SysConfig;


import rawobjectsPriv.EnumSysState;
import rmi_interfaces.SCHServer;
import route.SchRouteMoveReqResp;
import route.SchRouteResp;

/**
 * Implementation of RMI Interfaces for Scheduler
 * 
 * @author Ben
 *
 */

public class SCHServerImpl 
	extends UnicastRemoteObject 
	implements SCHServer {

	private static final long serialVersionUID = 6383850891903091210L;

	//------------------- RMI Operation/Configuration METHODS ---------------
	
	
	public SCHServerImpl(Registry pLocalRegistry, String vName, boolean pDoRebind)throws RemoteException, AlreadyBoundException{
		super();
		
		System.out.println(getMyDisplayName() + " - Binding to Registry, using remote reference name of: " + vName);
				
		if (pDoRebind)
			pLocalRegistry.rebind(vName, this);
		else
			pLocalRegistry.bind(vName, this);
	};
		
	private String getMyDisplayName(){
		return "SCH";
	}
	
	private String getMyDisplayName(String sRespType){
		return getMyDisplayName() + "." + sRespType;
	}
		
	//------------------- IMPLEMENTATION OF REMOTE METHODS ------------------

	public boolean startUpSCH(int pSimulatorRunID) 
		throws java.rmi.RemoteException{
		
		if (util.StateControl.getCurrentState()==EnumSysState.NOT_OPERATIONAL){
			SCHRMIReqObj req = new SCHRMIReqObj(SCHRMICmdType.SCH_PRESTART, false);
			req.setRequestData("SIM_RUN_ID", Integer.toString(pSimulatorRunID));
			appControl.SchMainControlRemoteTHD.queueAddRMITask(req);
			
			return true;
		}
		else {
			return false; 
		}
	}
	
	@Override
	public boolean startSCH() 
		throws RemoteException {
		
		System.out.println("CURR STATE =" + util.StateControl.getCurrentState().getState());
		
		if (util.StateControl.getCurrentState()==EnumSysState.READY_TO_START){
			SCHRMIReqObj req = new SCHRMIReqObj(SCHRMICmdType.SCH_START, false);
			appControl.SchMainControlRemoteTHD.queueAddRMITask(req);
			
			return true;
		}
		else {
			return false; 
		}
	}

	@Override
	public boolean stopSCH() 
		throws RemoteException {
		
		if (util.StateControl.getCurrentState()!=EnumSysState.IN_OPERATION){
			SCHRMIReqObj req = new SCHRMIReqObj(SCHRMICmdType.SCH_STOP, false);
			appControl.SchMainControlRemoteTHD.queueAddRMITask(req);
			
			return true;
		}
		else {
			return false; 
		}
	}
	
	
	//------------------- Common Implementation ------------------
	
	public String debugEcho(String pFromModule, String pMessageToEcho) throws RemoteException {
		//TODO 8FORNAV: When echo requested recd via RMI, echo to local console
		System.out.println(util.Formatter.formatDate() + " ECHO REQUEST - From: " + pFromModule.toUpperCase() + "=" + pMessageToEcho);
		return getMyDisplayName("ECHO:") + pMessageToEcho;
	}
	
	public Integer SCHState() throws java.rmi.RemoteException{
		return util.StateControl.getCurrentStateRMI();
	}


	public long SimTimeShare() throws java.rmi.RemoteException{
		//TODO 1HIGH [TIME]: Sharing Time
		//return util.General.getSimulatorTime();
		return util.General.getSimulatorTime_Actual();
	}
	
	@Override
	public boolean responseFromNav(UUID pRequestID, boolean moveAccept)
			throws RemoteException {
		
		SysConfig.perfStat_NAVRespTot_Increment();
		
		if (moveAccept)
			SysConfig.perfStat_NAVRespGranted_Increment();
		else
			SysConfig.perfStat_NAVRespDenied_Increment();
		
		SchRouteMoveReqResp resp = new SchRouteMoveReqResp(pRequestID, moveAccept);
		SchRouteResp.addResponseFromNav(resp);
		
		//System.out.println("NAV HAS RETURNED RESPONSE to REQUESTID = " + resp.getRequestUUID() + " of " + resp.getResponse());
		
		return true;
		
	}
	
}
