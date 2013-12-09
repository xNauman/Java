package routeCheck;

import java.awt.Point;
import java.util.Vector;

/**
 * Handles the identifications of obstructions that may occur when
 * moving from Point A to Point B.
 * 
 * @author Ben
 *
 */
public class SchRouteChecker {

	/*
	 * Constants.
	 */
	/** The radius of the locus */
	public static final int LOCUS_RADIUS = 40; //was 3

	private static boolean doDebug=false;

	private static Vector<SchSimpleObjCongArea> vCongArea = new Vector<SchSimpleObjCongArea>();
	private static Vector<SchSimpleObjStop> vStop = new Vector<SchSimpleObjStop>();
	private static Vector<SchSimpleObjTL> vTL = new Vector<SchSimpleObjTL>();

	public static void simpleTLClear(){
		vTL.clear();
	}

	public static void SimpleTLAdd(SchSimpleObjTL sTL){
		//TODO 3LOW [PERF]: This may be better as a map?
		vTL.add(sTL);
	}

	public static void simpleStopClear(){
		vStop.clear();
	}

	public static void SimpleStopAdd(SchSimpleObjStop pStop){
		//TODO 3LOW [PERF]: This may be better as a map?
		vStop.add(pStop);
	}

	public static void simpleCAClear(){
		vCongArea.clear();
	}

	public static void SimpleCAAdd(SchSimpleObjCongArea pCA){
		//TODO 3LOW [PERF]: This may be better as a map?
		vCongArea.add(pCA);
	}

	public static void setSimpleObjCongArea(Vector<SchSimpleObjCongArea> pVectCongArea){
		vCongArea = pVectCongArea;
	}

	public static void setSimpleObjStop(Vector<SchSimpleObjStop> pVectStop){
		vStop = pVectStop;
	}

	public static void setSimpleObjTL(Vector<SchSimpleObjTL> pVectTL){
		vTL = pVectTL;
	}

	/**
	 * <p>
	 * Checks the locus area for a given path. The points which lie within a given path is 
	 * retrieved using the method {@link #getPathPointData(Point, Point)}. The set of
	 * objects which lie within the locus of each these points is then retrieved using the
	 * method {@link #checkLocusOfPathPoint(Vector, Vector, Vector, Point)}.
	 * </p>
	 * 
	 * <p>
	 * All objects which lie with the locus area of the given path are checked by comparing
	 * the location of the object and its object type to verify that they are unique and
	 * do not already exist.
	 * </p>
	 * 
	 * @param vCongArea
	 * @param vStop
	 * @param vTL
	 * @param pStartPoint
	 * 				the starting point where the path begins.
	 * @param pEndPoint
	 * 				the starting point where the path ends.
	 * @return a vector containing all the unique objects which are located the locus area of a given path.
	 */
	/* NOTE: Problem with null pointer exceptions if i don't use the vectors as parameters : BEN removed, as per email
	 * For testing purpose i have left these parameters - Javadoc will be changed according to what is used */
	public static Vector<SchMovePointData> getMovePointData (Point pStartPoint, Point pEndPoint) {

		Vector<SchMovePointData> moveData = new Vector<SchMovePointData>();

		if (SchRouteChecker.areStaticObjectEmpty()) {
			SchMovePointData movePoint = new SchMovePointData();
			
			movePoint.setObjectTypeID(SchEnumObjectType.NORMAL);
			movePoint.setPoint(pEndPoint);
			movePoint.setRefDBID(-1);
			movePoint.setRefID(-1);
			
			moveData.add(movePoint);
			//System.out.println("PBSS-Scheduler.routeCheck.getMovePointData: ALL STATIC OBJECTS ARE EMPTY");
			return moveData;
		}
		//System.out.println("PBSS-Scheduler.routeCheck.getMovePointData NOT IMPLEMENTED");

		Vector<Point> vTempPathPoints = getPathPointData(pStartPoint, pEndPoint); // get all (x,y) points on the path from start to end

		System.out.println("SchRouteChecker.getMovePointData:: vTempPathPoints SIZE = " + vTempPathPoints.size());
		
		Point vCurrentPathPoint;
		for (int i = 0; i < vTempPathPoints.size(); i++) {
			vCurrentPathPoint = vTempPathPoints.get(i);

			if (doDebug) System.out.println("--- START - TempMoveData Vector Output at PathPoint Before: " +  vCurrentPathPoint + "---" );
			Vector<SchMovePointData> tempMoveData = checkLocusOfPathPoint (vCongArea, vStop, vTL, vCurrentPathPoint); // get all objects in locus of current path point
			debugPrintMoveData(tempMoveData, true);
			if (doDebug) System.out.println("--- END - TempMoveData Vector Output at PathPoint Before: " +  vCurrentPathPoint + "---");	
			if (doDebug) System.out.println("\n");	

			if (tempMoveData.size() != 0) { // if no objects are found surrounding that path point

				if (moveData.size() == 0) // if no objects exist in moveData then add the first one from tempMoveData to avoid null pointers
					moveData.add(tempMoveData.get(0));

				// add loop to check an object doesn't already exist in the moveData vector before adding it
				for (int a = 0; a < tempMoveData.size(); a++) {
					for (int b = 0; b < moveData.size(); b++) {

						if (doDebug) System.out.println("PointA: " + tempMoveData.get(a).getPoint());
						if (doDebug) System.out.println("PointB: " + moveData.get(b).getPoint());
						if (doDebug) System.out.println("ObjectA: " + tempMoveData.get(a).getObjectTypeID());
						if (doDebug) System.out.println("ObjectB: " + moveData.get(b).getObjectTypeID());
						if (doDebug) System.out.println("\n");


						// if they have the same point on map then check both objects are not the same, so compare the object types as well
						if (tempMoveData.get(a).getPoint().equals((moveData.get(b)).getPoint()) &&
								tempMoveData.get(a).getObjectTypeID().equals((moveData.get(b)).getObjectTypeID())) 
						{
							tempMoveData.removeElementAt(a); // delete the repeating object
							a = a - 1; // as objects are shifted down we need to recheck the current index we just deleted
							break; // break out to avoid out of bounds exception since we deleted the object
						}
					}
				}

				moveData.addAll(tempMoveData); // add all unique non repeating objects to the move point data vector
			}

			else {
				if (doDebug) System.out.println("No Objects found on that Path Point");
			}

			if (doDebug) System.out.println("--- START - TempMoveData Vector Output at PathPoint After: " +  vCurrentPathPoint + "---" );
			debugPrintMoveData(tempMoveData, true);
			if (doDebug) System.out.println("--- END - TempMoveData Vector Output at PathPoint After: " +  vCurrentPathPoint + "---");	
			if (doDebug) System.out.println("\n");

		}

		return moveData;
	}

