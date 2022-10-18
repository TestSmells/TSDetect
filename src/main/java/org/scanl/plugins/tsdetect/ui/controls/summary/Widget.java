package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class Widget {
    private JPanel panelMain;
    private JLabel changeLabel;
    private JLabel analysisData;

    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem){
        panelMain.setBorder(BorderFactory.createTitledBorder(analysisSummaryItem.getPrimaryHeader()));
        //analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease){

        //}
        changeLabel.setText(analysisSummaryItem.getPrimaryChangeValue());
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
