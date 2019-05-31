package com.talentPool.inbox.form;

import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.utils.Utils;

public class InboxForm extends TPActionForm {
	private static final long serialVersionUID = 1L;
	private String folderId;
	private String emailId;
	private String applicantId;
	private String emailLocation;

	private String selAttachment;
	private String updateResume;

	// variables for new email
	private String newEmailBody;
	private String from;
	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String includeOriginal ;
	private String positionId;
	private ArrayList positionIds;
	private ArrayList positionNames;
	private String newEmailType;
	private String tmpEmailId;
	private String[] attachmentId;
	private String tmpAttachmentId;
	// Attachment Screen fields
	private FormFile attachedFile;
	
	//
	private String searchApplicantName;
	
	private String sortBy;
	private String sortDir;
	
	private String templateCode;
	private String selectedIds;
	
	private String emailIds;
	
	//import Candidate from CSV
	private String filePath;
	private String sessionId;
	private String mappings;
	private String lastRowId;
	private String isSessionComplete;
	
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the mappings
	 */
	public String getMappings() {
		return mappings;
	}

	/**
	 * @param mappings the mappings to set
	 */
	public void setMappings(String mappings) {
		this.mappings = mappings;
	}

	/**
	 * @return the lastRowId
	 */
	public String getLastRowId() {
		return lastRowId;
	}

	/**
	 * @param lastRowId the lastRowId to set
	 */
	public void setLastRowId(String lastRowId) {
		this.lastRowId = lastRowId;
	}

	/**
	 * @return the isSessionComplete
	 */
	public String getIsSessionComplete() {
		return isSessionComplete;
	}

	/**
	 * @param isSessionComplete the isSessionComplete to set
	 */
	public void setIsSessionComplete(String isSessionComplete) {
		this.isSessionComplete = isSessionComplete;
	}

	private String messageReceivedType;
	/**
	 * @return Returns the searchApplicantName.
	 */
	public String getSearchApplicantName() {
		return searchApplicantName;
	}

	/**
	 * @param searchApplicantName The searchApplicantName to set.
	 */
	public void setSearchApplicantName(String searchApplicantName) {
		this.searchApplicantName = searchApplicantName;
	}

	/**
	 * @return Returns the newEmailType.
	 */
	public String getNewEmailType() {
		return newEmailType;
	}

	/**
	 * @param newEmailType
	 *            The newEmailType to set.
	 */
	public void setNewEmailType(String newEmailType) {
		this.newEmailType = newEmailType;
	}

