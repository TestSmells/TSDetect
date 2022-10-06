package org.scanl.plugins.tsdetect.ui.tabs;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestSmellTypeSummary;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestFileSummary;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestMethodSummary;

import javax.swing.*;
import java.util.List;

public class TabAnalysisSummary implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelSummary;
    TestFileSummary testFileSummary;
    TestMethodSummary testMethodSummary;
    TestSmellTypeSummary testSmellTypeSummary;
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
        testFileSummary.LoadData();
        testMethodSummary.LoadData();
        testSmellTypeSummary.LoadData();

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.add(testSmellTypeSummary.GetContent());
        content.add(testFileSummary.GetContent());
        content.add(testMethodSummary.GetContent());
        panelSummary.getViewport().add(content);
        panelSummary.validate();
    }
}
