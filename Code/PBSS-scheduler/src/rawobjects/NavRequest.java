package rawobjects;

public class NavRequest implements java.io.Serializable {
	
	private static final long serialVersionUID = 1740600634581485813L;
	
	private Bus bBus;
	private long iMovementStartTime;
	private long iMovementEndTime;
	
	public NavRequest(long pMovementStartTime, long pMovementEndTime, Bus pBus)
	{
		this.bBus = pBus;
		this.iMovementStartTime = pMovementStartTime;
		this.iMovementEndTime = pMovementEndTime;
		
	}


	public Bus getbBus() {
		return bBus;
	}

	public void setbBus(Bus bBus) {
		this.bBus = bBus;
	}

	public long getiMovementStartTime() {
		return iMovementStartTime;
	}

	public void setiMovementStartTime(long iMovementStartTime) {
		this.iMovementStartTime = iMovementStartTime;
	}

	public long getiMovementEndTime() {
		return iMovementEndTime;
	}

	public void setiMovementEndTime(long iMovementEndTime) {
		this.iMovementEndTime = iMovementEndTime;
	}
	

}
