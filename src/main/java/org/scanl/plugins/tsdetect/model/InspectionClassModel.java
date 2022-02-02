package org.scanl.plugins.tsdetect.model;

import com.intellij.psi.PsiElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Inspection Class Model, holds the details for the inspection
 * with name, type, and the actual object
 */
public class InspectionClassModel implements Identifier {

	private final String name; //name of the class
	private final PsiElement psiObject; //the actual object
	private final List<SmellType> smellTypesList = new ArrayList<>();

	public InspectionClassModel(String name, PsiElement psiObject, List<SmellType> smellTypes){
		this.name = name;
		this.psiObject = psiObject;
		this.smellTypesList.addAll(smellTypes);
	}

	/**
	 * Returns the Class Object
	 * @return Returns the class object
	 */
	@Override
	public PsiElement getPsiObject() {
		return psiObject;
	}

	/**
	 * Returns the name of the class
	 * @return returns the name of the class
	 */
	public String getName() {
		return name;
	}

	public List<SmellType> getSmellTypeList()
	{
		return smellTypesList;
	}
}

