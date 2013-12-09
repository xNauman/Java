package route;

import java.awt.Point;
import java.util.Vector;

import route.SchBusObjTHD;
import util.Debug;

public class SchBusContainer {
	private Thread threadTL;
	private int mIDDB; 	//Bus ID in the Database
	private int mIDLocal; //Bus ID (often the array reference)
	private int mIDLocalRoute; //What route is this bus on?
	private SchBusObjTHD newBus;
	private int mStartAtBaseTimeCount;
	private long mStartAtTime; //The simulator time this bus will start running at
	
	/***
	 * @param pIDLocal The local/internal ID for this bus (usually just an array index)
	 * @param pIDDB The DB ID for this bus
	 * @param pIDLocalRoute The local/internal ID for the route this  belongs to
	 * @param pRoutePointList The list of route points this bus will travel on
	 * @param The base time interval this bus will start at
	 */
	public SchBusContainer(int pIDLocal, int pIDDB, rawobjects.Bus pBus, int pIDLocalRoute, Vector<Point> pRoutePointList, int pStartAtBaseTimeCount){
		this.mIDLocal=pIDLocal;
		this.mIDDB = pIDDB;
		this.mIDLocalRoute=pIDLocalRoute;
		this.mStartAtBaseTimeCount=pStartAtBaseTimeCount;
		
		//Create new bus thread
		newBus = new SchBusObjTHD(this.mIDLocal,this.mIDLocalRoute, pBus, pRoutePointList);
		newBus.setBusDBID(this.mIDDB);
		
		//Create the actual Java thread
		threadTL = new Thread(newBus);
	}
	
	private String getDebugName(){
		return "SchBusContainer[Bus ID=" + this.mIDLocal + "][RouteID = " + this.mIDLocalRoute + "]";
	}
	
	public void busSetBeginTime(long pSimTime){
		mStartAtTime = pSimTime + util.General.returnModTime(this.mStartAtBaseTimeCount);
		
		newBus.setBusStartTime(mStartAtTime);
		
		Debug.writeDebugMsg(this.getDebugName(),"busSetBeginTime", "This bus is due to start at: " + util.Formatter.formatDate(this.mStartAtTime));
	}
	
	public boolean busStart(){
		if (newBus.getIsActive()&&this.threadTL.isAlive()){
			return false; //the light is active, and so is the thread
		}
		newBus.setIsActive(true);

		if (!this.threadTL.isAlive()){
			threadTL.start();
		}

		return true;
	}


	public boolean busStop(){
		if (newBus.getIsActive()) {
			newBus.setIsActive(false);

			return true; //bus has been stopped
		}
		else {
			return false; //bus has already been stopped
		}

	}

	public boolean busKill(){
		newBus.doKillBus();
		return true;
	}

	public Thread getThread(){
		return this.threadTL;
	}

	public int getIDDB(){
		return this.mIDDB;
	}
	
	public int setIDDB(){
		return this.mIDDB;
	}

	public int getIDLocal(){
		return this.mIDLocal;
	}
	
	public int getIDLocalRoute(){
		return this.mIDLocalRoute;
	}

}
