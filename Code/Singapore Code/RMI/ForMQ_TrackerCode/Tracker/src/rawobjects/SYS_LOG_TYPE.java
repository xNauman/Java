package rawobjects;
public class SYS_LOG_TYPE
{
  private int EventTypeID;
  private String EventType;

  public SYS_LOG_TYPE(){
      EventTypeID = 0;
      EventType = null;
  }
  public SYS_LOG_TYPE(int ID, String Type){
      this.EventTypeID = ID;
      this.EventType = Type;
  }
  public int getEventTypeID(){
      return EventTypeID;
  }

  public void setEventTypeID(short id){
      EventTypeID=id;
  }

  public String getEventType(){
      return EventType;
  }

  public void setEventType(String str){
      EventType=str;
  }
}
