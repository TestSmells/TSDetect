package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;

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
        this.fileAnalyzedItem.setPrimaryChangeValue("Increase 10 instances");


    }

    public void passFileHasSmellData(){
        this.fileHasSmellItem.setPrimaryHeader("Files With Smells");
        this.fileHasSmellItem.setPrimaryValue("10");
        this.fileHasSmellItem.setPrimaryChangeValue("None");


    }
    public void passFileNoSmellData(){
        this.fileNoSmellItem.setPrimaryHeader("File Without Smells");
        this.fileNoSmellItem.setPrimaryValue("Lazy Test");


    }
    public void passFileSmelliest(){
        this.fileSmelliestItem.setPrimaryHeader("Smelliest File ");
        this.fileSmelliestItem.setPrimaryValue("HelloTest.java");

        this.fileSmelliestItem.setSecondaryHeader("Total Smells:");
        this.fileSmelliestItem.setSecondaryValue("22");
        this.fileSmelliestItem.setSecondaryChangeValue("Increase 10 Smells");


   }
    @Override
    public void LoadData() {

        passFileAnalyzedData();
        passFileHasSmellData();
        passFileNoSmellData();
        passFileSmelliest();
        summaryHeader.setText("File Summary");

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
