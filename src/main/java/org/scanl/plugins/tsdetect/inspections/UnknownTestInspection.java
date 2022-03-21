package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.List;
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
                    holder.registerProblem(method, getDescription(),
                            new QuickFixRemove(getResourceName("FIX.REMOVE")),
                            new QuickFixComment(getResourceName("FIX.COMMENT")));
            }
        };
    }


    @Override
    public boolean hasSmell(PsiElement element) {
        if(element instanceof PsiMethod
                && JUnitUtil.isTestClass(Objects.requireNonNull(PsiTreeUtil.getParentOfType(element, PsiClass.class)))) {
            //PsiMethod method = (PsiMethod) element;
            if (!PluginSettings.GetSetting(getSmellType().toString())) {
                return false;
            }
            List<PsiMethodCallExpression> methods = PsiTreeUtil.getChildrenOfTypeAsList(element, PsiMethodCallExpression.class);
            for (PsiMethodCallExpression statement : methods) {
                String name = statement.getText().replaceAll("\\s", "");
                if(name.contains("assert") || name.contains("Assert")){
                    return false;
                }
            }
            return true;


        }
        return false;
    }

    @Override
    public SmellType getSmellType() {
        return null;
    }
}