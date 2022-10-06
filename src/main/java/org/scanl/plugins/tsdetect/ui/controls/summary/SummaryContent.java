package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;

import javax.swing.*;


public interface SummaryContent {

    public JPanel GetContent();

    public void LoadData(AnalysisSummaryItem analysisSummaryItem);

}
