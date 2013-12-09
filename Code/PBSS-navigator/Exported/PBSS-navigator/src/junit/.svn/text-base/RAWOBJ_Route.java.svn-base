/**
 * 
 */
package junit;

import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.util.Vector;

import org.junit.Test;

/**
 * @author RAJA NOMAN
 *
 */
public class RAWOBJ_Route {

	
	rawobjects.Route mRoute = new rawobjects.Route();
	Point mPoint = new Point();
	Vector<Point> mPointsVector =new Vector<Point>();
	
	@Test
	public void getDepartEveryTest(){
	this.mRoute.setDepartEvery((short)5);
		assertEquals((short)5,this.mRoute.getDepartEvery());
	}

	@Test
	public void getMapIDTest(){
		this.mRoute.setMapID(33);
		assertEquals(33,this.mRoute.getMapID());
	}

	@Test
	public void getPointsTest(){
		
		this.mRoute.setPoints(this.mPointsVector);
		
		assertEquals(this.mPointsVector,this.mRoute.getPoints());
	}

	@Test
	public void getRouteIDTest(){
		this.mRoute.setRouteID(89);
		 assertEquals(89,this.mRoute.getRouteID());
	}

	@Test
	public void getRouteNameTest(){
		this.mRoute.setRouteName("sampleRouteName");
		assertEquals("sampleRouteName", this.mRoute.getRouteName());
	}

	@Test
	public void getRouteNbrTest(){

	}

	@Test
	public void getStartDepotIDTest(){

	}

	@Test
	public void getStopDepotIDTest(){

	}
}
