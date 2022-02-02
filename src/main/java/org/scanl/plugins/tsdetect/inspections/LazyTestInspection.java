package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ProblemsHolder;
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
				issueStatements = new ArrayList<>();
				if(hasSmell(cls)) {
					for(PsiStatement statement:issueStatements)
						holder.registerProblem(statement, DESCRIPTION);
				}
			}
		};
	}

	@Override
	public boolean hasSmell(PsiElement element) {
		if(element instanceof PsiClass) {
			PsiClass psiClass = (PsiClass) element;
			if (hasMatchingClass(psiClass)) {
				PsiMethod[] methods = getMethodCalls(psiClass);
				for(PsiMethod productionMethod:methods){
					int count = 0;
					for(PsiMethod psiMethod:psiClass.getMethods()){
						if(Objects.requireNonNull(psiMethod.getBody()).getText().contains(productionMethod.getName())){
							count++;
						}
					}
					if(count>1) {
						for(PsiMethod method:psiClass.getMethods()){
							for(PsiStatement statement: Objects.requireNonNull(method.getBody()).getStatements()){
								if(statement.getText().contains(productionMethod.getName()))
									issueStatements.add(statement);
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}

	boolean hasMatchingClass(PsiClass psiClass){
		HashMap<String, String> classes = getPairs();
		return classes.containsKey(psiClass.getQualifiedName());
	}

	private HashMap<String, String> getPairs(){
		HashMap<String, String> classes = new HashMap<>();
		classes.put("LazyTest", "TestClass");
		return classes;
	}

	PsiMethod[] getMethodCalls(PsiClass psiClass){
		HashMap<String, String> classes = getPairs();

		Project project = ProjectManager.getInstance().getOpenProjects()[0];
		String productionClassName = classes.get(psiClass.getQualifiedName());
		VirtualFile virtualFile = null;
		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
				JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));
		for(VirtualFile vf: vFiles){
			if(vf.getNameWithoutExtension().toLowerCase().equals(productionClassName.toLowerCase()))
				virtualFile = vf;
		}
		PsiJavaFile psiFile = (PsiJavaFile) PsiManager.getInstance(project).findFile(Objects.requireNonNull(virtualFile));
		PsiClass productionClass = Objects.requireNonNull(psiFile).getClasses()[0];
		return productionClass.getMethods();
	}


	@Override
	public SmellType getSmellType() {
		return SmellType.LAZY_TEST;
	}
}
