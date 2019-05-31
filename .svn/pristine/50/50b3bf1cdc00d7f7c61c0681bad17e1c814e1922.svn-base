/**
 * 
 */
package com.talentPool.selectionProcess.dataobject;

import java.sql.Date;
import java.sql.Timestamp;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;

/**
 * @author shivprasad
 * 
 */
public class CommunicationData extends SimpleDataObject {

	public String getCommunicationId() {
		return getId("communicationId");
	}

	public void setCommunicationId(String communicationId) {
		setAttribute("communicationId", communicationId);
	}

	public String getApplicantId() {
		return getId("applicantId");
	}

	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	public String getUserId() {
		return getId("userId");
	}

	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	/**
	 * @return Returns the communicationDate.
	 */
	public Timestamp getCommunicationDate() {
		try {
			return getTimestamp("communicationDate");
		} catch (Exception e) {
			TPLogger.getLogger().debug("Error while getting sql communicationDate: returning null", e);
		}
		return null;
	}

	/**
	 * @return Returns the communicationDate to display in System Date Format.
	 */
	public String getCommunicationDateToDisplay() {
		return DateUtils.getSystemDateTimeFormat(new java.util.Date(getCommunicationDate().getTime()));
	}
	

	public void setCommunicationDate(Timestamp communicationDate) {
		setAttribute("communicationDate", communicationDate);
	}

	public int getCommunicationType() {
		return getInt("communicationType");
	}

	public void setCommunicationType(int communicationType) {
		setAttribute("communicationType", new Integer(communicationType));
	}

	public String getCommunicationText() {
		return getString("communicationText");
	}

	public void setCommunicationText(String communicationText) {
		setAttribute("communicationText", communicationText);
	}

	public String getCommunicationPhoneNo() {
		return getString("communicationPhoneNo");
	}

	public void setCommunicationPhoneNo(String communicationPhoneNo) {
		setAttribute("communicationPhoneNo", communicationPhoneNo);
	}
	
	public String getName(){
		return getString("name");
	}

	/**
	 * @return the documentId
	 */
	public String getDocumentId() {
		return getString("documentId");
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(String documentId) {
		setAttribute("documentId", documentId);
	}
}
