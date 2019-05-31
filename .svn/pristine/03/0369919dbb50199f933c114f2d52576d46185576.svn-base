package com.talentPool.customReports.queryBuilder;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CustomReportConstants;
import com.talentPool.customReports.jaxb.Filter;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.customReports.utils.CustomReportUtils;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.reports.ReportConstants;
import com.talentPool.user.manager.PermissionSet;

public class QueryBuilder {
	
	public String[] getWhereClause(Filters filters, String userId, PermissionSet permissionSet) {
		String[] dynamicParams =  null;
		dynamicParams = new String[1];
		dynamicParams[0] = buildWhereClause(filters, userId, permissionSet);
		
		return dynamicParams;
	}
	
	public String[] getWhereClauseForInactivePositions(Filters filters, String userId, PermissionSet permissionSet) {
		String[] dynamicParams =  null;
		dynamicParams = new String[2];
		dynamicParams[0] = buildWhereClause(filters, userId, permissionSet);
		dynamicParams[1] = buildWhereClauseForPositionMaster(filters, userId);
		
		return dynamicParams;
	}
		
	public String buildWhereClauseForPositionMaster(Filters filters, String userId) {
		StringBuilder sb = new StringBuilder();
		if(!Utils.isListEmptyOrNull(filters.getFilter())){
			for (Filter filter : filters.getFilter()) {
				if(CustomReportConstants.FILTER_POSITION_STATUS.equals(filter.getFilterId())){
					buildClauseForPositionStatusFilter(filter.getValue(), sb);
				}else if(CustomReportConstants.FILTER_POSITION_OWNER.equals(filter.getFilterId())){
					buildClauseForPositionOwnerFilter(filter.getValue(), sb);
				}else if(CustomReportConstants.FILTER_DEPARTMENT.equals(filter.getFilterId())){
					buildClauseForPositionDepartmentFilter(filter.getValue(), sb);
				}else if(CustomReportConstants.FILTER_POSITION.equals(filter.getFilterId())){
					buildClauseForPositionFilter(filter.getValue(), sb);
				}else if(CustomReportConstants.FILTER_STAGE.equals(filter.getFilterId())){
					if(!Utils.isBlankOrNull(filter.getValue())) {
						sb.append(" AND tsm.step_level IN (" + filter.getValue() +")");
					}
				}else if(CustomReportConstants.FILTER_STEP.equals(filter.getFilterId())){
					if(!Utils.isBlankOrNull(filter.getValue())) {
						sb.append(" AND tsm.step_id IN (" + filter.getValue() +")");
					}
				}
			}
		}
		return sb.toString();
	}
	
	public String buildWhereClause(Filters filters, String userId, PermissionSet permissionSet) {
		StringBuilder sb = new StringBuilder();
		if(!Utils.isListEmptyOrNull(filters.getFilter())){
			for (Filter filter : filters.getFilter()) {
				buildWhereClause(filter, sb);
			}
		}
		//sb.append(buildPermissionClause(userId, permissionSet));
		return sb.toString();
	}
	
