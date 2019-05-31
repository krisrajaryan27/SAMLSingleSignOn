/**
 * 
 */
package com.talentPool.applicant.dataobject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.utils.HTMLUtils.HTMLConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.dataobject.CustomFieldTable;

/**
 * @author shivprasad
 * 
 */
public class ApplicantData extends SimpleDataObject {
	private ArrayList<CustomFieldData> customFields;
	private Map<String,CustomFieldData> customFieldsMap;
	private ArrayList<EducationalData> educationalDetails;
	private ArrayList<EmploymentHistoryData> employmentHistoryDetails;
	private List<CustomFieldTable> customTables;

	public ApplicantData() {
	}
	
	/**
	 * @return Returns the applicantId.
	 */
	public String getApplicantId() {
		return getId("applicantId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	/**
	 * @return Returns the applicantName.
	 */
	public String getApplicantName() {
		return getString("applicantName");
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}

	/**
	 * @return Returns the applicantCellPhone.
	 */
	public String getApplicantCellPhone() {
		return getString("applicantCellPhone");
	}

	/**
	 * @param applicantCellPhone
	 *            The applicantCellPhone to set.
	 */
	public void setApplicantCellPhone(String applicantCellPhone) {
		setAttribute("applicantCellPhone", applicantCellPhone);
	}

	/**
	 * @return Returns the applicantCellPhoneIsInvalid.
	 */
	public String getApplicantCellPhoneIsInvalid() {
		return getString("applicantCellPhoneIsInvalid");
	}

	/**
	 * @param applicantCellPhoneIsInvalid
	 *            The applicantCellPhoneIsInvalid to set.
	 */
	public void setApplicantCellPhoneIsInvalid(String applicantCellPhoneIsInvalid) {
		setAttribute("applicantCellPhoneIsInvalid", applicantCellPhoneIsInvalid);
	}

	/**
	 * @return Returns the applicantCity.
	 */
	public String getApplicantCity() {
		return getString("applicantCity");
	}

	/**
	 * @param applicantCity
	 *            The applicantCity to set.
	 */
	public void setApplicantCity(String applicantCity) {
		setAttribute("applicantCity", applicantCity);
	}

	/**
	 * @return Returns the applicantemail1.
	 */
	public String getApplicantEmail1() {
		return getString("applicantEmail1");
	}

	/**
	 * @param applicantemail1
	 *            The applicantemail1 to set.
	 */
	public void setApplicantEmail1(String applicantEmail1) {
		setAttribute("applicantEmail1", applicantEmail1);
	}

	/**
	 * @return Returns the applicantemail2.
	 */
	public String getApplicantEmail2() {
		return getString("applicantEmail2");
	}

	/**
	 * @param applicantemail2
	 *            The applicantemail2 to set.
	 */
	public void setApplicantEmail2(String applicantEmail2) {
		setAttribute("applicantEmail2", applicantEmail2);
	}

	/**
	 * @return Returns the applicantHomePhone.
	 */
	public String getApplicantHomePhone() {
		return getString("applicantHomePhone");
	}

	/**
	 * @param applicantHomePhone
	 *            The applicantHomePhone to set.
	 */
	public void setApplicantHomePhone(String applicantHomePhone) {
		setAttribute("applicantHomePhone", applicantHomePhone);
	}

	/**
	 * @return Returns the applicantHomePhoneIsInvalid.
	 */
	public String getApplicantHomePhoneIsInvalid() {
		return getString("applicantHomePhoneIsInvalid");
	}

	/**
	 * @param applicantHomePhone
	 *            The applicantHomePhone to set.
	 */
	public void setApplicantHomePhoneIsInvalid(String applicantHomePhoneIsInvalid) {
		setAttribute("applicantHomePhoneIsInvalid", applicantHomePhoneIsInvalid);
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
	 * @return Returns the applicantWorkPhone.
	 */
	public String getApplicantWorkPhone() {
		return getString("applicantWorkPhone");
	}

	/**
	 * @param applicantWorkPhone
	 *            The applicantWorkPhone to set.
	 */
	public void setApplicantWorkPhone(String applicantWorkPhone) {
		setAttribute("applicantWorkPhone", applicantWorkPhone);
	}

	/**
	 * @return Returns the applicantWorkPhoneIsInvalid.
	 */
	public String getApplicantWorkPhoneIsInvalid() {
		return getString("applicantWorkPhoneIsInvalid");
	}

	/**
	 * @param applicantWorkPhoneIsInvalid
	 *            The applicantWorkPhoneIsInvalid to set.
	 */
	public void setApplicantWorkPhoneIsInvalid(String applicantWorkPhoneIsInvalid) {
		setAttribute("applicantWorkPhoneIsInvalid", applicantWorkPhoneIsInvalid);
	}

	public ArrayList getWorkExperienceDetails() {
		return (ArrayList) getAttribute("workExperienceDetails");
	}

	public void setWorkExperienceDetails(ArrayList workExperienceDetails) {
		setAttribute("workExperienceDetails", workExperienceDetails);
	}

	/**
	 * @return Returns the applicantPrimarySkills.
	 */
	public ArrayList getApplicantSkills() {
		return (ArrayList) getAttribute("applicantSkills");
	}

	/**
	 * @param applicantPrimarySkills
	 *            The applicantPrimarySkills to set.
	 */
	public void setApplicantSkills(ArrayList applicantSkills) {
		setAttribute("applicantSkills", applicantSkills);
	}

	/**
	 * @return Returns the applicantSourceTitle.
	 */
	public String getApplicantSourceTitle() {
		return getString("applicantSourceTitle");
	}

	/**
	 * @param applicantSourceTitle
	 *            The applicantSourceTitle to set.
	 */
	public void setApplicantSourceTitle(String applicantSourceTitle) {
		setAttribute("applicantSourceTitle", applicantSourceTitle);
	}

	/**
	 * @return Returns the applicantPositionCode.
	 */
	public String getApplicantPositionCode() {
		return getString("applicantPositionCode");
	}

	/**
	 * @param applicantPositionCode
	 *            The applicantPositionCode to set.
	 */
	public void setApplicantPositionCode(String applicantPositionCode) {
		setAttribute("applicantPositionCode", applicantPositionCode);
	}
	
	/**
	 * @return Returns the applicantPositionTitle.
	 */
	public String getApplicantPositionTitle() {
		return getString("applicantPositionTitle");
	}

	/**
	 * @param applicantPositionTitle
	 *            The applicantPositionTitle to set.
	 */
	public void setApplicantPositionTitle(String applicantPositionTitle) {
		setAttribute("applicantPositionTitle", applicantPositionTitle);
	}

	
	/**
	 * @return Returns the applicantStepTitle.
	 */
	public String getApplicantStepTitle() {
		return getString("applicantStepTitle");
	}

	/**
	 * @param applicantStepTitle
	 *            The applicantStepTitle to set.
	 */
	public void setApplicantStepTitle(String applicantStepTitle) {
		setAttribute("applicantStepTitle", applicantStepTitle);
	}

	/**
	 * @return Returns the applicantPositionId.
	 */
	public String getApplicantPositionId() {
		return getString("applicantPositionId");
	}

	/**
	 * @param applicantPositionId
	 *            The applicantPositionId to set.
	 */
	public void setApplicantPositionId(String applicantPositionId) {
		setAttribute("applicantPositionId", applicantPositionId);
	}

	/**
	 * @return Returns the applicantStepId.
	 */
	public String getApplicantStepId() {
		return getString("applicantStepId");
	}

	/**
	 * @param applicantStepId
	 *            The applicantStepId to set.
	 */
	public void setApplicantStepId(String applicantStepId) {
		setAttribute("applicantStepId", applicantStepId);
	}

	public int getApplicantSourceId() {
		return getInt("applicantSourceId");
	}

	public void setApplicantSourceId(int applicantSourceId) {
		setAttribute("applicantSourceId", new Integer(applicantSourceId));
	}

	public void setApplicantOriginalResumePath(String applicantOriginalResumePath) {
		setAttribute("applicantOriginalResumePath", applicantOriginalResumePath);
	}

	public String getApplicantOriginalResumePath() {
		return getString("applicantOriginalResumePath");
	}

	public void setApplicantOriginalDocPath(String applicantOriginalDocPath) {
		setAttribute("applicantOriginalDocPath", applicantOriginalDocPath);
	}

	public String getApplicantOriginalDocPath() {
		return getString("applicantOriginalDocPath");
	}

	public void setApplicantTextResume(String applicantTextResume) {
		setAttribute("applicantTextResume", applicantTextResume);
	}

	public String getApplicantTextResume() {
		return getString("applicantTextResume");
	}

	public void setApplicantCurrentEmployer(String applicantCurrentEmployer) {
		setAttribute("applicantCurrentEmployer", applicantCurrentEmployer);
	}

	public String getApplicantCurrentEmployer() {
		return getString("applicantCurrentEmployer");
	}

	public void setApplicantPassport(String applicantPassport) {
		setAttribute("applicantPassport", applicantPassport);
	}

	public String getApplicantPassport() {
		return getString("applicantPassport");
	}

	/**
	 * @return Returns the applicantDateImported.
	 */
	public Date getApplicantDateImported() {
		return getDate("applicantDateImported");
	}

	/**
	 * @param applicantDateImported
	 *            The applicantDateImported to set.
	 */
	public void setApplicantDateImported(Date applicantDateImported) {
		setAttribute("applicantDateImported", applicantDateImported);
	}

	public void setApplicantJoined(String applicantJoined) {
		setAttribute("applicantJoined", applicantJoined);
	}

	public String getApplicantJoined() {
		return getString("applicantJoined");
	}

	public void setApplicantDateJoined(java.util.Date applicantDateJoined) {
		setAttribute("applicantDateJoined", applicantDateJoined);
	}

	public Date getApplicantDateJoined() {
		try {
			return getDate("applicantDateJoined");
		} catch (Exception e) {
			// no need to check
		}
		return null;
	}
	
	public String getApplicantDateJoinedToDisplay() {
		return DateUtils.getSystemDateFormat(getApplicantDateJoined());
	}

	public String getSkillsString() {
		return getString("skillsString");
	}

	public String getAliasesString() {
		return getString("aliasesString");
	}

	public String getDegreeTitle() {
		return getString("degreeTitle");
	}

	/**
	 * @param institute
	 *            The institute to set.
	 */
	public void setDegreeTitle(String degreeTitle) {
		setAttribute("degreeTitle", degreeTitle);
	}

	public String getInstitute() {
		return getString("institute");
	}

	/**
	 * @param institute
	 *            The institute to set.
	 */
	public void setInstitute(String institute) {
		setAttribute("institute", institute);
	}

	public String getMajor() {
		return getString("major");
	}

	/**
	 * @param major
	 *            The major to set.
	 */
	public void setMajor(String major) {
		setAttribute("major", major);
	}

	public Date getYearOfPassing() {
		return getDate("yearOfPassing");
	}

	/**
	 * @param yearOfPassing
	 *            The yearOfPassing to set.
	 */
	public void setYearOfPassing(Date yearOfPassing) {
		setAttribute("yearOfPassing", yearOfPassing);
	}

	public String getUserId() {
		return getId("userId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setUserId(String userId) {
		setAttribute("userId", userId);
	}

	/**
	 * @return Returns the stepScheduled.
	 */
	public String getStepScheduled() {
		return getString("stepScheduled");
	}

	/**
	 * @param stepScheduled
	 *            the stepScheduled to set
	 */
	public void setStepScheduled(String stepScheduled) {
		setAttribute("stepScheduled", stepScheduled);
	}

	public void setGrade(String grade) {
		setAttribute("grade", grade);
	}

	public String getGrade() {
		return getString("grade");
	}

	// appointmentId

	public Date getAppointmentDate() {
		try {
			return getDate("appointmentDate");			
		} catch (ClassCastException e) {
			TPLogger.getLogger().error("ClassCastException: appointmentDate cannot be fetched in DATETIME Format.");
		}
		return null;
	}

	public void setAppointmentDate(Date appointmentDate) {
		setAttribute("appointmentDate", appointmentDate);
	}
	
	public String getAppointmentDateToDisplay() {
		return DateUtils.getSystemDateFormat(getAppointmentDate());
	}

	public void setUsersResponsible(String usersResponsible) {
		setAttribute("usersResponsible", usersResponsible);
	}

	public String getUsersResponsible() {
		return getString("usersResponsible");
	}

	public void setActionRequired(String actionRequired) {
		setAttribute("actionRequired", actionRequired);
	}

	public String getActionRequired() {
		return getString("actionRequired");
	}

	public void setCurrentStatus(String currentStatus) {
		setAttribute("currentStatus", currentStatus);
	}

	public String getCurrentStatus() {
		return getString("currentStatus");
	}

	/**
	 * @return Returns the applicantExperience.
	 */
	public String getApplicantExperience() {
		return Utils.getExperienceConstructed(getApplicantWorkingSince());
	}

	/**
	 * @param applicantExperience
	 *            The applicantExperience to set.
	 */
	public void setApplicantExperience(String applicantExperience) {
		setAttribute("applicantExperience", applicantExperience);
	}
	public String getApplicantTotalExperience() {
		return getString("applicantExperience");
	}
	public String getPositionStepLevel() {
		return getString("positionStepLevel");
	}

	public void setCurrentCTC(String currentCTC) {
		setAttribute("currentCTC", currentCTC);
	}

	public String getCurrentCTC() {
		return getString("currentCTC");
	}

	public void setExpectedCTC(String expectedCTC) {
		setAttribute("expectedCTC", expectedCTC);
	}

	public String getExpectedCTC() {
		return getString("expectedCTC");
	}

	public void setCurrentCTCDate(Date currentCTCDate) {
		setAttribute("currentCTCDate", currentCTCDate);
	}

	public Date getCurrentCTCDate() {
		try {
			return getDate("currentCTCDate");			
		} catch (Exception e) {
			TPLogger.getLogger().error("ClassCastException: currentCTCDate cannot be fetched in DATETIME Format.");
		}
		return null;
	}
	
	/**
	 * @return currentCTCDate to display in System Date Format 
	 */
	public String getCurrentCTCDateToDisplay() {
		return DateUtils.getSystemDateFormat(getCurrentCTCDate());
	}
	
	public void setExpectedCTCDate(Date expectedCTCDate) {
		setAttribute("expectedCTCDate", expectedCTCDate);
	}

	public Date getExpectedCTCDate() {
		try {
			return getDate("expectedCTCDate");
		} catch (Exception e) {
			TPLogger.getLogger().error("ClassCastException: expectedCTCDate cannot be fetched in DATETIME Format.");
		}
		return null;
	}
	
	/**
	 * @return expectedCTCDate to display in System Date Format
	 */
	public String getExpectedCTCDateToDisplay() {
		return DateUtils.getSystemDateFormat(getExpectedCTCDate());
	}
	
	/**
	 * @return currentCTC to display in System Date Format 
	 */
	public String getCurrentCTCToDisplay() {
		if(getCurrentCTC()!=null){
			if(GlobalConstants.CONFIDENTIAL_CHARACTER.equals(getCurrentCTC()))
				return GlobalConstants.CONFIDENTIAL_CHARACTER;
			else
				return getCurrentCTC() + " as  on "+getCurrentCTCDateToDisplay();
		}
		else
			return "";
	}
	
	/**
	 * @return currentCTC to display in System Date Format 
	 */
	public String getExpectedCTCToDisplay() {
		if(getExpectedCTC()!=null) {
			if(GlobalConstants.CONFIDENTIAL_CHARACTER.equals(getExpectedCTC()))
				return GlobalConstants.CONFIDENTIAL_CHARACTER;
			else
				return getExpectedCTC() + " as  on "+getExpectedCTCDateToDisplay();
		}
		else
			return "";
	}
	
	/**
	 * @param currentBasic
	 */
	public void setCurrentBasic(String currentBasic) {
		setAttribute("currentBasic", currentBasic);
	}

	/**
	 * @return
	 */
	public String getCurrentBasic() {
		return getString("currentBasic");
	}

	/**
	 * @return the customFields
	 */
	public ArrayList<CustomFieldData> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customFields
	 *            the customFields to set
	 */
	public void setCustomFields(ArrayList<CustomFieldData> customFields) {
		this.customFields = customFields;
	}
	
	/**
	 * @return the educationalDetails
	 */
	public ArrayList<EducationalData> getEducationalDetails() {
		return educationalDetails;
	}
	
	/**
	 * Builds a string of education details
	 * @return the educationalDetails
	 */
	public String getFullEducationalDetailsFormatted() {
		StringBuilder sb = new StringBuilder();
		if(getEducationalDetails()!=null){
			for (EducationalData eData : getEducationalDetails()) {
				sb.append(eData.getFormattedEducation()).append(HTMLConstants.BR);
			}
		}else {
			return "";
		}
		return sb.toString();
	}
	
	/**
	 * Builds a string of education details.
	 * Formats to only latest two education and with width of width
	 * @return the educationalDetails
	 */
	public String getEducationalDetailsFormatted(int noOfEdu, int width) {
		StringBuilder sb = new StringBuilder();
		ArrayList<EducationalData> eduDetails = getEducationalDetails();
		if(!Utils.isListEmptyOrNull(eduDetails)){
			int length = Math.min(noOfEdu, eduDetails.size());
			for (int i = 0; i < length; i++) {
				EducationalData eData = eduDetails.get(i);
				String formattedEdu = eData.getFormattedEducation();
				if(formattedEdu.length()>width){
					sb.append(formattedEdu.substring(0,width-3)).append("...").append(HTMLConstants.BR);
				}else{
					sb.append(eData.getFormattedEducation()).append(HTMLConstants.BR);					
				}
			}
		}else {
			return "";
		}
		return sb.toString();
	}

	/**
	 * @param educationalDetails
	 *            the educationalDetails to set
	 */
	public void setEducationalDetails(ArrayList<EducationalData> educationalDetails) {
		this.educationalDetails = educationalDetails;
	}

	/**
	 * @return Returns the applicantDateImported.
	 */
	public Date getResumeDateUpdated() {
		return getDate("resumeDateUpdated");
	}

	/**
	 * @param applicantDateImported
	 *            The applicantDateImported to set.
	 */
	public void setResumeDateUpdated(Date resumeDateUpdated) {
		setAttribute("resumeDateUpdated", resumeDateUpdated);
	}

	public String getFlagIds() {
		return getString("flagIds");
	}

	public String getSourceEmail() {
		return getString("sourceEmail");
	}

	public String getSourceMobile() {
		return getString("sourceMobile");
	}

	public String getNoticePeriod() {
		return getString("noticePeriod");
	}

	public void setNoticePeriod(String noticePeriod) {
		setAttribute("noticePeriod", noticePeriod);
	}
	
	public String getCtcOffered() {
		return getString("ctcOffered");
	}

	public void setCtcOffered(String ctcOffered) {
		setAttribute("ctcOffered", ctcOffered);
	}
	
	public String getLevelOffered() {
		return getString("levelOffered");
	}

	public void setLevelOffered(String levelOffered) {
		setAttribute("levelOffered", levelOffered);
	}
	
	public String getInputSalaryVariable() {
		return getString("inputSalaryVariable");
	}

	public void setInputSalaryVariable(String inputSalaryVariable) {
		setAttribute("inputSalaryVariable", inputSalaryVariable);
	}
	
	public String getDesignationOffered() {
		return getString("designationOffered");
	}

	public void setDesignationOffered(String designationOffered) {
		setAttribute("designationOffered", designationOffered);
	}
	
	public String getVendorId() {
		return getString("vendorId");
	}
	public void setVendorId(String vendorId){
		setAttribute("vendorId", vendorId);
	}
	
	public String getSkillIds() {
		return getString("skillIds");
	}

	public void setSkillIds(String skillIds) {
		setAttribute("skillIds", skillIds);
	}
	
	public String getSourceTypeId() {
		return getString("sourceTypeId");
	}

	public void setSourceTypeId(String sourceTypeId) {
		setAttribute("sourceTypeId", sourceTypeId);
	}

	public String getRejectReasonIds() {
		return getString("rejectReasonIds");
	}
	public String getRejectStepLevels() {
		return getString("rejectStepLevels");
	}
	/**
	 * @return the applicantLastInteractionDate
	 */
	public Date getApplicantLastInteractionDate() {
		return getDate("applicantLastInteractionDate");
	}

	/**
	 * @param applicantLastInteractionDate the applicantLastInteractionDate to set
	 */
	public void setApplicantLastInteractionDate(Date applicantLastInteractionDate) {
		setAttribute("applicantLastInteractionDate", applicantLastInteractionDate);
	}
	
	public String getApplicantIsFresher() {
		return getString("applicantIsFresher");
	}

	public void setApplicantIsFresher(String applicantIsFresher) {
		setAttribute("applicantIsFresher", applicantIsFresher);
	}
	
	public String getIsConfidential() {
		return getString("isConfidential");
	}
	
	public void setIsConfidential(String isConfidential) {
		setAttribute("isConfidential", isConfidential);
	}
	
	public String getInternalLink() {
		return getString("internalLink");
	}
	
	public void setInternalLink(String internalLink) {
		setAttribute("internalLink", internalLink);
	}
	
	public String getEmployeeCode() {
		return getString("employeeCode");
	}
	
	public void setEmployeeCode(String employeeCode) {
		setAttribute("employeeCode", employeeCode);
	}
	
	public String getBasicOffered() {
		return getString("basicOffered");
	}

	public void setBasicOffered(String basicOffered) {
		setAttribute("basicOffered", basicOffered);
	}
	
	public String getApplicantStatus() {
		return getString("applicantStatus");
	}

	public void setApplicantStatus(String applicantStatus) {
		setAttribute("applicantStatus", applicantStatus);
	}

	/**
	 * @return the customFieldsMap
	 */
	public Map<String, CustomFieldData> getCustomFieldsMap() {
		return customFieldsMap;
	}

	/**
	 * @param customFieldsMap the customFieldsMap to set
	 */
	public void setCustomFieldsMap(Map<String, CustomFieldData> customFieldsMap) {
		this.customFieldsMap = customFieldsMap;
	}
	
	public String getApplicantHRMSCode() {
		return getString("applicantHRMSCode");
	}
	
	public void setApplicantHRMSCode(String applicantHRMSCode) {
		setAttribute("applicantHRMSCode", applicantHRMSCode);
	}
	
	public Date getDateOfBirth() {
		return getDate("dateOfBirth");
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
		setAttribute("dateOfBirth", dateOfBirth);
	}
	
	public String getDateOfBirthToDisplay() {
		return DateUtils.getSystemDateFormat(getDateOfBirth());
	}
	
	public String getDateOfBirthForDisplay() {
		return DateUtils.getSystemDateFormatToDisplay(getDateOfBirth());
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
	
	public String getResumeTypeId() {
		return getString("resumeTypeId");
	}
	
	public void setResumeTypeId(String resumeTypeId) {
		setAttribute("resumeTypeId", resumeTypeId);
	}

	/**
	 * @return the employmentHistoryDetails
	 */
	public ArrayList<EmploymentHistoryData> getEmploymentHistoryDetails() {
		return employmentHistoryDetails;
	}

	/**
	 * @param employmentHistoryDetails the employmentHistoryDetails to set
	 */
	public void setEmploymentHistoryDetails(ArrayList<EmploymentHistoryData> employmentHistoryDetails) {
		this.employmentHistoryDetails = employmentHistoryDetails;
	}

	public String getFullEmploymentHistoryDetailsFormatted() {
		StringBuilder sb = new StringBuilder();
		if(getEmploymentHistoryDetails()!=null){
			for (EmploymentHistoryData eData : getEmploymentHistoryDetails()) {
				sb.append(eData.getFormattedEmploymentHistory()).append(HTMLConstants.BR);
			}
		}else {
			return "";
		}
		return sb.toString();
	}
	
	public String getJoiningBonus() {
		return getString("joiningBonus");
	}

	public void setJoiningBonus(String joiningBonus) {
		setAttribute("joiningBonus", joiningBonus);
	}
	
	public String getVariableOffered() {
		return getString("variableOffered");
	}

	public void setVariableOffered(String variableOffered) {
		setAttribute("variableOffered", variableOffered);
	}
	public String getCategory() {
		return getString("category");
	}

	public void setCategory(String category) {
		setAttribute("category", category);
	}
	public String getSubCategory() {
		return getString("subCategory");
	}

	public void setSubCategory(String subCategory) {
		setAttribute("subCategory", subCategory);
	}
	public String getIsEmployeeApply() {
		return getString("isEmployeeApply");
	}

	public void setIsEmployeeApply(String isEmployeeApply) {
		setAttribute("isEmployeeApply", isEmployeeApply);
	}
	
	public String getCandidatePortalId() {
		return getString("candidatePortalId");
	}
	
	public void setCandidatePortalId(String candidateId){
		setAttribute("candidatePortalId", candidateId);
	}

	public List<CustomFieldTable> getCustomTables() {
		return customTables;
	}

	public void setCustomTables(List<CustomFieldTable> customTables) {
		this.customTables = customTables;
	}
	
	public String getProfilePicPath() {
		return getString("profilePicPath");
	}
	
	public void setProfilePicPath(String profilePicPath) {
		setAttribute("profilePicPath", profilePicPath);
	}
	
	public String getApplicantDeclaration() {
		return getString("applicantDeclaration");
	}

	/**
	 * @param applicantCellPhoneIsInvalid
	 *            The applicantCellPhoneIsInvalid to set.
	 */
	public void setApplicantDeclaration(String applicantDeclaration) {
		setAttribute("applicantDeclaration", applicantDeclaration);
	}
}