/**
 * Class ServerThread
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
 */
package ProjectChat;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Class that creates a thread and holds a writer and reader that controlls the
 * textflow from the socket
 *
 * @author mastah
 */
public class ServerThread extends Observable {

    //Socket to connect the client to. Will come from the accepted socket 
    //to our serverSocket
    boolean newConnection = true;
    PrintWriter writer;
    String text;
    String XMLText;
    Thread runThread;
    private Socket clientSocket;

    String name;
    XMLHandler XMLHandler = new XMLHandler();
    //Input to the client
    BufferedReader reader;

    /**
     * Constructor that creates the serverThread aswell as an inner thread
     *
     * @param sock
     */
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
                            System.out.println("Detta kommer från andra "
                                    + "personen" + texten);
                            XMLText = texten;
                            text = XMLHandler.ReadXML(texten);
                            setChanged();
                            System.out.println("Has changed");
                            notifyObservers();
                            System.out.println("Have notified observers. "
                                    + "Sent: " + text);

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

    /**
     * Get the current text
     *
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Get the current text in XML format
     *
     * @return
     */
    public String getXMLText() {
        return XMLText;
    }

    /**
     * Method for stopping the thread and closing the socket
     */
    public void stopThread() {
        runThread.interrupt();
        try {
            clientSocket.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
