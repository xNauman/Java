/**
 * 
 */
package pbss.rmi.methodscaller;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author Raja Noman
 *
 */

public class NAVCaller{
	
	/**
     * Navigator methods;
     */
	private NAVMethodsListener mNAVObject;
	private Registry mRemoteRegistry;
	private String mDBServerAddress = "127.0.0.1";
	private String mDBServerPort = "1445";

	/**
	 * Constructor
	 */
	public NAVCaller(){
		
		
		try{		
		//finding the registry.
		mRemoteRegistry = LocateRegistry.getRegistry(
				mDBServerAddress,
				(new Integer(mDBServerPort).intValue()));
		
		System.out.println("Creating Remote-Object");
		/**
		 * get the object from the remote-registry
		 */		

//DBServerObject = (DBServer)mRemoteRegistry.lookup("SCHServer");
	
	}catch(Exception e){
		e.printStackTrace();
	}
}
	/**
	 * Method call BusMovePermission
	 */
       public boolean NAVBusMovePermission(){  	   
    	//   
    	   rawobjects.ReqBusMove vMoveReq = new rawobjects.ReqBusMove();
    	  boolean vResponce;
    	   /**
    	    * chek if registry is valid.
    	    */
    	   if(this.mRemoteRegistry != null){
    		 
    		   try {
						mNAVObject = (NAVMethodsListener)this.mRemoteRegistry.lookup("NAVRMIListener");
						vResponce =mNAVObject.BusMovePermission(vMoveReq);
						return vResponce;
					} catch (AccessException e) {
						
						System.out.println("Error Exception AccessException");
						e.printStackTrace();
					} catch (RemoteException e) {
						
						System.out.println("Error Exception RemoteException");
						e.printStackTrace();
					} catch (NotBoundException e) {
						
						System.out.println("Error Exception NotBoundExcption");
						e.printStackTrace();
					}     		
    	   }
    	   else return false;
    	   
    	   
		return true;
    	   
       }/** End of NAVCaller Class*/
	

 public static void main(String []args){
	 
	NAVCaller vNAVMethodsCaller = new NAVCaller();
	vNAVMethodsCaller.NAVBusMovePermission();
	
	}
}



