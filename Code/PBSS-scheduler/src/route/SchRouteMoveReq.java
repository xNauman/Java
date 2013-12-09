package route;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.Vector;

import routeCheck.SchMovePointData;

/**
 * Contains details relating to a move request
 * 
 * @author Ben
 *
 */
public class SchRouteMoveReq {

	private Queue<SchDoublePoint> mMoveList = new LinkedList<SchDoublePoint>();
	private SchDoublePoint mReqPoint = new SchDoublePoint();
	private double mMaxLengthToNav = 40;
	private SchMovePointData mMovePointData = null;

	public void basePoints(Point pStartPoint, Point pEndPoint){
		mReqPoint = new SchDoublePoint(pStartPoint, pEndPoint);
	}

	/*public void addObstruction(SchMovePointData pItem){
		this.mMoveList.add(pItem);
	}*/

	public void setPointStart(Point pStartPoint){
		mReqPoint.setStartPoint(pStartPoint);
	}

	public boolean setPointEnd(Point pEndPoint, SchMovePointData pMovePointData, boolean pDoInternalSplit){
		mReqPoint.setEndPoint(pEndPoint);
		this.mMovePointData = pMovePointData;

		if (pDoInternalSplit) {
			return this.performInternalSplit();
		}
		else {
			return true; //Since just setting an object
		}
	}

	/**
	 * Obtains the first Double Point from the list
	 * 
	 * @return
	 */
	public SchDoublePoint getPointFirst(){
		if (this.mMoveList.isEmpty())
			return null;

		if (!this.isReadyForSending(false))
			return null;

		return this.mMoveList.peek();
	}
	
	public SchMovePointData getMovePointData(){
		return this.mMovePointData;
	}
	
	public boolean removePoint(UUID pPointUUID){
		//System.out.println("SchRouteMoveReq:REMOVEPOINT - UUID to remove = " + pPointUUID.toString());
		if (this.mMoveList.isEmpty()) {
			//System.out.println("SchRouteMoveReq:REMOVEPOINT - Is Empty");
			return false;
		}
		
		if (this.mMoveList.peek().getPointUUID()==pPointUUID){
			this.mMoveList.poll();
			//System.out.println("SchRouteMoveReq:REMOVEPOINT - Removed, was first");
			return true;
		}
		
		Iterator<SchDoublePoint> itr = this.mMoveList.iterator();
		
		
		while (itr.hasNext()){

			SchDoublePoint p = (SchDoublePoint) itr.next();
			
			if (p.getPointUUID()==pPointUUID){
				this.mMoveList.remove(p);
				//System.out.println("SchRouteMoveReq:REMOVEPOINT - Removed, OTHER");
				return true;
			}
		}
		
		//System.out.println("SchRouteMoveReq:REMOVEPOINT - NOT FOUND!");
		return false;
	}
	

	public boolean isReadyForSending(boolean ifNotReadyDoInternalSplit){
		boolean bIsReady=false;
		bIsReady = mReqPoint.isComplete() && mMoveList.size()>0;

		if (!bIsReady&&ifNotReadyDoInternalSplit){
			performInternalSplit();
			bIsReady=true;
		}

		return bIsReady;
	}
	
	public boolean hasEndPoint(){
		return (this.mReqPoint.getEndPoint()==null);
	}

