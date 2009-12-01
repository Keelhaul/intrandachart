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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/*************************************************************************************
 * ChartDraw class creates and paints the chart depending on given parameters the value parameters are transfered as
 * {@link DataTable}-Object. width and height have to be set as pixel
 * 
 * @author Karsten Köhler
 * @author Hendrik Söhnholz
 * @author Steffen Hankiewicz
 * @author Andrey Kozhushkov
 * @version 30.11.2009 *
 ************************************************************************************/
public class ChartDraw {

	public enum ChartType {
		BAR, LINE;
	}

	public enum PointStyle {
		CIRCLE, SQUARE;
	}

	private Graphics2D g2d;
	private DataTable dataTable;

	// dimensions of the whole graphic
	private int width;
	private int height;

	// dimensions of the chart
	private int chartWidth;
	private int chartHeight;

	// dimensions of the legend
	private int legendWidth;
	private int legendHeight;

	// padding in the legend
	private static final int LEGENDPADDING = 5;

	/*
	 * total width of all strings shown in the legend including padding and space for the colored box
	 */
	private int totalLegendStringWidth;

	// border values
	private int borderLeft;
	private static final int BORDERRIGHT = 10;
	private int borderBottom;
	private int borderTop;

	// chart bottom (= x-axis)
	private int chartBottom;

	// distance between ticks on x-axis
	private int xGridWidth;
	// distance between ticks on y-axis
	private int yGridWidth;
	// text/numbers for the x and y ticks
	private ArrayList<String> xtickMarks;
	private ArrayList<String> ytickMarks;
	// width of the longest mark on x- and y-axis in pixels
	private int xMaxTickMarkWidth;
	private int yMaxTickMarkWidth;

	// next to last tick on y-axis
	private int yMaxTick;
	// max. y-value shown in the chart
	private int yMaxValue;

	private int nColors;
	private List<Color> chartColors;
	private FontMetrics fm;
	private ChartType chartType;
	private PointStyle pointStyle;

	/************************************************************************************
	 * Instantiates a new chart draw.
	 * 
	 * @param inDataTable the {@link DataTable} (contains the {@link DataRow}-objects)
	 * @param g2d the {@link Graphics2D}-object, where to paint
	 * @param width the width of the image
	 * @param height the height of the image
	 * @param chartType the type of the chart to draw (bar or line)
	 * @param pointStyle the style of points in the legend and line charts (circle or square)
	 * @param customColors optional <code>List</code> of custom <code>Color</code>s
	 ************************************************************************************/
	public ChartDraw(DataTable inDataTable, Graphics2D g2d, int width, int height, ChartType chartType, PointStyle pointStyle,
			List<Color> customColors) {
		this.dataTable = inDataTable;
		this.g2d = g2d;
		this.width = width;
		this.height = height;
		this.chartType = chartType;
		this.pointStyle = pointStyle;
		this.chartColors = customColors;

		if (chartColors == null || chartColors.isEmpty()) {
			// define colors
			chartColors = new ArrayList<Color>();
			chartColors.add(new Color(200, 0, 0)); // red
			chartColors.add(new Color(0, 0, 200)); // blue
			chartColors.add(new Color(250, 220, 50)); // yellow
			chartColors.add(new Color(0, 130, 80)); // green
			chartColors.add(new Color(150, 0, 210)); // violett
			chartColors.add(new Color(240, 140, 0)); // orange
			chartColors.add(new Color(50, 150, 240)); // light blue
			chartColors.add(new Color(70, 40, 0)); // brown
			chartColors.add(new Color(230, 50, 220)); // pink
			chartColors.add(new Color(0, 190, 150)); // mint green
		}

		nColors = chartColors.size();

		if (chartType == null) {
			chartType = ChartType.BAR;
		}

		if (pointStyle == null) {
			pointStyle = PointStyle.SQUARE;
		}

		fm = g2d.getFontMetrics();

	}

