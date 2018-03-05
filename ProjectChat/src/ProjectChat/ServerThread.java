package ProjectChat;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerThread extends Observable {

    //Socket to connect the client to. Will come from the accepted socket 
    //to our serverSocket
    private Socket clientSocket;

    //Output from client.
    PrintWriter writer;
    boolean newConnection = true;
    String text;
    String XMLText;
    Thread runThread;

    String name;
    XMLHandler XMLHandler = new XMLHandler();
    //Input to the client
    BufferedReader reader;

    //Unique ID for the client used for identification
    private String ID;

    //Creates a new thread for the new client with a unique ID.
    public ServerThread(Socket sock) {
        clientSocket = sock;

        runThread = new Thread() {
            public void run() {

                while (newConnection == true) {
                    try {
                        String texten = reader.readLine();
                        if (texten == null) {
                            newConnection = false;
                        } else {
                            System.out.println("Detta kommer från andra personen" + texten);
                            XMLText = texten;
                            text = XMLHandler.ReadXML(texten);
                            setChanged();
                            System.out.println("Has changed");
                            notifyObservers();
                            System.out.println("Have notified observers. Sent: " + text);

                        }
                    } catch (Exception e) {

                    }
                }

            }
        };
        try {
            writer = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            System.out.println("Have made writer");
        } catch (IOException e) {
            System.out.println("getOutputStream failed: " + e);
            System.exit(1);
        }
        try {
            reader = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("getInputStream failed: " + e);
            System.exit(1);
        }
        runThread.start();
    }

    public String GetID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    public String getXMLText() {
        return XMLText;
    }

    public void stopThread() {
        runThread.interrupt();
        try {
            clientSocket.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
