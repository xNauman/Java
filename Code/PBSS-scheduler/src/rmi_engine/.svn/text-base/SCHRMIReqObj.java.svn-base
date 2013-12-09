package rmi_engine;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SCHRMIReqObj {

	private Calendar mRequestedDate;
	private long mRequestOrder;
	private SCHRMICmdType mRequestCmd;
	private Map<String,Object> mRequestData = new HashMap<String,Object>();
	private boolean mResponseRequired;
	
	public SCHRMIReqObj(){
		this.mRequestedDate = Calendar.getInstance();
		this.mRequestCmd = SCHRMICmdType.UNKNOWN;
		this.mRequestOrder = this.mRequestedDate.getTimeInMillis();
	}
	
	public SCHRMIReqObj(SCHRMICmdType pRequestCmd, boolean pResponseRequired){
		super();
		
		this.mRequestCmd = pRequestCmd;
		this.mResponseRequired = pResponseRequired;		
	}
	
	public Calendar getRequestedDate() {
		return mRequestedDate;
	}
	
	public long getRequestOrder() {
		return mRequestOrder;
	}
	public void setRequestOrder(long pRequestOrder) {
		this.mRequestOrder = pRequestOrder;
	}
	public SCHRMICmdType getRequestCmd() {
		return mRequestCmd;
	}
	public void setRequestCmd(SCHRMICmdType pRequestCmd) {
		this.mRequestCmd = pRequestCmd;
	}
	public Map<String,Object> getRequestData() {
		return mRequestData;
	}
	public void setRequestData(Map<String,Object> pRequestData) {
		this.mRequestData = pRequestData;
	}
	public void setRequestData(String pDataName, String pDataValue){
		if (this.mRequestData==null){
			this.mRequestData = new HashMap<String,Object>();
		}
		this.mRequestData.put(pDataName, pDataValue);
	}
	
	public Object getRequestData(String pDataName, Object pDefaultData){
		if (this.mRequestData.containsKey(pDataName)){
			return this.mRequestData.get(pDataName);
		}
		else
			return pDefaultData;
	}
	
	public boolean isResponseRequired() {
		return mResponseRequired;
	}
	public void setResponseRequired(boolean pResponseRequired) {
		this.mResponseRequired = pResponseRequired;
	}
	
		
}