	/************************************************************************************
	 * Paint the chart
	 ************************************************************************************/
	public void paint() {
		// this is used to determine the width of strings in pixels
		int w;

		// only show the title if it is non-empty
		if (dataTable.getName().length() > 0) {
			borderTop = 2 * fm.getHeight();
		} else {
			borderTop = 10;
		}

		/*
		 * use the strings from the first DataRow for the labels on the x-axis determine maximum string width and set the
		 * bottom border accordingly
		 */
		xMaxTickMarkWidth = 0;
		DataRow firstRow = dataTable.getDataRows().get(0);
		xtickMarks = new ArrayList<String>(firstRow.getNumberValues());
		for (int i = 0; i < firstRow.getNumberValues(); i++) {
			String shortlabel = firstRow.getShortLabel(i);
			xtickMarks.add(i, shortlabel);
			w = fm.stringWidth(shortlabel);
			if (w > xMaxTickMarkWidth) {
				xMaxTickMarkWidth = w;
			}
		}

		/*
		 * choose ticks on the y-axis determine maximum y-value and set yGridWidth
		 */
		int powerOfTen = (int) Math.pow(10.0, Math.floor(Math.log10(dataTable.getMaxValue())));
		yMaxTick = (int) (Math.floor((dataTable.getMaxValue() / powerOfTen)) * powerOfTen);
		yGridWidth = yMaxTick / 10; /* define y grid width */

		if (yGridWidth >= 50 && yGridWidth < 100) {
			yGridWidth = 100;
		} else if (yGridWidth >= 25 && yGridWidth < 50) {
			yGridWidth = 50;
		} else if (yGridWidth >= 10 && yGridWidth < 25) {
			yGridWidth = 25;
		} else if (yGridWidth >= 5 && yGridWidth < 10) {
			yGridWidth = 10;
		} else if (yGridWidth > 1 && yGridWidth < 5) {
			yGridWidth = 5;
		} else if (yGridWidth <= 1) {
			yGridWidth = 1;
		}

		yMaxValue = yMaxTick + (((int) Math.ceil(dataTable.getMaxValue()) - yMaxTick) / yGridWidth + 1) * yGridWidth;

		// generate markers on the y-axis and determine width of the longest one
		ytickMarks = new ArrayList<String>(yMaxValue / yGridWidth - 1);
		for (int i = 0; i < yMaxValue / yGridWidth - 1; i++) {
			ytickMarks.add(i, "" + (i + 1) * yGridWidth);
		}
		yMaxTickMarkWidth = fm.stringWidth("" + yMaxTick);

		// adjust left border according to text on the y-axis and text at the
		// beginning of the x-axis
		if (yMaxTickMarkWidth > 2 * xMaxTickMarkWidth / 3) {
			borderLeft = yMaxTickMarkWidth;
		} else {
			borderLeft = 2 * xMaxTickMarkWidth / 3;
		}
		borderLeft += 15;

		// compute width of the chart (without the borders)
		chartWidth = width - borderLeft - BORDERRIGHT;

		// set the grid width for the x-axis
		xGridWidth = chartWidth / xtickMarks.size();

		/*
		 * set bottom border depending on the text on the x-axis (rotated by 45 degree or horizontal)
		 */
		if (xMaxTickMarkWidth > xGridWidth / 2) {
			borderBottom = 2 * xMaxTickMarkWidth / 3 + 2 * fm.getHeight();
		} else {
			borderBottom = 2 * fm.getHeight();
		}

		/*
		 * if there is more than one SimpleDataSet a legend is to be shown; determine size of the legend
		 */
		if (dataTable.getDataRows().size() > 1) {
			/*
			 * determine total string width of titles inside legend and set the legend width and height appropriately
			 */
			totalLegendStringWidth = 0;
			for (DataRow row : dataTable.getDataRows()) {
				/*
				 * border on each side of the legend + space for colored boxes + padding between different titles
				 */
				totalLegendStringWidth += LEGENDPADDING + fm.getHeight() + fm.stringWidth(row.getName()) + 2 * LEGENDPADDING;
			}
			if (totalLegendStringWidth > chartWidth) {
				// do line wrapping inside legend
				legendWidth = chartWidth;

				legendHeight = LEGENDPADDING;
				w = 0;
				for (DataRow row : dataTable.getDataRows()) {
					w += LEGENDPADDING + fm.getHeight() + fm.stringWidth(row.getName()) + 2 * LEGENDPADDING;
					if (w > legendWidth) {
						// start a new line
						legendHeight += fm.getHeight();
						w = LEGENDPADDING + fm.getHeight() + fm.stringWidth(row.getName()) + 2 * LEGENDPADDING;
					}
				}
				// add space for the last line
				legendHeight += fm.getHeight() + LEGENDPADDING;
			} else {
				// only a single line inside legend
				legendWidth = totalLegendStringWidth;
				legendHeight = LEGENDPADDING + fm.getHeight() + LEGENDPADDING;
			}

		} else {
			// no legend
			legendWidth = 0;
			legendHeight = 0;
		}

		/* if there should be displayed some label at the x-axis */
		if (showUnitLabel()) {
			legendHeight += LEGENDPADDING + fm.getHeight();
		}

		chartBottom = height - borderBottom - (legendHeight + 2 * LEGENDPADDING);
		chartHeight = chartBottom - borderTop;

		// set background color to white
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);

