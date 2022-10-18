package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class Widget {
    private JPanel panelMain;
    private JLabel PrimaryLabel;
    private JLabel PrimaryChangeData;
    private JLabel SecondaryLabel;
    private JLabel SecondaryChangeData;

    private String content;


    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem){
        panelMain.setBorder(BorderFactory.createTitledBorder(analysisSummaryItem.getPrimaryHeader()));
        PrimaryLabel.setText(analysisSummaryItem.getPrimaryValue());
        if(analysisSummaryItem.getPrimaryChangeType() == null){
            PrimaryChangeData.setText("");
        } else {
            if (analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease)) {
                this.content = analysisSummaryItem.getPrimaryChangeValue() + " less than last analysis";
            } else if (analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase)) {
                this.content = analysisSummaryItem.getPrimaryChangeValue() + " more than last analysis";
            } else {
                this.content = "No Change";
            }

            PrimaryChangeData.setText(content);
        }
        if(analysisSummaryItem.getSecondaryHeader() == null){
            SecondaryLabel.setText("");
            SecondaryChangeData.setText("");
        } else {
            SecondaryLabel.setText(analysisSummaryItem.getSecondaryHeader()+ analysisSummaryItem.getSecondaryValue());
            if (analysisSummaryItem.getSecondaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease)) {
                this.content = analysisSummaryItem.getSecondaryChangeValue() + " less than last analysis";
            } else if (analysisSummaryItem.getSecondaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase)) {
                this.content = analysisSummaryItem.getSecondaryChangeValue() + " more than last analysis";
            } else {
                this.content = "No change";
            }
            SecondaryChangeData.setText(content);
        }
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