	public boolean performInternalSplit(){

		if (mReqPoint.isComplete()){
			if (mReqPoint.getDistance() > mMaxLengthToNav){
				//System.out.println("************* DO SPLIT: Will be " + Math.ceil(mReqPoint.getDistance()/this.mMaxLengthToNav) + " sub components");
				
				if (mReqPoint.isStraightLine()){
					System.out.println("SchRouteMoveReq:performInternalSplit - STRAIGHT LINE= YES");				
					Vector <Double> v;
					if (mReqPoint.isStraightLineHorizontal()){
						System.out.println("SchRouteMoveReq:performInternalSplit - STRAIGHT LINE, HORIZ");
						//Horizontal Line, Y are all the same
						v = straightLineSplit(mReqPoint.getStartPoint().x, mReqPoint.getEndPoint().x, mMaxLengthToNav);

						if (v==null)
							return false;

						for (int i = 0;i<v.size()-1;i++){
							this.mMoveList.add(new SchDoublePoint(new Point(v.get(i).intValue(),mReqPoint.getStartPoint().y),new Point(v.get(i+1).intValue(),mReqPoint.getStartPoint().y)));
						}
					}
					else {
						System.out.println("SchRouteMoveReq:performInternalSplit - STRAIGHT LINE, VERT");
						//Vertical Line, X are all the same
						v = straightLineSplit(mReqPoint.getStartPoint().y, mReqPoint.getEndPoint().y, mMaxLengthToNav);

						for (int i = 0;i<v.size()-1;i++){
							this.mMoveList.add(new SchDoublePoint(new Point(mReqPoint.getStartPoint().x, v.get(i).intValue()),new Point(mReqPoint.getStartPoint().x,v.get(i+1).intValue())));
						}
						
						if (v==null)
							return false;
					}
				}
				else {
					System.out.println("SchRouteMoveReq:performInternalSplit - NOT STRAIGHT LINE");
					/*
					 * CODE HERE TO SPLIT A NON-STRAIGHT LINE (for this purpose, a diagonal line is deemed as not straight)
					 */

					/* Need to split the line up into equal distances */
					Vector <Point> splitPoints = diagonalLineSplit(mReqPoint.getStartPoint(), mReqPoint.getEndPoint(), mMaxLengthToNav);
					for (int i = 0; i < splitPoints.size()-1; i++){
						Point startPoint = splitPoints.get(i);
						Point endPoint = splitPoints.get(i+1);
						this.mMoveList.add(new SchDoublePoint(startPoint, endPoint));
					}
				}

				return true;
			}
			else {
				this.mMoveList.add(mReqPoint);
				return true;
			}
		}
		else {
			return false;
		}
	}

	public void debugDumpQueue(){
		
		//Copy the existing queue, so its not destroyed	
		Queue <SchDoublePoint> copyMoveList = new LinkedList<SchDoublePoint> ();
		Iterator <SchDoublePoint> itr = this.mMoveList.iterator();
		
		while (itr.hasNext()){
			copyMoveList.add((SchDoublePoint) itr.next());
		}
		

		if (copyMoveList.size()>0){
			System.out.println("Queue List (size = " + copyMoveList.size() + ") [MAX LEN = " + mMaxLengthToNav + "]");

			while (copyMoveList.size()>0) {
				SchDoublePoint p = copyMoveList.poll();
				System.out.println("... START AT " + p.getStartPoint().toString() + " to " + p.getEndPoint().toString() + " (GUID = " + p.getPointUUID() + ")");
			}

		}
	}

	/**
	 * Used for straight lines only, given a start and ending point, will 
	 * provide a split of points based on the maximum length.
	 * 
	 * 
	 * For example, given, start = 11, end = 21, and a max length of 3, 
	 * a vector of: 11, 14, 17, 20 and 21 will be returned.
	 * 
	 * @param startVal Starting Value (single co-ordinate only)
	 * @param endValue Ending Value (single co-ordinate only)
	 * @param maxLength Maximum length for a single line segment
	 * @return Split points
	 */
	private Vector<Double> straightLineSplit(double pStartValue, double pEndValue, double maxLength){
		Vector<Double> v = new Vector<Double>();

		boolean bIsCountBack = false;

		if (pStartValue > pEndValue)
			bIsCountBack = true;
		else
			bIsCountBack = false;

		double lengthCountDown = pEndValue-pStartValue;

		if (!bIsCountBack){
			if (lengthCountDown<=maxLength){
				v.add(pStartValue);
				v.add(pEndValue);
			}
			else {

				v.add(pStartValue);

				boolean keepCount = true;
				while (keepCount) {				
					pStartValue += maxLength;

					if (pStartValue >= pEndValue){
						v.add(pEndValue);
						keepCount = false;
					}
					else {
						v.add(pStartValue);
					}
				}
			}
		}
		else
		{
			if (Math.abs(lengthCountDown)<=maxLength){
				v.add(pEndValue);
				v.add(pStartValue);
			}
			else {

				v.add(pStartValue);

				boolean keepCount = true;
				while (keepCount) {				
					pStartValue -= maxLength;

					if (pStartValue < pEndValue){
						v.add(pEndValue);
						keepCount = false;
					}
					else {
						v.add(pStartValue);
					}
				}
			}
		}
		
		return v;
	}
	