		// read SimpleDataSets and draw the chart
		for (int d = 0; d < dataTable.getDataRows().size(); d++) {
			DataRow row = dataTable.getDataRows().get(d);

			// draw the bars
			int size = row.getNumberValues();

			switch (chartType) {
			case BAR:
				for (int i = 0; i < size; i++) {
					drawBar(borderLeft + 0.25 * xGridWidth + (d + 0.5) * (0.5 * xGridWidth / dataTable.getDataRows().size()) + i * chartWidth
							/ xtickMarks.size(), 0.5 * xGridWidth / dataTable.getDataRows().size(), row.getValue(i) * chartHeight / yMaxValue - 1,
							getColorByIndex(d));
				}
				break;
			case LINE:
				for (int i = 0; i < size; i++) {
					if (row.isShowPoint()) {
						drawPoint(borderLeft + 0.5 * xGridWidth + i * (width - borderLeft - BORDERRIGHT) / xtickMarks.size(), chartBottom
								- (row.getValue(i) * chartHeight / yMaxValue - 1), getColorByIndex(d), pointStyle);
					}
					if (i > 0) {
						drawLine(borderLeft + 0.5 * xGridWidth + (i - 1) * (width - borderLeft - BORDERRIGHT) / xtickMarks.size(), chartBottom
								- (row.getValue(i - 1) * chartHeight / yMaxValue - 1), borderLeft + 0.5 * xGridWidth + i
								* (width - borderLeft - BORDERRIGHT) / xtickMarks.size(), chartBottom
								- (row.getValue(i) * chartHeight / yMaxValue - 1), 1, getColorByIndex(d), "-");
					}

				}
				break;
			}
		}

		// show meanValues as line in front of bars
		for (int d = 0; d < dataTable.getDataRows().size(); d++) {
			DataRow row = dataTable.getDataRows().get(d);
			if (row.isShowMeanValue()) {
				drawMeanLine(row.getMeanValue() * chartHeight / yMaxValue - 1, getColorByIndex(d));
			}
		}

		if (dataTable.getDataRows().size() > 0) {
			// draw axes and labels
			drawAxis();

			// show title if non-empty
			if (dataTable.getName() != null && dataTable.getName().length() > 0) {
				drawCenteredString(dataTable.getName(), width / 2, borderTop / 2);
			}
		}

		if (dataTable.getDataRows().size() > 1) {
			// show the legend if there's more than one SimpleDataSet
			drawLegend();
		}

