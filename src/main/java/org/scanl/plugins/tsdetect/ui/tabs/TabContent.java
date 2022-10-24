package org.scanl.plugins.tsdetect.ui.tabs;

import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;

import javax.swing.*;
import java.util.List;


public interface TabContent {

    public JPanel GetContent();

    public void LoadSmellyData(
            List<InspectionClassModel> allClasses,
            List<InspectionMethodModel> allMethods);

}
