package rmi_dummy;

import java.awt.Point;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import rawobjects.Bus;
import rawobjects.Bus_Type;
import rawobjects.Congestion;
import rawobjects.ROUTE_POINT;
import rawobjects.Route;
import rawobjects.BusStop;
import rawobjects.TrafficLight;
import rawobjects.Traffic_Light_Angle;

import util.General;

public class DummyRMI_DB_MDB {

	private static Connection getDBConnection() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); //Check that the needed package/class files exist

			String filename = General.pathDataFile("dummyDB.mdb");

			//System.out.println("DB PATH = " + filename);

			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			database+= filename.trim() + ";DriverID=22;READONLY=false;"; // add on to the end
			// now we can get the connection from the DriverManager
			Connection con = DriverManager.getConnection( database ,"",""); 

			return con;
		}
		catch (Exception ex) {
			ex.printStackTrace();

			return null;
		}
	}

	private static boolean executeSQLInsert(String sSQL){

		//System.out.println("INSERT=" + sSQL);

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			s.close();
			dbConn.close();
			dbConn=null;

			return true;

		} catch (Exception ex){
			ex.printStackTrace();

			return false;
		}
	}

	private static String executeSQLSelectSingle(String sSQL){
		//System.out.println("SELECT [SINGLE]=" + sSQL);
		String sData = null;
		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null) {
				while ( rs.next() ) // this will step through our data row-by-row
				{
					sData = rs.getObject(1).toString();
				}
			}
			s.close();
			dbConn.close();
			dbConn=null;

			return sData;

		} catch (Exception ex){
			ex.printStackTrace();

			return null;
		}
	}

	private static String formatDBDate(Calendar c){
		java.util.Date d = null;
		d = c.getTime();
		d.setTime(c.getTimeInMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return "'" + sdf.format(d) + "'";
	}

	public static boolean createSysSimRunID(int pSimRunID, int pMapToUseID){
		String s = "INSERT INTO SYS_SIMULATOR_RUN(SimRunID, StartDate, MapID) " +
		"VALUES (" + pSimRunID + "," + formatDBDate(Calendar.getInstance()) + "," + pMapToUseID + ")";

		return executeSQLInsert(s);
	}

	public static Vector<rawobjects.TrafficLight> getListTrafficLight(int pSimRunID){
		Vector<rawobjects.TrafficLight> v = new Vector<rawobjects.TrafficLight>();

		String sSQL = "SELECT * FROM qList_TrafficLight WHERE SimRunID = " + pSimRunID;
		TrafficLight tl;

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); // get any ResultSet that came from our query
			if (rs != null) // if rs == null, then there is no ResultSet to view
				while ( rs.next() ) // this will step through our data row-by-row
				{
					tl = new TrafficLight();

					tl.setTrafficLightId(rs.getInt("TrafficLightID"));
					tl.setTrafficLightLocationX(rs.getInt("PointX"));
					tl.setTrafficLightLocationY(rs.getInt("PointY"));
					tl.setChangeFreq(rs.getInt("ChangeFreq"));
					tl.setIsJunction(rs.getBoolean("IsJunction"));
					tl.setLastChange(rs.getLong("LastChange"));
					tl.setCurrDirection(rs.getInt("CurrDirection"));
					tl.setMaxDirection(rs.getInt("MaxDirection"));
					
					tl.setTrafficLightAngle(getTLAngles(tl.getTrafficLightId()));
					
					System.out.println("TLID = " + tl.getTrafficLightId() + " || LIGHT_ANGLE_COUNT = " + tl.getTrafficLightAngle().size());

					//System.out.println("ADD TL: " + tl.getiTrafficLightId());

					v.add(tl);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}
	
	private static Vector<rawobjects.Traffic_Light_Angle> getTLAngles(int pTLID){
		Vector<rawobjects.Traffic_Light_Angle> v = new Vector<rawobjects.Traffic_Light_Angle>();

		String sSQL = "SELECT * FROM TRAFFIC_LIGHT_ANGLE WHERE TrafficLightID = " + pTLID;
		Traffic_Light_Angle tla;

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); // get any ResultSet that came from our query
			if (rs != null) // if rs == null, then there is no ResultSet to view
				while ( rs.next() ) // this will step through our data row-by-row
				{
					tla = new Traffic_Light_Angle();

					tla.setAngleID(rs.getInt("AngleID"));
					tla.setTrafficLightID(rs.getInt("TrafficLightID"));
					tla.setDirection((short)rs.getInt("Direction"));
					tla.setAngle(rs.getDouble("Angle"));

					System.out.println("ANGLE ID = " + tla.getAngleID());
						
					v.add(tla);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}

	public static TrafficLight getTrafficLight(int pTLID){


		String sSQL = "SELECT * FROM qList_TrafficLight WHERE TrafficLightID = " + pTLID;
		
		System.out.print(sSQL);
		
		TrafficLight tl = new TrafficLight();

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); // get any ResultSet that came from our query
			if (rs == null) // if rs == null, then there is no ResultSet to view
				tl = new TrafficLight();
			else {
				rs.next();
				
				tl.setTrafficLightId(rs.getInt("TrafficLightID"));
				tl.setTrafficLightLocationX(rs.getInt("PointX"));
				tl.setTrafficLightLocationY(rs.getInt("PointY"));
				tl.setChangeFreq(rs.getInt("ChangeFreq"));
				tl.setIsJunction(rs.getBoolean("IsJunction"));
				tl.setLastChange(rs.getLong("LastChange"));
				tl.setCurrDirection(rs.getInt("CurrDirection"));
				tl.setMaxDirection(rs.getInt("MaxDirection"));
				
				tl.setTrafficLightAngle(getTLAngles(tl.getTrafficLightId()));
				
				System.out.println("TLID = " + tl.getTrafficLightId() + " || LIGHT_ANGLE_COUNT = " + tl.getTrafficLightAngle().size());
			}

			//System.out.println("ADD TL: " + tl.getiTrafficLightId());
			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		System.out.println("*REQUEST AT: getTrafficLight(pTLID INFO : TLDBID = " + tl.getTrafficLightId() + " CUR DIR = " + tl.getCurrDirection());
		return tl;
	}

	public static Vector <rawobjects.Route>getListRoutes(int pSimRunID){
		Vector<rawobjects.Route> v = new Vector<rawobjects.Route>();

		String sSQL = "SELECT * FROM qList_Route WHERE SimRunID = " + pSimRunID;
		Route r;

		boolean bIncludeRoutePoints = true; //always get route points

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null)
				while ( rs.next() ) 
				{
					r = new Route();

					r.setRouteID(rs.getInt("RouteID"));
					//CancelledTODO [.] Add Route Number (refer DB)
					r.setRouteName(rs.getString("RouteName"));
					r.setDepartEvery(((short)rs.getInt("DepartEvery")));
					//r.setStartTerminalId(rs.getInt("StartTerminalID"));
					//r.setEndTerminalId(rs.getInt("EndTerminalID"));

					if (bIncludeRoutePoints){
						r.setPoints(getListRoutePoints(r.getRouteID()));
						System.out.println("ROUTE_POINT_COUNT = " + r.getPoints().size());
					}

					//System.out.println("ADD RTE: " + r.getiRouteId());

					v.add(r);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}

	public static Vector<rawobjects.ROUTE_POINT> getListRoutePoints(int pRouteID){
		Vector<rawobjects.ROUTE_POINT> v = new Vector<rawobjects.ROUTE_POINT>();

		String sSQL = "SELECT * FROM ROUTE_POINT WHERE RouteID = " + pRouteID;
		sSQL += " ORDER BY SeqOrder"; //THIS OrderBy IS IMPORTANT.

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null) {
				rawobjects.ROUTE_POINT rp = null;
				while ( rs.next() ) 
				{   	
					rp = new rawobjects.ROUTE_POINT();
					rp.setPointX(rs.getInt("PointX"));
					rp.setPointY(rs.getInt("PointY"));
					
					v.add(rp);
					//v.add(new Point(rs.getInt("PointX"), rs.getInt("PointY")));
				}
			}
			
			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}

	public static Vector <rawobjects.BusStop>getListStops(int pSimRunID){
		Vector<rawobjects.BusStop> v = new Vector<rawobjects.BusStop>();
		String sSQL = "SELECT * FROM qList_Stop WHERE SimRunID = " + pSimRunID;
		BusStop st;

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null)
				while ( rs.next() ) 
				{
					st = new BusStop();

					st.setBusStopID(rs.getInt("StopID"));
					st.setBusStopName(rs.getString("StopName"));
					st.setPhysPoint(new Point(rs.getInt("PhysPointX"), rs.getInt("PhysPointY")));
					st.setLogPoint(new Point(rs.getInt("LogPointX"),rs.getInt("LogPointY")));
					st.setPX_Start(rs.getInt("PaxCount_Start"));
					st.setPX_Min(rs.getInt("PaxCount_Min"));
					st.setPX_Max(rs.getInt("PaxCount_Max"));
					st.setPX_Count(rs.getInt("PaxCount_Count"));

					//System.out.println("ADD STOP: " + st.getStopID());

					v.add(st);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}

	public static Vector <rawobjects.Congestion>getListCongAreas(int pSimRunID){
		Vector<rawobjects.Congestion> v = new Vector<rawobjects.Congestion>();
		String sSQL = "SELECT * FROM qList_CongArea WHERE SimRunID = " + pSimRunID;
		Congestion ca;

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null)
				while ( rs.next() ) 
				{
					ca = new Congestion();

					ca.setCongestedAreaID(rs.getInt("CongestedAreaID"));
					ca.setPointA(new Point(rs.getInt("PointX_A"), rs.getInt("PointY_A")));
					ca.setPointB(new Point(rs.getInt("PointX_B"), rs.getInt("PointY_B")));

					//System.out.println("ADD CA: " + ca.getCongestedAreaID());

					v.add(ca);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}

	public static Vector getListBuses(){
		Vector<rawobjects.Bus> v = new Vector<rawobjects.Bus>();
		String sSQL = "";

		sSQL = "SELECT * FROM qList_BusALL";

		Bus b;
		Bus_Type bType;

		//System.out.println("getListBuses_SELECT=" + sSQL);

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null)
				while ( rs.next() ) 
				{
					b = new Bus();
					bType = new Bus_Type();

					b.setBusID(rs.getInt("BusID"));
					
					System.out.println("BUS ID = "  + b.getBusID());
					
					b.setRouteID(rs.getInt("RouteID"));	
					b.setDefaultLoad(rs.getInt("DefaultLoad"));
					b.setCurrLoad(rs.getInt("CurrLoad"));

					bType.setBusColour(rs.getInt("BusColour"));
					bType.setBusType(rs.getString("BusType"));
					bType.setMaxCapacity((short)rs.getInt("MaxCapacity"));
					b.setBusType(bType);

					//System.out.println("ADD BUS: " + b.getiBusID() + ", routeid = " + b.getiRouteID());

					v.add(b);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;

	}
	public static Vector<rawobjects.Bus> getListBuses(int pSimRunID, int pRouteID){
		Vector<rawobjects.Bus> v = new Vector<rawobjects.Bus>();
		String sSQL = "";

		if (pRouteID>=0){
			sSQL = "SELECT * FROM qList_BusALL WHERE SimRunID = " + pSimRunID;
			sSQL += " AND RouteID = "+ pRouteID;
		}
		else {
			sSQL = "SELECT * FROM qList_BusALL WHERE SimRunID = " + pSimRunID;
		}

		Bus b;
		Bus_Type bType;

		//System.out.println("getListBuses_SELECT=" + sSQL);

		try {
			Connection dbConn = getDBConnection();
			Statement s = dbConn.createStatement();
			s.execute(sSQL);

			ResultSet rs = s.getResultSet(); 
			if (rs != null)
				while ( rs.next() ) 
				{
					b = new Bus();
					bType = new Bus_Type();

					b.setBusID(rs.getInt("BusID"));
					
					System.out.println("BUS ID = "  + b.getBusID());
					
					b.setRouteID(rs.getInt("RouteID"));	
					b.setDefaultLoad(rs.getInt("DefaultLoad"));
					b.setCurrLoad(rs.getInt("CurrLoad"));

					bType.setBusColour(rs.getInt("BusColour"));
					bType.setBusType(rs.getString("BusType"));
					bType.setMaxCapacity((short)rs.getInt("MaxCapacity"));
					b.setBusType(bType);

					//System.out.println("ADD BUS: " + b.getiBusID() + ", routeid = " + b.getiRouteID());

					v.add(b);
				}

			s.close(); // close the Statement to let the database know we're done with it
			dbConn.close(); // close the Connection to let the database know we're done with it
			dbConn = null;

		} catch (Exception ex){
			ex.printStackTrace();

		}

		return v;
	}

	public static boolean trafficLightChanged(int pTLDBID, long pLastChange, int pDirCurr) {
		String sSQL=null;

		sSQL = "UPDATE TRAFFIC_LIGHT ";
		sSQL+= "SET LastChange = " + pLastChange;
		sSQL+= ", CurrDirection = " + pDirCurr;
		sSQL+= " WHERE TrafficLightID = " + pTLDBID;

		return executeSQLInsert(sSQL);
	}

	public static boolean stopUpdated(int pStopDBID, int pStopPaxCount) {
		String sSQL=null;

		sSQL = "UPDATE STOP ";
		sSQL+= "SET PaxCount_Count = " + pStopPaxCount;
		sSQL+= " WHERE StopID = " + pStopDBID;

		return executeSQLInsert(sSQL);
	}

	public static boolean addPaxStat(rawobjects.Pax_Stat pStat) {
		String sSQL=null;

		sSQL = "INSERT INTO PAX_STAT (SimRunID, BusStopID, BusID, [Count]) VALUES ";
		sSQL+= "(" + pStat.getSimRunID() + "," + pStat.getBusStopID() + "," + pStat.getBusID() + "," + pStat.getCount() + ")";

		//System.out.println("SQL=" + sSQL);

		return executeSQLInsert(sSQL);
	}

	public static boolean busUpdated(rawobjects.Bus pBus) {
		String sSQL=null;

		sSQL = "UPDATE BUS ";
		sSQL+= "SET RouteID = " + pBus.getRouteID();
		//sSQL+= ", BusTypeID = " + 
		sSQL+= ", CurrLoad = " + pBus.getCurrLoad() ;
		sSQL+= ", CurrPointX = " + pBus.getCurrentPointX();
		sSQL+= ", CurrPointY = " + pBus.getCurrentPointY();
		sSQL+= ", MovePointX = " + pBus.getMoveToPointX();
		sSQL+= ", MovePointY = " + pBus.getMoveToPointY();
		//sSQL+= "MoveReqState = " +
		//sSQL+= "MoveReqTimeStamp = " +
		sSQL+= ", DefaultLoad = " + pBus.getDefaultLoad();
		sSQL+= " WHERE BusID = " + pBus.getBusID();

		System.out.println(sSQL);

		return executeSQLInsert(sSQL);
	}


	public static int getMaxSimRunID(){
		String sSQL="SELECT * FROM qSysMaxSimRunID";

		String s = executeSQLSelectSingle(sSQL);

		if (s!=null)
			return Integer.parseInt(s);
		else
			return -1;

	}
	
	public static void main(String args[]){
		//LOCAL DB TESTING:
		
		Vector<rawobjects.TrafficLight> vTL = getListTrafficLight(31);
		
		System.out.println("SIZE = " + vTL.size());
	}
}

