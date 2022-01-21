package org.scanl.plugins.tsdetect.model;

import com.intellij.psi.PsiElement;

public interface Identifier {
	public String getName();
	public PsiElement getPsiObject();
}

