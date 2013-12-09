package util;

import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class General {

	//location of data files: traffic lights, route configs, etc
	public static String pathDataFile()
    {
        return System.getProperty("user.dir") + "/DAT/";
    }
	
	public static String pathDataFile(String sFileName)
    {
        return pathDataFile() + sFileName;
    }
	
	//location of RMI simulation files
	public static String pathRMISimFile()
    {
        return System.getProperty("user.dir") + "/RMI-SIM/";
    }
	
	public static String pathRMISimFile(String sFileName)
    {
        return pathRMISimFile() + sFileName;
    }
	
	public static String pathLogDir()
    {
		return System.getProperty("user.dir") + "/LOG/";
    }
	
	public static String pathLogFile()
    {
		return pathLogDir() + "SysLog.txt";
    }
	
	/**
	 * Location for the storage of Stack Trace Dumps
	 * @return
	 */
	public static String pathSTDir()
    {
		return System.getProperty("user.dir") + "/LOG/";
    }
	
	public static String pathSTDir(String sFileName)
    {
		return pathSTDir() + sFileName;
    }
	
	/**
	 * 
	 * @return Unit of Measure for PBSS Time
	 */
	public static String unitTime(){
		return "seconds";
	}
	
	/**
	 * Converts Time Unit value into the time value used within the system.
	 * E.g. if Time Unit is in seconds, and system time is measured in milliseconds,
	 * 	will when called, 2 seconds (pTimeUnit) will be returned as 2000 milliseconds.
	 * 
	 * @param pTimeUnit
	 * @return
	 */
	public static long returnModTime(int pTimeUnit){
		return pTimeUnit * 1000;
	}
	
	/**
	 * Returns the Simulator Run time, starting from 0.
	 * 
	 * @return
	 */
	public static long getSimulatorTime_Actual(){
		//return Calendar.getInstance().getTimeInMillis();
		//return Clock.getSimTime();
		return (long)Clock.getSimTime_NAVDirect();
	}
	
	/**
	 * Returns the Simulator time, based on "human" time
	 * @return
	 */
	public static long getSimulatorTime_UnixTS(){
		return Calendar.getInstance().getTimeInMillis();
	}
	
	
	@SuppressWarnings("unchecked")
	public static Queue copyQueue(Queue q){
		Queue q2 = new LinkedList();
		
		Iterator itr = q.iterator();
		
		while (itr.hasNext()){
			q2.add(itr.next());
		}
		
		return q2;
		
	}
	
	public static int randomNumberMAX(int pMinNum, int pMaxNum){
		if (pMinNum==pMaxNum)
			pMaxNum++;
		
		if (pMinNum>pMaxNum){
			int iSwap = pMinNum;
			pMinNum = pMaxNum;
			pMaxNum = iSwap;
		}
		
		return new Random().nextInt(pMaxNum-pMinNum+1)+pMinNum;
	}
	
	public static boolean between(int pLow, int pValue, int pHigh){
		return ((pLow <= pValue) && (pValue <= pHigh));
	}
}
