/**
 * 
 */
package com.talentPool.reports.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.user.dataobject.LoginData;

/**
 * @author shivprasad
 * 
 */
public class ReportForm extends TPActionForm {
	private static final long serialVersionUID = 1L;
	private String reportName;
	private String fromDate;
	private String toDate;
	private String filterId;
	private String positionId;
	private String departmentId;
	private String subDepartmentId;
	private String subSubDepartmentId;
	private String orderBy;
	private String reportFormat = ReportConstants.FORMAT_HTML;
	private String reportType = ReportConstants.REPORT_TYPE_SUMMARY;	
	private String positionTitle;
	private String departmentTitle;
	private String subDepartmentTitle;
	private String subSubDepartmentTitle;
	private String fromMonth;
	private String fromYear;
	private String toMonth;
	private String toYear;
	private String groupBy;
	private String columns;
	private String filters;
	private String fieldIds;

	private ArrayList userIds;
	private ArrayList userNames;
	private ArrayList applicantIds;
	private ArrayList applicantNames;

	private String userId;
	private String userName;
	private String applicantId;
	private String applicantName;
	private String sourceId;
	private String sourceName;
	private String sourceCategoryId;
	private String sourceCategoryName;
	private String roleId;
	private String roleName;


	private ArrayList sourceIds;
	private ArrayList sourceNames;
	private String sourceFilter;
	private ArrayList sourceCategoryIds;
	private ArrayList sourceCategoryNames;
	private String sourceCategoryFilter;
	private ArrayList roleIds;
	private ArrayList roleNames;
	private String roleFilter;
	
	private ArrayList positionIds;
	private ArrayList positionTitles;
	private String positionFilter;
	private ArrayList departmentIds;
	private ArrayList departmentTitles;
	private String departmentFilter;
	
	private String userFilter;
	private String auditType;
	private String entityType;
	
	private String reportTemplateId;
	
	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getPositionFilter() {
		return positionFilter;
	}

	public void setPositionFilter(String positionFilter) {
		this.positionFilter = positionFilter;
	}

	public String getDepartmentFilter() {
		return departmentFilter;
	}

	public void setDepartmentFilter(String departmentFilter) {
		this.departmentFilter = departmentFilter;
	}


	/**
	 * @return the sourceFilter
	 */
	public String getSourceFilter() {
		return sourceFilter;
	}

	/**
	 * @param sourceFilter the sourceFilter to set
	 */
	public void setSourceFilter(String sourceFilter) {
		this.sourceFilter = sourceFilter;
	}

	/**
	 * @return the sourceCategoryFilter
	 */
	public String getSourceCategoryFilter() {
		return sourceCategoryFilter;
	}

	/**
	 * @param sourceCategoryFilter the sourceCategoryFilter to set
	 */
	public void setSourceCategoryFilter(String sourceCategoryFilter) {
		this.sourceCategoryFilter = sourceCategoryFilter;
	}

	private String fromDt;
	private String interviewers;
	private List<LoginData> interviewerList;
	private String stages;	
	private String minExp;
	private String maxExp;
	private String degreeIds;
	
	/* Variables for Candidate Comparison Report */
	private String stepIds;
	private String stepTitles;
	private String applicants;	
	private String users;	
	/* Variable for Recruitment Cost Report */
	private String recruitmentCostReportType;
	
	/* Variable for Candidate Status Report */
	String actionId;	
	String dateRange;
	
	/* Report Scheduler*/
	String scheduleId;
	String reportId;
	String frequencyOfScheduler;
	String startDate;
	String userNameTo;	
	String scheduleTime;
	String dayOfWeek;
	String dayOfMonth;
	String everyDay;
	String weekDay;
	String mailSubject;
	String mailBody;	
	String isConflict;
	String numberRange;
	
	String joinedCandidatesReportFields;
	String isReportFormatCSV = ReportConstants.REPORT_FORMAT_NON_CSV;
	
	String selectedExpenseTypes;
	String selectedExpenseTypeNames;
	String selectedActivityTypes;
	String selectedActivityTypeNames;
	String selectedImportedByUsers;
	String selectedImportedByUserNames;
	String selectedUserIds;
	String selectedUserNames;
	String selectedActivityByUsers;
	String selectedActivityByUserNames;
	
