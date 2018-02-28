package ProjectChat;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.*;

public class OurGUI extends JFrame implements ActionListener {

    JTextArea textField;
    JButton sendButton;
    JButton connectButton;
    JFrame frame;
    JTextArea sendField;

    public OurGUI(){
        frame = new JFrame();
    }

    public void chatPanel() {
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
        textField = new JTextArea(8, 60);
        sendField = new JTextArea(4, 60);
        sendButton = new JButton("Send");
        
        textField.setEditable(false);
        //sendButton.addActionListener(this);
        
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        sendField.setLineWrap(true);
        sendField.setWrapStyleWord(true);
        
        chatPanel.add(textField);
        chatPanel.add(sendButton);
        chatPanel.add(sendField);    
        
        add(chatPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void startPanel() {
        JPanel startPanel = new JPanel();
        
        connectButton = new JButton("Connect");
        startPanel.add(connectButton);
        
        add(startPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JPanel createInterface3() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public JPanel createInterface4() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       String sendText= sendField.getText();
       
       
    }
}