	/**
	 * Given a start and a finish point represented as (x,y) coordinates, this method calculates all the
	 * points which lie on the path between these two points. For a horizontal or diagonal path, the method 
	 * {@link #calculateY(Point, Point, int)} is used to obtain the y coordinate for each point on the path.
	 * For a vertical path, the x coordinate never changes t on the path so to obtain the corresponding y
	 * coordinate for each point on the path a simple loop is used.
	 * 
	 * @param pPathStartPoint
	 * 				the starting point where the path begins.
	 * @param pPathEndPoint
	 * 				the finish point where the path ends.
	 * @return a vector containing all the points which lie on the path between the given starting and finish points.
	 */
	public static Vector<Point> getPathPointData(Point pPathStartPoint, Point pPathEndPoint) {
		Vector<Point> pathPoints = new Vector<Point>(); // vector to store all points on the path from the start to the end
		int vPointX = pPathStartPoint.x; // default for vertical cases - X coordinate always the same
		int vPointY = 0;

		if (pPathStartPoint.x == pPathEndPoint.x) { // if vertical line (X's are the same)
			if (pPathStartPoint.y < pPathEndPoint.y) // if going --> direction e.g. (10,9) to (10,15)
				for (int i = pPathStartPoint.y; i <= pPathEndPoint.y; i++) { // loop through all the y coordinates
					vPointY = i;
					Point vCurrentPathPoint = new Point(vPointX, vPointY);
					pathPoints.add(vCurrentPathPoint); // add the current path point to the vector
				}
			else { // else going <-- direction e.g. (10,15) to (10, 9)
				for (int i = pPathStartPoint.y; i >= pPathEndPoint.y; i--) {
					vPointY = i;
					Point vCurrentPathPoint = new Point(vPointX, vPointY);
					pathPoints.add(vCurrentPathPoint);
				}
			}
		}

		else { // horizontal and diagonal cases
			if (pPathStartPoint.x < pPathEndPoint.x) { // if going --> direction e.g. (5,10) to (8,10) OR (5,10) to (9,15)
				for (int i = pPathStartPoint.x; i <= pPathEndPoint.x; i++) { // loop through all the x coordinates
					vPointX = i; // The current path point's X coordinate is the current X point we are at in the loop

					/* Call y = mx + b here to get the current path point's Y coordinate */
					vPointY = calculateY (pPathStartPoint, pPathEndPoint, vPointX); 			
					Point vCurrentPathPoint = new Point(vPointX, vPointY);
					pathPoints.add(vCurrentPathPoint);
				}
			}
			else { // else going <-- direction e.g. (8,10) to (5,10) OR (9, 15) to (5,10)
				for (int i = pPathStartPoint.x; i >= pPathEndPoint.x; i--) {
					vPointX = i;
					vPointY = calculateY (pPathStartPoint, pPathEndPoint, vPointX);
					Point vCurrentPathPoint = new Point(vPointX, vPointY);
					pathPoints.add(vCurrentPathPoint);
				}
			}
		}

		/*
		System.out.println("--- START - Path Point Output ---");
		Point d;
		for (int i = 0; i < pathPoints.size(); i++) {
			d = pathPoints.get(i);
			System.out.println("Current Position in pathPoints Vector is: " + i);
			System.out.println("(X,Y) Point is: " + d.x + ", " + d.y);
		}
		System.out.println("--- END - Path Point Output ---");
		System.out.println("\n");
		 */

		return pathPoints;
	}

