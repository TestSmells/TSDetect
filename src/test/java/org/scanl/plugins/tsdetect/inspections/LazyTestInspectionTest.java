package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class LazyTestInspectionTest extends LightJavaCodeInsightFixtureTestCase {

	LazyTestInspection inspection;
	PsiClass psiClass;
	PsiFileFactory psiFileFactory;
	PsiJavaFile psiFile;
	Project project;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new LazyTestInspection();
		project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
		psiFileFactory = PsiFileFactory.getInstance(project);
		File f = new File("src//test//testData//inspections//LazyTest.java");
		Scanner fileReader = new Scanner(f);
		StringBuilder sb = new StringBuilder();
		while(fileReader.hasNextLine()){
			sb.append(fileReader.nextLine());
			sb.append('\n');
		}
		psiFile = (PsiJavaFile) psiFileFactory.createFileFromText(f.getName(), sb.toString());
		psiClass = psiFile.getClasses()[0];

		f = new File("src//test//testData//TestClass.java");
		fileReader = new Scanner(f);
		sb = new StringBuilder();
		while(fileReader.hasNextLine()){
			sb.append(fileReader.nextLine());
			sb.append('\n');
		}
		myFixture.addClass(sb.toString());

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
		PsiMethod[] methods = inspection.getMethodCalls(psiClass);
		assertNotNull(methods);
		assertEquals(methods.length, 1);
	}

	public void testHasSmell(){
		assertTrue(inspection.hasSmell(psiClass));
	}
}
