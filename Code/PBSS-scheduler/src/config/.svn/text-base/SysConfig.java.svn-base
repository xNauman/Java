package config;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;

public class SysConfig {

	//Variables to set if using DummyRMI Service
	//TODO 8FORNAV: DummyRMI Service Usage
	private static boolean mUseDummyRMIService = false;
	
	private static final int mSVNRevNum = 1726;
	private static boolean mAllowUserInputMode = false;
	
	private static EnumModName mThisModule = EnumModName.SCHEDULER;

	//private static String mModuleName="SCH";

	private static Calendar mAppStartTime;

	private static int mSimulatorRunID = -1;
	private static Calendar mSimulatorRunIDSetAt;
	private static String mSimulatorRunIDSetBy;

	private static int mMaxNAVDistance = -1; //The maximum distance between two points that can be sent to NAV.

	private static long mTimeOutNAVRequest = 1;
	private static long mTimeOutNAVRequestAutoKill = 1;

	private static double mDistanceToBeCompleted = 0.75; 
	private static double mSpeedTKR_Normal = 50;
	private static double mSpeedTKR_Congested = 25;

	private static int mPERFStat_NAVReqTimeOut = 0;	//Requests to NAV that SCH timed out
	private static int mPERFStat_NAVReqTimeOutWithKill = 0;	//Requests to NAV that SCH timed out and then killed the entire point
	private static int mPERFStat_NAVReqTotal = 0;	//Requests Made to NAV
	private static int mPERFStat_NAVRespGranted = 0; //Responses received, granting the move
	private static int mPERFStat_NAVRespDenied = 0; //Responses received, denying the move
	private static int mPERFStat_NAVRespTotal = 0; //Total Responses received
	private static int mPERFStat_TKRReqTotal = 0;	//Total Requests made to TKR
	private static int mPERFStat_TKRReqOK = 0;	//Requests made to TKR, returned true
	private static int mPERFStat_TKRReqFail = 0;	//Requests made to TKR, returned false
	private static int mPERFStat_PaxMove_ReqMade = 0; //Number of passenger movement calcs requests made
	private static int mPERFStat_PaxMove_Req = 0; //Number of passenger movement calcs requests (actual process0
	private static int mPERFStat_PaxMove_LoopTrip = 0; //When doing passenger movements calcs, number that had a loop trip

	//Now in util.StateControl
	//private static rawobjectsPriv.EnumSysState mAppState = rawobjectsPriv.EnumSysState.NOT_OPERATIONAL;

	private static Map<String,rawobjects.BusStop> mapStop = new HashMap<String,rawobjects.BusStop>();

	// ------------------ TIME CONTROL (Start) ------------------
	private static boolean mKeepPollingCCForTime = true; //Keep polling for the time, once set to false, kills the thread
	// ------------------ TIME CONTROL (End) ------------------


	//Variables for RMI status
	private static Map<String,rawobjectsPriv.NetIPInfo> mIPList = new HashMap<String,rawobjectsPriv.NetIPInfo>(); //list of IPs
	private static boolean mCoreModuleIPsKnown = false; // are TKR, NAV, SCH and DB's IPs known
	private static boolean mIPregistrationCompleted = false; //have we completed our IP registration  
	private static boolean mStopIPRegistration = false; // value used to stop the IpRegistrationTHD
	private static long mIPRegistrationSleepTimer = 5000;


	//Variables for State checking
	private static boolean mStateCheck_TLLoaded = false;
	private static boolean mStateCheck_StopLoaded = false;
	private static boolean mStateCheck_RouteLoaded = false;
	private static boolean mStateCheck_CongAreaLoaded = false;


	public static boolean getUseDummyRMIService(){
		return mUseDummyRMIService;
	}



	public static EnumModName getMyModuleName(){
		return mThisModule;
	}

	public static void stopObjClear(){
		mapStop.clear();
	}

	public static void stopObjAdd(rawobjects.BusStop pStop){
		mapStop.put(stopObjGetKey(pStop.getBusStopID()),pStop);
	}

	public static rawobjects.BusStop stopObjGet(int pStopDBID){
		if (stopObjExists(pStopDBID))
			return mapStop.get(stopObjGetKey(pStopDBID));
		else
			return null;
	}

	public static boolean stopObjExists(int pStopDBID){
		return mapStop.containsKey(stopObjGetKey(pStopDBID));
	}

