package org.scanl.plugins.tsdetect.model;

import com.intellij.psi.PsiElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all the information for a single method, including the object, name,
 * matching class, and smell types
 */
public class InspectionMethodModel implements Identifier {

	private final String name;
	private final InspectionClassModel className;
	private final PsiElement psiObject;
	private final List<SmellType> smellTypeList = new ArrayList<>();

	/**
	 * Creates the method model
	 * @param name name of the method
	 * @param className the class the method is contained in
	 * @param psiObject the PSI method object
	 * @param smellTypes a list of smell types that are included in the method
	 */
	public InspectionMethodModel(String name, InspectionClassModel className, PsiElement psiObject, List<SmellType> smellTypes){
		this.name = name;
		this.className = className;
		this.psiObject = psiObject;
		this.smellTypeList.addAll(smellTypes);
	}

	@Override
	public PsiElement getPsiObject() {
		return psiObject;
	}

	public String getName() {
		return name;
	}

	public InspectionClassModel getClassName(){ return className; }

	public List<SmellType> getSmellTypeList()
	{
		return smellTypeList;
	}

}
