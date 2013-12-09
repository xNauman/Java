package junit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

//import rawobjects.Bus_Type; NEED TO FIX



public class RAWOBJ_Bus {
	
	rawobjects.Bus mBus = new rawobjects.Bus();
	
	@Test
	public void getMoveRequestStateTest(){
		mBus.setMoveRequestState(12);
		
		assertEquals(12,mBus.getMoveRequestState());
	}
	
	@Test
	public void getTimeStampTest(){
		mBus.setTimeStamp(3333);
		
		assertEquals(3333,mBus.getTimeStamp());
	}
	
	@Test
	public void getCurrentPointXTest(){
		mBus.setCurrentPointX(4);
		
		assertEquals(4,mBus.getCurrentPointX());
	}

	@Test
	public void getCurrentPointYTest(){
		mBus.setCurrentPointY(5);
		
		assertEquals(5,mBus.getCurrentPointY());
	}
	
	@Test
	public void getMoveToPointXTest(){
		mBus.setMoveToPointX(10);
		
		assertEquals(10,mBus.getMoveToPointX());
	}
	
	@Test
	public void getMoveToPointYTest(){
		mBus.setMoveToPointY(12);
		
		assertEquals(12,mBus.getMoveToPointY());
	}
	
	@Test
	public void getBusIDTest(){
		mBus.setBusID(8);
		
		assertEquals(8,mBus.getBusID());
	}
	
	@Test
	public void getRouteIDTest(){
		mBus.setRouteID(3);
		
		assertEquals(3,mBus.getRouteID());
	}
	
	/*@Test NEED TO FIX
	public Bus_Type getBusType(){
		mBus.setBusType();
		
		assertEquals(null,mBus.getBusType());
		
	}*/
	
	@Test
	public void getCurrLoadTest(){
		mBus.setCurrLoad(45);
		
		assertEquals(45,mBus.getCurrLoad());
	}
	
	@Test
	public void getDefaultLoadTest(){
		mBus.setDefaultLoad(0);
		
		assertEquals(0,mBus.getDefaultLoad());
	}



}
