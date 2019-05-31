/**
 * 
 */
package com.talentPool.requisition.form;

import com.talentPool.common.base.TPActionForm;

/**
 * @author shivprasad
 * 
 */
public class RequisitionForm extends TPActionForm {
	private String requisitionApprovalTemplateId;
	private String requisitionApprovalTemplateName;
	private String requisitionApprovalStepId;
	private String requisitionApprovalStepName;
	private String userIds;
	private String save;
	private String moveDir;
	private String jsArrayRequisitionApprovalSteps;
	
	public String getRequisitionApprovalStepId() {
		return requisitionApprovalStepId;
	}
	public void setRequisitionApprovalStepId(String requisitionApprovalStepId) {
		this.requisitionApprovalStepId = requisitionApprovalStepId;
	}
	public String getRequisitionApprovalStepName() {
		return requisitionApprovalStepName;
	}
	public void setRequisitionApprovalStepName(String requisitionApprovalStepName) {
		this.requisitionApprovalStepName = requisitionApprovalStepName;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getSave() {
		return save;
	}
	public void setSave(String save) {
		this.save = save;
	}
	public String getMoveDir() {
		return moveDir;
	}
	public void setMoveDir(String moveDir) {
		this.moveDir = moveDir;
	}
	/**
	 * @return the requisitionApprovalTemplateName
	 */
	public String getRequisitionApprovalTemplateName() {
		return requisitionApprovalTemplateName;
	}
	/**
	 * @param requisitionApprovalTemplateName the requisitionApprovalTemplateName to set
	 */
	public void setRequisitionApprovalTemplateName(String requisitionApprovalTemplateName) {
		this.requisitionApprovalTemplateName = requisitionApprovalTemplateName;
	}
	/**
	 * @return the requisitionApprovalTemplateId
	 */
	public String getRequisitionApprovalTemplateId() {
		return requisitionApprovalTemplateId;
	}
	/**
	 * @param requisitionApprovalTemplateId the requisitionApprovalTemplateId to set
	 */
	public void setRequisitionApprovalTemplateId(String requisitionApprovalTemplateId) {
		this.requisitionApprovalTemplateId = requisitionApprovalTemplateId;
	}
	/**
	 * @return the jsArrayRequisitionApprovalSteps
	 */
	public String getJsArrayRequisitionApprovalSteps() {
		return jsArrayRequisitionApprovalSteps;
	}
	/**
	 * @param jsArrayRequisitionApprovalSteps the jsArrayRequisitionApprovalSteps to set
	 */
	public void setJsArrayRequisitionApprovalSteps(String jsArrayRequisitionApprovalSteps) {
		this.jsArrayRequisitionApprovalSteps = jsArrayRequisitionApprovalSteps;
	}
}
