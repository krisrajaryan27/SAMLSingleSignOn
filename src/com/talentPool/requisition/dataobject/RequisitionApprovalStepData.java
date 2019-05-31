/**
 * 
 */
package com.talentPool.requisition.dataobject;

import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.user.dataobject.LoginData;

/**
 * @author shivprasad
 * 
 */
public class RequisitionApprovalStepData extends SimpleDataObject {
	ArrayList<LoginData> users;	
	public int getRequisitionApprovalStepId() {
		return getInt("requisitionApprovalStepId");
	}

	public void setRequisitionApprovalStepId(int requisitionApprovalStepId) {
		setAttribute("requisitionApprovalStepId", requisitionApprovalStepId);
	}

	public String getRequisitionApprovalStepName() {
		return getString("requisitionApprovalStepName");
	}

	public void setRequisitionApprovalStepName(String requisitionApprovalStepName) {
		setAttribute("requisitionApprovalStepName", requisitionApprovalStepName);

	}

	public int getRequisitionApprovalStepRank() {
		return getInt("requisitionApprovalStepRank");
	}

	public void setRequisitionApprovalStepRank(int requisitionApprovalStepRank) {
		setAttribute("requisitionApprovalStepRank", requisitionApprovalStepRank);

	}

	public int getRequisitionApprovalStepStatus() {
		return getInt("requisitionApprovalStepStatus");
	}

	public void setRequisitionApprovalStepStatus(int requisitionApprovalStepStatus) {
		setAttribute("requisitionApprovalStepStatus", requisitionApprovalStepStatus);

	}

	public int getRequisitionApprovalStepType() {
		return getInt("requisitionApprovalStepType");
	}

	public void setRequisitionApprovalStepType(int requisitionApprovalStepType) {
		setAttribute("requisitionApprovalStepType", requisitionApprovalStepType);

	}

	public ArrayList<LoginData> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<LoginData> users) {
		this.users = users;
	}
	/**
	 * @return the userIds
	 */
	public String getUserIds() {
		return getString("userIds");
	}
	/**
	 * @param userIds the userIds to set
	 */
	public void setUserIds(String userIds) {
		setAttribute("userIds", userIds);
	}
	/**
	 * @return the userNames
	 */
	public String getUserNames() {
		return getString("userNames");
	}
	/**
	 * @param userNames the userNames to set
	 */
	public void setUserNames(String userNames) {
		setAttribute("userNames", userNames);
	}

	/**
	 * @return the requisitionApprovalTemplateId
	 */
	public int getRequisitionApprovalTemplateId() {
		return getInt("requisitionApprovalTemplateId");
	}

	/**
	 * @param requisitionApprovalTemplateId the requisitionApprovalTemplateId to set
	 */
	public void setRequisitionApprovalTemplateId(int requisitionApprovalTemplateId) {
		setAttribute("requisitionApprovalTemplateId", requisitionApprovalTemplateId);
	}
}
