
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Controller implements ActionListener{

    private ServerSocket serverSocket;
   
    private OurGUI startView;

    private List<Conversation> conversationList = new ArrayList<>();
    
    private String lastText;

    public Controller(int port) {
        try {
            startView = new OurGUI();
            startView.connectButton.addActionListener(this);
            serverSocket = new ServerSocket(port);

            Thread connectionThread = new Thread() {
                public void run() {
                    int IDCounter = 0;
                    while (true) {
                        Socket newSocket = null;
                        try {
                            newSocket = serverSocket.accept();
                            if (newSocket != null) {
                                Conversation newConversation = new Conversation();
                                newConversation.view.connectButton.addActionListener(Controller.this);
                                ServerThread newThread = new ServerThread(newSocket, IDCounter);
                                newConversation.add(newThread);
                                conversationList.add(newConversation);
                                IDCounter = IDCounter + 1;

                            }
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            };
            connectionThread.start();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void main(String[] args) {
        System.out.println("Controller is init");
        Controller newController = new Controller(4444);
        System.out.println("Controller is done");
        /*try {
            Socket conSock = new Socket("130.229.143.175.", 4444);
            Conversation newConversation = new Conversation();
            ServerThread newThread = new ServerThread(conSock, 1);
            newConversation.add(newThread);
        } catch (Exception e) {
            e.getMessage();
        }*/
    }
    public void startNewConv() {
            try {
            Socket conSock = new Socket("130.229.143.175", 4444);

            Conversation newConversation = new Conversation();
            ServerThread newThread = new ServerThread(conSock, 1);
            newConversation.add(newThread);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public String sendMess(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void actionPerformed(ActionEvent e){
        startNewConv();
    }
}