	/**
	 * Checks the locus area for a point which lies on a path. A locus area is defined as a set of
	 * points surrounding a given point, the radius of this locus area is defined by {@link #LOCUS_RADIUS}.
	 * The set of points within the locus area is obtained by first retrieving the path point, then the
	 * points surrounding this point and then the points surrounding the last set of points which were
	 * retrieved, so on and so forth such that all points retrieved within the locus area are unique.
	 * 
	 * @param pPathPoint
	 * 				the path point which lies on a path.
	 * @return a vector containing all the points which lie within the locus area for a path point.
	 */
	public static Vector<Point> getPointsWithinLocus (Point pPathPoint) {
		Vector<Point> pointsAroundPathPoint = new Vector<Point>();	
		int vPointX; // (X,Y) points for every point we need to check surrounding the path point
		int vPointY;

		for (int i = 1; i <= LOCUS_RADIUS; i++) { // check from inside first then fan out
			for (int j = pPathPoint.y - i; j <= pPathPoint.y + i; j++) { // check from top to bottom - left to right
				if (i == 1) { // get the path point and points surrounding it
					for (int k = pPathPoint.x - i; k <= pPathPoint.x + i; k++) {
						vPointX = k;
						vPointY = j;
						Point vCurrentPoint = new Point(vPointX, vPointY);
						pointsAroundPathPoint.add(vCurrentPoint);
					}
				}
				else { // grab points on the perimeter of the previous set of points
					for (int k = pPathPoint.x - i; k <= pPathPoint.x + i; k++) {
						int topEdge = pPathPoint.y - i;
						int bottomEdge = pPathPoint.y + i;

						if (j == topEdge || j == bottomEdge) { // if we are at the top or bottom edge perimeters grab the whole row
							vPointX = k;
							vPointY = j;
							Point vCurrentPoint = new Point(vPointX, vPointY);
							pointsAroundPathPoint.add(vCurrentPoint);
						}
						else { // we are not at the top or bottom edge perimeters
							if (k == pPathPoint.x - i) { // left hand side perimeter edge so grab one point
								vPointX = k;
								vPointY = j;
								Point vCurrentPoint = new Point(vPointX, vPointY);
								pointsAroundPathPoint.add(vCurrentPoint);
							}
							else if (k == pPathPoint.x + i) { // right hand side perimeter edge so grab one point
								vPointX = k;
								vPointY = j;
								Point vCurrentPoint = new Point(vPointX, vPointY);
								pointsAroundPathPoint.add(vCurrentPoint);
							}
						}
					}

				}

			}

		}

		/*
		Point d;
		for (int i = 0; i < pointsAroundPathPoint.size(); i++) {
			d = pointsAroundPathPoint.get(i);
			System.out.println("Current Position in pointsAroundPathPoint Vector is: " + i);
			System.out.println("(X,Y) Point is: " + d.x + ", " + d.y);
		}
		 */

		return pointsAroundPathPoint;
	}

