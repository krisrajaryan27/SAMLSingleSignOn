/**
 * 
 */
package com.talentPool.selectionProcess.manager;

import java.sql.SQLException;
import java.util.ArrayList;

import com.talentPool.calendar.CalendarConstants;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.dashboard.constants.DashboardConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.todo.constants.ToDoConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class FilterManager {

	public ArrayList<SimpleDataObject> getFilters(PermissionSet permissionSet, String userId, String filterFor, 
			String departmentId, String positionId, String stepName, String applicantName, String stepLevel, 
			String stepId, String locationTitle, String positionTypeExtInt, String actionType, String selectedUserId,String sourceId,String selectedView) {
		ArrayList<SimpleDataObject> filters = null;
		DBPreparedQuery dq = null;
		try {
			ArrayList<String> dynamicContent = new ArrayList<String>();
			
			String[] dynParams = new String[5];
			dynParams[0] = "";
			dynParams[1] = "";
			dynParams[2] = "";
			dynParams[3] = "";
			dynParams[4] = "";
			// first dynamic part
			if (filterFor.equals(SelectionProcessConstants.FILTER_DEPARTMENT)) {
				dynParams[0] = " dept_id AS filter_id, dept_name AS filter_short_name, dept_name AS filter_full_name ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION)) {
				dynParams[0] = " position_id AS filter_id, position_code AS filter_short_name, position_title AS filter_full_name ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_STEP)) {
				dynParams[0] = " position_step_title AS filter_id, position_step_title AS filter_short_name, position_step_title AS filter_full_name ";
			}  else if (filterFor.equals(SelectionProcessConstants.FILTER_LOCATION)) {
				dynParams[0] = " applicant_city AS filter_id, applicant_city AS filter_short_name, applicant_city AS filter_full_name ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION_TYPE)) {
				dynParams[0] = " position_type_ext_int AS filter_id, position_type_ext_int AS filter_short_name, position_type_ext_int AS filter_full_name";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_ACTION)) {
				dynParams[0] = " action_type AS filter_id, action_required AS filter_short_name, action_required AS filter_full_name ";
			} else if(filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				dynParams[0] = " user_id AS  filter_id, CONCAT(user_fname,' ',user_lname) AS filter_short_name,  CONCAT(user_fname,' ',user_lname) AS filter_full_name ";
			} else if(filterFor.equals(SelectionProcessConstants.FILTER_SOURCE)) {
				dynParams[0] = " source_id AS  filter_id, source_title AS filter_short_name, source_title AS filter_full_name ";
			}

			if(!Utils.isBlankOrNull(stepLevel)) {
				if(NavigationConstants.T_SELECT.equals(selectedView)) {
					String qMarks = Utils.setDynamicParamsAndReturnQmarks(stepLevel, dynamicContent);
					dynParams[1] += " AND tps.position_step_level in ("+qMarks+")  ";
				}else if(NavigationConstants.T_HIRE.equals(selectedView)) {
					dynParams[1] += " AND tps.position_step_level = ? ";
					dynamicContent.add(stepLevel);
				}else if(NavigationConstants.T_POSITIONS.equals(selectedView)) {
					String qMarks = Utils.setDynamicParamsAndReturnQmarks(stepLevel, dynamicContent);
					dynParams[1] += " AND tps.position_step_level in ("+qMarks+")  ";
				}
			}
			if(!Utils.isBlankOrNull(positionId)) {
				dynParams[1] += " AND todo.position_id= "+positionId;
			}
			dynParams[1] += PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "ta.applicant_position_id", dynamicContent, permissionSet);
			
			if (filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				dynParams[2] += " GROUP BY todo.applicant_id, todo.position_id, todo.user_id ";	
			}else{
				dynParams[2] += " GROUP BY todo.applicant_id, todo.position_id ";	
			}
			
			// add approve, select, hire screen criteria
			if (!Utils.isBlankOrNull(stepId)) {
				dynParams[3] += " AND applicant_step_id =? ";
				dynamicContent.add(stepId);
			}
			if (!Utils.isBlankOrNull(departmentId)) {
				dynParams[3] += " AND dept_id = ? ";
				dynamicContent.add(departmentId);
			}
			if (!Utils.isBlankOrNull(positionId)) {
				dynParams[3] += " AND position_id=? ";
				dynamicContent.add(positionId);
			}
			if (!Utils.isBlankOrNull(stepName)) {
				dynParams[3] += " AND position_step_title = ? ";
				dynamicContent.add(stepName);
			}
			if (!Utils.isBlankOrNull(applicantName)) {
				dynParams[3] += " AND applicant_name like ? ";
				dynamicContent.add(applicantName + "%");
			}
			if (!Utils.isBlankOrNull(locationTitle)) {
				dynParams[3] += " AND applicant_city like ? ";
				dynamicContent.add(locationTitle + "%");
			}			
			if (!Utils.isBlankOrNull(positionTypeExtInt)) {
				dynParams[3] += " AND position_type_ext_int=? ";
				dynamicContent.add(positionTypeExtInt);
			}
			if (!Utils.isBlankOrNull(selectedUserId)) {
				if(selectedUserId.indexOf(",")!=-1){
					String qMarks = Utils.setDynamicParamsAndReturnQmarks(selectedUserId, dynamicContent);
					dynParams[3] += " AND user_id in ("+qMarks+") ";
				}else{
					dynParams[3] += " AND user_id=? ";
					dynamicContent.add(selectedUserId);
				}
			}
			if(!Utils.isBlankOrNull(actionType)) {
				dynParams[3] += " AND action_type=? ";
				dynamicContent.add(actionType);
			}
			if(!Utils.isBlankOrNull(sourceId)) {
				dynParams[3] += " AND source_id=? ";
				dynamicContent.add(sourceId);
			}
			
			
			if (filterFor.equals(SelectionProcessConstants.FILTER_DEPARTMENT)) {
				dynParams[4] += " GROUP BY dept_id";
				dynParams[4] += " ORDER BY dept_name ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION)) {
				dynParams[4] += " GROUP BY position_id ";
				dynParams[4] += " ORDER BY position_code ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_STEP)) {
				dynParams[4] += " GROUP BY position_step_title ";
				dynParams[4] += " ORDER BY position_step_title ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_LOCATION)) {
				dynParams[4] += " AND applicant_city IS NOT NULL ";
				dynParams[4] += " GROUP BY applicant_city ";
				dynParams[4] += " ORDER BY applicant_city ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_POSITION_TYPE)) {
				dynParams[4] += " AND position_type_ext_int IS NOT NULL ";
				dynParams[4] += " GROUP BY position_type_ext_int ";
				dynParams[4] += " ORDER BY position_type_ext_int ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_ACTION)) {				
				dynParams[4] += " GROUP BY action_required ";
				dynParams[4] += " ORDER BY action_required ";
			}else if (filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				dynParams[4] += " AND user_id IS NOT NULL "; 
				dynParams[4] += " GROUP BY user_id ";
				dynParams[4] += " ORDER BY user_fname ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_SOURCE)) {				
				dynParams[4] += " GROUP BY source_id ";
				dynParams[4] += " ORDER BY source_id ";
			}
			dq = new DBPreparedQuery("dFilterManager_GetFilters", dynParams);
			int cnt = 1;
			dq.setInt(cnt++, DashboardConstants.ACTION_ON_HOLD);
			dq.setInt(cnt++, DashboardConstants.ACTION_ON_CONDUCT);
			dq.setString(cnt++, TPLabels.getLabel("dashboard.label.todo.conduct"));
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE);
			dq.setString(cnt++, TPLabels.getLabel("dashboard.label.todo.confirm"));
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_FEEDBACK);
			dq.setString(cnt++, TPLabels.getLabel("dashboard.label.todo.feedback"));
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_SCHEDULE);
			dq.setString(cnt++, TPLabels.getLabel("dashboard.label.todo.schedule"));
			dq.setString(cnt++, TPLabels.getLabel("dashboard.label.todo.onhold"));
			
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_SCHEDULE);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_HAPPENED);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_CONFIRM_ATTENDANCE);
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_FEEDBACK);
			dq.setInt(cnt++, DashboardConstants.ACTION_ON_CONDUCT);			
			dq.setInt(cnt++, DashboardConstants.ACTION_REQUIRED_FEEDBACK);
			
			dq.setInt(cnt++, PositionConstants.STEP_SCHEDULED);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_SCHEDULING);
			
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			dq.setInt(cnt++, CalendarConstants.APPOINTMENT_STATUS_HAPPENED);
			dq.setInt(cnt++, PositionConstants.STEP_INTERVIEWER_CAN_CONFIRM);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_INTERVIEW);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_SCHEDULING);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_SCHEDULING);
			
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_INTERVIEW);
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_DECISION);	
			dq.setInt(cnt++, PositionConstants.RESPONSIBLE_FOR_INTERVIEW);
			
			dq.setString(cnt++, ToDoConstants.FEEDBACK_NOT_PRESENT);
			dq.setString(cnt++, ToDoConstants.TODO_TYPE_SELECTION);
			dq.setString(cnt++, PositionConstants.POSITION_STATUS_OPENED);

			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			
			filters = dq.getResult();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return filters;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<SimpleDataObject> getRejectedApplicantsGridFilters (String filterFor, String positionId, String stepName, String applicantName,String rejectedBy) throws SQLException {
		ArrayList<SimpleDataObject> filters = null;
		DBPreparedQuery dq = null;
		try {
			ArrayList<String> dynamicContent = new ArrayList<String>();			
			String[] dynParams = {"","",""};
			
			if (filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				dynParams[0] = " rejected_by AS  filter_id, rejected_by AS filter_short_name,  rejected_by AS filter_full_name ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_STEP)) {
				dynParams[0] = " step_title AS  filter_id, step_title AS filter_short_name,  step_title AS filter_full_name ";
			}
			
			if(!Utils.isBlankOrNull(stepName)){
				dynParams[1] += " AND tps.position_step_title =?  ";
				dynamicContent.add(stepName);
			}
			
			if(!Utils.isBlankOrNull(rejectedBy)){
				dynParams[1] += " AND CONCAT(tu.user_fname,' ',tu.user_lname) =?  ";
				dynamicContent.add(rejectedBy);
			}
			
			if (!Utils.isBlankOrNull(applicantName)) {
				dynParams[1] += " AND applicant_name like ? ";
				dynamicContent.add(applicantName + "%");
			}
			
			if (filterFor.equals(SelectionProcessConstants.FILTER_USER)) {
				dynParams[2] += " GROUP BY rejected_by";
				dynParams[2] += " ORDER BY rejected_by ";
			} else if (filterFor.equals(SelectionProcessConstants.FILTER_STEP)) {
				dynParams[2] += " GROUP BY step_title ";
				dynParams[2] += " ORDER BY step_title ";
			} 
			
			dq = new DBPreparedQuery("dFilterManager_GetRejectedCandidatesFilters", dynParams);
			int cnt = 1;
			
			dq.setString(cnt++, positionId);			

			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			
			filters = (ArrayList<SimpleDataObject>)dq.getResult();

		} catch (SQLException sqle) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, sqle);
			throw sqle;
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		
		return filters;
	}
}
