package rawobjects;

import java.util.Calendar;

/**
 * Class that will be the raw representation of a TrafficLight in the nav and sch systems
 * 
 * @author David Taberner
 * 
 *
 */

public class TrafficLight {
	private int iTrafficLightId;
	private int iTrafficLightLocationX;
	private int iTrafficLightLocationY;
	private int iChangeFreq;
	private boolean bIsJunction;
	private Calendar tLastChange;
	private int iCurrDirection;
	private int iMaxDirection;
	
	
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
	public TrafficLight(int pTrafficLightId,int pTrafficLightLocationX, 
			int pTrafficLightLocationY,int pChangeFreq, boolean pIsJunction, Calendar tLastChange, int iCurrDirection, int iMaxDirection)
	{
		
		setiTrafficLightId(pTrafficLightId);
		setiTrafficLightLocationX(pTrafficLightLocationX);
		setiTrafficLightLocationY(pTrafficLightLocationY);
		setiChangeFreq(pChangeFreq);
		setbIsJunction(pIsJunction);
		
	}
	
	//getter and setter methods below!
	public int getiTrafficLightId() {
		return iTrafficLightId;
	}
	public void setiTrafficLightId(int iTrafficLightId) {
		this.iTrafficLightId = iTrafficLightId;
	}
	public int getiTrafficLightLocationX() {
		return iTrafficLightLocationX;
	}
	public void setiTrafficLightLocationX(int iTrafficLightLocationX) {
		this.iTrafficLightLocationX = iTrafficLightLocationX;
	}
	public int getiTrafficLightLocationY() {
		return iTrafficLightLocationY;
	}
	public void setiTrafficLightLocationY(int iTrafficLightLocationY) {
		this.iTrafficLightLocationY = iTrafficLightLocationY;
	}
	public int getiChangeFreq() {
		return iChangeFreq;
	}
	public void setiChangeFreq(int iChangeFreq) {
		this.iChangeFreq = iChangeFreq;
	}
	public boolean getbIsJunction() {
		return bIsJunction;
	}
	public void setbIsJunction(boolean bIsJunction) {
		this.bIsJunction = bIsJunction;
	}

	public void settLastChange(Calendar tLastChange) {
		this.tLastChange = tLastChange;
	}

	public Calendar gettLastChange() {
		return tLastChange;
	}

	public void setiCurrDirection(int iCurrDirection) {
		this.iCurrDirection = iCurrDirection;
	}

	public int getiCurrDirection() {
		return iCurrDirection;
	}

	public void setiMaxDirection(int iMaxDirection) {
		this.iMaxDirection = iMaxDirection;
	}

	public int getiMaxDirection() {
		return iMaxDirection;
	}
	
	

}
