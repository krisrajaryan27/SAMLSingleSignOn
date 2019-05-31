/**
 * 
 */
package com.talentPool.positions.manager;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author Ajeet
 *
 */
public class PositionPublishManager {

	public String getAnnouncementsToPublish(String employeeAnnouncements) {
		String announcements = null;
		DBPreparedQuery dq = null;
		try {

			dq = new DBPreparedQuery("dPositionPublishManager_GetAnnouncementsToPublish");
			dq.setString(1, employeeAnnouncements);
			announcements = dq.getIdResult();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return announcements;
	}

	public void updateAnnouncements(String portal, String announcements, String userId) {
		DBPreparedQuery dq = null;
		try {

			dq = new DBPreparedQuery("dPositionPublishManager_UpdateAnnouncements");
			dq.setString(1, announcements);
			dq.setString(2, userId);
			dq.setString(3, portal);
			dq.execute();
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}		
	}
}
