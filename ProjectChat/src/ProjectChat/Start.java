/**
 * Class Start
 * Authors Martin Ståhl & Oscar Örnberg
 * Version 1.0
 * Copywrite authors
 */
package ProjectChat;

import java.awt.event.*;

/**
 * Class start
 *
 * @author mastxah
 */
public class Start implements ActionListener {

    PortChooser portChooser;
    int port;
    String ownName;
    Controller controller;

    /**
     * Contrstructor that starts the portChooser
     */
    public Start() {
        portChooser = new PortChooser();
        portChooser.startButton.addActionListener(this);

    }

    /**
     * Main method that calls the start contstructor
     *
     * @param args
     */
    public static void main(String[] args) {
        Start start = new Start();

    }

    /**
     * Triggers when startButton is pressed
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == portChooser.startButton) {
            port = Integer.parseInt(portChooser.portField.getText());
            ownName = portChooser.nameField.getText();
            controller = new Controller(port);
            controller.setOwnName(ownName);
            portChooser.dispose();
        }
    }
}
