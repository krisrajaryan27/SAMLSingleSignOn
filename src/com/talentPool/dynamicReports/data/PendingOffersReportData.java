package com.talentPool.dynamicReports.data;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;


public class PendingOffersReportData extends SimpleDataObject {
	private static final long serialVersionUID = 1L;
	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return getString("applicantName");
	}
	/**
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}
	/**
	 * @return Returns the applicantWorkingSince.
	 */
	public Date getApplicantWorkingSince() {
		return getDate("applicantWorkingSince");
	}

	/**
	 * @param applicantWorkingSince
	 *            The applicantWorkingSince to set.
	 */
	public void setApplicantWorkingSince(Date applicantWorkingSince) {
		setAttribute("applicantWorkingSince", applicantWorkingSince);
	}
	/**
	 * @return the applicantCellPhone
	 */
	public String getApplicantCellPhone() {
		return getString("applicantCellPhone");
	}
	/**
	 * @param applicantCellPhone the applicantCellPhone to set
	 */
	public void setApplicantCellPhone(String applicantCellPhone) {
		setAttribute("applicantCellPhone",
				applicantCellPhone);
	}
	/**
	 * @return the applicantEmail1
	 */
	public String getApplicantEmail1() {
		return getString("applicantEmail1");
	}
	/**
	 * @param applicantEmail1 the applicantEmail1 to set
	 */
	public void setApplicantEmail1(String applicantEmail1) {
		setAttribute("applicantEmail1", applicantEmail1);
	}
	/**
	 * @return the applicantEmail2
	 */
	public String getApplicantEmail2() {
		return getString("applicantEmail2");
	}
	/**
	 * @param applicantEmail2 the applicantEmail2 to set
	 */
	public void setApplicantEmail2(String applicantEmail2) {
		setAttribute("applicantEmail2", applicantEmail2);
	}
	/**
	 * @return the applicantCurrentEmployer
	 */
	public String getApplicantCurrentEmployer() {
		return getString("applicantCurrentEmployer");
	}
	/**
	 * @param applicantCurrentEmployer the applicantCurrentEmployer to set
	 */
	public void setApplicantCurrentEmployer(String applicantCurrentEmployer) {
		setAttribute("applicantCurrentEmployer",
				applicantCurrentEmployer);
	}
	/**
	 * @return the applicantDateJoined
	 */
	public String getApplicantDateJoined() {
		return getString("applicantDateJoined");
	}
	/**
	 * @param applicantDateJoined the applicantDateJoined to set
	 */
	public void setApplicantDateJoined(String applicantDateJoined) {
		setAttribute("applicantDateJoined",
				applicantDateJoined);
	}
	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return getString("deptName");
	}
	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		setAttribute("deptName", deptName);
	}
	/**
	 * @return the subDeptName
	 */
	public String getSubDeptName() {
		return getString("subDeptName");
	}
	/**
	 * @param subDeptName the subDeptName to set
	 */
	public void setSubDeptName(String subDeptName) {
		setAttribute("subDeptName", subDeptName);
	}
	/**
	 * @return the subSubDeptName
	 */
	public String getSubSubDeptName() {
		return getString("subSubDeptName");
	}
	/**
	 * @param subSubDeptName the subSubDeptName to set
	 */
	public void setSubSubDeptName(String subSubDeptName) {
		setAttribute("subSubDeptName", subSubDeptName);
	}
	/**
	 * @return the positionDateExpiry
	 */
	public String getPositionDateExpiry() {
		return getString("positionDateExpiry");
	}
	/**
	 * @param positionDateExpiry the positionDateExpiry to set
	 */
	public void setPositionDateExpiry(String positionDateExpiry) {
		setAttribute("positionDateExpiry",
				positionDateExpiry);
	}
	/**
	 * @return the positionDateCreated
	 */
	public String getPositionDateCreated() {
		return getString("positionDateCreated");
	}
	/**
	 * @param positionDateCreated the positionDateCreated to set
	 */
	public void setPositionDateCreated(String positionDateCreated) {
		setAttribute("positionDateCreated",positionDateCreated);
	}
	/**
	 * @return the positionDateApproved
	 */
	public String getPositionDateApproved() {
		return getString("positionDateApproved");
	}
	/**
	 * @param positionDateApproved the positionDateApproved to set
	 */
	public void setPositionDateApproved(String positionDateApproved) {
		setAttribute("positionDateApproved",positionDateApproved);
	}
	/**
	 * @return the gradeName
	 */
	public String getGradeName() {
		return getString("gradeName");
	}
	/**
	 * @param gradeName the gradeName to set
	 */
	public void setGradeName(String gradeName) {
		setAttribute("gradeName", gradeName);
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return getString("locationName");
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		setAttribute("locationName", locationName);
	}
	/**
	 * @return the dueDate
	 */
	public Date getDueDate() {
		return getDate("dueDate");
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(Date dueDate) {
		setAttribute("dueDate", dueDate);
	}
	/**
	 * @return the sourceTitle
	 */
	public String getSourceTitle() {
		return getString("sourceTitle");
	}
	/**
	 * @param sourceTitle the sourceTitle to set
	 */
	public void setSourceTitle(String sourceTitle) {
		setAttribute("sourceTitle", sourceTitle);
	}
	/**
	 * @return the positionStepTitle
	 */
	public String getPositionStepTitle() {
		return getString("positionStepTitle");
	}
	/**
	 * @param positionStepTitle the positionStepTitle to set
	 */
	public void setPositionStepTitle(String positionStepTitle) {
		setAttribute("positionStepTitle", positionStepTitle);
	}
	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return getString("statusMessage");
	}
	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setStatusMessage(String statusMessage) {
		setAttribute("statusMessage", statusMessage);
	}
	/**
	 * @return the lastInteractionDate
	 */
	public String getLastInteractionDate() {
		return getString("lastInteractionDate");
	}
	/**
	 * @param lastInteractionDate the lastInteractionDate to set
	 */
	public void setLastInteractionDate(String lastInteractionDate) {
		setAttribute("lastInteractionDate",lastInteractionDate);
	}
	
	/**
	 * @return the applicantExperience
	 */
	public String getApplicantExperience() {
		return Utils.getExperienceConstructed(getApplicantWorkingSince());
	}
	
	/**
	 * @param 
	 */
	public void setApplicantExperience(String applicantExperience) {
		setAttribute("applicantExperience",applicantExperience);
	}
	
	/**
	 * @return the posCustomFieldValues
	 */
	public String getPosCustomFieldValues() {
		return getString("posCustomFieldValues");
	}
	/**
	 * @param posCustomFieldValues the posCustomFieldValues to set
	 */
	public void setPosCustomFieldValues(String posCustomFieldValues) {
		setAttribute("posCustomFieldValues",posCustomFieldValues);
	}
	/**
	 * @return the recruitmentManager
	 */
	public String getRecruitmentManager() {
		return getString("recruitmentManager");
	}
	/**
	 * @param recruitmentManager the recruitmentManager to set
	 */
	public void setRecruitmentManager(String recruitmentManager) {
		setAttribute("recruitmentManager", recruitmentManager);
	}
	/**
	 * @return the requestedBy
	 */
	public String getPositionRequestedBy() {
		return getString("positionRequestedBy");
	}
	/**
	 * @param requestedBy the requestedBy to set
	 */
	public void setPositionRequestedBy(String positionRequestedBy) {
		setAttribute("positionRequestedBy", positionRequestedBy);
	}
	
	public String getDesignationOffered() {
		return getString("designationOffered");
	}
	public void setDesignationOffered(String designationOffered) {
		setAttribute("designationOffered", designationOffered);
	}
}
