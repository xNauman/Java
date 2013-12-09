package junit;
/**
 * JUNIT test for utils.LineFormula code
 * 
 * @author Jose Varas
 */

import static org.junit.Assert.assertEquals;
import utils.LineFormula;

import org.junit.Test;


public class UTILS_LineFormula {
	
	public void setUp() throws Exception {
		
	}
	
	
	//test for the Slope of the different points given
	//Each point has been manually calculated and set to its expected slope.
	@Test
	public void testcalculateM(){
		double expectedM;
		double m;
		
		LineFormula w = new LineFormula(2,3,4,5);
		m = w.public_calculateM();
		expectedM = 1.0;
		assertEquals(expectedM,m,0);
		
		LineFormula p = new LineFormula(4,8,5,10);
		m = p.public_calculateM();
		expectedM = 2.0;
		assertEquals(expectedM,m,0);
		
		//diagonal case
		LineFormula y = new LineFormula(25,13,38,24);
		m = y.public_calculateM();
		expectedM = 0.8461538553237915;
		assertEquals(expectedM,m,0);
		
		//testing irrational decimal number
		LineFormula z = new LineFormula(100,200,30,150);
		m = z.public_calculateM();
		expectedM = 0.7142857313156128;
		assertEquals(expectedM,m,0);
		
		
	}
	//test for the Y Intercept of the different points given
	//Each point has been manually calculated and set to its expected Y Intercept.
	@Test
	public void testcalculateB(){
		double expectedB;
		double b;
		
		
		LineFormula k = new LineFormula(2,3,4,5);
		b = k.public_calculateB();
		expectedB = 1.0;
		assertEquals(expectedB,b,0);
		
		LineFormula e = new LineFormula(4,8,5,10);
		b = e.public_calculateB();
		expectedB = 0.0;
		assertEquals(expectedB,b,0);
		
		//diagonal case
		LineFormula d = new LineFormula(25,13,38,24);
		b = d.public_calculateB();
		expectedB = -8.153846740722656;
		assertEquals(expectedB,b,0);
		
		
		//testing irrational decimal number
		LineFormula j = new LineFormula(100,200,30,150);
		b = j.public_calculateB();
		expectedB= 128.57142639160156;
		assertEquals(expectedB,b,0);
		
		
	}

}
