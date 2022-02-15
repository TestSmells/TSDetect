package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class DuplicateAssertInspectionTest extends InspectionTest {


	DuplicateAssertInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new DuplicateAssertInspection();
		psiClass = loadExample("DuplicateAssertTest.java");
	}

	public void testDisplayName(){
		String expectedDisplayName = "Duplicate Assert";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "DA";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.DUPLICATE_ASSERT;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell(){
		PsiMethod method = psiClass.findMethodsByName("DuplicateAssertTest", true)[0];
		assertTrue(inspection.hasSmell(method));
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("NotDuplicateAssertTest", false)[0];
		assertFalse(inspection.hasSmell(method));
	}
}
