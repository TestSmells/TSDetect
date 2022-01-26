package org.scanl.plugins.tsdetect;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.inspections.SmellInspection;
import org.scanl.plugins.tsdetect.inspections.TestSmellInspectionProvider;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Smell Visitor to visit all the files to determine smells
 */
public class SmellVisitor extends JavaRecursiveElementVisitor {
	private final List<InspectionMethodModel> psiMethods = new ArrayList<>();

	private final TestSmellInspectionProvider provider = new TestSmellInspectionProvider();



	@Override
	public void visitMethod(PsiMethod method) {
		List<SmellType> smellTypes = new ArrayList<>();
		Class<? extends LocalInspectionTool> @NotNull [] classes = provider.getInspectionClasses(); //gets the list of smells to check for
		List<SmellInspection> inspections = new ArrayList<>();
		for(Class<? extends LocalInspectionTool> c : classes){ //converts from .class files into SmellInspection objects
			try {
				SmellInspection a = (SmellInspection) c.newInstance();
				inspections.add(a);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		boolean issues = false;

		//goes through every SmellInspection present to see if the method has that smell
		for(SmellInspection inspection:inspections) {
			boolean helperIssue = inspection.hasSmell(method);
			if(helperIssue){
				issues = true;
				smellTypes.add(inspection.getSmellType());
			}
		}

		//if there are any issues, get the containing class and add to the list of smelly methods
		if(issues) {
			PsiClass psiClass = method.getContainingClass();
			InspectionClassModel inspectionClassModel = new InspectionClassModel(Objects.requireNonNull(psiClass).getQualifiedName(), psiClass);
			getSmellyMethods().add(new InspectionMethodModel(method.getName(), inspectionClassModel, method, smellTypes));
		}

		super.visitMethod(method);
	}

	/**
	 * @return the list of smelly methods that have been determined after visiting all methods
	 */
	public List<InspectionMethodModel> getSmellyMethods() {
		return psiMethods;
	}
}
