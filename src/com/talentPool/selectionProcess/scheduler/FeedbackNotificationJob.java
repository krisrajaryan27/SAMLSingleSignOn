/**
 * 
 */
package com.talentPool.selectionProcess.scheduler;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.inbox.InboxConstants;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.dataobject.MessageData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.selectionProcess.utils.SelectionProcessUtils;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;

/**
 * @author shivprasad
 * 
 */
public class FeedbackNotificationJob implements Job {
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("Sending notification for assigned interviewer to submit feedback");
		LoginManager loginManager = null;
		try {
			loginManager = new LoginManager();
			String applicantId = context.getTrigger().getJobDataMap().getString("applicantId");
			String positionId = context.getTrigger().getJobDataMap().getString("positionId");
			String stepId = context.getTrigger().getJobDataMap().getString("stepId");
			String attendeesId = context.getTrigger().getJobDataMap().getString("attendeesId");
			String userId = context.getTrigger().getJobDataMap().getString("userId");

			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);

			LoginData loginData = loginManager.getUser(userId);
			String userStr = TemplateUtils.getConvertedContactData(loginData);
			contentStr = TemplateUtils.appndToToken(contentStr, userStr);

			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantData(applicantId);
			String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
			String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);

			PositionManager positionManager = new PositionManager();
			PositionData pData = positionManager.getPositionSummary(positionId);
			String positionTitle = TemplateUtils.getConstructedToken("POSITION_NAME", pData.getPositionTitle());
			contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
			String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME", positionManager.getPositionStepName(Integer.parseInt(stepId)));
			contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
			//start of To send candidate status details to Recruiter
			
		/*	if(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_MAIL_TO_RECRUITER_FOR_CANDIDATE_STATUS).equals(GlobalConstants.ENABLED)){
				try{
					SelectionProcessUtils selectionProcessUtils=new SelectionProcessUtils();
					String recruiterEmail=selectionProcessUtils.getRecruiterEmailForPosition();
					TemplateManager templateManager = new TemplateManager();
					TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_CANDIDATE_PROGRESS_TO_RECRUITER);
					String contentVM = templateData.getTemplateContentFile();
					String subjectVM = templateData.getTemplateSubjectFile();
					String keyMap = templateData.getTemplateVariables();
					HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
					VelocityManager velocityManager = new VelocityManager();
					String subject = velocityManager.handle(subjectVM, keyValMap);
					String content = velocityManager.handle(contentVM, keyValMap);

					InboxManager newInboxManager = new InboxManager();
					InboxData newInboxData = newInboxManager.getCurrentInboxSettings();
					
					MessageData messageData = new MessageData();
					messageData.setFrom(newInboxData.getInboxDisplayName() + " <" + newInboxData.getInboxEmail() + ">");
					messageData.setReadStatus(InboxConstants.INBOX_EMAIL_STATUS_UNREAD);
					messageData.setHtmlBody(content);
					messageData.setSubject(subject);
					messageData.setTo(recruiterEmail);
					
					
					TPMailSender sender = new TPMailSender();
					sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
				}
				 catch (Exception e) {
						log.error("error while sending candidate progress notification to Recuiter", e);
					}
				
				
			}*/
			
			
			
			
			
			
			
			
			
			
			//end of send candidate current status details to Recuiter
			String hostName = CommonConstants.HOST_NAME;
			String port = TPApplicationProperties.getProperty("application.port");
			String alias = TPApplicationProperties.getProperty("application.alias");
			String documentPath = DocumentUtils.getDocumentURL(applicantData.getApplicantOriginalResumePath(), "");
			String linkToOriginalResume = "http://" + hostName + ":" + port + "/" + alias + "/" + documentPath;
			// String candidateNameWithLink = "<a href=\"" + originalResume +
			// "\">" + applicantData.getApplicantName() + "</a>";
			String candidateLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME", linkToOriginalResume);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateLink);

			// external ip link
			String externalIP = TPApplicationProperties.getProperty("application.external_IP");
			String externalLinkToOriginalResume = "http://" + externalIP + ":" + port + "/" + alias + "/" + documentPath;
			String candidateExternalLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME_EXTERNAL", externalLinkToOriginalResume);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateExternalLink);
			// get template data
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_FEEDBACK_REMINDER_TO_ASSIGNED_PERSON);
			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();

			List keys = Utils.getKeys(keyMap, ",");

			String[] ateendeesIdList = attendeesId.split(",");
			for (int i = 0; i < ateendeesIdList.length; i++) {
				try {
					String uid = ateendeesIdList[i];
					UserManager userManager = new UserManager();
					String uEmail = userManager.getUserEmail(uid);

					HashMap keyValMap = new HashMap();
					String feedbackQueryString = getFeedbackQueryString(applicantId, positionId, stepId, uid);
					String linkToFeedback = Utils.buildTalentPoolURL(feedbackQueryString);

					linkToFeedback = TemplateUtils.getConstructedToken("LINK_TO_FEEDBACK_FORM", linkToFeedback);
					String cStr = TemplateUtils.appndToToken(contentStr, linkToFeedback);

					// external link to Feedback
					String externalLinkToFeedback = Utils.buildExternalTalentPoolURL(feedbackQueryString);
					externalLinkToFeedback = TemplateUtils.getConstructedToken("LINK_TO_FEEDBACK_FORM_EXTERNAL", externalLinkToFeedback);
					cStr = TemplateUtils.appndToToken(cStr, externalLinkToFeedback);

					for (int k = 0; k < keys.size(); k++) {
						String key = (String) keys.get(k);
						key = key.trim();
						String value = TemplateUtils.getBlobDataValue(cStr, key);
						keyValMap.put(key, value);
					}
					VelocityManager velocityManager = new VelocityManager();
					String subject = velocityManager.handle(subjectVM, keyValMap);
					String content = velocityManager.handle(contentVM, keyValMap);
					// send email
					MessageData messageData = new MessageData();
					messageData.setFrom(loginData.getName() + " <" + inboxData.getInboxEmail() + ">");
					messageData.setReplyTo(loginData.getName() + " <" + loginData.getEmail() + ">");
					messageData.setHtmlBody(content);
					messageData.setSubject(subject);
					messageData.setTo(uEmail);
					TPMailSender sender = new TPMailSender();
					sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
				} catch (Exception e) {
					log.error("Unable to send email " + e);
				}
			}
		} catch (Exception e) {
			log.error("error while sending reminders for feedback", e);
		}
	}
	
	private String getFeedbackQueryString(String aId, String pId, String sId, String uId){
		StringBuilder feedbackQueryString = new StringBuilder();
		feedbackQueryString.append("selectionProcess.do?");
		feedbackQueryString.append("mode").append("=").append("showfeedback").append("&");
		feedbackQueryString.append("feedback").append("=").append("1").append("&");
		feedbackQueryString.append("applicantId").append("=").append(aId).append("&");
		feedbackQueryString.append("positionId").append("=").append(pId).append("&");
		feedbackQueryString.append("stepId").append("=").append(sId).append("&");
		feedbackQueryString.append("userId").append("=").append(uId);
		return feedbackQueryString.toString();
	}
}
