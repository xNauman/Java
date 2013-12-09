import cc.registry.*;

public class print implements Runnable {

    private RegistryServer server;

    public print(RegistryServer server) {
        this.server = server;
    }

    public void run() {

        while(true) {

            System.out.println("CURRENTLY REGISTERED:");

            if(server.getClientIP("CME")!=null) {
                System.out.print("CME ");
                System.out.println(server.getClientIP("CME"));
            }

            if(server.getClientIP("DB")!=null) {
                System.out.print("DB ");
                System.out.println(server.getClientIP("DB"));
            }

            if(server.getClientIP("NAV")!=null) {
                System.out.print("NAV ");
                System.out.println(server.getClientIP("NAV"));
            }

            if(server.getClientIP("SCH")!=null) {
                System.out.print("SCH ");
                System.out.println(server.getClientIP("SCH"));
            }

            if(server.getClientIP("SU")!=null) {
                System.out.print("SU ");
                System.out.println(server.getClientIP("SU"));
            }

            if(server.getClientIP("TKR")!=null) {
                System.out.print("TKR ");
                System.out.println(server.getClientIP("TKR"));
            }

            try {
                Thread.sleep(3000);
            }
            catch(InterruptedException e) {}
        }
    }
}
