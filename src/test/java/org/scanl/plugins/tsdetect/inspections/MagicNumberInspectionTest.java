package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.util.PsiTreeUtil;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

public class MagicNumberInspectionTest extends InspectionTest {
	MagicNumberInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new MagicNumberInspection();
		psiClass = loadExample("MagicNumberTestData.java");
		myFixture.addClass(psiClass.getText());
	}

	public void testDisplayName(){
		String expectedDisplayName = "Magic Number";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "MN";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.MAGIC_NUMBER;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasIfSmell(){
		PsiMethod method = psiClass.findMethodsByName("FailureMagicNumber01", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("notDuplicateAssertTest", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
		assertFalse(result);
	}

}
