/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author chak0003
 */
package setup;

import java.sql.*;

public class DBConnection {

    private Connection dbConnection;
    private String dbFile;

    public DBConnection(String dbFile) {
        this.dbFile = dbFile;
        loadDriver();
        connectDatabase();
    }

    private void loadDriver() {

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        } catch (ClassNotFoundException err) {
            System.out.println("Could not load driver ");
            System.exit(1);
        }
    }

    private void connectDatabase() {
        try {
            dbConnection = DriverManager.getConnection("jdbc:odbc:" + dbFile);
        } catch (SQLException error) {
            System.err.println("Error connecting to database: " + error.toString());
        }
    }

    public void closeConnection() {
        try {
            dbConnection.close();
        } catch (SQLException error) {
            System.err.println("Cannot disconnect database");
        }
    }

    public SetupDataObj getUserConfig(int mapID) {
        SetupDataObj su = new SetupDataObj();
        int[] busInService = getUserBusInService(mapID);
        int[] disFreq = getUserDispatchFreq(mapID);
        Route[] routeList = new Route[busInService.length];
        su.setMapID(mapID);
        su.setFlavour(getUserFlavour());
        su.setTrafFreq(getUserTrafFreq());
        for (int i = 0; i < routeList.length; i++) {
            routeList[i] = new Route();
            routeList[i].setDispatchFreq(disFreq[i]);
            routeList[i].setNoOfBus(busInService[i]);
        }
        su.setRouteList(routeList);
        return su;
    }

    public SetupDataObj getSystemConfig(int mapID) {
        SetupDataObj su = new SetupDataObj();
        int[] busInService = getSystemBusInService(mapID);
        int[] disFreq = getSystemDispatchFreq(mapID);
        Route[] routeList = new Route[busInService.length];
        su.setMapID(mapID);
        su.setFlavour(getSystemFlavour());
        su.setTrafFreq(getSystemTrafFreq());
        for (int i = 0; i < routeList.length; i++) {
            routeList[i] = new Route();
            routeList[i].setDispatchFreq(disFreq[i]);
            routeList[i].setNoOfBus(busInService[i]);
        }
        su.setRouteList(routeList);
        return su;
    }

    public void setUserConfig(SetupDataObj su) {
        Route[] routeList = su.getRouteList();
        setUserFlavour(su.getFlavour());
        setUserTrafFreq(su.getTrafFreq());
        int[] busInService = new int[routeList.length];
        int[] disFreq = new int[routeList.length];
        for (int i = 0; i < routeList.length; i++) {
            busInService[i] = routeList[i].getNoOfBus();
            disFreq[i] = routeList[i].getDispatchFreq();
        }
        setUserBusInService(busInService, su.getMapID());
        setUserDispatchFreq(disFreq, su.getMapID());
    }

    public int getSystemTrafFreq() {
        int trafFreq = 0;
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT val FROM DefaultVal WHERE item='trafFreq' AND defType=0");

            while (rs.next()) {
                trafFreq = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println
                    ("Error retrieving from DefaultVal Table: " + error.toString());
            closeConnection();
        }
        closeConnection();
        return trafFreq;
    }

    public int getUserTrafFreq() {
        int trafFreq = 0;
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT val FROM DefaultVal WHERE item='trafFreq' AND defType=1");

            while (rs.next()) {
                trafFreq = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println
                    ("Error retrieving from DefaultVal Table: " + error.toString());
            closeConnection();
        }
        closeConnection();
        return trafFreq;
    }

    public int getSystemFlavour() {
        int flavour = 0;
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT val FROM DefaultVal WHERE item='flavour' AND defType=0");

            while (rs.next()) {
                flavour = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println
                    ("Error retrieving from DefaultVal Table: " + error.toString());
            closeConnection();
        }
        closeConnection();
        return flavour;
    }

    public int getUserFlavour() {
        int flavour = 0;
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT val FROM DefaultVal WHERE item='flavour' AND defType=1");

            while (rs.next()) {
                flavour = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println
                    ("Error retrieving from DefaultVal Table: " + error.toString());
            closeConnection();
        }
        closeConnection();
        return flavour;
    }

    public int[] getSystemBusInService(int mapID) {
        int[] busInService = new int[10];
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT busNo FROM BusInService WHERE mapID=" 
                    + mapID + " AND defType=0");
            int i = 0;
            while (rs.next()) {
                busInService[i++] = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println
                    ("Error retrieving from BusInService Table: " + error.toString());
            closeConnection();
        }
        closeConnection();
        return busInService;
    }

    public int[] getUserBusInService(int mapID) {
        int[] busInService = new int[10];
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT busNo FROM BusInService WHERE mapID=" 
                    + mapID + " AND defType=1");
            int i = 0;
            while (rs.next()) {
                busInService[i++] = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println("Error retrieving from BusInService Table: " 
                    + error.toString());
            closeConnection();
        }
        closeConnection();
        return busInService;
    }

    public int[] getSystemDispatchFreq(int mapID) {
        int[] dispatchFreq = new int[10];
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT freq FROM DispatchFreq WHERE mapID=" 
                    + mapID + " AND defType=0");
            int i = 0;
            while (rs.next()) {
                dispatchFreq[i++] = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println("Error retrieving from DispatchFreq Table: " 
                    + error.toString());
            closeConnection();
        }
        closeConnection();
        return dispatchFreq;
    }

    public int[] getUserDispatchFreq(int mapID) {
        int[] dispatchFreq = new int[10];
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT freq FROM DispatchFreq WHERE mapID=" 
                    + mapID + " AND defType=1");
            int i = 0;
            while (rs.next()) {
                dispatchFreq[i++] = rs.getInt(1);
            }

        } catch (SQLException error) {
            System.err.println("Error retrieving from DispatchFreq Table: " 
                    + error.toString());
            closeConnection();
        }
        closeConnection();
        return dispatchFreq;
    }

    public float[] getFare(int mapID) {
        float[] fare = new float[10];
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();

            ResultSet rs = stmt.executeQuery
                    ("SELECT fare FROM DispatchFreq WHERE mapID=" 
                    + mapID + " AND defType=1");
            int i = 0;
            while (rs.next()) {
                fare[i++] = rs.getFloat(1);
            }

        } catch (SQLException error) {
            System.err.println
                    ("Error retrieving from DispatchFreq (fare) Table: " 
                    + error.toString());
            closeConnection();
        }
        closeConnection();
        return fare;
    }

    public int setUserTrafFreq(int freq) {
        if (freq <= 0 || freq > 30) {
            return -2;
        }
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();
            stmt.executeUpdate("UPDATE DefaultVal SET val=" + freq 
                    + " WHERE defType=1 AND item='trafFreq'");


        } catch (SQLException error) {
            System.err.println("Error updating DefaultVal (freq) Table: " 
                    + error.toString());
            closeConnection();
            return -1;

        }
        closeConnection();
        return 0;
    }

    public int setUserFlavour(int flavour) {
        if (flavour != 0 && flavour != 1) {
            return -2;
        }
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();
            stmt.executeUpdate("UPDATE DefaultVal SET val=" + flavour 
                    + " WHERE defType=1 AND item='flavour'");
        } catch (SQLException error) {
            System.err.println("Error updating DefaultVal (flavour) Table: " 
                    + error.toString());
            closeConnection();
            return -1;
        }
        closeConnection();
        return 0;
    }

    public int setUserBusInService(int[] noOfBus, int mapID) {
        for (int i = 0; i < noOfBus.length; i++) {
            if (noOfBus[i] < 0 || noOfBus[i] > 10) {
                return -2;
            }
        }
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();
            for (int i = 0; i < noOfBus.length; i++) {
                stmt.executeUpdate("UPDATE BusInService SET busNo=" + noOfBus[i] 
                        + " WHERE defType=1 AND mapID=" + mapID 
                        + " AND routeID=" + (i + 1));
            }
            closeConnection();
        } catch (SQLException error) {
            System.err.println("Error updating BusInService Table: " 
                    + error.toString());
            closeConnection();
            return -1;
        }
        return 0;
    }

    public int setUserDispatchFreq(int[] freq, int mapID) {
        for (int i = 0; i < freq.length; i++) {
            if (freq[i] <= 0 || freq[i] > 30) {
                return -2;
            }
        }
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();
            for (int i = 0; i < freq.length; i++) {
                stmt.executeUpdate("UPDATE DispatchFreq SET freq=" + freq[i] 
                        + " WHERE defType=1 AND mapID=" + mapID + " AND routeID=" 
                        + (i + 1));
            }
        } catch (SQLException error) {
            System.err.println("Error updating DispatchFreq Table: " + error.toString());
            closeConnection();
            return -1;
        }
        closeConnection();
        return 0;
    }

    public int setFare(float[] fare, int mapID) {
        for (int i = 0; i < fare.length; i++) {
            if (fare[i] < 0) {
                return -2;
            }
        }
        connectDatabase();
        try {
            Statement stmt = dbConnection.createStatement();
            for (int i = 0; i < fare.length; i++) {
                stmt.executeUpdate("UPDATE DispatchFreq SET fare=" + fare[i] 
                        + " WHERE defType=1 AND mapID=" + mapID 
                        + " AND routeID=" + (i + 1));
            }
        } catch (SQLException error) {
            System.err.println("Error updating DispatchFreq (fare) Table: " 
                    + error.toString());
            closeConnection();
            return -1;
        }
        closeConnection();
        return 0;
    }
}

