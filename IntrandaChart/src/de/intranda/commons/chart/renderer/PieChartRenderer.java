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

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import de.intranda.commons.chart.results.DataRow;
import de.intranda.commons.chart.results.DataTable;

/*************************************************************************************
 * A renderer for painting the {@link DataTable} as chart
 * 
 * @author Steffen Hankiewicz
 * @version 22.05.2009
 *************************************************************************************/
public class PieChartRenderer implements IRenderer {
	private DataTable myDataTable;
	private int width = 1024;
	private int height = 800;
	private boolean showMeanValues = false;

	public void setDataTable(DataTable inDataTable) {
		myDataTable = inDataTable;
	}

	/*************************************************************************************
	 * get rendered chart as {@link BufferedImage}
	 * 
	 * @return BufferedImage with chart
	 *************************************************************************************/
	public Object getRendering() {
		if (myDataTable == null) {
			throw new IllegalStateException("No DataTable set. No rendering possible.");
		}

		if (myDataTable.getDataRows().size() == 0) {
			return new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		}
		
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		
		DataRow firstrow = myDataTable.getDataRows().get(0);
		for (int i = 0; i < firstrow.getNumberValues(); i++) {
			pieDataset.setValue(firstrow.getLabel(i), firstrow.getValue(i));
		}
		JFreeChart chart = ChartFactory.createPieChart3D("", pieDataset, true, false, false);
		chart.setBackgroundPaint(Color.white);
		
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setForegroundAlpha(0.6f);
		plot.setDepthFactor(0.05);
		plot.setCircular(true);
		
		BufferedImage image = chart.createBufferedImage(width,height);
		return image;
	}

	/*************************************************************************************
	 * setter for image size
	 * 
	 * @param width
	 *            with size to set as width
	 * @param height
	 *            with size to set as height
	 *************************************************************************************/
	public void setSize(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}

	/*************************************************************************************
	 * setter for pattern of number format
	 * 
	 * @param inPattern
	 *            to set
	 *************************************************************************************/
	public void setFormatPattern(String inPattern) {

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
