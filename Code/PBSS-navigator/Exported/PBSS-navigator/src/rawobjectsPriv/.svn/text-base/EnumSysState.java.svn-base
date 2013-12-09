package rawobjectsPriv;

public enum EnumSysState {
	NOT_OPERATIONAL ("Not Operational",0),	//0
	PRE_START_IN_PROGRESS ("Pre Start in Progress",1),	//1
	READY_TO_START ("Ready to Start",2), 	//2
	IN_OPERATION ("Operational",3);	//3

	private String mStateName = "";
	private int mStateNum = 0;
	
	EnumSysState(String pStateName, int pStateNum){
		this.mStateName = pStateName;
		this.mStateNum = pStateNum;
		
	}
	
	public int getStateNum(){
		return this.mStateNum;
	}
	
	public String getState(){
		return this.mStateName;
	}
	
}
