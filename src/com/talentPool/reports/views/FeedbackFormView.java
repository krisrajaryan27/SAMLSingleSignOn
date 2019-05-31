/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class FeedbackFormView extends SimpleDataObject {

	public void setFeedbackFormTitle(String feedbackFormTitle) {
		setAttribute("feedbackFormTitle", feedbackFormTitle);
	}

	public String getFeedbackFormTitle() {
		return getString("feedbackFormTitle");
	}

	public void setFeedbackFormDesc(String feedbackFormDesc) {
		setAttribute("feedbackFormDesc", feedbackFormDesc);
	}

	public String getFeedbackFormDesc() {
		return getString("feedbackFormDesc");
	}

	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}

	public String getApplicantName() {
		return getString("applicantName");
	}

	public void setInterviewerName(String interviewerName) {
		setAttribute("interviewerName", interviewerName);
	}

	public String getInterviewerName() {
		return getString("interviewerName");
	}

	public void setSourceName(String sourceName) {
		setAttribute("sourceName", sourceName);
	}

	public String getSourceName() {
		return getString("sourceName");
	}

	public void setPositionName(String positionName) {
		setAttribute("positionName", positionName);
	}

	public String getPositionName() {
		return getString("positionName");
	}

	public void setPositionStepName(String positionStepName) {
		setAttribute("positionStepName", positionStepName);
	}

	public String getPositionStepName() {
		return getString("positionStepName");
	}

	public void setInterviewDate(String interviewDate) {
		setAttribute("interviewDate", interviewDate);
	}

	public String getInterviewDate() {
		return getString("interviewDate");
	}

	public void setFeedbackDate(String feedbackDate) {
		setAttribute("feedbackDate", feedbackDate);
	}

	public String getFeedbackDate() {
		return getString("feedbackDate");
	}

	public void setFeedbackResult(String feedbackResult) {
		setAttribute("feedbackResult", feedbackResult);
	}

	public String getFeedbackResult() {
		return getString("feedbackResult");
	}

	public void setCategoryName(String categoryName) {
		setAttribute("categoryName", categoryName);
	}

	public String getCategoryName() {
		return getString("categoryName");
	}

	public void setFieldTitle(String fieldTitle) {
		setAttribute("fieldTitle", fieldTitle);
	}

	public String getFieldTitle() {
		return getString("fieldTitle");
	}

	public void setRating(String rating) {
		setAttribute("rating", rating);
	}

	public String getRating() {
		return getString("rating");
	}
	
	public void setMultipleSelect(String multipleSelect) {
		setAttribute("multipleSelect", multipleSelect);
	}

	public String getMultipleSelect() {
		return getString("multipleSelect");
	}

	public void setFieldComment(String fieldComment) {
		setAttribute("fieldComment", fieldComment);
	}

	public String getFieldComment() {
		return getString("fieldComment");
	}
	/**
	 * @return the compactFieldComment
	 */
	public String getCompactFieldComment() {
		return getString("compactFieldComment");
	}

	/**
	 * @param compactFieldComment the compactFieldComment to set
	 */
	public void setCompactFieldComment(String compactFieldComment) {
		setAttribute("compactFieldComment", compactFieldComment);
	}

	/**
	 * @return the compactHeader
	 */
	public String getCompactHeader() {
		return getString("compactHeader");
	}

	/**
	 * @param compactHeader the compactHeader to set
	 */
	public void setCompactHeader(String compactHeader) {
		setAttribute("compactHeader", compactHeader);
	}

	/**
	 * @return the compactRating
	 */
	public String getCompactRating() {
		return getString("compactRating");
	}

	/**
	 * @param compactRating the compactRating to set
	 */
	public void setCompactRating(String compactRating) {
		setAttribute("compactRating", compactRating);
	}

	/**
	 * @return the compactFieldTitle
	 */
	public String getCompactFieldTitle() {
		return getString("compactFieldTitle");
	}

	/**
	 * @param compactFieldTitle the compactFieldTitle to set
	 */
	public void setCompactFieldTitle(String compactFieldTitle) {
		setAttribute("compactFieldTitle", compactFieldTitle);
	}	
	/**
	 * @return the feedbackBy
	 */
	public String getFeedbackBy() {
		return getString("feedbackBy");
	}

	/**
	 * @param feedbackBy the feedbackBy to set
	 */
	public void setFeedbackBy(String feedbackBy) {
		setAttribute("feedbackBy", feedbackBy);
	}
	/**
	 * @return the feedbackReportDesc
	 */
	public String getFeedbackReportDesc() {
		return getString("feedbackReportDesc");
	}

	/**
	 * @param feedbackReportDesc the feedbackReportDesc to set
	 */
	public void setFeedbackReportDesc(String feedbackReportDesc) {
		setAttribute("feedbackReportDesc", feedbackReportDesc);
	}
	
	private String noDataMessage;

	/**
	 * @return the noDataMessage
	 */
	public String getNoDataMessage() {
		return noDataMessage;
	}

	/**
	 * @param noDataMessage the noDataMessage to set
	 */
	public void setNoDataMessage(String noDataMessage) {
		this.noDataMessage = noDataMessage;
	}
}
