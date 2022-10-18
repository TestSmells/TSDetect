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
    public void passFileAnalyzedData(){
        this.fileAnalyzedItem.setPrimaryHeader("Test Files Analyzed");
        this.fileAnalyzedItem.setPrimaryValue("150");
        this.fileAnalyzedItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
        this.fileAnalyzedItem.setPrimaryChangeValue("10 files");

    }

    public void passFileHasSmellData(){
        this.fileHasSmellItem.setPrimaryHeader("Files With Smells");
        this.fileHasSmellItem.setPrimaryValue("10");
        this.fileHasSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
        this.fileHasSmellItem.setPrimaryChangeValue("11 files");


    }
    public void passFileNoSmellData(){
        this.fileNoSmellItem.setPrimaryHeader("Files Without Smells");
        this.fileNoSmellItem.setPrimaryValue("2");
        this.fileNoSmellItem.setPrimaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Increase);
        this.fileNoSmellItem.setPrimaryChangeValue("12 files");


    }
    public void passFileSmelliest(){
        this.fileSmelliestItem.setPrimaryHeader("Smelliest File ");
        this.fileSmelliestItem.setPrimaryValue("HelloTest.java");


        this.fileSmelliestItem.setSecondaryHeader("Total Smells:");
        this.fileSmelliestItem.setSecondaryValue("22");
        this.fileSmelliestItem.setSecondaryChangeType(AnalysisSummaryItem.AnalysisSummaryChangeType.Decrease);
        this.fileSmelliestItem.setSecondaryChangeValue("10");


   }
    @Override
    public void LoadData() {

        passFileAnalyzedData();
        passFileHasSmellData();
        passFileNoSmellData();
        passFileSmelliest();
        summaryHeader.setText("File Summary");
        summaryHeader.setFont(new Font(null, Font.PLAIN,20));

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
}
