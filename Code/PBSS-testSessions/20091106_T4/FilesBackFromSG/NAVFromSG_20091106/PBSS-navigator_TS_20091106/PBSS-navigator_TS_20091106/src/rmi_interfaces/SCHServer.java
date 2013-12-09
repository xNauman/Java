package rmi_interfaces;

/**
 * RMI Interfaces for Scheduler
 * 
 * @author Ben
 *
 */

public interface SCHServer extends java.rmi.Remote {

	//Common Interface Methods


	/**
	 * Interface will be responsible for causing Scheduler to enter is pre-start sequence
	 * 
	 * Prepares to start the scheduler system, of importance here is that the Simulator Run ID is set.
	 * SCH to be in State 0, and will cause SCH to progress into State 1
	 * @param pRequestedBy The module requesting that SCH Starts
	 * @param pSimulatorRunID The simulator run ID to use
	 * @return If request was successful or not.
	 * @throws java.rmi.RemoteException
	 */
	boolean startUpSCH(int pSimulatorRunID) throws java.rmi.RemoteException;
	
	/**
	 * Interface will be responsible for causing Scheduler to start (i.e, begin moving buses)
	 * SCH to be in State 2, and will cause SCH to progress into State 3
	 * Starts the Scheduler System (called from CC)
	 * @return If request was successful or not.
	 * @throws java.rmi.RemoteException
	 */
	boolean startSCH() throws java.rmi.RemoteException;
	
	/**
	 * Interface will be responsible for causing Scheduler to stop operation
	 * SCH will stop process new bus movement requests but will complete any movement
	 * requests that are currently being processed.
	 * 
	 * SCH needs to be in State 3 for the method to be called.
	 * Will cause SCH to progress into State 0.
	 * 
	 * Stops the Scheduler System (called from CC)
	 * @return If request was successful or not.
	 * @throws java.rmi.RemoteException
	 */
	boolean stopSCH() throws java.rmi.RemoteException;
	

	/**
	 * Interface will return the current operational state of the Scheduler system.
	 * 	State 0: Not Operational
	 *  State 1: Pre-start In Progress
	 *  State 2: Ready to Start (Pre-Start Completed)
	 *  State 3: Currently In Operation
	 * @return Current operational state of the system from 0 - 3
	 * 	
	 */
	public Integer SCHState() throws java.rmi.RemoteException;

	/**
	 * SCH will return its current simulation time as per the clock object
	 * 
	 * @return a long data type that contacts the simulation time that this module is at
	 */
	public long SimTimeShare() throws java.rmi.RemoteException;

	 /**
     * Used for debugging purposes, echos the current module name, the module requesting the echo
     * and any text provided that is to be echoed
     * 
     * For example: SCH calls: NavigatorRMI.debugEcho("SCH","Echo Hello")
     * The response would be: "NAV.ECHO: From SCH = Echo Hello"
     * Used for debugging purposes, echos the current module name, the module requesting the echo
     * and any text provided that is to be echoed
     * 
     * 
     * @param pFromModule - The module that's making the request
     * @param pMessageToEcho - The message that will be echoed back
     * @return A string that exactly the same as pMessageToEcho but contains the source and destination
     */
	String debugEcho(String pFromModule, String pMessageToEcho) throws java.rmi.RemoteException;


	//SCHEDULER Specific Methods

	/**
	 * Accepts a response back from NAV
	 * @param pMoveRequestUUID Response relates to SCH request ID
	 * @param moveAccept Was this move accepted?
	 * @return If the message has been accepted by SCH
	 * @throws java.rmi.RemoteException
	 */
	boolean responseFromNav(java.util.UUID pMoveRequestUUID, boolean moveAccept) throws java.rmi.RemoteException;

}
