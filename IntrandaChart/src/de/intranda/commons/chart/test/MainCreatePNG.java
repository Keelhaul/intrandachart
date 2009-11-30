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
package de.intranda.commons.chart.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

import de.intranda.commons.chart.renderer.StringRenderer;
import de.intranda.commons.chart.results.ChartDraw;
import de.intranda.commons.chart.results.DataRow;
import de.intranda.commons.chart.results.DataTable;
import de.intranda.commons.chart.results.ChartDraw.ChartType;
import de.intranda.commons.chart.results.ChartDraw.PointStyle;

/*************************************************************************************
 * Draw a chart and save it as a PNG image
 * 
 * @author Karsten Köhler
 * @author Hendrik Söhnholz
 * @author Steffen Hankiewicz
 * @author Andrey Kozhushkov
 * @version 30.11.2009
 *************************************************************************************/
public class MainCreatePNG {

	/**
	 * sample main programm
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		DataTable dt1 = getSampleDataTable1();
		DataTable dt2 = dt1.getDataTableInverted();

		StringRenderer sr = new StringRenderer();
		sr.setDataTable(dt1);
		System.out.println(sr.getRendering());

		StringRenderer sr1 = new StringRenderer();
		sr1.setDataTable(dt2);
		System.out.println(sr1.getRendering());

		createChart(getSampleDataTable1(), "image1.png", 1024, 768);
		createChart(getSampleDataTable2(), "image2.png", 1024, 768);
		createChart(getSampleDataTable3(), "image3.png", 1024, 768);
		createChart(getSampleDataTable4(), "image4.png", 1024, 768);
		createChart(getSampleDataTable5(), "image5.png", 1024, 768);
		createChart(getSampleDataTable6(), "image6.png", 1024, 768);
		createChart(getSampleDataTable7(), "image7.png", 1024, 768);
		createChart(getSampleDataTable8(), "image8.png", 1024, 768);
		createChart(getSampleDataTable9(), "image9.png", 1920, 1200);
		createPieChart("image10.png", 1024, 768);

		System.out.println("fertig");
	}

	/************************************************************************************
	 * create sample pie chart
	 * 
	 * @param width width of the image
	 * @param height height of the image
	 * @throws IOException
	 ************************************************************************************/
	private static void createPieChart(String fileName, int width, int height) throws IOException {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		pieDataset.setValue("Java", new Integer(75));
		pieDataset.setValue("Other", new Integer(25));

		JFreeChart chart = ChartFactory.createPieChart3D("Sample Pie Chart", pieDataset, true, false, false);
		chart.setBackgroundPaint(Color.white);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setForegroundAlpha(0.6f);
		plot.setDepthFactor(0.05);
		plot.setCircular(true);

		ChartUtilities.saveChartAsPNG(new File(fileName), chart, width, height);
	}

	/************************************************************************************
	 * create one image with given filename, given datatable etc.
	 * 
	 * @param g2d instance of {@link Graphics2D}
	 * @param width width of the image
	 * @param height height of the image
	 * @throws IOException
	 ************************************************************************************/
	private static void createChart(DataTable dataTable, String fileName, int width, int height) throws IOException {
		BufferedImage image = generateImage(dataTable, width, height);
		File outputfile = new File(fileName);
		ImageIO.write(image, "png", outputfile);

		// FileOutputStream fo = new FileOutputStream("imageAsStream.png");
		// ImageIO.write(image, "png", fo);
		// fo.flush();
		// fo.close();
	}

