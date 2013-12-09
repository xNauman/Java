package rmi_engine;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import main.PreStartTHD;
import main.RMIControlTHD;
import navigation.NavigationInstance;

import rawobjects.NavRequest;
import rmi_interfaces.NAVServer;
import utils.Clock;


/**
 * Implementation of RMI Interfaces for Scheduler
 * 
 * @author Ben
 *
 */

public class NAVServerImpl 
	extends UnicastRemoteObject 
	implements NAVServer {

	private static final long serialVersionUID = 6383850891903091210L;

	//------------------- RMI Operation/Configuration METHODS ---------------
	
	
	public NAVServerImpl(Registry pLocalRegistry, String vName, boolean pDoRebind)throws RemoteException, AlreadyBoundException{
		super();
		
		System.out.println(config.SysConfig.getMyModuleName() + " - Binding to Registry, using remote reference name of: " + vName);
				
		if (pDoRebind)
			pLocalRegistry.rebind(vName, this);
		else
			pLocalRegistry.bind(vName, this);
	};
		
		
	//------------------- IMPLEMENTATION OF REMOTE METHODS ------------------
	




	public Integer SCHState() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long SimTimeShare() throws RemoteException {
		return Clock.getSimTime();
	}

	@Override
	public String debugEcho(String pFromModule, String pMessageToEcho)
			throws RemoteException {
		return "Request made to: "+config.SysConfig.getModuleName()+ " From: "+pFromModule+" ECHO:"+pMessageToEcho;
	}


	/**
	 * Request movement (this gets called by scheduler) 
	 * 
	 * 
	 * @param NavRequest - NavRequest object 
	 * @param UUID - unique ID number
	 * @return - boolean saying if the request was received correctly and we are in an operational state, otherwise false
	 */
	@Override
	public boolean MovementRequest(NavRequest pNavigationRequestObject,
			UUID pMoveRequestUUID) throws RemoteException {
		
		rawobjectsPriv.EnumSysState vCurrentState = utils.StateControl.getCurrentState();
		
		//if we aren't in an operational state or a ready to start state, throw false
		if(vCurrentState != rawobjectsPriv.EnumSysState.IN_OPERATION && vCurrentState != rawobjectsPriv.EnumSysState.READY_TO_START )
		{
			System.out.println("Nav request received, but not in an operational state - returning false");
			return false;
		}
		else
		{
			//lets create a new thread to put the movement request into
			Thread runThread; 
			runThread = new Thread(new NavigationInstance(pNavigationRequestObject, pMoveRequestUUID));

			System.out.println("Movement Request Thread fired! UUID:"+pMoveRequestUUID);
			//start the thread up
			runThread.start();
			
			//do quick check to make sure we are in state 3
			if(vCurrentState != rawobjectsPriv.EnumSysState.IN_OPERATION)
			{
				System.out.println("Set state to Operational as this was the first nav request");
				
				utils.StateControl.setState(rawobjectsPriv.EnumSysState.IN_OPERATION, true);
			}
			
			System.out.println("Exiting the Movement Request");
			
			return true;

		}
		


		
	}

	@Override
	public Integer Navstate() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to get nav to enter prestart .
	 * 
	 * @return - returns false if Nav isn't in a mode where it can be started otherwise returns true
	 */
	@Override
	public boolean startupNAV() throws RemoteException {
		rawobjectsPriv.EnumSysState vState = utils.StateControl.getCurrentState(); 
		
		if(vState != rawobjectsPriv.EnumSysState.NOT_OPERATIONAL)
		{
			return false;
		}
		//start threads and return true
		else
		{
			//start prestart thread and return
			Thread runThread; 
			runThread = new Thread(new PreStartTHD());
			runThread.start();
			
			return true;
		}
		
		
		
	}

	@Override
	public boolean stopNAV() throws RemoteException {
		rawobjectsPriv.EnumSysState vState = utils.StateControl.getCurrentState(); 
		if(vState == rawobjectsPriv.EnumSysState.NOT_OPERATIONAL || 
				vState == rawobjectsPriv.EnumSysState.PRE_START_IN_PROGRESS )
		{
			return false;
		}
		
		
		//change state
		utils.StateControl.setState(rawobjectsPriv.EnumSysState.NOT_OPERATIONAL, true);
		
		//TODO: stop the navigation threads
		
		//clear the bus vector
		datastore.BusMovementStore.vBusPositionVector.clear();
		
		//clear traffic lights
		datastore.TrafficLightStore.vTrafficLightStore.clear();
		

		
		
		return true;
	}
	
	


	
}
