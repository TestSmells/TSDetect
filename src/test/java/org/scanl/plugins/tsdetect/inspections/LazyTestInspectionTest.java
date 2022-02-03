package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.intellij.lang.annotations.Language;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class LazyTestInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	LazyTestInspection inspection;
	PsiClass psiSmellClass;
	PsiClass psiNoSmellClass;
	PsiFileFactory psiFileFactory;
	PsiJavaFile psiFile;
	Project project;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new LazyTestInspection();
		project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
		psiFileFactory = PsiFileFactory.getInstance(project);

		@Language("JAVA") String psiSmellCode = readFile("src//test//testData//inspections//LazyTest.java");
		psiFile = (PsiJavaFile) psiFileFactory.createFileFromText("LazyTest.java", psiSmellCode);
		psiSmellClass = psiFile.getClasses()[0];

		psiSmellCode = readFile("src//test//testData//TestClass.java");
		myFixture.addClass(psiSmellCode);

		psiSmellCode = readFile("src//test//testData//inspections//EmptyTestMethodData.java");
		psiFile = (PsiJavaFile) psiFileFactory.createFileFromText("EmptyTestMethodData.java", psiSmellCode);
		psiNoSmellClass = psiFile.getClasses()[0];
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
		assertEquals("Asserts that the size is what is expected",methods.size(), 1);
		assertEquals("Asserts that the expected class contained" +
				"expected method",methods.get(0).getName(),"getVal");
	}

	public void testHasSmell(){
		assertTrue(inspection.hasSmell(psiSmellClass));
	}

	public void testHasNoSmell(){
		assertFalse(inspection.hasSmell(psiNoSmellClass));
	}

	private String readFile(String fileName) throws FileNotFoundException {
		File f = new File(fileName);
		Scanner fileReader = new Scanner(f);
		StringBuilder sb = new StringBuilder();
		while(fileReader.hasNextLine()){
			sb.append(fileReader.nextLine());
			sb.append('\n');
		}
		return sb.toString();
	}
}
