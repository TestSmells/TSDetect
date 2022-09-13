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
import org.scanl.plugins.tsdetect.ui.tabs.TabDetectedSmells;
import org.scanl.plugins.tsdetect.ui.tabs.TabSmells;
import org.scanl.plugins.tsdetect.ui.tabs.TabSmellyFiles;

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

    private final JPanel panelSmells;
    private final JPanel panelDetectedSmells;
    private final JPanel panelSmellyFiles;

    private TabDetectedSmells tabDetectedSmells;
    private TabSmells tabSmells;
    private TabSmellyFiles tabSmellyFiles;

    public SmellTabbedPaneWindow() {
        panelSmells = new JPanel();
        panelSmells.setVisible(true);
        tabbedPane.add("Smells", panelSmells);

        panelDetectedSmells = new JPanel();
        panelDetectedSmells.setVisible(true);
        tabbedPane.add("Detected Smells", panelDetectedSmells);

        panelSmellyFiles = new JPanel();
        panelSmellyFiles.setVisible(true);
        tabbedPane.add("Smelly Files", panelSmellyFiles);

        panelInspection.remove(tabbedPane);
        panelInspection.updateUI();

        labelLoading.setIcon(Util.GetLoadingIcon());
        labelLoading.setVisible(false);

        buttonAnalyzeProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelLoading.setText("");
                labelLoading.setVisible(true);
                buttonAnalyzeProject.setText("Analyzing Project...Please Wait...");
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
                                        buttonAnalyzeProject.setText("Analyze Project");
                                        buttonAnalyzeProject.setVerticalAlignment(1);
                                        buttonAnalyzeProject.setEnabled(true);

                                        GridConstraints gc = new GridConstraints();
                                        gc.setFill(FILL_BOTH);

                                        tabDetectedSmells = new TabDetectedSmells();
                                        tabDetectedSmells.LoadSmellyData(allClasses,allMethods);
                                        JPanel content1  = tabDetectedSmells.GetContent();
                                        content1.setVisible(true);
                                        panelDetectedSmells.setLayout(new java.awt.BorderLayout());
                                        panelDetectedSmells.add(content1);
                                        panelDetectedSmells.validate();

                                        tabSmellyFiles = new TabSmellyFiles();
                                        tabSmellyFiles.LoadSmellyData(allClasses,allMethods);
                                        JPanel content2  = tabSmellyFiles.GetContent();
                                        content2.setVisible(true);
                                        panelSmellyFiles.setLayout(new java.awt.BorderLayout());
                                        panelSmellyFiles.add(content2);
                                        panelSmellyFiles.validate();

                                        tabSmells = new TabSmells();
                                        tabSmells.LoadSmellyData(allClasses,allMethods);
                                        JPanel content3  = tabSmells.GetContent();
                                        content3.setVisible(true);
                                        panelSmells.setLayout(new java.awt.BorderLayout());
                                        panelSmells.add(content3);
                                        panelSmells.validate();

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
//        setSmellDistributionTable();
//        setSmellTree();
//        setFileTree();
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
