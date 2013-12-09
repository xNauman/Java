/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Vector;

import org.junit.Test;

import rawobjects.Bus;

/**
 * @author Raja Noman
 *
 */
public class ROUTE_SchBusObjTHD {

	rawobjects.Bus mTestBus = new Bus();
	Vector<Point> mTestVectorPoint = new Vector<Point>();
	route.SchBusObjTHD mTestSchBusObj = new route.SchBusObjTHD(0, 0, mTestBus, (Vector<java.awt.Point>) this.mTestVectorPoint);
	/**
	 * Test method for {@link route.SchBusObjTHD#getIsActive()}.
	 */
	@Test
	public final void testGetIsActive() {
		this.mTestSchBusObj.setIsActive(true);
		assertEquals(true,this.mTestSchBusObj.getIsActive());
	}


	/**
	 * Test method for {@link route.SchBusObjTHD#getBusDBID()}.
	 */
	@Test
	public final void testGetBusDBID() {
		this.mTestSchBusObj.setBusDBID(44);
		assertEquals(44,this.mTestSchBusObj.getBusDBID());
	}

	/**
	 * Test method for {@link route.SchBusObjTHD#getPaxCount()}.
	 */
	@Test
	public final void testGetPaxCount() {
	this.mTestSchBusObj.setPaxCount(5);
	assertEquals(5,this.mTestSchBusObj.getPaxCount());
	}

	/**
	 * Test method for {@link route.SchBusObjTHD#getPaxCountMax()}.
	 */
	@Test
	public final void testGetPaxCountMax() {
	assertEquals(0,this.mTestSchBusObj.getPaxCountMax());
	}

	/**
	 * Test method for {@link route.SchBusObjTHD#doKillBus()}.
	 */
	@Test
	public final void testDoKillBus() {
	this.mTestSchBusObj.doKillBus();
	}


}
