package route;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import config.SysConfig;

import rawobjects.Bus;
import rmi_engine.SCHClientImplDB;
import rmi_interfaces.DBServer;

import util.Debug;

public class SchRouteObject {
	private int mRouteID;
	private int mRouteDBID;
	private int mFrequency; //How often should buses be despatched?
	private int mBusCount; //How many buses are needed?

	private Vector<Point> mRoutePoints;

	//Bus Objects
	private Vector<SchBusContainer> vBus;

	public SchRouteObject(){
		this.mRouteID = -1;
		this.mRouteDBID = -1;
		this.mFrequency = 0;
		this.mBusCount = 0;
		this.mRoutePoints = new Vector<Point>();
		vBus = new Vector<SchBusContainer>();
	}

	private String getDebugName(){
		return "SchRouteObject[ID=" + this.mRouteID + "]";
	}

	public int getRouteID() {
		return mRouteID;
	}

	public void setRouteID(int pRouteID) {
		this.mRouteID = pRouteID;
	}

	public int getRouteDBID() {
		return mRouteDBID;
	}

	public void setRouteDBID(int pRouteDBID) {
		this.mRouteDBID = pRouteDBID;
	}

	public int getFrequency() {
		return mFrequency;
	}

	public void setFrequency(int pFrequency) {
		this.mFrequency = pFrequency;
	}

	public int getBusCount() {
		if (mBusCount==0)
			return this.vBus.size();
		else
			return mBusCount;
	}

	public void setBusCount_File(int pBusCount) {
		this.mBusCount = pBusCount;
	}

	public Vector<Point> getRoutePoints() {
		return mRoutePoints;
	}

	public void addRoutePoint(Point pRoutePoint){
		this.mRoutePoints.add(pRoutePoint);
	}
	
	public void addRoutePoint(Vector<Point> pPointVector){
		this.mRoutePoints = pPointVector;
	}

	public int getLastBusScheduleTime(){
		return this.mFrequency * this.mBusCount;		
	}

	/**
	 * Loads essential route data from database
	 * 	- Bus Details
	 */
	public void loadRouteData(){
		this.createBuses();
	}

