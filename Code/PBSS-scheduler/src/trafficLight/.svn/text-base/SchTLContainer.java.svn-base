package trafficLight;

/**
 * Contains a Traffic Light Thread Object, and very basic
 * information needed by PBSS SCH to manage the object.
 * 
 * @author Ben
 *
 */
public class SchTLContainer {

	private Thread threadTL;
	private int mIDDB; 	//Traffic Light ID in the Database
	private int mIDLocal; //Local ID (often the array reference)
	private SchTLObjTHD newLight;
	
	public SchTLContainer(rawobjects.TrafficLight pTL, int pIDLocal, int pIDDB, int pChangeFreq, boolean pUpdateUsingRMI, int pDirectionCurr, int pDirectionMax){
		this.mIDLocal=pIDLocal;
		this.mIDDB=pIDDB;
		
		newLight = new SchTLObjTHD(pTL, pIDLocal, pUpdateUsingRMI);
        newLight.setFrequency(pChangeFreq);
        newLight.setDirectionCurr(pDirectionCurr);
        newLight.setDirectionMax(pDirectionMax);
        
        threadTL = new Thread(newLight);
	}

	public boolean lightStart(){
		if (newLight.getIsActive()&&this.threadTL.isAlive()){
			return false; //the light is active, and so is the thread
		}
		newLight.setIsActive(true);
		
		if (!this.threadTL.isAlive()){
			threadTL.start();
		}
		
		return true;
	}
	
	
	public boolean lightStop(){
		if (newLight.getIsActive()) {
			newLight.setIsActive(false);
			
			return true; //light has been stopped
		}
		else {
			return false; //light has already been stopped
		}
			
	}
	
	public boolean lightKill(){
		newLight.doKillLight();
		return true;
	}
	
	
	
	
	/*private void threadStart(){
		this.threadTL.start();
	}*/
	
	public Thread getThread(){
		return this.threadTL;
	}
	
	public int getIDDB(){
		return this.mIDDB;
	}
	
	public int getIDLocal(){
		return this.mIDLocal;
	}

}
