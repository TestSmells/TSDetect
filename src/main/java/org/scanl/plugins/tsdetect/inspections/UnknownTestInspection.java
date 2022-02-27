package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.Objects;

public class UnknownTestInspection extends SmellInspection{

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new JavaElementVisitor(){
            @Override
            public void visitMethod(PsiMethod method) {
                if (method.getBody() == null)
                    return;
                if (hasSmell(method))
                    holder.registerProblem(method, DESCRIPTION,
                            new QuickFixRemove("INSPECTION.SMELL.UNKNOWN_TEST.FIX.REMOVE"),
                            new QuickFixComment("INSPECTION.SMELL.UNKNOWN_TEST.FIX.COMMENT"));
            }
        };
    }


    @Override
    public boolean hasSmell(PsiElement element) {
        if(element instanceof PsiMethod) {
            PsiMethod method = (PsiMethod) element;
            if (!PluginSettings.GetSetting(getSmellType().toString())) {
                return false;
            }
            for (PsiStatement statement : Objects.requireNonNull(method.getBody()).getStatements()) {
                String name = statement.getText().replaceAll("\\s", "");
                if(name.contains("assert")){
                    
                }
            }


        }
        return false;
    }

    @Override
    public SmellType getSmellType() {
        return null;
    }
}
