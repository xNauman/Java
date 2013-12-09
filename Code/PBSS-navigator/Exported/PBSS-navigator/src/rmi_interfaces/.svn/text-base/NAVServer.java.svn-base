package rmi_interfaces;

//Modified by Ben to include UUID as Request ID (from integer)

import java.util.UUID;

/**
 * RMI interface file for the Navigator subsystem
 * @version 1456 - SYD MQ SVN
 * @author David Taberner
 *
 */


/*

* Operational States:
*     0: Not Operational 
*     1: Pre-Start Completed 
*     2: Ready to process simulation ?? REMOVE?
*     3: Started, and Operational
*
*/
public interface NAVServer extends java.rmi.Remote {



	/*
	*
	*
	*     COMMON RMI Methods
	*
	*/	
		
	      /**
	       * Interface will be responsible for causing Navigator to enter is pre-start sequence
	       * 
	       * NOTES: Nav need to be in state 0 for this method to be able to be called
	       * Calling the interface will cause Nav to move into state 1
	       * 
	       * @return True or False that will state if its possible for the subsystem to start
	       * 
	       */
	      public boolean startupNAV() throws java.rmi.RemoteException;
	      
	      
	      /**
	       * Interface will be responsible for causing Navigator to stop operation
	       * NAV will refuse to accept further movement requests but will complete any movement
	       * requests that are currently being processed
	       * 
	       * NOTES: Nav will need to be in state 3 for the method to be able to be called
	       * Calling it will cause nav to move into state 0
	       * 
	       * @return true or false: true will be returned if a stop is possible, false will be returned
	       * if it is possible for navigator to stop
	       * @throws java.rmi.RemoteException
	       */
	      public boolean stopNAV() throws java.rmi.RemoteException;

	      /**
	       * Interface will return the current operational state of the Navigator system.
	       * @return Current operational state of the system from 0 - 3
	       */
	      public Integer Navstate() throws java.rmi.RemoteException;
	            
	      /**
	       * Nav will return its current simulation time as per the clock object
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
	       * @param pMessageToEcho - The message that will be echod back
	       * @return A string that exactly the same as pMessageToEcho but contains the source and destination
	       */
	      public String debugEcho(String pFromModule, String pMessageToEcho) throws java.rmi.RemoteException;
	   
	/*
	*
	*
	*     navigator specific RMI Methods
	*
	*/

	      /**
	       * Interface provides ability for sch to submit a request for a bus movement to navigator for approval. 
	       * 
	       * @param pNavigationRequestObject - Navigation object that contains all the information required for the request including the bus object
	       * @param pMoveRequestUUID - unique identifier of the navigation request (shared by SCH)
	       * @return True or False, stating if the request has been received correctly and is in the expected format
	       */
	      public boolean MovementRequest(rawobjects.NavRequest pNavigationRequestObject, UUID pMoveRequestUUID) throws java.rmi.RemoteException;   
	      
	      
	
}
