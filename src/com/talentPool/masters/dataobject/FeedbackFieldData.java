/**
 * 
 */
package com.talentPool.masters.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFieldData extends SimpleDataObject {
	public String getFeedbackFieldId() {
		return getString("feedbackFieldId");
	}

	public void setFeedbackFieldId(String feedbackFieldId) {
		setAttribute("feedbackFieldId", feedbackFieldId);
	}

	public String getFeedbackFieldCategoryId() {
		return getString("feedbackFieldCategoryId");
	}

	public void setFeedbackFieldCategoryId(String feedbackFieldCategoryId) {
		setAttribute("feedbackFieldCategoryId", feedbackFieldCategoryId);
	}

	public String getFeedbackFieldTitle() {
		return getString("feedbackFieldTitle");
	}

	public void setFeedbackFieldTitle(String feedbackFieldTitle) {
		setAttribute("feedbackFieldTitle", feedbackFieldTitle);
	}
	
	public String getFeedbackFieldDesc() {
		return getString("feedbackFieldDesc");
	}

	public void setFeedbackFieldDesc(String feedbackFieldDesc) {
		setAttribute("feedbackFieldDesc", feedbackFieldDesc);
	}
	
	public String getSystemGenerated() {
		return getString("systemGenerated");
	}

	public void setSystemGenerated(String systemGenerated) {
		setAttribute("systemGenerated", systemGenerated);
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
