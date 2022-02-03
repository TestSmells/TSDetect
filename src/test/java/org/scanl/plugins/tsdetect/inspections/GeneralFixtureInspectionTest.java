package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.*;
import junit.framework.TestCase;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class GeneralFixtureInspectionTest extends InspectionTest {
    GeneralFixtureInspection inspection;
    private PsiClass psiClass;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        inspection = new GeneralFixtureInspection();
        psiClass = loadExample("GeneralFixtureTest.java");
    }

    public void testDisplayName(){
        String expectedDisplayName = "General Fixture";
        String displayName = inspection.getDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testShortName(){
        String expectedDisplayName = "GF";
        String displayName = inspection.getShortName();
        assertEquals(expectedDisplayName, displayName);
    }

    public void testGroupDisplayName(){
        String expectedDisplayName = "JavaTestSmells";
        String displayName = inspection.getGroupDisplayName();
        assertEquals(expectedDisplayName, displayName);
    }
    public void testSmellType(){
        SmellType expectedSmellType = SmellType.GENERAL_FIXTURE;
        SmellType smellType = inspection.getSmellType();
        assertEquals(expectedSmellType, smellType);
    }

    public void testHasSmell() {
        assertTrue(inspection.hasSmell(psiClass));
    }
    //todo switch to a more complex example file
    public void testHasNoSmell() throws FileNotFoundException {
        PsiClass notGeneralFixtureExample = loadExample("EmptyTestMethodData.java");
        assertTrue(inspection.hasSmell(notGeneralFixtureExample));
    }


    public void testGetSmellType() {
    }
}