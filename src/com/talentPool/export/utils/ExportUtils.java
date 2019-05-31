/**
 * 
 */
package com.talentPool.export.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.constants.ImportConfigurationConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.manager.ImportConfigurationManager;
import com.talentPool.applicant.utils.TypeOfProgram;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.export.ExportConstants;
import com.talentPool.export.bo.Field;
import com.talentPool.export.bo.Fields;

/**
 * @author pallavi
 *
 */
public class ExportUtils {
	public String getFieldsInXml(Fields fields) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (fields != null && fields.getHeaders() != null && fields.getHeaders().size() > 0) {
				for (int i = 0; i < fields.getHeaders().size(); i++) {
					Field field = (Field) fields.getHeaders().get(i);
									
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", field.getId());
					wr.startElement("", "row", "", atr);
					
					wr.startElement("cell");
					wr.characters(field.getName());
					wr.endElement("cell");		
					
					wr.endElement("row");			
				}
			}
			wr.endElement("rows");
			wr.endDocument();

		}catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	public static String getXMLforFieldsToExport(LinkedHashMap<String, String> fieldsMap) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			Iterator<String> itr = fieldsMap.keySet().iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				String value = fieldsMap.get(key);
				AttributesImpl atr = new AttributesImpl();
				atr.addAttribute("", "id", "", "", key);
				wr.startElement("", "row", "", atr);

				wr.startElement("cell");
				wr.characters(value);
				wr.endElement("cell");

				wr.endElement("row");
			}

			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public String exportToExcel(Fields fields, String selectedFields, List<SimpleDataObject> exportData, String sessionId) throws IOException {
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ".xlsx";
		
		Workbook wb = new XSSFWorkbook();
		CreationHelper helper = wb.getCreationHelper();
		Sheet s = wb.createSheet();
		if(!Utils.isBlankOrNull(selectedFields)) {			
			String[] flds = selectedFields.split(",");
			createHeader(s, wb, flds, fields, helper);
			if(exportData != null && exportData.size() > 0) {
				for(int rownum=0; rownum<exportData.size();) {
					SimpleDataObject obj = exportData.get(rownum);
					Row r = s.createRow(++rownum);
					for(int cellnum = 0; cellnum < flds.length; cellnum++) {
						Cell c = r.createCell(cellnum);
						String cellValue= obj.getString(flds[cellnum]);
						if(!Utils.isBlankOrNull(cellValue) && isDate(cellValue)) {
							CellStyle cellStyle = wb.createCellStyle();
							CreationHelper createHelper = wb.getCreationHelper();
							cellStyle.setDataFormat(createHelper
									.createDataFormat().getFormat("dd MMM yyyy"));		
							c.setCellStyle(cellStyle);
							try {
								c.setCellValue(new SimpleDateFormat("dd MMM yyyy").parse(cellValue));
							} catch (ParseException e) {
								c.setCellValue(helper.createRichTextString(cellValue));
							}
						} else if(!Utils.isBlankOrNull(cellValue) && NumberUtils.isNumber((cellValue))){
							c.setCellValue(new Double(cellValue));
						}else{
							c.setCellValue(helper.createRichTextString(cellValue));
						}
					}
			   }
			}
		}
		FileOutputStream out = new FileOutputStream(outputFileName);
		wb.write(out);
		out.close();

		return outputFileName;
	}
	public String exportToExcelApplicant( ArrayList<String> fieldList, LinkedHashMap<String, String> fieldsMap,Fields fields, String selectedFields, List<SimpleDataObject> exportData, String sessionId) throws IOException {
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ".xlsx";
		
		Workbook wb = new XSSFWorkbook();
		CreationHelper helper = wb.getCreationHelper();
		Sheet s = wb.createSheet();
		if(!Utils.isBlankOrNull(selectedFields)) {			
			String[] flds = selectedFields.split(",");
			createHeaderRow(s, wb, helper, fieldList, fieldsMap);
			if(exportData != null && exportData.size() > 0) {
				for(int rownum=0; rownum<exportData.size();) {
					SimpleDataObject obj = exportData.get(rownum);
					Row r = s.createRow(++rownum);
					ApplicantManager applicantManager=new ApplicantManager();
					LinkedHashMap<String, String> fieldValuesMap = getFieldValuesMap(obj, applicantManager);
					for(int cellnum = 0; cellnum < flds.length; cellnum++) {
						Cell c = r.createCell(cellnum);
						String cellValue= nvl(fieldValuesMap.get(flds[cellnum]));
						if(!Utils.isBlankOrNull(cellValue) && isDate(cellValue)) {
							CellStyle cellStyle = wb.createCellStyle();
							CreationHelper createHelper = wb.getCreationHelper();
							cellStyle.setDataFormat(createHelper
									.createDataFormat().getFormat("dd MMM yyyy"));		
							c.setCellStyle(cellStyle);
							try {
								c.setCellValue(new SimpleDateFormat("dd MMM yyyy").parse(cellValue));
							} catch (ParseException e) {
								c.setCellValue(helper.createRichTextString(cellValue));
							}
						} else if(!Utils.isBlankOrNull(cellValue) && NumberUtils.isNumber((cellValue))){
							c.setCellValue(cellValue);
						}else{
							c.setCellValue(helper.createRichTextString(cellValue));
						}
					}
			   }
			}
		}
		FileOutputStream out = new FileOutputStream(outputFileName);
		wb.write(out);
		out.close();

		return outputFileName;
	}

	private LinkedHashMap<String, String> getFieldValuesMap(SimpleDataObject aData, ApplicantManager applicantManager) {
		LinkedHashMap<String, String> fieldValuesMap = new LinkedHashMap<String, String>();
		try {
			String applicantId=aData.getString("fld0");
			if(Utils.isBlankOrNull(applicantId)){
				applicantId="";
			}
			fieldValuesMap.put(ExportConstants.FLD_ID, Utils.isBlankOrNull(aData.getString("fld0"))?"":aData.getString("fld0"));
			fieldValuesMap.put(ExportConstants.FLD_NAME, Utils.isBlankOrNull(aData.getString("fld1"))?"":aData.getString("fld1"));
			fieldValuesMap.put(ExportConstants.FLD_CURRENT_LOCATION, Utils.isBlankOrNull(aData.getString("fld2"))?"":aData.getString("fld2"));
			fieldValuesMap.put(ExportConstants.FLD_EMAIL_1, Utils.isBlankOrNull(aData.getString("fld3"))?"":aData.getString("fld3"));
			fieldValuesMap.put(ExportConstants.FLD_EMAIL_2, Utils.isBlankOrNull(aData.getString("fld4"))?"":aData.getString("fld4"));
			fieldValuesMap.put(ExportConstants.FLD_PHONE_1, Utils.isBlankOrNull(aData.getString("fld5"))?"":aData.getString("fld5"));
			fieldValuesMap.put(ExportConstants.FLD_PHONE_2, Utils.isBlankOrNull(aData.getString("fld6"))?"":aData.getString("fld6"));
			fieldValuesMap.put(ExportConstants.FLD_MOBILE, Utils.isBlankOrNull(aData.getString("fld7"))?"":aData.getString("fld7"));
			fieldValuesMap.put(ExportConstants.FLD_EXPERIENCE, Utils.isBlankOrNull(aData.getString("fld8"))?"":aData.getString("fld8"));
			fieldValuesMap.put(ExportConstants.FLD_POSITION_CODE, Utils.isBlankOrNull(aData.getString("fld9"))?"":aData.getString("fld9"));
			fieldValuesMap.put(ExportConstants.FLD_POSITION_NAME, Utils.isBlankOrNull(aData.getString("fld10"))?"":aData.getString("fld10"));
			fieldValuesMap.put(ExportConstants.FLD_DEPARTMENT, Utils.isBlankOrNull(aData.getString("fld11"))?"":aData.getString("fld11"));
			fieldValuesMap.put(ExportConstants.FLD_STEP_NAME, Utils.isBlankOrNull(aData.getString("fld12"))?"":aData.getString("fld12"));
			fieldValuesMap.put(ExportConstants.FLD_IMPORT_DATE, Utils.isBlankOrNull(aData.getString("fld13"))?"":aData.getString("fld13"));
			fieldValuesMap.put(ExportConstants.FLD_SOURCE, Utils.isBlankOrNull(aData.getString("fld14"))?"":aData.getString("fld14"));
			fieldValuesMap.put(ExportConstants.FLD_JOINING_DATE, Utils.isBlankOrNull(aData.getString("fld15"))?"":aData.getString("fld15"));
			fieldValuesMap.put(ExportConstants.FLD_CURRENT_EMPLOYER, Utils.isBlankOrNull(aData.getString("fld16"))?"":aData.getString("fld16"));
			fieldValuesMap.put(ExportConstants.FLD_IMPORTED_BY, Utils.isBlankOrNull(aData.getString("fld17"))?"":aData.getString("fld17"));
			fieldValuesMap.put(ExportConstants.FLD_CURRENT_CTC, Utils.isBlankOrNull(aData.getString("fld18"))?"":aData.getString("fld18"));
			fieldValuesMap.put(ExportConstants.FLD_EXPECTED_CTC, Utils.isBlankOrNull(aData.getString("fld19"))?"":aData.getString("fld19"));
			fieldValuesMap.put(ExportConstants.FLD_NOTICE_PERIOD, Utils.isBlankOrNull(aData.getString("fld20"))?"":aData.getString("fld20"));
			fieldValuesMap.put(ExportConstants.FLD_LEVEL_OFFERED, Utils.isBlankOrNull(aData.getString("fld21"))?"":aData.getString("fld21"));
			fieldValuesMap.put(ExportConstants.FLD_DESIGNATION_OFFERED, Utils.isBlankOrNull(aData.getString("fld22"))?"":aData.getString("fld22"));
			fieldValuesMap.put(ExportConstants.FLD_OFFERED_CTC, Utils.isBlankOrNull(aData.getString("fld23"))?"":aData.getString("fld23"));
			fieldValuesMap.put(ExportConstants.FLD_EMPLOYEE_ID, Utils.isBlankOrNull(aData.getString("fld24"))?"":aData.getString("fld24"));
			fieldValuesMap.put(ExportConstants.FLD_DATE_OF_BIRTH, Utils.isBlankOrNull(aData.getString("fld25"))?"":aData.getString("fld25"));
			fieldValuesMap.put(ExportConstants.FLD_SRNO, Utils.isBlankOrNull(aData.getString("fld26"))?"":aData.getString("fld26"));
			fieldValuesMap.put(ExportConstants.FLD_SUB_DEPT, Utils.isBlankOrNull(aData.getString("fld27"))?"":aData.getString("fld27"));
			fieldValuesMap.put(ExportConstants.FLD_STATUS, Utils.isBlankOrNull(aData.getString("fld28"))?"":aData.getString("fld28"));
			
			fieldValuesMap.put(ExportConstants.FLD_JOINING_BONUS, Utils.isBlankOrNull(aData.getString("fld29"))?"":aData.getString("fld29"));
			fieldValuesMap.put(ExportConstants.FLD_VARIABLE_OFFERED, Utils.isBlankOrNull(aData.getString("fld30"))?"":aData.getString("fld30"));
			
			ArrayList<EducationalData> eList = applicantManager.getEducationalInfo(applicantId);
			if (eList != null && eList.size() > 0) {
				EducationalData eduData = eList.get(0);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				Date stDate = eduData.getStartDate(); // Year Of passing
				String startDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = eduData.getEndDate(); // Year Of passing
				String endDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_YEAR_OF_PASSING_1, yop);
				fieldValuesMap.put(ExportConstants.FLD_INSTITUTE_1, eduData.getInstitute());
				fieldValuesMap.put(ExportConstants.FLD_DEGREE_1, eduData.getDegreeTitle());
				fieldValuesMap.put(ExportConstants.FLD_BRANCH_1, eduData.getMajor());
				fieldValuesMap.put(ExportConstants.FLD_GRADE_1, eduData.getGrade());
				fieldValuesMap.put(ExportConstants.FLD_START_DATE_1, startDate);
				fieldValuesMap.put(ExportConstants.FLD_END_DATE_1, endDate);
				try{
				fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_1, Utils.isBlankOrNull(eduData.getTypeOfProgram())?"":
					TypeOfProgram.getProgramByID(Integer.parseInt(eduData.getTypeOfProgram())));
				}catch(Exception ex){
					
				}
			}
			if (eList != null && eList.size() > 1) {
				EducationalData eduData = eList.get(1);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				Date stDate = eduData.getStartDate(); // Year Of passing
				String startDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = eduData.getEndDate(); // Year Of passing
				String endDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_YEAR_OF_PASSING_2, yop);
				fieldValuesMap.put(ExportConstants.FLD_INSTITUTE_2, eduData.getInstitute());
				fieldValuesMap.put(ExportConstants.FLD_DEGREE_2, eduData.getDegreeTitle());
				fieldValuesMap.put(ExportConstants.FLD_BRANCH_2, eduData.getMajor());
				fieldValuesMap.put(ExportConstants.FLD_GRADE_2, eduData.getGrade());
				fieldValuesMap.put(ExportConstants.FLD_START_DATE_2, startDate);
				fieldValuesMap.put(ExportConstants.FLD_END_DATE_2, endDate);
				//fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_2, eduData.getTypeOfProgram());
				try{
					fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_2, Utils.isBlankOrNull(eduData.getTypeOfProgram())?"":
						TypeOfProgram.getProgramByID(Integer.parseInt(eduData.getTypeOfProgram())));
					}catch(Exception ex){
						
					}
			}

			if (eList != null && eList.size() > 2) {
				EducationalData eduData = eList.get(2);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				Date stDate = eduData.getStartDate(); // Year Of passing
				String startDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = eduData.getEndDate(); // Year Of passing
				String endDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_YEAR_OF_PASSING_3, yop);
				fieldValuesMap.put(ExportConstants.FLD_INSTITUTE_3, eduData.getInstitute());
				fieldValuesMap.put(ExportConstants.FLD_DEGREE_3, eduData.getDegreeTitle());
				fieldValuesMap.put(ExportConstants.FLD_BRANCH_3, eduData.getMajor());
				fieldValuesMap.put(ExportConstants.FLD_GRADE_3, eduData.getGrade());
				fieldValuesMap.put(ExportConstants.FLD_START_DATE_3, startDate);
				fieldValuesMap.put(ExportConstants.FLD_END_DATE_3, endDate);
				//fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_3, eduData.getTypeOfProgram());
				try{
					fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_3, Utils.isBlankOrNull(eduData.getTypeOfProgram())?"":
						TypeOfProgram.getProgramByID(Integer.parseInt(eduData.getTypeOfProgram())));
					}catch(Exception ex){
						
					}
			}
			if (eList != null && eList.size() > 3) {
				EducationalData eduData = eList.get(3);
				Date pDate = eduData.getYearOfPassing(); // Year Of passing
				String yop = (pDate == null) ? "" : Utils.getDateConvertedToString(pDate, "yyyy");
				Date stDate = eduData.getStartDate(); // Year Of passing
				String startDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = eduData.getEndDate(); // Year Of passing
				String endDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_YEAR_OF_PASSING_4, yop);
				fieldValuesMap.put(ExportConstants.FLD_INSTITUTE_4, eduData.getInstitute());
				fieldValuesMap.put(ExportConstants.FLD_DEGREE_4, eduData.getDegreeTitle());
				fieldValuesMap.put(ExportConstants.FLD_BRANCH_4, eduData.getMajor());
				fieldValuesMap.put(ExportConstants.FLD_GRADE_4, eduData.getGrade());
				fieldValuesMap.put(ExportConstants.FLD_START_DATE_4, startDate);
				fieldValuesMap.put(ExportConstants.FLD_END_DATE_4, endDate);
				//fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_4, eduData.getTypeOfProgram());
				try{
					fieldValuesMap.put(ExportConstants.FLD_TYPE_OF_PROG_4, Utils.isBlankOrNull(eduData.getTypeOfProgram())?"":
						TypeOfProgram.getProgramByID(Integer.parseInt(eduData.getTypeOfProgram())));
					}catch(Exception ex){
						
					}
			}
			
			

			// Custom Fileds
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForEntity(applicantId, CustomFieldConstants.ENTITY_TYPE_APPLICANT);
				for (int c = 0; customFields != null && c < customFields.size(); c++) {
					CustomFieldData cData = customFields.get(c);
					fieldValuesMap.put(cData.getFieldName(), cData.getDisplayValue());
				}
			}
			if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
				CustomFieldManager customFieldManager = new CustomFieldManager();
				ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldDataForTables(applicantId);
				for (int c = 0; customFields != null && c < customFields.size(); c++) {
					CustomFieldData cData = customFields.get(c);
					fieldValuesMap.put(cData.getFieldName(), cData.getDisplayValue());
				}
			}
			
			ArrayList<EmploymentHistoryData> empList = applicantManager.getEmploymentHistoryInfo(applicantId);
			if (empList != null && empList.size() > 0) {
				EmploymentHistoryData empData = empList.get(0);
				Date stDate = empData.getEmployerFromDate(); // Year Of passing
				String empStartDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = empData.getEmployerToDate(); // Year Of passing
				String empEndDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_EMP_START_DATE_1, empStartDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_END_DATE_1, empEndDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_EMPLOYER_1, empData.getEmployerName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_DESIGNATION_1, empData.getDesignationName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_1, empData.getReasonForLeaving());
				fieldValuesMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_1, empData.getLastCtc());
				try{
				fieldValuesMap.put(ExportConstants.FLD_EMP_TYPE_1, Utils.isBlankOrNull(empData.getEmpType())?"":
					TypeOfProgram.getProgramByID(Integer.parseInt(empData.getEmpType())));
				}catch(Exception ex){
					
				}
				fieldValuesMap.put(ExportConstants.FLD_EMP_LOCATION_1, empData.getLocation());
				fieldValuesMap.put(ExportConstants.FLD_EMP_COUNTRY_1, empData.getCountry());
			}
			if (empList != null && empList.size() > 1) {
				EmploymentHistoryData empData = empList.get(1);
				Date stDate = empData.getEmployerFromDate(); // Year Of passing
				String empStartDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = empData.getEmployerToDate(); // Year Of passing
				String empEndDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_EMP_START_DATE_2, empStartDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_END_DATE_2, empEndDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_EMPLOYER_2, empData.getEmployerName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_DESIGNATION_2, empData.getDesignationName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_2, empData.getReasonForLeaving());
				fieldValuesMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_2, empData.getLastCtc());
				try{
				fieldValuesMap.put(ExportConstants.FLD_EMP_TYPE_2, Utils.isBlankOrNull(empData.getEmpType())?"":
					TypeOfProgram.getProgramByID(Integer.parseInt(empData.getEmpType())));
				}catch(Exception ex){
					
				}
				fieldValuesMap.put(ExportConstants.FLD_EMP_LOCATION_2, empData.getLocation());
				fieldValuesMap.put(ExportConstants.FLD_EMP_COUNTRY_2, empData.getCountry());
			}
			
			if (empList != null && empList.size() > 2) {
				EmploymentHistoryData empData = empList.get(2);
				Date stDate = empData.getEmployerFromDate(); // Year Of passing
				String empStartDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = empData.getEmployerToDate(); // Year Of passing
				String empEndDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_EMP_START_DATE_3, empStartDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_END_DATE_3, empEndDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_EMPLOYER_3, empData.getEmployerName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_DESIGNATION_3, empData.getDesignationName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_3, empData.getReasonForLeaving());
				fieldValuesMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_3, empData.getLastCtc());
				try{
				fieldValuesMap.put(ExportConstants.FLD_EMP_TYPE_3, Utils.isBlankOrNull(empData.getEmpType())?"":
					TypeOfProgram.getProgramByID(Integer.parseInt(empData.getEmpType())));
				}catch(Exception ex){
					
				}
				fieldValuesMap.put(ExportConstants.FLD_EMP_LOCATION_3, empData.getLocation());
				fieldValuesMap.put(ExportConstants.FLD_EMP_COUNTRY_3, empData.getCountry());
			}
			if (empList != null && empList.size() > 3) {
				EmploymentHistoryData empData = empList.get(3);
				Date stDate = empData.getEmployerFromDate(); // Year Of passing
				String empStartDate = (stDate == null) ? "" : Utils.getDateConvertedToString(stDate, "yyyy");
				Date enDate = empData.getEmployerToDate(); // Year Of passing
				String empEndDate = (enDate == null) ? "" : Utils.getDateConvertedToString(enDate, "yyyy");
				fieldValuesMap.put(ExportConstants.FLD_EMP_START_DATE_4, empStartDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_END_DATE_4, empEndDate);
				fieldValuesMap.put(ExportConstants.FLD_EMP_EMPLOYER_4, empData.getEmployerName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_DESIGNATION_4, empData.getDesignationName());
				fieldValuesMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_4, empData.getReasonForLeaving());
				fieldValuesMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_4, empData.getLastCtc());
				try{
				fieldValuesMap.put(ExportConstants.FLD_EMP_TYPE_4, Utils.isBlankOrNull(empData.getEmpType())?"":
					TypeOfProgram.getProgramByID(Integer.parseInt(empData.getEmpType())));
				}catch(Exception ex){
					
				}
				fieldValuesMap.put(ExportConstants.FLD_EMP_LOCATION_4, empData.getLocation());
				fieldValuesMap.put(ExportConstants.FLD_EMP_COUNTRY_4, empData.getCountry());
			}
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return fieldValuesMap;
	}
	
	public static LinkedHashMap<String, String> getAllFieldsMap(String stepLevel) {
		LinkedHashMap<String, String> fieldsMap = new LinkedHashMap<String, String>();
		fieldsMap.put(ExportConstants.FLD_ID, TPLabels.getLabel("exportToExcel.applicant.label.id"));
		fieldsMap.put(ExportConstants.FLD_NAME, TPLabels.getLabel("exportToExcel.applicant.label.name"));
		fieldsMap.put(ExportConstants.FLD_CURRENT_LOCATION, TPLabels.getLabel("exportToExcel.applicant.label.city"));
		fieldsMap.put(ExportConstants.FLD_EMAIL_1, TPLabels.getLabel("exportToExcel.applicant.label.email1"));
		fieldsMap.put(ExportConstants.FLD_EMAIL_2, TPLabels.getLabel("exportToExcel.applicant.label.email2"));
		fieldsMap.put(ExportConstants.FLD_PHONE_1, TPLabels.getLabel("exportToExcel.applicant.label.home_phone"));
		fieldsMap.put(ExportConstants.FLD_PHONE_2, TPLabels.getLabel("exportToExcel.applicant.label.cell_phone"));
		fieldsMap.put(ExportConstants.FLD_MOBILE, TPLabels.getLabel("exportToExcel.applicant.label.work_phone"));
		fieldsMap.put(ExportConstants.FLD_EXPERIENCE, TPLabels.getLabel("exportToExcel.applicant.label.experience"));
		fieldsMap.put(ExportConstants.FLD_POSITION_CODE, TPLabels.getLabel("common.position_code"));
		fieldsMap.put(ExportConstants.FLD_POSITION_NAME, TPLabels.getLabel("common.position_name"));
		fieldsMap.put(ExportConstants.FLD_DEPARTMENT, TPLabels.getLabel("exportToExcel.applicant.label.department"));
		fieldsMap.put(ExportConstants.FLD_STEP_NAME, TPLabels.getLabel("exportToExcel.applicant.label.step"));
		fieldsMap.put(ExportConstants.FLD_IMPORT_DATE, TPLabels.getLabel("exportToExcel.applicant.label.import_date"));
		fieldsMap.put(ExportConstants.FLD_SOURCE, TPLabels.getLabel("exportToExcel.applicant.label.source"));
		fieldsMap.put(ExportConstants.FLD_JOINING_DATE, TPLabels.getLabel("exportToExcel.applicant.label.joining_date"));
		fieldsMap.put(ExportConstants.FLD_IMPORTED_BY, TPLabels.getLabel("exportToExcel.applicant.label.imported_by"));
		fieldsMap.put(ExportConstants.FLD_NOTICE_PERIOD, TPLabels.getLabel("exportToExcel.applicant.label.notice_period"));
		fieldsMap.put(ExportConstants.FLD_LEVEL_OFFERED, TPLabels.getLabel("exportToExcel.applicant.label.level_offered"));
		fieldsMap.put(ExportConstants.FLD_DESIGNATION_OFFERED, TPLabels.getLabel("exportToExcel.applicant.label.designation_offered"));
		fieldsMap.put(ExportConstants.FLD_OFFERED_CTC, TPLabels.getLabel("exportToExcel.applicant.label.offered_ctc"));
		fieldsMap.put(ExportConstants.FLD_EMPLOYEE_ID, TPLabels.getLabel("exportToExcel.applicant.label.employee_id"));
		fieldsMap.put(ExportConstants.FLD_DATE_OF_BIRTH, TPLabels.getLabel("exportToExcel.applicant.label.dob"));
		fieldsMap.put(ExportConstants.FLD_SRNO, TPLabels.getLabel("exportToExcel.Sr_No"));
		fieldsMap.put(ExportConstants.FLD_SUB_DEPT, TPLabels.getLabel("exportToExcel.applicant.sub_dept"));
		fieldsMap.put(ExportConstants.FLD_STATUS, TPLabels.getLabel("exportToExcel.applicant.status"));
		
		fieldsMap.put(ExportConstants.FLD_JOINING_BONUS, TPLabels.getLabel("exportToExcel.applicant.label.joining_bonus"));
		fieldsMap.put(ExportConstants.FLD_VARIABLE_OFFERED, TPLabels.getLabel("exportToExcel.applicant.label.variable_offered"));
		
		if (ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_EDUCATION)) {
			fieldsMap.put(ExportConstants.FLD_YEAR_OF_PASSING_1, TPLabels.getLabel("exportToExcel.year_of_passing_1"));
			fieldsMap.put(ExportConstants.FLD_INSTITUTE_1, TPLabels.getLabel("exportToExcel.institute_name.label.name1"));
			fieldsMap.put(ExportConstants.FLD_DEGREE_1, TPLabels.getLabel("exportToExcel.degree_name.label.name1"));
			fieldsMap.put(ExportConstants.FLD_BRANCH_1, TPLabels.getLabel("exportToExcel.branch_name.label.name1"));
			fieldsMap.put(ExportConstants.FLD_YEAR_OF_PASSING_2, TPLabels.getLabel("exportToExcel.year_of_passing_2"));
			fieldsMap.put(ExportConstants.FLD_INSTITUTE_2, TPLabels.getLabel("exportToExcel.institute_name.label.name2"));
			fieldsMap.put(ExportConstants.FLD_DEGREE_2, TPLabels.getLabel("exportToExcel.degree_name.label.name2"));
			fieldsMap.put(ExportConstants.FLD_BRANCH_2, TPLabels.getLabel("exportToExcel.branch_name.label.name2"));
			fieldsMap.put(ExportConstants.FLD_YEAR_OF_PASSING_3, TPLabels.getLabel("exportToExcel.year_of_passing_3"));
			fieldsMap.put(ExportConstants.FLD_INSTITUTE_3, TPLabels.getLabel("exportToExcel.institute_name.label.name3"));
			fieldsMap.put(ExportConstants.FLD_DEGREE_3, TPLabels.getLabel("exportToExcel.degree_name.label.name3"));
			fieldsMap.put(ExportConstants.FLD_BRANCH_3, TPLabels.getLabel("exportToExcel.branch_name.label.name3"));
			fieldsMap.put(ExportConstants.FLD_GRADE_1, TPLabels.getLabel("exportToExcel.grade_name.label.name1"));
			fieldsMap.put(ExportConstants.FLD_GRADE_2, TPLabels.getLabel("exportToExcel.grade_name.label.name2"));
			fieldsMap.put(ExportConstants.FLD_GRADE_3, TPLabels.getLabel("exportToExcel.grade_name.label.name3"));
			fieldsMap.put(ExportConstants.FLD_YEAR_OF_PASSING_4, TPLabels.getLabel("exportToExcel.year_of_passing_4"));
			fieldsMap.put(ExportConstants.FLD_INSTITUTE_4, TPLabels.getLabel("exportToExcel.institute_name.label.name4"));
			fieldsMap.put(ExportConstants.FLD_DEGREE_4, TPLabels.getLabel("exportToExcel.degree_name.label.name4"));
			fieldsMap.put(ExportConstants.FLD_BRANCH_4, TPLabels.getLabel("exportToExcel.branch_name.label.name4"));
			fieldsMap.put(ExportConstants.FLD_GRADE_4, TPLabels.getLabel("exportToExcel.grade_name.label.name4"));
			fieldsMap.put(ExportConstants.FLD_TYPE_OF_PROG_1, TPLabels.getLabel("exportToExcel.type_of_program.label.name1"));
			fieldsMap.put(ExportConstants.FLD_START_DATE_1, TPLabels.getLabel("exportToExcel.Start_date.label.name1"));
			fieldsMap.put(ExportConstants.FLD_END_DATE_1, TPLabels.getLabel("exportToExcel.End_date.label.name1"));
			fieldsMap.put(ExportConstants.FLD_TYPE_OF_PROG_2, TPLabels.getLabel("exportToExcel.type_of_program.label.name2"));
			fieldsMap.put(ExportConstants.FLD_START_DATE_2, TPLabels.getLabel("exportToExcel.Start_date.label.name2"));
			fieldsMap.put(ExportConstants.FLD_END_DATE_2, TPLabels.getLabel("exportToExcel.End_date.label.name2"));
			fieldsMap.put(ExportConstants.FLD_TYPE_OF_PROG_3, TPLabels.getLabel("exportToExcel.type_of_program.label.name3"));
			fieldsMap.put(ExportConstants.FLD_START_DATE_3, TPLabels.getLabel("exportToExcel.Start_date.label.name3"));
			fieldsMap.put(ExportConstants.FLD_END_DATE_3, TPLabels.getLabel("exportToExcel.End_date.label.name3"));
			fieldsMap.put(ExportConstants.FLD_TYPE_OF_PROG_4, TPLabels.getLabel("exportToExcel.type_of_program.label.name4"));
			fieldsMap.put(ExportConstants.FLD_START_DATE_4, TPLabels.getLabel("exportToExcel.Start_date.label.name4"));
			fieldsMap.put(ExportConstants.FLD_END_DATE_4, TPLabels.getLabel("exportToExcel.End_date.label.name4"));
			
			fieldsMap.put(ExportConstants.FLD_EMP_START_DATE_1, TPLabels.getLabel("exportToExcel.emp_start_date.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_END_DATE_1, TPLabels.getLabel("exportToExcel.emp_end_date.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_TYPE_1, TPLabels.getLabel("exportToExcel.Emp_type.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_EMPLOYER_1, TPLabels.getLabel("exportToExcel.employer.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_DESIGNATION_1, TPLabels.getLabel("exportToExcel.designation.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_1, TPLabels.getLabel("exportToExcel.reason_for_leaving.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_1, TPLabels.getLabel("exportToExcel.last_drawn_ctc.label.name1"));
			fieldsMap.put(ExportConstants.FLD_EMP_LOCATION_1, TPLabels.getLabel("exportToExcel.location1.label.name"));
			fieldsMap.put(ExportConstants.FLD_EMP_COUNTRY_1, TPLabels.getLabel("exportToExcel.country1.label.name1"));
			
			fieldsMap.put(ExportConstants.FLD_EMP_START_DATE_2, TPLabels.getLabel("exportToExcel.emp_start_date.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_END_DATE_2, TPLabels.getLabel("exportToExcel.emp_end_date.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_TYPE_2, TPLabels.getLabel("exportToExcel.Emp_type.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_EMPLOYER_2, TPLabels.getLabel("exportToExcel.employer.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_DESIGNATION_2, TPLabels.getLabel("exportToExcel.designation.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_2, TPLabels.getLabel("exportToExcel.reason_for_leaving.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_2, TPLabels.getLabel("exportToExcel.last_drawn_ctc.label.name2"));
			fieldsMap.put(ExportConstants.FLD_EMP_LOCATION_2, TPLabels.getLabel("exportToExcel.location2.label.name"));
			fieldsMap.put(ExportConstants.FLD_EMP_COUNTRY_2, TPLabels.getLabel("exportToExcel.country2.label.name1"));
			
			fieldsMap.put(ExportConstants.FLD_EMP_START_DATE_3, TPLabels.getLabel("exportToExcel.emp_start_date.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_END_DATE_3, TPLabels.getLabel("exportToExcel.emp_end_date.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_TYPE_3, TPLabels.getLabel("exportToExcel.Emp_type.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_EMPLOYER_3, TPLabels.getLabel("exportToExcel.employer.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_DESIGNATION_3, TPLabels.getLabel("exportToExcel.designation.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_3, TPLabels.getLabel("exportToExcel.reason_for_leaving.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_3, TPLabels.getLabel("exportToExcel.last_drawn_ctc.label.name3"));
			fieldsMap.put(ExportConstants.FLD_EMP_LOCATION_3, TPLabels.getLabel("exportToExcel.location3.label.name"));
			fieldsMap.put(ExportConstants.FLD_EMP_COUNTRY_3, TPLabels.getLabel("exportToExcel.country3.label.name1"));
			
			fieldsMap.put(ExportConstants.FLD_EMP_START_DATE_4, TPLabels.getLabel("exportToExcel.emp_start_date.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_END_DATE_4, TPLabels.getLabel("exportToExcel.emp_end_date.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_TYPE_4, TPLabels.getLabel("exportToExcel.Emp_type.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_EMPLOYER_4, TPLabels.getLabel("exportToExcel.employer.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_DESIGNATION_4, TPLabels.getLabel("exportToExcel.designation.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_REASON_FOR_LEAVING_4, TPLabels.getLabel("exportToExcel.reason_for_leaving.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_LAST_DRAWN_CTC_4, TPLabels.getLabel("exportToExcel.last_drawn_ctc.label.name4"));
			fieldsMap.put(ExportConstants.FLD_EMP_LOCATION_4, TPLabels.getLabel("exportToExcel.location4.label.name"));
			fieldsMap.put(ExportConstants.FLD_EMP_COUNTRY_4, TPLabels.getLabel("exportToExcel.country4.label.name1"));
		}
		if (ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_CURRENT_EMPLOYER)) {
			fieldsMap.put(ExportConstants.FLD_CURRENT_EMPLOYER, TPLabels.getLabel("exportToExcel.applicant.label.current_employer"));
		}
		if (ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_CURRENT_CTC)) {
			fieldsMap.put(ExportConstants.FLD_CURRENT_CTC, TPLabels.getLabel("exportToExcel.applicant.label.current_ctc"));
		}
		if (ImportConfigurationManager.isImportOrEditFieldShow(ImportConfigurationConstants.FIELD_EXPECTED_CTC)) {
			fieldsMap.put(ExportConstants.FLD_EXPECTED_CTC, TPLabels.getLabel("exportToExcel.applicant.label.expected_ctc"));
		}
		

		// Custom Fileds
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int c = 0; c < customFields.size(); c++) {
				CustomFieldData cData = customFields.get(c);
				fieldsMap.put(cData.getFieldName(), cData.getFieldDisplayName());
			}
		}
		
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD)) {
			CustomFieldManager customFieldManager = new CustomFieldManager();
			ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT_TABLE_FIELD, CustomFieldConstants.INPUT_ALLOWED, true);
			for (int c = 0; c < customFields.size(); c++) {
				CustomFieldData cData = customFields.get(c);
				fieldsMap.put(cData.getFieldName(), cData.getFieldDisplayName());
			}
		}
		return fieldsMap;
	}

	
	private String nvl(String val) {
		return (val == null) ? "" : val;
	}
	/**
	 * @param cellValue
	 * @return if cellValue is in date format or not
	 */
	private boolean isDate(String cellValue) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		try {
			sdf.parse(cellValue);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	private void createHeader(Sheet s, Workbook wb, String[] flds, Fields fields, CreationHelper helper) {
		Row header = s.createRow(0);
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		
		for(int i = 0; i < flds.length; i++) {
			for(int j = 0; j < fields.getHeaders().size(); j++) {
				Field field = fields.getHeaders().get(j);
				if(field.getId().equals(flds[i])) {
					Cell c = header.createCell(i);
					c.setCellStyle(cs);
					c.setCellValue(helper.createRichTextString(field.getName()));						
					break;
				}
			}				
		}		
	}
	private void createHeaderRow(Sheet s, Workbook wb, CreationHelper helper, ArrayList<String> fieldList, LinkedHashMap<String, String> fieldsMap) {
		Row header = s.createRow(0);
		CellStyle cs = wb.createCellStyle();
		Font f = wb.createFont();
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cs.setFont(f);
		
		short column = 0;
			for(int j = 0; j <fieldList.size(); j++) {
				
					Cell c = header.createCell(column++);
					c.setCellStyle(cs);
					c.setCellValue(fieldsMap.get(fieldList.get(j)));	
				
			}				
		
	}
	
}
