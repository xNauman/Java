package rawobjects;

/**
 *
 * @author Nurfitria
 */

public class Traffic_Light_Angle {
    private int AngleID,TrafficLightID;
    private short Direction;
    private double Angle;

    public Traffic_Light_Angle(int iAngleID,int iTrafficLightID,short pDirection,double pAngle ){
        setAngleID(iAngleID);
        setTrafficLightID(iTrafficLightID);
        setDirection(pDirection);
        setAngle(pAngle);
    }

    public Traffic_Light_Angle(){
        this.AngleID = 0;
        this.TrafficLightID = 0;
        this.Direction = 0;
        this.Angle = 0;
    }

    public int getAngleID(){
        return AngleID;
    }

    public void setAngleID(int iAngleID){
        this.AngleID = iAngleID;
    }

    public int getTrafficLightID(){
        return TrafficLightID;
    }

    public void setTrafficLightID(int iTrafficLightID){
        this.TrafficLightID = iTrafficLightID;
    }

    public short getDirection(){
        return Direction;
    }

    public void setDirection(short pDirection){
        this.Direction = pDirection;
    }

    public double getAngle(){
        return Angle;
    }

    public void setAngle(double pAngle){
        this.Angle = pAngle;
    }
}
