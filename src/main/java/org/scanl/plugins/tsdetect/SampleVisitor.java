package org.scanl.plugins.tsdetect;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.inspections.SmellInspection;
import org.scanl.plugins.tsdetect.inspections.TestSmellInspectionProvider;
import org.scanl.plugins.tsdetect.model.ClassModel;
import org.scanl.plugins.tsdetect.model.Method;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.ArrayList;
import java.util.List;

public class SampleVisitor extends JavaRecursiveElementVisitor {
	private final List<Method> psiMethods = new ArrayList<>();

	private final TestSmellInspectionProvider provider = new TestSmellInspectionProvider();



	@Override
	public void visitMethod(PsiMethod method) {
		List<SmellType> smellTypes = new ArrayList<>();
		Class<? extends LocalInspectionTool> @NotNull [] classes = provider.getInspectionClasses();
		List<SmellInspection> inspections = new ArrayList<>();
		for(Class<? extends LocalInspectionTool> c : classes){
			try {
				SmellInspection a = (SmellInspection) c.newInstance();
				inspections.add(a);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
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
			ClassModel methodClass = new ClassModel(psiClass.getQualifiedName(), 0,0, ClassModel.ClassType.Class,psiClass);
			getPsiMethods().add(new Method(method.getName(), methodClass, 1, 1, method, smellTypes));
		}
		super.visitMethod(method);
	}

	public List<Method> getPsiMethods() {
		return psiMethods;
	}
}