	public boolean isQueueEmpty(){
		return this.mMoveList.isEmpty();
	}
	
	
	/**
	 * Calculates the distance between two given points represented as a set of (x,y) coordinates.
	 * A set of (x,y) coordinates represents one unit of distance.
	 * 
	 * @param pStartPoint
	 * 			the coordinates of the starting point
	 * @param pEndPoint
	 * 			the coordinates of the destination point
	 * @return the units of distance between two given points as an integer value	
	 */
	/* NOTE: Added/Altered for purpose of testing the diagonal line split method, can be moved to the appropriate location after 
	 * 		 Not sure if the getDistance method is meant to be used for a diagonal line or is it? */
	public static int calculateDistance(Point pStartPoint, Point pEndPoint) {
		double vDistance = 0;
		/* Horizontal Case: Y coordinates are the same */
		if (pStartPoint.y == pEndPoint.y && pStartPoint.x != pEndPoint.x)
			vDistance = Math.abs(pStartPoint.x - pEndPoint.x);
		/* Vertical Case: X coordinates are the same */
		else if (pStartPoint.x == pEndPoint.x && pStartPoint.y != pEndPoint.y)
			vDistance = Math.abs(pStartPoint.y - pEndPoint.y);
		/* Diagonal Case: the (x,y) coordinates are different */
		else {
			int xDistance = pEndPoint.x - pStartPoint.x;
			int yDistance = pEndPoint.y - pStartPoint.y;
			/* Use the Pythagorean Theorem to calculate the diagonal distance */
			vDistance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
			//vDistance = Math.round(vDistance);
		}
		
		int vDistanceInt = 0;
		/* Convert double to integer */
		vDistanceInt = (int)vDistance;
		
		return vDistanceInt;
	}
	
	/**
	 * The method {@link #calculateDistance(Point, Point)} is used to calculate the distance of the given
	 * diagonal line. This is then used to obtain the number of split segments for the diagonal line.
	 * The method {@link #calculateSplitPointsYMXB(Point, Point, double)} is then used to obtain the
	 * start and ending points for each split segment in the diagonal line.
	 * 
	 * @param pStartPoint
	 * 				the start point of the diagonal line path.
	 * @param pEndPoint
	 * 				the ending point of the diagonal line path.
	 * @param maxLength 
	 * 				maximum length of each split segment of a single line.
	 * @return a vector containing all the points for each split segment for a given line.
	 */
	public static Vector<Point> diagonalLineSplit(Point pStartPoint, Point pEndPoint, double maxLength) {
		Vector<Point> tempSplitPoints = new Vector<Point>();
		int totalLineDistance = calculateDistance(pStartPoint, pEndPoint);
		System.out.println("totalLineDistance is: " + totalLineDistance);
		double noOfSplits = (totalLineDistance/maxLength); // need double to get a more accurate result
		System.out.println("noOfSplits is: " + noOfSplits);
		
		// each split is even except for the last (which is smaller) -- easy way but don't think it always works
		//tempSplitPoints = calculateSplitPointsOLD(pStartPoint, pEndPoint, noOfSplits);
		
		tempSplitPoints = calculateSplitPoints(pStartPoint, pEndPoint, noOfSplits);
		
		return tempSplitPoints;
	}
	
