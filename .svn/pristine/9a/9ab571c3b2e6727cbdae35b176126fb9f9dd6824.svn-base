package com.talentPool.selectionProcess.scheduler;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.admin.manager.AdminManager;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.properties.TPLabels;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.masters.dataobject.SourceData;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.dataobject.FeedbackData;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;

public class CandidateProgressNotficationJob implements Job{
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Sending email notification when candidate moved to next step");
		LoginManager loginManager = null;
		PositionManager positionManager = null;
		try {
			loginManager = new LoginManager();
			positionManager = new PositionManager();
			String applicantId = context.getTrigger().getJobDataMap().getString("applicantId");
			String userId = context.getTrigger().getJobDataMap().getString("userId");
			String applicantPositionId = context.getTrigger().getJobDataMap().getString("positionId");
			String positionStepId = context.getTrigger().getJobDataMap().getString("positionStepId");
			
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_PROGRESS_EMAIL_TO_CANDIDATE);
			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();

			LoginData userData = loginManager.getUser(userId);		
			String contentStr = TemplateUtils.getConvertedUserData(userData);
			
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantData(applicantId);
			String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, "");
			contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
			
			PositionData pData = positionManager.getPositionSummary(applicantPositionId);
			String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
			contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
			
			AdminManager adminManager = new AdminManager();
			SourceData source =adminManager.getCandidatePortalSource();
			
			if (!Utils.isBlankOrNull(positionStepId)) {
				String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME", positionManager.getPositionStepName(Integer.parseInt(positionStepId)));
				contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
			}
			
			if(applicantData.getApplicantSourceId()==Integer.parseInt(source.getSourceId())){
				SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
				FeedbackData feedback = selectionProcessManager.getCandidateCurrentStepData(applicantId);
				if (!Utils.isBlankOrNull(feedback.getString("feedbackFormId"))){
					String questionnareDetails = TemplateUtils.getConstructedToken(TPLabels.getLabel("move_to_step.questionnare_label"), 
							TPLabels.getLabel("move_to_step.questionnare_details")+ " </p>  " + TPApplicationProperties.getProperty("questinnaire_details_link"));
					contentStr = TemplateUtils.appndToToken(contentStr, questionnareDetails);
				}
			}

			HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
			VelocityManager velocityManager = new VelocityManager();
			String subject = velocityManager.handle(subjectVM, keyValMap);
			String content = velocityManager.handle(contentVM, keyValMap);

			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			
			MessageData messageData = new MessageData();
			messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
			messageData.setHtmlBody(content);
			messageData.setSubject(subject);
			messageData.setTo(applicantData.getApplicantEmail1());
			messageData.setCc(applicantData.getApplicantEmail2());
			
			TPMailSender sender = new TPMailSender();
			sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
		} catch (Exception e) {
			log.error("error while sending reminders for feedback", e);
		}

	}
}
