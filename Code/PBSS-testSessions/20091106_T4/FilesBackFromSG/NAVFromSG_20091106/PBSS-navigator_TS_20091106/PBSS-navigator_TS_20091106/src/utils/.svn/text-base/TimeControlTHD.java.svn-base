package utils;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Calendar;

import config.SysConfig;


public class TimeControlTHD implements Runnable {

	/**
	 * Poll Control Centre every X milliseconds.
	 */
	private int mPollTime = 5000;	//Must be > 500 
	private long mLastPollTime = 0;
	private boolean bShowDebug = true;

	public void run() {
		while (SysConfig.timeControl_KeepPolling()){	//Are we to continue polling?
			if (SysConfig.getIPregistrationCompleted()){	//Have we got CC's IP?
				if (isPollDue()) {
					doCCTimePoll();

					try {
						Thread.sleep(mPollTime - 500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private boolean isPollDue(){
		return ((Calendar.getInstance().getTimeInMillis() - this.mLastPollTime) > mPollTime);
	}

	private boolean doCCTimePoll(){
		boolean bIsSuccess = false;
		try {
			rmi_interfaces.CCServer cc = new rmi_engine.NAVClientImplCC().getSrvObject();
			long timeCC = cc.SimTimeShare();
			int timeDiff = Clock.compareTime(timeCC);

			if (bShowDebug) System.out.println("CURR TIME (Local) = " + Clock.getSimTime());
			if (bShowDebug) System.out.println("CURR TIME (CC) = " + timeCC);
			if (bShowDebug) System.out.println("TIME DIFF = " + timeDiff);

			Clock.modifySimTime(timeDiff);

			bIsSuccess = true;
		} catch (RemoteException e) {
			e.printStackTrace();
			bIsSuccess = false;
		} catch (NotBoundException e) {
			e.printStackTrace();
			bIsSuccess = false;	
		}

		return bIsSuccess;
	}
}
