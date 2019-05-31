/*
 * Created on Jun 23, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.calendar.form;

import java.util.List;

import com.talentPool.common.base.TPActionForm;
;

/**
 * @author pallavi
 * @date Jun 23, 2006
 */
public class CalendarForm extends TPActionForm {
	// Calendar Home.
	private String currentWeekDays = null;
	// End Calendar Home.
	
	// New Appointment.
	private List positions = null;
	private List applicants = null;
	
	private String selectedPosition = null;
	private String selectedApplicant = null;
	private String applicantPositionId = null;
	private String applicantStepId = null;
	private String remindMe = null;
	private String remindInterviewers = null;
	private String remindCandidate = null;
	private String smsRemindMe = null;
	private String smsRemindInterviewers = null;
	private String smsRemindCandidate = null;
	private String remindMeTemplate = null;
	private String remindInterviewersTemplate = null;
	private String remindCandidateTemplate = null;
	private String smsRemindMeTemplate = null;
	private String smsRemindInterviewersTemplate = null;
	private String smsRemindCandidateTemplate = null;
	private String appointmentDate = null;
	private String appointmentFromDate = null;
	private String appointmentToDate = null;
	private String interviewers = null;
	private String subject = null;
	private String status = null;
	private String isSendAppointmentNotification = null;
	private String jsArrayPositions;
	//field added for Xoriant
	private String detailsInterviewMode;
	private String interviewMode;
	// End New Appointment.	
	
	// Printable View
	private String fromTime = null;
	private String toTime = null;
	// End Printable View
	
	// Edit Appointment
	private String appointmentId = null;
	private String isAppointmentFullyEditable = null;

	private String timeZone;
	// End Edit Appointment
	/**
	 * @return Returns the applicantPositionId.
	 */
	public String getApplicantPositionId() {
		return applicantPositionId;
	}
	
	/**
	 * @param applicantPositionId 
	 * 			The applicantPositionId to set.
	 */
	public void setApplicantPositionId(String applicantPositionId) {
		this.applicantPositionId = applicantPositionId;
	}
	
	/**
	 * @return Returns the applicants.
	 */
	public List getApplicants() {
		return applicants;
	}
	
	/**
	 * @param applicants 
	 * 			The applicants to set.
	 */
	public void setApplicants(List applicants) {
		this.applicants = applicants;
	}
	
	/**
	 * @return Returns the applicantStepId.
	 */
	public String getApplicantStepId() {
		return applicantStepId;
	}
	
	/**
	 * @param applicantStepId 
	 * 			The applicantStepId to set.
	 */
	public void setApplicantStepId(String applicantStepId) {
		this.applicantStepId = applicantStepId;
	}
	
	/**
	 * @return Returns the appointmentDate.
	 */
	public String getAppointmentDate() {
		return appointmentDate;
	}
	
	/**
	 * @param appointmentDate 
	 * 			The appointmentDate to set.
	 */
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	
	/**
	 * @return Returns the appointmentFromDate.
	 */
	public String getAppointmentFromDate() {
		return appointmentFromDate;
	}
	
	/**
	 * @param appointmentFromDate 
	 * 			The appointmentFromDate to set.
	 */
	public void setAppointmentFromDate(String appointmentFromDate) {
		this.appointmentFromDate = appointmentFromDate;
	}
	
	/**
	 * @return Returns the appointmentToDate.
	 */
	public String getAppointmentToDate() {
		return appointmentToDate;
	}
	
	/**
	 * @param appointmentToDate 
	 * 			The appointmentToDate to set.
	 */
	public void setAppointmentToDate(String appointmentToDate) {
		this.appointmentToDate = appointmentToDate;
	}
	
	/**
	 * @return Returns the currentWeekDays.
	 */
	public String getCurrentWeekDays() {
		return currentWeekDays;
	}
	
	/**
	 * @param currentWeekDays 
	 * 			The currentWeekDays to set.
	 */
	public void setCurrentWeekDays(String currentWeekDays) {
		this.currentWeekDays = currentWeekDays;
	}
	
