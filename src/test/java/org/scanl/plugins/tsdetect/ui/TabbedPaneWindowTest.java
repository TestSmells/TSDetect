package org.scanl.plugins.tsdetect.ui;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.TestDataPath;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.openapi.project.Project;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.InspectionTest;
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.model.IdentifierTableModel;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@TestDataPath("$CONTENT_ROOT/src/test/testData")
public class TabbedPaneWindowTest extends InspectionTest {
    TabbedPaneWindow testPane;
    Project tempProj;

    //store temp data for testing of private helper functions
    ArrayList<InspectionMethodModel> allMethods = new ArrayList<>();
    ArrayList<InspectionClassModel> allClasses = new ArrayList<>();

    /**
     * Need to artificially create all the table and everything so we're able to get the list of classes
     * and methods
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception{
        super.setUp();
        testPane = new TabbedPaneWindow();
        tempProj = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        PsiFile psiFile = loadExample("EmptyTestMethodData.java").getContainingFile(); //converts into a PsiFile
        if(psiFile instanceof  PsiJavaFile) //determines if the PsiFile is a PsiJavaFile
        {
            PsiJavaFile psiJavaFile = (PsiJavaFile) psiFile;
            PsiClass @NotNull [] classes = psiJavaFile.getClasses(); //gets the classes
            for(PsiClass psiClass: classes) {
                SmellVisitor sv = new SmellVisitor(); //creates the smell visitor
                psiFile.accept(sv); //visits the methods

                List<InspectionMethodModel> methods = sv.getSmellyMethods(); //gets all the smelly methods
                for(InspectionMethodModel method:methods){
                    System.out.println(method.getName());
                }
                List<InspectionClassModel> smellyClasses = sv.getSmellyClasses();
                System.out.println("added" + methods.size());
                allMethods.addAll(methods);
                allClasses.addAll(smellyClasses);
            }
        }
        myFixture.addClass(psiFile.getText());
        testPane.visitSmellDetection(tempProj);
    }

    public void testCreation(){
        assertNotNull(testPane);
    }


    public void testGetMethodBySmell(){
        List<InspectionMethodModel> tempListOfMethods = testPane.getMethodBySmell(SmellType.EMPTY_TEST);
        assertNotEmpty(tempListOfMethods);
        boolean tempContainsSmell = false;
        for(InspectionMethodModel m:tempListOfMethods){
            if (m.getSmellTypeList().contains(SmellType.EMPTY_TEST)) {
                tempContainsSmell = true;
                break;
            }
        }
        assertTrue(tempContainsSmell);
    }

    public void testGetClassBySmell(){
        List<InspectionClassModel> tempListOfClasses = testPane.getClassesBySmell(SmellType.EMPTY_TEST);
        assertNotEmpty(tempListOfClasses);
        boolean tempContainsSmell = false;
        for(InspectionClassModel m:tempListOfClasses){
            if (m.getSmellTypeList().contains(SmellType.EMPTY_TEST)) {
                tempContainsSmell = true;
                break;
            }
        }
        assertTrue(tempContainsSmell);
    }


}
