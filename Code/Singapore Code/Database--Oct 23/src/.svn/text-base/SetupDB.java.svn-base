import holder.ImageSerializer;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author DBteam
 */

public class SetupDB extends MainDB{
    public SetupDB() throws SQLException{
        useDB();
    }
    //Get map without image
    public Vector SQL_getMap() throws SQLException, IOException, ClassNotFoundException{
        Vector t = this.SQL_getMapList();
        int i=0;
        Vector<rawobjects.Map> map = new Vector();
        while(i<t.size()){
            String mapName =(String)t.get(i);
            int mapID = getMapID(mapName);
            BufferedImage temp =this.SQL_get_MapImage((String)t.get(i));
            int dm_width = temp.getWidth();
            int dm_height = temp.getHeight();
            Vector<rawobjects.Route> route = get_Route(mapID);
            Vector<rawobjects.BusStop> busStop = SQL_getBusStop(mapID);
            rawobjects.Map tempMap = new rawobjects.Map(mapID, mapName, null);
            tempMap.busStops = busStop;
            tempMap.routes = route;
            map.add(tempMap);
            i++;
        }
        return map;
    }
    //Get Map with Image
    public Vector SQL_getMapWithImage() throws SQLException, IOException, ClassNotFoundException{
        Vector t = this.SQL_getMapList();
        int i=0;
        Vector<rawobjects.Map> map = new Vector();
        while(i<t.size()){
            String mapName =(String)t.get(i);
            int mapID = getMapID(mapName);
            BufferedImage temp =this.SQL_get_MapImage((String)t.get(i));
            int dm_width = temp.getWidth();
            int dm_height = temp.getHeight();
            Vector<rawobjects.Route> route = get_Route(mapID);
            Vector<rawobjects.BusStop> busStop = SQL_getBusStop(mapID);
            rawobjects.Map tempMap = new rawobjects.Map(mapID, mapName, temp);
            tempMap.busStops = busStop;
            tempMap.routes = route;
            map.add(tempMap);
            i++;
        }
        return map;
    }
    //Update bus
    public void updateBus(Vector<rawobjects.Bus> busVector) throws SQLException{
        int i=0;
        while(i<busVector.size()){
            this.update_bus(busVector.get(i));
            i++;
        }
    }
    public void updateBusStop(Vector<rawobjects.BusStop> busStop) throws SQLException{
        int i=0;
        while(i<busStop.size()){
            this.update_Bus_Stop(busStop.get(i));
            i++;
        }
    }
    public void passSimRun(rawobjects.SYS_SIMULATOR_RUN sys) throws SQLException{
        insert_SIM_RUN(sys);
    }
    protected void insert_SIM_RUN(rawobjects.SYS_SIMULATOR_RUN temp) throws SQLException
        {
            String exCode = "INSERT INTO SYS_SIMULATION_RUN(SimRunID,MapID,StartDate,EndDate,StartSignal,CMEStatus,TRKStatus,DBStatus,SUStatus,SCHStatus,NAVStatus"+" VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement pre = connection.prepareStatement(exCode);
            pre.setInt(1, temp.getSimRunID());
            pre.setInt(2,temp.getMapID());
            pre.setTimestamp(3,temp.getStartDate());
            pre.setTimestamp(4,temp.getEndDate());
            pre.setBoolean(5,temp.getStartSignal());
            pre.setBoolean(6,temp.getCMEStatus());
            pre.setBoolean(7,temp.getTRKStatus());
            pre.setBoolean(8,temp.getDBStatus());
            pre.setBoolean(9,temp.getSUStatus());
            pre.setBoolean(10,temp.getSCHStatus());
            pre.setBoolean(11,temp.getNAVStatus());
            pre.executeUpdate();
            pre.close();
        }
    protected void insertBus(Vector<rawobjects.Bus> bus) throws SQLException{
        int i=0;
        while(i<bus.size()){
            rawobjects.Bus temp = bus.get(i);
            String exCode = "Insert into bus(busID,RouteID,BusTypeID,CurrLoad,CurrPointX,CurrPointY,MovePointX,MovePointY,MoveReqState,MoveReqTimeStamp,DefaultLoad) VALUE(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pt = connection.prepareStatement(exCode);
            pt.setInt(1, temp.getBusID());
            pt.setInt(2, temp.getRouteID());
            pt.setInt(3, temp.getBusType().getBusTypeID());
            pt.setInt(4, temp.getCurrLoad());
            pt.setInt(5, temp.getCurrentPointX());
            pt.setInt(6, temp.getCurrentPointY());
            pt.setInt(7, temp.getMoveToPointX());
            pt.setInt(8, temp.getMoveToPointY());
            pt.setInt(9, temp.getMoveRequestState());
            pt.setLong(10, temp.getTimeStamp());
            pt.setShort(11,(short) temp.getDefaultLoad());
            i++;
        }
    }
     public rawobjects.Bus_Type SQL_getBusType(int BusTypeID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT BusTypeID,BusType,BusColour,MaxCapacity,BusImage,Bt_Width,Bt_Height FROM Bus_Type WHERE BusTypeID?";
    PreparedStatement pre = connection.prepareStatement(exCode);
    pre.setInt(1, BusTypeID);
    ResultSet rs = pre.executeQuery();

    //Vector<rawobjects.Bus_Type> bustype = new Vector();
    //int i=0;

    //while(rs.next()){

        byte[] byteStream = (byte[]) rs.getObject(5);
        ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
        ObjectInputStream ois = new ObjectInputStream(baip);
        int[] array = (int[]) ois.readObject();
        BufferedImage tempimg = ImageSerializer.intArrayToBufferedImage(array, rs.getInt(6),rs.getInt(7));

        rawobjects.Bus_Type temp = new rawobjects.Bus_Type(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), tempimg, rs.getInt(6), rs.getInt(7));
        //bustype.add(temp);
        //i++;
    //}
    return temp;
}

    protected rawobjects.SYS_SIMULATOR_RUN getSysRun(int id) throws SQLException{
        String exCode = "Select * from SYS_SIMULATION_RUN where SimRunID=?";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1, id);
        ResultSet rs = pre.executeQuery();
        rs.next();
        rawobjects.SYS_SIMULATOR_RUN temp = new rawobjects.SYS_SIMULATOR_RUN();
        temp.setSimRunID(rs.getInt(1));
        temp.setStartDate(rs.getTimestamp(2));
        temp.setEndDate(rs.getTimestamp(3));
        temp.setMapID(rs.getInt(4));
        temp.setStartSignal(rs.getBoolean(5));
        temp.setCMEStatus(rs.getBoolean(6));
        temp.setTRKStatus(rs.getBoolean(7));
        temp.setDBStatus(rs.getBoolean(8));
        temp.setSUStatus(rs.getBoolean(9));
        temp.setSCHStatus(rs.getBoolean(10));
        temp.setNAVStatus(rs.getBoolean(11));
        return temp;
    }
    protected rawobjects.Pax_Stat getPaxStat() throws SQLException{
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("Select * from pax_stat;");
        rs.next();
        rawobjects.Pax_Stat temp = new rawobjects.Pax_Stat(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
        return temp;
    }
    protected void passTraFreq(int TraFreq) throws SQLException{
      String exCode="update traffic_light set ChangeFreq=?";
      PreparedStatement statement = connection.prepareStatement(exCode);
      statement.setInt(1, TraFreq);
      statement.executeUpdate();
      statement.close();
      //System.out.println("Update bus table succesful");
    }
    
    /*Test*/
    public static void main(String[]args) throws SQLException, IOException, ClassNotFoundException{

        SetupDB test = new SetupDB();
        test.createDB();
        test.useDB();
        test.create_Table();
        Vector<rawobjects.Map> temp = test.SQL_getMap();
    }
}
