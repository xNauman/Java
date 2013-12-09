import holder.ImageSerializer;
import java.awt.image.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.awt.Point;


/**
 *
 * @author AkS
 */
public class TrkDB extends MainDB{

    public TrkDB() throws SQLException{
        useDB();
    }
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
            Vector<rawobjects.Building> building = SQL_getBuildings(mapID);
            Vector<rawobjects.Congestion> congestion = SQL_getCongestion(mapID);
            Vector<rawobjects.Depot> depot = SQL_getDepot(mapID);
            Vector<rawobjects.TrafficLight> trafficLight = SQL_getTrafficlight(mapID);
            rawobjects.Map tempMap = new rawobjects.Map(mapID, mapName, temp);
            tempMap.busStops = busStop;
            tempMap.routes = route;
            tempMap.buildings=building;
            tempMap.depots=depot;
            tempMap.congestion=congestion;
            tempMap.trafficLights=trafficLight;
            map.add(tempMap);
            i++;
        }
        return map;
 }
/*Check A & B again!*/
public Vector<rawobjects.Congestion> SQL_getCongestion(int mapID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT CongestedAreaID,MapID,PointX_A,PointY_A,PointX_B,PointY_B FROM Map_Congested WHERE mapID =?";
    PreparedStatement pre = connection.prepareStatement(exCode);
    pre.setInt(1, mapID);
    ResultSet rs = pre.executeQuery();

    Vector<rawobjects.Congestion> congestion = new Vector();
    int i=0;
    Point Atemp=null,Btemp=null;

    while(rs.next()){

        Atemp.x=rs.getInt(3);
        Atemp.y=rs.getInt(4);
        Btemp.x=rs.getInt(5);
        Btemp.y=rs.getInt(6);

        rawobjects.Congestion temp = new rawobjects.Congestion(rs.getInt(1),Atemp,Btemp,rs.getInt(2));
        congestion.add(temp);
        i++;
    }
        return congestion;
}

public Vector<rawobjects.Building> SQL_getBuildings(int mapID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT BuildingID,MapID,PointX_A,PointY_A,PointX_B,PointY_B FROM Building WHERE mapID = ?";
    PreparedStatement pre = connection.prepareStatement(exCode);
    pre.setInt(1, mapID);
    ResultSet rs= pre.executeQuery();

    Vector<rawobjects.Building> building = new Vector();
    int i=0;

    while(rs.next()){

        rawobjects.Building temp = new rawobjects.Building(rs.getInt(1),rs.getInt(2),rs.getInt(3), rs.getInt(4), rs.getInt(5),rs.getInt(6));
        building.add(temp);
        i++;
    }
    return building;

}

public Vector<rawobjects.Depot> SQL_getDepot(int mapID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT DepotID,MapID,PhysPointX,PhysPointY,LogPointX,LogPointY FROM depot WHERE mapID = ?";
    PreparedStatement pre = connection.prepareStatement(exCode);
    pre.setInt(1,mapID);
    ResultSet rs = pre.executeQuery();

    Vector<rawobjects.Depot> depot = new Vector();
    int i=0;
    Point phystemp=null,logtemp=null;

    while(rs.next()){


        phystemp.x=rs.getInt(3);
        phystemp.y=rs.getInt(4);
        logtemp.x=rs.getInt(5);
        logtemp.y=rs.getInt(6);

        rawobjects.Depot temp = new rawobjects.Depot(rs.getInt(1),rs.getInt(2),phystemp,logtemp);
        depot.add(temp);
        i++;
    }
    return depot;
}

public Vector<rawobjects.TrafficLight> SQL_getTrafficlight(int mapID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT TrafficLightID,MapID,PointX,PointY,ChangeFreq,IsJunction,LastChange,CurrDirection,MaxDirection FROM TraffiLight WHERE mapID=?";
    PreparedStatement pre =connection.prepareStatement(exCode);
    pre.setInt(1, mapID);
    ResultSet rs = pre.executeQuery();

    Vector<rawobjects.TrafficLight> trafficlight = new Vector();
    Vector<rawobjects.Traffic_Light_Angle> tempangle = new Vector();
    int i=0;

    while(rs.next()){

        tempangle=SQL_getTrafficLightAngle(rs.getInt(1));

        rawobjects.TrafficLight temp = new rawobjects.TrafficLight(rs.getInt(2), rs.getInt(1), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getBoolean(6), rs.getInt(7), rs.getInt(8), rs.getInt(9),tempangle);
        trafficlight.add(temp);
        i++;
    }
    return trafficlight;
}
public Vector<rawobjects.Traffic_Light_Angle> SQL_getTrafficLightAngle(int TrafficID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT AngleID,TrafficLightID,Direction,Angle FROM Traffic_Light_Angle WHERE TrafficLightID=?";
    PreparedStatement pre = connection.prepareStatement(exCode);
    pre.setInt(1, TrafficID);
    ResultSet rs = pre.executeQuery();

    Vector<rawobjects.Traffic_Light_Angle> trafficAngle = new Vector();
    int i=0;

    while(rs.next()){

        rawobjects.Traffic_Light_Angle temp = new rawobjects.Traffic_Light_Angle(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4));
        trafficAngle.add(temp);
        i++;
    }
    return trafficAngle;
}
/*Problem with Bus type!*/
public Vector<rawobjects.Bus> SQL_getBus(int RouteID) throws IOException, ClassNotFoundException, SQLException{

    String exCode="SELECT BusID,RouteID,BusTypeID,CurrLoad,CurrPointX,CurrPointY,MovePointX,MovePointY,MoveReqState,MoveReqTimeStamp,DefaultLoad FROM Bus WHERE RouteID=?";
    PreparedStatement pre = connection.prepareStatement(exCode);
    pre.setInt(1, RouteID);
    ResultSet rs = pre.executeQuery();

    Vector<rawobjects.Bus> bus = new Vector();
    //Vector<database.Bus_Type> bustypetemp = new Vector();
    int i=0;

    while(rs.next()){

        rawobjects.Bus_Type bustypetemp;
        bustypetemp=SQL_getBusType(rs.getInt(3));

        rawobjects.Bus temp = new rawobjects.Bus(rs.getInt(1), rs.getInt(2), bustypetemp, rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getLong(10), rs.getInt(11));
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

    //Vector<database.Bus_Type> bustype = new Vector();
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
