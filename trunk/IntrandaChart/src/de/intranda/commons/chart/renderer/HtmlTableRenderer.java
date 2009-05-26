/*
 * This file is part of the intranda commons charting project.
 * Visit the websites for more information. 
 * 		- http://www.intranda.com 
 * 		- http://code.google.com/p/intrandachart/
 * 
 * Copyright 2009, intranda software.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"?);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"? BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
	private String pattern = "#";
	
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
		
		StringBuilder sb = new StringBuilder();
		sb.append("<table cellpadding=\"1px\" cellspacing=\"1px\" class=\"standardTable\">");
		
		/* --------------------------------
		 * write table header
		 * --------------------------------*/
		sb.append("<thead><tr class=\"standardTable_Header\">");
		sb.append("<th class=\"standardTable_Header\">");
		sb.append(myDataTable.getSubname());
		sb.append("</th class=\"standardTable_Header\">");
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
				sb.append(Util.roundAsString(row.getValue(i), pattern));
				sb.append("</td>");
			}

			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/*************************************************************************************
	 * setter for pattern of number format
	 * @param inPattern to set
	 *************************************************************************************/
	public void setFormatPattern(String inPattern){
		pattern=inPattern;
	}
	
	/*************************************************************************************
	 * getter for datatable
	 * 
	 * @return {@link DataTable}
	 *************************************************************************************/
	public DataTable getDataTable() {
		return myDataTable;
	}
}
