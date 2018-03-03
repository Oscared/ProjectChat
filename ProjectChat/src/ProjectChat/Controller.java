package ProjectChat;

import ProjectChat.Conversation;
import ProjectChat.ControllerFrame;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Controller implements ActionListener, Observer {

    private ServerSocket serverSocket;
    PopUpConnect connectRequest;

    private ControllerFrame startView;
    int decisionCounter;

    private List<Conversation> conversationList = new ArrayList<>();
    int IDCounter = 0;
    ServerThread newThread;
    Thread connectionThread;

    private String lastText;
    Socket newSocket;

    public Controller(int port) {
        try {
            startView = new ControllerFrame();
            startView.connectButton.addActionListener(this);
            serverSocket = new ServerSocket(port);
            connectionThread = new Thread() {
                public void run() {
                    while (true) {
                        newSocket = null;
                        try {
                            newSocket = serverSocket.accept();
                            if (newSocket != null) {
                                newThread = new ServerThread(newSocket, IDCounter);
                                newThread.addObserver(Controller.this);
                                connectRequest = new PopUpConnect();
                                connectRequest.acceptButton.addActionListener(Controller.this);
                                connectRequest.declineButton.addActionListener(Controller.this);
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

    public void startNewConv(String iP, int port, String name, String request) {
        try {
            Socket conSock = new Socket(iP, port);
            Conversation startConversation = new Conversation();
            startView.tabbedPane.addTab("Chat" + IDCounter, startConversation.view);
            IDCounter = IDCounter + 1;
            ServerThread startThread = new ServerThread(conSock, 1);
            //startThread.addObserver(this);
            startConversation.add(startThread);
            startConversation.sendRequestMess(request);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public String sendMess(String text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startView.connectButton) {
            startNewConv(startView.ipField.getText(),
                    Integer.parseInt(startView.portField.getText()),
                    startView.nameField.getText(), startView.requestField.getText());
        } else if (e.getSource() == connectRequest.acceptButton) {
            connectRequest.dispose();
            Conversation newConversation = new Conversation();
            startView.tabbedPane.addTab("Chat" + IDCounter, newConversation.view);
            newConversation.view.disconnectButton.addActionListener(Controller.this);
            newConversation.add(newThread);
            conversationList.add(newConversation);

        } else if (e.getSource() == connectRequest.declineButton) {
            connectRequest.dispose();
            newThread.stopThread();
            try {
                newSocket.close();
            } catch (Exception ee) {
                ee.getMessage();
            }
        } else if (e.getSource() == connectRequest.textField) {
            conversationList.get(decisionCounter).add(newThread);

        }
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o == newThread) {
            System.out.println("Updates controller!");
            connectRequest.textField.setText(newThread.getText());
            System.out.println("With: " + newThread.getText());
            }
    }
}
