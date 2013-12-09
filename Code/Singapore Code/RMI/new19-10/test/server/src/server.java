import cc.registry.*;

public class server {

    public static void main(String[] args) throws Exception {
        Server s = new Server(1);   // integer specifies no. of active clients
        System.out.println(s.getClientIP("CME"));
        System.out.println(s.getClientIP("DB"));
        System.out.println(s.getClientIP("NAV"));
        System.out.println(s.getClientIP("SCH"));
        System.out.println(s.getClientIP("SU"));
        System.out.println(s.getClientIP("TKR"));
    }
}