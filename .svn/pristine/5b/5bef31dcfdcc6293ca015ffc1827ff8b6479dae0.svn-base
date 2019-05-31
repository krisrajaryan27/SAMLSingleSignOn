/**
 * 
 */
package com.talentPool.dynamicReports.generator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.CollectionUtils;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.utils.CustomFieldUtils;
import com.talentPool.dynamicReports.utils.DynamicReportsColumnUtils;
import com.talentPool.masters.dataobject.ReportTemplateData;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.style.SSStyles;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author praveen
 *
 */
public class DynamicReportsCellGenerator {

	public <T extends SimpleDataObject> void createDataRow(List<T> sdoList, ArrayList<ColumnData> columns,
			Workbook wb, boolean customFieldPresent, ReportTemplateData reportData, int sheetIndex,
			int rowIndex, PermissionSet permissionSet) throws SQLException {
		try {
		
			Sheet sheet = wb.getSheetAt(sheetIndex);
			
			// define cellStyles
			CellStyle cellStyleFont = SSStyles.cellStyleNormal(wb);
					
			List<LinkedHashMap<String, String>> list = sortResult(sdoList, columns, customFieldPresent, reportData, permissionSet);

			for (int i = 0; i < list.size() && i< ReportDesignConstants.EXCEL_ALLOWABLE_LIMIT; i++) {
				LinkedHashMap<String, String> rowMap = list.get(i);
				
				int column = 0; // start column count			
				//Row row = sheet.createRow((i + rowIndex));
				
				Row row = sheet.getRow((i + rowIndex+1));
				if(row==null){
					row = sheet.createRow((i + rowIndex+1));
				}
				//row.setHeight((short) 0x130);
				
				/*** CELL CREATION START ***/	
				for (int j = 0; j < columns.size(); j++) {
					ColumnData columnData = columns.get(j);
					String field = columnData.getColumnName();
					if(!Utils.isBlankOrNull(rowMap.get(field))){
						Cell cell = row.createCell(column); 
						cell.setCellStyle(cellStyleFont);
						cell.setCellValue(rowMap.get(field));
					}
					column++;
				}
			}
		wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
	}
	
	
	private <T extends SimpleDataObject> List<LinkedHashMap<String, String>> sortResult(List<T> sdoList, ArrayList<ColumnData> columns, 
			boolean customFieldPresent, ReportTemplateData reportData, PermissionSet permissionSet) {
		
		List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
		try {			
			SimpleDataObject sdo = null;
		for (int i = 0; i < sdoList.size(); i++) {
				LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
				String customFields = null;
				HashMap<String, String> customFieldMap = new HashMap<String, String>();
				sdo = sdoList.get(i);
				if(customFieldPresent){
					try{
						//candidate
						customFields = SSStyles.nvl(sdo.getString(DynamicReportsColumnUtils.getColumnDBMapping(ReportDesignConstants.COLUMN_CANDIDATE_CUSTOM_FIELDS)));
						if(!Utils.isBlankOrNull(customFields)){
							CustomFieldUtils.generateCustomFieldMap(customFieldMap,customFields,"\\|\\|");
						}
					}catch (Exception e) {
						// do nothing
					}
					try {
						//position
						customFields = SSStyles.nvl(sdo.getString(DynamicReportsColumnUtils.getColumnDBMapping(ReportDesignConstants.COLUMN_POSITION_CUSTOM_FIELDS)));
						if(!Utils.isBlankOrNull(customFields)){
							CustomFieldUtils.generateCustomFieldMap(customFieldMap,customFields,"\\|\\|");
						}
					} catch (Exception e) {
						// do nothing
					}
				}
				for (int j = 0; j < columns.size(); j++) {
					ColumnData columnData = columns.get(j);
					String field = columnData.getColumnName();
					String type = columnData.getColumnType();
					if(ReportDesignConstants.COLUMN_EMPTY.equals(field)){
						row.put("", "");
					}else{
						if(type.equals("0")){
							if(!ImportConfigurationManager.isApplicantFieldViewable(ImportConfigurationConstants.FIELD_SOURCE ,permissionSet.isSHOW_CONFIDENTIAL_DATA()) && field.equals(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE)){
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
								row.put(field, getFieldDataValue(field,sdo));
							}
							//print custom field value
							if(!CollectionUtils.isEmpty(customFieldMap)){
								row.put(field, SSStyles.nvl(customFieldMap.get("CUS_"+field)));
							}
						}
					}
				}
				
				list.add(row);
			}

/*			if(!Utils.isBlankOrNull(reportData.getSortBy()) && !reportData.getSortBy().equals("-1")){
				Collections.sort(list, new ReportComparator(reportData.getSortBy(), 
					reportData.getSortWith(), reportData.getSortType()));
			}
*/			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return list;	
	}
	
	private String getFieldDataValue(String field,SimpleDataObject sdo){
		Object dataValObj = sdo.getAttribute(DynamicReportsColumnUtils.getColumnDBMapping(field));
		String value = null;
		if (dataValObj != null && (dataValObj instanceof java.sql.Timestamp || dataValObj instanceof java.sql.Date)) {
			value = DateUtils.getSystemDateFormat(sdo.getDate(DynamicReportsColumnUtils.getColumnDBMapping(field)));
		}else{
			value =Utils.getBlankIfNull(sdo.getString(DynamicReportsColumnUtils.getColumnDBMapping(field)));
		}
		return value;
	}
	
	public void fillCriteriaInSheet(Workbook wb, Sheet sheet, String reportName, String filterCriteria) {
		try {
			CellStyle cellStyleTitle = SSStyles.cellStyleTitle(wb);
			//Report name
			sheet.setColumnWidth(0, (short) ((80 * 8) / ((double) 1 / 20)));
			Row titleRow = sheet.createRow(0);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(cellStyleTitle);
			titleCell.setCellValue(reportName);
			
			// criteria cell
			String[] ftr = filterCriteria.split("\\\\n");
			for (int i = 0; i < ftr.length; i++) {
				Row criteriaRow = sheet.createRow(i+2);
				Cell summaryCell = criteriaRow.createCell(0);
				summaryCell.setCellValue(ftr[i]);	
			}
			
		}catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}
}
