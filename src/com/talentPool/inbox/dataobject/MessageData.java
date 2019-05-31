/**
 * 
 */
package com.talentPool.inbox.dataobject;

import java.sql.Date;
import java.util.ArrayList;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;

/**
 * @author shivprasad
 * 
 */
public class MessageData extends SimpleDataObject {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public MessageData() {

	}

	/**
	 * @return the messageId
	 * 
	 * In case this is email use this for retriving emailId
	 */
	public String getMessageId() {
		return getId("messageId");
	}

	public void setMessageId(String messageId) {
		setAttribute("messageId", messageId);
	}

	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return getString("from");
	}

	/**
	 * @param from
	 *            The from to set.
	 */
	public void setFrom(String from) {
		setAttribute("from", from);
	}
	
	public String getFromAddress() {
		return getString("fromAddress");
	}

	/**
	 * @param from
	 *            The from to set.
	 */
	public void setFromAddress(String fromAddress) {
		setAttribute("fromAddress", fromAddress);
	}
	
	/**
	 * @return Returns the sendDate.
	 */
	public Date getSendDate() {
		try {
			return getDate("sendDate");
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error while getting sql senddate: returning null", e);
		}
		return null;
	}
	
	/**
	 * @return Returns the sendDate.
	 */
	public String getSendDateToDisplay() {
		return DateUtils.getSystemDateTimeFormat(getSendDate());
	}

	/**
	 * @param sendDate
	 *            The sendDate to set.
	 */
	public void setSendDate(Date sendDate) {
		setAttribute("sendDate", sendDate);
	}

	public Date getReceivedDate() {
		try {
			return getDate("receivedDate");
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error while getting sql receiveddate: returning null", e);
		}
		return null;
	}

	/**
	 * @param sendDate
	 *            The sendDate to set.
	 */
	public void setReceivedDate(Date receivedDate) {
		setAttribute("receivedDate", receivedDate);
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return getString("subject");
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject(String subject) {
		setAttribute("subject", subject);
	}

	/**
	 * @return Returns the to.
	 */
	public String getTo() {
		return getString("to");
	}

	/**
	 * @param to
	 *            The to to set.
	 */
	public void setTo(String to) {
		setAttribute("to", to);
	}

	/**
	 * @return Returns the cc.
	 */
	public String getCc() {
		return getString("cc");
	}

	/**
	 * @param cc
	 *            The to cc set.
	 */
	public void setCc(String cc) {
		setAttribute("cc", cc);
	}

	/**
	 * @return Returns the cc.
	 */
	public String getBcc() {
		return getString("bcc");
	}

	/**
	 * @param bcc
	 *            The to bcc set.
	 */
	public void setBcc(String bcc) {
		setAttribute("bcc", bcc);
	}

	/**
	 * @return Returns the xMailer.
	 */
	public String getXMailer() {
		return getString("xMailer");
	}

	/**
	 * @param mailer
	 *            The xMailer to set.
	 */
	public void setXMailer(String xMailer) {
		setAttribute("xMailer", xMailer);
	}

	/**
	 * @return Returns the htmlBody.
	 */
	public String getHtmlBody() {
		return getString("htmlBody");
	}

	/**
	 * @param htmlBody
	 *            The htmlBody to set.
	 */
	public void setHtmlBody(String htmlBody) {
		setAttribute("htmlBody", htmlBody);
	}

	/**
	 * @return Returns the textBody.
	 */
	public String getTextBody() {
		return getString("textBody");
	}

	/**
	 * @param textBody
	 *            The textBody to set.
	 */
	public void setTextBody(String textBody) {
		setAttribute("textBody", textBody);
	}

	/**
	 * @return Returns the attachments.
	 */
	public ArrayList getAttachments() {
		return (ArrayList) getAttribute("attachments");
	}

	/**
	 * @param attachments
	 *            The attachments to set.
	 */
	public void setAttachments(ArrayList attachments) {
		setAttribute("attachments", attachments);
	}

	public int getFolderId() {
		return getInt("folderId");
	}

	public void setFolderId(int folderId) {
		setAttribute("folderId", new Integer(folderId));
	}

	public String getReadStatus() {
		return getString("readStatus");
	}

	public void setReadStatus(String readStatus) {
		setAttribute("readStatus", readStatus);
	}

	public int getNoOfAttachments() {
		return getInt("noOfAttachments");
	}

	public void setNoOfAttachments(int noOfAttachments) {
		setAttribute("noOfAttachments", new Integer(noOfAttachments));
	}

	public int getSize() {
		return getInt("size");
	}

	public void setSize(int size) {
		setAttribute("size", new Integer(size));
	}

	public String getApplicantId() {
		return getString("applicantId");
	}

	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	public void setOutboundId(String outboundId) {
		setAttribute("outboundId", outboundId);
	}

	public String getOutboundId() {
		return getString("outboundId");
	}

	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	public String getUserId() {
		return getString("userId");
	}

	public int getAutoImportFormat() {
		return getInt("autoImportFormat");
	}

	public void setAutoImportFormat(int autoImportFormat) {
		setAttribute("autoImportFormat", "" + autoImportFormat);
	}

	public boolean getAutoImportTried() {
		return getBoolean("autoImportTried");
	}

	public void setAutoImportTried(boolean autoImportTried) {
		setAttribute("autoImportTried", Boolean.valueOf(autoImportTried));
	}

	public void setAutoImportErrors(String autoImportErrors) {
		setAttribute("autoImportErrors", autoImportErrors);
	}

	public String getAutoImportErrors() {
		return getString("autoImportErrors");
	}

	public void setEmailLocation(String emailLocation) {
		setAttribute("emailLocation", emailLocation);
	}

	public String getEmailLocation() {
		return getString("emailLocation");
	}
	
	/**
	 * @return the replyTo
	 */
	public String getReplyTo() {
		return getString("replyTo");
	}

	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(String replyTo) {
		setAttribute("replyTo", replyTo);
	}
	
	
	public void setEntryId(String entryId) {
		setAttribute("entryId", entryId);
	}

	public String getEntryId() {
		return getString("entryId");
	}

	public void setSessionId(String sessionId) {
		setAttribute("sessionId", sessionId);
	}

	public String getSessionId() {
		return getString("sessionId");
	}
	
	public void setImportStatus(String importStatus) {
		setAttribute("importStatus", importStatus);
	}

	public String getImportStatus() {
		return getString("importStatus");
	}
	
	public String getFolderType() {
		return getString("folderType");
	}

	public void setFolderType(String folderType) {
		setAttribute("folderType", folderType);
	}

	public String getPositionId() {
		return getString("positionId");
	}

	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	public String getStepId() {
		return getString("stepId");
	}

	public void setStepId(String stepId) {
		setAttribute("stepId", stepId);
	}

	/**
	 * comment for browser import session 
	 * @return
	 */
	public String getCommentUrl() {
		return getString("commentUrl");
	}

	public void setCommentUrl(String commentUrl) {
		setAttribute("commentUrl", commentUrl);
	}
}