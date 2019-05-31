/*
 * Created on Jun 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.calendar.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.talentPool.calendar.dataobject.AppointmentData;
import com.talentPool.calendar.form.CalendarForm;
import com.talentPool.calendar.manager.CalendarManager;
import com.talentPool.common.MyThreadLocal;
import com.talentPool.common.NavigationConstants;
import com.talentPool.common.ThreadLocalContextObject;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.base.TPDispatchAction;
import com.talentPool.common.properties.GlobalApplicationProperties;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.CommonUtils;
import com.talentPool.common.utils.Utils;
import com.talentPool.notifier.TemplateConstants;
import com.talentPool.user.dataobject.LoginData;
import com.talentPool.user.manager.LoginManager;
import com.talentPool.user.manager.PermissionSet;
import com.talentPool.user.manager.SessionManager;
import com.talentPool.user.utils.UserUtils;

;

/**
 * @author pallavi
 * @date Jun 23, 2006
 */
public class CalendarAction extends TPDispatchAction {
	public ActionForward calendarHome(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "calendarHome";
		
		String userId = (String) request.getSession(false).getAttribute("userId");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		CalendarForm form = (CalendarForm) actionForm;
		
		CalendarManager calendarManager = new CalendarManager();
		List positions = calendarManager.getAllOpenPositions(userId, permissionSet);

		List remindMeTemplates = calendarManager.getTemplatesForType(TemplateConstants.TEMPLATE_TYPE_APPOINTMENT_REMINDER_OWNER);
		List remindInterviewerTemplates = calendarManager.getTemplatesForType(TemplateConstants.TEMPLATE_TYPE_APPOINTMENT_REMINDER_INTERVIEWER);
		List remindApplicantTemplates = calendarManager.getTemplatesForType(TemplateConstants.TEMPLATE_TYPE_APPOINTMENT_REMINDER_APPLICANT);

		String defaultRemindMeTemplate = calendarManager.getDefaultTemplateCode(remindMeTemplates);
		String defaultRemindInterviewerTemplate = calendarManager.getDefaultTemplateCode(remindInterviewerTemplates);
		String defaultRemindApplicantTemplate = calendarManager.getDefaultTemplateCode(remindApplicantTemplates);

		request.setAttribute("positions", positions);
		String jsArrayPositions = CommonUtils.getListJavaScriptArrayWithProperties((ArrayList)positions, "positionId", "positionTitle");
		form.setJsArrayPositions(jsArrayPositions);
		
		LoginManager loginManager = new LoginManager();
		LoginData user = loginManager.getUser(userId);
		form.setTimeZone(user.getTimeZone());
		
		request.setAttribute("remindMeTemplates", remindMeTemplates);
		request.setAttribute("remindInterviewerTemplates", remindInterviewerTemplates);
		request.setAttribute("remindApplicantTemplates", remindApplicantTemplates);

		request.setAttribute("defaultRemindMeTemplate", defaultRemindMeTemplate);
		request.setAttribute("defaultRemindInterviewerTemplate", defaultRemindInterviewerTemplate);
		request.setAttribute("defaultRemindApplicantTemplate", defaultRemindApplicantTemplate);

		request.setAttribute(GlobalConstants.PROPERTY_SHOW_REMINDER, GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SHOW_REMINDER));
		
		if (GlobalConstants.ENABLED.equalsIgnoreCase(GlobalApplicationProperties.getProperty(GlobalConstants.PROPERTY_SMS_ENABLED)) && permissionSet.isPERMISSION_SEND_SMS()) {
			List smsRemindMeTemplates = calendarManager.getTemplatesForType(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_OWNER);
			List smsRemindInterviewerTemplates = calendarManager.getTemplatesForType(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_INTERVIEWER);
			List smsRemindApplicantTemplates = calendarManager.getTemplatesForType(TemplateConstants.TEMPLATE_TYPE_SMS_REMINDER_APPLICANT);

			String defaultSmsRemindMeTemplate = calendarManager.getDefaultTemplateCode(smsRemindMeTemplates);
			String defaultSmsRemindInterviewerTemplate = calendarManager.getDefaultTemplateCode(smsRemindInterviewerTemplates);
			String defaultSmsRemindApplicantTemplate = calendarManager.getDefaultTemplateCode(smsRemindApplicantTemplates);

			request.setAttribute("smsRemindMeTemplates", smsRemindMeTemplates);
			request.setAttribute("smsRemindInterviewerTemplates", smsRemindInterviewerTemplates);
			request.setAttribute("smsRemindApplicantTemplates", smsRemindApplicantTemplates);

			request.setAttribute("defaultSmsRemindMeTemplate", defaultSmsRemindMeTemplate);
			request.setAttribute("defaultSmsRemindInterviewerTemplate", defaultSmsRemindInterviewerTemplate);
			request.setAttribute("defaultSmsRemindApplicantTemplate", defaultSmsRemindApplicantTemplate);
		}

