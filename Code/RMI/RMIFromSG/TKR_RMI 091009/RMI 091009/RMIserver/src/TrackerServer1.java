/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LOHS0019
 */
//import java.rmi.*;
import java.awt.*;
import java.io.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.rmi.RemoteException;
import java.rmi.Naming.*;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.*;


public class TrackerServer1 extends UnicastRemoteObject implements Server1Interface{

    static Image img;
    int rmiPort = 1234; //RMI Port
    int socketPort = 1235; //sokect Port
    Socket connection = null;
    ServerSocket senderSocket;
    ObjectOutputStream out;
    String thisAddress;
    Registry registry; //
    boolean canSend = false;

    public TrackerServer1() throws RemoteException {  //constructor
        try{
            

            //RMI
            thisAddress = (InetAddress.getLocalHost()).toString();
            //prints out the server's ip address
            System.out.println("This address= "+ thisAddress + ",port= "+rmiPort);
            //register the trackerserver as remote object into the registry
            registry = LocateRegistry.createRegistry(rmiPort);
            registry.rebind("Server1", this);

            //Socket
            senderSocket = new ServerSocket(socketPort); // create sokect connection
           // connection = senderSocket.accept();

        }catch(UnknownHostException e){
            e.printStackTrace();
            throw new RemoteException("cant get inet address");
        } catch(RemoteException e){
            throw e;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //method for say method was used for initial testing
    public String say(String msg) throws RemoteException{ 
        System.out.println(msg);
        String a = "Server says "+msg+" ha ha ha";
        return a;
    }
   
    
        public static void main(String args[]){
            try{
                //create a server instance
                TrackerServer1 server = new TrackerServer1();
             //   while(true){
                  // server.run();
               // }
                
            }catch (RemoteException ex){
                ex.printStackTrace();
            }

        }

        //	 Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            System.out.println("File Size too large");
        }
 
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
    
public byte[] getImage(){
        byte[] imageData;
        File file = new File("screenshot.jpg");
        //over here, state the name of the picture that you wish to
        //send

        try{
                //convert picture into byte array
                imageData = getBytesFromFile(file);
                // Send to client via RMI
                return imageData;

        }catch(IOException ioe){
               // Handle exception
               ioe.printStackTrace();
               return null; // or whatever you want..
        }
}

public boolean request(){
      System.out.println("entering request");
      try{
            connection = senderSocket.accept();
      }catch(IOException e){
         e.printStackTrace();
      }
      canSend = true;
     System.out.println("leaving request");
      return canSend;
}

public void sendPicViaSocket(){
    System.out.println("Entering");
    byte[] buffer = getImage();
    try{
//        FileInputStream fis = new FileInputStream("screenshot.jpg");
//        byte[] buffer = new byte[fis.available()];
//        fis.read(buffer);
//        fis.close();
        out = new ObjectOutputStream(connection.getOutputStream());
	out.flush();
        System.out.println("buffer size is "+buffer.length);
        System.out.println("Sending pic");
        out.writeObject(buffer);
        out.flush();
        System.out.println("picture sent");
        out.close();
        
        canSend = false;
    }
    catch(IOException ioException){
        ioException.printStackTrace();
    }
}

//void run(){
//    while(true){
//        try{
//            connection = senderSocket.accept();
//      }catch(IOException e){
//         e.printStackTrace();
//      }
//    }
//}


}

