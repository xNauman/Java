package rmi_engine;
import rawobjectsPriv.EnumModName;
import rawobjectsPriv.NetIPInfo;
import config.SysConfig;
public class BCastTHD implements Runnable {

	private cc.registry.RegistryClient c = null;
	
	@Override
	public void run() {
	
		
		System.out.println("About to start broadcast thread");
		
		
		try {
			//start up bcast thread
			c = new cc.registry.RegistryClient(SysConfig.getMyModuleName().getModAbbrev());
			Thread ipBroadCast; 
			ipBroadCast = new Thread(c);
			ipBroadCast.start();
			
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//Call the GET IP method to get the IP back from the client. 
		GetIP();
		//start IP Registration THD to keep checking the IPs

		System.out.println("Completed broadcast thread and monitoring thread start");
		
		
		
		
	}
	

	
	public void GetIP()
	{
		//loop to run to get the IP out 
		while(true)
		{
			/*the client code throw's null pointers so I have to catch that exception
			yes this is VERY poor coding, but I've got no choice as i don't have control over the broadcast client
			I'm catching call exceptions here as I don't trust the code I'm having to use and I don't want it breaking
			my application
			*/
			try{
				String ccIPAddr = c.getServerIP();
				
				NetIPInfo ccInfo = SysConfig.getIPInfo(EnumModName.CONTROL_CENTRE);
				ccInfo.setIPAddress(ccIPAddr, "CC_SET_BCAST");
		
				SysConfig.addIPInfo(EnumModName.CONTROL_CENTRE, ccInfo);
				SysConfig.setIPregistrationCompleted(true);
				
				//System.out.println("CC's IP!!! : "+ccIPAddr);
				
				//if we get to here, we must have got an IP (see exception note)
				//so I'm going to sleep for 10 seconds.
				Thread.sleep(10000);
				
			}
			catch(Exception e)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				
				}
			}

		}
	}
	

}
