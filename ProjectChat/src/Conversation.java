
import java.awt.event.*;
import java.util.*;

public class Conversation implements ActionListener, Observer {

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
        threadList.add(person);
        person.addObserver(this);
    }

    public void connect(String IP, int Portnummer) {

    }

    public ServerThread kick(ServerThread person) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMess(String text) {
        view.textField.append(text);
        for (int i = 0; i < threadList.size(); i++) {

            threadList.get(i).writer.println(text);
            threadList.get(i).fullText = threadList.get(i).fullText + "\n" + text;

            //threadList.get(i).writer.write(text);

        }
    }

    public void setName() {
    }

//    public void updateGUI(ServerThread person) {
//        Thread GUIThread = new Thread() {
//            public void run() {
//                //synchronized (this) {
//                while (true) {
//                    if (view.textField.getText() != person.fullText) {
//                        view.textField.setText(person.fullText);
//                        //try {
//                        //System.out.println("väääntar");
//                        //wait();
//                        //System.out.println("post wait");
//                        //} catch (Exception e) {
//                        //    e.getMessage();
//                        // }
//
//                    }
//                    //    }
//                }
//            }
//        };
//        GUIThread.start();
//    }

    public void actionPerformed(ActionEvent e) {
        System.out.println("Button is pressed");
        sendMess(view.sendField.getText());
        //Add event from serverthread!
    }

    @Override
    public void update(Observable o, Object o1) {
        for (int i = 0; i < threadList.size(); i++) {
            if(o == threadList.get(i)){
            //view.textField.setText(view.textField.getText() + "\n" + 
            //threadList.get(i).getText());
            view.textField.append(threadList.get(i).getText()+ "\n");

            }
        }
    }
}
