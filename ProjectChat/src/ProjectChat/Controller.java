package ProjectChat;

import ProjectChat.Conversation;
import ProjectChat.ControllerFrame;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller extends Observable implements ActionListener {

    private ServerSocket serverSocket;
    PopUpConnect connectRequest;
    private ControllerFrame startView;

    String ownName = "George Bush";

    ArrayList<String> convList = new ArrayList<String>();

    private List<Conversation> conversationList = new ArrayList<>();
    int IDCounter = 1;
    ServerThread newThread;
    Thread connectionThread;

    int port;
    PortChooser portChooser;

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
                                newThread  = new ServerThread(newSocket);
                                    connectRequest  = new PopUpConnect(convList);

                                    //if (newThread.getText () != null) {
                                    //connectRequest.textField.setText(newThread.getText());
                                    //}

                                    connectRequest.acceptButton.addActionListener (Controller.this);

                                    connectRequest.declineButton.addActionListener (Controller.this);
                                }
                            }catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        }

                    }
                }

                ;
                connectionThread.start ();
            } catch (Exception e) {
            e.getMessage();
        }
        }

    

    public void setOwnName(String name) {
        ownName = name;
    }

    public void startNewConv(String iP, int port, String name, String request) {
        try {
            Conversation startConversation = new Conversation();
            startConversation.setName(name);
            addObserver(startConversation);
            startView.tabbedPane.addTab("Chat" + IDCounter, startConversation.view);
            IDCounter = IDCounter + 1;
            Socket conSock = new Socket(iP, port);
            ServerThread startThread = new ServerThread(conSock);
            startConversation.add(startThread);
            startConversation.sendRequestMess(request);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startView.connectButton) {
            startNewConv(startView.ipField.getText(),
                    Integer.parseInt(startView.portField.getText()),
                    startView.nameField.getText(), startView.requestField.getText());
        } else if (e.getSource() == startView.quitButton) {

        } else if (e.getSource() == connectRequest.acceptButton) {
            connectRequest.dispose();
            if (connectRequest.convBox.getSelectedItem() == "New Chat") {
                Conversation newConversation = new Conversation();
                IDCounter = IDCounter + 1;
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
            newThread.XMLHandler.writeDecline();
            setChanged();
            notifyObservers();
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
