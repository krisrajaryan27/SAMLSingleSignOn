/**
 * 
 */
package com.talentPool.masters.manager;

import java.util.ArrayList;

import com.talentPool.admin.AdminConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.masters.dataobject.RecentlyUsedSourceData;

/**
 * @author shivprasad
 * 
 */
public class RecentlyUsedSourcesManager {
	public ArrayList<RecentlyUsedSourceData> getRecentlyUsedSourcesFromDB(int noOfSources) {
		ArrayList<RecentlyUsedSourceData> recentlyUsed = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRecentlyUsedSource_GetRecentlyUsedSources");
			dq.setString(1, AdminConstants.SOURCE_CATEGORY_EMPLOYEE_REFERAL);
			dq.setString(2, AdminConstants.SOURCE_NOT_BLACKLISTED);
			dq.setInt(3, noOfSources);
			recentlyUsed = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return recentlyUsed;
	}
	
	public void insertOrUpdateRecentlyUsedSource(String sourceId) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dRecentlyUsedSource_InsertOrUpdate");
			dq.setId(1, sourceId);
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
