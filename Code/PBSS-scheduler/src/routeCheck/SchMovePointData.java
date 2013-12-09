package routeCheck;

import java.awt.Point;

/**
 * Holds information related to those objects that will come into contact
 * when moving from Point A to Point B.
 * 
 * This is a 'response' object, that is returned back to SCH from SchRouteChecker
 * @author Ben
 *
 */

public class SchMovePointData {

	Point mPoint; //Location of item
	SchEnumObjectType mObjectType; //Object Type
	int mRefID; //Object ID (internal)
	int mRefDBID; //Object ID as used in DB
	
	public SchMovePointData(){
		this.mPoint=new Point();
		this.mObjectType=SchEnumObjectType.UNKNOWN;
		this.mRefDBID = -1;
		this.mRefID = -1;
	}
	
	public SchMovePointData(Point pPoint, SchEnumObjectType pObjectType, int pRefID, int pRefDBID){
		this.mPoint=pPoint;
		this.mObjectType=pObjectType;
		this.mRefDBID = pRefDBID;
		this.mRefID = pRefID;
	}
	
	public Point getPoint() {
		return mPoint;
	}
	public void setPoint(Point pPoint) {
		this.mPoint = pPoint;
	}
	public SchEnumObjectType getObjectTypeID() {
		return mObjectType;
	}
	
	public String getObjectTypeID(boolean pReturnString) {
		return this.mObjectType.getObjectName();
	}
	
	public void setObjectTypeID(SchEnumObjectType pObjectType) {
		this.mObjectType = pObjectType;
	}
	public int getRefID() {
		return mRefID;
	}
	public void setRefID(int pRefID) {
		this.mRefID = pRefID;
	}
	public int getRefDBID() {
		return mRefDBID;
	}
	public void setRefDBID(int pRefDBID) {
		this.mRefDBID = pRefDBID;
	}
		
		
}
