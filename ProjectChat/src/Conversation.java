
import java.awt.event.*;
import java.util.*;

public class Conversation implements ActionListener, Observer {

    TestFrame view;
    private List<ServerThread> threadList = new ArrayList<>();

    private String name = "Oscar";

    public Conversation() {
        view = new TestFrame();
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
        view.textField.append(name + ": " + text + "\n");
        for (int i = 0; i < threadList.size(); i++) {
            threadList.get(i).XMLHandler.writeXML(text);
            System.out.println("Gets text from XML and sen!" + threadList.get(i).XMLHandler.sendText());
            threadList.get(i).writer.println(threadList.get(i).XMLHandler.sendText());
            threadList.get(i).fullText = threadList.get(i).fullText + "\n" + text;
        }
    }

    public void setName(String Name) {
        name= Name;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Button is pressed");
        sendMess(view.sendField.getText());
    }

    @Override
    public void update(Observable o, Object o1) {
        for (int i = 0; i < threadList.size(); i++) {
            if(o == threadList.get(i)){
            System.out.println("Updated one thread :" + i);
            //view.textField.setText(view.textField.getText() + "\n" + 
            //threadList.get(i).getText());
            view.textField.append(threadList.get(i).getText()+ "\n");

            }
        }
    }
}
