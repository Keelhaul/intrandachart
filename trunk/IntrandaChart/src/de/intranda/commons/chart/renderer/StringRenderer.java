package de.intranda.commons.chart.renderer;

import de.intranda.commons.chart.results.DataRow;
import de.intranda.commons.chart.results.DataTable;

/*************************************************************************************
 * A renderer for printing the {@link DataTable} to a string (e.g. console)
 * 
 * @author Steffen Hankiewicz
 * @version 22.05.2009
 *************************************************************************************/
public class StringRenderer implements IRenderer {
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
		 * write header
		 * --------------------------------*/
		StringBuilder sb = new StringBuilder();
		sb.append(" | ");
		DataRow firstrow = myDataTable.getDataRows().get(0);
		for (int i = 0; i < firstrow.getNumberValues(); i++) {
			sb.append(firstrow.getLabel(i));
			sb.append(" | ");
		}
		sb.append("\n");

		/* --------------------------------
		 * write each row
		 * --------------------------------*/
		for (DataRow row : myDataTable.getDataRows()) {
			sb.append(row.getName());
			sb.append(" | ");

			for (int i = 0; i < row.getNumberValues(); i++) {
				sb.append(row.getValue(i));
				sb.append(" | ");
			}

			sb.append("\n");
		}
		return sb.toString();
	}

}
