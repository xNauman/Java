package main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;

import config.SysConfig;

public class PreStartTHD implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Starting PreStart THD");
		
		//update state to say we are in prestart
		utils.StateControl.setState(rawobjectsPriv.EnumSysState.PRE_START_IN_PROGRESS, true);
		
		//start the prestart
		boolean vPrestart = executePrestart();
		
		
		//if prestart went ok update the state to completed
		if (vPrestart == true)
		{
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Prestart completed OK!");
			
			utils.StateControl.setState(rawobjectsPriv.EnumSysState.READY_TO_START, true);
		}
		//otherwise there was an issue 
		else
		{
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("------ Prestart failed!! --------");
			utils.StateControl.setState(rawobjectsPriv.EnumSysState.NOT_OPERATIONAL, true);
		}
			
		
	
	}
	
	
	public boolean executePrestart()
	{
		
		if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Going to get simulator run value");
		if(getSystemSimulatorRunValue() == false)
		{
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("FAILED!!! : Going to get simulator run value");
			return false;
		}
		
		if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Going to get the traffic lights");
		if(getTrafficLights() == false)
		{
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("FAILED!!! : Going to get the traffic lights");
			return false;
		}
		
		if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Going to get the buses ");
		if(getBuses() == false)
		{
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("FAILED!!! : Going to get the buses");
			return false;
		}
		
				
		if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Ending PreStart THD");
		
		return true;
		
	}
	
	/**
	 * Method to find out the SystemSimulatorRunValue over RMI from the DB module
	 * 
	 * @return returns true if it could get the sim ID
	 */
	private boolean getSystemSimulatorRunValue()
	{
		
		//make RMI call to find out simulator id
		try
		{
			if (!SysConfig.getIPregistrationCompleted()) {
				if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("StateControl: Unable to Update CC - IP Registration NOT Completed.: from getSystemSumulatorRunValue");
				return false;
			}
			
			rmi_interfaces.DBServer db = new rmi_engine.NAVClientImplDB().getSrvObject();
			
			SysConfig.setSimRunID("RMI-TO-DB", db.getSYS_SIMULATOR_RUN().getSimRunID());
			
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Sim ID obtained, ID is: "+SysConfig.getSimRunID());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
		
	}
	
	
	/**
	 * Get the vector of traffic lights from the DB module over RMI and then 
	 * put that into the lights store in the datastore
	 * 
	 * @return boolean if this was possible
	 */
	private boolean getTrafficLights()
	{
		//make RMI call to find out simulator id
		try
		{
			if (!SysConfig.getIPregistrationCompleted()) {
				if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("StateControl: Unable to Update CC - IP Registration NOT Completed.: from getTrafficLights");
				return false;
			}
			
			rmi_interfaces.DBServer db = new rmi_engine.NAVClientImplDB().getSrvObject();
			
			//TODO: Apply Lock?
			datastore.TrafficLightStore.vTrafficLightStore = db.getListOfTrafficLights(SysConfig.getSimRunID());
			
			
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Got List of lights!)");
			
			
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
		
		
	}
	

	/**
	 * 
	 * Get list of buses from the DB over RMI and then put that into
	 * the buses datastore 
	 * 
	 * @return boolean if it was possible to get the buses
	 */
	private boolean getBuses()
	{
		//make RMI call to find out simulator id
		try
		{
			if (!SysConfig.getIPregistrationCompleted()) {
				if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("StateControl: Unable to Update CC - IP Registration NOT Completed.: from getBuses");
				return false;
			}
			
			rmi_interfaces.DBServer db = new rmi_engine.NAVClientImplDB().getSrvObject();
			
			//TODO: Apply Lock?
			datastore.BusMovementStore.vBusPositionVector = db.getBusesFromDB();
			
			
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Got List of Buses!)");
			
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("bus vector size; "+datastore.BusMovementStore.vBusPositionVector.size());
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
		
		
	}
	
	
	
}
