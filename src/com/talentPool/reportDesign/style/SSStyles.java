/**
 * 
 */
package com.talentPool.reportDesign.style;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;


/**
 * @author Ajeet
 *
 */
public class SSStyles {
	
	public static Font titleFont(Workbook wb) {
		Font fontBold = wb.createFont();
		fontBold.setFontHeightInPoints((short) 12);
		fontBold.setFontName("Arial");
		fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		return fontBold;
	}
	
	public static Font boldFont(Workbook wb) {
		Font fontBold = wb.createFont();
		fontBold.setFontHeightInPoints((short) 9);
		fontBold.setFontName("Arial");
		fontBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		return fontBold;
	}
	
	public static Font normalFont(Workbook wb) {
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName("Arial");
		return font;
	}
	
	public static CellStyle cellStyleDate(Workbook wb) {
		CellStyle cellStyleDate = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat(("d-mmm-yy")));
		cellStyleDate.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleDate.setFont(normalFont(wb));
		return cellStyleDate;
	}
	
	public static CellStyle cellStyleTime(Workbook wb) {
		CellStyle cellStyleDate = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat(("h:mm AM/PM")));
		cellStyleDate.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleDate.setFont(normalFont(wb));
		return cellStyleDate;
	}
	
	public static CellStyle cellStyleNormal(Workbook wb) {
		CellStyle cellStyleNormal = wb.createCellStyle();
		cellStyleNormal.setFont(normalFont(wb));
		return cellStyleNormal;
	}
	
	public static CellStyle cellStyleCriteria(Workbook wb) {
		CellStyle cellStyleCriteria = wb.createCellStyle();
		cellStyleCriteria.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleCriteria.setFont(normalFont(wb));
		return cellStyleCriteria;
	}
	
	public static CellStyle cellStyleBold(Workbook wb) {
		CellStyle cellStyleBold = wb.createCellStyle();
		cellStyleBold.setFont(SSStyles.boldFont(wb));
		return cellStyleBold;
	}
	
	public static CellStyle cellStyleTitle(Workbook wb) {
		CellStyle cellStyleTitle = wb.createCellStyle();
		cellStyleTitle.setFont(SSStyles.titleFont(wb));
		cellStyleTitle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		return cellStyleTitle;
	}

	public static void deleteAllExcelData(Sheet sheet) {
		Iterator rowItr = sheet.rowIterator();
		while (rowItr.hasNext()) {
			Row row = (Row) rowItr.next();
			Iterator cellItr = row.cellIterator();
			while (cellItr.hasNext()) {
				Cell cell = (Cell) cellItr.next();
				cell.setCellValue("");
			}
		}		
	}
	
	public static String nvl(String val) {
		return (val == null) ? "" : val;
	}
}
