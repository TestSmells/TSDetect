package org.scanl.plugins.tsdetect.inspections;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.InspectionEP;
import com.intellij.codeInspection.ui.InspectionOptionsPanel;
import com.intellij.psi.PsiElement;
import com.intellij.ui.DocumentAdapter;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.model.SmellType;

import javax.swing.*;
import java.awt.*;

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
}
