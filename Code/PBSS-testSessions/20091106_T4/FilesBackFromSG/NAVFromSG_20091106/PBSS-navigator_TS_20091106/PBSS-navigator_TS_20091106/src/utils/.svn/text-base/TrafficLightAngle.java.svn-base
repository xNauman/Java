/**
 * Project:   Public Bus Service Simulator
 * Created:   Oct 28, 2009
 * Author:    Ken Ho and James Chen
 */
package utils;

import java.awt.Point;
import java.util.Vector;

import rawobjects.TrafficLight;
import rawobjects.Traffic_Light_Angle;

/**
 * Methods of this class calculates the angle at which a given bus is approaching towards a given
 * traffic light and finds the traffic light direction which corresponds to the direction of the bus.
 * 
 * @author Ken Ho and James Chen
 * 
 */
public class TrafficLightAngle {

	/*
	 * Instance variables
	 */
	private static final double degreesOfError = config.SysConfig.getErrorValueForTrafficLightAngles();
	private static Vector<Traffic_Light_Angle> vTLAngle = new Vector<Traffic_Light_Angle>();
	
	public static double calculateRoadAngle(Point pTLightPos, Point pBusPos) {
		
		// distance from the bus to the traffic light (hyothenus)
		int vDistanceToLight = Calculation.calculateDistance(pBusPos.x, pBusPos.y, pTLightPos.x, pTLightPos.y);
		System.out.println("vDistanceToLight is "+ vDistanceToLight);
		System.out.println("bus X:" +pBusPos.getX());
		double adjOverHypot = Math.abs(pBusPos.getX()/vDistanceToLight);
		System.out.println("Abs value of adj is "+adjOverHypot);
		
		double vRoadAngleInRadians = Math.acos(adjOverHypot);  // Alpha = Cos(inverse) times adjacent over hypothenus.
		System.out.println("vTLightAngleInRadians is "+ vRoadAngleInRadians);
		
		double vRoadAngleInDegrees = convertRadiansToDegrees(vRoadAngleInRadians); // convert from radians to degrees
		System.out.println("vRoadAngleInDegrees is "+ vRoadAngleInDegrees);
		//double vAngleBackInRadians = convertDegreesToRadians(vAngleBackInRadians);
		
		double vNewRoadAngleInDegrees = calcQuadrantandAngle(vRoadAngleInDegrees); // get quadrant alpha lies in and calculate the new angle
		System.out.println("vNewRoadAngleInDegrees is "+ vNewRoadAngleInDegrees);
		System.out.println();
		
		//vNewRoadAngleInDegrees = Math.round(vRoadAngleInDegrees); // round it
		//System.out.println("vNewRoadAngleInDegrees rounded is "+ vNewRoadAngleInDegrees);
		
		return vNewRoadAngleInDegrees;
	}
	
    public static double convertDegreesToRadians (double pDegrees) {
    	double vRadians = (Math.PI / 180) * pDegrees;
    	//System.out.println("vRadians is "+ vRadians);
    	return vRadians;
    } 
	
    public static double convertRadiansToDegrees(double pRadians) {
    	double vDegrees = (180 / Math.PI) * pRadians;
    	//System.out.println("vDegrees is "+ vDegrees);
      	return vDegrees;
    }

	
	public static int findTLightAtAngle(double pAngle, TrafficLight pTLight) {
		
		int vTLightDirection = -1;
		
		/* old code
		Traffic_Light_Angle vCurrentTLAngle;
		// get traffic angle object corresponding to the traffic light
		for (int i = 0; i < vTLAngle.size(); i++) {
			vCurrentTLAngle = vTLAngle.get(i);
			if (vCurrentTLAngle.getTrafficLightID() == pTLightId) {
				System.out.println("vCurrentTLAngle is "+ vCurrentTLAngle);
				break;
			}
		}*/
		
		vTLAngle = pTLight.getTrafficLightAngle(); // get traffic angle object vector corresponding to the traffic light
		Traffic_Light_Angle vCurrentTLAngle;
		System.out.println("Lower angle boundary is: "+ (pAngle - degreesOfError));
		System.out.println("Upper angle boundary is: "+ (pAngle + degreesOfError));
		
		for (int j = 0; j < vTLAngle.size(); j++) { // loop through each angle for that light and find the one that matches
			vCurrentTLAngle = vTLAngle.get(j);
			
			System.out.println("--- Current TL angle in vector is: "+ vCurrentTLAngle.getAngle());
			System.out.println("--- Angle of bus approaching is: "+ pAngle);
			
			if ((vCurrentTLAngle.getAngle() >= pAngle - degreesOfError) && (vCurrentTLAngle.getAngle() <= pAngle + degreesOfError)) {
				// if the angle of approaching bus is within the angle range of a light
				vTLightDirection = vCurrentTLAngle.getDirection(); // get the direction of the light that matches the angle
				break;
			}
			
		}
		
		System.out.println("Corresponding TL direction is: " + vTLightDirection);
		return vTLightDirection;
	}
	
