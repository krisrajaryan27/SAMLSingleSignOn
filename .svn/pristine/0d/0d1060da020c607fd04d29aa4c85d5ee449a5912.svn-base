/*
 * Created on Jun 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.calendar.manager;

import java.io.StringWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.calendar.CalendarConstants;
import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.calendar.dataobject.TimeData;
import com.talentPool.calendar.dataobject.UserData;
import com.talentPool.calendar.scheduler.CalendarScheduler;
import com.talentPool.calendar.scheduler.SMSScheduler;
import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.constants.DateConstants;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.DBQuery;
import com.talentPool.common.db.DBTransaction;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.common.xmlutils.XMLWriter;
import com.talentPool.latestActivity.manager.LatestActivityManager;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.notifier.dataobject.TemplateData;
import com.talentPool.positions.PositionConstants;
import com.talentPool.positions.manager.PositionManager;
import com.talentPool.positions.utils.PositionWithRightsClause;
import com.talentPool.repository.TPIndexEvent;
import com.talentPool.repository.TPIndexEventQueue;
import com.talentPool.selectionProcess.SelectionProcessConstants;
import com.talentPool.selectionProcess.manager.SelectionProcessManager;
import com.talentPool.timeZone.TimeZoneUtils;
import com.talentPool.todo.manager.ToDoManager;
import com.talentPool.user.UserConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;

/**
 * @author pallavi
 * @date Jun 23, 2006
 */
@Component
public class CalendarManager {
	/**
	 * Method to get appointments for current week.
	 * 
	 * @param weekDays
	 *            The comma separated string containing the first day in next week in addition to
	 *            days in selected week.
	 * @param userId
	 *            The identifier of the logged in user.
	 * @param userRoles
	 *            The role identifier of the logged in user.
	 * @return The map containing date as key and list of appointments on that date as value.
	 */
	public Map getAppointmentsForWeekDays(String weekDays, String userId, String userRoles, 
			PermissionSet permissionSet, boolean doFetchReminders, String showMyAppointments) {
		DBPreparedQuery dq = null;
		Map appointments = new LinkedHashMap();
		List result = null;
		try {
			if (weekDays != null) {
				String[] days = weekDays.split(",");
				String[] dynParams = new String[4];
				if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
					dynParams[0] = " tpos.position_code ";
				} else {
					dynParams[0] = " tpos.position_title ";
				}
				
				dynParams[1] = " ";
				ArrayList<String> dynamicContent1 = new ArrayList<String>();
				if(GlobalConstants.ENABLED.equals(showMyAppointments)){
					dynParams[1] = "JOIN tp_appointment_attendees AS tatt ON (tatt.appointment_id = tpa.appointment_id AND tatt.attendee_id=?)";
					dynamicContent1.add(userId);
				}
				int roleId = Integer.parseInt(userRoles);

				ArrayList<String> dynamicContent2 = new ArrayList<String>();
				dynParams[2] = " ";
				if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
					dynParams[2] = PositionWithRightsClause.getClauseForStepUserAndRequestedByAndRequisitionApprovalAndAppointmentAttendee(userId, "tpa.position_id", dynamicContent2, permissionSet);
				} else if (UserConstants.ROLE_INTERVIEWER == roleId) {
					dynParams[2] = " and tpa.appointment_id in (select appointment_id from tp_appointment_attendees where attendee_id = ?) ";
					dynamicContent2.add(userId);
				} 
				
				if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
					dynParams[2] += " AND ta.is_confidential = ? ";
					dynamicContent2.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
				}
				
