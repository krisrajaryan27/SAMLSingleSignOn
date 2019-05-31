package com.talentPool.applicant.dataobject;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;

public class ApplicantBlackListHistoryData extends SimpleDataObject{
	public String getApplicantId() {
		return getId("applicantId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	/**
	 * @return Returns the interactionId.
	 */
	public String getInteractionId() {
		return getString("interactionId");
	}

	/**
	 * @param interactionId
	 *            The interactionId to set.
	 */
	public void setInteractionId(String interactionId) {
		setAttribute("interactionId", interactionId);
	}
	
	/**
	 * @return Returns the blackListReason.
	 */
	public String getBlackListReason() {
		return getString("blackListReason");
	}

	/**
	 * @param blackListReason
	 *            The blackListReason to set.
	 */
	public void setBlackListReason(String blackListReason) {
		setAttribute("blackListReason", blackListReason);
	}
	
	/**
	 * @return Returns the interactionType.
	 */
	public String getInteractionType() {
		return getString("interactionType");
	}

	/**
	 * @param interactionType
	 *            The interactionType to set.
	 */
	public void setInteractionType(String interactionType) {
		setAttribute("interactionType", interactionType);
	}
	
	/**
	 * @return Returns the userId.
	 */
	public String getUserId() {
		return getString("userId");
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return getString("userName");
	}

	/**
	 * @param userName
	 *            The userName to set.
	 */
	public void setUserName(String userName) {
		setAttribute("userId", userName);
	}
	
	/**
	 * @return Returns the dateCreated.
	 */
	public Date getDateCreated() {
		return getDate("dateCreated");
	}
	
	/**
	 * @return Returns the dateCreated to display in System date time format.
	 */
	public String getDateCreatedToDisplay() {
		return DateUtils.getSystemDateTimeFormat(getDateCreated());
	}
	
	/**
	 * @param dateCreated
	 *            The dateCreated to set.
	 */
	public void setDateCreated(Date dateCreated) {
		setAttribute("dateCreated", dateCreated);
	}
}
