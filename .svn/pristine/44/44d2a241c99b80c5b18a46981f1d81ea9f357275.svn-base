/**
 * 
 */
package com.talentPool.otherApplications.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.inbox.InboxConstants;

/**
 * @author Shantanu
 *
 */
public class ExcelGenerator {

	public String exportToCSV(ArrayList<ApplicantData> applicantsData, ArrayList<String> fieldList, LinkedHashMap<String, String> fieldsMap) throws IOException {
		
		String outputXLSFileName = "Schedule"+String.valueOf(System.currentTimeMillis())+".xls";
		String outputCSVFileName = TPApplicationProperties.getProperty("step_level_schedule_csv_name")  + ".csv";
		try {
			String xlsBasePath = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"),TPApplicationProperties.getProperty("customer.request_xml.dir"));
			String xlsPath = Utils.concatFilePath(xlsBasePath, "onHireExportToCSV.xls");
			
			String csvBasePath = TPApplicationProperties.getProperty("customer.created_csv.dir");
			File csvOutputDir = new File(csvBasePath);
			if (!csvOutputDir.isDirectory()) {
				csvOutputDir.mkdir();
			}
			String filePathXLS = Utils.concatFilePath(xlsBasePath, outputXLSFileName);
			String filePathCSV = Utils.concatFilePath(csvBasePath, outputCSVFileName);
			FileOutputStream fileOut = new FileOutputStream(filePathXLS);			
			
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(xlsPath));
			HSSFWorkbook wb = new HSSFWorkbook(fs);

			// Create header Row.
			createHeaderRow(wb, fieldList, fieldsMap);

			// Create data Row.
			createDataRow(wb, fieldList, fieldsMap, applicantsData, 1);

			wb.write(fileOut);
			fileOut.close();
			
			
			File f = new File(filePathCSV);

           	OutputStream os = (OutputStream)new FileOutputStream(f);
		    String encoding = "UTF8";
		    OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
		    BufferedWriter bw = new BufferedWriter(osw);

			WorkbookSettings ws = new WorkbookSettings();
		    ws.setLocale(new Locale("en", "EN"));
		    Workbook w = Workbook.getWorkbook(new File(filePathXLS),ws);
		    // sheet < 1 instead of w.getNumberOfSheets() because we consider only one sheet.
		    for (int sheet = 0; sheet < 1; sheet++){
		        Sheet s = w.getSheet(sheet);
		        //bw.write(s.getName());
		        //bw.newLine();
		        Cell[] row = null;		        
		        // Gets the cells from sheet
		        for (int i = 0 ; i < s.getRows() ; i++){
		        	row = s.getRow(i);
		        	if (row.length > 0){
		        		bw.write(row[0].getContents());
		        		for (int j = 1; j < row.length; j++){
		        			bw.write(',');
		        			bw.write(row[j].getContents());
		        		}
		        	}
		        	bw.newLine();
		        }
		    }
		    bw.flush();
		    bw.close();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return outputXLSFileName;
	}
	
	
	private int createDataRow(HSSFWorkbook wb, ArrayList<String> fieldList, LinkedHashMap<String, String> fieldsMap, ArrayList data, int rowCount) {
		try {

			// Fillup sheet 1
			HSSFSheet sheet = wb.getSheetAt(0);

			// Create a new font and alter it.
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 8);
			font.setFontName(HSSFFont.FONT_ARIAL);

			// define cellStyles
			HSSFCellStyle cellStyleDate = wb.createCellStyle();
			cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
			cellStyleDate.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			cellStyleDate.setFont(font);

			HSSFCellStyle cellStyleNormal = wb.createCellStyle();
			cellStyleNormal.setFont(font);

			ApplicantManager applicantManager = new ApplicantManager();			
			short column = 0; // start column count
			for (int i = 0; i < data.size(); i++) {
				ApplicantData aData = (ApplicantData) data.get(i);

				LinkedHashMap<String, String> fieldValuesMap = getFieldValuesMap(aData, applicantManager);
				column = 0; // start column count

				HSSFRow row = sheet.createRow((short) (i + rowCount));
				row.setHeight((short) 0x130);
				
				//modified as columns should appear in the order these are selected
				for(int f=0; f<fieldList.size();f++){
					HSSFCell cell = row.createCell(column++);
					cell.setCellValue(nvl(fieldValuesMap.get(fieldList.get(f))));
					cell.setCellStyle(cellStyleNormal);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		rowCount += data.size();
		return rowCount;
	}

	private void createHeaderRow(HSSFWorkbook wb, ArrayList<String> fieldList, LinkedHashMap<String, String> fieldsMap) {
		try {
			HSSFFont fontBold = wb.createFont();
			fontBold.setFontHeightInPoints((short) 9);
			fontBold.setFontName(HSSFFont.FONT_ARIAL);
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFCellStyle cellStyleBold = wb.createCellStyle();
			cellStyleBold.setFont(fontBold);

			HSSFSheet sheet = wb.getSheetAt(0);

			HSSFRow headerRow = sheet.createRow((short) 0);
			headerRow.setHeight((short) 0x130);
			
//			modified as columns should appear in the order these are selected
			short column = 0; // start column count
			for(int i=0; i<fieldList.size();i++){
				sheet.setColumnWidth((short) column, (short) ((40 * 8) / ((double) 1 / 20)));
				HSSFCell cell = headerRow.createCell(column++);
				cell.setCellValue(fieldsMap.get(fieldList.get(i)));
				cell.setCellStyle(cellStyleBold);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}

	}
	
	
	private LinkedHashMap<String, String> getFieldValuesMap(ApplicantData aData, ApplicantManager applicantManager) {
		LinkedHashMap<String, String> fieldValuesMap = new LinkedHashMap<String, String>();
		try {
			fieldValuesMap.put(InboxConstants.FLD_NAME, aData.getApplicantName());
			fieldValuesMap.put(InboxConstants.FLD_EMAIL_1, aData.getApplicantEmail1());
			fieldValuesMap.put(InboxConstants.FLD_EMAIL_2, aData.getApplicantEmail2());
			fieldValuesMap.put(InboxConstants.FLD_PHONE_1, aData.getApplicantWorkPhone());
			fieldValuesMap.put(InboxConstants.FLD_PHONE_2, aData.getApplicantHomePhone());
			fieldValuesMap.put(InboxConstants.FLD_MOBILE, aData.getApplicantCellPhone());
			fieldValuesMap.put(InboxConstants.FLD_EXPERIENCE, aData.getApplicantExperience());						
			fieldValuesMap.put(InboxConstants.FLD_CURRENT_LOCATION, aData.getApplicantCity());
			fieldValuesMap.put(InboxConstants.FLD_SKILLS, aData.getSkillsString());

			ArrayList<EducationalData> eList = applicantManager.getEducationalInfo(aData.getApplicantId());
			if (eList != null && eList.size() > 0) {
				EducationalData eduData = eList.get(0);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				fieldValuesMap.put(InboxConstants.FLD_YEAR_OF_PASSING_1, yop);
				fieldValuesMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_1, (eduData.getStartDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getStartDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_1, (eduData.getEndDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getEndDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_INSTITUTE_1, eduData.getInstitute());
				fieldValuesMap.put(InboxConstants.FLD_DEGREE_1, eduData.getDegreeTitle());
				fieldValuesMap.put(InboxConstants.FLD_BRANCH_1, eduData.getMajor());
				fieldValuesMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_1, eduData.getUniversity());
				fieldValuesMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_1, eduData.getTypeOfProgram());
				fieldValuesMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_1, eduData.getGrade());
			}
			if (eList != null && eList.size() > 1) {
				EducationalData eduData = eList.get(1);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				fieldValuesMap.put(InboxConstants.FLD_YEAR_OF_PASSING_2, yop);
				fieldValuesMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_2, (eduData.getStartDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getStartDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_2, (eduData.getEndDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getEndDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_INSTITUTE_2, eduData.getInstitute());
				fieldValuesMap.put(InboxConstants.FLD_DEGREE_2, eduData.getDegreeTitle());
				fieldValuesMap.put(InboxConstants.FLD_BRANCH_2, eduData.getMajor());
				fieldValuesMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_2, eduData.getUniversity());
				fieldValuesMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_2, eduData.getTypeOfProgram());
				fieldValuesMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_2, eduData.getGrade());
			}
			
			if (eList != null && eList.size() > 2) {
				EducationalData eduData = eList.get(2);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				fieldValuesMap.put(InboxConstants.FLD_YEAR_OF_PASSING_3, yop);
				fieldValuesMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_3, (eduData.getStartDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getStartDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_3, (eduData.getEndDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getEndDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_INSTITUTE_3, eduData.getInstitute());
				fieldValuesMap.put(InboxConstants.FLD_DEGREE_3, eduData.getDegreeTitle());
				fieldValuesMap.put(InboxConstants.FLD_BRANCH_3, eduData.getMajor());
				fieldValuesMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_3, eduData.getUniversity());
				fieldValuesMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_3, eduData.getTypeOfProgram());
				fieldValuesMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_3, eduData.getGrade());
			}
			
			if (eList != null && eList.size() > 3) {
				EducationalData eduData = eList.get(3);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				fieldValuesMap.put(InboxConstants.FLD_YEAR_OF_PASSING_4, yop);
				fieldValuesMap.put(InboxConstants.FLD_START_DATE_OF_PASSING_4, (eduData.getStartDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getStartDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_END_DATE_OF_PASSING_4, (eduData.getEndDate() == null) ? "" : Utils.getDateConvertedToString(eduData.getEndDate(),"ddMMMyyyy"));
				fieldValuesMap.put(InboxConstants.FLD_INSTITUTE_4, eduData.getInstitute());
				fieldValuesMap.put(InboxConstants.FLD_DEGREE_4, eduData.getDegreeTitle());
				fieldValuesMap.put(InboxConstants.FLD_BRANCH_4, eduData.getMajor());
				fieldValuesMap.put(InboxConstants.FLD_UNIVERSITY_OF_PASSING_4, eduData.getUniversity());
				fieldValuesMap.put(InboxConstants.FLD_TYPE_OF_PROGRAM_OF_PASSING_4, eduData.getTypeOfProgram());
				fieldValuesMap.put(InboxConstants.FLD_PERCENTAGE_OF_PASSING_4, eduData.getGrade());
			}
			
			fieldValuesMap.put(InboxConstants.FLD_CURRENT_EMPLOYER, aData.getApplicantCurrentEmployer());
			fieldValuesMap.put(InboxConstants.FLD_CURRENT_CTC, aData.getCurrentCTC());
			fieldValuesMap.put(InboxConstants.FLD_EXPECTED_CTC, aData.getExpectedCTC());
			
			// Custom Fileds
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(aData.getApplicantId(), CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				for (int c = 0; customFields != null && c < customFields.size(); c++) {
					CustomFieldData cData = customFields.get(c);
					fieldValuesMap.put(cData.getFieldName(), cData.getDisplayValue());
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return fieldValuesMap;
	}
	
	private String nvl(String val) {
		return (val == null) ? "" : val;
	}
	
	public static ArrayList<String> getAllCandidateFieldsToExport(LinkedHashMap<String, String> fieldsMap) {
		ArrayList<String> fieldList = new ArrayList<String>();
		try{
			Iterator<String> itr = fieldsMap.keySet().iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				String value = fieldsMap.get(key);
				fieldList.add(key);
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}
		return fieldList;
	}	
}