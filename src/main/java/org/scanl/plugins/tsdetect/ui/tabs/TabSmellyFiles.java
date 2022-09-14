package org.scanl.plugins.tsdetect.ui.tabs;

import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.Identifier;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TabSmellyFiles implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelTable;
    private JTree treeSmells;
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

        DefaultTreeModel model = (DefaultTreeModel) treeSmells.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        root.removeAllChildren();

        for (String path : getFilePaths()) {
            DefaultMutableTreeNode pathNode = new DefaultMutableTreeNode(path);

            for (SmellType smellType : SmellType.values()) {
                DefaultMutableTreeNode smellTypeNode = new DefaultMutableTreeNode(
                        PluginResourceBundle.message(
                                PluginResourceBundle.Type.INSPECTION, "INSPECTION.SMELL." + smellType.toString() + ".NAME.DISPLAY"
                        )
                );

                for (InspectionClassModel classModel : getClassesBySmell(smellType).stream().filter(clss -> getRelativeFilePath(clss).equals(path)).collect(Collectors.toList())) {
                    DefaultMutableTreeNode classNode = new DefaultMutableTreeNode(classModel.getName());

                    for (InspectionMethodModel method : getMethodBySmell(smellType).stream().filter(method -> getRelativeFilePath(method).equals(path)).collect(Collectors.toList())) {
                        DefaultMutableTreeNode methodNode = new DefaultMutableTreeNode(method.getName());
                        classNode.add(methodNode);
                    }

                    smellTypeNode.add(classNode);
                }

                if (smellTypeNode.getChildCount() > 0) {
                    pathNode.add(smellTypeNode);
                }
            }

            root.add(pathNode);
        }

        model.reload(root);
        treeSmells.setRootVisible(false);
    }


    /**
     * Gets all (relative) file paths of a smelly class
     *
     * @return a set of path strings of smelly classes
     */
    protected Set<String> getFilePaths() {
        Set<String> paths = new HashSet<>();
        for (InspectionClassModel smellyClass: allClasses) {
            paths.add(getRelativeFilePath(smellyClass));
        }
        return paths;
    }

    /**
     * Helper method to get a relative file path
     *
     * @param identifier Some sort of psi...thing
     * @return relative file path of identifier
     */
    protected String getRelativeFilePath(Identifier identifier) {
        String dir = identifier.getPsiObject().getContainingFile().getContainingDirectory().getName();
        String fullPath = identifier.getPsiObject().getContainingFile().getOriginalFile().getVirtualFile().getPath();
        return fullPath.substring(fullPath.indexOf(dir));
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
