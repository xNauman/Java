import java.io.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 *
 * @author DBteam
 */
public class NavDB extends MainDB
{
     protected rawobjects.TrafficLight get_Traffic_Light(int TLID) throws SQLException,IOException, ClassNotFoundException{
        String exCode = "SELECT TrafficLightID,MapID,PointX,PointY,ChangeFreq,IsJunction,LastChange,CurrDirection,MaxDirection from traffic_light where TrafficLightID="+TLID+";";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(exCode);
        int i=0;
        rawobjects.TrafficLight temp = new rawobjects.TrafficLight();
        while (rs.next()){
            temp.setTrafficLightId(rs.getInt(1));
            temp.setMapID(rs.getInt(2));
            temp.setTrafficLightLocationX(rs.getInt(3));
            temp.setTrafficLightLocationY(rs.getInt(4));
            temp.setChangeFreq(rs.getInt(5));
            temp.setIsJunction(rs.getBoolean(6));
            temp.setLastChange(rs.getLong(7));
            temp.setCurrDirection(rs.getInt(8));
            temp.setMaxDirection(rs.getInt(9));
            temp.setTLAngle(SQL_getTrafficLightAngle(temp.getTrafficLightId()));
            i++;
        }
        return temp;
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
      public static void main(String[]args) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
        NavDB test = new NavDB();
        test.createDB();
        test.create_Table();
        //test.get_Traffic_Light(TLID);
        //test.update_traffic_light(tl);
    }
}
