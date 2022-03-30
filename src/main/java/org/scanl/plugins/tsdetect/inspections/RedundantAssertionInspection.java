package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.Arrays;
import java.util.HashSet;

public class RedundantAssertionInspection extends SmellInspection {

    HashSet<String> assertsWithTwoArguments = new HashSet<>(
            Arrays.asList("assertarrayequals",
                    "assertequals",
                    "assertnotsame",
                    "assertsame",
                    "assertnotequals"
            ));

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (hasSmell(expression))
                    holder.registerProblem(expression, getDescription(),
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
        var methodName = expression.getMethodExpression().getQualifiedName();
        var argList = expression.getArgumentList();
        var count = argList.getExpressionCount();

        if (count < 1) return false;
        else if (count < 2) return testAssertWithOneArgument(methodName, argList);
        else return testAssertWithMultipleArguments(methodName, argList);
    }

    private boolean testAssertWithOneArgument(String name, PsiExpressionList args) {
        var arg = args.getExpressions()[0];
        var text = getExpressionText(arg);

        switch (name.toLowerCase()) {
            case "asserttrue":
                return text.equals("true") || text.equals("!false");
            case "assertfalse":
                return text.equals("false") || text.equals("!true");
            case "assertnull":
                return text.equals("null");
            default:
                return false;
        }
    }

    private boolean testAssertWithMultipleArguments(String name, PsiExpressionList args) {
        if (!assertsWithTwoArguments.contains(name.toLowerCase())) return false;
        var expressions = args.getExpressions();

        var text1 = getExpressionText(expressions[0]);
        var text2 = getExpressionText(expressions[1]);

        return text1.equals(text2);
    }

    /**
     * Gets a PsiVariable from a PsiExpression, if the expression contains only the variable.
     * @param expression The PsiExpression.
     * @return a PsiVariable, or null if the expression is not a variable.
     */
    private PsiVariable tryGetVariableFromExpression(PsiExpression expression) {
        if (!(expression instanceof PsiReferenceExpression)) return null;
        var ref = (PsiReferenceExpression) expression;

        PsiElement element = ref.resolve();
        if (!(element instanceof PsiVariable)) return null;

        var psiVar = (PsiVariable) element;

        return psiVar.hasInitializer() ? psiVar : null;
    }

    private String getExpressionText(PsiExpression expression) {
        var psiVar = tryGetVariableFromExpression(expression);
        String text;

        if (psiVar != null) {
            var initExpression = psiVar.getInitializer();
            text = initExpression == null ? "null" : initExpression.getText();
        } else {
            text = expression.getText();
        }

        return text;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.REDUNDANT_ASSERTION;
    }

    @Override
    public Class<? extends PsiElement> getVisitedType() {
        return PsiMethodCallExpression.class;
    }
}
