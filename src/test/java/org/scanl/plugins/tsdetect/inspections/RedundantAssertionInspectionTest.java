package org.scanl.plugins.tsdetect.inspections;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.TestDataPath;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;


@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class RedundantAssertionInspectionTest extends InspectionTest {


    RedundantAssertionInspection inspection;
    PsiClass psiClass;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        inspection = new RedundantAssertionInspection();
        psiClass = loadExample("RedundantAssertionTestData.java");
        myFixture.addClass(psiClass.getText());
    }

    public void testDisplayName(){
        String expectedDisplayName = "Redundant Assertion";
        String displayName = inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedDisplayName = "RA";
        String displayName = inspection.getShortName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testGroupDisplayName(){
        String expectedDisplayName = "JavaTestSmells";
        String displayName = inspection.getGroupDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testSmellType(){
        SmellType expectedSmellType = SmellType.REDUNDANT_ASSERTION;
        SmellType smellType = inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmell(){
        PsiMethod method = psiClass.findMethodsByName("testAlwaysTrue", false)[0];
        boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
        assertTrue(result);
    }

    public void testHasNoSmell(){
        PsiMethod method = psiClass.findMethodsByName("testNotAlwaysTrue", false)[0];
        boolean result = PsiTreeUtil.findChildrenOfType(method, PsiMethodCallExpression.class).stream().anyMatch(inspection::hasSmell);
        assertFalse(result);
    }
}
