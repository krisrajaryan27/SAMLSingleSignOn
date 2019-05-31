/**
 * 
 */
package com.talentPool.reports.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author Shantanu
 * 
 */
public class ReportScheduleData extends SimpleDataObject {
	

	static final long serialVersionUID = 1L;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {		
		return getString("userId");
		
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		setAttribute("userId",userId);
	}

		
	/**
	 * @return the actionId
	 */
	public String getActionId() {		
		return getString("actionId");
		
	}

	/**
	 * @param actionId the actionId to set
	 */
	public void setActionId(String actionId) {
		setAttribute("actionId",actionId);
	}

	/**
	 * @return the applicants
	 */
	public String getApplicants() {		
		return getString("applicants");
	}

	/**
	 * @param applicants the applicants to set
	 */
	public void setApplicants(String applicants) {
		setAttribute("applicants",applicants);
	}

	/**
	 * @return the costReportType
	 */
	public String getCostReportType() {		
		return getString("costReportType");
	}

	/**
	 * @param costReportType the costReportType to set
	 */
	public void setCostReportType(String costReportType) {
		setAttribute("costReportType",costReportType);
	}

	/**
	 * @return the dateRange
	 */
	public String getDateRange() {
		return getString("dateRange");
	}

	/**
	 * @param dateRange the dateRange to set
	 */
	public void setDateRange(String dateRange) {
		setAttribute("dateRange",dateRange);
	}

	/**
	 * @return the degreeIds
	 */
	public String getDegreeIds() {
		return getString("degreeIds");
	}

	/**
	 * @param degreeIds the degreeIds to set
	 */
	public void setDegreeIds(String degreeIds) {
		setAttribute("degreeIds",degreeIds);
	}

	/**
	 * @return the filterId
	 */
	public String getFilterId() {
		return getString("filterId");
	}

	/**
	 * @param filterId the filterId to set
	 */
	public void setFilterId(String filterId) {
		setAttribute("filterId",filterId);
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return getString("fromDate");
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		setAttribute("fromDate",fromDate);
	}

	/**
	 * @return the fromMonth
	 */
	public String getFromMonth() {
		return getString("fromMonth");
	}

	/**
	 * @param fromMonth the fromMonth to set
	 */
	public void setFromMonth(String fromMonth) {
		setAttribute("fromMonth",fromMonth);
	}

	/**
	 * @return the fromYear
	 */
	public String getFromYear() {
		return getString("fromYear");
	}

	/**
	 * @param fromYear the fromYear to set
	 */
	public void setFromYear(String fromYear) {
		setAttribute("fromYear",fromYear);
	}

	/**
	 * @return the interviewers
	 */
	public String getInterviewers() {
		return getString("interviewers");
	}

	/**
	 * @param interviewers the interviewers to set
	 */
	public void setInterviewers(String interviewers) {
		setAttribute("interviewers",interviewers);
	}

	/**
	 * @return the maxExp
	 */
	public String getMaxExp() {
		return getString("maxExp");
	}

	/**
	 * @param maxExp the maxExp to set
	 */
	public void setMaxExp(String maxExp) {
		setAttribute("maxExp",maxExp);
	}

	/**
	 * @return the minExp
	 */
	public String getMinExp() {
		return getString("minExp");
	}

