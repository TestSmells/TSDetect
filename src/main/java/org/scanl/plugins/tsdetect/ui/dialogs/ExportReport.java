package org.scanl.plugins.tsdetect.ui.dialogs;

import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.model.ReportItem;
import org.scanl.plugins.tsdetect.model.ReportType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExportReport extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<ReportItem> comboBoxReportType;
    private JLabel labelReportType;

    public ExportReport(Frame owner) {
        super(owner);
        setContentPane(contentPane);
        setModal(true);
        setUndecorated(false);
        setAlwaysOnTop(true);
        setTitle(PluginResourceBundle.message(PluginResourceBundle.Type.UI, ("DIALOG.REPORT.TITLE.TEXT")));
        getRootPane().setDefaultButton(buttonOK);
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width) / 2 - getWidth() / 2, (Toolkit.getDefaultToolkit().getScreenSize().height) / 2 - getHeight() / 2);
        InitializeComboBox();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void InitializeComboBox() {
        comboBoxReportType.addItem(new ReportItem(ReportType.CSV_RAW));
        comboBoxReportType.addItem(new ReportItem(ReportType.PIE_SMELL_DISTRIBUTION));
    }
}
