package org.scanl.plugins.tsdetect.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.uiDesigner.core.GridConstraints;
import org.scanl.plugins.tsdetect.common.CreateXml;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.common.Util;
import org.scanl.plugins.tsdetect.common.Xml;
import org.scanl.plugins.tsdetect.model.ExecutionResult;
import org.scanl.plugins.tsdetect.service.Analyzer;
import org.scanl.plugins.tsdetect.ui.dialogs.ExportReport;
import org.scanl.plugins.tsdetect.ui.tabs.TabAnalysisSummary;
import org.scanl.plugins.tsdetect.ui.tabs.TabDetectedSmellTypes;
import org.scanl.plugins.tsdetect.ui.tabs.TabInfectedFiles;
import org.scanl.plugins.tsdetect.ui.tabs.TabSmellDistribution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;

import static com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH;
import static org.scanl.plugins.tsdetect.common.Util.GetTestFiles;

public class SmellTabbedPaneWindow {

    private final JPanel panelAnalysisSummary;
    private final JPanel panelSmellDistribution;
    private final JPanel panelDetectedSmellTypes;
    private final JPanel panelInfectedFiles;
    private final TabAnalysisSummary tabAnalysisSummary;
    private final TabDetectedSmellTypes tabDetectedSmellTypes;
    private final TabSmellDistribution tabSmells;
    private final TabInfectedFiles tabInfectedFiles;
    private ExecutionResult executionResult;
    private JPanel panelInspection;
    private JButton buttonAnalyzeProject;
    private JLabel labelLoading;
    private JTabbedPane tabbedPane;
    private JLabel labelExecution;
    private JButton buttonExport;

    private int totalTestFiles;


    public SmellTabbedPaneWindow() {
        buttonAnalyzeProject.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.NAME"));
        buttonAnalyzeProject.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.TOOLTIP"));
        labelLoading.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "LABEL.LOADING.TEXT"));

        panelAnalysisSummary = new JPanel();
        panelAnalysisSummary.setVisible(true);
        panelAnalysisSummary.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.SUMMARY.TAB.NAME"));
        panelAnalysisSummary.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.SUMMARY.TAB.TOOLTIP"));
        tabbedPane.add(panelAnalysisSummary);

        panelSmellDistribution = new JPanel();
        panelSmellDistribution.setVisible(true);
        panelSmellDistribution.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.SMELLS.TAB.NAME"));
        panelSmellDistribution.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.SMELLS.TAB.TOOLTIP"));
        tabbedPane.add(panelSmellDistribution);

        panelDetectedSmellTypes = new JPanel();
        panelDetectedSmellTypes.setVisible(true);
        panelDetectedSmellTypes.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.DETECTED.TAB.NAME"));
        panelDetectedSmellTypes.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.DETECTED.TAB.TOOLTIP"));
        tabbedPane.add(panelDetectedSmellTypes);

        panelInfectedFiles = new JPanel();
        panelInfectedFiles.setVisible(true);
        panelInfectedFiles.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.FILES.TAB.NAME"));
        panelInfectedFiles.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "SMELL.FILES.TAB.TOOLTIP"));
        tabbedPane.add(panelInfectedFiles);

        panelInspection.remove(tabbedPane);
        panelInspection.updateUI();

        labelLoading.setIcon(Util.GetLoadingIcon());
        labelLoading.setVisible(false);
        labelExecution.setVisible(false);
        buttonExport.setVisible(false);

        tabAnalysisSummary = new TabAnalysisSummary();
        tabDetectedSmellTypes = new TabDetectedSmellTypes();
        tabInfectedFiles = new TabInfectedFiles();
        tabSmells = new TabSmellDistribution();
        buttonExport.setVisible(false);

        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        CreateXml.createXml(project);

        buttonAnalyzeProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonExport.setVisible(false);
                labelExecution.setText("");
                labelExecution.setVisible(false);
                labelLoading.setText("");
                labelLoading.setVisible(true);
                buttonAnalyzeProject.setText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.LOADING.TEXT"));
                buttonAnalyzeProject.setEnabled(false);
                panelInspection.remove(tabbedPane);
                panelInspection.updateUI();

                new Thread(() ->
                        ApplicationManager.getApplication().runReadAction(new Runnable() {
                            @Override
                            public void run() {
                                RunAnalysis();

                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        buttonAnalyzeProject.setText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.NAME"));
                                        buttonAnalyzeProject.setEnabled(true);

                                        GridConstraints gc = new GridConstraints();
                                        gc.setFill(FILL_BOTH);

                                        tabDetectedSmellTypes.LoadSmellyData(executionResult.getAllClasses(), executionResult.getAllMethods());
                                        JPanel content1 = tabDetectedSmellTypes.GetContent();
                                        content1.setVisible(true);
                                        panelDetectedSmellTypes.setLayout(new java.awt.BorderLayout());
                                        panelDetectedSmellTypes.add(content1);
                                        panelDetectedSmellTypes.validate();

                                        tabInfectedFiles.LoadSmellyData(executionResult.getAllClasses(), executionResult.getAllMethods());
                                        JPanel content2 = tabInfectedFiles.GetContent();
                                        content2.setVisible(true);
                                        panelInfectedFiles.setLayout(new java.awt.BorderLayout());
                                        panelInfectedFiles.add(content2);
                                        panelInfectedFiles.validate();

                                        tabSmells.LoadSmellyData(executionResult.getAllClasses(), executionResult.getAllMethods());
                                        JPanel content3 = tabSmells.GetContent();
                                        content3.setVisible(true);
                                        panelSmellDistribution.setLayout(new java.awt.BorderLayout());
                                        panelSmellDistribution.add(content3);
                                        panelSmellDistribution.validate();

                                        tabAnalysisSummary.setTotalTestFiles(totalTestFiles);
                                        tabAnalysisSummary.LoadSmellyData(executionResult.getAllClasses(), executionResult.getAllMethods());
                                        JPanel content4 = tabAnalysisSummary.GetContent();
                                        content4.setVisible(true);
                                        panelAnalysisSummary.setLayout(new java.awt.BorderLayout());
                                        panelAnalysisSummary.add(content4);
                                        panelAnalysisSummary.validate();

                                        panelInspection.add(tabbedPane, gc);
                                        labelLoading.setVisible(false);
                                        String pattern = PluginResourceBundle.message(PluginResourceBundle.Type.UI, ("LABEL.EXECUTION.TEXT"));
                                        String message = MessageFormat.format(pattern, executionResult.getExecutionTimestampString(), executionResult.getExecutionDurationInSeconds());
                                        labelExecution.setText(message);
                                        labelExecution.setVisible(true);
                                        buttonExport.setVisible(true);
                                        panelInspection.updateUI();
                                        panelInspection.updateUI();
                                    }
                                });
                            }

                        })).start();
            }
        });

        buttonExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window parentWindow = SwingUtilities.windowForComponent(panelInspection);
                Frame parentFrame = (Frame) parentWindow;

                ExportReport exportReportDialog = new ExportReport(parentFrame, executionResult);
                exportReportDialog.setVisible(true);
            }
        });
    }

    private void RunAnalysis() {
        System.out.println("Running...");
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        executionResult = Analyzer.getInstance().DetectTestSmells(project);
        totalTestFiles = GetTestFiles().size();
        Xml.getXml();
        System.out.println("Running Completed");
    }

    public JPanel getContent() {
        return panelInspection;
    }


}
