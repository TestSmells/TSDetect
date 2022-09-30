package org.scanl.plugins.tsdetect.service;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.model.ExecutionResult;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Analyzer {

    private static Analyzer single_instance = null;

    private Analyzer(){}

    public static Analyzer getInstance()
    {
        if (single_instance == null)
            single_instance = new Analyzer();

        return single_instance;
    }


    public ExecutionResult DetectTestSmells(Project project) {
        LocalDateTime start = LocalDateTime.now();
        Collection<VirtualFile> vFiles = FileTypeIndex.getFiles(JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));

        List<InspectionMethodModel> allMethods = new ArrayList<>();
        List<InspectionClassModel> allClasses = new ArrayList<>();

        for (VirtualFile vf : vFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(vf); //converts into a PsiFile
            if (psiFile instanceof PsiJavaFile) //determines if the PsiFile is a PsiJavaFile
            {
                SmellVisitor sv = new SmellVisitor(); //creates the smell visitor
                psiFile.accept(sv); //visits the methods
                allMethods.addAll(sv.getSmellyMethods());
                allClasses.addAll(sv.getSmellyClasses());

            }
        }

        LocalDateTime end = LocalDateTime.now();
        return new ExecutionResult(allClasses, allMethods, start, end);
    }
}
