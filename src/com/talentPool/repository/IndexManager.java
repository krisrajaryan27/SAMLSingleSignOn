package com.talentPool.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.index.IndexWriter;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBCallableQuery;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.constants.CustomFieldConstants;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.custom.manager.CustomFieldManager;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.selectionProcess.SelectionProcessConstants;

public class IndexManager {
	
	public static void processIndexEvent(TPIndexEvent event,IndexWriter idxWriter) {
		try {
			TPDocRepository tpDocRepository = new TPDocRepository();
			tpDocRepository.setIndexWriter(idxWriter);
			String eventType = event.getEventType();
			String eventTypeId = event.getEventTypeId();
			if (eventType.equals(TPIndexEvent.TYPE_ADD_APPLICANT) || eventType.equals(TPIndexEvent.TYPE_UPDATE_APPLICANT)) {
				ArrayList<TPDocument> docs = getApplicantDocuments(eventType, eventTypeId, 0, 1);
				if (docs != null && docs.size() > 0) {
					TPDocument tpDocument = docs.get(0);
					if (eventType.equals(TPIndexEvent.TYPE_ADD_APPLICANT)) {
						tpDocRepository.modifyRepository(RepositoryConstants.ACTION_ADD_DOC, tpDocument, null, null);
					} else {
						tpDocRepository.modifyRepository(RepositoryConstants.ACTION_UPDATE_DOC, tpDocument, null, null);
					}
				}
			}
			if (eventType.equals(TPIndexEvent.TYPE_ALL) || eventType.equals(TPIndexEvent.TYPE_SKILLS) || eventType.equals(TPIndexEvent.TYPE_DEGREE) || eventType.equals(TPIndexEvent.TYPE_BRANCH)
					|| eventType.equals(TPIndexEvent.TYPE_INSTITUTE) || eventType.equals(TPIndexEvent.TYPE_SOURCE)) {
				modifyRepositoryInBatches(tpDocRepository,eventType, eventTypeId);
			}
			if (eventType.equals(TPIndexEvent.TYPE_DELETE_APPLICANT)) {
				tpDocRepository.modifyRepository(RepositoryConstants.ACTION_DELETE_DOC, null, eventTypeId, null);
			}
			if (eventType.equals(TPIndexEvent.TYPE_OPTIMIZE_REPOSITORY)) {
				tpDocRepository.modifyRepository(RepositoryConstants.ACTION_OPTIMIZE, null, null, null);
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while processing index event", e);
		}
	}

	/**
	 * process the applicants in batch
	 * 
	 * @param type
	 * @param id
	 */
	public static void modifyRepositoryInBatches(TPDocRepository tpDocRepository,String eventType,String eventTypeId) throws Exception {
		int noOfApplicants = 0;
		if (eventType.equals(TPIndexEvent.TYPE_UPDATE_APPLICANT)) {
			noOfApplicants = 1;
		} else {
			noOfApplicants = getNoOfApplicantsToIndex(eventType, eventTypeId);
		}
		
		int threads = Runtime.getRuntime().availableProcessors();
		final DocumentQueue queue = new DocumentQueue();
		int cnt = 0;
		int batchSize = Integer.parseInt(TPApplicationProperties.getProperty("index.batch.fetch.size"));
		ExecutorService service = Executors.newFixedThreadPool(threads);
		service.execute(new MyThead(tpDocRepository,queue));
		while(cnt < noOfApplicants){
			DocFetchThread fetch = new DocFetchThread(eventType, eventTypeId, cnt, batchSize,noOfApplicants,queue);
			service.execute(fetch);
			cnt = cnt+batchSize;
		}
		service.shutdown();
		service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
	}

	public static ArrayList<TPDocument> getApplicantDocuments(String eventType, String eventTypeId, int start, int batchSize) {
		ArrayList<TPDocument> applicants = new ArrayList<TPDocument>();
		try {
			ArrayList results = getApplicantsData(eventType, eventTypeId, start, batchSize);
			ApplicantManager manager = new ApplicantManager();
			// Construct TPDocuments DO from list
			if (results != null) {
				for (int i = 0; i < results.size(); i++) {
					ApplicantData aData = (ApplicantData) results.get(i);
					ArrayList<SimpleDataObject> textResumes = manager.getApplicantTextResumes(aData.getApplicantId());
					if(textResumes!=null){
						StringBuilder combinedTextResume = new StringBuilder();
						for (SimpleDataObject textResume : textResumes) {
							combinedTextResume.append(textResume
									.getString("textResume"));
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
	
	public static TPDocument getTPDocument(ApplicantData aData) {
		TPDocument tpDocument = null;
		try {
			String contents = Utils.isBlankOrNull(aData.getApplicantTextResume()) ? "" : aData.getApplicantTextResume();
			String filePath = aData.getApplicantOriginalResumePath();
			String applicantName = aData.getApplicantName();
			String isConfidential = aData.getIsConfidential();
			String applicantStatus = aData.getApplicantStatus();
			// Date workingSince = aData.getApplicantWorkingSince();

			String workingSince = RepositoryConstants.BLANK_DATE;
			if (aData.getApplicantWorkingSince() != null) {
				workingSince = TPDateTools.dateToString(aData.getApplicantWorkingSince(), TPDateTools.Resolution.MONTH);
			}

			String mobileNo = Utils.isBlankOrNull(aData.getApplicantCellPhone()) ? "" : aData.getApplicantCellPhone();
			String lastEmp = Utils.isBlankOrNull(aData.getApplicantCurrentEmployer()) ? "" : aData.getApplicantCurrentEmployer();
			String passport = Utils.isBlankOrNull(aData.getPassportNumber()) ? "" : aData.getPassportNumber();
			String location = Utils.isBlankOrNull(aData.getApplicantCity()) ? "" : aData.getApplicantCity();
			String source = Utils.isBlankOrNull(aData.getApplicantSourceTitle()) ? "" : aData.getApplicantSourceTitle();
			String sourceTypeId = aData.getSourceTypeId();
			String resumeTypeId = Utils.isBlankOrNull(aData.getResumeTypeId()) ? "" : aData.getResumeTypeId();
			String email = Utils.isBlankOrNull(aData.getApplicantEmail1()) ? "" : aData.getApplicantEmail1();
			email += Utils.isBlankOrNull(aData.getApplicantEmail2()) ? "" : " " + aData.getApplicantEmail2();
			String phone1 = Utils.isBlankOrNull(aData.getApplicantHomePhone()) ? "" : aData.getApplicantHomePhone();
			String phone2 = Utils.isBlankOrNull(aData.getApplicantWorkPhone()) ? "" : aData.getApplicantWorkPhone();
			String inProcess = (!Utils.isBlankOrNull(aData.getApplicantStepId()) && !Utils.isBlankOrNull(aData.getApplicantPositionId())) ? "1" : "0";
			Date importDate = aData.getApplicantDateImported();
			Date birthDate = aData.getDateOfBirth();
			Date lastInteractionDate = aData.getApplicantLastInteractionDate();
			if(lastInteractionDate == null) {
				lastInteractionDate = importDate;
			} else if(importDate.after(lastInteractionDate)) {
				lastInteractionDate = importDate;
			}
			String state = RepositoryConstants.STATE_NEW;
			if (!Utils.isBlankOrNull(aData.getRejectReasonIds())) {
				state = RepositoryConstants.STATE_PROCESSED;
			}
			if (inProcess.equals("1")) {
				state = RepositoryConstants.STATE_INPROCESS;
			}
			if (!"1".equals(inProcess) && aData.getApplicantJoined().equals("1")) {
				state = RepositoryConstants.STATE_JOINED;
			}
			ApplicantManager applicantManager = new ApplicantManager();
			//if candidate has applied for any position but not in process
		
			
			String employeeCode = (Utils.isBlankOrNull(aData.getEmployeeCode())) ? "" : aData.getEmployeeCode();
			tpDocument = new TPDocument(aData.getApplicantId(), contents, filePath, applicantName, workingSince, lastEmp, passport, location, sourceTypeId, resumeTypeId, source, email, importDate, birthDate, lastInteractionDate, state, mobileNo, isConfidential, employeeCode, aData.getApplicantJoined(),applicantStatus);
			
			ArrayList<PositionData> positions = applicantManager.getPositionsAndResumesAppliedByCandidate(aData.getApplicantId(),true); 
			if(positions.size()>0 && Utils.isBlankOrNull(aData.getApplicantStepId())){
				state = RepositoryConstants.STATE_APPLIED;
				//applied positions
			}
			if(positions.size()>0 ){
				//applied positions
				tpDocument.addAppliedPosition(positions);
			}
			
			// add educational details
			ArrayList<EducationalData> eduList = aData.getEducationalDetails();
			for (int i = 0; eduList != null && i < eduList.size(); i++) {
				EducationalData eData = eduList.get(i);
				String degreeTitle = Utils.isBlankOrNull(eData.getDegreeTitle()) ? "" : eData.getDegreeTitle();
				int degreeId = Utils.isBlankOrNull(eData.getDegreeTitle()) ? 0 : eData.getDegreeId();
				String institute = Utils.isBlankOrNull(eData.getInstitute()) ? "" : eData.getInstitute();
				String major = Utils.isBlankOrNull(eData.getMajor()) ? "" : eData.getMajor();
				double grade=Utils.isNumeric(eData.getGrade()) ?Double.parseDouble(eData.getGrade()): 0;
				Date yearOfPassing = eData.getYearOfPassing()!=null?eData.getYearOfPassing():null;
				if (degreeTitle != "" ) {
					tpDocument.addEducation(degreeTitle, institute, major, yearOfPassing,grade,degreeId);
				}
			}
			
			// add Employment History details
			ArrayList<EmploymentHistoryData> empHistList = aData.getEmploymentHistoryDetails();
			for (int i = 0; empHistList != null && i < empHistList.size(); i++) {
				EmploymentHistoryData eData = empHistList.get(i);
				String employerName = Utils.isBlankOrNull(eData.getEmployerName()) ? "" : eData.getEmployerName();
				String designationName = Utils.isBlankOrNull(eData.getDesignationName()) ? "" : eData.getDesignationName();				
				if (employerName != "" || designationName != "" ) {
					tpDocument.addEmploymentHistory(employerName, designationName);
				}
			}
			
			// add custom fields
			ArrayList<CustomFieldData> customFields = aData.getCustomFields();
			for (int i = 0; customFields != null && i < customFields.size(); i++) {
				CustomFieldData cData = customFields.get(i);
				if (cData.getFieldType().equals(CustomFieldConstants.TYPE_DATE)) {
					Date dt = cData.getFieldDateValue();
					if (dt == null) {
						tpDocument.addDateAsTimeInMS(cData.getFieldName(), RepositoryConstants.BLANK_LONG);
					} else {
						tpDocument.addDateAsTimeInMS(cData.getFieldName(), dt.getTime());
					}
				} else if (cData.getFieldType().equals(CustomFieldConstants.TYPE_NUMBER)) {
					String number = cData.getFieldNumberValueInString();
					try {
						double num = Double.parseDouble(number);
						tpDocument.addNumber(cData.getFieldName(), num);
					} catch (Exception e) {
						tpDocument.addNumber(cData.getFieldName(), RepositoryConstants.BLANK_NUMBER);
					}
				} else {
					if (!Utils.isBlankOrNull(cData.getFieldStringValue())) {
						String[] vals = cData.getFieldStringValue().split("\\|");
						for (int k = 0; vals != null && k < vals.length; k++) {
							if (!Utils.isBlankOrNull(vals[k])) {
								// lower case is added to allow case insensetive search on custom
								// fields
								tpDocument.addField(cData.getFieldName(), vals[k].toLowerCase(), false);
							}
						}
					} else {
						tpDocument.addField(cData.getFieldName(), RepositoryConstants.BLANK_STRING, false);
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
			tpDocument.addPhone1(phone1);
			tpDocument.addPhone2(phone2);
			tpDocument.done();
		} catch (Exception e) {
			TPLogger.getLogger().fatal("Error while creating TPDocument object", e);
		}
		return tpDocument;
	}

	/**
	 * returns an arraylist of applicant data for given batch size and for given changes
	 * 
	 * @param eventType
	 * @param eventTypeId
	 * @param start
	 * @param batchSize
	 * @return
	 */
	public static ArrayList<ApplicantData> getApplicantsData(String eventType, String eventTypeId, int start, int batchSize) {
		ArrayList<ApplicantData> applicants = null;
		DBCallableQuery dq = null;
		
		try {
			dq = new DBCallableQuery("dIndexManager_ProcGetApplicantsToIndex");
			dq.setString(1, eventType);
			dq.setInt(2, Utils.isBlankOrNull(eventTypeId) ? 0 : Integer.parseInt(eventTypeId));
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
			dq.setInt(21, Integer.parseInt(ApplicantConstants.APPLICANT_NOT_JOINED));
			applicants = dq.getResult();

			for (int i = 0; applicants != null && i < applicants.size(); i++) {
				ApplicantData aData = applicants.get(i);
				ApplicantManager applicantManager = new ApplicantManager();
				ArrayList<EducationalData> educationalDetails = applicantManager.getEducationalInfo(aData.getApplicantId());
				aData.setEducationalDetails(educationalDetails);
				ArrayList<EmploymentHistoryData> employHistoryDetails = applicantManager.getEmploymentHistoryInfo(aData.getApplicantId());
				aData.setEmploymentHistoryDetails(employHistoryDetails);
				// set custom fields
				if (CustomFieldManager.isCustomFieldsAvailable(CustomFieldConstants.ENTITY_TYPE_APPLICANT)) {
					CustomFieldManager customFieldManager = new CustomFieldManager();
					ArrayList<CustomFieldData> customFields = customFieldManager.getCustomFieldValuesForSearchableFields(aData.getApplicantId(), CustomFieldConstants.ENTITY_TYPE_APPLICANT);
					aData.setCustomFields(customFields);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting batched", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}

		}
		return applicants;
	}

	public static int getNoOfApplicantsToIndex(String eventType, String eventTypeId) {
		int noOfApplicants = 0;
		DBPreparedQuery dq = null;
		try {
			if (eventType.equals(TPIndexEvent.TYPE_ALL)) {
				dq = new DBPreparedQuery("dIndexManager_CountAll");
			} else if (eventType.equals(TPIndexEvent.TYPE_SKILLS)) {
				dq = new DBPreparedQuery("dIndexManager_CountForSkills");
				dq.setId(1, eventTypeId);
			} else if (eventType.equals(TPIndexEvent.TYPE_DEGREE)) {
				dq = new DBPreparedQuery("dIndexManager_CountForDegree");
				dq.setId(1, eventTypeId);
			} else if (eventType.equals(TPIndexEvent.TYPE_BRANCH)) {
				dq = new DBPreparedQuery("dIndexManager_CountForBranch");
				dq.setId(1, eventTypeId);
			} else if (eventType.equals(TPIndexEvent.TYPE_INSTITUTE)) {
				dq = new DBPreparedQuery("dIndexManager_CountForInstitute");
				dq.setId(1, eventTypeId);
			} else if (eventType.equals(TPIndexEvent.TYPE_SOURCE)) {
				dq = new DBPreparedQuery("dIndexManager_CountForSource");
				dq.setId(1, eventTypeId);
			}else if (eventType.equals(TPIndexEvent.TYPE_PREVIOUS_EMPLOYER)) {
				dq = new DBPreparedQuery("dIndexManager_CountForPreviousEmployer");
				dq.setId(1, eventTypeId);
			}else if (eventType.equals(TPIndexEvent.TYPE_DESIGNATION)) {
				dq = new DBPreparedQuery("dIndexManager_CountForDesignation");
				dq.setId(1, eventTypeId);
			}
			
			noOfApplicants = dq.getIntResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting no of applicants to index", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return noOfApplicants;
	}

	public static void indexAllDocuments() {
		try {
			TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in indexing all applicants", e);
		}
	}

	public static void buildNewIndex() {
		try {
			TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_ALL, null, TPIndexEvent.PRIORITY_NORMAL));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in indexing all applicants", e);
		}
	}

	public void indexUpdate(String applicantIds,String indexStatus) throws SQLException{
		DBPreparedQuery dq =null;
		String[] dynParams = new String[1];
		String query = "("+"@@"+","+indexStatus+")";
		StringBuilder builder = new StringBuilder();
		for(String applicantId: applicantIds.split(",")){
			String tmp = query.replaceAll("@@", applicantId);
			if(!Utils.isBlankOrNull(builder.toString())){
				tmp = ","+tmp;
			}
			builder.append(tmp);
		}
		dynParams[0]= builder.toString();

		DBTransaction dt = null;
		try{
			dt = new DBTransaction();
			dq =  new DBPreparedQuery("dIndexManager_updateIndexStatus",dynParams,dt);
			dq.execute();
		}catch(SQLException e){
			TPLogger.getLogger().error("error updating index status in db",e);
			dt.rollback();
			throw e;
		}finally{
			if(dq!=null){
				dq.releaseTransaction(dt);
			}
		}
		
	}
	
	public String getFailedApplicantsForStatus(String indexStatus){
		DBPreparedQuery dq = null;
		dq = new DBPreparedQuery("dIndexManager_getApplicantForIndexStatus");
		String result = null;
		try {
			dq.setString(1, indexStatus);
			result = dq.getStringResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error("error getting failed to index applicants from idnex status table",e);
		}finally{
			if(dq!=null){
				dq.releaseConnection();
			}
		}
		return result;
	}
}
