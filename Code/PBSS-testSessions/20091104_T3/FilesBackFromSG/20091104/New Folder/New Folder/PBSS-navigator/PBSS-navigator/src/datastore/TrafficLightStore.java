package datastore;
import java.util.Iterator;
import java.util.Vector;

import rawobjects.TrafficLight;
/**
 * 
 * This class holds the thread objects for every active running thread on the system.
 * I don't know if this data will be useful, but ATM all I'm publishing is public static access to a 
 * vector object that the threads get into
 * 
 * As such, this class is also highly static
 * 
 * @author David Taberner
 *
 */

public class TrafficLightStore {

	public static Vector<TrafficLight> vTrafficLightStore = new Vector<TrafficLight>();
	
	
	public static void addLight(TrafficLight pLight)
	{
		vTrafficLightStore.add(pLight);
	}
	
	public static TrafficLight getLight(int pLightID)
	{
		boolean bFound = false;
		TrafficLight oTrafficLight = null;
		//loop to run over all the lights in a lights table
		Iterator<TrafficLight> itrTrafficLights = vTrafficLightStore.iterator();
		
		
		while(itrTrafficLights.hasNext() && bFound == false)
		{
			//get the object
			oTrafficLight = itrTrafficLights.next();
			
			//check if we've found it
			if(oTrafficLight.getTrafficLightId() == pLightID )
			{
				bFound = true;
			}
		}
		
		return oTrafficLight;
	}
	
}
