package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.List;
import java.util.Objects;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class LazyTestInspectionTest extends InspectionTest {

	LazyTestInspection inspection;
	PsiClass psiSmellClass;
	PsiClass psiNoSmellClass;
	PsiFileFactory psiFileFactory;
	Project project;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new LazyTestInspection();
		project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
		psiFileFactory = PsiFileFactory.getInstance(project);

		psiSmellClass = loadExample("LazyTest.java");

		PsiClass psiSmellCode = loadExample("TestClass.java");
		myFixture.addClass(psiSmellCode.getText());

		psiNoSmellClass = loadExample("EmptyTestMethodData.java");
	}

	public void testDisplayName(){
		String expectedDisplayName = "Lazy Test";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "LT";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.LAZY_TEST;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testGetMethodArray(){
		List<PsiMethod> methods = inspection.getAllMethodCalls();
		assertNotNull("Asserts that the variable itself is not null",methods);
		assertEquals("Asserts that the size is what is expected",methods.size(), 2);
		assertEquals("Asserts that the expected class contained" +
				"expected method",methods.get(0).getName(),"getVal");
	}

	public void testHasSmell(){
		assertTrue(inspection.hasSmell(psiSmellClass));
	}

	public void testHasNoSmell(){
		assertFalse(inspection.hasSmell(psiNoSmellClass));
	}

}