		request.setAttribute("t", NavigationConstants.T_CALENDAR);

		return mapping.findForward(forward);
	}

	public ActionForward getAppointmentXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String forward = "xmlFile";
		String xmlFile = "";
		String showMyAppointment = null;

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			CalendarForm form = (CalendarForm) actionForm;
			String currentWeekDays = form.getCurrentWeekDays();
			String userId = (String) request.getSession(false).getAttribute("userId");
			String userRoles = (String) request.getSession(false).getAttribute("userRoles");
			
			if(!Utils.isBlankOrNull(request.getParameter("showMyAppointments")))
				showMyAppointment = request.getParameter("showMyAppointments");
			else
				showMyAppointment = GlobalConstants.DISABLED;
			
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			CalendarManager calendarManager = new CalendarManager();
			Map appointments = calendarManager.getAppointmentsForWeekDays(currentWeekDays, userId, userRoles, permissionSet, true,showMyAppointment);		
			xmlFile = calendarManager.getAppointmentsInXml(appointments, userId, permissionSet, userRoles);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward getApplicantXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";
		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
			CalendarForm form = (CalendarForm) actionForm;
			String selectedPosition = form.getSelectedPosition();
			CalendarManager calendarManager = new CalendarManager();
			List applicants = calendarManager.getApplicantsInProcess(selectedPosition, permissionSet);
			xmlFile = calendarManager.getApplicantXml(applicants);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward getNewAppointmentXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			CalendarForm form = (CalendarForm) actionForm;
			String selectedApplicant = form.getSelectedApplicant();
			CalendarManager calendarManager = new CalendarManager();
			String userId = (String) request.getSession(false).getAttribute("userId");
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			xmlFile = calendarManager.initAppointmentData(selectedApplicant,loginData.getTimeZone());
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward createNewAppointment(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String forward = "xmlFile";
		String xmlFile = "";
		synchronized (this) {
			if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
				xmlFile = Utils.getXMLForSessionExpiry();
			} else {
				CalendarForm form = (CalendarForm) actionForm;
				CalendarManager calendarManager = new CalendarManager();
				boolean appointmentExistsInPast = calendarManager.checkIfAppointmentExistsInPast(form.getSelectedApplicant());
				
				TimeZone newZone = TimeZone.getTimeZone(form.getTimeZone());
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
				String appointmentFromDate = form.getAppointmentFromDate();
				
				formatter.setTimeZone(newZone); 
							
				try {
					if(formatter.parse(appointmentFromDate).before(formatter.parse(formatter.format(new Date()))))
					{
						String[] errors = new String[] { "calendar.error.can_not_set_appointments_in_past" };
						xmlFile = calendarManager.getErrorsXml(errors);
					}
				} catch (Exception e) {
					TPLogger.getLogger().error("unable to Parse appointment date", e);
				}
				
				form.getAppointmentFromDate();
				String existingAppointmentId = calendarManager.checkIfAppointmentExists(form.getSelectedApplicant(), form.getApplicantPositionId(), form.getApplicantStepId());
				if (Utils.isBlankOrNull(xmlFile) && !Utils.isBlankOrNull(existingAppointmentId)) {
					String[] errors = new String[] { "calendar.error.future_appointment_exists" };
					xmlFile = calendarManager.getErrorsXml(errors);
				} else if (Utils.isBlankOrNull(xmlFile) && appointmentExistsInPast) {
					String[] errors = new String[] { "calendar.error.past_appointment_exists" };
					xmlFile = calendarManager.getErrorsXml(errors);
				} else if (Utils.isBlankOrNull(xmlFile)) {
					return saveNewAppointment(mapping, actionForm, request, response);
				}
			}
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward saveNewAppointment(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			CalendarForm form = (CalendarForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			String oldTimeZoneId = TimeZone.getDefault().getID();
			//final TimeZone oldZone = TimeZone.getTimeZone(oldTimeZoneId);
			String timeZoneId = form.getTimeZone();
			/*final TimeZone newZone = TimeZone.getTimeZone(timeZoneId);
			TimeZone.setDefault(newZone);*/
			ThreadLocalContextObject obj = new ThreadLocalContextObject();
			obj.setTimeZone(timeZoneId);
			MyThreadLocal.unset();
			MyThreadLocal.set(obj);
			CalendarManager calendarManager = new CalendarManager();
			String existingAppointmentIds = calendarManager.checkIfAppointmentExists(form.getSelectedApplicant(), form.getApplicantPositionId(), form.getApplicantStepId());
			if (!Utils.isBlankOrNull(existingAppointmentIds)) {
				String appointmentids[] = existingAppointmentIds.split(",");
				int doSendEmail = Integer.parseInt(GlobalApplicationProperties.getProperty("send_appointment"));
				for (int i = 0; i < appointmentids.length; i++) {
					calendarManager.deleteAppointment(appointmentids[i].trim(), doSendEmail, form.getApplicantPositionId(), form.getSelectedApplicant());
				}
			}
			int doSendEmail = Integer.parseInt(form.getIsSendAppointmentNotification());
			calendarManager.createNewAppointment(form.getSubject(), form.getAppointmentFromDate(), form.getAppointmentToDate(), userId, form.getSelectedApplicant(), form.getApplicantPositionId(),
					form.getApplicantStepId(), form.getRemindMe(), form.getRemindInterviewers(), form.getRemindCandidate(), form.getSmsRemindMe(), form.getSmsRemindInterviewers(), form
							.getSmsRemindCandidate(), form.getRemindMeTemplate(), form.getRemindInterviewersTemplate(), form.getRemindCandidateTemplate(), form.getSmsRemindMeTemplate(), form
							.getSmsRemindInterviewersTemplate(), form.getSmsRemindCandidateTemplate(), form.getInterviewers(), form.getStatus(), doSendEmail,form.getInterviewMode(),form.getDetailsInterviewMode());
			//TimeZone.setDefault(oldZone);
			obj = new ThreadLocalContextObject();
			obj.setTimeZone(oldTimeZoneId);
			MyThreadLocal.unset();
			MyThreadLocal.set(obj);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward printableView(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		if (!SessionManager.validateSession(mapping, actionForm, request, response, this)) {
			return null;
		}
		String forward = "printableView";
		String showMyAppointment = null;
		CalendarForm form = (CalendarForm) actionForm;
		String from = form.getFromTime();
		String to = form.getToTime();
		String userId = (String) request.getSession(false).getAttribute("userId");
		String userRoles = (String) request.getSession(false).getAttribute("userRoles");
		PermissionSet permissionSet = (PermissionSet) request.getSession(false).getAttribute("permissionSet");
		if(!Utils.isBlankOrNull(request.getParameter("showMyAppointments")))
			showMyAppointment = request.getParameter("showMyAppointments");
		else
			showMyAppointment = GlobalConstants.DISABLED;
		CalendarManager calendarManager = new CalendarManager();
		List appointments = calendarManager.getAppointmentsForADay(from, to, userId, userRoles, permissionSet,showMyAppointment);
		request.setAttribute("appointments", appointments);
		request.setAttribute("appointmentdate", Utils.convertToDate(from, Utils.redYYYYMMDDFormat));
		request.setAttribute("listGenratedOn", new Date());

		return mapping.findForward(forward);
	}

	public ActionForward getEditAppointmentXml(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			String userId = (String) request.getSession(false).getAttribute("userId");
			CalendarForm form = (CalendarForm) actionForm;
			String appointmentId = form.getAppointmentId();
			CalendarManager calendarManager = new CalendarManager();
			LoginManager loginManager = new LoginManager();
			LoginData loginData = loginManager.getUser(userId);
			xmlFile = calendarManager.initEditAppointmentData(appointmentId,loginData.getTimeZone());
		}
		request.setAttribute("xmlFile", xmlFile);
		return mapping.findForward(forward);
	}

	public ActionForward XMLInterviewers(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		try {
			
			CalendarForm form = (CalendarForm) actionForm;
			String selectedApplicant = form.getSelectedApplicant();
			String appointmentId = form.getAppointmentId();
			List interviewers = new ArrayList();
			
			CalendarManager calendarManager = new CalendarManager();
			if (!Utils.isBlankOrNull(selectedApplicant) && !selectedApplicant.equals("-1")) {
				AppointmentData data = calendarManager.getApplicantSelectionStatusInfo(selectedApplicant);
				interviewers = calendarManager.getPositionInterviewers(String.valueOf(data.getApplicantStepId()), "");
			}else{
				AppointmentData data = calendarManager.getAppointmentDataToEdit(appointmentId);
				interviewers = calendarManager.getPositionInterviewers(String.valueOf(data.getApplicantStepId()), appointmentId);
			}	
			
			String xmlFile = UserUtils.getXMLforActiveUsers(interviewers);

			request.setAttribute("xmlFile", xmlFile);
			request.setAttribute("activeUsers", interviewers);
		} catch (Exception e) {
			TPLogger.getLogger().error("Error in getting XML:", e);
		}

		return mapping.findForward(forward);
	}
	
	public ActionForward editAppointment(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			CalendarForm form = (CalendarForm) actionForm;
			CalendarManager calendarManager = new CalendarManager();
			
			DateFormat userTimeZoneFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
			userTimeZoneFormatter.setTimeZone(TimeZone.getTimeZone(form.getTimeZone()));

			String appointmentFromDate = form.getAppointmentFromDate();
			
			try {
				if(userTimeZoneFormatter.parse(appointmentFromDate).before(new Date()))
				{
					String[] errors = new String[] { "calendar.error.can_not_set_appointments_in_past" };
					xmlFile = calendarManager.getErrorsXml(errors);
				}else{
					int doSendEmail = Integer.parseInt(form.getIsSendAppointmentNotification());
					
					String oldTimeZoneId = TimeZone.getDefault().getID();
					String timeZoneId = form.getTimeZone();
					ThreadLocalContextObject obj = new ThreadLocalContextObject();
					obj.setTimeZone(timeZoneId);
					MyThreadLocal.unset();
					MyThreadLocal.set(obj);
					String userId = (String) request.getSession(false).getAttribute("userId");
					
					calendarManager.editAppointment(form.getSelectedApplicant(), form.getAppointmentId(), form.getAppointmentFromDate(), form.getAppointmentToDate(), form.getRemindMe(), form
							.getRemindInterviewers(), form.getRemindCandidate(), form.getSmsRemindMe(), form.getSmsRemindInterviewers(), form.getSmsRemindCandidate(), form.getRemindMeTemplate(), form
							.getRemindInterviewersTemplate(), form.getRemindCandidateTemplate(), form.getSmsRemindMeTemplate(), form.getSmsRemindInterviewersTemplate(), form.getSmsRemindCandidateTemplate(),
							form.getInterviewers(), form.getStatus(), userId, doSendEmail,form.getInterviewMode(),form.getDetailsInterviewMode());
					
					obj = new ThreadLocalContextObject();
					obj.setTimeZone(oldTimeZoneId);
					MyThreadLocal.unset();
					MyThreadLocal.set(obj);
				}
			} catch (Exception e) {
				TPLogger.getLogger().error("unable to Parse appointment date", e);
			}
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward updateAppointmentStatus(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			CalendarForm form = (CalendarForm) actionForm;
			String userId = (String) request.getSession(false).getAttribute("userId");
			CalendarManager calendarManager = new CalendarManager();
			calendarManager.updateAppointmentStatus(form.getSelectedApplicant(), form.getStatus(), userId);
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}

	public ActionForward deleteAppointment(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		String forward = "xmlFile";
		String xmlFile = "";

		if (!SessionManager.isLoginFound(mapping, actionForm, request, response, this)) {
			xmlFile = Utils.getXMLForSessionExpiry();
		} else {
			CalendarForm form = (CalendarForm) actionForm;
			int doSendEmail = Integer.parseInt(form.getIsSendAppointmentNotification());
			CalendarManager calendarManager = new CalendarManager();
			calendarManager.deleteAppointment(form.getAppointmentId(), doSendEmail,form.getApplicantPositionId() , form.getSelectedApplicant() );
		}
		request.setAttribute("xmlFile", xmlFile);

		return mapping.findForward(forward);
	}
}
