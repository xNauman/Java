/**
 * Project:   Public Bus Service Simulator
 * Created:   Sep 24, 2009
 * Author:    James Chen
 */


/**
 * Methods of this class perform calculations to get the Slope(M) and the Y-Intercept (B) based on the Y= mX + b mathematical formula.
 *
 *
 * Calculation based on formula as follows:
 *
 * Y = mX + b
 *
 * getM:
 * m = (Y2-Y1/X2-X1)
 *
 * getB:
 * b = Y1 - mX1 // b = Y1 - [(Y2-Y1/X2-X1) * X1]
 *
 * Hence (for reference only):
 * Y = [(Y2-Y1/X2-X1) * X] + [{Y1 - (Y2-Y1/X2-X1) * X1 }]
 *
 * @author James Chen
 * 
 */
public class GetMB {
	
	/*
	 * Constructors.
	 */
	public GetMB() {
		
	}
	
	public static void main(String[] args) {
		System.out.println("Code Test");
		
        int iGradientValue = calculateM(5, 9, 8, 94);
        System.out.println("M VAL = " + iGradientValue);
        
        int iBValue = calculateB(5, 9, 8, 94);
        System.out.println("B VAL = " + iBValue);
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
	 * @param pSourcePointX
	 * 			the x coordinate of the starting point
	 * @param pSourcePointY
	 * 			the y coordinate of the starting point
	 * @param pDestPointX
	 * 			the x coordinate of the destination point
	 * @param pDestPointY
	 * 			the y coordinate of the destination point
	 * 
     * @return the m-value (Slope) between two given points as an integer value.
     * 
	 */

	public static int calculateM(int pSourcePointX, int pSourcePointY, int pDestPointX, int pDestPointY) {

        /*  m = (Y2-Y1/X2-X1) */
        double vSlope = ((pDestPointY - pSourcePointY)/(pDestPointX - pSourcePointX));

        /*  Rounds off to 0 decimal places for conversion to int */
        vSlope = Math.round(vSlope);

        /*  Variable to store int to be returned */
        int vSlopeInt = 0;
        
        /* Convert double to integer */
		vSlopeInt = (int)vSlope;

        /*  Returns m */
        return (vSlopeInt);
        
        
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
	 * @param pSourcePointX
	 * 			the x coordinate of the starting point
	 * @param pSourcePointY
	 * 			the y coordinate of the starting point
	 * @param pDestPointX
	 * 			the x coordinate of the destination point
	 * @param pDestPointY
	 * 			the y coordinate of the destination point
	 *
     * @return the b-value(Y-Intercept)) between two given points as an integer value.
     *
	 */

    public static int calculateB (int pSourcePointX, int pSourcePointY, int pDestPointX, int pDestPointY) {
        
     /*  b = Y1 - ((Y2-Y1/X2-X1) * X1) */
    double vYintercept = (pSourcePointY - (((pDestPointY - pSourcePointY)/(pDestPointX - pSourcePointX)) * pSourcePointX));

    /*  Rounds off to 0 decimal places for conversion to int */
     vYintercept = Math.round(vYintercept);

    /* Convert double to integer */
    int vYinterceptInt = 0;
    
    /* Convert double to integer */
    vYinterceptInt = (int)vYintercept;

    /*  Returns b */
    return (vYinterceptInt);
    }
}


