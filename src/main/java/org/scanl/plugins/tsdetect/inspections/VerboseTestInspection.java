package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.PsiLocation;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VerboseTestInspection extends SmellInspection{
    final int MAX_STATEMENT_THRESHOLD = 13; //This is the max number of statements a test method can have before it
    //marks it as a smelly test.

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly){
        return new JavaElementVisitor(){
            @Override
            public void visitMethod(PsiMethod method) {
                if (!JUnitUtil.isTestMethod(new PsiLocation<>(method)))
                    return;
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
        if(!(element instanceof PsiMethod)) return false;
        if (!shouldTestElement(element)) return false;
        PsiMethod method = (PsiMethod) element;
        return Objects.requireNonNull(method.getBody()).getStatementCount() > MAX_STATEMENT_THRESHOLD;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.VERBOSE_TEST;
    }

    @Override
    public Class<? extends PsiElement> getVisitedType()
    {
        return PsiElement.class;
    }
}
