/**
 * 
 */
package com.talentPool.customReports.djhelper.utils;

import java.util.regex.Matcher;

import com.talentPool.common.utils.RegexUtils;

/**
 * @author PraveenK
 * @since  Feb 17, 2012
 */
public class LayoutManagerUtil {
	public static final String regexForJRExpression = "\\$.\\{[^}]*\\}";

	public static boolean isJRExpression(String value){
		Matcher matcher = RegexUtils.getMatcher(value, regexForJRExpression);
		int count =0;
	    while (matcher.find()) {
    	 	count++;
	    }
	    if(count==1)
	    	return true;
	    else
	    	return false;
	}
	
	public static void main(String[] args) {
		System.out.println(isJRExpression("$p{subtitle}"));
	}
}
