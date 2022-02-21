package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.FileNotFoundException;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
//@TestDataPath("$CONTENT_ROOT/src/test/java/org/scanl/plugins/tsdetect/inspections")
public class IgnoredTestInspectionTest extends InspectionTest {
    IgnoredTestInspection inspection;
    private PsiClass psiClass;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        inspection = new IgnoredTestInspection();
        psiClass = loadExample("IgnoredTest.java");
    }

    public void testDisplayName(){
        String expectedDisplayName = "Ignored Test";
        String displayName = inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedShortName = "IT";
        String shortName = inspection.getShortName();
        assertEquals(expectedShortName, shortName);
    }

    public void testGroupDisplayName(){
        String expectedName = "JavaTestSmells";
        String name = inspection.getGroupDisplayName();
        assertEquals(expectedName, name);
    }

    public void testSmellType(){
        SmellType expectedSmellType = SmellType.IGNORED_TEST;
        SmellType smellType = inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmell(){
        assertTrue(inspection.hasSmell(psiClass));
    }

    public void testHasNoSmell() throws FileNotFoundException {
        PsiClass notGeneralFixtureExample = loadExample("EmptyTestMethodData.java");
        assertFalse(inspection.hasSmell(notGeneralFixtureExample));
    }

}
