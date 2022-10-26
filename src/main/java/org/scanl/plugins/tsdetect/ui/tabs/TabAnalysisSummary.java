package org.scanl.plugins.tsdetect.ui.tabs;

import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestSmellTypeSummary;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestFileSummary;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestMethodSummary;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TabAnalysisSummary implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelSummary;
    TestFileSummary testFileSummary;
    TestMethodSummary testMethodSummary;
    TestSmellTypeSummary testSmellTypeSummary;
    private List<InspectionMethodModel> allMethods;
    private int totalMethods = 0;
    private int smellyMethods = 0;
    private String smelliestMethod;
    private int smelliestMethodNumber;
    private AnalysisSummaryItem methodTotalTestItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem methodTotalSmellyItem = new AnalysisSummaryItem();
    private AnalysisSummaryItem methodSmelliestItem  = new AnalysisSummaryItem();

    JPanel content;


    public TabAnalysisSummary(){
        content = new JPanel();
        testFileSummary = new TestFileSummary();
        testMethodSummary = new TestMethodSummary();
        testSmellTypeSummary = new TestSmellTypeSummary();

    }

    @Override
    public JPanel GetContent() {
        return panelMain;
    }

    @Override
    public void LoadSmellyData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods) {
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

        testFileSummary.LoadData();
        testMethodSummary.LoadSmellTypeData(this.methodTotalTestItem, this.methodTotalSmellyItem, this.methodSmelliestItem,this.totalMethods, this.smellyMethods,this.smelliestMethod,this.smelliestMethodNumber);
        testSmellTypeSummary.LoadData();

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(testSmellTypeSummary.GetContent());
        content.add(testFileSummary.GetContent());
        content.add(testMethodSummary.GetContent());
        panelSummary.getViewport().add(content);
        panelSummary.validate();
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