package ProjectChat;

import java.awt.*;
import javax.swing.text.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author oscar
 */
public class ChatPanel extends javax.swing.JPanel {

    StyledDocument doc;

    /**
     * Creates new form ChatPanel
     */
    public ChatPanel() {
        initComponents();
        doc = textField.getStyledDocument();

    }

    public void appendText(String text, String color) {
        Color textColor = Color.decode(color);

        SimpleAttributeSet fontKey = new SimpleAttributeSet();
        StyleConstants.setForeground(fontKey, textColor);

        try {
            doc.insertString(doc.getLength(), text, fontKey);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textField = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        sednField = new javax.swing.JEditorPane();
        sendButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        colorButton = new javax.swing.JButton();
        kickButton = new javax.swing.JButton();

        textField.setEditable(false);
        jScrollPane1.setViewportView(textField);

        jScrollPane2.setViewportView(sednField);

        sendButton.setBackground(new java.awt.Color(255, 102, 0));
        sendButton.setText("Send!");

        disconnectButton.setBackground(new java.awt.Color(255, 0, 0));
        disconnectButton.setText("Disconnect");

        colorButton.setText("Color");

        kickButton.setText("Kick");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(disconnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(colorButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kickButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colorButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kickButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton colorButton;
    public javax.swing.JButton disconnectButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JButton kickButton;
    public javax.swing.JEditorPane sednField;
    public javax.swing.JButton sendButton;
    public javax.swing.JTextPane textField;
    // End of variables declaration//GEN-END:variables
}
