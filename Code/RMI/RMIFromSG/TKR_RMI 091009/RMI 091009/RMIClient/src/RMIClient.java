
/**
 *
 * @author lohs0019
 */
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;

public class RMIClient{
//    static Image  img;
    static Socket requestSocket ;
    static ObjectInputStream in;
    static long fileSize;
    static long time;
    static long averageTime;
    public static void main(String[] args){
      try{
          //RMI
          Registry registry;
          String serverAddress = "155.69.100.109";
          int serverPort = 1234;
          String msg = "Hi, i'm a RMI client talking to RMI server";
          Server1Interface server = null;
          //get the registry
          registry = LocateRegistry.getRegistry(serverAddress,
                  serverPort);
          //look up the remote object
          server = (Server1Interface)(registry.lookup("Server1"));

          //Socket connection
  //       int socketPort = 1235;


         for(int j = 0; j<10; j++){ //upper for loop
         // while(true){
          for(int i =0; i<25; i++){
               long start = System.currentTimeMillis(); //start timing
          try{
                byte[] buffer = server.getImage(); //using RMI
              convertByteToImage(buffer);

          }catch(Exception e){
              e.printStackTrace();
          }
             File file = new File("screenshot.jpg"); //for calculating size only
             fileSize+= file.length();
             time += System.currentTimeMillis() - start; //timing the duration taken to send the file
          } //end of for loop
          System.out.println("total data received "+fileSize/1024+" kb, time taken "+time+" ms");
          averageTime += time;
          fileSize = 0;
          time = 0;
         }//end of  outer for loop
        // System.out.println("total data received "+fileSize/1024+" kb, time taken "+time+" ms");
         System.out.println("Total average time ="+averageTime/10);
      }
      catch(Exception e){
          System.err.println("Fail to get Information");
          e.printStackTrace();
      }


    }

// public void paint(Graphics g) {
//        g.drawImage(img, 0, 0, null);
//    }

 public static void convertByteToImage(byte[] array){
     byte[] buffer = array;
     try{
            FileOutputStream fos = new FileOutputStream("screenshot.jpg");
            fos.write(buffer);

	}catch(Exception e){
		e.printStackTrace();
	}
 }
}
