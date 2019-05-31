/**
 * 
 */
package com.talentPool.inbox.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.employeeservice.manager.EmployeeNotificationManager;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.AttachmentData;
import com.talentPool.inbox.dataobject.AutoImportData;
import com.talentPool.inbox.dataobject.AutoImportEducationData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.AutoImportManager;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.inbox.utils.InboxUtils;
import com.talentPool.masters.dataobject.BranchesData;
import com.talentPool.masters.dataobject.DegreeData;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.notifier.CandidateStateTemplateEnums;
import com.talentPool.parser.EducationParser;
import com.talentPool.parser.SkillsParser;
import com.talentPool.parser.SourceParser;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.HTMLToPlainTextConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.dataobject.PositionRuleData;
import com.talentPool.positions.dataobject.RuleInstituteData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.reports.manager.ReportManager;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.dataobject.CommunicationData;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.vendorservice.manager.VendorNotificationManager;
import com.talentPool.websiteservice.manager.WebsiteNotificationManager;

/**
 * @author shivprasad
 * 
 */
public class AutoImportJob implements Job {
	public static int autocnt = 0;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (autocnt == 0) {
			autocnt = 1;
			try {
				TPLogger.getLogger().debug("========= START AUTO IMPORT JOB =============");
				boolean emailsExist = true;
				while (emailsExist) {
					AutoImportManager autoImportManager = new AutoImportManager();
					ArrayList emails = autoImportManager.getEmailsToAutoImport(InboxConstants.FORMAT_AUTO_IMPORT, false);
					if (emails == null || emails.size() == 0) {
						emailsExist = false;
					} else {
						autoImportEmails(emails);
					}
				}
				TPLogger.getLogger().debug("========= JOB EXIT =============");
			} catch (Exception e) {
				TPLogger.getLogger().error("Error", e);
			}
			autocnt = 0;
		}
	}

	public void autoImportEmails(ArrayList emails) {
		InboxUtils inboxUtils = new InboxUtils();
		InboxManager inboxManager = new InboxManager();
		AdminManager adminManager = new AdminManager();
		SourceData data = adminManager.getCompanyWebSiteSource();
		WebsiteNotificationManager websiteNotificationManager = new WebsiteNotificationManager();
		for (int i = 0; i < emails.size(); i++) {
			ArrayList errors = null;
			MessageData messageData = (MessageData) emails.get(i);
			try {
				String content = messageData.getTextBody();
				if (Utils.isBlankOrNull(content)) {
					content = messageData.getHtmlBody();
					HTMLToPlainTextConverter converter = new HTMLToPlainTextConverter();
					content = converter.convertText(content);
				}
				AutoImportData autoImportData = inboxUtils.getAutoImportData(content);

				errors = validateInfo(autoImportData);

				String sourceId = null;
				LoginData vendorLoginData = null;
				
				if (Utils.isBlankOrNull(autoImportData.getUserName())) {
					sourceId = getSourceId(autoImportData.getSource());
				} else {
					vendorLoginData = getVendorData(autoImportData.getUserName());
					if (vendorLoginData != null) {
						sourceId = vendorLoginData.getUserSourceId();
					}
				}
								
				if (sourceId == null) {
					errors.add(InboxConstants.ERROR_INCORRECT_SOURCE);
				} else {
					autoImportData.setSourceId(sourceId);
				}

				// convert resume to html format
				// Check if attachment exist or not
				ArrayList attachments = inboxManager.getAttachmentsOfType(messageData.getMessageId(), InboxConstants.EMAIL_LOCATION_INBOX, InboxConstants.ATTACHMENT_TYPE_NOTRELATED);
				String attachmentId = inboxManager.getAttachmentIdToParse(attachments);
				if (attachmentId == "0") {
					errors.add(InboxConstants.ERROR_NO_RESUME);
				}

				if (errors.size() == 0) {
					ArrayList<ApplicantDuplicateSearchData> duplicates = null;
					ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
					if (Utils.isBlankOrNull(autoImportData.getUserName())) {
						duplicates = applicantDuplicateChecker.getInternalDuplicateChecked(null, autoImportData.getName(), autoImportData.getEmail1(), autoImportData.getEmail2(), autoImportData.getCellPhone(), autoImportData.getCustomFields());
					} else {
						// We don't need to check duplicate as we have already
						// checked while
						// submitting resume, so commenting this code
						// duplicates =
						// applicantDuplicateChecker.getVendorDuplicateChecked(null,
						// autoImportData.getName(), autoImportData.getEmail1(),
						// autoImportData.getEmail2(), autoImportData
						// .getCellPhone(), autoImportData.getCustomFields());
					}
					if (duplicates != null && duplicates.size() > 0) {
						errors.add(InboxConstants.ERROR_DUPLICATE);
					}					
					if(duplicates != null && duplicates.size() > 0 && data.getSourceId().equals(sourceId)) {
						// Duplicate upload from company website						
						websiteNotificationManager.sendNotification(messageData);
					}
				}
				TPLogger.getLogger().debug("ERRORS ==>>> " + errors);
				if (errors.size() == 0) {
					TPLogger.getLogger().debug("Start auto import now");

					// fetch skills
					extractAndSetSkills(autoImportData);

					// fetch education
					extractAndSetEducationDetails(autoImportData);
					
					//fetch Employment History
					//extractAndSetEmploymentHistoryDetails(autoImportData);

					// get positionId if exist position code
					PositionManager positionManager = new PositionManager();
					String positionId = null;
					if (!Utils.isBlankOrNull(autoImportData.getPositionCode())) {
						positionId = positionManager.getPositionIdFromPositionCode(autoImportData.getPositionCode());
					}
					// copy original resume
					AttachmentData atData = inboxManager.getAttachmentData(attachmentId);
					String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, atData.getAttachmentFilePath());

					DocumentUploader documentUploader = new DocumentUploader();
					String filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);

					String applicantOriginalResumePath = "";
					if (Utils.isBlankOrNull(filePathFinal)) {
						filePathFinal = filePath;
					}
					if (filePathFinal.length() > DocumentConstants.documentsPath.length()) {
						applicantOriginalResumePath = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
					}					

					// get text content of the file
					GenericConverter conv = new GenericConverter();
					String textContent = conv.convert(filePathFinal);

					// create applicant data
					ApplicantData aData = getApplicantDataConstructed(autoImportData);
					aData.setApplicantOriginalDocPath(atData.getAttachmentFilePath());
					aData.setApplicantOriginalResumePath(applicantOriginalResumePath);
					aData.setApplicantTextResume(textContent);
															
					// add applicant
					if (vendorLoginData != null) {
						aData.setUserId(vendorLoginData.getUserId());
						aData.setVendorId(vendorLoginData.getUserId());
					} else {
						aData.setUserId(UserConstants.ADMIN_ID);
					}					
					if(data.getSourceId().equals(sourceId)) {
						// apply rules
						if(!Utils.isBlankOrNull(positionId)) {
							applyRules(aData,positionId,messageData.getMessageId(),autoImportData.getSkillIds());
						} else {
							updateAutoImportTried(messageData);
						}
					} else {
						ApplicantManager applicantManager = new ApplicantManager();
						String applicantId = applicantManager.addApplicant(aData, autoImportData.getSkillIds());
						
						//TODO AUDIT action has been removed from applicant manager.
						//AuditAction auditAction = new AuditAction();
						//auditAction.insertAuditInfo(TPLabels.getLabel("common.applicant"), AuditConstants.TYPE_ADDED, applicantId,AuditConstants.AUDIT_CANDIDATE, aData.getUserId(),null,null,null,true);
	
						// attach email to applicant interactions
						inboxManager.attachEmailToApplicantRecord(applicantId, messageData.getMessageId(), aData.getUserId());
	
						if (!Utils.isBlankOrNull(positionId)) {
							SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
							selectionProcessManager.shortListApplicant(applicantId, positionId, null, vendorLoginData.getUserId(), false);
						}
						// Add applicant to index to event queue
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));
						if (!Utils.isBlankOrNull(autoImportData.getNote())) {
							addNoteToApplicant(applicantId, aData.getUserId(), autoImportData.getNote());
						}
						// send email to HR Team
						if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION).equals(GlobalConstants.ENABLED)){						
							VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
							vendorNotificationManager.sendNotification(applicantId, aData);
						}
	
						if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR).equals(GlobalConstants.ENABLED)){						
							VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
							vendorNotificationManager.sendNotificationToVendor(applicantId, aData);
						}
						
						// send email to HR Team when Employee upload any resume
						if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION).equals(GlobalConstants.ENABLED)) {						
							EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
							employeeNotificationManager.sendEmployeeNotification(applicantId, aData);
						}
						
						if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE).equals(GlobalConstants.ENABLED)) {						
							EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
							employeeNotificationManager.sendNotificationToEmployee(applicantId, aData);
						}
					}
				}
			} catch (Exception e) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
				errors.add(InboxConstants.ERROR_UNKNOWN);
			}
			updateErrorCodes(errors, messageData);
		}
	}
	
	private void applyRules(ApplicantData aData,String positionId,String inboxEmailId,String skillIds){
		PositionManager positionManager = new PositionManager();
		AutoImportManager autoImportManager = new AutoImportManager();
		InboxManager inboxManager = new InboxManager();
		try{
			PositionRuleData  ruleData = (PositionRuleData)positionManager.getPositionRule(positionId);
			String ruleType = ruleData.getRuleType();
			if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_DATABASE)){
				//save applicant
				ApplicantManager applicantManager = new ApplicantManager();
				String applicantId = applicantManager.addApplicant(aData, skillIds);

				// attach email to applicant interactions
				inboxManager.attachEmailToApplicantRecord(applicantId, inboxEmailId, aData.getUserId());
				TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_INBOX)){
				//do nothing -Just set auto import tried to 1
				autoImportManager.updateAutoImportTried(inboxEmailId,InboxConstants.AUTO_IMPORT_TRIED);
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_MANDATORY)){			
				boolean isPassedFilters = chekFilterConditions(ruleData,positionId,aData);
				if(isPassedFilters){//add and shortlist the applicant
					//save applicant
					ApplicantManager applicantManager = new ApplicantManager();
					String applicantId = applicantManager.addApplicant(aData, skillIds);
					
					//TODO audit action has been removed from manager.
					//AuditAction auditAction = new AuditAction();
					//auditAction.insertAuditInfo(TPLabels.getLabel("common.applicant"), AuditConstants.TYPE_ADDED, applicantId,AuditConstants.AUDIT_CANDIDATE, aData.getUserId(),null,null,null,true);

					// attach email to applicant interactions
					inboxManager.attachEmailToApplicantRecord(applicantId, inboxEmailId, aData.getUserId());

					//shortlist applicant
					if (!Utils.isBlankOrNull(positionId)) {
						SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
						selectionProcessManager.shortListApplicant(applicantId, positionId, null, UserConstants.ADMIN_ID, false);
					}
					// Add applicant to index to event queue
					TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));						
				}else{// send to inbox
					//if none of the rule is matched set the error code to RULE_NOT_MATCHED.
					autoImportManager.updateErrorCode(inboxEmailId, InboxConstants.MANDATORY_CONDITIONS_NOT_MATCHED);
				}
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while applying rules against applicant Data", e);
			autoImportManager.updateErrorCode(inboxEmailId, InboxConstants.ERROR_UNKNOWN);
		}
	}
	
	private boolean chekFilterConditions(PositionRuleData ruleData, String positionId, ApplicantData aData) {
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
			if(!Utils.isBlankOrNull(degreeId)){
				degreeData = (DegreeData)masterManager.getDegreeFromMaster(degreeId);
			}
			if(!Utils.isBlankOrNull(branchId)){
				branchData = (BranchesData)masterManager.getBranchFromMaster(branchId);
			}
			
			ArrayList<RuleInstituteData> ruleInstitutesData = positionManager.getPositionRuleInstitutes(ruleData.getRuleId());
			if(ruleInstitutesData!=null && ruleInstitutesData.size()>0){
				for (Iterator iterator = ruleInstitutesData.iterator(); iterator.hasNext();) {
					RuleInstituteData ruleInstituteData = (RuleInstituteData) iterator.next();
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
				for (Iterator iterator = collEducationData.iterator(); iterator.hasNext();) {
					EducationalData educationalData = (EducationalData) iterator.next();
					instituteArr.add(educationalData.getInstitute());
					degreeArr.add(educationalData.getDegreeId()+"");
					branchArr.add(educationalData.getMajorId()+"");
					gradeArr.add(educationalData.getGrade());
				}
			}
			
			//start checking for filters
			//check for experience
			if(isPassedRules && !Utils.isBlankOrNull(minExp) && !Utils.isBlankOrNull(maxExp)){
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
			if(isPassedRules && !Utils.isBlankOrNull(currentLocation)){
				isPassedRules = currentLocation.equalsIgnoreCase(applicantCurrentLocation);
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while checking filters against applicant Data", e);
		}
		return isPassedRules;
	}
	
	private void addNoteToApplicant(String applicantId, String userId, String note) {
		try {
			CommunicationData cData = new CommunicationData();
			cData.setApplicantId(applicantId);
			cData.setUserId(userId);
			cData.setCommunicationType(SelectionProcessConstants.INTERACTION_NOTE);
			// cData.setCommunicationPhoneNo(selectionProcessForm.getPhoneNo());
			cData.setCommunicationText(note);
			GregorianCalendar cal = new GregorianCalendar();
			cData.setCommunicationDate(new java.sql.Timestamp(cal.getTimeInMillis()));

			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			selectionProcessManager.addPhoneLog(cData);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
	}

	private void extractAndSetSkills(AutoImportData autoImportData) throws Exception {
		SkillsParser skillsParser = new SkillsParser();
		ArrayList<String> skills = skillsParser.getArrayListOfParsedSkillIdsAndNames(autoImportData.getSkills(), Integer.parseInt(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_MAX_SKILLS_PARSED)));
		if (skills != null && skills.size() > 0) {
			autoImportData.setSkillIds((String) skills.get(0));
		}
	}

	private void extractAndSetEducationDetails(AutoImportData autoImportData) throws Exception {
		TPLogger.getLogger().debug("  |extractAndSetEducationDetails|  ");
		ArrayList<EducationalData> educationalDetails = new ArrayList<EducationalData>();
		ArrayList<AutoImportEducationData> rawEduDetails = autoImportData.getRawEducationDetails();
		for (int k = 0; rawEduDetails != null && k < rawEduDetails.size(); k++) {
			AutoImportEducationData rawData = rawEduDetails.get(k);
			EducationParser ep = new EducationParser();
			EducationalData eData = ep.getEducationParsedFromMaster(rawData.getDegree(), rawData.getBranch(), rawData.getInstitute(), rawData.getYearOfPassing(), rawData.getGrade());			
			if (Utils.isBlankOrNull(eData.getInstitute())) {
				eData.setInstitute(rawData.getInstitute());
			}
			educationalDetails.add(eData);			
		}
		autoImportData.setEducationalDetails(educationalDetails);
	}

	private LoginData getVendorData(String userName) {
		LoginData loginData = null;
		try {
			LoginManager loginManager = new LoginManager();
			loginData = loginManager.getLoginDataFor(userName.trim());
		} catch (Exception e) {
			TPLogger.getLogger().error("ERROR in auto import, login name: " + userName + " does not exists");
		}
		return loginData;
	}

	private void updateErrorCodes(ArrayList errors, MessageData messageData) {
		try {
			if (errors != null && errors.size() > 0) {
				String errorIds = Utils.convertArrayListIntoCommaSptdString(errors);
				TPLogger.getLogger().debug("Error Ids ==> " + errorIds);
				AutoImportManager autoImportManager = new AutoImportManager();
				autoImportManager.updateErrorCode(messageData.getMessageId(), errorIds);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating error codes");
		}
	}
	
	private void updateAutoImportTried(MessageData messageData) {
		try {
			AutoImportManager autoImportManager = new AutoImportManager();
			autoImportManager.updateAutoImportTried(messageData.getMessageId(), InboxConstants.AUTO_IMPORT_TRIED);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating error codes");
		}
	}

	private ApplicantData getApplicantDataConstructed(AutoImportData autoData) {
		ApplicantData aData = new ApplicantData();
		try {
			aData.setApplicantName(autoData.getName());
			aData.setApplicantSourceId(Integer.parseInt(autoData.getSourceId()));
			java.sql.Date dtWorkingFrom = null;
			if (!Utils.isBlankOrNull(autoData.getWorkingSince())) {
				Date dtWorkingSince = Utils.convertToDate(autoData.getWorkingSince(), Utils.regEUDateFormat);
				dtWorkingFrom = new java.sql.Date(dtWorkingSince.getTime());
			}
			aData.setApplicantWorkingSince(dtWorkingFrom);
			aData.setApplicantEmail1(autoData.getEmail1());
			aData.setApplicantEmail2(autoData.getEmail2());
			aData.setApplicantCity(autoData.getLocation());
			aData.setApplicantCellPhone(autoData.getCellPhone());
			aData.setApplicantHomePhone(autoData.getPhone1());
			aData.setApplicantWorkPhone(autoData.getPhone2());
			aData.setApplicantCurrentEmployer(autoData.getCurrentEmployer()); // not commented as in case some one is using web integration.
			aData.setEducationalDetails(autoData.getEducationalDetails());
			aData.setCurrentCTC(autoData.getCTC());
			aData.setExpectedCTC(autoData.getECTC());
			aData.setNoticePeriod(autoData.getNoticePeriod());
			aData.setEmployeeCode(autoData.getEmployeeCode());
			aData.setCustomFields(autoData.getCustomFields());
			
			java.sql.Date dtBirth = null;
			if (!Utils.isBlankOrNull(autoData.getDateOfBirth())) {
				Date dtOfBirth = Utils.convertToDate(autoData.getDateOfBirth(), Utils.regEUDateFormat);
				dtBirth = new java.sql.Date(dtOfBirth.getTime());
			}
			aData.setDateOfBirth(dtBirth);
			aData.setPassportNumber(autoData.getPassport());
			aData.setResumeTypeId(autoData.getResumeTypeId());
			
			ArrayList<EmploymentHistoryData> employmentHistoryDetails = autoData.getEmploymentHistoryDetails();			
			if(!Utils.isListEmptyOrNull(employmentHistoryDetails)){
				try{
					Collections.sort(employmentHistoryDetails,EmploymentHistoryData.REVERSE_CHRONOLOGICAL );
				}catch (Exception e) {
					TPLogger.getLogger().debug(GlobalConstants.ERROR,e);
				}
				aData.setEmploymentHistoryDetails(employmentHistoryDetails);
			}
			aData.setAttribute("uuid", autoData.getAttribute("uuid"));
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
		return aData;
	}

	private String getSourceId(String content) {
		try {
			SourceParser sourceParser = new SourceParser();
			String sourceId = sourceParser.getParsedSourceIdFromMaster(content);
			return sourceId;
		} catch (Exception e) {
			TPLogger.getLogger().error("Error", e);
		}
		return null;
	}

	private ArrayList validateInfo(AutoImportData autoImportData) {
		ArrayList errors = new ArrayList();
		// Name validations
		if (Utils.isBlankOrNull(autoImportData.getName())) {
			errors.add(InboxConstants.ERROR_BLANK_NAME);
		}
		// email validations
		String email1 = autoImportData.getEmail1();
		String email2 = autoImportData.getEmail2();
		if (!Utils.isBlankOrNull(email1) || !Utils.isBlankOrNull(email2)) {
			// Check if valid email
			if (!Utils.isBlankOrNull(email1) && !Utils.isValidPattern(email1, Utils.regEmail)) {
				// errors.add(InboxConstants.ERROR_INVALID_EMAIL);
			} else if (!Utils.isBlankOrNull(email2) && !Utils.isValidPattern(email2, Utils.regEmail)) {
				// errors.add(InboxConstants.ERROR_INVALID_EMAIL);
			}
		}

		String sourceId = getSourceId(autoImportData.getSource());
		if (sourceId == null) {
			errors.add(InboxConstants.ERROR_INCORRECT_SOURCE);
		} else {
			autoImportData.setSourceId(sourceId);
		}

		return errors;
	}	
	
	public void addApplicantFromPortal(ApplicantData applicantData, String note, String userName) throws Exception{
		String sourceId = null;
		ArrayList<String> errors = new ArrayList<String>();
		LoginData vendorLoginData = null;
		
		AdminManager adminManager = new AdminManager();
		SourceData data = adminManager.getCompanyWebSiteSource();
		WebsiteNotificationManager websiteNotificationManager = new WebsiteNotificationManager();
		
		//TODO needs to be fetched
		//String userName = applicantData.getApplicantSourceTitle();
		String isEmployeeApply=applicantData.getIsEmployeeApply();
		String source = applicantData.getApplicantSourceTitle();
		try {
			if (Utils.isBlankOrNull(userName)) {
				sourceId = getSourceId(source);
			} else {
				vendorLoginData = getVendorData(userName);
				if (vendorLoginData != null) {
					sourceId = vendorLoginData.getUserSourceId();
				}
			}
			
			if (sourceId == null) {
				errors.add(InboxConstants.ERROR_INCORRECT_SOURCE);
			} else {
				applicantData.setApplicantSourceId(Integer.parseInt(sourceId));
			}
			// Start of Employee related to get applicantId of Employee if there is any in database 
			String oldApplicantID="";
			ApplicantManager applicantManager=new ApplicantManager();
			oldApplicantID=applicantManager.getApplicantIdFromEmail(applicantData.getApplicantEmail1());
			if(Utils.isBlankOrNull(isEmployeeApply)){
				isEmployeeApply="7";
			}
			if(isEmployeeApply.equals("1")){
				if(!Utils.isBlankOrNull(oldApplicantID)){
															if(isEmployeeApply.equals("1")){
															String currentStepId=applicantManager.getApplicantCurrentStepId(oldApplicantID);
																if(Utils.isBlankOrNull(currentStepId)){
																	applicantData.setApplicantId(oldApplicantID);
																	if (vendorLoginData != null) {
																		applicantData.setUserId(vendorLoginData.getUserId());
																		applicantData.setVendorId(vendorLoginData.getUserId());
																	} else {
																		applicantData.setUserId(UserConstants.ADMIN_ID);
																	}	
																		applicantManager.updateApplicant(applicantData, applicantData.getSkillIds());
																		if (!Utils.isBlankOrNull(applicantData.getApplicantPositionId())) {
																			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
																			selectionProcessManager.shortListApplicant(oldApplicantID, applicantData.getApplicantPositionId(), null, vendorLoginData.getUserId(), false);
																		}
																		// Add applicant to index to event queue
																		TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, oldApplicantID, TPIndexEvent.PRIORITY_NORMAL));
																		if (!Utils.isBlankOrNull(note)) {
																			addNoteToApplicant(oldApplicantID, sourceId, note);
																		}
																		// send email to HR Team
																		if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION).equals(GlobalConstants.ENABLED)){						
																			VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
																			vendorNotificationManager.sendNotification(oldApplicantID, applicantData);
																		}
														
																		if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR).equals(GlobalConstants.ENABLED)){						
																			VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
																			vendorNotificationManager.sendNotificationToVendor(oldApplicantID, applicantData);
																		}
																		
																	
																		if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL).equals(GlobalConstants.ENABLED)) {						
																			EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
																			employeeNotificationManager.sendEmployeeNotification(oldApplicantID, applicantData);
																		}
																		if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE).equals(GlobalConstants.ENABLED)) {						
																			EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
																			employeeNotificationManager.sendNotificationToEmployee(oldApplicantID, applicantData);
																		}
																}
																else{
																	errors.add("The candidate is already in process for other position");
																	throw new Exception("upload_applicant_candidate_in_process");
																}
															}
							}	
				
				else{
					//add applicant as Employee here
					
					PositionManager positionManager = new PositionManager();
					
					String positionId = null;
					if (!Utils.isBlankOrNull(applicantData.getApplicantPositionId())) {
						positionId = applicantData.getApplicantPositionId();
					}
					
					String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, applicantData.getApplicantOriginalResumePath());
		
					DocumentUploader documentUploader = new DocumentUploader();
					String filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
		
					String applicantOriginalResumePath = "";
					if (Utils.isBlankOrNull(filePathFinal)) {
						filePathFinal = filePath;
					}
					if (filePathFinal.length() > DocumentConstants.documentsPath.length()) {
						applicantOriginalResumePath = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
					}					
		
					// get text content of the file
					GenericConverter conv = new GenericConverter();
					String textContent = conv.convert(filePathFinal);
		
				
					WordToHtmlConverter converter = new WordToHtmlConverter();
					converter.convertToHtml(filePath);
					filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
					if(!Utils.isBlankOrNull(filePathFinal) && filePathFinal.length() > DocumentConstants.documentsPath.length()) {
						filePathFinal = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
					} else {
						filePathFinal = applicantOriginalResumePath;
					}
					
					applicantData.setApplicantOriginalResumePath(filePathFinal);
					applicantData.setApplicantOriginalDocPath(applicantOriginalResumePath);
					applicantData.setApplicantTextResume(textContent);
															
					// add applicant
					if (vendorLoginData != null) {
						applicantData.setUserId(vendorLoginData.getUserId());
						applicantData.setVendorId(vendorLoginData.getUserId());
					} else {
						applicantData.setUserId(UserConstants.ADMIN_ID);
					}					
					if(data.getSourceId().equals(sourceId)) {
						// apply rules
						if(!Utils.isBlankOrNull(positionId)) {
							applyRules(applicantData,positionId,applicantData.getSkillIds());
						} else {
							
						}
					} else {
						ApplicantManager newApplicantManager = new ApplicantManager();
						
						oldApplicantID=newApplicantManager.addApplicant(applicantData, applicantData.getSkillIds());
							
					}
					if (!Utils.isBlankOrNull(positionId)) {
						SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
						selectionProcessManager.shortListApplicant(oldApplicantID, positionId, null, vendorLoginData.getUserId(), false);
					}
					// Add applicant to index to event queue
					TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, oldApplicantID, TPIndexEvent.PRIORITY_NORMAL));
					if (!Utils.isBlankOrNull(note)) {
						addNoteToApplicant(oldApplicantID, sourceId, note);
					}
					// send email to HR Team
					if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION).equals(GlobalConstants.ENABLED)){						
						VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
						vendorNotificationManager.sendNotification(oldApplicantID, applicantData);
					}
	
					if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR).equals(GlobalConstants.ENABLED)){						
						VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
						vendorNotificationManager.sendNotificationToVendor(oldApplicantID, applicantData);
					}
					
				
					if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL).equals(GlobalConstants.ENABLED)) {						
						EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
						employeeNotificationManager.sendEmployeeNotification(oldApplicantID, applicantData);
					}
					if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE).equals(GlobalConstants.ENABLED)) {						
						EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
						employeeNotificationManager.sendNotificationToEmployee(oldApplicantID, applicantData);
					}
					
					
				}
				
			
			}
					
					//end of employee applicant
			
			else{
				if (errors.size() == 0) {
					ArrayList<ApplicantDuplicateSearchData> duplicates = null;
					ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
					if (Utils.isBlankOrNull(userName)) {
						//TODO email1, email2, cellphone, convertedCustomFields
						duplicates = applicantDuplicateChecker.getInternalDuplicateChecked(null, applicantData.getApplicantName(), applicantData.getApplicantEmail1(), applicantData.getApplicantEmail2(), applicantData.getApplicantCellPhone(), applicantData.getCustomFields());
					} else {
						
					}
					if (duplicates != null && duplicates.size() > 0) {
						errors.add(InboxConstants.ERROR_DUPLICATE);
					}					
					if(duplicates != null && duplicates.size() > 0 && data.getSourceId().equals(sourceId)) {
					
						TPLogger.getLogger().error("Duplicate Applicant uploaded from website:::"+duplicates.get(0).getApplicantName());
					}
				}
				TPLogger.getLogger().debug("ERRORS ==>>> " + errors);
				if (errors.size() == 0) {
					TPLogger.getLogger().debug("Start auto import now");
		
					
					PositionManager positionManager = new PositionManager();
					
					String positionId = null;
					if (!Utils.isBlankOrNull(applicantData.getApplicantPositionId())) {
						positionId = applicantData.getApplicantPositionId();
					}
					
					String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, applicantData.getApplicantOriginalResumePath());
		
					DocumentUploader documentUploader = new DocumentUploader();
					String filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
		
					String applicantOriginalResumePath = "";
					if (Utils.isBlankOrNull(filePathFinal)) {
						filePathFinal = filePath;
					}
					if (filePathFinal.length() > DocumentConstants.documentsPath.length()) {
						applicantOriginalResumePath = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
					}					
		
					// get text content of the file
					GenericConverter conv = new GenericConverter();
					String textContent = conv.convert(filePathFinal);
		
				
					WordToHtmlConverter converter = new WordToHtmlConverter();
					converter.convertToHtml(filePath);
					filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
					if(!Utils.isBlankOrNull(filePathFinal) && filePathFinal.length() > DocumentConstants.documentsPath.length()) {
						filePathFinal = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
					} else {
						filePathFinal = applicantOriginalResumePath;
					}
					
					applicantData.setApplicantOriginalResumePath(filePathFinal);
					applicantData.setApplicantOriginalDocPath(applicantOriginalResumePath);
					applicantData.setApplicantTextResume(textContent);
															
					// add applicant
					if (vendorLoginData != null) {
						applicantData.setUserId(vendorLoginData.getUserId());
						applicantData.setVendorId(vendorLoginData.getUserId());
					} else {
						applicantData.setUserId(UserConstants.ADMIN_ID);
					}					
					if(data.getSourceId().equals(sourceId)) {
						// apply rules
						if(!Utils.isBlankOrNull(positionId)) {
							applyRules(applicantData,positionId,applicantData.getSkillIds());
						} else {
							
						}
					} else {
						//ApplicantManager applicantManager = new ApplicantManager();
						
						
						
						 String applicantId=applicantManager.addApplicant(applicantData, applicantData.getSkillIds());
							
							
						
						
						
					
		
						if (!Utils.isBlankOrNull(positionId)) {
							SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
							selectionProcessManager.shortListApplicant(applicantId, positionId, null, vendorLoginData.getUserId(), false);
						}
						// Add applicant to index to event queue
						TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));
						if (!Utils.isBlankOrNull(note)) {
							addNoteToApplicant(applicantId, sourceId, note);
						}
						// send email to HR Team
						if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION).equals(GlobalConstants.ENABLED)){						
							VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
							vendorNotificationManager.sendNotification(applicantId, applicantData);
						}
		
						if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_VENDOR_RESUME_UPLOAD_NOTIFICATION_TO_VENDOR).equals(GlobalConstants.ENABLED)){						
							VendorNotificationManager vendorNotificationManager = new VendorNotificationManager();
							vendorNotificationManager.sendNotificationToVendor(applicantId, applicantData);
						}
						
					
						if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_EMAIL_NOTIFICATION_TO_HR_FOR_EMPLOYEE_PORTAL).equals(GlobalConstants.ENABLED)) {						
							EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
							employeeNotificationManager.sendEmployeeNotification(applicantId, applicantData);
						}
						if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_EMPLOYEE_RESUME_UPLOAD_NOTIFICATION_TO_EMPLOYEE).equals(GlobalConstants.ENABLED)) {						
							EmployeeNotificationManager employeeNotificationManager = new EmployeeNotificationManager();
							employeeNotificationManager.sendNotificationToEmployee(applicantId, applicantData);
						}
						
						
						
						
					
						
					}
				}
			}
			
		
	} catch (Exception e) {
		TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		errors.add(InboxConstants.ERROR_UNKNOWN);
		throw e;
	}
	}
	
	private void applyRules(ApplicantData aData,String positionId, String skillIds) throws Exception{
		PositionManager positionManager = new PositionManager();
		AutoImportManager autoImportManager = new AutoImportManager();
		InboxManager inboxManager = new InboxManager();
		try{
			PositionRuleData  ruleData = (PositionRuleData)positionManager.getPositionRule(positionId);
			String ruleType = ruleData.getRuleType();
			if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_DATABASE)){
				//save applicant
				ApplicantManager applicantManager = new ApplicantManager();
				String applicantId = applicantManager.addApplicant(aData, skillIds);

				// attach email to applicant interactions
				//inboxManager.attachEmailToApplicantRecord(applicantId, inboxEmailId, aData.getUserId());
				TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_GO_TO_INBOX)){
				//do nothing -Just set auto import tried to 1
				//autoImportManager.updateAutoImportTried(inboxEmailId,InboxConstants.AUTO_IMPORT_TRIED);
			}else if(ruleType.equalsIgnoreCase(PositionConstants.RULE_MANDATORY)){			
				boolean isPassedFilters = chekFilterConditions(ruleData,positionId,aData);
				if(isPassedFilters){//add and shortlist the applicant
					//save applicant
					ApplicantManager applicantManager = new ApplicantManager();
					String applicantId = applicantManager.addApplicant(aData, skillIds);
					
					//TODO audit action has been removed from manager.
					//AuditAction auditAction = new AuditAction();
					//auditAction.insertAuditInfo(TPLabels.getLabel("common.applicant"), AuditConstants.TYPE_ADDED, applicantId,AuditConstants.AUDIT_CANDIDATE, aData.getUserId(),null,null,null,true);

					// attach email to applicant interactions
					//inboxManager.attachEmailToApplicantRecord(applicantId, inboxEmailId, aData.getUserId());

					//shortlist applicant
					if (!Utils.isBlankOrNull(positionId)) {
						SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
						selectionProcessManager.shortListApplicant(applicantId, positionId, null, UserConstants.ADMIN_ID, false);
					}
					// Add applicant to index to event queue
					TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_NORMAL));						
				}else{// send to inbox
					//if none of the rule is matched set the error code to RULE_NOT_MATCHED.
					//autoImportManager.updateErrorCode(inboxEmailId, InboxConstants.MANDATORY_CONDITIONS_NOT_MATCHED);
				}
			}
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL).equals(GlobalConstants.ENABLED)) {
				AutoReplyScheduler.addTrigger(aData.getApplicantEmail1(),CandidateStateTemplateEnums.ADDED,null);
			}
		}catch (Exception e) {
			TPLogger.getLogger().error("Error while applying rules against applicant Data", e);
			throw e;
			//autoImportManager.updateErrorCode(inboxEmailId, InboxConstants.ERROR_UNKNOWN);
		}
	}
	
}
