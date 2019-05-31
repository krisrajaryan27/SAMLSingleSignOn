/**
 *
 */
package com.talentPool.reports.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.masters.manager.StepManager;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.charts.JFreeChartScriptlet;
import com.talentPool.reports.charts.MonthlyJoiningChartScriptlet;
import com.talentPool.reports.charts.OverallRecruitmentCostSummaryChartScriptlet;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.reports.views.ApplicantDetailsView;
import com.talentPool.reports.views.CandidateJoiningCSVReportView;
import com.talentPool.reports.views.CandidateStatusReportView;
import com.talentPool.reports.views.HiringFunnelDetailView;
import com.talentPool.reports.views.HiringFunnelSummaryView;
import com.talentPool.reports.views.HiringStatusDetailView;
import com.talentPool.reports.views.HiringStatusSummaryView;
import com.talentPool.reports.views.InterviewListStatusView;
import com.talentPool.reports.views.InterviewListView;
import com.talentPool.reports.views.OfferToJoinedDetailedView;
import com.talentPool.reports.views.OfferToJoinedView;
import com.talentPool.reports.views.OfferedCTCView;
import com.talentPool.reports.views.OverallRecruitmentCostDetail;
import com.talentPool.reports.views.OverallRecruitmentCostSummary;
import com.talentPool.reports.views.PendingActionsView;
import com.talentPool.reports.views.PositionActivityReportView;
import com.talentPool.reports.views.PositionwiseRecruitmentCostDetailView;
import com.talentPool.reports.views.PositionwiseRecruitmentCostSummaryView;
import com.talentPool.reports.views.SourcewiseImportView;
import com.talentPool.reports.views.SourcewiseRecruitmentCostDetailView;
import com.talentPool.reports.views.SourcewiseRecruitmentCostSummaryView;
import com.talentPool.reports.views.TransactionCostView;
import com.talentPool.reports.views.UserActivityReportView;
import com.talentPool.reports.views.hiringEfficiencyReportView;
import com.talentPool.reports.views.importReportView;
import com.talentPool.reports.views.monthlyHiringChartView;
import com.talentPool.reports.views.monthlyJoiningView;
import com.talentPool.reports.views.sourceWiseHiringView;
import com.talentPool.reports.views.custom.CandidateOffersView;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

import net.sf.jasperreports.engine.JRParameter;

/**
 * @author Shantanu
 * 
 */

public class ReportGenerator {
	
	//Report Generator for Hiring Status Summary Report
	public String  generateHiringStatusSummaryReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getHiringStatusSummaryReport(filterData, userId, permissionSet);
			HashMap<String, String> params = new HashMap<String, String>();

