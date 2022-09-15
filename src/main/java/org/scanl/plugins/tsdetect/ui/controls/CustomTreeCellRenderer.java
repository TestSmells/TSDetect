package org.scanl.plugins.tsdetect.ui.controls;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    public CustomTreeCellRenderer() {
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

//            if (!leaf) {
        CustomTreeNode node = (CustomTreeNode) value;

        if (node.getIcon() != null) {
            System.out.println(node + " - " + node.getIcon());
            setClosedIcon(node.getIcon());
            setOpenIcon(node.getIcon());
            setLeafIcon(node.getIcon());
        } else {
            System.out.println(node + " - default");
            setClosedIcon(getDefaultClosedIcon());
            setLeafIcon(getDefaultLeafIcon());
            setOpenIcon(getDefaultOpenIcon());
        }
//            }

        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        return this;
    }
}