	/**
	 * Uses formula for straight line (y = mx + b) to calculate the corresponding y coordinates for each x coordinate.
	 * To obtain the x coordinates we divide the distance between the start and end x coordinates by the number of
	 * split segments required. For cases where we have a steep line these split segments are obtained using the
	 * method {@link #calculateSplitPtsSteepLine(Point, Point, double)}.
	 * 
	 * @param pStartPoint
	 * 				the start point of the diagonal line path.
	 * @param pEndPoint
	 * 				the end point of the diagonal line path.
	 * @param pNoOfSplits
	 * 				the total number of split segments which are needed for the diagonal line (not rounded)
	 * @return a vector containing all the split points for each split segment which lie between the start and end point
	 */
	public static Vector <Point> calculateSplitPoints(Point pStartPoint, Point pEndPoint, double pNoOfSplits) {
		Vector<Point> splitPoints = new Vector<Point>();
		// Value to add to get the x coordinate for each split point
		//double splitPointX = Math.abs(pStartPoint.x - pEndPoint.x) / (pNoOfSplits);
		
		//if (Math.abs(pStartPoint.x - pEndPoint.x) < pNoOfSplits) { // return empty vector if we can't split
		//	return splitPoints;
		//}
		
		//Added by Ben, fix for the endless loop?
		/*if (splitPointX <= 0)
			splitPointX = 1;*/
		
		
		if (Math.abs(pStartPoint.x - pEndPoint.x) < pNoOfSplits) { // line is too steep we need to work with double coordinates of X's
			splitPoints = calculateSplitPtsSteepLine(pStartPoint, pEndPoint, pNoOfSplits);
			return splitPoints;
		}
	
		int splitPointX = (int) (Math.abs(pStartPoint.x - pEndPoint.x) / (pNoOfSplits));
		System.out.println("splitPointX is: " + splitPointX);
		//double currentSplitX = pStartPoint.getX();
		//double currentSplitY = pStartPoint.getY();
		int currentSplitX = pStartPoint.x;
		int currentSplitY = pStartPoint.y;
		System.out.println("starting SplitX is: " + pStartPoint.x);
		System.out.println("starting SplitY is: " + pStartPoint.y);

		boolean exitLoop = true;
		if (pStartPoint.x < pEndPoint.x) { // if going --> direction
			//System.out.println("<--");
			while (exitLoop == true) {
				//int vCurrentSplitX = (int) currentSplitX;
				currentSplitY = routeCheck.SchRouteChecker.calculateY(pStartPoint, pEndPoint, currentSplitX);
				
				Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
				splitPoints.add(currentSplitPoint);
				
				currentSplitX = currentSplitX + splitPointX;
				// determine if we are at the last split segment
				if (currentSplitX >= pEndPoint.x) {
					System.out.println("currentSplitX is: " + currentSplitX);
					System.out.println("pEndPointX is: " + pEndPoint.x);
					exitLoop = false;
				}
			}

		}//331 and 158
		else { // if going <-- direction
			//System.out.println("-->");
			while (exitLoop == true) {
				
				currentSplitY = routeCheck.SchRouteChecker.calculateY(pStartPoint, pEndPoint, currentSplitX);
				//System.out.println("currentSplitY is: " + currentSplitY);
				Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
				splitPoints.add(currentSplitPoint);
				
				currentSplitX = currentSplitX - splitPointX;
				// determine if we are at the last split segment
				if (currentSplitX <= pEndPoint.x) {
					System.out.println("currentSplitX is: " + currentSplitX);
					System.out.println("pEndPointX is: " + pEndPoint.x);
					exitLoop = false;
				}
			}

		}
		splitPoints.add(pEndPoint); // add the initial end point to get the end point for the last split section 
		
		
		for (int i = 0; i < splitPoints.size()-1; i++) {
			System.out.println("-- Split Number -- " + (i+1));
			Point startPoint = splitPoints.get(i);
			System.out.println("	Split startPoint is: " + startPoint);
			Point endPoint = splitPoints.get(i+1);
			System.out.println("	Split endPoint is: " + endPoint);
			System.out.println("	Distance is: " + calculateDistance(startPoint, endPoint));
		}
	
		return splitPoints;
	}
	
