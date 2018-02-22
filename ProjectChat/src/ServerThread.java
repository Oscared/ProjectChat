import java.io.*;
import java.net.*;

public class ServerThread extends Thread {

    //XMLHandler to parse XML and send text
    public XMLHandler XMLHandler;
    
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
        
        boolean done = false;
        
        try{
	    writer = new PrintWriter(clientSocket.getOutputStream(), true);
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
        
        while(!done){
            try{
                String input = reader.readLine();
                if(input==null){
		    System.out.println("Client disconnect!");
		    done = true;
		}else{
                    XMLHandler = new XMLHandler();
                    XMLHandler.ReadXML(input);
		    String sendText = XMLHandler.sendText();//send the text to XMLHandler
                    //get input from XMLHandler if it has renewed?
		}
            }
            catch(IOException e){
		System.out.println("readLine failed: " + e);
		System.exit(1);
            }
            
        }
    }

    public int GetID() {
        return ID;
    }
}
