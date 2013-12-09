package navigation;

import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;

import config.SysConfig;

import main.InputTHD;
import main.RMIControlTHD;

import datastore.BusMovementStore;
import datastore.TrafficLightStore;

import rawobjects.Bus;
import rawobjects.Bus_Type;
import rawobjects.NavRequest;
import rawobjects.TrafficLight;
import rawobjectsPriv.BusPath;
import utils.Clock;
import utils.LineFormula;
import utils.TimeControlTHD;

public class BusChecking {

	private Bus mBus;
	private NavRequest NavRequest;
	private Vector<Point> vBusPoints;
	

		
	
	public BusChecking(NavRequest pNavRequest)
	{
//		this.NavRequest = pNavRequest;
		this.mBus = pNavRequest.getbBus();
		this.vBusPoints = new Vector<Point>();
		this.NavRequest = pNavRequest;
	}
	
	public boolean OkToMove()
	{
		//get the bus plot 
		PlotXYForBusPath();
		
		boolean bOkToMove = true;
		
		
		int iPlusOrMinusPixelCount = config.SysConfig.getCheckingRangeForBuses(); // this is a constant that will check if a light exists within plus or minus of this value. TODO set a location for constants to be stored
		
		//create a BusPath object for this request just in case its approved
		BusPath oRequestBusPath = new BusPath(NavRequest,vBusPoints);

		
		int iBusX;
		int iBusY; //variables to hold bus positions
		
		//lets lock the bus movement store
		BusMovementStore.lTableLock.lock();
		
		/*
		 * Break down of loops is as follows
		 * Loop for each point in the path of this current bus
		 * 		loop to get each other bus in the bus movement store
		 * 			loop to run over each point in each bus found and compair to the current point in loop 1
		 * 
		 * This is messy, however is currently based on time lost during testing, this is the best and safest method
		 */
		
		
		Iterator<Point> itrBusPoints = vBusPoints.iterator();
		while(itrBusPoints.hasNext() && bOkToMove != false)
		{
			Point oCurrentPoint = itrBusPoints.next();
			
			iBusX = (int) oCurrentPoint.getX();
			iBusY = (int) oCurrentPoint.getY();
			
			//loop to run over all the Nav Requests in the BusMoveMentSTore vector
			Iterator<BusPath> itrBuses = BusMovementStore.vBusPositionVector.iterator();
			while(itrBuses.hasNext() && bOkToMove != false)
			{
				//get the bus path object
				BusPath oBusPathFromDataStore = itrBuses.next();
				
				
				//get all the points for that bus
				Vector<Point> oBusPath = oBusPathFromDataStore.getBusPoints();
				Iterator<Point> itrExistingBusPoints = oBusPath.iterator();
				
				//check that this bus is still moving on the map
				//if its not, remove it from the vector and make sure the loop doesn't run
				boolean vRequestTimeEnded = false;
//				if(oBusPathFromDataStore.getNavRequest().getiMovementEndTime() < Clock.getSimTime())
//				{
//					itrBuses.remove();
//					vRequestTimeEnded = true; 
//				}
				
				//loop to run for each point in the bus object
				while(itrExistingBusPoints.hasNext() && bOkToMove != false && vRequestTimeEnded != true)
				{
					Point oCurrentExisitingPoint = itrExistingBusPoints.next();
					
					//Check if we have a match! 
					//check if a bus exists within the radius of the current point 
					
					if( (Math.abs(oCurrentExisitingPoint.x - iBusX ) <= iPlusOrMinusPixelCount ) 
							&&
							(Math.abs(oCurrentExisitingPoint.y - iBusY ) <= iPlusOrMinusPixelCount ) )
					{
						//if this fires! we have a problem! So kill it!
//						System.out.println("--------------------------------");
//						System.out.println("--------------------------------");
//						System.out.println("--------------------------------");
//						System.out.println("(Math.abs(oCurrentExisitingPoint.x - iBusX ) --------" +(Math.abs(oCurrentExisitingPoint.x - iBusX )));
//						System.out.println("(Math.abs(oCurrentExisitingPoint.y - iBusY ) --------" +(Math.abs(oCurrentExisitingPoint.y - iBusY )));
//						System.out.println("iPlusOrMinusPixelCount: "+iPlusOrMinusPixelCount );
//						System.out.println("oCurrentExisitingPoint.x: "+oCurrentExisitingPoint.x);
//						System.out.println("iBusX: "+iBusX);
//						System.out.println("oCurrentExisitingPoint.y: "+oCurrentExisitingPoint.y);
//						System.out.println("iBusY: "+iBusY);
//						System.out.println("--------------------------------");
//						System.out.println("--------------------------------");
//						System.out.println("--------------------------------");
						
						bOkToMove = false;
						
					}
					

					
					
//					if( (Math.abs(oBusFromDataStore.getCurrentPointX() - iBusX) <= iPlusOrMinusPixelCount) 
//							&& (Math.abs(oBusFromDataStore.getCurrentPointX() - iBusY) <= iPlusOrMinusPixelCount) )
//					{
//						//kill the loop and return that light ID value
//						bOkToMove = false;
//					}
				
					
				}
				
				if( (oBusPath.lastElement().x == vBusPoints.firstElement().x 
						&& oBusPath.lastElement().y == vBusPoints.firstElement().y) 
						|| oBusPathFromDataStore.getNavRequest().getbBus().getBusID() == mBus.getBusID() )
				{
					bOkToMove = true;
				}

			

			}
			
		}
		
		//if we get to here and the result is true
		//we'll need to add this to the BusMoveMentStoreVector
		if(bOkToMove)
		{
			
			
			Iterator<BusPath> itrBusStore = BusMovementStore.vBusPositionVector.iterator();
			while(itrBusStore.hasNext())
			{
				BusPath BusPath = itrBusStore.next();
				Bus gotBus = BusPath.getNavRequest().getbBus();
				if(gotBus.getBusID() == mBus.getBusID())
				{
					itrBusStore.remove();
					
				}
				
			}
			
			
			
			BusMovementStore.vBusPositionVector.add(oRequestBusPath);
		}
		
		
		
		
		//lets unlock the bus movement store
		BusMovementStore.lTableLock.unlock();
		
		if(SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Bus Request Status: "+bOkToMove);
			
		return bOkToMove;
		
		

	}
	
	
	
	//plot a path for the XY for the bus path
	public void PlotXYForBusPath()
	{
		LineFormula BusFormula = new LineFormula(mBus.getCurrentPointX(),mBus.getCurrentPointY(),
				mBus.getMoveToPointX(),mBus.getMoveToPointY());
		
		
		// TODO KEN: changed iLineSlope from int to double to reflect lineformula.java change
		double iLineSlope = BusFormula.getiLineSlopeM();
		boolean bPlus = false;
				
		//check if we are moving right to left
		if(mBus.getCurrentPointX() > mBus.getMoveToPointX())
		{
			bPlus = false;
			PlotXYForPathWithASlope(BusFormula, bPlus);
		}
		
		//check if we are moving left to right
		else if(mBus.getCurrentPointX() < mBus.getMoveToPointX())
		{
			bPlus = true;
			PlotXYForPathWithASlope(BusFormula, bPlus);
		}
		
		//if this is the case, we have a virtual straight line and we'll need to increase Y
		// that could get messy so lets move it to its own function
		else
		{
			PlotXYForPathWithZeroSlope(BusFormula);
		}


	}

	
	private void PlotXYForPathWithASlope(LineFormula BusFormula, boolean bPlus)
	{
		
		int iCurrentXValue = mBus.getCurrentPointX();
		boolean vLoopRun = true;
		
		
		while (vLoopRun)
		{
			
			Point BusPoint = new Point(iCurrentXValue, BusFormula.getYValue(iCurrentXValue));
			vBusPoints.add(BusPoint);
					
			
			if(bPlus)
			{
				iCurrentXValue++;
				if(iCurrentXValue > mBus.getMoveToPointX())
				{
					vLoopRun = false;
				}
				
				
			}
			if(!bPlus)
			{
				iCurrentXValue--;
				if(iCurrentXValue < mBus.getMoveToPointX())
				{
					vLoopRun = false;
				}
				
			}
			
		}
		
		/*
		
		while (BusFormula.checkXIsInRange(iCurrentXValue))
		{
			
			Point BusPoint = new Point(iCurrentXValue, BusFormula.getYValue(iCurrentXValue));
			vBusPoints.add(BusPoint);
					
			
			if(bPlus)
			{
				iCurrentXValue++;
			}
			if(!bPlus)
			{
				iCurrentXValue--;
			}
			
		}
		*/
		
		
	}
	
	private void PlotXYForPathWithZeroSlope(LineFormula BusFormula) {
		
		int iCurrentYValue = mBus.getCurrentPointY();
		
		//if a current is less than the move to 
		if(mBus.getCurrentPointY() < mBus.getMoveToPointY())
		{
			while(iCurrentYValue <= mBus.getMoveToPointY())
			{		
				Point BusPoint = new Point(mBus.getCurrentPointX(), iCurrentYValue);
				vBusPoints.add(BusPoint);
				iCurrentYValue++;	
			}
		}
		
		//if current is more than move to
		else if(mBus.getCurrentPointY() > mBus.getMoveToPointY())
		{
			while(iCurrentYValue >= mBus.getMoveToPointY())
			{
				Point BusPoint = new Point(mBus.getCurrentPointX(), iCurrentYValue);
				vBusPoints.add(BusPoint);
				iCurrentYValue--;
			}
		}		
		
	}
	
	
	
	
	
	
	
	
//	public static void main(String[] args){
//		
//		//setup a small error range for testing
//		config.SysConfig.setCheckingRangeForBuses(2);
//		
//		
//		Bus_Type oBusType = new Bus_Type();
//		
//		//create a bus
//		//			   id,route, , dfload, c X, c y, M x, M y, m req state,move time stamp	
//		Bus oBus = new Bus(2, 1, oBusType, 1, 363, 550, 403, 550 ,1,  50);
//		
//		//create a nav request
//		NavRequest oNavRequest = new NavRequest(0,500000, oBus);
//		//create bus checking
//		BusChecking oBusCheck = new BusChecking(oNavRequest);
//		
//		//work out if we are ok to move
//		boolean okToMove;
//		okToMove = oBusCheck.OkToMove(); 
//		
//		Vector vBalh = datastore.BusMovementStore.vBusPositionVector;
//		//create a bus
//			//		   id,route, , dfload, c X, c y, M x, M y, m req state,move time stamp	
//		oBus = new Bus(3, 1, oBusType, 1, 551, 418, 591, 418 ,1,  50);
//		
//		//create a nav request
//		oNavRequest = new NavRequest(0,500000, oBus);
//		//create bus checking
//		oBusCheck = new BusChecking(oNavRequest);
//		
//		//work out if we are ok to move
//		okToMove = oBusCheck.OkToMove(); 
//		
//		vBalh = datastore.BusMovementStore.vBusPositionVector;
//		okToMove = false;
//		
//	}
	
	

}
