/**
 *
 * @author DBteam
 */
import holder.ImageSerializer;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class MainDB {
    Connection connection = getConnection();
    private void displaySQLErrors(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }
    public MainDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception e) {
            System.err.println("Unable to find and load driver");
            System.exit(1);
        }
    }    
    public Connection getConnection() {
        try {
            String name = "root";
            String pass = "123456";
            connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user="+name+"&password="+pass);
            return connection;
        }
        catch(SQLException e) {
        displaySQLErrors(e);
        return null;
        }
    }
    protected int getMapID(String mapName) throws SQLException{
        String exCode = "Select mapID from map where mapName ='"+mapName+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(exCode);
        rs.next();
        return rs.getInt(1);
    }
    protected int getRouteID(String routeName) throws SQLException{
        String exCode = "Select ID from route where RouteName ='"+routeName+"';";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(exCode);
        rs.next();
        return rs.getInt(1);
    }

    protected void useDB() throws SQLException{
            Statement statement = connection.createStatement();
            String dbName = "test";
            statement.execute("use "+dbName+";");
    }
    protected void createDB() throws SQLException{
            Statement statement = connection.createStatement();
            String dbName = "test";
            String SQL = "CREATE rawobjects "+dbName;
            statement.execute(SQL);
            useDB();
        }
    protected void create_Table(){
        try{
            Statement statement = connection.createStatement();
            String map = "create table map (MapID int primary key auto_increment, MapName varchar(50),map_object Longblob, Dm_Width int, Dm_Height int, Mapimg longblob, ts timestamp) type=innodb;";
            String BUS_STOP="create table bus_stop (BusStopID int primary key auto_increment,busStopName varchar(50), MapID int, PhysPointX int, PhysPointY int, LogPointX int, LogPointY int, PaxCount_Start int, PaxCount_Min int, PaxCount_Max int, PaxCount_Count int, Foreign Key (MapID) references map(MapID), ts timestamp) type=innodb;";
            String MAP_CONGESTED="CREATE TABLE Map_Congested (ID int primary key auto_increment,CongestedAreaID int , MapID int, PointX_A int, PointY_A int, PointX_B int, PointY_B int, foreign key(MapID) references Map(MapID),ts timestamp)type=innodb;";
            String DEPOT="create table depot (ID int primary key auto_increment,DepotID int, MapID int, PhysPointX int, PhysPointY int, LogPointX int, LogPointY int, Foreign key (MapID) references map(MapID), ts timestamp) type=innodb;";
            String Building="CREATE TABLE Building (ID int primary key auto_increment,BuildingID int, MapID int, PointX_A int, PointY_A int, PointX_B int, PointY_B int, foreign key(MapID) references Map(MapID),ts timestamp)type=innodb;";
            String ROUTE="create table route (ID int primary key auto_increment, MapID int, RouteNbr varchar(5), RouteName varchar(100), DepartEvery int, StartDepotID int, EndDepotID int, Foreign key (MapID) references map(MapID),ts timestamp) type=innodb;";
            String ROUTE_POINT="create table route_point (RouteDefID int primary key auto_increment, RouteID int, SeqOrder int, PointX int, PointY int, Foreign key (RouteID) references route(ID), ts timestamp) type=innodb;";
            String BUS="CREATE TABLE Bus (BusID int primary key, RouteID int, BusTypeID int, CurrLoad int, CurrPointX int, CurrPointY int, MovePointX int, MovePointY int, MoveReqState int, MoveReqTimeStamp datetime, DefaultLoad int,Operating bit, foreign key(RouteID) references route(ID),foreign key(BusTypeID) references Bus_Type(BusTypeID),ts timestamp) type=innodb;";
            String BUS_TYPE="CREATE TABLE Bus_Type (BusTypeID int primary key, BusType varchar(25), BusColour int, MaxCapacity int, BusImage blob,ts timestamp) type=innodb;";
            String SYS_SIMULATOR_RUN ="CREATE TABLE Sys_Simulator_Run (SimRunID int primary key, StartDate datetime, EndDate datetime, MapID int, StartSignal bit, CMEStatus bit, TRKStatus bit, DBStatus bit, SUStatus bit, SCHStatus bit, NAVStatus bit, foreign key(MapID) references Map(MapID),ts timestamp) type=innodb;";
            String PAX_STAT="create table pax_stat (PaxStatID int primary key, SimRunID int, BusStopID int, BusID int, Count int, Foreign key (SimRunID) references SYS_SIMULATOR_RUN(SimRunID), Foreign key (BusStopID) references bus_stop(BusStopID), Foreign key (BusID) references bus(BusID),ts timestamp) type=innodb;";
            String TRAFFIC_LIGHT="create table traffic_light (TrafficLightID int primary key, MapID int, PointX int, PointY int, ChangeFreq int, IsJunction bit, LastChange datetime, CurrDirection int, MaxDirection int, Foreign key (MapID) references map(MapID), ts timestamp) type=innodb;";
            String TRAFFIC_LIGHT_ANGLE="create table traffic_light_angle (AngleID int primary key auto_increment, TrafficLightID int, Direction int,Angle double,Foreign key (TrafficLightID) references traffic_light(TrafficLightID), ts timestamp) type=innodb;";
            statement.execute(map);
            statement.execute(BUS_STOP);
            statement.execute(MAP_CONGESTED);
            statement.execute(DEPOT);
            statement.execute(Building);
            statement.execute(ROUTE);
            statement.execute(ROUTE_POINT);
            statement.execute(BUS_TYPE);
            statement.execute(BUS);
            statement.execute(SYS_SIMULATOR_RUN);
            statement.execute(PAX_STAT);
            statement.execute(TRAFFIC_LIGHT);
            statement.execute(TRAFFIC_LIGHT_ANGLE);
        }
        catch(SQLException e) {
            e.getMessage();
        }
    }

