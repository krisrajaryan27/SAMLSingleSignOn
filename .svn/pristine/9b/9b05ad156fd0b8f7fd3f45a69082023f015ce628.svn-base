/**
 * 
 */
package com.talentPool.inbox.scheduler;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.inbox.dataobject.CSVImportSessionList;
import com.talentPool.inbox.dataobject.CSVSessionEvent;
import com.talentPool.inbox.manager.ReadApplicantsFromCSV;


/**
 * @author Shantanu
 *
 */
public class CSVImportSessionProcessor implements Runnable{
	
	private String sessionId;
	private String filePath;
	private String mappings;
	private String userId;
	

	/**
	 * 
	 */
	public CSVImportSessionProcessor(String sessionId, String filePath, String mappings,  String userId) { 
		super();
		this.sessionId=sessionId;
		this.filePath=filePath;
		this.mappings=mappings;
		this.userId=userId;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		try{
			if (CSVImportSessionList.sessions.get(sessionId) != null) {
				TPLogger.getLogger().debug("SESSION ALREADY IN PROCESS");
				return;
			}			
			CSVImportSessionList.sessions.put(sessionId, new CSVSessionEvent(false,sessionId));
			ReadApplicantsFromCSV readApplicantsFromCSV = new ReadApplicantsFromCSV();
			readApplicantsFromCSV.validateExcelImportData(sessionId, filePath, mappings, userId);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			CSVImportSessionList.sessions.remove(sessionId);
		}
	}

}
