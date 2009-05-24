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
