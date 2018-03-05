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

    class connectionThread extends Thread {

        XMLHandler firstTextHandler = new XMLHandler();
        String firstText;

        public void run() {
            while (true) {
                newSocket = null;
                try {
                    Thread tempThread = new Thread() {
                        public void run() {
                            try {
                                System.out.println("Ska läsa nu!");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(
                                        newSocket.getInputStream()));
                                firstText = reader.readLine();
                                System.out.println("Har läst line och closat");
                                firstText = firstTextHandler.ReadXML(firstText);
                            } catch (IOException e) {
                                System.out.println("getInputStream failed: " + e);
                                System.exit(1);
                            }
                        }
                    };
                    newSocket = serverSocket.accept();
                    if (newSocket != null) {

                        tempThread.start();
                        connectRequest = new PopUpConnect(convList);
                        try {
                            System.out.println("väntar på join");
                            tempThread.join();
                            System.out.println("Har joinat");
                            if (firstTextHandler.isRequest == true) {
                                connectRequest.textField.setText(firstText);
                            } else {
                                connectRequest.textField.setText("Ett sämre program vill ansluta");
                            }
                        } catch (Exception e) {
                        }
                        newThread = new ServerThread(newSocket);
                        connectRequest.acceptButton.addActionListener(Controller.this);
                        connectRequest.declineButton.addActionListener(Controller.this);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

        }

    }

    public Controller(int port) {
        try {
            startView = new ControllerFrame();
            convList.add("New Chat");
            startView.connectButton.addActionListener(this);
            serverSocket = new ServerSocket(port);
            connectionThread = new connectionThread();
            connectionThread.start();
//            connectionThread.join();
//            ServerThread newThread = new ServerThread(newSocket); 
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
            while (startThread.newConnection == true){
                
            }
            startConversation.add(startThread);
            System.out.println("Ska sända request här");
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
