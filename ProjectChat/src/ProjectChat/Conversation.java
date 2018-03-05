/**
 * Class Conversation
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
 */
package ProjectChat;

import java.awt.Color;
import java.awt.event.*;
import java.util.*;

/**
 * Class that handles each conversation
 *
 * @author mastah
 */
public class Conversation implements ActionListener, Observer {

    String ownColor = "#000000";
    String forwardText;
    KickFrame kickFrame;
    ColorChooser colorChooser;
    ChatPanel view;
    private ArrayList<ServerThread> threadList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private String ownName;

    /**
     * Constructor that has a shutdownhook, also starts graphics
     */
    // (Stackoverflow hjälp för shutdownhook)
    public Conversation() {
        Thread hook = new Thread() {
            public void run() {
                for (int i = 0; i < threadList.size(); i++) {
                    String text = threadList.get(i).XMLHandler.
                            writeDisconnect(ownName);
                    threadList.get(i).writer.println(text);
                }
            }
        };
        Runtime.getRuntime().addShutdownHook(hook);
        view = new ChatPanel();
        view.sendButton.addActionListener(this);
        view.disconnectButton.addActionListener(this);
        view.colorButton.addActionListener(this);
        view.kickButton.addActionListener(this);
    }

    /**
     * deConnect a person
     */
    public void deConnect() {
        for (int i = 0; i < threadList.size(); i++) {
            String text = threadList.get(i).XMLHandler.writeDisconnect(ownName);
            threadList.get(i).writer.println(text);
            threadList.get(i).stopThread();
        }
        view.setVisible(false);

    }

    /**
     * Add a person to the conversation (A serverThread)
     *
     * @param person
     */
    public void add(ServerThread person) {
        System.out.println("Creates one Thread!");
        threadList.add(person);
        nameList.add(person.name);
        person.addObserver(this);
    }

    /**
     * Method for sending message
     *
     * @param text
     */
    public void sendMess(String text) {
        view.appendText(ownName + ": " + text + "\n", ownColor);
        for (int i = 0; i < threadList.size(); i++) {
            System.out.println("");
            String sendText = threadList.get(i).XMLHandler.
                    writeXML(text, ownName, ownColor);
            threadList.get(i).writer.println(sendText);
        }
    }

    /**
     * Sends a request message, used when starting a new conversation
     *
     * @param text
     */
    public void sendRequestMess(String text) {
        for (int i = 0; i < threadList.size(); i++) {
            text = threadList.get(i).XMLHandler.
                    writeRequest(text, ownName, ownColor);
            System.out.println("Has written request, should send next");
            threadList.get(i).writer.println(text);
            System.out.println("Has sent text: " + text);
        }
    }

    /**
     * Sets your own name
     *
     * @param Name
     */
    public void setName(String Name) {
        ownName = Name;
    }

    /**
     * Method that decides what happens for when different events
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.sendButton) {
            System.out.println("Button is pressed");
            sendMess(view.sednField.getText());
        } else if (e.getSource() == view.disconnectButton) {
            deConnect();
        } else if (e.getSource() == view.colorButton) {
            colorChooser = new ColorChooser();
            colorChooser.colorButton.addActionListener(this);
        } else if (e.getSource() == view.kickButton) {
            kickFrame = new KickFrame(threadList);
            kickFrame.kickButton.addActionListener(this);
        }
        if (colorChooser != null) {
            if (e.getSource() == colorChooser.colorButton) {
                Color color = colorChooser.colorWindow.getColor();
                System.out.println("Color Button pressed! " + color.toString());
                String hex = Integer.toHexString(color.getRGB() & 0xffffff);
                while (hex.length() < 6) {
                    hex = "0" + hex;
                }
                ownColor = "#" + hex;
                System.out.println("Hexa färg från chooser: " + ownColor);
                colorChooser.dispose();
            }
        }
        if (kickFrame != null) {
            if (e.getSource() == kickFrame.kickButton) {
                sendMess("Someone has been kicked...");
                int kickIndex = kickFrame.kickList.getSelectedIndex();
                threadList.get(kickIndex).stopThread();
                threadList.remove(kickIndex);

            }
        }

    }

    /**
     * Method that updates when something has happend with the observable
     *
     * @param o
     * @param o1
     */
    @Override
    public void update(Observable o, Object o1) {
        boolean isController = true;
        System.out.println("Updating conversation");
        for (int i = 0; i < threadList.size(); i++) {
            System.out.println("Going through threads: " + i);
            if (o == threadList.get(i)) {
                isController = false;
                System.out.println("Updated one thread :" + i);
                System.out.println(threadList.get(i).getText());
                view.appendText(threadList.get(i).getText() + "\n",
                        threadList.get(i).XMLHandler.getColor());
                if (threadList.size() > 1) {
                    for (int j = 0; j < threadList.size(); j++) {
                        if (j != i) {
                            forwardText = threadList.get(i).getXMLText();
                            threadList.get(j).writer.println(forwardText);
                        }
                    }
                }
            }
        }
        if (isController == true) {
            view.appendText("Du blev nekad tillträde till chatten", ownColor);
        }
    }
}
