package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.Arrays;

public class MagicNumberInspection extends SmellInspection {

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethodCallExpression(PsiMethodCallExpression expression) {
				if (hasSmell(expression))
					holder.registerProblem(expression, getDescription());
				super.visitMethodCallExpression(expression);
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if(!shouldTestElement(element)) return false;
		if(!(element instanceof PsiMethodCallExpression)) return false;
		PsiMethodCallExpression expression = (PsiMethodCallExpression) element;
		if (expression.getText().startsWith(("assertArrayEquals")) ||
				expression.getText().startsWith(("assertEquals")) ||
				expression.getText().startsWith(("assertNotSame")) ||
				expression.getText().startsWith(("assertSame")) ||
				expression.getText().startsWith(("assertThat")) ||
				expression.getText().startsWith("assertNotNull") ||
				expression.getText().startsWith("assertNull")) {
			for (PsiExpression ex : expression.getArgumentList().getExpressions()) {
				if (isNumber(ex.getText())) return true;
				if (ex instanceof PsiMethodCallExpression){
					for (PsiExpression e : ((PsiMethodCallExpression)ex).getArgumentList().getExpressions()) {
						if (isNumber(e.getText())) return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isNumber(String str) {
		try {
			double v = Double.parseDouble(str);
			return true;
		} catch (NumberFormatException nfe) {
		}
		return false;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.MAGIC_NUMBER;
	}

	@Override
	public Class<? extends PsiElement> getVisitedType() { return PsiMethodCallExpression.class; }
}
