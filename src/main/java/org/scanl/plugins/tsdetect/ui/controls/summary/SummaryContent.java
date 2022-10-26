package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;


public interface SummaryContent {

    public JPanel GetContent();

    void LoadData();

    void LoadSmellTypeData(AnalysisSummaryItem smellTotalItem,AnalysisSummaryItem smellDetectedItem, AnalysisSummaryItem smellCommonItem ,int totalMethods, int smellyMethods, String smelliestMethod, int smelliestMethodNumber);

}
