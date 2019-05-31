/**
 * 
 */
package com.talentPool.customReports.utils;

import java.util.Date;

import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.constants.CustomReportConstants;
import com.talentPool.customReports.jaxb.Filter;
import com.talentPool.customReports.jaxb.Filters;
import com.talentPool.masters.utils.StepLevelStaticUtils;
import com.talentPool.masters.utils.StepStaticUtils;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.utils.PositionUtils;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.ReportUtils;
import com.talentPool.reports.manager.ReportManager;

/**
 * @author PraveenK
 * @since  Feb 17, 2012
 */
public class FilterCriteriaBuilder {
	
	private Filters filters;
	private StringBuilder filterCriteria;
	
	/**
	 * 
	 */
	public FilterCriteriaBuilder(Filters filters) {
		this.filters=filters;
		filterCriteria = new StringBuilder();
	}
	
	/**
	 * 
	 */
	public String getFilterCriteria() {
		if(!Utils.isListEmptyOrNull(filters.getFilter())){
			for (Filter filter : filters.getFilter()) {
				StringBuilder sb = buildCriteria(filter);
				if(sb!=null)
					filterCriteria.append(sb).append("\n") ;
			}
		}
		return filterCriteria.toString();
	}
	
	/**
	 * 
	 */
	protected StringBuilder buildCriteria(Filter filter) {
		if(CustomReportConstants.FILTER_AS_OF_DATE.equals(filter.getFilterId())){
			return buildCriteriaForAsOfDateFilter(filter);
		}else if(CustomReportConstants.FILTER_DATE.equals(filter.getFilterId())){
			return buildCriteriaForDateFilter(filter);
		}else if(CustomReportConstants.FILTER_POSITION_STATUS.equals(filter.getFilterId())){
			return buildCriteriaForPositionStatusFilter(filter);
		}else if(CustomReportConstants.FILTER_POSITION_OWNER.equals(filter.getFilterId())){
			return buildCriteriaForPositionOwnerFilter(filter);
		}else if(CustomReportConstants.FILTER_DEPARTMENT.equals(filter.getFilterId())){
			return buildCriteriaForPositionDepartmentFilter(filter);
		}else if(CustomReportConstants.FILTER_POSITION.equals(filter.getFilterId())){
			return buildClauseForPositionFilter(filter);
		}else if(CustomReportConstants.FILTER_ACTIVITY_USER.equals(filter.getFilterId())){
			return buildClauseForActivityUserFilter(filter);
		}else if(CustomReportConstants.FILTER_STAGE.equals(filter.getFilterId())){
			return buildClauseForStepLevelFilter(filter);
		}else if(CustomReportConstants.FILTER_STEP.equals(filter.getFilterId())){
			return buildClauseForStepFilter(filter);
		}else if(CustomReportConstants.FILTER_PROCESS_USER.equals(filter.getFilterId())){
			return buildClauseForProcessUserFilter(filter);
		}else if(CustomReportConstants.FILTER_ACTIVITY_DATE.equals(filter.getFilterId())){
			return buildCriteriaForDateFilter(filter);
		}else if(CustomReportConstants.FILTER_OFFER_DATE.equals(filter.getFilterId())){
			return buildCriteriaForDateFilter(filter);
		}else if(CustomReportConstants.FILTER_JOINING_DATE.equals(filter.getFilterId())){
			return buildCriteriaForDateFilter(filter);
		}
		return null;
	}
	
	protected StringBuilder buildCriteriaForAsOfDateFilter(Filter filter){
		if(ReportConstants.CUSTOM.equals(filter.getValue())){
			if(!Utils.isBlankOrNull(filter.getValue1())){
				return ReportUtils.getAsOfDateCriteria(filter.getValue1());
			}
		} else {
			return ReportUtils.getAsOfDateCriteria(CustomReportUtils.getReportsAsOfDate(filter.getValue()));
		}
		return null;
	}
	
