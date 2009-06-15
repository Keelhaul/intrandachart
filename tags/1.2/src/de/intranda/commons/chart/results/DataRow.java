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
 * A DataRow object contains one one row of data consisting of
 * a name, the labels, the values (Doubles) 
 * 
 * @author Karsten Köhler
 * @author Hendrik Söhnholz
 * @author Steffen Hankiewicz
 * @version 21.05.2009
 * 
 * @see DataTable
 * *************************************************************************************/
public class DataRow implements Serializable {
	private static final long serialVersionUID = -2323261239936314945L;
	private String name;
	private boolean showMeanValue;
	private final static int maxShortNameLength = 30;

	private List<String> labels;
	private List<Double> values;

	/************************************************************************************
	 * public constructor, the name is set here
	 * 
	 * @param name
	 ************************************************************************************/
	public DataRow(String name) {
		super();
		this.name = name;
		this.showMeanValue = false;
		this.labels = new ArrayList<String>();
		this.values = new ArrayList<Double>();
	}

	/************************************************************************************
	 * add a value to this {@link DataRow}
	 * 
	 * @param inLabel the label for the value
	 * @param inValue the value as a Double
	 ************************************************************************************/
	public void addValue(String inLabel, Double inValue) {
		if (inLabel == null) {
			inLabel = "-";
		}
		if (labels.contains(inLabel)) {
			values.set(labels.indexOf(inLabel), inValue);
		} else {
			labels.add(inLabel);
			values.add(inValue);
		}
	}

	//	/************************************************************************************
	//	 * set a value in the {@link DataRow} by supplying the list index
	//	 * 
	//	 * @param index index of the element to change
	//	 * @param inLabel the label of the element
	//	 * @param inValue the value of the element
	//	 ************************************************************************************/
	//	public void setValue(int index, String inLabel, Double inValue) {
	//		labels.set(index, inLabel);
	//		values.set(index, inValue);
	//	}

	/************************************************************************************
	 * get label of an element
	 * 
	 * @param index list index of the element
	 * @return label of the element
	 ************************************************************************************/
	public String getLabel(int index) {
		if (labels.get(index) == null) {
			return "";
		} else {
			return labels.get(index);
		}
	}

	/************************************************************************************
	 * getter for shortened label
	 * 
	 * @param index for index of label to get
	 * @return name as String
	 ************************************************************************************/
	public String getShortLabel(int index) {
		String myLabel = getLabel(index);
		if (myLabel != null && myLabel.length() > maxShortNameLength) {
			return myLabel.substring(0, maxShortNameLength) + "...";
		} else {
			return myLabel;
		}
	}

	/************************************************************************************
	 * get value of an element
	 * 
	 * @param index list index of the element
	 * @return value of the element
	 ************************************************************************************/
	public Double getValue(int index) {
		return values.get(index);
	}

	/************************************************************************************
	 * get value of an element
	 * 
	 * @param index list index of the element
	 * @return value of the element
	 ************************************************************************************/
	public Double getValue(String inLabel) {
		if (labels.contains(inLabel)) {
			return values.get(labels.indexOf(inLabel));
		} else {
			return new Double(0);
		}
	}

	/************************************************************************************
	 * get number of values stored in this {@link DataRow}
	 * 
	 * @return number of values
	 ************************************************************************************/
	public Integer getNumberValues() {
		return values.size();
	}

	/************************************************************************************
	 * get maximum value of this {@link DataRow}
	 * 
	 * @return maximum y-value
	 ************************************************************************************/
	public Double getMaxValue() {
		double max = 0;
		for (Double d : values) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	/************************************************************************************
	 * calculate mean value
	 * 
	 * @return mean value as double
	 ************************************************************************************/
	public Double getMeanValue() {
		double sum = 0;
		for (Double d : values) {
			sum += d;
		}
		return sum / getNumberValues();
	}

	/************************************************************************************
	 * getter for name
	 * 
	 * @return name as String
	 ************************************************************************************/
	public String getName() {
		if (name == null) {
			return "";
		} else {
			return name;
		}
	}

	/**************************************************************************************
	 * Setter for name
	 *
	 * @param name the name to set
	 **************************************************************************************/
	public void setName(String name) {
		this.name = name;
	}

	/*************************************************************************************
	 * Getter for showMeanValue
	 *
	 * @return the showMeanValue
	 *************************************************************************************/
	public boolean isShowMeanValue() {
		return showMeanValue;
	}

	/**************************************************************************************
	 * Setter for showMeanValue
	 *
	 * @param showMeanValue the showMeanValue to set
	 **************************************************************************************/
	public void setShowMeanValue(boolean showMeanValue) {
		this.showMeanValue = showMeanValue;
	}

	public Boolean isContainsLabel(String inLabel) {
		return labels.contains(inLabel);
	}

	public List<String> getLabels() {
		return labels;
	}

}
