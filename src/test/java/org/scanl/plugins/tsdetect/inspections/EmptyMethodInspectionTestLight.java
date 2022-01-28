package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class EmptyMethodInspectionTestLight {
	EmptyMethodInspection inspection;
	PsiElementFactory elementFactory;

	@Before
	public void setUp() throws Exception {
		inspection = new EmptyMethodInspection();
		Project project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
		elementFactory = PsiElementFactory.getInstance(project);
	}

	@Test
	public void displayNameTest(){
		String expectedDisplayName = "Empty Test";
		String displayName = inspection.getDisplayName();
		Assert.assertEquals(expectedDisplayName, displayName);
	}

	@Test
	public void testShortName(){
		String expectedDisplayName = "ET";
		String displayName = inspection.getShortName();
		Assert.assertEquals(expectedDisplayName, displayName);
	}

	@Test
	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		Assert.assertEquals(expectedDisplayName, displayName);
	}

	@Test
	public void testSmellType(){
		SmellType expectedSmellType = SmellType.EMPTY_METHOD;
		SmellType smellType = inspection.getSmellType();
		Assert.assertEquals(expectedSmellType, smellType);
	}

	@Test
	public void testHasSmell(){
		PsiMethod method = elementFactory.createMethod("emptyTest", PsiType.VOID);
		try {
			addLines("src//test//testData//inspections//emptyTestHasSmellData.txt", method);
			System.out.println(method.getText());
			Assert.assertTrue(inspection.hasSmell(method));
		} catch (FileNotFoundException e) {
			Assert.fail("File containing text was not found " + e.getMessage());
		}
	}

	@Test
	public void testHasNoSmell(){
		PsiMethod method = elementFactory.createMethod("emptyTest", PsiType.VOID);
		System.out.println(method.getText());
		PsiCodeBlock codeBlock = method.getBody();
		PsiStatement statement = elementFactory.createStatementFromText("System.out.println(\"Hello\");", codeBlock);
		codeBlock.add(statement);
		System.out.println(method.getText());
		Assert.assertFalse(inspection.hasSmell(method));


	}

	private void addLines(String filename, PsiMethod method) throws FileNotFoundException {
		PsiCodeBlock codeBlock = method.getBody();
		File f = new File(filename);
		Scanner scan = new Scanner(f);
		System.out.println(method.getText());
		while(scan.hasNextLine()){
			String command = scan.nextLine();
			PsiStatement statement = elementFactory.createStatementFromText(command, codeBlock);
			Objects.requireNonNull(codeBlock).add(statement);
		}
		System.out.println(method.getText());
	}
}
