package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

public class DefaultTestInspectionTest extends InspectionTest {

	DefaultTestInspection inspection;
	PsiClass defaultClass1;
	PsiClass defaultClass2;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new DefaultTestInspection();
		psiClass = loadExample("EmptyTestMethodData.java");
		defaultClass1 = loadExample("ExampleInstrumentedTest.java");
		defaultClass2 = loadExample("ExampleUnitTest.java");
	}

	public void testDisplayName(){
		String expectedDisplayName = "Default Test";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "DT";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.DEFAULT_TEST;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmellInstrumented(){
		assertTrue(inspection.hasSmell(defaultClass1));
	}

	public void testHasSmellUnit(){
		assertTrue(inspection.hasSmell(defaultClass2));
	}
	
	public void testHasNoSmell(){
		assertFalse(inspection.hasSmell(psiClass));
	}
}
