package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class TestFileSummary implements SummaryContent {
    Widget fileAnalyzed;
    Widget fileHasSmell;
    Widget fileNoSmell;
    Widget fileSmelliest;
    private JPanel panelMain;
    private JLabel labelTemp;
    private JScrollPane paneWidgets;
    JPanel content;

    public TestFileSummary(){
        content = new JPanel();
        fileAnalyzed = new Widget();
        fileHasSmell = new Widget();
        fileNoSmell = new Widget();
        fileSmelliest = new Widget();
    }

    @Override
    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
    public void setDataFileSummary(){

    }
    @Override
    public void LoadData() {
        fileAnalyzed.LoadWidget(new AnalysisSummaryItem(), "Test Files Analyzed", "[more/less] than last analysis");
        fileHasSmell.LoadWidget(new AnalysisSummaryItem(),"Files With Smells", "[more/less] than last analysis");
        fileNoSmell.LoadWidget(new AnalysisSummaryItem(),"Files Without Smells", "[more/less] than last analysis");
        fileSmelliest.LoadWidget(new AnalysisSummaryItem(),"Smelliest file", "[more/less] than last analysis");

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(fileAnalyzed.GetContent());
        content.add(fileHasSmell.GetContent());
        content.add(fileNoSmell.GetContent());
        content.add(fileSmelliest.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }
}
