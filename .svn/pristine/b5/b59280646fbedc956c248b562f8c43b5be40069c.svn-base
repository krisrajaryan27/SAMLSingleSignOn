/**
 * 
 */
package com.talentPool.desktop.scheduler;

import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.desktop.constants.DesktopConstants;
import com.talentPool.desktop.dataobjects.BulkImportSessionData;
import com.talentPool.desktop.dataobjects.SessionEvent;
import com.talentPool.desktop.dataobjects.SessionList;
import com.talentPool.desktop.manager.BulkImportManager;
import com.talentPool.desktop.utils.BulkImportSessionUtils;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.inbox.dataobject.MessageData;


/**
 * @author shivprasad
 * 
 */
public class BulkImportSessionProcessor implements Runnable {
	private String sessionId;
	BulkImportManager bulkImportManager;
	private BulkImportSessionUtils bulkImportSessionUtils;

	public BulkImportSessionProcessor(String sessionId) {
		super();
		this.sessionId = sessionId;
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		try {
			// add session id to queue
			if (SessionList.sessions.get(sessionId) != null) {
				TPLogger.getLogger().debug("SESSION ALREADY IN PROCESS");
				return;
			}
			// get session data
			bulkImportSessionUtils = new BulkImportSessionUtils();
			bulkImportManager = new BulkImportManager();

			BulkImportSessionData bulkImportSessionData = bulkImportManager.getBulkImportSessionData(sessionId);

			if (bulkImportSessionData != null) {
				SessionList.sessions.put(sessionId, new SessionEvent(sessionId));
				if (bulkImportSessionData.getSessionType().equals(DesktopConstants.SESSION_TYPE_EMAIL_IMPORT) ||
						bulkImportSessionData.getSessionType().equals(DesktopConstants.SESSION_TYPE_BROWSER_IMPORT)) {
					processEmailsBulkImportSession(bulkImportSessionData);
				} else {
					processDocumentsBulkImportSession(bulkImportSessionData);
				}
				bulkImportManager.updateSessionImportStatus(sessionId, DesktopConstants.SESSION_STATUS_COMPLETED);
			}

		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		} finally {
			SessionList.sessions.remove(sessionId);
		}
	}

	private void processEmailsBulkImportSession(BulkImportSessionData bulkImportSessionData) {
		try {
			boolean batchAvailable = true;
			while (batchAvailable) {
				// fetch emails or documents and process import
				ArrayList<MessageData> emails = bulkImportManager.getEmailsToBulkImport(sessionId);
				if (emails == null || emails.size() == 0) {
					batchAvailable = false;
				} else {					
					for (int i = 0; emails != null && i < emails.size(); i++) {
						MessageData email = emails.get(i);
						bulkImportSessionUtils.processSingleEmail(email, bulkImportSessionData);
						bulkImportManager.updateEmailImportStatus(email.getMessageId(), DesktopConstants.IMPORT_STATUS_PARSING_DONE);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	private void processDocumentsBulkImportSession(BulkImportSessionData bulkImportSessionData) {
		try {
			// fetch emails or documents and process import
			boolean batchAvailable = true;
			while (batchAvailable) {
				ArrayList<DocumentData> documents = bulkImportManager.getDocumentsToBulkImport(sessionId);
				if (documents == null || documents.size() == 0) {
					batchAvailable = false;
				} else {
					for (int i = 0; i < documents.size(); i++) {
						DocumentData documentData = documents.get(i);
						if (bulkImportSessionUtils.parseSingleDocument(documentData.getRelativeFilePath(), bulkImportSessionData, null, documentData.getDocumentId())) {

						}
						bulkImportManager.updateDocumentImportStatus(documentData.getDocumentId(), DesktopConstants.IMPORT_STATUS_PARSING_DONE);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}
	
}
