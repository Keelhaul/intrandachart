package de.intranda.commons.chart.renderer;

import de.intranda.commons.chart.results.DataRow;
import de.intranda.commons.chart.results.DataTable;

/*************************************************************************************
 * A renderer for printing the {@link DataTable} as HTML-Table
 * 
 * @author Steffen Hankiewicz
 * @version 22.05.2009
 *************************************************************************************/
public class HtmlTableRenderer implements IRenderer {
	private DataTable myDataTable;

	public void setDataTable(DataTable inDataTable) {
		myDataTable = inDataTable;
	}

	public Object getRendering() {
		if (myDataTable == null || myDataTable.getDataRows().size()==0) {
			throw new IllegalStateException("No DataTable set. No rendering possible.");
		}

		if (myDataTable.getDataRows().size()==0){
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<table cellpadding=\"1px\" cellspacing=\"1px\" class=\"standardTable\">");
		
		/* --------------------------------
		 * write table header
		 * --------------------------------*/
		sb.append("<thead><tr class=\"standardTable_Header\">");
		sb.append("<th class=\"standardTable_Header\" />");
		DataRow firstrow = myDataTable.getDataRows().get(0);
		for (int i = 0; i < firstrow.getNumberValues(); i++) {
			sb.append("<th class=\"standardTable_Header\">");
			sb.append(firstrow.getLabel(i));
			sb.append("</th>");
		}
		sb.append("</tr></thead>");
		
		/* --------------------------------
		 * write each row
		 * --------------------------------*/
		for (DataRow row : myDataTable.getDataRows()) {
			sb.append("<tr class=\"standardTable_Row1\">");
			sb.append("<td class=\"standardTable_Column\">");
			sb.append(row.getName());
			sb.append("</td>");

			for (int i = 0; i < row.getNumberValues(); i++) {
				sb.append("<td class=\"standardTable_Column\">");
				sb.append(row.getValue(i));
				sb.append("</td>");
			}

			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

}
