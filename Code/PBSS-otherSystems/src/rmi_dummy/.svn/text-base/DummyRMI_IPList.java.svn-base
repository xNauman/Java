package rmi_dummy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import util.General;

public class DummyRMI_IPList {

	//Set this to where the BCast Server is - usually this runs the other dummy modules
	private static String mCCIPHost = "137.111.235.153"; //"137.111.235.104";
	
	/*
	 * NOTE: If a module is not been running on a machine of its own, get it to return the
	 * IP for the module you are using
	 */

	public static String getIP_CC(){
		//return getIPText("CC");
		return mCCIPHost;
	}
	
	public static String getIP_SCH(){
		//return getIPText("SCH");
		return "137.111.235.163"; 
	}

	public static String getIP_NAV(){
		//return getIPText("NAV");
		
		//return "137.111.235.164";
		return mCCIPHost;
	}

	public static String getIP_DB(){
		return mCCIPHost;
		//return getIPText("CC"); //In test, done on SCH machine
	}

	public static String getIP_TKR(){
		return mCCIPHost;
		//return getIPText("CC");
	}

	public static String getIP_SETUP(){
		return mCCIPHost;
		//return getIPText("CC");
	}

	public static String getIP_CME(){
		return mCCIPHost;
		//return getIPText("CME");
	}

	private static String getIPFileName(String pModName){
		return General.pathDataFileIPList(pModName + "_ip.txt");
	}
	
	public static void saveIP(String pModName, String pIP){
		System.out.println(pModName + " -- " + pIP);
		saveIPText(pModName.toUpperCase(),pIP);
	}
	
	private static void saveIPText(String pModName, String pIP){

		String sFilename = getIPFileName(pModName);

		File l = new File(sFilename);

		try {
			l.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (l.canWrite()){
			BufferedWriter fileOut;
			try {
				fileOut = new BufferedWriter(new FileWriter(sFilename,false));
				fileOut.write(pIP);
				fileOut.newLine();
				fileOut.close();	
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

	}

	private static String getIPText(String pModName){
		
		String sFilename = getIPFileName(pModName);
		
		File l = new File(sFilename);
		
		if (l.canRead()){
			BufferedReader fileIn;
			try {
				fileIn = new BufferedReader(new FileReader(sFilename));
				String s = null;
				while ((s = fileIn.readLine())!=null){
					return s;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return "127.0.0.1"; //as must have reached null
		}
		else 
			return "127.0.0.1";
		
	}

}
