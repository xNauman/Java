package route;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import config.SysConfig;

import rawobjects.NavRequest;
import rawobjectsPriv.ReqBusMove;
import rmi_engine.SCHClientImplDB;
import rmi_engine.SCHClientImplNAV;
import rmi_engine.SCHClientImplTKR;
import rmi_interfaces.DBServer;
import rmi_interfaces.NAVServer;
import rmi_interfaces.TKRServer;
import routeCheck.SchEnumObjectType;
import routeCheck.SchEnumSplitLoc;
import routeCheck.SchMovePointData;

import util.Debug;
import util.Formatter;
import util.General;

public class SchBusObjTHD implements Runnable {
	private int mBusID=0;  //this is INTERNAL value
	private int mBusIDRoute=0;  //this is INTERNAL value
	private int mBusDBID=-1;  //this is DB value
	private boolean mIsActive=true;
	private boolean mIsStarted=false;
	private boolean mDoKill=false;
	private long mStartRunTime=0; //The simulator system time this bus should commence at
	private Vector<Point> mRoutePointList; //list of points that this bus will travel on

	private int mCurrPoint; //What is the current point the bus is up to
	private long mDoNextMoveRequest; //At what Sys Sim Time, should BUS request a move to NAV? 

	private Queue<SchRouteMoveReq> mRequestsToNav = new LinkedList<SchRouteMoveReq>();
	private SchRouteMoveReqTrace mCurrRequestToNav = null;
	private long mNAVRequestTimeOutAt = -1; //The time the request was first believed to have timed out;

	private boolean mIsBusInCongArea = false; //is the bus currently in a congested area?
	private rawobjects.Bus mBusObj;

	public SchBusObjTHD(int pBusID, int pBusIDRoute, rawobjects.Bus pBus, Vector<Point> pRoutePointList){
		this.mBusID = pBusID;
		this.mBusDBID = pBus.getBusID();
		
		//System.out.println("************************* DB BUS ID = " + pBus.getBusID());
		
		this.mBusIDRoute = pBusIDRoute;
		this.mRoutePointList=pRoutePointList;
		this.mBusObj = pBus;
		this.mBusObj.setCurrLoad(this.mBusObj.getDefaultLoad()); //Set default load

		debugShowPaxCountInfo();

		this.mCurrPoint = 0;
	}

	private String getDebugName(){
		return "SchBusObj[DBID=" + this.mBusDBID + "|| on RTEID=" + this.mBusIDRoute + ", INTBusID=" + this.mBusID + "]";
	}

