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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.intranda.commons.chart.results.ChartDraw;
import de.intranda.commons.chart.results.DataTable;

/*************************************************************************************
 * A renderer for painting the {@link DataTable} as chart
 * 
 * @author Steffen Hankiewicz
 * @version 22.05.2009
 *************************************************************************************/
public class ChartRenderer implements IRenderer {
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
		if (myDataTable == null || myDataTable.getDataRows().size()==0) {
			throw new IllegalStateException("No DataTable set. No rendering possible.");
		}

		if (myDataTable.getDataRows().size()==0){
			return "";
		}
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		ChartDraw chartDraw = new ChartDraw(myDataTable, g2d, width, height);
		chartDraw.showAllMeanValues(showMeanValues);
		chartDraw.paint();
		return image;
	}

	
	/*************************************************************************************
	 * setter for image size
	 * 
	 * @param width with size to set as width
	 * @param heigth with size to set as height
	 *************************************************************************************/
	public void setSize(Integer width, Integer height) {
		this.width = width;
		this.height = height;
	}

	
	/*************************************************************************************
	 * setter for showing mean values
	 * 
	 * @param Boolean for showing or hiding mean values in chart
	 *************************************************************************************/
	public void setShowMeanValues(Boolean inShowMeanValues) {
		showMeanValues = inShowMeanValues;
	}
}