				if(GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_REMINDER)) && doFetchReminders) {
					dynParams[3] = " union " +
									" (select tpr.reminder_id as appointment_id, tpr.applicant_id, ta.applicant_name, " +
									" tpr.reminder_date as appointment_from_date, NULL as appointment_to_date, tpr.reminder_desc as subject, '' as appointment_created_by, " + 
									" '' as position_id,'' as position_title,'' as applicant_original_resume_path, '' as source_title, ? as type, " +
									" '' as status_message, " +" '' as interview_mode ," +" '' as details_interview_mode " +
									" from tp_reminder tpr left join tp_applicants ta on (tpr.applicant_id = ta.applicant_id) " +
									" where tpr.user_id = ? and tpr.reminder_date > ? " + 
									" AND tpr.reminder_date < ? ) ";
					dynamicContent2.add(CalendarConstants.CALENDAR_ITEM_REMINDER);
					dynamicContent2.add(userId);
					dynamicContent2.add("");
					dynamicContent2.add("");
				} else {
					dynParams[3] = "";
				}
				for (int i = 0; i < days.length - 1; i++) {
					dq = new DBPreparedQuery("dGetAppointmentData", dynParams);
					int cnt = 1;
					dq.setString(cnt++, CalendarConstants.CALENDAR_ITEM_APPOINTMENT);
					for (int k = 0; k < dynamicContent1.size(); k++) {
						dq.setString(cnt++, dynamicContent1.get(k));
					}
					dq.setString(cnt++, days[i]);
					dq.setString(cnt++, days[i + 1]);
					
					if(!Utils.isBlankOrNull(dynParams[3])) {
						dynamicContent2.set(dynamicContent2.size() - 2, days[i]);				
						dynamicContent2.set(dynamicContent2.size() - 1, days[i + 1]);
					}					
					for (int k = 0; k < dynamicContent2.size(); k++) {
						dq.setString(cnt++, dynamicContent2.get(k));
					}
					result = dq.getResult();
					appointments.put(days[i], result);
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting appointments for current week", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return appointments;
	}

	/**
	 * Method to get the appointments in XML file format.
	 * 
	 * @param appointments
	 *            The map containing date as key and list of appointments on that date as value.
	 * @param userId
	 *            The logged-in user's identifier.
	 * @param taskBits
	 *            The permission bit set.
	 * @param userRoles
	 *            The role identifier of the logged in user.
	 * @return String representation of the contents of XML file.
	 * @throws ParseException 
	 */
	public String getAppointmentsInXml(Map appointments, String userId, PermissionSet permissionSet, String userRoles) throws ParseException {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		Date now = new Date();
		
		DateFormat userTimeZoneformatter= new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		TimeZone userTimeZone = TimeZone.getTimeZone(MyThreadLocal.get().getTimeZone());
		userTimeZoneformatter.setTimeZone(userTimeZone);
		
		
		
		DateFormat defaultTimeZoneFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		
		
		try {
			wr.startDocument();
			wr.startElement("week");
				if (appointments != null && appointments.size() > 0) {
					Iterator itr = appointments.keySet().iterator();
					while (itr.hasNext()) {
						String key = (String) itr.next();
						List value = (List) appointments.get(key);
						wr.startElement("day");
						for (int indx = 0; indx < value.size(); indx++) {
							AppointmentData data = (AppointmentData) value.get(indx);
							
							
							
							
							AttributesImpl atr = new AttributesImpl();
							atr.addAttribute("", "id", "", "", String.valueOf(data.getAppointmentId()));
							
							Date appointmentDate = userTimeZoneformatter.parse(defaultTimeZoneFormatter.format(data.getAppointmentFrom()));
							
							if (now.before(appointmentDate) && permissionSet.isPERMISSION_SCHEDULE_INTERVIEW()) {
								atr.addAttribute("", "isFullyEditable", "", "", Boolean.TRUE.toString());
							} else {
								atr.addAttribute("", "isFullyEditable", "", "", Boolean.FALSE.toString());
							}

							wr.startElement("", "appointment", "", atr);

							wr.startElement("applicantId");
							wr.characters(String.valueOf(data.getApplicantId()));
							wr.endElement("applicantId");
							
							String applicantName = (data.getApplicantName() == null) ? "" : data.getApplicantName();
							wr.startElement("name");
							wr.characters(wr.doubleEscape(applicantName));
							wr.endElement("name");

							String path = (data.getApplicantOriginalResumePath() == null) ? "" : data.getApplicantOriginalResumePath();
							wr.startElement("path");
							wr.characters(path);
							wr.endElement("path");

							String subject = (data.getAppointmentSubject() == null) ? "" : data.getAppointmentSubject();
							wr.startElement("subject");
							wr.characters(wr.doubleEscape(subject));
							wr.endElement("subject");

							String position = (data.getApplicantPositionTitle() == null) ? "" : data.getApplicantPositionTitle();
							wr.startElement("position");
							wr.characters(wr.doubleEscape(position));
							wr.endElement("position");
							
							String interviewer = (CalendarConstants.CALENDAR_ITEM_APPOINTMENT.equalsIgnoreCase(data.getType())) ? getAppointmentAttendeesAsString(data.getAppointmentId()) : "";
							wr.startElement("interviewer");
							wr.characters(interviewer);
							wr.endElement("interviewer");

							wr.startElement("from");
							wr.characters(data.getAppointmentFromTimeToDisplay());
							wr.endElement("from");
							
							wr.startElement("to");
							wr.characters(data.getAppointmentToTimeToDisplay());
							wr.endElement("to");

							String status = (data.getStatus() == null) ? "" : data.getStatus();
							wr.startElement("status");
							wr.characters(wr.doubleEscape(status));
							wr.endElement("status");
							
							wr.startElement("type");
							wr.characters(data.getType());
							wr.endElement("type");
							String interviewMode=(data.getInterviewMode() == null) ? "" : data.getInterviewMode();
							wr.startElement("interviewMode");
							wr.characters(wr.doubleEscape(interviewMode));
							wr.endElement("interviewMode");
							String detailsInterviewMode=(data.getDetailsInterviewMode() == null) ? "" : data.getDetailsInterviewMode();
							wr.startElement("detailsInterviewMode");
							wr.characters(wr.doubleEscape(detailsInterviewMode));
							wr.endElement("detailsInterviewMode");
							
							wr.endElement("appointment");							
						}
						wr.endElement("day");
					}
				}
			wr.endElement("week");
			wr.endDocument();

		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while creating xml file for appointments", e);
		}

		return sWr.toString();
	}

	/**
	 * Method to get the interviewers for appointment identified by appointmentId.
	 * 
	 * @param appointmentId
	 *            The appointment identifier.
	 * @return The comma separated string of the names of interviewers.
	 */
	public String getAppointmentAttendeesAsString(String appointmentId) {
		StringBuffer attendees = new StringBuffer();
		List users = getAppointmentAttendees(appointmentId);
		if (users != null && users.size() > 0) {
			for (int i = 0; i < users.size(); i++) {
				UserData data = (UserData) users.get(i);
				if (attendees.length() > 0) {
					attendees.append(", ");
				}
				attendees.append(data.getUserName());
			}
		}
		return attendees.toString();
	}

	/**
	 * Method to get the list of attendees for appointment identified by appointmentId.
	 * 
	 * @param appointmentId
	 *            The appointment identifier.
	 * @return The list containing the name and identifier of the interviewers.
	 */
	public List getAppointmentAttendees(String appointmentId) {
		DBPreparedQuery dq = null;
		List users = null;
		try {
			dq = new DBPreparedQuery("dGetAppointmentAttendees");
			dq.setString(1, appointmentId);
			users = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting appointment attendees", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return users;
	}

	/**
	 * Method to get the all open positions.
	 * 
	 * @param userId
	 *            The logged in user'd id.
	 * @param userRoles
	 *            The role of the logged-in user.
	 * @return The list containing the all open positions.
	 */
	public List getAllOpenPositions(String userId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		List positions = null;
		String[] dynaParam = new String[2];
		try {
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynaParam[0] = " position_code ";
			} else {
				dynaParam[0] = " position_title ";
			}
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynaParam[1] = "";
			if (permissionSet.isSHOW_POSITIONS_WITH_RIGHTS()) {
				dynaParam[1] = " and position_id in (select su.position_id from tp_position_step_users su, tp_position_steps ps where su.position_step_id = ps.position_step_id and ps.position_step_status = ? "
						+ " and su.user_id = ? union select position_id from tp_positions where position_requested_by = ? "
						+ " UNION SELECT distinct traf.position_id FROM tp_requisition_approval_feedback traf WHERE traf.by_user_id=? OR traf.to_user_id=?)";
				dynamicContent.add(PositionConstants.STEP_ACTIVE);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
				dynamicContent.add(userId);
			}
			dq = new DBPreparedQuery("dGetOpenPositions", dynaParam);
			dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
			int cnt = 2;
			for (int i = 0; i < dynamicContent.size(); i++) {
				dq.setString(cnt++, dynamicContent.get(i));
			}
			positions = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting open positions", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return positions;
	}

	/**
	 * Method to get the applicants in process for a position.
	 * 
	 * @param positionId
	 *            The identifier of the position.
	 * @return The list containing applicants in process for a position.
	 */
	public List getApplicantsInProcess(String positionId, PermissionSet permissionSet) {
		DBPreparedQuery dq = null;
		List applicants = null;
		try {
			String dynaParams[] = new String[1];
			ArrayList<String> dynamicContent = new ArrayList<String>();
			dynaParams[0] = "";
			if (positionId != null && positionId.length() > 0) {
				dynaParams[0] += " and ta.applicant_position_id = ? ";
				dynamicContent.add(positionId);
			}
			
			if (permissionSet.isDO_NOT_SHOW_CONFIDENTIAL_PROFILE()) {
				dynaParams[0] += " AND ta.is_confidential = ? ";
				dynamicContent.add(ApplicantConstants.APPLICANT_NOT_CONFIDENTIAL);
			}
			
			dq = new DBPreparedQuery("dApplicantsInProcess", dynaParams);
			dq.setString(1, PositionConstants.POSITION_STATUS_OPENED);
			dq.setInt(2, PositionConstants.STEP_SCHEDULED);
			int cnt = 3;
			for (int k = 0; k < dynamicContent.size(); k++) {
				dq.setString(cnt++, dynamicContent.get(k));
			}
			applicants = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting applicants in process", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return applicants;
	}

	/**
	 * Method to create xml file for applicants in process for a position.
	 * 
	 * @param applicants
	 *            The applicants in process for a position.
	 * @return The applicant data in XML for applicants in process.
	 */
	public String getApplicantXml(List applicants) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("applicants");
			if (applicants != null && applicants.size() > 0) {
				Iterator itr = applicants.iterator();
				while (itr.hasNext()) {
					AppointmentData data = (AppointmentData) itr.next();

					AttributesImpl atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(data.getApplicantId()));
					wr.startElement("", "applicant", "", atr);
					String applicantName = (data.getApplicantName() == null) ? "" : data.getApplicantName();
					wr.characters(applicantName);
					wr.endElement("applicant");
				}
			}
			wr.endElement("applicants");
		} catch (SAXException e) {
			TPLogger.getLogger().error("Error while generating the xml file for applicants in process", e);
		}
		return sWr.toString();
	}

	/**
	 * Initialise the appointment data for candidate identified by applicantId.
	 * 
	 * @param applicantId
	 *            The identifier of the candidate.
	 * @return The initial data in XML file to set the appointment.
	 */
	public String initAppointmentData(String applicantId, String userTimeZone) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			AppointmentData data = getApplicantSelectionStatusInfo(applicantId);
			List interviewers = getPositionInterviewers(String.valueOf(data.getApplicantStepId()), "");
			List notifications = getNotifications();
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List status = selectionProcessManager.getStatusMessages(applicantId);

			wr.startDocument();
			wr.startElement("setAppointmentData");

			// Candidate
			wr.startElement("candidate");
			String candidate = (data.getApplicantName() == null) ? "" : data.getApplicantName();
			wr.characters(candidate);
			wr.endElement("candidate");

			// Position
			AttributesImpl atr = new AttributesImpl();
			atr.addAttribute("", "id", "", "", String.valueOf(data.getApplicantPositionId()));
			wr.startElement("", "position", "", atr);
			String position = (data.getApplicantPositionTitle() == null) ? "" : data.getApplicantPositionTitle();
			wr.characters(position);
			wr.endElement("position");

			// Step
			atr = new AttributesImpl();
			atr.addAttribute("", "id", "", "", String.valueOf(data.getApplicantStepId()));
			wr.startElement("", "step", "", atr);
			String step = (data.getApplicantStepTitle() == null) ? "" : data.getApplicantStepTitle();
			wr.characters(step);
			wr.endElement("step");

			// Interviewers
			wr.startElement("interviewers");
			if (interviewers != null && interviewers.size() > 0) {
				for (int i = 0; i < interviewers.size(); i++) {
					LoginData interviewer = (LoginData) interviewers.get(i);
					atr = new AttributesImpl();
					atr.addAttribute("", "id", "", "", String.valueOf(interviewer.getUserId()));
					wr.startElement("", "interviewer", "", atr);
					String userName = (interviewer.getName() == null) ? "" : interviewer.getName();
					wr.characters(userName);
					wr.endElement("interviewer");
				}
			}
			wr.endElement("interviewers");

			// Notifications
			wr.startElement("notifications");
			if (notifications != null && notifications.size() > 0) {
				for (int i = 0; i < notifications.size(); i++) {
					TimeData notification = (TimeData) notifications.get(i);
					atr = new AttributesImpl();
					atr.addAttribute("", "value", "", "", String.valueOf(notification.getNumberOfMinutes()));
					wr.startElement("", "notification", "", atr);
					wr.characters(notification.getDescription());
					wr.endElement("notification");
				}
			}
			wr.endElement("notifications");
			
			wr.startElement("timeZoneInfo");
			if (userTimeZone != null) {
					atr = new AttributesImpl();
					atr.addAttribute("", "value", "", "", userTimeZone);
					atr.addAttribute("", "checked", "", "", "checked");
					wr.startElement("", "timeZone", "", atr);
					wr.characters(userTimeZone);
					wr.endElement("timeZone");
			}
			wr.endElement("timeZoneInfo");

			// Status
			wr.startElement("statusInfo");
			if (status != null && status.size() > 0) {
				for (int i = 0; i < status.size(); i++) {
					SimpleDataObject statusObj = (SimpleDataObject) status.get(i);
					atr = new AttributesImpl();
					atr.addAttribute("", "value", "", "", statusObj.getString("messageId"));
					if (statusObj.getString("message") != null && statusObj.getString("message").equalsIgnoreCase(statusObj.getString("selectedMessage"))) {
						atr.addAttribute("", "checked", "", "", "checked");
					}
					wr.startElement("", "status", "", atr);
					wr.characters(statusObj.getString("message"));
					wr.endElement("status");
				}
			}
			wr.endElement("statusInfo");

			wr.endElement("setAppointmentData");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while initialising appointment data", e);
		}
		return sWr.toString();
	}

	/**
	 * Method to get the status of selection process for candidate identified by applicantId.
	 * 
	 * @param applicantId
	 *            The identifier of the candidate.
	 * @return information of the status of the selection process for candidate identified by
	 *         applicantId.
	 */
	public AppointmentData getApplicantSelectionStatusInfo(String applicantId) {
		DBPreparedQuery dq = null;
		AppointmentData data = null;
		try {
			String[] dynParam = new String[1];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParam[0] = " tpos.position_code ";
			} else {
				dynParam[0] = " tpos.position_title ";
			}			
			dq = new DBPreparedQuery("dGetApplicantSelectionStatusInfo", dynParam);
			dq.setString(1, applicantId);
			data = (AppointmentData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting selection status info of the applicant", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	/**
	 * Method to get the interviewers for position identified by positionId.
	 * 
	 * @param stepId
	 *            The step identifier.
	 * @param appointmentId
	 *            The appointment identifier.
	 * @return The list of interviewers for position identified by positionId.
	 */
	public List getPositionInterviewers(String stepId, String appointmentId) {
		DBPreparedQuery dq = null;
		List interviewers = null;
		try {
			dq = new DBPreparedQuery("dGetPositionInterviewers");
			dq.setString(1, stepId);
			dq.setString(2, "" + UserConstants.ACTIVE);
			dq.setInt(3, PositionConstants.NOT_RESPONSIBLE_FOR_SCHEDULING);
			dq.setString(4, appointmentId);
			interviewers = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting selection status info of the applicant", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return interviewers;
	}

	/**
	 * Method to get the notifications master list.
	 * 
	 * @return The master list containing the notification data.
	 */
	private List getNotifications() {
		DBPreparedQuery dq = null;
		List notifications = null;
		try {
			dq = new DBPreparedQuery("dGetNotifications");
			notifications = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting notifications data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return notifications;
	}

	/**
	 * Method to save new appointment data.
	 * 
	 * @param subject
	 *            The subject of the appointment.
	 * @param fromTime
	 *            The start time of the appointment.
	 * @param toTime
	 *            The end time of the appointment.
	 * @param userId
	 *            The identifier of the user who set the appointment.
	 * @param applicantId
	 *            The identifier of the candidate.
	 * @param positionId
	 *            The position for which the applicant is in process.
	 * @param positionStepId
	 *            The position step of the applicant.
	 * @param remindMe
	 *            The number of minutes the user identified by userId is to be reminded before. If 0
	 *            then don't remind.
	 * @param remindInterviewer
	 *            The number of minutes the interviewers is to be reminded before. If 0 then don't
	 *            remind.
	 * @param remindApplicant
	 *            The number of minutes the candidate is to be reminded before. If 0 then don't
	 *            remind.
	 * @param interviewers
	 *            The comma separated string containing the identifiers of the interviewers.
	 * @param status
	 *            The status of the appointment.
	 */
	public void createNewAppointment(String subject, String fromTime, String toTime, String userId, String applicantId, String positionId, String positionStepId, String remindMe,
			String remindInterviewer, String remindApplicant, String smsRemindMe, String smsRemindInterviewer, String smsRemindApplicant, String remindMeTemplate, String remindInterviewerTemplate,
			String remindApplicantTemplate, String smsRemindMeTemplate, String smsRemindInterviewerTemplate, String smsRemindApplicantTemplate, String interviewers, String status, int doSendEmail,String interviewMode,String detailsInterviewMode) {
		String appointmentId = null;
		DBPreparedQuery dq = null;
		DBQuery dbq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			dq = new DBPreparedQuery("dCreateNewAppointment", tran);
			dq.setString(1, subject);
			dq.setString(2, fromTime);
			dq.setString(3, toTime);
			dq.setString(4, userId);
			dq.setString(5, applicantId);
			dq.setString(6, positionId);
			dq.setString(7, positionStepId);
			dq.setString(8, remindMe);
			dq.setString(9, remindInterviewer);
			dq.setString(10, remindApplicant);
			dq.setString(11, smsRemindMe);
			dq.setString(12, smsRemindInterviewer);
			dq.setString(13, smsRemindApplicant);
			dq.setString(14, "" + CalendarConstants.APPOINTMENT_STATUS_TENTATIVE);
			dq.setString(15, userId);
			dq.setString(16, remindMeTemplate);
			dq.setString(17, remindInterviewerTemplate);
			dq.setString(18, remindApplicantTemplate);
			dq.setString(19, smsRemindMeTemplate);
			dq.setString(20, smsRemindInterviewerTemplate);
			dq.setString(21, smsRemindApplicantTemplate);
			
			dq.setString(22,interviewMode);
			dq.setString(23, detailsInterviewMode);
			
			dq.execute();

			if (interviewers != null && interviewers.length() > 0) {
				dbq = new DBQuery("dFetchLastInsertID", tran);
				appointmentId = dbq.getIdResult();
				addAppointmentAttendees(appointmentId, interviewers, tran);
			}
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			selectionProcessManager.saveStatusMessage(applicantId, status, userId);

			ToDoManager toDoManager = new ToDoManager();
			toDoManager.regenerateToDo(positionId, applicantId, tran);
			
			tran.commit();

			//add into user activity
			addUserActivity(applicantId, positionId, positionStepId, userId, appointmentId, fromTime);
			
			AppointmentData aData = getAppointmentDataToEdit(appointmentId);
			// Add notification to be send when appointment created
			if (doSendEmail == CalendarConstants.SEND_EMAIL_YES) {
				AppointmentNotificationManager appointmentNotificationManager = new AppointmentNotificationManager();
				appointmentNotificationManager.insertNotification(appointmentId, subject, fromTime, toTime, userId, applicantId, positionId, positionStepId, interviewers, null, null, null,
						CalendarConstants.NOTIFY_ADD, CalendarConstants.NOTIFICATION_NOT_PROCESSED, aData.getAppointmentDateCreated(), aData.getAppointmentDateModified(), aData.getRemindMe(), aData
								.getRemindInterviewer(),aData.getInterviewMode(),aData.getDetailsInterviewMode(), null);
			}
			// add trigger for reminder of this appointment
			CalendarScheduler.addTrigger(aData);
			SMSScheduler.addTrigger(aData);
			
			TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			try {
				tran.rollback();
			} catch (Exception ex) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			}
		} finally {
			if (dbq != null) {
				dbq.releaseTransaction(tran);
			}
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}

	

	private void addUserActivity(String applicantId, String positionId,
			String positionStepIdTo, String userId, String interactionId, String appointmentFromDate) {
		try {
			PositionManager positionManager = new PositionManager();
			String positionName = positionManager.getPositionName(positionId);
			String stepName = positionManager.getPositionStepName(Integer.parseInt(positionStepIdTo));
			String deptName = positionManager.getPositionDepartment(positionId);		
			
			appointmentFromDate=appointmentFromDate+":00";
			Date fromDate = Utils.convertToDate(appointmentFromDate, DateConstants.DB_DATE_TIME_PATTERN);
			String activity = "Scheduled "+stepName+" for "+deptName+"-"+positionName+" on "+
			Utils.getDateConvertedToString(fromDate, Utils.regDDMMMFormat)+ " at "+
			Utils.getDateConvertedToString(fromDate, Utils.reghhmma);

			LatestActivityManager activityManager = new LatestActivityManager(); 
			activityManager.addUserActivity(activity, interactionId, SelectionProcessConstants.INTERACTION_APPOINTMENTS, positionId, applicantId, userId);
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}
	}
	
	/**
	 * @param fromTime
	 * @param toTime
	 * @param userId
	 * @param userRoles
	 * @param permissionSet
	 * @return
	 */
	public List getAppointmentsForADay(String fromTime, String toTime, String userId, String userRoles, PermissionSet permissionSet) {
		return getAppointmentsForADay(fromTime, toTime, userId, userRoles, permissionSet, GlobalConstants.DISABLED);
	}
	
	/**
	 * Method to get the appointments for a day from fromTime to toTime.
	 * 
	 * @param fromTime
	 *            The start time of duration.
	 * @param toTime
	 *            The end time of duration.
	 * @param userId
	 *            The logged-in user's id.
	 * @param userRoles
	 *            The logged-in user's role.
	 * @return the list of appointments for duration fromTime to toTime.
	 */
	public List getAppointmentsForADay(String fromTime, String toTime, String userId, String userRoles, PermissionSet permissionSet,
			String showMyAppointments) {
		List appointments = new ArrayList();
		try {
			if (fromTime != null && toTime != null) {
				Map appointmentMap = getAppointmentsForWeekDays(fromTime + ","
						+ toTime, userId, userRoles, permissionSet, false, showMyAppointments);
				if (appointmentMap != null && appointmentMap.size() > 0) {
					appointments = (List) appointmentMap.get(fromTime);
					if (appointments != null && appointments.size() > 0) {
						for (int i = 0; i < appointments.size(); i++) {
							AppointmentData data = (AppointmentData) appointments.get(i);
							String interviewer = getAppointmentAttendeesAsString(data.getAppointmentId());
							data.setInterviewer(interviewer);
						}
					}
				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting appointments for a day", e);
		}
		return appointments;
	}

	/**
	 * Method to get the data for edit appointment screen.
	 * 
	 * @param appointmentId
	 *            The identifier of the appointment to edit.
	 * @return The string representation of the appointment data in XML.
	 */
	public String initEditAppointmentData(String appointmentId, String userTimeZone) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);

		try {
			AppointmentData data = getAppointmentDataToEdit(appointmentId);
			List interviewers = getPositionInterviewers(String.valueOf(data.getApplicantStepId()), appointmentId);
			List attendees = getAppointmentAttendees(appointmentId);
			List notifications = getNotifications();
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			List status = selectionProcessManager.getStatusMessages("" + data.getApplicantId());

			wr.startDocument();
			wr.startElement("setAppointmentData");

			// Candidate
			AttributesImpl atr = new AttributesImpl();
			atr.addAttribute("", "id", "", "", "" + data.getApplicantId());
			wr.startElement("", "candidate", "", atr);
			String candidate = (data.getApplicantName() == null) ? "" : data.getApplicantName();
			wr.characters(candidate);
			wr.endElement("candidate");

			// Subject
			wr.startElement("subject");
			String subject = (data.getAppointmentSubject() == null) ? "" : data.getAppointmentSubject();
			wr.characters(subject);
			wr.endElement("subject");

			// Interviewers
			StringBuffer interviewerIds = new StringBuffer();
			wr.startElement("interviewers");
			if (interviewers != null && interviewers.size() > 0) {
				for (int i = 0; i < interviewers.size(); i++) {
					LoginData interviewer = (LoginData) interviewers.get(i);
					atr = new AttributesImpl();
					if (attendees != null && attendees.size() > 0) {
						for (int j = 0; j < attendees.size(); j++) {
							UserData attendee = (UserData) attendees.get(j);
							if (interviewer.getUserId().equals(attendee.getUserId()+"")) {
								if (interviewerIds.length() > 0) {
									interviewerIds.append(",");
								}
								interviewerIds.append(interviewer.getUserId());

								atr.addAttribute("", "checked", "", "", "checked");
								// break;
							}
						}
					}
					atr.addAttribute("", "id", "", "", String.valueOf(interviewer.getUserId()));
					wr.startElement("", "interviewer", "", atr);
					String userName = (interviewer.getName() == null) ? "" : interviewer.getName();
					wr.characters(userName);
					wr.endElement("interviewer");
				}
			}
			wr.endElement("interviewers");

			// From
			String from = (data.getAppointmentFromDate() == null) ? "" : data.getAppointmentFromDate();
			wr.startElement("from");
			wr.characters(from);
			wr.endElement("from");
			
			wr.startElement("fromDate");
			wr.characters(data.getAppointmentFromDateToDisplay());
			wr.endElement("fromDate");
			
			wr.startElement("fromTime");
			wr.characters(data.getAppointmentFromTimeToDisplay());
			wr.endElement("fromTime");

			// To
			String to = (data.getAppointmentToDate() == null) ? "" : data.getAppointmentToDate();
			wr.startElement("to");
			wr.characters(to);
			wr.endElement("to");

			wr.startElement("timeZoneInfo");
			if (userTimeZone != null) {
					atr = new AttributesImpl();
					atr.addAttribute("", "value", "", "", userTimeZone);
					atr.addAttribute("", "checked", "", "", "checked");
					wr.startElement("", "timeZone", "", atr);
					wr.characters(userTimeZone);
					wr.endElement("timeZone");
			}
			wr.endElement("timeZoneInfo");
			
			// Notifications
			wr.startElement("notifications");
			if (notifications != null && notifications.size() > 0) {
				for (int i = 0; i < notifications.size(); i++) {
					TimeData notification = (TimeData) notifications.get(i);
					atr = new AttributesImpl();
					atr.addAttribute("", "value", "", "", String.valueOf(notification.getNumberOfMinutes()));
					wr.startElement("", "notification", "", atr);
					wr.characters(notification.getDescription());
					wr.endElement("notification");
				}
			}
			wr.endElement("notifications");

			// Status
			wr.startElement("statusInfo");
			if (status != null && status.size() > 0) {
				for (int i = 0; i < status.size(); i++) {
					SimpleDataObject statusObj = (SimpleDataObject) status.get(i);
					atr = new AttributesImpl();
					atr.addAttribute("", "value", "", "", statusObj.getString("messageId"));
					if (statusObj.getString("message") != null && statusObj.getString("message").equalsIgnoreCase(statusObj.getString("selectedMessage"))) {
						atr.addAttribute("", "checked", "", "", "checked");
					}
					wr.startElement("", "status", "", atr);
					wr.characters(statusObj.getString("message"));
					wr.endElement("status");
				}
			}
			wr.endElement("statusInfo");

			// Remind Me
			String remindMe = (data.getRemindMe() == null) ? "" : data.getRemindMe();
			wr.startElement("remindMe");
			wr.characters(remindMe);
			wr.endElement("remindMe");

			// Remind Interviewer
			String remindInterviewer = (data.getRemindInterviewer() == null) ? "" : data.getRemindInterviewer();
			wr.startElement("remindInterviewer");
			wr.characters(remindInterviewer);
			wr.endElement("remindInterviewer");

			// Remind Candidate
			String remindCandidate = (data.getRemindCandidate() == null) ? "" : data.getRemindCandidate();
			wr.startElement("remindCandidate");
			wr.characters(remindCandidate);
			wr.endElement("remindCandidate");

			// SMS Remind Me
			String smsRemindMe = (data.getSMSRemindMe() == null) ? "" : data.getSMSRemindMe();
			wr.startElement("smsRemindMe");
			wr.characters(smsRemindMe);
			wr.endElement("smsRemindMe");

			// SMS Remind Interviewer
			String smsRemindInterviewer = (data.getSMSRemindInterviewer() == null) ? "" : data.getSMSRemindInterviewer();
			wr.startElement("smsRemindInterviewer");
			wr.characters(smsRemindInterviewer);
			wr.endElement("smsRemindInterviewer");

			// SMS Remind Candidate
			String smsRemindCandidate = (data.getSMSRemindCandidate() == null) ? "" : data.getSMSRemindCandidate();
			wr.startElement("smsRemindCandidate");
			wr.characters(smsRemindCandidate);
			wr.endElement("smsRemindCandidate");

			// Remind Me Template
			String remindMeTemplate = (data.getRemindMeTemplate() == null) ? "" : data.getRemindMeTemplate();
			wr.startElement("remindMeTemplate");
			wr.characters(remindMeTemplate);
			wr.endElement("remindMeTemplate");

			// Remind Interviewer Template
			String remindInterviewerTemplate = (data.getRemindInterviewerTemplate() == null) ? "" : data.getRemindInterviewerTemplate();
			wr.startElement("remindInterviewerTemplate");
			wr.characters(remindInterviewerTemplate);
			wr.endElement("remindInterviewerTemplate");

			// Remind Candidate Template
			String remindCandidateTemplate = (data.getRemindCandidateTemplate() == null) ? "" : data.getRemindCandidateTemplate();
			wr.startElement("remindCandidateTemplate");
			wr.characters(remindCandidateTemplate);
			wr.endElement("remindCandidateTemplate");

			// SMS Remind Me Template
			String smsRemindMeTemplate = (data.getSMSRemindMeTemplate() == null) ? "" : data.getSMSRemindMeTemplate();
			wr.startElement("smsRemindMeTemplate");
			wr.characters(smsRemindMeTemplate);
			wr.endElement("smsRemindMeTemplate");

			// SMS Remind Interviewer Template
			String smsRemindInterviewerTemplate = (data.getSMSRemindInterviewerTemplate() == null) ? "" : data.getSMSRemindInterviewerTemplate();
			wr.startElement("smsRemindInterviewerTemplate");
			wr.characters(smsRemindInterviewerTemplate);
			wr.endElement("smsRemindInterviewerTemplate");

			// SMS Remind Candidate Template
			String smsRemindCandidateTemplate = (data.getSMSRemindCandidateTemplate() == null) ? "" : data.getSMSRemindCandidateTemplate();
			wr.startElement("smsRemindCandidateTemplate");
			wr.characters(smsRemindCandidateTemplate);
			wr.endElement("smsRemindCandidateTemplate");
			// interviewMode
			String interviewMode = (data.getInterviewMode() == null) ? "" : data.getInterviewMode();
			wr.startElement("interviewMode");
			wr.characters(interviewMode);
			wr.endElement("interviewMode");
			// detailsInterviewMode
			String detailsInterviewMode = (data.getDetailsInterviewMode() == null) ? "" : data.getDetailsInterviewMode();
			wr.startElement("detailsInterviewMode");
			wr.characters(detailsInterviewMode);
			wr.endElement("detailsInterviewMode");
			
			wr.endElement("setAppointmentData");
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while initialising appointment data", e);
		}

		return sWr.toString();
	}

	/**
	 * Method to get the data for edit appointment screen.
	 * 
	 * @param appointmentId
	 *            The identifier of the appointment to edit.
	 * @return The appointment data as object.
	 */
	public AppointmentData getAppointmentDataToEdit(String appointmentId) {
		AppointmentData data = null;
		DBPreparedQuery dq = null;
		try {
			String[] dynParams = new String[1];
			if(GlobalConstants.ENABLED.equals(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_POSITION_CODE))) {
				dynParams[0] = " tpos.position_code ";
			} else {
				dynParams[0] = " tpos.position_title ";
			}	
			dq = new DBPreparedQuery("dGetAppointmentDataToEdit", dynParams);
			dq.setString(1, appointmentId);
			data = (AppointmentData) dq.getSingleObjectResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting appointment data", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return data;
	}

	/**
	 * Method to save the edited data of the appointment.
	 * 
	 * @param applicantId
	 *            The applicant's identifier.
	 * @param appointmentId
	 *            The identifier of the appointment edited.
	 * @param fromTime
	 *            The modified fromTime.
	 * @param toTime
	 *            The modified toTime.
	 * @param remindMe
	 *            The modified remindMe.
	 * @param remindInterviewer
	 *            The modified remindInterviewer.
	 * @param remindApplicant
	 *            The modified remindApplicant.
	 * @param interviewers
	 *            The modified comma separated string of the ids of the interviewers.
	 * @param status
	 *            The modified status data of the appointment.
	 * @param userId
	 *            The logged-in user's id.
	 */
	public void editAppointment(String applicantId, String appointmentId, String fromTime, String toTime, String remindMe, String remindInterviewer, String remindApplicant, String smsRemindMe,
			String smsRemindInterviewer, String smsRemindApplicant, String remindMeTemplate, String remindInterviewerTemplate, String remindApplicantTemplate, String smsRemindMeTemplate,
			String smsRemindInterviewerTemplate, String smsRemindApplicantTemplate, String interviewers, String status, String userId, int doSendEmail,String interviewMode,String detailsInterviewMode) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			tran = new DBTransaction();
			// take copy of old appointment data
			AppointmentData aDataOld = getAppointmentDataToEdit(appointmentId);
			List attendeesOld = getAppointmentAttendees(appointmentId);
			// update with new data
			dq = new DBPreparedQuery("dEditAppointment",tran);
			dq.setString(1, fromTime);
			dq.setString(2, toTime);
			dq.setString(3, remindMe);
			dq.setString(4, remindInterviewer);
			dq.setString(5, remindApplicant);
			dq.setString(6, smsRemindMe);
			dq.setString(7, smsRemindInterviewer);
			dq.setString(8, smsRemindApplicant);
			dq.setString(9, "" + CalendarConstants.APPOINTMENT_STATUS_TENTATIVE);
			dq.setString(10, userId);
			dq.setString(11, remindMeTemplate);
			dq.setString(12, remindInterviewerTemplate);
			dq.setString(13, remindApplicantTemplate);
			dq.setString(14, smsRemindMeTemplate);
			dq.setString(15, smsRemindInterviewerTemplate);
			dq.setString(16, smsRemindApplicantTemplate);
			dq.setString(17, interviewMode);
			dq.setString(18, detailsInterviewMode);
			dq.setString(19, appointmentId);
			dq.execute();

			deleteAppointmentAttendees(appointmentId, tran);

			addAppointmentAttendees(appointmentId, interviewers, tran);
			
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			selectionProcessManager.saveStatusMessage(applicantId, status, userId);
		
			ToDoManager toDoManager = new ToDoManager();
			toDoManager.regenerateToDo(null, applicantId, tran);
			
			tran.commit();
			// update the triggers associated with calendar
			AppointmentData aData = getAppointmentDataToEdit(appointmentId);
			String oldAttendees = "";
			if (attendeesOld != null) {
				for (int i = 0; i < attendeesOld.size(); i++) {
					UserData uData = (UserData) attendeesOld.get(i);
					oldAttendees += uData.getUserId();
					if (i < attendeesOld.size() - 1) {
						oldAttendees = oldAttendees + ",";
					}
				}
			}
			if (doSendEmail == CalendarConstants.SEND_EMAIL_YES) {
				AppointmentNotificationManager appointmentNotificationManager = new AppointmentNotificationManager();
				appointmentNotificationManager.insertNotification(appointmentId, aDataOld.getAppointmentSubject(), aDataOld.getAppointmentFromDate(), aDataOld.getAppointmentToDate(), aDataOld
						.getAppointmentCreatedBy(), applicantId, "" + aDataOld.getApplicantPositionId(), "" + aDataOld.getApplicantStepId(), oldAttendees, fromTime, toTime, interviewers,
						CalendarConstants.NOTIFY_MODIFY, CalendarConstants.NOTIFICATION_NOT_PROCESSED, aDataOld.getAppointmentDateCreated(), aData.getAppointmentDateModified(), aData.getRemindMe(),
						aData.getRemindInterviewer(), aDataOld.getInterviewMode(),aDataOld.getDetailsInterviewMode(),null);
			}

			CalendarScheduler.updateTrigger(aData);
			SMSScheduler.updateTrigger(aData);
			
			TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, applicantId, TPIndexEvent.PRIORITY_HIGH));
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while editing appointment data", e);
			if (tran != null) {
				try {
					tran.rollback();
				} catch (SQLException e1) {
					TPLogger.getLogger().error("Error while rolling back the transaction", e1);
				}
			}
		} finally {
			if (dq != null) {
				dq.releaseTransaction(tran);
			}
		}
	}


	/**
	 * Method to update the status of the appointment.
	 * 
	 * @param appointmentId
	 *            The identifier of the appointment edited.
	 * @param status
	 *            The modified status data of the appointment.
	 * @param userId
	 *            The logged-in user's id.
	 */
	public void updateAppointmentStatus(String applicantId, String status, String userId) {
		try {
			SelectionProcessManager selectionProcessManager = new SelectionProcessManager();
			selectionProcessManager.saveStatusMessage(applicantId, status, userId);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while updating appointment status data", e);
		}
	}

	/**
	 * Method to check whether appointment already exists for an applicant at a particular selection
	 * process step.
	 * 
	 * @param applicantId
	 *            The identifier of the applicant.
	 * @param positionId
	 *            The position identifier.
	 * @param stepId
	 *            The step identifier.
	 * @return The comma separated list of the existing appointments.
	 */
	public String checkIfAppointmentExists(String applicantId, String positionId, String stepId) {
		DBPreparedQuery dq = null;
		StringBuffer existingAppointmentIds = new StringBuffer();
		try {
			dq = new DBPreparedQuery("dCalendarManager_GetAppointments");
			dq.setString(1, applicantId);
			dq.setString(2, positionId);
			dq.setString(3, stepId);
			List rs = dq.getResult();
			if (rs != null && rs.size() > 0) {
				for (int i = 0; i < rs.size(); i++) {
					SimpleDataObject obj = (SimpleDataObject) rs.get(i);
					if (existingAppointmentIds.length() > 0) {
						existingAppointmentIds.append(",");
					}
					existingAppointmentIds.append(obj.getAttribute("appointmentId").toString());

				}
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while checking whether future appointment exists for an applicant at particular selection step", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return existingAppointmentIds.toString();
	}

	/**
	 * Returns XML file containing the error messages.
	 * 
	 * @param errors
	 *            The string array containing error messages.
	 * @return Errors in XML file.
	 */
	public String getErrorsXml(String[] errors) {
		StringWriter sWr = new StringWriter();
		XMLWriter wr = new XMLWriter(sWr);
		try {
			wr.startDocument();
			wr.startElement("errors");
			if (errors != null && errors.length > 0) {
				for (int indx = 0; indx < errors.length; indx++) {
					wr.startElement("error");
					wr.characters(errors[indx]);
					wr.endElement("error");
				}
			}
			wr.endElement("errors");
			wr.endDocument();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while creating xml file for appointments", e);
		}
		return sWr.toString();
	}

	/**
	 * Method to delete the appointment.
	 * 
	 * @param appointmentId
	 *            The appointment identifier.
	 */
	public void deleteAppointment(String appointmentId, int doSendEmail, String positionId, String applicantId) {
		DBPreparedQuery dq = null;
		DBTransaction tran = null;
		try {
			// first add entry to notification and then delete
			AppointmentData aDataOld = getAppointmentDataToEdit(appointmentId);
			List attendeesOld = getAppointmentAttendees(appointmentId);
			String oldAttendees = "";
			if (attendeesOld != null) {
				for (int i = 0; i < attendeesOld.size(); i++) {
					UserData uData = (UserData) attendeesOld.get(i);
					oldAttendees += uData.getUserId();
					if (i < attendeesOld.size() - 1) {
						oldAttendees = oldAttendees + ",";
					}
				}
			}
			if (doSendEmail == CalendarConstants.SEND_EMAIL_YES) {
				AppointmentNotificationManager appointmentNotificationManager = new AppointmentNotificationManager();
				appointmentNotificationManager.insertNotification(appointmentId, aDataOld.getAppointmentSubject(), aDataOld.getAppointmentFromDate(), aDataOld.getAppointmentToDate(), aDataOld
						.getAppointmentCreatedBy(), "" + aDataOld.getApplicantId(), "" + aDataOld.getApplicantPositionId(), "" + aDataOld.getApplicantStepId(), oldAttendees, null, null, null,
						CalendarConstants.NOTIFY_DELETE, CalendarConstants.NOTIFICATION_NOT_PROCESSED, aDataOld.getAppointmentDateCreated(), aDataOld.getAppointmentDateModified(), aDataOld
								.getRemindMe(), aDataOld.getRemindInterviewer(),aDataOld.getInterviewMode(),aDataOld.getDetailsInterviewMode(), null);
			}

			tran = new DBTransaction();
			
			LatestActivityManager activityManager = new LatestActivityManager(); 
			activityManager.deleteUserActivity(appointmentId, SelectionProcessConstants.INTERACTION_APPOINTMENTS, tran);

			ToDoManager toDoManager = new ToDoManager();
			toDoManager.deleteToDoForAppointment(appointmentId, tran);
			
			dq = new DBPreparedQuery("dDeleteAppointment", tran);
			dq.setString(1, appointmentId);
			dq.execute();
			
			tran.commit();
			
			//regenerate todo for this position + applicant
			toDoManager.regenerateToDo(positionId, applicantId, null);
			
			// delete the trigger from the schedular associated with this appintment
			CalendarScheduler.deleteTrigger(appointmentId);
			SMSScheduler.deleteTrigger(appointmentId);
			
			TPIndexEventQueue.push(new TPIndexEvent(TPIndexEvent.TYPE_UPDATE_APPLICANT, ""+aDataOld.getApplicantId(), TPIndexEvent.PRIORITY_HIGH));
		} catch (Exception e) {
			try {
				tran.rollback();
			} catch (Exception se) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, se);
			}
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (tran != null) {
				tran.release();
			}
		}
	}

	public boolean checkIfAppointmentExistsInPast(String applicantId) {
		DBPreparedQuery dq = null;
		boolean appointmentExistsInPast = false;
		try {
			dq = new DBPreparedQuery("dCalendarManager_GetAppointmentInPast");
			dq.setString(1, applicantId);
			dq.setInt(2, CalendarConstants.APPOINTMENT_STATUS_NOSHOW);
			SimpleDataObject sDo = (SimpleDataObject) dq.getSingleObjectResult();
			if (sDo != null) {
				appointmentExistsInPast = true;
			}
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while checking whether past appointment exists for an applicant at particular selection step", e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return appointmentExistsInPast;
	}

	public List getTemplatesForType(String templateTypeId) {
		List templates = null;
		DBPreparedQuery dq = null;
		try {
			dq = new DBPreparedQuery("dCalendarManager_GetTemplatesForType");
			dq.setString(1, templateTypeId);
			dq.setString(2, TemplateConstants.AUTO_NOT_CREATED);
			templates = dq.getResult();
		} catch (Exception e) {
			TPLogger.getLogger().error("Error while getting templates for type " + templateTypeId, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return templates;
	}

	public String getDefaultTemplateCode(List templates) {
		String defaultTemplateCode = "";
		if (templates != null && templates.size() > 0) {
			for (int i = 0; i < templates.size(); i++) {
				TemplateData data = (TemplateData) templates.get(i);
				if (TemplateConstants.TEMPLATE_DEFAULT.equalsIgnoreCase(data.getIsTemplateDefault())) {
					defaultTemplateCode = data.getTemplateCode();
					break;
				}
			}
		}
		return defaultTemplateCode;
	}


	/**
	 * @param appointmentId
	 * @param interviewers
	 * @param tran
	 * @throws SQLException
	 */
	private void addAppointmentAttendees(String appointmentId, String interviewers, 
			DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			String[] interviewerIds = interviewers.split(",");
			for (int i = 0; i < interviewerIds.length; i++) {
				if (tran != null) {
					dq = new DBPreparedQuery("dCreateNewAppointmentAttendee", tran);
				} else {
					dq = new DBPreparedQuery("dCreateNewAppointmentAttendee");
				}
				dq.setString(1, appointmentId);
				dq.setString(2, interviewerIds[i].trim());
				dq.execute();
			}
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	/**
	 * @param appointmentId
	 * @param tran
	 * @return
	 * @throws SQLException
	 */
	private void deleteAppointmentAttendees(String appointmentId, DBTransaction tran) throws SQLException {
		DBPreparedQuery dq = null;
		try {
			if (tran != null) {
				dq = new DBPreparedQuery("dDeleteAppointmentAttendees",tran);
			} else {
				dq = new DBPreparedQuery("dDeleteAppointmentAttendees");
			}
			
			dq.setString(1, appointmentId);
			dq.execute();
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		}finally {
			if (dq != null) {
				if (tran == null) {
					dq.releaseConnection();
				} else {
					dq.closeOpenCursors();
				}
			}
		}
	}
	
	public void updateAppointmentAttendees(String appointmentId, String interviewers, DBTransaction tran) {
		try {
			deleteAppointmentAttendees(appointmentId, tran);
			addAppointmentAttendees(appointmentId, interviewers, tran);	
		} catch (Exception e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);

		}		
	}
	
}
