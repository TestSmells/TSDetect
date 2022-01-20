package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.LocalInspectionTool;
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
import java.util.Objects;

public class EmptyMethodInspection extends AbstractBaseJavaLocalInspectionTool implements SmellInspection{

	private static final String DESCRIPTION =
			PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"inspection.smell.emptytest.description");

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
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "inspection.smell.emptytest.name.display");
	}

	/**
	 * DO NOT OVERRIDE this method.
	 *
	 * @see InspectionEP#shortName
	 */
	@Override
	public @NonNls @NotNull String getShortName() {
		return PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION,"inspection.smell.emptytest.name.short");
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
			public void visitMethod(PsiMethod method) {
				// super.visitMethod(method);
				if (method.getBody() == null)
					return;
				if (hasSmell(method))
					holder.registerProblem(method, DESCRIPTION);
			}
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

	@Override
	public boolean hasSmell(PsiMethod method) {
		return method.getBody().isEmpty();
	}

	@Override
	public SmellType getSmellType() {
		return SmellType.EMPTY_METHOD;
	}
}
