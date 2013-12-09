package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class RAWOBJ_Congestion {
	
	rawobjects.Congestion mCongestion = new rawobjects.Congestion();
	
	
	
	@Test
	public void getMapIDTest(){
		mCongestion.setMapID(4);
		
		assertEquals(4,mCongestion.getMapID());
	}
	
	@Test
	public void getCongestedAreaIDTest(){
		mCongestion.setCongestedAreaID(9);
		
		assertEquals(9,mCongestion.getCongestedAreaID());
	}
	
	@Test
	public void getPointATest(){
		assertNotNull(mCongestion.getPointA());
	}
	
	@Test
	public void getPointBTest(){
		assertNotNull(mCongestion.getPointB());
	}
	

}
