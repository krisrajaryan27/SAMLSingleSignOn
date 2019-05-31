package com.talentPool.jobPortals.manager;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.ApplicantDuplicateSearchData;
import com.talentPool.applicant.manager.ApplicantDuplicateChecker;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.DocumentConstants;
import com.talentPool.documents.utils.DocumentUploader;
import com.talentPool.inbox.scheduler.AutoReplyScheduler;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.notifier.CandidateStateTemplateEnums;
import com.talentPool.parser.NaukriParser;
import com.talentPool.parser.converter.GenericConverter;
import com.talentPool.parser.converter.WordToHtmlConverter;
import com.talentPool.repository.TPIndexEvent;

/**
 * @author SiddharthK
 *
 */
@Component
public class ApplicantJobPortalManager {
	
	public String importApplicant(ApplicantData applicantData){
		//get source for naukri.com
		//SourceData data = adminManager.getCompanyWebSiteSource();
		//applicantData.setApplicantSourceId(Integer.parseInt(sourceId));
		String sourceId = "";
		ArrayList<String> sourceIds = CommonUtils.getSourceIds();
		ArrayList<String> sourceNames = CommonUtils.getSourceNames();
		int idx = 0;
		for (int i = 0; i < sourceNames.size(); i++) {
			String name = sourceNames.get(i);
			name = name.toLowerCase();
			if (name.contains(NaukriParser.part13)) {
				idx = i;
				break;
			}
		}
		if (idx >= 0) {
			sourceId = sourceIds.get(idx);
		}
		if(applicantData.getApplicantSourceId()==0){
			applicantData.setApplicantSourceId(Integer.parseInt(sourceId));
		}
		ArrayList<ApplicantDuplicateSearchData> duplicates = null;
		ApplicantDuplicateChecker applicantDuplicateChecker = new ApplicantDuplicateChecker();
		String applicantId = applicantData.getApplicantId();
		boolean updateCandidateFlag = false;
		try {
			duplicates = applicantDuplicateChecker.getInternalDuplicateChecked(null, applicantData.getApplicantName(), applicantData.getApplicantEmail1(), applicantData.getApplicantEmail2(), applicantData.getApplicantCellPhone(), applicantData.getCustomFields());
			if (duplicates != null && duplicates.size() > 0 || !Utils.isBlankOrNull(applicantId)) {
				String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, applicantData.getApplicantOriginalDocPath());
				
				if(Utils.isBlankOrNull(applicantData.getApplicantOriginalDocPath())){
					filePath = Utils.concatFilePath(DocumentConstants.documentsPath, applicantData.getApplicantOriginalResumePath());
				}

				DocumentUploader documentUploader = new DocumentUploader();
				String filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
		
				String applicantOriginalResumePath = "";
				if (Utils.isBlankOrNull(filePathFinal)) {
					filePathFinal = filePath;
				}
				if (filePathFinal.length() > DocumentConstants.documentsPath.length()) {
					applicantOriginalResumePath = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
				}					
		
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
				//applicantData.setApplicantOriginalDocPath(applicantOriginalResumePath);
				applicantData.setApplicantTextResume(textContent);
				
				ApplicantManager applicantManager = new ApplicantManager();
				if(Utils.isBlankOrNull(applicantId)){
					applicantId = duplicates.get(0).getApplicantId();
				}
				applicantData.setApplicantId(applicantId);
				ApplicantData aData = applicantManager.getApplicantData(applicantId);
				int source = aData.getApplicantSourceId();
				MastersManager mastersManager = new MastersManager();
				SourceData sourceData = mastersManager.getSource(String.valueOf(source));
				
				boolean isLockInPeriodExpired = false;
				if (!Utils.isBlankOrNull(sourceData.getLockInPeriodOnImport()) && Integer.parseInt(sourceData.getLockInPeriodOnImport())>0){
					DBPreparedQuery dq = null;
					String[] dynParams = new String[1];
					dynParams[0] = applicantId;
					String applicantDateCreatedStr = "";
					try{
						dq = new DBPreparedQuery("dApplicantManager_GetApplicantResumeLockedDate");
						dq.setString(1, applicantId);
						applicantDateCreatedStr = dq.getStringResult();
					}finally{
						if (dq != null) {
							dq.releaseConnection();
						}
					}
					Date applicantDateCreated = DateUtils.convertToDate(applicantDateCreatedStr, DateConstants.DB_DATE_TIME_PATTERN);
					int isExpired = Integer.parseInt(sourceData.getLockInPeriodOnImport())*30
					- Integer.parseInt(Utils.getDateDiffenence(applicantDateCreated, new java.util.Date()));
					if (isExpired<0){
						isLockInPeriodExpired = true;
					}
				}else{
					isLockInPeriodExpired = true;
				}
				if (!isLockInPeriodExpired){
					applicantData.setApplicantSourceId(source);
				}
				if (!Utils.isBlankOrNull(aData.getApplicantPositionId()) && !Utils.isBlankOrNull(aData.getApplicantStepId())) {
					String[] positionIds = applicantData.getApplicantPositionId().split(",");
					DBTransaction tran = new DBTransaction();
					DBPreparedQuery dq = null;
					try{
						for(int i=0; i<positionIds.length; i++){
							// Add Applicant Text Resume
							if (!aData.getApplicantPositionId().equals(positionIds[i])){
								applicantManager.addApplicantTextResume(applicantId, applicantData.getApplicantTextResume(), positionIds[i], tran);
								addApplicantPositionMapping(applicantId, positionIds[i], tran);
								dq = new DBPreparedQuery("dAddResumeApplicantPositionMapping", tran);
								dq.setString(1, applicantData.getApplicantOriginalResumePath());
								dq.setString(2, applicantData.getApplicantOriginalDocPath());
								dq.setString(3, applicantId);
								dq.setString(4, positionIds[i]);
								dq.execute();
							}
						}
						if(applicantData.getProfilePicPath()!=null){
							dq= new DBPreparedQuery("dUpdateProfilePicPath",tran);
							dq.setString(1, applicantData.getProfilePicPath());
							dq.setString(2, aData.getApplicantId());
							dq.execute();
						}
						tran.commit();
					}finally {
						if (dq != null) {
							dq.releaseTransaction(tran);
						}
					}
				}else {
					if(aData.getProfilePicPath()!=null && applicantData.getProfilePicPath()==null){
						applicantData.setProfilePicPath(aData.getProfilePicPath());
					}
					applicantManager.updateApplicant(applicantData, applicantData.getSkillIds(), null);
					applicantManager.updateOriginalResume(applicantId,applicantData.getApplicantPositionId(), applicantData.getApplicantOriginalResumePath(), applicantData.getApplicantOriginalDocPath(), applicantData.getApplicantTextResume());
				}
//				TPIndexEventQueue.push();
				pushIntoEventQueueOnTalentPool(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
				TPLogger.getLogger().debug("INSIDE UPDATE APPLICATNT JOB PORTAL MANAGER @@@@@@@@@@@@@@@");
				updateCandidateFlag=true;
			}
			
			//duplicate check end here
			
			
			
			else {
				String filePath = Utils.concatFilePath(DocumentConstants.documentsPath, applicantData.getApplicantOriginalDocPath());
				
				DocumentUploader documentUploader = new DocumentUploader();
				String filePathFinal = documentUploader.getHtmlFilePathIfExist(filePath);
		
				String applicantOriginalResumePath = "";
				if (Utils.isBlankOrNull(filePathFinal)) {
					filePathFinal = filePath;
				}
				if (filePathFinal.length() > DocumentConstants.documentsPath.length()) {
					applicantOriginalResumePath = filePathFinal.substring(DocumentConstants.documentsPath.length() + 1);
				}					
		
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
				//applicantData.setApplicantOriginalDocPath(applicantOriginalResumePath);
				applicantData.setApplicantTextResume(textContent);
				ApplicantManager applicantManager = new ApplicantManager();
				
				TPLogger.getLogger().debug("INSIDE ADD APPLICATNT JOB PORTAL MANAGER @@@@@@@@@@@@@@@");
				applicantId = applicantManager.addApplicant(applicantData, applicantData.getSkillIds());
				
//				TPIndexEventQueue.push();
				pushIntoEventQueueOnTalentPool(new TPIndexEvent(TPIndexEvent.TYPE_ADD_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
				TPLogger.getLogger().debug("INSIDE ADD APPLICATNT JOB PORTAL MANAGER @@@@@@@@@@@@@@@");
			}
			
			if (GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_AUTO_REPLY_EMAIL).equals(GlobalConstants.ENABLED)) {
				if (!Utils.isBlankOrNull(applicantData.getApplicantPositionId())) {
					AutoReplyScheduler.addTrigger(applicantData.getApplicantEmail1(),CandidateStateTemplateEnums.APPLIED,applicantData.getApplicantPositionId());
				}else if(updateCandidateFlag){
					AutoReplyScheduler.addTrigger(applicantData.getApplicantEmail1(),CandidateStateTemplateEnums.UPDATED,null);
				}else{
					AutoReplyScheduler.addTrigger(applicantData.getApplicantEmail1(),CandidateStateTemplateEnums.ADDED,null);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while importing applicant from Job portal", e);
		}
		return applicantId;
	}
	
	public ApplicantData convertNaukriApplicantToApplicantData(Object naukriObject){
		ApplicantData applicantData = new ApplicantData();
		return applicantData;
	}
	
	
	/**
	 * to be called only from the api side
	 */
	private void pushIntoEventQueueOnTalentPool(TPIndexEvent indexEvent){
		Client client = Client.create();
		String params = "eventType=" + indexEvent.getEventType() + "&eventTypeId=" + indexEvent.getEventTypeId() + "&priority="+indexEvent.getPriority();
		WebResource webResource = client.resource(Utils.buildTalentPoolURL("rest/index/push?"+params));
		ClientResponse response = webResource.type("text/plain").get(ClientResponse.class);
		String res = response.getEntity(String.class);
		TPLogger.getLogger().debug("Applicant add index event push status : " + res);
		response.close();
	}
	
	
	/**
	 * to be called when applicant is added or apply with a new position
	 * @param applicantId
	 * @param positionId
	 * @param tran
	 * @throws Exception
	 */
	public void addApplicantPositionMapping(String applicantId, String positionId, DBTransaction tran) throws Exception{
		DBPreparedQuery dq = null;
		try {
			if (tran != null){
				dq = new DBPreparedQuery("dAddApplicantPositionMapping", tran);
			} else {
				dq = new DBPreparedQuery("dAddApplicantPositionMapping");
			}
			dq.setId(1, applicantId);
			dq.setString(2, positionId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while adding applicant position mapping where applicantId::"+ applicantId +" and positionId::"+positionId, e);
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	
	/**
	 * 
	 * to be called when applicant is shortlisted
	 * @param applicantId
	 * @param positionId
	 * @param tran
	 * @throws Exception
	 */
	public void deleteApplicantPositionMapping(String applicantId, String positionId, DBTransaction tran) throws Exception{
		DBPreparedQuery dq = null;
		try {
			if (tran != null){
				dq = new DBPreparedQuery("dDeleteApplicantPositionMapping", tran);
			} else {
				dq = new DBPreparedQuery("dDeleteApplicantPositionMapping");
			}
			dq.setId(1, applicantId);
			dq.setString(2, positionId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting applicant position mapping where applicantId::"+ applicantId +" and positionId::"+positionId, e);
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	/**
	 * @param applicantId
	 * @param tran
	 * @throws Exception
	 * to be called when applicant is blacklisted or joined
	 */
	public void deleteApplicantPositionMappingByApplicant(String applicantId, DBTransaction tran) throws Exception{
		DBPreparedQuery dq = null;
		try {
			if (tran != null){
				dq = new DBPreparedQuery("dDeleteApplicantPositionMappingByApplicant", tran);
			} else{
				dq = new DBPreparedQuery("dDeleteApplicantPositionMappingByApplicant");
			}
			dq.setId(1, applicantId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting applicant position mapping where applicantId::"+ applicantId, e);
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	/**
	 * to be called when position is dropped or closed
	 * @param positionId
	 * @param tran
	 * @throws Exception
	 */
	public void deleteApplicantPositionMappingByPosition(String positionId, DBTransaction tran) throws Exception{
		DBPreparedQuery dq = null;
		try {
			if (tran != null){
				dq = new DBPreparedQuery("dDeleteApplicantPositionMappingByPosition", tran);
			} else {
				dq = new DBPreparedQuery("dDeleteApplicantPositionMappingByPosition");
			}
			dq.setString(1, positionId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while deleting applicant position mapping where positionId::"+ positionId, e);
		} finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}

}