	/**
	 * @return Returns the interviewers.
	 */
	public String getInterviewers() {
		return interviewers;
	}
	
	/**
	 * @param interviewers 
	 * 			The interviewers to set.
	 */
	public void setInterviewers(String interviewers) {
		this.interviewers = interviewers;
	}
	
	/**
	 * @return Returns the positions.
	 */
	public List getPositions() {
		return positions;
	}
	
	/**
	 * @param positions 
	 * 			The positions to set.
	 */
	public void setPositions(List positions) {
		this.positions = positions;
	}
	
	/**
	 * @return Returns the remindCandidate.
	 */
	public String getRemindCandidate() {
		return remindCandidate;
	}
	
	/**
	 * @param remindCandidate 
	 * 			The remindCandidate to set.
	 */
	public void setRemindCandidate(String remindCandidate) {
		this.remindCandidate = remindCandidate;
	}
	
	/**
	 * @return Returns the remindInterviewers.
	 */
	public String getRemindInterviewers() {
		return remindInterviewers;
	}
	
	/**
	 * @param remindInterviewers 
	 * 			The remindInterviewers to set.
	 */
	public void setRemindInterviewers(String remindInterviewers) {
		this.remindInterviewers = remindInterviewers;
	}
	
	/**
	 * @return Returns the remindMe.
	 */
	public String getRemindMe() {
		return remindMe;
	}
	
	public String getDetailsInterviewMode() {
		return detailsInterviewMode;
	}

	public void setDetailsInterviewMode(String detailsInterviewMode) {
		this.detailsInterviewMode = detailsInterviewMode;
	}

	public String getInterviewMode() {
		return interviewMode;
	}

	public void setInterviewMode(String interviewMode) {
		this.interviewMode = interviewMode;
	}

	/**
	 * @param remindMe 
	 * 			The remindMe to set.
	 */
	public void setRemindMe(String remindMe) {
		this.remindMe = remindMe;
	}
	
	/**
	 * @return Returns the selectedApplicant.
	 */
	public String getSelectedApplicant() {
		return selectedApplicant;
	}
	
	/**
	 * @param selectedApplicant 
	 * 			The selectedApplicant to set.
	 */
	public void setSelectedApplicant(String selectedApplicant) {
		this.selectedApplicant = selectedApplicant;
	}
	
	/**
	 * @return Returns the selectedPosition.
	 */
	public String getSelectedPosition() {
		return selectedPosition;
	}
	
