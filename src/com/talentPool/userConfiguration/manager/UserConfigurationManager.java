/**
 * 
 */
package com.talentPool.userConfiguration.manager;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.userConfiguration.dataobject.UserConfData;

/**
 * @author Ajeet
 *
 */
public class UserConfigurationManager {

	public void addUserConfigurations(String userId, String confId, String value) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserConfigurationManager_AddUserConfigurations");
			dq.setString(1, confId);
			dq.setString(2, userId);
			dq.setString(3, value);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void deleteUserConfigurations(String userId, String confId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dUserConfigurationManager_DeleteUserConfigurations");
			dq.setString(1, confId);
			dq.setString(2, userId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	public void updateUserConfigurations(String userId, String confId, String value) {
		try {
			deleteUserConfigurations(userId, confId);
			addUserConfigurations(userId, confId, value);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}		
	}

	public String getUserConfigurations(String userId, String confId) {
		DBPreparedQuery dq = null;
		String values = "";
		try {
			dq = new DBPreparedQuery("dUserConfigurationManager_GetUserConfigurations");
			dq.setString(1, confId);
			dq.setString(2, userId);
			values = dq.getIdResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return values;
	}

	public ArrayList<UserConfData> getAllUserConfigurations() {
		DBPreparedQuery dq = null;
		ArrayList<UserConfData> values = new ArrayList<UserConfData>();
		try {
			dq = new DBPreparedQuery("dUserConfigurationManager_GetAllUserConfigurations");
			values = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR,e);
		}finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return values;
	}
}
