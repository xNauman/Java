/**
 * 
 */
package junit;

import static org.junit.Assert.assertEquals;
import java.awt.Point;

import org.junit.Test;

/**
 * @author RAJA NOMAN
 *
 */
public class RAWOBJ_Depot {

	rawobjects.Depot mDepot = new rawobjects.Depot();
	Point mTestPoint = new Point(1,2);
	
	/**
	 * setting a random point and checking it in the object.
	 */
	@Test
	public void getLogPointTest(){
		
    this.mDepot.setLogPoint(this.mTestPoint);  
    assertEquals(this.mTestPoint,this.mDepot.getLogPoint());
	}

	/**
	 * setting a mapid and checking it.
	 */
	@Test
	public void getMapIDTest(){
		
		this.mDepot.setMapID(20);
		assertEquals(20,this.mDepot.getMapID());

	}

	/**
	 * setting a point and checking if method is working against it.
	 */
	@Test
	public void getPhyPointTest(){
		this.mTestPoint.setLocation(40, 99);
		this.mDepot.setPhysPoint(this.mTestPoint);
		assertEquals(this.mTestPoint,this.mDepot.getPhysPoint());
	}

}
