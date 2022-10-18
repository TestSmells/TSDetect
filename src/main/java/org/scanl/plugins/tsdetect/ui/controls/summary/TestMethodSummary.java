package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

public class TestMethodSummary implements SummaryContent {
    Widget methodTotalTest;
    Widget methodTotalSmelly;
    Widget methodSmelliest;
    private JPanel panelMain;
    private JLabel summaryHeader;
    private JScrollPane paneWidgets;
    private AnalysisSummaryItem methodTotalTestItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem methodTotalSmellyItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem methodSmelliestItem  = new AnalysisSummaryItem();
    JPanel content;

    public TestMethodSummary(){
        content = new JPanel();
        methodTotalTest = new Widget();
        methodTotalSmelly = new Widget();
        methodSmelliest = new Widget();
    }

    @Override
    public JPanel GetContent() {
        panelMain.setVisible(true);
        return panelMain;
    }
    public void passMethodTotalTestData(){
        this.methodTotalTestItem.setPrimaryHeader("Total test methods");
        this.methodTotalTestItem.setPrimaryValue("250");
        this.methodTotalTestItem.setPrimaryChangeValue("Increase 10 methods");


    }

    public void passMethodTotalSmellyItemData(){
        this.methodTotalSmellyItem.setPrimaryHeader("Smelly methods");
        this.methodTotalSmellyItem.setPrimaryValue("200");
        this.methodTotalSmellyItem.setPrimaryChangeValue("None");


    }
    public void passMethodSmelliestItemData(){
        this.methodSmelliestItem.setPrimaryHeader("Smelliest method");
        this.methodSmelliestItem.setPrimaryValue("testABC()");

        this.methodSmelliestItem.setSecondaryHeader("Total smells:");
        this.methodSmelliestItem.setSecondaryValue("50");
        this.methodSmelliestItem.setSecondaryChangeValue("Increase 10 smells");

    }

    @Override
    public void LoadData() {
        passMethodTotalTestData();
        passMethodTotalSmellyItemData();
        passMethodSmelliestItemData();
        summaryHeader.setText("Method Summary");
        methodTotalTest.LoadWidget(new AnalysisSummaryItem());
        methodTotalSmelly.LoadWidget(new AnalysisSummaryItem());
        methodSmelliest.LoadWidget(new AnalysisSummaryItem());

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(methodTotalTest.GetContent());
        content.add(methodTotalSmelly.GetContent());
        content.add(methodSmelliest.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }
}
