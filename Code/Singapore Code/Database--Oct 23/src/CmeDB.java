import holder.ImageSerializer;
import java.awt.Point;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author DBteam
 */
public class CmeDB extends MainDB {
    public CmeDB() throws SQLException{
    }
    public void read_Map_fromFile(String location) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
        FileInputStream in = new FileInputStream(location);
        ObjectInputStream s = new ObjectInputStream(in);
        holder.Map  today =  (holder.Map) s.readObject();
        insertRawMap(today);
    }
    public void update_Map_fromFile(String location) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
        FileInputStream in = new FileInputStream(location);
        ObjectInputStream s = new ObjectInputStream(in);
        holder.Map  today =  (holder.Map) s.readObject();
        updateMap(today);
    }
    //insertRawMap do the insert the original map sent by CME before let the user choose.
    private void insertRawMap(holder.Map map) throws SQLException{
        String exCode = "INSERT INTO MAP(mapName, map_object, dm_width, dm_height,mapImg) VALUES(?, null, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        String mapName = map.getMapID();
        BufferedImage bi = map.getMapInBufferedImage();
        pre.setString(1, mapName);
        pre.setInt(2, bi.getWidth());
        pre.setInt(3, bi.getHeight());
        int[] t = ImageSerializer.serialiseBufferedImage(map.getMapInBufferedImage());
        pre.setObject(4,t);
        pre.executeUpdate();
        pre.close();
    }
    //Update Map - CME call this to update the map after user edit.
    public void updateMap(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        String exCode = "Update map set map_object=? where mapName=?";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setObject(1, map);
        pre.setString(2, mapName);
        read_Bus_Stop(map);  //Read all the bus stop value. Pass into table.
        read_Congestion(map); //Read all congestion value. Pass into table.
        read_Depot(map); //Read depot and insert into table.
        read_Building(map);
        read_Route(map);
        read_TraLight(map);
     }

     //Return the mapList



//Bus_Stop reading and inserting
    private void read_Bus_Stop(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        int i=0;
        while(i<map.busStops.size()){
            holder.BusStop temp = map.busStops.get(i);
            insert_bus_stop(temp,mapName);
            i++;
        }
    }
    private void insert_bus_stop(holder.BusStop temp,String mapName) throws SQLException
    {
        String exCode = "INSERT INTO BUS_STOP(BusStopName, MapID, PhysPointX, PhysPointY, LogPointX, LogPointY, PaxCount_Start, PaxCount_Min, PaxCount_Max, PaxCount_Count)"+" VALUE(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setString(1,Integer.toString(temp.getBusStopID()));
        pre.setInt(2,getMapID(mapName));
        pre.setInt(5,temp.getOppPoint().x);
        pre.setInt(6,temp.getOppPoint().y);
        pre.setInt(3,temp.x);
        pre.setInt(4,temp.y);
        pre.setInt(7,0);
        pre.setInt(8,0);
        pre.setInt(9,0);
        pre.setInt(10,0);
        pre.executeUpdate();
        pre.close();
    }
// Congested reading and inserting
    private void read_Congestion(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        int i=0;
        while(i<map.congestion.size()){
            holder.Congestion temp = map.congestion.get(i);
            insert_Congestion(temp,mapName);
            i++;
        }
    }
    private void insert_Congestion(holder.Congestion temp,String mapName) throws SQLException
    {
        String exCode = "INSERT INTO map_Congested(CongestedAreaID, MapID, PointX_A, PointY_A, PointX_B, PointY_B)"+" VALUE(?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1,temp.getCongestionID());
        pre.setInt(2,getMapID(mapName));
        pre.setInt(3,temp.x);
        pre.setInt(4,temp.y);
        pre.setInt(5,temp.x+temp.width);
        pre.setInt(6,temp.y+temp.height);
        pre.executeUpdate();
        pre.close();
    }

