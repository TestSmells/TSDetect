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
    public void LoadData() {
        smellTotal.LoadWidget(new AnalysisSummaryItem(),"Total smelly instances", "[more/less] than last analysis");
        smellDetected.LoadWidget(new AnalysisSummaryItem(),"Detected smell types", "[more/less] than last analysis");
        smellCommon.LoadWidget(new AnalysisSummaryItem(),"Most common smell type", "[more/less] than last analysis");

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(smellTotal.GetContent());
        content.add(smellDetected.GetContent());
        content.add(smellCommon.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }
}
