package ProjectChat;

import ProjectChat.Conversation;
import ProjectChat.ControllerFrame;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Controller implements ActionListener {

    private ServerSocket serverSocket;

    private ControllerFrame startView;
    int decisionCounter;

    private List<Conversation> conversationList = new ArrayList<>();
    int IDCounter = 0;

    private String lastText;

    public Controller(int port) {
        try {
            startView = new ControllerFrame();
            startView.connectButton.addActionListener(this);
            serverSocket = new ServerSocket(port);

            Thread connectionThread = new Thread() {
                public void run() {
                    while (true) {
                        Socket newSocket = null;
                        try {
                            newSocket = serverSocket.accept();
                            if (newSocket != null) {
                                ServerThread newThread = new ServerThread(newSocket, IDCounter);
                                PopUpConnect connectRequest = new PopUpConnect();
                                connectRequest.acceptButton.addActionListener(Controller.this);
                                connectRequest.declineButton.addActionListener(Controller.this);
                                decisionCounter = -2;
                                while (decisionCounter == -2) {

                                }
                                if (decisionCounter == -1) {
                                    Conversation newConversation = new Conversation();
                                    startView.tabbedPane.addTab("Chat" + IDCounter, newConversation.view);
                                    newConversation.view.disconnectButton.addActionListener(Controller.this);
                                    newConversation.add(newThread);
                                    conversationList.add(newConversation);
                                    decisionCounter = -2;
                                }
                                if (decisionCounter >= 0) {
                                    conversationList.get(decisionCounter).add(newThread);
                                    decisionCounter = -2;

                                } else {
                                    newThread.stopThread();
                                    newSocket.close();
                                    decisionCounter = -2;
                                }
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
    }

    public void startNewConv(String iP, int port, String name) {
        try {
            Socket conSock = new Socket(iP, port);
            Conversation newConversation = new Conversation();
            startView.tabbedPane.addTab("Chat" + IDCounter, newConversation.view);
            IDCounter = IDCounter + 1;
            ServerThread newThread = new ServerThread(conSock, 1);
            newConversation.add(newThread);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public String sendMess(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == "connectButton") {
            startNewConv(startView.ipField.getText(),
                    Integer.parseInt(startView.portField.getText()),
                    startView.nameField.getText());
        }
        if (e.getSource() == "acceptButton") {
            decisionCounter = 1;
        }
        if(e.getSource() == "declineButton"){
            decisionCounter =-99;
        }
    }
}
