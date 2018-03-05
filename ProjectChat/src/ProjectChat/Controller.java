/**
 * Class Controller
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
 */
package ProjectChat;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Class that controlls the entire program, however
 * doesn't follow the MVC pattern
 * @author mastah & Oscar
 */
public class Controller extends Observable implements ActionListener {

    private ServerSocket serverSocket;
    private PopUpConnect connectRequest;
    private ControllerFrame startView;
    private String ownName;
    private ArrayList<String> convList = new ArrayList<String>();
    private List<Conversation> conversationList = new ArrayList<>();
    private int IDCounter = 1;
    private ServerThread newThread;
    private Thread connectionThread;
    private int port;
    private PortChooser portChooser;
    private String lastText;
    private Socket newSocket;

    /**
     * Contrstructor that starts the controller
     *
     * @param port
     */
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

    /**
     * Sets the users name
     *
     * @param name
     */
    public void setOwnName(String name) {
        ownName = name;
    }

    /**
     * Method that starts a new conversation
     *
     * @param iP
     * @param port
     * @param name
     * @param request
     */
    public void startNewConv(String iP, int port, String name, String request) {
        try {
            Conversation startConversation = new Conversation();
            startConversation.setName(name);
            addObserver(startConversation);
            startView.tabbedPane.addTab("Chat" + IDCounter, startConversation.view);
            IDCounter = IDCounter + 1;
            Socket conSock = new Socket(iP, port);
            ServerThread startThread = new ServerThread(conSock);
            startThread.name = name;
//            while (startThread.newConnection == true) {
//
//            }
            startConversation.add(startThread);
            System.out.println("Ska sända request här");
            startConversation.sendRequestMess(request);
        } catch (Exception e) {
            e.getMessage();
        }
    }
/**
 *  Tråd som lyssnar efter nya personer, verkar bli svårt om
 *  flera ansluter samtidigt
 */
    class connectionThread extends Thread {

        XMLHandler firstTextHandler;
        String firstText = null;

        public void run() {
            while (true) {
                newSocket = null;
                try {
                    Thread tempThread = new Thread() {
                        public void run() {
                            firstTextHandler = new XMLHandler();
                            try {
                                System.out.println("Ska läsa nu!");
                                BufferedReader reader = new BufferedReader(new InputStreamReader(
                                        newSocket.getInputStream()));
                                while (firstText == null) {
                                    firstText = reader.readLine();
                                }
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
                        firstText = null;
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

    /**
     * Method that triggers when some actions are made
     *
     * @param e
     */
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
