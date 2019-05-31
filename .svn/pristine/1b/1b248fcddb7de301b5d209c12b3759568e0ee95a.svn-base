package com.talentPool.dynamicReports.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.FileUtils.FileHandlerUtils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.masters.dataobject.ReportTemplateData;
import com.talentPool.masters.manager.ReportTemplateManager;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reports.ReportConstants;
import com.talentPool.user.manager.PermissionSet;

public class PreFormattedDynamicReportGenerator {
	public <T extends SimpleDataObject> String generateReport(List<T> sdoList, String filterCriteria,
			ArrayList<ColumnData> columns, ReportTemplateData reportData, String filePath, PermissionSet permissionSet) throws IOException {
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
			
			DynamicReportsCellGenerator cellGenerator = new DynamicReportsCellGenerator();
			cellGenerator.fillCriteriaInSheet(wb, sheet, reportData.getTemplateName(), filterCriteria);
			
			// Create data Row			
			cellGenerator.createDataRow(sdoList, columns, wb, customFieldPresent, reportData, sheetIndex, rowIndex, permissionSet);
			
			// Write the output to a file
			String filePostFix = "" + System.currentTimeMillis();
			newFileName = reportData.getTemplateName() + filePostFix + FileHandlerUtils.getFileExtention(filePath, ".xls");
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
	
	public <T extends SimpleDataObject> String generateReport(String templateId,List<T> sdoList, String filterCriteria,PermissionSet permissionSet) throws IOException {
		ReportTemplateManager reportTemplateManager = new ReportTemplateManager();
		ReportTemplateData reportTemplateData = null;
		reportTemplateData = reportTemplateManager.getReportTemplateData(templateId);
		ArrayList<ColumnData> columns = reportTemplateManager.getReportTemplateColumnData(templateId);
		String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, reportTemplateData.getReportFilePath());
		String outputFileName = generateReport(sdoList, filterCriteria, columns, reportTemplateData, filePath, permissionSet);
		return outputFileName;
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
