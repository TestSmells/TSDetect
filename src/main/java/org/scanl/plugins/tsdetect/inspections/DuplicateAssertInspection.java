package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
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
				if (method.getBody() == null)
					return;
				hasSmell(method);
				for(PsiExpression statement: problemStatements) {
					holder.registerProblem(statement, getDescription(),
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
		boolean output = false;

		List<String> assertCalls = new ArrayList<>();
		List<String> assertMessages = new ArrayList<>();
		HashMap<String,List<PsiMethodCallExpression>> callsToElements = new HashMap<>();
		HashMap<String,List<PsiMethodCallExpression>> messagesToElements = new HashMap<>();

		HashMap<String, Integer> callCounts = new HashMap<>();
		HashMap<String, Integer> messageCounts = new HashMap<>();

		for (PsiMethodCallExpression psiMethodCallExpression : getMethodExpressions((PsiMethod) element)) {
			//only care about message if one exists
			String message = parseMessage(psiMethodCallExpression);
			if(null != message) {
				assertMessages.add(message);
				//Maps messages to lists of elements so that if multiple are found they can be highlighted
				if (messagesToElements.containsKey(message)){
					messagesToElements.get(message).add(psiMethodCallExpression);
				}
				else{
					messagesToElements.put(message,new ArrayList<>());
					messagesToElements.get(message).add(psiMethodCallExpression);
				}
			}

			String name = psiMethodCallExpression.getMethodExpression().getQualifiedName();

			assertCalls.add(name);

			//Maps calls to lists of elements so that if multiple are found they can be highlighted
			if (callsToElements.containsKey(message)){
				callsToElements.get(message).add(psiMethodCallExpression);
			}
			else{
				callsToElements.put(message,new ArrayList<>());
				callsToElements.get(message).add(psiMethodCallExpression);
			}

		}
		if(new HashSet<>(assertMessages).size() < assertMessages.size()){
			for (String message : messagesToElements.keySet()) {
				if(messagesToElements.get(message).size()>0)
					problemStatements.addAll(messagesToElements.get(message));
			}

			return true;
		}

		if(new HashSet<>(assertCalls).size() < assertCalls.size()){
			for (String call : callsToElements.keySet()) {
				if(callsToElements.get(call).size()>0)
					problemStatements.addAll(callsToElements.get(call));
			}
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
