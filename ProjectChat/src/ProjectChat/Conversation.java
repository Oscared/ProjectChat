package ProjectChat;

import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.html.*;

public class Conversation implements ActionListener, Observer {

    ChatPanel view;
    private ArrayList<ServerThread> threadList = new ArrayList<>();

    private String ownName;
    String ownColor = "#000000";
    String forwardText;
    KickFrame kickFrame;
    ColorChooser colorChooser;

    // (Stackoverflow hjälp för shutdownhook)
    public Conversation() {
        Thread hook = new Thread() {
            public void run() {
                for (int i = 0; i < threadList.size(); i++) {
                    threadList.get(i).XMLHandler.writeDisconnect();
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

    public void deConnect() {
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).XMLHandler.writeDisconnect();
            threadList.get(i).stopThread();
        }
        view.setVisible(false);

    }

    public String respond(String request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(ServerThread person) {
        System.out.println("Creates one Thread!");
        threadList.add(person);
        person.addObserver(this);
    }

    public void connect(String IP, int Portnummer) {

    }

    public ServerThread kick(ServerThread person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMess(String text) {
        view.appendText(ownName + ": " + text + "\n", ownColor);
        for (int i = 0; i < threadList.size(); i++) {
            String sendText = threadList.get(i).XMLHandler.writeXML(text, ownName, ownColor);
            threadList.get(i).writer.println(sendText);
        }
    }

    public void sendRequestMess(String text) {
        for (int i = 0; i < threadList.size(); i++) {
            text = threadList.get(i).XMLHandler.writeRequest(text, ownName, ownColor);
            System.out.println("Has written request, should send next");
            threadList.get(i).writer.println(text);
        }
    }

    public void setName(String Name) {
        ownName = Name;
    }

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
                ownColor = "#" + Integer.toHexString(color.getRGB() & 0xffffff);
                colorChooser.dispose();
            }
        }
        if (kickFrame != null) {
            if (e.getSource() == kickFrame.kickButton) {
                sendMess("Someone has been kicked...");
                int kickIndex = kickFrame.kickList.getSelectedIndex();
                threadList.get(kickIndex).stopThread();
            }
        }

    }

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
                if (threadList.get(i).getText() == "</disconnect>") {
                } else {
                    view.appendText(threadList.get(i).getText() + "\n", threadList.get(i).XMLHandler.getColor());
                }
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
