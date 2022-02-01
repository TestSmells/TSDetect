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
	private final List<InspectionClassModel> psiClasses = new ArrayList<>();
	private List<String> smellyClasses = new ArrayList<>();

	private final TestSmellInspectionProvider provider = new TestSmellInspectionProvider();

	@Override
	public void visitClass(PsiClass cls){

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
		determineSmellsByClass(cls, inspections);
		for(PsiMethod method:cls.getMethods())
			determineSmellsByMethod(method, inspections);

	}

	public void determineSmellsByClass(PsiClass cls, List<SmellInspection> inspections){
		List<SmellType> classSmellTypes = new ArrayList<>();
		boolean issues = false;

		//goes through every SmellInspection present to see if the class has that smell
		for(SmellInspection inspection:inspections) {
			if (inspection.hasSmell(cls)) {
					issues = true;
					classSmellTypes.add(inspection.getSmellType());
			}
		}

		if(issues) {
			InspectionClassModel inspectionClassModel = new InspectionClassModel(Objects.requireNonNull(cls).getQualifiedName(), cls, classSmellTypes);
			getSmellyClasses().add(inspectionClassModel);
			smellyClasses.add(cls.getQualifiedName());
		}
	}

	public void determineSmellsByMethod(PsiMethod method, List<SmellInspection> inspections){
		List<SmellType> smellTypes = new ArrayList<>();
		boolean issues = false;
		for(SmellInspection inspection:inspections){
			boolean helperIssue = inspection.hasSmell(method);
			if(helperIssue){
				issues = true;
				smellTypes.add(inspection.getSmellType());
			}
		}
		JUnitUtil.isTestAnnotated(method);
		//JUnitUtil.isTestAnnotated(method) checks if it is an annotated test
		if(issues) {
			PsiClass psiClass = method.getContainingClass();
			assert psiClass != null;
			InspectionClassModel classModel = new InspectionClassModel(psiClass.getQualifiedName(), psiClass, smellTypes);
			getSmellyMethods().add(new InspectionMethodModel(method.getName(), classModel, method, smellTypes));
			if(!smellyClasses.contains(psiClass.getQualifiedName())){
				getSmellyClasses().add(classModel);
				smellyClasses.add(psiClass.getQualifiedName());
			}
		}
	}

	/**
	 * @return the list of smelly methods that have been determined after visiting all methods
	 */
	public List<InspectionMethodModel> getSmellyMethods() {
		return psiMethods;
	}

	public List<InspectionClassModel> getSmellyClasses(){return psiClasses;}
}
