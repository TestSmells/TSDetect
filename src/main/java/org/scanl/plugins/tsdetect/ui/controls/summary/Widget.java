package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Widget {
    private JPanel panelMain;
    private JLabel PrimaryLabel;
    private JLabel PrimaryChangeData;
    private JLabel SecondaryLabel;
    private JLabel SecondaryChangeData;

    private String content;

    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem) {
        Font primaryLabel = new Font(null, Font.PLAIN,20);
        Font changeLabel = new Font(null, Font.PLAIN,14);
        panelMain.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
        PrimaryLabel.setText(analysisSummaryItem.getPrimaryHeader()+analysisSummaryItem.getPrimaryValue());
        PrimaryLabel.setFont(primaryLabel);
        SecondaryLabel.setFont(primaryLabel);

        if(analysisSummaryItem.getPrimaryChangeType() == null) {
            PrimaryChangeData.setText("");
            PrimaryChangeData.setFont(changeLabel);
        } else {
            if (analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease)) {
                this.content = analysisSummaryItem.getPrimaryChangeValue() + " less than last analysis";
                PrimaryChangeData.setForeground(Color.GREEN);
            } else if (analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase)) {
                this.content = analysisSummaryItem.getPrimaryChangeValue() + " more than last analysis";
                PrimaryChangeData.setForeground(Color.RED);
            } else {
                this.content = "No change since last analysis";
                PrimaryChangeData.setForeground(Color.ORANGE);
            }
            PrimaryChangeData.setFont(changeLabel);
            PrimaryChangeData.setText(content);
        }

        if(analysisSummaryItem.getSecondaryHeader() == null){
            SecondaryLabel.setText("");
            SecondaryChangeData.setText("");
            SecondaryChangeData.setFont(changeLabel);
        } else {
            SecondaryLabel.setText(analysisSummaryItem.getSecondaryHeader()+ analysisSummaryItem.getSecondaryValue());
            if (analysisSummaryItem.getSecondaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease)) {
                this.content = analysisSummaryItem.getSecondaryChangeValue() + " less than last analysis";
                SecondaryChangeData.setForeground(Color.GREEN);
            } else if (analysisSummaryItem.getSecondaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase)) {
                this.content = analysisSummaryItem.getSecondaryChangeValue() + " more than last analysis";
                SecondaryChangeData.setForeground(Color.RED);
            } else {
                this.content = "No change since last analysis";
                SecondaryChangeData.setForeground(Color.ORANGE);
            }
            SecondaryChangeData.setText(content);
            SecondaryChangeData.setFont(changeLabel);
        }
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
