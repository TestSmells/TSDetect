package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

public class SleepyTestInspection extends SmellInspection {

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (hasSmell(expression))
                    holder.registerProblem(expression, DESCRIPTION,
                            new QuickFixRemove("INSPECTION.SMELL.SLEEPY_TEST.FIX.REMOVE"),
                            new QuickFixComment("INSPECTION.SMELL.SLEEPY_TEST.FIX.COMMENT"));
                super.visitMethodCallExpression(expression);
            }
        };
    }

    @Override
    public boolean hasSmell(PsiElement element) {
        if (!PluginSettings.GetSetting(getSmellType().toString())) return false;
        if (!(element instanceof PsiMethodCallExpression)) return false;

        PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        if (!JUnitUtil.isTestClass(psiClass)) return false;

        PsiMethodCallExpression expression = (PsiMethodCallExpression) element;
        return expression.getMethodExpression().getCanonicalText().equals("Thread.sleep");
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.SLEEPY_TEST;
    }

    @Override
    public Class<? extends PsiElement> getVisitedType() { return PsiMethodCallExpression.class; }
}