		/* if there should be displayed some label at the x-axis */
		if (showUnitLabel()) {
			drawUnitLabel();
		}

	}

	/************************************************************************************
	 * draw legend for chart
	 ************************************************************************************/
	private void drawLegend() {
		// current position inside the legend in pixels
		int entryXPos;
		int entryYPos;

		/* without unit-label, legend ist smaller */
		int legendHeightDependingOnUnitLabel = legendHeight;
		if (showUnitLabel()) {
			legendHeightDependingOnUnitLabel -= LEGENDPADDING;
			legendHeightDependingOnUnitLabel -= fm.getHeight();
		}

		// draw a box around the legend
		g2d.setColor(Color.black);
		g2d.draw(new Rectangle2D.Double(width / 2 - legendWidth / 2, height - legendHeightDependingOnUnitLabel - LEGENDPADDING, legendWidth,
				legendHeightDependingOnUnitLabel));

		// if the text is too long move it to the next line
		entryXPos = width / 2 - legendWidth / 2 + LEGENDPADDING;
		entryYPos = height - legendHeightDependingOnUnitLabel + fm.getHeight() / 2;
		for (int d = 0; d < dataTable.getDataRows().size(); d++) {
			DataRow row = dataTable.getDataRows().get(d);

			// text too long?
			if (entryXPos + fm.getHeight() + fm.stringWidth(row.getName()) + 2 * LEGENDPADDING > width / 2 + legendWidth / 2) {
				// move on to the next line
				entryXPos = width / 2 - legendWidth / 2 + LEGENDPADDING;
				entryYPos += fm.getHeight();
			}

			// draw a colored box
			drawPoint(entryXPos + fm.getHeight() / 2, entryYPos, getColorByIndex(d), pointStyle);
			// g2d.setColor(getColorByIndex(d));
			// g2d.fill(new Rectangle2D.Double(entryXPos + fm.getHeight() / 4, entryYPos - fm.getHeight() / 4, fm.getHeight()
			// / 2, fm.getHeight() / 2));
			entryXPos += fm.getHeight();

			// show the text
			g2d.setColor(Color.black);
			drawLeftAlignedString(row.getName(), entryXPos, entryYPos);

			entryXPos += fm.stringWidth(row.getName()) + 3 * LEGENDPADDING - 1;
		}
	}

	/************************************************************************************
	 * draw unit label for chart
	 ************************************************************************************/
	private void drawUnitLabel() {
		int entryXPos = width / 2;
		int entryYPos = height - legendHeight - LEGENDPADDING + fm.getHeight() / 2;
		// show the text
		g2d.setColor(Color.black);
		drawCenteredString(dataTable.getUnitLabel(), entryXPos, entryYPos);

	}

	/*************************************************************************************
	 * check if unit-Label should be drawn
	 * 
	 * @return boolean true, if label is shown
	 *************************************************************************************/
	private boolean showUnitLabel() {
		return dataTable.getUnitLabel() != null && dataTable.getUnitLabel().length() > 0;
	}

	/*************************************************************************************
	 * Draw bar.
	 * 
	 * @param xpos the x-position
	 * @param width the width of the bar
	 * @param barsize the height of the bar
	 * @param col the color of the bar
	 *************************************************************************************/
	private void drawBar(double xpos, double width, double barsize, Color col) {
		GradientPaint verlauf = new GradientPaint((int) xpos, borderTop, col, (int) xpos, (int) (2.0 * chartHeight), Color.white);

		g2d.setPaint(verlauf);
		g2d.fill(new Rectangle2D.Double(xpos - 0.5 * width, chartBottom - barsize, width, barsize));
	}

	/*************************************************************************************
	 * Draw point.
	 * 
	 * @param xpos the x-position
	 * @param ypos the y-position
	 * @param col the color of the point
	 * @param style the style of the point (circle or square)
	 *************************************************************************************/
	private void drawPoint(double xpos, double ypos, Color col, PointStyle style) {
		if (style == PointStyle.CIRCLE) {
			g2d.setPaint(col);
			g2d.fill(new Ellipse2D.Double(xpos - fm.getHeight() / 4, ypos - fm.getHeight() / 4, fm.getHeight() / 2, fm.getHeight() / 2));
		} else if (style == PointStyle.SQUARE) {
			g2d.setPaint(col);
			g2d.fill(new Rectangle.Double(xpos - fm.getHeight() / 4, ypos - fm.getHeight() / 4, fm.getHeight() / 2, fm.getHeight() / 2));
		}
	}

	/*************************************************************************************
	 * Draw line.
	 * 
	 * @param x1 the x-position of the origin
	 * @param y1 the y-position of the origin
	 * @param x2 the x-position of the destination
	 * @param y2 the y-position of the destination
	 * @param width width of the line
	 * @param col the color of the line
	 * @param style the style of the line
	 *************************************************************************************/
	private void drawLine(double x1, double y1, double x2, double y2, float width, Color col, String style) {
		if (style != null) {
			if (style.equals("-")) {
			} else if (style.equals("--")) {
				float dash1[] = { 10.0f, 2.0f };
				BasicStroke dashed = new BasicStroke(width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
				g2d.setStroke(dashed);
			} else if (style.equals(":")) {

			}
		}
		g2d.setColor(col);
		g2d.draw(new Line2D.Double(x1, y1, x2, y2));
	}

	/************************************************************************************
	 * Draw centered string.
	 * 
	 * @param str the string to show
	 * @param xpos the x-position (middle of string)
	 * @param ypos the y-position
	 ************************************************************************************/
	private void drawCenteredString(String str, double xpos, double ypos) {
		g2d.drawString(str, (int) (xpos - fm.stringWidth(str) / 2.0), (int) (ypos + 0.5 * fm.getAscent() - 1));
	}

	/************************************************************************************
	 * Draw right aligned string.
	 * 
	 * @param str the string to show
	 * @param xpos the x-position (end of string)
	 * @param ypos the y-position
	 ************************************************************************************/
	private void drawRightAlignedString(String str, double xpos, double ypos) {
		g2d.drawString(str, (int) (xpos - fm.stringWidth(str)), (int) (ypos + 0.5 * fm.getAscent() - 1));
	}

	/************************************************************************************
	 * Draw left aligned string.
	 * 
	 * @param str the string to show
	 * @param xpos the x-position (start of string)
	 * @param ypos the y-position
	 ************************************************************************************/
	private void drawLeftAlignedString(String str, double xpos, double ypos) {
		g2d.drawString(str, (int) (xpos), (int) (ypos + 0.5 * fm.getAscent() - 1));
	}

	/************************************************************************************
	 * Draw mean line
	 * 
	 * @param meanValue the mean value as double
	 ************************************************************************************/
	private void drawMeanLine(double meanValue, Color inColor) {
		float dash1[] = { 7.0f };
		BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
		g2d.setColor(inColor);
		g2d.setStroke(dashed);
		g2d.draw(new Line2D.Double(borderLeft, chartBottom - meanValue, width - BORDERRIGHT, chartBottom - meanValue));
	}

	/************************************************************************************
	 * Draw axes
	 ************************************************************************************/
	private void drawAxis() {
		// used for rotating text
		AffineTransform at;

		// holds the number of ticks on x- or y-axis
		int s;

		/*
		 * distance between marker texts on the x-axis (show the text only on every n-th tick); The default is to show it on
		 * each tick.
		 */
		int xMarkerTextDistance = 1;

		g2d.setColor(Color.black);

		// y-axis
		g2d.setStroke(new BasicStroke());
		g2d.draw(new Line2D.Double(borderLeft, chartBottom, borderLeft, borderTop));

		s = ytickMarks.size();
		for (int i = 0; i < s; i++) {
			// grid lines
			float dash1[] = { 2.0f };
			BasicStroke dashed = new BasicStroke(0.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
			g2d.setStroke(dashed);
			g2d.draw(new Line2D.Double(borderLeft, chartBottom - (i + 1) * yGridWidth * chartHeight / yMaxValue, width - BORDERRIGHT, chartBottom
					- (i + 1) * yGridWidth * chartHeight / yMaxValue));

			// ticks and numbers
			g2d.setStroke(new BasicStroke());
			g2d.draw(new Line2D.Double(borderLeft - 5, chartBottom - (i + 1) * yGridWidth * chartHeight / yMaxValue, borderLeft + 5, chartBottom
					- (i + 1) * yGridWidth * chartHeight / yMaxValue));
			drawRightAlignedString(ytickMarks.get(i), borderLeft - fm.getHeight(), chartBottom - (i + 1) * yGridWidth * chartHeight / yMaxValue);
		}

		// x-axis
		g2d.draw(new Line2D.Double(borderLeft, chartBottom, width - BORDERRIGHT, chartBottom));

		xMarkerTextDistance = fm.getHeight() / xGridWidth;
		if (xMarkerTextDistance < 1) {
			// show text on each tick
			xMarkerTextDistance = 1;
		} else {
			/*
			 * not enough room to show text on each tick; multiply distance by 2 to add some free space
			 */
			xMarkerTextDistance *= 2;
		}

		s = xtickMarks.size();
		for (int i = 0; i < s; i++) {
			// ticks
			g2d.draw(new Line2D.Double(borderLeft + 0.5 * xGridWidth + i * (width - borderLeft - BORDERRIGHT) / s, chartBottom + 7, borderLeft + 0.5
					* xGridWidth + i * (width - borderLeft - BORDERRIGHT) / s, chartBottom));

			/*
			 * rotate text by 45 degree if there's not enough space to show it horizontal
			 */
			if (xMaxTickMarkWidth > xGridWidth / 2) {
				if (i % xMarkerTextDistance == 0) {
					at = AffineTransform.getRotateInstance(Math.toRadians(-45), borderLeft + 0.5 * xGridWidth + i
							* (width - borderLeft - BORDERRIGHT) / s, chartBottom + fm.getHeight());
					g2d.transform(at);

					drawRightAlignedString(xtickMarks.get(i), borderLeft + 0.5 * xGridWidth + i * (width - borderLeft - BORDERRIGHT) / s, chartBottom
							+ fm.getHeight());
					at = AffineTransform.getRotateInstance(Math.toRadians(45), borderLeft + 0.5 * xGridWidth + i * (width - borderLeft - BORDERRIGHT)
							/ s, chartBottom + fm.getHeight());
					g2d.transform(at);
				}
			} else {
				drawCenteredString(xtickMarks.get(i), borderLeft + 0.5 * xGridWidth + i * (width - borderLeft - BORDERRIGHT) / s, chartBottom
						+ fm.getHeight());
			}
		}
	}

	/************************************************************************************
	 * Show or hide mean value for specific {@link DataRow}
	 * 
	 * @param dataRowIndex the SimpleDataSet index
	 * @param show given Boolean, if all MeanValues should be shown
	 ************************************************************************************/
	public void showMeanValue(Integer dataRowIndex, Boolean show) {
		dataTable.getDataRows().get(dataRowIndex).setShowMeanValue(show);
	}

	/************************************************************************************
	 * Show or hide all mean values
	 * 
	 * @param show given Boolean, if all MeanValues should be shown
	 ************************************************************************************/
	public void showAllMeanValues(Boolean show) {
		for (DataRow row : dataTable.getDataRows()) {
			row.setShowMeanValue(show);
		}
	}

	/************************************************************************************
	 * Choose color for specific {@link DataRow}
	 * 
	 * @param simpledataSetIndex the SimpleDataSet index
	 * @return the color as {@link Color}
	 ************************************************************************************/
	private Color getColorByIndex(Integer simpledataSetIndex) {
		simpledataSetIndex %= nColors;
		return chartColors.get(simpledataSetIndex);
	}
}
