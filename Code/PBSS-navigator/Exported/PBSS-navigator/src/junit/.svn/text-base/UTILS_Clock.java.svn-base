package junit;


/**
 * @author Raja Noman.
 * 
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import java.util.Calendar;
//import java.util.Date;


import org.junit.Test;


public class UTILS_Clock {
	
	utils.Clock  mPUC= new utils.Clock();
	
public void setUp() throws Exception {
		
	}
		/**
		 * Test to check if clock starts.
		 */
		@Test
		public void startClockTest(){
			boolean vRtnValue=true;
			
			assertEquals(vRtnValue, utils.Clock.StartClock());
		}
		
		/**
		 * Test if we get the simulation time.
		 * testing condition if simulation time is not zero
		 */
		@Test
		public void getSimTimeTest(){
			
			assertTrue( (utils.Clock.getSimTime()> 0) );			
		}
		
		/**
		 *As system is not in its running state , following test is 
		 *performed after investigating  the method.
		 *This test has failed sometimes as it is dependent on simulation
		 *time. if doesn't pass test run it couple of times.
		 */
		
		//@Test
		//public void compairTimeTest(){
			
		//	assertEquals( 0, mPUC.compairTime(2) );
			
		//}
		
		
		
}



