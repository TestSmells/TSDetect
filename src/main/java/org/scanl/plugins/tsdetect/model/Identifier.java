package org.scanl.plugins.tsdetect.model;

import com.intellij.psi.PsiElement;

public interface Identifier {
	String getName();
	PsiElement getPsiObject();
}

