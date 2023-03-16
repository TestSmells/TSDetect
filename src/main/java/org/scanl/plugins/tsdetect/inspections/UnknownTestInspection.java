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

public class UnknownTestInspection extends SmellInspection{

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


    /**
     *
     * @param element PsiElement
     * @return false if test contains assert
     */
    @Override
    public boolean hasSmell(PsiElement element) {
        if(!(element instanceof PsiMethod)) return false;
        if (!shouldTestElement(element)) return false;

        PsiMethod method = (PsiMethod) element;
        List<PsiMethodCallExpression> methods = getMethodExpressions(method);
        for (PsiMethodCallExpression statement : methods) {
            String name = statement.getMethodExpression().getQualifiedName().toLowerCase();
            if(name.contains("assert")){
                return false;
            }
        }
        return true;



    }

    @Override
    public SmellType getSmellType() {
        return SmellType.UNKNOWN_TEST;
    }

    @Override
    public Class<? extends PsiElement> getVisitedType()
    {
        return PsiElement.class;
    }
}
