/**
 * 
 */
package com.talentPool.masters.dataobject;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFieldCategoryData extends SimpleDataObject {
	ArrayList<FeedbackFieldData> feedbackFields = null;

	public String getFeedbackFieldCategoryId() {
		return getString("feedbackFieldCategoryId");
	}

	public void setFeedbackFieldCategoryId(String feedbackFieldCategoryId) {
		setAttribute("feedbackFieldCategoryId", feedbackFieldCategoryId);
	}

	public String getFeedbackFieldCategory() {
		return getString("feedbackFieldCategory");
	}

	public void setFeedbackFieldCategory(String feedbackFieldCategory) {
		setAttribute("feedbackFieldCategory", feedbackFieldCategory);
	}

	/**
	 * @return the feedbackFields
	 */
	public ArrayList<FeedbackFieldData> getFeedbackFields() {
		return feedbackFields;
	}

	/**
	 * @param feedbackFields
	 *            the feedbackFields to set
	 */
	public void setFeedbackFields(ArrayList<FeedbackFieldData> feedbackFields) {
		this.feedbackFields = feedbackFields;
	}
	
	public String getSystemGenerated() {
		return getString("systemGenerated");
	}

	public void setSystemGenerated(String systemGenerated) {
		setAttribute("systemGenerated", systemGenerated);
	}
	
	public String getIsSummaryField() {
		return getString("isSummaryField");
	}

	public void setIsSummaryField(String isSummaryField) {
		setAttribute("isSummaryField", isSummaryField);
	}
}
