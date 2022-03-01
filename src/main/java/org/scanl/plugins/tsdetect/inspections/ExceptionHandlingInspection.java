package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

public class ExceptionHandlingInspection extends SmellInspection{

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitElement(PsiElement expression) {
				if (hasSmell(expression))
					holder.registerProblem(expression, DESCRIPTION);
				super.visitElement(expression);
			}
		};
	}
	@Override
	public boolean hasSmell(PsiElement element) {
		if (!shouldTestElement(element)) return false;

		if(element instanceof PsiThrowStatement || element instanceof PsiCatchSection) {
			PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
			if(psiClass!=null)
				return JUnitUtil.isTestClass(psiClass);
		}
		return false;
	}

	@Override
	public Class<? extends PsiElement> getVisitedType()
	{
		return PsiElement.class;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.EXCEPTION_HANDLING;
	}
}
