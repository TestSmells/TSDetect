package org.scanl.plugins.tsdetect.model;
import org.scanl.plugins.tsdetect.common.PluginResourceBundle;
import org.scanl.plugins.tsdetect.config.PluginSettings;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IdentifierTableModel extends AbstractTableModel {

	private final String[] columnNames = {
			"Smell Type", "Infected Files", "Infected Methods"
	};

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
		List<SmellType> smellTypes = checkedSmells();
		data = new Object[smellTypes.size()+1][3];
		data[0][0] = "Smell Type";
		data[0][1] = "Infected Files";
		data[0][2] = "Infected Methods";
		for(int i = 1; i<smellTypes.size()+1; i++){
			SmellType sm = smellTypes.get(i-1);
			data[i][0] = PluginResourceBundle.message(PluginResourceBundle.Type.INSPECTION, "INSPECTION.SMELL." + sm.toString() + ".NAME.DISPLAY");
			data[i][1] = classes.get(sm).size();
			int infectedMethods = smellTypeListHashMap.get(sm).size();
			if(infectedMethods==0)
				data[i][2] = null;
			else
				data[i][2] = infectedMethods;
		}
	}

	public List<SmellType> checkedSmells(){
		List<SmellType> smellTypes = new ArrayList<>();
		for(SmellType sm:SmellType.values()){
			if(PluginSettings.GetSetting(sm.toString()))
				smellTypes.add(sm);
		}
		return smellTypes;
	}
}
