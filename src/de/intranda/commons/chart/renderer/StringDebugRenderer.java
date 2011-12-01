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

	/*************************************************************************************
	 * setter for pattern of number format
	 * @param inPattern to set
	 *************************************************************************************/
	public void setFormatPattern(String inPattern){
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
