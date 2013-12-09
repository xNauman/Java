package rawobjects;

public class SYS_LOG implements java.io.Serializable
{
  private int EntryID;
  private int SimRunID;
  private java.sql.Timestamp LogDateTime;
  private String SourceModule;
  private int EventTypeID;
  private String EventCode;
  private String EventData;

  public SYS_LOG(){
      EntryID = 0;
      SimRunID = 0;
      LogDateTime = null;
      SourceModule = null;
      EventTypeID = 0;
      EventCode = null;
      EventData = null;
  }
  public SYS_LOG(int iEntryID,int iSimRunID,java.sql.Timestamp pLogDateTime,String pSourceModule,int pEventTypeID,String pEventCode,String pEventData){
      this.SimRunID = iSimRunID;
      this.LogDateTime = pLogDateTime;
      this.SourceModule = pSourceModule;
      this.EventTypeID = pEventTypeID;
      this.EventCode = pEventCode;
      this.EventData = pEventData;
      this.EntryID=iEntryID;
  }
  public int getSimRunID(){
      return SimRunID;
  }

  public void setSimRunID(int pid){
      SimRunID=pid;
  }

  public java.sql.Timestamp getLogDateTime(){
    return LogDateTime;
   }

  public void setLogDateTime(java.sql.Timestamp pLogDateTime){
    LogDateTime= pLogDateTime;
   }

  public String getSourceModule(){
      return SourceModule;
  }

  public void setSourceModule(String pSourceModule){
      SourceModule=pSourceModule;
  }

  public String getEventCode(){
      return EventCode;
  }

  public void setEventCode(String pEventCode){
      EventCode=pEventCode;
  }

  public String getEventData(){
      return EventData;
  }

  public void setEventData(String pEventData){
      EventData=pEventData;
  }

  public int getEventTypeID(){
      return EventTypeID;
  }

  public void setEventTypeID(int pEventTypeid){
      EventTypeID=pEventTypeid;
  }

 public int getEntryID(){
  return EntryID;
  }

  public void setEntryID(int iEntryID){
    EntryID = iEntryID;
  }

}
