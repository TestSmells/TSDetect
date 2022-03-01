package org.scanl.plugins.tsdetect;

import com.intellij.execution.junit.JUnitUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public abstract class InspectionTest extends LightJavaCodeInsightFixtureTestCase {

    MockedStatic<JUnitUtil> junitUtil;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // the normal JUnitUtil struggles to understand our test data, because they aren't truly part of a project
        junitUtil = Mockito.mockStatic(JUnitUtil.class);
        junitUtil.when(() -> JUnitUtil.isTestClass(Mockito.any(PsiClass.class))).thenReturn(true);
        junitUtil.when(() -> JUnitUtil.isTestClass(Mockito.argThat(c -> c.getName().equals("TestClass")))).thenReturn(false);
    }

    @Override
    protected void tearDown() throws Exception {
        junitUtil.close();
        super.tearDown();
    }

    public PsiClass loadExample(String name) throws FileNotFoundException {
        Project project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
        File f = new File("src//test//testData//inspections//"+name);
        Scanner fileReader = new Scanner(f);
        StringBuilder sb = new StringBuilder();
        while(fileReader.hasNextLine()){
            sb.append(fileReader.nextLine());
            sb.append('\n');
        }
        PsiJavaFile psiFile = (PsiJavaFile) psiFileFactory.createFileFromText(f.getName(), sb.toString());
        return psiFile.getClasses()[0];
    }
}
