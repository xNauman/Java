package rmi_dummy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

//ONLY rmi_interfaces should exist here
import rmi_interfaces.DBServer;
import rmi_interfaces.NAVServer;
import rmi_interfaces.SCHServer;

/**
 * Dummy RMI implementation for Control Centre
 * @author Ben
 *
 */

//!!!!!!!!!!!!!!!!!!!!!! IMPORTANT !!!!!!!!!!!!!!!!!!!!!!!
/*
 * This uses DummyRMI_ImplSCH to aid in quick development.
 * NOTE, in the actual implementation, the use of
 * DummyRMI_ImplSCH and, all rmi_engine packages will not
 * be available.
 */
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class DummyRMI_CC_ClientImpl {

	private boolean mDoExit=false;

	public static void main (String args[]){

		DummyRMI_CC_ClientImpl cc = new DummyRMI_CC_ClientImpl();
		cc.ccMenuLoop();
	}

	public void ccMenuLoop(){
		System.out.println("PBSS Dummy Control Centre (from SCH)");
		System.out.println("'help' for Console Help.");
		System.out.println("");
		System.out.println("");

		while (!(mDoExit))
		{
			System.out.print("> ");
			Scanner inConsole = new Scanner(System.in);
			String sConsole = inConsole.nextLine();

			if (sConsole.length()>0){
				doCmdConsole(sConsole);
			}
		}
	}

	private void doCmdConsole(String pCmd){
		pCmd = pCmd.toLowerCase();

		if (pCmd.equalsIgnoreCase("help")){
			System.out.println("PBSS Control Centre: DUMMY CLIENT FROM SCH.");
			System.out.println("----------------------------------");
			System.out.println("exit: Exit CC Dummy");
			System.out.println("");
			System.out.println("ps: Pre Start the System");
			System.out.println("Start: Start the System");
			//System.out.println("");
			//System.out.println("sys.create: Creates a Simulator Run Entry.");
			//System.out.println("");
			System.out.println("sch.start: Send Scheduler a START command via RMI.");
			System.out.println("sch.stop: Send Scheduler a STOP command via RMI.");
		}
		else if (pCmd.equalsIgnoreCase("exit")){
			this.mDoExit=true;
		}
		else if (pCmd.equalsIgnoreCase("ps")){
			this.funcPreStart();
		}
		else if (pCmd.equalsIgnoreCase("start")){
			this.funcStart();
		}
		/*else if (pCmd.equalsIgnoreCase("sys.create")){
			//this.funcDBCreateSimRun(500,1);
		}*/
		else if (pCmd.equalsIgnoreCase("sch.start")){
			this.funcSCHStart();
		}
		else if (pCmd.equalsIgnoreCase("sch.stop")){
			this.funcSCHStop();
		}
		else if (pCmd.equalsIgnoreCase("nav.start")){
			this.funcNAVPreStart();
		}
		else if (pCmd.equalsIgnoreCase("nav.stop")){
			this.funcNAVStop();
		}
		else {
			System.out.println("The command '" + pCmd + "' is not recognised or not implemented.");
		}
	}

	private String readSingleLineInput(String pMessage, Object pDefault){
		System.out.print(pMessage + " [" + pDefault + "]> ");

		Scanner inConsole = new Scanner(System.in);
		String sConsole = inConsole.nextLine();

		if (sConsole.length()>0){
			return sConsole;
		}

		return pDefault.toString();
	}

	/*** **********************/

	private void funcPreStart(){
		String sRunID = Integer.toString(sysGetNextSimRunID()+1);
		sRunID = readSingleLineInput("Enter Simulator Run ID:", sRunID);

		String sMapID = "1";
		sMapID = readSingleLineInput("Enter Map ID to Use:", sMapID);

		boolean bOK = false;
		boolean bErr = false;

		try {
			bOK = funcDBCreateSimRun(Integer.parseInt(sRunID),Integer.parseInt(sMapID));
		}
		catch (Exception e){
			bOK=false;
			bErr=true;
			e.printStackTrace();
		}
		
		if (bOK){
			funcSCHPreStart(Integer.parseInt(sRunID));
			funcNAVPreStart();
			
		}
		else {
			if (bErr)
				System.out.println("DB Response Error!");
			else
				System.out.println("DB Response Failure!");
		}
	}

	private void funcStart(){
		funcSCHStart();
	}

	private boolean funcDBCreateSimRun(int pSimRunID, int pMapToUseID){
		boolean bOK = false;
		try {
			DBServer db = (DBServer) new DummyRMI_ImplDB().getSrvObject();
			bOK = db.createSimRunEntry(pSimRunID, pMapToUseID);
			System.out.println("DB Response to Create SimRUN= " + bOK);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

		return bOK;
	}

	private void funcSCHPreStart(int pSimRunID){
		try {
			SCHServer sch = (SCHServer) new DummyRMI_ImplSCH().getSrvObject();
			System.out.println("SCH Response = " + sch.startUpSCH(pSimRunID));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	private void funcSCHStart(){
		try {
			SCHServer sch = (SCHServer) new DummyRMI_ImplSCH().getSrvObject();
			System.out.println("SCH Response = " + sch.startSCH());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	private void funcSCHStop(){
		try {
			SCHServer sch = (SCHServer) new DummyRMI_ImplSCH().getSrvObject();
			System.out.println("SCH Response = " + sch.stopSCH());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private void funcNAVPreStart(){
		try {
			NAVServer nav = (NAVServer) new DummyRMI_ImplNAV().getSrvObject();
			System.out.println("NAV Response = " + nav.startupNAV());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	private void funcNAVStop(){
		try {
			NAVServer nav = (NAVServer) new DummyRMI_ImplNAV().getSrvObject();
			System.out.println("NAV Response = " + nav.stopNAV());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	private int sysGetNextSimRunID(){
		try {
			DBServer db = (DBServer) new DummyRMI_ImplDB().getSrvObject();
			return db.getMaxSimRunID();
		} catch (RemoteException e) {
			e.printStackTrace();
			return -99;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return -99;
		}
	}
}
