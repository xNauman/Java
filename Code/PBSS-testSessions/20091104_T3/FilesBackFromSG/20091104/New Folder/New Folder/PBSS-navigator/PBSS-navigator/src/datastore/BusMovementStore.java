package datastore;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import rawobjects.Bus;

/**
 * 
 * This class holds the navigators information about the location of each bus
 * This class needs to be used with its lock methods called, otherwise it will be NON THREAD SAFE
 * 
 * Method is also highly static
 * 
 * @author David Taberner
 *
 */

public class BusMovementStore {
	
	public static Vector vBusPositionVector = new Vector();
	
	public final static Lock lTableLock = new ReentrantLock();
	
	
    /**
     * Constructor that will initialize the who class from data in the database
     * The Data here will be updated by NavigationInstances 
     * 
     * @param vector containing type bus
     * 
     */
	public BusMovementStore(Vector pStartVector)
	{
		vBusPositionVector = pStartVector;
		
	}
	

	


}
