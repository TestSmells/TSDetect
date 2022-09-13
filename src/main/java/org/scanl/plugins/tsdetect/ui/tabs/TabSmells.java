package org.scanl.plugins.tsdetect.ui.tabs;

import javax.swing.*;

public class TabSmells  implements TabContent  {
    private JPanel panelMain;
    private JScrollPane panelTable;
    private JTable tableSmell;

    @Override
    public JPanel GetContent() {
        return panelMain;
    }
}
