package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IgnoredTestInspection extends SmellInspection{

    private List<PsiStatement> issueStatements = new ArrayList<>();

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new JavaElementVisitor(){
            @Override
            public void visitClass(PsiClass cls){
                if(hasSmell(cls)){
                    for(PsiStatement statement:issueStatements){
                        holder.registerProblem(statement, DESCRIPTION,
                                new QuickFixRemove("INSPECTION.SMELL.IGNORED_TEST.FIX.REMOVE"),
                                new QuickFixComment("INSPECTION.SMELL.IGNORED_TEST.FIX.COMMENT"));
                    }
                }
            }
        };
    }


    @Override
    public boolean hasSmell(PsiElement element) {
        PsiClass currClass = (PsiClass) element;
        if (!PluginSettings.GetSetting(getSmellType().toString())) return false;
        issueStatements = new ArrayList<>();
        PsiModifierList psiModifierList = currClass.getModifierList();
        if(psiModifierList == null){
            return false;
        }
        List<PsiAnnotation> annotations = Arrays.asList(psiModifierList.getAnnotations());
        for(PsiAnnotation annotation:annotations){
            if(annotation.getQualifiedName().equals("Ignore")){
                return true;
            }
        }
        return false;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.IGNORED_TEST;
    }
}
