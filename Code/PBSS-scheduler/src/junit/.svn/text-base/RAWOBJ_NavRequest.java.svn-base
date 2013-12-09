/**
 * 
 */
package junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//import rawobjects.Bus_Type;


/**
 * @author RAJA NOMAN & JOSE VARAS
 *
 */
public class RAWOBJ_NavRequest {
	
	
	rawobjects.Bus mBus =new rawobjects.Bus();
	
	//rawobjects.Bus_Type vBusType = new Bus_Type();
	
	rawobjects.NavRequest mNavRequest = new rawobjects.NavRequest(10,10,this.mBus);
	
	/**
	 * Checking against objects , so that if we set objects from the constructors should pass the test
	 */
	@Test
	public void getbBusTest(){
		assertEquals(this.mBus,this.mNavRequest.getbBus());
	}

	@Test
	public void getiMovementEndTimeTest(){
		this.mNavRequest.setiMovementEndTime(30);
		assertEquals(30,this.mNavRequest.getiMovementEndTime());
	}

	@Test
	public void getiMovementStartTimeTest(){
		this.mNavRequest.setiMovementStartTime(20);
		assertEquals(20,this.mNavRequest.getiMovementStartTime());
	}



}
