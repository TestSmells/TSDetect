package org.scanl.plugins.tsdetect.common;

import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import io.grpc.Attributes;
import org.scanl.plugins.tsdetect.ui.controls.summary.TestFileSummary;

import java.io.IOException;


public class CreateXml {
    static String content;
    static String updatedContent;
    static XmlFile xmlFile;
    static String TestFilesAnalyzed;
    static String FilesWithSmells;
    static String FilesWithoutSmells;
    static String FileTotalSmells;
    static String TotalTestMethods;
    static String SmellyMethods;
    static String MethodTotalSmells;
    static String SmellyInstances;
    static String DetectedSmellTypes;
    static String TotalInstances;
    public static void createXml(Project project) {

        WriteCommandAction.runWriteCommandAction(project, () -> {
            boolean xmlExist = FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project)).length > 0;
            if (!xmlExist) {
                setContent();
                PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
                XmlFile xmlFile = (XmlFile) psiFileFactory.createFileFromText("AnalysisSummary.xml", XMLLanguage.INSTANCE, content);
                setXmlFile(xmlFile);
                PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                psiDirectory.add(xmlFile);

            } else {
                xmlFile = (XmlFile) FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project))[0];
                setXmlFile(xmlFile);
            }
        });
    }
    public static void reCreateXml(Project project){
        WriteCommandAction.runWriteCommandAction(project, () -> {
            boolean xmlExist = FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project)).length > 0;
            if (xmlExist) {
                xmlFile.delete();
                System.out.println("--delete--");
                setUpdatedContent();
                PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
                XmlFile xmlFile = (XmlFile) psiFileFactory.createFileFromText("AnalysisSummary.xml", XMLLanguage.INSTANCE, updatedContent);
                setXmlFile(xmlFile);
                PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                psiDirectory.add(xmlFile);
            }
        });

    }

    public static void setXmlFile(XmlFile file){
        xmlFile = file;
    }
    public static XmlFile getXmlFile(){
        return xmlFile;
    }
    public static void setContent(){
        String tab1 = "\t";
        String tab2 = "\t\t";
        content = "<?xml version=\"1.0 \"?>\n"
                   + "<AnalysisSummary lastRunDate='2022-11-07' lastRunTime='15:38'>\n"
                        + tab1+ "<TestFileSummary>\n"
                            + tab2 +"<TestFilesAnalyzed>0</TestFilesAnalyzed>\n"
                            + tab2 + "<FilesWithSmells>0</FilesWithSmells>\n"
                            + tab2 + "<FilesWithoutSmells>0</FilesWithoutSmells>\n"
                            + tab2 + "<SmelliestFile>xyzTest.java</SmelliestFile>\n"
                            + tab2 + "<TotalSmells>0</TotalSmells>\n"
                        + tab1 + "</TestFileSummary>\n"
                        + tab1 + "<TestMethodSummary>\n"
                            + tab2 + "<TotalTestMethods>0</TotalTestMethods>\n"
                            + tab2 + "<SmellyMethods>0</SmellyMethods>\n"
                            + tab2 + "<SmelliestMethod>test_ABC</SmelliestMethod>\n"
                            + tab2 + "<TotalSmells>0</TotalSmells>\n"
                        + tab1 + "</TestMethodSummary>\n"
                        + tab1 +"<TestSmellTypeSummary>\n"
                            + tab2 + "<SmellyInstances>0</SmellyInstances>\n"
                            + tab2 + "<DetectedSmellTypes>0</DetectedSmellTypes>\n"
                            + tab2 + "<MostCommonSmellType>MAGIC_NUMBER</MostCommonSmellType>\n"
                            + tab2 +"<TotalInstances>0</TotalInstances>\n"
                        + tab1 +"</TestSmellTypeSummary>\n"
                    + "</AnalysisSummary>";
    }

    public static void setUpdatedContent(){
        String tab1 = "\t";
        String tab2 = "\t\t";
        updatedContent = "<?xml version=\"1.0 \"?>\n"
                + "<AnalysisSummary lastRunDate='2022-11-07' lastRunTime='15:38'>\n"
                + tab1+ "<TestFileSummary>\n"
                + tab2 +"<TestFilesAnalyzed>"+ "10000"+"</TestFilesAnalyzed>\n"
                + tab2 + "<FilesWithSmells>"+ "0"+"</FilesWithSmells>\n"
                + tab2 + "<FilesWithoutSmells>" +"0" +"</FilesWithoutSmells>\n"
                + tab2 + "<SmelliestFile>HelloWorld!</SmelliestFile>\n"
                + tab2 + "<TotalSmells>"+"0"+"</TotalSmells>\n"
                + tab1 + "</TestFileSummary>\n"
                + tab1 + "<TestMethodSummary>\n"
                + tab2 + "<TotalTestMethods>"+ Xml.getTotalTestMethods() +"</TotalTestMethods>\n"
                + tab2 + "<SmellyMethods>"+ Xml.getSmellyMethods() +"</SmellyMethods>\n"
                + tab2 + "<SmelliestMethod>HelloWorld!</SmelliestMethod>\n"
                + tab2 + "<TotalSmells>"+ Xml.getMethodTotalSmells() +"</TotalSmells>\n"
                + tab1 + "</TestMethodSummary>\n"
                + tab1 +"<TestSmellTypeSummary>\n"
                + tab2 + "<SmellyInstances>"+ Xml.getSmellyInstances() +"</SmellyInstances>\n"
                + tab2 + "<DetectedSmellTypes>"+ Xml.getDetectedSmellTypes() +"</DetectedSmellTypes>\n"
                + tab2 + "<MostCommonSmellType>ABC</MostCommonSmellType>\n"
                + tab2 +"<TotalInstances>"+ Xml.getTotalInstances() +"</TotalInstances>\n"
                + tab1 +"</TestSmellTypeSummary>\n"
                + "</AnalysisSummary>";
    }


    /** File **/
    public static void setTestFilesAnalyzed(String NewTestFilesAnalyzed){
        TestFilesAnalyzed = NewTestFilesAnalyzed;
    }
    public static void setFilesWithSmells(String NewFilesWithSmells){
        FilesWithSmells = NewFilesWithSmells;
    }
    public static void setFilesWithoutSmells(String NewFilesWithoutSmells){
        FilesWithoutSmells = NewFilesWithoutSmells;
    }
    public static void setFileTotalSmells(String NewFileTotalSmells){
        FileTotalSmells = NewFileTotalSmells;
    }

    /** Method**/
    public static void setTotalTestMethods(String NewTotalTestMethods){
        TotalTestMethods = NewTotalTestMethods;
    }
    public static void setSmellyMethods(String NewSmellyMethods){
        SmellyMethods = NewSmellyMethods;
    }
    public static void setMethodTotalSmells(String NewMethodTotalSmells){
        MethodTotalSmells = NewMethodTotalSmells;
    }

    /** SmellType **/
    public static void setSmellyInstances(String NewSmellyInstances){
        SmellyInstances = NewSmellyInstances;
    }

    public static void setDetectedSmellTypes(String NewDetectedSmellTypes){
        DetectedSmellTypes = NewDetectedSmellTypes;
    }
    public static void setTotalInstances(String NewTotalInstances){
        TotalInstances = NewTotalInstances;
    }
}
