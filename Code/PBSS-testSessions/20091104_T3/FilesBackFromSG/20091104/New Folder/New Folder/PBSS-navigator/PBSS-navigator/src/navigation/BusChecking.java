package navigation;

import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;

import datastore.BusMovementStore;
import datastore.TrafficLightStore;

import rawobjects.Bus;
import rawobjects.NavRequest;
import rawobjects.TrafficLight;
import utils.LineFormula;

public class BusChecking {

	private Bus Bus;
	private NavRequest NavRequest;
	private Vector<Point> vBusPoints;
	
/**
 * Methods author  Raja Noman.
 * @param pSourceBus
 * @param pListToCheck
 * @return
 */
	public boolean BusClashCheck(Bus pSourceBus, Vector<Bus> pListToCheck){

		Iterator<Bus> vItr = pListToCheck.iterator();
		
		Bus vTempBusObj = new Bus();
		
		while(vItr.hasNext())
		{
			vTempBusObj = vItr.next();
			//check if we have same bus to check collosion with;
			if(pSourceBus.getBusID() != vTempBusObj.getBusID())

				//now check for each bus.
			if(pSourceBus.getMoveToPointX()== vTempBusObj.getMoveToPointX()
					
					|| pSourceBus.getMoveToPointY()== vTempBusObj.getMoveToPointY())
			{
				
				return true;
				
			}
		}
		return false;
	}
		
	
	public BusChecking(Bus pBus)
	{
//		this.NavRequest = pNavRequest;
		this.Bus = pBus;
		vBusPoints = new Vector<Point>();
	}
	
	public boolean OkToMove()
	{
		
		PlotXYForBusPath();
		
		boolean bOkToMove = true;
		
		
		int iPlusOrMinusPixelCount = 1; // this is a constant that will check if a light exists within plus or minus of this value. TODO set a location for constants to be stored
		

		
		int iBusX;
		int iBusY; //variables to hold bus positions
		
		
		/* Now work out if there are any lights nearby */

		
		
		Iterator<Point> itrBusPoints = vBusPoints.iterator();
		
		while(itrBusPoints.hasNext() && bOkToMove != false)
		{
			Point oCurrentPoint = itrBusPoints.next();
			
			iBusX = (int) oCurrentPoint.getX();
			iBusY = (int) oCurrentPoint.getY();
			
			//loop to run over all the buses in the bus table
			Iterator<Bus> itrBuses = BusMovementStore.vBusPositionVector.iterator();
			while(itrBuses.hasNext() && bOkToMove != false)
			{
				//get the object
				Bus oBusFromDataStore = itrBuses.next();
				
	
				//check if a bus exists within the radius of the current point 
				if( (Math.abs(oBusFromDataStore.getCurrentPointX() - iBusX) <= iPlusOrMinusPixelCount) 
						&& (Math.abs(oBusFromDataStore.getCurrentPointX() - iBusY) <= iPlusOrMinusPixelCount) )
				{
					//kill the loop and return that light ID value
					bOkToMove = false;
				}
			}
			
		}
		
		return bOkToMove;
		
		

	}
	
	
	
	//plot a path for the XY for the bus path
	public void PlotXYForBusPath()
	{
		LineFormula BusFormula = new LineFormula(Bus.getCurrentPointX(),Bus.getCurrentPointY(),
				Bus.getMoveToPointX(),Bus.getMoveToPointY());
		
		
		// TODO KEN: changed iLineSlope from int to double to reflect lineformula.java change
		double iLineSlope = BusFormula.getiLineSlopeM();
		boolean bPlus = false;
				
		//if a negative slope
		if(iLineSlope < 0)
		{
			bPlus = false;
			PlotXYForPathWithASlope(BusFormula, bPlus);
		}
		
		//if its a positive slope
		else if(iLineSlope > 0)
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
		
		int iCurrentXValue = Bus.getCurrentPointX();
		
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
	}
	
	private void PlotXYForPathWithZeroSlope(LineFormula BusFormula) {
		
		int iCurrentYValue = Bus.getCurrentPointY();
		
		//if a current is less than the move to 
		if(Bus.getCurrentPointY() < Bus.getMoveToPointY())
		{
			while(iCurrentYValue <= Bus.getMoveToPointY())
			{		
				Point BusPoint = new Point(Bus.getCurrentPointX(), iCurrentYValue);
				vBusPoints.add(BusPoint);
				iCurrentYValue++;	
			}
		}
		
		//if current is more than move to
		else if(Bus.getCurrentPointY() > Bus.getMoveToPointY())
		{
			while(iCurrentYValue >= Bus.getMoveToPointY())
			{
				Point BusPoint = new Point(Bus.getCurrentPointX(), iCurrentYValue);
				vBusPoints.add(BusPoint);
				iCurrentYValue--;
			}
		}		
		
	}

}
