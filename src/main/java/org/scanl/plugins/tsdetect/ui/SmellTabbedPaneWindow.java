package org.scanl.plugins.tsdetect.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.uiDesigner.core.GridConstraints;
import org.scanl.plugins.tsdetect.common.Util;
import org.scanl.plugins.tsdetect.ui.tabs.TabDetectedSmells;
import org.scanl.plugins.tsdetect.ui.tabs.TabSmells;
import org.scanl.plugins.tsdetect.ui.tabs.TabSmellyFiles;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH;

public class SmellTabbedPaneWindow {
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
                                        JPanel content1  = tabDetectedSmells.GetContent();
                                        content1.setVisible(true);
                                        panelDetectedSmells.setLayout(new java.awt.BorderLayout());
                                        panelDetectedSmells.add(content1);
                                        panelDetectedSmells.validate();

                                        tabSmellyFiles = new TabSmellyFiles();
                                        JPanel content2  = tabSmellyFiles.GetContent();
                                        content2.setVisible(true);
                                        panelSmellyFiles.setLayout(new java.awt.BorderLayout());
                                        panelSmellyFiles.add(content2);
                                        panelSmellyFiles.validate();

                                        tabSmells = new TabSmells();
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
    }

    public JPanel getContent() {
        return panelInspection;
    }
}
