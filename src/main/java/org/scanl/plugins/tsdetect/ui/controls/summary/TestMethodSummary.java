package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
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

    public void passMethodTotalTestData(int totalMethod){
        this.before = Integer.parseInt(methodTotalTestItem.getPrimaryValue());
        this.methodTotalTestItem.setPrimaryHeader("Total test methods: ");
        this.methodTotalTestItem.setPrimaryValue(String.valueOf(totalMethod));
        Change.setPrimaryChange(this.methodTotalTestItem, totalMethod, this.before);

    }

    public void passMethodTotalSmellyItemData(int smellyMethods){
        this.before = Integer.parseInt(methodTotalSmellyItem.getPrimaryValue());
        this.methodTotalSmellyItem.setPrimaryHeader("Smelly methods: ");
        this.methodTotalSmellyItem.setPrimaryValue(String.valueOf(smellyMethods));
        Change.setPrimaryChange(this.methodTotalSmellyItem, smellyMethods, this.before);
    }

    public void passMethodSmelliestItemData(String smelliestMethod, int smelliestMethodNumber){
        this.methodSmelliestItem.setPrimaryHeader("Smelliest method: ");
        this.methodSmelliestItem.setPrimaryValue(smelliestMethod);
        this.before = Integer.parseInt(methodSmelliestItem.getSecondaryValue());
        this.methodSmelliestItem.setSecondaryHeader("Total smells: ");
        this.methodSmelliestItem.setSecondaryValue(String.valueOf(smelliestMethodNumber));
        Change.setSecondaryChange(this.methodSmelliestItem, smelliestMethodNumber,this.before);

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
        passMethodTotalTestData(totalMethods);
        passMethodTotalSmellyItemData(smellyMethods);
        passMethodSmelliestItemData(smelliestMethod, smelliestMethodNumber);
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
