package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.testFramework.TestDataPath;
import org.junit.Test;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.FileNotFoundException;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class UnknownTestInspectionTest extends InspectionTest{
    UnknownTestInspection inspection;
    private PsiClass psiClass;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        inspection = new UnknownTestInspection();
        psiClass = loadExample("UnknownTest.java");
    }

    public void testDisplayName(){
        String expectedDisplayName = "Unknown Test";
        String displayName = inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedShortName = "UT";
        String shortName = inspection.getShortName();
        assertEquals(expectedShortName, shortName);
    }

    public void testGroupDisplayName(){
        String expectedName = "JavaTestSmells";
        String name = inspection.getGroupDisplayName();
        assertEquals(expectedName, name);
    }

    public void testSmellType(){
        SmellType expectedSmellType = SmellType.UNKNOWN_TEST;
        SmellType smellType = inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmell(){
        PsiMethod method = psiClass.findMethodsByName("UnknownTestSmell", false)[0];
        assertTrue(inspection.hasSmell(method));
    }

    public void testHasNoSmell() throws FileNotFoundException {
        PsiMethod method = psiClass.findMethodsByName("NoUnknownTestSmell", false)[0];
        assertFalse(inspection.hasSmell(method));
    }
}
