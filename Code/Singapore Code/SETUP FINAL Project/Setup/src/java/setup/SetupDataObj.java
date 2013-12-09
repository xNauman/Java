/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package setup;
import java.io.Serializable;
/**
 *
 * @author pingz
 */
public class SetupDataObj implements Serializable {

    final static int DAY = 0;
    final static int NIGHT = 1;
    private int mapID;
    private int flavour;
    private int trafFreq;
    private Route[] routeList;
       
    public int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        this.mapID = mapID;
    }

    public int getFlavour() {
        return flavour;
    }

    public void setFlavour(int flavour) {
        this.flavour = flavour;
    }

    public int getTrafFreq() {
        return trafFreq;
    }

    public void setTrafFreq(int trafFreq) {
        this.trafFreq = trafFreq;
    }

    public void setRouteList(Route[] routeList) {
        this.routeList = routeList;
    }

    public Route[] getRouteList() {
        return routeList;
    }
}
