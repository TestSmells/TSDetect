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
    public Popup(){
        consentForm = new JPanel();
        if(choice != null)
            consentForm.setVisible(true);
    }
}