package main;

import java.util.Scanner;

import rawobjectsPriv.EnumModName;
import rawobjectsPriv.EnumSysState;
import rawobjectsPriv.NetIPInfo;
import config.SysConfig;

public class InputTHD implements Runnable {

	private boolean bSysCoreActive = true; //Set this to stop local input thread

	public void run() {

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

	private void printCmdPromptText(){
		System.out.print("[MENU]> ");
	}
	private void doCmdConsole(String pCmd){
		pCmd = pCmd.toLowerCase();

		if (pCmd.equalsIgnoreCase("help")){
			System.out.println("PBSS Navigator: Local Console Help");
			System.out.println("----------------------------------");
			System.out.println("[!] = Not Implemented");
			System.out.println("[^] = Deprecated (no longer operational)");
			System.out.println("[*] = Untested Code; use with care!");
			System.out.println("[R] = Uses RMI ");
			System.out.println("");

			System.out.println("* System Operations:");
			System.out.println("   [R] prestart.force: Loads details from database");
			System.out.println("   [!] help.run: Help on Running the simulator");
			System.out.println("   stop.console: Stops and exits/terminates the entire Scheduler Module");
			System.out.println("");
			System.out.println("* Communication Operations:");
			System.out.println("   comms.info: Details of Network Comms");

		}
		else if (pCmd.contains("help.run")){
			System.out.println("How to Run the Simulator:");
			System.out.println("  1. ....I");


			System.out.println("  To Stop Console Input: execute 'stop.console'");
		}
		else if (pCmd.equalsIgnoreCase("prestart.force")||pCmd.equalsIgnoreCase("p.f")){
			this.funcPBSSForcePreStart();
		}
		else if (pCmd.contains("stop.console")){
			this.bSysCoreActive = false;
		}
		else if (pCmd.contains("comms.info")){
			this.funcCommsInfo();
		}
		else {
			System.out.println("The command '" + pCmd + "' is not recognised or not implemented.");
		}
	}

	private void doCmdConsoleSepParam(String pCmd){
		String sOpt[] = pCmd.split("-");
		int i;

		//Not implemented, example only
		if (sOpt[0].equalsIgnoreCase("tl.start")){
			i = returnInteger(sOpt[1],-1); 
			System.out.println("Start Single Light: " + i);
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

		String s = this.readSingleLineInput("Enter Simulator Run ID: ", iSimRunID);

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

	public void funcPBSSPreStart(boolean pIsRMIRequest, String pSimRunIDSetBy, int pSimRunID){

		//Code taken from pure RMI call - may need to be tweaked
		
		rawobjectsPriv.EnumSysState vState = utils.StateControl.getCurrentState(); 

		if(vState != rawobjectsPriv.EnumSysState.NOT_OPERATIONAL)
		{
			System.out.println("NAV State - Not Operational");
		}
		//start threads and return true
		else
		{
			//start prestart thread and return
			Thread runThread; 
			runThread = new Thread(new PreStartTHD());
			runThread.start();

			System.out.println("Prestart Commenced by Force");
		}

		SysConfig.setSimRunID(pSimRunIDSetBy, pSimRunID);
	}

	private void funcCommsInfo(){

		System.out.println("---- Comms Information ----");
		System.out.println("** Broadcast Details **");
		System.out.println("CC Reg Complete:   " + SysConfig.getIPregistrationCompleted());
		System.out.println("");
		System.out.println("** Module Details **");
		System.out.println(funcCommsInfo_Formatted(EnumModName.NAVIGATOR));
		System.out.println("");
		System.out.println(funcCommsInfo_Formatted(EnumModName.CONTROL_CENTRE));
		System.out.println(funcCommsInfo_Formatted(EnumModName.SETUP));
		System.out.println(funcCommsInfo_Formatted(EnumModName.DB));
		System.out.println(funcCommsInfo_Formatted(EnumModName.CME));
		System.out.println(funcCommsInfo_Formatted(EnumModName.SCHEDULER));
		System.out.println(funcCommsInfo_Formatted(EnumModName.TRACKER));
	}

	private String funcCommsInfo_Formatted(EnumModName pMod){
		NetIPInfo ipInfo = SysConfig.getIPInfo(pMod);

		return pMod.getModAbbrev() + " = " + ipInfo.getIPAddress() + ":" + ipInfo.getPort() + " (" + ipInfo.getRMIName() + "), set by " + ipInfo.getSetBy();  
	}


}
