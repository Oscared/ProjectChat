package ProjectChat;

import java.awt.event.*;

public class Start implements ActionListener{

    PortChooser portChooser;
    int port;
    String ownName;
    Controller controller;
    
    
    
    public Start(){
        portChooser = new PortChooser();
        portChooser.startButton.addActionListener(this);
        
    }

    public static void main(String[] args) {
        Start start = new Start();
    
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == portChooser.startButton){
            port = Integer.parseInt(portChooser.portField.getText());
            ownName = portChooser.nameField.getText();
            controller = new Controller(port);
            controller.setOwnName(ownName);
        }
}
}
