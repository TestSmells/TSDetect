package org.scanl.plugins.tsdetect.ui.controls;

import com.intellij.psi.PsiElement;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

public class CustomTreeNode extends DefaultMutableTreeNode {


    private final ImageIcon icon;
    private PsiElement psiElement;

    public CustomTreeNode(ImageIcon icon, PsiElement psiElement, String displayText) {
        super(displayText);
        this.icon = icon;
        this.psiElement = psiElement;
    }

    public CustomTreeNode(ImageIcon icon, String displayText) {
        super(displayText);
        this.icon = icon;
    }

    public CustomTreeNode(ImageIcon icon, Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
        this.icon = icon;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public PsiElement getPsiElement() {
        return psiElement;
    }
}