			if (reportList.size() == 0) {
				reportList.add(new HiringStatusSummaryView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String other_criteria = "";
			String users = reportManager.getUsersForIds(filterData.getUsers());
			if (!Utils.isBlankOrNull(users)) {
				other_criteria += "Users : " + users;
			} else {
				other_criteria += "Users : All";
			}
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Report for : " + searchInText;
			}

			params.put("report_title", TPLabels.getLabel("hiring_status_summary_report.label.report_tile"));
			params.put("other_criteria", other_criteria);

			params.put("col1", TPLabels.getLabel("common.position"));
			params.put("col2", TPLabels.getLabel("common.vacancies"));
			params.put("col3", TPLabels.getLabel("common.in_process"));
			params.put("col4", TPLabels.getLabel("common.pending_offers"));
			params.put("col5", TPLabels.getLabel("common.joined"));
			params.put("col6", TPLabels.getLabel("common.applied"));

			reportManager.generateReport(ReportConstants.JRXML_HIRING_STATUS_SUMMARY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			

		} catch (Exception e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
		return outputFileName;	
	}
	
	
	//Report Generator for  Hiring Status Detail Report
	public String  generateHiringStatusDetailReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getHiringStatusDetailReport(filterData, userId,permissionSet);
			HashMap<String, String> params = new HashMap<String, String>();

			if (reportList.size() == 0) {
				reportList.add(new HiringStatusDetailView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String other_criteria = "";
			String users = reportManager.getUsersForIds(filterData.getUsers());
			if (!Utils.isBlankOrNull(users)) {
				other_criteria += "Users : " + users;
			} else {
				other_criteria += "Users : All";
			}
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Report for : " + searchInText;
			}

			params.put("report_title", TPLabels.getLabel("hiring_status_detail_report.label.report_tile"));
			params.put("other_criteria", other_criteria);

			params.put("col1", TPLabels.getLabel("hiring_status_detail_report.label.applicant_name"));
			params.put("col2", TPLabels.getLabel("hiring_status_detail_report.label.education_experience"));

			reportManager.generateReport(ReportConstants.JRXML_HIRING_STATUS_DETAIL_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Caught Exception", e);
		}
		return outputFileName;	
	}
	
	//	Report Generator for  Hiring Funnel Summary Report	
	public String  generateHiringFunnelSummaryReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;		
		setReportsAsOfDate(filterData);
		try{
			ReportManager reportManager = new ReportManager();		
			ArrayList reportList = reportManager.getHiringFunnelSummaryReport(filterData, userId, permissionSet);
			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new HiringFunnelSummaryView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			Date toDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			String date_criteria = "Date : Up to " + DateUtils.getSystemDateFormat(toDt);
			String other_criteria = "";
			String users = reportManager.getUsersForIds(filterData.getUsers());
			if (!Utils.isBlankOrNull(users)) {
				other_criteria += "Users : " + users;
			} else {
				other_criteria += "Users : All";
			}
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Report for : " + searchInText;
			}

			params.put("report_title", TPLabels.getLabel("hiring_funnel_summary_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			params.put("col_processed", TPLabels.getLabel("hiring_funnel_summary_report.label.processed"));
			params.put("col_rejected", TPLabels.getLabel("hiring_funnel_summary_report.label.rejected"));
			params.put("col_advanced", TPLabels.getLabel("hiring_funnel_summary_report.label.advanced"));
			params.put("col_inporcess", TPLabels.getLabel("hiring_funnel_summary_report.label.inprocess"));

			reportManager.generateReport(ReportConstants.JRXML_HIRING_FUNNEL_SUMMARY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
		}catch (Exception e) {			
			TPLogger.getLogger().error("Caught Exception", e);
		}
		return outputFileName;	
	}
	
	//	Report Generator for  Hiring Funnel Detail Report
	public String  generateHiringFunnelDetailReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		ReportGenerator reportGenerator=new ReportGenerator();
		reportGenerator.setReportsToAndFromDate(filterData);
		setReportsAsOfDate(filterData);
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList<HiringFunnelDetailView> reportList = reportManager.getHiringFunnelDetailReport(filterData, userId, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();

			if (reportList.size() == 0) {
				reportList.add(new HiringFunnelDetailView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			Date toDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			String date_criteria = "Date : Up to " + Utils.getDateConvertedToString(toDt, "dd, MMM yyyy");

			String other_criteria = "";
			String users = reportManager.getUsersForIds(filterData.getUsers());
			if (!Utils.isBlankOrNull(users)) {
				other_criteria += "Users : " + users;
			} else {
				other_criteria += "Users : All";
			}
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Report for : " + searchInText;
			}

			params.put("report_title", TPLabels.getLabel("hiring_funnel_detail_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			params.put("col1", TPLabels.getLabel("hiring_funnel_detail_report.label.name"));
			params.put("col2", TPLabels.getLabel("hiring_funnel_detail_report.label.date"));
			params.put("col3", TPLabels.getLabel("hiring_funnel_detail_report.label.user"));
			
			params.put("col4", TPLabels.getLabel("hiring_funnel_detail_report.label.applicant_id"));
			params.put("col5", TPLabels.getLabel("hiring_funnel_detail_report.label.emp_code"));
			params.put("col6", TPLabels.getLabel("hiring_funnel_detail_report.label.hrms_code"));
			params.put("col7", GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUSINESS_UNIT_LABEL));
			params.put("col8", GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_COST_CENTER_LABEL));

			reportManager.generateReport(ReportConstants.JRXML_HIRING_FUNNEL_DETAIL_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outputFileName;
		
	}
	
	//	Report Generator for Position Summary Report
	public String  generatePositionSummaryReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList rawList = reportManager.getPositionSummaryReport(filterData, userId, permissionSet);
			List<String> headerList = (ArrayList<String>) rawList.get(0);
			ArrayList<SimpleDataObject> reportList = (ArrayList<SimpleDataObject>) rawList.get(1);

			Map<String, String> params = new HashMap<String, String>();

			if (reportList.size() == 0) {
				reportList.add(new SimpleDataObject());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String searchInText = filterData.getSearchInText();
			String other_criteria = "";
			if (!Utils.isBlankOrNull(searchInText)) {
				other_criteria += "Report for : " + searchInText;
			}

			params.put("report_title",TPLabels.getLabel("report.label.position_summary_report"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			// ReportManager.generateDynReport(jrXMLName, outputFileName, params, reportList,
			// filterData.getReportFormat());
			reportManager.generateDynamicReport(outputFileName, params, headerList, reportList, filterData.getReportFormat(), false, "",userId, clientIpAddr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
//	Report Generator for Pending Actions Report
	public String  generatePendingActionsReport(FilterData filterData, String userRole, String userId,PermissionSet permissionSet, 
			String sessionId, String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<PendingActionsView> reportList = new ArrayList<PendingActionsView>();
			if (!Utils.isBlankOrNull(filterData.getInterviewers())) {
				reportList = reportManager.getToDoList(permissionSet, filterData, userRole, userId);
			}

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new PendingActionsView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			String other_criteria = null;
			if (filterData.getInterviewers() == null || filterData.getInterviewers().length() == 0) {
				other_criteria = "";
			} else {
				String users = reportManager.getUsersForIds(filterData.getInterviewers());
				other_criteria = "Interviewer : " + users;
			}

			params.put("report_title", TPLabels.getLabel("report.label.pending_actions"));
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.pending_actions.candidate"));
			params.put("col2", TPLabels.getLabel("common.position"));
			params.put("col3", TPLabels.getLabel("report.label.pending_actions.toDo"));
			params.put("col4", TPLabels.getLabel("report.label.pending_actions.dueDate"));

			reportManager.generateReport(ReportConstants.JRXML_PENDING_ACTIONS_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to generate pending action reports",e);
		}
		return outputFileName;
		
	}
	
	
//	Report Generator for Hiring Efficiency Report Summary
	public String  generateHiringEfficiencyReportSummary(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
				
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList report = reportManager.getHiringEfficiencySummaryReport(filterData, userId, permissionSet);
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			
			String other_criteria = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(other_criteria)) {
				other_criteria = "Report for: " + other_criteria;
			}

			HashMap<String, String> params = new HashMap<String, String>();

			if (report.size() == 0) {
				report.add(new hiringEfficiencyReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			params.put("report_title", TPLabels.getLabel("hiring_efficiency_summary_report.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col_position", TPLabels.getLabel("common.position"));
			params.put("col_open_since", TPLabels.getLabel("hiring_efficiency_summary_report.label.col_open_since"));
			params.put("col_hire_by_date", TPLabels.getLabel("common.hire_by_date"));
			params.put("col_vacancies", TPLabels.getLabel("common.vacancies"));
			params.put("col_joined", TPLabels.getLabel("common.joined"));
			params.put("col_days_to_hire", TPLabels.getLabel("hiring_efficiency_summary_report.label.col_days_to_hire"));
			params.put("col_min_days", TPLabels.getLabel("common.min"));
			params.put("col_max_days", TPLabels.getLabel("common.max"));
			params.put("col_avg_days", TPLabels.getLabel("common.avg"));
			params.put("col_avg_deviation", TPLabels.getLabel("hiring_efficiency_summary_report.label.col_avg_deviation"));

			reportManager.generateReport(ReportConstants.JRXML_HIRING_EFFICIENCY_SUMMARY_REPORT, outputFileName, params, report, 
					filterData.getReportFormat(), userId, clientIpAddr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
		
	}
	
//	Report Generator for Hiring Efficiency Report Detail
	public String  generateHiringEfficiencyReportDetail(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, String clientIpAddr){

		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		
		setReportsToAndFromDate(filterData);
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList report = reportManager.getHiringEfficiencyDetailsReport(filterData, userId, permissionSet);
			String date_criteria = ReportUtils.getDateCriteria(filterData.getConvertedFromDate(), filterData.getConvertedToDate());

			String other_criteria = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(other_criteria)) {
				other_criteria = "Report for: " + other_criteria;
			}

			HashMap<String, String> params = new HashMap<String, String>();

			if (report.size() == 0) {
				report.add(new hiringEfficiencyReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			params.put("report_title", TPLabels.getLabel("hiring_efficiency_details_report.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col_position", TPLabels.getLabel("common.position"));
			params.put("col_open_since", TPLabels.getLabel("hiring_efficiency_details_report.label.col_open_since"));
			params.put("col_hire_by_date", TPLabels.getLabel("common.hire_by_date"));
			params.put("col_vacancies", TPLabels.getLabel("common.vacancies"));
			params.put("col_candidate", TPLabels.getLabel("common.candidate"));
			params.put("col_join_date", TPLabels.getLabel("hiring_efficiency_details_report.label.col_join_date"));
			params.put("col_days_to_hire", TPLabels.getLabel("hiring_efficiency_details_report.label.col_days_to_hire"));
			params.put("col_deviation", TPLabels.getLabel("hiring_efficiency_details_report.label.col_deviation"));

			reportManager.generateReport(ReportConstants.JRXML_HIRING_EFFICIENCY_DETAILS_REPORT, outputFileName, params, report, filterData.getReportFormat(),userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
//	Report Generator for Source Wise Hring Report
	public String  generateSourceWiseHringReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;		
		setReportsToAndFromDate(filterData);
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList report = reportManager.getSourceWiseHiring(filterData, userId, permissionSet);
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			// CommonUtils.populateIdsAndNames(report,names,values,"sourceTitle","noOfJoined",null);
			if (report != null && report.size() > 0) {
				String prevName = "";
				int total = 0;
				for (int i = 0; i < report.size(); i++) {
					sourceWiseHiringView data = (sourceWiseHiringView) report.get(i);
					String newName = data.getSourceCategory();
					if (!newName.equals(prevName) && !Utils.isBlankOrNull(prevName)) {
						names.add(prevName);
						values.add("" + total);
						total = 0;
					}
					total = total + data.getNoOfJoined();
					prevName = newName;
				}
				if (total > 0) {
					names.add(prevName);
					values.add("" + total);
				}
			}
			JFreeChartScriptlet.setPieValues(names, values);
			String date_criteria = ReportUtils.getDateCriteria(filterData.getConvertedFromDate(), filterData.getConvertedToDate())	;
			HashMap<String, String> params = new HashMap<String, String>();
			String sourceCategoryCriteria = "";
			String sourceCriteria = "";
			// Criteria for Source Name
			if (!Utils.isBlankOrNull(filterData.getSourceId()) && !filterData.getSourceId().equals("-1")) {
				sourceCriteria = "Source : ";
				String[] sourceIds = filterData.getSourceId().split(",");
				for(String sourceId : sourceIds) {
					sourceCriteria += CommonUtils.getSourceName(sourceId) + ", ";
				}
				sourceCriteria = sourceCriteria.substring(0, sourceCriteria.length()-2);				
			}
			// Criteria for Source Category
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId()) 
					&& !filterData.getSourceCategoryId().equals("-1")) {
				sourceCategoryCriteria = "Source Category : ";
				String[] sourceCategoryIds = filterData.getSourceCategoryId().split(",");
				for(String sourceCategoryId : sourceCategoryIds) {
					sourceCategoryCriteria += CommonUtils.getSourceTypeName(sourceCategoryId) + ", ";
				}
				sourceCategoryCriteria = sourceCategoryCriteria.substring(0, sourceCategoryCriteria.length()-2);				
			}
			
			if (report.size() == 0) {
				report.add(new sourceWiseHiringView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", "Source wise hiring report");
			params.put("date_criteria", date_criteria);
			params.put("source_criteria", sourceCriteria);
			params.put("source_category_criteria", sourceCategoryCriteria);
			reportManager.generateReport(ReportConstants.JRXML_SOURCE_WISE_HIRING_REPORT, outputFileName, 
					params, report, filterData.getReportFormat(), userId, clientIpAddr);

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching report", e);
		}
		return outputFileName;
	}

	
	//	Report Generator for Monthly Joining Report Summary
	public String  generateMonthlyJoiningReportSummary(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		ReportManager reportManager = new ReportManager();		
		setReportsToAndFromDate(filterData);
		try {
			
			
			ArrayList reportList = reportManager.getmonthlyJoiningSummaryReport(filterData, userId, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new monthlyJoiningView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			String other_criteria = filterData.getSearchInText();
			if(!Utils.isBlankOrNull(other_criteria)){
				other_criteria = "Report for: " + other_criteria;
			}
			String date_criteria="";
			if((filterData.getDateRange()).equals(ReportConstants.CUSTOM)){
				Date toDateMonthly = Utils.adjustDateBy(filterData.getConvertedToDateMonthly(), Calendar.DATE, -1);
				date_criteria = ReportUtils.getDateCriteria(filterData.getConvertedFromDateMonthly(), Utils.convertDateToSQLDate(toDateMonthly), "MMM yyyy");
			}else{
				date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
						.getToDate(), DateConstants.INPUT_FORMAT));
			}
			params.put("report_title", TPLabels.getLabel("monthly_joining_summary_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col_month", TPLabels.getLabel("monthly_joining_summary_report.label.col_month"));
			params.put("col_position_dept", TPLabels.getLabel("monthly_joining_summary_report.label.col_department"));
			params.put("col_no_of_joined", TPLabels.getLabel("monthly_joining_summary_report.label.col_no_joined"));

			reportManager.generateReport(ReportConstants.JRXML_MONTHLY_JOINING_SUMMARY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;		
	}
	
	//	Report Generator for Monthly Joining Report Details
	public String  generateMonthlyJoiningReportDetails(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName =sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		
		
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			ArrayList report = reportManager.getmonthlyJoiningDetailsReport(filterData, userId, permissionSet);

			String other_criteria = filterData.getSearchInText();
			if(!Utils.isBlankOrNull(other_criteria)){
				other_criteria = "Report for: " + other_criteria;
			}
			String date_criteria="";
			if((filterData.getDateRange()).equals(ReportConstants.CUSTOM)){
				Date toDateMonthly = Utils.adjustDateBy(filterData.getConvertedToDateMonthly(), Calendar.DATE, -1);
				date_criteria = ReportUtils.getDateCriteria(filterData.getConvertedFromDateMonthly(), Utils.convertDateToSQLDate(toDateMonthly), "MMM yyyy");
			}else{
				date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
						.getToDate(), DateConstants.INPUT_FORMAT));
			}

			HashMap<String, String> params = new HashMap<String, String>();

			if (report.size() == 0) {
				report.add(new monthlyJoiningView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			params.put("report_title", TPLabels.getLabel("monthly_joining_details_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			params.put("col_month", TPLabels.getLabel("monthly_joining_details_report.label.col_month"));
			params.put("col_position", TPLabels.getLabel("common.position"));
			params.put("col_candidate", TPLabels.getLabel("monthly_joining_details_report.label.col_candidate"));

			reportManager.generateReport(ReportConstants.JRXML_MONTHLY_JOINING_DETAILS_REPORT, outputFileName, params, report, 
					filterData.getReportFormat(), userId, clientIpAddr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}

//	Report Generator for Monthly Joining Report Chart
	public String  generateMonthlyJoiningReportChart(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName =sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		
		
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			ArrayList report = reportManager.getmonthlyJoiningSummaryReport(filterData, userId, permissionSet);
			MonthlyJoiningChartScriptlet.setValues(new ArrayList(), new ArrayList());

			String other_criteria = filterData.getSearchInText();
			if(!Utils.isBlankOrNull(other_criteria)){
				other_criteria = "Report for: " + other_criteria;
			}
			String date_criteria="";
			if((filterData.getDateRange()).equals(ReportConstants.CUSTOM)){
				Date toDateMonthly = Utils.adjustDateBy(filterData.getConvertedToDateMonthly(), Calendar.DATE, -1);
				date_criteria = ReportUtils.getDateCriteria(filterData.getConvertedFromDateMonthly(), Utils.convertDateToSQLDate(toDateMonthly), "MMM yyyy");
			}else{
				date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
						.getToDate(), DateConstants.INPUT_FORMAT));
			}
			// populate data for chart
			ArrayList<String> names = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			for (int i = 0; i < report.size(); i++) {
				monthlyJoiningView sDo = (monthlyJoiningView) report.get(i);
				String monYr = Utils.getDateConvertedToString(sDo.getMonthJoined(), "MMM yy");
				int idx = names.indexOf(monYr);
				if (idx < 0) {
					names.add(monYr);
					values.add("" + sDo.getNoOfJoined());
				} else {
					int tot = Integer.parseInt((String) values.get(idx)) + sDo.getNoOfJoined();
					values.set(idx, "" + tot);
				}
			}
			MonthlyJoiningChartScriptlet.setValues(names, values);

			HashMap<String, String> params = new HashMap<String, String>();
			if (report.size() == 0) {
				report.add(new monthlyJoiningView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("monthly_joining_chart_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col_month", TPLabels.getLabel("monthly_joining_chart_report.label.col_month"));
			params.put("col_position_dept", TPLabels.getLabel("monthly_joining_chart_report.label.col_department"));
			params.put("col_no_of_joined", TPLabels.getLabel("monthly_joining_chart_report.label.col_no_joined"));

			if (report.size() == 0) {
				report.add(new monthlyHiringChartView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			reportManager.generateReport(ReportConstants.JRXML_MONTHLY_JOINING_CHART_REPORT, outputFileName, params, report, 
					filterData.getReportFormat(), userId, clientIpAddr);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
//	Report Generator for Source Wise Import Report
	public String  generateSourcewiseImportReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
				
		setReportsToAndFromDate(filterData);
		try {	
			ReportManager reportManager = new ReportManager();
			ArrayList<SourcewiseImportView> reportList = reportManager.getSourcewiseImportData(filterData, userId, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new SourcewiseImportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			
			String other_criteria = "Source Category : ";
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String[] arrIds = filterData.getSourceCategoryId().split(",");
				for (int i = 0; i < arrIds.length; i++) {
					if(i==(arrIds.length-1)){
						other_criteria += CommonUtils.getSourceTypeName(arrIds[i]);
					}else{
						other_criteria += CommonUtils.getSourceTypeName(arrIds[i])+",";
					}					
				}
			} else {
				other_criteria += "All";
			}
			other_criteria += ", ";
			
			other_criteria += "Source : ";
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String[] arrIds = filterData.getSourceId().split(",");
				for (int i = 0; i < arrIds.length; i++) {
					if(i==(arrIds.length-1)){
						other_criteria += CommonUtils.getSourceName(arrIds[i]);
					}else{
						other_criteria += CommonUtils.getSourceName(arrIds[i])+",";
					}					
				}			
			} else {
				other_criteria += "All";
			}
			
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));

			params.put("report_title", TPLabels.getLabel("report.label.sourcewise_import"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.sourcewise_import.source"));
			params.put("col2", TPLabels.getLabel("report.label.sourcewise_import.imported"));
			params.put("col3", TPLabels.getLabel("report.label.sourcewise_import.source_category"));
			params.put("groupFooterLabel", TPLabels.getLabel("report.label.sourcewise_import.total"));

			reportManager.generateReport(ReportConstants.JRXML_SOURCEWISE_IMPORT_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
		
	}
	
//	Report Generator for  Offer To Joined Report Summary
	public String  generateOfferToJoinedReportSummary(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;		
		
		setReportsToAndFromDate(filterData);
		try {
	
			ReportManager reportManager = new ReportManager();
			List<OfferToJoinedView> reportData = reportManager.getOfferToJoinedReportData(filterData, userId, permissionSet);

			String jrXMLName = ReportConstants.JRXML_OFFER_TO_JOINED_REPORT;
			HashMap<String, String> params = new HashMap<String, String>();

			if (reportData.size() == 0) {
				reportData.add(new OfferToJoinedView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = filterData.getSearchInText();
			if(!Utils.isBlankOrNull(other_criteria)){
				other_criteria= "Report for: " + other_criteria;
			}

			params.put("report_title", TPLabels.getLabel("offer_to_joined_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			params.put("col_month", TPLabels.getLabel("offer_to_joined_report.label.month"));
			params.put("col_offers", TPLabels.getLabel("offer_to_joined_report.label.offers"));
			params.put("col_joined", TPLabels.getLabel("offer_to_joined_report.label.joined"));
			params.put("col_to_join", TPLabels.getLabel("offer_to_joined_report.label.to_join"));
			params.put("col_will_not_join", TPLabels.getLabel("offer_to_joined_report.label.will_not_join"));

			reportManager.generateReport(jrXMLName, outputFileName, params, (ArrayList<OfferToJoinedView>) reportData, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
//	Report Generator for  Offer To Joined Report Detailed
	public String  generateOfferToJoinedReportDetailed(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			List<OfferToJoinedView> reportData = reportManager.getDetailedOfferToJoinedReportData(filterData, userId, permissionSet);
			String jrXMLName = ReportConstants.JRXML_DETAILED_OFFER_TO_JOINED_REPORT;
			HashMap<String, String> params = new HashMap<String, String>();

			if (reportData.size() == 0) {
				reportData.add(new OfferToJoinedView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = filterData.getSearchInText();
			if(!Utils.isBlankOrNull(other_criteria)){
				other_criteria= "Report for: " + other_criteria;
			}

			params.put("report_title", TPLabels.getLabel("detailed_offer_to_joined_report.label.title"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			params.put("col_month", TPLabels.getLabel("detailed_offer_to_joined_report.label.month"));
			params.put("col_candidate", TPLabels.getLabel("detailed_offer_to_joined_report.label.candidate"));
			params.put("col_position", TPLabels.getLabel("common.position"));
			params.put("col_status", TPLabels.getLabel("detailed_offer_to_joined_report.label.status"));
			params.put("col_summary", TPLabels.getLabel("detailed_offer_to_joined_report.label.summary"));
			params.put("label_offers_made", TPLabels.getLabel("detailed_offer_to_joined_report.label.offers_made"));
			params.put("label_total_joined", TPLabels.getLabel("detailed_offer_to_joined_report.label.total_joined"));
			params.put("label_total_to_join", TPLabels.getLabel("detailed_offer_to_joined_report.label.total_to_join"));
			params.put("label_total_will_not_join", TPLabels.getLabel("detailed_offer_to_joined_report.label.total_will_not_join"));

			reportManager.generateReport(jrXMLName, outputFileName, params, (ArrayList<OfferToJoinedView>) reportData, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
	@SuppressWarnings("unchecked")
	public String  generateOfferToJoinedDetailedReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			List<OfferToJoinedDetailedView> reportData = reportManager.getOfferToJoinedDetailedReportData(filterData, userId, permissionSet);
			String jrXMLName = ReportConstants.JRXML_OFFER_TO_JOINED_DETAILED_REPORT;
			HashMap params = new HashMap<String, String>();

			if (reportData.size() == 0) {
				reportData.add(new OfferToJoinedDetailedView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = "";
			
			LoginManager loginManager = new LoginManager();
			if (!Utils.isBlankOrNull(filterData.getUsers())){
				String userNames = "Recruiters:- ";
				String[] userIds = filterData.getUsers().split(",");
				for (int i = 0; i < userIds.length; i++) {
					LoginData loginData = loginManager.getUser(userIds[i]);
					if (i == (userIds.length - 1)) {
						userNames += loginData.getName();
					} else {
						userNames += loginData.getName() + ", ";
					}
				}
				other_criteria= other_criteria + userNames +  "\n";
			}
			
			if (!Utils.isBlankOrNull(filterData.getSourceId()) && !filterData.getSourceId().equals("-1")) {
				String sourceName = "Source:-";
				String[] sourceIds = filterData.getSourceId().split(",");
				for(String sourceId : sourceIds) {
					sourceName += CommonUtils.getSourceName(sourceId) + ", ";
				}
				sourceName = sourceName.substring(0, sourceName.length()-2);	
				other_criteria= other_criteria + sourceName +  "\n";
			}
			// Criteria for Source Category
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId()) 
					&& !filterData.getSourceCategoryId().equals("-1")) {
				String sourceCategory = "Source Category:-";
				String[] sourceCategoryIds = filterData.getSourceCategoryId().split(",");
				for(String sourceCategoryId : sourceCategoryIds) {
					sourceCategory += CommonUtils.getSourceTypeName(sourceCategoryId) + ", ";
				}
				sourceCategory = sourceCategory.substring(0, sourceCategory.length()-2);
				other_criteria= other_criteria + sourceCategory +  "\n";
			}

			params.put("report_title", TPLabels.getLabel("report.label.offer_to_joined_detailed"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);

			params.put("col_month", TPLabels.getLabel("offer_to_joined_detailed_report.label.month"));
			params.put("col_candidate", TPLabels.getLabel("offer_to_joined_detailed_report.label.candidate"));
			params.put("col_offerType", TPLabels.getLabel("offer_to_joined_detailed_report.label.offerType"));
			params.put("col_recruiter", TPLabels.getLabel("offer_to_joined_detailed_report.label.recruiter"));
			params.put("col_status", TPLabels.getLabel("offer_to_joined_detailed_report.label.status"));
			params.put("col_levelOffered", TPLabels.getLabel("offer_to_joined_detailed_report.label.levelOffered"));
			params.put("col_college", TPLabels.getLabel("offer_to_joined_detailed_report.label.college"));
			params.put("col_location", TPLabels.getLabel("offer_to_joined_detailed_report.label.location"));
			params.put("col_employer", TPLabels.getLabel("offer_to_joined_detailed_report.label.employer"));
			params.put("col_source", TPLabels.getLabel("offer_to_joined_detailed_report.label.source"));
			params.put("col_sr_no", TPLabels.getLabel("offer_to_joined_detailed_report.label.sr_no"));
			
			if (filterData.getReportFormat().equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
			    params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);    
			}
			   
			reportManager.generateReport(jrXMLName, outputFileName, params, (ArrayList<OfferToJoinedDetailedView>) reportData, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
//	Report Generator for  Candidate Comparison Report
	public String  generateCandidateComparisonReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		try {
			ReportManager reportManager = new ReportManager();
			List reportData = reportManager.getCandidateComparisonReportData(filterData);
			List headers = (List) reportData.get(0);
			List data = (List) reportData.get(1);
			HashMap<String, String> params = new HashMap<String, String>();
			if (headers.size() == 0) {
				data = new ArrayList<SimpleDataObject>();
				data.add(new SimpleDataObject());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String other_criteria = "";
			PositionManager positionManager = new PositionManager();
			other_criteria = "For Position: " + positionManager.getPositionName(filterData.getPositionId());

			params.put("report_title", TPLabels.getLabel("candidate_comparison_report.label.title"));
			params.put("other_criteria", other_criteria);

			reportManager.generateDynamicReport(outputFileName, params, headers, data, filterData.getReportFormat(), true, "0",userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
	//	Report Generator for  Overall Recruitment Cost Summary
	public String  generateOverallRecruitmentCostSummary(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName =sessionId + String.valueOf(System.currentTimeMillis()) + ext;
				
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<OverallRecruitmentCostSummary> reportList = (ArrayList<OverallRecruitmentCostSummary>) reportManager.getOverallRecruitmentCostSummaryReport(filterData);
			int numberOfCandidatesJoined = reportManager.getNoOfCandidatesJoinedOverPeriod(filterData);

			HashMap params = new HashMap();

			ArrayList<String> names = new ArrayList<String>();
			ArrayList<Double> values = new ArrayList<Double>();
			if (reportList.size() > 0) {
				Iterator<OverallRecruitmentCostSummary> itr = reportList.iterator();
				while (itr.hasNext()) {
					OverallRecruitmentCostSummary obj = itr.next();
					names.add(obj.getCostType());
					values.add(obj.getCost());
				}
			}
			OverallRecruitmentCostSummaryChartScriptlet.setPieValues((ArrayList<String>) names, (ArrayList<Double>) values);

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));

			if (reportList.size() == 0) {
				reportList.add(new OverallRecruitmentCostSummary());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.overall_recruitment_cost_summary_report"));
			params.put("date_criteria", date_criteria);
			params.put("total_label", TPLabels.getLabel("report.label.total"));
			params.put("cost_label", TPLabels.getLabel("report.overall_recruitment_cost_summary_report.label.cost"));
			params.put("purpose_label", TPLabels.getLabel("report.overall_recruitment_cost_summary_report.label.purpose"));

			params.put("no_of_joinee_label", TPLabels.getLabel("report.overall_recruitment_cost_summary_report.label.no_of_candidates_joined"));
			params.put("average_cost_per_hire_label", TPLabels.getLabel("report.overall_recruitment_cost_summary_report.label.average_cost_per_hire"));
			params.put("noOfCandidatesJoined", numberOfCandidatesJoined);

			reportManager.generateReport(ReportConstants.JRXML_OVERALL_RECRUITMENT_COST_SUMMARY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the overall recruitment cost summary report", e);
		}
		return outputFileName;
	}

//	Report Generator for  Overall Recruitment Cost Detail
	public String  generateOverallRecruitmentCostDetail(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
			
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<OverallRecruitmentCostDetail> reportList = (ArrayList<OverallRecruitmentCostDetail>) reportManager.getOverallRecruitmentCostDetailReport(filterData);
			int numberOfCandidatesJoined = reportManager.getNoOfCandidatesJoinedOverPeriod(filterData);

			HashMap params = new HashMap();

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));

			if (reportList.size() == 0) {
				reportList.add(new OverallRecruitmentCostDetail());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.overall_recruitment_cost_detail_report"));
			params.put("date_criteria", date_criteria);
			params.put("purpose_label", TPLabels.getLabel("report.overall_recruitment_cost_detail_report.label.purpose"));
			params.put("position_label", TPLabels.getLabel("common.position"));
			params.put("source_label", TPLabels.getLabel("report.overall_recruitment_cost_detail_report.label.source"));
			params.put("amount_label", TPLabels.getLabel("report.overall_recruitment_cost_detail_report.label.amount"));
			params.put("total_label", TPLabels.getLabel("report.label.total"));
			params.put("grand_label", TPLabels.getLabel("report.label.grand_total"));

			params.put("no_of_joinee_label", TPLabels.getLabel("report.overall_recruitment_cost_detail_report.label.no_of_candidates_joined"));
			params.put("average_cost_per_hire_label", TPLabels.getLabel("report.overall_recruitment_cost_detail_report.label.average_cost_per_hire"));
			params.put("noOfCandidatesJoined", numberOfCandidatesJoined);

			reportManager.generateReport(ReportConstants.JRXML_OVERALL_RECRUITMENT_COST_DETAIL_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the overall recruitment cost summary report", e);
		}
		return outputFileName;
	}
	
//	Report Generator for  Positionwise Recruitment Cost Summary
	public String  generatePositionwiseRecruitmentCostSummary(FilterData filterData, String userId,PermissionSet permissionSet, 
			String sessionId, String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<PositionwiseRecruitmentCostSummaryView> reportList = (ArrayList<PositionwiseRecruitmentCostSummaryView>) reportManager.getPositionwiseRecruitmentCostSummaryReport(filterData);
			int numberOfCandidatesJoined = reportManager.getNoOfCandidatesJoinedOverPeriod(filterData);

			HashMap params = new HashMap();

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = filterData.getSearchInText();

			if (!Utils.isBlankOrNull(other_criteria)) {
				other_criteria = "Report for : " + other_criteria;
			}
			if (reportList.size() == 0) {
				reportList.add(new PositionwiseRecruitmentCostSummaryView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.positionwise_recruitment_cost_summary_report"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("position_label", TPLabels.getLabel("common.position"));
			params.put("amount_label", TPLabels.getLabel("report.positionwise_recruitment_cost_summary_report.label.amount"));
			params.put("total_label", TPLabels.getLabel("report.label.total"));

			params.put("no_of_joinee_label", TPLabels.getLabel("report.recruitment_cost.label.no_of_candidates_joined"));
			params.put("average_cost_per_hire_label", TPLabels.getLabel("report.recruitment_cost.label.average_cost_per_hire"));
			params.put("noOfCandidatesJoined", numberOfCandidatesJoined);

			reportManager.generateReport(ReportConstants.JRXML_POSITIONWISE_RECRUITMENT_COST_SUMMARY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the positionwise recruitment cost summary report", e);
		}
		return outputFileName;
	}

//	Report Generator for  Positionwise Recruitment Cost Detail
	public String  generatePositionwiseRecruitmentCostDetail(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<PositionwiseRecruitmentCostDetailView> reportList = (ArrayList<PositionwiseRecruitmentCostDetailView>) reportManager.getPositionwiseRecruitmentCostDetailReport(filterData);
			int numberOfCandidatesJoined = reportManager.getNoOfCandidatesJoinedOverPeriod(filterData);

			HashMap params = new HashMap();

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = filterData.getSearchInText();

			if (!Utils.isBlankOrNull(other_criteria)) {
				other_criteria = "Report for : " + other_criteria;
			}
			if (reportList.size() == 0) {
				reportList.add(new PositionwiseRecruitmentCostDetailView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.positionwise_recruitment_cost_detail_report"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("position_label", TPLabels.getLabel("common.position"));
			params.put("purpose_label", TPLabels.getLabel("report.positionwise_recruitment_cost_detail_report.label.purpose"));
			params.put("source_label", TPLabels.getLabel("report.positionwise_recruitment_cost_detail_report.label.source"));
			params.put("amount_label", TPLabels.getLabel("report.positionwise_recruitment_cost_detail_report.label.amount"));
			params.put("total_label", TPLabels.getLabel("report.label.total"));
			params.put("grand_total_label", TPLabels.getLabel("report.label.grand_total"));

			params.put("no_of_joinee_label", TPLabels.getLabel("report.recruitment_cost.label.no_of_candidates_joined"));
			params.put("average_cost_per_hire_label", TPLabels.getLabel("report.recruitment_cost.label.average_cost_per_hire"));
			params.put("noOfCandidatesJoined", numberOfCandidatesJoined);

			reportManager.generateReport(ReportConstants.JRXML_POSITIONWISE_RECRUITMENT_COST_DETAIL_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the positionwise recruitment cost detail report", e);
		}
		return outputFileName;
	}
	
//	Report Generator for  sourcewise Recruitment Cost Summary
	public String  generateSourcewiseRecruitmentCostSummary(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<SourcewiseRecruitmentCostSummaryView> reportList = (ArrayList<SourcewiseRecruitmentCostSummaryView>) reportManager.getSourcewiseRecruitmentCostSummaryReport(filterData);
			int numberOfCandidatesJoined = reportManager.getNoOfCandidatesJoinedOverPeriod(filterData);

			HashMap params = new HashMap();

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = "Source : ";
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				//other_criteria += reportForm.getSourceName();
				other_criteria += CommonUtils.getSourceName(filterData.getSourceId());
			} else {
				other_criteria += "All";
			}
			if (reportList.size() == 0) {
				reportList.add(new SourcewiseRecruitmentCostSummaryView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.sourcewise_recruitment_cost_summary_report"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("source_label", TPLabels.getLabel("report.sourcewise_recruitment_cost_summary_report.label.source"));
			params.put("amount_label", TPLabels.getLabel("report.sourcewise_recruitment_cost_summary_report.label.amount"));
			params.put("total_label", TPLabels.getLabel("report.label.total"));

			params.put("no_of_joinee_label", TPLabels.getLabel("report.recruitment_cost.label.no_of_candidates_joined"));
			params.put("average_cost_per_hire_label", TPLabels.getLabel("report.recruitment_cost.label.average_cost_per_hire"));
			params.put("noOfCandidatesJoined", numberOfCandidatesJoined);

			reportManager.generateReport(ReportConstants.JRXML_SOURCEWISE_RECRUITMENT_COST_SUMMARY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the sourcewise recruitment cost summary report", e);
		}
		return outputFileName;
	}
	
	//	Report Generator for  sourcewise Recruitment Cost Detail
	public String  generateSourcewiseRecruitmentCostDetail(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<SourcewiseRecruitmentCostDetailView> reportList = (ArrayList<SourcewiseRecruitmentCostDetailView>) reportManager.getSourcewiseRecruitmentCostDetailReport(filterData);
			int numberOfCandidatesJoined = reportManager.getNoOfCandidatesJoinedOverPeriod(filterData);

			HashMap params = new HashMap();

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = "Source : ";
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				//other_criteria += reportForm.getSourceName();
				other_criteria += CommonUtils.getSourceName(filterData.getSourceId());
			} else {
				other_criteria += "All";
			}
			if (reportList.size() == 0) {
				reportList.add(new SourcewiseRecruitmentCostDetailView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.sourcewise_recruitment_cost_detail_report"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("position_label", TPLabels.getLabel("common.position"));
			params.put("purpose_label", TPLabels.getLabel("report.sourcewise_recruitment_cost_detail_report.label.purpose"));
			params.put("source_label", TPLabels.getLabel("report.sourcewise_recruitment_cost_detail_report.label.source"));
			params.put("amount_label", TPLabels.getLabel("report.sourcewise_recruitment_cost_detail_report.label.amount"));
			params.put("total_label", TPLabels.getLabel("report.label.total"));
			params.put("grand_total_label", TPLabels.getLabel("report.label.grand_total"));

			params.put("no_of_joinee_label", TPLabels.getLabel("report.recruitment_cost.label.no_of_candidates_joined"));
			params.put("average_cost_per_hire_label", TPLabels.getLabel("report.recruitment_cost.label.average_cost_per_hire"));
			params.put("noOfCandidatesJoined", numberOfCandidatesJoined);

			reportManager.generateReport(ReportConstants.JRXML_SOURCEWISE_RECRUITMENT_COST_DETAIL_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the positionwise recruitment cost detail report", e);
		}
		return outputFileName;
	}
	
	//	Report Generator for  Transaction Cost
	public String  generateTransactionCost(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList<TransactionCostView> reportList = (ArrayList<TransactionCostView>) reportManager.getTransactionCostReport(filterData);

			HashMap<String, String> params = new HashMap<String, String>();

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));

			if (reportList.size() == 0) {
				reportList.add(new TransactionCostView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			params.put("report_title", TPLabels.getLabel("report.label.transaction_cost_report"));
			params.put("date_criteria", date_criteria);
			params.put("col1", TPLabels.getLabel("report.transaction_cost_report.label.date"));
			params.put("col2", TPLabels.getLabel("report.transaction_cost_report.label.purpose"));
			params.put("col3", TPLabels.getLabel("report.transaction_cost_report.label.amount"));
			params.put("col4", TPLabels.getLabel("common.position"));
			params.put("col5", TPLabels.getLabel("report.transaction_cost_report.label.source"));
			params.put("col6", TPLabels.getLabel("report.transaction_cost_report.label.remark"));
			params.put("total_label", TPLabels.getLabel("report.label.total"));

			reportManager.generateReport(ReportConstants.JRXML_TRANSACTION_COST_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while generating the transaction cost report", e);
		}
		return outputFileName;
	}
	
//	Report Generator for  Transaction Cost
	public String  generateInterviewList(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
				
		setReportsToAndFromDate(filterData);
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getInterviewListData(filterData, userId, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new InterviewListView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			String other_criteria = null;
			if (filterData.getInterviewers() == null || filterData.getInterviewers().length() == 0) {
				other_criteria = "";
			} else {
				String users = reportManager.getUsersForIds(filterData.getInterviewers());
				other_criteria = "Interviewer : " + users;
			}
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
					.getToDate(), DateConstants.INPUT_FORMAT));

			params.put("report_title", TPLabels.getLabel("report.label.interview_list"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.interview_list.time"));
			params.put("col2", TPLabels.getLabel("common.position")+TPLabels.getLabel("report.label.interview_list.deptPosStep"));
			params.put("col3", TPLabels.getLabel("report.label.interview_list.nameLoc"));
			params.put("col4", TPLabels.getLabel("report.label.interview_list.emailPhone"));
			params.put("col5", TPLabels.getLabel("report.label.interview_list.expEmpl"));
			params.put("col6", TPLabels.getLabel("report.label.interview_list.education"));
			params.put("col7", TPLabels.getLabel("report.label.interview_list.interviewers"));

			reportManager.generateReport(ReportConstants.JRXML_INTERVIEW_LIST_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
	/**
	 * @param filterData
	 * @param userId
	 * @param permissionSet
	 * @param sessionId
	 * @param clientIpAddr
	 * @return
	 */
	public String  generateInterviewStatus(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
				
		setReportsToAndFromDate(filterData);
		try {			
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getInterviewListStatusData(filterData, userId, permissionSet);

			HashMap<String, Object> params = new HashMap<String, Object>();
			if (reportList.size() == 0) {
				reportList.add(new InterviewListStatusView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}
			String other_criteria = "";
			String departmentName = "";
			if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
				String [] departments = filterData.getDepartmentId().split(",");
				for (String department: departments) {
					if (!Utils.isBlankOrNull(departmentName)) {
						departmentName += ", ";
					}
					departmentName += CommonUtils.getDeptName(department);
				}
			}else{
				departmentName += " All Departments ";
			}
			if (!Utils.isBlankOrNull(departmentName)){
				departmentName = "Department : "+ departmentName;
				other_criteria += departmentName + "\n";
			}
			
			String positionName="";
			if (!Utils.isBlankOrNull(filterData.getPositionId())) {
				String [] positions = filterData.getPositionId().split(",");
					for (String position: positions) {
						if (!Utils.isBlankOrNull(positionName)) {
							positionName += ", ";
						}
						positionName += reportManager.getPositionName(position);
					}
			} else {
				if (filterData.getPositionFilter().equalsIgnoreCase(ReportConstants.FILTER_ALL_POSITIONS)) {
					positionName = "All Positions ";
				} else if (filterData.getPositionFilter().equalsIgnoreCase(ReportConstants.FILTER_OPEN_POSITIONS)) {
					positionName = "Open Positions ";
				}else if(filterData.getPositionFilter().equalsIgnoreCase(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)){
					positionName ="Open and OnHold Positions";
				}

			}
			
			if (!Utils.isBlankOrNull(positionName)){
				positionName = "Positions : "+ positionName;
				other_criteria += positionName + "\n";
			}
			
			String userName = "";
			if (!Utils.isBlankOrNull(filterData.getUsers())) {
				String [] user_Ids = filterData.getUsers().split(",");
				for (String user_Id : user_Ids) {
					if(!Utils.isBlankOrNull(userName)){
						userName += ", ";
					}
					userName += reportManager.getUsersForIds(user_Id);
				}
			}else {
				userName +=" All Recruiters ";
			}
			
			if (!Utils.isBlankOrNull(userName)){
				userName = "Recruiters : "+ userName;
				other_criteria += userName + "\n";
			}
			
			String interviewerName="";
			if(!Utils.isBlankOrNull(filterData.getInterviewers())){
				String [] interviewers= filterData.getInterviewers().split(",");
				for(String interviewer: interviewers){
					if(!Utils.isBlankOrNull(interviewerName)){
						interviewerName +=", ";
					}
					interviewerName += reportManager.getUsersForIds(interviewer);
				}
			}else{
				interviewerName += " All Interviewers ";
			}
			
			if (!Utils.isBlankOrNull(interviewerName)){
				interviewerName = "Interviewers : "+ interviewerName;
				other_criteria += interviewerName + "\n";
			}
			
			String stepName="";
			if(!Utils.isBlankOrNull(filterData.getStepIds())){
				String[] steps =filterData.getStepIds().split(",");
				StepManager stepManager = new StepManager();
				for(String step: steps){
					if(!Utils.isBlankOrNull(stepName)){
						stepName += ", ";
					}
					stepName += stepManager.getStepNameForStepId(step);
				}
			}else
				stepName+="All Steps ";
			
			if (!Utils.isBlankOrNull(stepName)){
				stepName = "Steps : "+ stepName;
				other_criteria += stepName + "\n";
			}
			
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
					.getToDate(), DateConstants.INPUT_FORMAT));

			params.put("report_title", TPLabels.getLabel("report.label.interview_status"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.interview_status.date"));
			params.put("col2", TPLabels.getLabel("report.label.interview_status.name"));
			params.put("col3", TPLabels.getLabel("report.label.interview_status.modeOfInterview"));
			params.put("col4", TPLabels.getLabel("report.label.interview_status.department"));
			params.put("col5", TPLabels.getLabel("report.label.interview_status.position"));
			params.put("col6", TPLabels.getLabel("report.label.interview_status.interviewers"));
			params.put("col7", TPLabels.getLabel("report.label.interview_status.recruiter"));
			params.put("col8", TPLabels.getLabel("report.label.interview_status.status"));
			params.put("col9", TPLabels.getLabel("report.label.interview_status.source"));
			params.put("col10", TPLabels.getLabel("report.label.interview_status.step"));
			
			//added one extra column for interviewer feedback
			params.put("col11", TPLabels.getLabel("report.label.interview_status_feedback.step"));
			
			if (filterData.getReportFormat().equalsIgnoreCase(ReportConstants.FORMAT_EXCEL)) {
			    params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);    
			}
			reportManager.generateReport(ReportConstants.JRXML_INTERVIEW_STATUS_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
	//	Report Generator for  Applicant Details
	public String  generateApplicantDetails(FilterData filterData, String userId,PermissionSet permissionSet, 
			String sessionId, String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;		
		setReportsToAndFromDate(filterData);
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getApplicantDetailsReportData(filterData, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new ApplicantDetailsView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String other_criteria = "Criteria : ";
			other_criteria += "Experience : ";
			if (!Utils.isBlankOrNull(filterData.getMinExp()) || !Utils.isBlankOrNull(filterData.getMaxExp())) {
				if (!Utils.isBlankOrNull(filterData.getMinExp()) && !Utils.isBlankOrNull(filterData.getMaxExp())) {
					other_criteria += filterData.getMinExp() + " to " + filterData.getMaxExp() + " yrs";
				} else if (!Utils.isBlankOrNull(filterData.getMinExp())) {
					other_criteria += "Minimum " + filterData.getMinExp() + " yrs";
				} else {
					other_criteria += "Upto " + filterData.getMaxExp() + " yrs";
				}
			} else {
				other_criteria += "All";
			}
			other_criteria += ", ";
			
			other_criteria += "Source Category : ";			
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId())) {
				String[] arrIds = filterData.getSourceCategoryId().split(",");
				for (int i = 0; i < arrIds.length; i++) {
					if(i==(arrIds.length-1)){
						other_criteria += CommonUtils.getSourceTypeName(arrIds[i]);
					}else{
						other_criteria += CommonUtils.getSourceTypeName(arrIds[i])+",";
					}					
				}			
			} else {
				other_criteria += "All";
			}			
			other_criteria += ", ";
			
			other_criteria += "Source : ";
			if (!Utils.isBlankOrNull(filterData.getSourceId())) {
				String[] arrIds = filterData.getSourceId().split(",");
				for (int i = 0; i < arrIds.length; i++) {
					if(i==(arrIds.length-1)){
						other_criteria += CommonUtils.getSourceName(arrIds[i]);
					}else{
						other_criteria += CommonUtils.getSourceName(arrIds[i])+",";
					}					
				}			
			} else {
				other_criteria += "All";
			}
			other_criteria += ", ";
			other_criteria += "Degree : ";
			if (!Utils.isBlankOrNull(filterData.getDegreeIds())) {
				String[] parts = filterData.getDegreeIds().split(",");
				String degreeExpr = "";
				for (int i = 0; i < parts.length; i++) {
					if (degreeExpr.length() > 0) {
						degreeExpr += ", ";
					}
					degreeExpr += CommonUtils.getDegreeName(parts[i].trim());
				}
				other_criteria += degreeExpr;
			} else {
				other_criteria += "All";
			}
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));

			params.put("report_title", TPLabels.getLabel("report.label.applicant_details"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.applicant_details.name"));
			params.put("col2", TPLabels.getLabel("report.label.applicant_details.sorurce"));
			params.put("col3", TPLabels.getLabel("report.label.applicant_details.current_employer"));
			params.put("col4", TPLabels.getLabel("report.label.applicant_details.experience"));
			params.put("col5", TPLabels.getLabel("report.label.applicant_details.qualification"));
			params.put("col6", TPLabels.getLabel("report.label.applicant_details.location"));
			params.put("col7", TPLabels.getLabel("report.label.applicant_details.email"));
			params.put("col8", TPLabels.getLabel("report.label.applicant_details.phone"));
			params.put("col9", TPLabels.getLabel("report.label.applicant_details.skills"));
			params.put("col10", TPLabels.getLabel("report.label.applicant_details.sorurce_category"));

			reportManager.generateReport(ReportConstants.JRXML_APPLICANT_DETAILS_REPORT, outputFileName, params, reportList,
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
//	Report Generator for  import Report
	public String  generateImportReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;		
		setReportsToAndFromDate(filterData);
		LoginManager loginManager = new LoginManager();
		LoginData loginData = loginManager.getUser(filterData.getUserId());
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList report = reportManager.getImportReport(filterData, permissionSet);
			String jrXMLName = "import_report.jrxml";

			HashMap params = new HashMap();

			if (report.size() == 0) {
				report.add(new importReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String filterId = filterData.getFilterId();
			String criteria = "";

			if (filterId.equals(ReportConstants.FILTER_SOURCE)) {
				//criteria = TPLabels.getLabel("monthly_hiring_report.lable.criteria_selected_source") + "\"" + reportForm.getSourceName() + "\"";
				criteria = TPLabels.getLabel("monthly_hiring_report.lable.criteria_selected_source") + "\"" + CommonUtils.getSourceName(filterData.getSourceId()) + "\"";
				;
			} else if (filterId.equals(ReportConstants.FILTER_IMPORTED_BY)) {
				//criteria = TPLabels.getLabel("monthly_hiring_report.lable.criteria_selected_user") + "\"" + reportForm.getUserName() + "\"";
				criteria = TPLabels.getLabel("monthly_hiring_report.lable.criteria_selected_user") + " \"" + loginData.getUserName() + "\"";	
			} else
				criteria = "All";

			Date toDt = Utils.convertToDate(filterData.getToDate(), "dd/MM/yyyy");
			Date frmDt = Utils.convertToDate(filterData.getFromDate(), "dd/MM/yyyy");
			params.put("report_title", "Import Report");
			params.put("from_date_title", TPLabels.getLabel("monthly_hiring_report.lable.from_date"));
			params.put("to_date_title", TPLabels.getLabel("monthly_hiring_report.lable.to_date"));
			params.put("from_date", frmDt);
			params.put("to_date", toDt);
			params.put("col_applicant_name", TPLabels.getLabel("call_report.lable.applicant_name"));
			params.put("col_imported_by", TPLabels.getLabel("import_report.lable.imported_by"));
			params.put("col_source", TPLabels.getLabel("import_report.lable.source"));
			params.put("col_date_of_import", TPLabels.getLabel("import_report.lable.date_of_import"));
			params.put("criteria_title", TPLabels.getLabel("hiring_status_summary_report.lable.report_for"));
			params.put("criteria", criteria);

			reportManager.generateReport(jrXMLName, outputFileName, params, report, filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		return outputFileName;
	}
	
//	Report Generator for  import Report
	public String  generateUserActivity(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;			
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getUserActivityReportData(filterData, userId, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new UserActivityReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String other_criteria = "";
			String users = reportManager.getUsersForIds(filterData.getInterviewers());
			if (!Utils.isBlankOrNull(users)) {
				other_criteria += "Interviewer : " + users;
			}
			String stagesText = filterData.getStagesText();
			if (!Utils.isBlankOrNull(stagesText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Stage : " + stagesText;
			}
			String activities = filterData.getActivities();
			if (other_criteria.length() > 0) {
				other_criteria += "\n";
			}
			if (!Utils.isBlankOrNull(activities)) {
				String[] activityIds = activities.split(",");
				other_criteria += "Activities : ";
				for(String activityId : activityIds) {
					String activityText = "";
					if(activityId.equalsIgnoreCase("4")) {
						activityText = "Feedback";
					} else {
						activityText = SelectionProcessConstants.INTERACTION_TYPES.get(activityId);
					}
					
					other_criteria += activityText + ", ";
				}
				other_criteria = other_criteria.substring(0, other_criteria.length()-2);
			} else {
				other_criteria += "Activities : All";
			}
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Report for : " + searchInText;
			}
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
					.getToDate(), DateConstants.INPUT_FORMAT));

			params.put("report_title", TPLabels.getLabel("report.label.user_activity"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.user_activity.interaction_date"));
			params.put("col2", TPLabels.getLabel("report.label.user_activity.interaction"));
			params.put("col3", TPLabels.getLabel("report.label.user_activity.department_position")+" "+TPLabels.getLabel("common.position"));
			params.put("col4", TPLabels.getLabel("report.label.user_activity.candidate"));

			reportManager.generateReport(ReportConstants.JRXML_USER_ACTIVITY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
	
//	Report Generator for  Position Activity
	public String  generatePositionActivity(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, 
			String clientIpAddr){
		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;
			
		setReportsToAndFromDate(filterData);
		try {				
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getPositionActivityReportData(filterData, userId, permissionSet);

			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new PositionActivityReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}

			String other_criteria = "";
			String users = reportManager.getUsersForIds(filterData.getInterviewers());
			if (!Utils.isBlankOrNull(users)) {
				other_criteria += "Interviewer : " + users;
			}
			String stagesText = filterData.getStagesText();
			if (!Utils.isBlankOrNull(stagesText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Stage : " + stagesText;
			}
			String searchInText = filterData.getSearchInText();
			if (!Utils.isBlankOrNull(searchInText)) {
				if (other_criteria.length() > 0) {
					other_criteria += "\n";
				}
				other_criteria += "Report for : " + searchInText;
			}
			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData
					.getToDate(), DateConstants.INPUT_FORMAT));
			
			params.put("report_title",TPLabels.getLabel("report.label.position_activity"));
			params.put("date_criteria", date_criteria);
			params.put("other_criteria", other_criteria);
			params.put("col1", TPLabels.getLabel("report.label.position_activity.interaction_date"));
			params.put("col2", TPLabels.getLabel("report.label.position_activity.interaction"));
			params.put("col3", TPLabels.getLabel("report.label.position_activity.user"));
			params.put("col4", TPLabels.getLabel("report.label.position_activity.candidate"));

			reportManager.generateReport(ReportConstants.JRXML_POSITION_ACTIVITY_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}

	
	//Report Generator for  Candidate Status Report
	public String  generateReportCandidateStatus(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId,
			String clientIpAddr){

		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ext;			
		
		if(Utils.isBlankOrNull(filterData.getActionId()) || filterData.getActionId().equals("-1")  ){			
			filterData.setFromDate(null);			
		}else{
			setReportsToAndFromDate(filterData);
		}
		try {
			ReportManager reportManager = new ReportManager();
			ArrayList reportList = reportManager.getCandidateStatusReport(filterData, userId, permissionSet);
			HashMap<String, String> params = new HashMap<String, String>();
			if (reportList.size() == 0) {
				reportList.add(new CandidateStatusReportView());
				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
			}			
			String position_criteria = "";
			String user_criteria = "";			
						
			if(filterData.getActionId().equals(ReportConstants.FILTER_REJECTED_BY) || filterData.getActionId().equals(ReportConstants.FILTER_MOVED_BY) || filterData.getActionId().equals(ReportConstants.FILTER_IMPORTED_BY) ){			
				String users = reportManager.getUsersForIds(filterData.getUsers());
				int actionType=Integer.parseInt(filterData.getActionId());
				if (!Utils.isBlankOrNull(users)) {
					switch (actionType) {
					case 5:
						user_criteria += "Imported By : " + users;
						break;
					case 8:
						user_criteria += "Moved By : " + users;;
						break;
					case 9:
						user_criteria += "Rejected By : " + users;
						break;						
					default:
						user_criteria += "Users : All";
						break;
					}					
				} else {
					user_criteria += "Users : All";
				}
			}else{
				user_criteria += "Users : All";
			}	
			
			String searchInText = filterData.getSearchInText();
			
			if (!Utils.isBlankOrNull(searchInText)) {
				if (position_criteria.length() > 0) {
					position_criteria += "\n";
				}
				position_criteria += "Position : " + searchInText;
			}
			
			String frmdate= filterData.getFromDate();
			java.sql.Date dt1=null;
			if(!Utils.isBlankOrNull(frmdate)){
				dt1=Utils.convertToSQLDate(frmdate, DateConstants.INPUT_FORMAT);
			}						
			String date_criteria = ReportUtils.getDateCriteria(dt1, Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			
			String sourceCategoryCriteria = "";
			String sourceCriteria = "";
			// Criteria for Source Name
			if (!Utils.isBlankOrNull(filterData.getSourceId()) && !filterData.getSourceId().equals("-1")) {
				sourceCriteria = "Source : ";
				String[] sourceIds = filterData.getSourceId().split(",");
				for(String sourceId : sourceIds) {
					sourceCriteria += CommonUtils.getSourceName(sourceId) + ", ";
				}
				sourceCriteria = sourceCriteria.substring(0, sourceCriteria.length()-2);				
			}
			// Criteria for Source Category
			if (!Utils.isBlankOrNull(filterData.getSourceCategoryId()) 
					&& !filterData.getSourceCategoryId().equals("-1")) {
				sourceCategoryCriteria = "Source Category : ";
				String[] sourceCategoryIds = filterData.getSourceCategoryId().split(",");
				for(String sourceCategoryId : sourceCategoryIds) {
					sourceCategoryCriteria += CommonUtils.getSourceTypeName(sourceCategoryId) + ", ";
				}
				sourceCategoryCriteria = sourceCategoryCriteria.substring(0, sourceCategoryCriteria.length()-2);				
			}
			
			params.put("report_title", TPLabels.getLabel("candidate_status_report.label.report_tile"));
			params.put("date_criteria", date_criteria);
			params.put("user_criteria", user_criteria);
			params.put("position_criteria", position_criteria);
			params.put("source_criteria", sourceCriteria);
			params.put("source_category_criteria", sourceCategoryCriteria);

			params.put("col1", TPLabels.getLabel("candidate_status_report.label.name"));
			params.put("col2", TPLabels.getLabel("candidate_status_report.label.exp_last_emp"));
			params.put("col3", TPLabels.getLabel("candidate_status_report.label.source"));
			params.put("col4", TPLabels.getLabel("candidate_status_report.label.step"));
			params.put("col5", TPLabels.getLabel("candidate_status_report.label.last_action"));
			params.put("col6", TPLabels.getLabel("candidate_status_report.label.next_action"));
			params.put("col7", TPLabels.getLabel("candidate_status_report.label.source_category"));
			reportManager.generateReport(ReportConstants.JRXML_CANDIDATE_STATUS_REPORT, outputFileName, params, reportList, 
					filterData.getReportFormat(), userId, clientIpAddr);			
		} catch (Exception e) {			
			TPLogger.getLogger().error("Caught Exception", e);
		}		
		return outputFileName;		
	}

	
	//Date Range Converter
	public void setReportsToAndFromDate(FilterData filterData){
		
		try{	
			if(!filterData.getDateRange().trim().isEmpty() && !filterData.getDateRange().equals(ReportConstants.CUSTOM)){
				HashMap<String, Date> fromAndToDateFromDateRange = getFromAndToDateFromDateRange(filterData.getDateRange(), filterData.getNumberRange());
				filterData.setToDate(Utils.getDateConvertedToString(fromAndToDateFromDateRange.get("toDate"), DateConstants.INPUT_FORMAT));
				filterData.setFromDate(Utils.getDateConvertedToString(fromAndToDateFromDateRange.get("fromDate"), DateConstants.INPUT_FORMAT));
			}
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while setting to and from date from date range", e);
		}
	}
	
	public void setReportsToAndFromDateForAppointment(FilterData filterData){
		
		try{	
			if(!filterData.getAppointmentDateRange().trim().isEmpty() && !filterData.getAppointmentDateRange().equals(ReportConstants.CUSTOM)){
				HashMap<String, Date> fromAndToDateFromDateRange = getFromAndToDateFromDateRange(filterData.getAppointmentDateRange(), filterData.getAppointmentNumberRange());
				filterData.setToAppointmentDate(Utils.getDateConvertedToString(fromAndToDateFromDateRange.get("toDate"), DateConstants.INPUT_FORMAT));
				filterData.setFromAppointmentDate(Utils.getDateConvertedToString(fromAndToDateFromDateRange.get("fromDate"), DateConstants.INPUT_FORMAT));
			}
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while setting to and from date from date range for appointment filter", e);
		}
	}
	
	private HashMap<String, Date> getFromAndToDateFromDateRange(String dateRange, String numberRange)throws Exception{
		Date toDate = null;
		Date fromDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		
		HashMap<String, Date> dateMap = new HashMap<String, Date>();
	
		//set to and from date
		if(dateRange.equals(ReportConstants.TODAY)){
			toDate=Utils.datePlusPlus(new Date());
			fromDate=new Date();
		}if(dateRange.equals(ReportConstants.YESTERDAY)){
			toDate = Utils.adjustDateBy(new Date(), Calendar.DAY_OF_MONTH, 0);
			fromDate=Utils.adjustDateBy(new Date(), Calendar.DAY_OF_MONTH, -1);
		}if(dateRange.equals(ReportConstants.CURRENT_WEEK)){
			toDate=new Date();
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK)+1);
		}if(dateRange.equals(ReportConstants.PREVIOUS_WEEK)){
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK));
			fromDate=Utils.adjustDateBy(toDate,Calendar.DATE,-Calendar.DAY_OF_WEEK+1);
		}if(dateRange.equals(ReportConstants.CURRENT_MONTH)){
			toDate=new Date();				
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
		}if(dateRange.equals(ReportConstants.PREVIOUS_MONTH)){
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
			cal.setTime(toDate);
			fromDate=Utils.adjustDateBy(toDate,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
		}if(dateRange.equals(ReportConstants.CURRENT_QUARTER)){
			toDate=new Date();
			fromDate=getCurrentQuarterStartDate();
		}if(dateRange.equals(ReportConstants.PREVIOUS_QUARTER)){
			toDate=getCurrentQuarterStartDate();
			toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			fromDate=getCurrentQuarterStartDate();
			fromDate=Utils.adjustDateBy(fromDate, Calendar.MONTH, -3);
		}if(dateRange.equals(ReportConstants.CURRENT_CALENDAR_YEAR)){
			toDate=new Date();
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_YEAR)+1);
		}if(dateRange.equals(ReportConstants.PREVIOUS_CALENDAR_YEAR)){
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_YEAR));
			fromDate=Utils.adjustDateBy(toDate,Calendar.YEAR, -1);
			fromDate=Utils.adjustDateBy(fromDate, Calendar.DATE, 1);
		}if(dateRange.equals(ReportConstants.CURRENT_FINANCIAL_YEAR)){
			toDate=new Date();
			fromDate=getCurrentFinancialStartDate();
		}if(dateRange.equals(ReportConstants.PREVIOUS_FINANCIAL_YEAR)){
			toDate=getCurrentFinancialStartDate();
			toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
			fromDate=getCurrentFinancialStartDate();
			fromDate=Utils.adjustDateBy(fromDate,Calendar.YEAR, -1);
		}if(dateRange.equals(ReportConstants.TOMORROW)){
			toDate = Utils.adjustDateBy(new Date(), Calendar.DAY_OF_MONTH, +1);
			fromDate=Utils.adjustDateBy(new Date(), Calendar.DAY_OF_MONTH, +1);				
		}if(dateRange.equals(ReportConstants.THIS_WEEK)){
			fromDate=new Date();
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			int daysLeftInWeek=Calendar.DAY_OF_WEEK - dayOfWeek;					
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, +daysLeftInWeek);					
		}if(dateRange.equals(ReportConstants.NEXT_WEEK)){
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			int daysLeftInWeek=Calendar.DAY_OF_WEEK - dayOfWeek;
			fromDate=Utils.adjustDateBy(new Date(),Calendar.DATE, +daysLeftInWeek+1);					
			int daysNextWeekEnd=daysLeftInWeek+Calendar.DAY_OF_WEEK ;
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, +daysNextWeekEnd);										
		}if(dateRange.equals(ReportConstants.THIS_MONTH)){					
			fromDate=new Date();					
			int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			int noDaysInMonth=cal.getActualMaximum(Calendar.DAY_OF_MONTH);					
			int daysLeftInMonth= noDaysInMonth- dayOfMonth;
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, +daysLeftInMonth);
		}if(dateRange.equals(ReportConstants.NEXT_N_DAYS)){
			fromDate=new Date();
			int noOfDays=Integer.parseInt(numberRange);
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, +noOfDays);
		}if(dateRange.equals(ReportConstants.NEXT_N_WEEKS)){
			fromDate=new Date();
			int noOfWeeks=Integer.parseInt(numberRange)*Calendar.DAY_OF_WEEK;					
			toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, +noOfWeeks);					
		}	
		
		dateMap.put("fromDate", fromDate);
		dateMap.put("toDate", toDate);
		return dateMap;
	}
	
	public Date getCurrentQuarterStartDate(){
		Date date =null;
		Calendar cal = Calendar.getInstance();
		int currentMonth=cal.get(Calendar.MONTH);
		try{
			switch(currentMonth){
				case Calendar.JANUARY:
				case Calendar.APRIL:
				case Calendar.JULY:
				case Calendar.OCTOBER:
					date = new Date();
					date =Utils.adjustDateBy(date,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
					break;
				case Calendar.FEBRUARY:
				case Calendar.MAY:
				case Calendar.AUGUST:
				case Calendar.NOVEMBER:
					date =Utils.adjustDateBy(new Date(),Calendar.MONTH, -1);
					date =Utils.adjustDateBy(date,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
					break;
				case Calendar.MARCH:			
				case Calendar.JUNE:
				case Calendar.SEPTEMBER:
				case Calendar.DECEMBER:
					date =Utils.adjustDateBy(new Date(),Calendar.MONTH, -2);
					date =Utils.adjustDateBy(date,Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH)+1);
					break;
			}
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while setting current quarter start date", e);
		}
		
		return date;
	}
	
	public Date getCurrentFinancialStartDate(){
		Date date =null;		
		try{
			Calendar cal = Calendar.getInstance();			
			int financialStartMonth =Integer.parseInt( GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_FINANCIAL_YEAR_START_MONTH));
			int currentMonth=cal.get(Calendar.MONTH);
			if(currentMonth>=financialStartMonth){
				cal.set(cal.get(Calendar.YEAR),financialStartMonth,1);
			}else{
				cal.set(cal.get(Calendar.YEAR)-1,financialStartMonth,1);
			}			
			date=cal.getTime();
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while setting current quarter start date", e);
		}
		return date;
	}
	
	public void setReportsAsOfDate(FilterData filterData){
		String dateRange = filterData.getDateRange();
		Date toDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		try{
			if(!dateRange.equals(ReportConstants.CUSTOM)){
				//set to date
				if(dateRange.equals(ReportConstants.TODAY)){
					toDate=new Date();
				}if(dateRange.equals(ReportConstants.YESTERDAY)){
					toDate = Utils.adjustDateBy(new Date(), Calendar.DATE, -1);
				}if(dateRange.equals(ReportConstants.END_OF_LAST_WEEK)){
					toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_WEEK));					
				}if(dateRange.equals(ReportConstants.END_OF_LAST_MONTH)){
					toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
				}if(dateRange.equals(ReportConstants.END_OF_LAST_QUARTER)){
					toDate=getCurrentQuarterStartDate();
					toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
				}if(dateRange.equals(ReportConstants.END_OF_LAST_CALENDAR_YEAR)){
					toDate=Utils.adjustDateBy(new Date(),Calendar.DATE, -cal.get(Calendar.DAY_OF_YEAR));
				}if(dateRange.equals(ReportConstants.END_OF_LAST_FINANCIAL_YEAR)){
					toDate=getCurrentFinancialStartDate();
					toDate=Utils.adjustDateBy(toDate, Calendar.DATE, -1);
				}
				filterData.setToDate(Utils.getDateConvertedToString(toDate, DateConstants.INPUT_FORMAT));
			}
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while setting As of Date from date range", e);
		}
	}	
	/*public int getNumberOfDaysInMonth(Date date){
		int numberOfDays=0;
		try{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int currentMonth=cal.get(Calendar.MONTH);
			if(currentMonth>Calendar.MARCH){
				cal.set(cal.get(Calendar.YEAR),Calendar.APRIL,1);
			}else{
				cal.set(cal.get(Calendar.YEAR)-1,Calendar.APRIL,1);
			}
			date=cal.getTime();
		} catch (Exception e) {			
			TPLogger.getLogger().error("Error while setting current quarter start date", e);
		}
		return numberOfDays;
	}*/
	
//	Report Generator for  candidate offers custom Report
	public String  generateCandidateOffersReport(FilterData filterData, String userId,PermissionSet permissionSet, String sessionId, String clientIpAddr){
//		String ext = ReportUtils.getReportExtension(filterData.getReportFormat());
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ".xls";
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			List<CandidateOffersView> reportData = reportManager.getCandidateOffersReport(filterData, userId, permissionSet);
			String xlsName = ReportConstants.XLS_CANDIDATE_OFFERS_REPORT;
			HashMap<String, String> params = new HashMap<String, String>();

//			if (reportData.size() == 0) {
//				reportData.add(new OfferToJoinedView());
//				params.put("no_report_data_message", TPLabels.getLabel("report.error.no_data"));
//			}

			String date_criteria = ReportUtils.getDateCriteria(Utils.convertToSQLDate(filterData.getFromDate(), DateConstants.INPUT_FORMAT), Utils.convertToSQLDate(filterData.getToDate(),
					DateConstants.INPUT_FORMAT));
			String other_criteria = filterData.getSearchInText();
			if(!Utils.isBlankOrNull(other_criteria)){
				other_criteria= "Report for: " + other_criteria;
			}

			reportManager.generateExcelReport(ReportVersionConstants.REPORT_CANDIDATE_OFFERS,xlsName, outputFileName, params, 
					(ArrayList<CandidateOffersView>) reportData,filterData,date_criteria,other_criteria,userId,permissionSet, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}
	
	public String  generateJoinedApplicantCSVReport(FilterData filterData,  String userId,PermissionSet permissionSet, String sessionId, String clientIpAddr){
		String outputFileName = sessionId + String.valueOf(System.currentTimeMillis()) + ".csv";
		setReportsToAndFromDate(filterData);
		try {
			
			ReportManager reportManager = new ReportManager();
			List<CandidateJoiningCSVReportView> reportData = reportManager.getJoinedCandidatesCSVReport(filterData, userId, permissionSet);
			reportManager.generateCSVReport("21", outputFileName, (ArrayList<CandidateJoiningCSVReportView>)reportData,userId);
			AuditAction auditAction = new AuditAction();
			auditAction.insertAuditInfo(null,AuditConstants.TYPE_GENERATED, "21", AuditConstants.AUDIT_REPORT,  userId,null,null,null,true, clientIpAddr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outputFileName;
	}

}