/**
 * 
 */
package com.talentPool.reportDesign.generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.reportDesign.comparator.ReportComparator;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.style.SSStyles;
import com.talentPool.reportDesign.utils.ColumnUtils;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class CellGenerator {

	public void createDataRow(ResultSet rs, ArrayList<ColumnData> columns,
			Workbook wb, boolean customFieldPresent, ReportData reportData, int sheetIndex,
			int rowIndex, PermissionSet permissionSet) throws SQLException {
		try {
		
			Sheet sheet = wb.getSheetAt(sheetIndex);
			
			// define cellStyles
			SSStyles.cellStyleDate(wb);
			SSStyles.cellStyleTime(wb);
			CellStyle cellStyleFont = SSStyles.cellStyleNormal(wb);
					
			List<LinkedHashMap<String, String>> list = sortResult(rs, columns, customFieldPresent, reportData, permissionSet);
			for (int i = 0; i < list.size() && i< ReportDesignConstants.EXCEL_ALLOWABLE_LIMIT; i++) {
				LinkedHashMap<String, String> rowMap = list.get(i);
				
				int column = 0; // start column count			
				Row row = sheet.createRow((i + rowIndex));
				row.setHeight((short) 0x130);
				
				/*** CELL CREATION START ***/	
				for (int j = 0; j < columns.size(); j++) {
					ColumnData columnData = columns.get(j);
					String field = columnData.getColumnName();
					Cell cell = row.createCell(column++); 
					cell.setCellStyle(cellStyleFont);
					cell.setCellValue(rowMap.get(field));
					
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	
	private List<LinkedHashMap<String, String>> sortResult(ResultSet rs, ArrayList<ColumnData> columns, 
			boolean customFieldPresent, ReportData reportData, PermissionSet permissionSet) {
		
		List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
		try {			
			while (rs.next()) {
				LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
				String customFields = null;
				HashMap<String, String> customFieldMap = new HashMap<String, String>();
				if(customFieldPresent){
					try{
						//candidate
						customFields = SSStyles.nvl(rs.getString(ColumnUtils.columnDBMap.get(ReportDesignConstants.COLUMN_CANDIDATE_CUSTOM_FIELDS)));
						if(!Utils.isBlankOrNull(customFields)){
							generateCustomFieldMap(customFieldMap,customFields);
						}
					}catch (Exception e) {
						// do nothing
					}
					try {
						//position
						customFields = SSStyles.nvl(rs.getString(ColumnUtils.columnDBMap.get(ReportDesignConstants.COLUMN_POSITION_CUSTOM_FIELDS)));
						if(!Utils.isBlankOrNull(customFields)){
							generateCustomFieldMap(customFieldMap,customFields);
						}
					} catch (Exception e) {
						// do nothing
					}
				}
				
				for (int j = 0; j < columns.size(); j++) {
					ColumnData columnData = columns.get(j);
					String field = columnData.getColumnName();
					String type = columnData.getColumnType();
					if(type.equals("0")){
						if(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE.equals(field) && !ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA())){
							row.put(field, SSStyles.nvl(GlobalConstants.CONFIDENTIAL_CHARACTER));
						}else if(ReportDesignConstants.COLUMN_CANDIDATE_OFFERED_CTC.equals(field) && !ImportConfigurationManager.isCTCOfferedViewable(permissionSet)){
							row.put(field, SSStyles.nvl(GlobalConstants.CONFIDENTIAL_CHARACTER));
						}else if(ReportDesignConstants.COLUMN_CANDIDATE_DESIGNATION_OFFERED.equals(field) && !ImportConfigurationManager.isDesignationOfferedViewable(permissionSet)){
							row.put(field, SSStyles.nvl(GlobalConstants.CONFIDENTIAL_CHARACTER));
						}else if(ReportDesignConstants.COLUMN_CANDIDATE_LEVEL_OFFERED.equals(field) && !ImportConfigurationManager.isLevelOfferedViewable(permissionSet)){
							row.put(field, SSStyles.nvl(GlobalConstants.CONFIDENTIAL_CHARACTER));
						}else if(ReportDesignConstants.COLUMN_CANDIDATE_CURRENT_CTC.equals(field) && !ImportConfigurationManager.isCurrentCTCViewable(permissionSet)){
							row.put(field, SSStyles.nvl(GlobalConstants.CONFIDENTIAL_CHARACTER));
						}else if(ReportDesignConstants.COLUMN_CANDIDATE_EXPECTED_CTC.equals(field) && !ImportConfigurationManager.isExpectedCTCViewable(permissionSet)){
							row.put(field, SSStyles.nvl(GlobalConstants.CONFIDENTIAL_CHARACTER));
						}else{
							row.put(field, SSStyles.nvl(rs.getString(ColumnUtils.columnDBMap.get(field))));
						}
					}else{ 
						//print custom field value
						if(customFieldMap!=null){
							row.put(field, SSStyles.nvl(customFieldMap.get(field)));
						}
					}
				}
				list.add(row);
			}

			if(!Utils.isBlankOrNull(reportData.getSortBy()) && !reportData.getSortBy().equals("-1")){
				Collections.sort(list, new ReportComparator(reportData.getSortBy(), 
					reportData.getSortWith(), reportData.getSortType()));
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return list;	
	}
	
	private void generateCustomFieldMap(HashMap<String, String> customFieldMap, String customFields) {
		try {
			String[] cusFlds = customFields.split(",");
			for (int i = 0; i < cusFlds.length; i++) {
				String cusFld = cusFlds[i];
				if(!Utils.isBlankOrNull(cusFld)){
					String[]customFld = cusFld.split("=");
					String id = customFld[0];
					String value = customFld[1];
					if(customFieldMap.containsKey(id)){
						value = customFieldMap.get(id)+","+value;
					}
					customFieldMap.put(id, value);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	public void fillCriteriaInSheet(Workbook wb, Sheet sheet, String reportName, FilterData filterData) {
		try {
			CellStyle cellStyleTitle = SSStyles.cellStyleTitle(wb);
						
			//Report name
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(cellStyleTitle);
			titleCell.setCellValue(reportName);

			// date range cell
			if(!Utils.isBlankOrNull(filterData.getFromDate()) && !Utils.isBlankOrNull(filterData.getToDate())){
				Date fromDate = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
				Date toDate = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
				String dateRange = "Date : "+Utils.getDateConvertedToString(fromDate, Utils.regEEEDDMMMYY)+
								" to "+Utils.getDateConvertedToString(toDate, Utils.regEEEDDMMMYY);
				
				Row dateRangeRow = sheet.createRow(1);
				Cell dateRangeCell = dateRangeRow.createCell(0);
				dateRangeCell.setCellValue(dateRange);
			}

			// criteria cell
			String criteria = filterData.getOtherFilterCriteria();
			Row criteriaRow = sheet.createRow(2);
			Cell summaryCell = criteriaRow.createCell(0);
			summaryCell.setCellValue(criteria);
		}catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}
	
	public boolean createHeaderRow(Workbook wb, ArrayList<ColumnData> columns) {
		boolean customFieldPresent = false;
		try {
			Sheet sheet = wb.getSheetAt(0);
			CellStyle cellStyleBold = SSStyles.cellStyleBold(wb);
			Row headerRow = sheet.createRow(4);
			headerRow.setHeight((short) 0x130);

			int column = 0; // start column count
			for (int i = 0; i < columns.size(); i++) {
				ColumnData columnData = columns.get(i);
				String field = columnData.getColumnName();
				if(columnData.getColumnType().equals("1")){
					customFieldPresent = true;
				}
				sheet.setColumnWidth(column, (short) ((40 * 8) / ((double) 1 / 20)));
				Cell cell = headerRow.createCell(column++);
				cell.setCellValue(ColumnUtils.columnLabelMap.get(field));
				cell.setCellStyle(cellStyleBold);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return customFieldPresent;
	}
}
