package junit;
/**
 * junit test for utils.Calculation code
 * 
 * @author Jose Varas
 */

import static org.junit.Assert.assertEquals;


import org.junit.Test;

public class UTILS_Calculation {
	
	
	public void setUp() throws Exception {
		
	}
	
	//Tests for horizontal Distances & time from source point to destination point 
	@Test
	public void hordistancetimeTest()
	{
		int expectedDistance;
		int expectedTime;
		
		
		expectedDistance=14;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(0, 1, 0, 15));
		expectedTime=14;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 1));
		
		expectedDistance=25;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(15, 1, 40, 0));
		expectedTime=50;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 2));

		expectedDistance=6;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(0, 0, 0, 6));
		expectedTime=18;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 3));
		
		expectedDistance=0;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(0, 0, 0, 0));
		expectedTime=0;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 2));
		
	}
	//Tests for vertical Distances & time from source point to destination point
	@Test
	public void verdistanceTest()
	{
		int expectedDistance;
		int expectedTime;
		
		expectedDistance=25;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(15, 0, 40, 0));
		expectedTime=75;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 3));
		
		expectedDistance=10;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(20, 2, 30, 2));
		expectedTime=30;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 3));
		
		expectedDistance=6;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(6, 0, 0, 0));
		expectedTime=6;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 1));
		
		expectedDistance=1000;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(0, 0, 1000, 0));
		expectedTime=2000;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 2));
		
	}
	//Tests for diagonal Distances & time from source point to destination point
	@Test
	public void DiadistancetimeTest()
	{
		int expectedDistance;
		int expectedTime;
		
		expectedDistance=13;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(1, 1, 10, 10));
		expectedTime=26;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 2));
		
		expectedDistance=18;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(1, 1, 10, 17));
		expectedTime=18;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 1));
		
		expectedDistance=9;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(1, 1, 2, 10));
		expectedTime=27;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 3));
		
		expectedDistance=5;
		assertEquals(expectedDistance,utils.Calculation.calculateDistance(0, 0, 5, 2));
		expectedTime=10;
		assertEquals(expectedTime,utils.Calculation.calculateTime(expectedDistance, 2));
		
	}

}
