package org.scanl.plugins.tsdetect.common;


import com.intellij.execution.junit.JUnitUtil;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

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

    public static HashSet<VirtualFile> GetTestFiles(String path){
        HashSet<VirtualFile> testClassesMap = new HashSet<>();
        for(Project project: ProjectManager.getInstance().getOpenProjects()){
            @NotNull Collection<VirtualFile> s = FileBasedIndex.getInstance()
                    .getContainingFiles(FileTypeIndex.NAME, JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));
            for(VirtualFile file: s){
                if(file.getPath().contains(path)){
                    testClassesMap.add(file);
                }
            }
        }
        return testClassesMap;
    }

    public enum TreeNodeIcon{
        FILE,
        CLASS,
        METHOD,
        SMELL
    }

}
