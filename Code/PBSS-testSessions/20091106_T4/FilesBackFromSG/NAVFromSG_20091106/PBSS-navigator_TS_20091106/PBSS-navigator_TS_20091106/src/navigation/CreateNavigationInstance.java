package navigation;


/**
 * 
 * This class will get called by scheduler over RMI to create a new Navigation instance.
 * The Navigation instances will be spawned here
 * 
 * Currently i have a method in the RMI server that's doing this work, not sure if this class will be
 * required
 * 
 * @author David Taberner
 *
 */
/* THIS IS PROBABLY NOT NEEDED, AS RMI will start the Navigation instance
public class CreateNavigationInstance 
{

	//static method that will take a bus object and create a new navigation request tread from it 
	public static boolean CreateNavigationInstance(rawobjects.bus pBus,int pRequestID)
	{
		
		//do a whole heap of checking here to ensure we have the correct information
		//if all is correct we'll create the thread and return true
		//otherwise false
		
		if(true)
		{
			//create a thread
			Thread runThread; 
			runThread = new Thread(new NavigationInstance(pBus, pRequestID));
			//enter the thread into the datastore - don't know if this is needed, but might be useful to
			//have a list of threads
			datastore.NavigationThreadStore.vThreadStore.add(runThread);
			//start the thread up
			runThread.start();
			
		}
		
		return false;
		
	}

}*/
