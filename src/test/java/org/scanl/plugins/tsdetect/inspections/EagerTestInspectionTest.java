package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class EagerTestInspectionTest extends InspectionTest {
    EagerTestInspection inspection;
    PsiClass psiSmellClass;
    PsiClass psiNoSmellClass;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        this.inspection = new EagerTestInspection();
        this.psiSmellClass = loadExample("EagerTestData.java");
        this.psiNoSmellClass = loadExample("EmptyTestMethodData.java");
    }

    public void testDisplayName(){
        String expectedDisplayName = "Eager Test";
        String displayName = this.inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedDisplayName = "EaT";
        String displayName = this.inspection.getShortName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testGroupDisplayName(){
        String expectedDisplayName = "JavaTestSmells";
        String displayName = this.inspection.getGroupDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testSmellType(){
        SmellType expectedSmellType = SmellType.EAGER_TEST;
        SmellType smellType = this.inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmell() {
        assertTrue(this.inspection.hasSmell(psiSmellClass));
    }

    public void testHasNoSmell() {
        assertFalse(this.inspection.hasSmell(psiNoSmellClass));
    }
}
