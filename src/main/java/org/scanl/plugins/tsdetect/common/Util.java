package org.scanl.plugins.tsdetect.common;


import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Util {

    public static Icon GetLoadingIcon(){
        URL url = Util.class.getResource("/images/loading.gif");
        return new ImageIcon(url);
    }

    public static ImageIcon GetTreeNodeIcon(TreeNodeIcon icon){
        URL url;
        switch (icon){
            case FILE: url = Util.class.getResource("/images/tree_node/file.png"); break;
            case CLASS: url = Util.class.getResource("/images/tree_node/folder.png"); break;
            case METHOD: url = Util.class.getResource("/images/tree_node/code.png"); break;
            default: url = Util.class.getResource("/images/tree_node/bug.png");
        }
        ImageIcon imageIcon = new ImageIcon(url);
        Image image = imageIcon.getImage(); // transform it
        image = image.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        return new ImageIcon(image);  // transform it back

    }

    public enum TreeNodeIcon{
        FILE,
        CLASS,
        METHOD,
        SMELL
    }

}
