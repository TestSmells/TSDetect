package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LazyTestInspection  extends SmellInspection{

	private static final String DESCRIPTION =
			PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.lazyTest.description");

	private List<PsiStatement> issueStatements = new ArrayList<>();

	/**
	 * @see InspectionEP#displayName
	 * @see InspectionEP#key
	 * @see InspectionEP#bundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getDisplayName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.lazyTest.name.display");
	}

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.lazyTest.name.short");
	}

	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {
			@Override
			public void visitClass(PsiClass cls) {
				if(hasSmell(cls)) {
					for(PsiStatement statement:issueStatements)
						holder.registerProblem(statement, DESCRIPTION);
				}
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		issueStatements = new ArrayList<>();
		List<PsiMethod> psiMethods = getAllMethodCalls();
		if(element instanceof PsiClass) {
			PsiClass psiClass = (PsiClass) element;
			for(PsiMethod proMethod:psiMethods) {
				List<PsiStatement> possibleIssues = new ArrayList<>();
				for (PsiMethod method : psiClass.getMethods()) {
					for (PsiStatement statement : Objects.requireNonNull(method.getBody()).getStatements()) {
						if (determineMatchingStatement(statement, proMethod)) {
							possibleIssues.add(statement);
						}
					}
				}
				if(possibleIssues.size()>1)
					issueStatements.addAll(possibleIssues);
			}
		}
		return issueStatements.size()>0;
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.LAZY_TEST;
	}
}
