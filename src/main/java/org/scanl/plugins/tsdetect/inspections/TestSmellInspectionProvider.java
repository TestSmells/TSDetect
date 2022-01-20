package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.codeInspection.InspectionToolProvider;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestSmellInspectionProvider implements InspectionToolProvider {
	/**
	 * Query method for inspection tools provided by a plugin.
	 *
	 * @return classes that extend {@link InspectionProfileEntry}
	 */
	@Override
	public Class<? extends LocalInspectionTool> @NotNull [] getInspectionClasses() {
		return new Class[]{
				EmptyMethodInspection.class
		};
	}

	public static List<PsiMethodCallExpression> getMethodExpressions(PsiMethod method){
		List<PsiMethodCallExpression> methodCallExpressionList = new ArrayList<>();
		PsiStatement @NotNull [] statements = method.getBody().getStatements();
		for(PsiStatement statement: statements) {
			if(statement instanceof PsiExpressionStatement)
			{
				PsiExpressionStatement expressionStatement = (PsiExpressionStatement) statement;
				PsiExpression expression = expressionStatement.getExpression();
				if(expression instanceof PsiMethodCallExpression) {
					PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) expression;
					methodCallExpressionList.add(methodCallExpression);
				}
			}
		}
		return methodCallExpressionList;
	}

}