	/************************************************************************************
	 * Draw a chart in a {@link Graphics2D} object
	 * 
	 * @param dataTable given DataTable
	 * @param width width of the image
	 * @param height height of the image
	 * @return generated chart as BufferedImage
	 ************************************************************************************/
	private static BufferedImage generateImage(DataTable dataTable, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		ChartDraw chartDraw = new ChartDraw(dataTable, g2d, width, height, ChartType.LINE, PointStyle.CIRCLE);
		chartDraw.showAllMeanValues(true);
		chartDraw.paint();
		return image;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable1() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		dataTable.addDataRow(getSampleRow1());
		dataTable.addDataRow(getSampleRow2());
		dataTable.addDataRow(getSampleRow3());
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable2() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		dataTable.addDataRow(getSampleRow1());
		dataTable.addDataRow(getSampleRow2());
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable3() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		dataTable.addDataRow(getSampleRow1());
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable4() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		dataTable.addDataRow(getSampleRow4());
		dataTable.addDataRow(getSampleRow5());
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable5() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		dataTable.addDataRow(getSampleRow4());
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable6() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		dataTable.addDataRow(getSampleRow5());
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable7() {
		String title = new String("Some sample data");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		DataRow row = new DataRow("2008");
		row.addValue(new String("Februar"), new Double(10.0));
		dataTable.addDataRow(row);
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable8() {
		String title = new String("Some sample data 8");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");
		DataRow row = new DataRow("2010");

		for (int i = 4; i <= 100; i++) {
			row.addValue(new String("titel nummer " + i), new Double(i));
		}
		dataTable.addDataRow(row);
		return dataTable;
	}

	/************************************************************************************
	 * helper method for some testings of the statistics result renderers
	 * 
	 * @return the sample results as {@link DataTable}
	 ************************************************************************************/
	private static DataTable getSampleDataTable9() {
		String title = new String("Some sample data 8");
		DataTable dataTable = new DataTable(title);
		dataTable.setUnitLabel("some unit");

		DataRow row = new DataRow("null");
		row.addValue("Anlegen eines Prozesses (auf Bandebene)", 6.0);
		row.addValue("Archivierung", 7.0);
		row.addValue("Auslieferung", 4.0);
		row.addValue("Automatische Generierung der SICI", 19.0);
		row.addValue("Bandweise Endkontrolle und Freigabe aller Metadaten", 18.0);
		row.addValue("Bearbeitung der bibliographischen Metadaten auf Artikelebene (Transliterierung / Übersetzung)", 15.0);
		row.addValue("Bestandsaufnahme der gedruckten Version", 2.0);
		row.addValue("bibliographische Arbeiten (GBV, ZDB, Buchbestellung)", 0.0);
		row.addValue("Bibliographische Aufnahme", 1.0);
		row.addValue("Bibliothekarische Aufnahme für den lokalen OPAC", 4.0);
		row.addValue("Brennen", 8.0);
		row.addValue("Buchrückgabe", 5.0);
		row.addValue("Datenbanktausch (alle Bände)", 11.0);
		row.addValue("Datenübernahme - RezensionsZeitschriftenDB", 4.0);
		row.addValue("Erfassung", 1.0);
		row
				.addValue(
						"Erfassung der Struktur-Metadaten (Erfassung der Seitenpaginierung, Nacherfassung fehlender Strukturen, Erstellung Metadaten-Strukturbaum)",
						17.0);
		row.addValue("Erstellung des MasterImageSets (automatisch)", 4.0);
		row.addValue("Export", 6.0);
		row.addValue("Export der Daten in DMS", 6.0);
		row.addValue("Imagenachbearbeitung", 10.0);
		row.addValue("ImageOptimierung", 3.0);
		row.addValue("Images einspielen", 6.0);
		row.addValue("Images importieren", 1.0);
		row.addValue("Import / Lieferung der russischen Metadaten", 17.0);
		row.addValue("Import der Zentralblatt-Metadaten", 12.0);
		row.addValue("Import DMS", 7.0);
		row.addValue("Import in das DMS", 10.0);
		row.addValue("Import in das DMS (GDZ)", 6.0);
		row.addValue("Import in die lokalen DMS bzw. Repositories", 21.0);
		row.addValue("Korrektur Import Zentralblatt", 17.0);
		row.addValue("Korrekturlesen", 8.0);
		row.addValue("Lizenzeinwerbung", 1.0);
		row.addValue("Nachweis in Katalogen aktualisieren", 12.0);
		row.addValue("Normierung der Zeitschriftentitel", 5.0);
		row.addValue("Paginierung erstellen", 2.0);
		row.addValue("PDF Generierung", 6.0);
		row.addValue("Prüfung der Vollständigkeit der Zentralblattdaten auf Artikelebene", 11.0);
		row.addValue("Prüfung und Korrektur der bibliographischen Daten im Tool", 13.0);
		row.addValue("Qualitätskontrolle", 9.0);
		row.addValue("Qualitätskontrolle Images", 2.0);
		row.addValue("Scannen", 2.0);
		row.addValue("Scannen der Images", 8.0);
		row.addValue("Schlußkontrolle", 7.0);
		row.addValue("Struktur- und Metadaten", 6.0);
		row.addValue("Struktur- und Metadatenerfassung", 4.0);
		row.addValue("Strukturdatenerfassung", 7.0);
		row.addValue("Tiff-Header erstellen", 7.0);
		row.addValue("Volltext und PDF-Erzeugung", 5.0);
		row.addValue("Volltextgenerierung", 5.0);
		row.addValue("ZDB-Aufnahme", 3.0);

		dataTable.addDataRow(row);
		return dataTable;
	}

	/************************************************************************************
	 * Sample row 1
	 ************************************************************************************/
	private static DataRow getSampleRow1() {
		DataRow row = new DataRow("2007");
		row.setShowPoint(false);
		row.addValue(new String("Januar"), new Double(5.0));
		row.addValue(new String("Februar"), new Double(10.0));
		row.addValue(new String("März"), new Double(21.7));
		row.addValue(new String("April"), new Double(42.1));
		row.addValue(new String("Mai"), new Double(342.7));
		row.addValue(new String("Juni"), new Double(211.3));
		row.addValue(new String("Juli"), new Double(121.7));
		row.addValue(new String("August"), new Double(42.1));
		row.addValue(new String("September"), new Double(252.7));
		row.addValue(new String("Oktober"), new Double(141.3));
		row.addValue(new String("November"), new Double(132.7));
		row.addValue(new String("Dezember"), new Double(224.1));
		return row;
	}

	/************************************************************************************
	 * Sample row 2
	 ************************************************************************************/
	private static DataRow getSampleRow2() {
		DataRow row = new DataRow("2008");
		row.addValue(new String("Januar"), new Double(38.4));
		row.addValue(new String("Februar"), new Double(10.0));
		row.addValue(new String("März"), new Double(28.7));
		row.addValue(new String("April"), new Double(82.1));
		row.addValue(new String("Mai"), new Double(242.7));
		row.addValue(new String("Juni"), new Double(289.3));
		row.addValue(new String("Juli"), new Double(223.7));
		row.addValue(new String("August"), new Double(52.1));
		row.addValue(new String("September"), new Double(162.7));
		row.addValue(new String("Oktober"), new Double(140.3));
		row.addValue(new String("November"), new Double(119.7));
		row.addValue(new String("Dezember"), new Double(424.1));
		return row;
	}

	/************************************************************************************
	 * Sample row 3
	 ************************************************************************************/
	private static DataRow getSampleRow3() {
		DataRow row = new DataRow("2009");
		row.addValue(new String("Januar"), new Double(80.0));
		row.addValue(new String("Februar"), new Double(100.0));
		row.addValue(new String("März"), new Double(201.7));
		row.addValue(new String("April"), new Double(402.1));
		row.addValue(new String("Mai"), new Double(42.7));
		row.addValue(new String("Juni"), new Double(111.3));
		row.addValue(new String("Juli"), new Double(181.7));
		row.addValue(new String("August"), new Double(152.1));
		row.addValue(new String("September"), new Double(52.7));
		row.addValue(new String("Oktober"), new Double(191.3));
		row.addValue(new String("November"), new Double(112.7));
		row.addValue(new String("Dezember"), new Double(324.1));
		return row;
	}

	/************************************************************************************
	 * Sample row 4
	 ************************************************************************************/
	private static DataRow getSampleRow4() {
		DataRow row = new DataRow("2010");
		row.addValue(new String("Januar"), new Double(5.0));
		row.addValue(new String("Februar"), new Double(10.0));
		return row;
	}

	/************************************************************************************
	 * Sample row 5
	 ************************************************************************************/
	private static DataRow getSampleRow5() {
		DataRow row = new DataRow("2011");
		row.addValue(new String("Januar"), new Double(1.83));
		row.addValue(new String("Februar"), new Double(40.9));
		return row;
	}

}
