package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Widget {
    private JPanel panelMain;
    private JLabel changeLabel;
    private JLabel analysisData;

    private String content;


    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem){
        panelMain.setBorder(BorderFactory.createTitledBorder(analysisSummaryItem.getPrimaryHeader()));
        if(analysisSummaryItem.getPrimaryChangeType() == null){
            changeLabel.setText(analysisSummaryItem.getPrimaryValue());
        } else {
            if (analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease)) {
                this.content = analysisSummaryItem.getPrimaryChangeValue() + " less than last analysis";
            } else if (analysisSummaryItem.getPrimaryChangeType().equals(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase)) {
                this.content = analysisSummaryItem.getPrimaryChangeValue() + " more than last analysis";
            } else {
                this.content = analysisSummaryItem.getPrimaryChangeValue();
            }

            changeLabel.setText(analysisSummaryItem.getPrimaryValue() + '\n' + content);
        }
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