//Depots reading and inserting
    private void read_Depot(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        int i=0;
        while(i<map.depots.size()){
            holder.Depot temp = map.depots.get(i);
            insert_Depot(temp,mapName);
            i++;
        }
    }
    private void insert_Depot(holder.Depot temp,String mapName) throws SQLException
    {
        String exCode = "INSERT INTO depot(DepotID, MapID, PhysPointX, PhysPointY, LogPointX, LogPointY)"+" VALUE(?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1,temp.getDepotID());
        pre.setInt(2,getMapID(mapName));
        pre.setInt(3,temp.x);
        pre.setInt(4,temp.y);
        pre.setInt(5,temp.getRoadPoint().x);
        pre.setInt(6,temp.getRoadPoint().y);
        pre.executeUpdate();
        pre.close();
    }
 // Building reading and inserting
    private void read_Building(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        int i=0;
        while(i<map.buildings.size()){
            holder.Building temp = map.buildings.get(i);
            insert_Building(temp,mapName);
            i++;
        }
    }
    private void insert_Building(holder.Building temp,String mapName) throws SQLException
    {
        String exCode = "INSERT INTO building(BuildingID, MapID, PointX_A, PointY_A, PointX_B,PointY_B)"+" VALUE(?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1,temp.getBuildingID());
        pre.setInt(2,getMapID(mapName));
        pre.setInt(3,temp.x);
        pre.setInt(4,temp.y);
        pre.setInt(5,temp.x+temp.width);
        pre.setInt(6,temp.y+temp.height);
        pre.executeUpdate();
        pre.close();
    }

    //Route reading and inserting.
    private void read_Route(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        int i=0;
        while(i<map.routes.size()){
            holder.Route temp = map.routes.get(i);
            insert_Route(temp,mapName);
            read_Route_Point(temp);
            i++;
        }
    }
    private void insert_Route(holder.Route temp,String mapName) throws SQLException
    {
        String exCode = "INSERT INTO Route(RouteName, MapID)"+" VALUE(?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setString(1,temp.getRouteID());
        pre.setInt(2,getMapID(mapName));
        pre.executeUpdate();
        pre.close();
    }

    //Route_Point reading and inserting
    private void read_Route_Point(holder.Route route) throws SQLException{
        String routeName = route.getRouteID();
        int i=0;
        while(i<route.getPoints().size()){
            Point temp = (Point) route.getPoints().get(i);
            insert_Route_Point(temp,routeName,i+1);
            i++;
        }
    }
    private void insert_Route_Point(Point route,String name,int order) throws SQLException{
        String exCode = "INSERT INTO Route_Point(RouteID,SeqOrder,PointX,PointY)"+" VALUE(?, ?,?,?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1,getRouteID(name));
        pre.setInt(2, order);
        pre.setInt(3, route.x);
        pre.setInt(4, route.y);
        pre.executeUpdate();
        pre.close();
    }
//TrafficLight
    private void read_TraLight(holder.Map map) throws SQLException{
        String mapName = map.getMapID();
        int i=0;
        while(i<map.depots.size()){
            holder.TrafficLight temp = map.trafficLights.get(i);
            insert_TrafLight(temp,mapName);
            i++;
        }
    }
    private void insert_TrafLight(holder.TrafficLight temp,String mapName) throws SQLException
    {
        String exCode = "INSERT INTO traffic_light(TrafficLightID, MapID, PointX, PointY)"+" VALUE(?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1,temp.getTrafficLightID());
        pre.setInt(2,getMapID(mapName));
        pre.setInt(3,temp.x);
        pre.setInt(4,temp.y);
        pre.executeUpdate();
        pre.close();
        Vector roadAngle = temp.getRoadAngle();
        int i=0;
        while (i<roadAngle.size()){
            insert_TrafAngle(temp.getTrafficLightID(),Double.parseDouble(roadAngle.get(i).toString()));
            i++;
        }
    }
    private void insert_TrafAngle(int ID,double angle) throws SQLException{
        String exCode = "Insert into traffic_light_angle(TrafficLightID,Angle) VALUE(?,?);";
        PreparedStatement pre = connection.prepareStatement(exCode);
        pre.setInt(1, ID);
        pre.setDouble(2, angle);
        pre.execute();
        pre.close();
    }
    /*Test method here*/
    //TrafficLight reading and inserting


    public static void main(String[]args) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
        CmeDB test = new CmeDB();
        test.createDB();
        test.create_Table();
        test.read_Map_fromFile("scotland.cme");
        test.update_Map_fromFile("scotland.cme");
    }
    /**/

}
