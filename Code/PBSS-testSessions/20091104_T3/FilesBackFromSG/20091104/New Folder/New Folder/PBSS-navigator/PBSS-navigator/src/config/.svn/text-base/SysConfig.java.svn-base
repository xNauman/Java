package config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import rawobjectsPriv.EnumModName;
import rawobjectsPriv.EnumSysState;
import rawobjectsPriv.NetIPInfo;


public class SysConfig {

	private static EnumModName mThisModule = EnumModName.NAVIGATOR;
	
	public static boolean SYSTEM_DEBUG_MODE_ENABLED = true;
	
	private static int mSimulatorRunID = -1;
	private static String mSimulatorRunIDSetBy;
	
	private static int mMaxNAVDistance = -1; //The maximum distance between two points that can be sent to NAV.
	
	
	private static double mDistanceToBeCompleted = 0.75; 
	private static rawobjectsPriv.EnumSysState mAppState = rawobjectsPriv.EnumSysState.NOT_OPERATIONAL;

	// ------------------ TIME CONTROL (Start) ------------------
	private static boolean mKeepPollingCCForTime = true; //Keep polling for the time, once set to false, kills the thread
	// ------------------ TIME CONTROL (End) ------------------


	//Variables for RMI status
	private static Map<String,rawobjectsPriv.NetIPInfo> mIPList = new HashMap<String,rawobjectsPriv.NetIPInfo>(); //list of IPs
	private static boolean mCoreModuleIPsKnown = false; // are TKR, NAV, SCH and DB's IPs known
	private static boolean mIPregistrationCompleted = false; //have we completed our IP registration  
	private static boolean mStopIPRegistration = false; // value used to stop the IpRegistrationTHD
	private static long mIPRegistrationSleepTimer = 5000;
	
	//error value for traffic light angles
	private static int mErrorValueForTrafficLightAngles = 3;
	


	public static EnumModName getMyModuleName(){
		return mThisModule;
	}
	
	
	public static String getModuleName(){
		//return mModuleName;
		return mThisModule.getModAbbrev();
	}
	
	
	
	public static void setSimRunID(String pSetBy, int pRunID){
		mSimulatorRunIDSetBy = pSetBy;
		mSimulatorRunID = pRunID;
	}
	
	public static int getSimRunID(){
		return mSimulatorRunID;
	}
	
	
	public static String getSimRunSetBy(){
		return mSimulatorRunIDSetBy;
	}
	
	/**
	 * The maximum distance between two points that can be sent to NAV.
	 * @return Maximum distance between two points that can be sent to NAV.
	 */
	public static int getMaxNavDistance(){
		return mMaxNAVDistance;
	}
	
	public static void setMaxNavDistance(int pMaxNAVDistance){
		mMaxNAVDistance = pMaxNAVDistance;
	}
	


	/**
	 * When a request is sent to TKR, how much of that move request should be completed, before SCH requests the next move
	 * with NAV.
	 * 
	 * @return Percentage, indicating approximate distance to be covered.
	 */
	public static double getDistanceToBeCompleted(){
		return mDistanceToBeCompleted;		
	}
	
	
	
	/**
	 * Find out if tkr, sch, nav, db are online as core modules
	 * 
	 * @return boolean that's true if they are online and we have the IPs
	 */
	public static boolean getCoreModuleIPsKnown() {
		return mCoreModuleIPsKnown;
	}

	/**
	 * Set if  tkr, sch, nav, db are online as core modules
	 * 
	 * @param mCoreModuleIPsKnown - boolean saying if they are online and we know the IPs not
	 */
	public static void setCoreModuleIPsKnown(boolean mCoreModuleIPsKnown) {
		SysConfig.mCoreModuleIPsKnown = mCoreModuleIPsKnown;
	}

	/**
	 * Find out if the boardcast is completed and we've obtained CC's IP address and CC knows our IP
	 * 
	 * @return - boolean saying that we have received the address
	 */
	public static boolean getIPregistrationCompleted() {
		return mIPregistrationCompleted;
	}

	/**
	 * Set if IP boardcast and registration is completed with CC
	 * 
	 * @param mIPregistrationCompleted - boolean saying if its completed or not. 
	 */
	public static void setIPregistrationCompleted(boolean mIPregistrationCompleted) {
		SysConfig.mIPregistrationCompleted = mIPregistrationCompleted;
	}
	
	
	/**
	 * Get the value of the IPRegistration
	 * 
	 * @return boolean that will say if IP registration can run or not
	 */
	public static boolean getStopIPRegistration() {
		return mStopIPRegistration;
	}

	/**
	 * set IPRegistration value, if this is test to true then 
	 * the NavIpRegistrationTHD will stop and will set the value back to true as it stops
	 * so I can be restarted if required
	 * 
	 * @param mStopIPRegistration
	 */
	public static void setStopIPRegistration(boolean mStopIPRegistration) {
		SysConfig.mStopIPRegistration = mStopIPRegistration;
	}

	/**
	 * Get the value of the sleep timer that will tell IpRegistration how long to sleep for between
	 * attempts to get IPs
	 * 
	 * @return long with the time to wait in milliseconds
	 */
	public static long getIPRegistrationSleepTimer() {
		return mIPRegistrationSleepTimer;
	}

	/**
	 * Get the error value for a use with traffic light angles
	 * 
	 * @return - int with error value
	 */
	public static int getErrorValueForTrafficLightAngles() {
		return mErrorValueForTrafficLightAngles;
	}


	/**
	 * Set error value for use with traffic light angles
	 * 
	 * @param int - value of the error
	 */
	public static void setErrorValueForTrafficLightAngles(
			int mErrorValueForTrafficLightAngles) {
		SysConfig.mErrorValueForTrafficLightAngles = mErrorValueForTrafficLightAngles;
	}



	//these record various status - maybe useful later
	/**
	 * Records the number of requests made to TKR, in which TKR returned false
	 */
//	public static void perfStat_TKRReqFail_Increment(){
//		mPERFStat_TKRReqFail++;
//	}
//	
//	public static int getPerfStat_TKRReqFail(){
//		return mPERFStat_TKRReqFail;
//	}
	
	
	
	public static void setNAVState(rawobjectsPriv.EnumSysState pState){
		mAppState = pState;
	}
	
	//gets a single IP for a module to connect with RMI
	public static NetIPInfo getIPInfo(EnumModName pMod){
		if (mIPList.containsKey(pMod.getModAbbrev())){
			return mIPList.get(pMod.getModAbbrev());
		}
		else {
			return new NetIPInfo();
		}
	}
	
	//adds an IP for a system into the local store
	public static void addIPInfo(EnumModName pMod, NetIPInfo pIPInfo){
		
		//System.out.println("ADDING " + pMod.getModAbbrev() + ", IP PORT = " + pIPInfo.getPort());
		
		mIPList.put(pMod.getModAbbrev(), pIPInfo);
	}
	
	// ------------------ TIME CONTROL (Start) ------------------
	public static boolean timeControl_KeepPolling(){
		return mKeepPollingCCForTime;
	}
	
	public static void timeControl_StopPolling(){
		mKeepPollingCCForTime = false;
	}
	
	public static void timeControl_StartPolling(){
		mKeepPollingCCForTime = true;
	}
	// ------------------ TIME CONTROL (End) ------------------
	
}
