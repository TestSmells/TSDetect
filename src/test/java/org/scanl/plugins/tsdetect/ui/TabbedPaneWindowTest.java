package org.scanl.plugins.tsdetect.ui;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.openapi.project.Project;
import com.intellij.util.indexing.FileBasedIndex;
import org.jetbrains.annotations.NotNull;
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.model.IdentifierTableModel;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.model.SmellType;

import java.util.*;

public class TabbedPaneWindowTest extends BasePlatformTestCase {
    TabbedPaneWindow testPane;
    Project tempProj;

    //store temp data for testing of private helper functions
    ArrayList<InspectionMethodModel> allMethods = new ArrayList<>();
    ArrayList<InspectionClassModel> allClasses = new ArrayList<>();
    @Override
    public void setUp() throws Exception{
        super.setUp();
        tempProj = Objects.requireNonNull(ProjectManager.getInstanceIfCreated()).getOpenProjects()[0];
        testPane = new TabbedPaneWindow();
        //all code below fills the "allMethods" with smelly methods so that I can test the helper functions
        Collection<VirtualFile> vFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME,
                JavaFileType.INSTANCE, GlobalSearchScope.projectScope(tempProj)); //gets the files in the project
        for(VirtualFile vf : vFiles)
        {
            PsiFile psiFile = PsiManager.getInstance(tempProj).findFile(vf); //converts into a PsiFile
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
                    allMethods.addAll(methods);
                    allClasses.addAll(smellyClasses);
                }
            }
        }
    }

    public void testCreation(){
        assertNotNull(testPane);
    }


    public void testSetSmellDistTable(){
        testPane.setSmellDistributionTable(tempProj);
        
    }

    public void testGetMethodBySmell(){
        List<InspectionMethodModel> tempListOfMethods = testPane.getMethodBySmell(SmellType.EMPTY_METHOD, allMethods);
        assertNotEmpty(tempListOfMethods);
        boolean tempContainsSmell = false;
        for(InspectionMethodModel m:tempListOfMethods){
            if (m.getSmellTypeList().contains(SmellType.EMPTY_METHOD)) {
                tempContainsSmell = true;
                break;
            }
        }
        assertTrue(tempContainsSmell);
    }

    public void testGetClassBySmell(){
        List<InspectionClassModel> tempListOfClasses = testPane.getClassesBySmell(SmellType.EMPTY_METHOD, allClasses);
        assertNotEmpty(tempListOfClasses);
        boolean tempContainsSmell = false;
        for(InspectionClassModel m:tempListOfClasses){
            if (m.getSmellTypeList().contains(SmellType.EMPTY_METHOD)) {
                tempContainsSmell = true;
                break;
            }
        }
        assertTrue(tempContainsSmell);
    }


}
