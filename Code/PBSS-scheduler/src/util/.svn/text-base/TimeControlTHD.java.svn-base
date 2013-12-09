package util;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;

import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;

import config.SysConfig;


public class TimeControlTHD implements Runnable {

	/**
	 * Poll Control Centre every X milliseconds.
	 */
	private int mPollTime = 5000;	//Must be > 500 
	private long mLastPollTime = 0;
	private boolean bShowDebug = false;
	
	private long mShowErrorSec = 60000;
	
	private long mLastErrorStackDump = 0;

	public void run() {
		while (SysConfig.timeControl_KeepPolling()){	//Are we to continue polling?
			if (SysConfig.getIPregistrationCompleted()){	//Have we got CC's IP?
				if (isPollDue()) {
					doNAVTimePoll();

					if (bShowDebug) System.out.println("TIME...");
					
					try {
						//Thread.sleep(mPollTime - 500);
						Thread.sleep(200);
					} catch (InterruptedException e) {
						if (showStackTrace(true)) e.printStackTrace();
					} catch (Exception e){
						if (showStackTrace(true)) e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean isPollDue(){
		return ((Calendar.getInstance().getTimeInMillis() - this.mLastPollTime) > mPollTime);
	}

	@SuppressWarnings("unused")
	private boolean doCCTimePoll(){
		boolean bIsSuccess = false;
		try {
			rmi_interfaces.CCServer cc = new rmi_engine.SCHClientImplCC().getSrvObject();
			long timeCC = cc.SimTimeShare();
			int timeDiff = Clock.compareTime(timeCC);

			if (bShowDebug) System.out.println("CURR TIME (Local) = " + Clock.getSimTime());
			if (bShowDebug) System.out.println("CURR TIME (CC) = " + timeCC);
			if (bShowDebug) System.out.println("TIME DIFF = " + timeDiff);

			Clock.modifySimTime(timeDiff);

			bIsSuccess = true;
		} catch (RemoteException e) {
			if (showStackTrace(true)) e.printStackTrace();
			bIsSuccess = false;
		} catch (NotBoundException e) {
			if (showStackTrace(true)) e.printStackTrace();
			bIsSuccess = false;	
		} catch (Exception e) {
			if (showStackTrace(true)) e.printStackTrace();
			bIsSuccess = false;
		}

		return bIsSuccess;
	}
	
	private boolean doNAVTimePoll(){
		boolean bIsSuccess = false;
		try {
			
			NetIPInfo ip = SysConfig.getIPInfo(EnumModName.NAVIGATOR);
			
			if (ip == null) {
				System.out.println("NULL NAV Info");
				return false;
			}
			
			rmi_interfaces.NAVServer nav = new rmi_engine.SCHClientImplNAV().getSrvObject();
			long timeNAV = nav.SimTimeShare();
			Clock.setSimTime_NAVDirect((int)timeNAV);
			
			int timeDiff = Clock.compareTime(timeNAV);

			if (bShowDebug) System.out.println("CURR TIME (Local) = " + Clock.getSimTime());
			if (bShowDebug) System.out.println("CURR TIME (NAV) = " + timeNAV);
			if (bShowDebug) System.out.println("TIME DIFF = " + timeDiff);

			Clock.modifySimTime(timeDiff);

			bIsSuccess = true;
		} catch (RemoteException e) {
			if (showStackTrace(true)) e.printStackTrace();
			bIsSuccess = false;
		} catch (NotBoundException e) {
			if (showStackTrace(true)) e.printStackTrace();
			bIsSuccess = false;	
		} catch (Exception e) {
			if (showStackTrace(true)) e.printStackTrace();
			bIsSuccess = false;
		}

		return bIsSuccess;
	}
	
	private void setLastErrorStackTime(){
		mLastErrorStackDump = Calendar.getInstance().getTimeInMillis();
		
		System.out.println("TimeControlTHD:setLastErrorStackTime = Future Errors Messages will be suppressed for the next " + mShowErrorSec/1000 + " seconds.");
	}
	
	private boolean showStackTrace(boolean updateTime){
		
		//Check if a single stack trace has been completed
		if (mLastErrorStackDump <= 0){
			if (updateTime) {
				this.setLastErrorStackTime();
			}
			return true;
		}
		
		long timeDiff = Calendar.getInstance().getTimeInMillis() - mLastErrorStackDump;
		
		//Only show an error every X seconds
		if (timeDiff > mShowErrorSec){
			if (updateTime) {
				this.setLastErrorStackTime();
			}
			return true;
		}
		else 
		{
			//System.out.println("NO MSG");
			return false;
		}
	}
}
