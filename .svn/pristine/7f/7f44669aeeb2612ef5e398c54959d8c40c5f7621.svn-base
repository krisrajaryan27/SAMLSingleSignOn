package com.talentPool.positions.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.talentPool.common.base.TPActionForm;

public class PositionForm extends TPActionForm {

	private static final long serialVersionUID = 1L;

	// Start: Position Home Page
	private String positionId; // The position identifier.
	private String _positionId;
	private String showCondition; // Show all/open position.
	private String positionStatus; // Open/Closed/Deleted position.
	// End: Position Home Page

	private String dir; // Navigaton Direction.
	private String step; // Position Wizard Screen Number.
	private String dest;
	private String subMode;
	// Start: Position Description Page
	private String positionName;
	private String positionCode;
	private String positionOwnerName;
	private String positionOwnerId;
	private String requisitioner;
	private String requisitionerId;
	private String department;
	private String subDepartment;
	private String subSubDepartment;
	private String sub3Department;
	private String sub4Department;
	private String departmentId;
	private String subDepartmentId;
	private String subSubDepartmentId;
	private String sub3DepartmentId;
	private String sub4DepartmentId;
	private String vacancies;
	private String hireByDate;
	private String positionLevel;
	private String positionReferalFees;
	private String note;
	// private List responsibilities;
	private String responsibilities;
	private String locationName;
	private String locationId;

	private String jsArrayRequisitioners;
	private String jsArrayPositionOwners;
	private String jsArrayDepartments;
	private String jsArraySubDepartments;
	private String jsArraySubSubDepartments;
	private String jsArraySub3Departments;
	private String jsArraySub4Departments;
	private String jsArrayPositions;
	private String locationsXML;

	private String jsArrayResponsibilities;
	private String jsArrayBudgetItems;
	// End: Position Description Page

	// Start: Position Requirements Page
	private String degreeTitle;
	private String degreeId;
	private String branchName;
	private String branchId;
	private String minimumExperience;
	private String maximumExperience;
	// private List requirements;
	private String requirements;
	private List skills;
	private String primarySkills;
	private String secondarySkills;

	private String jsArrayDegrees;
	private String jsArrayBranches;

	private String jsArrayRequirements;
	// End: Position Requirements Page

	// Start: Position Hiring Process
	private List hiringProcess;
	private List users;

	private String jsArrayHiringProcess;
	private String feedbackFormId;
	private String positionStepId;
	// End: Position Hiring Process

	// start: approval
	private String feedbackDecision;
	private String nextUserId;
	private String feedbackComment;
	private String fromApprovalStepId;
	private String toApprovalStepId;
	private String feedbackId;

	// Start: Position Priority
	private String positionPriority;

	// Position Create Date
	private String positionCreateDate;

	// End: approval

	/* Start: Publish position for walk-in and employee portal */
	private String publishType;
	private String positionsToBePublished;
	private String positionsNotToBePublished;
	/* End: Publish position for walk-in and employee portal */

	// Scheduled By Users:- Only users with Role HR_Manager and Recruiter
	private List hrUsers;

	private String notifyUserIds;
	private String approvalUserIds;

	private String requisitionApprovalTemplateId;
	private String requisitionApprovalTemplateName;

	private String markCandidatesAndClosePosition;

	private String ruleType;
	private String institute;
	private String instituteId;
	private String currentLocation;
	private String expSelected;
	private String degreeSelected;
	private String branchSelected;
	private String instituteSelected;
	private String currentLocationSelected;
	private String autoCompleteFieldType;
	private String isPublishedToSite;

	private FormFile attachedFile;

	private String draftId;
	private String draftName;

	private String relativeFilePath;
	private String recruiterId;
	private String filterFor;
	private String skillId;

	private String finishCopyPosition;

	private String budgetItemId;
	private String budgetItemName;
	private List trackModeNotifications;
	private String isBudgetCommitted;

	private String gradeId;
	private String gradeName;
	private String bandId;
	private String bandName;
	private String jsArrayGrades;
	private String jsArrayBands;

	private String publishTo;
	private String publishFrom;

	private String currentNoOfOpenings;

	private String customFieldFilterId;
	private String customFieldFilterType;
	private String customFieldFilterValue;

	private String employeeAnnouncements;

	private String sendPositionChangeNotification;
	private String copyFrom;

	private String employeeApplyRefer;
	private String employeeCanEmail;

	private String buId;
	private String buName;
	private String businessUnitXML;

	private String costCenterId;
	private String costCenterName;
	private String costCenterXML;

	private String typeOfVacancy;
	private String replacementEmpCode;
	private String positionTypeExtInt;
	private String positionClone;

	private String deletePositionTokenId;

	// Naukri fields
	private String contactPersonName;

	private String jobIndustryCode;
	private String jobFunctionCode;

	private String jobRoleCode;

	private String jobKeywords;
	private String country;
	private String minimumSalary;

	private String maximumSalary;
	private String benefitsDescription;
	private String displaySalary;

	private String desiredCandidateSummaryText;
	private String contactPersonEmail;
	private String applyByWebURL;
	private String jobFeedResponseEmail;

	private String salaryCurrency;

	private String jsArrayCountries;
	private String jsArrayJobIndustryCodes;
	private String jsArrayJobFunctionalAreaCodes;
	private String jsArrayJobRoleCodes;
	private String dropReason;
	private String dropComment;
	private String ignoreDuplicate;

	// RI questinnairre
	private String applicantFeedbackFormId;

	// For Asian Paints Position Creation
	private String positionIdForAP;
	private String positionCodeForAP;
	private String positionCodeForAPDisabled;
	private String positionTitleForAP;

	private String pJobFunction;
	private String pJobPayGrade;
	private String pJobCode;
	private String pLocation;
	private String countryName;
	private String stateName;
	private String pSubLocation;
	private String pSubSubLocation;
	private String pJobFunctionId;
	private String pJobPayGradeId;
	private String pJobCodeId;
	private String pSubLocationId;
	private String pSubSubLocationId;
	private String pLocationId;
	private String countryId;
	private String stateId;

	private String jsArrayLocations;
	private String jsArraySubLocations;
	private String jsArraySubSubLocations;
	private String jsArrayJobFunction;
	private String jsArrayJobPayGrade;
	private String jsArrayJobCode;
	private String jsArrayCountry;
	private String jsArrayState;
	private String jsPositionsArray;
	
	private String tenthMarksFilter;
	private String tenthMarks;
	private String twelvethMarksFilter;
	private String twelvethMarks;
	private String gradeMarksFilter;
	private String gradeMarks;
	private String postGradeMarksFilter;
	private String postGradeMarks;
	private String ageFilter;
	private String age;
	private String yearOfExperienceFilter;
	private String yearOfExperience;
	private String gapInAcademics;
	private String gender;
	public String getJsPositionsArray() {
		return jsPositionsArray;
	}
	public void setJsPositionsArray(String jsPositionsArray) {
		this.jsPositionsArray = jsPositionsArray;
	}
	
