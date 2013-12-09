package datastore;
import java.util.Vector;
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

public class NavigationThreadStore {
	
	public static Vector<Thread> vThreadStore = new Vector<Thread>();
	
}
