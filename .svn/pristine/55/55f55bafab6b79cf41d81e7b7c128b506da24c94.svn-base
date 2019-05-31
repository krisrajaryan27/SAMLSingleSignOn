/**
 * 
 */
package com.talentPool.latestActivity.manager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author Ajeet
 *
 */
public class LatestActivityManager {
	
	public void addUserActivity(String activity, String interactionId, int interactionType,
			String positionId, String applicantId, String userId) {
		try {
			addUserActivity(activity, interactionId, interactionType, positionId, applicantId, userId, null);			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	public void addUserActivity(String activity, String interactionId, int interactionType,
			String positionId, String applicantId, String userId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if(tran!=null)
				dq = new DBPreparedQuery("dActivityManager_AddUserActivity", tran);
			else
				dq = new DBPreparedQuery("dActivityManager_AddUserActivity");
			
			int cnt = 1;
			dq.setString(cnt++, activity);
			dq.setId(cnt++, interactionId);
			dq.setInt(cnt++, interactionType);
			dq.setTimestamp(cnt++, new Timestamp(new Date().getTime()));
			dq.setId(cnt++, positionId);
			dq.setId(cnt++, applicantId);
			dq.setId(cnt++, userId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	public ArrayList<SimpleDataObject> getLatestActivity(String userId, String stepLevel, PermissionSet permissionSet) {
		return getLatestActivity(userId, stepLevel, null, permissionSet); 
	}
	
	public ArrayList<SimpleDataObject> getLatestActivity(String userId, String stepLevel,String positionId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		ArrayList<SimpleDataObject> latestActivities = null;
		try {
			String[] dynParam = new String[4];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			ArrayList<String> dynamicContent0 = new ArrayList<String>();
			ArrayList<String> dynamicContent1 = new ArrayList<String>();
			ArrayList<String> dynamicContent2 = new ArrayList<String>();
			
			dynParam[0] = " ";
			if (!Utils.isBlankOrNull(stepLevel) && !stepLevel.equals(PositionConstants.STEP_LEVEL_ACCEPT)) {
				String[] arrIds = stepLevel.split(",");
				String qMarks = "";
				for (int i = 0; i < arrIds.length; i++) {
					qMarks += (i > 0) ? ",?" : "?";
					dynamicContent.add(arrIds[i].trim());
				}
				dynParam[0] = " AND tps.position_step_level IN ( " + qMarks + ")";
			}else if(!Utils.isBlankOrNull(stepLevel) && stepLevel.equals(PositionConstants.STEP_LEVEL_ACCEPT)){
				dynParam[0] = " AND (tps.position_step_level = ? OR applicant_joined=?)";
				dynamicContent.add(PositionConstants.STEP_LEVEL_ACCEPT);
				dynamicContent.add(ApplicantConstants.APPLICANT_JOINED);
			}
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[1] = " AND ta.is_confidential = ? ";
				dynamicContent0.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}else{
				dynParam[1] = " ";
			}			

			dynParam[2] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApproval(userId, "tua.position_id", dynamicContent1, permissionSet);
						
			if(Utils.isBlankOrNull(positionId)){
				dynParam[3] = "";
			}else {
				dynParam[3] = "AND tua.position_id=? ";
				dynamicContent2.add(positionId);
			}
			
			
			dq = new DBPreparedQuery("dActivityManager_GetLatestActivity", dynParam);
			int cnt = 1;
			dq.setInt(cnt++, 7);
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			for (int i = 0; i < dynamicContent0.size(); i++) {
				dq.setString(cnt++, dynamicContent0.get(i));
			}			
			for (int i = 0; i < dynamicContent1.size(); i++) {
				dq.setString(cnt++, dynamicContent1.get(i));
			}
			for (int i = 0; i < dynamicContent2.size(); i++) {
				dq.setString(cnt++, dynamicContent2.get(i));
			}
			latestActivities = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return latestActivities;
	}

	public String getPositionIdWithApplicantId(String applicantId) {
		DBPreparedQuery dq = null;
		String positionId = null;
		try {
			dq = new DBPreparedQuery("dActivityManager_GetPositionIdWithApplicantId");
			dq.setId(1, applicantId);
			positionId = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positionId;
	}
	
	public void updateShowHide(String communicationId, String communicationType) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dActivityManager_UpdateShowHideActivity");
			dq.setString(1, SelectionProcessConstants.INTERACTION_UNHIDE);
			dq.setString(2, SelectionProcessConstants.INTERACTION_HIDE);
			dq.setString(3, SelectionProcessConstants.INTERACTION_UNHIDE);
			dq.setId(4, communicationId);
			dq.setId(5, communicationType);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteUserActivity(String interactionId, int interactionType, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		try {
			if (tran == null) {
				dq = new DBPreparedQuery("dActivityManager_DeleteUserActivity");
			} else {
				dq = new DBPreparedQuery("dActivityManager_DeleteUserActivity", tran);
			}
			int cnt = 1;
			dq.setId(cnt++, interactionId);
			dq.setInt(cnt++, interactionType);
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			throw e;
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}		
	}
	

}
