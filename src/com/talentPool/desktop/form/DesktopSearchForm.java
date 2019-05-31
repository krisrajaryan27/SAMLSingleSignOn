/**
 * 
 */
package com.talentPool.desktop.form;

import java.util.ArrayList;
import java.util.List;

import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;


/**
 * @author shivprasad
 *
 */
public class DesktopSearchForm extends TPActionForm {
	private static final long serialVersionUID = 1L;
	private String fromEmail;
	private String fromName;
	private String applicantId;
	private String sessionId;
	private String userId;
	private String firstRequest;
	private String result;
	private String screenType;
	private String emailId;
	private String sessionType;	
	private String applicantName;
	
	// bulk import parameter
	private String currentLocation;
	private String currentEmployer;
	private String currentCTC;
	private String expectedCTC;
	private String noticePeriod;
	private String note;
	private String sourceId;
	private List skills;
	private String skillIds;
	private String experience;
	private String lastRowId;
	private String rowId;
	private String resultId;
	private String isSessionComplete;	
	
	// applicant Edu info fields
	private String[] educationYearOfPassing;
	private String[] educationInstitute;
	private String[] educationDegreeId;
	private String[] educationMajorId;
	private String[] educationalGrade;	
	
	//duplicate variables
	private String duplicateImport;
	private String duplicateString;
	private String ignoreDuplicate;
	private String duplicateApplicantId;
	private String duplicateApplicantOriginalResumePath;
	private String duplicateApplicantStatus;

