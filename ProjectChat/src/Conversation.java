
import java.awt.event.*;
import java.util.*;

public class Conversation implements ActionListener {

    OurGUI view;
    private List<ServerThread> threadList = new ArrayList<>();
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
        person.start();
        threadList.add(person);
        updateGUI(person);
    }

    public void connect(String IP, int Portnummer) {

    }

    public ServerThread kick(ServerThread person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMess(String text) {
        view.textField.setText(view.textField.getText() + "\n" + text);
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).writer.println(text);
            threadList.get(i).fullText = threadList.get(i).fullText + "\n" + text;

            //threadList.get(i).writer.write(text);
        }
    }

    public void setName() {
    }

    public void updateGUI(ServerThread person) {
        Thread GUIThread = new Thread() {
            public void run() {
                //synchronized (this) {
                while (true) {
                    if (view.textField.getText() != person.fullText) {
                        view.textField.setText(person.fullText);
                        //try {
                        //System.out.println("väääntar");
                        //wait();
                        //System.out.println("post wait");
                        //} catch (Exception e) {
                        //    e.getMessage();
                        // }

                    }
                    //    }
                }
            }
        };
        GUIThread.start();
    }

    public void actionPerformed(ActionEvent e) {
        sendMess(view.sendField.getText());

    }
}
