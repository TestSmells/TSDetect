package org.scanl.plugins.tsdetect.ui.controls;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.Arrays;
import java.util.Objects;

public class TableUtil {

    public static void AutoResizeByColumnData(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }

    public static void AutoResizeColumnHeader(int column, JTable table) {
        TableColumn tableColumn = table.getColumnModel().getColumn(column);
        int maxWidth = 0;
        TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();
        TableCellRenderer rendCol = tableColumn.getHeaderRenderer();
        if (rendCol == null) rendCol = rend;
        Component header = rendCol.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, column);
        maxWidth = header.getPreferredSize().width;
        tableColumn.setPreferredWidth(maxWidth);
    }



    public static void SetMouseCursorForImageColumn(JTable table, int... columnIndex){
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                boolean isPresent = Arrays.stream(columnIndex).anyMatch(number -> Objects.equals(number, column));
                if (isPresent) {
                    table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public static void SetMouseCursorForTableHeader(JTable table){
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row == 0 || row == 1) {
                    table.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    table.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public static ImageIcon GetOpenIcon(){
        URL url = TableUtil.class.getResource("/images/open.png");
        ImageIcon openIcon = new ImageIcon(url);
        Image openImage = openIcon.getImage(); // transform it
        openImage = openImage.getScaledInstance(14, 14, Image.SCALE_SMOOTH);
        openIcon = new ImageIcon(openImage);  // transform it back
        return openIcon;
    }
}
