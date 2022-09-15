package org.scanl.plugins.tsdetect.ui.tabs;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabSmellDistribution implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelTable;
    private JTable tableSmell;
    private JPanel panelChart;
    private List<InspectionMethodModel> allMethods;
    private List<InspectionClassModel> allClasses;

    @Override
    public JPanel GetContent() {
        return panelMain;
    }

    @Override
    public void LoadSmellyData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods) {
        this.allClasses = allClasses;
        this.allMethods = allMethods;

        IdentifierTableModel data = new IdentifierTableModel();

        HashMap<SmellType, List<InspectionMethodModel>> smellyMethods = new HashMap<>(); //hash to store smelly methods by smell
        HashMap<SmellType, List<InspectionClassModel>> smellyClasses = new HashMap<>(); //hash to store smelly classes by smell

        for(SmellType smellType: SmellType.values())
        {
            if (PluginSettings.GetSetting(smellType.toString())) {
                smellyMethods.put(smellType, getMethodBySmell(smellType));
                smellyClasses.put(smellType, getClassesBySmell(smellType));
            }
        }

        data.constructSmellTable(smellyMethods, smellyClasses); //constructs the smell table

        tableSmell.setModel(data); //sets the model to be the table and visible
        tableSmell.setVisible(true);

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for(Map.Entry<SmellType, List<InspectionClassModel>> entry : smellyClasses.entrySet()) {
            int size = entry.getValue().size();
            if (size != 0) {
                dataset.setValue(PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "INSPECTION.SMELL." + entry.getKey().toString() + ".NAME.DISPLAY"), size);
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "",
                dataset,
                true,
                true,
                false
        );

        panelChart.removeAll();
        panelChart.setLayout(new java.awt.BorderLayout());
        panelChart.add(new ChartPanel(chart));
        panelChart.validate();
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


    /**
     * Gets class that contains a matching smell
     *
     * @param smell The smell thats being searched for
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
}
