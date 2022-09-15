package org.scanl.plugins.tsdetect.ui.controls;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class CustomTreeNode extends DefaultMutableTreeNode {

    /**
     * The icon which is displayed on the JTree object. open, close, leaf icon.
     */
    private ImageIcon icon;

    public CustomTreeNode(ImageIcon icon) {
        this.icon = icon;
    }

    public CustomTreeNode(ImageIcon icon, Object userObject) {
        super(userObject);
        this.icon = icon;
    }

    public CustomTreeNode(ImageIcon icon, Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