/*All the update wil be put here*/
    public void update_bus(rawobjects.Bus Bus) throws SQLException{

    String exCode="UPDATE bus SET CurrPointX=?, CurrPointY=?, CurrLoad=?, MovePointX=?, MovePointY=?, MoveReqState=?, MoveReqTimeStamp=? WHERE bus.BusID= ?";
    PreparedStatement statement = connection.prepareStatement(exCode);
    statement.setInt(1, Bus.getCurrentPointX());
    statement.setInt(2, Bus.getCurrentPointY());
    statement.setInt(3, Bus.getCurrLoad());
    statement.setInt(4, Bus.getMoveToPointX());
    statement.setInt(5, Bus.getMoveToPointY());
    statement.setInt(6, Bus.getMoveRequestState());
    statement.setLong(7, Bus.getTimeStamp());
    statement.setInt(8, Bus.getBusID());

    statement.executeUpdate();
    statement.close();
    //System.out.println("Update bus table succesful");
  }
    public void update_Bus_Stop(rawobjects.BusStop Bus_Stop) throws SQLException{
      String exCode="UPDATE bus_stop SET PaxCount_Start=?, PacCount_Min=?, PaxCount_Max=?, PaxCount_Count=? WHERE bus_stop.BusStopID=?";
      PreparedStatement statement = connection.prepareStatement(exCode);

      statement.setInt(1,Bus_Stop.getPX_Start());
      statement.setInt(2,Bus_Stop.getPX_Min());
      statement.setInt(3,Bus_Stop.getPX_Max());
      statement.setInt(4,Bus_Stop.getPX_Count());

      statement.setInt(5,Bus_Stop.getBusStopID());

      statement.executeUpdate();
      statement.close();

  }
    public void update_traffic_light(rawobjects.TrafficLight tl) throws SQLException{
      String exCode="update traffic_light set PointX=?, PointY=?, ChangeFreq=?, IsJunction=?, LastChange=?, CurrDirection=?, Maxdirection=? where traffic_light.TrafficLightID= ?";
      PreparedStatement statement = connection.prepareStatement(exCode);
      statement.setInt(1, tl.getTrafficLightLocationX());
      statement.setInt(2, tl.getTrafficLightLocationY());
      statement.setInt(3, tl.getChangeFreq());
      statement.setBoolean(4, tl.getIsJunction());
      statement.setLong(5, tl.getLastChange());
      statement.setInt(6, tl.getCurrDirection());
      statement.setLong(7, tl.getMaxDirection());
      statement.setInt(8, tl.getTrafficLightId());
      statement.executeUpdate();
      statement.close();
      //System.out.println("Update bus table succesful");
  }

 /* All the insert will be put here.*/
    protected void insert_bus_stop(rawobjects.BusStop temp,int MapID) throws SQLException
        {
            String exCode = "INSERT INTO BUS_STOP(BusStopName, MapID, PhysPointX, PhysPointY, LogPointX, LogPointY, PaxCount_Start, PaxCount_Min, PaxCount_Max, PaxCount_Count)"+" VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement pre = connection.prepareStatement(exCode);
            pre.setString(1, Integer.toString(temp.getBusStopID()));
            pre.setInt(2,MapID);
            pre.setInt(5,temp.getLogPoint().x);
            pre.setInt(6,temp.getLogPoint().y);
            pre.setInt(3,temp.getPhysPoint().x);
            pre.setInt(4,temp.getPhysPoint().y);
            pre.setInt(7,temp.getPX_Start());
            pre.setInt(8,temp.getPX_Min());
            pre.setInt(9,temp.getPX_Max());
            pre.setInt(10,temp.getPX_Count());
            pre.executeUpdate();
            pre.close();
        }

