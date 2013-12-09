package junit;

import static org.junit.Assert.assertEquals;


import java.util.Vector;

import org.junit.Test;

public class RAWOBJ_TrafficLight {
	
	rawobjects.TrafficLight mTrafficLight = new rawobjects.TrafficLight();
	Vector<rawobjects.Traffic_Light_Angle> mTraffic_Light_Angle =new Vector<rawobjects.Traffic_Light_Angle>();
	
	@Test
	public void getMapIDTest(){
		mTrafficLight.setMapID(1);
		
		assertEquals(1,mTrafficLight.getMapID());
	}
	
	@Test
	public void getTrafficLightIdTest(){
		mTrafficLight.setTrafficLightId(50);
		
		assertEquals(50,mTrafficLight.getTrafficLightId());
	}
	
	@Test
	public void getTrafficLightLocationXTest(){
		mTrafficLight.setTrafficLightLocationX(90);
		
		assertEquals(90,mTrafficLight.getTrafficLightLocationX());
	}
	
	@Test
	public void getTrafficLightLocationYTest(){
		mTrafficLight.setTrafficLightLocationY(60);
		
		assertEquals(60,mTrafficLight.getTrafficLightLocationY());
	}
	
	@Test
	public void getChangeFreqTest(){
		mTrafficLight.setChangeFreq(20);
		
		assertEquals(20,mTrafficLight.getChangeFreq());
	}
	
	@Test
	public void getIsJunctionTest(){
		mTrafficLight.setIsJunction(true);
		
		assertEquals(true,mTrafficLight.getIsJunction());
	}
	
	@Test
	public void getLastChangeTest(){
		mTrafficLight.setLastChange(1000);
		
		assertEquals(1000,mTrafficLight.getLastChange());
	}
	
	@Test
	public void getCurrDirectionTest(){
		mTrafficLight.setCurrDirection(124);
		
		assertEquals(124,mTrafficLight.getCurrDirection());
	}
	
	@Test
	public void getMaxDirection(){
		mTrafficLight.setMaxDirection(1488);
		
		assertEquals(1488,mTrafficLight.getMaxDirection());
	}
	
	
	@Test
	public void getTrafficLightAngleTest(){
		
		this.mTrafficLight.setTrafficLightAngle(this.mTraffic_Light_Angle);
		
		assertEquals(this.mTraffic_Light_Angle,this.mTrafficLight.getTrafficLightAngle());
	}








}
