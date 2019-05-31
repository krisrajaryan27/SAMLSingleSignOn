/**
 * 
 */
package com.talentPool.inbox.manager;

import java.io.File;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.db.Exception.NoResultFoundException;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.FileHandler;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.Exception.FileUploadException;
import com.talentPool.common.utils.Exception.InvalidMimeTypeException;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.dataobject.DocumentData;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.exception.SendMailException;
import com.talentPool.inbox.scheduler.DeleteFilesThread;
import com.talentPool.latestActivity.manager.LatestActivityManager;
import com.talentPool.masters.constants.MastersConstants;
import com.talentPool.masters.dataobject.InboxFolderData;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.positions.PositionConstants;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author shivprasad
 * 
 */
@Component
public class InboxManager {

	/**
	 * Loads the inbox settings in the HashMap table
	 */
	public HashMap loadInboxSettings() {
		HashMap inboxSettings = new HashMap();
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchInboxSettings");
			dq.setString(1, InboxConstants.INBOX_ACTIVE);
			ArrayList inboxList = dq.getResult();
			if (inboxList != null) {
				// HashMap inboxSettings = new HashMap();
				for (int i = 0; i < inboxList.size(); i++) {
					InboxData iData = (InboxData) inboxList.get(i);
					inboxSettings.put("" + iData.getInboxId(), iData);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching inbox settings", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return inboxSettings;
	}

	/**
	 * Assuming inbox settings have only one row this method returns the inbox data
	 * 
	 * @return
	 */
	public InboxData getCurrentInboxSettings() {
		InboxData iData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchSingleInboxData");
			dq.setString(1, InboxConstants.INBOX_ACTIVE);
			iData = (InboxData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching inbox settings", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return iData;
	}

	/**
	 * @param message
	 * 
	 * Store the message for perticular email address in to DB with attachments
	 * 
	 */
	public String saveMessage(MessageData msg, String emailFolder, String userId) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		String emailId = "";
		try {
			tran = new DBTransaction();
			String subject = (Utils.isBlankOrNull(msg.getSubject())) ? "" : msg.getSubject();
			String fromEmail = (Utils.isBlankOrNull(msg.getFrom())) ? "" : msg.getFrom();
			String fromEmailAddress = (Utils.isBlankOrNull(msg.getFromAddress())) ? "" : msg.getFromAddress();
			subject = (subject.length() > 250) ? subject.substring(0, 249) : subject;
			fromEmail = (fromEmail.length() > 100) ? fromEmail.substring(0, 99) : fromEmail;
			fromEmailAddress = (fromEmailAddress.length() > 100) ? fromEmailAddress.substring(0, 99) : fromEmailAddress;

			dq = new DBPreparedQuery("dInsertEmailInbox", tran);
			dq.setString(1, fromEmail);
			dq.setString(2, fromEmailAddress);
			dq.setString(3, msg.getTo());
			dq.setString(4, msg.getCc());
			dq.setString(5, msg.getBcc());
			dq.setString(6, subject);
			if (msg.getSendDate() == null) {
				dq.setTimestamp(7, null);
			} else {
				dq.setTimestamp(7, new Timestamp(msg.getSendDate().getTime()));
			}
			if (msg.getReceivedDate() == null) {
				dq.setTimestamp(8, null);
			} else {
				dq.setTimestamp(8, new Timestamp(msg.getReceivedDate().getTime()));
			}
			dq.setString(9, msg.getTextBody());
			dq.setString(10, msg.getHtmlBody());
			dq.setInt(11, msg.getSize());
			dq.setId(12, emailFolder);
			dq.setInt(13, msg.getAutoImportFormat());
			dq.setBoolean(14, msg.getAutoImportTried());
			dq.setId(15, userId);
			if(Utils.isBlankOrNull(msg.getPositionId())) {
				dq.setNull(16, Types.NULL);
			} else {
				dq.setString(16, msg.getPositionId());
			}
			if(Utils.isBlankOrNull(msg.getApplicantId())) {
				dq.setNull(17, Types.NULL);
			} else {
				dq.setString(17, msg.getApplicantId());
			}
			if(Utils.isBlankOrNull(msg.getStepId())) {
				dq.setNull(18, Types.NULL);
			} else {
				dq.setString(18, msg.getStepId());
			}
			dq.execute();
			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			emailId = dq.getIdResult();
			// Save the attachments
			ArrayList attachments = msg.getAttachments();
			if (attachments != null) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					String contentId = (Utils.isBlankOrNull(aData.getContentId())) ? "" : aData.getContentId();
					contentId = (contentId.length() > 150) ? contentId.substring(0, 149) : contentId;
					dq = new DBPreparedQuery("dInsertAttachmentInbox", tran);
					dq.setId(1, emailId);
					dq.setString(2, aData.getAttachmentFilePath());
					dq.setString(3, aData.getOriginalFileName());
					dq.setString(4, aData.getContentType());
					dq.setString(5, contentId);
					dq.setString(6, aData.getAttachmentType());
					dq.setLong(7, aData.getAttachmentSize());
					dq.execute();
				}
			}
			tran.commit();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			} 
		}
		return emailId;
	}

	public void saveCommunicationMessage(MessageData msg, String emailFolder, String userId, String emailIsImported) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dInsertEmailCommunication", tran);
			String mailFrom = msg.getFrom();
			if(mailFrom.length() > 99){
				mailFrom= mailFrom.substring(0,98);
			}
			dq.setString(1, mailFrom);
			dq.setString(2, msg.getTo());
			dq.setString(3, msg.getCc());
			dq.setString(4, msg.getBcc());
			dq.setString(5, msg.getSubject());
			if (msg.getSendDate() == null) {
				dq.setTimestamp(6, null);
			} else {
				dq.setTimestamp(6, new Timestamp(msg.getSendDate().getTime()));
			}
			if (msg.getReceivedDate() == null) {
				dq.setTimestamp(7, null);
			} else {
				dq.setTimestamp(7, new Timestamp(msg.getReceivedDate().getTime()));
			}
			dq.setString(8, msg.getTextBody());
			dq.setString(9, msg.getHtmlBody());
			dq.setInt(10, msg.getSize());
			dq.setId(11, emailFolder);
			dq.setId(12, userId);
			dq.setId(13, msg.getApplicantId());
			dq.setString(14, emailIsImported);
			dq.setString(15, msg.getEntryId());
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String emailId = dq.getIdResult();
			// Save the attachments
			ArrayList attachments = msg.getAttachments();
			if (attachments != null) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					dq = new DBPreparedQuery("dInsertAttachmentCommunication", tran);
					dq.setId(1, emailId);
					dq.setString(2, aData.getAttachmentFilePath());
					dq.setString(3, aData.getOriginalFileName());
					dq.setString(4, aData.getContentType());
					dq.setString(5, aData.getContentId());
					dq.setString(6, aData.getAttachmentType());
					dq.setLong(7, aData.getAttachmentSize());
					dq.execute();
				}
			}
			tran.commit();
			
			// add activity to user log
			String activity = "Recieve Email";
			int interactionType = SelectionProcessConstants.INTERACTION_EMAIL_RECEIVED ;
			if(emailFolder.equals(ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT)){
				activity = "Sent Email";
				interactionType = SelectionProcessConstants.INTERACTION_EMAIL_SENT ;
			}
			LatestActivityManager activityManager = new LatestActivityManager(); 
			String positionId = activityManager.getPositionIdWithApplicantId(msg.getApplicantId());
			activityManager.addUserActivity(activity, emailId, interactionType, positionId, msg.getApplicantId(), userId);
			
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, ex);
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			} 
		}

	}

	/**
	 * get the inbox list Might be later on we change this for user
	 */
	public ArrayList getActiveInboxForUser() {
		ArrayList inboxList = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchInbox");
			dq.setString(1, InboxConstants.INBOX_ACTIVE);
			inboxList = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching All inbox for user", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return inboxList;
	}

	public ArrayList getAllEmails(String folderId, String sortBy, String sortDir, boolean isDrafts, 
			PermissionSet permissionSet, String userId){
		return getAllEmails(folderId, sortBy, sortDir, isDrafts, permissionSet, userId, null, null);
	}

	public ArrayList getAllEmails(String folderId, String sortBy, String sortDir, boolean isDrafts, 
			PermissionSet permissionSet, String userId, String pageNo, String pageSize) {
		ArrayList emails = null;
		DBPreparedQuery dq = null;
		String[] dynParam = new String[4];
		ArrayList<String> dynamicContent = new ArrayList<String>(); 
		int page = 0;
		int size = 0;
		try{
			page = Integer.parseInt(pageNo);
			if (page<1){
				pageNo = null;
			}
		} catch (Exception e){
			pageNo = null;
		}
		try{
			size = Integer.parseInt(pageSize);
			if (size<1){
				size = InboxConstants.INBOX_PAGE_SIZE;
			}
		} catch (Exception e){
			size = InboxConstants.INBOX_PAGE_SIZE;
		}
		try {
			if(isDrafts && permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParam[0] = " AND tem.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps where su.position_step_id = ps.position_step_id and ps.position_step_status = ? and su.user_id = ? "
					+ " UNION SELECT position_id from tp_positions where position_requested_by = ? " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? ) ";
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);				
			} else {
				dynParam[0] = "";
			}				
			
			if (sortBy.equals(InboxConstants.SORT_BY_DATE)) {
				dynParam[1] = " tem.email_date_send ";
			} else if (sortBy.equals(InboxConstants.SORT_BY_FROM)) {
				dynParam[1] = " tem.email_from ";
			} else if (sortBy.equals(InboxConstants.SORT_BY_SUBJECT)) {
				dynParam[1] = " tem.email_subject ";
			} else if (sortBy.equals(InboxConstants.SORT_BY_ATTACHMENT)) {
				dynParam[1] = " noOfAttachments ";
			}
			dynParam[1] = dynParam[1] + " " + sortDir;
			dynParam[2] = sortDir;
			if (!Utils.isBlankOrNull(pageNo)){
				dynParam[3] = "limit " + (page -1)*size + "," + size;
			} else {
				dynParam[3] = "";
			}
			dq = new DBPreparedQuery("dFetchEmailsForEmailFolder", dynParam);
			dq.setString(1, InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
			dq.setId(2, folderId);
			dq.setInt(3, InboxConstants.FORMAT_NOAUTO_IMPORT);
			dq.setBoolean(4, true);
			int cnt = 5;
			for(int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
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

	public ArrayList getSentEmails(String sortBy, String sortDir) {
		return getSentEmails(sortBy, sortDir, null, null);
	}

	public ArrayList getSentEmails(String sortBy, String sortDir, String pageNo, String pageSize) {
		ArrayList emails = null;
		DBPreparedQuery dq = null;
		int page = 0;
		int size = 0;
		try{
			page = Integer.parseInt(pageNo);
			if (page<1){
				pageNo = null;
			}
		} catch (Exception e){
			pageNo = null;
		}
		try{
			size = Integer.parseInt(pageSize);
			if (page<1){
				size = InboxConstants.INBOX_PAGE_SIZE;
			}
		} catch (Exception e){
			size = InboxConstants.INBOX_PAGE_SIZE;
		}
		try {
			String[] dynParam = new String[2];
			if (sortBy.equals(InboxConstants.SORT_BY_DATE)) {
				dynParam[0] = " email_date_send ";
			} else if (sortBy.equals(InboxConstants.SORT_BY_FROM)) {
				dynParam[0] = " email_from ";
			} else if (sortBy.equals(InboxConstants.SORT_BY_SUBJECT)) {
				dynParam[0] = " email_subject ";
			} else if (sortBy.equals(InboxConstants.SORT_BY_ATTACHMENT)) {
				dynParam[0] = " attCnt ";
			}
			dynParam[0] = dynParam[0] + " " + sortDir;
			
			if (!Utils.isBlankOrNull(pageNo)){
				dynParam[1] = "limit " + (page -1)*size + "," + size;
			} else {
				dynParam[1] = "";
			}

			dq = new DBPreparedQuery("dFetchEmailsForEmailSentFolder", dynParam);
			dq.setString(1, InboxConstants.EMAIL_LOCATION_COMMUNICATIONS);
			dq.setString(2, ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT);
			dq.setString(3, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS));
			dq.setString(4, InboxConstants.EMAIL_LOCATION_INBOX);
			dq.setString(5, InboxConstants.INBOX_FOLDER_SENT);
			dq.setString(6, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS));
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

	public String getXMLForEmails(ArrayList emails) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();
			wr.startElement("rows");
			if (emails != null) {
				for (int i = 0; i < emails.size(); i++) {
					MessageData mData = (MessageData) emails.get(i);
					String date = "";

					if (mData.getSendDate() != null) {
						date = DateUtils.getSystemDateTimeFormat(mData.getSendDate());
						date = Utils.isBlankOrNull(date)?"UNKNOWN":date;
					}

					String subject = "";
					if (mData.getSubject() != null) {
						subject = (mData.getSubject().length() > 35) ? mData.getSubject().substring(0, 35) : mData.getSubject();
					}
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", mData.getMessageId());
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "applicantId");
					wr.startElement("", "userdata", "", at);
					wr.characters(mData.getApplicantId());
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("" + mData.getNoOfAttachments());
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(mData.getApplicantId());
					wr.endElement("cell");

					wr.startElement("cell");
					String from = mData.getFrom();
					wr.characters(wr.doubleEscape(from));
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(subject));
					wr.endElement("cell");

					wr.startElement("cell");
					/*if(MastersConstants.DRAFT.equals(mData.getFolderType())) {
						wr.characters(InboxConstants.NONE);
					} else {
						wr.characters(date);
					}	*/
					wr.characters(date);
					wr.endElement("cell");

					int emailStatus = 0;
					if (!"0".equals(mData.getReadStatus())) {
						emailStatus += 1;
					}

					wr.startElement("cell");
					wr.characters("" + emailStatus);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	public String getXMLForSentEmails(ArrayList emails) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			wr.startDocument();

			if (emails == null || (emails != null && emails.size() == 0)) {
				AttributesImpl at = new AttributesImpl();
				at.addAttribute("", "total_rows", "", "", "0");
				wr.startElement("", "rows", "", at);
			} else {
				wr.startElement("rows");
			}
			if (emails != null) {
				for (int i = 0; i < emails.size(); i++) {
					MessageData mData = (MessageData) emails.get(i);
					String date = "";

					if (mData.getSendDate() != null) {
						date = DateUtils.getSystemDateTimeFormat(mData.getSendDate());
						date = Utils.isBlankOrNull(date)?"UNKNOWN":date;
					}

					String subject = "";
					if (mData.getSubject() != null) {
						subject = (mData.getSubject().length() > 35) ? mData.getSubject().substring(0, 35) : mData.getSubject();
					}
					// 18-Aug, Wed<br>12:20 PM
					AttributesImpl at = new AttributesImpl();
					at.addAttribute("", "id", "", "", mData.getMessageId());
					wr.startElement("", "row", "", at);

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "mId");
					wr.startElement("", "userdata", "", at);
					wr.characters(mData.getMessageId());
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "eLoc");
					wr.startElement("", "userdata", "", at);
					wr.characters(mData.getEmailLocation());
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "uId");
					wr.startElement("", "userdata", "", at);
					wr.characters(mData.getEmailLocation() + mData.getMessageId());
					wr.endElement("userdata");

					at = new AttributesImpl();
					at.addAttribute("", "name", "", "", "applicantId");
					wr.startElement("", "userdata", "", at);
					wr.characters(mData.getApplicantId());
					wr.endElement("userdata");

					wr.startElement("cell");
					wr.characters("" + mData.getNoOfAttachments());
					wr.endElement("cell");

					wr.startElement("cell");
					wr.characters(mData.getApplicantId());
					wr.endElement("cell");

					wr.startElement("cell");
					String to = mData.getTo();
					if (to != null && to.indexOf("<") > 0) {
						to = to.substring(0, to.indexOf("<"));
					}
					wr.characters(wr.doubleEscape(to));
					wr.endElement("cell");
					
					wr.startElement("cell");
					wr.characters(wr.doubleEscape(subject));
					wr.endElement("cell");

					/*
					 * wr.startElement("cell"); wr.characters(mData.getTo()); wr.endElement("cell");
					 */
					/*
					 * wr.startElement("cell"); wr.characters(mData.getSubject()); wr.endElement("cell");
					 */

					wr.startElement("cell");
					wr.characters(date);
					wr.endElement("cell");

					int emailStatus = 0;
					if (!"0".equals(mData.getReadStatus())) {
						emailStatus += 1;
					}

					wr.startElement("cell");
					wr.characters("" + emailStatus);
					wr.endElement("cell");

					wr.endElement("row");
				}
			}
			wr.endElement("rows");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for emails", e);
		}
		return sWr.getBuffer().toString();
	}

	public MessageData getEmailBody(String emailId, String emailLocation) {
		MessageData messageData = null;
		DBPreparedQuery dq = null;
		try {
			if (emailLocation.equals(InboxConstants.EMAIL_LOCATION_INBOX)) {
				dq = new DBPreparedQuery("dGetEmailBody");
			} else {
				dq = new DBPreparedQuery("dGetCommunicationEmailBody");
			}
			dq.setId(1, emailId);
			messageData = (MessageData) dq.getSingleObjectResult();

			if (messageData != null && !Utils.isBlankOrNull(messageData.getHtmlBody())) {
				ArrayList attachments = getAttachmentsOfType(emailId, emailLocation, InboxConstants.ATTACHMENT_TYPE_RELATED);
				if (attachments != null) {
					for (int i = 0; i < attachments.size(); i++) {
						AttachmentData aData = (AttachmentData) attachments.get(i);
						if (!Utils.isBlankOrNull(aData.getContentId())) {
							String htmlBody = messageData.getHtmlBody();
							String docsPath = DocumentUtils.getDocumentURL(aData.getAttachmentFilePath(), DocumentConstants.CONTENT_DISPOSITION_INLINE);
							messageData.setHtmlBody(htmlBody.replace("cid:" + aData.getContentId(), docsPath));

						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error While getting message body", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return messageData;
	}

	public void changeEmailReadStatus(String emailId, String userId, String readStatus) {
		DBPreparedQuery dq = null;
		try {
			if (readStatus.equals(InboxConstants.INBOX_EMAIL_STATUS_READ)) {
				dq = new DBPreparedQuery("dGetEmailReadCount");
				dq.setId(1, emailId);
				dq.setId(2, userId);
				int readCount = dq.getIntResult();
				if (readCount == 0) {
					dq = new DBPreparedQuery("dInsertEmailRead");
					dq.setId(1, emailId);
					dq.setId(2, userId);
					dq.execute();
				}
			} else {
				String[] dynParam = new String[1];
				dynParam[0] = emailId;
				dq = new DBPreparedQuery("dRemoveEmailRead", dynParam);
				dq.setId(1, userId);
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while changing email status", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * Starts separate thread to delete all attachments and delete attachment record from DB
	 * 
	 * @param emailId
	 * @throws Exception
	 */
	public void deleteInboxEmail(String emailId, boolean deleteFiles, DBTransaction tran) throws Exception {
		DBPreparedQuery dq = null;
		boolean commit = false;
		if (tran == null) {
			tran = new DBTransaction();
			commit = true;
		}
		try {
			if (deleteFiles) {
				ArrayList attachments = getAllAttachments(emailId);
				deleteEmailFiles(attachments);
			}
			String[] dynParam = new String[1];
			dynParam[0] = " (" + emailId + ")";
			TPLogger.getLogger().debug(dynParam[0]);
			dq = new DBPreparedQuery("dDeleteEmailAttachments", dynParam, tran);
			dq.execute();
			dq = new DBPreparedQuery("dDeleteEmailReadHistory", dynParam, tran);
			dq.execute();
			dq = new DBPreparedQuery("dDeleteEmail", dynParam, tran);
			dq.execute();
			if (commit) {
				tran.commit();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting email message", e);
			if (commit) {
				tran.rollback();
			}
			throw e;
		} finally {
			if (dq != null) {
				if (commit) {
					dq.releaseTransaction(tran);
				}else {
					dq.closeOpenCursors();
				}
			}
		}
	}

	/**
	 * starts new thread to delete email files
	 * 
	 * @param emailId
	 */
	public void deleteEmailFiles(ArrayList attachments) {
		try {
			ArrayList<String> files = new ArrayList<String>();
			if (attachments != null && attachments.size() > 0) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					files.add(aData.getAttachmentFilePath());
				}
			}
			// start thread to delete inbox files
			if (files.size() > 0) {
				DeleteFilesThread t = new DeleteFilesThread(files, true);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting email message", e);
		}
	}

	public MessageData getEmailHeader(String emailId, String emailLocation, boolean includeAttachments) {
		MessageData messageData = null;
		DBPreparedQuery dq = null;
		try {
			if (emailLocation.equals(InboxConstants.EMAIL_LOCATION_INBOX)) {
				dq = new DBPreparedQuery("dGetEmailHeader");
			} else {
				dq = new DBPreparedQuery("dGetCommunicationEmailHeader");
			}
			dq.setId(1, emailId);
			messageData = (MessageData) dq.getSingleObjectResult();

			// Take the attachments for this email
			if (messageData != null && includeAttachments) {
				ArrayList attachments = getAttachmentsOfType(emailId, emailLocation, InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
				messageData.setAttachments(attachments);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting email header", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return messageData;
	}

	public ArrayList getAllAttachments(String emailId) {
		ArrayList attachments = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = "(" + emailId + ")";
			dq = new DBPreparedQuery("dGetEmailAtachments", dynParam);
			attachments = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching All Attachments for single email", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return attachments;
	}

	public ArrayList getAttachmentsOfType(String emailId, String emailLocation, String attachmentType) {
		ArrayList attachments = null;
		DBPreparedQuery dq = null;
		try {

			if (emailLocation.equals(InboxConstants.EMAIL_LOCATION_INBOX)) {
				dq = new DBPreparedQuery("dGetEmailAtachmentsOfType");
			} else {
				dq = new DBPreparedQuery("dGetCommunicationEmailAtachmentsOfType");
			}
			dq.setId(1, emailId);
			dq.setId(2, attachmentType);
			attachments = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while fetching All Attachments for single email", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return attachments;
	}

	public String getXMLForEmailHeader(MessageData messageData, String emailLocation) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			AttributesImpl atr = new AttributesImpl();
			atr.addAttribute("", "id", "", "", messageData.getMessageId());
			wr.startElement("", "email", "", atr);
			wr.dataElement("emailLocation", emailLocation);
			wr.dataElement("from", getProperty(messageData.getFrom(), ""));
			wr.dataElement("to", getProperty(messageData.getTo(), ""));
			wr.dataElement("cc", getProperty(messageData.getCc(), ""));
			wr.dataElement("bcc", getProperty(messageData.getBcc(), ""));
			wr.dataElement("subject", getProperty(messageData.getSubject(), ""));
			wr.dataElement("sendDate", getProperty(DateUtils.getSystemDateTimeFormat(messageData.getSendDate()), "UNKNOWN"));
			wr.dataElement("errorIds", getProperty(messageData.getAutoImportErrors(), ""));
			ArrayList attachments = messageData.getAttachments();
			if (attachments != null && attachments.size() > 0) {
				wr.startElement("attachments");
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", aData.getAttachmentId());
					wr.startElement("", "attachment", "", atr);
					wr.dataElement("fName", aData.getAttachmentFilePath());
					wr.dataElement("oName", aData.getOriginalFileName());
					wr.dataElement("contentType", getProperty(aData.getContentType(), ""));
					wr.endElement("attachment");
				}
				wr.endElement("attachments");
			}
			wr.endElement("email");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting XML content for email header", e);
		}
		return sWr.getBuffer().toString();
	}

	public String getAttachmentIdToParse(ArrayList attachments) {
		String attachmentId = "0";
		try {
			if (attachments != null && attachments.size() > 0) {
				AttachmentData aData = (AttachmentData) attachments.get(0);
				String fName = aData.getAttachmentFilePath();
				fName = fName.toLowerCase();
				if (fName.endsWith(".doc") || fName.endsWith(".txt") || fName.endsWith(".htm") || fName.endsWith(".html") || fName.endsWith(".pdf") || fName.endsWith(".odt") || fName.endsWith(".rtf")|| fName.endsWith(".docx")) {
					attachmentId = aData.getAttachmentId();
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while attachment id to auto process", e);
			attachmentId = "0";
		}
		return attachmentId;
	}

	public AttachmentData getAttachmentData(String attachmentId) {
		AttachmentData aData = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchAttachment");
			dq.setId(1, attachmentId);
			aData = (AttachmentData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting attachment Data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return aData;
	}

	private String getProperty(String tag, String defaultVal) {
		if (Utils.isBlankOrNull(tag)) {
			return defaultVal;
		}
		return tag;
	}

	/**
	 * copy email data from inbox to applicant inbox and also copies attachments to documents folder. The folder hirarchy is maintained
	 * 
	 * @param applicantId
	 * @param emailId
	 */
	public synchronized void attachEmailToApplicantRecord(String applicantId, String emailId, String userId) throws Exception {
		DBTransaction tran = null;
		DBPreparedQuery dq = null;
		try {

			// Copy email content.
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dInsertApplicantInboxEmail", tran);
			dq.setId(1, ApplicantConstants.APPLICANT_EMAIL_FOLDER_INBOX);
			dq.setId(2, userId);
			dq.setId(3, applicantId);
			dq.setString(4, ApplicantConstants.EMAIL_IMPORTED);
			dq.setId(5, emailId);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			String newEmailId = dq.getIdResult();

			dq = new DBPreparedQuery("dInsertApplicantInboxEmailAttachment", tran);
			dq.setId(1, newEmailId);
			dq.setId(2, emailId);
			dq.execute();

			// Delete original email from inbox but don't delete email files
			deleteInboxEmail(emailId, false, tran);

			tran.commit();
			// now delete the email
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while attaching email to applicant record", e);
			try {
				tran.rollback();
			} catch (Exception ex) {
			}
			throw e;
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			} 
		}
	}

	public String getPositionInfoXML(String requirements, String responsibilities) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("info");
			wr.startElement("requirements");
			wr.characters(requirements);
			wr.endElement("requirements");
			wr.startElement("responsibilities");
			wr.characters(responsibilities);
			wr.endElement("responsibilities");
			wr.endElement("info");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting XML content for email header", e);
		}
		return sWr.getBuffer().toString();
	}

	/**
	 * Copys Attachments data only to temporary table. This function is called when an email is replied or forwarded
	 * 
	 * @param emailId
	 * @param emailLocation
	 * @param tmpEmailId
	 */
	public void copyAttachmentsDataToTemp(String emailId, String emailLocation, String tmpEmailId) {
		DBPreparedQuery dq = null;
		try {
			if (emailLocation.equals(InboxConstants.EMAIL_LOCATION_INBOX)) {
				dq = new DBPreparedQuery("dCopyEmailAttachments");
			} else {
				dq = new DBPreparedQuery("dCopyCommunicationEmailAttachments");
			}
			dq.setString(1, tmpEmailId);
			dq.setString(2, InboxConstants.ATTACHMENT_CONFIRMED);
			dq.setId(3, emailId);
			dq.setString(4, InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while copying attachments to temp folder", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * get attachments list from tmp table for unique email ID
	 * 
	 * @param tmpEmailId
	 * @return
	 */
	public ArrayList getTmpAttachments(String tmpEmailId) {
		ArrayList attachments = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dGetTmpAttachments");
			dq.setString(1, tmpEmailId);
			dq.setString(2, InboxConstants.ATTACHMENT_CONFIRMED);
			attachments = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting attachments from tmperory table", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return attachments;
	}

	/**
	 * Upload attachment and makes entry in DB as non confirmed attachment
	 * 
	 * @param formFile
	 * @param tmpEmailId
	 * @param emailLocation
	 * @return
	 * @throws FileUploadException
	 * @throws InvalidMimeTypeException 
	 */
	public AttachmentData uploadAttachment(FormFileData formFileData, String tmpEmailId, 
			String emailLocation) throws FileUploadException, InvalidMimeTypeException {
		AttachmentData aData = null;
		DocumentData documentData = null;
		try{
			// First upload the attachment
			DocumentUploader documentUploader = new DocumentUploader();
			documentData = documentUploader.saveFormFile(formFileData);
			
			String attachmentId = getAbsoluteAttachmentPath(formFileData.getFileName(),formFileData.getContentType(),formFileData.getFileSize(), documentData.getRelativeFilePath(),tmpEmailId, emailLocation);
			
			aData = new AttachmentData();
			aData.setAttachmentId(attachmentId);
			aData.setOriginalFileName(formFileData.getFileName());
			aData.setAttachmentSize(formFileData.getFileSize());
			
		} catch (InvalidMimeTypeException e) {
			throw e;
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while uploading file to destination ", e);
			try {
				if (documentData != null) {
					File f = new File(documentData.getAbsoluteFilePath());
					if (f.exists()) {
						f.delete();
					}
				}
			} catch (Exception fe) {
				TPLogger.getLogger().debug("Unable to delete file", fe);
			}
			throw new FileUploadException("Unable to upload file");
		}
		return aData;
	}
	
	public String getAbsoluteAttachmentPath(String fileName, String contentType, long fileSize, String relativeFilePath, String tmpEmailId, String emailLocation) throws FileUploadException {
		DBTransaction tran = null;
		String attachmentId ="";
		DBPreparedQuery dq = null;
		try {
			// get absolute attachment path
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dInsertTmpAttachmentData", tran);
			dq.setString(1, tmpEmailId);
			dq.setString(2, relativeFilePath);
			dq.setString(3, fileName);
			dq.setString(4, contentType);
			dq.setString(5, InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
			dq.setLong(6, fileSize);
			dq.setString(7, InboxConstants.ATTACHMENT_NOT_CONFIRMED);
			dq.execute();

			dq = new DBPreparedQuery("dFetchLastInsertID", tran);
			attachmentId = dq.getIdResult();
			tran.commit();

		} catch (SQLException sqle) {
			TPLogger.getLogger().error("Error while updating DB", sqle);
			try {
				if (tran != null) {
					tran.rollback();
				}
			} catch (SQLException ex) {
			}
			throw new FileUploadException("Unable to upload file");
		
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			} 
		}
		return attachmentId;
	}

	/**
	 * Confirms attachments by setting its status
	 * 
	 * @param attachmentIds -
	 *            comma separated list of attachmentIds
	 */
	public void confirmTmpAttachments(String attachmentIds, String tmpEmailId, String confirmStatus) {
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynParam[0] = "";
			if (!Utils.isBlankOrNull(attachmentIds)) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(attachmentIds, dynamicContent);
				dynParam[0] += " AND attachment_id IN (" + qMarks + ") ";
			}

			dq = new DBPreparedQuery("dConfirmAttachments", dynParam);
			dq.setString(1, confirmStatus);
			dq.setString(2, tmpEmailId);
			int cnt = 3;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while confirming attachments", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * delete temporary attachments from DB
	 * 
	 * @param attachmentIds -
	 *            comma separated list of attachmentIds
	 */
	public void deleteTmpAttachments(String tmpEmailId, String attachmentIds, String confirmedStatus) {
		DBPreparedQuery dq = null;
		try {
			String[] dynParam = new String[1];
			dynParam[0] = " ";
			ArrayList<String> dynamicContent = new ArrayList<String>();
			if (!Utils.isBlankOrNull(attachmentIds)) {
				String qMarks = Utils.setDynamicParamsAndReturnQmarks(attachmentIds, dynamicContent);
				dynParam[0] = " AND attachment_id IN (" + qMarks + ") ";
			}
			if (!Utils.isBlankOrNull(confirmedStatus)) {
				dynParam[0] = " AND attachment_confirmed='" + confirmedStatus + "' ";
			}

			dq = new DBPreparedQuery("dDeleteTmpAttachments", dynParam);
			dq.setString(1, tmpEmailId);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.execute();

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting tmp attachments", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
	}

	/**
	 * returns size of temporary attachments
	 * 
	 * @param attachmentIds
	 */
	public long getTmpAttachmentSize(String tmpEmailId) {
		long size = 0;
		DBPreparedQuery dq = null;
		try {
			if (!Utils.isBlankOrNull(tmpEmailId)) {
				dq = new DBPreparedQuery("dGetSizeForTmpAttachments");
				dq.setString(1, tmpEmailId);
				size = dq.getIntResult();
			}
		} catch (NoResultFoundException nrf) {
			TPLogger.getLogger().debug("No result Found", nrf);
			size = 0;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting attachment size", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return size;
	}

	public void processAndSendEmail(MessageData messageData, String applicantId) throws SendMailException {
		try {
			// First Replace all the
			ArrayList attachments = messageData.getAttachments();
			// First replace the related images path with cid available
			if (attachments != null) {
				for (int i = 0; i < attachments.size(); i++) {
					AttachmentData aData = (AttachmentData) attachments.get(i);
					if (!Utils.isBlankOrNull(messageData.getHtmlBody()) && aData.getAttachmentType().equals(InboxConstants.ATTACHMENT_TYPE_RELATED) && !Utils.isBlankOrNull(aData.getContentId())) {
						String docsPath = DocumentUtils.getDocumentURL(aData.getAttachmentFilePath(), DocumentConstants.CONTENT_DISPOSITION_INLINE);
						messageData.setHtmlBody(messageData.getHtmlBody().replace("&amp;", "&").replace(docsPath, "cid:" + aData.getContentId()));
					}
				}
			}
			TPMailSender mailSender = new TPMailSender();
			if (!Utils.isBlankOrNull(messageData.getHtmlBody())) {
				mailSender.send(messageData, InboxConstants.EMAIL_BODY_HTML, DocumentConstants.documentsPath);
			} else {
				mailSender.send(messageData, InboxConstants.EMAIL_BODY_TEXT, DocumentConstants.documentsPath);
			}
			if(!Utils.isBlankOrNull(applicantId)) {
				TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
			}
		} catch (SendMailException sme) {
			throw sme;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while sending an email", e);
		}
	}

	/**
	 * search the candidate and return arraylist
	 * 
	 * @param searchName
	 * @return
	 */
	public ArrayList searchCandidate(String searchName, PermissionSet permissionSet) {
		ArrayList applicants = null;
		DBPreparedQuery dq = null;
		try {
			ArrayList<String> dynamicContent = new ArrayList<String>();
			String[] dynParam = new String[1];
			dynParam[0] = " AND (applicant_name like ? OR applicant_name like ? )";
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynParam[0] += " AND is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dGetSearchedApplicant", dynParam);
			
			dq.setString(1, ApplicantConstants.APPLICANT_NOT_JOINED);
			dq.setString(2, searchName+"%");
			dq.setString(3, "% " + searchName + "%");
			int cnt = 4;
			for (int k = 0; k < dynamicContent.size(); k++) {
				dq.setString(cnt++, dynamicContent.get(k));
			}
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	public String getApplicantXML(ArrayList applicants) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("applicants");
			if (applicants != null) {
				for (int i = 0; i < applicants.size(); i++) {
					wr.startElement("applicant");
					ApplicantData aData = (ApplicantData) applicants.get(i);
					wr.startElement("applicantId");
					wr.characters("" + aData.getApplicantId());
					wr.endElement("applicantId");
					wr.startElement("applicantName");
					wr.characters(aData.getApplicantName());
					wr.endElement("applicantName");
					wr.endElement("applicant");
				}
			}
			wr.endElement("applicants");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting XML content for email header", e);
		}
		return sWr.getBuffer().toString();
	}

	public int fetchEmailCountForEmailSentFolder() {
		int count = 0;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dFetchEmailCountForEmailSentFolder");
			dq.setString(1, ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT);
			dq.setString(2, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS));
			dq.setString(3, InboxConstants.INBOX_FOLDER_SENT);
			dq.setString(4, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS));
			SimpleDataObject sDo = (SimpleDataObject) dq.getSingleObjectResult();
			count = sDo.getInt("count");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting email count for the sent item folder", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return count;
	}

	public String getTemplateXml(String templateCode, String subject, String content) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("template");

			wr.startElement("templateCode");
			wr.characters(wr.doubleEscape(templateCode));
			wr.endElement("templateCode");
			wr.startElement("subject");
			wr.characters(wr.doubleEscape(subject));
			wr.endElement("subject");
			wr.startElement("content");
			wr.characters(wr.doubleEscape(content));
			wr.endElement("content");

			wr.endElement("template");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("error while creating xml file for template", e);
		}
		return sWr.getBuffer().toString();
	}
	
	public List<InboxFolderData> getInboxFolders(String systemDefined, PermissionSet permissionSet, String userId) {
		List<InboxFolderData> folders = null;
		DBPreparedQuery dq = null;
		String[] dynParam = new String[1];
		ArrayList<String> dynamicContent = new ArrayList<String>();
		try {
			if(MastersConstants.DRAFT.equals(systemDefined) && permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynParam[0] = " AND tie.position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps where su.position_step_id = ps.position_step_id and ps.position_step_status = ? and su.user_id = ? "
					+ " UNION SELECT position_id from tp_positions where position_requested_by = ? " + " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=? ) ";
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);				
			} else {
				dynParam[0] = "";
			}	
			dq = new DBPreparedQuery("dInboxManager_FetchInboxFolders", dynParam);
			dq.setString(1, InboxConstants.INBOX_FOLDER_SENT);
			dq.setString(2, ApplicantConstants.APPLICANT_EMAIL_FOLDER_SENT);
			dq.setString(3, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS));
			dq.setString(4, InboxConstants.INBOX_FOLDER_SENT);
			dq.setString(5, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_DURATION_TO_DISPLAY_SENT_EMAILS));
			int cnt = 6;
			for(int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			dq.setString(cnt++, systemDefined);
			folders = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("");
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return folders;
	}
	
	public void moveInboxEmail(String destinationFolderId, String emailIds) {
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = emailIds;
			dq = new DBPreparedQuery("dInboxManager_MoveEmail", dynParams);			
			dq.setString(1, destinationFolderId);
			dq.execute();
		} catch (SQLException e) {
			TPLogger.getLogger().error("Error while moving inbox email", e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
	}
	
	public int getFolderIdForSystemFolderDrafts() {
		int folderId = Integer.parseInt(InboxConstants.INBOX_FOLDER_INBOX);
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dInboxManager_GetFolderIdOfSystemFolderDrafts");
			dq.setString(1, InboxConstants.INBOX_FOLDER_TYPE_DRAFT);
			folderId = dq.getIntResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} catch (NoResultFoundException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return folderId;
	}	
	
	public void uploadIndentImportTemplate(String tmpEmailId){
				
		try {
			String documentFolderPath = new DocumentUploader().createTodayFolder(DocumentConstants.documentsPath);
			File documentFile = File.createTempFile("ATT", ".txt", new File(documentFolderPath));
			new FileHandler().copyFile(Utils.concatFilePath(TemplateConstants.VELOCITY_TEMPLATE_DIR, "indent_import.txt"), documentFile.getAbsolutePath());
					
			String attachmentId = getAbsoluteAttachmentPath(TPLabels.getLabel("attachment.label.positionTemplateName"),"text/plain",documentFile.length(), Utils.concatFilePath(documentFolderPath.replace(DocumentConstants.documentsPath+"/", ""),documentFile.getName()),tmpEmailId, "");
			confirmTmpAttachments(attachmentId, tmpEmailId, InboxConstants.ATTACHMENT_CONFIRMED);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}


	}
	
	public AttachmentData getTmpAttachment(String attachmentId) throws SQLException {
		AttachmentData data = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dInboxManager_TmpAttachment");
			dq.setString(1, attachmentId);
			data = (AttachmentData) dq.getSingleObjectResult();
		} finally {
			if(dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}
}