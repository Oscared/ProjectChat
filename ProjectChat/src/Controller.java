
import java.io.*;
import java.net.*;
import java.util.List;

public class Controller {

    private ServerSocket serverSocket;
    //private OurGUI View;
    private List<Conversation> conversationList;
    private String lastText;

    public Controller(int port) {
        int IDCounter = 0;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket newSocket = null;
                try {
                    newSocket = serverSocket.accept();
                    if (newSocket != null) {
                        Conversation newConversation = new Conversation();
                        ServerThread newThread = new ServerThread(newSocket, IDCounter);
                        newConversation.add(newThread);
                        conversationList.add(newConversation);
                        IDCounter = IDCounter + 1;
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /*  //Main2
    public static void main(String[] args) {
        Controller newController = new Controller(4444);
        
    }
     
*/
    //Main1
    public static void main(String[] args) {
        Controller newController = new Controller(4444);
        try {
            Socket conSock = new Socket("130.229.178.247", 4444);
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
