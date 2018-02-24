
import java.io.*;
import java.net.*;
import java.util.*;

public class Controller {

    private ServerSocket serverSocket;
    //private OurGUI View;
    private List<Conversation> conversationList = new ArrayList<>();
    private String lastText;

    public Controller(int port) {
        int IDCounter = 0;
        try {
            serverSocket = new ServerSocket(port);
            Thread connectThread = new Thread() {
                public void run() {
                    while (true) {
                        Socket newSocket = null;
                        try {
                            newSocket = serverSocket.accept();
                            if (newSocket != null) {
                                Conversation newConversation = new Conversation();
                                ServerThread newThread = new ServerThread(newSocket, IDCounter);
                                newConversation.add(newThread);
                                conversationList.add(newConversation);
                                //IDCounter = IDCounter + 1;
                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            };
            connectThread.start();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /*    //Main2
    public static void main(String[] args) {
        Controller newController = new Controller(4444);
        
    }
     
     */
    //Main1
    public static void main(String[] args) {
        System.out.println("Controller is init");
        Controller newController = new Controller(4444);
        System.out.println("Controller is done");
        try {
            System.out.println("Trying socket");
            Socket conSock = new Socket("130.229.178.247", 4444);
            System.out.println("Socket is up!" + conSock.isConnected());
            Conversation newConversation = new Conversation();
            ServerThread newThread = new ServerThread(conSock, 1);
            newConversation.add(newThread);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public Conversation startNewConv() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String sendMess(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
