package org.scanl.plugins.tsdetect.common;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXml {

    public static void createXml() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlStandalone(true);

            Element analysisSummary = document.createElement("AnalysisSummary");
            document.appendChild(analysisSummary);

            Element testFileSummary = document.createElement("TestFileSummary");
            analysisSummary.appendChild(testFileSummary);
            setTestFileSummary(document, testFileSummary);

            Element testMethodSummary = document.createElement("TestMethodSummary");
            analysisSummary.appendChild(testMethodSummary);
            setTestMethodSummary(document, testMethodSummary);

            Element testSmellTypeSummary = document.createElement("TestSmellTypeSummary");
            analysisSummary.appendChild(testSmellTypeSummary);
            setTestSmellTypeSummary(document, testSmellTypeSummary);


            TransformerFactory tff = TransformerFactory.newInstance();

            Transformer tf = tff.newTransformer();

            tf.setOutputProperty(OutputKeys.INDENT, "yes");


            tf.transform(new DOMSource(document), new StreamResult(new File(
                    "Summary.xml")));
            System.out.println("Create Summary.xml");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("fail to create Summary.xml");
        }

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