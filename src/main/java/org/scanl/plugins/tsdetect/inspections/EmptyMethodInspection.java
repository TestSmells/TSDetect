package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.Objects;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class EmptyMethodInspection extends SmellInspection{

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
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
	 * Determines if the PSI Method is empty or not
	 * @param element the method being looked for to see if it has smells
	 * @return if the PSI Method is empty or not
	 */
	@Override
	public boolean hasSmell(PsiElement element) {
		if (!shouldTestElement(element)) return false;

		if(element instanceof PsiMethod) {
			PsiMethod method = (PsiMethod) element;
			return Objects.requireNonNull(method.getBody()).isEmpty();
		}
		return false;
	}

	/**
	 * Gets the matching smell type enum
	 * @return The Smell Type Enum
	 */
	@Override
	public SmellType getSmellType() {
		return SmellType.EMPTY_TEST;
	}
}
