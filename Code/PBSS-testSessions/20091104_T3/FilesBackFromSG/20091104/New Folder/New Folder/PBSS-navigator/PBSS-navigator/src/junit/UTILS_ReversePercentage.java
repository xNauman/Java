package junit;

/**
 * junit test for utils.ReversePercentage code
 * 
 * @author Jose Varas
 */

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import utils.ReversePercentage;

public class UTILS_ReversePercentage {

	public void setUp() throws Exception {
		
	}
	
	//test ReversePercentage values
	@Test
	public void reversePercetageTest(){
		
		int expectedValue;
		
		ReversePercentage z = new ReversePercentage();
		expectedValue = 3;
		assertEquals(expectedValue, z.reversePercentage(30));
	
		ReversePercentage y = new ReversePercentage();
		expectedValue = 4;
		assertEquals(expectedValue, y.reversePercentage(40));
		
		ReversePercentage a = new ReversePercentage();
		expectedValue = 2;
		assertEquals(expectedValue, a.reversePercentage(27));
		
		ReversePercentage j = new ReversePercentage();
		expectedValue = 3;
		assertEquals(expectedValue, j.reversePercentage(35));
		
		//values above 40 seconds
		ReversePercentage x = new ReversePercentage();
		expectedValue = 4;
		assertEquals(expectedValue, x.reversePercentage(70));
		
		//values above 40 seconds
		ReversePercentage f = new ReversePercentage();
		expectedValue = 4;
		assertEquals(expectedValue, f.reversePercentage(100));
	}
}
