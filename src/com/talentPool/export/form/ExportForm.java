/**
 * 
 */
package com.talentPool.export.form;

import com.talentPool.common.base.TPActionForm;

/**
 * @author pallavi
 *
 */
@SuppressWarnings("serial")
public class ExportForm extends TPActionForm {
	private String entityType;
	private String selectedFields;
	private String ids;
	private String positionId;
	private String applicantName;
	private String stepName;
	private String rejectedBy;
	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}
	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	/**
	 * @return the selectedFields
	 */
	public String getSelectedFields() {
		return selectedFields;
	}
	/**
	 * @param selectedFields the selectedFields to set
	 */
	public void setSelectedFields(String selectedFields) {
		this.selectedFields = selectedFields;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}
	/**
	 * @param positionId the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getRejectedBy() {
		return rejectedBy;
	}
	public void setRejectedBy(String rejectedBy) {
		this.rejectedBy = rejectedBy;
	}
}
