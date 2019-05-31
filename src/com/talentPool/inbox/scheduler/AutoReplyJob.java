/**
 * 
 */
package com.talentPool.inbox.scheduler;

import java.util.HashMap;

import org.quartz.Job;
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
import com.talentPool.notifier.CandidateStateTemplateEnums;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.user.manager.UserManager;

/**
 * @author shivprasad
 * 
 */
public class AutoReplyJob implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			String stateEnumStr = context.getTrigger().getJobDataMap().getString("stateEnum");
			String applicantPositionId = context.getTrigger().getJobDataMap().getString("positionId");
			CandidateStateTemplateEnums stateEnum = CandidateStateTemplateEnums.findByName(stateEnumStr);
			TPLogger.getLogger().debug("========= START AUTO REPLY JOB =============");
			String fromEmail = context.getTrigger().getJobDataMap().getString("fromEmail");
			UserManager userManager = new UserManager();
			InboxManager inboxManager = new InboxManager();
			// extract email address if in format Shivprasad Dhakane
			// <shivprasad@talentpoool.in>
			if (fromEmail != null && fromEmail.indexOf("<") > 0) {
				fromEmail = fromEmail.substring(fromEmail.indexOf("<") + 1, fromEmail.indexOf(">"));
			}
			// check if email is from known user, and send auto reply only if
			// email is not from known user.
			if (userManager.isEmailAddressExists(fromEmail)) {
				TPLogger.getLogger().debug("Email exist, do not send auto reply");
			} else {
				// send auto reply email code goes here
				InboxData inboxData = inboxManager.getCurrentInboxSettings();
				TemplateData templateData = null;
				String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
				TemplateManager templateManager = new TemplateManager();
				switch(stateEnum){
				case APPLIED:
					templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_CANDIDATE_POSITION_APPLIED);
					break;
				case UPDATED:
					templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_CANDIDATE_PROFILE_UPDATE);
					break;
				case ADDED:
					templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_AUTO_REPLY_EMAIL_TEMPLATE);
					break;
				}
				
				ApplicantManager applicantManager = new ApplicantManager();
				ApplicantData applicantData = applicantManager.getApplicantSummaryData(applicantManager.getApplicantIdFromEmail(fromEmail));
				String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
				String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
				contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
				
				if(!Utils.isBlankOrNull(applicantPositionId)){
					PositionManager positionManager = new PositionManager();
					PositionData pData = positionManager.getPositionSummary(applicantPositionId);
					String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
					contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
				}
				
				String contentVM = templateData.getTemplateContentFile();
				String subjectVM = templateData.getTemplateSubjectFile();
				String keyMap = templateData.getTemplateVariables();
				
				VelocityManager velocityManager = new VelocityManager();
				HashMap<String, String> keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
				String subject = velocityManager.handle(subjectVM, keyValMap);
				String content = velocityManager.handle(contentVM, keyValMap);
				
				MessageData messageData = new MessageData();
				messageData.setFrom(inboxData.getInboxDisplayName() + " <" + inboxData.getInboxEmail() + ">");
				messageData.setHtmlBody(content);
				messageData.setSubject(subject);
				messageData.setTo(fromEmail);
				TPMailSender sender = new TPMailSender();
				sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);

			}

		} catch (Exception e) {
			TPLogger.getLogger().error("Error in AutoReplyJob", e);
		}
		TPLogger.getLogger().debug("========= END AUTO REPLY JOB =============");
	}

}
