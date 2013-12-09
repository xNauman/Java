/**
 * Project:   Public Bus Service Simulator
 * Created:   Sep 22, 2009
 * Author:    Ken Ho and James Chen
 */
package utils;

/**
 * Methods of this class perform calculations for the movement of buses such as the distance between two given points
 * which a bus needs to traverse and the time taken for the bus to traverse this distance at a given speed.
 * 
 * @author Ken Ho and James Chen
 * 
 */
public class Calculation {
	
	/*
	 * Constructors.
	 */
	public Calculation() {
		
	}
	
	/**
	 * Calculates the distance between two given points represented as a set of (x,y) coordinates.
	 * A set of (x,y) coordinates represents one unit of distance [this can be changed according to what we define the distance of each point as]
	 * 
	 * @param pSourcePointX
	 * 			the x coordinate of the starting point
	 * @param pSourcePointY
	 * 			the y coordinate of the starting point
	 * @param pDestPointX
	 * 			the x coordinate of the destination point
	 * @param pDestPointY
	 * 			the y coordinate of the destination point
	 * @return the units of distance between two given points as an integer value	
	 */
	public static int calculateDistance(int pSourcePointX, int pSourcePointY, int pDestPointX, int pDestPointY) {
		double vDistance = 0;
		/* Horizontal Case: Y coordinates are the same */
		if (pSourcePointY == pDestPointY && pSourcePointX != pDestPointX)
			vDistance = Math.abs(pSourcePointX - pDestPointX);
		/* Vertical Case: X coordinates are the same */
		else if (pSourcePointX == pDestPointX && pSourcePointY != pDestPointY)
			vDistance = Math.abs(pSourcePointY - pDestPointY);
		/* Diagonal Case: the (x,y) coordinates are different */
		else {
			int xDistance = pDestPointX - pSourcePointX;
			int yDistance = pDestPointY - pSourcePointY;
			/* Use the Pythagorean Theorem to calculate the diagonal distance */
			vDistance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
			vDistance = Math.round(vDistance);
		}
		
		int vDistanceInt = 0;
		/* Convert double to integer */
		vDistanceInt = (int)vDistance;
		
		return vDistanceInt;
	}
	
	/**
	 * Calculates the total time taken for a bus to traverse a given distance at a relative speed 
	 * [speed can be normal speed or congested speed, depends on how we are going to define speed]
	 * 
	 * @param pDistance
	 * 			the total units of distance which need to be traversed by the bus
	 * @param pSpeed
	 * 			the speed at which the bus is travelling 
	 * @return the total time taken (in seconds?) for a bus to traverse a given distance
	 */
	public static int calculateTime(int pDistance, int pSpeed) {
		return (pDistance * pSpeed);
	}
	
	
	/**
	 * For testing output
	 * @param args
	 */
	public static void main(String[] args) {
		
		int vTestDistance = 0;
		int vTestTime = 0;

		vTestDistance = Calculation.calculateDistance(0, 1, 0, 15);
		System.out.println("Horizontal Distance is "+ vTestDistance);
		vTestTime = Calculation.calculateTime(vTestDistance, 1);
		System.out.println("Time Taken to travel that distance: "+ vTestTime);

		vTestDistance = Calculation.calculateDistance(15, 0, 40, 0);
		System.out.println("Vertical Distance is "+ vTestDistance);
		vTestTime = Calculation.calculateTime(vTestDistance, 2);
		System.out.println("Time Taken to travel that distance: "+ vTestTime);
		
		vTestDistance = Calculation.calculateDistance(1, 1, 10, 10);
		System.out.println("Diagonal Distance1 is "+ vTestDistance);
		vTestTime = Calculation.calculateTime(vTestDistance, 1);
		System.out.println("Time Taken to travel that distance: "+ vTestTime);
		
		vTestDistance = Calculation.calculateDistance(1, 1, 10, 17);
		System.out.println("Diagonal Distance2 is "+ vTestDistance);
		vTestTime = Calculation.calculateTime(vTestDistance, 2);
		System.out.println("Time Taken to travel that distance: "+ vTestTime);
		
	}
	
	
}

