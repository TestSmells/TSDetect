package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.Objects;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class EmptyMethodInspection extends SmellInspection{

	private static final String DESCRIPTION =
			PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.emptyTest.description");

	/**
	 * @see InspectionEP#displayName
	 * @see InspectionEP#key
	 * @see InspectionEP#bundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getDisplayName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.emptyTest.name.display");
	}

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.emptyTest.name.short");
	}

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if (method.getBody() == null)
					return;
				if (hasSmell(method))
					holder.registerProblem(method, DESCRIPTION,
							new QuickFixRemove("inspection.smell.emptyTest.fix.remove"),
							new QuickFixComment("inspection.smell.emptyTest.fix.comment"));
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
		return SmellType.EMPTY_METHOD;
	}
}
