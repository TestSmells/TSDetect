package org.scanl.plugins.tsdetect.ui.tabs;

import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TabDetectedSmells implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelTable;
    private JTree treeSmells;
    private List<InspectionMethodModel> allMethods;
    private List<InspectionClassModel> allClasses;

    public TabDetectedSmells(){
        treeSmells.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                doMouseClicked(me);
            }
        });
    }

    void doMouseClicked(MouseEvent me) {
        TreePath tp = treeSmells.getPathForLocation(me.getX(), me.getY());
        if (tp != null)
            System.out.println(tp.toString());
        else
            System.out.println("--");
    }

    @Override
    public JPanel GetContent() {
        return panelMain;
    }

    @Override
    public void LoadSmellyData(List<InspectionClassModel> allClasses, List<InspectionMethodModel> allMethods) {
        this.allClasses = allClasses;
        this.allMethods = allMethods;

        DefaultTreeModel model = (DefaultTreeModel) treeSmells.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();

        for (SmellType smellType : SmellType.values()) {
            DefaultMutableTreeNode smellTypeNode = new DefaultMutableTreeNode(
                    PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,
                            "INSPECTION.SMELL." + smellType.toString() + ".NAME.DISPLAY"));
            for (InspectionClassModel smellyClass : getClassesBySmell(smellType)) {
                DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(smellyClass.getName());
                for (InspectionMethodModel method : getMethodBySmell(smellType)) {
                    if (method.getClassName().getName().equals(smellyClass.getName())) {
                        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(method.getName());
                        classNode.add(methodNode);
                    }
                }
                smellTypeNode.add(classNode);
            }
            root.add(smellTypeNode);
        }

        model.reload(root);
        treeSmells.setRootVisible(false);
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
