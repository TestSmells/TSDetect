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


    /**
     *
     * @param element
     * @return false if test contains assert
     */
    @Override
    public boolean hasSmell(PsiElement element) {
        if(element instanceof PsiMethod
                && JUnitUtil.isTestClass(Objects.requireNonNull(PsiTreeUtil.getParentOfType(element, PsiClass.class)))) {
                PsiMethod method = (PsiMethod) element;
            if (!PluginSettings.GetSetting(getSmellType().toString())) {
                System.out.println("first false return");
                return false;
            }
            List<PsiMethodCallExpression> methods = PsiTreeUtil.getChildrenOfTypeAsList(element, PsiMethodCallExpression.class);
            //element.getMethodExpressions
            
            for (PsiMethodCallExpression statement : methods) {
                System.out.println(statement);
                //String name = statement.getText().replaceAll("\\s", "");
                String name = statement.getMethodExpression().getQualifiedName();
                System.out.println(name);
                if(name.contains("assert") || name.contains("Assert")){
                    System.out.println("Assert function found");
                    return false;
                }
            }
            System.out.println("True return");
            return true;


        }
        System.out.println("last false return");
        return false;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.UNKNOWN_TEST;
    }
}
