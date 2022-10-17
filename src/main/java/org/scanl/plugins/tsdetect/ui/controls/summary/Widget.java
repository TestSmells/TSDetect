package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class Widget {
    private JPanel panelMain;
    private JLabel changeLabel;
    private JLabel analysisData;

    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem, String title, String change){
        panelMain.setBorder(BorderFactory.createTitledBorder(title));
        changeLabel.setText(change);
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
gi