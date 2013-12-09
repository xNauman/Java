package rawobjects;
import java.util.Vector;
import java.io.Serializable;


public class TrafficLight implements Serializable{
    private int MapID;
    private int TrafficLightId;//uniqueID of traffic light
	private int PointX;//x co-ord of TL on graph
	private int PointY;//y co-ord of TL on graph
	private int ChangeFreq;//defines frequency of light change- RED/GREEN
	private boolean IsJunction;//states whether TL is at road junction
	private long LastChange;//stores last occurrence of TL state change 
	private int CurrDirection;//states direction TL is for
	private int MaxDirection;//maximum no. of directions TL can have
    private Vector<Double> TLAngle;//one trafficlight contains 3or 4 trafficlight angle
	
	/**
	 * Constructor for the light object
	 * 
	 * @param pTrafficLightId - The database ID for the light
	 * @param pTrafficLightLocationX - location X on the map
	 * @param pTrafficLightLocationY - Location Y on the map
	 * @param pChangeFreq - change frequency of the light
	 * @param pIsJunction - is it at a junction?
	 * @param tLastChange - time of last change (red to green)
	 * @param iCurrDirection - current direction the light is green for
	 * @param iMaxDirection - maximum number of directions the light has
	 */
	public TrafficLight(int mapID,int pTrafficLightId,int pTrafficLightLocationX,
			int pTrafficLightLocationY,int pChangeFreq, boolean pIsJunction, 
			long pLastChange, int pCurrDirection, int pMaxDirection, Vector pangle)
	{
        this.MapID = mapID;
		setTrafficLightId(pTrafficLightId);
		setTrafficLightLocationX(pTrafficLightLocationX);
		setTrafficLightLocationY(pTrafficLightLocationY);
		setChangeFreq(pChangeFreq);
		setIsJunction(pIsJunction);
		setLastChange(pLastChange);
		setCurrDirection(pCurrDirection);
		setMaxDirection(pMaxDirection);
        setTLAngle(pangle);
	}

	public TrafficLight()
	{
        this.MapID =0;
        this.ChangeFreq = 0;
        this.CurrDirection = 0;
        this.IsJunction = false;
        this.LastChange = 0;
        this.MaxDirection = 0;
        this.PointX = 0;
        this.PointY = 0;
        this.TrafficLightId = -1;
        this.TLAngle = new Vector<Double>();
	}
	
	public int getMapID(){
        return MapID;
    }
    public void setMapID(int ID){
        this.MapID = ID;
    }

	public int getTrafficLightId() {
		return TrafficLightId;
	}
	public void setTrafficLightId(int iTrafficLightId) {
		this.TrafficLightId = iTrafficLightId;
	}
	public int getTrafficLightLocationX() {
		return PointX;
	}
	public void setTrafficLightLocationX(int iTrafficLightLocationX) {
		this.PointX = iTrafficLightLocationX;
	}
	public int getTrafficLightLocationY() {
		return PointY;
	}
	public void setTrafficLightLocationY(int iTrafficLightLocationY) {
		this.PointY = iTrafficLightLocationY;
	}
	public int getChangeFreq() {
		return ChangeFreq;
	}
	public void setChangeFreq(int iChangeFreq) {
		this.ChangeFreq = iChangeFreq;
	}
	public boolean getIsJunction() {
		return IsJunction;
	}
	public void setIsJunction(boolean bIsJunction) {
		this.IsJunction = bIsJunction;
	}

	public void setLastChange(long tLastChange) {
		this.LastChange = tLastChange;
	}

	public long getLastChange() {
		return LastChange;
	}

	public void setCurrDirection(int iCurrDirection) {
		this.CurrDirection = iCurrDirection;
	}

	public int getCurrDirection() {
		return CurrDirection;
	}

	public void setMaxDirection(int iMaxDirection) {
		this.MaxDirection = iMaxDirection;
	}

	public int getMaxDirection() {
		return MaxDirection;
	}
    public Vector getTLAngle() {
        return TLAngle;
    }
    public void setTLAngle(Vector angle){
        this.TLAngle = angle;
    }
	
	

}