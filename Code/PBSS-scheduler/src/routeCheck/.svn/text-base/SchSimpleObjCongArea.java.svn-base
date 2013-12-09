package routeCheck;

import java.awt.Point;

/**
 * A simple object for Congested Areas, that contains the needed information for 
 * object detection to be performed.
 * 
 * @author Ben
 *
 */
public class SchSimpleObjCongArea {

	private int mCAID; //As used by the SCH system
	private int mCADBID; //as used in the DB
	private Point mCAPointA; //Location of this congested area (point A)
	private Point mCAPointB; //Location of this congested area (point B)
	
	public SchSimpleObjCongArea(){
		this.mCAID = -1;
		this.mCADBID = -1; 
		this.mCAPointA = new Point(0,0); 
		this.mCAPointB = new Point(0,0);
	}
	
	public SchSimpleObjCongArea(int pCAID, int pCADBID, Point pCAPointA, Point pCAPointB){
		this.mCAID = pCAID;
		this.mCADBID = pCADBID; 
		this.mCAPointA = pCAPointA; 
		this.mCAPointB = pCAPointB;
	}
	
	public int getCAID() {
		return mCAID;
	}
	public void setCAID(int pCAID) {
		this.mCAID = pCAID;
	}
	public int getCADBID() {
		return mCADBID;
	}
	public void setCADBID(int pCADBID) {
		this.mCADBID = pCADBID;
	}
	public Point getCAPointA() {
		return mCAPointA;
	}
	public void setCAPointA(Point pCAPointA) {
		this.mCAPointA = pCAPointA;
	}
	public Point getCAPointB() {
		return mCAPointB;
	}
	public void setCAPointB(Point pCAPointB) {
		this.mCAPointB = pCAPointB;
	}
	
	
	
}
