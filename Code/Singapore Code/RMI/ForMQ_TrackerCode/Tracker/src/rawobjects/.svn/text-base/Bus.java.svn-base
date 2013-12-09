
package rawobjects;

import java.io.Serializable;

public class Bus implements Serializable {

	private static final long serialVersionUID = 5812876758347715720L;
	
	private int BusID;
	private int RouteID;
	private Bus_Type Type; 
	private int DefaultLoad;
	private int CurrLoad;
	private int CurrPointX;
	private int CurrPointY;
	private int MovePointX;
	private int MovePointY;
	
	private int MoveReqState;
	private Long MoveReqTimeStamp;
	private boolean Operating;
	
	public Bus()
	{
		this.CurrPointX = 0;
		this.CurrPointY = 0;
		this.MovePointX =0;
		this.MovePointY = 0;
		this.BusID = 0;
		this.RouteID = 0;
		this.DefaultLoad=0;
		this.CurrLoad = 0;
		this.Type = new Bus_Type();
        this.MoveReqState=0;
        this.MoveReqTimeStamp = null;
		  this.Operating = false;
	}

	public Bus(int busID,int RouteID,Bus_Type type,int dfLoad,int pCurrentPointX,
	int pCurrentPointY, int pMoveToPointX, int pMoveToPointY, int pMoveRequestState,long pMoveReqTimeStamp, boolean pOperating)
	{
        this.BusID = busID;
        this.RouteID = RouteID;
        this.Type = type;
        this.DefaultLoad = dfLoad;
		this.CurrPointX = pCurrentPointX;
		this.CurrPointY = pCurrentPointY;
		this.MovePointX = pMoveToPointX;
		this.MovePointY = pMoveToPointY;
        this.MoveReqState = pMoveRequestState;
        this.MoveReqTimeStamp = pMoveReqTimeStamp;
		  this.Operating = pOperating;
	}

        //dummy constructor for testing
        public Bus(int busID,int RouteID,int dfLoad,int pCurrentPointX, int pCurrentPointY, int pMoveToPointX, int pMoveToPointY){
             this.BusID = busID;
             this.RouteID = RouteID;
        this.CurrPointX = pCurrentPointX;
        this.DefaultLoad = dfLoad;
		this.CurrPointY = pCurrentPointY;
		this.MovePointX = pMoveToPointX;
		this.MovePointY = pMoveToPointY;
        }

    public void setMoveRequestState(int pMoveReqState){
        this.MoveReqState = pMoveReqState;
    }
    public int getMoveRequestState(){
            return MoveReqState;
    }
    public void setTimeStamp(long pMoveReqTimeStamp){
        this.MoveReqTimeStamp = pMoveReqTimeStamp;
    }
    public long getTimeStamp(){
        return MoveReqTimeStamp;
    }

	public void setCurrentPointX(int iCurrentPointX) {

		this.CurrPointX = iCurrentPointX;
	}

	public int getCurrentPointX() {
		return CurrPointX;
	}

	public void setCurrentPointY(int iCurrentPointY) {
		this.CurrPointY = iCurrentPointY;
	}

	public int getCurrentPointY() {
		return CurrPointY;
	}

	public void setMoveToPointX(int iMoveToPointX) {
		this.MovePointX = iMoveToPointX;
	}

	public int getMoveToPointX() {
		return MovePointX;
	}

	public void setMoveToPointY(int iMoveToPointY) {
		this.MovePointY = iMoveToPointY;
	}

	public int getMoveToPointY() {
		return MovePointY;
	}

	public void setBusID(int iBusID) {
		this.MovePointY = iBusID;
	}

	public int getBusID() {
		return BusID;
	}

	public void setRouteID(int iRouteID) {
		this.RouteID = iRouteID;
	}

	public int getRouteID() {
		return RouteID;
	}

	public Bus_Type getBusType(){
		return this.Type;
	}

	public void setBusType(Bus_Type pBusType){
		this.Type = pBusType;
	}

	public void setCurrLoad(int pCurrLoad){
		this.CurrLoad = pCurrLoad;
	}

	public int getCurrLoad(){
		return this.CurrLoad;
	}

	public void setDefaultLoad(int pDefaultLoad){
		this.CurrLoad = pDefaultLoad;
	}

	public int getDefaultLoad(){
		return this.DefaultLoad;
	}

	public void setOperating(int pOperating){
		this.CurrLoad = pOperating;
	}

	public boolean getOperating(){
		return this.Operating;
	}

}
