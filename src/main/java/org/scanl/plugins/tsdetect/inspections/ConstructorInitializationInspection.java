package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

public class ConstructorInitializationInspection extends SmellInspection {

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if(hasSmell(method)) {
						holder.registerProblem(method, getDescription(),
								new QuickFixRemove(getResourceName("FIX.REMOVE")),
								new QuickFixComment(getResourceName("FIX.COMMENT")));
				}
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if (!shouldTestElement(element)) return false;
		if(!(element instanceof PsiMethod)) return false;
		PsiMethod method = (PsiMethod) element;
		return method.isConstructor();
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.CONSTRUCTOR_INITIALIZATION;
	}
}
