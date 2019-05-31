package com.talentPool.dashboard.form;

import com.talentPool.common.base.TPActionForm;

public class DashboardForm extends TPActionForm {
	private String messageIds;
	private String reminderId;
	private String toDoType;
	private String groupItemId;
	private String sortByColumn;
	private String sortOrder;
	private String applicantId;
	private String postionSummaryGridType;
	private String positionGroupItemId;
	
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

	public String getMessageIds() {
		return messageIds;
	}

	public void setMessageIds(String messageIds) {
		this.messageIds = messageIds;
	}

	/**
	 * @return the reminderId
	 */
	public String getReminderId() {
		return reminderId;
	}

	/**
	 * @param reminderId the reminderId to set
	 */
	public void setReminderId(String reminderId) {
		this.reminderId = reminderId;
	}

	public String getToDoType() {
		return toDoType;
	}

	public void setToDoType(String toDoType) {
		this.toDoType = toDoType;
	}

	public String getGroupItemId() {
		return groupItemId;
	}

	public void setGroupItemId(String groupItemId) {
		this.groupItemId = groupItemId;
	}

	public String getSortByColumn() {
		return sortByColumn;
	}

	public void setSortByColumn(String sortByColumn) {
		this.sortByColumn = sortByColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the postionSummaryGridType
	 */
	public String getPostionSummaryGridType() {
		return postionSummaryGridType;
	}

	/**
	 * @param postionSummaryGridType the postionSummaryGridType to set
	 */
	public void setPostionSummaryGridType(String postionSummaryGridType) {
		this.postionSummaryGridType = postionSummaryGridType;
	}

	/**
	 * @return the positionGroupItemId
	 */
	public String getPositionGroupItemId() {
		return positionGroupItemId;
	}

	/**
	 * @param positionGroupItemId the positionGroupItemId to set
	 */
	public void setPositionGroupItemId(String positionGroupItemId) {
		this.positionGroupItemId = positionGroupItemId;
	}
}
