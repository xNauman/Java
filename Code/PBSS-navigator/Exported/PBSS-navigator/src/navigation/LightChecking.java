package navigation;

import java.awt.Point;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;

import config.SysConfig;

import rawobjects.Bus;
import rawobjects.TrafficLight;
import utils.Clock;
import utils.TrafficLightAngle;
import datastore.TrafficLightStore;

public class LightChecking {
	
	/**
	 * Function that works out if the bus is near lights 
	 * (this nearness is set by a constant and the method will then workout if there are any lights within that radius)
	 * 
	 * 
	 * @param pointx - integer of the X value of the bus 
	 * @param pointy - integer the Y value of the bus
	 * @return iRreturnValue which is an integer of the light ID - if no lights are found a negative 1 will be returned (-1)
	 */
	public int AreWeAtLights(int pointx, int pointy)
	{
		
		boolean bAreWeAtLights = false;
		int iPlusOrMinusPixelCount = 2; // this is a constant that will check if a light exists within plus or minus of this value. TODO set a location for constants to be stored
		
		int iReturnValue = -1; 
		
		int iLightX;
		int iLightY; //variables to hold light values
		
		/* Now work out if there are any lights nearby */
		
		//loop to run over all the lights in a lights table
		Iterator<TrafficLight> itrTrafficLights = TrafficLightStore.vTrafficLightStore.iterator();
		while(itrTrafficLights.hasNext() && bAreWeAtLights != true)
		{
			//get the object
			TrafficLight oTrafficLight = itrTrafficLights.next();
			
			iLightX = oTrafficLight.getTrafficLightLocationX();
			iLightY = oTrafficLight.getTrafficLightLocationY();

			
			//check if a light exists within the radius given by the iPlusOrMinusPixelCount value
			if( (Math.abs(pointx-iLightX) <= iPlusOrMinusPixelCount) 
					&& (Math.abs(pointy-iLightY) <= iPlusOrMinusPixelCount) )
			{
				//kill the loop and return that light ID value
				bAreWeAtLights = true;
				iReturnValue = oTrafficLight.getTrafficLightId();
			}
		}
		
		
		return iReturnValue;
		
	}
	
