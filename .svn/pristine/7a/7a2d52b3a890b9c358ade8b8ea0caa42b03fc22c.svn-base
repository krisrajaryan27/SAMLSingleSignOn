package com.talentPool.calendar.scheduler;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.calendar.manager.CalendarManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
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
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.UserManager;

public class SendFeedbackRemainders implements Job {
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("Sending reminder for interviewer to submit feedback");
		LoginManager loginManager = null;
		try {
			loginManager = new LoginManager();
			String appointmentId = context.getTrigger().getJobDataMap().getString("appointmentId");
			String attendeesId = context.getTrigger().getJobDataMap().getString("attendeesId");
			
			// GET APPOINTMENT DATA
			CalendarManager calendarManager = new CalendarManager();
			AppointmentData aData = calendarManager.getAppointmentDataToEdit(appointmentId);

			InboxManager inboxManager = new InboxManager();
			InboxData inboxData = inboxManager.getCurrentInboxSettings();
			String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
			LoginData loginData = loginManager.getUser(aData.getAppointmentCreatedBy());
			String userStr = TemplateUtils.getConvertedContactData(loginData);
			contentStr = TemplateUtils.appndToToken(contentStr, userStr);
			ApplicantManager applicantManager = new ApplicantManager();
			ApplicantData applicantData = applicantManager.getApplicantData("" + aData.getApplicantId());
			String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());
			String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
//			String appointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(),aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat);
			String appointmentStr = "@@AppointmentStr@@";
			contentStr = TemplateUtils.appndToToken(contentStr, appointmentStr);
			PositionManager positionManager = new PositionManager();
			PositionData pData = positionManager.getPositionSummary("" + aData.getApplicantPositionId());
			String positionTitle = TemplateUtils.getConstructedToken("APPOINTMENT_POSITION_NAME", pData.getPositionTitle());
			contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
			int positionStepId = aData.getApplicantStepId();
			String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME",positionManager.getPositionStepName(positionStepId));
			contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
			
			String hostName = CommonConstants.HOST_NAME;
			String port = TPApplicationProperties.getProperty("application.port");
			String alias = TPApplicationProperties.getProperty("application.alias");
			String documentPath = DocumentUtils.getDocumentURL(applicantData.getApplicantOriginalResumePath(),"");
			String linkToOriginalResume = "http://" + hostName + ":" + port + "/" + alias + "/" + documentPath;
			String candidateLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME", linkToOriginalResume);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateLink);

//			external ip link
			String externalIP = TPApplicationProperties.getProperty("application.external_IP");
			String externalLinkToOriginalResume = "http://" + externalIP + ":" + port + "/" + alias + "/" + documentPath;
			String candidateExternalLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME_EXTERNAL", externalLinkToOriginalResume);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateExternalLink);
			// get template data
			TemplateManager templateManager = new TemplateManager();
			TemplateData templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_FEEDBACK_REMINDER_INTERVIEWER);
			String contentVM = templateData.getTemplateContentFile();
			String subjectVM = templateData.getTemplateSubjectFile();
			String keyMap = templateData.getTemplateVariables();

			List<String> keys = Utils.getKeys(keyMap, ",");
			
			String[] ateendeesIdList = attendeesId.split(",");
			for (int i = 0; i < ateendeesIdList.length; i++) {
				try {
					String uid = ateendeesIdList[i];
					UserManager userManager = new UserManager();
					String uEmail = userManager.getUserEmail(uid);
					
					HashMap<String, String> keyValMap = new HashMap<String, String>();
					String feedbackQueryString = getFeedbackQueryString(aData.getApplicantId(),aData.getApplicantPositionId(),aData.getApplicantStepId(),uid);
					
					String linkToFeedback = Utils.buildTalentPoolURL(feedbackQueryString);

					linkToFeedback = TemplateUtils.getConstructedToken("LINK_TO_FEEDBACK_FORM", linkToFeedback);
					LoginData tmpLoginData = loginManager.getUser(uid);
					String timezone = tmpLoginData.getTimeZone();
					String appointmentTime = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(),aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat,timezone);
					String tmpContentStr= contentStr.replaceAll("@@AppointmentStr@@", appointmentTime);
					String cStr = TemplateUtils.appndToToken(tmpContentStr, linkToFeedback);
					
					//external link to Feedback
					String externalLinkToFeedback = Utils.buildExternalTalentPoolURL(feedbackQueryString);
					externalLinkToFeedback = TemplateUtils.getConstructedToken("LINK_TO_FEEDBACK_FORM_EXTERNAL", externalLinkToFeedback);
					cStr = TemplateUtils.appndToToken(cStr, externalLinkToFeedback);
					
					for (int k = 0; k < keys.size(); k++) {
						String key = keys.get(k);
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
	
	private String getFeedbackQueryString(int aId, int pId, int sId, String uId){
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
