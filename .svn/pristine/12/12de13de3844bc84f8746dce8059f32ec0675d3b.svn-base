/**
 * 
 */
package com.talentPool.calendar.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.manager.ApplicantManager;
import com.talentPool.calendar.CalendarConstants;
import com.talentPool.calendar.dataobject.AppointmentNotificationData;
import com.talentPool.calendar.exception.DatePassedException;
import com.talentPool.calendar.manager.AppointmentNotificationManager;
import com.talentPool.common.CommonConstants;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.TPApplicationProperties;
import com.talentPool.common.utils.DateUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.documents.utils.DocumentUtils;
import com.talentPool.inbox.TPMailSender;
import com.talentPool.inbox.dataobject.InboxData;
import com.talentPool.inbox.manager.InboxManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.notifier.manager.TemplateManager;
import com.talentPool.notifier.manager.VelocityManager;
import com.talentPool.notifier.utils.TemplateUtils;
import com.talentPool.positions.dataobject.PositionData;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.timeZone.TimeZoneUtils;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;

/**
 * @author shivprasad
 * 
 */
public class SendAppointmentNotificationJob implements Job {
	private static Logger log = TPLogger.getLogger();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		LoginManager loginManager = null;
		try {
			loginManager = new LoginManager();
			log.debug("========= START SEND APPOINTMENT OUTLOOK NOTIFICATIONS =============");

			boolean notificationExist = true;
			while (notificationExist) {
				AppointmentNotificationManager appointmentNotificationManager = new AppointmentNotificationManager();
				ArrayList notifications = appointmentNotificationManager.getNotificationsToSend();
				log.debug("Total notifications to process == " + notifications.size());
				if (notifications == null || notifications.size() == 0) {
					notificationExist = false;
				} else {

					for (int i = 0; i < notifications.size(); i++) {
						AppointmentNotificationData nData = (AppointmentNotificationData) notifications.get(i);
						try {
							HashMap usersAction = getUserActionMap(nData);
							if (usersAction != null) {

								// CHECK FOR MISFIRE
								Calendar cal = new GregorianCalendar();
								Date dtLogDate = cal.getTime();
								if (dtLogDate.after(nData.getAppointmentFromDate())) {
									log.info("Job is misfired, the appointment date is already passed");
									throw new DatePassedException();
								}

								// get key value map for calendar
								HashMap calCommonMap = getCalMap(nData, usersAction);
								// Construct template data
								InboxManager inboxManager = new InboxManager();
								InboxData inboxData = inboxManager.getCurrentInboxSettings();
								String contentStr = TemplateUtils.getConvertedGlobalVars(inboxData);
								LoginData loginData = loginManager.getUser(nData.getAppointmentCreatedBy());
								String userStr = TemplateUtils.getConvertedContactData(loginData);
								contentStr = TemplateUtils.appndToToken(contentStr, userStr);
								ApplicantManager applicantManager = new ApplicantManager();
								ApplicantData applicantData = applicantManager.getApplicantData("" + nData.getApplicantId());
								String lastFeedback = applicantManager.getCandidateInterviewFeedback(applicantData.getApplicantId(), applicantData.getApplicantPositionId(), applicantData.getApplicantStepId());								
								String candidateStr = TemplateUtils.getConvertedApplicantData(applicantData, lastFeedback);
								contentStr = TemplateUtils.appndToToken(contentStr, candidateStr);
								int nType = nData.getAppointmentNotifyType();
//								Date fromDate = (nType == CalendarConstants.NOTIFY_MODIFY) ? nData.getAppointmentFromDateNew() : nData.getAppointmentFromDate();
//								Date toDate = (nType == CalendarConstants.NOTIFY_MODIFY) ? nData.getAppointmentToDateNew() : nData.getAppointmentToDate();
//								String appointmentStr = TemplateUtils.getConvertedAppointmentData(fromDate, toDate, TemplateConstants.regEEEEdMMMMyyyyFormat);
								String appointmentStr = "@@AppointmentStr@@";
								contentStr = TemplateUtils.appndToToken(contentStr, appointmentStr);
								PositionManager positionManager = new PositionManager();
								PositionData pData = positionManager.getPositionSummary("" + nData.getApplicantPositionId());
								String positionTitle = TemplateUtils.getConstructedToken("APPOINTMENT_POSITION_NAME", pData.getPositionTitle());					
								contentStr = TemplateUtils.appndToToken(contentStr, positionTitle);
								int positionStepId = nData.getApplicantStepId();
								String positionStepName = TemplateUtils.getConstructedToken("POSITION_STEP_NAME",positionManager.getPositionStepName(positionStepId));
								contentStr = TemplateUtils.appndToToken(contentStr, positionStepName);
								
								String interview_mode=nData.getInterviewMode();
								if(Utils.isBlankOrNull(interview_mode)){
									interview_mode="Not Specified";
								}
								String details_interview_mode=nData.getDetailsInterviewMode();
								if(Utils.isBlankOrNull(details_interview_mode)){
									details_interview_mode="Deatils of Interview Not available";
								}
								String interview_mode_link= TemplateUtils.getConstructedToken("APPOINTMENT_INTERVIEW_MODE", Utils.isBlankOrNull(interview_mode)?" ":interview_mode);
								String details_interview_mode_link= TemplateUtils.getConstructedToken("APPOINTMENT_INTERVIEW_MODE_DETAILS", Utils.isBlankOrNull(details_interview_mode)?"":details_interview_mode);
								contentStr = TemplateUtils.appndToToken(contentStr, interview_mode_link);
								contentStr = TemplateUtils.appndToToken(contentStr, details_interview_mode_link);
								
								String hostName = CommonConstants.HOST_NAME;
								String port = TPApplicationProperties.getProperty("application.port");
								String alias = TPApplicationProperties.getProperty("application.alias");
								String documentPath = DocumentUtils.getDocumentURL(applicantData.getApplicantOriginalResumePath(), "");
								String linkToOriginalResume = "http://" + hostName + ":" + port + "/" + alias + "/" + documentPath;
								// String candidateNameWithLink = "<a href=\"" +
								// originalResume + "\">" +
								// applicantData.getApplicantName() + "</a>";
								String candidateLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME", linkToOriginalResume);
								contentStr = TemplateUtils.appndToToken(contentStr, candidateLink);

								//external ip link
								String externalIP = TPApplicationProperties.getProperty("application.external_IP");
								String externalLinkToOriginalResume = "http://" + externalIP + ":" + port + "/" + alias + "/" + documentPath;
								String candidateExternalLink = TemplateUtils.getConstructedToken("LINK_TO_ORIGINAL_RESUME_EXTERNAL", externalLinkToOriginalResume);
								contentStr = TemplateUtils.appndToToken(contentStr, candidateExternalLink);
								// send email one by one
								Iterator it = usersAction.keySet().iterator();
								it = usersAction.keySet().iterator();
								while (it.hasNext()) {
									String uId = (String) it.next();
									int notifyState = Integer.parseInt((String) usersAction.get(uId));

									LoginData userData = loginManager.getUser(uId);
									String cStr = TemplateUtils.getConvertedUserData(userData);
									String timezone = userData.getTimeZone();
									if(Utils.isBlankOrNull(timezone)){
										timezone="Asia/Calcutta";
									}
									Date fromDate = (nType == CalendarConstants.NOTIFY_MODIFY) ? nData.getAppointmentFromDateNew() : nData.getAppointmentFromDate();
									Date toDate = (nType == CalendarConstants.NOTIFY_MODIFY) ? nData.getAppointmentToDateNew() : nData.getAppointmentToDate();
									String appointmentTime = TemplateUtils.getConvertedAppointmentData(fromDate, toDate, TemplateConstants.regEEEEdMMMMyyyyFormat,timezone);
									String tmpContentStr= contentStr.replaceAll("@@AppointmentStr@@", appointmentTime);
									cStr = TemplateUtils.appndToToken(tmpContentStr, cStr);
									TemplateData templateData = null;
									TemplateManager templateManager = new TemplateManager();
									if (notifyState == CalendarConstants.NOTIFY_ADD) {
										templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_NEW_APPOINTMENT_NOTIFICATION);
									} else if (notifyState == CalendarConstants.NOTIFY_MODIFY) {
										templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_MODIFIED_APPOINTMENT_NOTIFICATION);
									} else if (notifyState == CalendarConstants.NOTIFY_DELETE) {
										templateData = templateManager.getTemplateDataByTemplateTypeId(TemplateConstants.TEMPLATE_TYPE_CANCELLED_APPOINTMENT_NOTIFICATION);
									}
									String contentVM = templateData.getTemplateContentFile();
									String subjectVM = templateData.getTemplateSubjectFile();
									String keyMap = templateData.getTemplateVariables();
									HashMap keyValMap = TemplateUtils.getKeyValueMap(keyMap, cStr);
									VelocityManager velocityManager = new VelocityManager();
									String subject = velocityManager.handle(subjectVM, keyValMap);
									String content = velocityManager.handle(contentVM, keyValMap);
									String METHOD = "REQUEST";
									if (notifyState == CalendarConstants.NOTIFY_DELETE) {
										METHOD = "CANCEL";
									}
									int remindTime = (uId.equals(loginData.getUserId())) ? nData.getRemindMe() : nData.getRemindInterviewer();
									String ALARAM = getAlarmCalString(remindTime, "Interview reminder");
									calCommonMap.put("ALARAM", ALARAM);
									calCommonMap.put("METHOD", METHOD);
									calCommonMap.put("SUMMARY", subject);
									String calContent = velocityManager.handle("iCalendar.vm", calCommonMap);
									calContent = limitCalTextTo75(calContent);
									log.debug("sending email");
									String from = loginData.getName() + " <" + inboxData.getInboxEmail() + ">";
									String inReplyTo = loginData.getName() + " <" + loginData.getEmail() + ">";
									TPMailSender sender = new TPMailSender();
									sender.sendAppointment(userData.getEmail(), from, subject, content, calContent, nData.getNotificationId(), inReplyTo, fromDate, toDate);

								}
							}
						} catch (DatePassedException dp) {
							// Don't do anything just mark as done
						} catch (Exception e) {
							log.error("ERROR", e);
						}
						appointmentNotificationManager.updateNotificationStatus(nData.getNotificationId(), CalendarConstants.NOTIFICATION_PROCESSED);
					}
					notificationExist = false;
				}
			}
			log.debug("========= OUTLOOK NOTIFICATIONS =============");
		} catch (Exception e) {
			log.error("Error in send appointment notifications", e);
		}
	}

	private String limitCalTextTo75(String calContent) {
		StringBuffer sb = new StringBuffer();
		String[] lines = calContent.split("\n");
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if (line.length() < 76) {
				sb.append(line + "\n");
			} else {
				while (line.length() > 75) {
					sb.append(line.subSequence(0, 75) + "\n");
					line = " " + line.substring(75);
				}
				sb.append(line + "\n");
			}
		}
		return sb.toString();
	}

	private Date getAbsoluteUTCDate(Date srcDate) {
		try {
			Calendar cal = new GregorianCalendar();
			cal.setTime(srcDate);
			cal.add(Calendar.MILLISECOND, 0 - (cal.get(Calendar.ZONE_OFFSET)));
			return cal.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private HashMap getCalMap(AppointmentNotificationData nData, HashMap usersAction) throws Exception {
		HashMap calCommonMap = new HashMap();
		int notifyType = nData.getAppointmentNotifyType();

		Date fromDate = getAbsoluteUTCDate(nData.getAppointmentFromDate());
		Date fromDateNew = getAbsoluteUTCDate(nData.getAppointmentFromDateNew());
		Date toDate = getAbsoluteUTCDate(nData.getAppointmentToDate());
		Date toDateNew = getAbsoluteUTCDate(nData.getAppointmentToDateNew());

		String DTSTART = getDateToCalFormat((notifyType == CalendarConstants.NOTIFY_MODIFY) ? fromDateNew : fromDate);
		String DTEND = getDateToCalFormat((notifyType == CalendarConstants.NOTIFY_MODIFY) ? toDateNew : toDate);
		Calendar cal = new GregorianCalendar();

		String DTSTAMP = getDateToCalFormat(getAbsoluteUTCDate(cal.getTime()));
		String SEQUENCE = "" + nData.getAppointmentSequenceNo();
		String CREATED = getDateToCalFormat(getAbsoluteUTCDate(nData.getAppointmentDateCreated()));
		String LASTMODIFIED = getDateToCalFormat(getAbsoluteUTCDate(nData.getAppointmentDateModified()));
		String DESCRIPTION = "Please login into talentpool to view event details";
		String SUMMARY = nData.getAppointmentSubject();

		LoginManager loginManager = new LoginManager();

		String ownerId = nData.getAppointmentCreatedBy();
		LoginData ownerData = loginManager.getUser(ownerId);
		String ORGANIZER = "MAILTO:" + ownerData.getEmail();

		// Construct attendees
		StringBuffer sb = new StringBuffer();
		Iterator it = usersAction.keySet().iterator();
		while (it.hasNext()) {
			String uId = (String) it.next();
			if (uId.equals(ownerId)) {
				sb.append("ATTENDEE;CUTYPE=INDIVIDUAL;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE;");
				sb.append("X-NUM-GUESTS=0:MAILTO:" + ownerData.getEmail() + "\n");
			} else {
				LoginData attendeeData = loginManager.getUser(uId);
				sb.append("ATTENDEE;CUTYPE=INDIVIDUAL;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE;");
				sb.append("X-NUM-GUESTS=0:MAILTO:" + attendeeData.getEmail() + "\n");
			}
		}
		String ATTENDEE = sb.toString();
		ATTENDEE = ATTENDEE.substring(0, ATTENDEE.length() - 1);

		String STATUS = "CONFIRMED";
		String METHOD = "REQUEST"; // CANCEL
		String UID = nData.getAppointmentId() + "-" + nData.getAppointmentDateCreated().getTime() + "-" + ownerId;
		// Construct common variables string for all
		calCommonMap.put("PRODID", "-//Talentpool Calender//EN");
		calCommonMap.put("METHOD", METHOD);
		calCommonMap.put("DTSTART", DTSTART + "Z");
		calCommonMap.put("DTEND", DTEND + "Z");
		calCommonMap.put("DTSTAMP", DTSTAMP + "Z");
		calCommonMap.put("ORGANIZER", ORGANIZER);
		calCommonMap.put("ATTENDEE", ATTENDEE);
		calCommonMap.put("SEQUENCE", SEQUENCE);
		calCommonMap.put("UID", UID);
		calCommonMap.put("CREATED", CREATED + "Z");
		calCommonMap.put("DESCRIPTION", DESCRIPTION);
		calCommonMap.put("LAST-MODIFIED", LASTMODIFIED + "Z");
		calCommonMap.put("STATUS", STATUS);
		calCommonMap.put("SUMMARY", SUMMARY);
		return calCommonMap;
	}

	private String getAlarmCalString(int TRIGGER, String RDESCRIPTION) {
		String ALARAM = "";
		StringBuffer sb = new StringBuffer();
		if (TRIGGER > 0) {
			String strTrigger = "-PT" + TRIGGER + "M";
			sb.append("\nBEGIN:VALARM\n");
			sb.append("TRIGGER:" + strTrigger + "\n");
			sb.append("ACTION:DISPLAY\n");
			sb.append("DESCRIPTION:" + RDESCRIPTION + "\n");
			sb.append("END:VALARM\n");
			ALARAM = sb.toString();
		}
		return ALARAM;
	}

	public String getDateToCalFormat(Date dt) {
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String f = format.format(dt);
			format = new SimpleDateFormat("HHmmss");
			f += "T" + format.format(dt);
			return f;
		} catch (Exception e) {
			TPLogger.getLogger().error("");
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @param aData
	 *            as appointmentNotification data
	 * @return HashMap of userId and the action to be taken ADD/MODIFY/DELETE
	 */
	public HashMap getUserActionMap(AppointmentNotificationData aData) {
		HashMap usersAction = new HashMap();
		String attendee = Utils.isBlankOrNull(aData.getAppointmentAttendee()) ? "" : aData.getAppointmentAttendee();
		String attendeeNew = Utils.isBlankOrNull(aData.getAppointmentAttendeeNew()) ? "" : aData.getAppointmentAttendeeNew();
		String[] users = attendee.split(",");
		ArrayList listUsers = new ArrayList(Arrays.asList(users));
		ArrayList listUsersNew = new ArrayList();
		if (!Utils.isBlankOrNull(attendeeNew)) {
			String[] newUsers = attendeeNew.split(",");
			listUsersNew = new ArrayList(Arrays.asList(newUsers));
		}
		String ownerId = aData.getAppointmentCreatedBy();
		if (!listUsers.contains(ownerId)) {
			listUsers.add(ownerId);
		}
		if (!listUsersNew.contains(ownerId)) {
			listUsersNew.add(ownerId);
		}

		if (aData.getAppointmentNotifyType() == CalendarConstants.NOTIFY_ADD || aData.getAppointmentNotifyType() == CalendarConstants.NOTIFY_DELETE) {
			for (int i = 0; i < listUsers.size(); i++) {
				usersAction.put(listUsers.get(i), "" + aData.getAppointmentNotifyType());
			}
		} else {
			// modified appointment
			boolean timeModified = false;
			boolean attendeeModified = false;
			if (!aData.getAppointmentFromDate().equals(aData.getAppointmentFromDateNew()) || !aData.getAppointmentToDate().equals(aData.getAppointmentToDateNew())) {
				// nothing is modified send null
				timeModified = true;
			}

			for (int i = 0; i < listUsersNew.size(); i++) {
				String uId = (String) listUsersNew.get(i);
				if (listUsers.indexOf(uId) >= 0) {
					if (timeModified) {
						usersAction.put(uId, "" + CalendarConstants.NOTIFY_MODIFY);
					}
				} else {
					usersAction.put(uId, "" + CalendarConstants.NOTIFY_ADD);
					attendeeModified = true;
				}
			}
			for (int i = 0; i < listUsers.size(); i++) {
				String uId = (String) listUsers.get(i);
				if (listUsersNew.indexOf(uId) < 0) {
					usersAction.put(uId, "" + CalendarConstants.NOTIFY_DELETE);
					attendeeModified = true;
				}
			}
			if (!attendeeModified && !timeModified) {
				// nothing is modified send null
				usersAction = null;
			}
		}
		return usersAction;
	}
	
}