	@Override
	public void run() {
		while (!mDoKill){
			if (mIsStarted) {
				if (this.mIsActive){ //	
					try {
						procMove();
						Thread.sleep(500);
						handlePendingNAVResp(); //Check for a response
						Thread.sleep(800);
						//System.out.println("CURR AT: " + this.mCurrPoint + " || QUEUE SIZE: " + this.mRequestsToNav.size());

					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
				else{
					try {
						Thread.sleep(1200); //bus is sleeping
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			}
			else
			{
				if (!this.canBusStart());
				{
					try {
						Thread.sleep(900); //bus is not ready to sleep, so wait
					} catch (InterruptedException e) {
						e.printStackTrace();
					} 
				}
			}
		}
	}

	private boolean canBusStart(){
		//Debug.writeDebugMsg(getDebugName(), "canBusStart", this.mStartRunTime + " < " + util.General.getSimulatorTime());

		if (this.mStartRunTime == 0){
			//TODO 4LOG [LOGGING]: Log Entry?
			Debug.writeDebugMsg(getDebugName(), "canBusStart", "WARN-THDPRT: Bus does not have start time. Will be killed.");
			this.mDoKill=true;		
		}

		//Debug.writeDebugMsg(getDebugName(), "canBusStart", "XX Now:" + util.Formatter.formatDate(util.General.getSimulatorTime()));
		//Debug.writeDebugMsg(getDebugName(), "canBusStart", "XXXXX START AT:" + util.Formatter.formatDate(this.mStartRunTime));
		if (util.General.getSimulatorTime_UnixTS()>this.mStartRunTime){
			Debug.writeDebugMsg(getDebugName(), "canBusStart", "BUS STARTED!");
			//Set the NextMoveRequest time to now, so the first request is made.
			this.mDoNextMoveRequest = util.General.getSimulatorTime_UnixTS();
			this.mIsStarted=true;			
		}

		return this.mIsStarted;
	}

	public boolean getIsActive(){
		return this.mIsActive;
	}

	public void setIsActive(boolean b){
		this.mIsActive=b;
	}

	public void doKillBus(){
		this.mDoKill=true;
	}

	public int getBusDBID(){
		return this.mBusDBID;
	}

	public void setBusDBID(int pBusDBID){
		this.mBusDBID = pBusDBID;
	}

	public boolean setBusStartTime(long pStartTime){
		if (this.mStartRunTime==0){
			this.mStartRunTime=pStartTime;
			return true;
		}
		return false;
	}

	/*********** MAIN LOGIC BEHIND MOVING A BUS  ***********/

	private void procMove(){
		if (this.isTimeToMove() && !this.hasPendingNAVRequests()){ 
			//OK to move, and no pending move requests

			//System.out.println("~~~~~~~~~~~~~~~ START ~~~~~~~~~~~~~~~");
			this.doMoveRequest();
			//System.out.println("~~~~~~~~~~~~~~~ END ~~~~~~~~~~~~~~~");
			this.sendNextRequestToNAV();
		}
		else if (this.isTimeToMove() && this.hasPendingNAVRequests()){
			/*Can move, but has outstanding requests to send to NAV, so these
			 * need to be send first.
			 */

			this.sendNextRequestToNAV();
		}
	}

	/**
	 * Is it time for this bus to move to the next point
	 * (This is based on the last move, and the expectation that TRK should be ready for it)
	 * @return
	 */
	private boolean isTimeToMove(){
		if (this.mDoNextMoveRequest<util.General.getSimulatorTime_UnixTS()){
			return true;
		}
		return false;
	}

	private boolean hasPendingNAVRequests(){
		return this.mRequestsToNav.size()>0;
	}

	private void doMoveRequest(){
		if (this.mRoutePointList.size()<=0) {
			Debug.writeDebugMsg(getDebugName(), "doMoveRequest", "Cannot Move - Route Point List Empty");
			return;
		}

		//showPointList();

		//1. Perform a route check to locate obstructions
		Point startPoint = this.getPointStart(this.mCurrPoint);
		Point endPoint = this.getPointEnd(this.mCurrPoint);

		Debug.writeDebugMsg(getDebugName(), "doMoveRequest", "Want to Move from " + startPoint.toString() + " to " + endPoint.toString() + ", currently at: " + this.mCurrPoint);

		Vector<routeCheck.SchMovePointData> vMoveData = routeCheck.SchRouteChecker.getMovePointData(startPoint, endPoint);

		//System.out.println("SchBusObjTHD vMoveDataSize = " + vMoveData.size());
		
		if (vMoveData.size()==0){
			//Means that there are no objects/obstructions in between the start and end point.
			SchMovePointData d = new SchMovePointData();
			
			
			//TODO 0CRIT: Do you need a starting point?
			//TODO 0CRIT: Turn on debug info for Requests to NAV
			/*d.setPoint(startPoint);
			d.setObjectTypeID(SchEnumObjectType.NORMAL);
			vMoveData.add(d);*/

			d = new SchMovePointData();
			d.setPoint(endPoint);
			d.setObjectTypeID(SchEnumObjectType.NORMAL);
			vMoveData.add(d);
			
			System.out.println("No Obstruct; ADDING EndPoint = " + d.getPoint().toString());
			
		}
		else {
			System.out.println("OBSTRUCTIONS exist");
		}

		SchRouteMoveReq singleMoveReq=null;

		boolean atStart=true;
		Point baseStartPoint = startPoint;

		SchMovePointData movePoint = null;
		//2. Split up the route check data into the parts need for NAV
		if (vMoveData.size() > 0) {
			if (vMoveData.size()==1) {
				System.out.println("SchBusObjTHD:doMoveRequest - AAAAA");
				movePoint = vMoveData.get(0);

				singleMoveReq = new SchRouteMoveReq();
				singleMoveReq.setPointStart(startPoint);
				singleMoveReq.setPointEnd(movePoint.getPoint(),movePoint,true);
				mRequestsToNav.add(singleMoveReq);
			} 
			else {
				System.out.println("SchBusObjTHD:doMoveRequest - BBBBB");
				for (int i = 0;i<vMoveData.size();i++){
					movePoint = vMoveData.get(i);

					if (atStart) { 
						singleMoveReq = new SchRouteMoveReq();
						singleMoveReq.setPointStart(baseStartPoint);
						atStart=false;
					}

					if (movePoint.getObjectTypeID().getNAVSplitLocation() == SchEnumSplitLoc.SPLIT_BEFORE){
						singleMoveReq.setPointEnd(movePoint.getPoint(), movePoint,true);
						mRequestsToNav.add(singleMoveReq);

						singleMoveReq = new SchRouteMoveReq();
						baseStartPoint = movePoint.getPoint();
						singleMoveReq.setPointStart(baseStartPoint);
						atStart=false;
					}

					Debug.writeDebugMsg(getDebugName(), "doMoveRequest", "MOVE_ITEM_" + i + " is a " + movePoint.getObjectTypeID(true));
					Debug.writeDebugMsg(getDebugName(), "doMoveRequest", "...MOVE_ITEM_" + i + " at  " + movePoint.getPoint().toString());
					Debug.writeDebugMsg(getDebugName(), "doMoveRequest", "...MOVE_ITEM_" + i + " dist between this and start point =  " + movePoint.getPoint().distance(startPoint));

					if (movePoint.getObjectTypeID().getNAVSplitLocation() == SchEnumSplitLoc.SPLIT_AFTER){
						singleMoveReq.setPointEnd(movePoint.getPoint(),movePoint,true);
						mRequestsToNav.add(singleMoveReq);

						singleMoveReq = new SchRouteMoveReq();
						baseStartPoint = movePoint.getPoint();
						singleMoveReq.setPointStart(baseStartPoint);
						atStart=false;
					}


					//if at the end, need to add the final point to move to the end of the line
					if (i == vMoveData.size()-1){
						//if (singleMoveReq.isReadyForSending(false)) {
						if (singleMoveReq.hasEndPoint()) {	
							//System.out.println("TEST----------------------------------------------------");
							singleMoveReq = new SchRouteMoveReq();
							//System.out.println("START_PT = " + movePoint.getPoint().toString());
							baseStartPoint = movePoint.getPoint(); //use the last end point
							singleMoveReq.setPointStart(baseStartPoint);
							atStart=false;

							//System.out.println("END_PT = " + endPoint.toString());

							if (singleMoveReq.setPointEnd(endPoint,movePoint,true))
								mRequestsToNav.add(singleMoveReq);

						}
						else {
							//End the existing move request point
							singleMoveReq.setPointEnd(endPoint,movePoint,true);
							mRequestsToNav.add(singleMoveReq);
						}
					}
					//System.out.println("**** A");
				}

				//System.out.println("EXIT_LOOP_VAL = " + movePoint.getPoint().toString());
			}
		}

		//Debug.writeDebugMsg(getDebugName(), "doMoveRequest","OUT_OF_PRIMARY_SPLIT_WORK");

		if (this.mRequestsToNav.size() > 0){
			Debug.writeDebugMsg(getDebugName(), "doMoveRequest", this.mRequestsToNav.size() + " pending request(s) to NAV Exist.");

			//this.dumpNAVQueue();
		}		
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private void dumpNAVQueue(){
		//Copy the existing queue so its not destroyed
		//Queue<SchRouteMoveReq> copyRequestToNav = this.mRequestsToNav;
		Queue<SchRouteMoveReq> copyRequestToNav = util.General.copyQueue(this.mRequestsToNav);

		int orgSize = copyRequestToNav.size();

		if (mRequestsToNav.size() > 0){
			Debug.writeDebugMsg(getDebugName(), "doMoveRequest", copyRequestToNav.size() + " pending request(s) to NAV Exist.");

			int i = 1;
			while (!copyRequestToNav.isEmpty()){
				System.out.println("************** QUEUE ENTRY " + i + " of " + orgSize + "************************");

				SchRouteMoveReq req = copyRequestToNav.poll();

				System.out.println("SUB-POINTS: ");
				req.debugDumpQueue();

				System.out.println("****************************************************");
				i++;
			}
		}
	}

	private boolean sendNextRequestToNAV(){

		if (!this.hasPendingNAVRequests())
			return false;

		if (this.mCurrRequestToNav!=null){
			if (checkNAVReqTimeOut()) {
				if (mNAVRequestTimeOutAt <= 0) //Only set if it hasn't been set before
					mNAVRequestTimeOutAt = General.getSimulatorTime_Actual(); //General.getSimulatorTime(); 

				//TODO 4LOG [LOGGING]: Log Entry
				Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", "WARNING - Request to NAV has timed out: Req GUID = " + mCurrRequestToNav.getNAVReqUUID());
				SysConfig.perfStat_NAVReqTimeOut_Increment();

				if (checkNAVReqTimeOutDueAutoKill()){
					//TODO 4LOG [LOGGING]: Log Entry
					Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", "WARNING - Request has been killed: Req GUID = " + mCurrRequestToNav.getNAVReqUUID());
					SysConfig.perfStat_NAVReqTimeOutWithKill_Increment();
					this.mCurrRequestToNav = null; //clear the request
				}
			}

			return false; //An existing request is pending to NAV, need to wait for that to be replied to.
		}

		boolean bContinue=true;

		while (bContinue){
			//Get the first request
			SchRouteMoveReq req = this.mRequestsToNav.peek();

			System.out.println("SchBusObjTHD:sendNextRequestToNAV - PreReadySending");
			if (req.isReadyForSending(true)){
				//Create a request to NAV object
				ReqBusMove reqToNav = new ReqBusMove();
				SchDoublePoint pt = req.getPointFirst();

				reqToNav.setPointUUID(pt.getPointUUID());
				reqToNav.setPointStart(pt.getStartPoint());
				reqToNav.setPointEnd(pt.getEndPoint());
				reqToNav.setBus(this.mBusDBID); //
				
				//TODO 3LOW Check time check
				int startTime = Math.abs((int) util.General.getSimulatorTime_Actual()); //(int)util.General.getSimulatorTime();
				double moveDist = Math.abs(reqToNav.getPointStart().distance(reqToNav.getPointEnd()));
				int endTime = Math.abs(startTime + (int)(moveDist * SysConfig.getSpeedTKR_Normal()));
				endTime = endTime + 10; //add a allowance to allow for network comms overhead
					
				reqToNav.setMovementTimeStart(startTime);
				reqToNav.setMovementTimeEnd(endTime);

				mNAVRequestTimeOutAt = -1;
				reqToNav.setRequestSentTimeStamp();

				this.mCurrRequestToNav = new SchRouteMoveReqTrace(reqToNav.getMoveRequestUUID(), pt.getPointUUID(), reqToNav.getRequestSentTimeStamp());

				Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", "Request is FROM: " + reqToNav.getPointStart().toString() + " TO " + reqToNav.getPointEnd().toString() + " || REQ_GUID = " + reqToNav.getMoveRequestUUID().toString());
				Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", ".....Start Time = " + reqToNav.getMovementTimeStart() + ", End Time = " + reqToNav.getMovementTimeEnd());
				
				if (sendRequestToNAVuseRMI(reqToNav)){
					//Request sent OK
					//TODO 3LOW? [DB UPD]: UPDATE DB With Request details
					SysConfig.perfStat_NAVReq_Increment();
					Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", "Request To NAV for MOVE OK sent and recd OK");
				}
				else {
					//fails to send across
					this.mCurrRequestToNav = null;
					Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", "Request To NAV for sent and recd MOVE FAILS ***");
				}
				bContinue = false; //A request has been sent, stop requesting it.
			}
			else {
				//TODO 4LOG [LOGGING]: Log Entry as shows issue with route calculations - is ERROR
				Debug.writeDebugMsg(getDebugName(), "sendNextRequestToNAV", "WARN-NAVREQ: Incomplete NAV Request - Will be Removed.");

				this.mRequestsToNav.poll(); //removes this request

				if (this.mRequestsToNav.size() <= 0) bContinue = false;
			}
		}

		return true;
	}

	private boolean sendRequestToNAVuseRMI(ReqBusMove pReqToNav){

		//TODO 4LOG [LOGGING]: Check need to log this?
		
		try {
			NAVServer nav = (NAVServer)(new SCHClientImplNAV()).getSrvObject();
			rawobjects.Bus busMove = new rawobjects.Bus();
			busMove.setBusID(this.mBusObj.getBusID());
			busMove.setBusType(this.mBusObj.getBusType());
			busMove.setCurrentPointX(pReqToNav.getPointStart().x);
			busMove.setCurrentPointY(pReqToNav.getPointStart().y);
			busMove.setMoveToPointX(pReqToNav.getPointEnd().x);
			busMove.setMoveToPointY(pReqToNav.getPointEnd().y);
			busMove.setRouteID(this.mBusObj.getRouteID());
			
			
			//NOTE, not all items are copied for the bus object
						
			NavRequest navReq = new NavRequest(
					pReqToNav.getMovementTimeStart(),
					pReqToNav.getMovementTimeEnd(),
					busMove);
		
			/*navReq.setbBus(busMove);
			navReq.setiNavRequestID();
			navReq.setiMovementStartTime(); //(int)util.General.getSimulatorTime()
			navReq.setiMovementEndTime(pReqToNav.getMovementTimeStart());*/
			
			//return nav.requestBusMovement(pReqToNav);
			return nav.MovementRequest(navReq, pReqToNav.getMoveRequestUUID());
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		


	}

	/**
	 * Has the request to NAV timed out?
	 * @return
	 */
	private boolean checkNAVReqTimeOut(){

		if (this.mCurrRequestToNav == null)
			return false;

		long reqTime = util.General.getSimulatorTime_UnixTS()-this.mCurrRequestToNav.getRequestSentTime(); 

		if (reqTime > SysConfig.getTimeOutNAVRequest())
			return true;
		else
			return false;
	}

	/**
	 * Has the request to NAV timed out, and should it be auto killed?
	 * @return
	 */
	private boolean checkNAVReqTimeOutDueAutoKill(){

		if (SysConfig.getTimeOutNAVRequestAutoKill() <= 0)
			return false; //auto killed disabled

		if (this.mCurrRequestToNav == null)
			return false;

		long reqTime = util.General.getSimulatorTime_UnixTS()-this.mNAVRequestTimeOutAt;

		if (reqTime > SysConfig.getTimeOutNAVRequestAutoKill())
			return true;
		else
			return false;
	}

	private boolean handlePendingNAVResp(){

		if (!this.hasPendingNAVRequests())
			return false;

		if (this.mCurrRequestToNav==null)
			return false;

		if (SchRouteResp.containsResponse(this.mCurrRequestToNav.getNAVReqUUID())){
			//Response from NAV has been received

			SchRouteMoveReqResp resp = SchRouteResp.getResponse(this.mCurrRequestToNav.getNAVReqUUID(),true);

			if (resp.getResponse()) {
				//can move
				//Debug.writeDebugMsg(getDebugName(), "handlePendingNAVResp", "BUS CAN MOVE!!!!!!!!!!!!!!");

				SchRouteMoveReq moveReq = this.mRequestsToNav.peek();
				SchMovePointData moveData = moveReq.getMovePointData(); 

				//Check for congested area status
				if (moveData.getObjectTypeID()==SchEnumObjectType.CONGESTED_AREA_START)
					mIsBusInCongArea = true;
				else if (moveData.getObjectTypeID()==SchEnumObjectType.CONGESTED_AREA_END)
					mIsBusInCongArea = false;

				//Prepare Stop Info
				if (moveData.getObjectTypeID()==SchEnumObjectType.STOP){
					Debug.writeDebugMsg(getDebugName(), "handlePendingNAVResp","AT STOP (DBID) = " + moveData.getRefDBID());

					//Get the stop object, and then call the method
					doPaxMovement(SysConfig.stopObjGet(moveData.getRefDBID()),true);
				}


				//ReqEXTUpdBus updBus = new ReqEXTUpdBus();
				//updBus.setPointFrom(moveReq.getPointFirst().getStartPoint().getLocation());
				//updBus.setPointTo(moveReq.getPointFirst().getEndPoint().getLocation());
				//updBus.setInCongestedArea(mIsBusInCongArea);
				
				/*rawobjects.Bus updBus = new rawobjects.Bus();
				updBus.setiBusID(this.mBusDBID);
				updBus.setiCurrentPointX(moveReq.getPointFirst().getStartPoint().x);
				updBus.setiCurrentPointY(moveReq.getPointFirst().getStartPoint().y);
				updBus.setiMoveToPointX(moveReq.getPointFirst().getEndPoint().x);
				updBus.setiMoveToPointY(moveReq.getPointFirst().getEndPoint().y);*/
				
				this.mBusObj.setCurrentPointX(moveReq.getPointFirst().getStartPoint().x);
				this.mBusObj.setCurrentPointY(moveReq.getPointFirst().getStartPoint().y);
				this.mBusObj.setMoveToPointX(moveReq.getPointFirst().getEndPoint().x);
				this.mBusObj.setMoveToPointY(moveReq.getPointFirst().getEndPoint().y);
				
				//Move the bus
				updateBus_TKR(this.mBusObj, mIsBusInCongArea); //Update Tracker
				updateBus_DB(this.mBusObj); //Update DB
				

				//Set Next Check Time				
				double pSpeed = 1; //Set to 1 in case of fault
				
				if (mIsBusInCongArea)
					pSpeed = SysConfig.getSpeedTKR_Congested();
				else
					pSpeed = SysConfig.getSpeedTKR_Normal();
				
				//double TMP_DistMod = 40;	//TEMP - made to make the distance longer
				//this.setNextNAVRequestTime(moveReq.getPointFirst().getStartPoint().distance(moveReq.getPointFirst().getEndPoint())+TMP_DistMod, pSpeed, SysConfig.getDistanceToBeCompleted());
				
				this.setNextNAVRequestTime(moveReq.getPointFirst().getStartPoint().distance(moveReq.getPointFirst().getEndPoint()), pSpeed, SysConfig.getDistanceToBeCompleted());

				//Remove queue entry for this point, so next one can be requested
				moveReq.removePoint(this.mCurrRequestToNav.getSCHDoublePointReqUUID());
				//this.mRequestsToNav.peek().debugDumpQueue();

				//If there are no more queue points for this entry
				if (moveReq.isQueueEmpty()) {
					this.mRequestsToNav.poll();
				}				

				if (this.mRequestsToNav.isEmpty()){
					this.mCurrPoint++; //Move to the next route point

					if (this.mCurrPoint == this.mRoutePointList.size()-1) {
						Debug.writeDebugMsg(getDebugName(), "run", "All Points Processed - Killing Bus Thread");
						mIsActive = false;
					}
				}				
			}
			else {
				//cannot move, so request must remain in queue
				//Debug.writeDebugMsg(getDebugName(), "handlePendingNAVResp", "BUS CAN NOT MOVE!!!!!!!!!!!!!!");
			}

			this.mCurrRequestToNav = null; //Reset so no request is pending

			return true;
		}

		return false;
	}

	/**
	 * Sets the mDoNextMoveRequest time, which indicates when should next request a move to NAV.
	 * @param pDistance Distance to travel in last request
	 * @param pSpeed Movement Speed in milliseconds, based on Sim Time
	 * @param pOnceComplete Percentage representation of when a next request should be made
	 */
	private void setNextNAVRequestTime(double pDistance, double pSpeed, double pOnceComplete){	
		System.out.println("DIST to TRAVEL: " + Math.abs(pDistance));

		double distanceRequired = Math.abs(pDistance) * pOnceComplete;

		long estimatedTime = (long) (distanceRequired / pSpeed);

		//this.mDoNextMoveRequest = General.getSimulatorTime() + estimatedTime;
		this.mDoNextMoveRequest = General.getSimulatorTime_Actual() + estimatedTime;
		Debug.writeDebugMsg(getDebugName(), "setNextNAVRequestTime", "Next Check At: " + Formatter.formatDate(this.mDoNextMoveRequest) + " (approx " + estimatedTime + " milliseconds)");
	}

	private Point getPointStart(int pAtCurrPointIndex){
		if ((pAtCurrPointIndex >= 0) && (pAtCurrPointIndex < this.mRoutePointList.size())){
			return this.mRoutePointList.get(pAtCurrPointIndex);
		}
		else
			return null; //new Point(0,0);

	}

	private Point getPointEnd(int pAtCurrPointIndex){
		if ((pAtCurrPointIndex + 1 > 0) && (pAtCurrPointIndex + 1 < this.mRoutePointList.size())){
			return this.mRoutePointList.get(pAtCurrPointIndex+1);
		}
		else
			return null; //new Point(0,0)
	}

	@SuppressWarnings("unused")
	private void showPointList(){
		Debug.writeDebugMsg(getDebugName(), "showPointList", "Point Listing:");

		for (int i = 0;i<this.mRoutePointList.size();i++){
			Point p = mRoutePointList.get(i);	
			Debug.writeDebugMsg(getDebugName(), "showPointList", " ..." + (i + 1) + ": " + p.toString()); 
		}
	}


	public int getPaxCount(){
		return mBusObj.getCurrLoad();
	}

	public void setPaxCount(int pPaxCount){
		mBusObj.setCurrLoad(pPaxCount);
		
	}

	public int getPaxCountMax(){
		return this.mBusObj.getBusType().getMaxCapacity();
	}

	private void debugShowPaxCountInfo(){
		Debug.writeDebugMsg(getDebugName(), "debugShowPaxCountInfo", "BUS DEFAULT LOAD: " + this.mBusObj.getDefaultLoad());
		Debug.writeDebugMsg(getDebugName(), "debugShowPaxCountInfo", "BUS CURR LOAD: " + this.getPaxCount());
		Debug.writeDebugMsg(getDebugName(), "debugShowPaxCountInfo", "BUS MAX LOAD: " + this.getPaxCountMax());
	}

	/*
	 * Handles the movement of passengers between buses and stops
	 */
	private void doPaxMovement(rawobjects.BusStop pStop, boolean doStopUpdate){

		//Number of passengers getting OFF bus: BUS --> STOP
		int offBusPaxCount = this.doPaxMovement_OffBus();

		//Number of passengers getting ON bus: STOP --> BUS
		int onBusPaxCount = this.doPaxMovement_OnBus(pStop);

		Debug.writeDebugMsg(getDebugName(), "doPaxMovement","..... BUS wants to DROP: " + offBusPaxCount + " (BUS MAX CAP: " + this.getPaxCountMax() + ")");
		Debug.writeDebugMsg(getDebugName(), "doPaxMovement","..... STOP wants to ADD : " + onBusPaxCount + " (STOP MIN/MAX; CURR = " + pStop.getPX_Min() + "/" + pStop.getPX_Max() + " @ " + pStop.getPX_Count()  + ")");
		debugShowPaxCountInfo();


		//Determine net changes
		boolean bPaxMovementOK = false;
		int iLoopProtectCounter = 0;
		int countAtStop = 0;
		int countOnBus = 0;
		
		SysConfig.perfStat_PaxMoveReqMade_Increment();
		
		while (!bPaxMovementOK && iLoopProtectCounter < 5){
			iLoopProtectCounter++;
			SysConfig.perfStat_PaxMoveReq_Increment();
			countAtStop = pStop.getPX_Count() - onBusPaxCount + offBusPaxCount;
			countOnBus = this.getPaxCount() + onBusPaxCount - offBusPaxCount;
			
			Debug.writeDebugMsg(getDebugName(), "doPaxMovement","..... MOVEMENT REQUEST - New Bus Load: " + countOnBus + " (BUS MAX CAP: " + this.getPaxCountMax() + ")");
			Debug.writeDebugMsg(getDebugName(), "doPaxMovement","..... MOVEMENT REQUEST - New Stop Load: " + countAtStop + " (STOP MIN/MAX = " + pStop.getPX_Min() + "/" + pStop.getPX_Max() + ")");
			
			if (util.General.between(pStop.getPX_Min(),countAtStop,pStop.getPX_Max()) && util.General.between(0,countOnBus,this.getPaxCountMax())){
				//System.out.println("PAX MOVEMENTS OK - all in range");
				bPaxMovementOK = true;
			}
			else {
				//System.out.println("PAX MOVEMENTS FAIL - EXCEED RANGE, do check");
				
				if (countAtStop<=pStop.getPX_Max()){
					System.out.println("Get new ON Bus Count");
					onBusPaxCount = this.doPaxMovement_OnBus(pStop);
				}
				
				if (countOnBus<=this.getPaxCountMax()){
					System.out.println("Get new OFF Bus Count");
					offBusPaxCount = this.doPaxMovement_OffBus();;
				}
			}
		}
		
		if (!bPaxMovementOK) {
			//If loop protection trip, just ignore this stop, so that the numbers remain the same.
			Debug.writeDebugMsg(getDebugName(), "doPaxMovement","..... MOVEMENT REQUEST FAIL - Loop Protection Causes Trip");
			SysConfig.perfStat_PaxMoveLoopTrip_Increment();
			countAtStop = pStop.getPX_Count();	
			countOnBus = this.getPaxCount();
			onBusPaxCount = 0;
			offBusPaxCount = 0;
		}

		//Update this bus object
		this.setPaxCount(countOnBus);
		
		//Update the STOP object
		pStop.setPX_Count(countAtStop);
		SysConfig.stopObjAdd(pStop);
				
		if (doStopUpdate){
			this.updateStop_DB(pStop);
			this.updateStop_TKR(pStop);
			
			//CREATE a PaxStat Entry
			rawobjects.Pax_Stat pStat = new rawobjects.Pax_Stat();
			pStat.setBusID(this.mBusDBID);
			pStat.setBusStopID(pStop.getBusStopID());
			pStat.setSimRunID(config.SysConfig.getSimRunID());
			pStat.setCount(onBusPaxCount - offBusPaxCount);
			
			updatePaxStat_DB(pStat);
		}
		
	}

	/**
	 * Provides a random number of passengers that wish to get off the bus 
	 * based on the maximum capacity of the bus.
	 * 
	 * @return Random Number of Passengers to get off the bus
	 */
	private int doPaxMovement_OffBus(){
		//Random number of passengers want to get off this bus
		int offBusPaxCount = util.General.randomNumberMAX(0,this.getPaxCount());

		return offBusPaxCount;

	}

	/**
	 * Provides a random number of passengers that wish to get on a bus at the request stop 
	 * based on the minimum, maximum and current number at the stop.
	 * 
	 * @param pStop The stop to perform the calculation on
	 * @return Random Number of Passengers to get off the bus
	 */
	private int doPaxMovement_OnBus(rawobjects.BusStop pStop){
		//Random number of passengers want to get on at the stop
		int stopAvbCapacity = pStop.getPX_Max() - pStop.getPX_Min() - pStop.getPX_Count();

		int onBusPaxCount = util.General.randomNumberMAX(pStop.getPX_Min(), stopAvbCapacity);

		return onBusPaxCount;
	}

	
	private boolean updateBus_DB(rawobjects.Bus updBus){
		//Notify DB
		try {
			DBServer db = (DBServer)new SCHClientImplDB().getSrvObject();

			if (db.updateBUS(updBus)){
				Debug.writeDebugMsg(getDebugName(), "updateBus_DB", "....RMI - DB Update OK.");
				return true;
			}
			else {
				Debug.writeDebugMsg(getDebugName(), "updateBus_DB", "....RMI - Failure: DB returns FALSE");
				return false;
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_DB", "....RMI - DB Failure: Remote Exception.");
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_DB", "....RMI - DB Failure: Not Bound Exception.");
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_DB", "....RMI - DB Failure: SQL Exception.");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updateBus_TKR(rawobjects.Bus updBus, boolean pIsCongestedArea){
		try {
			TKRServer tkr = (TKRServer)new SCHClientImplTKR().getSrvObject();

			SysConfig.perfStat_TKRReq_Increment();

			if (tkr.moveBus(updBus, pIsCongestedArea)){
				Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - TKR Update OK.");
				SysConfig.perfStat_TKRReqOK_Increment();
				return true;
			}
			else {
				Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - Failure: TKR returns FALSE");
				SysConfig.perfStat_TKRReqFail_Increment();
				return false;
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - TKR Failure: Remote Exception.");
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - TKR Failure: Not Bound Exception.");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updateStop_DB(rawobjects.BusStop pStop){
		//Notify DB
		
		try {
			DBServer db = (DBServer)new SCHClientImplDB().getSrvObject();

			if (db.updateBusStop(pStop)){
				Debug.writeDebugMsg(getDebugName(), "updateStop_DB", "....RMI - DB Update OK.");
				return true;
			}
			else {
				Debug.writeDebugMsg(getDebugName(), "updateStop_DB", "....RMI - Failure: DB returns FALSE");
				return false;
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg(getDebugName(), "updateStop_DB", "....RMI - DB Failure: Remote Exception.");
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			Debug.writeDebugMsg(getDebugName(), "updateStop_DB", "....RMI - DB Failure: Not Bound Exception.");
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			Debug.writeDebugMsg(getDebugName(), "updateStop_DB", "....RMI - DB Failure: SQL Exception.");
			e.printStackTrace();
			return false;
		}
	}
		
	private boolean updateStop_TKR(rawobjects.BusStop pStop){
		//Notify TKR
		
		try {
			TKRServer tkr = (TKRServer)new SCHClientImplTKR().getSrvObject();

			SysConfig.perfStat_TKRReq_Increment();

			if (tkr.updateStop(pStop)){
				Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - TKR Update OK.");
				SysConfig.perfStat_TKRReqOK_Increment();
				return true;
			}
			else {
				Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - Failure: TKR returns FALSE");
				SysConfig.perfStat_TKRReqFail_Increment();
				return false;
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - TKR Failure: Remote Exception.");
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			Debug.writeDebugMsg(getDebugName(), "updateBus_TKR", "....RMI - TKR Failure: Not Bound Exception.");
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean updatePaxStat_DB(rawobjects.Pax_Stat pStat){
		//Notify DB
		
		try {
			DBServer db = (DBServer)new SCHClientImplDB().getSrvObject();

			if (db.updatePAX(pStat)){
				Debug.writeDebugMsg(getDebugName(), "updatePaxStat_DB", "....RMI - DB Update OK.");
				return true;
			}
			else {
				Debug.writeDebugMsg(getDebugName(), "updatePaxStat_DB", "....RMI - Failure: DB returns FALSE");
				return false;
			}
		} catch (RemoteException e) {
			Debug.writeDebugMsg(getDebugName(), "updatePaxStat_DB", "....RMI - DB Failure: Remote Exception.");
			e.printStackTrace();
			return false;
		} catch (NotBoundException e) {
			Debug.writeDebugMsg(getDebugName(), "updatePaxStat_DB", "....RMI - DB Failure: Not Bound Exception.");
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			Debug.writeDebugMsg(getDebugName(), "updatePaxStat_DB", "....RMI - DB Failure: SQL Exception.");
			e.printStackTrace();
			return false;
		}
	}
}
