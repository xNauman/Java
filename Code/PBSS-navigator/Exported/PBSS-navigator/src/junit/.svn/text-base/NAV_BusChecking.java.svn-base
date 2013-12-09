/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author RAJA NOMAN
 * 
 * 
 *
 */
public class NAV_BusChecking {

	
	rawobjects.Bus mTestBus = new rawobjects.Bus();
	rawobjects.NavRequest mNavRequest = new rawobjects.NavRequest(10, 30, mTestBus);
	navigation.BusChecking mBusCheck = new navigation.BusChecking(mNavRequest);
	
	
	/**
	 * Test method for {@link navigation.BusChecking#OkToMove()}.
	 * if bus is ok to move with our current setting (look member variables).
	 */
	@Test
	public void testOkToMove() {
		
		assertEquals(true,mBusCheck.OkToMove());
 
	}

	/**
	 * Test method for {@link navigation.BusChecking#PlotXYForBusPath()}.
	 * checking if bus path will be plotted with our current settings (look member variables). 
	 */
	@Test
	public void testPlotXYForBusPath() {
		mBusCheck.PlotXYForBusPath();
	}

}
