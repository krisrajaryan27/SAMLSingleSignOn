package com.talentPool.dashboard.manager;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LastViewedEntity;
import com.talentPool.user.manager.PermissionSet;

public class RecentViewManager {

	/**
	 * Method to add the last viewed entities.
	 * 
	 * @param entityId
	 *            The identifier of the entity.
	 * @param entityType
	 *            The type of the entity.
	 * @param userId
	 *            The identifier of the user.
	 */
	public void addLastViewedEntry(String entityId, String entityType, String userId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRecentViewManager_InsertLastViewed");
			dq.setString(1, entityId);
			dq.setString(2, entityType);
			dq.setString(3, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while inserting the last viewed entity", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Method to get the last viewed entities.
	 * 
	 * @return
	 */
	public List getLastViewedEntities(String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		List lastViewedEntities = null;
		try {
			String[] dynParam = new String[2];	
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " position_code ";
			} else {
				dynParam[0] = " position_title ";
			}
			dynParam[1] = "";
			if(!permissionSet.isPERMISSION_POSITION_DETAILS()){
				dynParam[1] = " AND entity_type = ? ";
				dynamicContent.add(UserConstants.ENTITY_CANDIDATE);
			}
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[1] += " AND (entity_type=? OR (entity_type=? AND entity_id in (SELECT applicant_id FROM tp_applicants WHERE is_confidential = ?))) ";
				dynamicContent.add(UserConstants.ENTITY_POSITION);
				dynamicContent.add(UserConstants.ENTITY_CANDIDATE);
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dRecentViewManager_GetLastViewedEntities",dynParam);
			dq.setString(1, UserConstants.ENTITY_CANDIDATE);
			dq.setString(2, userId);
			int cnt = 3;
			for (int k = 0; k < dynamicContent.size(); k++) {
				dq.setString(cnt++, dynamicContent.get(k));
			}
			lastViewedEntities = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return lastViewedEntities;
	}
	
	/**
	 * A Method which returns all recently viewed Positions
	 * @param userId
	 * @param permissionSet
	 * @return lastViewdPostions
	 */
	public List<LastViewedEntity> getLastViewedPositions(String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		List<LastViewedEntity> lastViewedPositions = null;
		try {
			String[] dynParam = new String[1];	
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " position_code ";
			} else {
				dynParam[0] = " position_title ";
			}
			dq = new DBPreparedQuery("dRecentViewManager_GetLastViewedPositions",dynParam);
			dq.setString(1, userId);
			dq.setString(2, UserConstants.ENTITY_POSITION);
			lastViewedPositions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return lastViewedPositions;
	}
}
