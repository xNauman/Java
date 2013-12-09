package controlCentre;

public class BCastServer {
	public static void main(String args[]){

		System.out.println("Broadcast Server Started...");

		//while (true) {
		

		//cc.registry.Server s;
		cc.registry.RegistryServer s;

		try {
			s = new cc.registry.RegistryServer();
			
			//s = new cc.registry.Server(1); //set to the number of systems planning to connect

			new Thread(s).start();
			
			
			/*System.out.println("IP Address for CME = " + s.getClientIP("CME"));
			System.out.println("IP Address for DB = " + s.getClientIP("DB"));
			System.out.println("IP Address for NAV = " + s.getClientIP("NAV"));
			System.out.println("IP Address for SCH = " + s.getClientIP("SCH"));
			System.out.println("IP Address for SU = " + s.getClientIP("SU"));
			System.out.println("IP Address for TKR = " + s.getClientIP("TKR"));*/
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//}
	}
}
