package de.intranda.commons.chart.renderer;

import de.intranda.commons.chart.results.DataTable;

/*************************************************************************************
 * A renderer interface for very different targets, 
 * where the statistical results should be rendered to
 * 
 * @author Steffen Hankiewicz
 * @version 22.05.2009
 *************************************************************************************/
public interface IRenderer {
	
	public void setDataTable(DataTable inDataTable);
	
	public Object getRendering();
		
}
