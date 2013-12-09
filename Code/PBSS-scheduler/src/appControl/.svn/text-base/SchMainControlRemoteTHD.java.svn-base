package appControl;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rawobjectsPriv.NetIPInfo;
import rmi_engine.SCHRMIReqObj;
import rmi_engine.SCHServerImpl;
import rmi_interfaces.SCHServer;

import java.util.PriorityQueue;

import config.SysConfig;

import util.Debug;
import util.SysLogTypeEnum;

/**
 * Handles Control Requests from a Remote Location, often via RMI
 * @author Ben
 *
 */
public class SchMainControlRemoteTHD implements Runnable {

	boolean bDEBUG_ShowPingMsg=true;
	public void debugPingMsg_Off(){ Debug.writeDebugMsg("schMainControlRemote", "debugPingMsg_Off", "Ping Msg OFF"); bDEBUG_ShowPingMsg = false;}
	public void debugPingMsg_On(){ Debug.writeDebugMsg("schMainControlRemote", "debugPingMsg_On", "Ping Msg ON"); bDEBUG_ShowPingMsg = true;}
	
	boolean bRemoteControlActive;
	SchMainControlTHD controlMainREF; //reference to the Main Control Thread
	
	Registry mLocalRegistry;
	SCHServer vSchedulerRMIServer; //The RMI server
	
	private static PriorityQueue<SCHRMIReqObj> mRMIRequestQueue = new PriorityQueue<SCHRMIReqObj>();
		
	public SchMainControlRemoteTHD(SchMainControlTHD pControlMain){
		this.controlMainREF = pControlMain;
		this.startRemoteControl();
	}
	
	public void startRemoteControl(){
		this.bRemoteControlActive=true;
	}
	
	public void stopRemoteControl(){
		this.bRemoteControlActive=false;
	}
	
	@Override
	public void run() {
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "120", "App Ops");
		Debug.writeDebugMsg("schMainControlRemote", "run", "Remote Control Thread Started");
				
		while (bRemoteControlActive){
			try {
				if (bDEBUG_ShowPingMsg) Debug.writeDebugMsg("schMainControlRemote", "run", "PING: OK");
				this.checkRMIQueue();
				Thread.sleep(800);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		Debug.writeDebugMsg("schMainControlRemote", "run", "Remote Control Thread Ended");
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "121", "App Ops");
	}
	
	private boolean checkRMIQueue(){
		if (mRMIRequestQueue.size()==0){
			return false;
		}
		else {
			this.handleRMIRequest(mRMIRequestQueue.poll());
			return true;
		}
	}
	
	private boolean handleRMIRequest(SCHRMIReqObj req){
		if (req==null)
			return false;
		else
		{
			switch (req.getRequestCmd()){
			case SCH_PRESTART:
				this.controlMainREF.funcPBSSPreStart(true, "RMI", Integer.parseInt(req.getRequestData("SIM_RUN_ID",-10).toString()));
				
				if (req.isResponseRequired()){
					//TODO 3LOW? [RMI_RESP]: Send an RMI response to CC (is this actually needed, other than just a true response?)
				}
				
				return true;
			case SCH_START:
				this.controlMainREF.funcPBSSStart(true);
				
				if (req.isResponseRequired()){
					//TODO 3LOW? [RMI_RESP]: Send an RMI response to CC (is this actually needed, other than just a true response?)
				}
				
				return true;
			case SCH_STOP:
				this.controlMainREF.funcPBSSStop(true);
				
				if (req.isResponseRequired()){
					//TODO 3LOW? [RMI_RESP]: Send an RMI response to CC (is this actually needed, other than just a true response?)
				}
				
				return true;
			case UNKNOWN:
				return false;
			
			default:
					return false;
			}
		}
	}
	
	/******************* RMI SERVER TASKS *********************/
	
	public String doRMIServerBind(boolean pDoRebind){
			NetIPInfo ip = SysConfig.getIPInfo(SysConfig.getMyModuleName());
			
			System.out.println("Scheduler Server Listening on Port: " + ip.getPort());

			Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "100", "RMI", 
					"REG_HOST=" + ip.getIPAddress() + "|" +
					"REG_PORT=" + ip.getPort() + "|" + 
					"OBJ_NAME=" + ip.getRMIName());
			try {
				this.mLocalRegistry = LocateRegistry.createRegistry(ip.getPort());
				this.vSchedulerRMIServer = new SCHServerImpl(this.mLocalRegistry,ip.getRMIName(), false);
			} catch (RemoteException e) {
				Debug.writeLogEntry(SysLogTypeEnum.ERROR, "102", "RMI", e);
				return "ERROR: " + e.getMessage() + " [RMI_BIND_E1]";
			} catch (AlreadyBoundException e) {
				Debug.writeLogEntry(SysLogTypeEnum.ERROR, "101", "RMI", e);
				return "ERROR: Already Bound [RMI_BIND_E2]";
			}
		
			return "OK" + " [RMI_BIND_N1]";
	}
	
	public String doRMIServerUnBind(){
		NetIPInfo ip = SysConfig.getIPInfo(SysConfig.getMyModuleName());
		
		if (mLocalRegistry==null){
			return "OK" + " [RMI_UNBIND_W1]"; 
		}
				
		try {
			mLocalRegistry.unbind(ip.getRMIName());
		} catch (AccessException e) {
			return "ERROR: " + e.getMessage() + " [RMI_UNBIND_E1]";
		} catch (RemoteException e) {
			return "ERROR: " + e.getMessage() + " [RMI_UNBIND_E2]";
		} catch (NotBoundException e) {
			return "ERROR: " + e.getMessage() + " [RMI_UNBIND_E3]";
		}
		mLocalRegistry=null;
		return "OK" + " [RMI_UNBIND_N1]";
	}
	
	public void doRMIServerKill(){
		this.doRMIServerUnBind();
		
		try {
			//Need to unexport the object from RMI, to allow for a clean EXIT.
			UnicastRemoteObject.unexportObject(this.vSchedulerRMIServer, true);
		} catch (NoSuchObjectException e) {
			Debug.writeDebugMsg("schMainControlRemote", "doRMIServerKill", "RMI Object Does Not Exist"); 
		} catch (Exception e){
			e.printStackTrace();
		}
		
		vSchedulerRMIServer = null;
		mLocalRegistry=null;
	}
	
	/******************* RMI QUEUE TASKS *********************/
	
	public static boolean queueAddRMITask(SCHRMIReqObj pNewQueueRequest){
		Debug.writeDebugMsg("schMainControlRemote", "queueAddRMITask", "RMI Task has been queued, task = " + pNewQueueRequest.getRequestCmd());
		mRMIRequestQueue.add(pNewQueueRequest);
		
		return true;
	}
		
	public static int queueCount(){
		return mRMIRequestQueue.size();
	}
	
}
