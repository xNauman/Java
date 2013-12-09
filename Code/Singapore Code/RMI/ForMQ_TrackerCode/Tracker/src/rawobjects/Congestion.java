package rawobjects;
import java.awt.Point;
import java.io.Serializable;

public class Congestion implements Serializable {

	private static final long serialVersionUID = 5693321679650539743L;
	
	private int CongestedAreaID;
	private Point PointA;
	private Point PointB;
      private Integer MapID;

    public Congestion(){
        CongestedAreaID =0;
        PointA = null;
        PointB = null;
        MapID = 0;
    }
    public Congestion(int CongestedAreaID,Point A,Point B,int mapID){
        this.CongestedAreaID = CongestedAreaID;
        this.PointA = A;
        this.PointB = B;
        this.MapID = mapID;
    }

    public int getMapID(){
        return MapID;
    }
    public void setMapID(int mapID){
        this.MapID = mapID;
    }
	
	public int getCongestedAreaID() {
		return CongestedAreaID;
	}
	public void setCongestedAreaID(int pCongestedAreaID) {
		this.CongestedAreaID = pCongestedAreaID;
	}
	public Point getPointA() {
		return PointA;
	}
	public void setPointA(Point pPointA) {
		this.PointA = pPointA;
	}
	public Point getPointB() {
		return PointB;
	}
	public void setPointB(Point pPointB) {
		this.PointB = pPointB;
	}	
}