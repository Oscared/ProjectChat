
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.*;

public class OurGUI extends JPanel implements ActionListener {

    JTextField textField;
    JButton sendButton;
    JFrame frame;
    JTextField sendField;
    
    public static void main(String [] args){
        
        OurGUI gui = new OurGUI();
    }
    public OurGUI(){
        
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        createInterface1();
        setVisible(true);
        frame.add(this);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void createInterface1() {
        textField = new JTextField("Text will be seen here!");
        sendField = new JTextField("Type in text here.");
        sendButton = new JButton("Send");
        sendField.setEditable(true);
        sendButton.addActionListener(this);
        add(textField, BorderLayout.NORTH);
        add(sendButton, BorderLayout.WEST);
        add(sendField, BorderLayout.EAST);    
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