	public String getIgnoreDuplicate() {
		return ignoreDuplicate;
	}

	public void setIgnoreDuplicate(String ignoreDuplicate) {
		this.ignoreDuplicate = ignoreDuplicate;
	}

	/**
	 * @return the positionClone
	 */
	public String getPositionClone() {
		return positionClone;
	}

	/**
	 * @param positionClone
	 *            the positionClone to set
	 */
	public void setPositionClone(String positionClone) {
		this.positionClone = positionClone;
	}

	/**
	 * @return the positionTypeExtInt
	 */
	public String getPositionTypeExtInt() {
		return positionTypeExtInt;
	}

	/**
	 * @param positionTypeExtInt
	 *            the positionTypeExtInt to set
	 */
	public void setPositionTypeExtInt(String positionTypeExtInt) {
		this.positionTypeExtInt = positionTypeExtInt;
	}

	/**
	 * @return the replacementEmpCode
	 */
	public String getReplacementEmpCode() {
		return replacementEmpCode;
	}

	/**
	 * @param replacementEmpCode
	 *            the replacementEmpCode to set
	 */
	public void setReplacementEmpCode(String replacementEmpCode) {
		this.replacementEmpCode = replacementEmpCode;
	}

	/**
	 * @return the typeOfVacancy
	 */
	public String getTypeOfVacancy() {
		return typeOfVacancy;
	}

	/**
	 * @param typeOfVacancy
	 *            the typeOfVacancy to set
	 */
	public void setTypeOfVacancy(String typeOfVacancy) {
		this.typeOfVacancy = typeOfVacancy;
	}

	public String getEmployeeApplyRefer() {
		return employeeApplyRefer;
	}

	public void setEmployeeApplyRefer(String employeeApplyRefer) {
		this.employeeApplyRefer = employeeApplyRefer;
	}

	/**
	 * @return the publishTO
	 */
	public String getPublishTo() {
		return publishTo;
	}

	/**
	 * @param publishTO
	 *            the publishTO to set
	 */
	public void setPublishTo(String publishTO) {
		this.publishTo = publishTO;
	}

	/**
	 * @return the publishFROM
	 */
	public String getPublishFrom() {
		return publishFrom;
	}

	/**
	 * @param publishFROM
	 *            the publishFROM to set
	 */
	public void setPublishFrom(String publishFROM) {
		this.publishFrom = publishFROM;
	}

	/**
	 * @return the positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * @param positionId
	 *            the positionId to set
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * @return the _positionId
	 */
	public String get_positionId() {
		return _positionId;
	}

	/**
	 * @param positionId
	 *            the _positionId to set
	 */
	public void set_positionId(String positionId) {
		_positionId = positionId;
	}

	/**
	 * @return the showCondition
	 */
	public String getShowCondition() {
		return showCondition;
	}

	/**
	 * @param showCondition
	 *            the showCondition to set
	 */
	public void setShowCondition(String showCondition) {
		this.showCondition = showCondition;
	}

	/**
	 * @return the positionStatus
	 */
	public String getPositionStatus() {
		return positionStatus;
	}

