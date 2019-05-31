package com.talentPool.applicant.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.bc.ApplicantBC;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.form.ApplicantFromWebForm;
import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.audit.action.AuditAction;
import com.talentPool.audit.constants.AuditConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.dataobject.FormFileData;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.custom.utils.CustomFieldDataProcessor;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.AutoImportManager;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.masters.dataobject.BranchesData;
import com.talentPool.masters.dataobject.DegreeData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionRuleData;
import com.talentPool.positions.dataobject.RuleInstituteData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.recaptcha.ReCaptchaImpl;
import com.talentPool.recaptcha.ReCaptchaResponse;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.UserConstants;

public class ApplicantRegistrationFromWebAction extends TPDispatchAction{
	
	public ActionForward applicantRegistrationFromSite(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "applicantRegistrationFromSite";
		try {
			ArrayList<CustomFieldData> customFields = null;
			CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
			CustomFieldManager customFieldManager = new CustomFieldManager();
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
			request.setAttribute("customFields", customFields);			
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting showing applicant registration form.", e);
		}
		return mapping.findForward(forward);
	}
	
	public ActionForward addApplicant(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "applicantAdded";
		try {
			ApplicantFromWebForm applicantFromWebForm = (ApplicantFromWebForm) actionForm;
			ActionErrors errors = (ActionErrors) (request.getAttribute(Globals.ERROR_KEY));
			if (errors == null) {
				errors = new ActionErrors();
			}
			validateForm(errors, applicantFromWebForm,request,response);
			if (errors!=null && errors.size() > 0) {
				request.setAttribute(Globals.ERROR_KEY, errors);
				return applicantRegistrationFromSite(mapping, actionForm, request, response);
			}else{
				String positionId = applicantFromWebForm.getPositionId();
				ApplicantData aData = getApplicantDataConstructed(applicantFromWebForm,request);
				applyRules(positionId,aData,applicantFromWebForm,request);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while saving applicant from web site", e);
		}
		return mapping.findForward(forward);
	}
	
	private void validateForm(ActionErrors errors, ApplicantFromWebForm aForm,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!Utils.isBlankOrNull(aForm.getApplicantEmail1())) {
			if (!Utils.isValidPattern(aForm.getApplicantEmail1(), Utils.regEmail)) {
				errors.add("add_applicant.errors.email1_not_valid", new ActionError("add_applicant.errors.email1_not_valid"));
			}
		}
		if (!Utils.isBlankOrNull(aForm.getApplicantEmail2())) {
			if (!Utils.isValidPattern(aForm.getApplicantEmail2(), Utils.regEmail)) {
				errors.add("add_applicant.errors.email2_not_valid", new ActionError("add_applicant.errors.email2_not_valid"));
			}
		}
		//check For file size
		FormFile formFile = aForm.getAttachedFile();
		long totalSize = formFile.getFileSize() ;
		double maxSizeInBytes = new Double(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_ATTACHMENT_SIZE_IN_MB)).doubleValue() * 1000 * 1024;
		if (totalSize > maxSizeInBytes) {
			errors.add("attachment.error.size_exceeded_max",new ActionError("attachment.error.size_exceeded_max"));
		}
		
