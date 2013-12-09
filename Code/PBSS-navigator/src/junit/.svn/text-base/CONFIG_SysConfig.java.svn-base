/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Raja Noman
 *
 */
public class CONFIG_SysConfig {

	config.SysConfig mTestSysConfig = new config.SysConfig();
	
	
	/**
	 * Test method for {@link config.SysConfig#getMyModuleName()}.
	 * checking in Navigator so module name should be NAV.
	 */
	@Test
	public final void testGetMyModuleName() {		
	
	assertEquals("NAV",this.mTestSysConfig.getModuleName());
	
	}

	/**
	 * Test method for {@link config.SysConfig#getModuleName()}.
	 */
	@Test
	public final void testGetModuleName() {
		
		
		assertEquals("NAV",this.mTestSysConfig.getModuleName());
		
	}

	/**
	 * Test method for {@link config.SysConfig#getSimRunID()}.
	 */
	@Test
	public final void testGetSimRunID() {
		
		assertEquals(-1, this.mTestSysConfig.getSimRunID());
		
	}

	/**
	 * Test method for {@link config.SysConfig#getSimRunSetBy()}.
	 */
	@Test
	public final void testGetSimRunSetBy() {
		
		assertEquals(null,this.mTestSysConfig.getSimRunSetBy());

	}

	/**
	 * Test method for {@link config.SysConfig#getMaxNavDistance()}.
	 */
	@Test
	public final void testGetMaxNavDistance() {
		this.mTestSysConfig.setMaxNavDistance(30);
		assertEquals(30,this.mTestSysConfig.getMaxNavDistance());
	}

	/**
	 * Test method for {@link config.SysConfig#getDistanceToBeCompleted()}.
	 */
	@Test
	public final void testGetDistanceToBeCompleted() {
		
		assertEquals(true,(0.75==this.mTestSysConfig.getDistanceToBeCompleted()));
		
		
	}

	/**
	 * Test method for {@link config.SysConfig#getCoreModuleIPsKnown()}.
	 */
	@Test
	public final void testGetCoreModuleIPsKnown() {
		this.mTestSysConfig.setCoreModuleIPsKnown(true);
		assertEquals(true, this.mTestSysConfig.getCoreModuleIPsKnown());
	}

	/**
	 * Test method for {@link config.SysConfig#getIPregistrationCompleted()}.
	 */
	@Test
	public final void testGetIPregistrationCompleted() {
		this.mTestSysConfig.setIPregistrationCompleted(true);
		assertEquals(true,this.mTestSysConfig.getIPregistrationCompleted());
	}

	/**
	 * Test method for {@link config.SysConfig#getStopIPRegistration()}.
	 */
	@Test
	public final void testGetStopIPRegistration() {
		this.mTestSysConfig.setStopIPRegistration(true);
		assertEquals(true,this.mTestSysConfig.getStopIPRegistration());
	}

	/**
	 * Test method for {@link config.SysConfig#getIPRegistrationSleepTimer()}.
	 * value is found by cheking the values with other variables settings.
	 */
	@Test
	public final void testGetIPRegistrationSleepTimer() {
		//this.mTestSysConfig
	assertEquals(true,(5000==this.mTestSysConfig.getIPRegistrationSleepTimer()));
	}

	/**
	 * Test method for {@link config.SysConfig#getErrorValueForTrafficLightAngles()}.
	 */
	@Test
	public final void testGetErrorValueForTrafficLightAngles() {
		assertEquals(true,(3  == this.mTestSysConfig.getErrorValueForTrafficLightAngles()));
	}

	/**
	 * Test method for {@link config.SysConfig#getCheckingRangeForBuses()}.
	 * testing value is found by cheking the values with other variables settings.
	 */
	@Test
	public final void testGetCheckingRangeForBuses() {
		assertEquals(true ,( 70 == this.mTestSysConfig.getCheckingRangeForBuses()));
	}

	/**
	 * Test method for {@link config.SysConfig#getIPInfo(rawobjectsPriv.EnumModName)}.
	 */
	//@Test
	/*public final void testGetIPInfo() {
		assertNotNull(this.mTestSysConfig.getIPInfo(this.mTestSysConfig.g))
	}*/

	/**
	 * Test method for {@link config.SysConfig#addIPInfo(rawobjectsPriv.EnumModName, rawobjectsPriv.NetIPInfo)}.
	 */
	//@Test
/*	public final void testAddIPInfo() {
		
		rawobjectsPriv.EnumModName mTestModName = new rawobjectsPriv.EnumModName();
		
		System.out.println(mTestModName.getModName());
		
		//this.mTestSysConfig.addIPInfo(pMod, pIPInfo)
}*/


	/**
	 * Test method for {@link config.SysConfig#timeControl_StartPolling()}.
	 * If method runs this means its all good and test is successfull.
	 */
	@Test
	public final void testTimeControl_StartPolling() {
		this.mTestSysConfig.timeControl_StartPolling();
	}

	/**
	 * Test method for {@link config.SysConfig#timeControl_KeepPolling()}.
	 */
	@Test
	public final void testTimeControl_KeepPolling() {
		assertEquals(true,this.mTestSysConfig.timeControl_KeepPolling());
	}

	/**
	 * Test method for {@link config.SysConfig#timeControl_StopPolling()}.
	 * If method runs this means its all good and test is successfull.
	 */
	@Test
	public final void testTimeControl_StopPolling() {
		this.mTestSysConfig.timeControl_StopPolling();
		
	}

}
