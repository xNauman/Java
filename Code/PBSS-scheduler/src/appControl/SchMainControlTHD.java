package appControl;

import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import config.SysConfig;

import mainCall.schMainCall;

import rawobjectsPriv.EnumModName;
import rawobjectsPriv.EnumSysState;
import rawobjectsPriv.NetIPInfo;
import route.SchRouteControlTHD;
import route.SchRouteResp;
import routeCheck.SchRouteChecker;
import trafficLight.SchTLControlTHD;
import util.Debug;
import util.Formatter;
import util.General;
import util.SysLogTypeEnum;
import util.TimeControlTHD;

/**
 * The main controlling thread for local interaction.
 * Creates a Console reader, to accept commands and handles them as appropriate.
 * 
 * Also creates the:
 *   - main controlling thread for remote control (i.e.,RMI requests from CC)
 *   - main control thread for traffic lights 
 * 
 * @author Ben
 *
 */

public class SchMainControlTHD implements Runnable {

	//If AutoStart = false, use console to start each Control thread manually
	boolean bAutoStart=true;

	Thread thrdControlTime;
	TimeControlTHD controlTime= new TimeControlTHD();

	Thread thrdControlRemote;
	SchMainControlRemoteTHD controlRemote = new SchMainControlRemoteTHD(this);

	Thread thrdControlBCast;
	RMIControlTHD controlBCast = new RMIControlTHD(); 

	Thread thrdControlTL;
	SchTLControlTHD controlTL = new SchTLControlTHD();

	Thread thrdControlRoute;
	SchRouteControlTHD controlRoute = new SchRouteControlTHD();

	boolean bSysCoreActive = true;

	@Override
	public void run() {

		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "110", "App Ops");

		Debug.writeDebugMsg("schMainControl", "run", "Main Local Control Thread Started");
		//Debug.writeDebugMsg("schMainControl", "run", "WARN: Starting/Stopping individual lights not properly tested.");

		if (!SysConfig.getAllowUserInputMode()){
			Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "110B", "LUIM OFF");

			Debug.writeDebugMsg("schMainControl", "run", "(LUIM OFF)");
			this.funcDebugPingMsgOff();
			