	public static double calcQuadrantandAngle (double pAngle) {
		double vAngle = -1;
		
		if (pAngle >= 0 && pAngle < 90) { // Quadrant 1: angle stays same
			vAngle = pAngle;
		}
		else if (pAngle >= 90 && pAngle < 180) { // Quadrant 2: angle is 180 - pAngle
			vAngle = 180 - pAngle;
		}
		else if (pAngle >= 180 && pAngle < 270) { // Quadrant 3: angle is 180 + pAngle
			vAngle = 180 + pAngle;
		}
		else if (pAngle >= -0 && pAngle < -90) { // Quadrant 4: angle is negative of pAngle
			vAngle = pAngle * -1;
		}
		
		return vAngle;
	}
	
	public static void main(String[] args) {
		//System.out.println("Degree to radian is "+ convertDegreesToRadians(360));
		//System.out.println("Radian to degree is "+ convertRadiansToDegrees(6.283185307179586));
		
		//calculateRoadAngle(new Point (-5, 10), new Point (-1, 18));
		//System.out.println();
		//calculateRoadAngle(new Point (2, 10), new Point (7, 17));
		
		/* Dummy test data -- added vector of angles to TL object + getters/setters -- only for testing not sure how this will be handled normally */
		Vector<Traffic_Light_Angle> vTLAng = new Vector<Traffic_Light_Angle>();	
		Traffic_Light_Angle vTLAngle1 = new Traffic_Light_Angle(1, 1, (short) 1, 39);
		Traffic_Light_Angle vTLAngle2 = new Traffic_Light_Angle(1, 1, (short) 2, 75);
		Traffic_Light_Angle vTLAngle3 = new Traffic_Light_Angle(1, 1, (short) 3, 130);
		vTLAng.add(vTLAngle1);
		vTLAng.add(vTLAngle2);
		vTLAng.add(vTLAngle3);
		
		double vAngle = calculateRoadAngle(new Point (2, 10), new Point (7, 17));
		//System.out.println("vAngle is: "+ vAngle);
		
		TrafficLight vTLight = new TrafficLight(1, 1, 2, 10, 5, false, 2, 1, 4, vTLAng);		
		findTLightAtAngle(vAngle, vTLight);
	}
	
}

//Input: traffic light location, bus location
//Output: the angle of the light (X) of the road the bus is on which is used to retrieve
//the (closest) light to that angle (from the DB)

//1) get length of the line (Bus to traffic light) using pythagoras.
//2) make everything positive and calc the angle (alpha). - Why do we make everything positive? so we don't have
//to deal with the moving around the circle - that's dealth with as we have quadrants.
//3) figure out what quadrant it's in - Refer to image 0061
//4) HOW do we calc the angle? Pic 0055. Alpha = Cos(inverse) times adjacent over hypothenus.
//5) how do we get adjacent and hypo? Hypo is the length calculated in (1). Adjacent is basically (x,0) x being the x of the bus.
//6) once we know all that, depending on which quadrant it's in, we add 90/180/270 to the calculated angle in (5).
//7) then compare to angles in db with a 3 degree error(or however amount)