/*All the get will be put here.*/
    protected Vector SQL_getMapList() throws SQLException{
        Vector mapList = new Vector();
        String exCode = "SELECT mapName FROM map;";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(exCode);
        while(rs.next()){
            mapList.add(rs.getString(1));
        }
        return mapList;
    }
    //Get specific map object when pass in a mapName
    protected holder.Map SQL_get_Map(String mapName) throws IOException, ClassNotFoundException {
        try{
            String exCode = "SELECT map_object FROM map WHERE mapName = ?";
            PreparedStatement pre = connection.prepareStatement(exCode);
            pre.setString(1,mapName);
            ResultSet rs = pre.executeQuery();
            rs.next();
            //In order to read BLOB into object, have to read it into byte array first
            // Then combine it into object by using ObjectInputStream.
            byte[] byteStream = (byte[]) rs.getObject(1);
            ByteArrayInputStream baip = new ByteArrayInputStream(byteStream);
            ObjectInputStream ois = new ObjectInputStream(baip);
            holder.Map temp = (holder.Map) ois.readObject();
            return temp;
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Get a specific mapImage when pass in a mapName
    protected BufferedImage SQL_get_MapImage(String mapName) throws SQLException, IOException, ClassNotFoundException{
        String exCode = "SELECT dm_width,dm_height,mapImg FROM map WHERE mapName = ?";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setString(1, mapName);
        ResultSet rs = pre.executeQuery();
        rs.next();
        byte[] stream = (byte[]) rs.getObject(3);
        ByteArrayInputStream baip = new ByteArrayInputStream(stream);
        ObjectInputStream ois = new ObjectInputStream(baip);
        int[] array = (int[]) ois.readObject();
        BufferedImage temp = ImageSerializer.intArrayToBufferedImage(array, rs.getInt(1), rs.getInt(2));
        return temp;
    }
    protected Vector<rawobjects.Route> get_Route(int mapID) throws SQLException{
        String exCode = "SELECT ID,MapID,RouteNbr,RouteName,DepartEvery,StartDepotID,EndDepotID from Route where mapID="+mapID+";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(exCode);
        Vector t = new Vector<rawobjects.Route>();
        int i=0;
        while (rs.next()){
            rawobjects.Route temp = new rawobjects.Route();
            temp.setRouteID(rs.getInt(1));
            temp.setMapID(rs.getInt(2));
            temp.setRouteNbr(rs.getString(3));
            temp.setRouteName(rs.getString(4));
            temp.setStartDepotID(rs.getInt(5));
            temp.setStopDepotID(rs.getInt(6));
            temp.setPoints(get_Route_Point(temp.getRouteID()));
            t.add(temp);
            i++;
        }
        return t;
    }
    protected Vector<rawobjects.ROUTE_POINT> get_Route_Point(int RouteID) throws SQLException{
        String exCode = "SELECT RouteDefID,routeID,SeqOrder,PointX,PointY from Route_point where routeID = "+RouteID+";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(exCode);
        Vector t = new Vector<rawobjects.ROUTE_POINT>();
        int i=0;
        while (rs.next()){
            rawobjects.ROUTE_POINT x = new rawobjects.ROUTE_POINT();
            x.setRouteDefID(rs.getInt(1));
            x.setRouteID(rs.getInt(2));
            x.setSeqOrder(rs.getShort(3));
            x.setPointX(rs.getInt(4));
            x.setPointY(rs.getInt(5));
            t.add(x);
            i++;
        }
            return t;
    }
    protected Vector<rawobjects.BusStop> SQL_getBusStop(int mapID) throws IOException, ClassNotFoundException, SQLException{
        String exCode = "SELECT BusStopID,busStopName,MapID,PhysPointX,PhysPointY,LogPointX,LogPointY,PaxCount_Start,PaxCount_Min,PaxCount_Max,PaxCount_Count FROM BUS_STOP WHERE mapID = ?";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1, mapID);
        ResultSet rs = pre.executeQuery();
        Vector<rawobjects.BusStop> busStop = new Vector();
        int i=0;
        while(rs.next()){
            rawobjects.BusStop temp = new rawobjects.BusStop(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getInt(10), rs.getInt(11));
            busStop.add(temp);
            i++;
        }
        return busStop;
    }
    protected Vector<rawobjects.Bus> SQL_getBus() throws IOException, ClassNotFoundException,SQLException{
        String exCode = "SELECT * from bus;";
        PreparedStatement pre = connection.prepareStatement(exCode);
        ResultSet rs = pre.executeQuery();
        Vector<rawobjects.Bus> bus = new Vector();
        rawobjects.Bus_Type bustype;
        int i=0;
        while(rs.next()){
            rawobjects.Bus temp = new rawobjects.Bus();
            temp.setBusID(rs.getInt(1));
            temp.setRouteID(rs.getInt(2));
            bustype=SQL_getBusType(rs.getInt(3));
            temp.setBusType(bustype);
            temp.setCurrLoad(rs.getInt(4));
            temp.setCurrentPointX(rs.getInt(5));
            temp.setCurrentPointY(rs.getInt(6));
            temp.setMoveToPointX(rs.getInt(7));
            temp.setMoveToPointY(rs.getInt(8));
            temp.setMoveRequestState(rs.getInt(9));
            temp.setTimeStamp(rs.getLong(10));
            temp.setDefaultLoad(rs.getInt(11));
            bus.add(temp);
            i++;
        }
        return bus;
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
}