	public static boolean stopObjSet(int pStopDBID, rawobjects.BusStop pStop){
		if (stopObjExists(pStopDBID)){
			stopObjAdd(pStop);
			return true;
		}
		else {
			//Error it?
			stopObjAdd(pStop);
			return true;
		}
	}

	private static String stopObjGetKey(int pStopDBID){
		return "D" + pStopDBID;
	}


	public static String getModuleName(){
		//return mModuleName;
		return mThisModule.getModAbbrev();
	}

	public static void setAppStartTime(){
		mAppStartTime = Calendar.getInstance();
	}

	public static Calendar getAppStartTime(){
		return mAppStartTime;
	}

	/**
	 * Application Run Time
	 * @return Run time in seconds
	 */
	public static long getAppRunTime(){
		return (Calendar.getInstance().getTimeInMillis() - mAppStartTime.getTimeInMillis())/1000;
	}

	public static void setSimRunID(String pSetBy, int pRunID){
		mSimulatorRunIDSetBy = pSetBy;
		mSimulatorRunID = pRunID;
		mSimulatorRunIDSetAt = Calendar.getInstance();
	}

	public static int getSimRunID(){
		return mSimulatorRunID;
	}

	public static Calendar getSimRunSetAt(){
		return mSimulatorRunIDSetAt;
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
	 * The time out value (in milliseconds) for a response to be received from NAV.
	 * @return
	 */
	public static long getTimeOutNAVRequest(){
		return mTimeOutNAVRequest;
	}

	public static void setTimeOutNAVRequest(long pTimeOutNAVRequest){
		mTimeOutNAVRequest = pTimeOutNAVRequest;
	}

	/**
	 * Once a request has been deemed to have time out, how long until it should be taken as cancelled.
	 * (value of -1 prevents)
	 * @return
	 */
	public static long getTimeOutNAVRequestAutoKill(){
		return mTimeOutNAVRequestAutoKill;
	}

	public static void setTimeOutNAVRequestAutoKill(long pTimeOutNAVRequestAutoKill){
		mTimeOutNAVRequestAutoKill = pTimeOutNAVRequestAutoKill;
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
	 * Records the number of time a NAV request times out.
	 */
	public static void perfStat_NAVReqTimeOut_Increment(){
		mPERFStat_NAVReqTimeOut++;
	}

	public static int getPerfStat_NAVReqTimeOut(){
		return mPERFStat_NAVReqTimeOut;
	}

	/**
	 * Records the number of time a NAV request times out, and leads to a request being aborted locally.
	 */
	public static void perfStat_NAVReqTimeOutWithKill_Increment(){
		mPERFStat_NAVReqTimeOutWithKill++;
	}

	public static int getPerfStat_NAVReqTimeOutWithKill(){
		return mPERFStat_NAVReqTimeOutWithKill;
	}

	/**
	 * Records the number of requests made to NAV.
	 */
	public static void perfStat_NAVReq_Increment(){
		mPERFStat_NAVReqTotal++;
	}

	public static int getPerfStat_NAVReq(){
		return mPERFStat_NAVReqTotal;
	}

	/**
	 * Records the number of responses recd from NAV.
	 */
	public static void perfStat_NAVRespTot_Increment(){
		mPERFStat_NAVRespTotal++;
	}

	public static int getPerfStat_NAVRespTot(){
		return mPERFStat_NAVRespTotal;
	}

	/**
	 * Records the number of responses recd from NAV, in which the move request was denied
	 */
	public static void perfStat_NAVRespDenied_Increment(){
		mPERFStat_NAVRespDenied++;
	}

	public static int getPerfStat_NAVRespDenied(){
		return mPERFStat_NAVRespDenied;
	}

	/**
	 * Records the number of responses recd from NAV, in which the move request was granted
	 */
	public static void perfStat_NAVRespGranted_Increment(){
		mPERFStat_NAVRespGranted++;
	}

	public static int getPerfStat_NAVRespGranted(){
		return mPERFStat_NAVRespGranted;
	}

	/**
	 * Records the number of requests made to TKR
	 */
	public static void perfStat_TKRReq_Increment(){
		mPERFStat_TKRReqTotal++;
	}

	public static int getPerfStat_TKRReq(){
		return mPERFStat_TKRReqTotal;
	}

	/**
	 * Records the number of requests made to TKR, in which TKR returned true
	 */
	public static void perfStat_TKRReqOK_Increment(){
		mPERFStat_TKRReqOK++;
	}

	public static int getPerfStat_TKRReqOK(){
		return mPERFStat_TKRReqOK;
	}

	/**
	 * Records the number of requests made to TKR, in which TKR returned false
	 */
	public static void perfStat_TKRReqFail_Increment(){
		mPERFStat_TKRReqFail++;
	}

	public static int getPerfStat_TKRReqFail(){
		return mPERFStat_TKRReqFail;
	}

	/**
	 * Records the number of request made internally to calculate passenger movement
	 */
	public static void perfStat_PaxMoveReqMade_Increment(){
		mPERFStat_PaxMove_ReqMade++;
	}

	public static int getPerfStat_PaxMoveReqMade(){
		return mPERFStat_PaxMove_ReqMade;
	}

	/**
	 * Records the number of times the actual code was executed to determine pax movement
	 */
	public static void perfStat_PaxMoveReq_Increment(){
		mPERFStat_PaxMove_Req++;
	}

	public static int getPerfStat_PaxMoveReq(){
		return mPERFStat_PaxMove_Req;
	}

	/**
	 * Records the number of times the actual code process was aborted because it reached the internal loop protection limit
	 */
	public static void perfStat_PaxMoveLoopTrip_Increment(){
		mPERFStat_PaxMove_LoopTrip++;
	}

	public static int getPerfStat_PaxMoveLoopTrip(){
		return mPERFStat_PaxMove_LoopTrip;
	}

	public static rawobjectsPriv.EnumSysState getSCHState(){
		return util.StateControl.getCurrentState();
	}


	//USE util.StateControl
	/*public static void setSCHState(rawobjectsPriv.EnumSysState pState){
		mAppState = pState;
	}*/

	public static NetIPInfo getIPInfo(EnumModName pMod){
		if (mIPList.containsKey(pMod.getModAbbrev())){
			return mIPList.get(pMod.getModAbbrev());
		}
		else {
			return new NetIPInfo();
		}
	}

	public static void addIPInfo(EnumModName pMod, NetIPInfo pIPInfo){
		//System.out.println("ADDING " + pMod.getModAbbrev() + ", IP PORT = " + pIPInfo.getPort());
		//TODO 8FORNAV: Warning when IP Address is NULL (which is what happens from CC?)

		if (pIPInfo.getIPAddress()==null){
			//System.out.println("WARNING: IP Address is NULL for " +  pMod.getModName() + " (MSG AT: SysConfig.addIPInfo)");
		}

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
		//System.out.println("SysConfig.setIPregistrationCompleted, changed to =  " + mIPregistrationCompleted);

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

	public static double getSpeedTKR_Normal(){
		return mSpeedTKR_Normal;
	}

	public static double getSpeedTKR_Congested(){
		return mSpeedTKR_Congested;
	}

	//Used for checking of prestart info:

	public static boolean getStateCheck_TLLoaded(){
		return mStateCheck_TLLoaded;
	}

	public static boolean getStateCheck_StopLoaded(){
		return mStateCheck_StopLoaded;
	}

	public static boolean getStateCheck_RouteLoaded(){
		return mStateCheck_RouteLoaded;
	}

	public static boolean getStateCheck_CongAreaLoaded(){
		return mStateCheck_CongAreaLoaded;
	}
	
	public static void setStateCheck_TLLoaded(boolean b){
		mStateCheck_TLLoaded = b;
	}

	public static void setStateCheck_StopLoaded(boolean b){
		mStateCheck_StopLoaded = b;
	}

	public static void setStateCheck_RouteLoaded(boolean b){
		mStateCheck_RouteLoaded = b;
	}

	public static void setStateCheck_CongAreaLoaded(boolean b){
		mStateCheck_CongAreaLoaded = b;
	}

	public static boolean getStateCheck_IsPrestartDone(){
		return mStateCheck_TLLoaded && 
		mStateCheck_StopLoaded && 
		mStateCheck_RouteLoaded &&
		mStateCheck_CongAreaLoaded;
	}

	public static int getSVNRevNum(){
		return mSVNRevNum;
	}
	
	public static boolean getAllowUserInputMode(){
		return mAllowUserInputMode;
	}
	
	public static void setAllowUserInputMode(boolean b){
		mAllowUserInputMode = b;
	}
}
