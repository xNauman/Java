
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author DuongDangChien
 */
public class SUClient {
    public static void main(String args[]){
        try{
            Registry registry;
            //later on, this IP need to retreive from the registry server
            String serverAddress = "172.22.143.141";
            //the port also...
            String serverPort = "1234";
            SUInterface server = null;

            registry = LocateRegistry.getRegistry(serverAddress,
                    Integer.valueOf(serverPort));
            server = (SUInterface)(registry.lookup("SUServer"));
            while(true){
                System.out.println("Trying to get the array from SU.");
                try{
                    int[] temp = server.getArray();
                    for(int i=0;i<temp.length;i++){
                        System.out.print(" "+i);
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println("Mission accomplished.");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
