package util;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import config.SysConfig;


import rawobjectsPriv.EnumSysState;

public class StateControl {

	
	private static rawobjectsPriv.EnumSysState mAppState = rawobjectsPriv.EnumSysState.NOT_OPERATIONAL;
	
	/**
	 * Notify Control Centre via RMI of a change in state
	 * 
	 * @param pState The new state of the module
	 * @return If the update was successfully communicated to CC.
	 */
	private static boolean notifyCC(EnumSysState pState){
		
		try {
			if (!SysConfig.getIPregistrationCompleted()) {
				System.out.println("StateControl: Unable to Update CC - IP Registration NOT Completed.");
				return false;
			}
				
			
			rmi_interfaces.CCServer cc = new rmi_engine.SCHClientImplCC().getSrvObject();
			
			cc.updateStatus(SysConfig.getMyModuleName().getModAbbrev(), pState.getStateNum());
			
			return true;
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Obtains the current system state for the module
	 * 
	 * @return System State for the current module
	 */
	public static EnumSysState getCurrentState(){
		return mAppState;
	}
	
	/**
	 * Obtains the current system state for the module, specifically used for RMI calls from CC
	 * 
	 * @return The current numerical value for the state of the system
	 */
	public static int getCurrentStateRMI(){
		return mAppState.getStateNum();
	}
	
	
	/**
	 * Update the state of the module.
	 * 
	 * @param pStateNew The modules' new state
	 * @param pNotifyCC Should Control Centre be notified of the change
	 * @return If the update was made successfully (and if notifying CC, if that was a success) 
	 */
	public static boolean setState(EnumSysState pStateNew, boolean pNotifyCC){
		mAppState = pStateNew;
		
		if (pNotifyCC)
			return notifyCC(pStateNew);
		else
			return true;
	}
	
	
}
