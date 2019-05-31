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
 * @author Shantanu
 *
 */
public class EmployeeRejectMailJob implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			TPLogger.getLogger().debug("Start reject email to employee job");

			JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
			String applicantId = jobDataMap.getString("applicantId");
			String positionId = jobDataMap.getString("positionId");
			String positionStepId = jobDataMap.getString("positionStepId");
			String userId = jobDataMap.getString("userId");
			String sourceEmail = jobDataMap.getString("sourceEmail");
			String employeeStr = "";

			// get template data
			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
			
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			String userStr = TemplateUtils.getConvertedContactData(loginData);
			contentStr = TemplateUtils.appndToToken(contentStr, userStr);
			
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantId);
			String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
			String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
			
			PositionManager positionManager = new PositionManager();
			if(!Utils.isBlankOrNull(positionId)){
				PositionData pData = positionManager.getPositionSummary(positionId);
				String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
				contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
			}
			if(!Utils.isBlankOrNull(positionStepId)){
				String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME",positionManager.getPositionStepName(Integer.parseInt(positionStepId)));
				contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
			}
			
			MastersManager mastersManager = new MastersManager();
			SourceData sourceData = mastersManager.getSource(String.valueOf(applicantData.getApplicantSourceId()));
			
			if(sourceData!=null){
				employeeStr = TemplateUtils.getConvertedEmployeeData(sourceData);
				contentStr = TemplateUtils.appndToToken(contentStr, employeeStr);
			}

			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_REJECTION_EMAIL_TO_EMPLOYEE);
			
			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();

			HashMap<String, String> keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
			VelocityManager velocityManager = new VelocityManager();
			String subject = velocityManager.handle(subjectVM, keyValMap);
			String content = velocityManager.handle(contentVM, keyValMap);
			// send email to users
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setHtmlBody(content);
			messageData.setSubject(subject);
			messageData.setTo(sourceEmail);
			
			if(TemplateConstants.TEMPLATE_SAVE_AS_DRAFT.equals(templateData.getIsTemplateSaveAsDraft())) {			
				int folderId = inboxManager.getFolderIdForSystemFolderDrafts();
				messageData.setFolderId(folderId);
				messageData.setApplicantId(applicantId);
				messageData.setPositionId(positionId);
				messageData.setStepId(positionStepId);
				Calendar cal = new GregorianCalendar();
				messageData.setSendDate(new java.sql.Date(new Timestamp(cal.getTime().getTime()).getTime()));
				inboxManager.saveMessage(messageData, ""+folderId, userId);
			} else {
				TPMailSender sender = new TPMailSender();
				sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
			}
			
			TPLogger.getLogger().debug("End reject email to employee job");
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}

	}

}
