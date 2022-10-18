package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Widget {
    private JPanel panelMain;
    private JLabel changeLabel;
    private JLabel analysisData;

    public void LoadWidget(AnalysisSummaryItem analysisSummaryItem){
        panelMain.setBorder(BorderFactory.createTitledBorder(null, analysisSummaryItem.getPrimaryHeader(), TitledBorder.LEFT, TitledBorder.TOP, new Font(null, Font.PLAIN,16)));
        changeLabel.setText(analysisSummaryItem.getPrimaryChangeValue());
        panelMain.setVisible(true);
    }

    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
}
