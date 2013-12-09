package setup.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class SetupServer {

    public SetupServer() {
        try {
            LocateRegistry.createRegistry(1099);
            SetupInterface s = new SetupImp(this);
            Naming.rebind("rmi://localhost:1099/SetupService", s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new SetupServer();
        new ClientIP();
    }
}
