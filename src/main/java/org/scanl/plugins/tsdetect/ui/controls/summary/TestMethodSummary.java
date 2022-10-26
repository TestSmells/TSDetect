package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;
import java.awt.*;

public class TestMethodSummary implements SummaryContent {
    Widget methodTotalTest;
    Widget methodTotalSmelly;
    Widget methodSmelliest;
    private JPanel panelMain;
    private JLabel summaryHeader;
    private JScrollPane paneWidgets;
    private AnalysisSummaryItem methodTotalTestItem;
    private AnalysisSummaryItem methodTotalSmellyItem;
    private AnalysisSummaryItem methodSmelliestItem;

    private int methodTotalPre;
    private int smellyMethodsPre;
    private int smelliestMethodNumberPre;
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

    @Override
    public void LoadData() {

    }

    public void passMethodTotalTestData(AnalysisSummaryItem methodTotalTestItem, int totalMethod){
        this.methodTotalTestItem = methodTotalTestItem;
        this.methodTotalTestItem.setPrimaryHeader("Total test methods: ");
        if(methodTotalTestItem.getPrimaryValue() == String.valueOf(0)){
            this.methodTotalPre = 0;
        } else {
            this.methodTotalPre = Integer.parseInt(methodTotalTestItem.getPrimaryValue());
        }
        this.methodTotalTestItem.setPrimaryValue(String.valueOf(totalMethod));
        if(totalMethod > methodTotalPre){
            this.methodTotalTestItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.methodTotalTestItem.setPrimaryChangeValue(String.valueOf(totalMethod-methodTotalPre));
        } else if (totalMethod < methodTotalPre){
            this.methodTotalTestItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.methodTotalTestItem.setPrimaryChangeValue(String.valueOf(methodTotalPre - totalMethod));
        } else {
            this.methodTotalTestItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.methodTotalTestItem.setPrimaryChangeValue("");
        }
    }

    public void passMethodTotalSmellyItemData(AnalysisSummaryItem methodTotalSmellyItem, int smellyMethods){
        this.methodTotalSmellyItem = methodTotalSmellyItem;
        this.methodTotalSmellyItem.setPrimaryHeader("Smelly methods: ");
        if(methodTotalSmellyItem.getPrimaryValue() == String.valueOf(0)){
            this.smellyMethodsPre = 0;
        } else {
            this.smellyMethodsPre = Integer.parseInt(methodTotalSmellyItem.getPrimaryValue());
        }
        this.methodTotalSmellyItem.setPrimaryValue(String.valueOf(smellyMethods));
        if( smellyMethods > smellyMethodsPre){
            this.methodTotalSmellyItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.methodTotalSmellyItem.setPrimaryChangeValue(String.valueOf(smellyMethods - smellyMethodsPre));
        } else if (smellyMethods < smellyMethodsPre){
            this.methodTotalSmellyItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.methodTotalSmellyItem.setPrimaryChangeValue(String.valueOf(smellyMethodsPre - smellyMethods));
        } else {
            this.methodTotalSmellyItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.methodTotalSmellyItem.setPrimaryChangeValue("");
        }

    }

    public void passMethodSmelliestItemData(AnalysisSummaryItem methodSmelliestItem, String smelliestMethod, int smelliestMethodNumber){
        this.methodSmelliestItem = methodSmelliestItem;
        this.methodSmelliestItem.setPrimaryHeader("Smelliest method: ");
        this.methodSmelliestItem.setPrimaryValue(smelliestMethod);
        this.methodSmelliestItem.setSecondaryHeader("Total smells: ");
        if(methodTotalSmellyItem.getSecondaryValue() == String.valueOf(0)){
            this.smelliestMethodNumberPre = 0;
        } else {
            this.smelliestMethodNumberPre = Integer.parseInt(methodSmelliestItem.getSecondaryValue());
        }
        this.methodSmelliestItem.setSecondaryValue(String.valueOf(smelliestMethodNumber));

        if( smelliestMethodNumber > smelliestMethodNumberPre){
            this.methodSmelliestItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.methodSmelliestItem.setSecondaryChangeValue(String.valueOf(smelliestMethodNumber - smelliestMethodNumberPre));
        } else if (smelliestMethodNumber < smelliestMethodNumberPre){
            this.methodSmelliestItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.methodSmelliestItem.setSecondaryChangeValue(String.valueOf(smelliestMethodNumberPre -smelliestMethodNumber));
        } else {
            this.methodSmelliestItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.methodSmelliestItem.setSecondaryChangeValue("");
        }

    }

    @Override
    public void LoadSmellTypeData(AnalysisSummaryItem methodTotalTestItem,AnalysisSummaryItem methodTotalSmellyItem, AnalysisSummaryItem methodSmelliestItem,
                                  int totalMethods, int smellyMethods, String smelliestMethod, int smelliestMethodNumber){
        passMethodTotalTestData(methodTotalTestItem,totalMethods);
        passMethodTotalSmellyItemData(methodTotalSmellyItem,smellyMethods);
        passMethodSmelliestItemData(methodSmelliestItem,smelliestMethod, smelliestMethodNumber);
        summaryHeader.setText("Method Summary");
        summaryHeader.setFont(new Font(null, Font.PLAIN,22));

        methodTotalTest.LoadWidget(this.methodTotalTestItem);
        methodTotalSmelly.LoadWidget(this.methodTotalSmellyItem);
        methodSmelliest.LoadWidget(this.methodSmelliestItem);

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(methodTotalTest.GetContent());
        content.add(methodTotalSmelly.GetContent());
        content.add(methodSmelliest.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }

}
