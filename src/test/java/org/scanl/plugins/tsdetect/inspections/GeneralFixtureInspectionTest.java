package org.scanl.plugins.tsdetect.inspections;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiElementFactory;
import junit.framework.TestCase;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.Objects;

public class GeneralFixtureInspectionTest extends TestCase {
    EmptyMethodInspection inspection;
    PsiElementFactory elementFactory;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        inspection = new EmptyMethodInspection();
        Project project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        elementFactory = PsiElementFactory.getInstance(project);
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
        PsiClass psiClass = elementFactory.createClass("generalFixture");

    }

    public void testHasNoSmell() {
    }

    public void testGetSmellType() {
    }
}