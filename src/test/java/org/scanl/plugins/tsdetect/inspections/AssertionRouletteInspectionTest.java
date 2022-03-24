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
    public void testHasNoSmells(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("hasNoActualJunitAsserts", true);
        assertFalse(inspection.hasSmell(psiMethod[0]));
    }
    public void testHasSmellUnit_assertTrueWorks(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertTrueWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertFalseWorks(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertFalseWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertNotNullWorks(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertNotNullWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertNullWorks(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertNullWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertFalse(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertFalseWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasNoSmell(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("testFailureDifferentAssertMethods", true);
        assertFalse(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertArrayEquals(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertArrayEqualsWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertNotSame(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertNotSameWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertSame(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertSameWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }

    public void testHasSmellUnit_assertThrows(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertThrowsWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }
    public void testHasSmellUnit_assertNotEquals(){
        PsiMethod [] psiMethod = psiClass.findMethodsByName("assertNotEqualsWorks", true);
        assertTrue(inspection.hasSmell(psiMethod[0]));
    }
}
