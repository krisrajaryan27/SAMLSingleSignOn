/**
 * 
 */
package com.talentPool.documents.form;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;

/**
 * @author shivprasad
 *
 */
public class DocumentForm extends TPActionForm {
	String fileName;
	String contentDisposition;
	FormFile attachedFile;
	String documentId;
	String applicantId;
	String positionId;
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the contentDisposition
	 */
	public String getContentDisposition() {
		return contentDisposition;
	}

	/**
	 * @param contentDisposition the contentDisposition to set
	 */
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * @return the attachedFile
	 */
	public FormFile getAttachedFile() {
		return attachedFile;
	}

	/**
	 * @param attachedFile the attachedFile to set
	 */
	public void setAttachedFile(FormFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the positionid
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionid the positionid to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
}