	private StringBuilder buildWhereClause(Filter filter, StringBuilder sb) {
		if(CustomReportConstants.FILTER_AS_OF_DATE.equals(filter.getFilterId())){
			buildClauseForAsOfDateFilter(filter.getValue(), filter.getValue1(), sb);
		}else if(CustomReportConstants.FILTER_DATE.equals(filter.getFilterId())){
			buildClauseForDateFilter(filter.getValue(), filter.getValue1(), filter.getValue2(), sb);
		}else if(CustomReportConstants.FILTER_POSITION_STATUS.equals(filter.getFilterId())){
			buildClauseForPositionStatusFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_POSITION_OWNER.equals(filter.getFilterId())){
			buildClauseForPositionOwnerFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_DEPARTMENT.equals(filter.getFilterId())){
			buildClauseForPositionDepartmentFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_POSITION.equals(filter.getFilterId())){
			buildClauseForPositionFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_USER_ROLE.equals(filter.getFilterId())){
			buildClauseForUserRoleFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_ACTIVITY_USER.equals(filter.getFilterId())){
			buildClauseForActivityUserFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_SOURCE_CATEGORY.equals(filter.getFilterId())){
			buildClauseForSourceCategoryFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_SOURCE.equals(filter.getFilterId())){
			buildClauseForSourceFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_STAGE.equals(filter.getFilterId())){
			buildClauseForStageFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_STEP.equals(filter.getFilterId())){
			buildClauseForStepFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_PROCESS_USER.equals(filter.getFilterId())){
			buildClauseForProcessUserFilter(filter.getValue(), sb);
		}else if(CustomReportConstants.FILTER_ACTIVITY_DATE.equals(filter.getFilterId())){
			buildClauseForActivityDateFilter(filter.getValue(), filter.getValue1(), filter.getValue2(), sb);
		}else if(CustomReportConstants.FILTER_OFFER_DATE.equals(filter.getFilterId())){
			buildClauseForDateFilterForColumn(filter.getValue(), filter.getValue1(), filter.getValue2(), sb, "trcm.offer_date");
		}else if(CustomReportConstants.FILTER_JOINING_DATE.equals(filter.getFilterId())){
			buildClauseForDateFilterForColumn(filter.getValue(), filter.getValue1(), filter.getValue2(), sb, "trcm.applicant_date_joined");
		}
		return sb; 
	}
	
	private StringBuilder buildClauseForAsOfDateFilter(String asOfDate, String toDate, StringBuilder sb) {
		if(!Utils.isBlankOrNull(asOfDate)) {
			String dateStr = "";
			if(!ReportConstants.CUSTOM.equals(asOfDate)){
				dateStr = CustomReportUtils.getReportsAsOfDateAsString(asOfDate);
			} else if(!Utils.isBlankOrNull(toDate)) {
				dateStr = (DateUtils.convertToSqlDate(toDate, DateConstants.INPUT_FORMAT)).toString();
			}
			
			sb.append(" AND tres.process_date <= '" + dateStr + "' ");	
		}
		return sb;
	}
	
	private StringBuilder buildClauseForDateFilter(String dateRange, String fromDate, String toDate, StringBuilder sb) {
		if(!Utils.isBlankOrNull(dateRange)) {
			try {
				String[] fromAndToDates = new String[2];
				if(!ReportConstants.CUSTOM.equals(dateRange)){
					fromAndToDates = CustomReportUtils.getFromAndToDateFromDateRangeAsString(dateRange);
				} else if(!Utils.isBlankOrNull(toDate)) {
					fromAndToDates[0] = (DateUtils.convertToSqlDate(fromDate, DateConstants.INPUT_FORMAT)).toString();
					fromAndToDates[1] = (Utils.datePlusPlus(DateUtils.convertToSqlDate(toDate, DateConstants.INPUT_FORMAT))).toString();
				}
				
				sb.append(" AND tres.process_date <= '" + fromAndToDates[1] + "' ");
				sb.append(" AND tres.process_date >= '" + fromAndToDates[0] + "' ");
				
			} catch (Exception e) {
				TPLogger.getLogger().error("Unable to parse date and apply filter. dateRange: "+ dateRange +", fromDate: "+ fromDate+", toDate: "+toDate, e);
			}
		}
		return sb;
	}
	
	/**
	 * @param dateRange
	 * @param fromDate
	 * @param toDate
	 * @param sb
	 * @param column
	 * @return where clause for date values based for the given column
	 */
	private StringBuilder buildClauseForDateFilterForColumn(String dateRange, String fromDate, String toDate, StringBuilder sb, String column) {
		if(!Utils.isBlankOrNull(dateRange)) {
			try {
				String[] fromAndToDates = new String[2];
				if(!ReportConstants.CUSTOM.equals(dateRange)){
					fromAndToDates = CustomReportUtils.getFromAndToDateFromDateRangeAsString(dateRange);
				} else if(!Utils.isBlankOrNull(toDate)) {
					fromAndToDates[0] = (DateUtils.convertToSqlDate(fromDate, DateConstants.INPUT_FORMAT)).toString();
					fromAndToDates[1] = (DateUtils.convertToSqlDate(toDate, DateConstants.INPUT_FORMAT)).toString();
				}
				
				sb.append(" AND " + column + " <= '" + fromAndToDates[1] + "' ");
				sb.append(" AND " + column + " >= '" + fromAndToDates[0] + "' ");
				
			} catch (Exception e) {
				TPLogger.getLogger().error("Unable to parse date and apply filter. dateRange: "+ dateRange +", fromDate: "+ fromDate+", toDate: "+toDate, e);
			}
		}
		return sb;
	}
	
