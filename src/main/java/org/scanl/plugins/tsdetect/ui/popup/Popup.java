package org.scanl.plugins.tsdetect.ui.popup;

import javax.swing.*;
import java.awt.*;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

public class Popup{
    javax.swing.Popup pop;
    PopupFactory pf;
    private final JFrame frame;
    private final JPanel consentForm;
    private final JButton confirm;
    private final JButton deny;
    //Initialized to null so it runs the first time
    private Boolean choice = null;
    private final JLabel infor;

    public Popup() {
        consentForm = new JPanel();
        frame = new JFrame("Consent Form");
        frame.setSize(400,400);
        pf = new PopupFactory();
        infor = new JLabel("Do you consent to sending anonymous test smell data for research purposes?");
        confirm = new JButton();
        deny = new JButton();
        if (choice  == null) {
            consentForm.add(infor);
            consentForm.add(confirm);
            consentForm.add(deny);
            frame.add(consentForm);
            pop = pf.getPopup(frame, consentForm, 180, 100);
            frame.setVisible(true);
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
    public static void main(String args[]){
        Popup p = new Popup();
    }
}
