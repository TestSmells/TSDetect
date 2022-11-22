package org.scanl.plugins.tsdetect.common;

import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import java.time.format.DateTimeFormatter;


public class CreateXml {
    static String content;
    static XmlFile xmlFile;
    static String TestFilesAnalyzed = "0";
    static String FilesWithSmells = "0";
    static String FilesWithoutSmells = "0";
    static String FileTotalSmells = "0";
    static String TotalTestMethods = "0";
    static String SmellyMethods = "0";
    static String MethodTotalSmells = "0";
    static String SmellyInstances = "0";
    static String DetectedSmellTypes = "0";
    static String TotalInstances = "0";
    static String SmelliestFile = "NULL";
    static String SmelliestMethod = "NULL";
    static String MostCommonSmellType = "NULL";
    static String date = "yyyy-mm-dd";
    static String time = "hh:mm";
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void createXml(Project project) {

        WriteCommandAction.runWriteCommandAction(project, () -> {
            boolean xmlExist = FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project)).length > 0;
            if (!xmlExist) {
                setContent();
                PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
                xmlFile = (XmlFile) psiFileFactory.createFileFromText("AnalysisSummary.xml", XMLLanguage.INSTANCE, content);
                PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                psiDirectory.add(xmlFile);

            } else {
                xmlFile = (XmlFile) FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project))[0];
            }
        });
    }
    public static void reCreateXml(Project project){
        WriteCommandAction.runWriteCommandAction(project, () -> {
            boolean xmlExist = FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project)).length > 0;
            if (xmlExist) {
                xmlFile.delete();
                System.out.println("--delete--");
                setContent();
                PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);
                xmlFile = (XmlFile) psiFileFactory.createFileFromText("AnalysisSummary.xml", XMLLanguage.INSTANCE, content);
                PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                psiDirectory.add(xmlFile);
            }
        });

    }
    public static void getXml() {
        XmlDocument document = xmlFile.getDocument();
        if (document != null) {
            XmlTag rootTag = document.getRootTag();
            if (rootTag != null) {
                XmlTag TestFileSummary = rootTag.findFirstSubTag("TestFileSummary");
                if (TestFileSummary != null) {
                    TestFilesAnalyzed = TestFileSummary.findSubTags("TestFilesAnalyzed")[0].getValue().getTrimmedText();
                    FilesWithSmells = TestFileSummary.findSubTags("FilesWithSmells")[0].getValue().getTrimmedText();
                    FilesWithoutSmells = TestFileSummary.findSubTags("FilesWithoutSmells")[0].getValue().getTrimmedText();
                    FileTotalSmells = TestFileSummary.findSubTags("TotalSmells")[0].getValue().getTrimmedText();

                }

                XmlTag TestMethodSummary = rootTag.findFirstSubTag("TestMethodSummary");
                if (TestMethodSummary != null) {
                    TotalTestMethods = TestMethodSummary.findSubTags("TotalTestMethods")[0].getValue().getTrimmedText();
                    SmellyMethods = TestMethodSummary.findSubTags("SmellyMethods")[0].getValue().getTrimmedText();
                    MethodTotalSmells = TestMethodSummary.findSubTags("TotalSmells")[0].getValue().getTrimmedText();

                }

                XmlTag TestSmellTypeSummary = rootTag.findFirstSubTag("TestSmellTypeSummary");
                if (TestSmellTypeSummary != null) {
                    SmellyInstances = TestSmellTypeSummary.findSubTags("SmellyInstances")[0].getValue().getTrimmedText();
                    DetectedSmellTypes = TestSmellTypeSummary.findSubTags("DetectedSmellTypes")[0].getValue().getTrimmedText();
                    TotalInstances = TestSmellTypeSummary.findSubTags("TotalInstances")[0].getValue().getTrimmedText();

                }
            }
        }
    }
    public static void setContent(){
        String tab1 = "\t";
        String tab2 = "\t\t";
        content = "<?xml version=\"1.0 \"?>\n"
                + "<AnalysisSummary lastRunDate='" + date+ "' lastRunTime='"+ time +"'>\n"
                + tab1+ "<TestFileSummary>\n"
                + tab2 +"<TestFilesAnalyzed>"+ TestFilesAnalyzed +"</TestFilesAnalyzed>\n"
                + tab2 + "<FilesWithSmells>"+ FilesWithSmells +"</FilesWithSmells>\n"
                + tab2 + "<FilesWithoutSmells>" +FilesWithoutSmells +"</FilesWithoutSmells>\n"
                + tab2 + "<SmelliestFile>"+SmelliestFile +"</SmelliestFile>\n"
                + tab2 + "<TotalSmells>"+ FileTotalSmells +"</TotalSmells>\n"
                + tab1 + "</TestFileSummary>\n"
                + tab1 + "<TestMethodSummary>\n"
                + tab2 + "<TotalTestMethods>"+ TotalTestMethods +"</TotalTestMethods>\n"
                + tab2 + "<SmellyMethods>"+ SmellyMethods +"</SmellyMethods>\n"
                + tab2 + "<SmelliestMethod>"+ SmelliestMethod +"</SmelliestMethod>\n"
                + tab2 + "<TotalSmells>"+ MethodTotalSmells +"</TotalSmells>\n"
                + tab1 + "</TestMethodSummary>\n"
                + tab1 +"<TestSmellTypeSummary>\n"
                + tab2 + "<SmellyInstances>"+ SmellyInstances +"</SmellyInstances>\n"
                + tab2 + "<DetectedSmellTypes>"+ DetectedSmellTypes +"</DetectedSmellTypes>\n"
                + tab2 + "<MostCommonSmellType>"+ MostCommonSmellType +"</MostCommonSmellType>\n"
                + tab2 +"<TotalInstances>"+ TotalInstances +"</TotalInstances>\n"
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
    public static void setSmelliestFile(String NewSmelliestFile){
        SmelliestFile = NewSmelliestFile;
    }
    public static void setSmelliestMethod(String NewSmelliestMethod){
        SmelliestMethod = NewSmelliestMethod;
    }
    public static void setMostCommonSmellType(String NewMostCommonSmellType){
        MostCommonSmellType = NewMostCommonSmellType;
    }
    public static void setDateTime(){
        date = String.valueOf(java.time.LocalDate.now());
        time = java.time.LocalTime.now().format(formatter);
    }
    /** File **/
    public static String getTestFilesAnalyzed() {
        return TestFilesAnalyzed;
    }

    public static String getFilesWithSmells() {
        return FilesWithSmells;
    }

    public static String getFilesWithoutSmells() {
        return FilesWithoutSmells;
    }

    public static String getFileTotalSmells() {
        return FileTotalSmells;
    }

    /** Method **/
    public static String getTotalTestMethods() {
        return TotalTestMethods;
    }

    public static String getSmellyMethods() {
        return SmellyMethods;
    }

    public static String getMethodTotalSmells() {
        return MethodTotalSmells;
    }
    /** SmellType **/
    public static String getSmellyInstances() {
        return SmellyInstances;
    }

    public static String getDetectedSmellTypes() {
        return DetectedSmellTypes;
    }

    public static String getTotalInstances() {
        return TotalInstances;
    }

}
