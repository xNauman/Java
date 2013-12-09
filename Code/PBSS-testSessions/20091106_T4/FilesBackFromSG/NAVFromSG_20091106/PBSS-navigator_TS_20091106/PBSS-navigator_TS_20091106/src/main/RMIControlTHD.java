package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;

import config.SysConfig;

import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;
import rmi_engine.BCastTHD;
import rmi_engine.GetIPsTHD;
import rmi_engine.NAVServerImpl;

public class RMIControlTHD implements Runnable {
	
	private Thread thrdControlBCast;
	private GetIPsTHD controlBCast = new GetIPsTHD(); 
	
	public static void RMIBoardCastControl()
	{


	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
			
			
			//SPECIAL: note that this will require main to call another function, as this thread won't auto start to boardcast in final version, does currently (DT NOTE ONLY)
			//start the boardcast
			//ipRegistrationStart();
			 
			//setup data from Bcast
			setupDataForBCastStart();
			
			//call thread to handle bcasting
			Thread vBCastThread; 
			vBCastThread = new Thread(new BCastTHD());
			vBCastThread.start();
			
			//start RMI server
			if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Attempting to start RMI server");
			try {
				Registry vRegistry;
				vRegistry = LocateRegistry.createRegistry(SysConfig.getIPInfo(SysConfig.getMyModuleName()).getPort());
				NAVServerImpl vNavServer = new NAVServerImpl(vRegistry, SysConfig.getModuleName(), false);
				if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Started RMI server");
				
			} catch (RemoteException e) {

				e.printStackTrace();
			} catch (AlreadyBoundException e) {

				e.printStackTrace();
			}
	
			//call thread to start checking for IPs
			Thread vIPRego; 
			vIPRego = new Thread(new GetIPsTHD());
			vIPRego.start();
			
			
			//SPECIAL: need to create a ThreadClientRequest thingy to create a new thread to handle each request, otherwise control will lock
			// and no RMI requests will be possible. 
			

			//SPECIAL: we create a new navigation request thread on each request ben makes to me
			System.out.println("-----------------------------");
			System.out.println("NavRMIControlTHD ended");
			System.out.println("-----------------------------");
			
	}
	
	
	/**
	 * Function sets default IP information, just so we don't end up with null values in our code
	 */
	private void setupDataForBCastStart()
	{
		//this code adds the default IP and port, boardcast will give IP but no port, this just means we don't have empty data types incase
		//something horrible happens
		//Add self info
		SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo("127.0.0.1",4445, SysConfig.getMyModuleName().getModAbbrev(),"DEFAULT"));

		//Add Default IP Info
		SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, new NetIPInfo("127.0.0.1",4445,EnumModName.CONTROL_CENTRE.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.DB, new NetIPInfo("127.0.0.1",4445, EnumModName.DB.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.SETUP, new NetIPInfo("127.0.0.1",4445, EnumModName.SETUP.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.CME, new NetIPInfo("127.0.0.1",4445, EnumModName.CME.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.SCHEDULER, new NetIPInfo("127.0.0.1",4445, EnumModName.SCHEDULER.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.TRACKER, new NetIPInfo("127.0.0.1",4445, EnumModName.TRACKER.getModAbbrev(),"DEFAULT"));
	
	}
	
	////////////// Boardcast control bits and pieces - probably need to move into my own control thread
	/**
	 * Starts the Broadcast Client thread that will keep running for the whole application. 
	 */
	private void ipRegistrationStart(){
		
		//setup the inital data
		
		
		

	}





}
