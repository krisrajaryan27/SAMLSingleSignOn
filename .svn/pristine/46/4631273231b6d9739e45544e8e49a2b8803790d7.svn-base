/**
 * 
 */
package com.talentPool.employeeservice.scheduler;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author Praveen
 *
 */
public class EmployeeProgressMailJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			TPLogger.getLogger().debug("Start progress email to employee job");

			JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
			String templateTypeId =  jobDataMap.getString("templateTypeId");
			String userId = jobDataMap.getString("userId");
			String applicantId = jobDataMap.getString("applicantId");
			String applicantPositionId = jobDataMap.getString("applicantPositionId");
			String applicantStepId = jobDataMap.getString("applicantStepId");
			String positionStepName = null;
			
			
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(templateTypeId);
			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();

			LoginManager loginManager = new LoginManager();
			LoginData userData = loginManager.getUser(userId);		
			String contentStr = TemplateUtils.getConvertedUserData(userData);
			String employeeStr = "";
			String globalVar = "";
			
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantData(applicantId);
			String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantPositionId, applicantStepId);
			String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
			
			PositionManager positionManager = new PositionManager();
			PositionData pData = positionManager.getPositionSummary(applicantPositionId);
			String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
			contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);

			if (!Utils.isBlankOrNull(applicantStepId)) {
				positionStepName = positionManager.getPositionStepName(Integer.parseInt(applicantStepId));
				if(!Utils.isBlankOrNull(positionStepName)){
					positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME", positionStepName);
					contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
				}
			}
			
			MastersManager mastersManager = new MastersManager();
			SourceData sourceData = mastersManager.getSource(String.valueOf(applicantData.getApplicantSourceId()));
			
			if(sourceData!=null){
				employeeStr = TemplateUtils.getConvertedEmployeeData(sourceData);
				contentStr = TemplateUtils.appndToToken(contentStr, employeeStr);
			}
			
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			
			if(inboxData!=null && !Utils.isBlankOrNull(inboxData.getInboxDisplayName()) ){
				globalVar = TemplateUtils.getConvertedGlobalVars(inboxData);
				contentStr = TemplateUtils.appndToToken(contentStr, globalVar);
			}

			HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
			VelocityManager velocityManager = new VelocityManager();
			String subject = velocityManager.handle(subjectVM, keyValMap);
			String content = velocityManager.handle(contentVM, keyValMap);

			
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
			messageData.setHtmlBody(content);
			messageData.setSubject(subject);
			if(TemplateConstants.TEMPLATE_TYPE_REJECTION_EMAIL_TO_CANDIDATE.equals(templateTypeId)) {
				messageData.setTo(applicantData.getApplicantEmail1());
				messageData.setCc(applicantData.getApplicantEmail2());
			} else {
				//userData = loginManager.getUser(applicantData.getVendorId());
				messageData.setTo(sourceData.getSourceEmail());
			}		
			if(TemplateConstants.TEMPLATE_SAVE_AS_DRAFT.equals(templateData.getIsTemplateSaveAsDraft())) {			
				int folderId = inboxManager.getFolderIdForSystemFolderDrafts();
				messageData.setFolderId(folderId);
				messageData.setApplicantId(applicantId);
				messageData.setPositionId(applicantPositionId);
				messageData.setStepId(applicantStepId);
				Calendar cal = new GregorianCalendar();
				messageData.setSendDate(new java.sql.Date(new Timestamp(cal.getTime().getTime()).getTime()));
				inboxManager.saveMessage(messageData, ""+folderId, userId);
			} else {
				TPMailSender sender = new TPMailSender();
				sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
			}

			TPLogger.getLogger().debug("End progress email to employee job");
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}

	}

}
