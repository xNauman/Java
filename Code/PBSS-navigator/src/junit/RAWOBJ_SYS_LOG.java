package junit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class RAWOBJ_SYS_LOG {
	
	  rawobjects.SYS_LOG mSYSLOG = new rawobjects.SYS_LOG();
	  
	  @Test
		public void getSimRunIDTest(){
		  mSYSLOG.setSimRunID(1);
			
			assertEquals(1,mSYSLOG.getSimRunID());
		}
	  
	  @Test
		public void getSourceModuleTest(){
		  mSYSLOG.setSourceModule("Nav");
			
			assertEquals("Nav",mSYSLOG.getSourceModule());
		}
	  @Test
		public void getEventCodeTest(){
		  mSYSLOG.setEventCode("Eve");
			
			assertEquals("Eve",mSYSLOG.getEventCode());
		}
	  @Test
		public void getEventDataTest(){
		  mSYSLOG.setEventData("Move");
			
			assertEquals("Move",mSYSLOG.getEventData());
		}
	  @Test
		public void getEventTypeIDTest(){
		  mSYSLOG.setEventTypeID(4);
			
			assertEquals(4,mSYSLOG.getEventTypeID());
		}
	  
	  @Test
		public void getEntryIDTest(){
		  mSYSLOG.setEntryID(1);
			
			assertEquals(1,mSYSLOG.getEntryID());
		}
	  

/*
	  public java.sql.Timestamp getLogDateTime(){
	    return LogDateTime;
	   }

	  public void setLogDateTime(java.sql.Timestamp pLogDateTime){
	    LogDateTime= pLogDateTime;
	   }*/

}
