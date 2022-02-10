package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.*;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class EmptyMethodInspectionTest extends InspectionTest {


	EmptyMethodInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new EmptyMethodInspection();
		psiClass = loadExample("EmptyTestMethodData.java");//psiFile.getClasses()[0];
	}

	public void testDisplayName(){
		String expectedDisplayName = "Empty Test";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "ET";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.EMPTY_TEST;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell(){
		PsiMethod method = psiClass.findMethodsByName("EmptyTest", false)[0];
		assertTrue(inspection.hasSmell(method));
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("NotEmptyTest", false)[0];
		assertFalse(inspection.hasSmell(method));
	}
}
