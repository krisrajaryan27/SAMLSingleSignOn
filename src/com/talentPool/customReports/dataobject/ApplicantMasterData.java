package com.talentPool.customReports.dataobject;


import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;

public class ApplicantMasterData extends SimpleDataObject{

	private static final long serialVersionUID = 1L;

	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return getId("applicantId");
	}
	/**
	 * @param applicantId the applicantId to set
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
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}
	/**
	 * @return the applicantCity
	 */
	public String getApplicantCity() {
		return getString("applicantCity");
	}
	/**
	 * @param applicantCity the applicantCity to set
	 */
	public void setApplicantCity(String applicantCity) {
		setAttribute("applicantCity", applicantCity);
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
	 * @return the applicantHomePhone
	 */
	public String getApplicantHomePhone() {
		return getString("applicantHomePhone");
	}
	/**
	 * @param applicantHomePhone the applicantHomePhone to set
	 */
	public void setApplicantHomePhone(String applicantHomePhone) {
		setAttribute("applicantHomePhone", applicantHomePhone);
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
		setAttribute("applicantCellPhone", applicantCellPhone);
	}
	/**
	 * @return the applicantWorkPhone
	 */
	public String getApplicantWorkPhone() {
		return getString("applicantWorkPhone");
	}
	/**
	 * @param applicantWorkPhone the applicantWorkPhone to set
	 */
	public void setApplicantWorkPhone(String applicantWorkPhone) {
		setAttribute("applicantWorkPhone", applicantWorkPhone);
	}
	/**
	 * @return the applicantWorkingSince
	 */
	public Date getApplicantWorkingSince() {
		return getDate("applicantWorkingSince");
	}
	/**
	 * @param applicantWorkingSince the applicantWorkingSince to set
	 */
	public void setApplicantWorkingSince(Date applicantWorkingSince) {
		setAttribute("applicantWorkingSince", applicantWorkingSince);
	}
	/**
	 * @return the applicantDateCreated
	 */
	public Date getApplicantDateCreated() {
		return getDate("applicantDateCreated");
	}
	/**
	 * @param applicantDateCreated the applicantDateCreated to set
	 */
	public void setApplicantDateCreated(Date applicantDateCreated) {
		setAttribute("applicantDateCreated", applicantDateCreated);
	}
	/**
	 * @return the currentCtc
	 */
	public String getCurrentCtc() {
		return getString("currentCtc");
	}
	/**
	 * @param currentCtc the currentCtc to set
	 */
	public void setCurrentCtc(String currentCtc) {
		setAttribute("currentCtc", currentCtc);
	}
	/**
	 * @return the expectedCtc
	 */
	public String getExpectedCtc() {
		return getString("expectedCtc");
	}
	/**
	 * @param expectedCtc the expectedCtc to set
	 */
	public void setExpectedCtc(String expectedCtc) {
		setAttribute("expectedCtc", expectedCtc);
	}
	/**
	 * @return the offeredCtc
	 */
	public String getOfferedCtc() {
		return getString("offeredCtc");
	}
	/**
	 * @param offeredCtc the offeredCtc to set
	 */
	public void setOfferedCtc(String offeredCtc) {
		setAttribute("offeredCtc", offeredCtc);
	}
	/**
	 * @return the levelOffered
	 */
	public String getLevelOffered() {
		return getString("levelOffered");
	}
	/**
	 * @param levelOffered the levelOffered to set
	 */
	public void setLevelOffered(String levelOffered) {
		setAttribute("levelOffered", levelOffered);
	}
	/**
	 * @return the designationOffered
	 */
	public String getDesignationOffered() {
		return getString("designationOffered");
	}
	/**
	 * @param designationOffered the designationOffered to set
	 */
	public void setDesignationOffered(String designationOffered) {
		setAttribute("designationOffered", designationOffered);
	}
	/**
	 * @return the applicantNoticePeriod
	 */
	public String getApplicantNoticePeriod() {
		return getString("applicantNoticePeriod");
	}
	/**
	 * @param applicantNoticePeriod the applicantNoticePeriod to set
	 */
	public void setApplicantNoticePeriod(String applicantNoticePeriod) {
		setAttribute("applicantNoticePeriod", applicantNoticePeriod);
	}
	/**
	 * @return the isConfidential
	 */
	public String getIsConfidential() {
		return getString("isConfidential");
	}
	/**
	 * @param isConfidential the isConfidential to set
	 */
	public void setIsConfidential(String isConfidential) {
		setAttribute("isConfidential", isConfidential);
	}
	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return getString("employeeCode");
	}
	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		setAttribute("employeeCode", employeeCode);
	}
	/**
	 * @return the applicantHrmsCode
	 */
	public String getApplicantHrmsCode() {
		return getString("applicantHrmsCode");
	}
	/**
	 * @param applicantHrmsCode the applicantHrmsCode to set
	 */
	public void setApplicantHrmsCode(String applicantHrmsCode) {
		setAttribute("applicantHrmsCode", applicantHrmsCode);
	}
	/**
	 * @return the applicantJoined
	 */
	public String getApplicantJoined() {
		return getString("applicantJoined");
	}
	/**
	 * @param applicantJoined the applicantJoined to set
	 */
	public void setApplicantJoined(String applicantJoined) {
		setAttribute("applicantJoined", applicantJoined);
	}
	/**
	 * @return the applicantDateJoined
	 */
	public Date getApplicantDateJoined() {
		return getDate("applicantDateJoined");
	}
	/**
	 * @param applicantDateJoined the applicantDateJoined to set
	 */
	public void setApplicantDateJoined(Date applicantDateJoined) {
		setAttribute("applicantDateJoined", applicantDateJoined);
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
		setAttribute("applicantCurrentEmployer", applicantCurrentEmployer);
	}
	/**
	 * @return the applicantPositionId
	 */
	public String getApplicantPositionId() {
		return getString("applicantPositionId");
	}
	/**
	 * @param applicantPositionId the applicantPositionId to set
	 */
	public void setApplicantPositionId(String applicantPositionId) {
		setAttribute("applicantPositionId", applicantPositionId);
	}
	/**
	 * @return the positionCode
	 */
	public String getPositionCode() {
		return getString("positionCode");
	}
	/**
	 * @param positionCode the positionCode to set
	 */
	public void setPositionCode(String positionCode) {
		setAttribute("positionCode", positionCode);
	}
	/**
	 * @return the positionTitle
	 */
	public String getPositionTitle() {
		return getString("positionTitle");
	}
	/**
	 * @param positionTitle the positionTitle to set
	 */
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
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
	 * @return the applicantStepId
	 */
	public String getApplicantStepId() {
		return getString("applicantStepId");
	}
	/**
	 * @param applicantStepId the applicantStepId to set
	 */
	public void setApplicantStepId(String applicantStepId) {
		setAttribute("applicantStepId", applicantStepId);
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
	 * @return the positionStepLevel
	 */
	public String getPositionStepLevel() {
		return getString("positionStepLevel");
	}
	/**
	 * @param positionStepLevel the positionStepLevel to set
	 */
	public void setPositionStepLevel(String positionStepLevel) {
		setAttribute("positionStepLevel", positionStepLevel);
	}
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return getString("sourceId");
	}
	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		setAttribute("sourceId", sourceId);
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
	 * @return the sourceTypeId
	 */
	public String getSourceTypeId() {
		return getString("sourceTypeId");
	}
	/**
	 * @param sourceTypeId the sourceTypeId to set
	 */
	public void setSourceTypeId(String sourceTypeId) {
		setAttribute("sourceTypeId", sourceTypeId);
	}
	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return getString("sourceType");
	}
	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		setAttribute("sourceType", sourceType);
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return getString("userId");
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}
	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return getString("userName");
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}
	/**
	 * @return the yOP
	 */
	public Date getYOP() {
		return getDate("YOP");
	}
	/**
	 * @param yOP the yOP to set
	 */
	public void setYOP(Date YOP) {
		setAttribute("YOP", YOP);
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return getString("grade");
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		setAttribute("grade", grade);
	}
	/**
	 * @return the degreeTitle
	 */
	public String getDegreeTitle() {
		return getString("degreeTitle");
	}
	/**
	 * @param degreeTitle the degreeTitle to set
	 */
	public void setDegreeTitle(String degreeTitle) {
		setAttribute("degreeTitle", degreeTitle);
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return getString("branchName");
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		setAttribute("branchName", branchName);
	}
	/**
	 * @return the instituteName
	 */
	public String getInstituteName() {
		return getString("instituteName");
	}
	/**
	 * @param instituteName the instituteName to set
	 */
	public void setInstituteName(String instituteName) {
		setAttribute("instituteName", instituteName);
	}
	/**
	 * @return the flags
	 */
	public String getFlags() {
		return getString("flags");
	}
	/**
	 * @param flags the flags to set
	 */
	public void setFlags(String flags) {
		setAttribute("flags", flags);
	}
	/**
	 * @return the skills
	 */
	public String getSkills() {
		return getString("skills");
	}
	/**
	 * @param skills the skills to set
	 */
	public void setSkills(String skills) {
		setAttribute("skills", skills);
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
	 * @return the trait
	 */
	public String getTrait() {
		return getString("trait");
	}
	/**
	 * @param trait the trait to set
	 */
	public void setTrait(String trait) {
		setAttribute("trait", trait);
	}
	/**
	 * @return the custom
	 */
	public String getCustom() {
		return getString("custom");
	}
	/**
	 * @param custom the custom to set
	 */
	public void setCustom(String custom) {
		setAttribute("custom", custom);
	}

	/**
	 * @return the customFields
	 */
	public String[] getCustomFields() {
		return (String[])getAttribute("customFields");
	}
	/**
	 * @param customFields the customFields to set
	 */	
	public void setCustomFields(String[] customFields) {
		setAttribute("customFields", customFields);
	}	
	
	public Date getDateOfBirth() {
		return getDate("dateOfBirth");
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		setAttribute("dateOfBirth", dateOfBirth);
	}
	
	public String getPassportNumber() {
		return getString("passportNumber");
	}
	
	public void setPassportNumber(String passportNumber) {
		setAttribute("passportNumber", passportNumber);
	}
	
	public String getResumeType() {
		return getString("resumeType");
	}
	
	public void setResumeType(String resumeType) {
		setAttribute("resumeType", resumeType);
	}
	
	public String getOfferCode() {
		return getString("offerCode");
	}
	
	public void setOfferCode(String offerCode) {
		setAttribute("offerCode", offerCode);
	}
	
	public Date getOfferDate() {
		return getDate("offerDate");
	}
	
	public void setOfferDate(Date offerDate) {
		setAttribute("offerDate", offerDate);
	}
	
	public String getDegrees() {
		return getString("degrees");
	}
	
	public void setDegrees(String degrees) {
		setAttribute("degrees", degrees);
	}
	
	public String getEmployer2() {
		return getString("employer2");
	}
	
	public void setEmployer2(String employer2) {
		setAttribute("employer2", employer2);
	}
	
	public String getEmployer3() {
		return getString("employer3");
	}
	
	public void setEmployer3(String employer3) {
		setAttribute("employer3", employer3);
	}
	
}
