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

    private int smellTotalBefore;
    private int smellDetectedBefore;
    private int smellCommonBefore;
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

    }

    public void passSmellTotalData(AnalysisSummaryItem smellTotalItem, int smellTotal){
        this.smellTotalItem = smellTotalItem;
        this.smellTotalItem.setPrimaryHeader("Total smelly instances: ");
        if (smellTotalItem.getPrimaryValue() == String.valueOf(0)) {
            this.smellTotalBefore = 0;
        } else {
            this.smellTotalBefore = Integer.parseInt(smellTotalItem.getPrimaryValue());
        }

        this.smellTotalItem.setPrimaryValue(String.valueOf(smellTotal));

        if (smellTotal > this.smellTotalBefore) {
            this.smellTotalItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.smellTotalItem.setPrimaryChangeValue(String.valueOf(smellTotal - this.smellTotalBefore));
        } else if (smellTotal < this.smellTotalBefore) {
            this.smellTotalItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.smellTotalItem.setPrimaryChangeValue(String.valueOf(this.smellTotalBefore - smellTotal));
        } else {
            this.smellTotalItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.smellTotalItem.setPrimaryChangeValue("");
        }
    }

    public void passSmellDetectedData(AnalysisSummaryItem smellDetectedItem, int smellDetected){
        this.smellDetectedItem = smellTotalItem;
        this.smellDetectedItem.setPrimaryHeader("Detected smell types: ");
        if (smellDetectedItem.getPrimaryValue() == String.valueOf(0)) {
            this.smellDetectedBefore = 0;
        } else {
            this.smellDetectedBefore = Integer.parseInt(smellDetectedItem.getPrimaryValue());
        }

        this.smellDetectedItem.setPrimaryValue(String.valueOf(smellTotal));

        if (smellDetected > this.smellDetectedBefore) {
            this.smellDetectedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.smellDetectedItem.setPrimaryChangeValue(String.valueOf(smellDetected - this.smellDetectedBefore));
        } else if (smellDetected < this.smellDetectedBefore) {
            this.smellDetectedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.smellDetectedItem.setPrimaryChangeValue(String.valueOf(this.smellDetectedBefore - smellDetected));
        } else {
            this.smellDetectedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.smellDetectedItem.setPrimaryChangeValue("");
        }
    }
    public void passSmellCommonData(AnalysisSummaryItem smellCommonItem, int smellCommonValue, String smellCommon){
        this.smellCommonItem = smellCommonItem;
        this.smellCommonItem.setPrimaryHeader("Most common smell type: ");
        this.smellCommonItem.setPrimaryValue(smellCommon);

        this.smellCommonItem.setSecondaryHeader("Total instances: ");
        if (smellCommonItem.getSecondaryValue() == String.valueOf(0)) {
            this.smellCommonBefore = 0;
        } else {
            this.smellCommonBefore = Integer.parseInt(smellCommonItem.getSecondaryValue());
        }

        this.smellCommonItem.setSecondaryValue(String.valueOf(smellCommonValue));
        if (smellCommonValue > this.smellCommonBefore) {
            this.smellCommonItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.smellCommonItem.setSecondaryChangeValue(String.valueOf(smellCommonValue - this.smellCommonBefore));
        } else if (smellCommonValue < this.smellCommonBefore) {
            this.smellCommonItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.smellCommonItem.setSecondaryChangeValue(String.valueOf(this.smellCommonBefore - smellCommonValue));
        } else {
            this.smellCommonItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.smellCommonItem.setPrimaryChangeValue("");
        }
    }

    @Override
    public void LoadData(AnalysisSummaryItem smellTotalItem, int smellTotalValue, AnalysisSummaryItem smellDetectedItem, int smellDetectedValue, AnalysisSummaryItem smellCommonItem, int smellCommonValue, String smelliestCommon) {
        passSmellTotalData(smellTotalItem, smellTotalValue);
        passSmellDetectedData(smellDetectedItem, smellDetectedValue);
        passSmellCommonData(smellCommonItem, smellCommonValue, smelliestCommon);
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

    @Override
    public void LoadSmellTypeData(AnalysisSummaryItem smellTotalItem, AnalysisSummaryItem smellDetectedItem, AnalysisSummaryItem smellCommonItem, int totalMethods, int smellyMethods, String smelliestMethod, int smelliestMethodNumber) {

    }

    @Override
    public void LoadSmellFileData(AnalysisSummaryItem totalFilesItem, AnalysisSummaryItem fileHasSmellItem, AnalysisSummaryItem fileNoSmellItem, AnalysisSummaryItem fileSmelliestItem, int totalFiles, int filesSmell, int noSmell, String smelliestFile, int smelliestFileValue) {

    }
}
