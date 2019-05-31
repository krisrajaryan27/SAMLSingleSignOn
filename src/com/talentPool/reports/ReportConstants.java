/**
 * 
 */
package com.talentPool.reports;

import java.util.HashMap;

import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class ReportConstants {	
	public static String JRXML_FOLDER_PATH;
	public static String REPORT_DESTINATION_FOLDER;
	public static String REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH;
	public static final String DYNA_REPORT_FOLDER = "dynaReport";
	
	// HTML Report Header Parameters
	public static final String DEFAULT_REPORT_HEADER_PARAM_TITLE = "<title>"
			+ TPLabels.getLabel("title.common") + " Report</title>\n";
	public static final String DEFAULT_REPORT_HEADER_PARAM_CACHE_CONTROL = "<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-store,no-cache\">\n";
	public static final String DEFAULT_REPORT_HEADER_PARAM_PRAGMA = "<META HTTP-EQUIV=\"Pragma\" CONTENT=\"NO-CACHE\">\n";
	public static final String DEFAULT_REPORT_HTML_HEADER = DEFAULT_REPORT_HEADER_PARAM_TITLE+
															DEFAULT_REPORT_HEADER_PARAM_CACHE_CONTROL+
															DEFAULT_REPORT_HEADER_PARAM_PRAGMA;
	
	// REPORT FILTER CONSTANTS
	public static final String FILTER_OPTION_SELECT="-1";
	public static final String FILTER_ALL = "0";
	public static final String FILTER_ALL_POSITIONS = "1";
	public static final String FILTER_OPEN_POSITIONS = "2";
	public static final String FILTER_SPECIFIC_POSITION = "3";
	public static final String FILTER_SPECIFIC_DEPARTMENT = "4";
	public static final String FILTER_IMPORTED_BY = "5";
	public static final String FILTER_SOURCE = "6";
	public static final String FILTER_HR ="7";
	public static final String FILTER_ALL_DEPARTMENT_CHECK = "-1";
	
	//Candidate Status Report
	public static final String FILTER_MOVED_BY = "8";
	public static final String FILTER_REJECTED_BY = "9";
	public static final String FILTER_OPEN_AND_ONHOLD_POSITIONS = "10";
		
	public static final String FILTER_ALL_DEPARTMENTS = "11";
	public static final String FILTER_POSITION_STEPS = "12";
	public static final String FILTER_SPECIFIC_USER = "13";
	public static final String FILTER_SPECIFIC_EXPENSE_TYPE = "14";
	public static final String FILTER_SPECIFIC_ACTIVITY_TYPE = "15";
	public static final String FILTER_ACTIVE_BUDGET_ITEMS = "16";
	public static final String FILTER_SPECIFIC_POSITION_OWNER = "17";
	
	public static final String FILTER_SPECIFIC_SOURCE_CATEGORY = "18";
	public static final String FILTER_SPECIFIC_SOURCE = "19";
	
	public static final String FILTER_SPECIFIC_USER_ROLE = "20";
	
	public static final String FILTER_SPECIFIC_STEP = "21";
	public static final String FILTER_SPECIFIC_STAGE = "22";
	
	//JRXML
	public static final String JRXML_MONTHLY_JOINING_SUMMARY_REPORT="monthly_joining_summary_report.jrxml";
	public static final String JRXML_MONTHLY_JOINING_DETAILS_REPORT="monthly_joining_details_report.jrxml";
	public static final String JRXML_MONTHLY_JOINING_CHART_REPORT="monthly_joining_chart_report.jrxml";
	public static final String JRXML_HIRING_EFFICIENCY_SUMMARY_REPORT="hiring_efficiency_summary_report.jrxml";
	public static final String JRXML_HIRING_EFFICIENCY_DETAILS_REPORT="hiring_efficiency_details_report.jrxml";
	public static final String JRXML_INTERVIEW_LIST_REPORT="interview_list_report.jrxml";
	public static final String JRXML_INTERVIEW_STATUS_REPORT="interview_status_report.jrxml";
	public static final String JRXML_USER_ACTIVITY_REPORT="user_activity_report.jrxml";
	public static final String JRXML_POSITION_ACTIVITY_REPORT="position_activity_report.jrxml";
	public static final String JRXML_SOURCE_WISE_HIRING_REPORT="source_wise_hiring.jrxml";
	public static final String JRXML_HIRING_STATUS_SUMMARY_REPORT="hiring_status_summary_report.jrxml";
	public static final String JRXML_HIRING_STATUS_DETAIL_REPORT="hiring_status_detail_report.jrxml";	
	public static final String JRXML_SOURCEWISE_IMPORT_REPORT="sourcewise_import_report.jrxml";
	public static final String JRXML_APPLICANT_DETAILS_REPORT="applicant_details_report.jrxml";
	public static final String JRXML_OFFER_TO_JOINED_REPORT = "offer_to_joined_report.jrxml";
	
	public static final String JRXML_DETAILED_OFFER_TO_JOINED_REPORT = "detailed_offer_to_joined_report.jrxml";
	public static final String JRXML_OFFER_TO_JOINED_DETAILED_REPORT = "offer_to_joined_detailed_report.jrxml";
	public static final String JRXML_PENDING_ACTIONS_REPORT="pending_actions_report.jrxml";
	public static final String JRXML_HIRING_FUNNEL_SUMMARY_REPORT="hiring_funnel_summary_report.jrxml";
	public static final String JRXML_HIRING_FUNNEL_DETAIL_REPORT="hiring_funnel_detail_report.jrxml";	
	public static final String JRXML_OVERALL_RECRUITMENT_COST_SUMMARY_REPORT = "overall_recruitment_cost_summary_report.jrxml";	
	public static final String JRXML_OVERALL_RECRUITMENT_COST_DETAIL_REPORT = "overall_recruitment_cost_detail_report.jrxml";
	public static final String JRXML_POSITIONWISE_RECRUITMENT_COST_SUMMARY_REPORT = "positionwise_recruitment_cost_summary_report.jrxml";
	public static final String JRXML_POSITIONWISE_RECRUITMENT_COST_DETAIL_REPORT = "positionwise_recruitment_cost_detail_report.jrxml";
	public static final String JRXML_SOURCEWISE_RECRUITMENT_COST_SUMMARY_REPORT = "sourcewise_recruitment_cost_summary_report.jrxml";
	public static final String JRXML_SOURCEWISE_RECRUITMENT_COST_DETAIL_REPORT = "sourcewise_recruitment_cost_detail_report.jrxml";
	public static final String JRXML_TRANSACTION_COST_REPORT = "transaction_cost_report.jrxml";
	public static final String JRXML_FEEDBACK_FORM_REPORT="feedback_form_report.jrxml";
	public static final String JRXML_CANDIDATE_STATUS_REPORT="candidate_status_report.jrxml";	
	public static final String JRXML_CONSOLIDATED_FEEDBACK_FORM_REPORT="consolidated_feedback_form_report.jrxml";
	public static final String JRXML_PRINT_FEEDBACK_FORM_REPORT="print_feedback_form_report.jrxml";
	public static final String JRXML_AUDIT_TRAIL="audit_taril_report.jrxml";
	public static final String JRXML_ALL_CONSOLIDATED_FEEDBACK_FORM_REPORT="all_consolidated_feedback_form_report.jrxml";
	public static final String JRXML_CLOSED_POSITION_TAT_REPORT="closed_position_tat_report.jrxml";
	
	public static final String  JRXML_OFFER_CTC = "offer_ctc_report.jrxml";
	// Custom Reports
	public static final String XLS_CANDIDATE_OFFERS_REPORT="candidate_offers_report.xls";
	
	// REPORT SORT BY CONSTANTS
	public static final String SORT_BY_POSITION_NAME = "1";
	public static final String SORT_BY_DEPARTMENT_NAME = "2";
	public static final String SORT_BY_DATE_CREATED = "3";
	public static final String SORT_BY_CANDIDATE_NAME = "4" ;
	public static final String SORT_BY_CANDIDATE_STATUS = "5";
	public static final String SORT_BY_USER = "6" ;
	public static final String SORT_BY_DATE_OF_IMPORT = "7";
	public static final String SORT_BY_SOURCE ="8";
	public static final String SORT_BY_IMPORTED_BY = "9";
	public static final String SORT_BY_DATE_IMPORTED = "10";

	// REPORT OUTPUT FORMAT
	public static final String FORMAT_HTML = "1";
	public static final String FORMAT_PDF = "2";
	public static final String FORMAT_EXCEL = "3";
	public static final String FORMAT_PRE_FORMATTED = "4";
	
	// REPORT TYPE SUMMARY or DETAILS
	public static final String REPORT_TYPE_SUMMARY="0";
	public static final String REPORT_TYPE_DETAILS="1";
	public static final String REPORT_TYPE_CHART="2";	
	public static final String REPORT_FORMAT_CSV="1";	
	public static final String REPORT_FORMAT_NON_CSV="0";	
	
	// REPORT CONSTANT FOR DATE RANGE
	public static final String CUSTOM = "0";
	public static final String TODAY = "1";
	public static final String YESTERDAY = "2";
	public static final String CURRENT_WEEK = "3";
	public static final String PREVIOUS_WEEK = "4";
	public static final String CURRENT_MONTH = "5";
	public static final String PREVIOUS_MONTH = "6";
	public static final String CURRENT_QUARTER = "7";
	public static final String PREVIOUS_QUARTER = "8";
	public static final String CURRENT_CALENDAR_YEAR = "9";
	public static final String PREVIOUS_CALENDAR_YEAR = "10";
	public static final String CURRENT_FINANCIAL_YEAR = "11";
	public static final String PREVIOUS_FINANCIAL_YEAR = "12";
	
	public static final String END_OF_LAST_WEEK = "13";
	public static final String END_OF_LAST_MONTH = "14";
	public static final String END_OF_LAST_QUARTER = "15";
	public static final String END_OF_LAST_FINANCIAL_YEAR = "16";
	public static final String END_OF_LAST_CALENDAR_YEAR = "17";
	
	public static final String TOMORROW = "18";
	public static final String NEXT_WEEK = "19";
	public static final String THIS_WEEK = "20";//from today till week end
	public static final String THIS_MONTH = "21";	//from today till month end
	public static final String NEXT_N_DAYS = "22";	
	public static final String NEXT_N_WEEKS = "23";
	
	public static final String SOURCE_EMP_REFERRALS_ALL="-1";
	
	
	// REPORT SCHEDULER CONSTANT
	public static final String REPORT_SCHEDULER_DAILY = "1";
	public static final String REPORT_SCHEDULER_WEEKLY = "2";
	public static final String REPORT_SCHEDULER_MONTHLY = "3";
	
	public static final String REPORT_SCHEDULER_DAILY_WEEKDAY = "1";
	public static final String REPORT_SCHEDULER_DAILY_EVERYDAY = "2";
	
	public static final String REPORT_SCHEDULER_WEEKLY_MONDAY = "1";
	public static final String REPORT_SCHEDULER_WEEKLY_TUESDAY = "2";
	public static final String REPORT_SCHEDULER_WEEKLY_WEDNESDAY = "3";
	public static final String REPORT_SCHEDULER_WEEKLY_THURSDAY = "4";
	public static final String REPORT_SCHEDULER_WEEKLY_FRIDAY = "5";
	public static final String REPORT_SCHEDULER_WEEKLY_SATURDAY = "6";
	public static final String REPORT_SCHEDULER_WEEKLY_SUNDAY = "7";
	
	//Customization report constants
	public static final String SERVICE_PACKAGE="customization.talentpool.service.";
	public static final String SERVICE_SUFFIX = "ReportService";
	public static final String CUSTOMIZATION_REPORT_ERROR_JRXML="customization_report_not_present.jrxml";
	
	static {
		loadDateRangeConstantMap();	
		loadReportSchedulerConstantMap();
		loadReportFormantConstantMap();
		loadReportTypeConstantMap();
		loadSortByConstantMap();
	}
	public static HashMap<String, String> mapReportSchedulerConstant;
	public static HashMap<String, String> mapDateRangeConstant;
	public static HashMap<String, String> mapReportFormatConstant;
	public static HashMap<String, String> mapReportTypeConstant;
	public static HashMap<String, String> mapSortByConstant;
	
	private static void loadReportFormantConstantMap() {
		mapReportFormatConstant = new HashMap<String, String>();
		mapReportFormatConstant.put(FORMAT_HTML,"Html" );
		mapReportFormatConstant.put(FORMAT_PDF,"Pdf" );
		mapReportFormatConstant.put(FORMAT_EXCEL,"Excel" );		
		mapReportFormatConstant.put(FORMAT_PRE_FORMATTED,"Pre formatted Excel" );
	}
	private static void loadReportTypeConstantMap() {
		mapReportTypeConstant = new HashMap<String, String>();
		mapReportTypeConstant.put(REPORT_TYPE_SUMMARY,"Summary" );
		mapReportTypeConstant.put(REPORT_TYPE_DETAILS,"Details" );
		mapReportTypeConstant.put(REPORT_TYPE_CHART,"Chart" );		
	}
	
	private static void loadDateRangeConstantMap() {
		mapDateRangeConstant = new HashMap<String, String>();
		mapDateRangeConstant.put(TODAY,TPLabels.getLabel("report.label.today"));
		mapDateRangeConstant.put(YESTERDAY,TPLabels.getLabel("report.label.yesterday") );
		mapDateRangeConstant.put(CURRENT_WEEK,TPLabels.getLabel("report.label.current_week") );
		mapDateRangeConstant.put(PREVIOUS_WEEK,TPLabels.getLabel("report.label.previous_week") );
		mapDateRangeConstant.put(CURRENT_MONTH,TPLabels.getLabel("report.label.current_month") );
		mapDateRangeConstant.put(PREVIOUS_MONTH,TPLabels.getLabel("report.label.previous_month") );
		mapDateRangeConstant.put(CURRENT_QUARTER,TPLabels.getLabel("report.label.current_quarter") );
		mapDateRangeConstant.put(PREVIOUS_QUARTER,TPLabels.getLabel("report.label.previous_quarter") );
		mapDateRangeConstant.put(CURRENT_CALENDAR_YEAR,TPLabels.getLabel("report.label.current_calendar_year") );
		mapDateRangeConstant.put(PREVIOUS_CALENDAR_YEAR,TPLabels.getLabel("report.label.previous_calendar_year") );
		mapDateRangeConstant.put(CURRENT_FINANCIAL_YEAR,TPLabels.getLabel("report.label.current_financial_year") );
		mapDateRangeConstant.put(PREVIOUS_FINANCIAL_YEAR,TPLabels.getLabel("report.label.previous_financial_year") );
		mapDateRangeConstant.put(END_OF_LAST_WEEK,TPLabels.getLabel("report.label.end_of_last_week") );
		mapDateRangeConstant.put(END_OF_LAST_MONTH,TPLabels.getLabel("report.label.end_of_last_month") );
		mapDateRangeConstant.put(END_OF_LAST_QUARTER,TPLabels.getLabel("report.label.end_of_last_quarter") );
		mapDateRangeConstant.put(END_OF_LAST_FINANCIAL_YEAR,TPLabels.getLabel("report.label.end_of_last_financial_year") );
		
		mapDateRangeConstant.put(TOMORROW,TPLabels.getLabel("report.label.tomorrow") );	
		mapDateRangeConstant.put(NEXT_WEEK,TPLabels.getLabel("report.label.current_week") );
		mapDateRangeConstant.put(THIS_WEEK,TPLabels.getLabel("report.label.next_week") );
		mapDateRangeConstant.put(THIS_MONTH,TPLabels.getLabel("report.label.current_month") );
		mapDateRangeConstant.put(NEXT_N_DAYS,TPLabels.getLabel("report.label.next_n_days") );
		mapDateRangeConstant.put(NEXT_N_WEEKS,TPLabels.getLabel("report.label.next_n_weeks") );		
	}
	
	private static void loadReportSchedulerConstantMap() {
		mapReportSchedulerConstant = new HashMap<String, String>();
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_MONDAY,"MON" );
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_TUESDAY,"TUE" );
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_WEDNESDAY,"WED" );
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_THURSDAY,"THU" );
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_FRIDAY,"FRI" );
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_SATURDAY,"SAT" );
		mapReportSchedulerConstant.put(REPORT_SCHEDULER_WEEKLY_SUNDAY,"SUN" );
	}
	
	private static void loadSortByConstantMap() {
		mapSortByConstant = new HashMap<String, String>();
		mapSortByConstant.put(SORT_BY_POSITION_NAME,"Position Name" );
		mapSortByConstant.put(SORT_BY_DEPARTMENT_NAME,"Department Name" );
		mapSortByConstant.put(SORT_BY_DATE_CREATED,"Date Created" );
		mapSortByConstant.put(SORT_BY_CANDIDATE_NAME,"Candidate Name" );
		mapSortByConstant.put(SORT_BY_CANDIDATE_STATUS,"Current Status" );
		mapSortByConstant.put(SORT_BY_USER,"Users" );
		mapSortByConstant.put(SORT_BY_DATE_OF_IMPORT,"Date Of Import" );
		mapSortByConstant.put(SORT_BY_SOURCE,"Source" );
		mapSortByConstant.put(SORT_BY_IMPORTED_BY,"Imported By" );
		mapSortByConstant.put(SORT_BY_DATE_IMPORTED,"Imported Date" );
	}

	
	static {
		REPORT_DESTINATION_FOLDER = TPApplicationProperties.getProperty("reports.dest.folder");
		JRXML_FOLDER_PATH = Utils.concatFilePath(TPApplicationProperties.getProperty("installation.path"), TPApplicationProperties.getProperty("reports.src.folder"));
		String basePath = Utils.concatFilePath(TPApplicationProperties.getProperty("application.path"), TPApplicationProperties.getProperty("ui.dir"));
		REPORT_DESTINATION_FOLDER_ABSOLUTE_PATH = Utils.concatFilePath(basePath,REPORT_DESTINATION_FOLDER);
	}
}