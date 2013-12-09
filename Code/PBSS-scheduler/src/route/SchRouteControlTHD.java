package route;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import config.SysConfig;

import rawobjects.Congestion;
import rawobjects.Route;
import rawobjects.BusStop;
import rmi_engine.SCHClientImplDB;
import rmi_interfaces.DBServer;
import routeCheck.SchRouteChecker;
import routeCheck.SchSimpleObjCongArea;
import routeCheck.SchSimpleObjStop;

import util.Debug;
import util.General;
import util.SysLogTypeEnum;

public class SchRouteControlTHD implements Runnable{

	boolean bDEBUG_ShowPingMsg=true;
	public void debugPingMsg_Off(){ Debug.writeDebugMsg("SchRouteControl", "debugPingMsg_Off", "Ping Msg OFF"); bDEBUG_ShowPingMsg = false;}
	public void debugPingMsg_On(){ Debug.writeDebugMsg("SchRouteControl", "debugPingMsg_On", "Ping Msg ON"); bDEBUG_ShowPingMsg = true;}

	boolean bRouteControlActive;

	//Route Objects
	private Vector<SchRouteObject> vRoute;

	public SchRouteControlTHD(){
		vRoute = new Vector<SchRouteObject>();
		this.startRouteControl();
	}

	@Override
	public void run() {

		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "140", "App Ops");
		Debug.writeDebugMsg("SchRouteControl", "run", "Route Control Thread Started");

		while (bRouteControlActive){
			try {
				if (bDEBUG_ShowPingMsg) Debug.writeDebugMsg("SchRouteControl", "run", "PING....");
				Thread.sleep(800);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		Debug.writeDebugMsg("SchRouteControl", "run", "Route Control Thread Ended");
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "141", "App Ops");
	}

	public void startRouteControl(){		
		this.bRouteControlActive=true;
	}

	public void stopRouteControl(){
		this.procRouteOper_KillBus(); 
		this.bRouteControlActive=false;
	}

	private void procRouteOper_KillBus(){
		this.procRouteBusKillAll();
	}

