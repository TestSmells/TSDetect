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

import java.util.*;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class GeneralFixtureInspection extends SmellInspection{

	private static final String DESCRIPTION =
			PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.generalFixture.description");

	private HashMap<String, PsiElement> unusedFields;

	/**
	 * @see InspectionEP#displayName
	 * @see InspectionEP#key
	 * @see InspectionEP#bundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getDisplayName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.generalFixture.name.display");
	}

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.generalFixture.name.short");
	}

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitClass(PsiClass cls) {
				if(hasSmell(cls)) {
					//any fields left must be unused
					for (String unusedField : unusedFields.keySet()) {
						holder.registerProblem(unusedFields.get(unusedField), DESCRIPTION,
								new QuickFixRemove("inspection.smell.generalFixture.fix.remove"),
								new QuickFixComment("inspection.smell.generalFixture.fix.comment")
						);
					}
				}
			}
		};
	}

	/**
	 * Determines if the methods are using all fields declared in the test
	 * @param element the method being looked for to see if it is using the fields
	 * @return if the PSI Method is empty or not
	 */
	@Override
	public boolean hasSmell(PsiElement element) {
		PsiClass cls = (PsiClass) element;
		PsiMethod @NotNull [] methods = cls.getMethods();
		PsiField @NotNull [] fields = cls.getFields();
		unusedFields = new HashMap<>();
		for (PsiField field : fields) {
			unusedFields.put(field.getName(),field);
		}
		for (PsiMethod method : methods) {
			if(!(method.getName().equals("setUp"))){
				for (PsiStatement statement : Objects.requireNonNull(method.getBody()).getStatements()) {
					for (String potMatch : statement.getText().split("\\W+")) {
						unusedFields.remove(potMatch);
					}
				}
			}
		}
		return unusedFields.keySet().size() > 0;
	}

	/**
	 * Gets the matching smell type enum
	 * @return The Smell Type Enum
	 */
	@Override
	public SmellType getSmellType() {
		return SmellType.GENERAL_FIXTURE;
	}
}
