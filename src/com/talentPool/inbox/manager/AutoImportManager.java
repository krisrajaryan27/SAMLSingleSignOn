package com.talentPool.inbox.manager;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;

/**
 * @author shivprasad
 * 
 * methods for auto import process goes here
 */
public class AutoImportManager {
	public ArrayList getEmailsToAutoImport(int autoImportFormat, boolean autoimportTried) {
		ArrayList emails = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAutoImportManager_FetchEmails");
			dq.setInt(1, autoImportFormat);
			dq.setBoolean(2, autoimportTried);
			emails = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching All Emails For inboxId and folder ID", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return emails;
	}

	public void updateErrorCode(String emailId, String errorCodes) {
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAutoImportManager_UpdateErrorCodes");
			dq.setBoolean(1, true);
			dq.setString(2, errorCodes);
			dq.setId(3, emailId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in updating errorcodes in inbox", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public void updateAutoImportTried(String emailId,String tried){
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dAutoImportManager_UpdateImportTried");
			dq.setString(1, tried);
			dq.setId(2, emailId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in updating errorcodes in inbox", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}


}
