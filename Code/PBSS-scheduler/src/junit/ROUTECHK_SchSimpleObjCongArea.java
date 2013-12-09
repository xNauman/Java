package junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ROUTECHK_SchSimpleObjCongArea {
	
	routeCheck.SchSimpleObjCongArea mSchSimpleObjCongArea = new routeCheck.SchSimpleObjCongArea();
	
	
	@Test
	public void getCAIDTest(){
		mSchSimpleObjCongArea.setCAID(2);
		
		assertEquals(2,mSchSimpleObjCongArea.getCAID());
	}
	
	@Test
	public void getCADBIDTest(){
		mSchSimpleObjCongArea.setCADBID(3);
		
		assertEquals(3,mSchSimpleObjCongArea.getCADBID());
	}
	
	@Test
	public void getCAPointATest(){
		assertNotNull(mSchSimpleObjCongArea.getCAPointA());
	}
	
	@Test
	public void getCAPointBTest(){
		assertNotNull(mSchSimpleObjCongArea.getCAPointB());
	}
	
	
	
	
	
	
	

}
