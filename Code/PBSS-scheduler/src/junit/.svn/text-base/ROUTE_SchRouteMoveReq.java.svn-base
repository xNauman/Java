/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

/**
 * @author Raja Noman
 *
 */
public class ROUTE_SchRouteMoveReq {

	route.SchRouteMoveReq mTestRouteMoveReq = new route.SchRouteMoveReq();
	route.SchDoublePoint mtestSchDoublePoint = new route.SchDoublePoint();
	
	/**
	 * Test method for {@link route.SchRouteMoveReq#basePoints(java.awt.Point, java.awt.Point)}.
	 */
	@Test
	public final void testBasePoints() {
		this.mTestRouteMoveReq.basePoints(new Point(1,1), new Point(100,100));
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#getPointFirst()}.
	 */
	@Test
	public final void testGetPointFirst() {
	
		assertEquals(null,this.mTestRouteMoveReq.getPointFirst());
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#getMovePointData()}.
	 */
	@Test
	public final void testGetMovePointData() {
		assertNull(null,this.mTestRouteMoveReq.getMovePointData());
	}
	/**
	 * Test method for {@link route.SchRouteMoveReq#isReadyForSending(boolean)}.
	 */
	@Test
	public final void testIsReadyForSending() {
		assertEquals(true,this.mTestRouteMoveReq.isReadyForSending(true));
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#hasEndPoint()}.
	 */
	@Test
	public final void testHasEndPoint() {
		assertEquals(true, this.mTestRouteMoveReq.hasEndPoint());
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#performInternalSplit()}.
	 */
	@Test
	public final void testPerformInternalSplit() {
		assertEquals(false,this.mTestRouteMoveReq.performInternalSplit());
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#debugDumpQueue()}.
	 */
	@Test
	public final void testDebugDumpQueue() {
		this.mTestRouteMoveReq.debugDumpQueue();
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#isQueueEmpty()}.
	 */
	@Test
	public final void testIsQueueEmpty() {
		assertEquals(true,this.mTestRouteMoveReq.isQueueEmpty());
	}

	/**
	 * Test method for {@link route.SchRouteMoveReq#calculateDistance(java.awt.Point, java.awt.Point)}.
	 */
	@Test
	public final void testCalculateDistance() {
		assertEquals(true,(28==this.mTestRouteMoveReq.calculateDistance(new Point(20,20), new Point(40,40))));
	}

	

	/**
	 * Test method for {@link route.SchRouteMoveReq#removePoint(java.util.UUID)}.
	 */
	@Test
	public final void testRemovePoint() {
		this.mTestRouteMoveReq.removePoint(this.mtestSchDoublePoint.getPointUUID());
	}



}
