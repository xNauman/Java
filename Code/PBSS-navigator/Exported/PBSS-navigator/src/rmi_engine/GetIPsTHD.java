package rmi_engine;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import main.RMIControlTHD;

import config.SysConfig;

import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;

/**
 * Handles the CC broadcast system
 * 
 * @author Ben
 *
 */
public class GetIPsTHD implements Runnable {



	public void run() {
		//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Called doCCRegistration");
		//get the CCs IP
		//doCCRegistration();
		
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Update state to "+rawobjectsPriv.EnumSysState.NOT_OPERATIONAL);
		//update state to not operational
		utils.StateControl.setState(rawobjectsPriv.EnumSysState.NOT_OPERATIONAL, true);
		
		
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Starting IPRegistration cc call loop");
		//If the registration state isn't set to false, keep this going
		while(!SysConfig.getStopIPRegistration())
		{
			
			//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Calling getIPInfoFromCC");
			//get IPs for all other modules from CC
			getIPInfoFromCC();
			//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("IPRegistration going to sleep");
			//sleep for awhile
			try {
				Thread.sleep(SysConfig.getIPRegistrationSleepTimer());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//if we get to here we must have been stopped, so restart the stopIPregistration value so it can be rerun
		//if needed
		SysConfig.setStopIPRegistration(false);
		
	} 



	/**
	 * boardcast rego from CC
	 */
	private void doCCRegistration(){
		
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("About to start broadcast thread");
		
		
		try {
			cc.registry.RegistryClient c = null;
			c = new cc.registry.RegistryClient(SysConfig.getMyModuleName().getModAbbrev());
			Thread ipBroadCast; 
			ipBroadCast = new Thread(c);
			ipBroadCast.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SysConfig.setIPregistrationCompleted(true);

		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Completed broadcast thread start");
		

		
		
		/*while (!SysConfig.getIPregistrationCompleted()) {
			
			
				cc.registry.RegistryClient c = null;
				try {
					String ccIPAddr = "";
					boolean useBCast = true;

					if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("still in doCCrego");
					if (useBCast) {
						c = new cc.registry.RegistryClient(SysConfig.getMyModuleName().getModAbbrev()); //specify string as CME, DB, NAV, SCH, SU, TKR
						ccIPAddr = c.getServerIP();
						if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("CC SERVER IS AT: " + ccIPAddr);
					}
					else {
						if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("************ BROADCAST NOT ACTIVE *******************");
						ccIPAddr = "127.0.0.1";
					}
					if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("still in doCCrego-after if");

					NetIPInfo ccInfo = SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE);
					ccInfo.setIPAddress(ccIPAddr, "CC_SET_BCAST");

					SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, ccInfo);
					SysConfig.setIPregistrationCompleted(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		}
		*/
		
		
	}



	private boolean getIPInfoFromCC(){
		
		if (SysConfig.getIPregistrationCompleted()) {
			
			//variables work out what IPs were obtained
			boolean vErrorGettingIP_DB = false;
			boolean vErrorGettingIP_CME = false;
			boolean vErrorGettingIP_TRACKER = false;
			boolean vErrorGettingIP_SETUP = false;
			boolean vErrorGettingIP_SCHEDULER = false;
			
			try {
				
				//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("at a1:SchMainControlBCAstTHD");
				
				//rmi_interfaces.TKRServer tkr = new rmi_engine.NAVClientImplTKR().getSrvObject();
				//System.out.println("TRK OUT = " + tkr.debugEcho("SCH", "Just a message"));
				
				//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("at a1.1:SchMainControlBCAstTHD");
				
				//int myNum = 0/0;
				//System.exit(55);
				
				rmi_interfaces.CCServer cc = new rmi_engine.NAVClientImplCC().getSrvObject();
				//rmi_interfaces.CCServer cc = new rmi_engine.SCHClientImplCC("127.0.0.1",25000,"CC").getSrvObject();
				
				//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("at a2:SchMainControlBCAstTHD");

				if (!funcGetIPInfoFromCC_Worker(EnumModName.DB, cc)) 
					vErrorGettingIP_DB = true;
				
				if (!funcGetIPInfoFromCC_Worker(EnumModName.CME, cc))
					vErrorGettingIP_CME = true;
				
				if (!funcGetIPInfoFromCC_Worker(EnumModName.TRACKER, cc))
					vErrorGettingIP_TRACKER = true;
				
				if (!funcGetIPInfoFromCC_Worker(EnumModName.SETUP, cc))
					vErrorGettingIP_SETUP = true;
				
				if (!funcGetIPInfoFromCC_Worker(EnumModName.SCHEDULER, cc))
					vErrorGettingIP_SCHEDULER = true;


			} catch (RemoteException e) {
				e.printStackTrace();
				return false;
			} catch (NotBoundException e) {
				e.printStackTrace();
				return false;
			}
		
			//work out if we got the IPs for the core modules. If set the CoreModuleIPsKNow to true
			if(vErrorGettingIP_DB == true && vErrorGettingIP_TRACKER == true
					&& vErrorGettingIP_SCHEDULER == true)	
			{
				SysConfig.setCoreModuleIPsKnown(true); 
			}
			return true;
		}
		
		else {
			return false;
		}
	}

	private boolean funcGetIPInfoFromCC_Worker(EnumModName pMod, rmi_interfaces.CCServer pCC){

		//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("GET_RMI_CALL:SchMainControlBCAstTHD" + pMod.getModAbbrev());

		NetIPInfo currInfo = SysConfig.getIPInfo(pMod);

		//Since CC only provides IP information, we obtain the existing port info, and just update the IP

		String sIPAddressFromCC = "";

		try {
			/*System.out.println("AT_RMI--3_CALL" + pMod.getModAbbrev());
			rmi_interfaces.CCServer cc = new rmi_engine.SCHClientImplCC().getSrvObject();*/
		//	if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("AT_RMI--4_CALL" + pMod.getModAbbrev());
			sIPAddressFromCC = pCC.getIP(pMod.getModAbbrev());
		//	if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("AT_RMI--5_CALL" + pMod.getModAbbrev());
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}

		//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("AT_RMI--1_CALL" + pMod.getModAbbrev());

		currInfo.setIPAddress(sIPAddressFromCC,"GET_FROM_CC");
		SysConfig.addIPInfo(pMod, currInfo);

		//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("AT_RMI--2_CALL" + pMod.getModAbbrev());

		//if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("END_RMI_CALL" + pMod.getModAbbrev());

		return true;
	}

}
