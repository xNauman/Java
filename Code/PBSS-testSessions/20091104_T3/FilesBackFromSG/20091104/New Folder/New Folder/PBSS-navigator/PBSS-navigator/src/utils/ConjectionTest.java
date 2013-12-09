/**
 * This class takes rectangle dimensions and 
 * checks if a given point(XY) is present within this rectangle.
 * 
 * @author Jose Varas
 */
package utils;

public class ConjectionTest {

	/**
	 * Default constructor
	 */
	public ConjectionTest(){
		
	}


	/**
	 * 
	 * @param x1 lowest x point that the congestion starts at
	 * @param y1 lowest y point that the congestion starts at
	 * @param x2 highest x point that the congestion ends at
	 * @param y2 highest x point that the congestion ends at
	 * @param tX the x position of the bus
	 * @param tY the y position of the bus
	 * @return Boolean stating true if the tX and tY values are inside or on the border
  	 * line of the rectangle plotted by x1,y1,x2,y2, otherwise false
	 * 
	 */
public static boolean doTest(int x1,int y1, int x2, int y2, int tX, int tY){
		
	if(tX >= x1 && tX <= x2 && tY >= y1 &&  tY <= y2)
			
		return true;
	else
		return false;

	
}


	/**
	 * Test outputs
	 * @param args
	 */
	public static void main(String[] args) {
		// todo Auto-generated method stub
		
		boolean vTestResult = false;
		
		//returns true if the point is inside
	    vTestResult = ConjectionTest.doTest(10, 10, 20, 20, 15, 19);
		System.out.println("Test result is "+vTestResult);

		//returns false if the point is outside
		vTestResult = ConjectionTest.doTest(10, 10, 20, 20, 1, 25);
		System.out.println("Test result is "+vTestResult);
		
		//returns true if the line is on the border line
		vTestResult = ConjectionTest.doTest(10, 10, 20, 20, 20, 15);
		System.out.println("Test result is "+vTestResult);
		
	}

}
