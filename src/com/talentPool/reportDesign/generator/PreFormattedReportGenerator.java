package com.talentPool.reportDesign.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.manager.PermissionSet;

public class PreFormattedReportGenerator {
	public String generateReport(ResultSet rs, FilterData filterData,
			ArrayList<ColumnData> columns, ReportData reportData, String filePath, PermissionSet permissionSet) throws IOException {
		String newFileName = "";
		try {
			
			Workbook wb = WorkbookFactory.create(new FileInputStream(new File(filePath)));	
			int sheetIndex = 0;
			int rowIndex = 0;
			if(!Utils.isBlankOrNull(reportData.getSheetIndex())){
				sheetIndex = Integer.parseInt(reportData.getSheetIndex());
			}
			if(!Utils.isBlankOrNull(reportData.getRowIndex())){
				rowIndex = Integer.parseInt(reportData.getRowIndex());
			}	
			
			Sheet sheet = wb.createSheet(TPLabels.getLabel("reportdesigner.label.pre_formatted.message_sheet"));
			
			// Create header Row
			boolean customFieldPresent = isCustomFieldPresent(columns);

			//fill criteria in sheet
			CellGenerator cellGenerator = new CellGenerator();
			cellGenerator.fillCriteriaInSheet(wb, sheet, reportData.getReportName(), filterData);
			
			// Create data Row			
			cellGenerator.createDataRow(rs, columns, wb, customFieldPresent, reportData, sheetIndex, rowIndex, permissionSet);
			
			// Write the output to a file
			String filePostFix = "" + System.currentTimeMillis();
			newFileName = reportData.getReportName() + filePostFix + ".xls";
			String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
			String destinationPath = Utils.concatFilePath(basePath, ReportConstants.REPORT_DESTINATION_FOLDER);
			filePath = Utils.concatFilePath(destinationPath, newFileName);
			FileOutputStream fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileOut.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		} 
		return newFileName;
	}

	
	private boolean isCustomFieldPresent(ArrayList<ColumnData> columns) {
		boolean customFieldPresent = false;
		try {
			for (int i = 0; i < columns.size(); i++) {
				ColumnData columnData = columns.get(i);
				if(columnData.getColumnType().equals("1")){
					customFieldPresent = true;
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return customFieldPresent;
	}
	
	
}
