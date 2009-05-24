package de.intranda.commons.chart.renderer;

import de.intranda.commons.chart.results.DataRow;
import de.intranda.commons.chart.results.DataTable;

/*************************************************************************************
 * A renderer for printing the {@link DataTable} as copy&paste-Java-Code to a string (e.g. console)
 * 
 * @author Steffen Hankiewicz
 * @version 23.05.2009
 *************************************************************************************/
public class StringDebugRenderer implements IRenderer {
	private DataTable myDataTable;

	public void setDataTable(DataTable inDataTable) {
		myDataTable = inDataTable;
	}

	public Object getRendering() {
		if (myDataTable == null) {
			throw new IllegalStateException("No DataTable set. No rendering possible.");
		}

		if (myDataTable.getDataRows().size()==0){
			return "";
		}
		
		/* --------------------------------
		 * write each row as java code
		 * --------------------------------*/
		StringBuilder sb = new StringBuilder();
		for (DataRow row : myDataTable.getDataRows()) {
			sb.append("DataRow row = new DataRow(\"" + row.getName() + "\");");
			sb.append("\n");
			for (int i = 0; i < row.getNumberValues(); i++) {
				sb.append("row.addValue(\"" + row.getLabel(i) + "\", " + row.getValue(i) + ");");
				sb.append("\n");
			}
			
		}
		return sb.toString();
	}

}
