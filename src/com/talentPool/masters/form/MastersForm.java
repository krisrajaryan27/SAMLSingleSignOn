/**
 * 
 */
package com.talentPool.masters.form;

import com.talentPool.common.base.TPActionForm;
import com.talentPool.common.utils.Utils;

/**
 * @author shivprasad
 * 
 */
public class MastersForm extends TPActionForm {
	private static final long serialVersionUID = 1L;
	private String subMode;
	private String[] aliases = new String[3];
	private String branchName;
	private String branchId;
	private String departmentName;
	private String deptId;
	private String degreeName;
	private String degreeId;
	private String degreeType;
	private String parentDepartmentId;
	private String parentDepartmentLevel;
	
	private String buId;
	private String buName;
	private String costCenterId;
	private String costCenterName;

	/**
	 * additional variables for source for vendor management 
	 */	
	private String email;
	private String phone;
	private String mobile;
	private String sendEmailToSource;
	private String sendSMSToSource;
	private String lockInPeriodOnImport;
	private String sourceBlacklisted;
	private String employeeCode;

	private String templateId;
	private String templateCode;
	private String templateName;
	private String templatePrivate;
	private String templateSubject;
	private String templateContent;

	private String templateType;
	private String templateTypeId;
	private String templateVariableIds;
	private String templateVariables;
	private String templateOwnerId;
	private String templateIsDefault;
	private String templateIsSaveAsDraft;
	private String doShowSaveAsDraftOption;
	private String jsArrayTemplateFor;

	
	private String flagId;
	private String flagText;
	private String flagImage;
	private String flagType;

	private String instituteName;
	private String instituteId;
	
	private String skillCategory;
	private String skillCategoryId;
	private String skillName;
	private String skillId;

	private String sourceTypeId;
	private String sourceTypeName;
	private String sourceName;
	private String sourceId;
	private String sourceCategoryType;
	
	//rating constants
	private String ratingId;
	private String ratingTitle;
	private String ratingFieldsString;
	private String submitted;
	
	//multiple select  constants
	private String multipleSelectId;
	private String multipleSelectTitle;
	private String multipleSelectFieldsString;
	
	//fedback categories
	private String feedbackFieldCategoryId;
	private String feedbackFieldCategory;
	private String feedbackFieldId;
	private String feedbackFieldTitle;
	private String feedbackFieldDesc;
	private String feedbackFieldType;
	private String applicantFieldId;
	private String fieldCategoryIsSummary;
	
	// location related fields.
	private String locationId;
	private String locationName;
	
	// inbox folder related fields
	private String folderId;
	private String folderName;
	private String systemDefined;
	
	private String sessionId;
	private String filePath;
	
	private String budgetGradeId;
	private String budgetGradeName;
	private String gradeRank;
	private String description;
	private String gradeRankUp;
	
	private String budgetBandId;
	private String budgetBandName;
	
	private String hireByDuration;
	
	private String officeId;
	private String officeName;
	private String officeAddress;
	private String officeDesc;
	
	// step master
	private String stepId;
	private String stepName;
	private String stepDesc;
	private String stepRank;
	private String stepOrder;
	private String systemStep;
	private String stepLevel;
	private String stage;
	private String stepSchedulable;
	private String stepDisabled;
	private String hideDisabled;
	private String sourceCvLimit;
	
	
	//added for Asian Paints Begin
	private String locId;
	private String locName;
	private String parentLocationId;
	private String parentLocationLevel;
	private String pLocation;
	private String pSubLocation;
	private String pSubSubLocation;
	private String pLocationId;
	private String pSubLocationId;
	private String pSubSubLocationId;
	//added for Asian Paints Begin
	
	/**
	 * @return the hideDisabled
	 */
	public String getHideDisabled() {
		return hideDisabled;
	}

	/**
	 * @param hideDisabled the hideDisabled to set
	 */
	public void setHideDisabled(String hideDisabled) {
		this.hideDisabled = hideDisabled;
	}

	/**
	 * @return the stepSchedulable
	 */
	public String getStepSchedulable() {
		return stepSchedulable;
	}