	/**
	 * Checks the locus area for a point which lies on a given path. The points which lie within the locus area
	 * of a point is retrieved using the method {@link #getPointsWithinLocus(Point)}. For each set of points
	 * within this area, the corresponding object vectors are checked to verify whether any of these objects
	 * are located at this point. An object can be defined as a Stop, Traffic Light, Junction or Congested Area.
	 * 
	 * @param vCongArea
	 * @param vStop
	 * @param vTL
	 * @param pPathPoint
	 * 				the path point which lies on a path.
	 * @return a vector containing all the objects which are located within the locus area of a path point.
	 */
	/* NOTE: Problem with null pointer exceptions if i don't use the vectors as parameters */
	public static Vector<SchMovePointData> checkLocusOfPathPoint 
	(Vector<SchSimpleObjCongArea> vCongArea, Vector<SchSimpleObjStop> vStop, Vector<SchSimpleObjTL> vTL, Point pPathPoint) 
	{
		Vector<SchMovePointData> pathPointObjects = new Vector<SchMovePointData>();
		SchMovePointData vCurrentPathPointObject = new SchMovePointData();

		Vector<Point> pointsWithinLocus = getPointsWithinLocus(pPathPoint); // get all (x,y) points within the locus of the given path point
		Point vCurrentPointWithinLocus;
		SchSimpleObjCongArea vCurrentCongArea;
		SchSimpleObjTL vCurrentTL;
		SchSimpleObjStop vCurrentStop;

		if (doDebug) listStop();
		if (doDebug) listTL();
		if (doDebug) listCongArea();

		if (vCongArea.size() == 0 && vTL.size() == 0 && vStop.size() == 0){ // if all empty skip checks
			if (doDebug) System.out.println("The Congestion Area, Traffic Light and Stop Vectors are currently empty - no checks will be performed.");
		}
		else {
			/*
			if (vCongArea.size() == 0)
				System.out.println("The Congestion Area vector is currently empty - no CA checks will be performed.");
			if (vTL.size() == 0)
				System.out.println("The Traffic Light vector is currently empty - no TL checks will be performed.");
			if (vStop.size() == 0)
				System.out.println("The Stop vector is currently empty - no Stop checks will be performed.");
			 */

			for (int i = 0; i < pointsWithinLocus.size(); i++) { // for each point in locus check if any object exists at that point
				vCurrentPointWithinLocus = pointsWithinLocus.get(i);

				if (vCongArea.size() != 0) {
					for (int cong = 0; cong < vCongArea.size(); cong++) {
						vCurrentCongArea = vCongArea.get(cong);

						// if the current x,y point matches the start of a congested area (in the CA vector)
						if (vCurrentCongArea.getCAPointA().x == vCurrentPointWithinLocus.x &&
								vCurrentCongArea.getCAPointA().y == vCurrentPointWithinLocus.y) 
						{
							vCurrentPathPointObject = new SchMovePointData(vCurrentPointWithinLocus, SchEnumObjectType.CONGESTED_AREA_START, 
									vCurrentCongArea.getCAID(),vCurrentCongArea.getCADBID());
							pathPointObjects.add(vCurrentPathPointObject);
						}
						// if the current x,y point matches the end of a congested area (in the CA vector)
						else if (vCurrentCongArea.getCAPointB().x == vCurrentPointWithinLocus.x &&
								vCurrentCongArea.getCAPointB().y == vCurrentPointWithinLocus.y) 
						{
							vCurrentPathPointObject = new SchMovePointData(vCurrentPointWithinLocus, SchEnumObjectType.CONGESTED_AREA_END, 
									vCurrentCongArea.getCAID(),vCurrentCongArea.getCADBID());
							pathPointObjects.add(vCurrentPathPointObject);
						}
					}
				}

				if (vTL.size() != 0) {
					for (int tl = 0; tl < vTL.size(); tl++) {
						vCurrentTL = vTL.get(tl);

						// if the current x,y point matches a TL or Junction point
						if (vCurrentTL.getTLPoint().x == vCurrentPointWithinLocus.x &&
								vCurrentTL.getTLPoint().y == vCurrentPointWithinLocus.y) 
						{
							// if its a Junction point
							if (vCurrentTL.getIsJunction()) { 
								vCurrentPathPointObject = new SchMovePointData(vCurrentPointWithinLocus, SchEnumObjectType.JUNCTION, 
										vCurrentTL.getTLID(),vCurrentTL.getTLDBID());
								pathPointObjects.add(vCurrentPathPointObject);
							}
							// if its a TL point
							else {
								vCurrentPathPointObject = new SchMovePointData(vCurrentPointWithinLocus, SchEnumObjectType.TRAFFIC_LIGHT, 
										vCurrentTL.getTLID(),vCurrentTL.getTLDBID());
								pathPointObjects.add(vCurrentPathPointObject);
							}
						}
					}
				}

				if (vStop.size() != 0) {
					for (int stop = 0; stop < vStop.size(); stop++) {
						vCurrentStop = vStop.get(stop);

						// if the current x,y point matches a TL or Junction point
						if (vCurrentStop.getStopPoint().x == vCurrentPointWithinLocus.x &&
								vCurrentStop.getStopPoint().y == vCurrentPointWithinLocus.y) 
						{ 
							vCurrentPathPointObject = new SchMovePointData(vCurrentPointWithinLocus, SchEnumObjectType.STOP, 
									vCurrentStop.getStopID(),vCurrentStop.getStopDBID());
							pathPointObjects.add(vCurrentPathPointObject);
						}
					}
				}

			}
		}

		return pathPointObjects;
	}


