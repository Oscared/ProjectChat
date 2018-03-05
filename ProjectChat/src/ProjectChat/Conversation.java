package ProjectChat;

import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.text.html.*;

public class Conversation implements ActionListener, Observer {

    ChatPanel view;
    private ArrayList<ServerThread> threadList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();

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
                    String text = threadList.get(i).XMLHandler.writeDisconnect();
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

    public void deConnect() {
        for (int i = 0; i < threadList.size(); i++) {
            String text = threadList.get(i).XMLHandler.writeDisconnect();
            threadList.get(i).writer.println(text);
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
        nameList.add(person.name);
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
            System.out.println("");
            String sendText = threadList.get(i).XMLHandler.writeXML(text, ownName, ownColor);
            threadList.get(i).writer.println(sendText);
        }
    }

    public void sendRequestMess(String text) {
        for (int i = 0; i < threadList.size(); i++) {
            text = threadList.get(i).XMLHandler.writeRequest(text, ownName, ownColor);
            System.out.println("Has written request, should send next");
            threadList.get(i).writer.println(text);
            System.out.println("Has sent text: " + text);
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
            kickFrame = new KickFrame(nameList);
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
                String kickName = kickFrame.kickList.getSelectedItem().toString();
                System.out.println(kickName);
                for (int i = 0; i < nameList.size(); i++) {
                    if (threadList.get(i).name == kickName) {
                        threadList.get(i).stopThread();
                        threadList.remove(i);
                    }
                }
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
                view.appendText(threadList.get(i).getText() + "\n", threadList.get(i).XMLHandler.getColor());
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
