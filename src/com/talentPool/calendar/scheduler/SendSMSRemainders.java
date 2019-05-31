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
import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.calendar.manager.CalendarManager;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.scheduler.SchedulerConstants;
import com.talentPool.sms.SMSGateway;
import com.talentPool.sms.SMSGatewayImpl;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author pallavi
 * 
 */
public class SendSMSRemainders implements Job {
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
			String appointmentStr = TemplateUtils.getConvertedAppointmentData(aData.getAppointmentFromDateTime(), aData.getAppointmentToDateTime(), TemplateConstants.regEEEdMMMyyyyhhmmaaaFormat);
			contentStr = TemplateUtils.appndToToken(contentStr, appointmentStr);
			PositionManager positionManager = new PositionManager();
			PositionData pData = positionManager.getPositionSummary("" + aData.getApplicantPositionId());
			String positionTitle = TemplateUtils.getConstructedToken("APPOINTMENT_POSITION_NAME", pData.getPositionTitle());
			contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
			int positionStepId = aData.getApplicantStepId();
			String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME",positionManager.getPositionStepName(positionStepId));
			contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
			
			String interview_mode=aData.getInterviewMode();
			String details_interview_mode=aData.getDetailsInterviewMode();
			
			String interview_mode_link= TemplateUtils.getConstructedToken("APPOINTMENT_INTERVIEW_MODE", Utils.isBlankOrNull(interview_mode)?"":interview_mode);
			String details_interview_mode_link= TemplateUtils.getConstructedToken("APPOINTMENT_INTERVIEW_MODE_DETAILS", Utils.isBlankOrNull(details_interview_mode)?"":details_interview_mode);
			contentStr = TemplateUtils.appndToToken(contentStr, interview_mode_link);
			contentStr = TemplateUtils.appndToToken(contentStr, details_interview_mode_link);
			
			
			// get template data
			TemplateData templateData = null;
			TemplateManager templateManager = new TemplateManager();
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_ME)) {
				templateData = templateManager.getTemplateData(aData.getSMSRemindMeTemplate());
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER)) {
				templateData = templateManager.getTemplateData(aData.getSMSRemindInterviewerTemplate());
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE)) {
				templateData = templateManager.getTemplateData(aData.getSMSRemindCandidateTemplate());
			}
			String contentVM = templateData.getTemplateContentFile();
			String keyMap = templateData.getTemplateVariables();
			HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, contentStr);
			
			VelocityManager velocityManager = new VelocityManager();
			String content = velocityManager.handle(contentVM, keyValMap);
			
			SMSGateway gateway = new SMSGatewayImpl();
			if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_ME)) {
				gateway.send(content, loginData.getCellPhone(), loginData.getCellPhone());				
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_INTERVIEWER)) {
				ArrayList attendees = (ArrayList) calendarManager.getAppointmentAttendees(appointmentId);
				for (int i = 0; i < attendees.size(); i++) {	
					SimpleDataObject interviewer = (SimpleDataObject) attendees.get(i);
					gateway.send(content, interviewer.getString("cellPhone"), loginData.getCellPhone());					
				}
			} else if (appointmentWith.equals(SchedulerConstants.TRIGGER_APPOINTMENT_CANDIDATE)) {
				gateway.send(content, applicantData.getApplicantCellPhone(), loginData.getCellPhone());
			}
		} catch (Exception e) {
			log.error("Error in execute", e);
		}
	}

}