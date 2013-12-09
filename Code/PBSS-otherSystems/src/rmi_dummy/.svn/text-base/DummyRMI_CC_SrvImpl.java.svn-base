package rmi_dummy;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

import rmi_interfaces.CCServer;

public class DummyRMI_CC_SrvImpl
	extends UnicastRemoteObject 
	implements CCServer{
	
	private static final long serialVersionUID = -2115116123564776740L;
	private int mPort = 25000;
	private Registry mLocalRegistry;
	
	private String getMyDisplayName(){
		return "CC";
	}
	
	public DummyRMI_CC_SrvImpl()throws RemoteException{
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
			CCServer vDummyCCServer = new DummyRMI_CC_SrvImpl();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	
	public String getIP(String ID) throws RemoteException {
		
		System.out.println("REQUEST IP for " + ID);
		if (ID.equalsIgnoreCase("SCH")) 
			return DummyRMI_IPList.getIP_SCH();
		else if (ID.equalsIgnoreCase("NAV")) 
			return DummyRMI_IPList.getIP_NAV();
		else if (ID.equalsIgnoreCase("DB")) 
			return DummyRMI_IPList.getIP_DB();
		else if (ID.equalsIgnoreCase("SU")) 
			return DummyRMI_IPList.getIP_SETUP();
		else if (ID.equalsIgnoreCase("CME")) 
			return DummyRMI_IPList.getIP_CME();
		else if (ID.equalsIgnoreCase("TKR")) 
			return DummyRMI_IPList.getIP_TKR();
		else 
			return "";
	}

	public void getImage() throws RemoteException {
		return;
	}

	public void prestart() throws RemoteException {
		return;
	}

	public void setupComplete(boolean saved) throws RemoteException {
		return;
	}

	public void start() throws RemoteException {
		return;
	}

	public void stop() throws RemoteException {
		return;
	}

	public void updateStatus(String ID, int state) throws RemoteException {
		System.out.println("STATUS CHANGE - " + ID + ", State is now = " + state);
	}

    public long SimTimeShare() throws RemoteException {
		return Calendar.getInstance().getTimeInMillis();
	}

}
