package org.scanl.plugins.tsdetect.inspections;

import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

public class ExceptionHandlingInspectionTest extends InspectionTest {

	PsiClass psiClass;
	MockedStatic<JUnitUtil> junitUtil;
	ExceptionHandlingInspection inspection;

	@Override
	protected void tearDown() throws Exception {
		junitUtil.close();
		super.tearDown();
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		junitUtil = Mockito.mockStatic(JUnitUtil.class);
		junitUtil.when(() -> JUnitUtil.isTestClass(Mockito.any(PsiClass.class))).thenReturn(true);
		inspection = new ExceptionHandlingInspection();
		psiClass = loadExample("ExceptionHandlingData.java");
		myFixture.addClass(psiClass.getText());
	}

	public void testDisplayName(){
		String expectedDisplayName = "Exception Handling";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "EH";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.EXCEPTION_HANDLING;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasThrowSmell(){
		PsiMethod method = psiClass.findMethodsByName("testCatch", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiElement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasCatchSmell(){
		PsiMethod method = psiClass.findMethodsByName("testThrow", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiElement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("testNoThrowing", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiElement.class).stream().anyMatch(inspection::hasSmell);
		assertFalse(result);
	}

}

