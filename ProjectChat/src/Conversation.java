
import java.awt.event.*;
import java.util.*;

public class Conversation implements ActionListener {

    OurGUI view;
    private List<ServerThread> threadList;

    private String name;

    public Conversation() {
        view = new OurGUI();
        view.sendButton.addActionListener(this);
        while (true) {

        }
    }

    public void deConnect() {
    }

    public String respond(String request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void add(ServerThread person) {
        threadList.add(person);
    }

    public void connect(String IP, int Portnummer) {

    }

    public ServerThread kick(ServerThread person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMess(String text) {
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).writer.append(text);
            threadList.get(i).writer.flush();

        }
    }

    public void setName() {
    }

    public void actionPerformed(ActionEvent e) {
        sendMess(view.sendField.getText());

    }
}
