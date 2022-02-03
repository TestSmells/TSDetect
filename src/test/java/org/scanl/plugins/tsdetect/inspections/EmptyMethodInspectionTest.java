package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class EmptyMethodInspectionTest extends InspectionTest {


	EmptyMethodInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new EmptyMethodInspection();
		Project project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
		PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
		File f = new File("src//test//testData//inspections//EmptyTestMethodData.java");
		Scanner fileReader = new Scanner(f);
		StringBuilder sb = new StringBuilder();
		while(fileReader.hasNextLine()){
			sb.append(fileReader.nextLine());
			sb.append('\n');
		}
		PsiJavaFile psiFile = (PsiJavaFile) psiFileFactory.createFileFromText(f.getName(), sb.toString());
		psiClass = psiFile.getClasses()[0];
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
		SmellType expectedSmellType = SmellType.EMPTY_METHOD;
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
