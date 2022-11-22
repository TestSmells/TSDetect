package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.common.CreateXml;
import org.scanl.plugins.tsdetect.common.Xml;
import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.*;
import java.util.List;
import java.util.Map;

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
    private List<InspectionMethodModel> allMethods;
    private int totalMethods;
    private int smellyMethods;
    private String smelliestMethod;
    private int smelliestMethodNumber;
    private int before;

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
        this.before = Integer.parseInt(Xml.getTotalTestMethods());
        this.methodTotalTestItem.setPrimaryHeader(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SUMMARY.HEADER.METHOD.TOTAL"));
        this.methodTotalTestItem.setPrimaryValue(String.valueOf(this.totalMethods));
        CreateXml.setTotalTestMethods(String.valueOf(this.totalMethods));
        Change.setPrimaryChange(this.methodTotalTestItem, this.totalMethods, this.before);

    }

    public void passMethodTotalSmellyItemData(){
        this.before = Integer.parseInt(Xml.getSmellyMethods());
        this.methodTotalSmellyItem.setPrimaryHeader(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SUMMARY.HEADER.METHOD.SMELLY"));
        this.methodTotalSmellyItem.setPrimaryValue(String.valueOf(this.smellyMethods));
        CreateXml.setSmellyMethods(String.valueOf(this.smellyMethods));
        Change.setPrimaryChange(this.methodTotalSmellyItem, this.smellyMethods, this.before);
    }

    public void passMethodSmelliestItemData(){
        this.methodSmelliestItem.setPrimaryHeader(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SUMMARY.HEADER.METHOD.SMELLIEST"));
        this.methodSmelliestItem.setPrimaryValue(this.smelliestMethod);
        this.before = Integer.parseInt(Xml.getMethodTotalSmells());
        this.methodSmelliestItem.setSecondaryHeader(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SUMMARY.HEADER.METHOD.TOTAL.SMELLS"));
        this.methodSmelliestItem.setSecondaryValue(String.valueOf(this.smelliestMethodNumber));
        CreateXml.setMethodTotalSmells(String.valueOf(this.smelliestMethodNumber));
        Change.setSecondaryChange(this.methodSmelliestItem, this.smelliestMethodNumber,this.before);

    }

    @Override
    public void LoadData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods) {
        this.allMethods = allMethods;
        this.totalMethods = allMethods.size();
        HashMap<String, Integer> map = new HashMap<>(); // string for method name, integer for related smells
        List<InspectionMethodModel> smellTypeMethods;
        for(SmellType smellType: SmellType.values())
        {
            if (PluginSettings.GetSetting(smellType.toString())) {
                smellTypeMethods =  getMethodBySmell(smellType);
                for(InspectionMethodModel method: smellTypeMethods){
                    String key = method.getName();
                    map.put(key, map.containsKey(key) ? map.get(key) + 1 : 1);
                }
            }
        }
        this.smellyMethods = map.size();
        this.smelliestMethodNumber = (Collections.max(map.values()));
        for(Map.Entry<String,Integer> entry: map.entrySet()){
            if(entry.getValue() == this.smelliestMethodNumber){
                this.smelliestMethod = entry.getKey();
            }
        }
        passMethodTotalTestData();
        passMethodTotalSmellyItemData();
        passMethodSmelliestItemData();
        summaryHeader.setText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SUMMARY.HEADER.METHOD"));
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

    @Override
    public void WriteDataToFile() {

    }

    @Override
    public void ReadDataFromFile() {

    }

    /**
     * Gets the methods for a matching smell
     *
     * @param smell The smell that is being searched for
     * @return a list of methods with a specific smell
     */
    protected List<InspectionMethodModel> getMethodBySmell(SmellType smell) {
        List<InspectionMethodModel> smellyMethods = new ArrayList<>();
        for (InspectionMethodModel m : allMethods) {
            if (m.getSmellTypeList().contains(smell))
                smellyMethods.add(m);
        }
        return smellyMethods;
    }


}
