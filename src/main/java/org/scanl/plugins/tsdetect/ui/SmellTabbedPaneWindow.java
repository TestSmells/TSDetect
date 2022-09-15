package org.scanl.plugins.tsdetect.ui;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.uiDesigner.core.GridConstraints;
import org.scanl.plugins.tsdetect.SmellVisitor;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.common.Util;
import org.scanl.plugins.tsdetect.model.InspectionClassModel;
import org.scanl.plugins.tsdetect.model.InspectionMethodModel;
import org.scanl.plugins.tsdetect.ui.tabs.TabDetectedSmellTypes;
import org.scanl.plugins.tsdetect.ui.tabs.TabSmellDistribution;
import org.scanl.plugins.tsdetect.ui.tabs.TabInfectedFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH;

public class SmellTabbedPaneWindow {
    private final List<InspectionMethodModel> allMethods = new ArrayList<>();
    private final List<InspectionClassModel> allClasses = new ArrayList<>();

    private JPanel panelInspection;
    private JButton buttonAnalyzeProject;
    private JLabel labelLoading;
    private JTabbedPane tabbedPane;

    private final JPanel panelSmellDistribution;
    private final JPanel panelDetectedSmellTypes;
    private final JPanel panelInfectedFiles;

    private TabDetectedSmellTypes tabDetectedSmellTypes;
    private TabSmellDistribution tabSmells;
    private TabInfectedFiles tabInfectedFiles;

    public SmellTabbedPaneWindow() {
        buttonAnalyzeProject.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.NAME"));
        buttonAnalyzeProject.setToolTipText(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "BUTTON.ANALYSIS.TOOLTIP"));
        labelLoading.setName(PluginResourceBundle.message(PluginResourceBundle.Type.UI, "LABEL.LOADING.TEXT"));

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

        buttonAnalyzeProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                                        tabDetectedSmellTypes = new TabDetectedSmellTypes();
                                        tabDetectedSmellTypes.LoadSmellyData(allClasses,allMethods);
                                        JPanel content1  = tabDetectedSmellTypes.GetContent();
                                        content1.setVisible(true);
                                        panelDetectedSmellTypes.setLayout(new java.awt.BorderLayout());
                                        panelDetectedSmellTypes.add(content1);
                                        panelDetectedSmellTypes.validate();

                                        tabInfectedFiles = new TabInfectedFiles();
                                        tabInfectedFiles.LoadSmellyData(allClasses,allMethods);
                                        JPanel content2  = tabInfectedFiles.GetContent();
                                        content2.setVisible(true);
                                        panelInfectedFiles.setLayout(new java.awt.BorderLayout());
                                        panelInfectedFiles.add(content2);
                                        panelInfectedFiles.validate();

                                        tabSmells = new TabSmellDistribution();
                                        tabSmells.LoadSmellyData(allClasses,allMethods);
                                        JPanel content3  = tabSmells.GetContent();
                                        content3.setVisible(true);
                                        panelSmellDistribution.setLayout(new java.awt.BorderLayout());
                                        panelSmellDistribution.add(content3);
                                        panelSmellDistribution.validate();

                                        panelInspection.add(tabbedPane, gc);
                                        labelLoading.setVisible(false);
                                        panelInspection.updateUI();
                                        panelInspection.updateUI();
                                    }
                                });
                            }

                        })).start();

            }
        });
    }

    private void RunAnalysis() {
        System.out.println("Running...");
        Project project = ProjectManager.getInstance().getOpenProjects()[0];
        visitSmellDetection(project);
        System.out.println("Running Completed");
    }

    public JPanel getContent() {
        return panelInspection;
    }

    /**
     * Visits all of the smell detection stuff
     *
     * @param project the project that is open
     */
    protected void visitSmellDetection(Project project) {
        Collection<VirtualFile> vFiles = FileTypeIndex.getFiles(JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project)); //gets the files in the project

        allMethods.clear();
        allClasses.clear();
        for (VirtualFile vf : vFiles) {
            PsiFile psiFile = PsiManager.getInstance(project).findFile(vf); //converts into a PsiFile
            if (psiFile instanceof PsiJavaFile) //determines if the PsiFile is a PsiJavaFile
            {
                SmellVisitor sv = new SmellVisitor(); //creates the smell visitor
                psiFile.accept(sv); //visits the methods
                allMethods.addAll(sv.getSmellyMethods());
                allClasses.addAll(sv.getSmellyClasses());

            }
        }
    }




}
