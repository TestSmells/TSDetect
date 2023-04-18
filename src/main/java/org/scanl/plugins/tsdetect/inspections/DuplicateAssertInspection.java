package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.PsiLocation;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;

import java.util.*;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class DuplicateAssertInspection extends SmellInspection{
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

	ArrayList<PsiExpression> problemStatements = new ArrayList<>();

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitMethod(PsiMethod method) {
				if (!JUnitUtil.isTestMethod(new PsiLocation<>(method)))
					return;
				if (method.getBody() == null)
					return;
				if(hasSmell(method)){
					holder.registerProblem(method.getBody(), getDescription(),
							new QuickFixComment(getResourceName("FIX.COMMENT")));
				}
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
		if(!(element instanceof PsiMethod)) return false;
		if (!shouldTestElement(element)) return false;

		List<String> assertCalls = new ArrayList<>();
		List<String> assertMessages = new ArrayList<>();

		for (PsiMethodCallExpression psiMethodCallExpression : getMethodExpressions((PsiMethod) element)) {
			//only care about message if one exists
			String message = parseMessage(psiMethodCallExpression);
			if(null != message) {
				assertMessages.add(message);
			}

			String name = psiMethodCallExpression.getMethodExpression().getQualifiedName();
			if(assertsWithTwoParameters.contains(name) ||
				assertsWithOneParameter.contains(name)||
				name.equals("fail")
			)
			assertCalls.add(name);
		}
		if(new HashSet<>(assertMessages).size() < assertMessages.size()){
			return true;
		}

		if(new HashSet<>(assertCalls).size() < assertCalls.size()){
			return true;
		}


		return false;
	}

	private String parseMessage(PsiMethodCallExpression psiMethodCallExpression) {
		int count = psiMethodCallExpression.getArgumentList().getExpressionCount();
		String key = psiMethodCallExpression.getMethodExpression().getQualifiedName();
		String message = null;
		if (key.equals("fail")) {
			if (count == 1) {
				message = foundWithMessage(psiMethodCallExpression) ;
			}
		}
		else if(assertsWithOneParameter.contains(key)){
			if (count == 2){
				message = foundWithMessage(psiMethodCallExpression);
			}
		}
		else if(assertsWithTwoParameters.contains(key)) {
			if (count == 3) {
				message = foundWithMessage(psiMethodCallExpression);
			}
		}
		return message;
	}

	private String foundWithMessage(PsiMethodCallExpression psiMethodCallExpression) {
		PsiExpression @NotNull [] x = psiMethodCallExpression.getArgumentList().getExpressions();
		return x[x.length-1].getText();
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
