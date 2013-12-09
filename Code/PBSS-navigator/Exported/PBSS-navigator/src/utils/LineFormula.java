package utils;
/**
 * Class that holds all the details about a line between two X and Y points
 * Based on the GetMB code written by James
 * 
 * @author David Taberner
 * @author Ken Ho
 * 
 *
 */

public class LineFormula {
	/*
	 * Instance variables
	 */
	
	private float iX1;
	private float iX2;
	private float iY1;
	private float iY2;
	private double iLineSlopeM;
	private double iYInterceptB;
	
	
	
	
	/*
	 * Constructors.
	 */

	/**
	 * Default constructor for the line formula object
	 * takes two points set on the number plane and then calculates the m and b so we 
	 * can get a formula for the line
	 * 
	 * @param pX1
	 * @param pY1
	 * @param pX2
	 * @param pY2
	 */
	public LineFormula(int pX1, int pY1, int pX2, int pY2)
	{
		
		//set the points
		this.setiX1(pX1);
		this.setiY1(pY1);
		this.setiX2(pX2);
		this.setiY2(pY2);
		
		//get a B value set
		calculateB();
		
		//get an M value set
		calculateM() ;
		
	}
	
	
	/**
	 * Method will return the corresponding Y value that is on the line for and X value
	 * 
	 * NOTE: this method doesn't check that this x or y is between the points specified
	 * 
	 * @param pXValue - the X point we want to get the Y value for
	 * 
	 * @return Y value that matches for that X point for the line defined in the constructor 
	 */
	public int getYValue(int pXValue)
	{
		double iReturnValue;
		
		// NOTE: for a vertical line case the x coordinate is always the same so do NOT use this formula or you will get errors, obtaining
		//       the y values is just a matter of looping through all the y coordinates in correct order with the x coordinate staying the same.
		if (getiY1() == getiY2()) {
			iReturnValue = calculateB(); // y = b if horizontal line (Y's are the same)
			//System.out.println("M: " + calculateB());
		}
		else { // diagonal line case so use y = mx + b
			iReturnValue = calculateM() * pXValue + calculateB();
			//System.out.println("M: " + calculateM());
			//System.out.println("B: " + calculateB());
		}
        
		// round the decimal result and convert to integer - done at the end to obtain a more accurate result
		// E.g. the slope m will always be rounded up or down to 1 or 0 respectively thus getting a really inaccurate result
		iReturnValue = Math.round(iReturnValue);
        int iReturnValueInt = 0;
        iReturnValueInt = (int)iReturnValue;
		
		return iReturnValueInt;
	}
	
	/**
	 * Check if a provided X point lines within the X values defined in the constructor
	 * 
	 * Note: this checks if points were put in a left to right x1 x2 ie x2 is > x1, but also checks 
	 * in the opposite direction, if x2 < x1 ie right to left. 
	 * 
	 * @param pXValue - interger X value that we want to test for
	 * @return - boolean - True if inside the x values the object was constructed with, otherwise false
	 */
	public boolean checkXIsInRange(int pXValue)
	{	
		boolean bReturn = false;
		
		//check if we are in range do this for X1 and X2
		if(pXValue >= iX1 && pXValue <= iX2)
		{
			bReturn = true;
		}
		//but what if the points were defined going right to left? Better do this too
		else if(pXValue <= iX1 && pXValue >= iX2)
		{
			bReturn = true;
		}
		
		return bReturn;
	}
	
	/**
	 * Calculates the b-value(Y-Intercept) of the equation (thus line) based on two sets of: a source coodinate and a destination coordinate.
	 * A set of (x,y) coordinates represents one unit of distance [this can be changed according to what we define the distance of each point as].
	 * The base mathematical formula for this calculation is b = Y1 - mX1
     * Hence:
     * b = Y1 - ((Y2-Y1/X2-X1) * X1)
     *
     *
     * WARNING: Until I figure out how to make an exception, (or do we assume this won't be the case?) any set of ccordinates on the same X
     * WARNING: (i.e. (15,20) (15,44)) will cause a divide by 0.
     * WARNING:
     * WARNING: We don't like dividing by zero.
     *
     *
     * James
     *
     * Method uses values set when the object constructor is created, there are no perams now 
     *
	 */
	//Changed from private to protected for testing purposes
	//**may change back if need be
    protected double calculateB(){
        
        /*  b = Y1 - ((Y2-Y1/X2-X1) * X1) */
       double vYintercept = (iY1 - (((iY2 - iY1)/(iX2 - iX1)) * iX1));

       /*  Rounds off to 0 decimal places for conversion to int */
       // vYintercept = Math.round(vYintercept);

       /* Convert double to integer */
       //int vYinterceptInt = 0;
       
       /* Convert double to integer */
       //vYinterceptInt = (int)vYintercept;

       /*  Returns b */
       setiYInterceptB(vYintercept);
       return vYintercept;
    }
    
