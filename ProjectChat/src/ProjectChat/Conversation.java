package ProjectChat;

import java.awt.event.*;
import java.util.*;
import javax.swing.text.html.*;

public class Conversation implements ActionListener, Observer {

    ChatPanel view;
    private List<ServerThread> threadList = new ArrayList<>();

    private String name = "Oscar";
    HTMLDocument doc;
    String ownColor = "#ff0000";

    public Conversation() {
        view = new ChatPanel();
        view.sendButton.addActionListener(this);
        
    }

    public void deConnect() {
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
        view.appendText(name + ": " + text + "\n",ownColor);
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).XMLHandler.writeXML(text);
            System.out.println("Gets text from XML and sen!" + threadList.get(i).XMLHandler.sendText());
            threadList.get(i).writer.println(threadList.get(i).XMLHandler.sendText());
            threadList.get(i).fullText = threadList.get(i).fullText + "\n" + text;
        }
    }

    public void setName(String Name) {
        name = Name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button is pressed");
        sendMess(view.sednField.getText());
    }

    @Override
    public void update(Observable o, Object o1) {
        for (int i = 0; i < threadList.size(); i++) {
            if (o == threadList.get(i)) {
                System.out.println("Updated one thread :" + i);
                //view.textField.setText(view.textField.getText() + "\n" + 
                //threadList.get(i).getText());
                System.out.println(threadList.get(i).getText());
                view.appendText(threadList.get(i).getText()+ "\n",threadList.get(i).XMLHandler.getColor());

            }
        }
    }
}
