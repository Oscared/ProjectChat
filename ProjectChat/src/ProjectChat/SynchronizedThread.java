package ProjectChat;


import java.awt.event.ActionEvent;
import java.io.*;
import java.net.*;

public class SynchronizedThread extends Thread {

    //Socket to connect the client to. Will come from the accepted socket 
    //to our serverSocket
    private Socket clientSocket;
    //Output from client.
    PrintWriter writer;
    boolean newConnection = true;
    String text;
    String fullText;

    //Input to the client
    BufferedReader reader;

    //Unique ID for the client used for identification
    private int ID;

    //Creates a new thread for the new client with a unique ID.
    public SynchronizedThread(Socket sock, int id) {
        clientSocket = sock;
        ID = id;
    }

    @Override
    public void run() {            
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
                synchronized (this) {
                try {
                    text = reader.readLine();
                    fullText=fullText + "\n" + text;
                    System.out.println("Dags för notify");
                    notifyAll();
                    System.out.println("notify har skett");
                    System.out.println(text);
                    if (text == null) {
                        newConnection = true;
                    }
                } catch (Exception e) {

                }
            }
        }
    }

    public int GetID() {
        return ID;
    }
}