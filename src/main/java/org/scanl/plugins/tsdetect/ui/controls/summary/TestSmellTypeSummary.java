package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;
import java.awt.*;

public class TestSmellTypeSummary implements SummaryContent {
    Widget smellTotal;
    Widget smellDetected;
    Widget smellCommon;
    private JPanel panelMain;
    private JLabel summaryHeader;
    private JScrollPane paneWidgets;
    private AnalysisSummaryItem smellTotalItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem smellDetectedItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem smellCommonItem  = new AnalysisSummaryItem();


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

    public void passSmellTotalData(){
        this.smellTotalItem.setPrimaryHeader("Total smelly instances: ");
        this.smellTotalItem.setPrimaryValue("150");
        this.smellTotalItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
        this.smellTotalItem.setPrimaryChangeValue("10");


    }

    public void passSmellDetectedData(){
        this.smellDetectedItem.setPrimaryHeader("Detected smell types: ");
        this.smellDetectedItem.setPrimaryValue("10");
        this.smellDetectedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
        this.smellDetectedItem.setPrimaryChangeValue("");


    }
    public void passSmellCommonData(){
        this.smellCommonItem.setPrimaryHeader("Most common smell type: ");
        this.smellCommonItem.setPrimaryValue("Lazy Test");
        this.smellCommonItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);

        this.smellCommonItem.setSecondaryHeader("Total instances: ");
        this.smellCommonItem.setSecondaryValue("50");
        this.smellCommonItem.setSecondaryChangeValue("10");

    }

    @Override
    public void LoadData() {
        passSmellCommonData();
        passSmellDetectedData();
        passSmellTotalData();
        summaryHeader.setText("Smell Type Summary");
        summaryHeader.setFont(new Font(null, Font.PLAIN,22));
        smellTotal.LoadWidget(smellTotalItem);
        smellDetected.LoadWidget(smellDetectedItem);
        smellCommon.LoadWidget(smellCommonItem);

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(smellTotal.GetContent());
        content.add(smellDetected.GetContent());
        content.add(smellCommon.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }
}
