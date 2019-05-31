package com.talentPool.reports.dataobject;

import com.talentPool.common.db.SimpleDataObject;

public class CandidateOneStopFileReportData extends SimpleDataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getApplicantId() {
		return getString("applicantId");
	}
	
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	public String getFeedbackFieldTitle() {
		return getString("feedbackFieldTitle");
	}
	
	public void setFeedbackFieldTitle(String feedbackFieldTitle) {
		setAttribute("feedbackFieldTitle", feedbackFieldTitle);
	}

	public String getTraitComment() {
		return getString("traitComment");
	}
	
	public void setTraitComment(String traitComment) {
		setAttribute("traitComment", traitComment);
	}

	/**
	 * @return Returns the serial number
	 */
	public String getSrNo() {
		return getString("srNo");
	}
	
	/**
	 * @param srNo
	 */
	public void setSrNo(String srNo) {
		setAttribute("srNo", srNo);
	}
	
	public String getRequisitionNumber() {
		return getString("requisitionNumber");
	}
	
	public void setRequisitionNumber(String requisitionNumber) {
		setAttribute("requisitionNumber", requisitionNumber);
	}
	public String getHireType() {
		return getString("hireType");
	}
	
	public void setHireType(String hireType) {
		setAttribute("hireType", hireType);
	}
	 
	/**
	 * @param source
	 */
	public void setSource(String source) {
		setAttribute("source", source);
	}
	/**
	 * @return
	 */
	public String getSource() {
		return getString("source");
	}
	/**
	 * @param sourceName
	 */
	public void setSourceName(String sourceName) {
		setAttribute("sourceName", sourceName);
	}
	/**
	 * @return
	 */
	public String getSourceName() {
		return getString("sourceName");
	}
	
	/**
	 * @return Returns the candidateName.
	 */
	public String getCandidateName() {
		return getString("candidateName");
	}

	/**
	 * @param candidateName
	 *            The candidateName to set.
	 */
	public void setCandidateName(String candidateName) {
		setAttribute("candidateName", candidateName);
	}

	public String getQualification() {
		return getString("qualification");
	}
	
	public void setQualification(String qualification) {
		setAttribute("qualification", qualification);
	}
	
	public String getPrimarySkills() {
		return getString("primarySkills");
	}
	
	public void setPrimarySkills(String primarySkills) {
		setAttribute("primarySkills", primarySkills);
	}
	public String getNoticePeriod() {
		return getString("noticePeriod");
	}
	
	public void setNoticePeriod(String noticePeriod) {
		setAttribute("noticePeriod", noticePeriod);
	}
	
	/**
	 * @return Returns the currentStatus.
	 */
	public String getCurrentStatus() {
		return getString("currentStatus");
	}

	/**
	 * @param currentStatus
	 */
	public void setCurrentStatus(String currentStatus) {
		setAttribute("currentStatus", currentStatus);
	}
	
	public String getInterviewScore() {
		return getString("interviewScore");
	}
	
	public void setInterviewScore(String interviewScore) {
		setAttribute("interviewScore", interviewScore);
	}
	public String getCommunicationScore() {
		return getString("communicationScore");
	}
	
	public void setCommunicationScore(String communicationScore) {
		setAttribute("communicationScore", communicationScore);
	}
	/**
	 * @param Location
	 */
	public void setLocation(String location) {
		setAttribute("location", location);
	}
	
	/**
	 * @return
	 */
	public String getLocation() {
		return getString("location");
	}
	
	public String getLastCompany() {
		return getString("lastCompany");
	}
	
	public void setLastCompany(String lastCompany) {
		setAttribute("lastCompany", lastCompany);
	}
	
	public String getContactDetails() {
		return getString("contactDetails");
	}
	
	public void setContactDetails(String contactDetails) {
		setAttribute("contactDetails", contactDetails);
	}
	
	/**
	 * @return offeredDesignation
	 */
	public String getOfferedDesignation() {
		return getString("offeredDesignation");
	}
	
	/**
	 * @param offeredDesignation
	 */
	public void setOfferedDesignation(String offeredDesignation) {
		setAttribute("offeredDesignation", offeredDesignation);
	}
	/**
	 * @return experience
	 */
	public String getExperience() {
		return getString("experience");
	}
	
	/**
	 * @param experience
	 */
	public void setExperience(String experience) {
		setAttribute("experience", experience);
	}
	/**
	 * @return currentCTC
	 */
	public String getCurrentCTC() {
		return getString("currentCTC");
	}
	
	/**
	 * @param currentCTC
	 */
	public void setCurrentCTC(String currentCTC) {
		setAttribute("currentCTC", currentCTC);
	}
	
	/**
	 * @return offeredCTC
	 */
	public String getOfferedCTC() {
		return getString("offeredCTC");
	}
	
	/**
	 * @param offeredCTC
	 */
	public void setOfferedCTC(String offeredCTC) {
		setAttribute("offeredCTC", offeredCTC);
	}
	
	public String getHikePercent() {
		return getString("hikePercent");
	}
	
	public void setHikePercent(String hikePercent) {
		setAttribute("hikePercent", hikePercent);
	}
	
	public String getNoticePeriodBuyOutDays() {
		return getString("noticePeriodBuyOutDays");
	}
	
	public void setNoticePeriodBuyOutDays(String noticePeriodBuyOutDays) {
		setAttribute("noticePeriodBuyOutDays", noticePeriodBuyOutDays);
	}
	public String getJoiningBonus() {
		return getString("joiningBonus");
	}
	
	public void setJoiningBonus(String joiningBonus) {
		setAttribute("joiningBonus", joiningBonus);
	}
	
	public String getContractorCost() {
		return getString("contractorCost");
	}
	
	public void setContractorCost(String contractorCost) {
		setAttribute("contractorCost", contractorCost);
	}
	
	public String getRecruiterName() {
		return getString("recruiterName");
	}
	
	public void setRecruiterName(String recruiterName) {
		setAttribute("recruiterName", recruiterName);
	}
	
	public String getBand() {
		return getString("band");
	}
	public void setBand(String band) {
		setAttribute("band", band);
	}
	public String getOfferedApprovalDate() {
		return getString("offeredApprovalDate");
	}
	public void setOfferedApprovalDate(String offeredApprovalDate) {
		setAttribute("offeredApprovalDate", offeredApprovalDate);
	}
	
	public String getOfferedDate() {
		return getString("offeredDate");
	}
	public void setOfferedDate(String offeredDate) {
		setAttribute("offeredDate", offeredDate);
	}
	
	public String getExpectedDOJ() {
		return getString("expectedDOJ");
	}
	public void setExpectedDOJ(String expectedDOJ) {
		setAttribute("expectedDOJ", expectedDOJ);
	}
	public String getEmpID() {
		return getString("empID");
	}
	public void setEmpID(String empID) {
		setAttribute("empID", empID);
	}
	public String getDoj() {
		return getString("doj");
	}
	public void setDoj(String doj) {
		setAttribute("doj", doj);
	}
	
	//Position related fields
	
	public String getPositionId() {
		return getString("positionId");
	}
	
	public void setPositionId(String positionId) {
		setAttribute("positionId", positionId);
	}
	public String getRequisitionRaiseDate() {
		return getString("requisitionRaiseDate");
	}
	
	public void setRequisitionRaiseDate(String requisitionRaiseDate) {
		setAttribute("requisitionRaiseDate", requisitionRaiseDate);
	}
	
	public String getSubBU() {
		return getString("subBU");
	}
	
	public void setSubBU(String subBU) {
		setAttribute("subBU", subBU);
	}
	public String getRequisitionAllocationDate() {
		return getString("requisitionAllocationDate");
	}
	
	public void setRequisitionAllocationDate(String requisitionAllocationDate) {
		setAttribute("requisitionAllocationDate", requisitionAllocationDate);
	}
	
	public String getRequisitionStatus() {
		return getString("requisitionStatus");
	}
	public void setRequisitionStatus(String requisitionStatus) {
		setAttribute("requisitionStatus", requisitionStatus);
	}
	
	
	
	//custom fields
	public String getLateralCampusIntern() {
		return getString("lateralCampusIntern");
	}
	public void setLateralCampusIntern(String lateralCampusIntern) {
		setAttribute("lateralCampusIntern", lateralCampusIntern);
	}
	
	public String getLocalOutstation() {
		return getString("localOutstation");
	}
	public void setLocalOutstation(String localOutstation) {
		setAttribute("localOutstation", localOutstation);
	}
	public String getTier() {
		return getString("tier");
	}
	public void setTier(String tier) {
		setAttribute("tier", tier);
	}
	
	public String getGender() {
		return getString("gender");
	}
	public void setGender(String gender) {
		setAttribute("gender", gender);
	}
}
