package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class TestSmellTypeSummary implements SummaryContent {
    Widget smellTotal;
    Widget smellDetected;
    Widget smellCommon;
    private JPanel panelMain;
    private JLabel labelTemp;
    private JScrollPane paneWidgets;
    JPanel content;

    public TestSmellTypeSummary(){
        content = new JPanel();
        smellTotal = new Widget();
        smellDetected = new Widget();
        smellCommon = new Widget();
    }

    @Override
    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }

    @Override
    public void LoadData(AnalysisSummaryItem analysisSummaryItem) {
        smellTotal.LoadWidget("Total smelly instances");
        smellDetected.LoadWidget("Detected smell types");
        smellCommon.LoadWidget("Most common smell type");

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(smellTotal.GetContent());
        content.add(smellDetected.GetContent());
        content.add(smellCommon.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }
}
