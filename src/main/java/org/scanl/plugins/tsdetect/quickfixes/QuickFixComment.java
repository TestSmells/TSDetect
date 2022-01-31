package org.scanl.plugins.tsdetect.quickfixes;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.project.Project;
import com.intellij.profile.codeInspection.ProjectInspectionProfileManager;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiComment;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;

public class QuickFixComment implements LocalQuickFix {
	String resourceName;

	public QuickFixComment(String resourceName){
		this.resourceName = resourceName;
	}
	/**
	 * @return true if this quick-fix should not be automatically filtered out when running inspections in the batch mode.
	 * Fixes that require editor or display UI should return false. It's not harmful to return true if the fix is never
	 * registered in the batch mode (e.g. {@link ProblemsHolder#isOnTheFly()} is checked at the fix creation site).
	 */
	@Override
	public boolean availableInBatchMode() {
		return false;
	}

	/**
	 * @return the name of the quick fix.
	 */
	@Override
	public @IntentionName @NotNull String getName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,resourceName);
	}

	/**
	 * Called to apply the fix.
	 * <p>
	 * Please call {@link ProjectInspectionProfileManager#fireProfileChanged()} if inspection profile is changed as result of fix.
	 *
	 * @param project    {@link Project}
	 * @param descriptor problem reported by the tool which provided this quick fix action
	 */
	@Override
	public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
		try {
			String psiText = "/* " + descriptor.getPsiElement().getText() + " */";
			PsiComment pisComment = JavaPsiFacade.getElementFactory(project).createCommentFromText(psiText, null);
			descriptor.getPsiElement().replace(pisComment);
		} catch (IncorrectOperationException e) {
			System.out.println(e);
		}
	}


	/**
	 * @return text to appear in "Apply Fix" popup when multiple Quick Fixes exist (in the results of batch code inspection). For example,
	 * if the name of the quickfix is "Create template &lt;filename&gt", the return value of getFamilyName() should be "Create template".
	 * If the name of the quickfix does not depend on a specific element, simply return {@link #getName()}.
	 */
	@Override
	public @IntentionFamilyName @NotNull String getFamilyName() {
		return getName();
	}
}
