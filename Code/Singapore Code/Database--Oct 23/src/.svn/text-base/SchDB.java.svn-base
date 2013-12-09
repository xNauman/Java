import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 *
 * @author DBteam
 */

public class SchDB extends MainDB {
    public SchDB(){}
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

}
