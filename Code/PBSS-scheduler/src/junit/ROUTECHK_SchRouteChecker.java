package junit;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class ROUTECHK_SchRouteChecker {
	
	routeCheck.SchRouteChecker mTestRoutCheck = new routeCheck.SchRouteChecker();
	routeCheck.SchSimpleObjTL mTestSchSimpleObj = new routeCheck.SchSimpleObjTL();
	routeCheck.SchSimpleObjStop mTestSchSimpleObjStop = new routeCheck.SchSimpleObjStop();
	routeCheck.SchSimpleObjCongArea mTestSchSimpleObjCongArea = new routeCheck.SchSimpleObjCongArea();
	
	@Test
	public final void testSimpleTLClear() {
		
		this.mTestRoutCheck.simpleTLClear();
		
	}

	@Test
	public final void testSimpleTLAdd() {
		
		this.mTestSchSimpleObj.setIsJunction(true);
		this.mTestSchSimpleObj.setTLPoint(new Point(12,12));
	this.mTestRoutCheck.SimpleTLAdd(this.mTestSchSimpleObj);
	}

	@Test
	public final void testSimpleStopClear() {
		this.mTestRoutCheck.simpleStopClear();
	}

	@Test
	public final void testSimpleStopAdd() {
	this.mTestSchSimpleObjStop.setStopDBID(20);
	this.mTestSchSimpleObjStop.setStopID(33);
	this.mTestSchSimpleObjStop.setStopPoint(new Point(12,19));
		this.mTestRoutCheck.SimpleStopAdd(this.mTestSchSimpleObjStop);
	}

	@Test
	public final void testSimpleCAAdd() {
		this.mTestSchSimpleObjCongArea.setCADBID(3);
		this.mTestSchSimpleObjCongArea.setCAID(4);
		this.mTestSchSimpleObjCongArea.setCAPointA(new Point(22,22));
		this.mTestSchSimpleObjCongArea.setCAPointB(new Point(44,22));
		this.mTestRoutCheck.SimpleCAAdd(this.mTestSchSimpleObjCongArea);
	}

	@Test
	public final void testSimpleCAClear() {
	this.mTestRoutCheck.simpleCAClear();
	}
	
	@Test
	public final void testCalculateY() {
		assertEquals(0,this.mTestRoutCheck.calculateY(new Point(10,10), new Point(10,15),40));
	}

	@Test
	public final void testCalculateYSteep() {
	assertEquals(0,this.mTestRoutCheck.calculateYSteep(new Point(10,10), new Point(10,15),50));
	}

	@Test
	public final void testCalculateM() {
	assertEquals(true,(2==this.mTestRoutCheck.calculateM(new Point(10,10), new Point(15,20))));
	}

	@Test
	public final void testCalculateB() {
	assertEquals(true,(-10 == this.mTestRoutCheck.calculateB(new Point(10,10), new Point(15,20))));
	}

	@Test
	public final void testListStop() {
		this.mTestRoutCheck.listStop();
	}

	@Test
	public final void testListTL() {
	this.mTestRoutCheck.listTL();
	}

	@Test
	public final void testListCongArea() {
	this.mTestRoutCheck.listCongArea();
	}

	@Test
	public final void testAreStaticObjectEmpty() {
		assertEquals(false,this.mTestRoutCheck.areStaticObjectEmpty());
		}

}
