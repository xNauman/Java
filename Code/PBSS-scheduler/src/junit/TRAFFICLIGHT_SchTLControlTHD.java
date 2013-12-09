/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import trafficLight.SchTLControlTHD;

/**
 * @author Raja Noman
 *
 */
public class TRAFFICLIGHT_SchTLControlTHD {

	trafficLight.SchTLControlTHD mTestSchTLControl = new trafficLight.SchTLControlTHD();
	
	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#debugPingMsg_On()}.
	 */
	@Test
	public final void testDebugPingMsg_On() {
		this.mTestSchTLControl.debugPingMsg_On();
	}
	

	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#debugPingMsg_Off()}.
	 */
	@Test
	public final void testDebugPingMsg_Off() {
		this.mTestSchTLControl.debugPingMsg_Off();
		
	}


	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#startTLControl()}.
	 */
	@Test
	public final void testStartTLControl() {
		this.mTestSchTLControl.startTLControl();
	}

	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#stopTLControl()}.
	 */
	@Test
	public final void testStopTLControl() {
		this.mTestSchTLControl.stopTLControl();
	}

	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#loadTLObj(boolean)}.
	 */
	@Test
	public final void testLoadTLObj() {
		this.mTestSchTLControl.loadTLObj(true);
	}

//	/**
//	 * Test method for {@link trafficLight.SchTLControlTHD#loadTLObj_File(boolean)}.
//	 */
//	@Test
//	public final void testLoadTLObj_File() {
//
//	}

	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#procTLStartAll()}.
	 */
	@Test
	public final void testProcTLStartAll() {
		this.mTestSchTLControl.procTLStartAll();
	}
//
//	/**
//	 * Test method for {@link trafficLight.SchTLControlTHD#procTLSingleStart(int)}.
//	 */
//	@Test
//	public final void testProcTLSingleStart() {
//		this.mTestSchTLControl.procTLStopAll();
//		this.mTestSchTLControl.procTLSingleStart(this.mTestSchTLControl);
//	}

	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#procTLStopAll()}.
	 */
	@Test
	public final void testProcTLStopAll() {
		this.mTestSchTLControl.procTLStopAll();
	}

//	/**
//	 * Test method for {@link trafficLight.SchTLControlTHD#procTLSingleStop(int)}.
//	 */
//	@Test
//	public final void testProcTLSingleStop() {
//
//	}

	/**
	 * Test method for {@link trafficLight.SchTLControlTHD#procTLKillAll()}.
	 */
	@Test
	public final void testProcTLKillAll() {
		this.mTestSchTLControl.procTLKillAll();
	}
//
//	/**
//	 * Test method for {@link trafficLight.SchTLControlTHD#procTLSingleKill(int)}.
//	 */
//	@Test
//	public final void testProcTLSingleKill() {
//		this.mTestSchTLControl
//
//	}

}