	protected StringBuilder buildCriteriaForDateFilter(Filter filter){
		try {
			StringBuilder sb = new StringBuilder();
			if(ReportConstants.CUSTOM.equals(filter.getValue())){
				if(!Utils.isBlankOrNull(filter.getValue1()) && !Utils.isBlankOrNull(filter.getValue2())){
					return sb.append(ReportUtils.getDateCriteria(filter.getValue1(), filter.getValue2()));
				}
			} else {
				 Date[] fromAndToDate;
					fromAndToDate = CustomReportUtils.getFromAndToDateFromDateRange(filter.getValue());
				
				return sb.append(ReportUtils.getDateCriteria(fromAndToDate[0],fromAndToDate[1]));
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Unable to critearia to Date range filter due to some wrong input formats",e);
		}
		return null;
	}
	
	protected StringBuilder buildCriteriaForPositionStatusFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.position_status")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			boolean start = true;
			for (String positionStatusId : filter.getValue().split(CommonConstants.DEFAULT_DELIMITER)) {
				if(start){
					sb.append(Utils.getBlankIfNull(PositionUtils.getPositionStatusValues(positionStatusId)));
					start=false;
				}else{
					sb.append(CommonConstants.DEFAULT_DELIMITER).append(Utils.getBlankIfNull(PositionUtils.getPositionStatusValues(positionStatusId)));
				}
			}
			return sb;
		}else{
			return sb.append(TPLabels.getLabel("common.all"));
		}
	}
	
	protected StringBuilder buildCriteriaForPositionOwnerFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("global.position_owner")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			ReportManager reportManager = new ReportManager();
			String positionOwner = reportManager.getUsersForIds(filter.getValue());
			return sb.append(Utils.getBlankIfNull(positionOwner));
		}else{
			return sb.append(TPLabels.getLabel("common.all"));
		}
	}
	
	protected StringBuilder buildCriteriaForPositionDepartmentFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.department")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			String deptNames = CommonUtils.getDeptNames(filter.getValue());
			return sb.append(Utils.getBlankIfNull(deptNames));
		}else{
			return sb.append(TPLabels.getLabel("common.department_all"));
		}
	}
	
	protected StringBuilder buildClauseForPositionFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.positions")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			PositionManager positionManager = new PositionManager();
			String positionName = positionManager.getPositionNames(filter.getValue());
			return sb.append(Utils.getBlankIfNull(positionName));
		}else{
			return sb.append(TPLabels.getLabel("common.positions_all"));
		}
	}
	
	protected StringBuilder buildClauseForActivityUserFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.users")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			ReportManager reportManager = new ReportManager();
			String userNames = reportManager.getUsersForIds(filter.getValue());
			return sb.append(Utils.getBlankIfNull(userNames));
		}else{
			return sb.append(TPLabels.getLabel("common.all"));
		}
	}
	
	protected StringBuilder buildClauseForProcessUserFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.process")).append(" ").append(TPLabels.getLabel("common.users")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			ReportManager reportManager = new ReportManager();
			String userNames = reportManager.getUsersForIds(filter.getValue());
			return sb.append(Utils.getBlankIfNull(userNames));
		}else{
			return sb.append(TPLabels.getLabel("common.all"));
		}
	}
	
	protected StringBuilder buildClauseForStepLevelFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.stage")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			String stepLevelName = StepLevelStaticUtils.getStepLevelNames(filter.getValue());
			return sb.append(Utils.getBlankIfNull(stepLevelName));
		}else{
			return sb.append(TPLabels.getLabel("common.all"));
		}
	}
	
	protected StringBuilder buildClauseForStepFilter(Filter filter){
		StringBuilder sb = new StringBuilder(TPLabels.getLabel("common.step")).append(": ");
		if(!Utils.isBlankOrNull(filter.getValue())){
			String stepLevelName = StepStaticUtils.getStepNames(filter.getValue());
			return sb.append(Utils.getBlankIfNull(stepLevelName));
		}else{
			return sb.append(TPLabels.getLabel("common.all"));
		}
	}
}