	private StringBuilder buildClauseForActivityDateFilter(String dateRange, String fromDate, String toDate, StringBuilder sb) {
		if(!Utils.isBlankOrNull(dateRange)) {
			try {
				String[] fromAndToDates = new String[2];
				if(!ReportConstants.CUSTOM.equals(dateRange)){
					fromAndToDates = CustomReportUtils.getFromAndToDateFromDateRangeAsString(dateRange);
				} else if(!Utils.isBlankOrNull(toDate)) {
					fromAndToDates[0] = (DateUtils.convertToSqlDate(fromDate, DateConstants.INPUT_FORMAT)).toString();
					fromAndToDates[1] = (DateUtils.convertToSqlDate(toDate, DateConstants.INPUT_FORMAT)).toString();
				}
				
				sb.append(" AND tras.activity_date <= '" + fromAndToDates[1] + "' ");
				sb.append(" AND tras.activity_date >= '" + fromAndToDates[0] + "' ");
				
			} catch (Exception e) {
				TPLogger.getLogger().error("Unable to parse date and apply filter. dateRange: "+ dateRange +", fromDate: "+ fromDate+", toDate: "+toDate, e);
			}
		}
		return sb;
	}
	
	private StringBuilder buildClauseForPositionStatusFilter(String positionStatusId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(positionStatusId)){
			sb.append(" AND trpm.position_status_id IN (").append(positionStatusId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForPositionOwnerFilter(String positionOwnerId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(positionOwnerId)){
			sb.append(" AND trpm.position_owner_id IN (").append(positionOwnerId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForPositionDepartmentFilter(String deptId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(deptId)){
			sb.append(" AND trpm.dept_id IN (").append(deptId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForPositionFilter(String positionId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(positionId)){
			sb.append(" AND trpm.position_id IN (").append(positionId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForUserRoleFilter(String userRoleId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(userRoleId)){
			// TODO: need to implement User Role filter
		}
		return sb;
	}
	
	private StringBuilder buildClauseForActivityUserFilter(String userId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(userId)){
			sb.append(" AND tras.user_id IN (").append(userId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForProcessUserFilter(String userId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(userId)){
			sb.append(" AND tres.user_id IN (").append(userId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForSourceCategoryFilter(String sourceCategoryId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(sourceCategoryId)){
			sb.append(" AND trcm.source_type_id IN (").append(sourceCategoryId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForSourceFilter(String sourceId, StringBuilder sb) {
		if(!Utils.isBlankOrNull(sourceId)){
			sb.append(" AND trcm.source_id IN (").append(sourceId).append(")");
		}
		return sb;
	}
	
	private StringBuilder buildClauseForStageFilter(String stageIds, StringBuilder sb) {
		if(!Utils.isBlankOrNull(stageIds)) {
			sb.append(" AND tres.step_level IN (" + stageIds +")");	
		}
		return sb;
	}
	
	private StringBuilder buildClauseForStepFilter(String stepIds, StringBuilder sb) {
		if(!Utils.isBlankOrNull(stepIds)) {
			sb.append(" AND tres.step_id IN (" + stepIds +")");	
		}
		return sb;
	}
	
	public static String buildPermissionClause(String userId, PermissionSet permissionSet){
		StringBuilder sb = new StringBuilder();
		if(permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
			String dynaParam = "";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynaParam = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "trpm.position_id", dynamicContent, permissionSet);
			dynaParam = Utils.replaceQMarksWithDynamicContent(dynaParam, dynamicContent);
			sb.append(dynaParam);
		}
		return sb.toString();
	}
	
}
