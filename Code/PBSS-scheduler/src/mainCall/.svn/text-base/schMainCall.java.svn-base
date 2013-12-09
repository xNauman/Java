package mainCall;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import config.SysConfig;

import rawobjectsPriv.NetIPInfo;
import rmi_engine.SCHClientImplTKR;
import rmi_interfaces.TKRServer;
import routeCheck.SchRouteChecker;

import appControl.SchMainControlTHD;
import util.Debug;
import util.SysLogTypeEnum;

public class schMainCall {

	public static void main(String[] args) {

		//Default for user input "permission"
		SysConfig.setAllowUserInputMode(true);
		
		for (int i = 0; i < args.length; i++){
			String sArg = args[i].toUpperCase();
			
			if (sArg.equalsIgnoreCase("-v")){
				System.out.println("Scheduler Revision Number (SVN) = " + SysConfig.getSVNRevNum());
			}
			else if (sArg.equalsIgnoreCase("-i")){
				SysConfig.setAllowUserInputMode(true);
			}
		}
		
		//testCode_CCBCast_1();

		//Starts the Main Control Thread
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "100", "App Ops");
		Debug.writeDebugMsg("schMainCall", "main", "Main Application Started");

		//Add self info
		//LEAVE THIS HERE - NEEDED TO HANDLE RMI SERVER START
		//ALSO SET IT: RMIControlTHD.setupDataForBCastStart
		
