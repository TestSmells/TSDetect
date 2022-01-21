package org.scanl.plugins.tsdetect.model;
import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IdentifierTableModel extends AbstractTableModel {

	private final String[] columnNames = {
			"Smell Type", "Infected Files", "Infected Methods"
	};

	private final List<SmellType> smellTypes = Arrays.asList(SmellType.values());
	private Object[][] data;

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public java.lang.Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	public void constructSmellTable(HashMap<SmellType, List<InspectionMethodModel>> smellTypeListHashMap, HashMap<SmellType, List<InspectionClassModel>> classes){
		data = new Object[smellTypeListHashMap.size()+1][3];
		data[0][0] = "Smell Type";
		data[0][1] = "Infected Files";
		data[0][2] = "Infected Methods";
		for(int i = 1; i<smellTypes.size()+1; i++){
			SmellType sm = smellTypes.get(i-1);
			data[i][0] = sm;
			data[i][1] = classes.get(sm).size();
			data[i][2] = smellTypeListHashMap.get(sm).size();
		}
	}
}
