package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixComment;
import org.scanl.plugins.tsdetect.quickfixes.QuickFixRemove;


public class ConditionalTestLogicInspection extends SmellInspection {

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitStatement(PsiStatement statement){
				PsiMethod psiMethod= PsiTreeUtil.getParentOfType(statement, PsiMethod.class);
				if (JUnitUtil.isTestAnnotated(psiMethod)) {
					if (hasSmell(statement)) {
						holder.registerProblem(statement, DESCRIPTION);
					}
				}
				super.visitStatement(statement);
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if (!PluginSettings.GetSetting(getSmellType().toString())) return false;
		if(element instanceof PsiIfStatement)
			return true;
		if(element instanceof PsiForStatement)
			return true;
		if(element instanceof PsiSwitchStatement)
			return true;
		if(element instanceof PsiForeachStatement)
			return true;
		return element instanceof PsiWhileStatement;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.CONDITIONAL_TEST;
	}

	@Override
	public Class<? extends PsiElement> getVisitedType() { return PsiStatement.class; }
}
