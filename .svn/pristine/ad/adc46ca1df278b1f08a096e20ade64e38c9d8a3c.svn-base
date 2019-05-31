/**
 * 
 */
package com.talentPool.inbox.manager;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.inbox.dataobject.CSVImportSessionList;
import com.talentPool.inbox.dataobject.CSVSessionEvent;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shantanu
 *
 */
public class CSVImporter implements Runnable {
	
	private String sessionId;
	private String mappings;
	private String userId;
	private PermissionSet permissionSet;

	public CSVImporter(String mappings, String sessionId, String userId, PermissionSet permissionSet) {
		super();
		this.sessionId = sessionId;
		this.mappings = mappings;
		this.userId = userId;
		this.permissionSet=permissionSet;
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		try {if (CSVImportSessionList.sessions.get(sessionId) != null) {
			TPLogger.getLogger().debug("SESSION ALREADY IN PROCESS");
			return;
		}			
			CSVImportSessionList.sessions.put(sessionId, new CSVSessionEvent(false,sessionId));			
			ReadApplicantsFromCSV readApplicantsFromCSV = new ReadApplicantsFromCSV();
			readApplicantsFromCSV.startExcelImport(mappings, sessionId, userId, permissionSet);
						
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			CSVImportSessionList.sessions.remove(sessionId);
		}
	}	

	

}
