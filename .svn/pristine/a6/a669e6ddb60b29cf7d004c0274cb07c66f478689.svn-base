package com.talentPool.positions.utils;

import java.util.ArrayList;

import com.talentPool.admin.manager.HierarchyManager;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.user.manager.PermissionSet;

public class PositionWithRightsClause {

	public static String getClauseForStepUserAndRequestedBy(String userId, String positionColumn, ArrayList<String> dynamicContent, PermissionSet permissionSet) {
		
		return getClause(userId, positionColumn, dynamicContent, true, true, false, false, true, permissionSet);
		
	}
	
	public static String getClauseForStepUserAndRequestedByAndRequisitionApproval(String userId, String positionColumn, ArrayList<String> dynamicContent, PermissionSet permissionSet) {
		
		return getClause(userId, positionColumn, dynamicContent, true, true, true, false, true, permissionSet);
		
	}
	
	public static String getClauseForStepUserAndRequestedByAndRequisitionApprovalForUserOnly(String userId, String positionColumn, ArrayList<String> dynamicContent, PermissionSet permissionSet) {
		
		return getClause(userId, positionColumn, dynamicContent, true, true, true, false, false, permissionSet);
		
	}

	public static String getClauseForStepUserAndRequestedByAndRequisitionApprovalAndAppointmentAttendee(String userId, String positionColumn, ArrayList<String> dynamicContent, PermissionSet permissionSet) {
		
		return getClause(userId, positionColumn, dynamicContent, true, true, true, true, true, permissionSet);
		
	}
	
	private static String getClause(String userId, String positionColumn, ArrayList<String> dynamicContent, 
			boolean positionOwnerId,boolean requestedBy, boolean approvalFeedback, boolean appointmentAttendee, 
			boolean checkForSubOrdinates, PermissionSet permissionSet) {
		StringBuffer dynParam = new StringBuffer("");		
		if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
			if(!Utils.isBlankOrNull(userId) && dynamicContent!=null){
				String userList = userId;
				if(checkForSubOrdinates && GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY))){
					HierarchyManager hierarchyManager = new HierarchyManager();
					String subordinateUsers = hierarchyManager.getAllChildrens(userId);
					if(!Utils.isBlankOrNull(subordinateUsers)){
						userList += ","+subordinateUsers;
					}
				}
				int userIds = userList.split(",").length;
				
				dynParam.append(" AND ("+positionColumn+" IN (")
						.append("SELECT su.position_id FROM tp_position_step_users su, tp_position_steps ps WHERE  su.position_step_id = ps.position_step_id and ps.position_step_status = ? AND su.user_id IN ("+Utils.getQmarks(userIds)+")  ");
				if(requestedBy){
					dynParam.append("UNION  SELECT position_id from tp_positions where position_requested_by IN ("+Utils.getQmarks(userIds)+") ");
				}
				if(positionOwnerId){
					dynParam.append("UNION  SELECT position_id from tp_positions where position_owner_id = ? ");
				}
				if(approvalFeedback){
					dynParam.append("UNION SELECT distinct position_id FROM tp_requisition_approval_feedback WHERE by_user_id IN ("+Utils.getQmarks(userIds)+") OR to_user_id IN ("+Utils.getQmarks(userIds)+") ");
				}
				if(appointmentAttendee){
					dynParam.append(") or tpa.appointment_id in (select appointment_id from tp_appointment_attendees where attendee_id IN ("+Utils.getQmarks(userIds)+")");
				}
				dynParam.append("))");
				
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				Utils.setDynamicParamsAndReturnQmarks(userList, dynamicContent);
				if(requestedBy){
					Utils.setDynamicParamsAndReturnQmarks(userList, dynamicContent);
				}
				if(positionOwnerId){
					dynamicContent.add(userId);
				}
				if(approvalFeedback){
					Utils.setDynamicParamsAndReturnQmarks(userList, dynamicContent);
					Utils.setDynamicParamsAndReturnQmarks(userList, dynamicContent);
				}
				if(appointmentAttendee){
					Utils.setDynamicParamsAndReturnQmarks(userList, dynamicContent);
				}
			}
		}
		return dynParam.toString();
	}
	
	public static String getClauseForUserInList(String userId, String userColumn, ArrayList<String> dynamicContent, PermissionSet permissionSet) {
		
		StringBuffer dynParam = new StringBuffer("");		
		if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
			if(!Utils.isBlankOrNull(userId) && dynamicContent!=null){
				String userList = userId;
				if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_ENABLE_ORG_HIERARCHY_FOR_VISIBILITY))){
					HierarchyManager hierarchyManager = new HierarchyManager();
					String subordinateUsers = hierarchyManager.getAllChildrens(userId);
					if(!Utils.isBlankOrNull(subordinateUsers)){
						userList += ","+subordinateUsers;
					}
				}
				int userIds = userList.split(",").length;
				
				dynParam.append(" AND ("+userColumn+" IN ("+Utils.getQmarks(userIds)+"))");				
				Utils.setDynamicParamsAndReturnQmarks(userList, dynamicContent);
			}
		}
		return dynParam.toString();
		
	}
}
