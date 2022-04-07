package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class VerboseTestInspectionTest extends InspectionTest {
    VerboseTestInspection inspection;
    private PsiClass psiClass;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        inspection = new VerboseTestInspection();
        psiClass = loadExample("VerboseTest.java");
    }

    public void testDisplayName(){
        String expectedDisplayName = "Verbose Test";
        String displayName = inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedShortName = "VT";
        String shortName = inspection.getShortName();
        assertEquals(expectedShortName, shortName);
    }

    public void testGroupDisplayName(){
        String expectedName = "JavaTestSmells";
        String name = inspection.getGroupDisplayName();
        assertEquals(expectedName, name);
    }

    public void testSmellType(){
        SmellType expectedSmellType = SmellType.VERBOSE_TEST;
        SmellType smellType = inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmell(){
        PsiMethod method = psiClass.findMethodsByName("TooLong", false)[0];
        assertTrue(inspection.hasSmell(method));
    }

    public void testHasNoSmell(){
        PsiMethod method = psiClass.findMethodsByName("NotTooLong", false)[0];
        assertFalse(inspection.hasSmell(method));
    }

    public void testEmptyMethod(){
        PsiMethod method = psiClass.findMethodsByName("NothingInside", false)[0];
        assertFalse(inspection.hasSmell(method));
    }
}
