/**
 * 
 */
package com.talentPool.calendar.scheduler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.applicant.utils.ApplicantUtils;
import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.calendar.manager.CalendarManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
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
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author shivprasad
 * 
 */
public class SendRemainders implements Job {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		LoginManager loginManager = null;
		try {
			loginManager = new LoginManager();
			log.debug("Sending reminder for interview");

			String appointmentId = context.getTrigger().getJobDataMap().getString("appointmentId");
			String appointmentWith = context.getTrigger().getJobDataMap().getString("appointmentWith");

			// Check if send reminder is enabled
			boolean remindMe = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_REMINDER_TO_ME).trim().equals(GlobalConstants.ENABLED);
			boolean remindInterviewer = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_REMINDER_TO_INTERVIEWER).trim().equals(GlobalConstants.ENABLED);
			boolean remindCandidate = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_REMINDER_TO_CANDIDATE).trim().equals(GlobalConstants.ENABLED);
			boolean remindVendor = GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SEND_REMINDER_TO_VENDOR).trim().equals(GlobalConstants.ENABLED);

			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_ME)) {
				if (!remindMe) {
					log.info("send reminders disabled");
					return;
				}
			}
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER)) {
				if (!remindInterviewer) {
					log.info("send reminders disabled");
					return;
				}
			}
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE)) {
				if (!remindCandidate) {
					log.info("send reminders disabled");
					return;
				}
			}
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_VENDOR)) {
				if (!remindVendor) {
					log.info("send reminders disabled");
					return;
				}
			}

			// GET APPOINTMENT DATA
			CalendarManager calendarManager = new CalendarManager();
			AppointmentData aData = calendarManager.getAppointmentDataToEdit(appointmentId);

			// CHECK FOR MISFIRE
			Calendar cal = new GregorianCalendar();
			Date dtLogDate = cal.getTime();
			if (dtLogDate.after(aData.getAppointmentFromDateTime())) {
				log.info("Job is misfired, the appointment date is already passed");
				return;
			}
			// Construct token data
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
			
			String interview_mode=aData.getInterviewMode();
			String details_interview_mode=aData.getDetailsInterviewMode();
			
			String interview_mode_link= TemplateUtils.getConstructedToken("APPOINTMENT_INTERVIEW_MODE", Utils.isBlankOrNull(interview_mode)?"":interview_mode);
			String details_interview_mode_link= TemplateUtils.getConstructedToken("APPOINTMENT_INTERVIEW_MODE_DETAILS", Utils.isBlankOrNull(details_interview_mode)?"":details_interview_mode);
			contentStr = TemplateUtils.appndToToken(contentStr, interview_mode_link);
			contentStr = TemplateUtils.appndToToken(contentStr, details_interview_mode_link);
			
//			String appointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(), aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat);
			String appointmentStr = "@@appointmentId@@";
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
			String documentPath = DocumentUtils.getDocumentURL(applicantData.getApplicantOriginalResumePath(), "");
			String linkToOriginalResume = "http://" + hostName + ":" + port + "/" + alias + "/" + documentPath;
			// String candidateNameWithLink = "<a href=\"" + originalResume +
			// "\">" + applicantData.getApplicantName() + "</a>";
			String candidateLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME", linkToOriginalResume);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateLink);
			
//			external ip link
			String externalIP = TPApplicationProperties.getProperty("application.external_IP");
			String externalLinkToOriginalResume = "http://" + externalIP + ":" + port + "/" + alias + "/" + documentPath;
			String candidateExternalLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME_EXTERNAL", externalLinkToOriginalResume);
			contentStr = TemplateUtils.appndToToken(contentStr, candidateExternalLink);
			// get template data
			TemplateData templateData = null;
			TemplateManager templateManager = new TemplateManager();
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_ME)) {
				templateData = templateManager.getTemplateData(aData.getRemindMeTemplate());
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER)) {
				templateData = templateManager.getTemplateData(aData.getRemindInterviewerTemplate());
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE)) {
				templateData = templateManager.getTemplateData(aData.getRemindCandidateTemplate());
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_VENDOR)){
				templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_APPOINTMENT_REMINDER_VENDOR);
			}
