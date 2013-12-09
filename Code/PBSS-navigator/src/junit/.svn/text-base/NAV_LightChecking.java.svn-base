/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Admin
 *
 */
public class NAV_LightChecking {

	/**
	 * Test method for {@link navigation.LightChecking#AreWeAtLights(int, int)}.
	 */
	navigation.LightChecking mTestLightChecking = new navigation.LightChecking();
	rawobjects.Bus mTestBus = new rawobjects.Bus();
	rawobjects.TrafficLight mTestTrafficLight = new rawobjects.TrafficLight();
	
	@Test
	public void testAreWeAtLights() {
		
		assertEquals(false,mTestLightChecking.AreLightsGreen(0, null));
		
		this.mTestBus.setCurrentPointX(10);
		this.mTestBus.setCurrentPointY(20);
		this.mTestTrafficLight.setTrafficLightId(5);
		
		assertEquals(false,mTestLightChecking.AreLightsGreen(this.mTestTrafficLight.getTrafficLightId(), this.mTestBus));
	
	}

	/**
	 * Test method for {@link navigation.LightChecking#AreLightsGreen(int, rawobjects.Bus)}.
	 */
	@Test
	public void testAreLightsGreen() {
		this.mTestLightChecking.AreWeAtLights(0, 0);
		assertEquals(-1,this.mTestLightChecking.AreWeAtLights(0, 0));
		
		this.mTestBus.setCurrentPointX(10);
		this.mTestBus.setCurrentPointY(20);
		assertEquals(-1,this.mTestLightChecking.AreWeAtLights(0, 0));
	}

}
