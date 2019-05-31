/**
 * 
 */
package com.talentPool.websiteservice.dataobject;

import java.util.ArrayList;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.talentPool.common.utils.Utils;

/**
 * @author pallavi
 * 
 */
public class WapplicantData {
	private String applicantId;
	private String applicantName;
	private Date applicantWorkingSince;
	@SerializedName("applicantCurrentEmployer")
	private String currentEmployer;
	@SerializedName("applicantPositionTitle")
	private String positionTitle;
	@SerializedName("applicantDateImported")
	private Date dateUploaded;
	private String currentStatus;
	@SerializedName("applicantCity")
	private String currentLocation;
	@SerializedName("applicantOriginalResumePath")
	private String originalResumePath;
	private boolean applicantJoined;
	private String currentCTC;
	private String expectedCTC;
	private Date currentCTCDate;
	private Date expectedCTCDate;
	private String applicantPositionId;
	private String applicantStepId;
	@SerializedName("skillsString")
	private String skills;
	private String applicantEmail1;
	private String applicantEmail2;
	private String applicantCellPhone;
	private String applicantHomePhone;
	private String applicantWorkPhone;
	private String noticePeriod;
	private String uuid;
	@SerializedName("applicantSourceTitle")
	private String source;
	 private String userName;
	 private String note;
	 @SerializedName("applicantExperience")
	 private String totalExperience;
	 @SerializedName("passportNumber")
	 private String passport;
	 private String dateOfBirth;
	private String resumeType;
	@SerializedName("educationDetails")
	private ArrayList<WeducationalData> educationalDetails;
	private ArrayList<WcustomFieldData> customFields;
	@SerializedName("employmentHistoryDetails")
	private ArrayList<WemploymentHistoryData> empHistoryData;
	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId
	 *            the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return applicantName;
	}

	/**
	 * @param applicantName
	 *            the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * @return the applicantWorkingSince
	 */
	public Date getApplicantWorkingSince() {
		return applicantWorkingSince;
	}

	/**
	 * @param applicantWorkingSince
	 *            the applicantWorkingSince to set
	 */
	public void setApplicantWorkingSince(Date applicantWorkingSince) {
		this.applicantWorkingSince = applicantWorkingSince;
	}

	/**
	 * @return the currentEmployer
	 */
	public String getCurrentEmployer() {
		return currentEmployer;
	}

	/**
	 * @param currentEmployer
	 *            the currentEmployer to set
	 */
	public void setCurrentEmployer(String currentEmployer) {
		this.currentEmployer = currentEmployer;
	}

	/**
	 * @return the currentStatus
	 */
	public String getCurrentStatus() {
		return currentStatus;
	}

	/**
	 * @param currentStatus
	 *            the currentStatus to set
	 */
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	/**
	 * @return the dateUploaded
	 */
	public Date getDateUploaded() {
		return dateUploaded;
	}

	/**
	 * @param dateUploaded
	 *            the dateUploaded to set
	 */
	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}

	/**
	 * @return the positionTitle
	 */
	public String getPositionTitle() {
		return positionTitle;
	}

	/**
	 * @param positionTitle
	 *            the positionTitle to set
	 */
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getExperience() {
		return Utils.getExperienceConstructed(applicantWorkingSince);
	}

	/**
	 * @return the applicantJoined
	 */
	public boolean isApplicantJoined() {
		return applicantJoined;
	}

	/**
	 * @param applicantJoined the applicantJoined to set
	 */
	public void setApplicantJoined(boolean applicantJoined) {
		this.applicantJoined = applicantJoined;
	}

	/**
	 * @return the cureentCTCDate
	 */
	public Date getCurrentCTCDate() {
		return currentCTCDate;
	}

	/**
	 * @param cureentCTCDate the cureentCTCDate to set
	 */
	public void setCurrentCTCDate(Date currentCTCDate) {
		this.currentCTCDate = currentCTCDate;
	}

	/**
	 * @return the currentCTC
	 */
	public String getCurrentCTC() {
		return currentCTC;
	}

	/**
	 * @param currentCTC the currentCTC to set
	 */
	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}

	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the expectedCTC
	 */
	public String getExpectedCTC() {
		return expectedCTC;
	}

	/**
	 * @param expectedCTC the expectedCTC to set
	 */
	public void setExpectedCTC(String expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	/**
	 * @return the expectedCTCDate
	 */
	public Date getExpectedCTCDate() {
		return expectedCTCDate;
	}

	/**
	 * @param expectedCTCDate the expectedCTCDate to set
	 */
	public void setExpectedCTCDate(Date expectedCTCDate) {
		this.expectedCTCDate = expectedCTCDate;
	}

	/**
	 * @return the originalResumePath
	 */
	public String getOriginalResumePath() {
		return originalResumePath;
	}

	/**
	 * @param originalResumePath the originalResumePath to set
	 */
	public void setOriginalResumePath(String originalResumePath) {
		this.originalResumePath = originalResumePath;
	}

	/**
	 * @return the skills
	 */
	public String getSkills() {
		return skills;
	}

	/**
	 * @param skills the skills to set
	 */
	public void setSkills(String skills) {
		this.skills = skills;
	}

	/**
	 * @return the customFields
	 */
	public ArrayList<WcustomFieldData> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customFields the customFields to set
	 */
	public void setCustomFields(ArrayList<WcustomFieldData> customFields) {
		this.customFields = customFields;
	}

	/**
	 * @return the educationalDetails
	 */
	public ArrayList<WeducationalData> getEducationalDetails() {
		return educationalDetails;
	}

	/**
	 * @param educationalDetails the educationalDetails to set
	 */
	public void setEducationalDetails(ArrayList<WeducationalData> educationalDetails) {
		this.educationalDetails = educationalDetails;
	}

	/**
	 * @return the applicantPositionId
	 */
	public String getApplicantPositionId() {
		return applicantPositionId;
	}

	/**
	 * @param applicantPositionId the applicantPositionId to set
	 */
	public void setApplicantPositionId(String applicantPositionId) {
		this.applicantPositionId = applicantPositionId;
	}

	/**
	 * @return the applicantStepId
	 */
	public String getApplicantStepId() {
		return applicantStepId;
	}

	/**
	 * @param applicantStepId the applicantStepId to set
	 */
	public void setApplicantStepId(String applicantStepId) {
		this.applicantStepId = applicantStepId;
	}

	/**
	 * @return the applicantCellPhone
	 */
	public String getApplicantCellPhone() {
		return applicantCellPhone;
	}

	/**
	 * @param applicantCellPhone the applicantCellPhone to set
	 */
	public void setApplicantCellPhone(String applicantCellPhone) {
		this.applicantCellPhone = applicantCellPhone;
	}

	/**
	 * @return the applicantEmail1
	 */
	public String getApplicantEmail1() {
		return applicantEmail1;
	}

	/**
	 * @param applicantEmail1 the applicantEmail1 to set
	 */
	public void setApplicantEmail1(String applicantEmail1) {
		this.applicantEmail1 = applicantEmail1;
	}

	/**
	 * @return the applicantEmail2
	 */
	public String getApplicantEmail2() {
		return applicantEmail2;
	}

	/**
	 * @param applicantEmail2 the applicantEmail2 to set
	 */
	public void setApplicantEmail2(String applicantEmail2) {
		this.applicantEmail2 = applicantEmail2;
	}

	/**
	 * @return the applicantHomePhone
	 */
	public String getApplicantHomePhone() {
		return applicantHomePhone;
	}

	/**
	 * @param applicantHomePhone the applicantHomePhone to set
	 */
	public void setApplicantHomePhone(String applicantHomePhone) {
		this.applicantHomePhone = applicantHomePhone;
	}

	/**
	 * @return the applicantWorkPhone
	 */
	public String getApplicantWorkPhone() {
		return applicantWorkPhone;
	}

	/**
	 * @param applicantWorkPhone the applicantWorkPhone to set
	 */
	public void setApplicantWorkPhone(String applicantWorkPhone) {
		this.applicantWorkPhone = applicantWorkPhone;
	}

	/**
	 * @return the noticePeriod
	 */
	public String getNoticePeriod() {
		return noticePeriod;
	}

	/**
	 * @param noticePeriod the noticePeriod to set
	 */
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public ArrayList<WemploymentHistoryData> getEmpHistoryData() {
		return empHistoryData;
	}

	public void setEmpHistoryData(ArrayList<WemploymentHistoryData> empHistoryData) {
		this.empHistoryData = empHistoryData;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResumeType() {
		return resumeType;
	}

	public void setResumeType(String resumeType) {
		this.resumeType = resumeType;
	}
}
