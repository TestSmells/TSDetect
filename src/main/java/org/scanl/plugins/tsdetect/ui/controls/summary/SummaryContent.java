package org.scanl.plugins.tsdetect.ui.controls.summary;

import org.scanl.plugins.tsdetect.model.AnalysisSummaryItem;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;

import javax.swing.*;
import java.util.List;


public interface SummaryContent {

    public JPanel GetContent();

    void LoadData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods);
}
