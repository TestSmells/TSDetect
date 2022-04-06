package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ui.InspectionOptionsPanel;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.MethodSignature;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.ui.DocumentAdapter;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.PluginSettings;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Interface to extend when new inspections are being made
 */
public abstract class SmellInspection extends AbstractBaseJavaLocalInspectionTool {
	/**
	 * This method determines if the element being passed in contains a the test smell that has been
	 * implemented
	 * @param element The element that is tested
	 * @return true if the smell has been found, false if not. Used in the "Run Analysis" scan of the whole project
	 */
	public abstract boolean hasSmell(PsiElement element);

	/**
	 * Used by UI to determine what to label the smell as
	 * @return  the Smell Enum defined in SmellTypes
	 */
	public abstract SmellType getSmellType();

	public Class<? extends PsiElement> getVisitedType() { return PsiMethod.class; }

	protected String getResourceName(String resource) { return "INSPECTION.SMELL." + getSmellType().toString() + "." + resource; }

	protected String getResource(String resource) { return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, getResourceName(resource)); }

	/**
	 * Helper method that determines whether the test smell inspection should run. If the inspection is disabled, or if
	 * the code being tested is not actually a JUnit test, then the inspection should not run.
	 *
	 * @param element The element being tested.
	 * @return A boolean indicating whether the inspection should be ran.
	 */
	protected boolean shouldTestElement(PsiElement element) {
		if (!PluginSettings.GetSetting(getSmellType().toString())) return false;

		PsiClass psiClass = element instanceof PsiClass ? (PsiClass) element : PsiTreeUtil.getParentOfType(element, PsiClass.class);
		if (psiClass == null) return false;

		return JUnitUtil.isTestClass(psiClass);
	}

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

	/**
	 * @see InspectionEP#displayName
	 * @see InspectionEP#key
	 * @see InspectionEP#bundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getDisplayName() { return getResource("NAME.DISPLAY"); }

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() { return getResource("NAME.SHORT"); }

	public String getDescription(){
		return getResource("DESCRIPTION");
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


	/**
	 * Determines if the statement contains a call to the production method
	 * @param statement the statement to verify contains the production method
	 * @param productionMethod the production method to search for
	 * @return if the statement contains a call to the production method
	 */
	boolean determineMatchingStatement(PsiStatement statement, PsiMethod productionMethod){
		if (statement instanceof PsiDeclarationStatement) { //Declaration, make variable
			PsiDeclarationStatement declarationStatement = (PsiDeclarationStatement) statement;
			PsiElement psiElement = declarationStatement.getDeclaredElements()[0]; //get what is actually declared
			if (psiElement instanceof PsiLocalVariable) { //if the declared variable is local aka only in the method
				PsiLocalVariable e = (PsiLocalVariable) declarationStatement.getDeclaredElements()[0];
				if (e.getInitializer() instanceof PsiMethodCallExpression) {
					PsiMethodCallExpression expression = (PsiMethodCallExpression) e.getInitializer(); //gets what variable is initialized
					PsiMethod psiMethod = Objects.requireNonNull(expression).resolveMethod();
					return determineMatchingMethods(productionMethod, psiMethod);
				}
			}
		}

		else {
			if (statement instanceof PsiExpressionStatement) { //ExpressionStatement like assertTrue(true);
				PsiExpressionStatement expressionStatement = (PsiExpressionStatement) statement;
				PsiExpression psiExpression = expressionStatement.getExpression();
				if(psiExpression instanceof PsiMethodCallExpression) { //Calls an actual method
					PsiMethodCallExpression methodCallExpression = (PsiMethodCallExpression) psiExpression;
					PsiExpressionList expressionList = methodCallExpression.getArgumentList();
					for (PsiExpression e : expressionList.getExpressions()) { //gets all of arguments as expressions
						if (e instanceof PsiMethodCallExpression) { //if it is an method call expression as an arugment
							PsiMethodCallExpression expression = (PsiMethodCallExpression) e;
							PsiMethod psiMethod = Objects.requireNonNull(expression).resolveMethod();
							return determineMatchingMethods(productionMethod, psiMethod);
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * Determines if the 2 methods are matching
	 * @param productionMethod production method matching
	 * @param psiMethod psi method that was found when testing
	 * @return if the 2 methods have the same signature and class names
	 */
	private boolean determineMatchingMethods(PsiMethod productionMethod, PsiMethod psiMethod) {
		MethodSignature productionSignature = productionMethod.getSignature(PsiSubstitutor.EMPTY);
		MethodSignature psiSignature = Objects.requireNonNull(psiMethod).getSignature(PsiSubstitutor.EMPTY);
		String productionClassName = Objects.requireNonNull(productionMethod.getContainingClass()).getQualifiedName();
		String psiClassName = Objects.requireNonNull(psiMethod.getContainingClass()).getQualifiedName();

		return productionSignature.toString().equals(psiSignature.toString()) //determines if method signature is the same
				&& Objects.requireNonNull(productionClassName).equals(psiClassName); //determines if class names are the same
	}

	/**
	 * Gets all method calls made within a method
	 * @param method
	 * @return
	 */
	static List<PsiMethodCallExpression> getMethodExpressions(PsiMethod method){
		List<PsiMethodCallExpression> methodCallExpressionList = new ArrayList<>();
		PsiStatement @NotNull [] statements = Objects.requireNonNull(method.getBody()).getStatements();
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
	/**
	 * Gets all Method Calls in all production methods
	 * @return a list of methods that exist in all production classes
	 */
	List<PsiMethod> getAllMethodCalls(){
		ArrayList<PsiMethod> methods = new ArrayList<>();
		Project project = ProjectManager.getInstance().getOpenProjects()[0];
		Collection<VirtualFile> vFiles = FileTypeIndex.getFiles(JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)); //gets the files in the project
		for(VirtualFile vf: vFiles){
			PsiFile psiFile = PsiManager.getInstance(project).findFile(Objects.requireNonNull(vf));
			if(psiFile instanceof PsiJavaFile ) { //if is a java file
				PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
				PsiClass productionClass = Objects.requireNonNull(psiJavaFile).getClasses()[0];
				if (!JUnitUtil.isTestClass(productionClass)) { //if not a test class
					methods.addAll(Arrays.asList(productionClass.getMethods()));
				}
			}
		}
		return methods;
	}

}
