package com.talentPool.reports.views;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;

/**
 * @author Shantanu
 * @date May 12, 2008
 */

public class CandidateStatusReportView extends SimpleDataObject {
	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return getString("applicantId");
	}

	/**
	 * @param applicantId
	 *            the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return getString("applicantName");
	}

	/**
	 * @param applicantName
	 *            the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}

	/**
	 * @return the applicantCurrentEmployer
	 */
	public String getApplicantCurrentEmployer() {
		return getString("applicantCurrentEmployer");
	}

	/**
	 * @param applicantCurrentEmployer
	 *            the applicantCurrentEmployer to set
	 */
	public void setApplicantCurrentEmployer(String applicantCurrentEmployer) {
		setAttribute("applicantCurrentEmployer", applicantCurrentEmployer);
	}

	/**
	 * @return the applicantJoined
	 */
	public String getApplicantJoined() {
		return getString("applicantJoined");
	}

	/**
	 * @param applicantJoined
	 *            the applicantJoined to set
	 */
	public void setApplicantJoined(String applicantJoined) {
		setAttribute("applicantJoined", applicantJoined);
	}

	/**
	 * @return the applicantWorkingSince
	 */
	public Date getApplicantWorkingSince() {
		return getDate("applicantWorkingSince");
	}

	public String getApplicantExperience() {
		String expEmp = Utils.getExperienceConstructed(getApplicantWorkingSince());
		expEmp = getApplicantCurrentEmployer() == null ? expEmp : expEmp + " / " + getApplicantCurrentEmployer();
		return expEmp;
	}

	/**
	 * @param applicantWorkingSince
	 *            the applicantWorkingSince to set
	 */
	public void setApplicantWorkingSince(String applicantWorkingSince) {

		setAttribute("applicantWorkingSince", applicantWorkingSince);
	}

	/**
	 * @return the positionStepIdTo
	 */
	public String getPositionStepIdTo() {
		return getString("positionStepIdTo");
	}

	/**
	 * @param positionStepIdTo
	 *            the positionStepIdTo to set
	 */
	public void setPositionStepIdTo(String positionStepIdTo) {
		setAttribute("positionStepIdTo", positionStepIdTo);
	}

	/**
	 * @return the positionStepTitle
	 */
	public String getPositionStepTitle() {
		return getString("positionStepTitle");
	}

	/**
	 * @param positionStepTitle
	 *            the positionStepTitle to set
	 */
	public void setPositionStepTitle(String positionStepTitle) {
		setAttribute("positionStepTitle", positionStepTitle);
	}

	/**
	 * @return the positionTitle
	 */
	public String getPositionTitle() {
		return getString("positionTitle");
	}

	/**
	 * @param positionTitle
	 *            the positionTitle to set
	 */
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}

	/**
	 * @return the processMovedDate
	 */
	public String getProcessMovedDate() {
		return getString("processMovedDate");
	}

	/**
	 * @param processMovedDate
	 *            the processMovedDate to set
	 */
	public void setProcessMovedDate(String processMovedDate) {
		setAttribute("processMovedDate", processMovedDate);
	}

	/**
	 * @return the sourceTitle
	 */
	public String getSourceTitle() {
		return getString("sourceTitle");
	}

	/**
	 * @param sourceTitle
	 *            the sourceTitle to set
	 */
	public void setSourceTitle(String sourceTitle) {
		setAttribute("sourceTitle", sourceTitle);
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return getString("userName");
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}

	/**
	 * @return the applicantStepId.
	 */
	public String getApplicantStepId() {
		return getString("applicantStepId");
	}

	/**
	 * @param applicantStepId
	 *            the applicantStepId to set.
	 */
	public void setApplicantStepId(String applicantStepId) {
		setAttribute("applicantStepId", applicantStepId);
	}

	/**
	 * @return the applicantPositionId.
	 */
	public String getApplicantPositionId() {
		return getString("applicantPositionId");
	}

	/**
	 * @param applicantPositionId
	 *            the applicantPositionId to set.
	 */
	public void setApplicantPositionId(String applicantPositionId) {
		setAttribute("applicantPositionId", applicantPositionId);
	}

	/**
	 * 
	 * @return Returns the applicantStep.
	 */
	public String getApplicantStep() {
		return getString("applicantStep");
	}

	/**
	 * 
	 * @param applicantStep
	 *            The applicantStep to set.
	 */
	public void setApplicantStep(String applicantStep) {
		setAttribute("applicantStep", applicantStep);
	}

	/**
	 * @return Returns the responsibleUsers.
	 */
	public String getResponsibleUsers() {
		return getString("responsibleUsers");
	}

	/**
	 * @param responsibleUsers
	 *            The responsibleUsers to set.
	 */
	public void setResponsibleUsers(String responsibleUsers) {
		setAttribute("responsibleUsers", responsibleUsers);
	}

	/**
	 * @return Returns the actionRequired.
	 */
	public String getActionRequired() {
		return getString("actionRequired");
	}

	/**
	 * @param actionRequired
	 *            The actionRequired to set.
	 */
	public void setActionRequired(String actionRequired) {
		setAttribute("actionRequired", actionRequired);
	}

	/**
	 * @return the lastAction
	 */
	public String getLastAction() {
		return getString("lastAction");
	}

	/**
	 * @param lastAction
	 *            the lastAction to set
	 */
	public void setLastAction(String lastAction) {
		setAttribute("lastAction", lastAction);
	}
	
	/**
	 * @return the sourceCategory
	 */
	public String getSourceCategory() {
		return getString("sourceCategory");
	}

	/**
	 * @param sourceCategory
	 *            the sourceCategory to set
	 */
	public void setSourceCategory(String sourceCategory) {
		setAttribute("sourceCategory", sourceCategory);
	}

}
