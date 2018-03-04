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
    String forwardColor;
    String forwardText;
    String forwardName;
    
    KickFrame kickFrame;

    ColorChooser colorChooser;

    int removeIndex;

    public Conversation() {
        view = new ChatPanel();
        view.sendButton.addActionListener(this);
        view.disconnectButton.addActionListener(this);
        view.colorButton.addActionListener(this);
    }

    public void deConnect() {
        sendMess("<- Har loggat ut!");
        for (int i = 0; i < threadList.size(); i++) {
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
            threadList.get(i).XMLHandler.setColor(ownColor);
            threadList.get(i).XMLHandler.setName(ownName);
            String sendText = threadList.get(i).XMLHandler.writeXML(text);
            threadList.get(i).writer.println(sendText);
        }
    }

    public void sendRequestMess(String text) {
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).XMLHandler.setColor(ownColor);
            threadList.get(i).XMLHandler.setName(ownName);
            text = threadList.get(i).XMLHandler.writeRequest(text);
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
        } else if (e.getSource() == colorChooser.colorButton) {
            Color color = colorChooser.colorWindow.getColor();
            System.out.println("Color Button pressed! " + color.toString());
            ownColor = "#" + Integer.toHexString(color.getRGB() & 0xffffff);
            colorChooser.dispose();
        } else if (e.getSource() == view.kickButton){
            kickFrame = new KickFrame(threadList);
            kickFrame.kickButton.addActionListener(this);
        } else if (e.getSource() == kickFrame.kickButton){
            sendMess("Someone has been kicked...");
            int kickIndex = kickFrame.kickList.getSelectedIndex();
            threadList.get(kickIndex).stopThread();
        }
        
        

    }

    @Override
    public void update(Observable o, Object o1) {
        System.out.println("Updating conversation");
        for (int i = 0; i < threadList.size(); i++) {
            System.out.println("Going through threads: " + i);
            if (o == threadList.get(i)) {
                System.out.println("Updated one thread :" + i);
                System.out.println(threadList.get(i).getText());
                view.appendText(threadList.get(i).getText() + "\n", threadList.get(i).XMLHandler.getColor());
                if (threadList.size() > 1) {
                    for (int j = 0; j < threadList.size(); j++) {
                        if (j != i) {
                            forwardColor = threadList.get(i).XMLHandler.getColor();
                            forwardName = threadList.get(i).XMLHandler.getName();
                            threadList.get(j).XMLHandler.setName(forwardName);
                            threadList.get(j).XMLHandler.setColor(forwardColor);
                            removeIndex = threadList.get(i).getText().indexOf(":");
                            //+1 för att ta bort mellanslaget efter : också (Varför +2
                            forwardText = threadList.get(i).getText().substring(removeIndex + 2);
                            forwardText = threadList.get(j).XMLHandler.writeXML(forwardText);
                            threadList.get(j).writer.println(forwardText);
                        }
                    }
                }
            }
        }
    }
}
