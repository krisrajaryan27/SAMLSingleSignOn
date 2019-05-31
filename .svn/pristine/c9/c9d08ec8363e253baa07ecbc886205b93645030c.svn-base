/**
 * 
 */
package com.talentPool.reports.excelReportGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.views.custom.CandidateOffersView;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author ajeet
 *
 */
public class CandidateOffersExcelReport {

	public void generateCandidateOffersExcelReport(String reportName, HSSFWorkbook wb, ArrayList data, FilterData filterData,String date_criteria,String other_criteria,PermissionSet permissionSet){
		
//		 Fillup Detail sheet
		HSSFSheet sheet = wb.getSheet("Details");	
		
		// Create a new font and alter it.
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 8);
		font.setFontName(HSSFFont.FONT_ARIAL);

		// define cellStyles
		HSSFCellStyle cellStyleDate = wb.createCellStyle();
		cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
		cellStyleDate.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// cellStyleDate.setFont(font);

		HSSFCellStyle cellStyleNormal = wb.createCellStyle();
		cellStyleNormal.setFont(font);

		HSSFFont fontBold = wb.createFont();
		fontBold.setFontHeightInPoints((short) 9);
		fontBold.setFontName(HSSFFont.FONT_ARIAL);
		fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle cellStyleBold = wb.createCellStyle();
		cellStyleBold.setFont(fontBold);

		// Enter Date and criteria in other sheet
		fillCriteriaInSheet("Details", wb, date_criteria,other_criteria);

		short column = 0; // start column count
		int rowCount = 3; // Title and filter row
		// Create header Row.
		HSSFRow headerRow = sheet.createRow((short) rowCount);
		headerRow.setHeight((short) 0x130);
	
