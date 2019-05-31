package com.talentPool.inbox.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.utils.ApplicantUtils;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.repository.TPDocument;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

public class MassEmailManager {
	public ArrayList setEmailSentStatus(ArrayList documents) {
		try {
			StringBuffer sb = new StringBuffer();
			ArrayList docIndex = new ArrayList(documents.size());
			if (documents != null) {
				int sz = documents.size();
				for (int i = 0; i < sz; i++) {
					TPDocument doc = (TPDocument) documents.get(i);
					if (i < sz) {
						sb.append(doc.getId());
						if (i < sz - 1) {
							sb.append(",");
						}
						docIndex.add(i, doc.getId());
					}
				}
			}
			ArrayList applicants = getAllApplicantsSentEmails(sb.toString(), 7);
			if (applicants != null) {
				int sz = applicants.size();
				for (int i = 0; i < sz; i++) {
					String aId = ((SimpleDataObject) applicants.get(i)).getString("applicantId");
					int idx = docIndex.indexOf(aId);
					TPDocument doc = (TPDocument) documents.get(idx);
					doc.setEmailSent(true);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while setting email sent status", e);
		}
		return documents;
	}

	public ArrayList getAllApplicantsSentEmails(String applicants, int days) {
		ArrayList result = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			dynParams[0] = " AND applicant_id in (" + applicants + ")";
			dq = new DBPreparedQuery("dMassEmailManager_GetEmailSentApplicants", dynParams);
			dq.setInt(1, days);
			dq.setId(2, InboxConstants.INBOX_FOLDER_SENT);
			result = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting applicant sent email", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}

	public void excludeEmailSent(ArrayList documents) {
		try {
			if (documents != null) {
				int i = 0;
				while (i < documents.size()) {
					TPDocument doc = (TPDocument) documents.get(i);
					if (doc.isEmailSent()) {
						documents.remove(i);
					} else {
						i++;
					}
				}

			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error while excluding email sent", e);
		}
	}

	public MessageData getMessageData(String templateCode, String applicantId, String contentStr, String userId) throws Exception {
		TemplateManager templateManager   = new TemplateManager();
		VelocityManager velocityManager   = new VelocityManager();
		ApplicantManager applicantManager = new ApplicantManager();
		LoginManager loginManager		  = null;
		ApplicantData emailApplicantData  = null;
		ApplicantData applicantData 	  = null;
		LoginData loginUserData			  = null; 		
		
		TemplateData templateData 		 = templateManager.getTemplateData(templateCode);		
		String contentVM 	= templateData.getTemplateContentFile();
		String subjectVM 	= templateData.getTemplateSubjectFile();
		
		String blockToRepeat = null;
		StringBuffer blockToRepeatReplaced = new StringBuffer();
		String totalContent = velocityManager.getContent(contentVM);
		String totalSubject = velocityManager.getContent(subjectVM);
		
		if (Utils.isBlankOrNull(contentStr)) {
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
		}
		
/*		if (!Utils.isBlankOrNull(applicantId)) {
			ApplicantManager applicantManager = new ApplicantManager();
			applicantData = applicantManager.getApplicantData(applicantId);
			if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_TYPE_APPLICANTS)) {
				String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
				String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
				contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
			}
			emailApplicantData = applicantManager.getApplicantEmailAddress(applicantId, true);

		}
*/		
		if (!Utils.isBlankOrNull(userId) && TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_USER)) {
			loginManager = new LoginManager();
			loginUserData = loginManager.getUser(userId);
		}
		
		
		String[] applicantIdList = applicantId.split(",");
		if(!Utils.isBlankOrNull(totalContent) && totalContent.contains(TemplateConstants.REPEAT) && totalContent.contains(TemplateConstants.END_REPEAT)){
			int repeatStartIndex = totalContent.indexOf(TemplateConstants.REPEAT)+TemplateConstants.REPEAT.length();
			int repeatEndIndex = totalContent.indexOf(TemplateConstants.END_REPEAT);
			blockToRepeat = totalContent.substring(repeatStartIndex,repeatEndIndex);
		}
		
		if(!Utils.isBlankOrNull(blockToRepeat)){
			for (String appId:applicantIdList ) {
				applicantData = applicantManager.getApplicantData(appId);
				blockToRepeatReplaced.append(replaceVariables(applicantData, templateData, blockToRepeat, contentStr,loginUserData));
			}
			totalContent = totalContent.replace(blockToRepeat, blockToRepeatReplaced.toString());
			totalContent = totalContent.replace(TemplateConstants.REPEAT, "");
			totalContent = totalContent.replace(TemplateConstants.END_REPEAT, "");
		}else {
			emailApplicantData = applicantManager.getApplicantEmailAddress(applicantIdList[0], true);
		}
		applicantData = applicantManager.getApplicantData(applicantIdList[0]);
		totalContent= replaceVariables(applicantData, templateData, totalContent, contentStr,loginUserData);
		totalSubject= replaceVariables(applicantData, templateData, totalSubject, contentStr,loginUserData);
		
		MessageData messageData = new MessageData();
		messageData.setHtmlBody(totalContent);
		messageData.setSubject(totalSubject);
		messageData.setApplicantId(applicantId);
		String emails = ApplicantUtils.getEmailToString(emailApplicantData);
		messageData.setTo(emails);

		return messageData;
	}
	
	private String replaceVariables(ApplicantData applicantData, TemplateData templateData, 
							String blockToRepeat, String contentStr,LoginData loginData) throws Exception{
		String candidateStr = null;
		String positionStr 	= null;
		String userStr 		= null;
		
		HashMap<String,String> keyValMap	= null;
		TemplateManager templateManager 	= new TemplateManager();
		VelocityManager velocityManager 	= new VelocityManager();
		try{
			
			if(!Utils.isBlankOrNull(applicantData.getApplicantId())){
				if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
					candidateStr = templateManager.getCandidateStr(applicantData, true);
					contentStr = TemplateUtils.appndToToken(candidateStr,contentStr);
				}
			}
			
			if(!Utils.isBlankOrNull(applicantData.getApplicantPositionId())){
				if (TemplateUtils.isTypeExist(templateData.getTemplateVariableIds(), TemplateConstants.TEMPLATE_VAR_TYPE_POSITIONS)) {
					positionStr = templateManager.getPositionStr(applicantData.getApplicantPositionId(), true);
					contentStr = TemplateUtils.appndToToken(positionStr,contentStr);
				}
			}
			
			if(loginData!=null){
				userStr = TemplateUtils.getConvertedUserData(loginData);
				contentStr = TemplateUtils.appndToToken(contentStr, userStr);
			}
			
			keyValMap = TemplateUtils.getKeyValueMap(templateData.getTemplateVariables(), contentStr);
			blockToRepeat = velocityManager.handleContent(blockToRepeat, keyValMap);
		}catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}	
		return blockToRepeat;
	}
	
}
