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
				for(PsiExpression statement: problemStatements)
				holder.registerProblem(statement, getDescription(),
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
		if(!(element instanceof PsiMethod)) return false;
		if (!shouldTestElement(element)) return false;
		boolean output = false;

		Set<AssT> methodExpressions = new HashSet<>();
		for (PsiMethodCallExpression psiMethodCallExpression : getMethodExpressions((PsiMethod) element)) {

			AssT ass = new AssT(psiMethodCallExpression);
				methodExpressions.add(ass);

		}
		return output;
	}

	private class AssT {
		public final String name;
		public final PsiMethodCallExpression ref;
		public Set<PsiExpression> params;

		AssT(PsiMethodCallExpression psiMethodCallExpression){
			this.ref = psiMethodCallExpression;
			String key = psiMethodCallExpression.getMethodExpression().getQualifiedName();
			this.name = key;

			int count = psiMethodCallExpression.getArgumentList().getExpressionCount();
			if (key.equals("fail")) {
				if (count == 1) {
					this.params = foundWithMessage(psiMethodCallExpression);
				}
				else {
					this.params = foundWithoutMessage(psiMethodCallExpression);
				}
			}
			else if(assertsWithOneParameter.contains(key)){
				if (count == 2){
					this.params = foundWithMessage(psiMethodCallExpression);
				}
				else{
					this.params = foundWithoutMessage(psiMethodCallExpression);
				}
			}
			else if(assertsWithTwoParameters.contains(key)) {
				if (count == 3) {
					this.params = foundWithMessage(psiMethodCallExpression);
				}
				else{
					this.params = foundWithoutMessage(psiMethodCallExpression);
				}
			}
		}

		private Set<PsiExpression> foundWithoutMessage(PsiMethodCallExpression psiMethodCallExpression) {
			HashSet<PsiExpression> psiParameters = new HashSet<>();
			PsiExpression @NotNull [] x = psiMethodCallExpression.getArgumentList().getExpressions();
			psiParameters.addAll(List.of(x));
			return psiParameters;
		}

		private Set<PsiExpression> foundWithMessage(PsiMethodCallExpression psiMethodCallExpression) {
			HashSet<PsiExpression> psiParameters = new HashSet<>();
			PsiExpression @NotNull [] x = psiMethodCallExpression.getArgumentList().getExpressions();
			int i = 0;
			while (i < x.length-1){
					psiParameters.add(x[i]);
				i++;
			}
			psiParameters.addAll(List.of(x));
			return psiParameters;
		}

		@Override
		public String toString() {
			return "AssT{" +
					"name='" + name + '\'' +
//					", ref=" + ref +
					", params=" + params +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			AssT assT = (AssT) o;
			return Objects.equals(name, assT.name) && Objects.equals(params, assT.params);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name,params);
		}
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
