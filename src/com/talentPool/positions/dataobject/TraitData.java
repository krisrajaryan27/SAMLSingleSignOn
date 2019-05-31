/**
 * 
 */
package com.talentPool.positions.dataobject;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.masters.constants.FeedbackFieldsConstant;

/**
 * @author shivprasad
 * 
 */
public class TraitData extends SimpleDataObject {

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

	public String getTraitComment() {
		return getString("traitComment");
	}

	public void setTraitComment(String traitComment) {
		setAttribute("traitComment", traitComment);
	}

	/**
	 * @return the ratingFieldDesc
	 */
	public String getRatingFieldDesc() {
		return getString("ratingFieldDesc");
	}

	/**
	 * @param ratingFieldDesc
	 *            the ratingFieldDesc to set
	 */
	public void setRatingFieldDesc(String ratingFieldDesc) {
		setAttribute("ratingFieldDesc", ratingFieldDesc);
	}

	/**
	 * @return the ratingFieldId
	 */
	public String getRatingFieldId() {
		return getString("ratingFieldId");
	}

	/**
	 * @param ratingFieldId
	 *            the ratingFieldId to set
	 */
	public void setRatingFieldId(String ratingFieldId) {
		setAttribute("ratingFieldId", ratingFieldId);
	}

	/**
	 * @return Returns the interviewerId.
	 */
	public int getInterviewerId() {
		return getInt("interviewerId");
	}

	/**
	 * @param interviewerId
	 *            The interviewerId to set.
	 */
	public void setInterviewerId(int interviewerId) {
		setAttribute("interviewerId", new Integer(interviewerId));
	}	
	/**
	 * @return the feedbackFormFieldDisplayType
	 */
	public String getFeedbackFormFieldDisplayType() {
		return getString("feedbackFormFieldDisplayType");
	}

	/**
	 * @param feedbackFormFieldDisplayType the feedbackFormFieldDisplayType to set
	 */
	public void setFeedbackFormFieldDisplayType(String feedbackFormFieldDisplayType) {
		setAttribute("feedbackFormFieldDisplayType", feedbackFormFieldDisplayType);
	}
	/**
	 * @return the feedbackFormFieldIsMandatory
	 */
	public String getFeedbackFormFieldIsMandatory() {
		return getString("feedbackFormFieldIsMandatory");
	}

	/**
	 * @param feedbackFormFieldIsMandatory the feedbackFormFieldIsMandatory to set
	 */
	public void setFeedbackFormFieldIsMandatory(String feedbackFormFieldIsMandatory) {
		setAttribute("feedbackFormFieldIsMandatory", feedbackFormFieldIsMandatory);
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
	
	/**
	 * @return the multipleSelectFieldDesc
	 */
	public String getMultipleSelectFieldDesc() {
		return getString("multipleSelectFieldDesc");
	}

	/**
	 * @param multipleSelectFieldDesc
	 *            the multipleSelectFieldDesc to set
	 */
	public void setMultipleSelectFieldDesc(String multipleSelectFieldDesc) {
		setAttribute("multipleSelectFieldDesc", multipleSelectFieldDesc);
	}

	/**
	 * @return the multipleSelectFieldId
	 */
	public String getMultipleSelectFieldId() {
		return getString("multipleSelectFieldId");
	}

	/**
	 * @param multipleSelectFieldId
	 *            the multipleSelectFieldId to set
	 */
	public void setMultipleSelectFieldId(String multipleSelectFieldId) {
		setAttribute("multipleSelectFieldId", multipleSelectFieldId);
	}
	
	/**
	 * feedbackFieldType is different from feedbackFormFieldType.
	 * feedbackFormFieldType says whether it is feedback category or a feedback field
	 * feedbackFieldType says whether a field is a normal field or applicant field
	 *
	 * @return feedbackFieldType
	 */
	public String getFeedbackFieldType() {
		return getString("feedbackFieldType");
	}

	public void setFeedbackFieldType(String feedbackFieldType) {
		setAttribute("feedbackFieldType", feedbackFieldType);
	}
	
	
	/**
	 * If feedbackFieldType is of type {@link FeedbackFieldsConstant}.<code>FIELD_TYPE_APPLICANT</code> then corresponding applicant mapping is fetched through <code>applicantFieldId</code> 
	 * 
	 * @return applicantFieldId
	 */
	public String getApplicantFieldId() {
		return getString("applicantFieldId");
	}

	public void setApplicantFieldId(String applicantFieldId) {
		setAttribute("applicantFieldId", applicantFieldId);
	}
	public String getFeedbackFormCategoryTitle() {
		return getString("feedbackFormCategoryTitle");
	}
	
	public void setFeedbackFormCategoryTitle(String feedbackFormCategoryTitle) {
		setAttribute("feedbackFormCategoryTitle", feedbackFormCategoryTitle);
	}
	
	public String getFeedbackFormTitle() {
		return getString("feedbackFormTitle");
	}
	
	public void setFeedbackFormTitle(String feedbackFormTitle) {
		setAttribute("feedbackFormTitle", feedbackFormTitle);
	}
	
	public String getFeedbackFormId() {
		return getString("feedbackFormId");
	}
	
	public void setFeedbackFormId(String feedbackFormId) {
		setAttribute("feedbackFormId", feedbackFormId);
	}
	
	public String getFeedbackFormHeader() {
		return getString("feedbackFormHeader");
	}
	
	public void setFeedbackFormHeader(String feedbackFormHeader) {
		setAttribute("feedbackFormHeader", feedbackFormHeader);
	}

}