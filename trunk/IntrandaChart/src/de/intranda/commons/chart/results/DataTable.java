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
package de.intranda.commons.chart.results;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*************************************************************************************
 * A DataTable object holds all the information needed to collect multiple
 * {@link DataRow} for rendering them as chart or table
 * 
 * A DataTable consists of - a name - a list of {@link DataRow}
 * 
 * @author Karsten Köhler
 * @author Hendrik Söhnholz
 * @author Steffen Hankiewicz
 * @version 21.05.2009
 * 
 * @see DataRow
 *************************************************************************************/
public class DataTable implements Serializable {
	private static final long serialVersionUID = -6649337945039135394L;
	private String name;
	private String subname;
	private List<DataRow> dataRows;
	private boolean showableInTable = true;
	private boolean showableInChart = true;

	/************************************************************************************
	 * public constructor, the name is set here
	 * 
	 * @param title
	 *            the title to set
	 ************************************************************************************/
	public DataTable(String title) {
		super();
		this.name = title;
		this.subname = "";
		dataRows = new ArrayList<DataRow>();
	}

	/************************************************************************************
	 * add {@link DataRow} to list
	 * 
	 * @param newRow
	 *            the {@link DataRow} to add
	 ************************************************************************************/
	public void addDataRow(DataRow newRow) {
		if (dataRows.size() > 0) {
			/* fill missing columns in other rows */
			for (DataRow row : dataRows) {
				for (String label : newRow.getLabels()) {
//					System.out.println("label coming from row " + row.getName() + ": " + label);
					if (!row.isContainsLabel(label)) {
						row.addValue(label, new Double(0));
					}
				}
			}

			/* fill missing columns in new row */
			DataRow tempRow = new DataRow(newRow.getName());
			tempRow.setShowMeanValue(newRow.isShowMeanValue());
			for (String label : dataRows.get(0).getLabels()) {
//				System.out.println("label check: " + label);
				tempRow.addValue(label, newRow.getValue(label));
			}
			dataRows.add(tempRow);
		} else {
			dataRows.add(newRow);
		}
	}

	/************************************************************************************
	 * get global maximum value (for y-axis)
	 * 
	 * @return maxValue
	 ************************************************************************************/
	public Double getMaxValue() {
		double max = 0;
		for (DataRow row : dataRows) {
			double temp = row.getMaxValue();
			if (temp > max) {
				max = temp;
			}
		}
		return max;
	}

	/************************************************************************************
	 * calculate mean value of all DataRows
	 * 
	 * @return mean value as Double
	 ************************************************************************************/
	public Double getMeanValue() {
		double sum = 0;
		for (DataRow dr : dataRows) {
			sum += dr.getMeanValue();
		}
		return sum / dataRows.size();
	}

	/************************************************************************************
	 * getter for name
	 * 
	 * @return name as string
	 ************************************************************************************/
	public String getName() {
		return name;
	}

	/************************************************************************************
	 * setter for name
	 * 
	 * @param name
	 *            as string
	 ************************************************************************************/
	public void setName(String name) {
		this.name = name;
	}

	/************************************************************************************
	 * getter for subname
	 * 
	 * @return subname as string
	 ************************************************************************************/
	public String getSubname() {
		return subname;
	}

	/************************************************************************************
	 * setter for subname
	 * 
	 * @param subname
	 *            as string
	 ************************************************************************************/
	public void setSubname(String subname) {
		this.subname = subname;
	}

	/************************************************************************************
	 * getter for all DataRows
	 * 
	 * @return list of {@link DataRow}
	 ************************************************************************************/
	public List<DataRow> getDataRows() {
		return dataRows;
	}

	/************************************************************************************
	 * getter for size of datarow list
	 * 
	 * @return number of dataRows
	 ************************************************************************************/
	public int getDataRowsSize() {
		if (dataRows == null) {
			return 0;
		} else {
			return dataRows.size();
		}
	}

	/************************************************************************************
	 * getter current data table as inverted table for different renderings
	 * 
	 * @return {@link DataTable} as inverted table
	 ************************************************************************************/
	public DataTable getDataTableInverted() {
		if (getDataRowsSize() == 0)
			return this;
		DataTable dt = new DataTable(this.getName());

		System.out.println(this.getDataRows().size());

		for (int i = 0; i < this.getDataRows().get(0).getNumberValues(); i++) {
			String label = this.getDataRows().get(0).getLabel(i);
			DataRow dr = new DataRow(label);

			for (int j = 0; j < getDataRowsSize(); j++) {
				DataRow row = this.getDataRows().get(j);
				dr.addValue(row.getName(), row.getValue(i));
			}
			dt.addDataRow(dr);
		}

		dt.setShowableInChart(this.isShowableInChart());
		dt.setShowableInTable(this.isShowableInTable());
		dt.setSubname(this.getSubname());
		return dt;
	}

	/************************************************************************************
	 * setter for boolean if datatable ist showable in graph
	 * 
	 * @param showableInChart
	 ************************************************************************************/
	public void setShowableInChart(boolean showableInChart) {
		this.showableInChart = showableInChart;
	}

	/************************************************************************************
	 * getter for showableInChart
	 * 
	 * @return if datatable is showableInGraph in Chart
	 ************************************************************************************/
	public boolean isShowableInChart() {
		return showableInChart;
	}

	/************************************************************************************
	 * setter for boolean if datatable ist showable in table
	 * 
	 * @param showableInTable
	 ************************************************************************************/
	public void setShowableInTable(boolean showableInTable) {
		this.showableInTable = showableInTable;
	}

	/************************************************************************************
	 * getter for showableInTable
	 * 
	 * @return if datatable is showableInGraph in table
	 ************************************************************************************/
	public boolean isShowableInTable() {
		return showableInTable;
	}
}
