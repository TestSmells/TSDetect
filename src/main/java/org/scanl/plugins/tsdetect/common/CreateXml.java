package org.scanl.plugins.tsdetect.common;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.intellij.lang.Language;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlFile;
import org.apache.tools.ant.types.selectors.TypeSelector;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXml {

    public static void createXml(Project project) {
//        try {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
                @Override
                public void run() {
                    PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);

                    XmlFile xmlFile = (XmlFile) psiFileFactory.createFileFromText("Test123.xml", XMLLanguage.INSTANCE, "Hello World!!!");
                    EditorFactory editorFactory = EditorFactory.getInstance();

                    PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                    psiDirectory.add(xmlFile);

//                    PsiFile file =  FilenameIndex.getFilesByName(project, "Test123.xml", GlobalSearchScope.projectScope(project))[0];
//                    PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
//                    Editor xmlEditor = editorFactory.createEditor(Objects.requireNonNull(psiDocumentManager.getDocument(file)));
//                    FileEditorManager.getInstance(project).openFile(file.getVirtualFile(), true);
//                    FileDocumentManager.getInstance().saveAllDocuments();

                }});

//
//            DocumentBuilderFactory factory = DocumentBuilderFactory
//                    .newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            Document document = builder.newDocument();
//            document.setXmlStandalone(true);
//
//            Element analysisSummary = document.createElement("AnalysisSummary");
//            document.appendChild(analysisSummary);
//
//            Element testFileSummary = document.createElement("TestFileSummary");
//            analysisSummary.appendChild(testFileSummary);
//            setTestFileSummary(document, testFileSummary);
//
//            Element testMethodSummary = document.createElement("TestMethodSummary");
//            analysisSummary.appendChild(testMethodSummary);
//            setTestMethodSummary(document, testMethodSummary);
//
//            Element testSmellTypeSummary = document.createElement("TestSmellTypeSummary");
//            analysisSummary.appendChild(testSmellTypeSummary);
//            setTestSmellTypeSummary(document, testSmellTypeSummary);
//
//
//            TransformerFactory tff = TransformerFactory.newInstance();
//
//            Transformer tf = tff.newTransformer();
//
//            tf.setOutputProperty(OutputKeys.INDENT, "yes");
//
//
//            tf.transform(new DOMSource(document), new StreamResult(new File(
//                    "Summary.xml")));
//            System.out.println("Create Summary.xml");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("fail to create Summary.xml");
//        }

    }

    public static void setTestFileSummary(Document document, Element testFileSummary) {
        Element testFilesAnalyzed = document.createElement("TestFilesAnalyzed");
        testFilesAnalyzed.setTextContent("0");
        testFileSummary.appendChild(testFilesAnalyzed);

        Element filesWithSmells = document.createElement("FilesWithSmells");
        filesWithSmells.setTextContent("0");
        testFileSummary.appendChild(filesWithSmells);

        Element filesWithoutSmell = document.createElement("FilesWithoutSmells");
        filesWithoutSmell.setTextContent("0");
        testFileSummary.appendChild(filesWithoutSmell);

        Element smelliestFile = document.createElement("SmelliestFile");
        smelliestFile.setTextContent("xyzTest.java");
        testFileSummary.appendChild(smelliestFile);

        Element totalSmells = document.createElement("TotalSmells");
        totalSmells.setTextContent("0");
        testFileSummary.appendChild(totalSmells);
    }

    public static void setTestMethodSummary(Document document, Element testMethodSummary) {
        Element totalTestMethods = document.createElement("TotalTestMethods");
        totalTestMethods.setTextContent("0");
        testMethodSummary.appendChild(totalTestMethods);

        Element smellyMethods = document.createElement("SmellyMethods");
        smellyMethods.setTextContent("0");
        testMethodSummary.appendChild(smellyMethods);

        Element SmelliestMethod = document.createElement("SmelliestMethod");
        SmelliestMethod.setTextContent("test_ABC");
        testMethodSummary.appendChild(SmelliestMethod);

        Element totalSmells = document.createElement("TotalSmells");
        totalSmells.setTextContent("0");
        testMethodSummary.appendChild(totalSmells);

    }

    public static void setTestSmellTypeSummary(Document document, Element testSmellTypeSummary) {
        Element smellyInstances = document.createElement("SmellyInstances");
        smellyInstances.setTextContent("0");
        testSmellTypeSummary.appendChild(smellyInstances);

        Element detectedSmellTypes = document.createElement("DetectedSmellTypes");
        detectedSmellTypes.setTextContent("0");
        testSmellTypeSummary.appendChild(detectedSmellTypes);

        Element mostCommonSmellType = document.createElement("MostCommonSmellType");
        mostCommonSmellType.setTextContent("MAGIC_NUMBER");
        testSmellTypeSummary.appendChild(mostCommonSmellType);

        Element totalInstances = document.createElement("TotalInstances");
        totalInstances.setTextContent("0");
        testSmellTypeSummary.appendChild(totalInstances);

    }
}