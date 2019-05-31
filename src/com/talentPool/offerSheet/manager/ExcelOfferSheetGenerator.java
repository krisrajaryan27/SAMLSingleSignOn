/**
 * 
 */
package com.talentPool.offerSheet.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateData;
import com.talentPool.offerSheet.dataobject.OfferSheetTemplateVariable;
import com.talentPool.offerSheet.utils.OfferSheetUtils;

/**
 * @author pallavi
 *
 */
public class ExcelOfferSheetGenerator extends OfferSheetGenerator {
	public void generateOfferSheet(OfferSheetTemplateData templateData, List<OfferSheetTemplateVariable> templateVariables, 
			String destinationPath) throws Exception {
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, templateData.getTemplateFilePath());
		InputStream stream = new FileInputStream(filePath);
		Workbook wb = WorkbookFactory.create(stream);
		
		/**
		 * Substitute user defined variables
		 */
		substituteUserDefinedVariables(wb, templateVariables);

		/**
		 * Evaluate spreadsheet formulas
		 */
		evaluateFormulas(wb);			

		FileOutputStream fileOut = new FileOutputStream(destinationPath);
		wb.write(fileOut);
		fileOut.close();
	}
	
	private void evaluateFormulas(Workbook wb) {
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		Sheet sheet = wb.getSheetAt(0);
		Iterator<Row> rowItr = sheet.rowIterator();
		while (rowItr.hasNext()) {
			Row row = (Row) rowItr.next();
			Iterator<Cell> cellItr = row.cellIterator();
			while (cellItr.hasNext()) {
				Cell cell = (Cell) cellItr.next();
				if(cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
	                evaluator.evaluateFormulaCell(cell);
	            }
			}
		}			
	}

	private void substituteUserDefinedVariables(Workbook wb, List<OfferSheetTemplateVariable> templateVariables) {
		Pattern pattern = Pattern.compile(OfferSheetUtils.getRegexpUserDefinedVariable());
		
		Sheet sheet = wb.getSheetAt(0);
		
		Iterator<Row> rowItr = sheet.rowIterator();
		while (rowItr.hasNext()) {
			Row row = (Row) rowItr.next();
			Iterator<Cell> cellItr = row.cellIterator();
			while (cellItr.hasNext()) {
				Cell cell = (Cell) cellItr.next();
				String txt = cell.toString();
				String value = "";
				Matcher m = pattern.matcher(txt);
				if(!Utils.isBlankOrNull(txt) && m.find()){
					String variable = m.group();
					value = getTemplateVariableValue(variable, templateVariables);
					m.replaceFirst(value);
					txt = txt.substring(0, m.start()) + value + txt.substring(m.end(), txt.length());					
					switch (cell.getCellType()) {
					 	case Cell.CELL_TYPE_NUMERIC:
					    	cell.setCellValue(Float.parseFloat(txt));
					        break;
						case Cell.CELL_TYPE_BOOLEAN:
							cell.setCellValue(Boolean.parseBoolean(txt));
					        break;					   
					    case Cell.CELL_TYPE_STRING:
					    	cell.setCellValue(txt);
					        break;
					    case Cell.CELL_TYPE_BLANK:
					        break;
					    case Cell.CELL_TYPE_ERROR:
					        break;
					    case Cell.CELL_TYPE_FORMULA: 
					        break;
					}					
				}
			}
		}			
	}
}
