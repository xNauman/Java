package rawobjectsPriv;

import java.awt.Point;
import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

/**
 * Used by SCH when requesting bus movement of NAV.
 * 
 * @author Ben
 *
 */

public class ReqBusMove implements Serializable {
	
	private static final long serialVersionUID = 3087361947353983117L;

	/**
	 * A bus ID (the ID is the database ID)
	 */
	private int mBusIDDB;
	
	/**
	 * A Unique identifier for this request
	 */
	private UUID mMoveRequestUUID;
	
	/**
	 * A unique identifier for the point request. Used By SCH
	 */
	private UUID mPointRequestUUID;
	
	/**
	 * Where SCH would like to start from
	 */
	private Point mMovePointStart;
	
	/**
	 * Where SCH would like to move to (end at)
	 */
	private Point mMovePointEnd;
	
	/**
	 * The time this request was sent
	 */
	private long mRequestSentTimeStamp;
	
	/**
	 * When should this request start
	 */
	private int mMoveTimeStart;
	
	/**
	 * When should this request end
	 */
	private int mMoveTimeEnd;
	
	/**
	 * Default constructor, that sets the UUID.
	 */
	public ReqBusMove(){
		this.mMoveRequestUUID = UUID.randomUUID();
		this.mRequestSentTimeStamp = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Constructor, that accepts a bus object and the from/to point
	 * @param pBus The bus object to be moved.
	 * @param pPointStart The point the bus will be moved from.
	 * @param pPointEnd The point the bus should be moved to.
	 */
	public ReqBusMove(int pBusIDDB, Point pPointStart, Point pPointEnd){
		super();
		
		this.mBusIDDB = pBusIDDB;
		this.mMovePointStart = pPointStart;
		this.mMovePointEnd = pPointEnd;
	}
	
	/**
	 * Sets the request sent time stamp to current time.
	 */
	public void setRequestSentTimeStamp(){
		this.mRequestSentTimeStamp = Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * Sets the request sent time stamp to the provide time stamp.
	 * 
	 * @param pRequestTimeStamp Time stamp of when request was made.
	 */
	public void setRequestSentTimeStamp(long pRequestTimeStamp){
		this.mRequestSentTimeStamp = pRequestTimeStamp;
	}
	
	public long getRequestSentTimeStamp(){
		return this.mRequestSentTimeStamp;
	}
	
	public UUID getMoveRequestUUID(){
		return this.mMoveRequestUUID;
	}
	
	public void setBus(int pBusIDDB){
		this.mBusIDDB = pBusIDDB;
	}
	
	public int getBus(){
		return this.mBusIDDB;
	}
	
	public void setPointUUID(UUID pUUID){
		this.mPointRequestUUID = pUUID;
	}
	
	public UUID getPointUUID(){
		return this.mPointRequestUUID;
	}
	
	public void setPointStart(Point pPoint){
		this.mMovePointStart = pPoint;
	}
	
	public Point getPointStart(){
		return this.mMovePointStart;
	}
	
	public void setPointEnd(Point pPoint){
		this.mMovePointEnd = pPoint;
	}
	
	public Point getPointEnd(){
		return this.mMovePointEnd;
	}
	
	public int getMovementTimeStart(){
		return this.mMoveTimeStart;
	}
	
	public void setMovementTimeStart(int pMoveTimeStart){
		this.mMoveTimeStart = pMoveTimeStart;
	}
		
	public int getMovementTimeEnd(){
		return this.mMoveTimeEnd;
	}
	
	public void setMovementTimeEnd(int pMoveTimeEnd){
		this.mMoveTimeEnd = pMoveTimeEnd;
	}
		
	/**
	 * Prints debug information on this request to console.
	 * 
	 * @param pOutPrefix Text (if any) to begin each line sent to console.
	 * @param pOutSuffix Text (if any) to end each line sent to console.
	 */
	public void debugRequestInfo(String pOutPrefix, String pOutSuffix){
		doDebug("Request Information", pOutPrefix, pOutSuffix);
		doDebug("  Req UUID = " + this.mMoveRequestUUID.toString(), pOutPrefix, pOutSuffix);
		doDebug("  Bus DB ID = " + this.mBusIDDB, pOutPrefix, pOutSuffix);
		doDebug("  Moving From = " + this.mMovePointStart.toString(), pOutPrefix, pOutSuffix);
		doDebug("  Moving To = " + this.mMovePointEnd.toString(), pOutPrefix, pOutSuffix);
		doDebug("  Request Sent At = " + util.Formatter.formatDate(this.mRequestSentTimeStamp), pOutPrefix, pOutSuffix);
	}
	
	private void doDebug(String pMsg, String pOutPrefix, String pOutSuffix){
		System.out.println(pOutPrefix + pMsg + pOutSuffix);
	}
}
