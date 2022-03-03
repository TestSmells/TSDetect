package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

public class AssertionRouletteInspectionTest extends InspectionTest {

    SmellInspection inspection;
    PsiClass psiClass;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        inspection = new AssertionRouletteInspection();
        psiClass = loadExample("AssertionRouletteTest.java");
    }

    public void testDisplayName(){
        String expectedDisplayName = "Assertion Roulette";
        String displayName = inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedDisplayName = "AR";
        String displayName = inspection.getShortName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testGroupDisplayName(){
        String expectedDisplayName = "JavaTestSmells";
        String displayName = inspection.getGroupDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testSmellType(){
        SmellType expectedSmellType = SmellType.ASSERTION_ROULETTE;
        SmellType smellType = inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmellUnit(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("FailureAssertionRoulette01", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasNoSmell(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("testFailureAssertionRoulette02", true);
        assertFalse(inspection.hasSmell(psiMethod[0]));
    }
}