//			String contentVM = templateData.getTemplateContentFile();
//			String subjectVM = templateData.getTemplateSubjectFile();
//			String keyMap = ;
			MessageData messageData = new MessageData();
			// messageData.setFrom(inboxData.getInboxDisplayName() + " <" +
			// inboxData.getInboxEmail() + ">");
			messageData.setFrom(loginData.getName() + " <" + inboxData.getInboxEmail() + ">");
			messageData.setReplyTo(loginData.getName() + " <" + loginData.getEmail() + ">");

			
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_ME)) {
				messageData.setTo(loginData.getEmail());
				String tempAppointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(), aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat);
				String tmpContenStr=contentStr.replace("@@appointmentId@@",tempAppointmentStr);
				composeMessageData(templateData,tmpContenStr,messageData);
				TPMailSender sender = new TPMailSender();
				sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER)) {
				ArrayList attendees = (ArrayList) calendarManager.getAppointmentAttendees(appointmentId);
				for (int i = 0; i < attendees.size(); i++) {
					try {
						SimpleDataObject interviewer = (SimpleDataObject) attendees.get(i);
						messageData.setTo(interviewer.getString("email"));
						LoginData tmpLoginData = loginManager.getUser(loginManager.getUserIdFromEmail(interviewer.getString("email")));
						String tempAppointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(), aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat,tmpLoginData.getTimeZone());
						String tmpContenStr=contentStr.replace("@@appointmentId@@",tempAppointmentStr);
						composeMessageData(templateData,tmpContenStr,messageData);
						TPMailSender sender = new TPMailSender();
						sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
					} catch (Exception e) {
						log.error("Unable to send email " + e);
					}
				}

			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE)) {
				ApplicantData emailApplicantData = applicantManager.getApplicantEmailAddress(applicantData.getApplicantId(), true);
				String emails = ApplicantUtils.getEmailToString(emailApplicantData);
				messageData.setTo(emails);
				String tempAppointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(), aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat);
				String tmpContenStr=contentStr.replace("@@appointmentId@@",tempAppointmentStr);
				composeMessageData(templateData,tmpContenStr,messageData);
				TPMailSender sender = new TPMailSender();
				sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);

			}else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_VENDOR)) {
				String vendorEmail = applicantManager.getVendorEmailAddress(applicantData.getApplicantId());				
				if(!Utils.isBlankOrNull(vendorEmail)){
					messageData.setTo(vendorEmail);
					LoginData tmpLoginData = loginManager.getUser(loginManager.getUserIdFromEmail(vendorEmail));
					String tempAppointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(), aData.getAppointmentToDateTime(), TemplateConstants.regEEEEdMMMMyyyyFormat,tmpLoginData.getTimeZone());
					String tmpContenStr=contentStr.replace("@@appointmentId@@",tempAppointmentStr);
					composeMessageData(templateData,tmpContenStr,messageData);
					TPMailSender sender = new TPMailSender();
					sender.send(messageData, InboxConstants.EMAIL_BODY_HTML, null);
				}
			}

		} catch (Exception e) {
			log.error("Error in execute", e);
		}
	}
	
	private void composeMessageData(TemplateData templateData,String contentStr,MessageData messageData){
		HashMap keyValMap = TemplateUtils.getKeyValueMap(templateData.getTemplateVariables(), contentStr);
		VelocityManager velocityManager = new VelocityManager();
		String subject = velocityManager.handle(templateData.getTemplateSubjectFile(), keyValMap);
		String content = velocityManager.handle(templateData.getTemplateContentFile(), keyValMap);
		// send email
		messageData.setHtmlBody(content);
		messageData.setSubject(subject);
	}
	
}