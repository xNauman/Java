package rmi_interfaces;

/**
 * Example interface file from Navigator showing suggested common interfaces and operational states
 * @author David Taberner
 *
 */


/*

* Operational States:
*     0: Not Operational 
*     1: Pre-Start Completed 
*     2: Ready to Start 
*     3: Started, and Operational
*
*/

public interface NAVRMIInterface extends java.rmi.Remote{
	
		//Interface will be responsible for causing Navigator to enter is pre-start sequence
		//True will be returned if startup is possible
		//if startup is not possible for some reason a false will be returned
		//NOTES: Nav need to be in state 0 for this method to be able to be called
		// Calling the interface will cause Nav to move into state 1
      public boolean startupNAV() throws java.rmi.RemoteException;
      
		//Interface will be responsible for causing Navigator to stop operation
		// NAV will refuse to accept further movement requests but will complete any movement
		//requests that are currently being processed
		// True will be returned if stop is possible
		// False will be returned if its not possible for navigator to stop
		//NOTES: Nav will need to be in state 3 for the method to be able to be called
		// Calling it will cause nav to mvoe into state 0
		public boolean stopNAV() throws java.rmi.RemoteException;

      //Interface will return the current operational state of the Navigator system.
      public Integer Navstate() throws java.rmi.RemoteException;
            
      //Nav will return its current simulation time as per the clock object
      public long SimTimeShare();
      
      
      //other navigator specific interface items below
      
      
}