	String fromAppointmentDate;
	String toAppointmentDate;
	String appointmentDateRange;
	String appointmentNumberRange;
	
	String stageNames;
	
	String entityTypeId;
	
	String entityTypeTitle;
	
	String budgetGradeId;
	String budgetGradeName;
	String budgetBandId;
	String budgetBandName;
	String budgetStatus;
	String budgetStatusTitle;
	
	private String selectedPositionOwnerIds;
	
	public String getJoinedCandidatesReportFields() {
		return joinedCandidatesReportFields;
	}

	public void setJoinedCandidatesReportFields(String joinedCandidatesReportFields) {
		this.joinedCandidatesReportFields = joinedCandidatesReportFields;
	}

	private String activities;
	/**
	 * @return the users
	 */
	public String getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(String users) {
		this.users = users;
	}

	/**
	 * @return the interviewers.
	 */
	public String getInterviewers() {
		return interviewers;
	}

	/**
	 * @param interviewers 
	 *			the interviewers to set.
	 */
	public void setInterviewers(String interviewers) {
		this.interviewers = interviewers;
	}

	/**
	 * @return Returns the fromDt.
	 */
	public String getFromDt() {
		return fromDt;
	}

	/**
	 * @param fromDt
	 *            The fromDt to set.
	 */
	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}

	/**
	 * @return Returns the sourceIds.
	 */
	public ArrayList getSourceIds() {
		return sourceIds;
	}

	/**
	 * @param sourceIds
	 *            The sourceIds to set.
	 */
	public void setSourceIds(ArrayList sourceIds) {
		this.sourceIds = sourceIds;
	}

	/**
	 * @return Returns the sourceNames.
	 */
	public ArrayList getSourceNames() {
		return sourceNames;
	}

	/**
	 * @param sourceNames
	 *            The sourceNames to set.
	 */
	public void setSourceNames(ArrayList sourceNames) {
		this.sourceNames = sourceNames;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return Returns the sourceName.
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName
	 *            The sourceName to set.
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	
	
	

	/**
	 * @return the sourceCategoryId
	 */
	public String getSourceCategoryId() {
		return sourceCategoryId;
	}

	/**
	 * @param sourceCategoryId the sourceCategoryId to set
	 */
	public void setSourceCategoryId(String sourceCategoryId) {
		this.sourceCategoryId = sourceCategoryId;
	}

	/**
	 * @return the sourceCategoryName
	 */
	public String getSourceCategoryName() {
		return sourceCategoryName;
	}

	/**
	 * @param sourceCategoryName the sourceCategoryName to set
	 */
	public void setSourceCategoryName(String sourceCategoryName) {
		this.sourceCategoryName = sourceCategoryName;
	}

	/**
	 * @return the sourceCategoryIds
	 */
	public ArrayList getSourceCategoryIds() {
		return sourceCategoryIds;
	}

	/**
	 * @param sourceCategoryIds the sourceCategoryIds to set
	 */
	public void setSourceCategoryIds(ArrayList sourceCategoryIds) {
		this.sourceCategoryIds = sourceCategoryIds;
	}

	/**
	 * @return the sourceCategoryNames
	 */
	public ArrayList getSourceCategoryNames() {
		return sourceCategoryNames;
	}

	/**
	 * @param sourceCategoryNames the sourceCategoryNames to set
	 */
	public void setSourceCategoryNames(ArrayList sourceCategoryNames) {
		this.sourceCategoryNames = sourceCategoryNames;
	}

	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return Returns the userIds.
	 */
	public ArrayList getUserIds() {
		return userIds;
	}

	/**
	 * @param userIds
	 *            The userIds to set.
	 */
	public void setUserIds(ArrayList userIds) {
		this.userIds = userIds;
	}

	/**
	 * @return Returns the userNames.
	 */
	public ArrayList getUserNames() {
		return userNames;
	}

	/**
	 * @return Returns the applicantIds.
	 */
	public ArrayList getApplicantIds() {
		return applicantIds;
	}

	/**
	 * @param applicantIds
	 *            The applicantIds to set.
	 */
	public void setApplicantIds(ArrayList applicantIds) {
		this.applicantIds = applicantIds;
	}

	/**
	 * @return Returns the applicantNames.
	 */
	public ArrayList getApplicantNames() {
		return applicantNames;
	}

	/**
	 * @param applicantNames
	 *            The applicantNames to set.
	 */
	public void setApplicantNames(ArrayList applicantNames) {
		this.applicantNames = applicantNames;
	}

	/**
	 * @param userNames
	 *            The userNames to set.
	 */
	public void setUserNames(ArrayList userNames) {
		this.userNames = userNames;
	}

	/**
	 * construct filter data from report form	 * 
	 * @return FilterData
	 */
	public FilterData getFilterData() {
		FilterData filterData = new FilterData();
		filterData.setFromDate(getFromDate());
		filterData.setToDate(getToDate());
		filterData.setReportName(getReportName());
		filterData.setFilterId(getFilterId());
		filterData.setPositionId(getPositionId());
		filterData.setPositionOwnerId(getSelectedPositionOwnerIds());
		filterData.setDepartmentId(getDepartmentId());
		filterData.setSubDepartmentId(getSubDepartmentId());
		filterData.setSubSubDepartmentId(getSubSubDepartmentId());
		filterData.setOrderBy(getOrderBy());
		filterData.setReportFormat(getReportFormat());
		filterData.setFromMonth(getFromMonth());
		filterData.setFromYear(getFromYear());
		filterData.setToMonth(getToMonth());
		filterData.setToYear(getToYear());
		filterData.setUserId(getUserId());
		filterData.setSourceId(getSourceId());
		filterData.setSourceCategoryId(getSourceCategoryId());
		filterData.setReportType(getReportType());
		filterData.setInterviewers(getInterviewers());
		filterData.setStages(getStages());
		filterData.setMaxExp(getMaxExp());
		filterData.setMinExp(getMinExp());
		filterData.setDegreeIds(getDegreeIds());
		filterData.setApplicants(getApplicants());
		filterData.setStepIds(getStepIds());
		filterData.setStepTitles(getStepTitles());
		filterData.setUsers(getSelectedUserIds());
		filterData.setRecruitmentCostReportType(getRecruitmentCostReportType());
		filterData.setActionId(getActionId());
		filterData.setDateRange(getDateRange());
		filterData.setNumberRange(getNumberRange());
		filterData.setActivities(getActivities());
		filterData.setJoinedCandidatesReportFields(getJoinedCandidatesReportFields());
		filterData.setSelectedExpenseTypes(getSelectedExpenseTypes());
		filterData.setSelectedActivityTypes(getSelectedActivityTypes());
		filterData.setAppointmentDateRange(getAppointmentDateRange());
		filterData.setFromAppointmentDate(getFromAppointmentDate());
		filterData.setToAppointmentDate(getToAppointmentDate());
		filterData.setAppointmentNumberRange(getAppointmentNumberRange());
		filterData.setSelectedImportedByUsers(getSelectedImportedByUsers());
		filterData.setSelectedActivityByUsers(getSelectedActivityByUsers());
		filterData.setEntityTypeId(getEntityTypeId());
		filterData.setBudgetBandId(getBudgetBandId());
		filterData.setBudgetGradeId(getBudgetGradeId());
		filterData.setBudgetStatus(getBudgetStatus());
		filterData.setFieldIds(getFieldIds());		
		filterData.setSourceFilter(getSourceFilter());
		filterData.setSourceCategoryFilter(getSourceFilter());
		filterData.setPositionFilter(getPositionFilter());
		filterData.setDepartmentFilter(getDepartmentFilter());
		filterData.setGroupBy(getGroupBy());
		filterData.setEntityType(getEntityType());
		filterData.setAuditType(getAuditType());
		return filterData;		
	}

	/**
	 * @return Returns the departmentId.
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            The departmentId to set.
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return Returns the filterId.
	 */
	public String getFilterId() {
		return filterId;
	}

	/**
	 * @param filterId
	 *            The filterId to set.
	 */
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}

	/**
	 * @return Returns the orderBy.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * @param orderBy
	 *            The orderBy to set.
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * @return Returns the positionId.
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId
	 *            The positionId to set.
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return Returns the reportTitle.
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportTitle
	 *            The reportTitle to set.
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return Returns the fromDate.
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            The fromDate to set.
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return Returns the toDate. return today's date by default, if empty
	 */
	public String getToDate() {
		if (Utils.isBlankOrNull(toDate)) {
			return Utils.getDateConvertedToString(new Date(), "dd/MM/yyyy");
		}
		return toDate;
	}

	/**
	 * @param toDate
	 *            The toDate to set.
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return Returns the stepTypeNames.
	 */
	public ArrayList getDepartmentIds() {
		return CommonUtils.getDeptIds();
	}

	public ArrayList getDepartmentNames() {
		return CommonUtils.getDeptNames();
	}

	/**
	 * @return Returns the reportFormat.
	 */
	public String getReportFormat() {
		return reportFormat;
	}

	/**
	 * @param reportFormat
	 *            The reportFormat to set.
	 */
	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	/**
	 * @return Returns the departmentTitle.
	 */
	public String getDepartmentTitle() {
		return departmentTitle;
	}

	/**
	 * @param departmentTitle
	 *            The departmentTitle to set.
	 */
	public void setDepartmentTitle(String departmentTitle) {
		this.departmentTitle = departmentTitle;
	}

	/**
	 * @return Returns the positionTitle.
	 */
	public String getPositionTitle() {
		return positionTitle;
	}

	/**
	 * @param positionTitle
	 *            The positionTitle to set.
	 */
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	/**
	 * @return Returns the fromMonth.
	 */
	public String getFromMonth() {
		return fromMonth;
	}

	/**
	 * @param fromMonth
	 *            The fromMonth to set.
	 */
	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	/**
	 * @return Returns the fromYear.
	 */
	public String getFromYear() {
		return fromYear;
	}

	/**
	 * @param fromYear
	 *            The fromYear to set.
	 */
	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	/**
	 * @return Returns the toMonth.
	 */
	public String getToMonth() {
		return toMonth;
	}

	/**
	 * @param toMonth
	 *            The toMonth to set.
	 */
	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	/**
	 * @return Returns the toYear.
	 */
	public String getToYear() {
		return toYear;
	}

	/**
	 * @param toYear
	 *            The toYear to set.
	 */
	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public void setUsersList(String roles) {
		try {
			PositionManager positionManager = new PositionManager();
			ArrayList<LoginData> userList = positionManager.getUsersForRole(roles);
			ArrayList<String> userIds = new ArrayList<String>();
			ArrayList<String> userNames = new ArrayList<String>();
			CommonUtils.populateIdsAndNames(userList, userIds, userNames, "userId", "name", null);
			setUserIds(userIds);
			setUserNames(userNames);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while setting users list", e);
		}
	}

	/**
	 * @return Returns the applicantId.
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * @return Returns the applicantName.
	 */
	public String getApplicantName() {
		return applicantName;
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	/**
	 * @return the interviewerList.
	 */
	public List<LoginData> getInterviewerList() {
		return interviewerList;
	}

	/**
	 * @param interviewerList 
	 *			the interviewerList to set.
	 */
	public void setInterviewerList(List<LoginData> interviewerList) {
		this.interviewerList = interviewerList;
	}

	/**
	 * @return the stages.
	 */
	public String getStages() {
		return stages;
	}

	/**
	 * @param stages 
	 *			the stages to set.
	 */
	public void setStages(String stages) {
		this.stages = stages;
	}

	/**
	 * @return the maxExp
	 */
	public String getMaxExp() {
		return maxExp;
	}

	/**
	 * @param maxExp the maxExp to set
	 */
	public void setMaxExp(String maxExp) {
		this.maxExp = maxExp;
	}

	/**
	 * @return the minExp
	 */
	public String getMinExp() {
		return minExp;
	}

	/**
	 * @param minExp the minExp to set
	 */
	public void setMinExp(String minExp) {
		this.minExp = minExp;
	}

	/**
	 * @return the degreeIds
	 */
	public String getDegreeIds() {
		return degreeIds;
	}

	/**
	 * @param degreeIds the degreeIds to set
	 */
	public void setDegreeIds(String degreeIds) {
		this.degreeIds = degreeIds;
	}

	/**
	 * @return the applicants
	 */
	public String getApplicants() {
		return applicants;
	}

	/**
	 * @param applicants the applicants to set
	 */
	public void setApplicants(String applicants) {
		this.applicants = applicants;
	}

	/**
	 * @return the stepIds
	 */
	public String getStepIds() {
		return stepIds;
	}

	/**
	 * @param stepIds the stepIds to set
	 */
	public void setStepIds(String stepIds) {
		this.stepIds = stepIds;
	}

	/**
	 * @return the recruitmentCostReportType
	 */
	public String getRecruitmentCostReportType() {
		return recruitmentCostReportType;
	}

	/**
	 * @param recruitmentCostReportType 
	 * 			the recruitmentCostReportType to set
	 */
	public void setRecruitmentCostReportType(String recruitmentCostReportType) {
		this.recruitmentCostReportType = recruitmentCostReportType;
	}

	/**
	 * @return the actionId
	 */
	public String getActionId() {
		return actionId;
	}

	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	/**
	 * @return the dateRange
	 */
	public String getDateRange() {
		return dateRange;
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	/**
	 * @return the userNameTo
	 */
	public String getUserNameTo() {
		return userNameTo;
	}

	/**
	 * @param userNameTo the userNameTo to set
	 */
	public void setUserNameTo(String userNameTo) {
		this.userNameTo = userNameTo;
	}

	/**
	 * @return the dayOfMonth
	 */
	public String getDayOfMonth() {
		return dayOfMonth;
	}

	/**
	 * @param dayOfMonth the dayOfMonth to set
	 */
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	/**
	 * @return the dayOfWeek
	 */
	public String getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * @return the everyDay
	 */
	public String getEveryDay() {
		return everyDay;
	}

	/**
	 * @param everyDay the everyDay to set
	 */
	public void setEveryDay(String everyDay) {
		this.everyDay = everyDay;
	}

	/**
	 * @return the frequencyOfScheduler
	 */
	public String getFrequencyOfScheduler() {
		return frequencyOfScheduler;
	}

	/**
	 * @param frequencyOfScheduler the frequencyOfScheduler to set
	 */
	public void setFrequencyOfScheduler(String frequencyOfScheduler) {
		this.frequencyOfScheduler = frequencyOfScheduler;
	}

	/**
	 * @return the scheduleTime
	 */
	public String getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the weekDay
	 */
	public String getWeekDay() {
		return weekDay;
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	/**
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the mailBody
	 */
	public String getMailBody() {
		return mailBody;
	}

	/**
	 * @param mailBody the mailBody to set
	 */
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	/**
	 * @return the isConflict
	 */
	public String getIsConflict() {
		return isConflict;
	}

	/**
	 * @param isConflict the isConflict to set
	 */
	public void setIsConflict(String isConflict) {
		this.isConflict = isConflict;
	}

	/**
	 * @return the numberRange
	 */
	public String getNumberRange() {
		return numberRange;
	}

	/**
	 * @param numberRange the numberRange to set
	 */
	public void setNumberRange(String numberRange) {
		this.numberRange = numberRange;
	}

	/**
	 * @return the subDepartmentId
	 */
	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	/**
	 * @param subDepartmentId the subDepartmentId to set
	 */
	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	/**
	 * @return the subSubDepartmentId
	 */
	public String getSubSubDepartmentId() {
		return subSubDepartmentId;
	}

	/**
	 * @param subSubDepartmentId the subSubDepartmentId to set
	 */
	public void setSubSubDepartmentId(String subSubDepartmentId) {
		this.subSubDepartmentId = subSubDepartmentId;
	}

	/**
	 * @return the activities
	 */
	public String getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(String activities) {
		this.activities = activities;
	}

	public String getIsReportFormatCSV() {
		return isReportFormatCSV;
	}

	public void setIsReportFormatCSV(String isReportFormatCSV) {
		this.isReportFormatCSV = isReportFormatCSV;
	}

	/**
	 * @return the groupBy
	 */
	public String getGroupBy() {
		return groupBy;
	}

	/**
	 * @param groupBy the groupBy to set
	 */
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	/**
	 * @return the columns
	 */
	public String getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}

	/**
	 * @return the filters
	 */
	public String getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(String filters) {
		this.filters = filters;
	}
	
	public String getFieldIds() {
		return fieldIds;
	}
	public void setFieldIds(String fieldIds) {
		this.fieldIds = fieldIds;
	}

	public String getSelectedExpenseTypes() {
		return selectedExpenseTypes;
	}

	public void setSelectedExpenseTypes(String selectedExpenseTypes) {
		this.selectedExpenseTypes = selectedExpenseTypes;
	}

	public String getSelectedActivityTypes() {
		return selectedActivityTypes;
	}

	public void setSelectedActivityTypes(String selectedActivityTypes) {
		this.selectedActivityTypes = selectedActivityTypes;
	}

	public String getAppointmentDateRange() {
		return appointmentDateRange;
	}

	public void setAppointmentDateRange(String appointmentDateRange) {
		this.appointmentDateRange = appointmentDateRange;
	}

	public String getFromAppointmentDate() {
		return fromAppointmentDate;
	}

	public void setFromAppointmentDate(String fromAppointmentDate) {
		this.fromAppointmentDate = fromAppointmentDate;
	}

	public String getToAppointmentDate() {
		return toAppointmentDate;
	}

	public void setToAppointmentDate(String toAppointmentDate) {
		this.toAppointmentDate = toAppointmentDate;
	}

	public String getAppointmentNumberRange() {
		return appointmentNumberRange;
	}

	public void setAppointmentNumberRange(String appointmentNumberRange) {
		this.appointmentNumberRange = appointmentNumberRange;
	}

	public String getSelectedImportedByUsers() {
		return selectedImportedByUsers;
	}

	public void setSelectedImportedByUsers(String selectedImportedByUsers) {
		this.selectedImportedByUsers = selectedImportedByUsers;
	}

	public String getSubDepartmentTitle() {
		return subDepartmentTitle;
	}

	public void setSubDepartmentTitle(String subDepartmentTitle) {
		this.subDepartmentTitle = subDepartmentTitle;
	}

	public String getSubSubDepartmentTitle() {
		return subSubDepartmentTitle;
	}

	public void setSubSubDepartmentTitle(String subSubDepartmentTitle) {
		this.subSubDepartmentTitle = subSubDepartmentTitle;
	}

	public String getSelectedExpenseTypeNames() {
		return selectedExpenseTypeNames;
	}

	public void setSelectedExpenseTypeNames(String selectedExpenseTypeNames) {
		this.selectedExpenseTypeNames = selectedExpenseTypeNames;
	}

	public String getSelectedActivityTypeNames() {
		return selectedActivityTypeNames;
	}

	public void setSelectedActivityTypeNames(String selectedActivityTypeNames) {
		this.selectedActivityTypeNames = selectedActivityTypeNames;
	}

	public String getSelectedImportedByUserNames() {
		return selectedImportedByUserNames;
	}

	public void setSelectedImportedByUserNames(String selectedImportedByUserNames) {
		this.selectedImportedByUserNames = selectedImportedByUserNames;
	}

	public String getSelectedUserIds() {
		return selectedUserIds;
	}

	public void setSelectedUserIds(String selectedUserIds) {
		this.selectedUserIds = selectedUserIds;
	}

	public String getSelectedUserNames() {
		return selectedUserNames;
	}

	public void setSelectedUserNames(String selectedUserNames) {
		this.selectedUserNames = selectedUserNames;
	}

	public String getStageNames() {
		return stageNames;
	}

	public void setStageNames(String stageNames) {
		this.stageNames = stageNames;
	}

	public String getSelectedActivityByUsers() {
		return selectedActivityByUsers;
	}

	public void setSelectedActivityByUsers(String selectedActivityByUsers) {
		this.selectedActivityByUsers = selectedActivityByUsers;
	}

	public String getSelectedActivityByUserNames() {
		return selectedActivityByUserNames;
	}

	public void setSelectedActivityByUserNames(String selectedActivityByUserNames) {
		this.selectedActivityByUserNames = selectedActivityByUserNames;
	}

	public String getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(String entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	public String getEntityTypeTitle() {
		return entityTypeTitle;
	}

	public void setEntityTypeTitle(String entityTypeTitle) {
		this.entityTypeTitle = entityTypeTitle;
	}

	public String getBudgetGradeId() {
		return budgetGradeId;
	}

	public void setBudgetGradeId(String budgetGradeId) {
		this.budgetGradeId = budgetGradeId;
	}

	public String getBudgetBandId() {
		return budgetBandId;
	}

	public void setBudgetBandId(String budgetBandId) {
		this.budgetBandId = budgetBandId;
	}

	public String getBudgetStatus() {
		return budgetStatus;
	}

	public void setBudgetStatus(String budgetStatus) {
		this.budgetStatus = budgetStatus;
	}

	public String getBudgetGradeName() {
		return budgetGradeName;
	}

	public void setBudgetGradeName(String budgetGradeName) {
		this.budgetGradeName = budgetGradeName;
	}

	public String getBudgetBandName() {
		return budgetBandName;
	}

	public void setBudgetBandName(String budgetBandName) {
		this.budgetBandName = budgetBandName;
	}

	public String getBudgetStatusTitle() {
		return budgetStatusTitle;
	}

	public void setBudgetStatusTitle(String budgetStatusTitle) {
		this.budgetStatusTitle = budgetStatusTitle;
	}

	/**
	 * @return the reportTemplateId
	 */
	public String getReportTemplateId() {
		return reportTemplateId;
	}

	/**
	 * @param reportTemplateId the reportTemplateId to set
	 */
	public void setReportTemplateId(String reportTemplateId) {
		this.reportTemplateId = reportTemplateId;
	}

	/**
	 * @return the stepTitles
	 */
	public String getStepTitles() {
		return stepTitles;
	}

	/**
	 * @param stepTitles the stepTitles to set
	 */
	public void setStepTitles(String stepTitles) {
		this.stepTitles = stepTitles;
	}

	/**
	 * @return the selectedPositionOwnerIds
	 */
	public String getSelectedPositionOwnerIds() {
		return selectedPositionOwnerIds;
	}

	/**
	 * @param selectedPositionOwnerIds the selectedPositionOwnerIds to set
	 */
	public void setSelectedPositionOwnerIds(String selectedPositionOwnerIds) {
		this.selectedPositionOwnerIds = selectedPositionOwnerIds;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public ArrayList getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(ArrayList roleIds) {
		this.roleIds = roleIds;
	}

	public ArrayList getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(ArrayList roleNames) {
		this.roleNames = roleNames;
	}

	public String getRoleFilter() {
		return roleFilter;
	}

	public void setRoleFilter(String roleFilter) {
		this.roleFilter = roleFilter;
	}

	/**
	 * @return the userFilter
	 */
	public String getUserFilter() {
		return userFilter;
	}

	/**
	 * @param userFilter the userFilter to set
	 */
	public void setUserFilter(String userFilter) {
		this.userFilter = userFilter;
	}

	/**
	 * @return the positionIds
	 */
	public ArrayList getPositionIds() {
		return positionIds;
	}

	/**
	 * @param positionIds the positionIds to set
	 */
	public void setPositionIds(ArrayList positionIds) {
		this.positionIds = positionIds;
	}

	/**
	 * @return the positionTitles
	 */
	public ArrayList getPositionTitles() {
		return positionTitles;
	}

	/**
	 * @param positionTitles the positionTitles to set
	 */
	public void setPositionTitles(ArrayList positionTitles) {
		this.positionTitles = positionTitles;
	}

	/**
	 * @return the departmentTitles
	 */
	public ArrayList getDepartmentTitles() {
		return departmentTitles;
	}

	/**
	 * @param departmentTitles the departmentTitles to set
	 */
	public void setDepartmentTitles(ArrayList departmentTitles) {
		this.departmentTitles = departmentTitles;
	}

	/**
	 * @param departmentIds the departmentIds to set
	 */
	public void setDepartmentIds(ArrayList departmentIds) {
		this.departmentIds = departmentIds;
	}	
	
}