
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MulticastClientThread extends QuoteClientThread {

    static Scanner scanner = new Scanner(System.in);
    private static long second = 3000;
    InetAddress address = InetAddress.getByName("230.0.0.1");
    MulticastSocket socket = new MulticastSocket(4446);
    
    public MulticastClientThread()throws IOException{
        super("MulticastClientThread");
        socket.joinGroup(address);
    }
    
    public void run() {
        DatagramPacket sPacket;
        System.out.println("waiting for responses from sever...");

        while (true) {// get a few quotes *the while loop may be removed later when used by other programs
            try{
                byte[] buf = new byte[512];
                sPacket = new DatagramPacket(buf, buf.length);
                socket.receive(sPacket);
                int sPort = sPacket.getPort();
                InetAddress sAddr = sPacket.getAddress();
                String received = new String(sPacket.getData(), 0, sPacket.getLength());
                System.out.println("Client receive: " + received);
             
                byte[] buf2 = new byte[512];
                // construct quote
                String cString = null;
                if (in == null) {
                    cString ="Client ip is "+InetAddress.getLocalHost().getHostAddress();
                }
                else
                    cString = getNextQuote();
                
                buf2 = cString.getBytes();
                //repose to sever by sending to server's ip, not broadcasting any more
                DatagramPacket cPacket = new DatagramPacket(buf2, buf2.length,sAddr,sPort);
                socket.send(cPacket);
                System.out.println("Client send back "+ cString);
                
                try {//wait for a while
                  sleep((long)(Math.random() * second));
                }
                   catch (InterruptedException e) { }
            }
            catch(IOException e){}
            }
        }
}