	//Used for testing purposes
	public double public_calculateB(){
		
	double b = calculateB();
	return b;

	}

	
	/**
	 * Calculates the m-value(Slope) of the equation (thus line) based on two sets of: a source coodinate and a destination coordinate.
	 * A set of (x,y) coordinates represents one unit of distance [this can be changed according to what we define the distance of each point as].
	 * The base mathematical formula for this calculation is m = (Y2-Y1/X2-X1).
     *
     *
     * WARNING: Until I figure out how to make an exception, (or do we assume this won't be the case?) any set of ccordinates on the same X 
     * WARNING: (i.e. (15,20) (15,44)) will cause a divide by 0. 
     * WARNING: 
     * WARNING: We don't like dividing by zero.
     *
     *
     * James
     * 
     * Method uses values set when the object constructor is created, there are no perams now 
     * 
	 */
    //Changed from private to protected for testing purposes
	//**may change back if need be
	protected double calculateM() {

        /*  m = (Y2-Y1/X2-X1) */
        double vSlope = ((iY2 - iY1)/(iX2 - iX1));

        /*  Rounds off to 0 decimal places for conversion to int */
        //vSlope = Math.round(vSlope);

        /*  Variable to store int to be returned */
        //int vSlopeInt = 0;
        
        /* Convert double to integer */
		//vSlopeInt = (int)vSlope;
		

        /*  Returns m */
		setiLineSlopeM(vSlope);
		//System.out.println(vSlopeInt);
        
        return vSlope;
    }
	
	//Used for testing purposes
	public double public_calculateM(){
	
	double m = calculateM();
	return m;
	}
    
    
	/*
	 * Getters and Setters
	 */
	public void setiY1(int iY1) {
		this.iY1 = (float)iY1;
	}

	public int getiY1() {
		return (int) iY1;
	}

	public void setiX1(int iX1) {
		this.iX1 = (float)iX1;
	}

	public int getiX1() {
		return (int) iX1;
	}

	public void setiX2(int iX2) {
		this.iX2 = (float)iX2;
	}

	public int getiX2() {
		return (int) iX2;
	}

	public void setiY2(int iY2) {
		this.iY2 = (float)iY2;
	}

	public int getiY2() {
		return (int)iY2;
	}

	public void setiLineSlopeM(double iM) {
		this.iLineSlopeM = iM;
	}

	public double getiLineSlopeM() {
		return iLineSlopeM;
	}


	public void setiYInterceptB(double iYInterceptB) {
		this.iYInterceptB = iYInterceptB;
	}


	public double getiYInterceptB() {
		return iYInterceptB;
	}
	//Test out two X points and Two Y points to get the M Slope and Y Intercept
	public static void main(String[] args) {
		
		//LineFormula a = new LineFormula(4,8,2,6);
		//a.calculateM();
		//System.out.println("M is: " + a.calculateM());
		
		//a.calculateB();
		//System.out.println("B is: " + a.calculateB());
		
		
		
		//System.out.println(a.getiX1());
		//System.out.println(a.getiY1());
		//System.out.println(a.getiX2());
		//System.out.println(a.getiY2());
		
		
		// diagonal case --> Y = 0.8461538461538461 * X  + (—8.153846153846153) - WORKS
		/*LineFormula b = new LineFormula(25, 13, 38, 24);
		System.out.println("M is: " + b.calculateM());
		System.out.println("B is: " + b.calculateB());
		int start = b.getiX1();
		int end = b.getiX2();
		System.out.println("X1 is: " + b.getiX1());
		System.out.println("X2 is: " + b.getiX2());
		
		for (int i = start; i <= end; i++) {
			System.out.println("Y is: " + b.getYValue(i));
		}*/
		
		
		// horizontal case --> // NOTE: getYValue function does not work properly i cannot/do not know how to fix this, y = b
		/*
		LineFormula c = new LineFormula(33, 24, 21, 24);
		System.out.println("M is: " + c.calculateM());
		System.out.println("B is: " + c.calculateB());
		
		int start = c.getiX1();
		int end = c.getiX2();
		System.out.println("X1 is: " + c.getiX1());
		System.out.println("X2 is: " + c.getiX2());
		for (int i = start; i <= end; i++) {
			System.out.println("Y is: " + c.getYValue(i));
		}*/
		
		// vertical case --> NOTICE I did not use y = mx + b or i would get divide by zero errors - just loop through all y values to get y
		/*
		LineFormula d = new LineFormula(22, 15, 22, 24);
		int start = d.getiY1();
		int end = d.getiY2();
		
		System.out.println("Y1 is: " + d.getiY1());
		System.out.println("Y2 is: " + d.getiY2());
		for (int i = start; i <= end; i++) {
			System.out.println("Y is: " + i);
			// X just stays the same
		}*/

	}


}
