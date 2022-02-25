package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RedundantPrintInspection extends SmellInspection{

	private List<PsiMethodCallExpression> issueStatements = new ArrayList<>();

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method){
				if(hasSmell(method)){
					for(PsiMethodCallExpression expression:issueStatements)
						holder.registerProblem(expression, DESCRIPTION,
								new QuickFixRemove("INSPECTION.SMELL.REDUNDANT_PRINT.FIX.REMOVE"),
								new QuickFixComment("INSPECTION.SMELL.REDUNDANT_PRINT.FIX.COMMENT"));
				}
				super.visitMethod(method);
			}
		};
	}


	@Override
	public boolean hasSmell(PsiElement element) {
		if (!PluginSettings.GetSetting(getSmellType().toString())) return false;
		issueStatements = new ArrayList<>();
		if(element instanceof PsiMethod) {
			PsiMethod method = (PsiMethod) element;
			List<PsiMethodCallExpression> methodCallExpressionList = getMethodExpressions(method);
			for (PsiMethodCallExpression methodCallExpression : methodCallExpressionList) {
				if (methodCallExpression.getMethodExpression().getQualifierExpression() != null) {
					PsiType s = methodCallExpression.getMethodExpression().getQualifierExpression().getType();
					if (s != null) {
						if (s.getCanonicalText().equals("java.io.PrintStream"))
							issueStatements.add(methodCallExpression);
					}
					if (Objects.requireNonNull(methodCallExpression.getMethodExpression().getReferenceName()).contains("print")) {
						issueStatements.add(methodCallExpression);
					}
				}
			}
		}
		return issueStatements.size()>0;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.REDUNDANT_PRINT;
	}
}
