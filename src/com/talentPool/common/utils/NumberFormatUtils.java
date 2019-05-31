package com.talentPool.common.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * @author PraveenK
 * @since  Jan 10, 2011
 */
public class NumberFormatUtils {
	public static final String commonFormat = "#,###";
	
	public static String format(double number,String format) {
		DecimalFormat decFormat = new DecimalFormat(format);
		return decFormat.format(number);
	}
	
	public static double parse(String number,String format) throws ParseException {
		DecimalFormat decFormat = new DecimalFormat(format);
		return decFormat.parse(number).doubleValue();
	}
}
