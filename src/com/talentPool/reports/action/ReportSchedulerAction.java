package com.talentPool.reports.action;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.db.DQMetaData;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.customization.service.Service;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.ReportScheduleData;
import com.talentPool.reports.form.ReportForm;
import com.talentPool.reports.manager.CustomizedReportManager;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.reports.manager.ReportSchedulerManager;
import com.talentPool.reports.views.PositionwiseRecruitmentCostDetailView;
import com.talentPool.reports.views.PositionwiseRecruitmentCostSummaryView;
import com.talentPool.selectionProcess.utils.SelectionProcessUtils;
import com.talentPool.user.constants.ModuleConstants;
import com.talentPool.user.constants.PermissionConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;

/**
 * @author shantanu
 * 
 */
public class ReportSchedulerAction extends TPDispatchAction {

	DQMetaData dqMetaData = null;

	// Start Report Scheduler
	public ActionForward addReportScheduler(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "addReportScheduler";
		Integer[] permissions = new Integer[1];
		permissions[0] = PermissionConstants.PERMISSION_REPORTS;
		if(!isUserAuthorized(request, ModuleConstants.MODULE_REPORT_SCHEDULER, permissions,null,null,null)) {
			forward = "authorizationFailure";			
			return mapping.findForward(forward);
		}
		try {
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}
			ReportForm reportForm = (ReportForm) actionForm;
			String scheduleId = reportForm.getScheduleId();

			ReportSchedulerManager reportSchedulerManager = new ReportSchedulerManager();
			ReportScheduleData reportScheduleData = new ReportScheduleData();

			if (!Utils.isBlankOrNull(scheduleId) && errors.size() == 0) {
				reportScheduleData = reportSchedulerManager.getReportSchedule(scheduleId);
				reportForm.setReportName(reportScheduleData.getReportId());
				reportForm.setFrequencyOfScheduler(reportScheduleData.getFrequency());
				String dateStr = Utils.getDateConvertedToString(reportScheduleData.getStartDate(), Utils.regEUDateFormat);
				String timeStr = Utils.getDateConvertedToString(reportScheduleData.getScheduleTime(), "hh:mm a");
				reportForm.setStartDate(dateStr);
				reportForm.setScheduleTime(timeStr);
				reportForm.setEveryDay(reportScheduleData.getEveryday());
				reportForm.setWeekDay(reportScheduleData.getWeekdays());
				reportForm.setDayOfMonth(reportScheduleData.getDayOfMonth());
				reportForm.setDayOfWeek(reportScheduleData.getDayOfWeek());
				reportForm.setUserNameTo(reportScheduleData.getEmailIds());
				reportForm.setMailSubject(reportScheduleData.getMailSubject());
				reportForm.setMailBody(reportScheduleData.getMailBody());
				
				request.setAttribute("CriteriaData", reportSchedulerManager.getReportScheduledCriteria(reportScheduleData));
			} else {
				FilterData filterData = reportForm.getFilterData();
				filterData.setPositionFilter(request.getParameter("positionFilter"));
				filterData.setDepartmentFilter(request.getParameter("departmentFilter"));
				filterData = processFilterData(filterData);

				reportScheduleData = reportSchedulerManager.generateReportScheduleData(filterData);
				
				request.setAttribute("CriteriaData", reportSchedulerManager.getReportScheduledCriteria(reportScheduleData));
				if(reportScheduleData.getTemplateId() != null){
					request.setAttribute("reportTemplateId", reportScheduleData.getTemplateId());
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error ", e);
		}

		return mapping.findForward(forward);
	}

	public FilterData processFilterData(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		if (filterDataOriginal.getReportName().equals("1")) {
			filterData = processFilterDataHiringStatus(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("2")) {
			filterData = processFilterDataHiringFunnel(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("3")) {
			filterData = processFilterDataPositionSummary(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("4")) {
			filterData = processFilterDataPendingActions(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("5")) {
			filterData = processFilterDataHiringEfficiency(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("6")) {
			filterData = processFilterDataSourcewiseHiring(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("7")) {
			filterData = processFilterDataMonthlyJoining(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("8")) {
			filterData = processFilterDataSourcewiseImport(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("9")) {
			filterData = processFilterDataOfferToJoined(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("10")) {
			filterData = processFilterDataCandidateComparision(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("11")) {
			filterData = processFilterDataRecruitmentCost(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("13")) {
			filterData = processFilterDataInterviewList(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("14")) {
			filterData = processFilterDataApplicantDetail(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("15")) {
			filterData = processFilterDataImportReoprt(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("16")) {
			filterData = processFilterDataUserActivity(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("17")) {
			filterData = processFilterDataPostionActivity(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("18")) {			
			filterData = processFilterDataCandidateStatus(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("27")) {			
			filterData = processFilterDataInterviewListStatus(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("28")) {			
			filterData = processFilterDataOfferToJoinedDetailed(filterDataOriginal);
		} else if (filterDataOriginal.getReportName().equals("46")) {			
			filterData = processFilterDataOfferCTCDetailed(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("47")) {			
			filterData = processFilterDataOneStopFileReport(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("48")) {			
			filterData = processFilterDataFinanceCostSheetReport(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("49")) {			
			filterData = processFilterDataJoinerDataFinanceReport(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("50")) {			
			filterData = processFilterDataIndiaHiringReqReport(filterDataOriginal);
		}else if (filterDataOriginal.getReportName().equals("51")) {			
			filterData = processFilterDataIndiaHiringSummaryReport(filterDataOriginal);
		}else { 
			ArrayList<String> customizedReportNames = new CustomizedReportManager().getAllCustomizedReportNames();
			if(!Utils.isListEmptyOrNull(customizedReportNames) 
					&& customizedReportNames.contains(filterDataOriginal.getReportName())) {
				filterData = processFilterDataCustomizedReports(filterDataOriginal);
			}
		}

		return filterData;
	}

	public FilterData processFilterDataOfferCTCDetailed(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(ReportConstants.FORMAT_PRE_FORMATTED);
		filterData.setReportType("");
		filterData.setReportTemplateId(filterDataOriginal.getReportTemplateId());

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");		
		return filterData;
	}
	
	
	public FilterData processFilterDataOneStopFileReport(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");		
		return filterData;
	}
	
	public FilterData processFilterDataFinanceCostSheetReport(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId(filterDataOriginal.getPositionId());
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(ReportConstants.FORMAT_EXCEL);
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");		
		return filterData;
	}
	
	public FilterData processFilterDataJoinerDataFinanceReport(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId(filterDataOriginal.getPositionId());
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(ReportConstants.FORMAT_EXCEL);
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");		
		return filterData;
	}
	public FilterData processFilterDataIndiaHiringReqReport(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId(filterDataOriginal.getPositionId());
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(ReportConstants.FORMAT_EXCEL);
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");		
		return filterData;
	}
	
	public FilterData processFilterDataIndiaHiringSummaryReport(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId(filterDataOriginal.getPositionId());
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(ReportConstants.FORMAT_EXCEL);
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");		
		return filterData;
	}

	// Process FilterData Hiring Status
	public FilterData processFilterDataHiringStatus(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		filterData.setDateRange("");
		filterData.setFromDate("");
		filterData.setToDate("");
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Hiring Funnel
	public FilterData processFilterDataHiringFunnel(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();

		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}

		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// Process FilterData Position Summary
	public FilterData processFilterDataPositionSummary(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// Process FilterData Pending Actions
	public FilterData processFilterDataPendingActions(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");
		filterData.setDateRange("");
		filterData.setFromDate("");
		filterData.setToDate("");
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers(filterDataOriginal.getInterviewers());
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// Process FilterData Hiring Efficiency
	public FilterData processFilterDataHiringEfficiency(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Sourcewise Hiring
	public FilterData processFilterDataSourcewiseHiring(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		if (Utils.isBlankOrNull(filterDataOriginal.getSourceCategoryId())) {
			filterData.setSourceCategoryId("");
		} else {
			filterData.setSourceCategoryId(filterDataOriginal.getSourceCategoryId());
		}
		if (Utils.isBlankOrNull(filterDataOriginal.getSourceId())) {
			filterData.setSourceId("");
		} else {
			filterData.setSourceId(filterDataOriginal.getSourceId());
		}
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Monthly Joining
	public FilterData processFilterDataMonthlyJoining(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		if (filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Sourcewise Import
	public FilterData processFilterDataSourcewiseImport(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}

		if (!Utils.isBlankOrNull(filterDataOriginal.getSourceId())) {
			filterData.setSourceId("");
		} else {
			filterData.setSourceId(filterDataOriginal.getSourceId());
		}
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Offer To Joined
	public FilterData processFilterDataOfferToJoined(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());

		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Candidate Comparision
	public FilterData processFilterDataCandidateComparision(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId(filterDataOriginal.getPositionId());
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");
		filterData.setDateRange("");
		filterData.setFromDate("");
		filterData.setToDate("");
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		if (!Utils.isBlankOrNull(filterDataOriginal.getStepIds()) && !Utils.isBlankOrNull(filterDataOriginal.getApplicants())) {
			filterData.setApplicants(filterDataOriginal.getApplicants());
		} else {
			filterData.setApplicants("");
		}

		filterData.setStepIds(filterDataOriginal.getStepIds());
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// processFilterDataRecruitmentCost
	public FilterData processFilterDataRecruitmentCost(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		if (Utils.isBlankOrNull(filterDataOriginal.getSourceId())) {
			filterData.setSourceId("");
		} else {
			filterData.setSourceId(filterDataOriginal.getSourceId());
		}
		filterData.setRecruitmentCostReportType(filterDataOriginal.getRecruitmentCostReportType());
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Interview List
	public FilterData processFilterDataInterviewList(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
			if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
					filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
				filterData.setNumberRange(filterDataOriginal.getNumberRange());
			}else{
				filterData.setNumberRange("");
			}
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers(filterDataOriginal.getInterviewers());
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");				
		return filterData;
	}
	
	// process FilterData Interview List
		public FilterData processFilterDataInterviewListStatus(FilterData filterDataOriginal) {
			FilterData filterData = new FilterData();
			ReportGenerator reportGenerator = new ReportGenerator();
			filterData.setReportName(filterDataOriginal.getReportName());
			filterData.setFilterId(filterDataOriginal.getFilterId());
			filterData.setActionId("");
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
			filterData.setSubDepartmentId("");
			filterData.setSubSubDepartmentId("");
			filterData.setPositionId(filterDataOriginal.getPositionId());
			filterData.setUsers(filterDataOriginal.getUsers());
			filterData.setInterviewers(filterDataOriginal.getInterviewers());
			filterData.setReportFormat(filterDataOriginal.getReportFormat());
			filterData.setReportType("");

			if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
				filterData.setDateRange(filterDataOriginal.getDateRange());
				reportGenerator.setReportsToAndFromDate(filterDataOriginal);
				filterData.setFromDate(filterDataOriginal.getFromDate());
				filterData.setToDate(filterDataOriginal.getToDate());
				if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
						filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
					filterData.setNumberRange(filterDataOriginal.getNumberRange());
				}else{
					filterData.setNumberRange("");
				}
			}
			filterData.setPositionFilter(filterDataOriginal.getPositionFilter());
			filterData.setDepartmentFilter(filterDataOriginal.getDepartmentFilter());
			filterData.setSourceId("");
			filterData.setRecruitmentCostReportType("");
			filterData.setStages("");
			filterData.setDegreeIds("");
			filterData.setMinExp("");
			filterData.setMaxExp("");
			filterData.setApplicants("");
			filterData.setStepIds(filterDataOriginal.getStepIds());
			filterData.setFromMonth("");
			filterData.setToMonth("");
			filterData.setFromYear("");
			filterData.setToYear("");
			filterData.setOrderBy("");				
			return filterData;
		}
		
		// process FilterData Interview List
				public FilterData processFilterDataOfferToJoinedDetailed(FilterData filterDataOriginal) {
					FilterData filterData = new FilterData();
					ReportGenerator reportGenerator = new ReportGenerator();
					filterData.setReportName(filterDataOriginal.getReportName());
					filterData.setFilterId(filterDataOriginal.getFilterId());
					filterData.setActionId("");
					filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
					filterData.setSubDepartmentId("");
					filterData.setSubSubDepartmentId("");
					filterData.setPositionId("");
					filterData.setUsers(filterDataOriginal.getUsers());
					filterData.setReportFormat(filterDataOriginal.getReportFormat());
					filterData.setReportType("");

					if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
						filterData.setDateRange(filterDataOriginal.getDateRange());
						reportGenerator.setReportsToAndFromDate(filterDataOriginal);
						filterData.setFromDate(filterDataOriginal.getFromDate());
						filterData.setToDate(filterDataOriginal.getToDate());
						if(filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_DAYS) ||
								filterDataOriginal.getDateRange().equals(ReportConstants.NEXT_N_WEEKS)){
							filterData.setNumberRange(filterDataOriginal.getNumberRange());
						}else{
							filterData.setNumberRange("");
						}
					}
					filterData.setSourceId(filterDataOriginal.getSourceId());
					filterData.setSourceCategoryId(filterDataOriginal.getSourceCategoryId());
					filterData.setSourceFilter(filterDataOriginal.getSourceFilter());
					filterData.setRecruitmentCostReportType("");
					filterData.setInterviewers("");
					filterData.setStages("");
					filterData.setDegreeIds("");
					filterData.setMinExp("");
					filterData.setMaxExp("");
					filterData.setApplicants("");
					filterData.setStepIds(filterDataOriginal.getStepIds());
					filterData.setFromMonth("");
					filterData.setToMonth("");
					filterData.setFromYear("");
					filterData.setToYear("");
					filterData.setOrderBy("");				
					return filterData;
				}


	// process FilterData Applicant Detail
	public FilterData processFilterDataApplicantDetail(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId("");
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		filterData.setUsers("");
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		if (!Utils.isBlankOrNull(filterDataOriginal.getSourceId())) {
			filterData.setSourceId("");
		} else {
			filterData.setSourceId(filterDataOriginal.getSourceId());
		}
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");		
		filterData.setDegreeIds(filterDataOriginal.getDegreeIds());
		filterData.setMinExp(filterDataOriginal.getMinExp());
		filterData.setMaxExp(filterDataOriginal.getMaxExp());
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// process FilterData Import Reoprt
	public FilterData processFilterDataImportReoprt(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");
		filterData.setPositionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SOURCE) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals("-1")) {
			filterData.setUsers("");
		} else {
			filterData.setUsers(filterDataOriginal.getUserId());// For database as User Id is for login user
		}
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType("");

		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_IMPORTED_BY) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals("-1")) {
			filterData.setSourceId("");
		} else {
			filterData.setSourceId(filterDataOriginal.getSourceId());
		}

		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy(filterDataOriginal.getOrderBy());
		return filterData;
	}

	// process FilterData User Activity
	public FilterData processFilterDataUserActivity(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL) || filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());

		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL) || filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers(filterDataOriginal.getInterviewers());
		filterData.setStages(filterDataOriginal.getStages());
		filterData.setActivities(filterDataOriginal.getActivities());
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;

	}

	// process FilterData Postion Activity
	public FilterData processFilterDataPostionActivity(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId("");
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)
				|| filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL) || filterDataOriginal.getDepartmentId().equals("-1")) {
			filterData.setDepartmentId("");
		} else {
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		filterData.setSubDepartmentId(filterDataOriginal.getSubDepartmentId());
		filterData.setSubSubDepartmentId(filterDataOriginal.getSubSubDepartmentId());
		
		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL) || filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}
		filterData.setUsers(filterDataOriginal.getUsers());
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());
		if (!Utils.isBlankOrNull(filterDataOriginal.getDateRange())) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		}
		filterData.setSourceId("");
		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers(filterDataOriginal.getInterviewers());
		filterData.setStages(filterDataOriginal.getStages());
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// processFilterDataCandidateStatus
	public FilterData processFilterDataCandidateStatus(FilterData filterDataOriginal) {
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		filterData.setFilterId(filterDataOriginal.getFilterId());
		filterData.setActionId(filterDataOriginal.getActionId());
		filterData.setDepartmentId("");
		filterData.setSubDepartmentId("");
		filterData.setSubSubDepartmentId("");

		if (filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS) || filterDataOriginal.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_DEPARTMENT)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}

		if (filterDataOriginal.getActionId().equals(ReportConstants.FILTER_IMPORTED_BY) || filterDataOriginal.getActionId().equals(ReportConstants.FILTER_REJECTED_BY) || filterDataOriginal.getActionId().equals(ReportConstants.FILTER_MOVED_BY) || !filterDataOriginal.getActionId().equals("-1")) {
			filterData.setUsers(filterDataOriginal.getUsers());
		} else {
			filterData.setUsers("");
		}

		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());

		if (filterDataOriginal.getActionId().equals(ReportConstants.FILTER_IMPORTED_BY) || 
				filterDataOriginal.getActionId().equals(ReportConstants.FILTER_REJECTED_BY) || 
				filterDataOriginal.getActionId().equals(ReportConstants.FILTER_MOVED_BY) ) {
			filterData.setDateRange(filterDataOriginal.getDateRange());
			reportGenerator.setReportsToAndFromDate(filterDataOriginal);
			filterData.setFromDate(filterDataOriginal.getFromDate());
			filterData.setToDate(filterDataOriginal.getToDate());
		} else {			
			filterData.setDateRange(null);
			filterData.setFromDate(null);
			filterData.setToDate(filterDataOriginal.getToDate());
		}

		if (Utils.isBlankOrNull(filterDataOriginal.getSourceCategoryId())) {
			filterData.setSourceCategoryId("");
		} else {
			filterData.setSourceCategoryId(filterDataOriginal.getSourceCategoryId());
		}
		if (Utils.isBlankOrNull(filterDataOriginal.getSourceId())) {
			filterData.setSourceId("");
		} else {
			filterData.setSourceId(filterDataOriginal.getSourceId());
		}

		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;

	}

	public ActionForward getReportSchedulerXMLFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		ReportSchedulerManager reportSchedulerManager = new ReportSchedulerManager();
		try {
			String userId = (String) request.getSession().getAttribute("userId");
			String reportName = request.getParameter("reportName");
			ArrayList<ReportScheduleData> scheduledReports = reportSchedulerManager.getScheduledReports(userId, reportName);
			String xmlFile = reportSchedulerManager.getXMLForScheduledReports(scheduledReports);
			request.setAttribute("xmlFile", xmlFile);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Fetching Scheduled Report xmlFile", e);
		}
		return mapping.findForward(forward);
	}

	/* Validate Data Before Submit */
	public ActionForward validateAddReportScheduler(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "validateAddReportScheduler";
		ArrayList receiverConflicts = new ArrayList();
		try {
			String userId = (String) request.getSession(false).getAttribute("userId");
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			ReportForm reportForm = (ReportForm) actionForm;
			FilterData filterData = reportForm.getFilterData();
			filterData = processFilterData(filterData);

			String toNames = reportForm.getUserNameTo();
			PositionManager positionManager = new PositionManager();
			ArrayList<LoginData> users = positionManager.getUsersForRole(null);
			String[] arrNames = toNames.replaceAll(";", ",").split(",");
			ArrayList<String> toUserIds = SelectionProcessUtils.getUserIdsFromNames(arrNames, users);
			//ArrayList toUserNames = new ArrayList(Arrays.asList(arrNames));			
			
			receiverConflicts = getReceiverConflicts(userId, toUserIds, reportForm.getReportName(), filterData);

			for (int i = 0; receiverConflicts != null && i < receiverConflicts.size(); i++) {
				SimpleDataObject sDo = (SimpleDataObject) receiverConflicts.get(i);
				String uId = sDo.getString("userId");
				if (toUserIds.indexOf(uId) >= 0) {
					toUserIds.remove(uId);
				}
			}
			
			String sbUserIds="";
			for(int i = 0; toUserIds != null && i < toUserIds.size(); i++) {
				if(i>0){
					sbUserIds+=",";
				}
				sbUserIds += toUserIds.get(i);
			}
			reportForm.setUserNameTo(sbUserIds);			
			
			if (receiverConflicts == null || receiverConflicts.size() == 0) {
				return saveReportScheduleCriteria(mapping, actionForm, request, response);
			}else {				
				request.setAttribute("receiverConflicts", receiverConflicts);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in validateShortlist", e);
		}
		return mapping.findForward(forward);
	}

	public ActionForward saveReportScheduleCriteria(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "closeModalCall";

		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		ReportSchedulerManager reportSchedulerManager = new ReportSchedulerManager();
		ReportManager reportManager = new ReportManager();

		try {
			String userId = (String) request.getSession().getAttribute("userId");
			ReportForm reportForm = (ReportForm) actionForm;
			FilterData filterData = reportForm.getFilterData();		
			filterData = processFilterData(filterData);
			String toNames = reportForm.getUserNameTo();
			
			String[] arrNames = toNames.replaceAll(";", ",").split(",");
			ArrayList toUserIds = new ArrayList(Arrays.asList(arrNames));						
			String scheduleId = reportForm.getScheduleId();
			String date = reportForm.getStartDate();
			String time = reportForm.getScheduleTime();

			String dateTimeStr = date + " " + time; 
			// format
			Date dateTime = Utils.convertToDate(dateTimeStr, "dd/MM/yyyy hh:mm a");
			if (errors.size() == 0) {
				ReportScheduleData reportScheduleData = new ReportScheduleData();
				reportScheduleData.setFrequency(reportForm.getFrequencyOfScheduler());
				reportScheduleData.setEveryday(reportForm.getEveryDay());
				reportScheduleData.setWeekdays(reportForm.getWeekDay());
				reportScheduleData.setDayOfMonth(reportForm.getDayOfMonth());
				reportScheduleData.setDayOfWeek(reportForm.getDayOfWeek());
				reportScheduleData.setStartDate(Utils.convertToSQLDate(date, Utils.regEUDateFormat));
				reportScheduleData.setStringStartDate(dateTimeStr);
				reportScheduleData.setReportId(reportForm.getReportName());
				reportScheduleData.setEmailIds(reportForm.getUserNameTo());				
				reportScheduleData.setMailSubject(reportForm.getMailSubject());
				reportScheduleData.setMailBody(reportForm.getMailBody());
				reportScheduleData.setScheduleTime(new Timestamp(dateTime.getTime()));
				
				if (Utils.isBlankOrNull(scheduleId)) {					
					String id = reportSchedulerManager.createReportSchedule(reportScheduleData, userId, filterData, toUserIds);
					ArrayList<String> customizedReports = new CustomizedReportManager().getAllCustomizedReportNames();
					String reportName = reportForm.getReportName();
					if (customizedReports.contains(reportName)) {
						String serviceName = ReportUtils.getServiceNameForCustomizedReport(reportName);
						try{
							Class<Service> serv = (Class<Service>) Class.forName(serviceName);
							Service reportService= serv.newInstance();
							reportService.updateCustomizedReportSchedule(id, filterData);
						}catch (ClassNotFoundException e){
							TPLogger.getLogger().error("Report Scheduler Class is not present.");
						}
					}
				} else {
					reportSchedulerManager.updateReportSchedule(reportScheduleData, userId, scheduleId, toUserIds);
				}
			}
		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error updating Report Schedule", sqle);
			errors.add("SQL Exception", new ActionError(""));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While Saving Report Schedule Data ", e);
			errors.add("report_scheduler.errors.scheduler_data", new ActionError("report_scheduler.errors.scheduler_data"));
		}
		if (errors.size() > 0) {
			saveErrors(request, errors);
			return addReportScheduler(mapping, actionForm, request, response);
		}
		return mapping.findForward(forward);
	}

	public ActionForward deleteScheduleReport(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "xmlFile";
		String xmlFile = "";
		// Do the validations here
		ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
		if (errors == null) {
			errors = new ActionErrors();
		}
		ReportSchedulerManager reportSchedulerManager = new ReportSchedulerManager();
		try {
			ReportForm reportForm = (ReportForm) actionForm;
			String scheduleId = reportForm.getScheduleId();
			reportSchedulerManager.removeScheduledReport(scheduleId);
			xmlFile = Utils.getXMLForIds(scheduleId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting Scheduled Report", e);
			xmlFile = Utils.getXMLForError(null);
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ArrayList getReceiverConflicts(String userId, ArrayList receiverUserId, String reportName, FilterData filterData) {
		ArrayList conflicts = new ArrayList();
		ArrayList positionIds = new ArrayList<String>();
		ReportSchedulerManager reportSchedulerManager = new ReportSchedulerManager();
		int reportId = 0;
		try {
			reportId = Integer.parseInt(reportName);
		} catch (NumberFormatException e) {
			log.debug("Cannot parse reportId as Integer, it must be a customized report", e);			
		}
		try {
			switch (reportId) {
			case 1:				
				positionIds = getPositionIdsToBeSentInHiringStatus(userId, filterData);				
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);				
				break;
			case 2:
				positionIds = getPositionIdsToBeSentInHiringFunnel(userId, filterData);				
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);				
				break;
			case 3:
				positionIds = getPositionIdsToBeSentInPositionSummary(userId, filterData);				
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);				
				break;			
			case 5:
				positionIds = getPositionIdsToBeSentInHiringEfficiency(userId, filterData);				
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);				
				break;			
			case 9:
				positionIds = getPositionIdsToBeSentInOfferToJoin(userId, filterData);				
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);				
				break;
			case 10:
				positionIds = getPositionIdsToBeSentInCandidateComparision(userId, filterData);				
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);				
				break;
			case 11:				
				if(filterData.getRecruitmentCostReportType().equals(TPLabels.getLabel("report.label.positionwise_recruitment_cost_report"))){
					positionIds = getPositionIdsToBeSentInRecruitmentCost(userId, filterData);					
					conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);										
					break;					
				}else{					
					break;
				}			
			
			case 16:
				if(!filterData.getFilterId().equals(ReportConstants.FILTER_ALL)){
					positionIds = getPositionIdsToBeSentInUserActivity(userId, filterData);
					conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);
					break;
				}else{
					break;
				}				
			case 17:				
				positionIds = getPositionIdsToBeSentInPositionActivity(userId, filterData);
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);
				break;						
			case 18:
				positionIds = getPositionIdsToBeSentInCandidateStatus(userId, filterData);
				conflicts = reportSchedulerManager.getConflicts(receiverUserId, positionIds);
				break;
			}
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while Getting Conflicts", e);
		}

		return conflicts;

	}

	public ArrayList getPositionIdsToBeSentInHiringStatus(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			ArrayList reportList=new ArrayList();
			PermissionSet permissionSet = reportManager.getUserPermission(userId);			
			if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_SUMMARY)){			
				reportList = reportManager.getHiringStatusSummaryReport(filterData, userId, permissionSet);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}else if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)){			
				reportList = reportManager.getHiringStatusDetailReport(filterData, userId,permissionSet);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}
	

	public ArrayList getPositionIdsToBeSentInHiringFunnel(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			ArrayList reportList=new ArrayList();
			PermissionSet permissionSet = reportManager.getUserPermission(userId);			
			if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_SUMMARY)){			
				reportList = reportManager.getHiringFunnelSummaryReport(filterData, userId, permissionSet);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}else if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)){			
				reportList = reportManager.getHiringFunnelDetailReport(filterData, userId, permissionSet);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}

	
	public ArrayList getPositionIdsToBeSentInPositionSummary(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			PermissionSet permissionSet = reportManager.getUserPermission(userId);
			
			ArrayList rawList = reportManager.getPositionSummaryReport(filterData, userId, permissionSet);
			List<String> headerList = (ArrayList<String>) rawList.get(0);
			ArrayList<SimpleDataObject> reportList = (ArrayList<SimpleDataObject>) rawList.get(1);
			
			pIds = getDistinctPositionIds(reportList, "positionId");			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}

	public ArrayList getPositionIdsToBeSentInHiringEfficiency(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			ArrayList reportList=new ArrayList();
			PermissionSet permissionSet = reportManager.getUserPermission(userId);			
			if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_SUMMARY)){
				reportList = reportManager.getHiringEfficiencySummaryReport(filterData, userId, permissionSet);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}else if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)){
				reportList = reportManager.getHiringEfficiencyDetailsReport(filterData, userId, permissionSet);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}

			
	public ArrayList getPositionIdsToBeSentInOfferToJoin(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			ArrayList reportList=new ArrayList();
			List listReport=new ArrayList();
			PermissionSet permissionSet = reportManager.getUserPermission(userId);			
			if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_SUMMARY)){
				//position ids are taken from Detail Report even for Summary
				listReport=reportManager.getDetailedOfferToJoinedReportData(filterData, userId, permissionSet);
				reportList = new ArrayList(listReport);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}else if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)){
				listReport=reportManager.getDetailedOfferToJoinedReportData(filterData, userId, permissionSet);
				reportList = new ArrayList(listReport);
				pIds = getDistinctPositionIds(reportList, "positionId");
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}

	public ArrayList getPositionIdsToBeSentInCandidateComparision(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			PermissionSet permissionSet = reportManager.getUserPermission(userId);
			pIds.add(filterData.getPositionId());						
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}		
		return pIds;
	}
	
	public ArrayList getPositionIdsToBeSentInRecruitmentCost(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			ArrayList reportList=new ArrayList();
			PermissionSet permissionSet = reportManager.getUserPermission(userId);			
			if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_SUMMARY)){				
				reportList = (ArrayList<PositionwiseRecruitmentCostSummaryView>) reportManager.getPositionwiseRecruitmentCostSummaryReport(filterData);
				pIds = getDistinctPositionIds(reportList, "positionId");				
			}else if(filterData.getReportType().equals(ReportConstants.REPORT_TYPE_DETAILS)){				
				reportList = (ArrayList<PositionwiseRecruitmentCostDetailView>) reportManager.getPositionwiseRecruitmentCostDetailReport(filterData);	
				pIds = getDistinctPositionIds(reportList, "positionId");				
			}			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}

	public ArrayList getPositionIdsToBeSentInUserActivity(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			PermissionSet permissionSet = reportManager.getUserPermission(userId);
			ArrayList reportList = reportManager.getUserActivityReportData(filterData, userId, permissionSet);
			pIds = getDistinctPositionIds(reportList, "positionId");			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver User Activity", e);
		}
		return pIds;
	}
	
	public ArrayList getPositionIdsToBeSentInPositionActivity(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			PermissionSet permissionSet = reportManager.getUserPermission(userId);
			ArrayList reportList = reportManager.getPositionActivityReportData(filterData, userId, permissionSet);
			pIds = getDistinctPositionIds(reportList, "positionId");			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Position Activity", e);
		}
		return pIds;
	}

	public ArrayList getPositionIdsToBeSentInCandidateStatus(String userId, FilterData filterData) {
		ArrayList pIds = new ArrayList();
		ReportManager reportManager = new ReportManager();
		try {
			PermissionSet permissionSet = reportManager.getUserPermission(userId);
			ArrayList reportList = reportManager.getCandidateStatusReport(filterData, userId, permissionSet);
			pIds = getDistinctPositionIds(reportList, "applicantPositionId");			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while Scheduled Report for Receiver Candidate Status", e);
		}
		return pIds;
	}

	private ArrayList<String> getDistinctPositionIds(ArrayList<SimpleDataObject> result, String property) {
		ArrayList pIds = new ArrayList();
		for (int i = 0; result != null && i < result.size(); i++) {
			SimpleDataObject sDo = result.get(i);
			String positionId = sDo.getString(property);
			if (!Utils.isBlankOrNull(positionId)) {
				if(pIds.indexOf(positionId)<0){
				pIds.add(positionId);
				}
			}
		}
		return pIds;
	}
	
	/**
	 * @param filterDataOriginal
	 * @return
	 */
	public FilterData processFilterDataCustomizedReports(FilterData filterDataOriginal) {
		
		// TODO : Since this method will be common to all customized reports, 
		//		we need to make sure that only the applicable filters are set of respective reports.
		
		FilterData filterData = new FilterData();
		ReportGenerator reportGenerator = new ReportGenerator();
		filterData.setReportName(filterDataOriginal.getReportName());
		
		filterData.setPositionFilter(filterDataOriginal.getPositionFilter());
		if (filterDataOriginal.getPositionFilter().equals(ReportConstants.FILTER_ALL_POSITIONS)
				|| filterDataOriginal.getPositionFilter().equals(ReportConstants.FILTER_OPEN_POSITIONS)
				|| filterDataOriginal.getPositionFilter().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)
				|| filterDataOriginal.getPositionId().equals("-1")) {
			filterData.setPositionId("");			
		} else {
			filterData.setPositionId(filterDataOriginal.getPositionId());
		}

		filterData.setDepartmentFilter(filterDataOriginal.getDepartmentFilter());
		if (filterDataOriginal.getDepartmentFilter().equals(ReportConstants.FILTER_ALL_DEPARTMENTS)) {
			filterData.setDepartmentId("");
		} else {	
			filterData.setDepartmentId(filterDataOriginal.getDepartmentId());
		}
		
		filterData.setReportFormat(filterDataOriginal.getReportFormat());
		filterData.setReportType(filterDataOriginal.getReportType());

		filterData.setDateRange(filterDataOriginal.getDateRange());
		reportGenerator.setReportsToAndFromDate(filterDataOriginal);
		filterData.setFromDate(filterDataOriginal.getFromDate());
		filterData.setToDate(filterDataOriginal.getToDate());

		filterData.setRecruitmentCostReportType("");
		filterData.setInterviewers("");
		filterData.setStages("");
		filterData.setDegreeIds("");
		filterData.setMinExp("");
		filterData.setMaxExp("");
		filterData.setApplicants("");
		filterData.setStepIds("");
		filterData.setFromMonth("");
		filterData.setToMonth("");
		filterData.setFromYear("");
		filterData.setToYear("");
		filterData.setOrderBy("");
		return filterData;
	}

	// End Report Scheduler

}