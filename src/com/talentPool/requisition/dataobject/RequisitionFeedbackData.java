/**
 * 
 */
package com.talentPool.requisition.dataobject;

import java.sql.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

/**
 * @author shivprasad
 * 
 */
public class RequisitionFeedbackData extends SimpleDataObject {

	public String getByUserId() {
		return getString("byUserId");
	}

	public void setByUserId(String byUserId) {
		setAttribute("byUserId", byUserId);
	}

	public String getFeedbackComment() {
		return getString("feedbackComment");
	}

	public void setFeedbackComment(String feedbackComment) {
		setAttribute("feedbackComment", feedbackComment);
	}

	public Date getFeedbackDate() {
		try {
			return getDate("feedbackDate");	
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
		}
		return null;
	}
	
	public String getFeedbackDateToDisplay() {
		return DateUtils.getSystemDateTimeFormat(getFeedbackDate());
	}

	public void setFeedbackDate(Date feedbackDate) {
		setAttribute("feedbackDate", feedbackDate);
	}

	public String getFeedbackDecision() {
		return getString("feedbackDecision");
	}

	public void setFeedbackDecision(String feedbackDecision) {
		setAttribute("feedbackDecision", feedbackDecision);
	}

	public String getFeedbackId() {
		return getString("feedbackId");
	}

	public void setFeedbackId(String feedbackId) {
		setAttribute("feedbackId", feedbackId);
	}

	public String getFromStepId() {
		return getString("fromStepId");
	}

	public void setFromStepId(String fromStepId) {
		setAttribute("fromStepId", fromStepId);
	}

	public String getPositionId() {
		return getString("positionId");
	}

	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}

	public String getToStepId() {
		return getString("toStepId");
	}

	public void setToStepId(String toStepId) {
		setAttribute("toStepId", toStepId);
	}

	public String getToUserId() {
		return getString("toUserId");
	}

	public void setToUserId(String toUserId) {
		setAttribute("toUserId", toUserId);
	}
	
	public String getFromUserName(){
		return getString("fromUserName");
	}
	
	public String getFromStepName(){
		return getString("fromStepName");
	}
	
	public String getToUserName(){
		return getString("toUserName");
	}
	
	public String getToStepName(){
		return getString("toStepName");
	}
	
	public String getPositionTitle(){
		return getString("positionTitle");
	}
	
	public String getBudgetItemId() {
		return getString("budgetItemId");
	}
	
	public String getBudgetItemName() {
		return getString("budgetItemName");
	}
	
	public void setFeedBackError(String feedBackError) {
		setAttribute("feedBackError",feedBackError);
	}
	
	public String getFeedBackError() {
		return getString("feedBackError");
	}	
}
