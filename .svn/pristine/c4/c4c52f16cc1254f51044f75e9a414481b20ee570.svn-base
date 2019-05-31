/**
 * 
 */
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
import com.talentPool.common.utils.Utils;
import com.talentPool.reportDesign.dataobject.ColumnData;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reportDesign.style.SSStyles;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.manager.PermissionSet;


/**
 * @author Ajeet
 *
 */
public class ExcelReportGenerator {

	public String generateReport(ResultSet rs, FilterData filterData,
			ArrayList<ColumnData> columns, ReportData reportData, String filePath, PermissionSet permissionSet) throws IOException {
		String newFileName = "";
		try {
			
			Workbook wb = WorkbookFactory.create(new FileInputStream(new File(filePath)));
			
			Sheet sheet = wb.getSheetAt(0);

			// delete all previous data
			SSStyles.deleteAllExcelData(sheet);

			//fill criteria in sheet
			CellGenerator cellGenerator = new CellGenerator();
			cellGenerator.fillCriteriaInSheet(wb, sheet, reportData.getReportName(), filterData);
			
			// Create header Row
			boolean customFieldPresent = cellGenerator.createHeaderRow(wb, columns);

			// Create data Row
			cellGenerator.createDataRow(rs, columns, wb, customFieldPresent, reportData, 0, 5, permissionSet);
	

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

	

	
}
