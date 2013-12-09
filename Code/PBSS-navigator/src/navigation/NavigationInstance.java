package navigation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.UUID;

import config.SysConfig;

import datastore.TrafficLightStore;
import rawobjects.NavRequest;
import rawobjects.TrafficLight;
import rawobjects.Bus;
import utils.Clock;

/**
 * 
 * When a navigation request comes in, an instance of this object will be created. Each instance will 
 * be created into a new thread
 * 
 * @author David Taberner
 *
 */

public class NavigationInstance implements Runnable {
	
	private NavRequest mNavRequest; // should be able to create these object without importing the package i think i don't remember how to do this though?
	private TrafficLight oTrafficLight;
	private Clock oClock;
	UUID iRequestID;

	/**
	 * Run() will get called when the thread is started, this is like main but for the thread
	 */
	@Override
	public void run() {
			
		//work out if we can move
		boolean vCanWeMove = CanWeMove(); 
//		boolean vCanWeMove = true;
		try
		{
			//setup the RMI connect object
			rmi_interfaces.SCHServer sch = new rmi_engine.NAVClientImplSCH().getSrvObject() ;
			
			//call sch and tell it the news
			sch.responseFromNav(iRequestID, vCanWeMove);
			
			
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
	

	
		System.out.println("TIME2: "+Clock.getSimTime());
		
//		//constant loop, atm this is just testing code!
//		while(true)
//		{
//			System.out.println(this.hashCode());
//			try {
//				
//				/* TASKS before we allow movement 
//				-are we at lights?
//					- are they green? - note the 10% rule here
//				-are we at a bus stop and have we already stopped?
//				-are there buses in the way?
//				
//				
//				
//				
//				*/
//				
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// todo Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
	}
	
	
	private boolean CanWeMove()
	{
		
	
		//create a new instance of the light checking object so we don't get threads playing nasty with various objects if they are static
		LightChecking oLightCheckClass = new LightChecking();
		
		//check we are at a light and get the light ID. 
		int iLightID = oLightCheckClass.AreWeAtLights(mNavRequest.getbBus().getCurrentPointX(), mNavRequest.getbBus().getCurrentPointY());
		
		//if not equal to minus one we need to check is it green
		if(iLightID != -1)
		{
			if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("------------------------");
			System.out.println("DEBUG: light ID "+iLightID);
			
			boolean bLightCheck = oLightCheckClass.AreLightsGreen(iLightID, mNavRequest.getbBus());
			
			System.out.println("DEBUG: Is the light green? "+bLightCheck);
			if(config.SysConfig.SYSTEM_DEBUG_MODE_ENABLED) System.out.println("------------------------");
			//if we get a false lets use shortcircuit evaluation to get back ASAP
			if(bLightCheck == false)
			{
				return bLightCheck;
			}
			
		}
		
		
		//check if the bus can move
		//boolean vBusCanMove = true;
		
		//create the bus check object
		BusChecking oBusCheck = new BusChecking(mNavRequest);
		
		//now run the check 
		return oBusCheck.OkToMove();

		
	}
	
	
	/**
	 * Constructor for the NavigationInstance
	 * @param pBus - an object which is the representation of a bus
	 * @param pTrafficLight - an object which is the representation of a traffic light
	 * @param pRequestID - an ID for the request
	 */
	// notation not needed as we already imported that package
	public NavigationInstance(NavRequest pNavRequest, UUID pRequestID)
	{
		mNavRequest = pNavRequest;
		iRequestID = pRequestID;
	}



}
