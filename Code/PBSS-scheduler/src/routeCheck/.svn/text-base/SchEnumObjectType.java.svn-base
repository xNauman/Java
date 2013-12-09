package routeCheck;

/**
 * Types of Objects that are detected by the routeCheck package.
 * 
 * @author Ben
 *
 */

public enum SchEnumObjectType {
	UNKNOWN ("Unknown", SchEnumSplitLoc.NO_SPLIT),
	TRAFFIC_LIGHT ("Traffic Light", SchEnumSplitLoc.SPLIT_BEFORE),
	JUNCTION ("Junction", SchEnumSplitLoc.NO_SPLIT),
	STOP ("Stop", SchEnumSplitLoc.NO_SPLIT),
	CONGESTED_AREA_START ("Congested Area - Start", SchEnumSplitLoc.SPLIT_BEFORE),
	CONGESTED_AREA_END ("Congested Area - End", SchEnumSplitLoc.SPLIT_AFTER),
	NORMAL ("Normal Request", SchEnumSplitLoc.NO_SPLIT);
	
	private final String mObjectName;
	private final SchEnumSplitLoc mNAVSplitLocation;
		
	SchEnumObjectType(String pObjectName, SchEnumSplitLoc pNAVSplitLocation){
		this.mObjectName = pObjectName;
		this.mNAVSplitLocation = pNAVSplitLocation;
	}
	
	public String getObjectName(){
		return this.mObjectName;
	}
	
	public SchEnumSplitLoc getNAVSplitLocation(){
		return this.mNAVSplitLocation;
	}
	
}
