package trafficLight;

import java.awt.Point;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Vector;

import config.SysConfig;


import rawobjects.TrafficLight;
import rmi_engine.SCHClientImplDB;
import rmi_interfaces.DBServer;
import routeCheck.SchRouteChecker;

import util.Debug;
import util.General;
import util.SysLogTypeEnum;

public class SchTLControlTHD implements Runnable{

	boolean bDEBUG_ShowPingMsg=true;
	public void debugPingMsg_Off(){ Debug.writeDebugMsg("schTLControl", "debugPingMsg_Off", "Ping Msg OFF"); bDEBUG_ShowPingMsg = false;}
	public void debugPingMsg_On(){ Debug.writeDebugMsg("schTLControl", "debugPingMsg_On", "Ping Msg ON"); bDEBUG_ShowPingMsg = true;}

	boolean bTLControlActive;

	//Traffic Light Objects
	private Vector<SchTLContainer> vTL;

	public SchTLControlTHD(){
		vTL = new Vector<SchTLContainer>();
		this.startTLControl();
	}

	@Override
	public void run() {
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "130", "App Ops");
		Debug.writeDebugMsg("schTLControl", "run", "TL Control Thread Started");

		while (bTLControlActive){
			try {
				if (bDEBUG_ShowPingMsg) Debug.writeDebugMsg("schTLControl", "run", "PING....");
				Thread.sleep(800);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		Debug.writeDebugMsg("schTLControl", "run", "TL Control Thread Ended");
		Debug.writeLogEntry(SysLogTypeEnum.INFORMATION, "131", "App Ops");
	}

	public void startTLControl(){
		this.bTLControlActive=true;
	}

	public void stopTLControl(){

		//this.procTLStopAll();
		this.procTLKillAll(); 

		//System.out.println("**** STOP_TL_CONTROL ***");
		this.bTLControlActive=false;
	}

	public void loadTLObj(boolean pStartLight){
		Debug.writeDebugMsg("schTLControl", "loadTLObj", "Loading Lights");
		int iLocalID=0;
		
		SysConfig.setStateCheck_TLLoaded(false);
		
		Vector<TrafficLight> vTLDB = new Vector<TrafficLight>();
		SchRouteChecker.simpleTLClear(); //Clear any TL objects
		
		try {
			//Create RMI session to DB:

			DBServer db = (DBServer)(new SCHClientImplDB()).getSrvObject();

			vTLDB = db.getListOfTrafficLights(SysConfig.getSimRunID());

			//TODO 4LOG [LOGGING]: Need some logging here, so we know if none are returned?
				
			if (vTLDB.size()>0){
				TrafficLight tl;

				for (int i = 0; i < vTLDB.size();i++){
					tl = vTLDB.get(i);
					
					if (!tl.getIsJunction())
						iLocalID = this.createTL(tl, pStartLight, tl.getTrafficLightId(), tl.getChangeFreq(),true,tl.getCurrDirection(),tl.getMaxDirection());
					else
						iLocalID = -1;
					
					//Create Entry for Route Checking:
					routeCheck.SchSimpleObjTL simpleTL = new routeCheck.SchSimpleObjTL();
					simpleTL.setTLPoint(new Point(tl.getTrafficLightLocationX(),tl.getTrafficLightLocationY()));
					simpleTL.setTLDBID(tl.getTrafficLightId());
					simpleTL.setTLID(iLocalID);
					simpleTL.setIsJunction(tl.getIsJunction());
					SchRouteChecker.SimpleTLAdd(simpleTL);
				}
			}
			SysConfig.setStateCheck_TLLoaded(true);
		}
		catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e){

		}
		
		Debug.writeDebugMsg("schTLControl", "loadTLObj", "Loading Lights Complete - " + vTLDB.size() + " light(s) created from DB.");
	}

