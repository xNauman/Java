package junit;
/**
 * junit test for utils.ConjectionTest code
 * 
 * @author David Taberner & Jose Varas
 * 
 */

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class UTILS_ConjectionTest {
	
	
	//setUp gets called before any testing is done, so put any stuff you need to setup here first
	@Before
	public void setUp() throws Exception {
		
	}
	
	//test points inside the conjection zone
	@Test
	public void testIsAreaConjected() {
		
		//do various checks for inside a conjected area 
		assertTrue(utils.ConjectionTest.doTest(10, 10, 20, 20, 12, 13));
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 500, 250));
		
		//check one back from edge
		//left side
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 2, 3));
		//right side
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 999, 50));
		
		//top
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 5, 999));
		
		//bottom
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 20, 2));
		
		

		
	}
	
	//test on the line is conjected
	@Test
	public void testIsAreaOnLineIsConjected()
	{
		//check on the line
		//left side
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 1, 20));
		//right side
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 1000 , 40));
		
		//bottom
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 20, 1));
		
		//top
		assertTrue(utils.ConjectionTest.doTest(1, 1, 1000, 1000, 60, 1000));
		
	}
	
	//test points outside are not congested
	@Test
	public void testIsAreaNotConjected()
	{
		//first general test
		assertTrue(!utils.ConjectionTest.doTest(2, 3, 5, 6, 1, 1));
		
		//test left outside
		assertTrue(!utils.ConjectionTest.doTest(2, 3, 5, 6, 1, 3));
		
		//test right outside
		assertTrue(!utils.ConjectionTest.doTest(2, 3, 5, 6, 6, 4));
		
		//test top outside
		assertTrue(!utils.ConjectionTest.doTest(2, 3, 5, 6, 4, 7));
		
		//test bottom outside
		assertTrue(!utils.ConjectionTest.doTest(2, 3, 5, 6, 3, 1));
		
	}

}
