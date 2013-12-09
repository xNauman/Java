/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

/**
 * @author Admin
 *
 */
public class ROUTE_SchRouteResp {

	route.SchRouteResp mTestRouteResp = new route.SchRouteResp();
	UUID mTestUUID = new UUID(10, 10);
	route.SchRouteMoveReqResp mTestRouteMoveReq = new route.SchRouteMoveReqResp(this.mTestUUID, true);
	/**
	 * Test method for {@link route.SchRouteResp#addResponseFromNav(route.SchRouteMoveReqResp)}.
	 */
	@Test
	public final void testAddResponseFromNav() {
		this.mTestRouteResp.addResponseFromNav(this.mTestRouteMoveReq);
	}

	

	/**
	 * Test method for {@link route.SchRouteResp#containsResponse(java.util.UUID)}.
	 */
	@Test
	public final void testContainsResponse() {
		assertEquals(true,mTestRouteResp.containsResponse(this.mTestUUID));
	}

	/**
	 * Test method for {@link route.SchRouteResp#getResponse(java.util.UUID, boolean)}.
	 */
	@Test
	public final void testGetResponse() {
		assertEquals(true,this.mTestRouteResp.getResponse(this.mTestUUID,true).getResponse());
	}

	
	/**
	 * Test method for {@link route.SchRouteResp#getCount()}.
	 */
	@Test
	public final void testGetCount() {
		
		assertEquals(0,this.mTestRouteResp.getCount());
	}

	/**
	 * Test method for {@link route.SchRouteResp#debugListAll()}.
	 */
	@Test
	public final void testDebugListAll() {
		this.mTestRouteResp.debugListAll();
	}
	
	/**
	 * Test method for {@link route.SchRouteResp#clearAll()}.
	 */
	@Test
	public final void testClearAll() {
	this.mTestRouteResp.clearAll();	
	}
	/**
	 * Test method for {@link route.SchRouteResp#removeResponseFromNav(java.util.UUID)}.
	 */
	@Test
	public final void testRemoveResponseFromNav() {
		this.mTestRouteResp.removeResponseFromNav(this.mTestUUID);
	}

}
