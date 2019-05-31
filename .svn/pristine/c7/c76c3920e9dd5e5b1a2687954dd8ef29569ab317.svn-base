/**
 * 
 */
package com.talentPool.applicant.form;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class ApplicantForm extends TPActionForm {

	private static final long serialVersionUID = 1L;
	private String selAttachment;
	private String emailId;

	// Applicant Summary Form Fields
	private String applicantId;
	private String applicantName;
	private String applicantAddress;
	private String applicantCity;
	private String applicantEmail1;
	private String applicantEmail2;
	private String applicantHomePhone;
	private String applicantWorkPhone;
	private String applicantCellPhone;
	private String applicantWorkingSince;
	private String applicantNote;
	private String applicantCurrentEmployer;
	private String sourceId;
	private String source;
	private String parse;
	private String fresher;
	private String saveNcontinue;
	private String subMode;
	private String confidential;
	// store the resume in text format when import starts
	private String applicantTextResume;

	// applicant skills
	private String rawPrimarySkills;
	private String primarySkills;
	private String primarySkillIds;

	// applicant Edu info fields
	private String[] educationYearOfPassing;
	private String[] educationInstitute;
	private String[] educationDegreeId;
	private String[] educationMajorId;
	private String[] educationalGrade;
	private String[] fromYear;
	private String[] remarks;
	private String[] educationalStartDate;
	private String[] educationalEndDate;
	private String[] university;
	private String[] typeOfProgram;
	private String[] rawEducationText = new String[1];
	private String rawEducation;
	private String educationLevel;

	
	// parameter for autocomplete list to decide on which list
	private String fld;

	private String educationalInfoId;
	private String educationalInfos;
	private String workExpInfos;

	private String originalResumePath;
	private String originalResumeDocPath;
	private String uploadedFilePath;
	private FormFile attachedFile;

	private String currentCTC;
	private String expectedCTC;
	private String noticePeriod;
	private String dateOfBirth;
	private String passportNumber;
	private String resumeType;
	private String resumeTypeId;
	
	private String ignoreDuplicate;
	private String duplicateString;
	private String duplicateApplicantId;
	private String duplicateApplicantOriginalResumePath;
	private String duplicateApplicantStatus;
	
	private String jsArrayEmployeeSource;
	private String employeeSourceName;
	private String isEmployeeSource;
	private String vendorId;
	
//	these variables are used from desktop talentpool addin
	private String requestSource;
	private String result;
	private String resultId;
	
	private String ieImport;
	private String sessionId;

	private String isEmpApply;
	private String employeeCode;
	private String applicantHRMSCode;
	
	//Employment History
	
	private String[] employmentFromDate;
	private String[] employmentToDate;
	private String[] employmentEmployerId;
	private String[] employmentDesignationId;
	private String[] employmentExperience;	
	private String[] reasonForLeaving;
	private String[] dutiesInvolved;
	private String[] grossSalary;
	private String[] allowance;
	private String[] location;
	private String[] country;
	private String[] empType;
	private String[] lastCtc;
	private String[] rawEmploymentHistoryText = new String[1];
	private String uuid;
	private String category;
	private String subCategory;

	
	public String[] getFromYear() {
		return fromYear;
	}

	public void setFromYear(String[] fromYear) {
		this.fromYear = fromYear;
	}

	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String[] getReasonForLeaving() {
		return reasonForLeaving;
	}

	public void setReasonForLeaving(String[] reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

	public String[] getDutiesInvolved() {
		return dutiesInvolved;
	}

	public void setDutiesInvolved(String[] dutiesInvolved) {
		this.dutiesInvolved = dutiesInvolved;
	}

	public String[] getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(String[] grossSalary) {
		this.grossSalary = grossSalary;
	}

	public String[] getAllowance() {
		return allowance;
	}

	public void setAllowance(String[] allowance) {
		this.allowance = allowance;
	}

	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the employeeSourceName
	 */
	public String getEmployeeSourceName() {
		return employeeSourceName;
	}


	/**
	 * @param employeeSourceName the employeeSourceName to set
	 */
	public void setEmployeeSourceName(String employeeSourceName) {
		this.employeeSourceName = employeeSourceName;
	}

	/**
	 * @return the jsArrayEmployeeSource
	 */
	public String getJsArrayEmployeeSource() {
		return jsArrayEmployeeSource;
	}

	/**
	 * @param jsArrayEmployeeSource the jsArrayEmployeeSource to set
	 */
	public void setJsArrayEmployeeSource(String jsArrayEmployeeSource) {
		this.jsArrayEmployeeSource = jsArrayEmployeeSource;
	}

	public void setDefaultEducationRow() {
		setFromYear(new String[1]);
		setEducationYearOfPassing(new String[1]);
		setEducationInstitute(new String[1]);
		setEducationDegreeId(new String[1]);
		setEducationMajorId(new String[1]);
		setEducationalGrade(new String[1]);
		setRemarks(new String[1]);
		setEducationalStartDate(new String[1]);
		setEducationalEndDate(new String[1]);
		setUniversity(new String[1]);
		setTypeOfProgram(new String[1]);
	}

	/**
	 * @return Returns the applicantCellPhone.
	 */
	public String getApplicantCellPhone() {
		return applicantCellPhone;
	}

	/**
	 * @param applicantCellPhone
	 *            The applicantCellPhone to set.
	 */
	public void setApplicantCellPhone(String applicantCellPhone) {
		this.applicantCellPhone = applicantCellPhone;
	}

	/**
	 * @return Returns the applicantCity.
	 */
	public String getApplicantCity() {
		return applicantCity;
	}

	/**
	 * @param applicantCity
	 *            The applicantCity to set.
	 */
	public void setApplicantCity(String applicantCity) {
		this.applicantCity = applicantCity;
	}

	/**
	 * @return Returns the applicantCurrentAddress.
	 */
	public String getApplicantAddress() {
		return applicantAddress;
	}

	/**
	 * @param applicantCurrentAddress
	 *            The applicantCurrentAddress to set.
	 */
	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	/**
	 * @return Returns the applicantEmail1.
	 */
	public String getApplicantEmail1() {
		return applicantEmail1;
	}

	/**
	 * @param applicantEmail1
	 *            The applicantEmail1 to set.
	 */
	public void setApplicantEmail1(String applicantEmail1) {
		this.applicantEmail1 = applicantEmail1;
	}

	/**
	 * @return Returns the applicantEmail2.
	 */
	public String getApplicantEmail2() {
		return applicantEmail2;
	}

	/**
	 * @param applicantEmail2
	 *            The applicantEmail2 to set.
	 */
	public void setApplicantEmail2(String applicantEmail2) {
		this.applicantEmail2 = applicantEmail2;
	}

	/**
	 * @return Returns the applicantHomePhone.
	 */
	public String getApplicantHomePhone() {
		return applicantHomePhone;
	}

	/**
	 * @param applicantHomePhone
	 *            The applicantHomePhone to set.
	 */
	public void setApplicantHomePhone(String applicantHomePhone) {
		this.applicantHomePhone = applicantHomePhone;
	}

	/**
	 * @return Returns the applicantName.
	 */
	public String getApplicantName() {
		return applicantName;
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	/**
	 * @return Returns the applicantOfficePhone.
	 */
	public String getApplicantWorkPhone() {
		return applicantWorkPhone;
	}

	/**
	 * @param applicantOfficePhone
	 *            The applicantOfficePhone to set.
	 */
	public void setApplicantWorkPhone(String applicantWorkPhone) {
		this.applicantWorkPhone = applicantWorkPhone;
	}

	/**
	 * @return Returns the applicantWorkingSince.
	 */
	public String getApplicantWorkingSince() {
		return applicantWorkingSince;
	}

	/**
	 * @param applicantWorkingSince
	 *            The applicantWorkingSince to set.
	 */
	public void setApplicantWorkingSince(String applicantWorkingSince) {
		this.applicantWorkingSince = applicantWorkingSince;
	}

	/**
	 * @return Returns the selAttachment.
	 */
	public String getSelAttachment() {
		return selAttachment;
	}

	/**
	 * @param selAttachment
	 *            The selAttachment to set.
	 */
	public void setSelAttachment(String selAttachment) {
		this.selAttachment = selAttachment;
	}

	/**
	 * @return Returns the emailId.
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            The emailId to set.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return Returns the stateIds.
	 */
	public ArrayList<String> getDegreeIds() {
		return CommonUtils.getDegreeIds();
	}

	/**
	 * @return Returns the stateNames.
	 */
	public ArrayList<String> getDegreeNames() {
		return CommonUtils.getDegreeNames();
	}

	/**
	 * @return Returns the applicantId.
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	/**
	 * @return Returns the educationDegreeId.
	 */
	public String[] getEducationDegreeId() {
		return educationDegreeId;
	}

	/**
	 * @param educationDegreeId
	 *            The educationDegreeId to set.
	 */
	public void setEducationDegreeId(String[] educationDegreeId) {
		// Instead of directly referring to the parameter, make a copy of it.
		this.educationDegreeId = Utils.getArrayCopy(educationDegreeId);
	}

	/**
	 * @return Returns the educationInstitute.
	 */
	public String[] getEducationInstitute() {
		return educationInstitute;
	}

	/**
	 * @param educationInstitute
	 *            The educationInstitute to set.
	 */
	public void setEducationInstitute(String[] educationInstitute) {
		this.educationInstitute = Utils.getArrayCopy(educationInstitute);
	}

	/**
	 * @return Returns the educationMajor.
	 */
	public String[] getEducationMajorId() {
		return educationMajorId;
	}

	/**
	 * @param educationMajor
	 *            The educationMajor to set.
	 */
	public void setEducationMajorId(String[] educationMajorId) {
		this.educationMajorId = Utils.getArrayCopy(educationMajorId);
	}

	/**
	 * @return Returns the educationYearOfPassing.
	 */
	public String[] getEducationYearOfPassing() {
		return educationYearOfPassing;
	}

	/**
	 * @param educationYearOfPassing
	 *            The educationYearOfPassing to set.
	 */
	public void setEducationYearOfPassing(String[] educationYearOfPassing) {
		this.educationYearOfPassing = Utils.getArrayCopy(educationYearOfPassing);
	}

	/**
	 * @return Returns the educationalInfoId.
	 */
	public String getEducationalInfoId() {
		return educationalInfoId;
	}

	/**
	 * @param educationalInfoId
	 *            The educationalInfoId to set.
	 */
	public void setEducationalInfoId(String educationalInfoId) {
		this.educationalInfoId = educationalInfoId;
	}

	/**
	 * @return Returns the educationalInfos.
	 */
	public String getEducationalInfos() {
		return educationalInfos;
	}

	/**
	 * @param educationalInfos
	 *            The educationalInfos to set.
	 */
	public void setEducationalInfos(String educationalInfos) {
		this.educationalInfos = educationalInfos;
	}

	/**
	 * @return Returns the workExpInfos.
	 */
	public String getWorkExpInfos() {
		return workExpInfos;
	}

	/**
	 * @param workExpInfos
	 *            The workExpInfos to set.
	 */
	public void setWorkExpInfos(String workExpInfos) {
		this.workExpInfos = workExpInfos;
	}

	/**
	 * @return Returns the primarySkills.
	 */
	public String getPrimarySkills() {
		return primarySkills;
	}

	/**
	 * @param primarySkills
	 *            The primarySkills to set.
	 */
	public void setPrimarySkills(String primarySkills) {
		this.primarySkills = primarySkills;
	}

	/**
	 * @return Returns the applicantNote.
	 */
	public String getApplicantNote() {
		return applicantNote;
	}

	/**
	 * @param applicantNote
	 *            The applicantNote to set.
	 */
	public void setApplicantNote(String applicantNote) {
		this.applicantNote = applicantNote;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId
	 *            The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return Returns the sourceIds.
	 */
	public ArrayList<String> getSourceIds() {
		return CommonUtils.getSourceIds();
	}

	/**
	 * @return Returns the sourceNames.
	 */
	public ArrayList<String> getSourceNames() {
		return CommonUtils.getSourceNames();
	}

	/**
	 * @return Returns the educationalGradeId.
	 */
	public String[] getEducationalGrade() {
		return educationalGrade;
	}

	/**
	 * @param educationalGradeId
	 *            The educationalGradeId to set.
	 */
	public void setEducationalGrade(String[] educationalGrade) {
		this.educationalGrade = Utils.getArrayCopy(educationalGrade);
	}

	/**
	 * @return Returns the fld.
	 */
	public String getFld() {
		return fld;
	}

	/**
	 * @param fld
	 *            The fld to set.
	 */
	public void setFld(String fld) {
		this.fld = fld;
	}

	/**
	 * @return Returns the rawPrimarySkills.
	 */
	public String getRawPrimarySkills() {
		return rawPrimarySkills;
	}

	/**
	 * @param rawPrimarySkills
	 *            The rawPrimarySkills to set.
	 */
	public void setRawPrimarySkills(String rawPrimarySkills) {
		this.rawPrimarySkills = rawPrimarySkills;
	}

	/**
	 * @return Returns the primarySkillsIds.
	 */
	public String getPrimarySkillIds() {
		return primarySkillIds;
	}

	/**
	 * @param primarySkillsIds
	 *            The primarySkillsIds to set.
	 */
	public void setPrimarySkillIds(String primarySkillIds) {
		this.primarySkillIds = primarySkillIds;
	}

	/**
	 * @return Returns the applicantCurrentEmployer.
	 */
	public String getApplicantCurrentEmployer() {
		return applicantCurrentEmployer;
	}

	/**
	 * @param applicantCurrentEmployer
	 *            The applicantCurrentEmployer to set.
	 */
	public void setApplicantCurrentEmployer(String applicantCurrentEmployer) {
		this.applicantCurrentEmployer = applicantCurrentEmployer;
	}

	/**
	 * @return Returns the rawEducation.
	 */
	public String getRawEducation() {
		return rawEducation;
	}

	/**
	 * @param rawEducation
	 *            The rawEducation to set.
	 */
	public void setRawEducation(String rawEducation) {
		this.rawEducation = rawEducation;
	}

	/**
	 * @return Returns the originalResumeDocPath.
	 */
	public String getOriginalResumeDocPath() {
		return originalResumeDocPath;
	}

	/**
	 * @param originalResumeDocPath
	 *            The originalResumeDocPath to set.
	 */
	public void setOriginalResumeDocPath(String originalResumeDocPath) {
		this.originalResumeDocPath = originalResumeDocPath;
	}

	/**
	 * @return Returns the originalResumePath.
	 */
	public String getOriginalResumePath() {
		return originalResumePath;
	}

	/**
	 * @param originalResumePath
	 *            The originalResumePath to set.
	 */
	public void setOriginalResumePath(String originalResumePath) {
		this.originalResumePath = originalResumePath;
	}

	public String getParse() {
		return parse;
	}

	public void setParse(String parse) {
		this.parse = parse;
	}

	/**
	 * @return Returns the fresher.
	 */
	public String getFresher() {
		return fresher;
	}

	/**
	 * @param fresher
	 *            The fresher to set.
	 */
	public void setFresher(String fresher) {
		this.fresher = fresher;
	}

	public String getSaveNcontinue() {
		return saveNcontinue;
	}

	public void setSaveNcontinue(String saveNcontinue) {
		this.saveNcontinue = saveNcontinue;
	}

	public String getSubMode() {
		return subMode;
	}

	public void setSubMode(String subMode) {
		this.subMode = subMode;
	}

	/**
	 * @return the educationLevel
	 */
	public String getEducationLevel() {
		return educationLevel;
	}

	/**
	 * @param educationLevel
	 *            the educationLevel to set
	 */
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	/**
	 * @return the rawEducationText
	 */
	public String[] getRawEducationText() {
		return rawEducationText;
	}

	/**
	 * @param rawEducationText
	 *            the rawEducationText to set
	 */
	public void setRawEducationText(String[] rawEducationText) {
		this.rawEducationText = Utils.getArrayCopy(rawEducationText);
	}

	/**
	 * @return the currentCTC
	 */
	public String getCurrentCTC() {
		return currentCTC;
	}

	/**
	 * @param currentCTC
	 *            the currentCTC to set
	 */
	public void setCurrentCTC(String currentCTC) {
		this.currentCTC = currentCTC;
	}

	/**
	 * @return the expectedCTC
	 */
	public String getExpectedCTC() {
		return expectedCTC;
	}

	/**
	 * @param expectedCTC
	 *            the expectedCTC to set
	 */
	public void setExpectedCTC(String expectedCTC) {
		this.expectedCTC = expectedCTC;
	}

	/**
	 * @return the uploadedFilePath
	 */
	public String getUploadedFilePath() {
		return uploadedFilePath;
	}

	/**
	 * @param uploadedFilePath
	 *            the uploadedFilePath to set
	 */
	public void setUploadedFilePath(String uploadedFilePath) {
		this.uploadedFilePath = uploadedFilePath;
	}

	/**
	 * @return the attachedFile
	 */
	public FormFile getAttachedFile() {
		return attachedFile;
	}

	/**
	 * @param attachedFile
	 *            the attachedFile to set
	 */
	public void setAttachedFile(FormFile attachedFile) {
		this.attachedFile = attachedFile;
	}

	/**
	 * @return the applicantTextResume
	 */
	public String getApplicantTextResume() {
		return applicantTextResume;
	}

	/**
	 * @param applicantTextResume
	 *            the applicantTextResume to set
	 */
	public void setApplicantTextResume(String applicantTextResume) {
		this.applicantTextResume = applicantTextResume;
	}

	/**
	 * @return the ignoreDuplicate
	 */
	public String getIgnoreDuplicate() {
		return ignoreDuplicate;
	}

	/**
	 * @param ignoreDuplicate
	 *            the ignoreDuplicate to set
	 */
	public void setIgnoreDuplicate(String ignoreDuplicate) {
		this.ignoreDuplicate = ignoreDuplicate;
	}

	/**
	 * @return the duplicateString
	 */
	public String getDuplicateString() {
		return duplicateString;
	}

	/**
	 * @param duplicateString
	 *            the duplicateString to set
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
	 * @param duplicateApplicantId
	 *            the duplicateApplicantId to set
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

	/**
	 * @return the isEmployeeSource
	 */
	public String getIsEmployeeSource() {
		return isEmployeeSource;
	}

	/**
	 * @param isEmployeeSource the isEmployeeSource to set
	 */
	public void setIsEmployeeSource(String isEmployeeSource) {
		this.isEmployeeSource = isEmployeeSource;
	}

	public String getRequestSource() {
		return requestSource;
	}

	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the confidential
	 */
	public String getConfidential() {
		return confidential;
	}

	/**
	 * @param confidential the confidential to set
	 */
	public void setConfidential(String confidential) {
		this.confidential = confidential;
	}

	/**
	 * @return the ieImport
	 */
	public String getIeImport() {
		return ieImport;
	}

	/**
	 * @param ieImport the ieImport to set
	 */
	public void setIeImport(String ieImport) {
		this.ieImport = ieImport;
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

	public String getIsEmpApply() {
		return isEmpApply;
	}

	public void setIsEmpApply(String isEmpApply) {
		this.isEmpApply = isEmpApply;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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
	
	public String getApplicantHRMSCode() {
		return applicantHRMSCode;
	}
	public void setApplicantHRMSCode(String applicantHRMSCode) {
		this.applicantHRMSCode = applicantHRMSCode;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the passportNumber
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * @param passportNumber the passportNumber to set
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * @return the resumeType
	 */
	public String getResumeType() {
		return resumeType;
	}

	/**
	 * @param resumeType the resumeType to set
	 */
	public void setResumeType(String resumeType) {
		this.resumeType = resumeType;
	}

	/**
	 * @return the resumeTypeId
	 */
	public String getResumeTypeId() {
		return resumeTypeId;
	}

	/**
	 * @param resumeTypeId the resumeTypeId to set
	 */
	public void setResumeTypeId(String resumeTypeId) {
		this.resumeTypeId = resumeTypeId;
	}

	
	public void setDefaultEmploymentHistoryRow() {
		setEmploymentFromDate(new String[1]);
		setEmploymentToDate(new String[1]);
		setEmploymentEmployerId(new String[1]);
		setEmploymentDesignationId(new String[1]);
		setEmploymentExperience(new String[1]);
		setAllowance(new String[1]);
		setGrossSalary(new String[1]);
		setReasonForLeaving(new String[1]);
		setDutiesInvolved(new String[1]);
		setLocation(new String[1]);
		setCountry(new String[1]);
		setLastCtc(new String[1]);
		setEmpType(new String[1]);
	}
	/**
	 * @return the employmentFromDate
	 */
	public String[] getEmploymentFromDate() {
		return employmentFromDate;
	}

	/**
	 * @param employmentFromDate the employmentFromDate to set
	 */
	public void setEmploymentFromDate(String[] employmentFromDate) {
		this.employmentFromDate = Utils.getArrayCopy(employmentFromDate);
	}

	/**
	 * @return the employmentToDate
	 */
	public String[] getEmploymentToDate() {
		return employmentToDate;
	}

	/**
	 * @param employmentToDate the employmentToDate to set
	 */
	public void setEmploymentToDate(String[] employmentToDate) {
		this.employmentToDate = Utils.getArrayCopy(employmentToDate);
	}

	/**
	 * @return the employmentEmployerId
	 */
	public String[] getEmploymentEmployerId() {
		return employmentEmployerId;
	}

	/**
	 * @param employmentEmployerId the employmentEmployerId to set
	 */
	public void setEmploymentEmployerId(String[] employmentEmployerId) {
		this.employmentEmployerId = Utils.getArrayCopy(employmentEmployerId);
	}

	/**
	 * @return the designationId
	 */
	public String[] getEmploymentDesignationId() {
		return employmentDesignationId;
	}

	/**
	 * @param employmentDesignationId the employmentDesignationId to set
	 */
	public void setEmploymentDesignationId(String[] employmentDesignationId) {
		this.employmentDesignationId = Utils.getArrayCopy(employmentDesignationId);
	}

	/**
	 * @return the employerExperience
	 */
	public String[] getEmploymentExperience() {
		return employmentExperience;
	}

	/**
	 * @param employmentExperience the employmentExperience to set
	 */
	public void setEmploymentExperience(String[] employmentExperience) {
		this.employmentExperience = Utils.getArrayCopy(employmentExperience);
	}

	/**
	 * @return the rawEmploymentHistoryText
	 */
	public String[] getRawEmploymentHistoryText() {
		return rawEmploymentHistoryText;
	}

	/**
	 * @param rawEmploymentHistoryText the rawEmploymentHistoryText to set
	 */
	public void setRawEmploymentHistoryText(String[] rawEmploymentHistoryText) {
		this.rawEmploymentHistoryText = Utils.getArrayCopy(rawEmploymentHistoryText);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String[] getEducationalStartDate() {
		return educationalStartDate;
	}

	public void setEducationalStartDate(String[] educationalStartDate) {
		this.educationalStartDate = educationalStartDate;
	}

	public String[] getEducationalEndDate() {
		return educationalEndDate;
	}

	public void setEducationalEndDate(String[] educationalEndDate) {
		this.educationalEndDate = educationalEndDate;
	}

	public String[] getUniversity() {
		return university;
	}

	public void setUniversity(String[] university) {
		this.university = university;
	}

	public String[] getTypeOfProgram() {
		return typeOfProgram;
	}

	public void setTypeOfProgram(String[] typeOfProgram) {
		this.typeOfProgram = typeOfProgram;
	}

	public String[] getLocation() {
		return location;
	}

	public void setLocation(String[] location) {
		this.location = location;
	}

	public String[] getCountry() {
		return country;
	}

	public void setCountry(String[] country) {
		this.country = country;
	}

	public String[] getEmpType() {
		return empType;
	}

	public void setEmpType(String[] empType) {
		this.empType = empType;
	}

	public String[] getLastCtc() {
		return lastCtc;
	}

	public void setLastCtc(String[] lastCtc) {
		this.lastCtc = lastCtc;
	}
	
}