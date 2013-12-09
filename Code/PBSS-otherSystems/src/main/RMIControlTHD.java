package main;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;

import rmi_engine.IpRegistrationTHD;


public class RMIControlTHD implements Runnable {
	
	private Thread thrdControlBCast;
	private IpRegistrationTHD controlBCast = null; 
	
	private boolean bDebug = false;
	
	public RMIControlTHD(String pModName){
		controlBCast = new IpRegistrationTHD(pModName);
	}
	
	public static void RMIBoardCastControl()
	{


	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			//SPECIAL: note that this will require main to call another function, as this thread won't auto start to boardcast in final version, does currently (DT NOTE ONLY)
			//start the boardcast
			ipRegistrationStart();
			
			/*if(bDebug) System.out.println("Attempting to start RMI server");
			//start RMI server
			try {
				Registry vRegistry;
				vRegistry = LocateRegistry.createRegistry(SysConfig.getIPInfo(SysConfig.getMyModuleName()).getPort());
				NAVServerImpl vNavServer = new NAVServerImpl(vRegistry, SysConfig.getModuleName(), false);
				if(bDebug) System.out.println("Started RMI server");
				
			} catch (RemoteException e) {

				e.printStackTrace();
			} catch (AlreadyBoundException e) {

				e.printStackTrace();
			}*/
	
			
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
		/*SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo("127.0.0.1",25004, SysConfig.getMyModuleName().getModAbbrev(),"DEFAULT"));

		//Add Default IP Info
		SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, new NetIPInfo("127.0.0.1",25000,EnumModName.CONTROL_CENTRE.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.DB, new NetIPInfo("127.0.0.1",25001, EnumModName.DB.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.SETUP, new NetIPInfo("127.0.0.1",25002, EnumModName.SETUP.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.CME, new NetIPInfo("127.0.0.1",25003, EnumModName.CME.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.SCHEDULER, new NetIPInfo("127.0.0.1",25005, EnumModName.SCHEDULER.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.TRACKER, new NetIPInfo("127.0.0.1",25006, EnumModName.TRACKER.getModAbbrev(),"DEFAULT"));
	*/
	}
	
	////////////// Boardcast control bits and pieces - probably need to move into my own control thread
	/**
	 * Starts the Broadcast Control Thread
	 *  - Registers module in CC IP list
	 *  - Obtains CC IP address
	 *  - Goes into check mode
	 *  	-check mode will call CC to get the list of IPs and will continue to do so 
	 *  		at a time period set in SysConfig until state changes from 3 to 0 at which point it will exit
	 */
	private void ipRegistrationStart(){
		
		setupDataForBCastStart();
		
		System.out.println("Starting Broadcast Thread...");

		if (this.thrdControlBCast==null)
			thrdControlBCast = new Thread(controlBCast);

		if (this.thrdControlBCast.isAlive()){
			System.out.println("   FAILED: Broadcast Thread Already Running");
		}
		else{
			thrdControlBCast = new Thread(controlBCast);
			thrdControlBCast.start();

		}
	}





}
