/**
 * This class takes a rectangle dimentions and 
 * checks if a given point(XY) is present in this rectangle
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
	 * @return Boolean stating true if the tX and tY values are inside the rectangle plotted by x1,y1,x2,y2, otherwise false
	 * 
	 */
public static boolean doTest(int x1,int y1, int x2, int y2, int tX, int tY){
		
	if(tX > x1 && tX < x2)
		if(tX >y1 &&  tX < y2)
			return true;
		else
			return false;
	
	else return false;
	
}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// todo Auto-generated method stub
		
		boolean vTestResult = false;
		
		//testing a point outside the rectangle
	    vTestResult = ConjectionTest.doTest(10, 10, 20, 20, 15, 19);
		System.out.println("Test result is "+vTestResult);

		//testing a point inside a rectangle
		vTestResult = ConjectionTest.doTest(10, 10, 20, 20, 1, 25);
		System.out.println("Test result is "+vTestResult);
		
		//testing a point inside a rectangle
		vTestResult = ConjectionTest.doTest(2, 2, 5, 5, 2, 3);
		System.out.println("Test result is "+vTestResult);
		
	}

}
