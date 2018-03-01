package ProjectChat;

import ProjectChat.XMLHandler;
import java.awt.event.ActionEvent;
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
    String fullText;
    Thread runThread;

    XMLHandler XMLHandler = new XMLHandler();
    //Input to the client
    BufferedReader reader;

    //Unique ID for the client used for identification
    private int ID;

    //Creates a new thread for the new client with a unique ID.
    public ServerThread(Socket sock, int id) {
        clientSocket = sock;
        ID = id;

        runThread = new Thread() {
            public void run() {
                synchronized (this) {
                    if (newConnection = true) {
                        try {
                            writer = new PrintWriter(
                                    clientSocket.getOutputStream(), true);
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
                        newConnection = false;
                    }
                    while (newConnection == false) {
                        try {
                            String texten = reader.readLine();
                            System.out.println("Detta kommer från andra personen" + texten);
                            XMLHandler.ReadXML(texten);
                            text = XMLHandler.sendText();
                            text = texten;
                            setChanged();
                            notifyObservers();
                            fullText += "\n" + text;
                            System.out.println("Have notified observers. Sent: " + text);
                            if (text == null) {
                                newConnection = true;
                            }
                        } catch (Exception e) {

                        }
                    }
                }
            }
        };
        runThread.start();
    }

    public int GetID() {
        return ID;
    }

    public String getText() {
        return text;
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