	/**
	 * For testing output
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		// insert test data for congestion area
		Vector<SchSimpleObjCongArea> vCongArea = new Vector<SchSimpleObjCongArea>();
		SchSimpleObjCongArea vTestCongArea;
		Point CStart = new Point(6,10);
		Point CEnd = new Point (8,8);
		vTestCongArea = new SchSimpleObjCongArea(5, 12, CStart, CEnd);
		vCongArea.add(vTestCongArea);
		 */

		/*
		// insert test data for stops
		Vector<SchSimpleObjStop> vStop = new Vector<SchSimpleObjStop>();;
		SchSimpleObjStop vTestStop;
		Point vStop1 = new Point (7,10);
		vTestStop = new SchSimpleObjStop(2, 2, vStop1);
		vStop.add(vTestStop);

		Point vStop2 = new Point (10,3);
		vTestStop = new SchSimpleObjStop(4, 6, vStop2);
		vStop.add(vTestStop);

		Point vStop3 = new Point (15,6);
		vTestStop = new SchSimpleObjStop(5, 9, vStop3);
		vStop.add(vTestStop);
		 */

		/*
		// insert test data for lights/junctions
		Vector<SchSimpleObjTL> vTL = new Vector<SchSimpleObjTL>();;
		SchSimpleObjTL vTestTL;
		Point vTestTL1 = new Point (4,12);
		vTestTL = new SchSimpleObjTL(2, 2, vTestTL1, true);
		vTL.add(vTestTL);

		Point vTestTL1B = new Point (11,5);
		vTestTL = new SchSimpleObjTL(3, 4, vTestTL1B, true);
		vTL.add(vTestTL);

		Point vTestTL2 = new Point (7,13);
		vTestTL = new SchSimpleObjTL(1, 54, vTestTL2, false);
		vTL.add(vTestTL);

		Point vTestTL2B = new Point (8,8);
		vTestTL = new SchSimpleObjTL(2, 55, vTestTL2B, false);
		vTL.add(vTestTL);

		Point vTestTL3 = new Point (10,7);
		vTestTL = new SchSimpleObjTL(3, 105, vTestTL3, false);
		vTL.add(vTestTL);

		Point vTestTL3B = new Point (14,3);
		vTestTL = new SchSimpleObjTL(4, 106, vTestTL3B, false);
		vTL.add(vTestTL);
		 */

		listStop();
		listTL();
		listCongArea();

		Vector<SchMovePointData> vTestMoveData = new Vector<SchMovePointData>();
		Point vTestPointStart = new Point (2,13);
		Point vTestPointEnd = new Point (13,4);

		System.out.println("- START - Test Move Data Output -");
		vTestMoveData = getMovePointData(vTestPointStart, vTestPointEnd);
		debugPrintMoveData(vTestMoveData, true);
		System.out.println("- END - Test Move Data Output -");
		System.out.println("\n");

