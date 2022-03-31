package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.FileNotFoundException;

public class ResourceOptimismInspectionTest extends InspectionTest {

	ResourceOptimismInspection inspection;
	private PsiClass psiClass;

	@Override
	public void setUp() throws Exception{
		super.setUp();
		inspection = new ResourceOptimismInspection();
		psiClass = loadExample("ResourceOptimismTest.java");
		myFixture.addClass(psiClass.getText());
	}

	public void testDisplayName(){
		String expectedDisplayName = "Resource Optimism";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedShortName = "RO";
		String shortName = inspection.getShortName();
		assertEquals(expectedShortName, shortName);
	}

	public void testGroupDisplayName(){
		String expectedName = "JavaTestSmells";
		String name = inspection.getGroupDisplayName();
		assertEquals(expectedName, name);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.RESOURCE_OPTIMISM;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell(){
		PsiMethod method = psiClass.findMethodsByName("ResourceOptimism", false)[0];
		assertTrue(inspection.hasSmell(method));
	}

	public void testHasNoSmell() {
		PsiMethod method = psiClass.findMethodsByName("NotResourceOptimism", false)[0];
		assertFalse(inspection.hasSmell(method));
	}
}
