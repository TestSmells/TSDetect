package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ui.InspectionOptionsPanel;
import com.intellij.psi.*;
import com.intellij.ui.DocumentAdapter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Empty Method Inspection
 * Looks for test methods that are empty
 */
public class GeneralFixtureInspection extends AbstractBaseJavaLocalInspectionTool implements SmellInspection{
	private static final String DESCRIPTION =
			PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"inspection.smell.generalfixture.description");

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
	 * @see InspectionEP#displayName
	 * @see InspectionEP#key
	 * @see InspectionEP#bundle
	 */
	@Override
	public @Nls(capitalization = Nls.Capitalization.Sentence) @NotNull String getDisplayName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.generalfixture.name.display");
	}

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"inspection.smell.generalfixture.name.short");
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


	@Override
	public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
		return new JavaElementVisitor() {

			@Override
			public void visitClass(PsiClass cls) {
				PsiMethod @NotNull [] methods = cls.getMethods();
				PsiField @NotNull [] fields = cls.getFields();
				HashMap<String, PsiElement> unusedFields = new HashMap<>();
				for (PsiField field : fields) {
					unusedFields.put(field.getName(),field);
				}
				for (PsiMethod method : methods) {
					if(!(method.getName().equals("setUp"))){
						for (PsiStatement statement : Objects.requireNonNull(method.getBody()).getStatements()) {
							for (String potMatch : statement.getText().split("\\W+")) {
//								System.out.print("removing: ");
//								System.out.println(potMatch);
								unusedFields.remove(potMatch);

							}
						}
					}
				}
				//any fields left must be unused
				for (String unusedField : unusedFields.keySet()) {
//					System.out.print("unusedfield: ");
//					System.out.println(unusedField);
					holder.registerProblem(unusedFields.get(unusedField), DESCRIPTION);

				}



			}
//			return new JavaElementVisitor() {
//				@Override
//				public void visitClass(PsiClass psiClass) {
//					if (hasSmell(psiClass))
//						holder.registerProblem(psiClass, DESCRIPTION);
//				}
//			};
		};
	}

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
	 * Determines if the methods are using all fields declared in the test
	 * @param element the method being looked for to see if it is using the fields
	 * @return if the PSI Method is empty or not
	 */
	@Override
	public boolean hasSmell(PsiElement element) {
		return false;
	}


	/**
	 * Gets the matching smell type enum
	 * @return The Smell Type Enum
	 */
	@Override
	public SmellType getSmellType() {
		return SmellType.GENERAL_FIXTURE;
	}
}
