package rmi_dummy;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.UUID;

import rawobjects.NavRequest;
import rmi_interfaces.NAVServer;
import rmi_interfaces.SCHServer;

public class DummyRMI_NAV_SrvImpl 
	extends UnicastRemoteObject 
	implements NAVServer {
	private static final long serialVersionUID = 2897918669229937385L;
	
	private int mPort = 25004;
	private Registry mLocalRegistry;

	@Override
	public String debugEcho(String pFromModule, String pMessageToEcho) throws RemoteException {
		System.out.println("ECHO REQUEST - From: " + pFromModule.toUpperCase() + "=" + pMessageToEcho);
		return getMyDisplayName() + ".ECHO:"  + pMessageToEcho;
	}

	private String getMyDisplayName(){
		return "NAV";
	}
	
	//--------- RMI SERVER CODE
	public DummyRMI_NAV_SrvImpl()throws RemoteException{		
		super();

		System.out.println(getMyDisplayName() + " - Starting Local Registry on port " + mPort);

		try{
			mLocalRegistry = LocateRegistry.createRegistry(mPort);
			mLocalRegistry.rebind(getMyDisplayName(), this);
		}catch(Exception e){
			e.printStackTrace();
		}

	};


	public static void main(String[] args){

		try{
			@SuppressWarnings("unused")
			NAVServer vDummyNAVServer = new DummyRMI_NAV_SrvImpl();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	//----------------- GENERIC HANDLING OF BUS MOVEMENT ----------------
	//private static Queue<rawobjects.ReqBusMove> mReqList = new LinkedList<rawobjects.ReqBusMove>();
	
	public boolean MovementRequest(NavRequest pNavigationRequestObject,
			UUID pMoveRequestUUID) throws RemoteException
	{
		System.out.println("REQ_FROM_SCH_RECD");
		
		//pMoveReq.debugRequestInfo("FROM_NAV", "");
	
		System.out.println("");
		//mReqList.add(pMoveReq);
		
		boolean bUseAutoResponder = true;
		
		if (bUseAutoResponder) {
			System.out.println("*** USING AUTO RESPONDER ***");
			NAVDummyResponder r = new NAVDummyResponder(pMoveRequestUUID);
			Thread thrd = new Thread(r);
			thrd.start();
		}
		
		System.out.println("");
		System.out.println("");
		
		//Return if the request was accepted
		return true;
	}
	
	private class NAVDummyResponder implements Runnable {

		private UUID mRequestUUID;
		
		public NAVDummyResponder(UUID pRequestUUID){
			this.mRequestUUID = pRequestUUID;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(2000); //Wait 2 seconds (2000)
				boolean bResp = getResponse();
				
				try {
					SCHServer sch = (SCHServer) new DummyRMI_ImplSCH().getSrvObject();
					System.out.println("SCH Response = " + sch.responseFromNav(this.mRequestUUID, bResp));
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		private boolean getResponse(){
			//Randomly get a response
			
			Random g = new Random();
			
			return (g.nextInt()%2==0);
			
		}
		
	}

	//@Override
	/*public boolean MovementRequest(NavRequest pNavigationRequestObject,
			UUID pMoveRequestUUID) throws RemoteException {
		// TODO !HIGH [DummyIMP] Fix this up
		System.out.println("*********** CAUTION - not implemented **********");
		return false;
	}*/

	@Override
	public Integer Navstate() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented navstate **********");
		return null;
	}

	@Override
	public long SimTimeShare() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented SimTimeShare **********");
		return 0;
	}

	@Override
	public boolean startupNAV() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented startUpNav **********");
		return false;
	}

	@Override
	public boolean stopNAV() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented stopNAV **********");
		return false;
	}

}