		if (!SysConfig.getUseDummyRMIService()){
			SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo("127.0.0.1",4445, SysConfig.getMyModuleName().getModAbbrev(),"DEFAULT"));
		}
		else {
			System.out.println("***WARNING: Using DummyRMI Service - Disable From SysConfig (MSG AT: schMainCall.main)");
			SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo("127.0.0.1",25005, SysConfig.getMyModuleName().getModAbbrev(),"DEFAULT"));
		}
		

		//DEFAULT INFO NOW SET IN: RMIControlTHD.setupDataForBCastStart
		//Add Default IP Info
		/*SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, new NetIPInfo("127.0.0.1",4445,EnumModName.CONTROL_CENTRE.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.DB, new NetIPInfo("127.0.0.1",4445, EnumModName.DB.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.SETUP, new NetIPInfo("127.0.0.1",4445, EnumModName.SETUP.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.CME, new NetIPInfo("127.0.0.1",4445, EnumModName.CME.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.NAVIGATOR, new NetIPInfo("127.0.0.1",4445, EnumModName.NAVIGATOR.getModAbbrev(),"DEFAULT"));
		SysConfig.addIPInfo(EnumModName.TRACKER, new NetIPInfo("127.0.0.1",4445, EnumModName.TRACKER.getModAbbrev(),"DEFAULT"));
		 */

		//testCode_D1();

		SysConfig.setAppStartTime();
		SysConfig.setSimRunID("Default", -2);

		//Set default details
		SysConfig.setMaxNavDistance(100);

		System.out.println("DEFAULT TIME OUT VALUES ARE HIGH, SET AT schMainCall.main");

		SysConfig.setTimeOutNAVRequest(3000); //30000
		SysConfig.setTimeOutNAVRequestAutoKill(2000); //20000

		Thread mainThread = new Thread(new SchMainControlTHD());
		mainThread.start();

		//LOCAL TESTING AREA - use to check local code
		//testCode_Random();
		//testCode_RouteCheck_2();
		//testCode_RMI_1();
		//testCode_RMI_2();
		//testCode_RMI_3_DB();
		//testCode_RMI_3_TKR();

		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "101", "App Ops");
	}

	@SuppressWarnings("unused")
	private static void testCode_Random(){
		for (int i = 1;i<500;i++){
			System.out.println("RAN:" + util.General.randomNumberMAX(100, 200));
		}
	}

	@SuppressWarnings("unused")
	private static void testCode_D1(){
		//commented out as this code is test and doesn't work with the new RMI boardcast threads
		//		Thread thrdControlBCast;
		//		RMIControlTHD controlBCast = new RMIControlTHD();
		//		
		//		thrdControlBCast = new Thread(controlBCast);
		//		controlBCast.contBroadcastStart();
		//		thrdControlBCast.start();
		//		
		//		System.exit(5);
	}
	/**
	 * Use for the testing of a generic sample route, with a number of stops, 
	 * junctions, traffic lights, congested areas and move requests defined.
	 */
	public static void testCode_RouteCheck_1(){
		//Testing of the Route Check Package

		//********Create Some Dummy Traffic Lights/Junctions
		Vector<routeCheck.SchSimpleObjTL> vTL = new Vector<routeCheck.SchSimpleObjTL>();

		//Create TL 1 (DB = 20) at (1,4)
		routeCheck.SchSimpleObjTL entryTL = new routeCheck.SchSimpleObjTL(1,20,new Point(1,4),false);
		vTL.add(entryTL);

		//Create TL 2 (DB = 29) at (4,20)
		entryTL = new routeCheck.SchSimpleObjTL(2,29,new Point(4,20),false);
		vTL.add(entryTL);

		//Create Junction  1 (DB = 1) at (1,10)
		entryTL = new routeCheck.SchSimpleObjTL(3,1,new Point(1,10),true);
		vTL.add(entryTL);

		routeCheck.SchRouteChecker.setSimpleObjTL(vTL);


		//********Create Some Dummy Stops
		Vector<routeCheck.SchSimpleObjStop> vStop = new Vector<routeCheck.SchSimpleObjStop>();

		//Create Stop 1 (DB = 15) at (4,10)
		routeCheck.SchSimpleObjStop entryStop = new routeCheck.SchSimpleObjStop(1,15,new Point(4,10));
		vStop.add(entryStop);

		//Create Stop 2 (DB = 17) at (4,16)
		entryStop = new routeCheck.SchSimpleObjStop(2,17,new Point(4,16));
		vStop.add(entryStop);

		routeCheck.SchRouteChecker.setSimpleObjStop(vStop);


		//********Create Some Dummy Congested Areas
		Vector<routeCheck.SchSimpleObjCongArea> vCA = new Vector<routeCheck.SchSimpleObjCongArea>();

		//Create CA 1 (DB = 5) at (4,10) to (4,12)
		routeCheck.SchSimpleObjCongArea entryCA = new routeCheck.SchSimpleObjCongArea(1,5,new Point(4,10), new Point(4,12));
		vCA.add(entryCA);

		//Create CA 2 (DB = 7) at (8,12) to (15,20)
		entryCA = new routeCheck.SchSimpleObjCongArea(2,7,new Point(8,12), new Point(15,20));
		vCA.add(entryCA);

		routeCheck.SchRouteChecker.setSimpleObjCongArea(vCA);


		//Want to move from (1,5) to (1, 10)
		Vector<routeCheck.SchMovePointData> vMoveData = routeCheck.SchRouteChecker.getMovePointData(new Point (1,5), new Point (1,10));
		routeCheck.SchRouteChecker.debugPrintMoveData(vMoveData, true);

		//Want to move from (1,10) to (4, 25)
		vMoveData = routeCheck.SchRouteChecker.getMovePointData(new Point (1,10), new Point (4,25));
		routeCheck.SchRouteChecker.debugPrintMoveData(vMoveData, true);
	}

	/**
	 * Use to produce SAMPLE output, that is expected from RouteCheck.
	 * NOTE: Does not perform any checks/calculations. 
	 */
	public static void testCode_RouteCheck_2(){
		Vector<routeCheck.SchMovePointData> vMoveData_Dummy;
		vMoveData_Dummy = routeCheck.SchRouteChecker.debugAddEntries();

		SchRouteChecker.debugPrintMoveData(vMoveData_Dummy, true);
	}

	@SuppressWarnings("unused")
	private static void testCode_RMI_1(){

		SCHClientImplTKR rmiToTKR=null;
		TKRServer srvTKR=null;

		System.out.println("RMI_TEST_CODE-START");

		try {
			rmiToTKR = new SCHClientImplTKR("127.0.0.1", 27001, "TKRServer");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		srvTKR = rmiToTKR.getSrvObject();
		try {
			srvTKR.debugEcho("SCH","echo from SCH");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("RMI_TEST_CODE-END");

	}

	@SuppressWarnings("unused")
	private static void testCode_RMI_2(){

		Registry mRemoteRegistry;
		TKRServer mServerObject;
		String mServerAddress = "127.0.0.1";
		String mServerPort = "27001";

		//Added Sec Handler, appears to work if .class files are in the same dir, but not
		//when they are separated.
		//REFER: http://www.comp.lancs.ac.uk/~weerasin/csc253/tutorials/week8.html

		System.setSecurityManager(new java.rmi.RMISecurityManager());

		try{
			mRemoteRegistry = 
				LocateRegistry.getRegistry(	mServerAddress,
						(new Integer(mServerPort).intValue()));
			/**
			 * get the object from the remote-registry
			 */		
			System.out.println("Creating Remote-Object");

			// connect to the rmi server

			mServerObject = (TKRServer)mRemoteRegistry.lookup("TKRServer"); //DummyTKR


			//show the results
			System.out.println("ECHO RESP = " + mServerObject.debugEcho("SCH", "hello from testCode2"));
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void testCode_RMI_3_DB(){
		rmi_interfaces.DBServer testEcho;
		try {
			testEcho = new rmi_engine.SCHClientImplDB().getSrvObject();
			//TODO 9NOTE [SG] DB does not support echo
			//System.out.println(testEcho.debugEcho("SCH", "say hello111111111111111"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void testCode_RMI_3_TKR(){
		rmi_interfaces.TKRServer testEcho;
		try {
			testEcho = new rmi_engine.SCHClientImplTKR().getSrvObject();
			System.out.println(testEcho.debugEcho("SCH", "say hello111111111111111"));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static void testCode_CCBCast_1(){

		// specify string as CME, DB, NAV, SCH, SU, TKR
		cc.registry.RegistryClient c = null;
		try {
			c = new cc.registry.RegistryClient("SCH");
			System.out.println("REMOTE SERVER IS: " + c.getServerIP());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.exit(11);

	}

}
