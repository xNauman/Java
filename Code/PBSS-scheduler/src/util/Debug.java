package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import config.SysConfig;


public class Debug {
	private static boolean bDebugMode = true;

	public static void writeDebugMsg(String sClass, String sMethodCall, String sText){
		if (bDebugMode) {

			System.out.println(Formatter.formatDate(new Date()) + " " + sClass + "::" + sMethodCall + ": " +  sText);
		}
	}

	public static void writeLogEntry(SysLogTypeEnum pEventType, String pEventCode, String pEventCat, String pEventData){
		//TODO 4LOG [LOGGING-DB]: Will need to have another method to support upload to DB.

		return; //disabling logging due to security related issues 
		
		/*String sEntry = "";
		sEntry = appendLogEntry(sEntry, SysConfig.getSimRunID());
		sEntry = appendLogEntry(sEntry, Calendar.getInstance().getTimeInMillis());
		sEntry = appendLogEntry(sEntry, SysConfig.getModuleName());
		sEntry = appendLogEntry(sEntry, pEventType.getEventTypeID());
		sEntry = appendLogEntry(sEntry, pEventCat);
		sEntry = appendLogEntry(sEntry, pEventCode);
		sEntry = appendLogEntry(sEntry, pEventData);

		if (doesLogFileExist(true)){
			writeLogFileEntry(sEntry);
		}*/
	}

	public static void writeLogEntry(SysLogTypeEnum pEventType, String pEventCode, String pEventCat){
		writeLogEntry(pEventType, pEventCode, pEventCat, "");
	}

	public static void writeLogEntry(SysLogTypeEnum pEventType, String pEventCode, String pEventCat, Exception e){

		UUID newUUID = UUID.randomUUID();
		String sSTFile = newUUID.toString() + ".st.txt";
		sSTFile = sSTFile.replace("-", ""); //Remove the - char in GUIDs

		writeLogEntry(pEventType, pEventCode, pEventCat, "@SEE:" + sSTFile);
		sSTFile = General.pathSTDir(sSTFile);
		File file = new File(sSTFile);

		try {
			if (file.createNewFile()){
				BufferedWriter fileOut = new BufferedWriter(new FileWriter(sSTFile,false));
				PrintWriter pw = new PrintWriter(fileOut);
				
				e.printStackTrace(pw);
				pw.close();
				fileOut.close();
			}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		@SuppressWarnings("unused")
		private static String appendLogEntry(String sAppendTo, String s){
			return sAppendTo + '\t' + s; 
		}

		@SuppressWarnings("unused")
		private static String appendLogEntry(String sAppendTo, int i){
			return sAppendTo + '\t' + i; 
		}

		@SuppressWarnings("unused")
		private static String appendLogEntry(String sAppendTo, long l){
			return sAppendTo + '\t' + l; 
		}

		@SuppressWarnings("unused")
		private static boolean doesLogFileExist(boolean pCreateIfNotExist){
			File l = new File(General.pathLogFile());

			if (l.exists()){
				return true;
			}
			else{
				if (pCreateIfNotExist){
					try {
						l.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					writeLogFileEntry("#Public Bus Simulator System (PBSS)");
					writeLogFileEntry("#Scheduler Module");
					writeLogFileEntry("#Team Sydney (Macqaurie University/ISYS346)");
					writeLogFileEntry("#System Log File");
					writeLogFileEntry("#Create Date: " + Formatter.formatDate());
					writeLogFileEntry("#");

					return true;
				}
				else
				{
					return false;
				}
			}
		}

		private static boolean writeLogFileEntry(String s){
			File l = new File(General.pathLogFile());

			if (l.exists()){
				if (l.canWrite()){
					try {
						BufferedWriter fileOut = new BufferedWriter(new FileWriter(General.pathLogFile(),true));
						fileOut.write(s);
						fileOut.newLine();
						fileOut.close();
					} catch (IOException e) {
						e.printStackTrace();
						return false;
					}

					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}


	}
