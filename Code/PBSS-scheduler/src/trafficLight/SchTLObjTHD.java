package trafficLight;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import rmi_engine.SCHClientImplDB;
import rmi_engine.SCHClientImplTKR;
import rmi_interfaces.DBServer;
import rmi_interfaces.TKRServer;

import util.*;

public class SchTLObjTHD implements Runnable {
	private int mLightID=-1;
	//private int mLightDBID=-1;
	private boolean mIsGreen=false;
	private boolean mIsActive=true;
	private boolean mDoKill=false;
	private int mChangeFreq=1;
	//private Date mLastChange;
	private long mLastChange_SimRunTime;
	private int mCurrDirection=1;
	private int mMaxDirection=1;
	private boolean mUpdateUsingRMI;
	private rawobjects.TrafficLight mTLObj;

	public SchTLObjTHD(rawobjects.TrafficLight pTL, int iLightID, boolean pUpdateUsingRMI){
		this.mLightID = iLightID;
		//this.mLightDBID = pTL.getiTrafficLightId();
		this.mIsGreen=false;
		this.mIsActive=true;
		/*this.mCurrDirection=pCurrDirection;
		this.mMaxDirection=pMaxDirection;*/
		this.mUpdateUsingRMI = pUpdateUsingRMI;
		this.mTLObj = pTL;
	}

	public void run() {
		//mLastChange = new Date();
		mLastChange_SimRunTime = util.General.getSimulatorTime_Actual();
		mIsGreen=true;

		while (!mDoKill){
			if (this.mIsActive){ //is this light currently set to change
				if (doChange()) {
					//mIsGreen = !mIsGreen; //With directions, green no longer applies
					if (this.mCurrDirection+1>this.mMaxDirection)
						mCurrDirection=1; //Go back to start direction
					else
						mCurrDirection++; //Next direction has the green
					
					mLastChange_SimRunTime = util.General.getSimulatorTime_Actual();
					//this.mLastChange=new Date();

					//Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "Light Changed - Is Green?" + this.mIsGreen);
					Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "Light Changed - Direction with right of way = " + this.mCurrDirection);
					Calendar c = Calendar.getInstance();
					/*c.setTime(this.mLastChange);
					c.add(Calendar.SECOND, this.mChangeFreq);

					Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....Next Change Due At:" + Formatter.formatDate(c));*/
					Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....Next Change Due At [SimTIME]:" + mLastChange_SimRunTime + this.mChangeFreq);
					
					if (this.mUpdateUsingRMI){
						doRMIUpdateDB();
						doRMIUpdateTKR();			
					}
				}
				try {
					Thread.sleep(800);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			else{
				try {
					Thread.sleep(1200); //light is sleeping
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	/**
	 * Updates traffic light data in the DB.
	 */
	private void doRMIUpdateDB(){
		Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - Sending Update to DB.");
		
		//TODO 4LOG [LOGGING]: Need some logging for this
		try {
			DBServer db = (DBServer)new SCHClientImplDB().getSrvObject();
			
			updateSelfObject();
			
			Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "LAST CHANGE = " + this.mTLObj.getLastChange());
			/*ReqEXTUpdTL updTL = new ReqEXTUpdTL();
			updTL.setTLDBID(this.mLightDBID);
			updTL.setCurrDirection(this.mCurrDirection);
			updTL.setLastChange(this.mLastChange.getTime());*/
			
			if (db.updateTrafficLight(this.mTLObj)){
				Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - DB Update OK.");
			}
			else {
				Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - Failure: DB returns FALSE");
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - DB Failure: Remote Exception.");
			e.printStackTrace();
		} catch (NotBoundException e) {
			Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - DB Failure: Not Bound Exception.");
			e.printStackTrace();
		} catch (SQLException e) {
			Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - DB Failure: SQL Exception.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Notifies Tracker of traffic light change.
	 */
	private void doRMIUpdateTKR(){
		Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - Sending Update to TKR.");
		
		//TODO 4LOG [LOGGING]: Need some logging for this
		try {
			TKRServer tkr = (TKRServer)new SCHClientImplTKR().getSrvObject();
			
			updateSelfObject();
			
			/*ReqEXTUpdTL tkrTLUpd = new ReqEXTUpdTL();
			tkrTLUpd.setTLDBID(this.mLightDBID);
			tkrTLUpd.setCurrDirection(this.mCurrDirection);*/
					
			if (tkr.updateTL(this.mTLObj)){
				Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - TKR Update OK.");
			}
			else {
				Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - Failure: TKR returns FALSE");
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - TKR Failure: Remote Exception.");
			e.printStackTrace();
		} catch (NotBoundException e) {
			Debug.writeDebugMsg("schTLObj[ID=" + this.mLightID + "]", "run", "....RMI - TKR Failure: Not Bound Exception.");
			e.printStackTrace();
		}
	}
	
	private void updateSelfObject(){
		this.mTLObj.setCurrDirection(this.mCurrDirection);
		this.mTLObj.setLastChange(mLastChange_SimRunTime);
		//this.mTLObj.setLastChange(this.mLastChange.getTime());
	}

	public boolean getIsGreen(){
		return this.mIsGreen;
	}

	public boolean getIsActive(){
		return this.mIsActive;
	}

	public void setIsActive(boolean b){
		this.mIsActive=b;

	}

	public void doKillLight(){
		this.mDoKill=true;
	}

	public int getLightID(){
		return this.mLightID;
	}

	public void setFrequency(int f){
		this.mChangeFreq=f;
	}
	
	public void setDirectionCurr(int d){
		this.mCurrDirection=d;
	}
	
	public int getDirectionCurr(){
		return this.mCurrDirection;
	}
	
	public void setDirectionMax(int d){
		this.mMaxDirection=d;
	}
	
	public int getDirectionMax(){
		return this.mMaxDirection;
	}

	private boolean doChange(){
		long timeElapse = (new Date().getTime());
		//timeElapse = timeElapse - this.mLastChange.getTime();
		timeElapse = timeElapse - this.mLastChange_SimRunTime;
		timeElapse = timeElapse / 1000; //convert from milliseconds to seconds

		if (timeElapse > this.mChangeFreq){
			return true;
		}
		else
			return false;
	}
}
