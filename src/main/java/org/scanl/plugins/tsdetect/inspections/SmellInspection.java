package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.scanl.plugins.tsdetect.model.SmellType;

/**
 * Interface to extend when new inspections are being made
 */
public interface SmellInspection {
	boolean hasSmell(PsiElement element);
	SmellType getSmellType();
}