		//validate Recaptcha Security code
		ReCaptchaImpl recaptcha = new ReCaptchaImpl();
		ReCaptchaResponse recResponse  = recaptcha.checkAnswer(request.getRemoteAddr(),request.getParameter("recaptcha_challenge_field"),request.getParameter("recaptcha_response_field"));
		if(!recResponse.isValid()){
			errors.add("add_applicant.errors.challenge_not_valid", new ActionError("add_applicant.errors.challenge_not_valid"));
		}
	}
	
	private void applyRules(String positionId,ApplicantData aData,ApplicantFromWebForm applicantFromWebForm,
			HttpServletRequest request){
		PositionManager positionManager = new PositionManager();
		SelectionProcessManager processManager = new SelectionProcessManager();
		try{
			PositionRuleData  ruleData = (PositionRuleData)positionManager.getPositionRule(positionId);
			String ruleType = ruleData.getRuleType();
			String clientIpAddr = getClientIpAddr(request);
			if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_DATABASE)){
				//Duplicate Check
				if(checkForDuplicates(applicantFromWebForm,aData)){
					addToInbox(applicantFromWebForm, aData,InboxConstants.ERROR_DUPLICATE);
				}else{
					saveApplicant(applicantFromWebForm,aData, clientIpAddr);
				}
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_INBOX)){
				addToInbox(applicantFromWebForm, aData,"");
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_MANDATORY)){			
				boolean isPassedFilters = chekFilterConditions(ruleData,positionId,aData,request);
				if(isPassedFilters){//add and shortlist the applicant
					if(checkForDuplicates(applicantFromWebForm,aData)){
						addToInbox(applicantFromWebForm, aData,InboxConstants.ERROR_DUPLICATE);
					}else{
						//Add new Applicant
						String applicantId = saveApplicant(applicantFromWebForm,aData, clientIpAddr);
						//shortlist applicant
						if(!Utils.isBlankOrNull(applicantId)){
							processManager.shortListApplicant(applicantId, positionId, null, UserConstants.ADMIN_ID, true);
						}
					}
				}else{// send to inbox
					addToInbox(applicantFromWebForm, aData,InboxConstants.MANDATORY_CONDITIONS_NOT_MATCHED);
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while applying rules against applicant Data", e);
		}
	}
	
	private boolean checkForDuplicates(ApplicantFromWebForm applicantFromWebForm,ApplicantData aData){
		ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
		ArrayList<ApplicantDuplicateSearchData> duplicates = applicantDuplicateChecker.getInternalDuplicateChecked(applicantFromWebForm.getApplicantId(), applicantFromWebForm.getApplicantName(),
				applicantFromWebForm.getApplicantEmail1(), applicantFromWebForm.getApplicantEmail2(), applicantFromWebForm.getApplicantCellPhone(), aData.getCustomFields());
		if (duplicates != null && duplicates.size() > 0) {
			return true;
		}
		return false;
	}
	
	private void addToInbox(ApplicantFromWebForm applicantFromWebForm,ApplicantData aData,String errorCode){
		PositionManager positionManager = new PositionManager();
		InboxManager inboxManager = new InboxManager();
		AutoImportManager autoImportManager = new AutoImportManager();
		try{
			ApplicantBC bc = new ApplicantBC();
			String content = bc.getAutoImportBody(applicantFromWebForm, aData,"");

			MessageData messageData = new MessageData();
			messageData.setHtmlBody(content);
			SimpleDataObject positionDescription = (SimpleDataObject)positionManager.getPositionDescriptionToView(applicantFromWebForm.getPositionId());
			
			messageData.setSubject(positionDescription.getString("positionCode"));
			InboxData iData = inboxManager.getCurrentInboxSettings();
			messageData.setTo(iData.getInboxEmail());
			messageData.setFrom(aData.getApplicantEmail1());
			messageData.setAutoImportFormat(InboxConstants.FORMAT_AUTO_IMPORT);
			messageData.setAutoImportTried(true);
			messageData.setAutoImportErrors(errorCode);
			
			AttachmentData attachmentData = uploadApplicantResumeFile(applicantFromWebForm);
			if(attachmentData!=null){
				attachmentData.setAttachmentType(InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
				ArrayList<AttachmentData> colAttachmentData = new ArrayList<AttachmentData>();
				colAttachmentData.add(attachmentData);
				messageData.setAttachments(colAttachmentData);
			}
			
			String emailId = inboxManager.saveMessage(messageData, InboxConstants.INBOX_FOLDER_INBOX, UserConstants.ADMIN_ID);
			autoImportManager.updateErrorCode(emailId, errorCode);
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while adding appliant to inbox", e);
		} 
	}
	
	/**
	 * Save applicant to database.
	 * @param applicantFromWebForm
	 * @param aData
	 * @return
	 */
	private String saveApplicant(ApplicantFromWebForm applicantFromWebForm,ApplicantData aData, String clientIpAddr){
		String applicantId = "";
		String relativeFilePath ="";
		String attachmentId = ""; 
		ApplicantManager applicantManager = new ApplicantManager();
		ApplicantBC applicantBC = new ApplicantBC();
		try{
			// upload a resume file.
			AttachmentData attachmentData = uploadApplicantResumeFile(applicantFromWebForm);
			if (attachmentData != null && !Utils.isBlankOrNull(attachmentData.getAttachmentFilePath())) {
				relativeFilePath = attachmentData.getAttachmentFilePath();
				attachmentId = attachmentData.getAttachmentId();
				String srcDocFilePath = Utils.concatFilePath(DocumentConstants.documentsPath, attachmentData.getAttachmentFilePath());
				WordToHtmlConverter converter = new WordToHtmlConverter();
				converter.convertToHtml(srcDocFilePath);
				
				String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, relativeFilePath);
				// read content from the file
				DocumentUploader documentUploader = new DocumentUploader();
				String htmlFilePath = documentUploader.getHtmlFilePathIfExist(filePath);
				if (!Utils.isBlankOrNull(htmlFilePath)) {
					filePath = htmlFilePath;
				}				
				GenericConverter conv = new GenericConverter();
				String textContent = conv.convert(filePath);
				aData.setApplicantTextResume(textContent);
			}
			aData = applicantBC.saveResumeAndUpdatePaths(aData, attachmentId, applicantFromWebForm.getEmailId(), relativeFilePath);
			
			// Save Applicant
			String skillIds = applicantFromWebForm.getPrimarySkillIds();
			applicantId = applicantManager.addApplicant(aData, skillIds, clientIpAddr);
			AuditAction auditAction = new AuditAction();
			auditAction.insertAuditInfo(TPLabels.getLabel("common.applicant"), AuditConstants.TYPE_ADDED, 
					applicantId, AuditConstants.AUDIT_CANDIDATE, aData.getUserId(), null,
					null, null, true, clientIpAddr);
			
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while adding an applicant data", e);
		}
		return applicantId;
	}
	
	private AttachmentData uploadApplicantResumeFile(ApplicantFromWebForm applicantFromWebForm){
		AttachmentData attachmentData = null;
		ApplicantManager applicantManager = new ApplicantManager();
		String error ="";
		try{
			FormFile formFile = applicantFromWebForm.getAttachedFile();
			// upload a resume file.
			if (formFile != null) {
				if (formFile.getFileSize() <= 0) {
					error = TPLabels.getLabel("inbox.error.could_not_read_file");
				}
				FormFileData formFileData = new FormFileData(formFile.getFileName(), formFile.getFileSize(), formFile.getContentType(), formFile.getInputStream());
				if (Utils.isBlankOrNull(error)) {
					 attachmentData = applicantManager.uploadResumeTmp(formFileData);
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error in uploading  applicant resume file", e);
		}
		return attachmentData;
	}
	
	private ApplicantData getApplicantDataConstructed(ApplicantFromWebForm aForm,HttpServletRequest request) {
		AdminManager adminManager = new AdminManager();
		CustomFieldManager customFieldManager = new CustomFieldManager();
		CustomFieldDataProcessor customFieldDataProcessor = new CustomFieldDataProcessor();
		
		ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
		if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
			customFields = customFieldManager.getCustomFieldsForInputAllowed(CustomFieldConstants.ENTITY_TYPE_APPLICANT, CustomFieldConstants.INPUT_ALLOWED, true);
			customFields = customFieldDataProcessor.setCustomFieldValuesFromRequest(request, customFields);
		}
		
		ApplicantData aData = new ApplicantData();
		aData.setApplicantName(aForm.getApplicantName());
		aData.setApplicantCity(aForm.getApplicantCity());
		aData.setApplicantEmail1(aForm.getApplicantEmail1());
		aData.setApplicantEmail2(aForm.getApplicantEmail2());
		aData.setApplicantWorkPhone(aForm.getApplicantWorkPhone());
		aData.setApplicantHomePhone(aForm.getApplicantHomePhone());
		aData.setApplicantCellPhone(aForm.getApplicantCellPhone());
		aData.setApplicantCurrentEmployer(aForm.getApplicantCurrentEmployer());
		aData.setCurrentCTC(aForm.getCurrentCTC());
		aData.setExpectedCTC(aForm.getExpectedCTC());
		aData.setNoticePeriod(aForm.getNoticePeriod());
		aData.setCustomFields(customFields);
		aData.setUserId(UserConstants.ADMIN_ID);
		aData.setApplicantOriginalResumePath("");
		aData.setApplicantOriginalDocPath("");
		aData.setIsConfidential(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
		
		SourceData sourceData = adminManager.getCompanyWebSiteSource();
		aData.setApplicantSourceId(Integer.parseInt(sourceData.getSourceId()));
		aData.setApplicantSourceTitle(sourceData.getSourceTitle());
		
		java.sql.Date dtWorkingFrom = null;
		if (aForm.getFresher().equals("0")) {
			dtWorkingFrom = Utils.convertToSQLDate("1" + Utils.dateDescSeparator + aForm.getApplicantWorkingSince(), Utils.regDDMMMYYYYFormat);
		}
		aData.setApplicantWorkingSince(dtWorkingFrom);
		
		ArrayList<EducationalData> educationalDetails = new ArrayList<EducationalData>();
		String[] yearOfPassing = request.getParameterValues("yearOfPassing");
		String[] institutes = request.getParameterValues("institute");
		String[] degreeIds = request.getParameterValues("degreeSelect");
		String[] branchIds = request.getParameterValues("branchSelect");
		String[] grade = request.getParameterValues("grade");
		String[] fromYear = request.getParameterValues("fromYear");
		String[] remarks = request.getParameterValues("remarks");
		String[] startDate = request.getParameterValues("startDate");
		String[] endDate = request.getParameterValues("endDate");
		String[] typeOfProgram = request.getParameterValues("typeOfProgram");
		String[] university = request.getParameterValues("university");
		
		if(yearOfPassing != null && yearOfPassing.length > 0) { 
			for(int i = 0; i < yearOfPassing.length; i++) {
				EducationalData educationData = new EducationalData();
				Date tmpDt = null;
				if(!fromYear[i].trim().isEmpty()){
					tmpDt = Utils.convertToDate("1" + Utils.dateDescSeparator + "1" + Utils.dateDescSeparator + fromYear[i], Utils.redDDMMYYYYFormat);
					if (tmpDt != null) {
						educationData.setFromYear(new java.sql.Date(tmpDt.getTime()));
					}
				}else{
					educationData.setFromYear(null);
				}
				if(!yearOfPassing[i].trim().isEmpty()){
					tmpDt = Utils.convertToDate("1" + Utils.dateDescSeparator + "1" + Utils.dateDescSeparator + yearOfPassing[i], Utils.redDDMMYYYYFormat);
					if (tmpDt != null) {
						educationData.setYearOfPassing(new java.sql.Date(tmpDt.getTime()));
					}
				}else{
					educationData.setYearOfPassing(null);
				}
				educationData.setInstitute(institutes[i].trim().isEmpty() ? null : institutes[i].trim());
				if(Integer.parseInt(degreeIds[i])!=-1){
					educationData.setDegreeId(Integer.parseInt(degreeIds[i]));
				}
				if(Integer.parseInt(branchIds[i])!=-1){
					educationData.setMajorId(Integer.parseInt(branchIds[i]));
				}
				Date tmpStrDt = null;
				Date tmpEndDt = null;
				if(!startDate[i].trim().isEmpty()){
					tmpStrDt = Utils.convertToDate("1" + Utils.dateDescSeparator + startDate[i], Utils.redDDMMYYYYFormat);
					if (tmpStrDt != null) {
						educationData.setStartDate(new java.sql.Date(tmpStrDt.getTime()));
					}
				}else{
					educationData.setStartDate(null);
				}
				if(!endDate[i].trim().isEmpty()){
					tmpEndDt = Utils.convertToDate("1" + Utils.dateDescSeparator + endDate[i], Utils.redDDMMYYYYFormat);
					if (tmpStrDt != null) {
						educationData.setEndDate(new java.sql.Date(tmpEndDt.getTime()));
					}
				}else{
					educationData.setEndDate(null);
				}
				educationData.setGrade(grade[i]);
				educationData.setRemarks(remarks[i]);
				educationData.setTypeOfProgram(typeOfProgram[i]);
				educationData.setUniversity(university[i]);
				educationalDetails.add(educationData);
				
			}
			aData.setEducationalDetails(educationalDetails);
		}
		
		if (!(aData.getEducationalDetails()!=null && aData.getEducationalDetails().size()>0)) {
			aForm.setDefaultEducationRow();
		}
		return aData;
	}
	
	private boolean chekFilterConditions(PositionRuleData ruleData, String positionId, ApplicantData aData,
			HttpServletRequest request) {
		//get rule data for this position
		PositionManager positionManager = new PositionManager();
		MastersManager masterManager = new MastersManager();
		boolean isPassedRules = true;

		ArrayList<String> ruleInstituteIds = new ArrayList<String>();
		try{
			String minExp = ruleData.getMinExp();
			String maxExp = ruleData.getMaxExp();
			String degreeId = ruleData.getDegreeId();
			String branchId = ruleData.getBranchId();
			String currentLocation = ruleData.getCurrentLocation();
			
			DegreeData degreeData = null;
			BranchesData branchData = null;
			if(degreeId!=null){
				degreeData = (DegreeData)masterManager.getDegreeFromMaster(degreeId);
			}
			if(branchId!=null){
				branchData = (BranchesData)masterManager.getBranchFromMaster(branchId);
			}
			
			ArrayList<RuleInstituteData> ruleInstitutesData = positionManager.getPositionRuleInstitutes(ruleData.getRuleId());
			if(ruleInstitutesData!=null && ruleInstitutesData.size()>0){
				for (Iterator<RuleInstituteData> iterator = ruleInstitutesData.iterator(); iterator.hasNext();) {
					RuleInstituteData ruleInstituteData = iterator.next();
					ruleInstituteIds.add(ruleInstituteData.getInstituteId());
				}
			}
			
			// Get Data from Applicant Form
			ArrayList<String> instituteArr = new ArrayList<String>();
			ArrayList<String> degreeArr = new ArrayList<String>();
			ArrayList<String> branchArr = new ArrayList<String>();
			ArrayList<String> gradeArr = new ArrayList<String>();
						
			String applicantCurrentLocation = Utils.isBlankOrNull(aData.getApplicantCity()) ? "" :  aData.getApplicantCity().trim();
			Date applicantWorkingSince = aData.getApplicantWorkingSince();
			
			ArrayList<EducationalData> collEducationData = aData.getEducationalDetails();
			if(collEducationData!=null && collEducationData.size()>0){
				for (Iterator<EducationalData> iterator = collEducationData.iterator(); iterator.hasNext();) {
					EducationalData educationalData = iterator.next();
					instituteArr.add(educationalData.getInstitute());
					degreeArr.add(educationalData.getDegreeId()+"");
					branchArr.add(educationalData.getMajorId()+"");
					gradeArr.add(educationalData.getGrade());
				}
			}
			
			//start checking for filters
			//check for experience
			if(isPassedRules && minExp!=null && maxExp!=null){
				double exp = 0 ;
				if(applicantWorkingSince!=null){
					exp = Utils.getExperienceInNumberFormat(applicantWorkingSince);
				}
				if(exp<=Double.parseDouble(maxExp) && exp>=Double.parseDouble(minExp)){
					isPassedRules = true;
				}else{
					isPassedRules = false;
				}
			}
			
			//check for Education
			if(isPassedRules && degreeId!=null){
				isPassedRules = degreeArr.contains(degreeData.getItemId()+"");
			}
			if(isPassedRules && branchId!=null){
				isPassedRules = branchArr.contains(branchData.getItemId()+"");
			}
			
			//check for institute id
			if(isPassedRules && ruleInstitutesData!=null && ruleInstitutesData.size()>0){
				Object[] objArr = instituteArr.toArray();
				for (int i = 0; i < objArr.length; i++) {
					String instituteId = masterManager.getInstitutesWithAliasesByName(objArr[i].toString());
					if(ruleInstituteIds.contains(instituteId)){
						isPassedRules = true;
						break;
					}else{
						isPassedRules = false;
					}
				}
				
			}
			//current Location
			if(isPassedRules && currentLocation!=null){
				isPassedRules = currentLocation.equalsIgnoreCase(applicantCurrentLocation);
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while checking filters against applicant Data", e);
		}
		return isPassedRules;
	}
	
	public ActionForward successPage(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response){
		String forward = "successPage";
		
		return mapping.findForward(forward);
	}
}
