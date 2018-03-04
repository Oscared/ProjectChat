package ProjectChat;

import ProjectChat.Conversation;
import ProjectChat.ControllerFrame;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Controller implements ActionListener {

    private ServerSocket serverSocket;
    PopUpConnect connectRequest;

    private ControllerFrame startView;

    String ownName="George Bush";

    ArrayList<String> convList = new ArrayList<String>();

    private List<Conversation> conversationList = new ArrayList<>();
    int IDCounter = 0;
    ServerThread newThread;
    Thread connectionThread;

    private String lastText;
    Socket newSocket;

    public Controller(int port) {
        try {
            startView = new ControllerFrame();
            convList.add("New Chat");
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
                                //newThread.addObserver(Controller.this);
                                connectRequest = new PopUpConnect(convList);
                                if (newThread.getText() != null) {
                                    connectRequest.textField.setText(newThread.getText());
                                }
                                System.out.println("Has made popup");
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
        //PortChooser portChooser = new PortChooser();
        //portChooser.startButton.addActionListener(this);
        Controller newController = new Controller(4444);
        System.out.println("Controller is done");
    }

    public void startNewConv(String iP, int port, String name, String request) {
        try {
            Socket conSock = new Socket(iP, port);
            Conversation startConversation = new Conversation();
            startConversation.setName(name);
            startView.tabbedPane.addTab("Chat" + IDCounter, startConversation.view);
            IDCounter = IDCounter + 1;
            ServerThread startThread = new ServerThread(conSock, 1);
            startConversation.add(startThread);
            System.out.println("Should have added conv with name: " + name);
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
        } else if (e.getSource() == startView.quitButton){
            
        } else if (e.getSource() == connectRequest.acceptButton) {
            connectRequest.dispose();
            if (connectRequest.convBox.getSelectedItem() == "New Chat") {
                Conversation newConversation = new Conversation();
                newConversation.setName(ownName);
                startView.tabbedPane.addTab("Chat" + IDCounter, newConversation.view);
                newConversation.view.disconnectButton.addActionListener(Controller.this);
                newConversation.add(newThread);
                conversationList.add(newConversation);
                convList.add("Chat " + IDCounter);
            } else {
                //Fixat index till minus
                conversationList.get(connectRequest.convBox.getSelectedIndex() - 1).add(newThread);
            }

        } else if (e.getSource() == connectRequest.declineButton) {
            connectRequest.dispose();
            newThread.stopThread();
            try {
                newSocket.close();
            } catch (Exception ee) {
                ee.getMessage();
            }
        }

    }
}
