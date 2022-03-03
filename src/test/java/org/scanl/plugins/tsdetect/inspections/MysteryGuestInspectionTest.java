package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDeclarationStatement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiStatement;
import com.intellij.psi.util.PsiTreeUtil;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

public class MysteryGuestInspectionTest extends InspectionTest {

	MysteryGuestInspection inspection;
	PsiClass psiClass;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		inspection = new MysteryGuestInspection();
		psiClass = loadExample("MysteryGuestTestData.java");
	}

	public void testDisplayName(){
		String expectedDisplayName = "Mystery Guest";
		String displayName = inspection.getDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testShortName(){
		String expectedDisplayName = "MG";
		String displayName = inspection.getShortName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testGroupDisplayName(){
		String expectedDisplayName = "JavaTestSmells";
		String displayName = inspection.getGroupDisplayName();
		assertEquals(expectedDisplayName, displayName);
	}

	public void testSmellType(){
		SmellType expectedSmellType = SmellType.MYSTERY_GUEST;
		SmellType smellType = inspection.getSmellType();
		assertEquals(expectedSmellType, smellType);
	}

	public void testHasSmell(){
		PsiMethod method = psiClass.findMethodsByName("mysteryGuestTest", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiDeclarationStatement.class).stream().anyMatch(inspection::hasSmell);
		assertTrue(result);
	}

	public void testHasNoSmell(){
		PsiMethod method = psiClass.findMethodsByName("notDuplicateAssertTest", false)[0];
		boolean result = PsiTreeUtil.findChildrenOfType(method, PsiDeclarationStatement.class).stream().anyMatch(inspection::hasSmell);
		assertFalse(result);
	}
}
