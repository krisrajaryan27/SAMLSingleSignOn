package com.talentPool.repository;

import java.util.ArrayList;
import java.util.Date;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBCallableQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class DocFetchThread implements Runnable{

	private String eventType;
	private String eventTypeId;
	private int batch_size;
	private int start;
	private DocumentQueue queue;
	private int end;
	
	public DocFetchThread(String eventType,String eventTypeId, int start, int batch_size,int end,DocumentQueue queue) {
		this.eventType = eventType;
		this.eventTypeId = eventTypeId;
		this.batch_size = batch_size;
		this.start = start;
		this.queue = queue;
		this.end = end;
	}
	
	@Override
	public void run() {
		TPLogger.getLogger().info("Entering " + Thread.currentThread().getName()+ " with start: " + start + " and batch size "+ batch_size);
		long startTime = System.currentTimeMillis();
		ArrayList<TPDocument> docs = getApplicantDocuments(eventType,eventTypeId, start, batch_size);
		long endTime = System.currentTimeMillis();
		TPLogger.getLogger().info("time taken for querying for "+ Thread.currentThread().getName() + " with start: " + start  +" :: "+ (endTime-startTime)/1000);
		for(TPDocument doc: docs){
			try {
				queue.put(doc);
			} catch (InterruptedException e) {
				TPLogger.getLogger().info("unable to put tasks to queuue",e);
			}
		}
		if((start+batch_size)>=end){
			queue.continueProducing=Boolean.FALSE;
			TPLogger.getLogger().info("Turning off the queue for further tasks");
		}
	}

	public ArrayList<TPDocument> getApplicantDocuments(String eventType,
			String eventTypeId, int start, int batchSize) {
		ArrayList<TPDocument> applicants = new ArrayList<TPDocument>();
		try {
			ArrayList<ApplicantData> results = getApplicantsData(eventType, eventTypeId,start, batchSize);
			ApplicantManager manager = new ApplicantManager();
			// Construct TPDocuments DO from list
			if (results != null) {
				for (int i = 0; i < results.size(); i++) {
					ApplicantData aData = (ApplicantData) results.get(i);
					ArrayList<SimpleDataObject> textResumes = manager.getApplicantTextResumes(aData.getApplicantId());
					if(textResumes!=null){
						StringBuilder combinedTextResume = new StringBuilder();
						for (SimpleDataObject textResume : textResumes) {
							combinedTextResume.append(textResume.getString("textResume"));
						}
						aData.setApplicantTextResume(combinedTextResume.toString());
					}
					TPDocument tpDocument = getTPDocument(aData);
					if (tpDocument != null) {
						applicants.add(tpDocument);
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting documents list", e);
		}
		return applicants;
	}

	public ArrayList<ApplicantData> getApplicantsData(String eventType,
			String eventTypeId, int start, int batchSize) {
		ArrayList<ApplicantData> applicants = null;
		DBCallableQuery dq = null;

		try {
			dq = new DBCallableQuery("dIndexManager_ProcGetApplicantsToIndex");
			dq.setString(1, eventType);
			dq.setInt(
					2,
					Utils.isBlankOrNull(eventTypeId) ? 0 : Integer
							.parseInt(eventTypeId));
			dq.setInt(3, start);
			dq.setInt(4, batchSize);
			dq.setString(5, TPIndexEvent.TYPE_ALL);
			dq.setString(6, TPIndexEvent.TYPE_ADD_APPLICANT);
			dq.setString(7, TPIndexEvent.TYPE_UPDATE_APPLICANT);
			dq.setString(8, TPIndexEvent.TYPE_SKILLS);
			dq.setString(9, TPIndexEvent.TYPE_DEGREE);
			dq.setString(10, TPIndexEvent.TYPE_INSTITUTE);
			dq.setString(11, TPIndexEvent.TYPE_BRANCH);
			dq.setString(12, TPIndexEvent.TYPE_SOURCE);
			dq.setString(13, TPIndexEvent.TYPE_PREVIOUS_EMPLOYER);
			dq.setString(14, TPIndexEvent.TYPE_DESIGNATION);
			dq.setId(15, SelectionProcessConstants.STEP_REJECT);
			dq.setId(16, SelectionProcessConstants.STEP_NOT_ATTENDED);
			dq.setId(17, SelectionProcessConstants.STEP_NOT_INTERESTED_REJECT);
			dq.setId(18, SelectionProcessConstants.STEP_POSITION_CLOSED_REJECT);
			dq.setString(19, SelectionProcessConstants.STATUS_USER_GENERATED);
			dq.setInt(20, Integer.parseInt(ApplicantConstants.APPLICANT_JOINED));
			dq.setInt(21,
					Integer.parseInt(ApplicantConstants.APPLICANT_NOT_JOINED));
			applicants = dq.getResult();

			for (int i = 0; applicants != null && i < applicants.size(); i++) {
				ApplicantData aData = applicants.get(i);
				ApplicantManager applicantManager = new ApplicantManager();
				ArrayList<EducationalData> educationalDetails = applicantManager
						.getEducationalInfo(aData.getApplicantId());
				aData.setEducationalDetails(educationalDetails);
				ArrayList<EmploymentHistoryData> employHistoryDetails = applicantManager
						.getEmploymentHistoryInfo(aData.getApplicantId());
				aData.setEmploymentHistoryDetails(employHistoryDetails);
				// set custom fields
				if (CustomFieldManager
						.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
					CustomFieldManager customFieldManager = new CustomFieldManager();
					ArrayList<CustomFieldData> customFields = customFieldManager
							.getCustomFieldValuesForSearchableFields(
									aData.getApplicantId(),
									CustomFieldConstants.ENTITY_TYPE_APPLICANT);
					aData.setCustomFields(customFields);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting batched", e);
		} finally {
			if (dq != null) {
				dq.closeOpenCursors();
				dq.releaseConnection();
			}

		}
		return applicants;
	}

	public TPDocument getTPDocument(ApplicantData aData) {
		TPDocument tpDocument = null;
		try {
			String contents = Utils.isBlankOrNull(aData
					.getApplicantTextResume()) ? "" : aData
					.getApplicantTextResume();
			String filePath = aData.getApplicantOriginalResumePath();
			String applicantName = aData.getApplicantName();
			String isConfidential = aData.getIsConfidential();
			String applicantStatus = aData.getApplicantStatus();
			// Date workingSince = aData.getApplicantWorkingSince();

			String workingSince = RepositoryConstants.BLANK_DATE;
			if (aData.getApplicantWorkingSince() != null) {
				workingSince = TPDateTools.dateToString(
						aData.getApplicantWorkingSince(),
						TPDateTools.Resolution.MONTH);
			}

			String mobileNo = Utils
					.isBlankOrNull(aData.getApplicantCellPhone()) ? "" : aData
					.getApplicantCellPhone();
			String lastEmp = Utils.isBlankOrNull(aData
					.getApplicantCurrentEmployer()) ? "" : aData
					.getApplicantCurrentEmployer();
			String passport = Utils.isBlankOrNull(aData.getPassportNumber()) ? ""
					: aData.getPassportNumber();
			String location = Utils.isBlankOrNull(aData.getApplicantCity()) ? ""
					: aData.getApplicantCity();
			String source = Utils
					.isBlankOrNull(aData.getApplicantSourceTitle()) ? ""
					: aData.getApplicantSourceTitle();
			String sourceTypeId = aData.getSourceTypeId();
			String resumeTypeId = Utils.isBlankOrNull(aData.getResumeTypeId()) ? ""
					: aData.getResumeTypeId();
			String email = Utils.isBlankOrNull(aData.getApplicantEmail1()) ? ""
					: aData.getApplicantEmail1();
			email += Utils.isBlankOrNull(aData.getApplicantEmail2()) ? "" : " "
					+ aData.getApplicantEmail2();
			String inProcess = (!Utils
					.isBlankOrNull(aData.getApplicantStepId()) && !Utils
					.isBlankOrNull(aData.getApplicantPositionId())) ? "1" : "0";
			Date importDate = aData.getApplicantDateImported();
			Date birthDate = aData.getDateOfBirth();
			Date lastInteractionDate = aData.getApplicantLastInteractionDate();
			if (lastInteractionDate == null) {
				lastInteractionDate = importDate;
			} else if (importDate.after(lastInteractionDate)) {
				lastInteractionDate = importDate;
			}
			String state = RepositoryConstants.STATE_NEW;
			if (!Utils.isBlankOrNull(aData.getRejectReasonIds())) {
				state = RepositoryConstants.STATE_PROCESSED;
			}
			if (inProcess.equals("1")) {
				state = RepositoryConstants.STATE_INPROCESS;
			}
			if (!"1".equals(inProcess)
					&& aData.getApplicantJoined().equals("1")) {
				state = RepositoryConstants.STATE_JOINED;
			}
			ApplicantManager applicantManager = new ApplicantManager();
			// if candidate has applied for any position but not in process
			ArrayList<PositionData> positions = applicantManager
					.getPositionsAndResumesAppliedByCandidate(
							aData.getApplicantId(), true);
			if (positions.size() > 0
					&& Utils.isBlankOrNull(aData.getApplicantStepId())) {
				state = RepositoryConstants.STATE_APPLIED;
			}

			String employeeCode = (Utils.isBlankOrNull(aData.getEmployeeCode())) ? ""
					: aData.getEmployeeCode();
			tpDocument = new TPDocument(aData.getApplicantId(), contents,
					filePath, applicantName, workingSince, lastEmp, passport,
					location, sourceTypeId, resumeTypeId, source, email,
					importDate, birthDate, lastInteractionDate, state,
					mobileNo, isConfidential, employeeCode,
					aData.getApplicantJoined(), applicantStatus);
			
			if(positions.size() > 0){
				tpDocument.addAppliedPosition(positions);
			}
			// add educational details
			ArrayList<EducationalData> eduList = aData.getEducationalDetails();
			for (int i = 0; eduList != null && i < eduList.size(); i++) {
				EducationalData eData = eduList.get(i);
				String degreeTitle = Utils
						.isBlankOrNull(eData.getDegreeTitle()) ? "" : eData
						.getDegreeTitle();
				int degreeId=Utils
						.isBlankOrNull(eData.getDegreeTitle()) ? 0 : eData
						.getDegreeId();
				String institute = Utils.isBlankOrNull(eData.getInstitute()) ? ""
						: eData.getInstitute();
				String major = Utils.isBlankOrNull(eData.getMajor()) ? ""
						: eData.getMajor();
				Date yearOfPassing = eData.getYearOfPassing()!=null?eData.getYearOfPassing():null;
				try{
					if (degreeTitle != "") {
						tpDocument.addEducation(degreeTitle, institute, major,
								yearOfPassing,Utils.isNumeric(eData.getGrade())?Double.parseDouble(eData.getGrade()):0,degreeId);
					}
				}catch(Exception ex){
					TPLogger.getLogger().error("Error occured while indexing Education details of Applicantid"+aData.getApplicantId(),ex);
				}
			
			}

			// add Employment History details
			ArrayList<EmploymentHistoryData> empHistList = aData
					.getEmploymentHistoryDetails();
			for (int i = 0; empHistList != null && i < empHistList.size(); i++) {
				EmploymentHistoryData eData = empHistList.get(i);
				String employerName = Utils.isBlankOrNull(eData
						.getEmployerName()) ? "" : eData.getEmployerName();
				String designationName = Utils.isBlankOrNull(eData
						.getDesignationName()) ? "" : eData
						.getDesignationName();
				if (employerName != "" || designationName != "") {
					tpDocument.addEmploymentHistory(employerName,
							designationName);
				}
			}

			// add custom fields
			ArrayList<CustomFieldData> customFields = aData.getCustomFields();
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if (cData.getFieldType().equals(CustomFieldConstants.TYPE_DATE)) {
					Date dt = cData.getFieldDateValue();
					if (dt == null) {
						tpDocument.addDateAsTimeInMS(cData.getFieldName(),
								RepositoryConstants.BLANK_LONG);
					} else {
						tpDocument.addDateAsTimeInMS(cData.getFieldName(),
								dt.getTime());
					}
				} else if (cData.getFieldType().equals(
						CustomFieldConstants.TYPE_NUMBER)) {
					String number = cData.getFieldNumberValueInString();
					try {
						double num = Double.parseDouble(number);
						tpDocument.addNumber(cData.getFieldName(), num);
					} catch (Exception e) {
						tpDocument.addNumber(cData.getFieldName(),
								RepositoryConstants.BLANK_NUMBER);
					}
				} else {
					if (!Utils.isBlankOrNull(cData.getFieldStringValue())) {
						String[] vals = cData.getFieldStringValue()
								.split("\\|");
						for (int k = 0; vals != null && k < vals.length; k++) {
							if (!Utils.isBlankOrNull(vals[k])) {
								// lower case is added to allow case insensetive
								// search on custom
								// fields
								tpDocument.addField(cData.getFieldName(),
										vals[k].toLowerCase(), false);
							}
						}
					} else {
						tpDocument.addField(cData.getFieldName(),
								RepositoryConstants.BLANK_STRING, false);
					}
				}

			}

			// add skills
			if (!Utils.isBlankOrNull(aData.getSkillsString())) {
				tpDocument.addSkill(aData.getSkillsString());
			}
			if (!Utils.isBlankOrNull(aData.getAliasesString())) {
				tpDocument.addKeyword(aData.getAliasesString());
			}
			// add flags
			String flagIds = aData.getFlagIds();
			if (!Utils.isBlankOrNull(flagIds)) {
				String[] fIds = flagIds.split(",");
				for (int i = 0; i < fIds.length; i++) {
					tpDocument.addFlagId(fIds[i]);
				}
			}
			String rejectReasonId = aData.getRejectReasonIds();
			if (!Utils.isBlankOrNull(rejectReasonId)) {
				String[] fIds = rejectReasonId.split(",");
				for (int i = 0; i < fIds.length; i++) {
					tpDocument.addRejectReasonId(fIds[i]);
				}
			}
			String rejectLevelIds = aData.getRejectStepLevels();
			if (!Utils.isBlankOrNull(rejectLevelIds)) {
				String[] fIds = rejectLevelIds.split(",");
				for (int i = 0; i < fIds.length; i++) {
					tpDocument.addRejectLevelId(fIds[i]);
				}
			}

			tpDocument.done();
		} catch (Exception e) {
			TPLogger.getLogger().fatal(
					"Error while creating TPDocument object", e);
		}
		return tpDocument;
	}

}
