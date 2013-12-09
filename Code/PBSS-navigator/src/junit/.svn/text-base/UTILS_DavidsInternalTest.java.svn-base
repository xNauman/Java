package junit;

import static org.junit.Assert.*;
import navigation.BusChecking;

import org.junit.Test;

import rawobjects.Bus;
import rawobjects.Bus_Type;
import rawobjects.NavRequest;

/**
 * 
 * This Junit test case if for my own (David) messing around with the system. 
 * It is NOT to be used for any formal testing as I'm constantly changing it to test whatever part of the code I
 * need to test at this point.
 * 
 * @author David Taberner
 *
 */

public class UTILS_DavidsInternalTest {

	BusChecking oBusCheck;
	
	public void setUp() throws Exception {
	

		
		
		
	}
	
	@Test
	public void testBusMovement()
	{
		
		//setup a small error range for testing
		config.SysConfig.setCheckingRangeForBuses(2);
		
		
		Bus_Type oBusType = new Bus_Type();
		
		//create a bus
		//			   id,route, , dfload, c X, c y, M x, M y, m req state,move time stamp	
		Bus oBus = new Bus(2, 1, oBusType, 1, 363, 550, 403, 550 ,1,  50);
		
		//create a nav request
		NavRequest oNavRequest = new NavRequest(0,500000, oBus);
		//create bus checking
		oBusCheck = new BusChecking(oNavRequest);
		
		//work out if we are ok to move
		boolean okToMove;
		okToMove = oBusCheck.OkToMove(); 
		assertTrue(okToMove);
		
		
		//create a bus
			//		   id,route, , dfload, c X, c y, M x, M y, m req state,move time stamp	
		oBus = new Bus(1, 1, oBusType, 1, 551, 418, 591, 418 ,1,  50);
		
		//create a nav request
		oNavRequest = new NavRequest(0,500000, oBus);
		//create bus checking
		oBusCheck = new BusChecking(oNavRequest);
		
		//work out if we are ok to move
		okToMove = oBusCheck.OkToMove(); 
		assertTrue(okToMove);
		
	}
	
}