		//Point vTestPathPoint = new Point (13,3);
		//getPointsWithinLocus (vTestPathPoint);

	}

	/* Formula for y = mx + b added here for testing purposes only as it does not exist in any package right now 
	 * Made a number of changes to parameters and code as it did not work correctly for all cases
	 */
	public static int calculateY (Point pStartPoint, Point pEndPoint, int pPointX) {
		double vPointY = 0;

		//System.out.println("--- START - Y = MX + B Output ---");
		if (pStartPoint.y == pEndPoint.y)
			vPointY = calculateB(pStartPoint, pEndPoint); // y = b if horizontal line (Y's are the same)
		else { // vertical line case
			double m = calculateM(pStartPoint, pEndPoint);
			double b = calculateB(pStartPoint, pEndPoint);
			vPointY = m * pPointX + b;

			// Y = -0.8181818181818182 * X  + 14.636363636363637 for (2,13) to (13,4)
			//System.out.println("M: " + m);
			//System.out.println("B: " + b);
			//System.out.println("Y: " + vPointY);
		}

		// round the decimal result and convert to integer - done at the end to obtain a more accurate result
		vPointY = Math.round(vPointY);
		int vPointYInt = 0;
		vPointYInt = (int)vPointY;

		//System.out.println("--- END - Y = MX + B Output ---");
		//System.out.println("\n");

		return vPointYInt;
	}
	
	/* Formula for a straight line which calculates Y based on more accurate X coordinates given as a double */
	public static int calculateYSteep(Point pStartPoint, Point pEndPoint, double pPointX) {
		double vPointY = 0;

		//System.out.println("--- START - Y = MX + B Output ---");
		if (pStartPoint.y == pEndPoint.y)
			vPointY = calculateB(pStartPoint, pEndPoint); // y = b if horizontal line (Y's are the same)
		else { // vertical line case
			double m = calculateM(pStartPoint, pEndPoint);
			double b = calculateB(pStartPoint, pEndPoint);
			vPointY = m * pPointX + b;

			// Y = -0.8181818181818182 * X  + 14.636363636363637 for (2,13) to (13,4)
			//System.out.println("M: " + m);
			//System.out.println("B: " + b);
			//System.out.println("Y: " + vPointY);
		}

		// round the decimal result and convert to integer - done at the end to obtain a more accurate result
		vPointY = Math.round(vPointY);
		int vPointYInt = 0;
		vPointYInt = (int)vPointY;

		//System.out.println("--- END - Y = MX + B Output ---");
		//System.out.println("\n");

		return vPointYInt;
	}

	/**
	 * Calculates the m-value(Slope) of the equation (thus line) based on two sets of: a source coordinate and a destination coordinate.
	 * A set of (x,y) coordinates represents one unit of distance [this can be changed according to what we define the distance of each point as].
	 * The base mathematical formula for this calculation is m = (Y2-Y1/X2-X1).
	 *
	 * @param pSourcePointX
	 * 			the x coordinate of the starting point
	 * @param pSourcePointY
	 * 			the y coordinate of the starting point
	 * @param pDestPointX
	 * 			the x coordinate of the destination point
	 * @param pDestPointY
	 * 			the y coordinate of the destination point
	 * @return the m-value (Slope) between two given points as a double value.
	 */
	public static double calculateM(Point pStartPoint, Point pEndPoint) {
		/*  m = (Y2-Y1/X2-X1) */
		return ((pEndPoint.getY() - pStartPoint.getY())/(pEndPoint.getX() - pStartPoint.getX()));
	}

	/**
	 * Calculates the b-value(Y-Intercept) of the equation (thus line) based on two sets of: a source coordinate and a destination coordinate.
	 * A set of (x,y) coordinates represents one unit of distance [this can be changed according to what we define the distance of each point as].
	 * The base mathematical formula for this calculation is b = Y1 - mX1
	 * Hence:
	 * b = Y1 - ((Y2-Y1/X2-X1) * X1)
	 *
	 * @param pSourcePointX
	 * 			the x coordinate of the starting point
	 * @param pSourcePointY
	 * 			the y coordinate of the starting point
	 * @param pDestPointX
	 * 			the x coordinate of the destination point
	 * @param pDestPointY
	 * 			the y coordinate of the destination point
	 * @return the b-value(Y-Intercept)) between two given points as a double value.
	 */
	public static double calculateB (Point pStartPoint, Point pEndPoint) {
		/*  b = Y1 - ((Y2-Y1/X2-X1) * X1) */
		return (pStartPoint.getY() - (((pEndPoint.getY() - pStartPoint.getY())/(pEndPoint.getX() - pStartPoint.getX())) * pStartPoint.getX()));
	}


	/* ---- Self Test and Debug Entries BELOW ONLY ---- */

	/**
	 * Returns a SAMPLE List of MovePointData Entries
	 * (NOTE: Does not perform an actual check)
	 */
	public static Vector<SchMovePointData> debugAddEntries(){
		Vector<SchMovePointData> data = new Vector<SchMovePointData>();

		SchMovePointData d1 = new SchMovePointData(new Point(1,1), SchEnumObjectType.STOP, 1,1);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,5), SchEnumObjectType.JUNCTION, 2,2);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,8), SchEnumObjectType.STOP, 2,2);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,10), SchEnumObjectType.CONGESTED_AREA_START, 5,5);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,10), SchEnumObjectType.STOP, 4,6);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,25), SchEnumObjectType.STOP, 5,9);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,29), SchEnumObjectType.TRAFFIC_LIGHT, 1,54);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,35), SchEnumObjectType.CONGESTED_AREA_END, 5,12);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,46), SchEnumObjectType.TRAFFIC_LIGHT, 2,105);
		data.add(d1);

		d1 = new SchMovePointData(new Point(1,52), SchEnumObjectType.STOP, 6,13);
		data.add(d1);

		return data;
	}

	public static void debugPrintMoveData(Vector<SchMovePointData> pVect, boolean pShowNAVSplits){
		//System.out.println("--- START - Internal Debug Output ---");
		//System.out.println("Source: SchRouteChecker (STATIC)");

		SchMovePointData d;
		for (int i = 0;i<pVect.size();i++){
			d = pVect.get(i);

			if (pShowNAVSplits){
				//The request to navigator is split before this object
				if (d.mObjectType.getNAVSplitLocation()==SchEnumSplitLoc.SPLIT_BEFORE){
					if (doDebug) System.out.println("  Seq " + i + ": ---- Split to NAV ----");
				}
			}

			if (doDebug) System.out.println("  Seq " + i + ": At (" + d.getPoint().x + "," + d.getPoint().y + ") a " + d.getObjectTypeID(true) + " is located.");
			if (doDebug) System.out.println("      " + i + ": Database ID = " + d.getRefDBID() + ", Internal/Array ID = " + d.getRefID());

			if (pShowNAVSplits){
				//The request to navigator is split after this object
				if (d.mObjectType.getNAVSplitLocation()==SchEnumSplitLoc.SPLIT_AFTER){
					if (doDebug) System.out.println("  Seq " + i + ": ---- Split to NAV ----");
				}
			}
		}

		//System.out.println("---  END  - Internal Debug Output ---");
	}

	public static void listStop(){

		System.out.println("Listing of Defined Stop Objects");

		SchSimpleObjStop stop;
		for (int i = 0;i<vStop.size();i++){
			stop = vStop.get(i);
			System.out.println("ID = " + stop.getStopID() + ", DBID = " + stop.getStopDBID() + " at Position (" + stop.getStopPoint().x + "," + stop.getStopPoint().y + ")"); 
		}

		System.out.println("Count = " + vStop.size());
	}

	public static void listTL(){

		System.out.println("Listing of Defined TL Objects");

		SchSimpleObjTL tL;
		for (int i = 0;i<vTL.size();i++){
			tL = vTL.get(i);
			System.out.println("ID = " + tL.getTLID() + ", DBID = " + tL.getTLDBID() + " at Position (" + tL.getTLPoint().x + "," + tL.getTLPoint().y + "); is Junction = " + tL.getIsJunction()); 
		}

		System.out.println("Count = " + vTL.size());
	}

	public static void listCongArea(){

		System.out.println("Listing of Defined Congested Area Objects");

		SchSimpleObjCongArea ca;
		for (int i = 0;i<vCongArea.size();i++){
			ca = vCongArea.get(i);
			System.out.println("ID = " + ca.getCAID() + ", DBID = " + ca.getCADBID() + " at Position (" + ca.getCAPointA().x + "," + ca.getCAPointA().y + ")" + " to (" + ca.getCAPointB().x + "," + ca.getCAPointB().y + ")"); 
		}

		System.out.println("Count = " + vCongArea.size());
	}
	
	public static boolean areStaticObjectEmpty(){
		return vTL.isEmpty()&&vStop.isEmpty()&&vCongArea.isEmpty();
	}
}
