package org.scanl.plugins.tsdetect.ui.tabs;

import javax.swing.*;

public class TabDetectedSmells implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelTable;
    private JTree treeSmells;

    @Override
    public JPanel GetContent() {
        return panelMain;
    }
}
