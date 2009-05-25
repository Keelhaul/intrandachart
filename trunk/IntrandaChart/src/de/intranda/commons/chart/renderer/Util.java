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

import java.text.DecimalFormat;

/*************************************************************************************
 * A utility class
 * 
 * @author Steffen Hankiewicz
 * @version 24.05.2009
 *************************************************************************************/
public class Util {

	public static void main(String[] args) {
		double bla = 135.54654;
		System.out.println(roundAsString(bla, "#.##"));
	}

	/*************************************************************************************
	 * method for rounding number to given digits
	 * 
	 * @param inValue
	 *            the value to round
	 * @param pattern
	 *            to use for number format
	 * @return number formatted using the pattern as String
	 *************************************************************************************/
	public static String roundAsString(double inValue, String inPattern) {
		DecimalFormat twoDForm = new DecimalFormat(inPattern);
		return String.valueOf(twoDForm.format(inValue));

	}

}