	/**
	 * @param stepSchedulable the stepSchedulable to set
	 */
	public void setStepSchedulable(String stepSchedulable) {
		this.stepSchedulable = stepSchedulable;
	}

	/**
	 * @return the stepId
	 */
	public String getStepId() {
		return stepId;
	}

	/**
	 * @return the stepOrder
	 */
	public String getStepOrder() {
		return stepOrder;
	}

	/**
	 * @param stepOrder the stepOrder to set
	 */
	public void setStepOrder(String stepOrder) {
		this.stepOrder = stepOrder;
	}

	/**
	 * @param stepId the stepId to set
	 */
	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	/**
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * @param stepName the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * @return the stepDesc
	 */
	public String getStepDesc() {
		return stepDesc;
	}

	/**
	 * @param stepDesc the stepDesc to set
	 */
	public void setStepDesc(String stepDesc) {
		this.stepDesc = stepDesc;
	}

	/**
	 * @return the stepRank
	 */
	public String getStepRank() {
		return stepRank;
	}

	/**
	 * @param stepRank the stepRank to set
	 */
	public void setStepRank(String stepRank) {
		this.stepRank = stepRank;
	}

	/**
	 * @return the systemStep
	 */
	public String getSystemStep() {
		return systemStep;
	}

	/**
	 * @param systemStep the systemStep to set
	 */
	public void setSystemStep(String systemStep) {
		this.systemStep = systemStep;
	}

	/**
	 * @return Returns the subMode.
	 */
	public String getSubMode() {
		return subMode;
	}

	/**
	 * @param subMode
	 *            The subMode to set.
	 */
	public void setSubMode(String subMode) {
		this.subMode = subMode;
	}

	/**
	 * @return the buId
	 */
	public String getBuId() {
		return buId;
	}

	/**
	 * @param buId the buId to set
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
	 * @param buName the buName to set
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
	 * @param costCenterId the costCenterId to set
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
	 * @param costCenterName the costCenterName to set
	 */
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	/**
	 * @return the aliases
	 */
	public String[] getAliases() {
		return aliases;
	}

	/**
	 * @param aliases the aliases to set
	 */
	public void setAliases(String[] aliases) {
		this.aliases = Utils.getArrayCopy(aliases);
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the degreeId
	 */
	public String getDegreeId() {
		return degreeId;
	}

	/**
	 * @param degreeId the degreeId to set
	 */
	public void setDegreeId(String degreeId) {
		this.degreeId = degreeId;
	}

	/**
	 * @return the degreeName
	 */
	public String getDegreeName() {
		return degreeName;
	}

	/**
	 * @param degreeName the degreeName to set
	 */
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}

	/**
	 * @return the departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * @param departmentName the departmentName to set
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * @return the deptId
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the lockInPeriodOnImport
	 */
	public String getLockInPeriodOnImport() {
		return lockInPeriodOnImport;
	}

