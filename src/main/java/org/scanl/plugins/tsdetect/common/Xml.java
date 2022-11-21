package org.scanl.plugins.tsdetect.common;

import com.intellij.ide.JavaLanguageCodeStyleSettingsProvider;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

import java.text.MessageFormat;


public class Xml {

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

    public static void getXml() {

        XmlFile file = CreateXml.getXmlFile();
        XmlDocument document = file.getDocument();
        if (document != null) {
            XmlTag rootTag = document.getRootTag();
            if (rootTag != null) {
                XmlTag TestFileSummary = rootTag.findFirstSubTag("TestFileSummary");
                if (TestFileSummary != null) {
                    TestFilesAnalyzed  = TestFileSummary.findSubTags("TestFilesAnalyzed")[0].getValue().getTrimmedText();
                    FilesWithSmells  = TestFileSummary.findSubTags("FilesWithSmells")[0].getValue().getTrimmedText();
                    FilesWithoutSmells = TestFileSummary.findSubTags("FilesWithoutSmells")[0].getValue().getTrimmedText();
                    FileTotalSmells = TestFileSummary.findSubTags("TotalSmells")[0].getValue().getTrimmedText();

                }

                XmlTag TestMethodSummary = rootTag.findFirstSubTag("TestMethodSummary");
                if (TestMethodSummary != null) {
                    TotalTestMethods  = TestMethodSummary.findSubTags("TotalTestMethods")[0].getValue().getTrimmedText();
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

    public static void setXml() {

        XmlFile file = CreateXml.getXmlFile();
        XmlDocument document = file.getDocument();
        if (document != null) {
            XmlTag rootTag = document.getRootTag();
            if (rootTag != null) {
                XmlTag TestFileSummary = rootTag.findFirstSubTag("TestFileSummary");
                if (TestFileSummary != null) {
                    TestFileSummary.findSubTags("TestFilesAnalyzed")[0].getText().replace("/[0-9]+/", TestFilesAnalyzed);
                    TestFileSummary.findSubTags("FilesWithSmells")[0].getText().replace("/[0-9]+/", FilesWithSmells);
                    TestFileSummary.findSubTags("FilesWithoutSmells")[0].getText().replace("/[0-9]+/", FilesWithoutSmells);
                    TestFileSummary.findSubTags("TotalSmells")[0].getText().replace("/[0-9]+/", FileTotalSmells);

                }

                XmlTag TestMethodSummary = rootTag.findFirstSubTag("TestMethodSummary");
                if (TestMethodSummary != null) {
                    TestMethodSummary.findSubTags("TotalTestMethods")[0].getText().replace("/[0-9]+/", TotalTestMethods);
                    TestMethodSummary.findSubTags("SmellyMethods")[0].getText().replace("/[0-9]+/", SmellyMethods);
                    TestMethodSummary.findSubTags("TotalSmells")[0].getText().replace("/[0-9]+/", MethodTotalSmells);

                }

                XmlTag TestSmellTypeSummary = rootTag.findFirstSubTag("TestSmellTypeSummary");
                if (TestSmellTypeSummary != null) {
                    TestSmellTypeSummary.findSubTags("SmellyInstances")[0].getText().replace("/[0-9]+/", SmellyInstances);
                    TestSmellTypeSummary.findSubTags("DetectedSmellTypes")[0].getText().replace("/[0-9]+/", DetectedSmellTypes);
                    TestSmellTypeSummary.findSubTags("TotalInstances")[0].getText().replace("/[0-9]+/", TotalInstances);

                }
            }
        }
    }

    /** File **/
    public static String getTestFilesAnalyzed(){
        return TestFilesAnalyzed;
    }
    public static String getFilesWithSmells(){
        return FilesWithSmells;
    }
    public static String getFilesWithoutSmells(){
        return FilesWithoutSmells;
    }
    public static String getFileTotalSmells(){
        return FileTotalSmells;
    }
    /** Method**/
    public static String getTotalTestMethods(){
        return TotalTestMethods;
    }
    public static String getSmellyMethods(){
        return SmellyMethods;
    }
    public static String getMethodTotalSmells(){
        return MethodTotalSmells;
    }
    /** SmellType **/
    public static String getSmellyInstances(){
        return SmellyInstances;
    }

    public static String getDetectedSmellTypes(){
        return DetectedSmellTypes;
    }
    public static String getTotalInstances(){
        return TotalInstances;
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
