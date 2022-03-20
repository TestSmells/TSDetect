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
public class DuplicateAssertInspection extends SmellInspection{
	HashMap<String, List<PsiMethodCallExpression>> duplicateAsserts = new HashMap<>();
	HashMap<String, HashSet<String>> foundParameters = new HashMap<>();
	HashSet<String> assertsWithOneParameter = new HashSet<>(
			Arrays.asList(
					"assertTrue",
					"assertFalse",
					"assertNotNull",
					"assertNull"
			));

	HashSet<String> assertsWithTwoParameters = new HashSet<>(
			Arrays.asList(
					"assertArrayEquals",
					"assertEquals",
					"assertNotSame",
					"assertSame",
					"assertThrows",
					"assertNotEquals"
			));

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if (method.getBody() == null)
					return;
				hasSmell(method);
				for (List<PsiMethodCallExpression> statements : duplicateAsserts.values()) {
					if(statements.size() > 1){
						for (PsiMethodCallExpression statement : statements) {
							holder.registerProblem(statement, getDescription(),
								new QuickFixComment(getResourceName("FIX.COMMENT")));
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
		if (!PluginSettings.GetSetting(getSmellType().toString())) return false;
		if(!(element instanceof PsiMethod)) return false;
		if (!shouldTestElement(element)) return false;

		boolean output = false;

		List<PsiMethodCallExpression> x = getMethodExpressions((PsiMethod) element);
		for (PsiMethodCallExpression psiMethodCallExpression : x) {
			int count = psiMethodCallExpression.getArgumentList().getExpressionCount();
			String key = psiMethodCallExpression.getMethodExpression().getQualifiedName();
			System.out.println(key);
			if (key.equals("fail")) {
				if (count == 1) {
					output = foundWithMessage(psiMethodCallExpression);
				}
				else {
					output = foundWithoutMessage(psiMethodCallExpression);
				}
			}
			else if(assertsWithOneParameter.contains(key)){
				if (count == 2){
					output = foundWithMessage(psiMethodCallExpression);
				}
				else{
					output = foundWithoutMessage(psiMethodCallExpression);
				}
			}
			else if(assertsWithTwoParameters.contains(key)) {
				if (count == 3) {
					output = foundWithMessage(psiMethodCallExpression);
				}
				else{
					output = foundWithoutMessage(psiMethodCallExpression);
				}
			}
		}
		return output;
	}
	/**
	 * if has does not have duplicate arguments, then is not duplicate assert
		- covered by checking if all params are equal, if false then not duplicate assert

	 * if has duplicate arguments and unique message, then is not duplicate assert
		- covered by checking if arguments are equal, then checking if message is already found
	 * if has duplicate arguments AND not unique message, then is duplicate assert
	 	- covered by checking if arguments are equal, then checking if message is already found

	 * if has duplicate arguments and no message, then is duplicate assert
	 	- covered by checking if arguments are equal and no message exists
	 */

	/**
	 * handles if a Junit assert without a message is found
	 */
	private boolean foundWithMessage(PsiMethodCallExpression psiMethodCallExpression) {
		String key = psiMethodCallExpression.getMethodExpression().getQualifiedName();
		return false;
	}

	/**
	 * handles if a JUnit assert with a message is found
	 * @return
	 */
	private boolean foundWithoutMessage(PsiMethodCallExpression psiMethodCallExpression){
		String key = psiMethodCallExpression.getMethodExpression().getQualifiedName();
		@NotNull PsiExpressionList x = psiMethodCallExpression.getArgumentList();
		List<String> args = new ArrayList<>();
		for (PsiExpression expression : x.getExpressions()) {
			args.add(expression.getText());
			System.out.println(expression.getText());
		}
		if(foundParameters.containsKey(key)){
			if(foundParameters.get(key).contains(x.toString().replaceAll("\\+W",""))){
			}
		}

		return false;
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
