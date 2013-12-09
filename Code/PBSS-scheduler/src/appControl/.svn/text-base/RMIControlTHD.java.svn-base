package appControl;

import config.SysConfig;


import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;
import rmi_engine.BCastTHD;
import rmi_engine.GetIPsTHD;

public class RMIControlTHD implements Runnable {
		
	public static void RMIBoardCastControl()
	{


	}
	
	@Override
	public void run() {
	
				
			//setup data from Bcast
			setupDataForBCastStart();
	
			//call thread to handle bcasting
			Thread vBCastThread; 
			vBCastThread = new Thread(new BCastTHD());
			vBCastThread.start();
			
			//Nav starts RMI server at this point, but sch is manual for now
			
			//call thread to start checking for IPs
			//This will call into CC regulary and get ALL the IPs it can
			Thread vIPRego; 
			vIPRego = new Thread(new GetIPsTHD());
			vIPRego.start();
			
			//SPECIAL: we create a new navigation request thread on each request ben makes to me
			/*System.out.println("-----------------------------");
			System.out.println("RMIControlTHD ended");
			System.out.println("-----------------------------");*/
			
	}
	
	
	/**
	 * Function sets default IP information, just so we don't end up with null values in our code
	 */
	private void setupDataForBCastStart()
	{
		
		//this code adds the default IP and port, boardcast will give IP but no port, this just means we don't have empty data types incase
		//something horrible happens
		
		//TODO 8FORNAV: DummyRMI Service Usage (Part B)
		
		if (!SysConfig.getUseDummyRMIService()){
			//Add self info
			SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo("127.0.0.1",4445, SysConfig.getMyModuleName().getModAbbrev(),"DEFAULT"));

			//Add Default IP Info
			SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, new NetIPInfo("127.0.0.1",4445,EnumModName.CONTROL_CENTRE.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.DB, new NetIPInfo("127.0.0.1",4445, EnumModName.DB.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.SETUP, new NetIPInfo("127.0.0.1",4445, EnumModName.SETUP.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.CME, new NetIPInfo("127.0.0.1",4445, EnumModName.CME.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.NAVIGATOR, new NetIPInfo("127.0.0.1",4445, EnumModName.NAVIGATOR.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.TRACKER, new NetIPInfo("127.0.0.1",4445, EnumModName.TRACKER.getModAbbrev(),"DEFAULT"));
		
		}
		else {
		
			System.out.println("***WARNING: Using DummyRMI Service - Disable From SysConfig (MSG AT: RMIControlTHD.setupDataForBCastStart)");
			//Add self info
			SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo("127.0.0.1",25005, SysConfig.getMyModuleName().getModAbbrev(),"DEFAULT"));

			//Add Default IP Info
			SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, new NetIPInfo("127.0.0.1",25000,EnumModName.CONTROL_CENTRE.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.DB, new NetIPInfo("127.0.0.1",25001, EnumModName.DB.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.SETUP, new NetIPInfo("127.0.0.1",25002, EnumModName.SETUP.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.CME, new NetIPInfo("127.0.0.1",25003, EnumModName.CME.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.NAVIGATOR, new NetIPInfo("127.0.0.1",25004, EnumModName.NAVIGATOR.getModAbbrev(),"DEFAULT"));
			SysConfig.addIPInfo(EnumModName.TRACKER, new NetIPInfo("127.0.0.1",25006, EnumModName.TRACKER.getModAbbrev(),"DEFAULT"));
		
		}
		
			
		
		
	}
	






}