		sheet.setColumnWidth((short)column , (short) ((30 * 8) / ((double) 1 / 20)));
		HSSFCell cell = headerRow.createCell(column++);
		cell.setCellValue("Name of candidate");
		cell.setCellStyle(cellStyleBold);				
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Date of Joining");
		cell.setCellStyle(cellStyleBold);

		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Location");
		cell.setCellStyle(cellStyleBold);
	
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Designation");
		cell.setCellStyle(cellStyleBold);
	
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Title");
		cell.setCellStyle(cellStyleBold);

		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("First Name");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Last Name");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Date of birth");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("List of grades");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Cluster");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Annual CTC");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Emergency (Cur)Address");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Add line 1");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Add line 2");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Dist");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("City");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Postal code");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Permanent Address");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Add line 1");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Add line 2");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Dist");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("City");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Postal code");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Phone 2");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Email 1");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Source of Recruitment");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Cost of Reqruitment");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Referre Employee code");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Educational Qualification");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Name of Institute/University");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Current Employer Name");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Date From");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Till Date");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Previous Employer");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Date From");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Till Date");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Current CTC");
		cell.setCellStyle(cellStyleBold);
		
		sheet.setColumnWidth((short)column , (short) ((20 * 8) / ((double) 1 / 20)));
		cell = headerRow.createCell(column++);
		cell.setCellValue("Stage");
		cell.setCellStyle(cellStyleBold);

		
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				CandidateOffersView cov = (CandidateOffersView) data.get(i);
				HashMap<String, String> customField = cov.getCustomFields();
				
				column = 0; // start column count

				HSSFRow row = sheet.createRow((short) (i + rowCount + 1));
				row.setHeight((short) 0x130);

				cell = row.createCell(column++); // Applicant Name
				cell.setCellValue(cov.getApplicantName());
				cell.setCellStyle(cellStyleNormal);

				String doj = cov.getApplicantDateJoined();
				Date jDate = Utils.convertToDate(doj, Utils.redYYYYMMDDFormat);
				cell = row.createCell(column++); // Joining date
				if (jDate != null) {
					cell.setCellValue(jDate);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // Location
				cell.setCellValue(customField.get("location"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Designation
				cell.setCellValue(customField.get("Designation"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Title
				cell.setCellValue(customField.get("title"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // First Name
				cell.setCellValue(customField.get("firstName"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Last Name
				cell.setCellValue(customField.get("lastName"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Date Of Birth
				cell.setCellValue(customField.get("dateOfBirth"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // List Of Grades
				cell.setCellValue(customField.get("listOfGrades"));
				cell.setCellStyle(cellStyleNormal);
			
				cell = row.createCell(column++); // cluster
				cell.setCellValue(customField.get("cluster"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Annual CTC 
				if(ImportConfigurationManager.isCTCOfferedViewable(permissionSet)){
					cell.setCellValue(cov.getOfferedCtc());	
				}else{
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}
				
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Emergency Add
				cell.setCellValue("");
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Emergency Add Line 1
				cell.setCellValue(customField.get("emergencyLine1"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Emergency Add Line 2
				cell.setCellValue(customField.get("emergencyLine2"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Emergency Dist
				cell.setCellValue(customField.get("emergencyDist"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Emergency City
				cell.setCellValue(customField.get("emergencyCity"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Emergency Postal code
				cell.setCellValue(customField.get("emergencyZip"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Permanenet Add
				cell.setCellValue("");
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Permanent Address Line1
				cell.setCellValue(customField.get("permanentLine1"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Permanent Address Line2
				cell.setCellValue(customField.get("permanentLine2"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Permanent Dist
				cell.setCellValue(customField.get("permanentDist"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Permanent City
				cell.setCellValue(customField.get("permanentCity"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Permanent Postal code
				cell.setCellValue(customField.get("permanentZip"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Phone 2
				cell.setCellValue(cov.getApplicantCellPhone());
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Email1
				cell.setCellValue(cov.getApplicantEmail());
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Source
				cell.setCellValue(cov.getSource());
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // cost of recruitment
				cell.setCellValue(customField.get("recruitmentCost"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // referer Employee Code
				cell.setCellValue(customField.get("refererEmployeeCode"));
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Edu qualification
				cell.setCellValue(cov.getDegree());
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Institute
				cell.setCellValue(cov.getInstitute());
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Current Employer
				cell.setCellValue(cov.getApplicantCurrentEmployer());
				cell.setCellStyle(cellStyleNormal);
				
				String cDateFrm = customField.get("currentEmpFromDate"); // date from
				Date cDateFrom = Utils.convertToDate(cDateFrm, Utils.regEUDateFormat);
				cell = row.createCell(column++); 
				if (cDateFrom != null) {
					cell.setCellValue(cDateFrom);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}

				
				String cDateTo = customField.get("currentEmpToDate"); // date to
				Date cDatTo = Utils.convertToDate(cDateTo, Utils.regEUDateFormat);
				cell = row.createCell(column++); 
				if (cDatTo != null) {
					cell.setCellValue(cDatTo);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // prev Emp
				cell.setCellValue(customField.get("prevEmp"));
				cell.setCellStyle(cellStyleNormal);
				
				String pDateFrm = customField.get("prevEmpFromDate"); // date from
				Date pDateFrom = Utils.convertToDate(pDateFrm, Utils.regEUDateFormat);
				cell = row.createCell(column++); 
				if (pDateFrom != null) {
					cell.setCellValue(pDateFrom);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				String pDateTo = customField.get("prevEmpToDate"); // date to
				Date pDatTo = Utils.convertToDate(pDateTo, Utils.regEUDateFormat);
				cell = row.createCell(column++); 
				if (pDatTo != null) {
					cell.setCellValue(pDatTo);
					cell.setCellStyle(cellStyleDate);
				} else {
					cell.setCellValue("");
				}
				
				cell = row.createCell(column++); // Current CTC
				if(ImportConfigurationManager.isCurrentCTCViewable(permissionSet)){
					cell.setCellValue(cov.getCurrentCtc());	
				}else {
					cell.setCellValue(GlobalConstants.CONFIDENTIAL_CHARACTER);
				}
				cell.setCellStyle(cellStyleNormal);
				
				cell = row.createCell(column++); // Stage
				cell.setCellValue(cov.getPositionStepTitle());
				cell.setCellStyle(cellStyleNormal);
				
			}
		}else {
			HSSFRow row = sheet.createRow((short) ( rowCount + 1));
			row.setHeight((short) 0x130);
			cell = row.createCell((short)0); 
			cell.setCellValue(TPLabels.getLabel("report.error.no_data"));
			cell.setCellStyle(cellStyleNormal);
		}
		
	}
	
	public void fillCriteriaInSheet(String sheetName, HSSFWorkbook wb, String dateRange, String criteria1) {
		HSSFSheet summarySheet = wb.getSheet(sheetName);

		HSSFRow summaryRow1 = summarySheet.createRow((short) 1);
		HSSFCell summaryCell = summaryRow1.createCell((short) 0);
		summaryCell.setCellValue(dateRange);
		
		HSSFRow summaryRow2 = summarySheet.createRow((short) 2);
		summaryCell = summaryRow2.createCell((short) 0);
		summaryCell.setCellValue(criteria1);

	}
}
