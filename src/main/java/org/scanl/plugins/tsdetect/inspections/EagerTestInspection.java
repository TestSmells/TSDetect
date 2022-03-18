package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EagerTestInspection extends SmellInspection {
    private List<PsiStatement> issueStatements = new ArrayList<>();

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                if(hasSmell(method)) {
                    for(PsiStatement statement : issueStatements)
                        holder.registerProblem(statement, getDescription());
                }
            }
        };
    }

    @Override
    public boolean hasSmell(PsiElement element) {
        if (!shouldTestElement(element)) return false;
        if (!(element instanceof PsiMethod)) return false;
        System.out.println(element);

        this.issueStatements.clear();

        List<PsiMethod> allProductionMethods = getAllMethodCalls();

        PsiMethod testMethod = (PsiMethod) element;

        List<PsiStatement> possibleIssues = new ArrayList<>();
        for (PsiStatement statement : Objects.requireNonNull(testMethod.getBody()).getStatements()) {
            for (PsiMethod productionMethod : allProductionMethods) {
                if (determineMatchingStatement(statement, productionMethod)) {
                    possibleIssues.add(statement);
                }
            }
        }

        if (possibleIssues.size() > 1)
            this.issueStatements.addAll(possibleIssues);

        System.out.println((issueStatements.size()>0) + " " + testMethod.getName());
        return this.issueStatements.size() > 0;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.EAGER_TEST;
    }
}