	/**
	 * Uses formula for straight line (y = mx + b) to calculate the corresponding y coordinates for each x coordinate.
	 * To obtain the x coordinates we divide the distance between the start and end x coordinates by the number of
	 * split segments required. 
	 * 
	 * This method is required for steep lines where the X coordinates for each split segment need to be rounded down.
	 * This method also uses an altered formula for a straight line which requires an X coordinate as a double in order
	 * to calculate a more accurate position of Y for every X coordinate of each split segment.
	 * 
	 * @param pStartPoint
	 * 				the start point of the diagonal line path.
	 * @param pEndPoint
	 * 				the end point of the diagonal line path.
	 * @param pNoOfSplits
	 * 				the total number of split segments which are needed for the diagonal line (not rounded)
	 * @return a vector containing all the split points for each split segment which lie between the start and end point
	 */
	public static Vector <Point> calculateSplitPtsSteepLine(Point pStartPoint, Point pEndPoint, double pNoOfSplits) {
		Vector<Point> splitPointsSL = new Vector<Point>();
		Vector<Integer> splitPointsX = new Vector<Integer>();
		Vector<Double> splitPointsXDouble = new Vector<Double>();
		
		double splitPointX = Math.abs(pStartPoint.x - pEndPoint.x) / (pNoOfSplits);
		//System.out.println("splitPointX is: " + splitPointX);
		//System.out.println("pNoOfSplits is: " + pNoOfSplits);
		splitPointsX = roundDouble(splitPointX, pNoOfSplits);
		splitPointsXDouble = getDoublePointsX(splitPointX, pNoOfSplits);
		
		double initialSplitXDouble = pStartPoint.getX();
		double currentSplitXDouble = initialSplitXDouble;
		System.out.println("starting SplitXDouble (Steep Line) is: " + initialSplitXDouble);
		int initialSplitX = pStartPoint.x;
		int currentSplitX = pStartPoint.x;
		int currentSplitY = pStartPoint.y;
		System.out.println("starting SplitX (Steep Line) is: " + pStartPoint.x);
		System.out.println("starting SplitY (Steep Line) is: " + pStartPoint.y);
		splitPointsSL.add(pStartPoint); // add first point
		
		System.out.println("size of splitPointsX (Steep Line)is: " + splitPointsX.size());
		System.out.println("size of splitPointsXDouble (Steep Line)is: " + splitPointsXDouble.size());
		
		if (pStartPoint.x < pEndPoint.x) { // if going --> direction
			//System.out.println("<--");
				for (int i = 1; i < splitPointsX.size(); i++) {
					currentSplitXDouble = initialSplitXDouble + splitPointsXDouble.get(i);
					//System.out.println("currentSplitXDouble is: " + currentSplitXDouble);
					currentSplitX = initialSplitX + splitPointsX.get(i);
					//System.out.println("splitPointsX.get(i) is: " + splitPointsX.get(i));
					//System.out.println("currentSplitX is: " + currentSplitX);
					
					currentSplitY = routeCheck.SchRouteChecker.calculateYSteep(pStartPoint, pEndPoint, currentSplitXDouble);
					//System.out.println("currentSplitY is: " + currentSplitY);
					Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
					splitPointsSL.add(currentSplitPoint);
				}

		}
		else { // if going <-- direction
			//System.out.println("-->");

				for (int i = 1; i < splitPointsX.size(); i++) {
					currentSplitXDouble = initialSplitXDouble - splitPointsXDouble.get(i);
					//System.out.println("currentSplitXDouble is: " + currentSplitXDouble);
					currentSplitX = initialSplitX - splitPointsX.get(i);
					//System.out.println("splitPointsX.get(i) is: " + splitPointsX.get(i));
					//System.out.println("currentSplitX is: " + currentSplitX);
					
					currentSplitY = routeCheck.SchRouteChecker.calculateYSteep(pStartPoint, pEndPoint, currentSplitXDouble);
					//System.out.println("currentSplitY is: " + currentSplitY);
					Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
					splitPointsSL.add(currentSplitPoint);
				}

		}
		splitPointsSL.add(pEndPoint); // add the initial end point to get the end point for the last split section 
		
		
		for (int i = 0; i < splitPointsSL.size()-1; i++) {
			System.out.println("-- Split Number (Steep Line) -- " + (i+1));
			Point startPoint = splitPointsSL.get(i);
			System.out.println("	Split startPoint (Steep Line) is: " + startPoint);
			Point endPoint = splitPointsSL.get(i+1);
			System.out.println("	Split endPoint (Steep Line) is: " + endPoint);
			System.out.println("	Distance (Steep Line) is: " + calculateDistance(startPoint, endPoint));
		}
		
		return splitPointsSL;
	}
	
