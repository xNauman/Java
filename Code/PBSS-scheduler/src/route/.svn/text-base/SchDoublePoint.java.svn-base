package route;

import java.awt.Point;
import java.util.UUID;

public class SchDoublePoint {

	private UUID mPointUUID = UUID.randomUUID();
	private Point startPoint = null;
	private Point endPoint = null;

	public SchDoublePoint(){
		this.startPoint = null;
		this.endPoint = null;
	}
	
	public SchDoublePoint(Point pStartPoint, Point pEndPoint){
		this.startPoint = pStartPoint;
		this.endPoint = pEndPoint;
	}

	public Point getStartPoint(){
		return this.startPoint;
	}

	public Point getEndPoint(){
		return this.endPoint;
	}
	
	public void setStartPoint(Point pStart){
		this.startPoint = pStart;
	}

	public void setEndPoint(Point pEnd){
		this.endPoint = pEnd;
	}
	
	public UUID getPointUUID(){
		return this.mPointUUID;
	}
	
	public double getDistance(){
		return startPoint.distance(endPoint)+1;
	}
	
	public boolean isComplete(){
		return !(this.startPoint==null&&this.endPoint==null);
	}
	
	public boolean isStraightLine(){
		return ((this.isStraightLineVertical())||(this.isStraightLineHorizontal()));
	}
	
	//Vertical ||||
	public boolean isStraightLineVertical(){
		return (this.startPoint.x==this.endPoint.x);
	}
	
	//Horizontal -----
	public boolean isStraightLineHorizontal(){
		return (this.startPoint.y==this.endPoint.y);
	}
	
	
}
