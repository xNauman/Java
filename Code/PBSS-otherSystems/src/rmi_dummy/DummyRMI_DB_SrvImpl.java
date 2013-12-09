package rmi_dummy;

import rawobjects.Bus;
import rawobjects.BusStop;
import rawobjects.Depot;
import rawobjects.Map;
import rawobjects.SYS_LOG;
import rawobjects.SYS_SIMULATOR_RUN;
import rawobjects.Sys_Setting;
import rawobjects.TrafficLight;
import rmi_interfaces.DBServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Vector;

import main.RMIControlTHD;

public class DummyRMI_DB_SrvImpl
extends UnicastRemoteObject 
implements DBServer {

	private static final long serialVersionUID = -5595741468602394920L;

	private int mPort = 25001;
	private Registry mLocalRegistry;



	public DummyRMI_DB_SrvImpl()throws RemoteException{
		super();

		System.out.println(getMyDisplayName() + " - Starting Local Registry on port " + mPort);

		try{
			mLocalRegistry = LocateRegistry.createRegistry(mPort);
			mLocalRegistry.rebind(getMyDisplayName(), this);
		}catch(Exception e){
			e.printStackTrace();
		}

	};

	private String getMyDisplayName(){
		return "DB";
	}

	public String debugEcho(String pFromModule, String pMessageToEcho) throws RemoteException {
		System.out.println("ECHO REQUEST - From: " + pFromModule.toUpperCase() + "=" + pMessageToEcho);
		return getMyDisplayName() + ".ECHO:"  + pMessageToEcho;
	}

	public static void main(String[] args){

		/*//start RMIcontrol thread
		Thread runThread; 
		runThread = new Thread(new RMIControlTHD("DB"));
		runThread.start();*/
		
		try{
			@SuppressWarnings("unused")
			DBServer vDummyDBServer = new DummyRMI_DB_SrvImpl();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}

	/***** RMI Dummy Implementations ******/

	public boolean createSimRunEntry(int pSimRunID, int pMapToUseID) 
	throws java.rmi.RemoteException{

		return DummyRMI_DB_MDB.createSysSimRunID(pSimRunID, pMapToUseID);
	}

	public Vector getListOfTrafficLights(int pSimRunID)
	throws java.rmi.RemoteException{

		return DummyRMI_DB_MDB.getListTrafficLight(pSimRunID);
	}

	public Vector getListOfRoutes(int pSimRunID) 
	throws RemoteException, SQLException{

		return DummyRMI_DB_MDB.getListRoutes(pSimRunID);
	}

	public Vector getListOfStops(int pSimRunID) 
	throws java.rmi.RemoteException{

		return DummyRMI_DB_MDB.getListStops(pSimRunID);
	}

	public Vector getListOfCongAreas(int pSimRunID) 
	throws java.rmi.RemoteException{

		return DummyRMI_DB_MDB.getListCongAreas(pSimRunID);
	}

	public Vector getListOfBuses(int pSimRunID, int pRouteID) 
	throws java.rmi.RemoteException{

		return DummyRMI_DB_MDB.getListBuses(pSimRunID, pRouteID);
	}



	public int getMaxSimRunID() 
	throws java.rmi.RemoteException{

		return DummyRMI_DB_MDB.getMaxSimRunID();
	}
	
	public boolean updateBUS(rawobjects.Bus pBus)
	throws java.rmi.RemoteException {

		System.out.println("BUS - Moving Bus " + pBus.getBusID() + " from (" + pBus.getCurrentPointX() + "," + pBus.getCurrentPointY() + ") to (" + pBus.getMoveToPointX() + "," + pBus.getMoveToPointY() + ")");

		return DummyRMI_DB_MDB.busUpdated(pBus);
	}

	
	
	public boolean updateTrafficLight(rawobjects.TrafficLight pTL) 
	throws java.rmi.RemoteException{

		System.out.println("TRAFFIC LIGHT - Updating TLDBID = " + pTL.getTrafficLightId() + ", CurrDir = " + pTL.getCurrDirection());
		
		return DummyRMI_DB_MDB.trafficLightChanged(pTL.getTrafficLightId(), pTL.getLastChange(),pTL.getCurrDirection());
	}
	
	
	
	public boolean updateBusStop(rawobjects.BusStop pStop) 
	throws java.rmi.RemoteException {
		
		System.out.println("BUS STOP - Updating StopDBID = " + pStop.getBusStopID() + ", New Count = " + pStop.getPX_Count());
		
		return DummyRMI_DB_MDB.stopUpdated(pStop.getBusStopID(), pStop.getPX_Count());
	}
	
	public boolean updatePAX(rawobjects.Pax_Stat pStat) 
	throws java.rmi.RemoteException {
		
		System.out.println("PAX_STA - Adding Entry: At StopDBID = " + pStat.getBusStopID() + ", BusDBID = " + pStat.getBusID() + " has moved " + pStat.getCount() + " passengers (bus point of view).");
		
		return DummyRMI_DB_MDB.addPaxStat(pStat);
		
	}

	@Override
	public Map CMEgetMapSavedInDB(String filename) throws RemoteException,
			SQLException {
		System.out.println("*********** CAUTION - not implemented CMEGetMapSavedinDB**********");
		return null;
	}

	@Override
	public boolean DBState() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented  DBState**********");
		return false;
	}

	@Override
	public Map SCHgetMapSavedInDB(int MapID) throws RemoteException,
			SQLException {
		System.out.println("*********** CAUTION - not implemented  SCHgetMapSavedInDB**********");
		return null;
	}

	@Override
	public byte[] TRKgetMapFileSavedInDB() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented TRKgetMapFileSavedInDB **********");
		return null;
	}

	@Override
	public Map TRKgetMapSavedInDB() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented TRKgetMapSavedInDB **********");
		return null;
	}

	@Override
	public Bus getBus(int pBUSID) throws RemoteException  {
		System.out.println("*********** CAUTION - not implemented  getBus**********");
		return null;
	}

	@Override
	public Vector getBusSavedInDB() throws RemoteException, SQLException {
		return DummyRMI_DB_MDB.getListBuses();
	}

	@Override
	public BusStop getBusStop() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented getBusStop **********");
		return null;
	}

	@Override
	public Depot getDepot(int pDepotID)  throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented getDepot 1**********");
		return null;
	}

	@Override
	public Depot getDepot() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented getDepot 2**********");
		return null;
	}

	@Override
	public String getIPFromDB() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented  getIPFromDB **********");
		return null;
	}

	@Override
	public Vector getListOfMapSavedInDB() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented getListOfMapSavedInDB **********");
		return null;
	}

	@Override
	public String getLogData() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented  getLogData**********");
		return null;
	}

	@Override
	public Sys_Setting getSYS_SETTING() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented getSYS_SETTING **********");
		return null;
	}

	@Override
	public SYS_SIMULATOR_RUN getSYS_SIMULATOR_RUN() throws RemoteException,
			SQLException {
		System.out.println("*********** WARNING - not implemented in full - getSYS_SIMULATOR_RUN **********");
		
		SYS_SIMULATOR_RUN sr = new SYS_SIMULATOR_RUN();
		sr.setSimRunID(31);
		
		return sr;
	}

	@Override
	public BusStop getStop(int pStopID) {
		System.out.println("*********** CAUTION - not implemented  getStop**********");
		return null;
	}

	@Override
	public TrafficLight getTrafficLight(int pTrafficLight) {
		//System.out.println("P_LIGHT_ID = " + pTrafficLight);
		
		return DummyRMI_DB_MDB.getTrafficLight(pTrafficLight);
	}

	@Override
	public Vector getTrafficLightsSavedInDB() throws RemoteException,
			SQLException {
		System.out.println("*********** CAUTION - not implemented getTrafficLightsSavedInDB **********");
		return null;
	}

	public boolean saveBusObjectIntoDB(Bus bus) throws RemoteException,
			SQLException {
		System.out.println("*********** CAUTION - not implemented  saveBusObjectIntoDB**********");
		return false;
	}

	@Override
	public void saveIPFromCCIntoDB(String ip) throws RemoteException,
			SQLException {
		System.out.println("*********** CAUTION - not implemented  saveIPFromCCIntoDB**********");
		
	}

	@Override
	public void saveMapIntoDB(byte[] buffer) throws RemoteException,
			SQLException {
		System.out.println("*********** CAUTION - not implemented  saveMapIntoDB**********");
	}

	@Override
	public void saveTrafficLightsIntoDB(TrafficLight light)
			throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented  saveTrafficLightsIntoDB **********");
	}

	@Override
	public void startDB() throws RemoteException, SQLException {	
		System.out.println("*********** CAUTION - not implemented  startDB **********");
	}

	@Override
	public void stopDB() throws RemoteException, SQLException {
		System.out.println("*********** CAUTION - not implemented   stopDB**********");
	}

	@Override
	public boolean updateLog(SYS_LOG log) throws RemoteException {
		System.out.println("*********** CAUTION - not implemented  updateLog**********");
		return false;
	}
}
