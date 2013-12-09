package route;

import java.util.UUID;

import util.General;

public class SchRouteMoveReqResp {

	private UUID mRequestUUID;
	private boolean mResponse;
	private long mRequestReceivedTime;
	
	public SchRouteMoveReqResp(UUID pRequestUUID, boolean pResponse){
		this.mRequestUUID = pRequestUUID;
		this.mResponse = pResponse;
		this.mRequestReceivedTime = General.getSimulatorTime_Actual(); //General.getSimulatorTime();
	}
	
	public SchRouteMoveReqResp(UUID pRequestUUID, boolean pResponse, long pRequestRecdTime){
		this.mRequestUUID = pRequestUUID;
		this.mResponse = pResponse;
		this.mRequestReceivedTime = pRequestRecdTime;
	}
	
	public UUID getRequestUUID(){
		return this.mRequestUUID;
	}
	
	public boolean getResponse(){
		return this.mResponse;
	}
	
	public long getResponseReceivedTime(){
		return this.mRequestReceivedTime;
	}
	
}
