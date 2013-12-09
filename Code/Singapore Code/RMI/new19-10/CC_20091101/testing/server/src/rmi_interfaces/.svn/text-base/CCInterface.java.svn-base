package rmi_interfaces;

import java.rmi.*;

public interface CCInterface extends Remote {

    // all subsystems can call this method to get IP address of another
    // subsystem with the specified ID - CME, DB, NAV, SCH, SU, TKR
    public String getIP(String ID) throws RemoteException;

//    // all subsystems to call this method to update their status
//    // ID - CME, DB, NAV, SCH, SU, TKR
//    // 0: Online, Currently Idling
//    // 1: Pre-Start Initiated, Currently in Progress
//    // 2: Pre-Start Completed, Ready to Start
//    // 3: Started, Currently in Operation
//    public void updateStatus(String ID, int state) throws RemoteException;
//
//    // provide the PBSS time to synchronise all simulation time of all subsystems
//    public long SimTimeShare() throws RemoteException;
//
//    // DB to call this method to update CC on log information
//    public void updateLog(String str) throws RemoteException;
}