	/**
	 * @param selectedPosition 
	 * 			The selectedPosition to set.
	 */
	public void setSelectedPosition(String selectedPosition) {
		this.selectedPosition = selectedPosition;
	}
	
	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * @param subject 
	 * 			The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}	
	
	/**
	 * @return Returns the fromTime.
	 */
	public String getFromTime() {
		return fromTime;
	}
	
	/**
	 * @param fromTime 
	 * 			The fromTime to set.
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	
	/**
	 * @return Returns the toTime.
	 */
	public String getToTime() {
		return toTime;
	}
	
	/**
	 * @param toTime 
	 * 			The toTime to set.
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	
	/**
	 * @return Returns the appointmentId.
	 */
	public String getAppointmentId() {
		return appointmentId;
	}
	
	/**
	 * @param appointmentId 
	 * 			The appointmentId to set.
	 */
	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status 
	 * 			The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}	
	
	/**
	 * @return Returns the isAppointmentFullyEditable.
	 */
	public String getIsAppointmentFullyEditable() {
		return isAppointmentFullyEditable;
	}
	
	/**
	 * @param isAppointmentFullyEditable 
	 * 				The isAppointmentFullyEditable to set.
	 */
	public void setIsAppointmentFullyEditable(String isAppointmentFullyEditable) {
		this.isAppointmentFullyEditable = isAppointmentFullyEditable;
	}

	/**
	 * @return the isSendAppointmentNotification
	 */
	public String getIsSendAppointmentNotification() {
		return isSendAppointmentNotification;
	}

	/**
	 * @param isSendAppointmentNotification the isSendAppointmentNotification to set
	 */
	public void setIsSendAppointmentNotification(
			String isSendAppointmentNotification) {
		this.isSendAppointmentNotification = isSendAppointmentNotification;
	}

	/**
	 * @return the smsRemindCandidate
	 */
	public String getSmsRemindCandidate() {
		return smsRemindCandidate;
	}

	/**
	 * @param smsRemindCandidate the smsRemindCandidate to set
	 */
	public void setSmsRemindCandidate(String smsRemindCandidate) {
		this.smsRemindCandidate = smsRemindCandidate;
	}

	/**
	 * @return the smsRemindInterviewers
	 */
	public String getSmsRemindInterviewers() {
		return smsRemindInterviewers;
	}

	/**
	 * @param smsRemindInterviewers the smsRemindInterviewers to set
	 */
	public void setSmsRemindInterviewers(String smsRemindInterviewers) {
		this.smsRemindInterviewers = smsRemindInterviewers;
	}

	/**
	 * @return the smsRemindMe
	 */
	public String getSmsRemindMe() {
		return smsRemindMe;
	}

	/**
	 * @param smsRemindMe the smsRemindMe to set
	 */
	public void setSmsRemindMe(String smsRemindMe) {
		this.smsRemindMe = smsRemindMe;
	}

	/**
	 * @return the remindCandidateTemplate
	 */
	public String getRemindCandidateTemplate() {
		return remindCandidateTemplate;
	}

	/**
	 * @param remindCandidateTemplate 
	 * 			the remindCandidateTemplate to set
	 */
	public void setRemindCandidateTemplate(String remindCandidateTemplate) {
		this.remindCandidateTemplate = remindCandidateTemplate;
	}

	/**
	 * @return the remindInterviewersTemplate
	 */
	public String getRemindInterviewersTemplate() {
		return remindInterviewersTemplate;
	}

	/**
	 * @param remindInterviewersTemplate 
	 * 			the remindInterviewersTemplate to set
	 */
	public void setRemindInterviewersTemplate(String remindInterviewersTemplate) {
		this.remindInterviewersTemplate = remindInterviewersTemplate;
	}

	/**
	 * @return the remindMeTemplate
	 */
	public String getRemindMeTemplate() {
		return remindMeTemplate;
	}

	/**
	 * @param remindMeTemplate 
	 * 			the remindMeTemplate to set
	 */
	public void setRemindMeTemplate(String remindMeTemplate) {
		this.remindMeTemplate = remindMeTemplate;
	}

	/**
	 * @return the smsRemindCandidateTemplate
	 */
	public String getSmsRemindCandidateTemplate() {
		return smsRemindCandidateTemplate;
	}

	/**
	 * @param smsRemindCandidateTemplate 
	 * 			the smsRemindCandidateTemplate to set
	 */
	public void setSmsRemindCandidateTemplate(String smsRemindCandidateTemplate) {
		this.smsRemindCandidateTemplate = smsRemindCandidateTemplate;
	}

	/**
	 * @return the smsRemindInterviewersTemplate
	 */
	public String getSmsRemindInterviewersTemplate() {
		return smsRemindInterviewersTemplate;
	}

	/**
	 * @param smsRemindInterviewersTemplate 
	 * 			the smsRemindInterviewersTemplate to set
	 */
	public void setSmsRemindInterviewersTemplate(
			String smsRemindInterviewersTemplate) {
		this.smsRemindInterviewersTemplate = smsRemindInterviewersTemplate;
	}

	/**
	 * @return the smsRemindMeTemplate
	 */
	public String getSmsRemindMeTemplate() {
		return smsRemindMeTemplate;
	}

	/**
	 * @param smsRemindMeTemplate 
	 * 			the smsRemindMeTemplate to set
	 */
	public void setSmsRemindMeTemplate(String smsRemindMeTemplate) {
		this.smsRemindMeTemplate = smsRemindMeTemplate;
	}

	public String getJsArrayPositions() {
		return jsArrayPositions;
	}

	public void setJsArrayPositions(String jsArrayPositions) {
		this.jsArrayPositions = jsArrayPositions;
	}
	
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