	/**
	 * @param positionStatus
	 *            the positionStatus to set
	 */
	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}

	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the step
	 */
	public String getStep() {
		return step;
	}

	/**
	 * @param step
	 *            the step to set
	 */
	public void setStep(String step) {
		this.step = step;
	}

	/**
	 * @return the dest
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * @param dest
	 *            the dest to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	/**
	 * @return the subMode
	 */
	public String getSubMode() {
		return subMode;
	}

	/**
	 * @param subMode
	 *            the subMode to set
	 */
	public void setSubMode(String subMode) {
		this.subMode = subMode;
	}

	/**
	 * @return the positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * @param positionName
	 *            the positionName to set
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * @return the positionCode
	 */
	public String getPositionCode() {
		return positionCode;
	}

	/**
	 * @param positionCode
	 *            the positionCode to set
	 */
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	/**
	 * @return the requisitioner
	 */
	public String getRequisitioner() {
		return requisitioner;
	}

	/**
	 * @param requisitioner
	 *            the requisitioner to set
	 */
	public void setRequisitioner(String requisitioner) {
		this.requisitioner = requisitioner;
	}

	/**
	 * @return the requisitionerId
	 */
	public String getRequisitionerId() {
		return requisitionerId;
	}

	/**
	 * @param requisitionerId
	 *            the requisitionerId to set
	 */
	public void setRequisitionerId(String requisitionerId) {
		this.requisitionerId = requisitionerId;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the subDepartment
	 */
	public String getSubDepartment() {
		return subDepartment;
	}

	/**
	 * @param subDepartment
	 *            the subDepartment to set
	 */
	public void setSubDepartment(String subDepartment) {
		this.subDepartment = subDepartment;
	}

	/**
	 * @return the subSubDepartment
	 */
	public String getSubSubDepartment() {
		return subSubDepartment;
	}

	/**
	 * @param subSubDepartment
	 *            the subSubDepartment to set
	 */
	public void setSubSubDepartment(String subSubDepartment) {
		this.subSubDepartment = subSubDepartment;
	}

	/**
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * @param departmentId
	 *            the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * @return the subDepartmentId
	 */
	public String getSubDepartmentId() {
		return subDepartmentId;
	}

	/**
	 * @param subDepartmentId
	 *            the subDepartmentId to set
	 */
	public void setSubDepartmentId(String subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}

	/**
	 * @return the subSubDepartmentId
	 */
	public String getSubSubDepartmentId() {
		return subSubDepartmentId;
	}

	/**
	 * @param subSubDepartmentId
	 *            the subSubDepartmentId to set
	 */
	public void setSubSubDepartmentId(String subSubDepartmentId) {
		this.subSubDepartmentId = subSubDepartmentId;
	}

	/**
	 * @return the vacancies
	 */
	public String getVacancies() {
		return vacancies;
	}

	/**
	 * @param vacancies
	 *            the vacancies to set
	 */
	public void setVacancies(String vacancies) {
		this.vacancies = vacancies;
	}

	/**
	 * @return the hireByDate
	 */
	public String getHireByDate() {
		return hireByDate;
	}

	/**
	 * @param hireByDate
	 *            the hireByDate to set
	 */
	public void setHireByDate(String hireByDate) {
		this.hireByDate = hireByDate;
	}

	/**
	 * @return the positionLevel
	 */
	public String getPositionLevel() {
		return positionLevel;
	}

	/**
	 * @param positionLevel
	 *            the positionLevel to set
	 */
	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel;
	}

	/**
	 * @return the positionReferalFees
	 */
	public String getPositionReferalFees() {
		return positionReferalFees;
	}

	/**
	 * @param positionReferalFees
	 *            the positionReferalFees to set
	 */
	public void setPositionReferalFees(String positionReferalFees) {
		this.positionReferalFees = positionReferalFees;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the responsibilities
	 */
	public String getResponsibilities() {
		return responsibilities;
	}

	/**
	 * @param responsibilities
	 *            the responsibilities to set
	 */
	public void setResponsibilities(String responsibilities) {
		this.responsibilities = responsibilities;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName
	 *            the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId
	 *            the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the jsArrayRequisitioners
	 */
	public String getJsArrayRequisitioners() {
		return jsArrayRequisitioners;
	}

	/**
	 * @param jsArrayRequisitioners
	 *            the jsArrayRequisitioners to set
	 */
	public void setJsArrayRequisitioners(String jsArrayRequisitioners) {
		this.jsArrayRequisitioners = jsArrayRequisitioners;
	}

	/**
	 * @return the jsArrayDepartments
	 */
	public String getJsArrayDepartments() {
		return jsArrayDepartments;
	}

	/**
	 * @param jsArrayDepartments
	 *            the jsArrayDepartments to set
	 */
	public void setJsArrayDepartments(String jsArrayDepartments) {
		this.jsArrayDepartments = jsArrayDepartments;
	}

	/**
	 * @return the jsArraySubDepartments
	 */
	public String getJsArraySubDepartments() {
		return jsArraySubDepartments;
	}

	/**
	 * @param jsArraySubDepartments
	 *            the jsArraySubDepartments to set
	 */
	public void setJsArraySubDepartments(String jsArraySubDepartments) {
		this.jsArraySubDepartments = jsArraySubDepartments;
	}

	/**
	 * @return the jsArraySubSubDepartments
	 */
	public String getJsArraySubSubDepartments() {
		return jsArraySubSubDepartments;
	}

	/**
	 * @param jsArraySubSubDepartments
	 *            the jsArraySubSubDepartments to set
	 */
	public void setJsArraySubSubDepartments(String jsArraySubSubDepartments) {
		this.jsArraySubSubDepartments = jsArraySubSubDepartments;
	}

	/**
	 * @return the jsArrayPositions
	 */
	public String getJsArrayPositions() {
		return jsArrayPositions;
	}

	/**
	 * @param jsArrayPositions
	 *            the jsArrayPositions to set
	 */
	public void setJsArrayPositions(String jsArrayPositions) {
		this.jsArrayPositions = jsArrayPositions;
	}

	/**
	 * @return the jsArrayLocations
	 */
	public String getLocationsXML() {
		return locationsXML;
	}

	/**
	 * @param jsArrayLocations
	 *            the jsArrayLocations to set
	 */
	public void setLocationsXML(String locationsXML) {
		this.locationsXML = locationsXML;
	}

	/**
	 * @return the jsArrayResponsibilities
	 */
	public String getJsArrayResponsibilities() {
		return jsArrayResponsibilities;
	}

	/**
	 * @param jsArrayResponsibilities
	 *            the jsArrayResponsibilities to set
	 */
	public void setJsArrayResponsibilities(String jsArrayResponsibilities) {
		this.jsArrayResponsibilities = jsArrayResponsibilities;
	}

	/**
	 * @return the degreeTitle
	 */
	public String getDegreeTitle() {
		return degreeTitle;
	}

	/**
	 * @param degreeTitle
	 *            the degreeTitle to set
	 */
	public void setDegreeTitle(String degreeTitle) {
		this.degreeTitle = degreeTitle;
	}

	/**
	 * @return the degreeId
	 */
	public String getDegreeId() {
		return degreeId;
	}

	/**
	 * @param degreeId
	 *            the degreeId to set
	 */
	public void setDegreeId(String degreeId) {
		this.degreeId = degreeId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the minimumExperience
	 */
	public String getMinimumExperience() {
		return minimumExperience;
	}

	/**
	 * @param minimumExperience
	 *            the minimumExperience to set
	 */
	public void setMinimumExperience(String minimumExperience) {
		this.minimumExperience = minimumExperience;
	}

	/**
	 * @return the maximumExperience
	 */
	public String getMaximumExperience() {
		return maximumExperience;
	}

	/**
	 * @param maximumExperience
	 *            the maximumExperience to set
	 */
	public void setMaximumExperience(String maximumExperience) {
		this.maximumExperience = maximumExperience;
	}

	/**
	 * @return the requirements
	 */
	public String getRequirements() {
		return requirements;
	}

	/**
	 * @param requirements
	 *            the requirements to set
	 */
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	/**
	 * @return the skills
	 */
	public List getSkills() {
		return skills;
	}

	/**
	 * @param skills
	 *            the skills to set
	 */
	public void setSkills(List skills) {
		this.skills = skills;
	}

	/**
	 * @return the primarySkills
	 */
	public String getPrimarySkills() {
		return primarySkills;
	}

	/**
	 * @param primarySkills
	 *            the primarySkills to set
	 */
	public void setPrimarySkills(String primarySkills) {
		this.primarySkills = primarySkills;
	}

	/**
	 * @return the secondarySkills
	 */
	public String getSecondarySkills() {
		return secondarySkills;
	}

	/**
	 * @param secondarySkills
	 *            the secondarySkills to set
	 */
	public void setSecondarySkills(String secondarySkills) {
		this.secondarySkills = secondarySkills;
	}

	/**
	 * @return the jsArrayDegrees
	 */
	public String getJsArrayDegrees() {
		return jsArrayDegrees;
	}

	/**
	 * @param jsArrayDegrees
	 *            the jsArrayDegrees to set
	 */
	public void setJsArrayDegrees(String jsArrayDegrees) {
		this.jsArrayDegrees = jsArrayDegrees;
	}

	/**
	 * @return the jsArrayBranches
	 */
	public String getJsArrayBranches() {
		return jsArrayBranches;
	}

	/**
	 * @param jsArrayBranches
	 *            the jsArrayBranches to set
	 */
	public void setJsArrayBranches(String jsArrayBranches) {
		this.jsArrayBranches = jsArrayBranches;
	}

	/**
	 * @return the jsArrayRequirements
	 */
	public String getJsArrayRequirements() {
		return jsArrayRequirements;
	}

	/**
	 * @param jsArrayRequirements
	 *            the jsArrayRequirements to set
	 */
	public void setJsArrayRequirements(String jsArrayRequirements) {
		this.jsArrayRequirements = jsArrayRequirements;
	}

	/**
	 * @return the hiringProcess
	 */
	public List getHiringProcess() {
		return hiringProcess;
	}

	/**
	 * @param hiringProcess
	 *            the hiringProcess to set
	 */
	public void setHiringProcess(List hiringProcess) {
		this.hiringProcess = hiringProcess;
	}

	/**
	 * @return the users
	 */
	public List getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List users) {
		this.users = users;
	}

	/**
	 * @return the jsArrayHiringProcess
	 */
	public String getJsArrayHiringProcess() {
		return jsArrayHiringProcess;
	}

	/**
	 * @param jsArrayHiringProcess
	 *            the jsArrayHiringProcess to set
	 */
	public void setJsArrayHiringProcess(String jsArrayHiringProcess) {
		this.jsArrayHiringProcess = jsArrayHiringProcess;
	}

	/**
	 * @return the feedbackFormId
	 */
	public String getFeedbackFormId() {
		return feedbackFormId;
	}

	/**
	 * @param feedbackFormId
	 *            the feedbackFormId to set
	 */
	public void setFeedbackFormId(String feedbackFormId) {
		this.feedbackFormId = feedbackFormId;
	}

	/**
	 * @return the positionStepId
	 */
	public String getPositionStepId() {
		return positionStepId;
	}

	/**
	 * @param positionStepId
	 *            the positionStepId to set
	 */
	public void setPositionStepId(String positionStepId) {
		this.positionStepId = positionStepId;
	}

	/**
	 * @return the feedbackDecision
	 */
	public String getFeedbackDecision() {
		return feedbackDecision;
	}

	/**
	 * @param feedbackDecision
	 *            the feedbackDecision to set
	 */
	public void setFeedbackDecision(String feedbackDecision) {
		this.feedbackDecision = feedbackDecision;
	}

	/**
	 * @return the nextUserId
	 */
	public String getNextUserId() {
		return nextUserId;
	}

	/**
	 * @param nextUserId
	 *            the nextUserId to set
	 */
	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}

	/**
	 * @return the feedbackComment
	 */
	public String getFeedbackComment() {
		return feedbackComment;
	}

	/**
	 * @param feedbackComment
	 *            the feedbackComment to set
	 */
	public void setFeedbackComment(String feedbackComment) {
		this.feedbackComment = feedbackComment;
	}

	/**
	 * @return the fromApprovalStepId
	 */
	public String getFromApprovalStepId() {
		return fromApprovalStepId;
	}

	/**
	 * @param fromApprovalStepId
	 *            the fromApprovalStepId to set
	 */
	public void setFromApprovalStepId(String fromApprovalStepId) {
		this.fromApprovalStepId = fromApprovalStepId;
	}

	/**
	 * @return the toApprovalStepId
	 */
	public String getToApprovalStepId() {
		return toApprovalStepId;
	}

	/**
	 * @param toApprovalStepId
	 *            the toApprovalStepId to set
	 */
	public void setToApprovalStepId(String toApprovalStepId) {
		this.toApprovalStepId = toApprovalStepId;
	}

	/**
	 * @return the feedbackId
	 */
	public String getFeedbackId() {
		return feedbackId;
	}

	/**
	 * @param feedbackId
	 *            the feedbackId to set
	 */
	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	/**
	 * @return the positionPriority
	 */
	public String getPositionPriority() {
		return positionPriority;
	}

	/**
	 * @param positionPriority
	 *            the positionPriority to set
	 */
	public void setPositionPriority(String positionPriority) {
		this.positionPriority = positionPriority;
	}

	/**
	 * @return the positionCreateDate
	 */
	public String getPositionCreateDate() {
		return positionCreateDate;
	}

	/**
	 * @param positionCreateDate
	 *            the positionCreateDate to set
	 */
	public void setPositionCreateDate(String positionCreateDate) {
		this.positionCreateDate = positionCreateDate;
	}

	/**
	 * @return the publishType
	 */
	public String getPublishType() {
		return publishType;
	}

	/**
	 * @param publishType
	 *            the publishType to set
	 */
	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	/**
	 * @return the positionsToBePublished
	 */
	public String getPositionsToBePublished() {
		return positionsToBePublished;
	}

	/**
	 * @param positionsToBePublished
	 *            the positionsToBePublished to set
	 */
	public void setPositionsToBePublished(String positionsToBePublished) {
		this.positionsToBePublished = positionsToBePublished;
	}

	/**
	 * @return the positionsNotToBePublished
	 */
	public String getPositionsNotToBePublished() {
		return positionsNotToBePublished;
	}

	/**
	 * @param positionsNotToBePublished
	 *            the positionsNotToBePublished to set
	 */
	public void setPositionsNotToBePublished(String positionsNotToBePublished) {
		this.positionsNotToBePublished = positionsNotToBePublished;
	}

	/**
	 * @return the hrUsers
	 */
	public List getHrUsers() {
		return hrUsers;
	}

	/**
	 * @param hrUsers
	 *            the hrUsers to set
	 */
	public void setHrUsers(List hrUsers) {
		this.hrUsers = hrUsers;
	}

	/**
	 * @return the notifyUserIds
	 */
	public String getNotifyUserIds() {
		return notifyUserIds;
	}

	/**
	 * @param notifyUserIds
	 *            the notifyUserIds to set
	 */
	public void setNotifyUserIds(String notifyUserIds) {
		this.notifyUserIds = notifyUserIds;
	}

	public String getApprovalUserIds() {
		return approvalUserIds;
	}

	public void setApprovalUserIds(String approvalUserIds) {
		this.approvalUserIds = approvalUserIds;
	}

	/**
	 * @return the requisitionApprovalTemplateId
	 */
	public String getRequisitionApprovalTemplateId() {
		return requisitionApprovalTemplateId;
	}

	/**
	 * @param requisitionApprovalTemplateId
	 *            the requisitionApprovalTemplateId to set
	 */
	public void setRequisitionApprovalTemplateId(String requisitionApprovalTemplateId) {
		this.requisitionApprovalTemplateId = requisitionApprovalTemplateId;
	}

	/**
	 * @return the markCandidatesAndClosePosition
	 */
	public String getMarkCandidatesAndClosePosition() {
		return markCandidatesAndClosePosition;
	}

	/**
	 * @param markCandidatesAndClosePosition
	 *            the markCandidatesAndClosePosition to set
	 */
	public void setMarkCandidatesAndClosePosition(String markCandidatesAndClosePosition) {
		this.markCandidatesAndClosePosition = markCandidatesAndClosePosition;
	}

	/**
	 * @return the ruleType
	 */
	public String getRuleType() {
		return ruleType;
	}

	/**
	 * @param ruleType
	 *            the ruleType to set
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * @return the institute
	 */
	public String getInstitute() {
		return institute;
	}

	/**
	 * @param institute
	 *            the institute to set
	 */
	public void setInstitute(String institute) {
		this.institute = institute;
	}

	/**
	 * @return the instituteId
	 */
	public String getInstituteId() {
		return instituteId;
	}

	/**
	 * @param instituteId
	 *            the instituteId to set
	 */
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	/**
	 * @return the currentLocation
	 */
	public String getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation
	 *            the currentLocation to set
	 */
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * @return the expSelected
	 */
	public String getExpSelected() {
		return expSelected;
	}

	/**
	 * @param expSelected
	 *            the expSelected to set
	 */
	public void setExpSelected(String expSelected) {
		this.expSelected = expSelected;
	}

	/**
	 * @return the degreeSelected
	 */
	public String getDegreeSelected() {
		return degreeSelected;
	}

	/**
	 * @param degreeSelected
	 *            the degreeSelected to set
	 */
	public void setDegreeSelected(String degreeSelected) {
		this.degreeSelected = degreeSelected;
	}

	/**
	 * @return the branchSelected
	 */
	public String getBranchSelected() {
		return branchSelected;
	}

	/**
	 * @param branchSelected
	 *            the branchSelected to set
	 */
	public void setBranchSelected(String branchSelected) {
		this.branchSelected = branchSelected;
	}

	/**
	 * @return the instituteSelected
	 */
	public String getInstituteSelected() {
		return instituteSelected;
	}

	/**
	 * @param instituteSelected
	 *            the instituteSelected to set
	 */
	public void setInstituteSelected(String instituteSelected) {
		this.instituteSelected = instituteSelected;
	}

	/**
	 * @return the currentLocationSelected
	 */
	public String getCurrentLocationSelected() {
		return currentLocationSelected;
	}

	/**
	 * @param currentLocationSelected
	 *            the currentLocationSelected to set
	 */
	public void setCurrentLocationSelected(String currentLocationSelected) {
		this.currentLocationSelected = currentLocationSelected;
	}

	/**
	 * @return the autoCompleteFieldType
	 */
	public String getAutoCompleteFieldType() {
		return autoCompleteFieldType;
	}

	/**
	 * @param autoCompleteFieldType
	 *            the autoCompleteFieldType to set
	 */
	public void setAutoCompleteFieldType(String autoCompleteFieldType) {
		this.autoCompleteFieldType = autoCompleteFieldType;
	}

	/**
	 * @return the isPublishedToSite
	 */
	public String getIsPublishedToSite() {
		return isPublishedToSite;
	}

	/**
	 * @param isPublishedToSite
	 *            the isPublishedToSite to set
	 */
	public void setIsPublishedToSite(String isPublishedToSite) {
		this.isPublishedToSite = isPublishedToSite;
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
	 * @return the draftId
	 */
	public String getDraftId() {
		return draftId;
	}

	/**
	 * @param draftId
	 *            the draftId to set
	 */
	public void setDraftId(String draftId) {
		this.draftId = draftId;
	}

	/**
	 * @return the draftName
	 */
	public String getDraftName() {
		return draftName;
	}

	/**
	 * @param draftName
	 *            the draftName to set
	 */
	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}

	/**
	 * @return the relativeFilePath
	 */
	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	/**
	 * @param relativeFilePath
	 *            the relativeFilePath to set
	 */
	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}

	/**
	 * @return the recruiterId
	 */
	public String getRecruiterId() {
		return recruiterId;
	}

	/**
	 * @param recruiterId
	 *            the recruiterId to set
	 */
	public void setRecruiterId(String recruiterId) {
		this.recruiterId = recruiterId;
	}

	/**
	 * @return the filterFor
	 */
	public String getFilterFor() {
		return filterFor;
	}

	/**
	 * @param filterFor
	 *            the filterFor to set
	 */
	public void setFilterFor(String filterFor) {
		this.filterFor = filterFor;
	}

	/**
	 * @return the skillId
	 */
	public String getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId
	 *            the skillId to set
	 */
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	public String getFinishCopyPosition() {
		return finishCopyPosition;
	}

	public void setFinishCopyPosition(String finishCopyPosition) {
		this.finishCopyPosition = finishCopyPosition;
	}

	public String getJsArrayBudgetItems() {
		return jsArrayBudgetItems;
	}

	public void setJsArrayBudgetItems(String jsArrayBudgetItems) {
		this.jsArrayBudgetItems = jsArrayBudgetItems;
	}

	public String getBudgetItemId() {
		return budgetItemId;
	}

	public void setBudgetItemId(String budgetItemId) {
		this.budgetItemId = budgetItemId;
	}

	public String getBudgetItemName() {
		return budgetItemName;
	}

	public void setBudgetItemName(String budgetItemName) {
		this.budgetItemName = budgetItemName;
	}

	public List getTrackModeNotifications() {
		if (trackModeNotifications == null) {
			trackModeNotifications = new ArrayList();
		}
		return trackModeNotifications;
	}

	public void setTrackModeNotifications(List trackModeNotifications) {
		this.trackModeNotifications = trackModeNotifications;
	}

	public String getIsBudgetCommitted() {
		return isBudgetCommitted;
	}

	public void setIsBudgetCommitted(String isBudgetCommitted) {
		this.isBudgetCommitted = isBudgetCommitted;
	}

	public String getGradeId() {
		return gradeId;
	}

	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}

	public String getBandId() {
		return bandId;
	}

	public void setBandId(String bandId) {
		this.bandId = bandId;
	}

	public String getJsArrayGrades() {
		return jsArrayGrades;
	}

	public void setJsArrayGrades(String jsArrayGrades) {
		this.jsArrayGrades = jsArrayGrades;
	}

	public String getJsArrayBands() {
		return jsArrayBands;
	}

	public void setJsArrayBands(String jsArrayBands) {
		this.jsArrayBands = jsArrayBands;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	/**
	 * @return the currentNoOfOpenings
	 */
	public String getCurrentNoOfOpenings() {
		return currentNoOfOpenings;
	}

	/**
	 * @param currentNoOfOpenings
	 *            the currentNoOfOpenings to set
	 */
	public void setCurrentNoOfOpenings(String currentNoOfOpenings) {
		this.currentNoOfOpenings = currentNoOfOpenings;
	}

	/**
	 * @return the customFieldFilterId
	 */
	public String getCustomFieldFilterId() {
		return customFieldFilterId;
	}

	/**
	 * @param customFieldFilterId
	 *            the customFieldFilterId to set
	 */
	public void setCustomFieldFilterId(String customFieldFilterId) {
		this.customFieldFilterId = customFieldFilterId;
	}

	/**
	 * @return the customFieldFilterType
	 */
	public String getCustomFieldFilterType() {
		return customFieldFilterType;
	}

	/**
	 * @param customFieldFilterType
	 *            the customFieldFilterType to set
	 */
	public void setCustomFieldFilterType(String customFieldFilterType) {
		this.customFieldFilterType = customFieldFilterType;
	}

	/**
	 * @return the customFieldFilterValue
	 */
	public String getCustomFieldFilterValue() {
		return customFieldFilterValue;
	}

	/**
	 * @param customFieldFilterValue
	 *            the customFieldFilterValue to set
	 */
	public void setCustomFieldFilterValue(String customFieldFilterValue) {
		this.customFieldFilterValue = customFieldFilterValue;
	}

	/**
	 * @return the employeeAnnouncements
	 */
	public String getEmployeeAnnouncements() {
		return employeeAnnouncements;
	}

	/**
	 * @param employeeAnnouncements
	 *            the employeeAnnouncements to set
	 */
	public void setEmployeeAnnouncements(String employeeAnnouncements) {
		this.employeeAnnouncements = employeeAnnouncements;
	}

	public String getSendPositionChangeNotification() {
		return sendPositionChangeNotification;
	}

	public void setSendPositionChangeNotification(String sendPositionChangeNotification) {
		this.sendPositionChangeNotification = sendPositionChangeNotification;
	}

	public String getCopyFrom() {
		return copyFrom;
	}

	public void setCopyFrom(String copyFrom) {
		this.copyFrom = copyFrom;
	}

	public String getSub3Department() {
		return sub3Department;
	}

	public void setSub3Department(String sub3Department) {
		this.sub3Department = sub3Department;
	}

	public String getSub4Department() {
		return sub4Department;
	}

	public void setSub4Department(String sub4Department) {
		this.sub4Department = sub4Department;
	}

	public String getSub3DepartmentId() {
		return sub3DepartmentId;
	}

	public void setSub3DepartmentId(String sub3DepartmentId) {
		this.sub3DepartmentId = sub3DepartmentId;
	}

	public String getSub4DepartmentId() {
		return sub4DepartmentId;
	}

	public void setSub4DepartmentId(String sub4DepartmentId) {
		this.sub4DepartmentId = sub4DepartmentId;
	}

	public String getJsArraySub3Departments() {
		return jsArraySub3Departments;
	}

	public void setJsArraySub3Departments(String jsArraySub3Departments) {
		this.jsArraySub3Departments = jsArraySub3Departments;
	}

	public String getJsArraySub4Departments() {
		return jsArraySub4Departments;
	}

	public void setJsArraySub4Departments(String jsArraySub4Departments) {
		this.jsArraySub4Departments = jsArraySub4Departments;
	}

	public String getRequisitionApprovalTemplateName() {
		return requisitionApprovalTemplateName;
	}

	public void setRequisitionApprovalTemplateName(String requisitionApprovalTemplateName) {
		this.requisitionApprovalTemplateName = requisitionApprovalTemplateName;
	}

	/**
	 * @return the buId
	 */
	public String getBuId() {
		return buId;
	}

	/**
	 * @param buId
	 *            the buId to set
	 */
	public void setBuId(String buId) {
		this.buId = buId;
	}

	/**
	 * @return the buName
	 */
	public String getBuName() {
		return buName;
	}

	/**
	 * @param buName
	 *            the buName to set
	 */
	public void setBuName(String buName) {
		this.buName = buName;
	}

	/**
	 * @return the costCenterId
	 */
	public String getCostCenterId() {
		return costCenterId;
	}

	/**
	 * @param costCenterId
	 *            the costCenterId to set
	 */
	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}

	/**
	 * @return the costCenterName
	 */
	public String getCostCenterName() {
		return costCenterName;
	}

	/**
	 * @param costCenterName
	 *            the costCenterName to set
	 */
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	/**
	 * @return the businessUnitXML
	 */
	public String getBusinessUnitXML() {
		return businessUnitXML;
	}

	/**
	 * @param businessUnitXML
	 *            the businessUnitXML to set
	 */
	public void setBusinessUnitXML(String businessUnitXML) {
		this.businessUnitXML = businessUnitXML;
	}

	/**
	 * @return the costCenterXML
	 */
	public String getCostCenterXML() {
		return costCenterXML;
	}

	/**
	 * @param costCenterXML
	 *            the costCenterXML to set
	 */
	public void setCostCenterXML(String costCenterXML) {
		this.costCenterXML = costCenterXML;
	}

	/**
	 * @return the jsArrayPositionOwners
	 */
	public String getJsArrayPositionOwners() {
		return jsArrayPositionOwners;
	}

	/**
	 * @param jsArrayPositionOwners
	 *            the jsArrayPositionOwners to set
	 */
	public void setJsArrayPositionOwners(String jsArrayPositionOwners) {
		this.jsArrayPositionOwners = jsArrayPositionOwners;
	}

	/**
	 * @return the positionOwnerId
	 */
	public String getPositionOwnerId() {
		return positionOwnerId;
	}

	/**
	 * @param positionOwnerId
	 *            the positionOwnerId to set
	 */
	public void setPositionOwnerId(String positionOwnerId) {
		this.positionOwnerId = positionOwnerId;
	}

	/**
	 * @return the positionOwnerName
	 */
	public String getPositionOwnerName() {
		return positionOwnerName;
	}

	/**
	 * @param positionOwnerName
	 *            the positionOwnerName to set
	 */
	public void setPositionOwnerName(String positionOwnerName) {
		this.positionOwnerName = positionOwnerName;
	}

	/**
	 * @return the employeeCanEmail
	 */
	public String getEmployeeCanEmail() {
		return employeeCanEmail;
	}

	/**
	 * @param employeeCanEmail
	 *            the employeeCanEmail to set
	 */
	public void setEmployeeCanEmail(String employeeCanEmail) {
		this.employeeCanEmail = employeeCanEmail;
	}

	public String getDeletePositionTokenId() {
		return deletePositionTokenId;
	}

	public void setDeletePositionTokenId(String deletePositionTokenId) {
		this.deletePositionTokenId = deletePositionTokenId;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getJobIndustryCode() {
		return jobIndustryCode;
	}

	public void setJobIndustryCode(String jobIndustryCode) {
		this.jobIndustryCode = jobIndustryCode;
	}

	public String getJobFunctionCode() {
		return jobFunctionCode;
	}

	public void setJobFunctionCode(String jobFunctionCode) {
		this.jobFunctionCode = jobFunctionCode;
	}

	public String getJobRoleCode() {
		return jobRoleCode;
	}

	public void setJobRoleCode(String jobRoleCode) {
		this.jobRoleCode = jobRoleCode;
	}

	public String getJobKeywords() {
		return jobKeywords;
	}

	public void setJobKeywords(String jobKeywords) {
		this.jobKeywords = jobKeywords;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMinimumSalary() {
		return minimumSalary;
	}

	public void setMinimumSalary(String minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	public String getMaximumSalary() {
		return maximumSalary;
	}

	public void setMaximumSalary(String maximumSalary) {
		this.maximumSalary = maximumSalary;
	}

	public String getBenefitsDescription() {
		return benefitsDescription;
	}

	public void setBenefitsDescription(String benefitsDescription) {
		this.benefitsDescription = benefitsDescription;
	}

	public String getDisplaySalary() {
		return displaySalary;
	}

	public void setDisplaySalary(String displaySalary) {
		this.displaySalary = displaySalary;
	}

	public String getDesiredCandidateSummaryText() {
		return desiredCandidateSummaryText;
	}

	public void setDesiredCandidateSummaryText(String desiredCandidateSummaryText) {
		this.desiredCandidateSummaryText = desiredCandidateSummaryText;
	}

	public String getContactPersonEmail() {
		return contactPersonEmail;
	}

	public void setContactPersonEmail(String contactPersonEmail) {
		this.contactPersonEmail = contactPersonEmail;
	}

	public String getApplyByWebURL() {
		return applyByWebURL;
	}

	public void setApplyByWebURL(String applyByWebURL) {
		this.applyByWebURL = applyByWebURL;
	}

	public String getJobFeedResponseEmail() {
		return jobFeedResponseEmail;
	}

	public void setJobFeedResponseEmail(String jobFeedResponseEmail) {
		this.jobFeedResponseEmail = jobFeedResponseEmail;
	}

	public String getSalaryCurrency() {
		return salaryCurrency;
	}

	public void setSalaryCurrency(String salaryCurrency) {
		this.salaryCurrency = salaryCurrency;
	}

	public String getJsArrayCountries() {
		return jsArrayCountries;
	}

	public void setJsArrayCountries(String jsArrayCountries) {
		this.jsArrayCountries = jsArrayCountries;
	}

	public String getJsArrayJobIndustryCodes() {
		return jsArrayJobIndustryCodes;
	}

	public void setJsArrayJobIndustryCodes(String jsArrayJobIndustryCodes) {
		this.jsArrayJobIndustryCodes = jsArrayJobIndustryCodes;
	}

	public String getJsArrayJobFunctionalAreaCodes() {
		return jsArrayJobFunctionalAreaCodes;
	}

	public void setJsArrayJobFunctionalAreaCodes(String jsArrayJobFunctionalAreaCodes) {
		this.jsArrayJobFunctionalAreaCodes = jsArrayJobFunctionalAreaCodes;
	}

	public String getJsArrayJobRoleCodes() {
		return jsArrayJobRoleCodes;
	}

	public void setJsArrayJobRoleCodes(String jsArrayJobRoleCodes) {
		this.jsArrayJobRoleCodes = jsArrayJobRoleCodes;
	}

	/**
	 * @return the dropReason
	 */
	public String getDropReason() {
		return dropReason;
	}

	/**
	 * @param dropReason
	 *            the dropReason to set
	 */
	public void setDropReason(String dropReason) {
		this.dropReason = dropReason;
	}

	/**
	 * @return the dropComment
	 */
	public String getDropComment() {
		return dropComment;
	}

	/**
	 * @param dropComment
	 *            the dropComment to set
	 */
	public void setDropComment(String dropComment) {
		this.dropComment = dropComment;
	}

	public String getApplicantFeedbackFormId() {
		return applicantFeedbackFormId;
	}

	public void setApplicantFeedbackFormId(String applicantFeedbackFormId) {
		this.applicantFeedbackFormId = applicantFeedbackFormId;
	}

	/**
	 * @return the positionIdForAP
	 */
	public String getPositionIdForAP() {
		return positionIdForAP;
	}

	/**
	 * @param positionIdForAP
	 *            the positionIdForAP to set
	 */
	public void setPositionIdForAP(String positionIdForAP) {
		this.positionIdForAP = positionIdForAP;
	}

	/**
	 * @return the positionCodeForAP
	 */
	public String getPositionCodeForAP() {
		return positionCodeForAP;
	}

	/**
	 * @param positionCodeForAP
	 *            the positionCodeForAP to set
	 */
	public void setPositionCodeForAP(String positionCodeForAP) {
		this.positionCodeForAP = positionCodeForAP;
	}

	/**
	 * @return the positionTitleForAP
	 */
	public String getPositionTitleForAP() {
		return positionTitleForAP;
	}

	/**
	 * @param positionTitleForAP
	 *            the positionTitleForAP to set
	 */
	public void setPositionTitleForAP(String positionTitleForAP) {
		this.positionTitleForAP = positionTitleForAP;
	}

	/**
	 * @return the pLocation
	 */
	public String getpLocation() {
		return pLocation;
	}

	/**
	 * @param pLocation
	 *            the pLocation to set
	 */
	public void setpLocation(String pLocation) {
		this.pLocation = pLocation;
	}

	/**
	 * @return the pSubLocation
	 */
	public String getpSubLocation() {
		return pSubLocation;
	}

	/**
	 * @param pSubLocation
	 *            the pSubLocation to set
	 */
	public void setpSubLocation(String pSubLocation) {
		this.pSubLocation = pSubLocation;
	}

	/**
	 * @return the pSubSubLocation
	 */
	public String getpSubSubLocation() {
		return pSubSubLocation;
	}

	/**
	 * @param pSubSubLocation
	 *            the pSubSubLocation to set
	 */
	public void setpSubSubLocation(String pSubSubLocation) {
		this.pSubSubLocation = pSubSubLocation;
	}

	/**
	 * @return the pLocationId
	 */
	public String getpLocationId() {
		return pLocationId;
	}

	/**
	 * @param pLocationId
	 *            the pLocationId to set
	 */
	public void setpLocationId(String pLocationId) {
		this.pLocationId = pLocationId;
	}

	/**
	 * @return the pSubLocationId
	 */
	public String getpSubLocationId() {
		return pSubLocationId;
	}

	/**
	 * @param pSubLocationId
	 *            the pSubLocationId to set
	 */
	public void setpSubLocationId(String pSubLocationId) {
		this.pSubLocationId = pSubLocationId;
	}

	/**
	 * @return the pSubSubLocationId
	 */
	public String getpSubSubLocationId() {
		return pSubSubLocationId;
	}

	/**
	 * @param pSubSubLocationId
	 *            the pSubSubLocationId to set
	 */
	public void setpSubSubLocationId(String pSubSubLocationId) {
		this.pSubSubLocationId = pSubSubLocationId;
	}

	/**
	 * @return the jsArrayLocations
	 */
	public String getJsArrayLocations() {
		return jsArrayLocations;
	}

	/**
	 * @param jsArrayLocations
	 *            the jsArrayLocations to set
	 */
	public void setJsArrayLocations(String jsArrayLocations) {
		this.jsArrayLocations = jsArrayLocations;
	}

	/**
	 * @return the jsArraySubLocations
	 */
	public String getJsArraySubLocations() {
		return jsArraySubLocations;
	}

	/**
	 * @param jsArraySubLocations
	 *            the jsArraySubLocations to set
	 */
	public void setJsArraySubLocations(String jsArraySubLocations) {
		this.jsArraySubLocations = jsArraySubLocations;
	}

	/**
	 * @return the jsArraySubSubLocations
	 */
	public String getJsArraySubSubLocations() {
		return jsArraySubSubLocations;
	}

	/**
	 * @param jsArraySubSubLocations
	 *            the jsArraySubSubLocations to set
	 */
	public void setJsArraySubSubLocations(String jsArraySubSubLocations) {
		this.jsArraySubSubLocations = jsArraySubSubLocations;
	}

	/**
	 * @return the positionCodeForAPDisabled
	 */
	public String getPositionCodeForAPDisabled() {
		return positionCodeForAP;
	}

	/**
	 * @return the pJobFunction
	 */
	public String getpJobFunction() {
		return pJobFunction;
	}

	/**
	 * @param pJobFunction
	 *            the pJobFunction to set
	 */
	public void setpJobFunction(String pJobFunction) {
		this.pJobFunction = pJobFunction;
	}

	/**
	 * @return the pJobPayGrade
	 */
	public String getpJobPayGrade() {
		return pJobPayGrade;
	}

	/**
	 * @param pJobPayGrade
	 *            the pJobPayGrade to set
	 */
	public void setpJobPayGrade(String pJobPayGrade) {
		this.pJobPayGrade = pJobPayGrade;
	}

	/**
	 * @return the pJobCode
	 */
	public String getpJobCode() {
		return pJobCode;
	}

	/**
	 * @param pJobCode
	 *            the pJobCode to set
	 */
	public void setpJobCode(String pJobCode) {
		this.pJobCode = pJobCode;
	}

	/**
	 * @return the pJobFunctionId
	 */
	public String getpJobFunctionId() {
		return pJobFunctionId;
	}

	/**
	 * @param pJobFunctionId
	 *            the pJobFunctionId to set
	 */
	public void setpJobFunctionId(String pJobFunctionId) {
		this.pJobFunctionId = pJobFunctionId;
	}

	/**
	 * @return the pJobPayGradeId
	 */
	public String getpJobPayGradeId() {
		return pJobPayGradeId;
	}

	/**
	 * @param pJobPayGradeId
	 *            the pJobPayGradeId to set
	 */
	public void setpJobPayGradeId(String pJobPayGradeId) {
		this.pJobPayGradeId = pJobPayGradeId;
	}

	/**
	 * @return the pJobCodeId
	 */
	public String getpJobCodeId() {
		return pJobCodeId;
	}

	/**
	 * @param pJobCodeId
	 *            the pJobCodeId to set
	 */
	public void setpJobCodeId(String pJobCodeId) {
		this.pJobCodeId = pJobCodeId;
	}

	/**
	 * @return the jsArrayJobFunction
	 */
	public String getJsArrayJobFunction() {
		return jsArrayJobFunction;
	}

	/**
	 * @param jsArrayJobFunction
	 *            the jsArrayJobFunction to set
	 */
	public void setJsArrayJobFunction(String jsArrayJobFunction) {
		this.jsArrayJobFunction = jsArrayJobFunction;
	}

	/**
	 * @return the jsArrayJobPayGrade
	 */
	public String getJsArrayJobPayGrade() {
		return jsArrayJobPayGrade;
	}

	/**
	 * @param jsArrayJobPayGrade
	 *            the jsArrayJobPayGrade to set
	 */
	public void setJsArrayJobPayGrade(String jsArrayJobPayGrade) {
		this.jsArrayJobPayGrade = jsArrayJobPayGrade;
	}

	/**
	 * @return the jsArrayJobCode
	 */
	public String getJsArrayJobCode() {
		return jsArrayJobCode;
	}

	/**
	 * @param jsArrayJobCode
	 *            the jsArrayJobCode to set
	 */
	public void setJsArrayJobCode(String jsArrayJobCode) {
		this.jsArrayJobCode = jsArrayJobCode;
	}

	/**
	 * @param positionCodeForAPDisabled
	 *            the positionCodeForAPDisabled to set
	 */
	public void setPositionCodeForAPDisabled(String positionCodeForAPDisabled) {
		this.positionCodeForAPDisabled = positionCodeForAPDisabled;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getTenthMarksFilter() {
		return tenthMarksFilter;
	}
	public void setTenthMarksFilter(String tenthMarksFilter) {
		this.tenthMarksFilter = tenthMarksFilter;
	}
	public String getTenthMarks() {
		return tenthMarks;
	}
	public void setTenthMarks(String tenthMarks) {
		this.tenthMarks = tenthMarks;
	}
	public String getTwelvethMarksFilter() {
		return twelvethMarksFilter;
	}
	public void setTwelvethMarksFilter(String twelvethMarksFilter) {
		this.twelvethMarksFilter = twelvethMarksFilter;
	}
	public String getTwelvethMarks() {
		return twelvethMarks;
	}
	public void setTwelvethMarks(String twelvethMarks) {
		this.twelvethMarks = twelvethMarks;
	}
	public String getGradeMarksFilter() {
		return gradeMarksFilter;
	}
	public void setGradeMarksFilter(String gradeMarksFilter) {
		this.gradeMarksFilter = gradeMarksFilter;
	}
	public String getGradeMarks() {
		return gradeMarks;
	}
	public void setGradeMarks(String gradeMarks) {
		this.gradeMarks = gradeMarks;
	}
	public String getPostGradeMarksFilter() {
		return postGradeMarksFilter;
	}
	public void setPostGradeMarksFilter(String postGradeMarksFilter) {
		this.postGradeMarksFilter = postGradeMarksFilter;
	}
	public String getPostGradeMarks() {
		return postGradeMarks;
	}
	public void setPostGradeMarks(String postGradeMarks) {
		this.postGradeMarks = postGradeMarks;
	}
	public String getAgeFilter() {
		return ageFilter;
	}
	public void setAgeFilter(String ageFilter) {
		this.ageFilter = ageFilter;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getYearOfExperienceFilter() {
		return yearOfExperienceFilter;
	}
	public void setYearOfExperienceFilter(String yearOfExperienceFilter) {
		this.yearOfExperienceFilter = yearOfExperienceFilter;
	}
	public String getYearOfExperience() {
		return yearOfExperience;
	}
	public void setYearOfExperience(String yearOfExperience) {
		this.yearOfExperience = yearOfExperience;
	}
	public String getGapInAcademics() {
		return gapInAcademics;
	}
	public void setGapInAcademics(String gapInAcademics) {
		this.gapInAcademics = gapInAcademics;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the countryId
	 */
	public String getCountryId() {
		return countryId;
	}

	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	/**
	 * @return the stateId
	 */
	public String getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the jsArrayCountry
	 */
	public String getJsArrayCountry() {
		return jsArrayCountry;
	}

	/**
	 * @param jsArrayCountry the jsArrayCountry to set
	 */
	public void setJsArrayCountry(String jsArrayCountry) {
		this.jsArrayCountry = jsArrayCountry;
	}

	/**
	 * @return the jsArrayState
	 */
	public String getJsArrayState() {
		return jsArrayState;
	}

	/**
	 * @param jsArrayState the jsArrayState to set
	 */
	public void setJsArrayState(String jsArrayState) {
		this.jsArrayState = jsArrayState;
	}

	
}