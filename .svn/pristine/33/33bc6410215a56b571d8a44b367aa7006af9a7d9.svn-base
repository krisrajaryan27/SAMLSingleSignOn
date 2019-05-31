package com.talentPool.dynamicReports.utils;

import java.awt.Color;

import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

public class DJStyles {
	private static Style defaultTopHeader;
	private static Style defaultColHeader;
	private static Style defaultRowDetail;
	private static Style defaultExcelRowDetail;
	private static Style noDataStyle;
	private static Style groupByFooterStyle;
	private static Style measureStyle;
	
	public static Style getTopHeaderStyle() {
		if(defaultTopHeader==null){			
			defaultTopHeader= new StyleBuilder(false)
							.setHorizontalAlign(HorizontalAlign.LEFT)
							.setVerticalAlign(VerticalAlign.MIDDLE)
							.setFont(FontStyle.getTopHeaderFont())
							.build();  
		}
		return defaultTopHeader;
	}
	
	public static Style getNoDataStyle(){
		if(noDataStyle==null){
			noDataStyle = new StyleBuilder(false)
							.setFont(Font.VERDANA_MEDIUM)
							.setHorizontalAlign(HorizontalAlign.LEFT)
							.build(); 
		}
		return noDataStyle;
	}
	
	public static Style getDefaultColHeaderStyle(){
		if(defaultColHeader==null){
			defaultColHeader= new StyleBuilder(false)
							.setHorizontalAlign(HorizontalAlign.LEFT)
							.setBorderTop(Border.THIN)
							.setBorderBottom(Border.THIN)
							.setVerticalAlign(VerticalAlign.MIDDLE)
							.setFont(FontStyle.getColHeaderFont())
							.build();  
		}
		return defaultColHeader;
	}
	
	public static Style getDefaultRowStyle(){
		if(defaultRowDetail==null){
			defaultRowDetail= new Style("ROW_DETAIL");
			defaultRowDetail.setHorizontalAlign(HorizontalAlign.LEFT);
			defaultRowDetail.setBorderBottom(Border.THIN);
			defaultRowDetail.setBorderColor(Color.LIGHT_GRAY);
		}
		return defaultRowDetail;
	}
	
	public static Style getDefaultExcelRowStyle(){
		if(defaultExcelRowDetail==null){
			defaultExcelRowDetail= new Style("EXCEL_ROW_DETAIL");
			defaultExcelRowDetail.setHorizontalAlign(HorizontalAlign.LEFT);
		}
		return defaultExcelRowDetail;
	}
	/**
	 * @return the groupByFooterStyle
	 */
	public static Style getGroupByFooterStyle() {
		if(groupByFooterStyle==null){			
			groupByFooterStyle= new StyleBuilder(false)
							.setHorizontalAlign(HorizontalAlign.LEFT)
							.setVerticalAlign(VerticalAlign.MIDDLE)
							.setFont(FontStyle.getGroupByFooterFont())
							.setBorderBottom(Border.PEN_1_POINT)
							.build();  
		}
		return groupByFooterStyle;
	}
	
	public static Style getMeasureStyle(){
		if(measureStyle==null){
			measureStyle =  new StyleBuilder(false)
			.setName("MEASURE STYLE")
			//.setPattern("#,###")
			.setHorizontalAlign(HorizontalAlign.RIGHT)
			.setFont(Font.ARIAL_MEDIUM).build();
		}
		return measureStyle;	
	}
	
}
