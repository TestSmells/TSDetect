package org.scanl.plugins.tsdetect.common;

import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.xml.DomManager;

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
    public static void geXml() {

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
}
