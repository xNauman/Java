package routeCheck;

import java.awt.Point;

/**
 * A simple object for Stops, that contains the needed information for 
 * object detection to be performed.
 * 
 * @author Ben
 *
 */
public class SchSimpleObjStop {
	private int mStopID; //As used by the SCH system
	private int mStopDBID; //as used in the DB
	private Point mStopPoint; //Location of this stop

	public SchSimpleObjStop(){
		this.mStopID = -1;
		this.mStopDBID = -1;
		this.mStopPoint = new Point(0,0); 
	}
	
	public SchSimpleObjStop(int pStopID, int pStopDBID, Point pStopPoint){
		this.mStopID = pStopID;
		this.mStopDBID = pStopDBID;
		this.mStopPoint = pStopPoint; 
	}

	public int getStopID() {
		return mStopID;
	}
	public void setStopID(int pStopID) {
		this.mStopID = pStopID;
	}
	public int getStopDBID() {
		return mStopDBID;
	}
	public void setStopDBID(int pStopDBID) {
		this.mStopDBID = pStopDBID;
	}
	public Point getStopPoint() {
		return mStopPoint;
	}
	public void setStopPoint(Point pStopPoint) {
		this.mStopPoint = pStopPoint;
	}

}
