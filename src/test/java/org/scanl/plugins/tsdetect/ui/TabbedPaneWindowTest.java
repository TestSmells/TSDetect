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
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.model.IdentifierTableModel;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@TestDataPath("$CONTENT_ROOT/tests")
public class TabbedPaneWindowTest extends BasePlatformTestCase {
    TabbedPaneWindow testPane;
    Project tempProj;

    //store temp data for testing of private helper functions
    ArrayList<InspectionMethodModel> allMethods = new ArrayList<>();
    ArrayList<InspectionClassModel> allClasses = new ArrayList<>();

    public PsiFile loadExample(String name) throws FileNotFoundException {
        Project project = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
        File f = new File("tests//"+name);
        Scanner fileReader = new Scanner(f);
        StringBuilder sb = new StringBuilder();
        while(fileReader.hasNextLine()){
            sb.append(fileReader.nextLine());
            sb.append('\n');
        }
        return psiFileFactory.createFileFromText(f.getName(), sb.toString());
    }

    /**
     * Need to artificially create all the table and everything so we're able to get the list of classes
     * and methods
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception{
        super.setUp();
        testPane = new TabbedPaneWindow();
        PsiFile psiFile = loadExample("EmptyTestTest.java"); //converts into a PsiFile
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

    }

    public void testCreation(){
        assertNotNull(testPane);
    }


    public void testGetMethodBySmell(){
        List<InspectionMethodModel> tempListOfMethods = testPane.getMethodBySmell(SmellType.EMPTY_TEST, allMethods);
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
        List<InspectionClassModel> tempListOfClasses = testPane.getClassesBySmell(SmellType.EMPTY_TEST, allClasses);
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