	private void createBuses(){

		int iNextDueAt = 1;
		
		Debug.writeDebugMsg("schRouteObject", "createBuses", "Loading Buses from DB for Route DBID = " + this.getRouteDBID());
		
		Vector<Bus> vBusDB = new Vector<Bus>();
		
		try {
			//Create RMI session to DB:

			DBServer db = (DBServer)(new SCHClientImplDB()).getSrvObject();
			System.out.println("SIM_RUN = " + SysConfig.getSimRunID());
			System.out.println("ROUTE ID = " + this.getRouteDBID());
			
			vBusDB = db.getListOfBuses(SysConfig.getSimRunID(),this.getRouteDBID());

			if (vBusDB==null){
				System.out.println("YOU HAVE NO BUSES");
				System.exit(52); //Added to easily noticable
			}
			//TODO 4LOG [LOGGING]: Need some logging here, so we know if none are returned?
				
			if (vBusDB.size()>0){
				Bus b;
					
				for (int i = 0; i < vBusDB.size();i++){
					b = vBusDB.get(i);
					int j = vBus.size(); //+ 1;

					Debug.writeDebugMsg(this.getDebugName(),"createBuses", "Creating Bus (INT_ID = " + j + "), Base Start Time = " + iNextDueAt);
					
					SchBusContainer newBus = new SchBusContainer(j,b.getBusID(),b,this.mRouteID, this.mRoutePoints, iNextDueAt);
					
					//TODO 2MED? [IMP]: Also need to zero out bus info, so set to 0
				
					vBus.add(newBus);
					
					if (iNextDueAt==1) iNextDueAt=0; //Need to do this so frequency is not ahead by 1
					
					iNextDueAt = iNextDueAt + this.mFrequency;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		Debug.writeDebugMsg("schRouteObject", "createBuses", "Loading Buses Complete - " + vBusDB.size() + " buses(s) created from DB.");
	}
	
	public void createBuses_File(){

		int iNextDueAt = 1;
		
		for (int iCurrNewBus=0;iCurrNewBus<this.mBusCount;iCurrNewBus++){
			int i = vBus.size(); //+ 1;

			Debug.writeDebugMsg(this.getDebugName(),"createBuses_File", "Creating Bus (INT_ID = " + i + "), Base Start Time = " + iNextDueAt);
			
			SchBusContainer newBus = new SchBusContainer(i,-1,new rawobjects.Bus(), this.mRouteID, this.mRoutePoints, iNextDueAt);
		
			vBus.add(newBus);
			
			if (iNextDueAt==1) iNextDueAt=0; //Need to do this so frequency is not ahead by 1
			
			iNextDueAt = iNextDueAt + this.mFrequency;

		}
	}
	
	public void setBusStartTime(){
		for (int i = 0;i<vBus.size();i++){
			SchBusContainer bus = (SchBusContainer) vBus.get(i);
			
			//bus.busSetBeginTime(util.General.getSimulatorTime());
			bus.busSetBeginTime(util.General.getSimulatorTime_Actual());
		}
	}
		
	public void startBuses(){
		for (int i = 0;i<vBus.size();i++){
			SchBusContainer bus = (SchBusContainer) vBus.get(i);
			
			bus.busStart();
		}
	}

	public void procBusStartAll(){
		for (int i = 0;i<this.vBus.size();i++){
			this.procBusSingleStart(i);
		}
	}

	/**
	 * This starts the Bus thread
	 * @param iBusID The Bus (internal) ID to start
	 */
	public void procBusSingleStart(int iBusID){
		Debug.writeDebugMsg(this.getDebugName(),"procTLSingleStart", "Starting Bus: " + iBusID);

		if (this.vBus.size()>0){
			if ((iBusID >= 0) && (iBusID < vBus.size())){

				SchBusContainer busContainer = (SchBusContainer) vBus.get(iBusID);

				Thread thrd = busContainer.getThread();

				if (!thrd.isAlive())
				{
					thrd.start();
					Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStart", " >> Bus " + iBusID + " Started (bus+thread)");
				}
				else
				{
					if (busContainer.busStart())
						Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStart", " >> Bus " + iBusID + " Started (bus ONLY)");
					else
						Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStart", " >> Bus " + iBusID + " already started");
				}
			}
			else
				Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStart", " >> Could Not Start Bus " + iBusID + " - Out of Range");
		}
		else
			Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStart", " >> Could Not Start Bus " + iBusID + " - No Definitions");
	}

	public void procBusStopAll(){
		for (int i = 0;i<this.vBus.size();i++){
			this.procBusSingleStop(i);
		}
	}

	public void procBusSingleStop(int iBusID){
		Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStop", "Stopping Bus: " + iBusID);

		if (this.vBus.size()>0){
			if ((iBusID >= 0) && (iBusID <= vBus.size())){

				SchBusContainer bus = (SchBusContainer) vBus.get(iBusID);

				if (bus.busStop()){
					Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStop", "... Bus has been stopped.");
				}
				else {
					Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStop", "... Bus ALREADY stopped.");
				}
			}
			else
				Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStop", " >> Could Not Stop Bus " + iBusID + " - Out of Range");
		}
		else
			Debug.writeDebugMsg(this.getDebugName(),"procBusSingleStop", " >> Could Not Stop Bus " + iBusID + " - No Definitions");
	}

	public void procBusKillAll(){
		for (int i = 0;i<this.vBus.size();i++){
			this.procBusSingleKill(i);
		}
	}

	public void procBusSingleKill(int iBusID){
		Debug.writeDebugMsg(this.getDebugName(),"procBusSingleKill", "Killing Bus: " + iBusID);

		if (this.vBus.size()>0){
			if ((iBusID >= 0) && (iBusID <= vBus.size())){
				SchBusContainer bus = (SchBusContainer) vBus.get(iBusID);

				bus.busKill();
			}
			else
				Debug.writeDebugMsg(this.getDebugName(),"procBusSingleKill", " >> Could Not Kill Bus " + iBusID + " - Out of Range");
		}
		else
			Debug.writeDebugMsg(this.getDebugName(),"procBusSingleKill", " >> Could Not Kill Bus " + iBusID + " - No Definitions");

	}


}
