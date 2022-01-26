package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.Objects;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class EmptyMethodInspectionTest extends BasePlatformTestCase {


	EmptyMethodInspection inspection;
	PsiElementFactory elementFactory;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new EmptyMethodInspection();
		Project project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
		elementFactory = PsiElementFactory.getInstance(project);
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
		PsiMethod method = elementFactory.createMethod("emptyTest", PsiType.VOID);
		assertTrue(inspection.hasSmell(method));
	}


	public void testHasNoSmell(){
		PsiMethod method = elementFactory.createMethod("emptyTest", PsiType.VOID);
		PsiCodeBlock codeBlock = method.getBody();
		PsiStatement statement = elementFactory.createStatementFromText("System.out.println(\"Hello\");", codeBlock);
		Objects.requireNonNull(codeBlock).add(statement);
		assertFalse(inspection.hasSmell(method));
	}
}
