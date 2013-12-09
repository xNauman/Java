   import java.io.*;
   import java.net.*;

    public class MulticastServerThread extends QuoteServerThread {

      private long second = 5000;
      InetAddress group = InetAddress.getByName("230.0.0.1");
       public MulticastServerThread() throws IOException {
         super("MulticastServerThread");
      }

       public void run() {
         while (moreQuotes) {
            try {
               byte[] buf = new byte[512];
               // construct quote
               String sString = null;
               if (in == null) {
                  sString ="Sever ip is "+InetAddress.getLocalHost().getHostAddress();
               }
               else
                  sString = getNextQuote();

               buf = sString.getBytes();
               //broadcast across the network
               DatagramPacket sPacket = new DatagramPacket(buf, buf.length, group, 4446);
               socket.send(sPacket);
               System.out.println("Server sent: " + sString);

               // recieve msg from cient
               System.out.print("Sever ready to recieve msg\n");
               byte[] buf2 = new byte[512];
               DatagramPacket cPacket = new DatagramPacket(buf2, buf2.length);
               socket.receive(cPacket);
               String received = new String(cPacket.getData(), 0, cPacket.getLength());
               System.out.println("Sever received: " + received+"\n");
               
               try {// sleep for a while
                  sleep((long)(Math.random() * second));
               }
                   catch (InterruptedException e) { }
            }
                catch (IOException e) {
                  e.printStackTrace();
                  moreQuotes = false;
               }
            }
         socket.close();
      }
   }
