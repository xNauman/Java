/**
 * 
 */
package pbss.rmi.sch;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Raja Noman
 * 
 * RMI server is running on local RMI - Registry. 
 * Registry Server on Port  "1440"
 */
public class SCHMethodsListenerImpl 
				extends UnicastRemoteObject 
				implements SCHMethodListener,Serializable {

	
	/**
	 * Local Members
	 * */
	private String mHost ="";
	private int mPort =1440;
	private Registry mRMIRegistry;
	
	private static final long serialVersionUID = 8373647357170275263L;
	/* *
	 * (non-Javadoc)
	 * @see pbss.rmi.sch.SCHMethodListener#startupCC(int)
	 */
	
	public SCHMethodsListenerImpl() throws RemoteException {
		super();
	
		
		String vName = "SCHRMIListener";
		
		//replacing InetAdddress.getLocalHost() by Inetddress.getByAddress()
		 try{
			 this.mHost = (InetAddress.getByAddress("127.0.0.1",new byte[]{127,0,0,1})).toString();
		 }catch(Exception e){
			 throw new RemoteException("Can not get inet address");
		 }
		 
		 
		 System.out.println("Starting RMI registry at:"+mHost+"port:"+mPort);
/**
* Creating the registry and binding the name.
*/
		
		try{
			mRMIRegistry = LocateRegistry.createRegistry(mPort);
			mRMIRegistry.rebind(vName, this);
			
			
		//Naming.rebind("rmi://127.0.0.1:1099/"+vName, this);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		

		
	}
/**
 * @param int pSimulatiorID
 * <br> .
 */
	
	@Override
	public void startupCC(int pSimulatorID) throws RemoteException {
		// TODO Auto-generated method stub
		//this ID will be passed to the

	}
	
	
	
	/*public static void main(String[] args){
		try{
			SCHMethoListenerImpl vListener = new SCHMethoListenerImpl();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}*/
	

}
