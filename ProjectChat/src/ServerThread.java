import java.io.*;
import java.net.*;


public class ServerThread extends Thread {

    //Socket to connect the client to. Will come from the accepted socket 
    //to our serverSocket
    private Socket clientSocket;

    //Output from client.
    private PrintWriter writer;

    //Input to the client.
    private BufferedReader reader;

    //Unique ID for the client used for identification.
    private int ID;

    //Creates a new thread for the new client with a unique ID.
    public ServerThread(Socket sock, int id) {
        clientSocket = sock;
        ID = id; 
    }

    @Override
    public void run() {
        try{
	    writer = new PrintWriter(
				  clientSocket.getOutputStream(), true);
	}catch(IOException e){
	    System.out.println("getOutputStream failed: " + e);
	    System.exit(1);
	}
	try{
	    reader = new BufferedReader(new InputStreamReader(
	            clientSocket.getInputStream()));
	}catch(IOException e){
	    System.out.println("getInputStream failed: " + e);
	    System.exit(1);
	}
    }

    public int GetID() {
        return ID;
    }
}
