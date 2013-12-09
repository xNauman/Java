/**
 * 
 */
package pbss.rmi.nav;

/**
 * @author Admin
 *
 */
public interface NAVMethodsListener extends java.rmi.Remote{
	
      public void startupCC() throws java.rmi.RemoteException;
      
      public boolean BusMovePermission(rawobjects.ReqBusMove pMoveReq) throws java.rmi.RemoteException;
      
      public void SimTimeShare() throws java.rmi.RemoteException;
}
