package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

public class TestFileSummary implements SummaryContent {
    Widget fileAnalyzed;
    Widget fileHasSmell;
    Widget fileNoSmell;
    Widget fileSmelliest;
    private JPanel panelMain;
    private JLabel summaryHeader;
    private JScrollPane paneWidgets;
    private int totalTestFiles;
    private int analyzedFiles;
    private int before;
    private List<InspectionClassModel> allClasses;
    private int totalSmells;
    private String smelliestFile;

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
        this.before = Integer.parseInt(this.fileAnalyzedItem.getPrimaryValue());
        this.fileAnalyzedItem.setPrimaryHeader("Test Files Analyzed: ");
        this.fileAnalyzedItem.setPrimaryValue(String.valueOf(this.totalTestFiles));
        Change.setPrimaryChange(this.fileAnalyzedItem, this.totalTestFiles, this.before);

    }

    public void passFileHasSmellData(){
        this.before = Integer.parseInt(fileHasSmellItem.getPrimaryValue());
        this.fileHasSmellItem.setPrimaryHeader("Files With Smells: ");
        this.fileHasSmellItem.setPrimaryValue(String.valueOf(this.analyzedFiles));
        Change.setPrimaryChange(this.fileHasSmellItem, this.analyzedFiles, this.before);

    }
    public void passFileNoSmellData(){
        int value = this.totalTestFiles -this.analyzedFiles;
        this.before = Integer.parseInt(fileNoSmellItem.getPrimaryValue());
        this.fileNoSmellItem.setPrimaryHeader("Files Without Smells: ");
        this.fileNoSmellItem.setPrimaryValue(String.valueOf(value));
        Change.setPrimaryChange(this.fileNoSmellItem, value, this.before);


    }
    public void passFileSmelliest(){
        this.fileSmelliestItem.setPrimaryHeader("Smelliest File: ");
        this.fileSmelliestItem.setPrimaryValue(this.smelliestFile);

        this.before = Integer.parseInt(fileSmelliestItem.getSecondaryValue());
        this.fileSmelliestItem.setSecondaryHeader("Total Smells: ");
        this.fileSmelliestItem.setSecondaryValue(String.valueOf(this.totalSmells));
        Change.setSecondaryChange(this.fileSmelliestItem, totalSmells, this.before);


   }
    @Override
    public void LoadData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods) {
        this.allClasses = allClasses;
        HashMap<String, Integer> map = new HashMap<>();
        List<InspectionClassModel> smellTypeClasses;
        for(SmellType smellType: SmellType.values())
        {
            if (PluginSettings.GetSetting(smellType.toString())) {
                smellTypeClasses =  getClassesBySmell(smellType);
                for(InspectionClassModel smellTypeClass: smellTypeClasses){
                    String key = smellTypeClass.getName();
                    map.put(key, map.containsKey(key) ? map.get(key) + 1 : 1);
                }
            }
        }
        this.analyzedFiles = map.size();
        this.totalSmells = (Collections.max(map.values()));
        for(Map.Entry<String,Integer> entry: map.entrySet()){
            if(entry.getValue() == this.totalSmells){
                String temp = entry.getKey().replace(".","/");
                this.smelliestFile= temp + ".java";
            }
        }

        passFileAnalyzedData();
        passFileHasSmellData();
        passFileNoSmellData();
        passFileSmelliest();
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
    public void WriteDataToFile() {

    }

    @Override
    public void ReadDataFromFile() {

    }

    /**
     * Gets class that contains a matching smell
     *
     * @param smell The smell that is being searched for
     * @return a list of classes with the specific smell
     */
    protected List<InspectionClassModel> getClassesBySmell(SmellType smell) {
        List<InspectionClassModel> classes = new ArrayList<>();
        for (InspectionClassModel smellyClass : allClasses) {
            if (smellyClass.getSmellTypeList().contains(smell))
                classes.add(smellyClass);
        }
        return classes;
    }

    public void setTotalTestFiles(int totalTestFiles) {
        this.totalTestFiles = totalTestFiles;
    }
}