	/**
	 * @return Returns the cc.
	 */
	public String getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            The cc to set.
	 */
	public void setCc(String cc) {
		this.cc = cc;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the to.
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            The to to set.
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return Returns the folderId.
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId
	 *            The folderId to set.
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return Returns the emailId.
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            The emailId to set.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	/**
	 * @return Returns the applicantId.
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * @return Returns the emailLocation.
	 */
	public String getEmailLocation() {
		return emailLocation;
	}

	/**
	 * @param emailLocation
	 *            The emailLocation to set.
	 */
	public void setEmailLocation(String emailLocation) {
		this.emailLocation = emailLocation;
	}

	/**
	 * @return Returns the newEmailBody.
	 */
	public String getNewEmailBody() {
		return newEmailBody;
	}

	/**
	 * @param newEmailBody
	 *            The newEmailBody to set.
	 */
	public void setNewEmailBody(String newEmailBody) {
		this.newEmailBody = newEmailBody;
	}

	/**
	 * @return Returns the includeOriginal.
	 */
	public String getIncludeOriginal() {
		return includeOriginal;
	}

	/**
	 * @param includeOriginal
	 *            The includeOriginal to set.
	 */
	public void setIncludeOriginal(String includeOriginal) {
		this.includeOriginal = includeOriginal;
	}

	/**
	 * @return Returns the positionId.
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId
	 *            The positionId to set.
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return Returns the positionIds.
	 */
	public ArrayList getPositionIds() {
		return positionIds;
	}

	/**
	 * @param positionIds
	 *            The positionIds to set.
	 */
	public void setPositionIds(ArrayList positionIds) {
		this.positionIds = positionIds;
	}

	/**
	 * @return Returns the positionNames.
	 */
	public ArrayList getPositionNames() {
		return positionNames;
	}

	/**
	 * @param positionNames
	 *            The positionNames to set.
	 */
	public void setPositionNames(ArrayList positionNames) {
		this.positionNames = positionNames;
	}

	/**
	 * @return Returns the attachedFile.
	 */
	public FormFile getAttachedFile() {
		return attachedFile;
	}

	/**
	 * @param attachedFile
	 *            The attachedFile to set.
	 */
	public void setAttachedFile(FormFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	/**
	 * @return Returns the tmpEmailId.
	 */
	public String getTmpEmailId() {
		return tmpEmailId;
	}

	/**
	 * @param tmpEmailId
	 *            The tmpEmailId to set.
	 */
	public void setTmpEmailId(String tmpEmailId) {
		this.tmpEmailId = tmpEmailId;
	}

	/**
	 * @return Returns the attachmentId.
	 */
	public String[] getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId
	 *            The attachmentId to set.
	 */
	public void setAttachmentId(String[] attachmentId) {
		this.attachmentId = Utils.getArrayCopy(attachmentId);
	}

	public String getAttachmentIds() {
		String attachmentIds = "";
		if (attachmentId != null) {
			for (int i = 0; i < attachmentId.length; i++) {
				attachmentIds += attachmentId[i];
				if (i < attachmentId.length - 1) {
					attachmentIds += ",";
				}
			}
		}
		return attachmentIds;
	}

	/**
	 * @return Returns the selAttachment.
	 */
	public String getSelAttachment() {
		return selAttachment;
	}

	/**
	 * @param selAttachment The selAttachment to set.
	 */
	public void setSelAttachment(String selAttachment) {
		this.selAttachment = selAttachment;
	}

	/**
	 * @return Returns the updateResume.
	 */
	public String getUpdateResume() {
		return updateResume;
	}

	/**
	 * @param updateResume The updateResume to set.
	 */
	public void setUpdateResume(String updateResume) {
		this.updateResume = updateResume;
	}

	/**
	 * @return Returns the sortBy.
	 */
	public String getSortBy() {
		return sortBy;
	}

	/**
	 * @param sortBy The sortBy to set.
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return Returns the sortDir.
	 */
	public String getSortDir() {
		return sortDir;
	}

	/**
	 * @param sortDir The sortDir to set.
	 */
	public void setSortDir(String sortDir) {
		this.sortDir = sortDir;
	}

	/**
	 * @return the templateCode
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @param templateCode the templateCode to set
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @return the selectedIds
	 */
	public String getSelectedIds() {
		return selectedIds;
	}

	/**
	 * @param selectedIds the selectedIds to set
	 */
	public void setSelectedIds(String selectedIds) {
		this.selectedIds = selectedIds;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the emailIds
	 */
	public String getEmailIds() {
		return emailIds;
	}

	/**
	 * @param emailIds the emailIds to set
	 */
	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}

	/**
	 * @return the messageReceivedType
	 */
	public String getMessageReceivedType() {
		return messageReceivedType;
	}

	/**
	 * @param messageReceivedType the messageReceivedType to set
	 */
	public void setMessageReceivedType(String messageReceivedType) {
		this.messageReceivedType = messageReceivedType;
	}

	/**
	 * @return the tmpAttachmentId
	 */
	public String getTmpAttachmentId() {
		return tmpAttachmentId;
	}

	/**
	 * @param tmpAttachmentId the tmpAttachmentId to set
	 */
	public void setTmpAttachmentId(String tmpAttachmentId) {
		this.tmpAttachmentId = tmpAttachmentId;
	}

	/**
	 * @return the bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc the bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}
}
