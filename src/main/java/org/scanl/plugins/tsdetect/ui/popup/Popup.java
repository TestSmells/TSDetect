package org.scanl.plugins.tsdetect.ui.popup;

import javax.swing.*;
import java.awt.*;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

public class Popup{
    private final JOptionPane consentForm;
    private final JButton confirm;
    private final JButton deny;
    //Initialized to null so it runs the first time
    private Boolean choice = null;
    private final JLabel infor;

    public Popup() {
        consentForm = new JOptionPane();
        infor = new JLabel("Do you consent to sending anonymous test smell data for research purposes?");
        confirm = new JButton();
        deny = new JButton();
        if (choice  == null) {
            consentForm.add(infor);
            consentForm.add(confirm);
            consentForm.add(deny);
            consentForm.setVisible(true);
            confirm.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    choice = true;
                }
            });
            deny.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    choice = false;
                }
            });
        }
    }
}
