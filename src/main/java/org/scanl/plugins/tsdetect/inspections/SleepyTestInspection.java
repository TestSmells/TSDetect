package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
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
                            new QuickFixRemove(getResourceName("FIX.REMOVE")),
                            new QuickFixComment(getResourceName("FIX.COMMENT")));
                super.visitMethodCallExpression(expression);
            }
        };
    }

    @Override
    public boolean hasSmell(PsiElement element) {
        if (!shouldTestElement(element)) return false;
        if (!(element instanceof PsiMethodCallExpression)) return false;

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
