package org.scanl.plugins.tsdetect.model;

import com.intellij.psi.PsiElement;

public class ClassModel implements Identifier {

	private final String name;
	private final int lineNumber;
	private final int columnNumber;
	private final String type;
	private final PsiElement psiObject;

	public ClassModel(String name, int lineNumber, int columnNumber, ClassType type, PsiElement psiObject){
		this.name = name;
		this.lineNumber = lineNumber;
		this.columnNumber = columnNumber;
		this.type = type.toString();
		this.psiObject = psiObject;
	}

	public String getType() {
		return type;
	}

	@Override
	public PsiElement getPsiObject() {
		return psiObject;
	}

	public String getName() {
		return name;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public enum ClassType{
		Class,
		Interface,
		Annotation,
		Record,
		Enum
	}
}

