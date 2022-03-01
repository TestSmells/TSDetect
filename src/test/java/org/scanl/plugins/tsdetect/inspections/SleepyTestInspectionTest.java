package org.scanl.plugins.tsdetect.inspections;

import com.intellij.execution.junit.JUnitUtil;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.TestDataPath;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class SleepyTestInspectionTest extends InspectionTest {


	SleepyTestInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		inspection = new SleepyTestInspection();
		psiClass = loadExample("SleepyTestData.java");
		myFixture.addClass(psiClass.getText());
	}

	public void testDisplayName(){
		String expectedDisplayName = "Sleepy Test";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "ST";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.SLEEPY_TEST;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell(){
		PsiMethod method = psiClass.findMethodsByName("SleepyTest", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("NotSleepyTest", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
		assertFalse(result);
	}
}