	/**
	 * Method to calculate if the lights nearby are green or not and if its is going to stay green for long enough
	 * NOTE: this method uses a lot of shortcircuit  exit expressions, this is in an attempt to make the code run as fast as possible
	 * if we discovered early that we can't continue (ie light is red) we are going to use shortcircuit evaluation to escape the method. 
	 * 
	 * @param pLightID - integer that is the ID of the traffic light we are at
	 * @param pDirection - integer with the direction that we will be facing 
	 * 
	 * @return true if the bus can move through the light - false if we cannot
	 */
	public boolean AreLightsGreen(int pLightID,Bus pBus)
	{
		//TEMP object as we don't have database - would want to get this object from the DB.		
		/*
		 * VERY TEMP this is just for testing of the method itself, this will need to be replaced by code the pulls the light info from the DB
		 * 
		 */
		
		TrafficLight oTrafficLight;
		//oTrafficLight = TrafficLightStore.getLight(pLightID);
		
		
		
		try
		{
			//setup the RMI connect object
			rmi_interfaces.DBServer db = new rmi_engine.NAVClientImplDB().getSrvObject();
			
			//call sch and tell it the news
			oTrafficLight = db.getTrafficLight(pLightID);
			
					
		}
		//catch any exceptions as I don't want any RMI or network issue ot break my code
		//I  will just return a false which prevents the bus movement anyway
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
			
		//get the angle of the bus on the road
		double vAngle = TrafficLightAngle.calculateRoadAngle(new Point (pBus.getMoveToPointX(), pBus.getMoveToPointY()), new Point (pBus.getCurrentPointX(), pBus.getCurrentPointY()));
		
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("LightChecking.java: vangle is: "+vAngle);
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("LightChecking.java: FROM database(angle): "+oTrafficLight.getTrafficLightAngle().toString()+" Vector Size: "+ +oTrafficLight.getTrafficLightAngle().size());
		
		
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Bus Move to Pos: "+pBus.getMoveToPointX() +" "+ pBus.getMoveToPointY());
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Bus Current Pos: "+pBus.getCurrentPointX() +" "+ pBus.getCurrentPointY());
		if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("Traffic Light pos: "+oTrafficLight.getTrafficLightLocationX() +" "+oTrafficLight.getTrafficLightLocationY());
		
		//find the light direction that matches for the lgiht and the bus
		int vLightDirection = TrafficLightAngle.findTLightAtAngle(vAngle, oTrafficLight);
		

		int vBufferTimeConstant = 2; //TODO lightchecking:  inverse percentage that we will consider the light still red for..
		
		//if the light direction the bus is facing isn't the current light direction 
		//return false right away
		if(vLightDirection != oTrafficLight.getCurrDirection())
		{
			System.out.println("DEBUG: Failed Direction check: "+oTrafficLight.getCurrDirection());
			return false;
		}
		
				
		System.out.println("DEBUG: Passed Direction check");
		
		System.out.println("DEBUG TIME@lightCheck: "+Clock.getSimTime());
		//NOW we'll check to see if there is remaining time on the change. 
		int vTimeRemainingOnLight = (int) ((oTrafficLight.getLastChange()+oTrafficLight.getChangeFreq())-utils.Clock.getSimTime()); 
		
		
		//this was built for making sure bus's don't go through on the red if its close in time
		//but seems time is messed up
//		//if we don't have time left on the light lets throw a false
//		if(vTimeRemainingOnLight < 0)
//		{
//			return false;
//		}
		
		//if all the other tests pass, lets return true ! :) 
		
		return true;
		
	
		//if we get to here in the code, there must be time left to go on the light and it must be green for our direction..
		//do I need extra code here? Or have I just over simplified it? 
	

		
		
		/*
		 * ----------------------- OLD CODE BELOW --------------------
		 */

//	
//		// Note: cannot perform arithmetic on calendar type object
//		int vTimeRemainingOnLight = oTrafficLight.getiChangeFreq() - oTrafficLight.gettLastChange() // time left till current light changes
//		// if the state of the traffic lights aren't going to change at all within the given time frame then skip the other checks
//		if (vTimeRemainingOnLight < vBufferTimeConstant) { 
//			if (oTrafficLight.getiCurrDirection() == pDirection) // now check the state of the lights and whether its green for our direction
//				vLightsAreGreen = true;
//		}
//		// else at least one of the lights will change in the given time frame so lets find out which light it will be
//		else {
//			// check how many times the lights will change within this period of time and change the direction of the lights to reflect this
//			int vFinalLightDirection = oTrafficLight.getiCurrDirection() + checkChangeCycle(vBufferTimeConstant, vTimeRemainingOnLight);
//			// if we exceed the max direction (the max number of lights at intersection) then we need to subtract max direction to get the actual direction of the lights
//			if (vFinalLightDirection > oTrafficLight.getiMaxDirection()) 
//				vFinalLightDirection = vFinalLightDirection - oTrafficLight.getiMaxDirection();
//			
//			if (vFinalLightDirection == pDirection) // now check the state of the lights and whether its green for our direction
//				vLightsAreGreen = true;
//		}
//		
//		/* Code for David's original psuedocode --> Assumed lastChange is used like a timestamp of the last traffic light change? */
//		/* Checks if the light is currently red for our street (current direction) and if it may change to green after a given period of time (the buffer constant?) */
//		if (oTrafficLight.getiCurrDirection() != pDirection)
//		{
//			//check if its about to change based on time, this again requires time
//			// TODO KEN: AreLightsGreen: create logic for working out if a light is about to change - maybe make a function with the todo below this
//			// if this says lights are or have changed then return true right away and skip the code below
//			
//			
//			/* If its going to change to green make sure the direction which is to go green by that given amount of time is actually our direction first 
//			   If our direction is not going to change then it will still be red so we can just return false */
//			
//			// If our light direction is next to change
//			if (oTrafficLight.getiCurrDirection() + 1 == pDirection || (oTrafficLight.getiCurrDirection() + 1) - oTrafficLight.getiMaxDirection() == pDirection)
//				// will our lights be changed to green by the end of that given time period
//				if (oClock.getSimTime() + vBufferTimeConstant >= oTrafficLight.gettLastChange() + oTrafficLight.getiChangeFreq()) 
//					vLightsAreGreen = true;
//		
//		}
//		/* As the light is currently green for our street, we check if the light will stay green */
//		else
//		{
//			//this object will be the database object
//			/* this is sudo code for someone TODO KEN: AreLightsGreen: create logic for working out if a light has just changed but the db isn't current) 
//			 * 
//			 * get last change time of the lights oTrafficLight.gettLastChange() 
//			 * check that last change + oTrafficLight.getiChangeFreq() < than current time-IbufferTimeConstant			
//			*/
//			
//			/* If the lights aren't going to change at the end of that given period of time then we return true */
//			if (oTrafficLight.gettLastChange() + oTrafficLight.getiChangeFreq() < oClock.getSimTime() + vBufferTimeConstant) // Note: cannot perform arithmetic on calendar type object
//				vLightsAreGreen = true;
//			
//		}
//		
//		//then we'll check to see if the light is going to change soon and inside our buffer constant
		

	}
	
//	// return how many times a traffic light will change given a set period of time -- changeFreq is universal for all lights
//	public int checkChangeCycle(int pBufferTimeConstant, int pTimeRemainingOnLight) {
//		int vBufferTimeConstant = pBufferTimeConstant - pTimeRemainingOnLight; // get remaining time left after the first light changes 
//		return ((vBufferTimeConstant/oTrafficLight.getiChangeFreq()) + 1); // add 1 to account for the first light which changed
//	}

}
