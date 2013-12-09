/**
 * 
 */
package pbss.rmi.nav;

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rawobjects.ReqBusMove;

/**
 * @author Raja Noman
 *RMI server listening on 1445
 */
@SuppressWarnings("serial")
public class NAVMethodListenerImpl 
						extends UnicastRemoteObject
								implements NAVMethodsListener, Serializable{
	private String mHost ="";
	private int mPort =1445;
	private Registry mRMIRegistry;
	
	protected NAVMethodListenerImpl() throws RemoteException {
		super();
	String vName = "NAVRMIListener";
		
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
		
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	/**
	 * Implemented methods of "Interface".
	 */

	@Override
	public boolean BusMovePermission(ReqBusMove pMoveReq)
			throws RemoteException {
		// TODO Auto-generated method stub
		// here a 'mediator' method for the responce.
		//can fill in this method also but recomended to use another NON-RMI mehtod.
		
		return false;
	}

	@Override
	public void SimTimeShare() throws RemoteException {
		// TODO Auto-generated method stub
		//Information from SCH/NAV regarding the simulation time.
	}

	@Override
	public void startupCC() throws RemoteException {
		// TODO Auto-generated method stub
		// information required from CC
	}
	
	
	public static void main(String []args){
       
		
		
		try{
			
			NAVMethodListenerImpl vNListener = new NAVMethodListenerImpl();
			
		}catch(Exception  e){
			e.printStackTrace();
		}
	}

}
