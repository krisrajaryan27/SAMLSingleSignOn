/**
 * 
 */
package com.talentPool.applicant.manager;

import com.talentPool.admin.AdminConstants;
import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;

/**
 * @author shivprasad
 * 
 */
public class SourceLockinCheckerJobManager {
	public void changeLockinExpiredSources() {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dLockinManager_ChangeSources");
			dq.setString(1, AdminConstants.SYSTEM_GENERATED);
			dq.setString(2, AdminConstants.SYSTEM_GENERATED);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while resetting locing expired sources job", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
}
