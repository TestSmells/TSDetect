package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class RedundantPrintInspectionTest extends InspectionTest {


	RedundantPrintInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new RedundantPrintInspection();
		psiClass = loadExample("RedundantPrintData.java");//psiFile.getClasses()[0];
		myFixture.addClass(psiClass.getText());
	}

	public void testDisplayName(){
		String expectedDisplayName = "Redundant Print";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "RP";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.REDUNDANT_PRINT;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell(){
		PsiMethod method = psiClass.findMethodsByName("Printf", false)[0];
		assertTrue(inspection.hasSmell(method.getBody().getStatements()[3].getFirstChild())); // get the method call expression
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("notWriting", false)[0];
		assertFalse(inspection.hasSmell(method));
	}
}