	/**
	 * @param minExp the minExp to set
	 */
	public void setMinExp(String minExp) {
		setAttribute("minExp",minExp);
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return getString("orderBy");
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		setAttribute("orderBy",orderBy);
	}

	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return getString("positionId");
	}

	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		setAttribute("positionId",positionId);
	}

	/**
	 * @return the reportFormat
	 */
	public String getReportFormat() {
		return getString("reportFormat");
	}

	/**
	 * @param reportFormat the reportFormat to set
	 */
	public void setReportFormat(String reportFormat) {
		setAttribute("reportFormat",reportFormat);
	}

	/**
	 * @return the reportType
	 */
	public String getReportType() {
		return getString("reportType");
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(String reportType) {
		setAttribute("reportType",reportType);
	}

	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return getString("sourceId");
	}

	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		setAttribute("sourceId",sourceId);
	}

	/**
	 * @return the sourceCategoryId
	 */
	public String getSourceCategoryId() {
		return getString("sourceCategoryId");
	}

	/**
	 * @param sourceCategoryId the sourceCategoryId to set
	 */
	public void setSourceCategoryId(String sourceCategoryId) {
		setAttribute("sourceCategoryId",sourceCategoryId);
	}

	/**
	 * @return the stages
	 */
	public String getStages() {
		return getString("stages");
	}

	/**
	 * @param stages the stages to set
	 */
	public void setStages(String stages) {
		setAttribute("stages",stages);
	}

	/**
	 * @return the stepIds
	 */
	public String getStepIds() {
		return getString("stepIds");
	}

	/**
	 * @param stepIds the stepIds to set
	 */
	public void setStepIds(String stepIds) {
		setAttribute("stepIds",stepIds);
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return getString("toDate");
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		setAttribute("toDate",toDate);
	}

	/**
	 * @return the toMonth
	 */
	public String getToMonth() {
		return getString("toMonth");
	}

	/**
	 * @param toMonth the toMonth to set
	 */
	public void setToMonth(String toMonth) {
		setAttribute("toMonth",toMonth);
	}

	/**
	 * @return the toYear
	 */
	public String getToYear() {
		return getString("toYear");
	}

	/**
	 * @param toYear the toYear to set
	 */
	public void setToYear(String toYear) {
		setAttribute("toYear",toYear);
	}

	/**
	 * @return the users
	 */
	public String getUsers() {
		return getString("users");
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(String users) {
		setAttribute("users",users);
	}

	/**
	 * @return the dayOfMonth
	 */
	public String getDayOfMonth() {		
		return getString("dayOfMonth");
	}

	/**
	 * @param dayOfMonth the dayOfMonth to set
	 */
	public void setDayOfMonth(String dayOfMonth) {
		setAttribute("dayOfMonth",dayOfMonth);
	}

	/**
	 * @return the dayOfWeek
	 */
	public String getDayOfWeek() {		
		return getString("dayOfWeek");
	}

	/**
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(String dayOfWeek) {		
		setAttribute("dayOfWeek",dayOfWeek);
	}


	/**
	 * @return the emailIds
	 */
	public String getEmailIds() {		
		return getString("emailIds");
	}

	/**
	 * @param emailIds the emailIds to set
	 */
	public void setEmailIds(String emailIds) {		
		setAttribute("emailIds",emailIds);
	}

	/**
	 * @return the everyday
	 */
	public String getEveryday() {		
		return getString("everyday");
	}

	/**
	 * @param everyday the everyday to set
	 */
	public void setEveryday(String everyday) {		
		setAttribute("everyday",everyday);
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {		
		return getString("frequency");
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {		
		setAttribute("frequency",frequency);
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {		
		return getString("reportId");
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		setAttribute("reportId",reportId);		
	}

	/**
	 * @return the scheduleId
	 */
	public int getScheduleId() {		
		return getInt("scheduleId");
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(int scheduleId) {		
		setAttribute("scheduleId",scheduleId);
	}

	/**
	 * @return the scheduleStatus
	 */
	public String getScheduleStatus() {		
		return getString("scheduleStatus");
	}

	/**
	 * @param scheduleStatus the scheduleStatus to set
	 */
	public void setScheduleStatus(String scheduleStatus) {
		setAttribute("scheduleStatus",scheduleStatus);
	}

	/**
	 * @return the scheduleTime
	 */
	public java.sql.Timestamp getScheduleTime() {		
		return getTimestamp("scheduleTime");
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(java.sql.Timestamp scheduleTime) {		
		setAttribute("scheduleTime",scheduleTime);
	}


	/**
	 * @return the startDate
	 */
	public java.sql.Date getStartDate() {		
		return getDate("startDate");
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(java.sql.Date startDate) {
		setAttribute("startDate",startDate);
	}

	
	public String getStringStartDate() {		
		return getString("startStringDate");
	}

	/**
	 * @param date the startDate to set
	 */
	public void setStringStartDate(String date) {
		setAttribute("startStringDate",date);
	}
	
	/**
	 * @return the weekdays
	 */
	public String getWeekdays() {		
		return getString("weekdays");
	}

	/**
	 * @param weekdays the weekdays to set
	 */
	public void setWeekdays(String weekdays) {		
		setAttribute("weekdays",weekdays);
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {		
		return getString("departmentId");
	}

	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {		
		setAttribute("departmentId",departmentId);
	}
	
	/**
	 * @return the subDepartmentId
	 */
	public String getSubDepartmentId() {		
		return getString("subDepartmentId");
	}

	/**
	 * @param subDepartmentId the departmentId to set
	 */
	public void setSubDepartmentId(String subDepartmentId) {		
		setAttribute("subDepartmentId",subDepartmentId);
	}
	
	/**
	 * @return the subSubDepartmentId
	 */
	public String getSubSubDepartmentId() {		
		return getString("subSubDepartmentId");
	}

	/**
	 * @param subDepartmentId the departmentId to set
	 */
	public void setSubSubDepartmentId(String subSubDepartmentId) {		
		setAttribute("subSubDepartmentId",subSubDepartmentId);
	}
	/**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return getString("mailSubject");
	}

	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		setAttribute("mailSubject",mailSubject);
	}
	/**
	 * @return the mailBody
	 */
	public String getMailBody() {
		return getString("mailBody");
	}

	/**
	 * @param mailBody the mailBody to set
	 */
	public void setMailBody(String mailBody) {
		setAttribute("mailBody",mailBody);
	}
	/**
	 * @return the numberRange
	 */
	public String getNumberRange() {		
		return getString("numberRange");
		
	}

	/**
	 * @param numberRange the numberRange to set
	 */
	public void setNumberRange(String numberRange) {
		setAttribute("numberRange",numberRange);
	}

	/**
	 * @return the activities
	 */
	public String getActivities() {		
		return getString("activities");
		
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivites(String activities) {
		setAttribute("activities",activities);
	}
	
	/**
	 * @return the positionFilter
	 */
	public String getPositionFilter() {		
		return getString("positionFilter");
		
	}

	/**
	 * @param positionFilter the positionFilter to set
	 */
	public void setPositionFilter(String positionFilter) {
		setAttribute("positionFilter", positionFilter);
	}

	/**
	 * @return the departmentFilter
	 */
	public String getDepartmentFilter() {		
		return getString("departmentFilter");
		
	}

	/**
	 * @param departmentFilter the departmentFilter to set
	 */
	public void setDepartmentFilter(String departmentFilter) {
		setAttribute("departmentFilter", departmentFilter);
	}
	
	public String getTemplateId() {		
		return getString("templateId");
		
	}

	public void setTemplateId(String templateId) {
		setAttribute("templateId", templateId);
	}
}
