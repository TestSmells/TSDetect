package org.scanl.plugins.tsdetect.ui.controls;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    public CustomTreeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) value;
        if (defaultMutableTreeNode.isRoot()) {
            setClosedIcon(getDefaultClosedIcon());
            setLeafIcon(getDefaultLeafIcon());
            setOpenIcon(getDefaultOpenIcon());
        } else {
            CustomTreeNode node = (CustomTreeNode) value;
            if (node.getIcon() != null) {
                if (node.getPsiElement()!=null)
                    System.out.println(node + " - " + node.getPsiElement().getClass().getName());
                setIcon(node.getIcon());
            } else {
                System.out.println(node + " - default");
                setClosedIcon(getDefaultClosedIcon());
                setLeafIcon(getDefaultLeafIcon());
                setOpenIcon(getDefaultOpenIcon());
            }
        }


        return this;
    }
}