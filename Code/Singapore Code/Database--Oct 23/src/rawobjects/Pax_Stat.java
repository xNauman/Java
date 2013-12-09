package rawobjects;

public class Pax_Stat implements java.io.Serializable{

    private int PaxStatID;
    private int SimRunID;
    private int BusStopID;
    private int Count;
	 	 
	 private int BusID;
	 
	 
    public Pax_Stat(){
        PaxStatID = 0;
        SimRunID =0;
        BusStopID =0;
        Count =0;
		BusID = 0;
    }
    public Pax_Stat(int PaxStatID,int SimRunID,int BusStopID, int busID,int count){
        this.PaxStatID = PaxStatID;
        this.SimRunID = SimRunID;
        this.BusStopID = BusStopID;
        this.Count = count;
		this.BusID  = busID;
	 }
    public int getPaxStatID(){
        return PaxStatID;
    }
    
    public void setPaxStatID(int iPaxStatID){
        PaxStatID = iPaxStatID;
    }
    
    public int getSimRunID(){
        return SimRunID;
    }
    
    public void setSimRunID(int iSimRunID){
        SimRunID = iSimRunID;
    }
    

    
    public int getBusStopID(){
        return BusStopID;
    }
    
    public void setBusStopID(int iBusStopID){
        BusStopID = iBusStopID;
    }
    
    public int getCount(){
        return Count;
    }
    
    public void setCount(int pCount){
        Count = pCount;
    }
	 
	 public int getBusID(){
        return BusID;
    }
    
    public void setBusID(int pBusID ){
       BusID = pBusID;
    }
    
}
