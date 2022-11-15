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
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.command.WriteCommandAction;
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
    static String content;

    public static void createXml(Project project) {


        WriteCommandAction.runWriteCommandAction(project, () -> {
            boolean xmlExist = FilenameIndex.getFilesByName(project, "AnalysisSummary.xml", GlobalSearchScope.projectScope(project)).length > 0;
            if (!xmlExist) {
                setContent();
                PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(project);

                XmlFile xmlFile = (XmlFile) psiFileFactory.createFileFromText("AnalysisSummary.xml", XMLLanguage.INSTANCE, content);
                EditorFactory editorFactory = EditorFactory.getInstance();

                PsiDirectory psiDirectory = PsiDirectoryFactory.getInstance(project).createDirectory(project.getBaseDir());
                psiDirectory.add(xmlFile);
            }
        });
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
    }