	//parsed Data
	private String parsedName;
	private String parsedPhone1;
	private String parsedPhone2;
	private String parsedEmail;
	private String parsedSkills;
	private String parsedResumePath;
	private String parsedSkillIds;
	private String portal;

	
	/**
	 * @return the property
	 */
	public String getPortal() {
		return portal;
	}
	/**
	 * @param property the property to set
	 */
	public void setPortal(String portal) {
		this.portal = portal;
	}
	/**
	 * @return the parsedResumePath
	 */
	public String getParsedResumePath() {
		return parsedResumePath;
	}
	/**
	 * @param parsedResumePath the parsedResumePath to set
	 */
	public void setParsedResumePath(String parsedResumePath) {
		this.parsedResumePath = parsedResumePath;
	}
	/**
	 * @return the applicantName
	 */
	public String getApplicantName() {
		return applicantName;
	}
	/**
	 * @param applicantName the applicantName to set
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
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
	/**
	 * @return the fromEmail
	 */
	public String getFromEmail() {
		return fromEmail;
	}
	/**
	 * @param fromEmail the fromEmail to set
	 */
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}
	/**
	 * @return the fromName
	 */
	public String getFromName() {
		return fromName;
	}
	/**
	 * @param fromName the fromName to set
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the firstRequest
	 */
	public String getFirstRequest() {
		return firstRequest;
	}
	/**
	 * @param firstRequest the firstRequest to set
	 */
	public void setFirstRequest(String firstRequest) {
		this.firstRequest = firstRequest;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @return the screenType
	 */
	public String getScreenType() {
		return screenType;
	}
	/**
	 * @param screenType the screenType to set
	 */
	public void setScreenType(String screenType) {
		this.screenType = screenType;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	/**
	 * @return Returns the sourceIds.
	 */
	public ArrayList<String> getSourceIds() {
		return CommonUtils.getActiveSourceIds();
	}

	/**
	 * @return Returns the sourceNames.
	 */
	public ArrayList<String> getSourceNames() {
		return CommonUtils.getActiveSourceNames();
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
	 * @return the experience
	 */
	public String getExperience() {
		return experience;
	}
	/**
	 * @param experience the experience to set
	 */
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	/**
	 * @return the skillIds
	 */
	public String getSkillIds() {
		return skillIds;
	}
	/**
	 * @param skillIds the skillIds to set
	 */
	public void setSkillIds(String skillIds) {
		this.skillIds = skillIds;
	}
	/**
	 * @return the sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}
	/**
	 * @param sourceId the sourceId to set
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	/**
	 * @return the skills
	 */
	public List getSkills() {
		return skills;
	}
	/**
	 * @param skills the skills to set
	 */
	public void setSkills(List skills) {
		this.skills = skills;
	}
	/**
	 * @return the sessionType
	 */
	public String getSessionType() {
		return sessionType;
	}
	/**
	 * @param sessionType the sessionType to set
	 */
	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	/**
	 * @return the lastRowId
	 */
	public String getLastRowId() {
		return lastRowId;
	}
	/**
	 * @param lastRowId the lastRowId to set
	 */
	public void setLastRowId(String lastRowId) {
		this.lastRowId = lastRowId;
	}
	/**
	 * @return the parsedEmail
	 */
	public String getParsedEmail() {
		return parsedEmail;
	}
	/**
	 * @param parsedEmail the parsedEmail to set
	 */
	public void setParsedEmail(String parsedEmail) {
		this.parsedEmail = parsedEmail;
	}
	/**
	 * @return the parsedName
	 */
	public String getParsedName() {
		return parsedName;
	}
	/**
	 * @param parsedName the parsedName to set
	 */
	public void setParsedName(String parsedName) {
		this.parsedName = parsedName;
	}
	/**
	 * @return the parsedPhone1
	 */
	public String getParsedPhone1() {
		return parsedPhone1;
	}
	/**
	 * @param parsedPhone1 the parsedPhone1 to set
	 */
	public void setParsedPhone1(String parsedPhone1) {
		this.parsedPhone1 = parsedPhone1;
	}
	/**
	 * @return the parsedPhone2
	 */
	public String getParsedPhone2() {
		return parsedPhone2;
	}
	/**
	 * @param parsedPhone2 the parsedPhone2 to set
	 */
	public void setParsedPhone2(String parsedPhone2) {
		this.parsedPhone2 = parsedPhone2;
	}
	/**
	 * @return the parsedSkills
	 */
	public String getParsedSkills() {
		return parsedSkills;
	}
	/**
	 * @param parsedSkills the parsedSkills to set
	 */
	public void setParsedSkills(String parsedSkills) {
		this.parsedSkills = parsedSkills;
	}
	/**
	 * @return the resultId
	 */
	public String getResultId() {
		return resultId;
	}
	/**
	 * @param resultId the resultId to set
	 */
	public void setResultId(String resultId) {
		this.resultId = resultId;
	}
	/**
	 * @return the isSessionComplete
	 */
	public String getIsSessionComplete() {
		return isSessionComplete;
	}
	/**
	 * @param isSessionComplete the isSessionComplete to set
	 */
	public void setIsSessionComplete(String isSessionComplete) {
		this.isSessionComplete = isSessionComplete;
	}
	/**
	 * @return the duplicateImport
	 */
	public String getDuplicateImport() {
		return duplicateImport;
	}
	/**
	 * @param duplicateImport the duplicateImport to set
	 */
	public void setDuplicateImport(String duplicateImport) {
		this.duplicateImport = duplicateImport;
	}
	/**
	 * @return the duplicateString
	 */
	public String getDuplicateString() {
		return duplicateString;
	}
	/**
	 * @param duplicateString the duplicateString to set
	 */
	public void setDuplicateString(String duplicateString) {
		this.duplicateString = duplicateString;
	}
	/**
	 * @return the duplicateApplicantId
	 */
	public String getDuplicateApplicantId() {
		return duplicateApplicantId;
	}
	/**
	 * @param duplicateApplicantId the duplicateApplicantId to set
	 */
	public void setDuplicateApplicantId(String duplicateApplicantId) {
		this.duplicateApplicantId = duplicateApplicantId;
	}
	/**
	 * @return the duplicateApplicantOriginalResumePath
	 */
	public String getDuplicateApplicantOriginalResumePath() {
		return duplicateApplicantOriginalResumePath;
	}
	/**
	 * @param duplicateApplicantOriginalResumePath the duplicateApplicantOriginalResumePath to set
	 */
	public void setDuplicateApplicantOriginalResumePath(String duplicateApplicantOriginalResumePath) {
		this.duplicateApplicantOriginalResumePath = duplicateApplicantOriginalResumePath;
	}
	/**
	 * @return the ignoreDuplicate
	 */
	public String getIgnoreDuplicate() {
		return ignoreDuplicate;
	}
	/**
	 * @param ignoreDuplicate the ignoreDuplicate to set
	 */
	public void setIgnoreDuplicate(String ignoreDuplicate) {
		this.ignoreDuplicate = ignoreDuplicate;
	}
	/**
	 * @return the parsedSkillIds
	 */
	public String getParsedSkillIds() {
		return parsedSkillIds;
	}
	/**
	 * @param parsedSkillIds the parsedSkillIds to set
	 */
	public void setParsedSkillIds(String parsedSkillIds) {
		this.parsedSkillIds = parsedSkillIds;
	}
	/**
	 * @return the rowId
	 */
	public String getRowId() {
		return rowId;
	}
	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String rowId) {
		this.rowId = rowId;
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
	 * @return the currentEmployer
	 */
	public String getCurrentEmployer() {
		return currentEmployer;
	}
	/**
	 * @param currentEmployer the currentEmployer to set
	 */
	public void setCurrentEmployer(String currentEmployer) {
		this.currentEmployer = currentEmployer;
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
	 * @return the timeToJoin
	 */
	public String getNoticePeriod() {
		return noticePeriod;
	}
	/**
	 * @param timeToJoin the timeToJoin to set
	 */
	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	/**
	 * @return the educationalGrade
	 */
	public String[] getEducationalGrade() {
		return educationalGrade;
	}
	/**
	 * @param educationalGrade the educationalGrade to set
	 */
	public void setEducationalGrade(String[] educationalGrade) {
		this.educationalGrade = Utils.getArrayCopy(educationalGrade);
	}
	/**
	 * @return the educationDegreeId
	 */
	public String[] getEducationDegreeId() {
		return educationDegreeId;
	}
	/**
	 * @param educationDegreeId the educationDegreeId to set
	 */
	public void setEducationDegreeId(String[] educationDegreeId) {
		this.educationDegreeId = Utils.getArrayCopy(educationDegreeId);
	}
	/**
	 * @return the educationInstitute
	 */
	public String[] getEducationInstitute() {
		return educationInstitute;
	}
	/**
	 * @param educationInstitute the educationInstitute to set
	 */
	public void setEducationInstitute(String[] educationInstitute) {
		this.educationInstitute = Utils.getArrayCopy(educationInstitute);
	}
	/**
	 * @return the educationMajorId
	 */
	public String[] getEducationMajorId() {
		return educationMajorId;
	}
	/**
	 * @param educationMajorId the educationMajorId to set
	 */
	public void setEducationMajorId(String[] educationMajorId) {
		this.educationMajorId = Utils.getArrayCopy(educationMajorId);
	}
	/**
	 * @return the educationYearOfPassing
	 */
	public String[] getEducationYearOfPassing() {
		return educationYearOfPassing;
	}
	/**
	 * @param educationYearOfPassing the educationYearOfPassing to set
	 */
	public void setEducationYearOfPassing(String[] educationYearOfPassing) {
		this.educationYearOfPassing = Utils.getArrayCopy(educationYearOfPassing);
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the duplicateApplicantStatus
	 */
	public String getDuplicateApplicantStatus() {
		return duplicateApplicantStatus;
	}
	/**
	 * @param duplicateApplicantStatus the duplicateApplicantStatus to set
	 */
	public void setDuplicateApplicantStatus(String duplicateApplicantStatus) {
		this.duplicateApplicantStatus = duplicateApplicantStatus;
	}

	
	
}
