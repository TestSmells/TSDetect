package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.*;

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
						if (statement instanceof PsiDeclarationStatement) {
							PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) statement;
							if(determineMatchingPsiDeclaration(declarationStatement, proMethod))
								possibleIssues.add(statement);

						} else {
							if (statement instanceof PsiExpressionStatement) {
								PsiExpressionStatement expressionStatement = (PsiExpressionStatement) statement;
								if(determineMatchingPsiExpression(expressionStatement, proMethod))
									possibleIssues.add(statement);
							}
						}
					}
				}
				if(possibleIssues.size()>1)
					issueStatements.addAll(possibleIssues);
			}
		}
		return issueStatements.size()>0;
	}

	private boolean determineMatchingPsiExpression(PsiExpressionStatement expressionStatement, PsiMethod method){
		PsiExpression expression = expressionStatement.getExpression();
		if(expression instanceof PsiMethodCallExpression) {
			PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) expression;
			PsiExpressionList expressionList = methodCallExpression.getArgumentList();
			for (PsiExpression e : expressionList.getExpressions()) {
				if (e instanceof PsiMethodCallExpression) {
					PsiMethodCallExpression mCall = (PsiMethodCallExpression) e;
					PsiMethod psiMethod = Objects.requireNonNull(mCall).resolveMethod();
					return Objects.requireNonNull(psiMethod).getName().equals(method.getName());
				}
			}
		}
		return false;
	}

	private boolean determineMatchingPsiDeclaration(PsiDeclarationStatement declarationStatement, PsiMethod method){
		PsiElement psiElement = declarationStatement.getDeclaredElements()[0];
		if(psiElement instanceof PsiLocalVariable) {
			PsiLocalVariable e = (PsiLocalVariable) declarationStatement.getDeclaredElements()[0];
			if(e.getInitializer() instanceof PsiMethodCallExpression) {
				PsiMethodCallExpression expression = (PsiMethodCallExpression) e.getInitializer();
				PsiMethod psiMethod = Objects.requireNonNull(expression).resolveMethod();
				return method.getName().equals(Objects.requireNonNull(psiMethod).getName());
			}
		}
		return false;
	}


	List<PsiMethod> getAllMethodCalls(){
		ArrayList<PsiMethod> methods = new ArrayList<>();
		Project project = ProjectManager.getInstance().getOpenProjects()[0];
		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
				JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));
		for(VirtualFile vf: vFiles){
			PsiJavaFile psiFile = (PsiJavaFile) PsiManager.getInstance(project).findFile(Objects.requireNonNull(vf));
			PsiClass productionClass = Objects.requireNonNull(psiFile).getClasses()[0];
			if(!JUnitUtil.isTestClass(productionClass)) {
				methods.addAll(Arrays.asList(productionClass.getMethods()));
			}
		}
		return methods;
	}


	@Override
	public SmellType getSmellType() {
		return SmellType.LAZY_TEST;
	}
}
