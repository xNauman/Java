package rawobjects;
/**
 * Class that will be the raw representation of a TrafficLight in the nav and sch systems
 * 
 * @author David Taberner
 * 
 * TODO: add move javadoc comments and possibly comments about the data types the construtor takes
 *
 */

public class TrafficLight {
	private int iTrafficLightId;
	private int iTrafficLightLocationX;
	private int iTrafficLightLocationY;
	private int iChangeFreq;
	private boolean bIsJunction;
	
	
	public TrafficLight()
	{
		
	}
	
	public TrafficLight(int pTrafficLightId,int pTrafficLightLocationX, 
			int pTrafficLightLocationY,int pChangeFreq, boolean pIsJunction)
	{
		
		setiTrafficLightId(pTrafficLightId);
		setiTrafficLightLocationX(pTrafficLightLocationX);
		setiTrafficLightLocationY(pTrafficLightLocationY);
		setiChangeFreq(pChangeFreq);
		setbIsJunction(pIsJunction);
		
	}
	
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
	
	

}
