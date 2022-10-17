package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class Widget {
    private JPanel panelMain;
    private JLabel changeLabel;
    private JLabel analysisData;
    private JLabel subHeader;

    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem, String title, String change, String...subtitle){
        panelMain.setBorder(BorderFactory.createTitledBorder(title));
        changeLabel.setText(change);
        analysisData.setText("...analysis data...");
        /* Set visible to true if the widget needs the subHeader */
        subHeader.setText(subtitle[0]);
        subHeader.setVisible(false);
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
