package com.talentPool.reports.utils;

import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.reports.ReportVersionConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.dataobject.ReportScheduleData;

import fr.opensagres.xdocreport.core.utils.StringUtils;

public class FilterConverter {

	public static StringBuilder getConvertedCriteriaFilter(FilterData filterData, String reportName) {
		StringBuilder criteria = new StringBuilder("\"criterias\" : [");
		if (filterData.getFromDate() != null && filterData.getToDate() != null) {

			String fromDate = DateUtils.getDateConversionFromOneStringFormatToAnother(DateConstants.INPUT_FORMAT,
					DateConstants.yyyyMMdd, filterData.getFromDate());
			String toDate = DateUtils.getDateConversionFromOneStringFormatToAnother(DateConstants.INPUT_FORMAT,
					DateConstants.yyyyMMdd, filterData.getToDate());

			switch (reportName) {
			case ReportVersionConstants.COST_SHEET_FINANCE_REPORT:
				criteria.append(
						"{\"@type\":\"joinerDateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;
			case ReportVersionConstants.JOINER_DATA_FINANCE_REPORT:
				criteria.append(
						"{\"@type\":\"joinerDateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;

			case ReportVersionConstants.INDIA_HIRING_REQ_REPORT:
				criteria.append(
						"{\"@type\":\"offerDateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;

			case ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT:
				criteria.append("{\"@type\":\"dateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;
			}

		}
		if (StringUtils.isNotEmpty(filterData.getDepartmentId())) {
			criteria.append(
					", {\"@type\":\"departmentCriteria\",\"departments\":[" + filterData.getDepartmentId() + "]}");
		}
		if (StringUtils.isNotEmpty(filterData.getPositionId())) {
			criteria.append(", {\"@type\":\"positionCriteria\",\"positions\":[" + filterData.getPositionId() + "]}");
		}
		if (StringUtils.isNotEmpty(filterData.getUsers())) {
			criteria.append(", {\"@type\":\"userCriteria\",\"users\":[" + filterData.getUsers() + "]}");
		}
		return criteria.append("]}");
	}
	
	
	public static StringBuilder getConvertedCriteriaFilterForScedulableReports(ReportScheduleData reportScheduleData, String reportName) {
		StringBuilder criteria = new StringBuilder("\"criterias\" : [");
		if (reportScheduleData.getFromDate() != null && reportScheduleData.getToDate() != null) {

			String fromDate = DateUtils.getDateConversionFromOneStringFormatToAnother(DateConstants.INPUT_FORMAT,
					DateConstants.yyyyMMdd, reportScheduleData.getFromDate());
			String toDate = DateUtils.getDateConversionFromOneStringFormatToAnother(DateConstants.INPUT_FORMAT,
					DateConstants.yyyyMMdd, reportScheduleData.getToDate());

			switch (reportName) {
			case ReportVersionConstants.COST_SHEET_FINANCE_REPORT:
				criteria.append(
						"{\"@type\":\"joinerDateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;
			case ReportVersionConstants.JOINER_DATA_FINANCE_REPORT:
				criteria.append(
						"{\"@type\":\"joinerDateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;

			case ReportVersionConstants.INDIA_HIRING_REQ_REPORT:
				criteria.append(
						"{\"@type\":\"offerDateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;

			case ReportVersionConstants.INDIA_HIRING_SUMMARY_REPORT:
				criteria.append("{\"@type\":\"dateCriteria\",\"from\":\"" + fromDate + "\",\"to\":\"" + toDate + "\"}");
				break;
			}

		}
		if (StringUtils.isNotEmpty(reportScheduleData.getDepartmentId())) {
			criteria.append(
					", {\"@type\":\"departmentCriteria\",\"departments\":[" + reportScheduleData.getDepartmentId() + "]}");
		}
		if (StringUtils.isNotEmpty(reportScheduleData.getPositionId())) {
			criteria.append(", {\"@type\":\"positionCriteria\",\"positions\":[" + reportScheduleData.getPositionId() + "]}");
		}
		if (StringUtils.isNotEmpty(reportScheduleData.getUserId())) {
			criteria.append(", {\"@type\":\"userCriteria\",\"users\":[" + reportScheduleData.getUserId() + "]}");
		}
		return criteria.append("]}");
	}
}
