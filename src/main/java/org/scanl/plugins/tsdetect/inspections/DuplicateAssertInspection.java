package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class DuplicateAssertInspection extends SmellInspection{
	HashMap<String, List<PsiStatement>> duplicateAsserts = new HashMap<>();

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if (method.getBody() == null)
					return;
				hasSmell(method);
				for (List<PsiStatement> statements : duplicateAsserts.values()) {
					if(statements.size() > 1){
						for (PsiStatement statement : statements) {
							holder.registerProblem(statement, DESCRIPTION,
									new QuickFixRemove("INSPECTION.SMELL.DUPLICATE_ASSERT.FIX.REMOVE"),
									new QuickFixComment("INSPECTION.SMELL.DUPLICATE_ASSERT.FIX.COMMENT"));
						}

					}
				}
				duplicateAsserts.clear();

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
		boolean output = false;
		duplicateAsserts.clear();
		if(element instanceof PsiMethod) {
			PsiMethod method = (PsiMethod) element;
			for (PsiStatement statement : Objects.requireNonNull(method.getBody()).getStatements()) {
				String name = statement.getText().replaceAll("\\s", "");
				if(name.contains("assert")){
					if(duplicateAsserts.containsKey(name)){
						duplicateAsserts.get(name).add(statement);
						output = true;
					}
					else{
						duplicateAsserts.put(name, new ArrayList<>());
						duplicateAsserts.get(name).add(statement);

					}
				}
			}
		}
		return output;
	}

	/**
	 * Gets the matching smell type enum
	 * @return The Smell Type Enum
	 */
	@Override
	public SmellType getSmellType() {
		return SmellType.DUPLICATE_ASSERT;
	}
}
