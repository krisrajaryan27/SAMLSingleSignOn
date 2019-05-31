/**
 * 
 */
package com.talentPool.masters.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormFieldData extends SimpleDataObject {
	public String getFeedbackFieldId() {
		return getString("feedbackFieldId");
	}

	public void setFeedbackFieldId(String feedbackFieldId) {
		setAttribute("feedbackFieldId", feedbackFieldId);
	}

	public String getFeedbackFieldTitle() {
		return getString("feedbackFieldTitle");
	}

	/**
	 * @param feedbackFieldTitle
	 *            the feedbackFieldTitle to set
	 */
	public void setFeedbackFieldTitle(String feedbackFieldTitle) {
		setAttribute("feedbackFieldTitle", feedbackFieldTitle);
	}

	/**
	 * @return the feedbackFormFieldDesc
	 */
	public String getFeedbackFormFieldDesc() {
		return getString("feedbackFormFieldDesc");
	}

	/**
	 * @param feedbackFormFieldDesc
	 *            the feedbackFormFieldDesc to set
	 */
	public void setFeedbackFormFieldDesc(String feedbackFormFieldDesc) {
		setAttribute("feedbackFormFieldDesc", feedbackFormFieldDesc);
	}

	/**
	 * @return the feedbackFormFieldId
	 */
	public String getFeedbackFormFieldId() {
		return getString("feedbackFormFieldId");
	}

	/**
	 * @param feedbackFormFieldId
	 *            the feedbackFormFieldId to set
	 */
	public void setFeedbackFormFieldId(String feedbackFormFieldId) {
		setAttribute("feedbackFormFieldId", feedbackFormFieldId);
	}

	/**
	 * @return the feedbackFormFieldRank
	 */
	public int getFeedbackFormFieldRank() {
		return getInt("feedbackFormFieldRank");
	}

	/**
	 * @param feedbackFormFieldRank
	 *            the feedbackFormFieldRank to set
	 */
	public void setFeedbackFormFieldRank(int feedbackFormFieldRank) {
		setAttribute("feedbackFormFieldRank", "" + feedbackFormFieldRank);
	}

	/**
	 * @return the feedbackFormFieldType
	 */
	public String getFeedbackFormFieldType() {
		return getString("feedbackFormFieldType");
	}

	/**
	 * @param feedbackFormFieldType
	 *            the feedbackFormFieldType to set
	 */
	public void setFeedbackFormFieldType(String feedbackFormFieldType) {
		setAttribute("feedbackFormFieldType", feedbackFormFieldType);
	}

	/**
	 * @return the feedbackFormId
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
	 * @return the ratingId
	 */
	public String getRatingId() {
		return getString("ratingId");
	}

	/**
	 * @param ratingId
	 *            the ratingId to set
	 */
	public void setRatingId(String ratingId) {
		setAttribute("ratingId", ratingId);
	}

	public String getFeedbackFormFieldCommentRequired() {
		return getString("feedbackFormFieldCommentRequired");
	}

	public void setFeedbackFormFieldCommentRequired(String feedbackFormFieldCommentRequired) {
		setAttribute("feedbackFormFieldCommentRequired", feedbackFormFieldCommentRequired);
	}
	/**
	 * @return the fieldDisplayType
	 */
	public String getFieldDisplayType() {
		return getString("fieldDisplayType");
	}

	/**
	 * @param fieldDisplayType the fieldDisplayType to set
	 */
	public void setFieldDisplayType(String fieldDisplayType) {
		setAttribute("fieldDisplayType", fieldDisplayType);
	}

	/**
	 * @return the fieldIsMandatory
	 */
	public String getFieldIsMandatory() {
		return getString("fieldIsMandatory");
	}

	/**
	 * @param fieldIsMandatory the fieldIsMandatory to set
	 */
	public void setFieldIsMandatory(String fieldIsMandatory) {
		setAttribute("fieldIsMandatory", fieldIsMandatory);
	}
	
	public String getSystemGenerated() {
		return getString("systemGenerated");
	}

	public void setSystemGenerated(String systemGenerated) {
		setAttribute("systemGenerated", systemGenerated);
	}
	
	/**
	 * @return the multipleSelectId
	 */
	public String getMultipleSelectId() {
		return getString("multipleSelectId");
	}

	/**
	 * @param multipleSelectId
	 *            the multipleSelectId to set
	 */
	public void setMultipleSelectId(String multipleSelectId) {
		setAttribute("multipleSelectId", multipleSelectId);
	}
	
	public String getIsSummaryField() {
		return getString("isSummaryField");
	}

	public void setIsSummaryField(String isSummaryField) {
		setAttribute("isSummaryField", isSummaryField);
	}
	
	public String getFeedbackFieldType() {
		return getString("feedbackFieldType");
	}

	public void setFeedbackFieldType(String feedbackFieldType) {
		setAttribute("feedbackFieldType", feedbackFieldType);
	}
	
	public String getApplicantFieldId() {
		return getString("applicantFieldId");
	}

	public void setApplicantFieldId(String applicantFieldId) {
		setAttribute("applicantFieldId", applicantFieldId);
	}
}
