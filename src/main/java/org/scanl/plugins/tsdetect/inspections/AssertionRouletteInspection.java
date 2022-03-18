package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;

import java.util.*;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class AssertionRouletteInspection extends SmellInspection {
	HashMap<String, List<PsiElement>> asserts = new HashMap<>();
	HashSet<String> oneParams = new HashSet<>(
			Arrays.asList("assertFalse",
					"assertNotNull",
					"assertNull",
					"assertTrue"
			));
//	HashSet<String> twoParams = new HashSet<>(
//			Arrays.asList("assertFalse",
//					"assertNotNull",
//					"assertNull",
//					"assertTrue"
//			));

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if (method.getBody() == null)
					return;
				hasSmell(method);
				for (List<PsiElement> statements : asserts.values()) {
					if(statements.size() > 1){
						for (PsiElement statement : statements) {
							holder.registerProblem(statement, getDescription());
						}

					}
				}
				asserts.clear();
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
		if (!PluginSettings.GetSetting(getSmellType().toString())) return false;
		boolean output = false;

		if(shouldTestElement(element)) {
			List<PsiMethodCallExpression> x = getMethodExpressions((PsiMethod) element);
			for (PsiMethodCallExpression psiMethodCallExpression : x) {
				int count = psiMethodCallExpression.getArgumentList().getExpressionCount();
				String key = psiMethodCallExpression.getMethodExpression().getQualifiedName();

				if (key.equals("fail")) {
					output = found(key,psiMethodCallExpression);
				} else if (key.startsWith("assert")) {
					if(oneParams.contains(key)){
						if (count < 2){
							output = found(key,psiMethodCallExpression);
						}
					}
					else if (count < 3) {
						output = found(key,psiMethodCallExpression);
					}
				}
			}
		}
		return output;
	}
private boolean found(String key, PsiMethodCallExpression psiMethodCallExpression){

	if (asserts.containsKey(key)) {
		asserts.get(key).add(psiMethodCallExpression);
		return true;
	} else {
		asserts.put(key, new ArrayList<>());
		asserts.get(key).add(psiMethodCallExpression);
		return false;
	}
}
	/**
	 * Gets the matching smell type enum
	 * @return The Smell Type Enum
	 */
	@Override
	public SmellType getSmellType() {
		return SmellType.ASSERTION_ROULETTE;
	}
}