/**
 * 
 */
package junit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
/**
 * @author RAJA NOMAN & JOSE VARAS
 *
 */
public class RAWOBJ_Bus_Type {

	rawobjects.Bus_Type mBusType = new rawobjects.Bus_Type();
	
	/**
	 * setting a specific color of the bus and then checking.
	 */
	@Test
	public void getBusColourTest(){
		mBusType.setBusColour(12);
		
		assertEquals(12,mBusType.getBusColour());
	}

	/**
	 * setting the bus type and checking it.
	 */
	@Test
	public void getBusTypeTest(){
		
		mBusType.setBusType("A");
		assertEquals("A",this.mBusType.getBusType());
	}
	/**
	 *checking bus max capacity after setting it manually. 
	 */
	
	@Test
	public void getMaxCapacityTest(){
		this.mBusType.setMaxCapacity((short) 100);
		assertEquals(100, mBusType.getMaxCapacity());
		
	}

}
