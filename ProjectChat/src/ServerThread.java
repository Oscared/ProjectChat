import java.io.*;
import java.net.*;


public class ServerThread extends Thread {

    //Socket to connect the client to. Will come from the accepted socket 
    //to our serverSocket
    private Socket clientSocket;
    String text;
    //Output from client.
    PrintWriter writer;

    //Input to the client
    private BufferedReader reader;

    //Unique ID for the client used for identification
    private int ID;

    //Creates a new thread for the new client with a unique ID.
    public ServerThread(Socket sock, int id) {
        clientSocket = sock;
        ID = id; 
        if(clientSocket.getPort()==0){
            
        }
    }

    @Override
    public void run() {
        System.out.println("Runs thread");
        boolean newConnection = true;
        try{
            System.out.println("Creates writer");
	    writer = new PrintWriter(
				  clientSocket.getOutputStream(), true);
	}catch(IOException e){
	    System.out.println("getOutputStream failed: " + e);
	    System.exit(1);
	}
	try{
            System.out.println("Creates reader");
	    reader = new BufferedReader(new InputStreamReader(
	            clientSocket.getInputStream()));
	}catch(IOException e){
	    System.out.println("getInputStream failed: " + e);
	    System.exit(1);
	}
        if (newConnection==true){
            try{
               String text = reader.readLine();
               System.out.println(text);
               if(text==null){
                   newConnection=false;
               }
            } 
             catch(Exception e){
                 
            }
        }
    }

    public int GetID() {
        return ID;
    }
}