	/** 
	 * Given an X value as a double, calculate round this X value up if its is in the greater
	 * than X + 0.5 otherwise round it down. This method retrieves all the values of X for
	 * the split points in a steep diagonal line.
	 *  
	 * @param pPointX
	 * 			the distance between each X value for each split point in the steep line
	 * @param pNoOfSplits
	 * 			the total number of split segments which are needed for the diagonal line (not rounded)
	 * @return a vector containing the rounded X values for each split point in the steep diagonal line
	 */			
	public static Vector<Integer> roundDouble(double pPointX, double pNoOfSplits) {
		Vector<Integer> splitPointsX = new Vector<Integer>();
		
		int vRoundedPointX;
		int vNoOfSplits = (int) Math.ceil(pNoOfSplits);
		//System.out.println("vNoOfSplits is: " + vNoOfSplits);
		
		for (int i = 0; i < vNoOfSplits; i++) {
			double vMultipliedPointX = pPointX * i;
			//System.out.println("vMultipliedPointX is: " + vMultipliedPointX);
			
			if ((vMultipliedPointX % 1) < 0.5) { // round down
				vRoundedPointX = (int) Math.floor(vMultipliedPointX);
			}
			else { // round up
				vRoundedPointX= (int) Math.ceil(vMultipliedPointX);	
			}
			
			splitPointsX.add(vRoundedPointX);
		}
		
		//for (int i = 0; i < splitPointsX.size()-1; i++) {
		//	System.out.println("-- Split X Number -- " + (i));
		//	System.out.println("	Split X Point is: " + splitPointsX.get(i));
		//}
		
		return splitPointsX;
	}
	
