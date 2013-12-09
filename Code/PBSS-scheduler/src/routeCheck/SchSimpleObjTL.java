package routeCheck;

import java.awt.Point;
/**
 * A simple object for Traffic Lights and Stops, that contains the
 * needed information for object detection to be performed.
 * 
 * @author Ben
 *
 */

//also used for junctions
public class SchSimpleObjTL {
	private int mTLID; //As used by the SCH system
	private int mTLDBID; //as used in the DB
	private Point mTLPoint; //Location of this traffic light
	private boolean mIsJunction; //is this a junction or traffic light?
	
	public SchSimpleObjTL(){
		this.mTLID = -1;
		this.mTLDBID = -1;
		this.mTLPoint = new Point(0,0);
		this.mIsJunction = false;	
	}
	
	public SchSimpleObjTL(int pTLID, int pTLDBID, Point pTLPoint, boolean pIsJunction){
		this.mTLID = pTLID;
		this.mTLDBID = pTLDBID;
		this.mTLPoint = pTLPoint;
		this.mIsJunction = pIsJunction;	
	}
	
	public int getTLID() {
		return mTLID;
	}
	public void setTLID(int pTLID) {
		this.mTLID = pTLID;
	}
	public int getTLDBID() {
		return mTLDBID;
	}
	public void setTLDBID(int pTLDBID) {
		this.mTLDBID = pTLDBID;
	}
	
	public Point getTLPoint() {
		return mTLPoint;
	}
	public void setTLPoint(Point pTLPoint) {
		this.mTLPoint = pTLPoint;
	}
	public boolean getIsJunction() {
		return mIsJunction;
	}
	public void setIsJunction(boolean pIsJunction) {
		this.mIsJunction = pIsJunction;
	}
}
