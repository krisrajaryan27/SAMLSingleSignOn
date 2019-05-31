/**
 * 
 */
package com.talentPool.dynamicReports.utils;

import ar.com.fdvs.dj.domain.constants.Font;

/**
 * @author Ajeet
 *
 */
public class FontStyle {
	private static Font defaultTopHeader;
	private static Font defaultColHeader;
	private static Font groupByFooter;
	
	public static Font getTopHeaderFont(){
		if(defaultTopHeader==null)
			setTopHeaderFont();
		return defaultTopHeader;
	}
	
	public static Font getColHeaderFont(){
		if(defaultColHeader==null)
			setColHeaderFont();
		return defaultColHeader;
	}
	
	private static void setTopHeaderFont(){ 
		defaultTopHeader = Font.VERDANA_BIG;
		defaultTopHeader.setFontSize(18);
		defaultTopHeader.setBold(true);
	}
	
	private static void setColHeaderFont(){ 
		defaultColHeader = Font.VERDANA_BIG;
		defaultColHeader.setFontSize(10);
		defaultColHeader.setBold(true);
	}
	
	/**
	 * @return the groupByFooter
	 */
	public static Font getGroupByFooterFont() {
		if(groupByFooter==null)
			setGroupByFooterFont();
		return groupByFooter;
	}
	
	public static void setGroupByFooterFont() {
		groupByFooter = Font.VERDANA_BIG;
		groupByFooter.setFontSize(10);
		groupByFooter.setBold(true);
	}
}