	/**
	 * Given an X value as a double, we calculate all the real values of X given as a double for all
	 * the split points in a steep diagonal line. These points are required to perform the line
	 * formula calculation in order to get the corresponding Y values for each split point.
	 *  
	 * @param pPointX
	 * 			the distance between each X value for each split point in the steep line
	 * @param pNoOfSplits
	 * 			the total number of split segments which are needed for the diagonal line (not rounded)
	 * @return a vector containing the real X values for each split point in the steep diagonal line
	 */
	public static Vector<Double> getDoublePointsX(double pPointX, double pNoOfSplits) {
		Vector<Double> splitPointsXDouble = new Vector<Double>();
		
		double vMultipliedPointX;
		System.out.println("pPointX is: " + pPointX);
		int vNoOfSplits = (int) Math.ceil(pNoOfSplits);
		//System.out.println("vNoOfSplits is: " + vNoOfSplits);
		
		for (int i = 0; i < vNoOfSplits; i++) {
			vMultipliedPointX = pPointX * (double)i;
			splitPointsXDouble.add(vMultipliedPointX);
		}
		
		//for (int i = 0; i < splitPointsXDouble.size()-1; i++) {
		//	System.out.println("-- Split X Number (in double) -- " + (i));
		//	System.out.println("	Split X Point in double is: " + splitPointsXDouble.get(i));
		//}
		
		return splitPointsXDouble;
	}
	
	
	public static void main(String[] args) {
		/* Non-steep lines */
		//diagonalLineSplit(new Point(15, 10), new Point(154, 231), 38);
		//diagonalLineSplit(new Point(154, 231), new Point(15, 10), 38);
		//diagonalLineSplit(new Point(15, 10), new Point(711, 211), 38);
		//diagonalLineSplit(new Point(154, 231), new Point(637, 285), 38);
		//diagonalLineSplit(new Point(331, 162), new Point(158, 52), 15);
		//diagonalLineSplit(new Point(158, 52), new Point(331, 162), 15);
		
		//Vector<Point> vp = calculateSplitPoints(new Point(5,10), new Point(9,10), 5);
		
		/* Steep lines */
		diagonalLineSplit(new Point(637,285), new Point(649,156), 5);
		//diagonalLineSplit(new Point(1111,531), new Point(1115,156), 17);
		//diagonalLineSplit(new Point(636,680), new Point(626,156), 22);
		//diagonalLineSplit(new Point(513,147), new Point(530,1683), 35);

		//for (int i = 0; i < 13; i++) {
		//	int result = roundDouble(0.07751937984496124, i);
		//	System.out.println("	Result for i = " + i + " is: " + result);
		//}
		
		//roundDouble(0.07751937984496124, 13);
	}
	
	
	
	
	/* Old code 
	public static Vector <Point> calculateSplitPtsSteepLine(Point pStartPoint, Point pEndPoint, double pNoOfSplits) {
		Vector<Point> splitPointsSL = new Vector<Point>();
		Vector<Integer> splitPointsX = new Vector<Integer>();
		// Value to add to get the x coordinate in decimals for each split point
		//double splitPointX = Math.abs(pStartPoint.x - pEndPoint.x) / (pNoOfSplits);
		//System.out.println("splitPointX is: " + splitPointX);
		//double currentSplitX = pStartPoint.getX();
		//double currentSplitY = pStartPoint.getY();
		//System.out.println("currentSplitX is: " + pStartPoint.x);
		//System.out.println("currentSplitY is: " + pStartPoint.y);

		double splitPointX = Math.abs(pStartPoint.x - pEndPoint.x) / (pNoOfSplits);
		splitPointsX = roundDouble(splitPointX, pNoOfSplits);
		
		int currentSplitX = pStartPoint.x;
		int currentSplitY = pStartPoint.y;
		System.out.println("currentSplitX is: " + pStartPoint.x);
		System.out.println("currentSplitY is: " + pStartPoint.y);
		
		boolean exitLoop = true;
		if (pStartPoint.x < pEndPoint.x) { // if going --> direction
			//System.out.println("<--");
			while (exitLoop == true) {
				
				for (int i = 0; i < splitPointsX.size(); i++) {
					currentSplitY = routeCheck.SchRouteChecker.calculateY(pStartPoint, pEndPoint, currentSplitX);
					Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
					splitPointsSL.add(currentSplitPoint);
					
					currentSplitX = currentSplitX + splitPointsX.get(i);
					// determine if we are at the last split segment
					if (currentSplitX >= pEndPoint.x) {
						System.out.println("currentSplitX is: " + currentSplitX);
						System.out.println("pEndPointX is: " + pEndPoint.x);
						exitLoop = false;
					}
				}
				
			}

		}//331 and 158
		else { // if going <-- direction
			//System.out.println("-->");

				for (int i = 0; i < splitPointsX.size(); i++) {
					currentSplitY = routeCheck.SchRouteChecker.calculateY(pStartPoint, pEndPoint, currentSplitX);
					//System.out.println("currentSplitY is: " + currentSplitY);
					Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
					splitPointsSL.add(currentSplitPoint);
					
					currentSplitX = currentSplitX - splitPointsX.get(i);
				}

		}
		splitPointsSL.add(pEndPoint); // add the initial end point to get the end point for the last split section 
		
		
		for (int i = 0; i < splitPointsSL.size()-1; i++) {
			System.out.println("-- Split Number -- " + (i+1));
			Point startPoint = splitPointsSL.get(i);
			System.out.println("	Split startPoint is: " + startPoint);
			Point endPoint = splitPointsSL.get(i+1);
			System.out.println("	Split endPoint is: " + endPoint);
			System.out.println("	Distance is: " + calculateDistance(startPoint, endPoint));
		}
		
		return splitPointsSL;
	}
	
	
	// Similar to method above but uses a different approach. May not be used
	public static Vector <Point> calculateSplitPointsOLD(Point pStartPoint, Point pEndPoint, double pNoOfSplits) {
		Vector<Point> splitPoints = new Vector<Point>();
		// x,y values to add to each consecutive split point
		int splitPointX = (int) (Math.abs(pStartPoint.x - pEndPoint.x) / pNoOfSplits);
		int splitPointY = (int) (Math.abs(pStartPoint.y - pEndPoint.y) / pNoOfSplits);
		//int splitPointX = (pStartPoint.x + pEndPoint.x) / pNoOfSplits;
		//int splitPointY = (pStartPoint.x + pEndPoint.x) / pNoOfSplits;
		System.out.println("splitPointX is: " + splitPointX);
		System.out.println("splitPointY is: " + splitPointY);
		// The initial start point is the start point for the first split section
		int currentSplitX = pStartPoint.x;
		int currentSplitY = pStartPoint.y;

		for (int i = 0; i < pNoOfSplits; i++) {
			Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
			splitPoints.add(currentSplitPoint);
			// update x,y values to get next split point
			if (pStartPoint.x < pEndPoint.x) { // if going --> direction
				currentSplitX = currentSplitX + splitPointX;
				currentSplitY = currentSplitY + splitPointY;
			}
			else {
				currentSplitX = currentSplitX - splitPointX;
				currentSplitY = currentSplitY - splitPointY;
			}

		}
		splitPoints.add(pEndPoint); // add the initial end point to get the end point for the last split section
		
		for (int i = 0; i < splitPoints.size(); i++) {
			System.out.println("Split Point is: " + splitPoints.get(i));
		}
		
		return splitPoints;
	}

	// Probably won't be needed
	public static Point calculateMidPoint(Point pStartPoint, Point pEndPoint) {
		int midPointX = (pStartPoint.x + pEndPoint.x) / 2;
		int midPointY = (pStartPoint.y + pEndPoint.y) / 2;
		Point midPoint = new Point(midPointX, midPointY);
		System.out.println("MidPoint is: " + midPoint);
		
		return midPoint;
	}

		/* // problem does not always work for the last few split segments -- from calculateSplit working
		for (int i = 0; i < pNoOfSplits; i++) {
			currentSplitY = routeCheck.SchRouteChecker.calculateY(pStartPoint, pEndPoint, currentSplitX);
			Point currentSplitPoint = new Point(currentSplitX, currentSplitY);
			splitPoints.add(currentSplitPoint);
			
			if (pStartPoint.x < pEndPoint.x) { // if going --> direction
				currentSplitX = currentSplitX + splitPointX;
			}
			else {
				currentSplitX = currentSplitX - splitPointX;
			}
		}
		splitPoints.add(pEndPoint); // add the initial end point to get the end point for the last split segment
		*/

	
}
