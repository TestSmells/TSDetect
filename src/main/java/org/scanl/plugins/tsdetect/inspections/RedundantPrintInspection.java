package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.Objects;

public class RedundantPrintInspection extends SmellInspection{

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
		if (expression.getMethodExpression().getQualifierExpression() != null) {
			PsiType type = expression.getMethodExpression().getQualifierExpression().getType();
			if (type != null)
				if (type.getCanonicalText().equals("java.io.PrintStream"))
					return true;
				if (Objects.requireNonNull(expression.getMethodExpression().getReferenceName()).contains("print"))
					return true;
		}

		return false;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.REDUNDANT_PRINT;
	}

	@Override
	public Class<? extends PsiElement> getVisitedType() { return PsiMethodCallExpression.class; }
}
