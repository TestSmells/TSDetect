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
            public void visitClass(PsiClass cls) {
                if(hasSmell(cls)) {
                    for(PsiStatement statement : issueStatements)
                        holder.registerProblem(statement, DESCRIPTION);
                }
            }
        };
    }

    @Override
    public boolean hasSmell(PsiElement element) {
        if (!shouldTestElement(element)) return false;

        this.issueStatements = new ArrayList<>();

        List<PsiMethod> allProductionMethods = getAllMethodCalls();

        if (element instanceof PsiClass) {
            PsiClass testClass = (PsiClass) element;

            for (PsiMethod testMethod : testClass.getMethods()) {
                List<PsiStatement> possibleIssues = new ArrayList<>();

                for (PsiStatement statement : Objects.requireNonNull(testMethod.getBody()).getStatements()) {
                    for (PsiMethod productionMethod : allProductionMethods) {
                        if (determineMatchingStatement(statement, productionMethod)) {
                            possibleIssues.add(statement);
                        }
                    }
                }

                if (possibleIssues.size() > 0)
                    this.issueStatements.addAll(possibleIssues);
            }
        }

        return this.issueStatements.size() > 0;
    }

    @Override
    public SmellType getSmellType() {
        return SmellType.EAGER_TEST;
    }
}