	public void loadRouteList(){
		Debug.writeDebugMsg("schRouteControl", "loadTLObj", "Loading Routes from DB");

		SysConfig.setStateCheck_RouteLoaded(false);
		
		Vector<Route> vRouteDB = new Vector<Route>();

		try {
			//Create RMI session to DB:

			DBServer db = (DBServer)(new SCHClientImplDB()).getSrvObject();
			vRouteDB = db.getListOfRoutes(SysConfig.getSimRunID());

			//TODO 4LOG [LOGGING]: Need some logging here, so we know if none are returned?

			if (vRouteDB.size()>0){
				Route r;
				SchRouteObject rO;

				for (int i = 0; i < vRouteDB.size();i++){
					r = vRouteDB.get(i);
					rO = new SchRouteObject();

					rO.setRouteID(i);
					rO.setRouteDBID(r.getRouteID());
					rO.setFrequency(r.getDepartEvery());
					Vector<rawobjects.ROUTE_POINT> vRP = r.getPoints();
					Vector<Point> vP = new Vector<Point>();
					
					//Added due to issue with type conversion from route_point
					for (int j = 0;j < vRP.size();j++) {
						rawobjects.ROUTE_POINT rp = vRP.get(j);
						Point p = new Point(rp.getPointX(),rp.getPointY());
						vP.add(p);
					}
					//rO.addRoutePoint(r.getPoints());
					rO.addRoutePoint(vP);

					rO.loadRouteData();
					
					this.vRoute.add(rO);
				}
			}
			
			SysConfig.setStateCheck_RouteLoaded(true);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		Debug.writeDebugMsg("schRouteControl", "loadRouteObj", "Loading Routes Complete - " + vRouteDB.size() + " route(s) created from DB.");

	}


	public void loadStopList(){
		Debug.writeDebugMsg("schRouteControl", "loadStopList", "Loading Stops from DB");
		
		SysConfig.setStateCheck_StopLoaded(false);
		
		Vector<BusStop> vStopDB = new Vector<BusStop>();
		SchRouteChecker.simpleStopClear();
		SysConfig.stopObjClear();
		
		try {
			//Create RMI session to DB:
			DBServer db = (DBServer)(new SCHClientImplDB()).getSrvObject();
			vStopDB = db.getListOfStops(SysConfig.getSimRunID());

			
			//TODO 4LOG [LOGGING]: Need some logging here, so we know if none are returned?

			if (vStopDB.size()>0){
				SchSimpleObjStop simStop;
				BusStop stop;
				for (int i = 0;i<vStopDB.size();i++){
					stop = vStopDB.get(i);
					stop.setPX_Count(stop.getPX_Start()); //Set the default PAX count at this stop
					SysConfig.stopObjAdd(stop);
					
					simStop = new SchSimpleObjStop();

					simStop.setStopID(i);
					simStop.setStopDBID(stop.getBusStopID());
					simStop.setStopPoint(stop.getLogPoint());

					SchRouteChecker.SimpleStopAdd(simStop);
				}
			}
			
			SysConfig.setStateCheck_StopLoaded(true);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		Debug.writeDebugMsg("schRouteControl", "loadStopList", "Loading Stops Complete - " + vStopDB.size() + " stop(s) created from DB.");

	}

	public void loadCongAreaList(){
		Debug.writeDebugMsg("schRouteControl", "loadCongAreaObj", "Loading Congested Areas from DB");
		
		SysConfig.setStateCheck_CongAreaLoaded(false);
		
		Vector<Congestion> vCADB = new Vector<Congestion>();
		SchRouteChecker.simpleCAClear();

		try {
			//Create RMI session to DB:
			DBServer db = (DBServer)(new SCHClientImplDB()).getSrvObject();
			vCADB = db.getListOfCongAreas(SysConfig.getSimRunID());

			//TODO 4LOG [LOGGING]: Need some logging here, so we know if none are returned?

			if (vCADB.size()>0){
				SchSimpleObjCongArea simCA;
				Congestion ca;
				for (int i = 0;i<vCADB.size();i++){
					ca = vCADB.get(i);
					simCA = new SchSimpleObjCongArea();

					simCA.setCAID(i);
					simCA.setCADBID(ca.getCongestedAreaID());
					simCA.setCAPointA(ca.getPointA());
					simCA.setCAPointB(ca.getPointB());

					SchRouteChecker.SimpleCAAdd(simCA);
				}
			}
			
			SysConfig.setStateCheck_CongAreaLoaded(true);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}

		Debug.writeDebugMsg("schRouteControl", "loadCongAreaList", "Loading Congested Areas Complete - " + vCADB.size() + " congested area(s) created from DB.");

	}

	public void loadRouteList_File(){
		Debug.writeDebugMsg("SchRouteControl", "loadRouteList", "Loading Routes");

		try {
			//Create and open file stream
			FileInputStream fstream = new FileInputStream(General.pathDataFile("routesched.txt"));
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String sLine;
			Vector<String> vectRouteFileList = new Vector<String>();

			while ((sLine = br.readLine()) != null) {
				if (sLine.length()>0){
					if (sLine.startsWith("#")){
						//comment line, do nothing
					}
					else if (sLine.startsWith(":")){
						//TRoute Files Lists
						vectRouteFileList.add(sLine.replace(":", ""));
					}
				}
			}

			in.close();

			for (int i = 0;i<vectRouteFileList.size();i++){
				this.loadRoute_File(vectRouteFileList.get(i));            	
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void loadRoute_File(String sFileName){
		Debug.writeDebugMsg("SchRouteControl", "loadRoute", "Loading Route from FILE: " + sFileName);

		try {
			//Create and open file stream
			FileInputStream fstream = new FileInputStream(General.pathDataFile(sFileName));
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String sLine;
			Vector<String[]> vectRouteInfo = new Vector<String[]>();
			Vector<String[]> vectRoutePointList = new Vector<String[]>();
			String s[];

			while ((sLine = br.readLine()) != null) {
				if (sLine.length()>0){
					if (sLine.startsWith("#")){
						//comment line, do nothing
					}
					else if (sLine.startsWith(":")){
						//Route Config
						s = (sLine.replace(":", "")).split("=");
						vectRouteInfo.add(s);
					}
					else if (sLine.startsWith("~")){
						//Route Points
						s = (sLine.replace("~", "")).split(",");
						vectRoutePointList.add(s);
					}
				}
			}

			in.close();

			SchRouteObject route = new SchRouteObject();

			route.setRouteID(this.vRoute.size());

			s = vectRouteInfo.get(0);
			route.setRouteDBID(Integer.parseInt(s[1]));

			s = vectRouteInfo.get(1);
			route.setFrequency(Integer.parseInt(s[1]));

			s = vectRouteInfo.get(2);
			route.setBusCount_File(Integer.parseInt(s[1]));

			for (int i = 0;i<vectRoutePointList.size();i++){
				s = vectRoutePointList.get(i);
				route.addRoutePoint(new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1])));
			}

			Debug.writeDebugMsg("SchRouteControl", "loadRoute", "Route Loaded - Route ID = " + route.getRouteID() + ", DBID = " + route.getRouteDBID() + "; Point Count = " + route.getRoutePoints().size());

			route.createBuses_File();

			this.vRoute.add(route);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	public boolean procRouteBusStartAll(){
		for (int i = 0;i<this.vRoute.size();i++){
			SchRouteObject route = (SchRouteObject) vRoute.get(i);

			route.setBusStartTime();
			route.startBuses();
		}

		return true;
	}

	public boolean procRouteBusStopAll(){
		for (int i = 0;i<this.vRoute.size();i++){
			SchRouteObject route = (SchRouteObject) vRoute.get(i);

			route.procBusStopAll();
		}

		return false;
	}

	public boolean procRouteBusKillAll(){
		for (int i = 0;i<this.vRoute.size();i++){
			SchRouteObject route = (SchRouteObject) vRoute.get(i);

			route.procBusKillAll();
		}

		return false;
	}

	public void procRouteInfo(int pRouteID){

		if (this.vRoute.size()>=0){
			if ((pRouteID >= 0) && (pRouteID < this.vRoute.size())){
				SchRouteObject route = vRoute.get(pRouteID);
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "**** Route Information - " + pRouteID);
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "  Internal Route ID = " + route.getRouteID());
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "  DB Route ID = " + route.getRouteDBID());
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "  Frequency = Every " + route.getFrequency() + " " + General.unitTime());
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "  Max Bus Count = " + route.getBusCount() + " bus(es)");
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "   thus, last bus starts at = " + route.getLastBusScheduleTime() + " " + General.unitTime());
				Vector<Point> vP = route.getRoutePoints();
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "  Route Points List: ");
				
				for (int i = 0;i<vP.size();i++){
					Point p = vP.get(i);
					
					Debug.writeDebugMsg("SchRouteControl","procRouteInfo", "    " + (i + 1) + ": " + p.toString());  
				}
			}
			else
				Debug.writeDebugMsg("SchRouteControl","procRouteInfo", " >> Unable to find Route Info (internal ID = " + pRouteID + " - Out of Range)");
		}
		else
			Debug.writeDebugMsg("SchRouteControl","procRouteInfo", " >> Unable to find Route Info " + pRouteID + " - No Definitions");
	}


}
