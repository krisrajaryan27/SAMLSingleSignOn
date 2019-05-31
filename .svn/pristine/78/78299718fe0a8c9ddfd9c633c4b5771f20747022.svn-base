/**
 * 
 */
package com.talentPool.reports;

import static com.talentPool.common.CommonConstants.NEW_ARRAY;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.dynamicReports.utils.DynamicReportsColumnUtils;
import com.talentPool.masters.dataobject.DepartmentData;
import com.talentPool.masters.dataobject.MasterStepData;
import com.talentPool.masters.exception.MasterExistException;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.utils.UserUtils;

/**
 * @author shivprasad
 * 
 */
public class ReportUtils {

	public static String getReportExtension(String reportFormat) {
		String ext = ".html";
		if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_HTML)) {
			ext = ".html";
		}
		if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_PDF)) {
			ext = ".pdf";
		}
		if (reportFormat.equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
			ext = ".xls";
		}

		return ext;
	}
	
	/**
	 * Converts date range to criteria string to be displayed on report in application level defined date format.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static String getDateCriteria(String fromDate, String toDate) {
		return getDateCriteria(DateUtils.convertToSqlDate(fromDate, DateConstants.INPUT_FORMAT), 
									DateUtils.convertToSqlDate(toDate, DateConstants.INPUT_FORMAT) ,
											DateUtils.getSystemDatePattern());
	}

	/**
	 * Converts date range to criteria string to be displayed on report in application level defined date format.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static String getDateCriteria(Date fromDate, Date toDate) {
		return getDateCriteria(fromDate, toDate, DateUtils.getSystemDatePattern());
	}
	
	/**
	 * Converts date range to criteria string to be displayed on report in application level defined date format.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static String getDateCriteria(java.util.Date fromDate, java.util.Date toDate) {
		return getDateCriteria(fromDate, toDate, DateUtils.getSystemDatePattern());
	}
	
	/**
	 * Converts date range to criteria string to be displayed on report.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param format
	 *            date format expression
	 * @return
	 */
	public static String getDateCriteria(java.util.Date fromDate, java.util.Date toDate, String format) {
		String date_criteria = "";

		if (fromDate != null) {
			date_criteria = DateUtils.getDateFormated(fromDate, format);
		}
		if (toDate != null) {
			if (Utils.isBlankOrNull(date_criteria)) {
				date_criteria += " Up to ";
			} else {
				date_criteria += " to ";
			}

			date_criteria += DateUtils.getDateFormated(toDate, format);
		}
		if (!Utils.isBlankOrNull(date_criteria)) {
			date_criteria = "Date : " + date_criteria;
		}
		return date_criteria;
	}
	
	
	/**
	 * Converts as of date to criteria string to be displayed on report in application level defined date format.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static StringBuilder getAsOfDateCriteria(String asOfDate) {
		return getAsOfDateCriteria(DateUtils.convertToSqlDate(asOfDate, DateConstants.INPUT_FORMAT), DateUtils.getSystemDatePattern());
	}
	
	
	/**
	 * Converts as of date to criteria string to be displayed on report in application level defined date format.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static StringBuilder getAsOfDateCriteria(Date asOfDate) {
		return getAsOfDateCriteria(asOfDate, DateUtils.getSystemDatePattern());
	}
	
	/**
	 * Converts as of date to criteria string to be displayed on report in application level defined date format.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static StringBuilder getAsOfDateCriteria(java.util.Date asOfDate) {
		return getAsOfDateCriteria(asOfDate, DateUtils.getSystemDatePattern());
	}
	
	/**
	 * Converts As of date to criteria string to be displayed on report.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param format
	 *            date format expression
	 * @return
	 */
	public static StringBuilder getAsOfDateCriteria(java.util.Date asOfDate, String format) {
		StringBuilder date_criteria = new StringBuilder();
		
		if (asOfDate != null) {
			date_criteria.append(TPLabels.getLabel("custom_report.label.filter_criteria_asofdate")).append(": ");
			date_criteria.append(DateUtils.getDateFormated(asOfDate, format));
		}
		
		return date_criteria;
	}
	
	public static String getJSArrayForDateRange(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportConstants.TODAY+ "','" + TPLabels.getLabel("report.label.today") + "'),");
				sb.append("new SelectOption('" + ReportConstants.YESTERDAY+ "','" + TPLabels.getLabel("report.label.yesterday") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CURRENT_WEEK+ "','" + TPLabels.getLabel("report.label.current_week") + "'),");
				sb.append("new SelectOption('" + ReportConstants.PREVIOUS_WEEK+ "','" + TPLabels.getLabel("report.label.previous_week") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CURRENT_MONTH+ "','" + TPLabels.getLabel("report.label.current_month") + "'),");
				sb.append("new SelectOption('" + ReportConstants.PREVIOUS_MONTH+ "','" + TPLabels.getLabel("report.label.previous_month") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CURRENT_QUARTER+ "','" + TPLabels.getLabel("report.label.current_quarter") + "'),");
				sb.append("new SelectOption('" + ReportConstants.PREVIOUS_QUARTER+ "','" + TPLabels.getLabel("report.label.previous_quarter") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CURRENT_CALENDAR_YEAR+ "','" + TPLabels.getLabel("report.label.current_calendar_year") + "'),");
				sb.append("new SelectOption('" + ReportConstants.PREVIOUS_CALENDAR_YEAR+ "','" + TPLabels.getLabel("report.label.previous_calendar_year") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CURRENT_FINANCIAL_YEAR+ "','" + TPLabels.getLabel("report.label.current_financial_year") + "'),");
				sb.append("new SelectOption('" + ReportConstants.PREVIOUS_FINANCIAL_YEAR+ "','" + TPLabels.getLabel("report.label.previous_financial_year") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CUSTOM+ "','" + TPLabels.getLabel("report.label.custom") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for date range", e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	
	public static String getJSArrayForAsOfDate(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportConstants.TODAY+ "','" + TPLabels.getLabel("report.label.today") + "'),");
				sb.append("new SelectOption('" + ReportConstants.YESTERDAY+ "','" + TPLabels.getLabel("report.label.yesterday") + "'),");
				sb.append("new SelectOption('" + ReportConstants.END_OF_LAST_WEEK+ "','" + TPLabels.getLabel("report.label.end_of_last_week") + "'),");
				sb.append("new SelectOption('" + ReportConstants.END_OF_LAST_MONTH+ "','" + TPLabels.getLabel("report.label.end_of_last_month") + "'),");
				sb.append("new SelectOption('" + ReportConstants.END_OF_LAST_QUARTER+ "','" + TPLabels.getLabel("report.label.end_of_last_quarter") + "'),");
				sb.append("new SelectOption('" + ReportConstants.END_OF_LAST_CALENDAR_YEAR+ "','" + TPLabels.getLabel("report.label.end_of_last_calendar_year") + "'),");
				sb.append("new SelectOption('" + ReportConstants.END_OF_LAST_FINANCIAL_YEAR+ "','" + TPLabels.getLabel("report.label.end_of_last_financial_year") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CUSTOM+ "','" + TPLabels.getLabel("report.label.custom") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for As of date ", e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	
	/*For Interview List*/
	
	public static String getJSArrayForDateRangeOfFutureDates(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + ReportConstants.TODAY+ "','" + TPLabels.getLabel("report.label.today") + "'),");
				sb.append("new SelectOption('" + ReportConstants.TOMORROW+ "','" + TPLabels.getLabel("report.label.tomorrow") + "'),");
				sb.append("new SelectOption('" + ReportConstants.THIS_WEEK+ "','" + TPLabels.getLabel("report.label.current_week") + "'),");
				sb.append("new SelectOption('" + ReportConstants.NEXT_WEEK+ "','" + TPLabels.getLabel("report.label.next_week") + "'),");
				sb.append("new SelectOption('" + ReportConstants.THIS_MONTH+ "','" + TPLabels.getLabel("report.label.current_month") + "'),");
				sb.append("new SelectOption('" + ReportConstants.NEXT_N_DAYS+ "','" + TPLabels.getLabel("report.label.next_n_days") + "'),");
				sb.append("new SelectOption('" + ReportConstants.NEXT_N_WEEKS+ "','" + TPLabels.getLabel("report.label.next_n_weeks") + "'),");
				sb.append("new SelectOption('" + ReportConstants.CUSTOM+ "','" + TPLabels.getLabel("report.label.custom") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for Future dates ", e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}	
	
	public static String getJSArrayForAuditEntityTypes(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_USER+ "','" + TPLabels.getLabel("common.user") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_POSITION+ "','" + TPLabels.getLabel("common.position") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_CANDIDATE+ "','" + TPLabels.getLabel("common.candidate") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_ROLE+ "','" + TPLabels.getLabel("common.role") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_REPORT_LEVEL+ "','" + TPLabels.getLabel("common.report_level") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_REQUISITION+ "','" + TPLabels.getLabel("common.requisition") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_LOGGED_IN+ "','" + TPLabels.getLabel("login.label.title") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_LOGGED_OUT+ "','" + TPLabels.getLabel("header.label.log_off") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_HIRING_PROGRESS+ "','" + TPLabels.getLabel("common.hiring_process") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_REPORT+ "','" + TPLabels.getLabel("common.report") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for audit entity types", e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	
	public static String getJSArrayForEntityTypes(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + AuditConstants.TYPE_ALL+ "','" + TPLabels.getLabel("common.all") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_USER_TYPE+ "','" + TPLabels.getLabel("common.user.profile") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_POSITION_TYPE+ "','" + TPLabels.getLabel("common.position") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_CANDIDATE_TYPE+ "','" + TPLabels.getLabel("common.candidate") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_ROLE_TYPE+ "','" + TPLabels.getLabel("common.role") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_PERMISSION+ "','" + TPLabels.getLabel("common.permission") + "'),");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_APPLICANT_TYPE+ "','" + TPLabels.getLabel("common.audit_applicant") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for audit entity types", e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	public static String getJSArrayForAuditTypes(){
		StringBuffer sb = new StringBuffer();
		try {
				sb.append("[");
				sb.append("new SelectOption('" + AuditConstants.AUDIT_ALL+ "','" + TPLabels.getLabel("common.type_all") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_ADDED+ "','" + TPLabels.getLabel("common.type_added") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_DELETED+ "','" + TPLabels.getLabel("common.type_deleted") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_MODIFIED+ "','" + TPLabels.getLabel("common.type_modified") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_BLACKLISTED+ "','" + TPLabels.getLabel("common.type_blacklisted") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_GENERATED+ "','" + TPLabels.getLabel("common.type_generated") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_LOGGED_IN+ "','" + TPLabels.getLabel("common.type_logged_in") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_LOGGED_OUT+ "','" + TPLabels.getLabel("common.type_logged_out") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_LOGIN_EXPIRED+ "','" + TPLabels.getLabel("common.type_login_expired") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_REJECTED+ "','" + TPLabels.getLabel("common.type_rejected") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_APPROVED+ "','" + TPLabels.getLabel("common.type_approved") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_ONHOLD+ "','" + TPLabels.getLabel("common.type_hold") + "'),");
				sb.append("new SelectOption('" + AuditConstants.TYPE_HIRING_PROGRESS_MOVED+ "','" + TPLabels.getLabel("common.type_moved") + "')");
				sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error("error while generating javascript array for audit entity types", e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	
	public static String getJSArrayGrades(){
		MastersManager mastersManager = new MastersManager();
		ArrayList grades = null;
		try {
			grades = mastersManager.getAllBudgetGrades();
		} catch (MasterExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsArrayGrades = CommonUtils.getListJavaScriptArrayWithProperties(grades,"itemId","itemName");
		return jsArrayGrades;
	}
	
	public static String getJSArrayBands(){
		MastersManager mastersManager = new MastersManager();
		ArrayList bands = null;
		try {
			bands = mastersManager.getAllBudgetBands();
		} catch (MasterExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsArrayBands = CommonUtils.getListJavaScriptArrayWithProperties(bands,"itemId","itemName");
		return jsArrayBands;
	}
	
	public static String getFieldsXML(PermissionSet permissionSet,String reportName, String reportType){
		String xmlFile = "";
		try {
			//LinkedHashMap<String, String> fieldsMap = DynamicReportsColumnUtils.getColumnList(permissionSet,reportName, reportType);
			LinkedHashMap<String, String> fieldsMap = DynamicReportsColumnUtils.getOptionalFields(permissionSet,reportName, reportType);
			xmlFile = getXMLofFields(fieldsMap);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return xmlFile;
	}
	
	public static String getXMLofFields(LinkedHashMap<String, String> fieldsMap) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("rows");
			Iterator<String> itr = fieldsMap.keySet().iterator();
			int cnt=1;
			while (itr.hasNext()) {
				String key = itr.next();
				String value = fieldsMap.get(key);
				AttributesImpl atr = new AttributesImpl();
				
				//atr.addAttribute("", "id", "", "", key);
				atr.addAttribute("", "id", "", "", String.valueOf(cnt));
				wr.startElement("", "row", "", atr);

				atr = new AttributesImpl();
				atr.addAttribute("", "name", "", "", "key");
				wr.startElement("", "userdata", "", atr);
				
				wr.characters(key);
				wr.endElement("userdata");
				
				wr.startElement("cell");
				wr.characters(value);
				wr.endElement("cell");

				wr.endElement("row");
				cnt++;
			}

			wr.endElement("rows");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sWr.getBuffer().toString();
	}
	
	public static String getJSArrayForFields(PermissionSet permissionSet,String reportName, String reportType){
		String jsArray = "";
		try {
			LinkedHashMap<String, String> fieldsMap = DynamicReportsColumnUtils.getOptionalFields(permissionSet,reportName,reportType);
			jsArray = ReportUtils.getJSArrayForFields(fieldsMap);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return jsArray;
	}
	
	public static String getJSArrayForFields(LinkedHashMap<String, String> fieldsMap) {
		StringBuffer sb = new StringBuffer();
		try {
			Iterator<String> itr = fieldsMap.keySet().iterator();
			sb.append("[");
			while (itr.hasNext()) {
				String key = itr.next();
				String value = fieldsMap.get(key);
				sb.append("new SelectOption('" + key+ "','" + value + "'),");
			}
			sb.deleteCharAt(sb.length()-1); //to remove last ,
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return sb.toString();
	}
	
	public static String getJSArrayReportTemplateType(){
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("[");
			sb.append("new SelectOption('" + ReportVersionConstants.REPORT_PENDING_OFFER+ "','" + ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_PENDING_OFFER) + "'),");
			sb.append("new SelectOption('" + ReportVersionConstants.REPORT_TIME_TO_HIRE+ "','" + ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_TIME_TO_HIRE) + "'),");
			sb.append("new SelectOption('" + ReportVersionConstants.REPORT_HIRING_ACTIVITY+ "','" + ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_HIRING_ACTIVITY) + "'),");
			sb.append("new SelectOption('" + ReportVersionConstants.REPORT_OFFER_CTC+ "','" + ReportVersionConstants.getReportTitle(ReportVersionConstants.REPORT_OFFER_CTC) + "')");
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuffer(NEW_ARRAY);
		}
		return sb.toString();
	}
	
	public static ArrayList<String> excelFieldName(String relativeFilePath, int sheetAt, int rowAt) {
		ArrayList<String> excelFieldName = new ArrayList<String>();
		try {
			String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, relativeFilePath);
			Workbook wb = WorkbookFactory.create(new FileInputStream(new File(filePath)));
			Sheet sheet = wb.getSheetAt(sheetAt);
			Row row = sheet.getRow(rowAt-1);
			if(row!=null){
				for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
					Cell cell = row.getCell(i);
					int cellType = cell.getCellType();
					if(cellType==Cell.CELL_TYPE_NUMERIC){
						excelFieldName.add(""+cell.getNumericCellValue());
					}else{
						excelFieldName.add(cell.getStringCellValue());
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return excelFieldName;
	}
	
	
	
	public String getXMLforActiveSources(List sources, String loggedInUserId, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (sources != null && sources.size() > 0) {
				
				for (int i = 0; i < sources.size(); i++) {
					SimpleDataObject sourceData = (SimpleDataObject) sources.get(i);
					
					
					String sourceId = Utils.isBlankOrNull(sourceData.getString("sourceId"))?"":sourceData.getString("sourceId");						
					String sourceName = Utils.isBlankOrNull(sourceData.getString("sourceTitle"))?"":sourceData.getString("sourceTitle");
					String displayName = sourceName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(sourceId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "sourceName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	public String getXMLforSourceCategories(List sources, String loggedInUserId, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (sources != null && sources.size() > 0) {
				
				for (int i = 0; i < sources.size(); i++) {
					//SourceData sourceData = (SourceData) sources.get(i);					
					SimpleDataObject sourceData = (SimpleDataObject) sources.get(i);
					String sourceCategoryId = Utils.isBlankOrNull(sourceData.getString("sourceTypeId"))?"":sourceData.getString("sourceTypeId");						
					String sourceCategoryName = Utils.isBlankOrNull(sourceData.getString("sourceType"))?"":sourceData.getString("sourceType");
					String displayName = sourceCategoryName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(sourceCategoryId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "sourceCategoryName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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

	public String getUsersFilterXMLForReports(String roles){
		PositionManager positionManager = new PositionManager(); 
		List<LoginData> users = positionManager.getUsersForRole(roles);
		return UserUtils.getXMLforActiveUsers(users);
	}
	
	public String getPositionGridXML(List positions) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (positions != null && positions.size() > 0) {
				
				for (int i = 0; i < positions.size(); i++) {
					SimpleDataObject sourceData = (SimpleDataObject) positions.get(i);					
					
					String positionId = Utils.isBlankOrNull(sourceData.getString("positionId"))?"":sourceData.getString("positionId");						
					String positionName = Utils.isBlankOrNull(sourceData.getString("positionTitle"))?"":sourceData.getString("positionTitle");
					String displayName = positionName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(positionId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "positionName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	
	public String getXMLforUserRoles(ArrayList<SimpleDataObject> roles, String loggedInUserId, PermissionSet permissionSet) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (roles != null && roles.size() > 0) {
				
				for (int i = 0; i < roles.size(); i++) {										
					SimpleDataObject roleData = (SimpleDataObject) roles.get(i);
					String roleId = Utils.isBlankOrNull(roleData.getString("roleId"))?"":roleData.getString("roleId");						
					String roleTitle = Utils.isBlankOrNull(roleData.getString("roleTitle"))?"":roleData.getString("roleTitle");
					String displayName = roleTitle;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(roleId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "roleTitle");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	public String getXMLforDepartments(ArrayList<DepartmentData> departments) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (departments != null && departments.size() > 0) {
				
				for (int i = 0; i < departments.size(); i++) {
					DepartmentData departmentData = (DepartmentData) departments.get(i);					
					
					String departmentId = departmentData.getItemId()+"";
					String departmentName = departmentData.getItemName();
					String displayName = departmentName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(departmentId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "departmentName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	public String getUsersFilterXMLForReports(){
		return getUsersFilterXMLForReports(UserConstants.ROLE_HR_MANAGER + ", " + UserConstants.ROLE_RECRUITER);
	}	
	
	public String getStageGridXML(List<MasterStepData> stages) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (stages != null && stages.size() > 0) {
				
				for (int i = 0; i < stages.size(); i++) {
					MasterStepData stageData = (MasterStepData) stages.get(i);					
					
					String stageId = Utils.isBlankOrNull(stageData.getStepLevel())?"":stageData.getStepLevel();						
					String stageName = Utils.isBlankOrNull(stageData.getStage())?"":stageData.getStage();
					String displayName = stageName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(stageId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "stageName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	public String getStepGridXML(List<MasterStepData> steps) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (steps != null && steps.size() > 0) {
				
				for (int i = 0; i < steps.size(); i++) {
					MasterStepData stepData = (MasterStepData) steps.get(i);					
					
					String stepId = Utils.isBlankOrNull(stepData.getStepId())?"":stepData.getStepId();						
					String stepName = Utils.isBlankOrNull(stepData.getStepName())?"":stepData.getStepName();
					String displayName = stepName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(stepId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "stepName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	public String getStatusesGridXML(List<SimpleDataObject> statuses) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);		
		try {
			wr.startDocument();
			wr.startElement("rows");
			if (statuses != null && statuses.size() > 0) {
				
				for (int i = 0; i < statuses.size(); i++) {
					SimpleDataObject statusData = (SimpleDataObject) statuses.get(i);					
					
					String statusId = Utils.isBlankOrNull(statusData.getString("statusId"))?"":statusData.getString("statusId");						
					String statusName = Utils.isBlankOrNull(statusData.getString("statusName"))?"":statusData.getString("statusName");
					String displayName = statusName;
					
					AttributesImpl atr = new AttributesImpl();					
					atr.addAttribute("", "id", "", "", String.valueOf(statusId));
					wr.startElement("", "row", "", atr);
										
					atr = new AttributesImpl();
					atr.addAttribute("", "name", "", "", "statusName");
					wr.startElement("", "userdata", "", atr);
					
					wr.characters(displayName);
					wr.endElement("userdata");
					
					wr.startElement("cell");
					wr.characters(displayName);
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
	
	public static String getJSArrayReportLevel(){
		AdminManager adminManager = new AdminManager();
		ArrayList reportLevels = null;
		reportLevels = adminManager.getLevels();
		
		String jsArrayGrades = CommonUtils.getListJavaScriptArrayWithProperties(reportLevels,"levelId","levelName");
		
		return jsArrayGrades;
	}
	
	public static String getGroupByColumnsJSArray(String reportId){
		if(ReportVersionConstants.REPORT_REJECTED_CANDIDATES.equals(reportId)){
			return getGroupByColumnsForRejectedCandidatesReport();
		}
		return "new Array()";
	}
	
	public static String getGroupByColumnsForRejectedCandidatesReport(){
		StringBuilder sb = new StringBuilder();
		try {
			sb.append("[");
			Utils.getJSArraySelectOption("-1","---Select---", sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_REJECTED_BY, sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_POSITION_TITLE, sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_POSITION_CODE, sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_DEPARTMENT, sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_CANDIDATE_SOURCE, sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_STEP_LEVEL, sb);
			sb.append(CommonConstants.DEFAULT_DELIMITER);
			DynamicReportsColumnUtils.getJSArraySelectOption(ReportDesignConstants.COLUMN_STEP_NAME, sb);
			sb.append("]");
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			sb = new StringBuilder("new Array()");
		}
		return sb.toString();
	}
	
	public static String getServiceNameForCustomizedReport(String reportName){
		String serviceName = ReportConstants.SERVICE_PACKAGE + reportName.substring(0, 1).toUpperCase() 
		 		+ reportName.substring(1, reportName.length()) + ReportConstants.SERVICE_SUFFIX;
		return serviceName;
	}
	
	public static String excuteGET(String targetURL) {
		URL url;
		HttpURLConnection connection = null;
		try {
			StringBuilder sb = new StringBuilder("grant_type=client_credentials");
			sb.append("&client_id=").append(TPLabels.getLabel("report.newtalentpool.client_id"));
			sb.append("&client_secret=").append(TPLabels.getLabel("report.newtalentpool.client_secret"));

			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Authorization", "Basic cmVwb3J0LWNsaWVudDpyZXBvcnRfY2xpZW50X3NlY3JldA==");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(sb.toString());
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();

		} catch (Exception e) {

			TPLogger.getLogger().error("Exception occured during fetching auth token from new talentpool application"+e);
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static InputStream excutePOST(String targetURL, String tokenType, String authToken, String fileName,String sb) {
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", tokenType + " " + authToken);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(sb.toString());
			wr.flush();
			wr.close();

			// Get Response
			InputStream inputStream = connection.getInputStream();

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(fileName);

			int bytesRead = -1;
			byte[] buffer = new byte[512];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			inputStream.close();
			return inputStream;

		} catch (Exception e) {

			TPLogger.getLogger().error("Exception occured during report download from new talentpool application"+e);
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
