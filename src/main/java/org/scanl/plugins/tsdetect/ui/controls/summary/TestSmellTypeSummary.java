package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashSet;

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
    private List<InspectionMethodModel> allMethods;
    private int totalSmells;
    private int totalSmellTypes;
    private int totalCommonSmell;
    private String mostCommonSmell;
    private int before;

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
        this.before = Integer.parseInt(smellTotalItem.getPrimaryValue());
        this.smellTotalItem.setPrimaryHeader("Total smelly instances: ");
        this.smellTotalItem.setPrimaryValue(String.valueOf(this.totalSmells));
        Change.setPrimaryChange(this.smellTotalItem, this.totalSmells, this.before);
    }

    public void passSmellDetectedData(){
        this.before = Integer.parseInt(smellDetectedItem.getPrimaryValue());
        this.smellDetectedItem.setPrimaryHeader("Detected smell types: ");
        this.smellDetectedItem.setPrimaryValue(String.valueOf(this.totalSmellTypes));
        Change.setPrimaryChange(this.smellDetectedItem, this.totalSmellTypes, this.before);
    }

    public void passSmellCommonData(){
        this.smellCommonItem.setPrimaryHeader("Most common smell type: ");
        this.smellCommonItem.setPrimaryValue(this.mostCommonSmell);
        this.before = Integer.parseInt(smellCommonItem.getSecondaryValue());
        this.smellCommonItem.setSecondaryHeader("Total instances: ");
        this.smellCommonItem.setSecondaryValue(String.valueOf(this.totalCommonSmell));
        Change.setSecondaryChange(this.smellCommonItem, this.totalCommonSmell, this.before);

    }

    @Override
    public void LoadData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods) {
        this.allMethods = allMethods;

        HashMap<String, Integer> smellMap = new HashMap<>();
        List<InspectionMethodModel> smellTypeMethods;
        HashSet<SmellType> uniqueSmells = new HashSet<>();
        for(SmellType smellType: SmellType.values())
        {
            if (PluginSettings.GetSetting(smellType.toString())) {
                smellTypeMethods =  getMethodBySmell(smellType);
                for(InspectionMethodModel method: smellTypeMethods){
                    for(SmellType smell : method.getSmellTypeList()) {
                        uniqueSmells.add(smell);
                        String currSmell = String.valueOf(smell);
                        smellMap.put(currSmell, smellMap.containsKey(currSmell) ? smellMap.get(currSmell) + 1 : 1);
                    }
                }
            }
        }

        for (int count : smellMap.values()) {
            this.totalSmells += count;
        }

        this.totalSmellTypes = uniqueSmells.size();

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : smellMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        this.mostCommonSmell = maxEntry.getKey();
        this.totalCommonSmell = maxEntry.getValue();

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
