package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;
import java.awt.*;

public class TestFileSummary implements SummaryContent {
    Widget fileAnalyzed;
    Widget fileHasSmell;
    Widget fileNoSmell;
    Widget fileSmelliest;
    private JPanel panelMain;
    private JLabel summaryHeader;
    private JScrollPane paneWidgets;
    private AnalysisSummaryItem fileAnalyzedItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem fileHasSmellItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem fileNoSmellItem  = new AnalysisSummaryItem();
    private AnalysisSummaryItem fileSmelliestItem  = new AnalysisSummaryItem();

    private int fileAnalyzedBefore;
    private int fileHasSmellsBefore;
    private int fileNoSmellBefore;
    private int smelliestFileBefore;
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
    public void passFileAnalyzedData(AnalysisSummaryItem fileAnalyzedItem, int totalFiles){
        this.fileAnalyzedItem = fileAnalyzedItem;
        this.fileAnalyzedItem.setPrimaryHeader("Test Files Analyzed: ");
        if (fileAnalyzedItem.getPrimaryValue() == String.valueOf(0)) {
            this.fileAnalyzedBefore = 0;
        } else {
            this.fileAnalyzedBefore = Integer.parseInt(fileAnalyzedItem.getPrimaryValue());
        }

        this.fileAnalyzedItem.setPrimaryValue(String.valueOf(totalFiles));

        if (totalFiles > this.fileAnalyzedBefore) {
            this.fileAnalyzedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.fileAnalyzedItem.setPrimaryChangeValue(String.valueOf(totalFiles - this.fileAnalyzedBefore));
        } else if (totalFiles < this.fileAnalyzedBefore) {
            this.fileAnalyzedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.fileAnalyzedItem.setPrimaryChangeValue(String.valueOf(this.fileAnalyzedBefore - totalFiles));
        } else {
            this.fileAnalyzedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.fileAnalyzedItem.setPrimaryChangeValue("");
        }
    }

    public void passFileHasSmellData(AnalysisSummaryItem fileHasSmellItem, int filesSmell){
        this.fileHasSmellItem = fileHasSmellItem;
        this.fileHasSmellItem.setPrimaryHeader("Files With Smells: ");
        if (fileHasSmellItem.getPrimaryValue() == String.valueOf(0)) {
            this.fileHasSmellsBefore = 0;
        } else {
            this.fileHasSmellsBefore = Integer.parseInt(fileHasSmellItem.getPrimaryValue());
        }

        this.fileHasSmellItem.setPrimaryValue(String.valueOf(filesSmell));

        if (filesSmell > this.fileHasSmellsBefore) {
            this.fileHasSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.fileHasSmellItem.setPrimaryChangeValue(String.valueOf(filesSmell - this.fileHasSmellsBefore));
        } else if (filesSmell < this.fileHasSmellsBefore) {
            this.fileHasSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.fileHasSmellItem.setPrimaryChangeValue(String.valueOf(this.fileHasSmellsBefore - filesSmell));
        } else {
            this.fileHasSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.fileHasSmellItem.setPrimaryChangeValue("");
        }
    }

    public void passFileNoSmellData(AnalysisSummaryItem fileNoSmellItem, int noSmell){
        this.fileNoSmellItem = fileNoSmellItem;
        this.fileNoSmellItem.setPrimaryHeader("Files Without Smells: ");
        if (fileHasSmellItem.getPrimaryValue() == String.valueOf(0)) {
            this.fileNoSmellBefore = 0;
        } else {
            this.fileNoSmellBefore = Integer.parseInt(fileHasSmellItem.getPrimaryValue());
        }

        this.fileNoSmellItem.setPrimaryValue(String.valueOf(noSmell));

        if (noSmell > this.fileNoSmellBefore) {
            this.fileNoSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.fileNoSmellItem.setPrimaryChangeValue(String.valueOf(noSmell - this.fileNoSmellBefore));
        } else if (noSmell < this.fileNoSmellBefore) {
            this.fileNoSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.fileNoSmellItem.setPrimaryChangeValue(String.valueOf(this.fileNoSmellBefore - noSmell));
        } else {
            this.fileNoSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.fileNoSmellItem.setPrimaryChangeValue("");
        }
    }

    public void passFileSmelliest(AnalysisSummaryItem fileSmelliestItem, int smelliestFileValue, String smelliestFile){
        this.fileSmelliestItem = fileSmelliestItem;
        this.fileSmelliestItem.setPrimaryHeader("Smelliest File: ");
        this.fileSmelliestItem.setPrimaryValue(smelliestFile);

        this.fileSmelliestItem.setSecondaryHeader("Total Smells: ");
        if (fileSmelliestItem.getSecondaryValue() == String.valueOf(0)) {
            this.smelliestFileBefore = 0;
        } else {
            this.smelliestFileBefore = Integer.parseInt(fileHasSmellItem.getSecondaryValue());
        }
        this.fileSmelliestItem.setSecondaryValue(String.valueOf(smelliestFileValue));

        if (smelliestFileValue > this.smelliestFileBefore) {
            this.fileNoSmellItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
            this.fileNoSmellItem.setSecondaryChangeValue(String.valueOf(smelliestFileValue - this.smelliestFileBefore));
        } else if (smelliestFileValue < this.smelliestFileBefore) {
            this.fileNoSmellItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
            this.fileNoSmellItem.setSecondaryChangeValue(String.valueOf(this.smelliestFileBefore - smelliestFileValue));
        } else {
            this.fileNoSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.None);
            this.fileNoSmellItem.setPrimaryChangeValue("");
        }
   }

    @Override
    public void LoadData() {

    }

    @Override
    public void LoadSmellTypeData(AnalysisSummaryItem smellTotalItem, AnalysisSummaryItem smellDetectedItem, AnalysisSummaryItem smellCommonItem, int totalMethods, int smellyMethods, String smelliestMethod, int smelliestMethodNumber) {

    }

    @Override
    public void LoadSmellFileData(AnalysisSummaryItem totalFilesItem, AnalysisSummaryItem fileHasSmellItem, AnalysisSummaryItem fileNoSmellItem, AnalysisSummaryItem fileSmelliestItem, int totalFiles, int filesSmell, int noSmell, String smelliestFile, int smelliestFileValue) {
        passFileAnalyzedData(totalFilesItem, totalFiles);
        passFileHasSmellData(fileHasSmellItem, filesSmell);
        passFileNoSmellData(fileNoSmellItem, noSmell);
        passFileSmelliest(fileSmelliestItem, smelliestFileValue, smelliestFile);
        summaryHeader.setText("File Summary");
        summaryHeader.setFont(new Font(null, Font.PLAIN,22));

        fileAnalyzed.LoadWidget(this.fileAnalyzedItem);
        fileHasSmell.LoadWidget(this.fileHasSmellItem);
        fileNoSmell.LoadWidget(this.fileNoSmellItem);
        fileSmelliest.LoadWidget(this.fileSmelliestItem);

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(fileAnalyzed.GetContent());
        content.add(fileHasSmell.GetContent());
        content.add(fileNoSmell.GetContent());
        content.add(fileSmelliest.GetContent());
        paneWidgets.getViewport().add(content);
        paneWidgets.validate();

        paneWidgets.setVisible(true);
    }

    @Override
    public void LoadData(AnalysisSummaryItem smellTotalItem, int smellTotalValue, AnalysisSummaryItem smellDetectedItem, int smellDetectedValue, AnalysisSummaryItem smellCommonItem, int smellCommonValue, String smelliestCommon) {

    }
}
