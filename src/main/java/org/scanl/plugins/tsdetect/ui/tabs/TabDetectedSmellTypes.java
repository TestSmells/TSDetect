package org.scanl.plugins.tsdetect.ui.tabs;

import com.intellij.util.PsiNavigateUtil;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.common.Util;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.ui.controls.CustomTreeCellRenderer;
import org.scanl.plugins.tsdetect.ui.controls.CustomTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class TabDetectedSmellTypes implements TabContent {
    private final JTree treeSmells;
    private JPanel panelMain;
    private JScrollPane panelTable;
    private List<InspectionMethodModel> allMethods;
    private List<InspectionClassModel> allClasses;

    public TabDetectedSmellTypes() {
        treeSmells = new JTree();
        treeSmells.setVisible(true);
        panelTable.getViewport().add(treeSmells);

        treeSmells.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                TreePath treePath = treeSmells.getPathForLocation(me.getX(), me.getY());
                if (treePath != null)
                    if (treePath.getLastPathComponent() instanceof CustomTreeNode) {
                        if (((CustomTreeNode) treePath.getLastPathComponent()).getPsiElement() != null) {
                            PsiNavigateUtil.navigate(((CustomTreeNode) treePath.getLastPathComponent()).getPsiElement(), true);
                        }
                    }
            }
        });

        treeSmells.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                TreePath treePath = treeSmells.getPathForLocation(me.getX(), me.getY());
                if (treePath != null && treePath.getLastPathComponent() instanceof CustomTreeNode && ((CustomTreeNode) treePath.getLastPathComponent()).getPsiElement() != null) {
                    treeSmells.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    treeSmells.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
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
            String smellName = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "INSPECTION.SMELL." + smellType.toString() + ".NAME.DISPLAY");
            CustomTreeNode smellTypeNode = new CustomTreeNode(Util.GetTreeNodeIcon(Util.TreeNodeIcon.SMELL), smellName);
            for (InspectionClassModel smellyClass : getClassesBySmell(smellType)) {
                CustomTreeNode classNode = new CustomTreeNode(Util.GetTreeNodeIcon(Util.TreeNodeIcon.CLASS), smellyClass.getPsiObject(), smellyClass.getName());
                for (InspectionMethodModel method : getMethodBySmell(smellType)) {
                    if (method.getClassName().getName().equals(smellyClass.getName())) {
                        CustomTreeNode methodNode = new CustomTreeNode(Util.GetTreeNodeIcon(Util.TreeNodeIcon.METHOD), method.getPsiObject(), method.getName());
                        classNode.add(methodNode);
                    }
                }
                smellTypeNode.add(classNode);
            }
            root.add(smellTypeNode);
        }

        treeSmells.setCellRenderer(new CustomTreeCellRenderer());
        treeSmells.setRootVisible(false);
        model.reload(root);
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
     * @param smell The smell that is being searched for
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
