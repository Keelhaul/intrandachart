package de.intranda.commons.chart.results;

import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartDraw {
	public static void main(String[] args) throws IOException {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("JavaWorld", new Integer(75));
		pieDataset.setValue("Other", new Integer(25));

		JFreeChart chart = ChartFactory.createPieChart("Sample Pie Chart", pieDataset, true, false, false);
		chart.setBackgroundPaint(Color.white);
		ChartUtilities.saveChartAsPNG(new File("C:/home/freechart.png"), chart, 1024, 768);
	}
}
