import java.awt.Point;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;
public class insert
{
   Connection connection;
   int MapID;
   private void displaySQLErrors(SQLException e) {
   System.out.println("SQLException: " + e.getMessage());
   System.out.println("SQLState: " + e.getSQLState());
   System.out.println("VendorError: " + e.getErrorCode());
}
public insert() {
try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
}
catch (Exception e) {
System.err.println("Unable to find and load driver");
System.exit(1);
}
}
public void connectToDB() {
try {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter user name: ");
    String name = sc.next();
    System.out.println("Enter password: ");
    String pass = sc.next();
    connection = DriverManager.getConnection("jdbc:mysql://localhost/mysql?user="+name+"&password="+pass);
    System.out.println("Connect successfully");
}
catch(SQLException e) {
displaySQLErrors(e);
}
}

public void createDB()
{
    try
    {
        Statement statement = connection.createStatement();
        String createdb = "create database pbss;";
        statement.execute(createdb);
        System.out.println("Database PBSS created successfully");
    }
    catch(SQLException e)
    {
        displaySQLErrors(e);
    }
}

public void cretables()
{
   try {
         Statement statement = connection.createStatement();

         String MAP="create table map (MapID int primary key auto_increment, MapName varchar(50),map_object Longblob, Dm_Width int, Dm_Height int, Mapimg longblob, ts timestamp) type=innodb;";
         String BUS_STOP="create table bus_stop (BusStopID int primary key auto_increment,busStopName varchar(50), MapID int, PhysPointX int, PhysPointY int, LogPointX int, LogPointY int, PaxCount_Start int, PaxCount_Min int, PaxCount_Max int, PaxCount_Count int, Foreign Key (MapID) references map(MapID), ts timestamp) type=innodb;";
         String DEPOT="create table depot (DepotID int primary key, MapID int, PhysPointX int, PhysPointY int, LogPointX int, LogPointY int, Foreign key (MapID) references map(MapID), ts timestamp) type=innodb;";
         String PAX_STAT="create table pax_stat (PaxStatID int primary key, SimRunID int, BusStopID int, BusID int, Count int, Foreign key (SimRunID) references SYS_SIMULATOR_RUN(SimRunID), Foreign key (BusStopID) references bus_stop(BusStopID), Foreign key (BusID) references bus(BusID),ts timestamp) type=innodb;";
         String ROUTE="create table route (ID int primary key auto_increment, RouteID int , MapID int, RouteNbr varchar(5), RouteName varchar(100), DepartEvery int, StartDepotID int, EndDepotID int, Foreign key (MapID) references map(MapID), Foreign key (StartDepotID) references depot(DepotID), Foreign key (EndDepotID) references depot(DepotID),ts timestamp) type=innodb;";
         String TRAFFIC_LIGHT="create table traffic_light (TrafficLightID int primary key, MapID int, PointX int, PointY int, ChangeFreq int, IsJunction bit, LastChange datetime, CurrDirection int, MaxDirection int, Foreign key (MapID) references map(MapID), ts timestamp) type=innodb;";
         String ROUTE_POINT="create table route_point (RouteDefID int primary key, RouteID int, SeqOrder int, PointX int, PointY int, Foreign key (RouteID) references route(RouteID), ts timestamp) type=innodb;";
         String BUS="CREATE TABLE Bus (BusID int primary key, RouteID int, BusTypeID int, CurrLoad int, CurrPointX int, CurrPointY int, MovePointX int, MovePointY int, MoveReqState int, MoveReqTimeStamp datetime, DefaultLoad int, foreign key(RouteID) references route(RouteID),foreign key(BusTypeID) references Bus_Type(BusTypeID),ts timestamp) type=innodb;";
         String BUS_TYPE="CREATE TABLE Bus_Type (BusTypeID int primary key, BusType varchar(25), BusColour int, MaxCapacity int, BusImage blob,ts timestamp) type=innodb;";
         String MAP_CONGESTED="CREATE TABLE Map_Congested (CongestedAreaID int primary key, MapID int, PointX_A int, PointY_A int, PointX_B int, PointY_B int, foreign key(MapID) references Map(MapID),ts timestamp)type=innodb;";
         String SYS_SIMULATOR_RUN ="CREATE TABLE Sys_Simulator_Run (SimRunID int primary key, StartDate datetime, EndDate datetime, MapID int, StartSignal bit, CMEStatus bit, TRKStatus bit, DBStatus bit, SUStatus bit, SCHStatus bit, NAVStatus bit, foreign key(MapID) references Map(MapID),ts timestamp) type=innodb;";
         String SYS_LOG ="CREATE TABLE Sys_Log (EntryID int primary key, SimRunID int, LogDateTime datetime, SourceModule varchar(3), EventTypeID int, EventCode varchar(10),EventData varchar(1000), foreign key(SimRunID) references Sys_Simulator_Run(SimRunID), foreign key(EventTypeID) references Sys_Log_Type(EventTypeID), ts timestamp) type=innodb;";
         String SYS_LOG_TYPE="CREATE TABLE Sys_Log_Type(EventTypeID int primary key,EventType varchar(30), ts timestamp) type=innodb;";
         String SYS_SETTING="CREATE TABLE Sys_Setting (SettingName varchar(30) primary key, SettingValue varchar(50), SettingDesc varchar(250), CreatedBy varchar(50), CreatedDate datetime, ModifiedBy varchar(50), ModifiedDate datetime, ts timestamp) type=innodb;";

         statement.execute("use pbss;");
         statement.execute(MAP);
         statement.execute(BUS_STOP);
         statement.execute(DEPOT);
         statement.execute(ROUTE);
         statement.execute(TRAFFIC_LIGHT);
         statement.execute(ROUTE_POINT);
         statement.execute(BUS_TYPE);
         statement.execute(MAP_CONGESTED);
         statement.execute(SYS_SIMULATOR_RUN);
         statement.execute(SYS_LOG_TYPE);
         statement.execute(SYS_SETTING);
         statement.execute(SYS_LOG);
         statement.execute(BUS);
         statement.execute(PAX_STAT);


         statement.close();
         connection.close();
         System.out.println("Tables created successfully");
     }
    catch(SQLException e)
     {
        displaySQLErrors(e);
     }
}
public void read_Map() throws FileNotFoundException, IOException, ClassNotFoundException, SQLException
{
    FileInputStream in = new FileInputStream("test.cme");
    ObjectInputStream s = new ObjectInputStream(in);
    int n =3;
    for(MapID=1;MapID<=n;MapID++){
    holder.Map  today =  (holder.Map) s.readObject();
    insertMap(today,MapID);
    read_bus_stop(today);
    read_depot(today);
    read_Route(today);

    }
}
public void read_depot(holder.Map today) throws IOException, ClassNotFoundException, SQLException
{
    int cap=today.depots.size();
    int i=0;
    while(i<cap){
        holder.Depot temp=(holder.Depot) today.depots.get(i);
        insert_depot(temp.getDepotID(),MapID,(int)temp.getRoadPoint().getX(),(int)temp.getRoadPoint().getY());
        i++;
    }
}
public void read_Route(holder.Map today) throws IOException, ClassNotFoundException, SQLException
{
    int cap=today.routes.size();
    int i=0;
    while(i <cap){
        holder.Route temp = (holder.Route) today.routes.get(i);
        insert_Route(temp.getRouteID(),MapID);
        i++;
    }
}
public void read_TrafficLight(holder.Map today) throws IOException, ClassNotFoundException, SQLException
{
    int cap=today.trafficLights.size();
    int i=0;
    while(i <cap){
        holder.TrafficLight temp = (holder.TrafficLight) today.trafficLights.get(i);
        insert_traffic_light(temp.getTrafficLightID(),MapID,(int) temp.getX(),(int)temp.getY());
        i++;
    }
}
public void read_bus_stop(holder.Map today) throws IOException, ClassNotFoundException, SQLException
{
    int cap=today.busStops.size();
    String MapName = today.getMapID();
    int i=0;
    while(i <cap){
        holder.BusStop temp = (holder.BusStop) today.busStops.get(i);
        //insert_bus_stop(temp);
        i++;
    }
}
public void read_congestion(holder.Map today) throws IOException, ClassNotFoundException, SQLException
{
  
}
private void insertMap(holder.Map map,int j) throws SQLException
{
    String exCode = "INSERT INTO MAP(MapID, MapName, Mapimg, width, height) VALUES(?, ?, ?, ?, ?)";
    PreparedStatement pre = connection.prepareStatement(exCode);
    String MapName = map.getMapID();
    BufferedImage Mapimg = map.getMapInBufferedImage();
    pre.setInt(4, Mapimg.getWidth());
    pre.setInt(5, Mapimg.getHeight());
    pre.setString(2, MapName);
    pre.setObject(3, Mapimg);
    pre.setInt(1,j);
    pre.executeUpdate();
    pre.close();
}

    public void insert_bus_stop(rawobjects.BusStop temp,int MapID) throws SQLException
{
        String exCode = "INSERT INTO BUS_STOP(BusStopName, MapID, PhysPointX, PhysPointY, LogPointX, LogPointY, PaxCount_Start, PaxCount_Min, PaxCountMax, PaxCount_Count)"+" VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
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
public void insert_depot(int DepotID, int MapID, int LogX, int LogY) throws SQLException
{
    String exCode = "INSERT INTO depot(DepotID, MapID, PhysPointX, PhysPointY, LogPointX, LogPointY) VALUES(?, ?, ?, ?, ?, ?)";
    PreparedStatement pre=connection.prepareStatement(exCode);
    pre.setInt(1,DepotID);
    pre.setInt(2,MapID);
    pre.setInt(3,0);
    pre.setInt(4,0);
    pre.setInt(5,LogX);
    pre.setInt(6,LogY);
    pre.executeUpdate();
    pre.close();
}
public void insert_Route(String RouteID,int MapID) throws SQLException{
    String exCode = "insert into Route(RouteID, MapID) values (?, ?)";
    PreparedStatement pre=connection.prepareStatement(exCode);
    pre.setString(1,RouteID);
    pre.setInt(2,MapID);
    pre.executeUpdate();
    pre.close();

}
public void insert_traffic_light(int TrafficLightID, int MapID, int PointX, int PointY) throws SQLException
{
    String exCode = "INSERT INTO traffic_light(TrafficLightID, MapID, PointX, PointY, ChangeFreq, ISJunction, LastChange, CurrDirection, MaxDirection, TLColor, RoadAngle) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement pre=connection.prepareStatement(exCode);
    pre.setInt(1,TrafficLightID);
    pre.setInt(2,MapID);
    pre.setInt(3,PointX);
    pre.setInt(4,PointY);
    pre.setInt(5,0);
    pre.setInt(6,0);
    pre.setInt(7,0);
    pre.setInt(8,0);
    pre.setInt(9,0);
    pre.setInt(10,0);
    pre.setInt(11,0);
    pre.executeUpdate();
    pre.close();
}
  public static void main(String[] args)
  {
   try{
     insert pbss = new insert();
     pbss.connectToDB();
     pbss.createDB();
     pbss.cretables();
     pbss.read_Map();
   }
     catch (Exception e) {
            System.out.println(e.getMessage());
        }
  }

}
