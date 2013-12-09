/**
 *
 * @author Nurfitria
 */

import java.io.*;
import java.sql.*;
import java.util.*;

public class ccDB extends MainDB {
    
    public void insert_CC(rawobjects.SYS_LOG sys_log)throws SQLException{
        String excode = "INSERT INTO SYS_LOG(EntryID,SimRunID,LogDateTime,SourceModule,EventTypeID,EventCode,EventData)VALUES (?,?,?,?,?,?,?)";

      PreparedStatement statement = connection.prepareStatement(excode);
      statement.setInt(1, sys_log.getEntryID());
      statement.setInt(2, sys_log.getSimRunID());
      statement.setTimestamp(3, sys_log.getLogDateTime());
      statement.setString(4, sys_log.getSourceModule());
      statement.setString(6, sys_log.getEventCode());
      statement.setString(7, sys_log.getEventData());
      statement.setInt(5, sys_log.getEventTypeID());

      statement.executeUpdate();
      statement.close();
    }

    public rawobjects.SYS_LOG getSys_Log(int entryID) throws SQLException{
        String excode = "SELECT * FROM SYS_LOG WHERE EntryID = ?";
        PreparedStatement pre = connection.prepareStatement(excode);
        pre.setInt(1, entryID);
        ResultSet rs = pre.executeQuery();
        rs.next();
        rawobjects.SYS_LOG temp = new rawobjects.SYS_LOG();
        temp.setEntryID(rs.getInt(1));
        temp.setSimRunID(rs.getInt(2));
        temp.setLogDateTime(rs.getTimestamp(3));
        temp.setSourceModule(rs.getString(4));
        temp.setEventTypeID(rs.getInt(5));
        temp.setEventCode(rs.getString(6));
        temp.setEventData(rs.getString(7));

        return temp;
    }
//public String CC_LOG (){}// what DB is doing
//
public Timestamp gettime(int entryID)throws SQLException,IOException,ClassNotFoundException{  // get DB's time
    String excode = "SELECT LogDateTime FROM Sys_Log WHERE EntryID = ?";
    PreparedStatement pre = connection.prepareStatement(excode);
    pre.setInt(1,entryID);
    ResultSet rs = pre.executeQuery();
    rs.next();
    
    rawobjects.SYS_LOG temp = new rawobjects.SYS_LOG();
    temp.setLogDateTime(rs.getTimestamp(1));

    return null;

}
//
public boolean CC_Status(String SimRunID) throws SQLException{ // Check DB's ready or not when doing prestart,also can check other subsystem status after cc put in
    
    String excode = "SELECT StartSignal FROM SYS_SIMULATOR_RUN WHERE SimRunID = ?";
      PreparedStatement pre = connection.prepareStatement(excode);
        pre.setString(1, SimRunID);
        ResultSet rs = pre.executeQuery();
        rs.next();
        
        rawobjects.SYS_SIMULATOR_RUN temp = new rawobjects.SYS_SIMULATOR_RUN();
        temp.setStartSignal(rs.getBoolean(1));
        
        return true;
}

public Vector CC_bus_stop()throws SQLException{    // final passenger data for every busstop

    Vector paxData_BS = new Vector();
    String excode = "SELECT PaxCount_Count FROM Bus_Stop";
    Statement st = connection.createStatement();
    ResultSet rs = st.executeQuery(excode);
    while(rs.next()){
        paxData_BS.add(rs.getString(1));
    }
    return paxData_BS;
}
//
public Vector CC_pax_stat()throws SQLException{    // final passenger data for every simrun, bus, busstop

    Vector paxData_PS = new Vector();
    String excode = "SELECT Count FROM Pax_Stat";
    Statement st = connection.createStatement();
    ResultSet rs = st.executeQuery(excode);
    while(rs.next()){
        paxData_PS.add(rs.getString(1));
    }
    return paxData_PS;
}

public static void main(String[] args)throws SQLException, IOException,ClassNotFoundException{
    ccDB test = new ccDB();
    test.createDB();
    test.create_Table();
    
}


}
