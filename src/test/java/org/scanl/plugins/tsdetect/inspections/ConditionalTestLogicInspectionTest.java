package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.util.PsiTreeUtil;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

public class ConditionalTestLogicInspectionTest extends InspectionTest {
	ConditionalTestLogicInspection inspection;
	PsiClass psiClass;


	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new ConditionalTestLogicInspection();
		psiClass = loadExample("ConditionalTestLogicData.java");
	}

	public void testDisplayName(){
		String expectedDisplayName = "Conditional Test";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "CT";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.CONDITIONAL_TEST;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasIfSmell(){
		PsiMethod method = psiClass.findMethodsByName("testConditionalIf", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiStatement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasForSmell(){
		PsiMethod method = psiClass.findMethodsByName("testConditionalFor", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiStatement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasSwitchSmell(){
		PsiMethod method = psiClass.findMethodsByName("testConditionalSwitch", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiStatement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasForeachSmell(){
		PsiMethod method = psiClass.findMethodsByName("testConditionalForEach", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiStatement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasWhileSmell(){
		PsiMethod method = psiClass.findMethodsByName("testConditionalWhile", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiStatement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("DuplicateAssertTest", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
		assertFalse(result);
	}

}
