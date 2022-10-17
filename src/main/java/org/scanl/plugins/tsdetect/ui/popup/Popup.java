package org.scanl.plugins.tsdetect.ui.popup;

import javax.swing.*;
import java.awt.*;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

public class Popup{
    private final JPanel consentForm;
    private final JCheckBox decisionBox;
    private final JButton confirm;
    private boolean choice = false;
    private final JLabel infor;

    public Popup() {
        consentForm = new JPanel();
        confirm = new JButton();
        infor = new JLabel("Do you want to share your anonymous usage data?");
        decisionBox = new JCheckBox();
        if (!choice) {
            consentForm.add(infor);
            consentForm.add(decisionBox);
            consentForm.add(confirm);
            consentForm.setVisible(true);
            confirm.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    choice = decisionBox.isSelected();
                    //consentForm.dispose();
                }
            });
        }
    }
}