	/**
	 * @param lockInPeriodOnImport the lockInPeriodOnImport to set
	 */
	public void setLockInPeriodOnImport(String lockInPeriodOnImport) {
		this.lockInPeriodOnImport = lockInPeriodOnImport;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the sendEmailToSource
	 */
	public String getSendEmailToSource() {
		return sendEmailToSource;
	}

	/**
	 * @param sendEmailToSource the sendEmailToSource to set
	 */
	public void setSendEmailToSource(String sendEmailToSource) {
		this.sendEmailToSource = sendEmailToSource;
	}

	/**
	 * @return the sendSMSToSource
	 */
	public String getSendSMSToSource() {
		return sendSMSToSource;
	}

	/**
	 * @param sendSMSToSource the sendSMSToSource to set
	 */
	public void setSendSMSToSource(String sendSMSToSource) {
		this.sendSMSToSource = sendSMSToSource;
	}

	/**
	 * @return the jsArrayTemplateFor
	 */
	public String getJsArrayTemplateFor() {
		return jsArrayTemplateFor;
	}

	/**
	 * @param jsArrayTemplateFor the jsArrayTemplateFor to set
	 */
	public void setJsArrayTemplateFor(String jsArrayTemplateFor) {
		this.jsArrayTemplateFor = jsArrayTemplateFor;
	}

	/**
	 * @return the templateCode
	 */
	public String getTemplateCode() {
		return templateCode;
	}

	/**
	 * @param templateCode the templateCode to set
	 */
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	/**
	 * @return the templateContent
	 */
	public String getTemplateContent() {
		return templateContent;
	}

	/**
	 * @param templateContent the templateContent to set
	 */
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the templateIsDefault
	 */
	public String getTemplateIsDefault() {
		return templateIsDefault;
	}

	/**
	 * @param templateIsDefault the templateIsDefault to set
	 */
	public void setTemplateIsDefault(String templateIsDefault) {
		this.templateIsDefault = templateIsDefault;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * @return the templateOwnerId
	 */
	public String getTemplateOwnerId() {
		return templateOwnerId;
	}

	/**
	 * @param templateOwnerId the templateOwnerId to set
	 */
	public void setTemplateOwnerId(String templateOwnerId) {
		this.templateOwnerId = templateOwnerId;
	}

	/**
	 * @return the templatePrivate
	 */
	public String getTemplatePrivate() {
		return templatePrivate;
	}

	/**
	 * @param templatePrivate the templatePrivate to set
	 */
	public void setTemplatePrivate(String templatePrivate) {
		this.templatePrivate = templatePrivate;
	}

	/**
	 * @return the templateSubject
	 */
	public String getTemplateSubject() {
		return templateSubject;
	}

	/**
	 * @param templateSubject the templateSubject to set
	 */
	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}

	/**
	 * @return the templateType
	 */
	public String getTemplateType() {
		return templateType;
	}

	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	/**
	 * @return the templateTypeId
	 */
	public String getTemplateTypeId() {
		return templateTypeId;
	}

	/**
	 * @param templateTypeId the templateTypeId to set
	 */
	public void setTemplateTypeId(String templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	/**
	 * @return the templateVariableIds
	 */
	public String getTemplateVariableIds() {
		return templateVariableIds;
	}

	/**
	 * @param templateVariableIds the templateVariableIds to set
	 */
	public void setTemplateVariableIds(String templateVariableIds) {
		this.templateVariableIds = templateVariableIds;
	}

	/**
	 * @return the templateVariables
	 */
	public String getTemplateVariables() {
		return templateVariables;
	}

	/**
	 * @param templateVariables the templateVariables to set
	 */
	public void setTemplateVariables(String templateVariables) {
		this.templateVariables = templateVariables;
	}

	/**
	 * @return the flagId
	 */
	public String getFlagId() {
		return flagId;
	}

	/**
	 * @param flagId the flagId to set
	 */
	public void setFlagId(String flagId) {
		this.flagId = flagId;
	}

	/**
	 * @return the flagText
	 */
	public String getFlagText() {
		return flagText;
	}

	/**
	 * @param flagText the flagText to set
	 */
	public void setFlagText(String flagText) {
		this.flagText = flagText;
	}

	/**
	 * @return the instituteId
	 */
	public String getInstituteId() {
		return instituteId;
	}

	/**
	 * @param instituteId the instituteId to set
	 */
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	/**
	 * @return the instituteName
	 */
	public String getInstituteName() {
		return instituteName;
	}

	/**
	 * @param instituteName the instituteName to set
	 */
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	/**
	 * @return the skillCategory
	 */
	public String getSkillCategory() {
		return skillCategory;
	}

	/**
	 * @param skillCategory the skillCategory to set
	 */
	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}

	/**
	 * @return the skillCategoryId
	 */
	public String getSkillCategoryId() {
		return skillCategoryId;
	}

	/**
	 * @param skillCategoryId the skillCategoryId to set
	 */
	public void setSkillCategoryId(String skillCategoryId) {
		this.skillCategoryId = skillCategoryId;
	}

	/**
	 * @return the skillId
	 */
	public String getSkillId() {
		return skillId;
	}

	/**
	 * @param skillId the skillId to set
	 */
	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}

	/**
	 * @return the skillName
	 */
	public String getSkillName() {
		return skillName;
	}

