package rmi_dummy;

import rmi_interfaces.TKRServer;

import java.awt.Point;
import java.net.InetAddress;
import java.net.SocketPermission;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

import main.RMIControlTHD;


@SuppressWarnings("unused")
public class DummyRMI_TKR_SrvImpl 
extends UnicastRemoteObject 
implements TKRServer {
	private static final long serialVersionUID = 2897918669229935985L;
	private int mPort = 25006;
	private Registry mLocalRegistry;

	private long mLastMoveBusReq = 0;
	private boolean mLastMoveBusAction = false;

	@Override
	public String debugEcho(String pFromModule, String pMessageToEcho) throws RemoteException {
		System.out.println("ECHO REQUEST - From: " + pFromModule.toUpperCase() + "=" + pMessageToEcho);
		return getMyDisplayName() + ".ECHO:"  + pMessageToEcho;
	}

	private String getMyDisplayName(){
		return "TKR";
	}
	

	//--------- RMI SERVER CODE
	public DummyRMI_TKR_SrvImpl()throws RemoteException{
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

		/*//start RMIcontrol thread
		Thread runThread; 
		runThread = new Thread(new RMIControlTHD("TKR"));
		runThread.start();*/
		
		try{
			TKRServer vDummyRMIServer = new DummyRMI_TKR_SrvImpl();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}


	public boolean updateTL(rawobjects.TrafficLight pTLUpd) 
	throws java.rmi.RemoteException{

		System.out.println(getMyDisplayName() + " DRAW_CHANGE: TRAFFIC LIGHT (DB ID =  " + pTLUpd.getTrafficLightId() + ") has changed. Now at direction = " + pTLUpd.getCurrDirection());

		return true;
	}


	public boolean updateStop(rawobjects.BusStop pStop) 
	throws java.rmi.RemoteException{

		System.out.println(getMyDisplayName() + " DRAW_CHANGE: UPDATE STOP (DB ID =  " + pStop.getBusStopID() + ") has been updated. Pax Count = " + pStop.getPX_Count());

		return true;

	}
	
	public boolean moveBus(rawobjects.Bus pBus, boolean isCongestedArea) 
	throws java.rmi.RemoteException{

		System.out.println(getMyDisplayName() + " Time since last request: " + getBusMoveTime() + " milliseconds.");
		System.out.println(getMyDisplayName() + " DRAW_CHANGE: MOVE BUS (DB ID =  " + pBus.getBusID() + ") from (" + pBus.getCurrentPointX() + "," + pBus.getCurrentPointY() + ") TO (" + pBus.getMoveToPointX()+ "," + pBus.getMoveToPointY() + ") - Distance of (NO LONGER CALCed)" + " Is Cong Area = " + isCongestedArea);

		return true;
	}

	//NOT REQUIRED IN FINAL IMPLEMENTED
	//USED TO CHECK THAT MOVE REQUESTS ARE BEING SENT AT A near CONSTANT LEVEL.
	private long getBusMoveTime(){

		long timeDiff = Calendar.getInstance().getTimeInMillis() - this.mLastMoveBusReq;
		this.mLastMoveBusReq = Calendar.getInstance().getTimeInMillis();

		if (this.mLastMoveBusReq > 5)
			return timeDiff;
		else
			return 0;	
	}

	@Override
	public long SimTimeShare() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented **********");
		return 0;
	}

	@Override
	public byte[] getImage() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented **********");
		return null;
	}

	@Override
	public boolean startupTKR() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented **********");
		return false;
	}

	@Override
	public boolean stopTKR() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented **********");
		return false;
	}

	@Override
	public int tkrState() throws RemoteException {
		System.out.println("*********** CAUTION - not implemented **********");
		return 0;
	}

}
