package org.scanl.plugins.tsdetect.ui.controls;

import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;


public class CustomTable extends JTable {

    public void AutoResizeByColumnData() {
        TableUtil.AutoResizeByColumnData(this);
    }

    public void AutoResizeColumnHeaders(int... columnIndex) {
        for (int index : columnIndex) {
            TableUtil.AutoResizeColumnHeader(index, this);
        }
    }

    public void SetMouseCursorForImageColumns(int... columnIndex) {
        TableUtil.SetMouseCursorForImageColumn(this, columnIndex);
    }

    public void SetMouseCursorForTableHeader() {
        TableUtil.SetMouseCursorForTableHeader(this);
    }

    public void EnableColumnSort(int columnIndex, SortOrder sortOrder){
        TableRowFilterSupport.forTable(this)
                .searchable(true)
                .useTableRenderers(true)
                .apply();

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.getModel());
        this.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(columnIndex, sortOrder));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
}
