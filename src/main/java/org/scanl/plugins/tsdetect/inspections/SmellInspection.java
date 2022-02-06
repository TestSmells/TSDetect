package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ui.InspectionOptionsPanel;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;
import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

/**
 * Interface to extend when new inspections are being made
 */
public abstract class SmellInspection extends AbstractBaseJavaLocalInspectionTool {
	public abstract boolean hasSmell(PsiElement element);
	public abstract SmellType getSmellType();

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#enabledByDefault
	 */
	@Override
	public boolean isEnabledByDefault() {
		return true;
	}

	/**
	 * @see InspectionEP#groupDisplayName
	 * @see InspectionEP#groupKey
	 * @see InspectionEP#groupBundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getGroupDisplayName() {
		return "JavaTestSmells";
	}

	@SuppressWarnings({"WeakerAccess"})
	@NonNls
	public String CHECKED_CLASSES = "java.io.PrintStream";

	/**
	 * This method is called each time UI is shown.
	 * To get correct spacing, return a JComponent with empty insets using Kotlin UI DSL
	 * or {@link InspectionOptionsPanel}.
	 *
	 * @return {@code null} if no UI options required.
	 */
	@Override
	public JComponent createOptionsPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JTextField checkedClasses = new JTextField(CHECKED_CLASSES);
		checkedClasses.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			protected void textChanged(javax.swing.event.@NotNull DocumentEvent e) {
				CHECKED_CLASSES = checkedClasses.getText();
			}
		});
		panel.add(checkedClasses);
		return panel;
	}

	/*boolean hasMatchingClass(PsiClass psiClass){
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
		VirtualFile vf = getFileMatchingName(productionClassName);
		PsiJavaFile psiFile = (PsiJavaFile) PsiManager.getInstance(project).findFile(Objects.requireNonNull(vf));
		PsiClass productionClass = Objects.requireNonNull(psiFile).getClasses()[0];
		return productionClass.getMethods();
	}

	private VirtualFile getFileMatchingName(String className){
		Project project = ProjectManager.getInstance().getOpenProjects()[0];
		Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
				JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project));
		for(VirtualFile vf: vFiles){
			if(vf.getNameWithoutExtension().toLowerCase().equals(className.toLowerCase()))
				return vf;
		}
		return null;
	}*/
}
