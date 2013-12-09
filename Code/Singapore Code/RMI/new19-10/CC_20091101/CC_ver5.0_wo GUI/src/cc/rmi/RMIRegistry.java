package cc.rmi;

import java.rmi.*;
import java.rmi.registry.*;
import rmi_interfaces.*;

public class RMIRegistry {

    private RMIServer CC;
    private int port = 4445;

    public RMIRegistry(RMIServer CC) {

        this.CC = CC;
    }

    public CMEInterface getCME() {//throws Exception {

        try {
            Registry CMEregistry = LocateRegistry.getRegistry(CC.getIP("CME"), port);
            CMEInterface CME = (CMEInterface) CMEregistry.lookup("CME");

            System.out.println("CME RMI Server found.");

            return CME;
        }
        catch (NotBoundException e) {
            System.err.println(e);
            return null;
        }
        catch (RemoteException e) {
            System.err.println(e);
            return null;
        }
    }

    public DBInterface getDB() {//throws Exception {

        try {
            Registry DBregistry = LocateRegistry.getRegistry(CC.getIP("DB"), port);
            DBInterface DB = (DBInterface) DBregistry.lookup("DB");

            System.out.println("DB RMI Server found.");

            return DB;
        }
        catch (NotBoundException e) {
            System.err.println(e);
            return null;
        }
        catch (RemoteException e) {
            System.err.println(e);
            return null;
        }
    }

    public NAVInterface getNAV() {//throws Exception {

        try {
            Registry NAVregistry = LocateRegistry.getRegistry(CC.getIP("NAV"), port);
            NAVInterface NAV = (NAVInterface) NAVregistry.lookup("NAV");

            System.out.println("NAV RMI Server found.");

            return NAV;
        }
        catch (NotBoundException e) {
            System.err.println(e);
            return null;
        }
        catch (RemoteException e) {
            System.err.println(e);
            return null;
        }
    }

    public SCHInterface getSCH() {//throws Exception {

        try {
            Registry SCHregistry = LocateRegistry.getRegistry(CC.getIP("SCH"), port);
            SCHInterface SCH = (SCHInterface) SCHregistry.lookup("SCH");

            System.out.println("SCH RMI Server found.");

            return SCH;
        }
        catch (NotBoundException e) {
            System.err.println(e);
            return null;
        }
        catch (RemoteException e) {
            System.err.println(e);
            return null;
        }
    }

    public SUInterface getSU() {//throws Exception {

        try {
            Registry SUregistry = LocateRegistry.getRegistry(CC.getIP("SU"), port);
            SUInterface SU = (SUInterface) SUregistry.lookup("SU");

            System.out.println("SU RMI Server found.");

            return SU;
        }
        catch (NotBoundException e) {
            System.err.println(e);
            return null;
        }
        catch (RemoteException e) {
            System.err.println(e);
            return null;
        }
    }

    public TKRInterface getTKR() {//throws Exception {

        try {
            Registry TKRregistry = LocateRegistry.getRegistry(CC.getIP("TKR"), port);
            TKRInterface TKR = (TKRInterface) TKRregistry.lookup("TKR");

            System.out.println("TKR RMI Server found.");

            return TKR;
        }
        catch (NotBoundException e) {
            System.err.println(e);
            return null;
        }
        catch (RemoteException e) {
            System.err.println(e);
            return null;
        }
    }
}
