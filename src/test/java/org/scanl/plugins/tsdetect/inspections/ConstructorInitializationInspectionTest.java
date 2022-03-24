package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.FileNotFoundException;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class ConstructorInitializationInspectionTest extends InspectionTest {
	ConstructorInitializationInspection inspection;
	private PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new ConstructorInitializationInspection();
		psiClass = loadExample("ConstructorInitializationTestData.java");
	}

	public void testDisplayName(){
		String expectedDisplayName = "Constructor Initialization";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "CI";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}
	public void testSmellType(){
		SmellType expectedSmellType = SmellType.CONSTRUCTOR_INITIALIZATION;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell() {
		PsiMethod method = psiClass.findMethodsByName("ConstructorInitializationTestData", false)[0];
		assertTrue(inspection.hasSmell(method));
	}

	public void testHasNoSmell() {
		PsiMethod method = psiClass.findMethodsByName("TestConstructorInitializationSmell", false)[0];
		assertFalse(inspection.hasSmell(method));
	}
}
