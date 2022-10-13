package org.scanl.plugins.tsdetect.popup;

import javax.swing.*;
import java.awt.*;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

public class Popup{
    private final JPanel consentForm;
    private final JCheckBox decisionBox;
    private final JButton confirm;
    private boolean choice = null;
    private final JLabel infor;
    public Popup(){
        consentForm = new JPanel();
        confirm = new JButton();
        infor = new JLabel("Do you want to share your anonymous usage data?");
        decisionBox = new JCheckBox();
        if(choice != null){
            consentForm.add(infor);
            consentForm.add(decisionBox);
            consentForm.add(confirm);
            consentForm.setVisible(true);
            confirm.addActionListener(new java.awt.event.ActionListener(){
                public void actionPerformed(java.awt.event.ActionEvent evt){
                   if(decisionBox.isSelected() == true){
                       choice = true;
                   }
                   else{
                       choice = false;
                   }
                   consentForm.dispose();
                }
            });
        }
    }
}