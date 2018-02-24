
import java.awt.event.*;
import java.util.*;

public class Conversation implements ActionListener {

    OurGUI view;
    private List<ServerThread> threadList = new ArrayList<>();

    private String name;

    public Conversation() {
        view = new OurGUI();
        view.sendButton.addActionListener(this);
//        Thread watchThread = new Thread() {
//            public void run() {
//                while (true) {
//                    for (int i = 0; i < threadList.size(); i++) {
//                        try {
//                            String input = threadList.get(i).reader.readLine();
//                            if (input != null) {
//                                view.textField.setText(view.textField.getText()
//                                                       + "\n" + input);
//                            }
//                        } catch (Exception e) {
//
//                        }
//                    }
//                }
//            }
//        };
//        watchThread.start();
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
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).writer.write(text + "\n");
            //threadList.get(i).writer.println(text);
            System.out.println("Has written: " + text);
            threadList.get(i).writer.flush();
        }
        view.textField.setText(view.textField.getText() + "\n" + text);
    }

    public void setName() {
    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Button is pressed");
        sendMess(view.sendField.getText());
        //Add event from serverthread!
    }
}
