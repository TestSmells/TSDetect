package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RedundantPrintInspection extends SmellInspection{

	private static final String DESCRIPTION =
			PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.redundantPrint.description");

	private List<PsiMethodCallExpression> issueStatements = new ArrayList<>();
	/**
	 * @see InspectionEP#displayName
	 * @see InspectionEP#key
	 * @see InspectionEP#bundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getDisplayName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.redundantPrint.name.display");
	}

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.redundantPrint.name.short");
	}

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method){
				if(hasSmell(method)){
					for(PsiMethodCallExpression expression:issueStatements)
						holder.registerProblem(expression, DESCRIPTION,
								new QuickFixRemove("inspection.smell.redundantPrint.fix.remove"),
								new QuickFixComment("inspection.smell.redundantPrint.fix.comment"));
				}
				super.visitMethod(method);
			}
		};
	}

	private static List<PsiMethodCallExpression> getMethodExpressions(PsiMethod method){
		List<PsiMethodCallExpression> methodCallExpressionList = new ArrayList<>();
		PsiStatement @NotNull [] statements = Objects.requireNonNull(method.getBody()).getStatements();
		for(PsiStatement statement: statements) {
			if(statement instanceof PsiExpressionStatement)
			{
				PsiExpressionStatement expressionStatement = (PsiExpressionStatement) statement;
				PsiExpression expression = expressionStatement.getExpression();
				if(expression instanceof PsiMethodCallExpression) {
					PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) expression;
					methodCallExpressionList.add(methodCallExpression);
				}
			}
		}
		return methodCallExpressionList;
	}

	@Override
	public boolean hasSmell(PsiElement element) {
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
