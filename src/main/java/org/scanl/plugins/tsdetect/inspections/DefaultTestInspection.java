package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;

public class DefaultTestInspection extends SmellInspection{

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitClass(PsiClass cls) {
				if(hasSmell(cls)) {
					holder.registerProblem(cls.getNameIdentifier(), DESCRIPTION);
				}
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if (!PluginSettings.GetSetting(getSmellType().toString())) return false;

		if(element instanceof PsiClass){
			PsiClass cls = (PsiClass)element;
			if(cls.getQualifiedName().equals("ExampleInstrumentedTest") || cls.getQualifiedName().equals("ExampleUnitTest"))
				return true;
		}
		return false;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.DEFAULT_TEST;
	}
}
