package com.talentPool.reportDesign.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.talentPool.admin.manager.HierarchyManager;
import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.budget.BudgetConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.reportDesign.constants.ReportDesignConstants;
import com.talentPool.reportDesign.dataobject.ReportData;
import com.talentPool.reports.ReportConstants;
import com.talentPool.reports.dataobject.FilterData;
import com.talentPool.reports.form.ReportForm;
import com.talentPool.user.manager.PermissionSet;

public class FilterCriteria {
	
	public String addFilterCriteria(String query, FilterData filterData, ReportData reportData, 
			String dateColumn, String userColumn){
		ArrayList<String> queryList = new ArrayList<String>();
		try {
			if(!Utils.isBlankOrNull(reportData.getFilters())){
				List<String> filters = Arrays.asList(reportData.getFilters().split(","));		
				
				if(filters.contains(ReportDesignConstants.FILTER_DEPARTMENT)){
					if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
						queryList.add("tpo.dept_id = "+filterData.getDepartmentId());
						if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
							queryList.add("tpo.sub_dept_id = "+filterData.getSubDepartmentId());
							if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
								queryList.add( "tpo.sub_sub_dept_id = "+filterData.getSubSubDepartmentId());
							}
						}
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_DEPARTMENT)){
					if (!Utils.isBlankOrNull(filterData.getDepartmentId())) {
						queryList.add("tbi.dept_id = "+filterData.getDepartmentId());
						if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
							queryList.add("tbi.sub_dept_id = "+filterData.getSubDepartmentId());
							if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
								queryList.add( "tbi.sub_sub_dept_id = "+filterData.getSubSubDepartmentId());
							}
						}
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_POSITION)){
					if(!Utils.isBlankOrNull(filterData.getFilterId())){ 
						if (filterData.getFilterId().equals(ReportConstants.FILTER_ALL_POSITIONS)) {
							queryList.add("tpo.position_status != "+PositionConstants.POSITION_STATUS_TEMPLATE);
						}else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_POSITIONS)) {
							queryList.add("tpo.position_status = "+PositionConstants.POSITION_STATUS_OPENED);
						} else if (filterData.getFilterId().equals(ReportConstants.FILTER_OPEN_AND_ONHOLD_POSITIONS)) {
							queryList.add( "(tpo.position_status = "+PositionConstants.POSITION_STATUS_OPENED+" OR tpo.position_status = "+PositionConstants.POSITION_STATUS_HOLD+") ");
						} else if (filterData.getFilterId().equals(ReportConstants.FILTER_SPECIFIC_POSITION)) {
							queryList.add( "tpo.position_id = "+filterData.getPositionId());
						}
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_STAGE)){
					if(!Utils.isBlankOrNull(filterData.getStages())){ 
						if(filterData.getStages().equals(PositionConstants.STEP_LEVEL_JOIN)){
							queryList.add( "tap.applicant_joined ="+ApplicantConstants.APPLICANT_JOINED);
						}else{
							queryList.add( "tps.position_step_level ="+filterData.getStages());
						}
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_SOURCE)){
					if(!Utils.isBlankOrNull(filterData.getSourceId())){ 
						if(filterData.getSourceId().equals(ReportConstants.SOURCE_EMP_REFERRALS_ALL)){
							queryList.add("tsy.source_type_category=2");
						}else
							queryList.add( "tso.source_id ="+filterData.getSourceId());
					}
						
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_USER)){
					if(!Utils.isBlankOrNull(filterData.getUsers())){
						queryList.add(userColumn +" in("+filterData.getUsers()+")");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_DATE)){
					if(!Utils.isBlankOrNull(filterData.getToDate())){
						Date toDate = Utils.convertToDate(filterData.getToDate(), Utils.regEUDateFormat);
						toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
						String toDateStr = Utils.getDateConvertedToString(toDate, DateConstants.DB_DATE_TIME_PATTERN);
						queryList.add(dateColumn+" <'"+toDateStr+"'");
					}
					if(!Utils.isBlankOrNull(filterData.getFromDate())){
						Date fromDate = Utils.convertToDate(filterData.getFromDate(), Utils.regEUDateFormat);
						String fromDateStr = Utils.getDateConvertedToString(fromDate, DateConstants.DB_DATE_TIME_PATTERN);
						queryList.add(dateColumn+" >='"+fromDateStr+"'");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_APPOINTMENT)){
					if(!Utils.isBlankOrNull(filterData.getToAppointmentDate())){
						Date toDate = Utils.convertToDate(filterData.getToAppointmentDate(), Utils.regEUDateFormat);
						toDate = Utils.adjustDateBy(toDate, Calendar.DATE, 1);
						String toDateStr = Utils.getDateConvertedToString(toDate, DateConstants.DB_DATE_TIME_PATTERN);
						queryList.add("tpp.appointment_from_date <'"+toDateStr+"'");
					}
					if(!Utils.isBlankOrNull(filterData.getFromAppointmentDate())){
						Date fromDate = Utils.convertToDate(filterData.getFromAppointmentDate(), Utils.regEUDateFormat);
						String fromDateStr = Utils.getDateConvertedToString(fromDate, DateConstants.DB_DATE_TIME_PATTERN);
						queryList.add("tpp.appointment_from_date >='"+fromDateStr+"'");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_EXPENSE_TYPE)){
					if(!Utils.isBlankOrNull(filterData.getSelectedExpenseTypes())){
						queryList.add( "tct.cost_type_id in("+filterData.getSelectedExpenseTypes()+")");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_ACTIVITY_BY)){
					if(!Utils.isBlankOrNull(filterData.getSelectedActivityByUsers())){
						queryList.add( "tact.user_id in("+filterData.getSelectedActivityByUsers()+")");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_ACTIVITY_TYPE)){
					if(!Utils.isBlankOrNull(filterData.getSelectedActivityTypes())){
						queryList.add( "tact.interaction_type in("+filterData.getSelectedActivityTypes()+")");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_IMPORTED_BY)){
					if(!Utils.isBlankOrNull(filterData.getSelectedImportedByUsers())){
						queryList.add("tap.user_id in("+filterData.getSelectedImportedByUsers()+")");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_AUDIT_ENTITY)){
					if(!Utils.isBlankOrNull(filterData.getEntityTypeId()) && !filterData.getEntityTypeId().equals("-1")){
						queryList.add("tae.entity_type IN("+filterData.getEntityTypeId()+")");
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_GRADE)){
					if(!Utils.isBlankOrNull(filterData.getBudgetGradeId()) && !filterData.getBudgetGradeId().equals("-1")){
						queryList.add("tbi.grade_id ="+filterData.getBudgetGradeId());
					}
				}
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_BAND)){
					if(!Utils.isBlankOrNull(filterData.getBudgetBandId()) && !filterData.getBudgetBandId().equals("-1")){
						queryList.add("tbi.band_id ="+filterData.getBudgetBandId());
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_STATUS)){
					if(!Utils.isBlankOrNull(filterData.getBudgetStatus()) && !filterData.getBudgetStatus().equals("-1")){
						queryList.add("tbi.status ="+BudgetConstants.BUDGET_ITEM_STATUS_ACTIVE);
					}
				}
				if(queryList.size()>0){
					for (String string : queryList) {
						query+=" AND "+string;
					}
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return query ;
	}

	public String addShowPositionWithRightsClause(String query, String userId, PermissionSet permissionSet){
		if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
			HierarchyManager hierarchyManager = new HierarchyManager();
			String subordinateUsers = hierarchyManager.getAllChildrens(userId);
			if(!Utils.isBlankOrNull(subordinateUsers)){
				subordinateUsers += ",";
			}
			subordinateUsers += userId;
			
			query +=" AND tpo.position_id in (select su.position_id from tp_position_step_users su, " +
			" tp_position_steps st where st.position_step_id = su.position_step_id " +
			" and st.position_step_status = "+PositionConstants.STEP_ACTIVE + 
			" and su.user_id IN ("+subordinateUsers+") union select position_id from tp_positions " +
			" where position_requested_by IN ("+subordinateUsers+") UNION SELECT distinct traf.position_id " +
			" FROM tp_requisition_approval_feedback traf " +
			" WHERE traf.by_user_id IN ("+subordinateUsers+") OR traf.to_user_id IN ("+subordinateUsers+") )";
            
		}
		return query;
	}
	
	public String addDoNotShowConfidentialProfileClause(String query,  String userId, PermissionSet permissionSet){
		if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
			query += " AND tap.is_confidential="+ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL;
      }
		return query;
	}
	
	public static void setFilterCriteriaString(FilterData filterData, ReportForm reportForm, ReportData reportData){
		try {
			if(!Utils.isBlankOrNull(reportData.getFilters())){
				List<String> filters = Arrays.asList(reportData.getFilters().split(","));					
				
				if(filters.contains(ReportDesignConstants.FILTER_DATE)){
					String dateQuery = "Date: "+filterData.getFromDate()+" to "+filterData.getToDate();
					filterData.setDateFilterCriteria(dateQuery);
				}
				
				StringBuffer otherFilterCriteria = new StringBuffer("");			
				
				if(filters.contains(ReportDesignConstants.FILTER_DEPARTMENT)){
					otherFilterCriteria.append("   "+GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_1)+": "+reportForm.getDepartmentTitle());
					if(!Utils.isBlankOrNull(filterData.getSubDepartmentId())){
						otherFilterCriteria.append("   "+GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_2)+": "+reportForm.getSubDepartmentTitle());
						if(!Utils.isBlankOrNull(filterData.getSubSubDepartmentId())){
							otherFilterCriteria.append( "   "+GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DEPARTMENT_LEVEL_3)+": "+reportForm.getSubSubDepartmentTitle());
						}
					}
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_POSITION)){
					otherFilterCriteria.append("   "+TPLabels.getLabel("common.position")+": "+reportForm.getPositionTitle());				
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_STAGE)){
					otherFilterCriteria.append("   "+TPLabels.getLabel("report.label.position_stages.stage")+": "+reportForm.getStageNames());					
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_SOURCE)){
					otherFilterCriteria.append( "   "+TPLabels.getLabel("report.label.candidate_status.source")+": "+reportForm.getSourceName());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_IMPORTED_BY)){
					otherFilterCriteria.append("   "+TPLabels.getLabel("report.label.importedBy")+": "+reportForm.getSelectedImportedByUserNames());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_USER)){
					otherFilterCriteria.append("   "+TPLabels.getLabel("report.label.users")+": "+reportForm.getSelectedUserNames());
				}		
				
				if(filters.contains(ReportDesignConstants.FILTER_APPOINTMENT)){
					String appointmentDate = "   "+TPLabels.getLabel("common.label.appointment_date_range")+": "+filterData.getFromAppointmentDate()+" to "+filterData.getToAppointmentDate();
					otherFilterCriteria.append(appointmentDate);
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_EXPENSE_TYPE)){
					otherFilterCriteria.append( "   "+TPLabels.getLabel("report.label.expenseType")+": "+reportForm.getSelectedExpenseTypeNames());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_ACTIVITY_BY)){
					otherFilterCriteria.append("   "+TPLabels.getLabel("report.label.activity_by")+": "+reportForm.getSelectedActivityByUserNames());
				}	
				
				if(filters.contains(ReportDesignConstants.FILTER_ACTIVITY_TYPE)){
					otherFilterCriteria.append( "   "+TPLabels.getLabel("report.label.user_activity.interaction")+": "+reportForm.getSelectedActivityTypeNames());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_AUDIT_ENTITY)){
					otherFilterCriteria.append( "   "+TPLabels.getLabel("report.label.audit_entry.entity")+": "+reportForm.getEntityTypeTitle());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_GRADE)){
					otherFilterCriteria.append( "   "+GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_GRADE_LABEL)+": "+reportForm.getBudgetGradeName());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_BAND)){
					otherFilterCriteria.append( "   "+GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_BUDGET_ITEM_BAND_LABEL)+": "+reportForm.getBudgetBandName());
				}
				
				if(filters.contains(ReportDesignConstants.FILTER_BUDGET_STATUS)){
					otherFilterCriteria.append( "   "+TPLabels.getLabel("common.budget_item")+" "+TPLabels.getLabel("common.status")+": "+reportForm.getBudgetStatusTitle());
				}
				String otherFC = otherFilterCriteria.toString();
				if(otherFilterCriteria.length()>2)
					otherFC = (String) (otherFilterCriteria.toString()).subSequence(3,otherFilterCriteria.length());
				filterData.setOtherFilterCriteria(otherFC);
			}				
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}	
	}
	
}
