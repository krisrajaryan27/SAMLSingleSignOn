/**
 * 
 */
package com.talentPool.masters.dataobject;

import java.util.ArrayList;
import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormData extends SimpleDataObject {
	ArrayList<FeedbackFormFieldData> feedbackFormFields = null;

	/**
	 * @return getString("the feedbackFormHeader
	 */
	public String getFeedbackFormHeader() {
		return getString("feedbackFormHeader");
	}

	/**
	 * @param feedbackFormHeader
	 *            the feedbackFormHeader to set
	 */
	public void setFeedbackFormHeader(String feedbackFormHeader) {
		setAttribute("feedbackFormHeader", feedbackFormHeader);
	}

	/**
	 * @return getString("the feedbackFormId
	 */
	public String getFeedbackFormId() {
		return getString("feedbackFormId");
	}

	/**
	 * @param feedbackFormId
	 *            the feedbackFormId to set
	 */
	public void setFeedbackFormId(String feedbackFormId) {
		setAttribute("feedbackFormId", feedbackFormId);
	}

	/**
	 * @return getString("the feedbackFormStatus
	 */
	public String getFeedbackFormStatus() {
		return getString("feedbackFormStatus");
	}

	/**
	 * @param feedbackFormStatus
	 *            the feedbackFormStatus to set
	 */
	public void setFeedbackFormStatus(String feedbackFormStatus) {
		setAttribute("feedbackFormStatus", feedbackFormStatus);
	}

	/**
	 * @return getString("the feedbackFormTitle
	 */
	public String getFeedbackFormTitle() {
		return getString("feedbackFormTitle");
	}

	/**
	 * @param feedbackFormTitle
	 *            the feedbackFormTitle to set
	 */
	public void setFeedbackFormTitle(String feedbackFormTitle) {
		setAttribute("feedbackFormTitle", feedbackFormTitle);
	}

	/**
	 * @return getString("the userId
	 */
	public String getUserId() {
		return getString("userId");
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	/**
	 * @return the feedbackFormFields
	 */
	public ArrayList<FeedbackFormFieldData> getFeedbackFormFields() {
		return feedbackFormFields;
	}

	public Date getDateCreated() {
		return getDate("dateCreated");
	}

	public Date getLastModified() {
		try {
			return getDate("lastModified");	
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}

	public String getCreatedBy() {
		return getString("createdBy");
	}

	/**
	 * @param feedbackFormFields
	 *            the feedbackFormFields to set
	 */
	public void setFeedbackFormFields(ArrayList<FeedbackFormFieldData> feedbackFormFields) {
		this.feedbackFormFields = feedbackFormFields;
	}

	/**
	 * @return the displayType
	 */
	public String getDisplayType() {
		return getString("displayType");
	}

	/**
	 * @param displayType the displayType to set
	 */
	public void setDisplayType(String displayType) {
		setAttribute("displayType", displayType);
	}
}
