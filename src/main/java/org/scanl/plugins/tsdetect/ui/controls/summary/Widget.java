package org.scanl.plugins.tsdetect.ui.controls.summary;

import javax.swing.*;

public class Widget {
    private JPanel panelMain;
    private JLabel LabelTemp1;
    private JLabel LabelDynamicText;
    private JLabel LabelTemp2;

    public void LoadWidget(String text){
        LabelDynamicText.setText(text);
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
