import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.*;

public class OurGUI extends JPanel implements ActionListener {

    JTextArea textField;
    JButton sendButton;
    JFrame frame;
    JTextArea sendField;

    public OurGUI(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        frame = new JFrame();
        createInterface1();
        setVisible(true);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createInterface1() {
        textField = new JTextArea(8, 60);
        sendField = new JTextArea(4, 60);
        sendButton = new JButton("Send");
        
        textField.setEditable(false);
        //sendButton.addActionListener(this);
        
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        sendField.setLineWrap(true);
        sendField.setWrapStyleWord(true);
        
        add(textField);
        add(sendButton);
        add(sendField);    
    }

    public JPanel createInterface2() {
        throw new UnsupportedOperationException("Not supported yet.");
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