			this.funcRun();
		}
		else 
		{
			if (bAutoStart){
				/*this.funcRemContStart();
				this.funcTLContStart();
				this.funcRouteContStart();*/
				this.funcRun();
			}
			else {
				Debug.writeDebugMsg("schMainControl", "run", "INFO: Auto Start is Disabled; Start System Manually.");
			}


			this.funcDebugPingMsgOff();
			Debug.writeDebugMsg("schMainControl", "run", "INFO: Ping Messages OFF (use pingmsg.on to enable)");

			Debug.writeDebugMsg("schMainControl", "run", "INFO: Type 'help' at console line for info/help.");
			while (bSysCoreActive)
			{
				printCmdPromptText();
				Scanner inConsole = new Scanner(System.in);
				String sConsole = inConsole.nextLine();

				if (sConsole.length()>0){
					doCmdConsole(sConsole);
				}
			}
		}

		Debug.writeDebugMsg("schMainControl", "run", "Main Local Control Thread Ended");
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "111", "App Ops");

	}

	private void printCmdPromptText(){
		System.out.print("[MENU]> ");
	}
	private void doCmdConsole(String pCmd){
		pCmd = pCmd.toLowerCase();

		if (pCmd.equalsIgnoreCase("help")){
			System.out.println("PBSS Scheduler: Local Console Help");
			System.out.println("----------------------------------");
			System.out.println("[!] = Not Implemented");
			System.out.println("[^] = Deprecated (no longer operational)");
			System.out.println("[*] = Untested Code; use with care!");
			System.out.println("[R] = Uses RMI ");
			System.out.println("");
			System.out.println("* General/Diagnostics:");
			System.out.println("   [R] test.db.echo: Test DB Communications");
			System.out.println("   [!] test.cc.echo: Test CC Communications");
			System.out.println("   [R] test.tkr.echo: Test TKR Communications");
			System.out.println("   [!] test.nav.echo: Test NAV Communications");
			System.out.println("   [!] time: Return Simulator Time Information");
			System.out.println("   info.file: Display File Path Information");
			System.out.println("   info.sys: Display General System Information");
			System.out.println("   stat.sys: Display General System Statistics");
			System.out.println("   help.run: Help on Running the simulator");
			System.out.println("   pingmsg.on: Turns on Ping Messages");
			System.out.println("   pingmsg.off: Turns off Ping Messages");
			System.out.println("");
			System.out.println("* System Operations:");
			System.out.println("   [^] prestart: Load Traffic Light and Route Data, but DO NOT START");
			System.out.println("   [R] prestart.force: Load Traffic Light and Route Data, but DO NOT START");
			System.out.println("   run: Starts the simulator, ready for operation");
			System.out.println("   [^] start: Start the simulator (locally)");
			System.out.println("   start.force: Force the simulator to start without a Remote Start Cmd");
			System.out.println("   stop: Stops the entire Scheduler Module (only allows for local input)");
			System.out.println("   [!] reset: Resets the Scheduler Module ready for another prestart request.");
			System.out.println("   exit: Stops and exits/terminates the entire Scheduler Module");
			System.out.println("");
			System.out.println("* Communication Operations:");
			System.out.println("   comms.info: Details of Network Comms");
			System.out.println("   net.set.cc: Sets Network Details for Control Centre");
			System.out.println("   net.set.db: Sets Network Details for Database");
			System.out.println("   net.set.nav: Sets Network Details for Navigator");
			System.out.println("   net.set.tkr: Sets Network Details for Tracker");
			System.out.println("   net.set.cme: Sets Network Details for CME");
			System.out.println("   net.set.su: Sets Network Details for Setup");
			System.out.println("   net.set.sch: Sets Network Details for Scheduler");
			System.out.println("   [^] rmi.set.host: Set the RMI registry Host");
			System.out.println("   rmi.set.port: Set the RMI registry port");
			System.out.println("   rmi.bind: Bind (Starts) the SCH RMI Server");
			System.out.println("   rmi.unbind: Unbind (Stops) the SCH RMI Server");
			//Need some to manually set the IPs of things.
			System.out.println("");
			System.out.println("* Major Module Control:");
			System.out.println("   start.rem: Start Remote Control (RMI) Function");
			System.out.println("   stop.rem: Stop Remote Control (RMI) Function");
			System.out.println("   start.bc: Start Broadcast Function");
			System.out.println("   stop.bc: Stop Broadcast Function");
			System.out.println("   start.time: Start Time Sync Function");
			System.out.println("   stop.time: Stop Time Sync Function");
			System.out.println("   start.tl: Start TL Control Function");
			System.out.println("   stop.tl: Stop TL Control Function");
			System.out.println("   kill.tl: Kill all TL Threads");
			System.out.println("   start.route: Start Route Control Function");
			System.out.println("   stop.route: Stop Route Control Function");
			System.out.println("");
			System.out.println("* Traffic Light Control:");
			System.out.println("   tl.start-<x>: Start Traffic Light <x> (start at 0)");
			System.out.println("   tl.stop-<x>: Stop Traffic Light <x>  (start at 0)");
			System.out.println("   [!] tl.info-<x>: Traffic Light Information <x>  (start at 0)");
			System.out.println("   tl.kill-<x>: Kill TL Thread <x>");
			System.out.println("");
			System.out.println("* Route Control:");
			System.out.println("   route.info-<x>: Get Information on Route ID <x> (start at 0)");
			System.out.println("   route.bus.start: Start ALL buses for each route.");
			System.out.println("   route.bus.stop: Stops ALL buses for each route.");
			System.out.println("   route.bus.kill: Stops ALL buses for each route.");
			System.out.println("");
			System.out.println("* Navigation Request Control:");
			System.out.println("   navr.list: Dumps response that have been received.");
			System.out.println("   [!] navr.clear: Clear the response list.");
			System.out.println("");
			System.out.println("* Objects (Static, as for Route Check:");
			System.out.println("   o.stops.list: Lists Defined Stops:");
			System.out.println("   o.ca.list: Lists Defined Congested Areas");
			System.out.println("   o.tl.list: Lists Defined Traffic Lights/Junctions");
			System.out.println("");
			System.out.println("* Create RMI File --- TEMP ONLY WHILE RMI NOT AVB ---");
			System.out.println("   [^] rmif.start: Create a RMI Start File");
			System.out.println("   [^] rmif.stop: Create a RMI Stop File");
			/*System.out.println("");
			System.out.println("* TESTING USE ONLY");
			System.out.println("   x.rc1: Test Route Check Logic, with a generic route move request.");
			System.out.println("   x.rc2: Display SAMPLE output of response from Route Check package.");*/
			System.out.println("--");
		}
		else if (pCmd.equalsIgnoreCase("x.rc1")){
			schMainCall.testCode_RouteCheck_1();
		}
		else if (pCmd.equalsIgnoreCase("x.rc2")){
			schMainCall.testCode_RouteCheck_2();
		}
		else if (pCmd.equalsIgnoreCase("stop")){
			this.funcPBSSStop(false);
		}
		else if (pCmd.equalsIgnoreCase("exit")){
			this.funcPBSSExit();
		}
		else if (pCmd.equalsIgnoreCase("s.t")){
			System.out.println("Simulator Run Time [NAV DIRECT] = " + General.getSimulatorTime_Actual());
		}
		else if (pCmd.equalsIgnoreCase("info.file")){
			System.out.println("DAT Path:");
			System.out.println("   " + General.pathDataFile());
			System.out.println("RMI-SIM Path:");
			System.out.println("   " + General.pathRMISimFile());
			System.out.println("LOG Path:");
			System.out.println("   " + General.pathLogFile());
		}
		else if (pCmd.equalsIgnoreCase("info.sys")){
			funcSysInfo();
		}
		else if (pCmd.equalsIgnoreCase("stat.sys")){
			funcSCHOpsStats();
		}
		else if (pCmd.contains("help.run")){
			System.out.println("How to Run the Simulator:");
			System.out.println("  1. Execute 'run' to bind SCH in the RMI");
			System.out.println("  2. Have CC issue a 'Pre Start' command. Alternatively, use 'prestart.force'.");
			System.out.println("  3. Have CC issue a 'Start' command. Alternatively, use 'start.force'.");

			System.out.println("  To Stop: execute 'stop'");
			System.out.println("  To Exit: execute 'exit'");
		}
		else if (pCmd.equalsIgnoreCase("pingmsg.on")){
			this.funcDebugPingMsgOn();
		}
		else if (pCmd.equalsIgnoreCase("pingmsg.off")){
			this.funcDebugPingMsgOff();
		}
		else if (pCmd.equalsIgnoreCase("rmif.start")){
			System.out.println("The cmd 'rmif.start' is no longer available. Use RMI.");
			//this.funcCreateFile(General.pathRMISimFile("doRMI_Start.txt"));
		}
		else if (pCmd.equalsIgnoreCase("rmif.stop")){
			System.out.println("The cmd 'rmif.stop' is no longer available. Use RMI.");
			//this.funcCreateFile(General.pathRMISimFile("doRMI_Stop.txt"));
		}
		else if (pCmd.equalsIgnoreCase("prestart")){
			System.out.println("The cmd 'prestart' is no longer available. Use 'prestart.force'.");
			//this.funcPBSSPreStart();
		}
		else if (pCmd.equalsIgnoreCase("run")){
			this.funcRun();
		}
		else if (pCmd.equalsIgnoreCase("start")){
			System.out.println("The cmd 'start' has been deprecated: use 'run'");
		}
		else if (pCmd.equalsIgnoreCase("prestart.force")||pCmd.equalsIgnoreCase("p.f")){
			this.funcPBSSForcePreStart();
		}
		else if (pCmd.equalsIgnoreCase("start.force")||pCmd.equalsIgnoreCase("s.f")){
			this.funcPBSSStart(false);
		}
		else if (pCmd.equalsIgnoreCase("start.rem")){
			this.funcRemContStart();
		}
		else if (pCmd.equalsIgnoreCase("stop.rem")){
			this.funcRemContStop();
		}
		else if (pCmd.equalsIgnoreCase("start.bc")){
			this.funcContRMI_THD();
		}
		else if (pCmd.equalsIgnoreCase("stop.bc")){
			this.funcContRMI_THD_Stop();
		}
		else if (pCmd.equalsIgnoreCase("start.time")){
			this.funcTimeContStart();
		}
		else if (pCmd.equalsIgnoreCase("stop.time")){
			this.funcTimeContStop();
		}
		else if (pCmd.equalsIgnoreCase("start.tl")){
			this.funcTLContStart();
		}
		else if (pCmd.equalsIgnoreCase("stop.tl")||pCmd.equalsIgnoreCase("s.tl")){
			this.funcTLContStop();
		}
		else if (pCmd.equalsIgnoreCase("kill.tl")){
			this.funcTLKill();
		}
		else if (pCmd.equalsIgnoreCase("start.route")){
			this.funcRouteContStart();
		}
		else if (pCmd.equalsIgnoreCase("stop.route")){
			this.funcRouteContStop();
		}
		else if (pCmd.contains("route.info-")){
			doCmdConsoleSepParam(pCmd);
		}
		else if (pCmd.contains("route.bus.start")){
			funcRouteBusesStart();
		}
		else if (pCmd.contains("route.bus.stop")){
			funcRouteBusesStop();
		}
		else if (pCmd.contains("route.bus.kill")){
			funcRouteBusesKill();
		}
		else if (pCmd.contains("tl.start-")){
			doCmdConsoleSepParam(pCmd);
		}
		else if (pCmd.contains("tl.stop-")){
			doCmdConsoleSepParam(pCmd);
		}
		else if (pCmd.contains("tl.info-")){
			doCmdConsoleSepParam(pCmd);
		}
		else if (pCmd.contains("tl.kill-")){
			doCmdConsoleSepParam(pCmd);
		}
		else if (pCmd.contains("comms.info")){
			this.funcCommsInfo();
		}
		else if (pCmd.contains("rmi.bind")){
			this.funcRMIServerBind(false);
		}
		else if (pCmd.contains("rmi.rebind")){
			this.funcRMIServerBind(true);
		}
		else if (pCmd.contains("rmi.unbind")){
			this.funcRMIServerUnBind();
		}
		else if (pCmd.contains("rmi.set.host")||pCmd.contains("rmi.s.h")){
			//this.funcRMIServerSetHost();
			System.out.println("No Longer Supported - Central RMI Registry Decommissioned");
		}
		//TODO 8FORNAV: Ability to manual set IP Information
		else if (pCmd.contains("net.set.cc")||pCmd.contains("net.s.cc")){
			this.funcNETSetCCInfo();
		}
		else if (pCmd.contains("net.set.db")||pCmd.contains("net.s.db")){
			this.funcSetNetInfo(EnumModName.DB);
		}
		else if (pCmd.contains("net.set.cme")||pCmd.contains("net.s.cme")){
			this.funcSetNetInfo(EnumModName.CME);
		}
		else if (pCmd.contains("net.set.su")||pCmd.contains("net.s.su")){
			this.funcSetNetInfo(EnumModName.SETUP);
		}
		else if (pCmd.contains("net.set.tkr")||pCmd.contains("net.s.tkr")){
			this.funcSetNetInfo(EnumModName.TRACKER);
		}
		else if (pCmd.contains("net.set.nav")||pCmd.contains("net.s.nav")){
			this.funcSetNetInfo(EnumModName.NAVIGATOR);
		}
		else if (pCmd.contains("net.set.sch")||pCmd.contains("net.s.sch")){
			this.funcSetNetInfo(EnumModName.SCHEDULER);
		}
		else if (pCmd.contains("rmi.set.port")||pCmd.contains("rmi.s.p")){
			this.funcRMIServerSetPort();
		}
		else if (pCmd.contains("test.tkr.echo")||pCmd.contains("t.t.e")){
			this.funcTestTKREcho();
		}
		else if (pCmd.contains("test.nav.echo")||pCmd.contains("t.n.e")){
			this.funcTestNAVEcho();
		}
		else if (pCmd.contains("test.db.echo")||pCmd.contains("t.d.e")){
			this.funcTestDBEcho();
		}
		else if (pCmd.contains("o.stops.list")||pCmd.contains("o.s.l")){
			this.funcObjectsStopList();
		}
		else if (pCmd.contains("o.ca.list")||pCmd.contains("o.ca.l")){
			this.funcObjectsCAList();
		}
		else if (pCmd.contains("o.tl.list")||pCmd.contains("o.tl.l")){
			this.funcObjectsTLList();
		}
		else if (pCmd.contains("navr.list")||pCmd.contains("nr.l")){
			this.funcNavReqList();
		}
		else if (pCmd.equalsIgnoreCase("b")){
			try {
				doCmdConsole("rmi.unbind");
				doCmdConsole("run");
				doCmdConsole("p.f");
				Thread.sleep(100);
				doCmdConsole("s.f");
				doCmdConsole("s.tl");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		else {
			System.out.println("The command '" + pCmd + "' is not recognised or not implemented.");
		}
	}

	/**
	 * Handles console requests that have parameters appended to them.
	 * @param pCmd
	 */
	private void doCmdConsoleSepParam(String pCmd){
		String sOpt[] = pCmd.split("-");
		int i;

		if (sOpt[0].equalsIgnoreCase("tl.start")){

			i = returnInteger(sOpt[1],-1); 
			System.out.println("Start Single Light: " + i);
			this.controlTL.procTLSingleStart(i);
		}
		else if (sOpt[0].equalsIgnoreCase("tl.stop")){
			i = returnInteger(sOpt[1],-1); 
			System.out.println("Stop Single Light: " + i);
			this.controlTL.procTLSingleStop(i);
		}
		else if (sOpt[0].equalsIgnoreCase("tl.kill")){
			i = returnInteger(sOpt[1],-1); 
			System.out.println("Kill Single Light: " + i);
			this.controlTL.procTLSingleKill(i);
		}
		else if (sOpt[0].equalsIgnoreCase("tl.info")){
			i = returnInteger(sOpt[1],-1);  
			System.out.println("Traffic Light Information Light: " + i);
			//TODO 3LOW [INFO]: Get light object code
			System.out.println("FREQ/CURR STATE/NEXT CHANGE< etc");
		}
		else if (sOpt[0].equalsIgnoreCase("route.info")){
			i = returnInteger(sOpt[1],-1); 
			//System.out.println("Route Info on Internal ROut: " + i);
			this.controlRoute.procRouteInfo(i);
		}
		else {
			System.out.println("The command '" + pCmd + "' is not recognised or not implemented.");
		}

	}

	private int returnInteger(String s, int pDefaultVal){
		int i = pDefaultVal;

		try {
			i = Integer.parseInt(s);
		}
		catch (java.lang.NumberFormatException ex){
			//i = 0;
		}

		return i;
	}

	/**
	 * Display a message, and wait for user input
	 * @param pMessage Message to display to user
	 * @param pDefault The default value to use, if no input supplied.
	 * @return The string the user has supplied
	 */
	private String readSingleLineInput(String pMessage, Object pDefault){
		System.out.print(pMessage + " [" + pDefault + "]> ");

		Scanner inConsole = new Scanner(System.in);
		String sConsole = inConsole.nextLine();

		if (sConsole.length()>0){
			return sConsole;
		}

		return pDefault.toString();
	}

	private void funcPBSSForcePreStart(){
		int iSimRunID = 31;

		String s = this.readSingleLineInput("Obtain SimRunID from DB? [0 = No, 1 = Yes]", "0");

		if (s.equalsIgnoreCase("1")){
			iSimRunID = getSimRunIDFromDB();
			s = "(FROM DB)";
		}
		else {
			s = "";
		}

		s = this.readSingleLineInput("Enter Simulator Run ID " + s + ": ", iSimRunID);

		try {
			iSimRunID = Integer.parseInt(s);

			this.funcPBSSPreStart(false, "USER", iSimRunID);
		}
		catch (NumberFormatException ex){
			System.out.println("Invalid Number Input [SET_SIM-RUN-ID_M1]");
		}
		catch (Exception ex){
			System.out.println("Error [SET_SIM-RUN-ID_M2]");
		}		
	}

	private int getSimRunIDFromDB(){

		int iSimRunID = -1;

		try
		{			
			rmi_interfaces.DBServer db = new rmi_engine.SCHClientImplDB().getSrvObject();
			iSimRunID = db.getSYS_SIMULATOR_RUN().getSimRunID();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			iSimRunID = -1;
		}

		return iSimRunID;
	}

	/**
	 * Resets internal data structures ready for use (or reuse)
	 */
	private void funcPBBSReset(){
		//TODO 2MED [OPS]: Need function to clean any thread objects
		SchRouteResp.clearAll();
		SchRouteChecker.simpleCAClear();
		SchRouteChecker.simpleStopClear();
		SchRouteChecker.simpleTLClear();
	}

	/**
	 * Executes Pre Start Functions
	 * 	- PreStarts are tasks that can be done before the simulator has been officially
	 * 		started by control centre
	 * (often requested by RMI)
	 */
	public void funcPBSSPreStart(boolean pIsRMIRequest, String pSimRunIDSetBy, int pSimRunID){
		util.StateControl.setState(EnumSysState.PRE_START_IN_PROGRESS, true);

		SysConfig.setSimRunID(pSimRunIDSetBy, pSimRunID);

		this.funcPBBSReset();

		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "100", "Module Ops");
		this.funcTLLoad(false);
		this.funcRouteLoad();
		this.funcStopLoad();
		this.funcCALoad();


		/*if (SysConfig.getStateCheck_IsPrestartDone()) 
		{

		}*/

		//during debug test, tell CC ready to start without confirming data
		//NEEDS TO CHANGE IN PRODN ENVIR

		//TODO 2MED [STATE] Need to check that all needed data is OK/obtained, etc before setting state
		util.StateControl.setState(EnumSysState.READY_TO_START, true);

	}

	/**
	 * Starts the entire PBSS Scheduler Module: ie, the Simulator is now running
	 * (often requested by RMI)
	 */
	public void funcPBSSStart(boolean pIsRMIRequest){	

		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "101", "Module Ops", "RMI_REQUEST=" + pIsRMIRequest);

		this.funcTLContStart();
		this.funcTLStartAll();
		this.funcRouteContStart();
		this.funcRouteBusesStart();

		util.StateControl.setState(EnumSysState.IN_OPERATION, true); //Once all the other tasks have started, ready to start

	}

	/**
	 * Stops the entire PBSS Scheduler Module
	 * (often requested by RMI)
	 */
	public void funcPBSSStop(boolean pIsRMIRequest){
		System.out.println("Stopping Scheduler Module...");

		SysConfig.setSimRunID("PBSS_STOP", -2);

		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "102", "Module Ops", "RMI_REQUEST=" + pIsRMIRequest);

		this.funcTLContStop();
		this.funcRemContStop();
		this.funcContRMI_THD_Stop();
		this.funcRouteContStop();
		this.funcRMIServerUnBind();

		util.StateControl.setState(EnumSysState.NOT_OPERATIONAL, true);
	}

	/**
	 * Stops and EXITS the PBSS Scheduler Module
	 */
	public void funcPBSSExit(){
		this.funcSCHOpsStats();
		this.funcPBSSStop(false);
		this.funcTimeContStop();
		this.funcRMIServerKill();

		System.out.println("Stopping Local Control Thread...");
		this.bSysCoreActive=false;
		System.out.println("Scheduler Module Exitted/Terminated");
	}

	/**
	 * Starts the Remote Control Thread
	 *  - Listens for incoming requests and handles them
	 */
	private void funcRemContStart(){
		System.out.println("Starting Remote Control Thread...");

		if (this.thrdControlRemote==null)
			thrdControlRemote = new Thread(controlRemote);

		if (this.thrdControlRemote.isAlive()){
			System.out.println("   FAILED: Remote Control Thread Already Running");
		}
		else{
			thrdControlRemote = new Thread(controlRemote);
			controlRemote.startRemoteControl();
			thrdControlRemote.start();
		}
	}

	/**
	 * Stops the Remote Control Thread
	 */
	private void funcRemContStop(){
		System.out.println("Stopping Remote Control Thread...");

		if (this.thrdControlRemote==null)
			return; //Object has not been created

		if (!this.thrdControlRemote.isAlive()){
			System.out.println("   FAILED: Remote Control Thread Already Stopped");
		}
		else{
			controlRemote.stopRemoteControl();
		}
	}

	/**
	 * Starts the Time Control Thread
	 */
	private void funcTimeContStart(){
		System.out.println("Starting Time Control Thread...");

		if (this.thrdControlTime==null)
			thrdControlTime = new Thread(controlTime);

		if (this.thrdControlTime.isAlive()){
			System.out.println("   FAILED: Time Control Thread Already Running");
		}
		else{
			thrdControlTime = new Thread(controlTime);
			SysConfig.timeControl_StartPolling();
			thrdControlTime.start();
		}
	}

	/**
	 * Stops the Remote Control Thread
	 */
	private void funcTimeContStop(){
		System.out.println("Stopping Time Control Thread...");

		if (this.thrdControlTime==null)
			return; //Object has not been created

		if (!this.thrdControlTime.isAlive()){
			System.out.println("   FAILED: Time Control Thread Already Stopped");
		}
		else{
			SysConfig.timeControl_StopPolling();
		}
	}




	/**
	 * Starts the Traffic Light Control Thread
	 *  - NOTE: Traffic Lights need to be loaded first
	 */
	private void funcTLContStart(){
		System.out.println("Starting TL Control Thread...");

		if (this.thrdControlTL==null){
			thrdControlTL = new Thread(controlTL);
		}

		if (this.thrdControlTL.isAlive()){
			System.out.println("   FAILED: TL Control Thread Already Running");
		}
		else{
			thrdControlTL = new Thread(controlTL);
			controlTL.startTLControl();
			thrdControlTL.start();
		}
	}

	/**
	 * Stops the Traffic Light Control Thread
	 */
	private void funcTLContStop(){
		System.out.println("Stopping TL Control Thread...");
		if (this.thrdControlTL==null)
		{
			return; //Object has not been created
		}

		if (!this.thrdControlTL.isAlive()){
			System.out.println("   FAILED: TL Control Thread Already Stopped");
		}
		else{
			controlTL.stopTLControl();
		}
	}

	/**
	 * Kills traffic light threads
	 */
	private void funcTLKill(){
		System.out.println("Killing TL Threads...");
		if (this.thrdControlTL==null)
			return; //Object has not been created

		if (!this.thrdControlTL.isAlive()){
			System.out.println("   FAILED: TL Control Thread Already Stopped");
		}
		else{
			controlTL.procTLKillAll();
		}
	}

	/**
	 * Loads Traffic Lights from Data Source
	 *  - Creates traffic lights
	 */
	private void funcTLLoad(boolean pStartLight){
		this.controlTL.loadTLObj(pStartLight);
	}

	/**
	 * Starts Traffic Lights Thread
	 *  - Traffic lights need to be created first
	 */
	private void funcTLStartAll(){
		this.controlTL.procTLStartAll();
	}

	/**
	 * Turns on Debug Ping Messages
	 */
	private void funcDebugPingMsgOn(){
		this.controlRemote.debugPingMsg_On();
		this.controlTL.debugPingMsg_On();
		this.controlRoute.debugPingMsg_On();
	}

	/**
	 * Turns off Debug Ping Messages
	 */
	private void funcDebugPingMsgOff(){
		this.controlRemote.debugPingMsg_Off();
		this.controlTL.debugPingMsg_Off();
		this.controlRoute.debugPingMsg_Off();
	}

	/**
	 * Creates a local file
	 * 	- Primary use to create 'file' RMI requests
	 */
	@SuppressWarnings("unused")
	private void funcCreateFile(String sFullFilePath){
		File file = new File(sFullFilePath);

		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Starts the Scheduler module.
	 *  This will put SCH into a mode ready for the CC to start the simulator
	 */
	private void funcRun(){
		//this.funcPBSSPreStart(false);
		this.funcRMIServerBind(false);		//Bind RMI Server
		this.funcRemContStart();			//Start Remote Control Queue Process
		this.funcContRMI_THD();				//Start broadcasting
		this.funcTimeContStart(); 			//Start time

	}


	/**
	 * Starts the RMI IP Broadcast/Registration Thread
	 */
	private void funcContRMI_THD()
	{
		Thread runThread; 
		runThread = new Thread(new RMIControlTHD());

		//start the thread up
		runThread.start();
	}

	private void funcContRMI_THD_Stop(){
		SysConfig.setStopIPRegistration(true);
	}

	/**
	 * Starts the Traffic Light Control Thread
	 *  - NOTE: Traffic Lights need to be loaded first
	 */
	private void funcRouteContStart(){
		System.out.println("Starting Route Control Thread...");

		if (this.thrdControlRoute==null){
			thrdControlRoute = new Thread(controlRoute);
		}

		if (this.thrdControlRoute.isAlive()){
			System.out.println("   FAILED: Route Control Thread Already Running");
		}
		else{
			thrdControlRoute = new Thread(controlRoute);
			controlRoute.startRouteControl();
			thrdControlRoute.start();
		}
	}

	/**
	 * Stops the Traffic Light Control Thread
	 */
	private void funcRouteContStop(){

		System.out.println("Stopping Route Control Thread...");
		if (this.thrdControlRoute==null)
		{
			return; //Object has not been created
		}

		if (!this.thrdControlRoute.isAlive()){
			System.out.println("   FAILED: Route Control Thread Already Stopped");
		}
		else{
			controlRoute.stopRouteControl();
		}
	}

	/**
	 * Loads Routes 
	 */
	private void funcRouteLoad(){
		this.controlRoute.loadRouteList();
	}

	/** Loads Stops
	 * 
	 */
	private void funcStopLoad(){
		this.controlRoute.loadStopList();
	}

	/** Loads Congested Areas
	 * 
	 */
	private void funcCALoad(){
		this.controlRoute.loadCongAreaList();
	}


	/**
	 * Starts the buses
	 */
	private void funcRouteBusesStart(){
		this.controlRoute.procRouteBusStartAll();
	}

	/**
	 * Stops the buses
	 */
	private void funcRouteBusesStop(){
		this.controlRoute.procRouteBusStopAll();
	}

	/**
	 * Kills the buses (kill, meaning all their threads are ended)
	 */
	private void funcRouteBusesKill(){
		this.controlRoute.procRouteBusKillAll();
	}

	private void funcCommsInfo(){
		System.out.println("---- Comms Information ----");
		System.out.println("** Broadcast Details **");
		System.out.println("CC Reg Complete:   " + SysConfig.getIPregistrationCompleted());
		System.out.println("");
		System.out.println("** Module Details **");
		System.out.println(funcCommsInfo_Formatted(EnumModName.SCHEDULER));
		System.out.println("");
		System.out.println(funcCommsInfo_Formatted(EnumModName.CONTROL_CENTRE));
		System.out.println(funcCommsInfo_Formatted(EnumModName.SETUP));
		System.out.println(funcCommsInfo_Formatted(EnumModName.DB));
		System.out.println(funcCommsInfo_Formatted(EnumModName.CME));
		System.out.println(funcCommsInfo_Formatted(EnumModName.NAVIGATOR));
		System.out.println(funcCommsInfo_Formatted(EnumModName.TRACKER));
	}

	private String funcCommsInfo_Formatted(EnumModName pMod){
		NetIPInfo ipInfo = SysConfig.getIPInfo(pMod);

		return pMod.getModAbbrev() + " = " + ipInfo.getIPAddress() + ":" + ipInfo.getPort() + " (" + ipInfo.getRMIName() + "), set by " + ipInfo.getSetBy();  
	}

	/**
	 * Bind the local RMI Server
	 */
	private void funcRMIServerBind(boolean pDoRebind){
		funcRMIServerKill();

		if (pDoRebind)
			Debug.writeDebugMsg("SchMainControl", "funcRMIServerBind", "Attempting to perform RMI rebind...");
		else
			Debug.writeDebugMsg("SchMainControl", "funcRMIServerBind", "Attempting to perform RMI bind...");

		String sResp = this.controlRemote.doRMIServerBind(pDoRebind);
		Debug.writeDebugMsg("SchMainControl", "funcRMIServerBind", "    Outcome = " + sResp);
	}

	/**
	 * UnBind the local RMI Server
	 */
	private void funcRMIServerUnBind(){
		Debug.writeDebugMsg("SchMainControl", "funcRMIServerUnBind", "Attempting to perform RMI unbind...");
		String sResp = this.controlRemote.doRMIServerUnBind();
		Debug.writeDebugMsg("SchMainControl", "funcRMIServerUnBind", "    Outcome = " + sResp);
	}

	private void funcRMIServerKill(){
		this.controlRemote.doRMIServerKill();
	}

	/**
	 * Set the RMI Host Address
	 */
	private void funcNETSetCCInfo(){

		//TODO 8FORNAV: Ability to force set CC info

		this.funcSetNetInfo(EnumModName.CONTROL_CENTRE);

		String s = readSingleLineInput("Force CC Registration Completed Flag? [0 = No, 1 = Yes]","0");

		if (s.equalsIgnoreCase("1"))
			SysConfig.setIPregistrationCompleted(true);
		//else
		//SysConfig.setIPregistrationCompleted(false);

	}

	/**
	 * Set the RMI Port Address for local RMI Registry
	 */
	private void funcRMIServerSetPort(){

		NetIPInfo myIPInfo = SysConfig.getIPInfo(EnumModName.SCHEDULER);

		int iPort = myIPInfo.getPort();
		String s = readSingleLineInput("Set new RMI Server Listening Port",iPort);

		try {
			iPort = Integer.parseInt(s);

			if ((iPort > 0) && (iPort < 65535)){
				//SCHRMIInfo.setRMI_RegPort(iPort, "USER");
				//Add self info
				SysConfig.addIPInfo(SysConfig.getMyModuleName(), new NetIPInfo(myIPInfo.getIPAddress(),iPort, SysConfig.getMyModuleName().getModAbbrev(),"USER"));

				System.out.println("RMI Server Listening port has been successfully changed to:" + iPort + " [RMI_SET_PORT_M1]");
			}
			else {
				System.out.println("Invalid Port Specification [RMI_SET_PORT_M2]");
			}
		}
		catch (NumberFormatException ex){
			System.out.println("Invalid Number Input [RMI_SET_PORT_M3]");
		}
		catch (Exception ex){
			System.out.println("Error [RMI_SET_PORT_M4]");
		}
	}

	//TODO 8FORNAV: Add ECHO to TKR Support; also do one for SCH
	private void funcTestTKREcho(){
		String sEchoText = "Echo from SCH";
		sEchoText = readSingleLineInput("Echo Text to TKR",sEchoText);

		//Create a new Instance for TKR
		try {
			rmi_interfaces.TKRServer testEcho = new rmi_engine.SCHClientImplTKR().getSrvObject();
			System.out.println("@: " + testEcho.debugEcho("SCH", sEchoText));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		System.out.println("----- (End Echo Request) -----");
	}

	private void funcTestNAVEcho(){
		String sEchoText = "Echo from SCH";
		sEchoText = readSingleLineInput("Echo Text to NAV",sEchoText);

		//Create a new Instance for NAV
		try {
			rmi_interfaces.NAVServer testEcho = new rmi_engine.SCHClientImplNAV().getSrvObject();
			System.out.println("@: " + testEcho.debugEcho("NAV", sEchoText));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		System.out.println("----- (End Echo Request) -----");
	}

	private void funcTestDBEcho(){
		String sEchoText = "Echo from SCH";
		sEchoText = readSingleLineInput("Echo Text to DB",sEchoText);

		//Create a new Instance for DB
		try {
			@SuppressWarnings("unused")
			rmi_interfaces.DBServer testEcho = new rmi_engine.SCHClientImplDB().getSrvObject();
			//TODO 9NOTE [SG] DB does not support echo
			//System.out.println("@: " + testEcho.debugEcho("DB", sEchoText));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		System.out.println("----- (End Echo Request) -----");
	}

	private void funcSysInfo(){
		System.out.println("----- PBSS Scheduler System Information -----");
		System.out.println("Application Start At: " + Formatter.formatDate(SysConfig.getAppStartTime()));
		System.out.println("  Running Time (Sec): " + SysConfig.getAppRunTime() + " second(s)");
		System.out.println("  Current State:    " + util.StateControl.getCurrentState().getState());
		System.out.println("");
		System.out.println("Simulator Run ID = " + SysConfig.getSimRunID());
		System.out.println("          Set At = " + Formatter.formatDate(SysConfig.getSimRunSetAt()));
		System.out.println("          Set By = " + SysConfig.getSimRunSetBy());
		System.out.println("");
		System.out.println("Max Distance between points for NAV = " + SysConfig.getMaxNavDistance());
		System.out.println("");
		System.out.println("Timeout for Response from NAV to be recd = " + SysConfig.getTimeOutNAVRequest() + " milliseconds");
		System.out.println("If a timeout from NAV is detected, time to wait before killing = " + SysConfig.getTimeOutNAVRequestAutoKill() + " milliseconds");
	}

	private void funcObjectsCAList(){
		SchRouteChecker.listCongArea();
	}

	private void funcObjectsStopList(){
		SchRouteChecker.listStop();
	}

	private void funcObjectsTLList(){
		SchRouteChecker.listTL();
	}

	private void funcNavReqList(){
		SchRouteResp.debugListAll();
	}

	private void funcSCHOpsStats(){
		System.out.println("===== PBSS Scheduler System Statistics =====");
		System.out.println("Application Start At: " + Formatter.formatDate(SysConfig.getAppStartTime()));
		System.out.println("  Running Time (Sec): " + SysConfig.getAppRunTime() + " second(s)");
		System.out.println("");
		System.out.println("---- Navigator Based Statistics ----");
		System.out.println("Requests to:");
		System.out.println("........................Made = " + SysConfig.getPerfStat_NAVReq());
		System.out.println(".....................Timed Out = " + SysConfig.getPerfStat_NAVReqTimeOut());
		System.out.println("..Timed Out and aborted by SCH = " + SysConfig.getPerfStat_NAVReqTimeOutWithKill());
		System.out.println("");
		System.out.println("Responses from:");
		System.out.println("........Total Received = " + SysConfig.getPerfStat_NAVRespTot());
		System.out.println(".......with Move Granted = " + SysConfig.getPerfStat_NAVRespGranted());
		System.out.println("........with Move Denied = " + SysConfig.getPerfStat_NAVRespDenied());
		System.out.println("");
		System.out.println("");
		System.out.println("---- Tracker Based Statistics ----");
		System.out.println("Requests to:");
		System.out.println("....Total Made = " + SysConfig.getPerfStat_TKRReq());
		System.out.println(".........TKR ACK = " + SysConfig.getPerfStat_TKRReqOK());
		System.out.println(".........TKR NAK = " + SysConfig.getPerfStat_TKRReqFail());
		System.out.println("");
		System.out.println("");
		System.out.println("---- Internal Statistics ----");
		System.out.println("Passenger Movement Calculation Requests:");
		System.out.println("..................Total Made = " + SysConfig.getPerfStat_PaxMoveReqMade());
		System.out.println("............Process Call Count = " + SysConfig.getPerfStat_PaxMoveReq());
		System.out.println("..........Loop Protection Trip = " + SysConfig.getPerfStat_PaxMoveLoopTrip());
		System.out.println("");
	}

	private void funcSetNetInfo(EnumModName pMod){
		NetIPInfo ipInfo = SysConfig.getIPInfo(pMod);

		String sIP = ipInfo.getIPAddress();
		int iPort = ipInfo.getPort();
		boolean bIsOK = false;

		sIP = readSingleLineInput("Set New IP Address for " + pMod.getModName(), sIP);

		try {
			bIsOK = true;
			System.out.println("RMI Server IP Address to be changed to:" + sIP + " [RMI_SET_IP_M1]");
		}
		catch (Exception ex){
			bIsOK = false;
			System.out.println("Error [RMI_SET_IP_M4]");
		}


		if (bIsOK){
			System.out.println("MQ Debug Ports = CC: 25000, DB = 25001, SU = 25002, CME = 25003, NAV = 25004, SCH = 25005, TKR = 25006");
			String s = readSingleLineInput("Set New IP Port for " + pMod.getModName(),iPort);

			try {
				iPort = Integer.parseInt(s);

				if ((iPort > 0) && (iPort < 65535)){
					bIsOK = true;
					System.out.println("RMI Server Listening port to be changed to:" + iPort + " [RMI_SET_PORT_M1]");
				}
				else {
					bIsOK = false;
					System.out.println("Invalid Port Specification [RMI_SET_PORT_M2]");
				}
			}
			catch (NumberFormatException ex){
				bIsOK = false;
				System.out.println("Invalid Number Input [RMI_SET_PORT_M3]");
			}
			catch (Exception ex){
				bIsOK = false;
				System.out.println("Error [RMI_SET_PORT_M4]");
			}
		}

		if (bIsOK){
			SysConfig.addIPInfo(pMod, new NetIPInfo(sIP,iPort, pMod.getModAbbrev(),"USER_CONSOLE"));
			System.out.println("Network Information Updated for " + pMod.getModName());
			System.out.println(funcCommsInfo_Formatted(pMod));
		}
	}
}
