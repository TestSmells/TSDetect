package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.*;

/**
 * General Fixture Inspection
 * Looks for test classes that have any fields that are never used
 */
public class GeneralFixtureInspection extends SmellInspection{

	private HashMap<String, PsiElement> unusedFields;

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitClass(PsiClass cls) {
				if(hasSmell(cls)) {
					//any fields left must be unused
					for (PsiElement unusedField : unusedFields.values()) {
						holder.registerProblem(unusedField, getDescription(),
								new QuickFixRemove(getResourceName("FIX.REMOVE")),
								new QuickFixComment(getResourceName("FIX.COMMENT")));
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
		if (!shouldTestElement(element)) return false;
		unusedFields = new HashMap<>();
		if(element instanceof PsiClass) {
			PsiClass cls = (PsiClass) element;
			PsiMethod @NotNull [] methods = cls.getMethods();
			PsiField @NotNull [] fields = cls.getFields();
			unusedFields = new HashMap<>();
			for (PsiField field : fields) {
				unusedFields.put(field.getName(), field);
			}
			for (PsiMethod method : methods) {
				if (!(method.getName().equals("setUp"))) {
					for (PsiStatement statement : Objects.requireNonNull(method.getBody()).getStatements()) {
						for (String potMatch : statement.getText().split("\\W+")) {
							unusedFields.remove(potMatch);
						}
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
