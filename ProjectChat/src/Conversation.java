
import java.awt.event.*;
import java.util.*;

public class Conversation implements ActionListener {

    OurGUI view;
    private List<ServerThread> threadList;

    private String name;

    public Conversation() {
        view = new OurGUI();
        view.sendButton.addActionListener(this);
        
    }

    public void deConnect() {
    }

    public String respond(String request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(ServerThread person) {
        System.out.println("Adds person");
        threadList.add(person);
        person.start();
    }

    public void connect(String IP, int Portnummer) {

    }

    public ServerThread kick(ServerThread person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMess(String text) {
        System.out.println("Sending message..." + text);
        System.out.println("Thread List length: " + threadList.size());
        for (int i = 0; i < threadList.size(); i++) {
            System.out.println("On thread: " + i);
            threadList.get(i).writer.append(text);
            System.out.println("Has written: " + text);
            threadList.get(i).writer.flush();
            //view.textField.
        }
    }

    public void setName() {
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Button is pressed");
        sendMess(view.sendField.getText());

    }
}
