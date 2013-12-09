import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import cc.registry.*;
import rmi_interfaces.*;


public class client {

    private String CC_IP;
    private RegistryClient c;
    private String subs;

    public client() throws Exception {
        subs = "SU";
        // specify string as CME, DB, NAV, SCH, SU, TKR
        c = new RegistryClient(subs);
        // start registration
        new Thread(c).start();

        // wait until get cc ip address
        while(CC_IP==null) {
//            System.out.println("getting cc ip...");
            try {
                CC_IP = c.getServerIP();
                System.out.print("CC IP: ");
                System.out.println(CC_IP);
            }
            catch(NullPointerException e) {
//                System.out.println("CC IP not found!");
                Thread.sleep(1000);
            }
        }

        System.out.println(getIP(subs));
    }

    public static void main(String[] args) throws Exception {
        new client();
    }

    private String getIP(String ID) throws Exception {
        
        System.out.println("RMI starting...");

        // get registry for the providing host and port
        Registry registry = LocateRegistry.getRegistry(CC_IP, 4445);
        CCInterface CC = (CCInterface) registry.lookup("CC");

        System.out.println("IP retrieved:");
        return CC.getIP(ID);
    }
}