	/**
	 * @param skillName the skillName to set
	 */
	public void setSkillName(String skillName) {
		this.skillName = skillName;
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
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * @return the sourceTypeId
	 */
	public String getSourceTypeId() {
		return sourceTypeId;
	}

	/**
	 * @param sourceTypeId the sourceTypeId to set
	 */
	public void setSourceTypeId(String sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	/**
	 * @return the sourceTypeName
	 */
	public String getSourceTypeName() {
		return sourceTypeName;
	}

	/**
	 * @param sourceTypeName the sourceTypeName to set
	 */
	public void setSourceTypeName(String sourceTypeName) {
		this.sourceTypeName = sourceTypeName;
	}

	/**
	 * @return the ratingFields
	 */
	public String getRatingFieldsString() {
		return ratingFieldsString;
	}

	/**
	 * @param ratingFields the ratingFields to set
	 */
	public void setRatingFieldsString(String ratingFieldsString) {
		this.ratingFieldsString = ratingFieldsString;
	}

	/**
	 * @return the ratingId
	 */
	public String getRatingId() {
		return ratingId;
	}

	/**
	 * @param ratingId the ratingId to set
	 */
	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	/**
	 * @return the ratingTitle
	 */
	public String getRatingTitle() {
		return ratingTitle;
	}

	/**
	 * @param ratingTitle the ratingTitle to set
	 */
	public void setRatingTitle(String ratingTitle) {
		this.ratingTitle = ratingTitle;
	}

	/**
	 * @return the submitted
	 */
	public String getSubmitted() {
		return submitted;
	}

	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	/**
	 * @return the feedbackFieldCategory
	 */
	public String getFeedbackFieldCategory() {
		return feedbackFieldCategory;
	}

	/**
	 * @param feedbackFieldCategory the feedbackFieldCategory to set
	 */
	public void setFeedbackFieldCategory(String feedbackFieldCategory) {
		this.feedbackFieldCategory = feedbackFieldCategory;
	}

	/**
	 * @return the feedbackFieldCategoryId
	 */
	public String getFeedbackFieldCategoryId() {
		return feedbackFieldCategoryId;
	}

	/**
	 * @param feedbackFieldCategoryId the feedbackFieldCategoryId to set
	 */
	public void setFeedbackFieldCategoryId(String feedbackFieldCategoryId) {
		this.feedbackFieldCategoryId = feedbackFieldCategoryId;
	}

	/**
	 * @return the feedbackFieldId
	 */
	public String getFeedbackFieldId() {
		return feedbackFieldId;
	}

	/**
	 * @param feedbackFieldId the feedbackFieldId to set
	 */
	public void setFeedbackFieldId(String feedbackFieldId) {
		this.feedbackFieldId = feedbackFieldId;
	}

	/**
	 * @return the feedbackFieldTitle
	 */
	public String getFeedbackFieldTitle() {
		return feedbackFieldTitle;
	}

	/**
	 * @param feedbackFieldTitle the feedbackFieldTitle to set
	 */
	public void setFeedbackFieldTitle(String feedbackFieldTitle) {
		this.feedbackFieldTitle = feedbackFieldTitle;
	}

	/**
	 * @return the feedbackFieldDesc
	 */
	public String getFeedbackFieldDesc() {
		return feedbackFieldDesc;
	}

	/**
	 * @param feedbackFieldDesc the feedbackFieldDesc to set
	 */
	public void setFeedbackFieldDesc(String feedbackFieldDesc) {
		this.feedbackFieldDesc = feedbackFieldDesc;
	}

	/**
	 * @return the parentDepartmentId
	 */
	public String getParentDepartmentId() {
		return parentDepartmentId;
	}

	/**
	 * @param parentDepartmentId the parentDepartmentId to set
	 */
	public void setParentDepartmentId(String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	/**
	 * @return the parentDepartmentLevel
	 */
	public String getParentDepartmentLevel() {
		return parentDepartmentLevel;
	}

	/**
	 * @param parentDepartmentLevel the parentDepartmentLevel to set
	 */
	public void setParentDepartmentLevel(String parentDepartmentLevel) {
		this.parentDepartmentLevel = parentDepartmentLevel;
	}

	/**
	 * @return the sourceCategoryType
	 */
	public String getSourceCategoryType() {
		return sourceCategoryType;
	}

	/**
	 * @param sourceCategoryType the sourceCategoryType to set
	 */
	public void setSourceCategoryType(String sourceCategoryType) {
		this.sourceCategoryType = sourceCategoryType;
	}

	/**
	 * @return the locationId
	 */
	public String getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}

	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	/**
	 * @return the folderId
	 */
	public String getFolderId() {
		return folderId;
	}

	/**
	 * @param folderId the folderId to set
	 */
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * @return the systemDefined
	 */
	public String getSystemDefined() {
		return systemDefined;
	}

	/**
	 * @param systemDefined the systemDefined to set
	 */
	public void setSystemDefined(String systemDefined) {
		this.systemDefined = systemDefined;
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
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the flagImage
	 */
	public String getFlagImage() {
		return flagImage;
	}

	/**
	 * @param flagImage the flagImage to set
	 */
	public void setFlagImage(String flagImage) {
		this.flagImage = flagImage;
	}

	/**
	 * @return the flagType
	 */
	public String getFlagType() {
		return flagType;
	}

	/**
	 * @param flagType the flagType to set
	 */
	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getTemplateIsSaveAsDraft() {
		return templateIsSaveAsDraft;
	}

	public void setTemplateIsSaveAsDraft(String templateIsSaveAsDraft) {
		this.templateIsSaveAsDraft = templateIsSaveAsDraft;
	}

	public String getDoShowSaveAsDraftOption() {
		return doShowSaveAsDraftOption;
	}

	public void setDoShowSaveAsDraftOption(String doShowSaveAsDraftOption) {
		this.doShowSaveAsDraftOption = doShowSaveAsDraftOption;
	}

	public String getBudgetGradeId() {
		return budgetGradeId;
	}

	public void setBudgetGradeId(String budgetGradeId) {
		this.budgetGradeId = budgetGradeId;
	}

	public String getBudgetGradeName() {
		return budgetGradeName;
	}

	public void setBudgetGradeName(String budgetGradeName) {
		this.budgetGradeName = budgetGradeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBudgetBandId() {
		return budgetBandId;
	}

	public void setBudgetBandId(String budgetBandId) {
		this.budgetBandId = budgetBandId;
	}

	public String getBudgetBandName() {
		return budgetBandName;
	}

	public void setBudgetBandName(String budgetBandName) {
		this.budgetBandName = budgetBandName;
	}

	public String getHireByDuration() {
		return hireByDuration;
	}

	public void setHireByDuration(String hireByDuration) {
		this.hireByDuration = hireByDuration;
	}

	public String getMultipleSelectId() {
		return multipleSelectId;
	}

	public void setMultipleSelectId(String multipleSelectId) {
		this.multipleSelectId = multipleSelectId;
	}

	public String getMultipleSelectTitle() {
		return multipleSelectTitle;
	}

	public void setMultipleSelectTitle(String multipleSelectTitle) {
		this.multipleSelectTitle = multipleSelectTitle;
	}

	public String getMultipleSelectFieldsString() {
		return multipleSelectFieldsString;
	}

	public void setMultipleSelectFieldsString(String multipleSelectFieldsString) {
		this.multipleSelectFieldsString = multipleSelectFieldsString;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getOfficeDesc() {
		return officeDesc;
	}

	public void setOfficeDesc(String officeDesc) {
		this.officeDesc = officeDesc;
	}

	/**
	 * @return the degreeType
	 */
	public String getDegreeType() {
		return degreeType;
	}

	/**
	 * @param degreeType the degreeType to set
	 */
	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	/**
	 * @return the sourceBlacklisted
	 */
	public String getSourceBlacklisted() {
		return sourceBlacklisted;
	}

	/**
	 * @param sourceBlacklisted the sourceBlacklisted to set
	 */
	public void setSourceBlacklisted(String sourceBlacklisted) {
		this.sourceBlacklisted = sourceBlacklisted;
	}

	/**
	 * @return the gradeRank
	 */
	public String getGradeRank() {
		return gradeRank;
	}

	/**
	 * @param gradeRank the gradeRank to set
	 */
	public void setGradeRank(String gradeRank) {
		this.gradeRank = gradeRank;
	}

	/**
	 * @return the gradeRankUp
	 */
	public String getGradeRankUp() {
		return gradeRankUp;
	}

	/**
	 * @param gradeRankUp the gradeRankUp to set
	 */
	public void setGradeRankUp(String gradeRankUp) {
		this.gradeRankUp = gradeRankUp;
	}

	/**
	 * @return the fieldCategoryIsSummary
	 */
	public String getFieldCategoryIsSummary() {
		return fieldCategoryIsSummary;
	}

	/**
	 * @param fieldCategoryIsSummary the fieldCategoryIsSummary to set
	 */
	public void setFieldCategoryIsSummary(String fieldCategoryIsSummary) {
		this.fieldCategoryIsSummary = fieldCategoryIsSummary;
	}

	/**
	 * @return the feedbackFieldType
	 */
	public String getFeedbackFieldType() {
		return feedbackFieldType;
	}

	/**
	 * @param feedbackFieldType the feedbackFieldType to set
	 */
	public void setFeedbackFieldType(String feedbackFieldType) {
		this.feedbackFieldType = feedbackFieldType;
	}

	/**
	 * @return the applicantFieldId
	 */
	public String getApplicantFieldId() {
		return applicantFieldId;
	}

	/**
	 * @param applicantFieldId the applicantFieldId to set
	 */
	public void setApplicantFieldId(String applicantFieldId) {
		this.applicantFieldId = applicantFieldId;
	}

	public String getSourceCvLimit() {
		return sourceCvLimit;
	}

	public void setSourceCvLimit(String sourceCvLimit) {
		this.sourceCvLimit = sourceCvLimit;
	}

	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}

	/**
	 * @return the stepLevel
	 */
	public String getStepLevel() {
		return stepLevel;
	}

	/**
	 * @param stepLevel the stepLevel to set
	 */
	public void setStepLevel(String stepLevel) {
		this.stepLevel = stepLevel;
	}

	/**
	 * @return the stepDisabled
	 */
	public String getStepDisabled() {
		return stepDisabled;
	}

	/**
	 * @param stepDisabled the stepDisabled to set
	 */
	public void setStepDisabled(String stepDisabled) {
		this.stepDisabled = stepDisabled;
	}
	
	

	/**
	 * @return the locId
	 */
	public String getLocId() {
		return locId;
	}

	/**
	 * @param locId the locId to set
	 */
	public void setLocId(String locId) {
		this.locId = locId;
	}

	/**
	 * @return the locName
	 */
	public String getLocName() {
		return locName;
	}

	/**
	 * @param locName the locName to set
	 */
	public void setLocName(String locName) {
		this.locName = locName;
	}

	/**
	 * @return the pLocation
	 */
	public String getpLocation() {
		return pLocation;
	}

	/**
	 * @param pLocation the pLocation to set
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
	 * @param pSubLocation the pSubLocation to set
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
	 * @param pSubSubLocation the pSubSubLocation to set
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
	 * @param pLocationId the pLocationId to set
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
	 * @param pSubLocationId the pSubLocationId to set
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
	 * @param pSubSubLocationId the pSubSubLocationId to set
	 */
	public void setpSubSubLocationId(String pSubSubLocationId) {
		this.pSubSubLocationId = pSubSubLocationId;
	}

	/**
	 * @return the parentLocationId
	 */
	public String getParentLocationId() {
		return parentLocationId;
	}

	/**
	 * @param parentLocationId the parentLocationId to set
	 */
	public void setParentLocationId(String parentLocationId) {
		this.parentLocationId = parentLocationId;
	}

	/**
	 * @return the parentLocationLevel
	 */
	public String getParentLocationLevel() {
		return parentLocationLevel;
	}

	/**
	 * @param parentLocationLevel the parentLocationLevel to set
	 */
	public void setParentLocationLevel(String parentLocationLevel) {
		this.parentLocationLevel = parentLocationLevel;
	}
	
	
	
}
