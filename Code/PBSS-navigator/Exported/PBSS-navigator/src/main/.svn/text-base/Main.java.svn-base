package main;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import config.SysConfig;

import datastore.BusMovementStore;
import datastore.TrafficLightStore;

import rawobjects.Bus;
import rawobjects.TrafficLight;
import rmi_engine.NAVServerImpl;
import rmi_interfaces.NAVServer;
import utils.Clock;
import utils.TimeControlTHD;


public class Main {

	public static void main(String[] args){

		//Set default User Input mode
		SysConfig.setAllowUserInputMode(true);

		for (int i = 0; i < args.length; i++){
			String sArg = args[i].toUpperCase();

			if (sArg.equalsIgnoreCase("-i")){
				SysConfig.setAllowUserInputMode(true);
			}
		}

//		//start clock thread
//		Thread runTimeThread; 
//		runTimeThread = new Thread(new TimeControlTHD());
//		runTimeThread.start();

		//start RMIcontrol thread
		Thread runThread; 
		runThread = new Thread(new RMIControlTHD());
		runThread.start();

		if (SysConfig.getAllowUserInputMode()){
			//start Input thread - allows local console commands
			Thread runInputThread; 
			runInputThread = new Thread(new InputTHD());
			runInputThread.start();
		}
		else
		{
			System.out.println("LUIM OFF");
		}

		//test stuff
		//setup startup
		//startup();

		//set it all goingx
		createnavthread();



		System.out.println("NavRMIControlTHD Thread should be started!");
	}



	private static void startup()
	{
		//start the simulation clock


		//		//create some traffic lights
		//		TrafficLight vTrafficLight;
		//		
		//		vTrafficLight = new TrafficLight(1,3,2,3,false, 2, 3 ,4);
		//		TrafficLightStore.vTrafficLightStore.add(vTrafficLight);
		//		
		//		vTrafficLight = new TrafficLight(2,6,1,10,false, 5, 4 ,4);
		//		TrafficLightStore.vTrafficLightStore.add(vTrafficLight);


		/*
				public TrafficLight(int pTrafficLightId,int pTrafficLightLocationX, 
						int pTrafficLightLocationY,int pChangeFreq, boolean pIsJunction, 
						long tLastChange, int iCurrDirection, int iMaxDirection)
		 */


	}

	private static void createnavthread()
	{
		/*
		public Bus(int pCurrentPointX, int pCurrentPointY, int pMoveToPointX, int pMoveToPointY, int pBusID,
				int pRouteID, int pColor, int pbusType, int pCapacity, boolean pOperating)
		{
		 */
		//create a bus
		//		Bus pBus = new Bus(2000000,3000000,5000000,5000000,1,1,1,1,50,true);
		//
		//
		//		BusMovementStore.vBusPositionVector.add(new Bus(1,2,5,5,2,1,1,1,50,true));
		//		BusMovementStore.vBusPositionVector.add(new Bus(3,4,5,5,3,1,1,1,50,true));
		//		BusMovementStore.vBusPositionVector.add(new Bus(5,2,5,5,4,1,1,1,50,true));
		//		BusMovementStore.vBusPositionVector.add(new Bus(4,3,5,5,5,1,1,1,50,true));

		//		//create a thread
		//		Thread runThread; 
		//		runThread = new Thread(new NavigationInstance(pBus, 1));
		//		//enter the thread into the datastore - don't know if this is needed, but might be useful to
		//		//have a list of threads
		//		datastore.NavigationThreadStore.vThreadStore.add(runThread);
		//		//start the thread up
		//		runThread.start();
		//		

		//print clock
		System.out.println("TIME: "+Clock.getSimTime());
	}









}
