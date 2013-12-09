/**
 * 
 */
package junit;

import static org.junit.Assert.*;

import org.junit.Test;

//import util.SysLogTypeEnum;

/**
 * @author Raja Noman & Jose Varas
 *
 */
public class UTILS_SysLogTypeEnum {
	
	@Test
	public void getEventTypeID(){
		//SysLogTypeEnum vToCheck. = 1;
		
		assertNotNull( util.SysLogTypeEnum.INFORMATION );

		assertNotNull( util.SysLogTypeEnum.ERROR);
		
		assertNotNull( util.SysLogTypeEnum.FATAL);
		
		assertNotNull( util.SysLogTypeEnum.SECURITY_FAILURE );
		
		assertNotNull( util.SysLogTypeEnum.SECURITY_OK );
		
		assertNotNull( util.SysLogTypeEnum.WARNING );
		
				
	}
}
