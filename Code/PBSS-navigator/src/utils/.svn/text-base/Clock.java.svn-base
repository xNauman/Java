package utils;
/**
 * 
 * Clock class
 * THis class provides the functionality of handling simulation time and adjusting the simulation time
 * to match with another module (such as CC) if the machine system times don't line up correctly.
 * This class is designed to be highly static and not be treated as an object per say. 
 * 
 * @author David Taberner
 */

import java.util.Calendar;
import java.util.Date;


public class Clock {
	
	/*
	 * Instance variables
	 */
	private static Calendar tTimeObject;
	private static long lSimStartTime = 0;
	private static long lSimCurrentTime = 0;
	private static int iSimTimeModifier = 0;
	
	/*
	 * Constructors
	 */
	// added a constructor for the instance variables
//	public Clock(Calendar pTimeObject, long pSimStartTime, long pSimCurrentTime, int pSimTimeModifier)
//	{
//		tTimeObject = pTimeObject;
//		lSimStartTime = pSimStartTime;
//		lSimCurrentTime = pSimCurrentTime;
//		iSimTimeModifier = pSimTimeModifier;
//	}
//	
	/**
	 * Void method that will start the clock for the simulation
	 *
	 * @return boolean is returned if the recorded timestamp is not zero
	 */
	public static boolean StartClock()
	{		
		
		
		//set the start time
		lSimStartTime = getCurrentUnixTime();
		
		//check we get something useful
		if(lSimStartTime > 0)
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	
	
	/**
	 * Method that will obtain the current simulation time by taking the current time 
	 * and then subtracting the new timestamp from the old, this will give a difference.
	 * The difference is the simulation current time.
	 * 
	 * A modifier is also added, this can be a value to reduce the clock, or increase it
	 * 
	 * @return long with current simulation time
	 */
	public static long getSimTime()
	{
		long lReturnValue;
		
		lSimCurrentTime = getCurrentUnixTime();
		
		lReturnValue = lSimCurrentTime-lSimStartTime;
		lReturnValue = lReturnValue + iSimTimeModifier;
		
		//if we haven't started the clock yet, return zero
		if(lSimStartTime == 0) lReturnValue = 0;

		
		//System.out.println("TIME SYNC: "+lReturnValue+ " DEBUG lSimStartTime: "+lSimStartTime+" DEBUG lSimCurrentTime: "+lSimCurrentTime);
		
		return lReturnValue;
		
	}
	
	
	/**
	 * This function will change the time modifier, if the close is ahead or behind
	 * 
	 * @param pModifier the integer to modify the time by + values will be used if this time is behind
	 * - values will be used if its ahead
	 *
	 */
	public static void modifySimTime(int pModifier)
	{
		
		iSimTimeModifier = iSimTimeModifier+pModifier;
		
	}
	
	/**
	 * 
	 * @param pOtherSimTime the unixtime in long to compare too - this would probably come from CC over RMI
	 * @return integer that represents the value the clock was out by. A + value means 
	 * our clock is behind by that amount
	 * A - value means its ahead by that amount
	 */
	public static int compareTime(long pOtherSimTime)
	{
		int iModifier;
		
		iModifier = (int) (pOtherSimTime-getSimTime());
		modifySimTime(iModifier);
		
		return iModifier;
	}
	
	
	/**
	 * Get the current unixtime stamp of the system running the simulation
	 * 
	 * @return Type Long with a unixtime stamp
	 */
	private static long getCurrentUnixTime()
	{
		tTimeObject = Calendar.getInstance();
		long lReturnValue = 0;
		Date dDateObject = new Date();
		//get time object and get the unix timestamp from it
		dDateObject = tTimeObject.getTime();
		lReturnValue = dDateObject.getTime();
		
		return lReturnValue;
		
	}

	

}