	/**
	 * Load Traffic Light Objects from Data Store (Text File)
	 * @param pStartLight Should lights be started on creation?
	 */
	public void loadTLObj_File(boolean pStartLight){
		Debug.writeDebugMsg("schTLControl", "loadTLObj_Text", "Loading Lights (Text File)");

		try {
			//Create and open file stream
			FileInputStream fstream = new FileInputStream(General.pathDataFile("TL.txt"));
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String sLine;
			Vector<String> vectTL = new Vector<String>();

			while ((sLine = br.readLine()) != null) {
				if (sLine.length()>0){
					if (sLine.startsWith("#")){
						//comment line, do nothing
					}
					else if (sLine.startsWith(":")){
						//Traffic Light Lists, number value is the interval
						vectTL.add(sLine.replace(":", ""));
					}
				}

			}

			in.close();

			for (int i = 0;i<vectTL.size();i++){
				Debug.writeDebugMsg("schTLControl", "loadTLObj_Text", "Creating Light ID = " + i + ", FREQ = " + vectTL.get(i));
				this.createTL(new TrafficLight(), pStartLight, -1, Integer.parseInt(vectTL.get(i)),false,1,3);
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private int createTL(TrafficLight pTL, boolean bStartLight, int pIDDB, int pChangeFreq, boolean pUpdateUsingRMI, int pDirectionCurr, int pDirectionMax){
		int i = vTL.size(); //+ 1;

		/*schTLObj newLight = new schTLObj(i);
        newLight.setFrequency(pChangeFreq);

        Thread thrdTL = new Thread(newLight);*/

		SchTLContainer newLight = new SchTLContainer(pTL, i,pIDDB,pChangeFreq,pUpdateUsingRMI, pDirectionCurr, pDirectionMax);

		if (bStartLight)
			//newLight.threadStart();
			newLight.lightStart();

		vTL.add(newLight);

		return newLight.getIDLocal();
	}

	public void procTLStartAll(){
		for (int i = 0;i<this.vTL.size();i++){
			this.procTLSingleStart(i);
		}
	}

	/**
	 * This starts the Traffic Light thread
	 * @param iTLID
	 */
	public void procTLSingleStart(int iTLID){
		Debug.writeDebugMsg("schTLControl","procTLSingleStart", "Starting Light: " + iTLID);

		if (this.vTL.size()>0){
			if ((iTLID >= 0) && (iTLID < vTL.size())){

				SchTLContainer tlContainer = (SchTLContainer) vTL.get(iTLID);

				Thread thrd = tlContainer.getThread();

				if (!thrd.isAlive())
				{
					thrd.start();
					Debug.writeDebugMsg("schTLControl","procTLSingleStart", " >> Light " + iTLID + " Started (light+thread)");
				}
				else
				{
					if (tlContainer.lightStart())
						Debug.writeDebugMsg("schTLControl","procTLSingleStart", " >> Light " + iTLID + " Started (light ONLY)");
					else
						Debug.writeDebugMsg("schTLControl","procTLSingleStart", " >> Light " + iTLID + " already started");
				}
			}
			else
				Debug.writeDebugMsg("schTLControl","procTLSingleStart", " >> Could Not Start Light " + iTLID + " - Out of Range");
		}
		else
			Debug.writeDebugMsg("schTLControl","procTLSingleStart", " >> Could Not Start Light " + iTLID + " - No Definitions");
	}

	public void procTLStopAll(){
		for (int i = 0;i<this.vTL.size();i++){
			this.procTLSingleStop(i);
		}
	}

	public void procTLSingleStop(int iTLID){
		Debug.writeDebugMsg("schTLControl","procTLSingleStop", "Stopping Light: " + iTLID);
		//TODO 2MED [OPS]: Check light stopping (so when request made via cmd line)

		if (this.vTL.size()>0){
			if ((iTLID >= 0) && (iTLID <= vTL.size())){

				SchTLContainer light = (SchTLContainer) vTL.get(iTLID);

				if (light.lightStop()){
					Debug.writeDebugMsg("schTLControl","procTLSingleStop", "... Light has been stopped.");
				}
				else {
					Debug.writeDebugMsg("schTLControl","procTLSingleStop", "... Light ALREADY stopped.");
				}
			}
			else
				Debug.writeDebugMsg("schTLControl","procTLSingleStop", " >> Could Not Stop Light " + iTLID + " - Out of Range");
		}
		else
			Debug.writeDebugMsg("schTLControl","procTLSingleStop", " >> Could Not Stop Light " + iTLID + " - No Definitions");
	}

	public void procTLKillAll(){
		for (int i = 0;i<this.vTL.size();i++){
			this.procTLSingleKill(i);
		}
	}

	public void procTLSingleKill(int iTLID){
		Debug.writeDebugMsg("schTLControl","procTLSingleKill", "Killing Light: " + iTLID);
		//TODO 2MED [CHECK]: Check light KILLING (kills the thread)

		if (this.vTL.size()>0){
			if ((iTLID >= 0) && (iTLID <= vTL.size())){
				SchTLContainer light = (SchTLContainer) vTL.get(iTLID);

				light.lightKill();
			}
			else
				Debug.writeDebugMsg("schTLControl","procTLSingleKill", " >> Could Not Kill Light " + iTLID + " - Out of Range");
		}
		else
			Debug.writeDebugMsg("schTLControl","procTLSingleKill", " >> Could Not Kill Light " + iTLID + " - No Definitions");

	}

}
