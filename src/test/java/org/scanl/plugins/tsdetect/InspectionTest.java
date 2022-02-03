package org.scanl.plugins.tsdetect;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public abstract class InspectionTest extends LightJavaCodeInsightFixtureTestCase {
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
