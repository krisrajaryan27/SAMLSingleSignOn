/*
 * Created on Jun 28, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.talentPool.calendar.dataobject;

import java.util.Date;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.DateUtils;

/**
 * @author pallavi
 * @date Jun 28, 2006
 */
public class AppointmentData extends SimpleDataObject {

	/**
	 * @return Returns the appointmentId.
	 */
	public String getAppointmentId() {
		return getString("appointmentId");
	}

	/**
	 * @param appointmentId
	 *            The appointmentId to set.
	 */
	public void setAppointmentId(String appointmentId) {
		setAttribute("appointmentId", appointmentId);
	}

	/**
	 * @return Returns the applicantId.
	 */
	public int getApplicantId() {
		return getInt("applicantId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(int applicantId) {
		setAttribute("applicantId", new Integer(applicantId));
	}

	/**
	 * @return Returns the applicantPositionId.
	 */
	public int getApplicantPositionId() {
		return getInt("applicantPositionId");
	}

	/**
	 * @param applicantPositionId
	 *            The applicantPositionId to set.
	 */
	public void setApplicantPositionId(int applicantPositionId) {
		setAttribute("applicantPositionId", new Integer(applicantPositionId));
	}

	/**
	 * @return Returns the applicantStepId.
	 */
	public int getApplicantStepId() {
		return getInt("applicantStepId");
	}

	/**
	 * @param applicantStepId
	 *            The applicantStepId to set.
	 */
	public void setApplicantStepId(int applicantStepId) {
		setAttribute("applicantStepId", new Integer(applicantStepId));
	}

	/**
	 * @return Returns the applicantName.
	 */
	public String getApplicantName() {
		return getString("applicantName");
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}

	/**
	 * @return Returns the interviewer.
	 */
	public String getInterviewer() {
		return getString("interviewer");
	}

	/**
	 * @param interviewer
	 *            The interviewer to set.
	 */
	public void setInterviewer(String interviewer) {
		setAttribute("interviewer", interviewer);
	}

	/**
	 * @return Returns the appointmentToDate.
	 */
	public String getAppointmentToDate() {
		return getString("appointmentToDate");
	}
	
	/**
	 * @return Returns the appointmentToTimeToDispaly.
	 */
	public String getAppointmentToTimeToDisplay() {
		try {
			return DateUtils.getSystemTimeFormat(getDate("appointmentToDate"));			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			return "";
		}
	}

	/**
	 * @param appointmentToDate
	 *            The appointmentToDate to set.
	 */
	public void setAppointmentToDate(String appointmentToDate) {
		setAttribute("appointmentToDate", appointmentToDate);
	}

	/**
	 * @return Returns the applicantPositionTitle.
	 */
	public String getApplicantPositionTitle() {
		return getString("applicantPositionTitle");
	}

	/**
	 * @param applicantPositionTitle
	 *            The applicantPositionTitle to set.
	 */
	public void setApplicantPositionTitle(String applicantPositionTitle) {
		setAttribute("applicantPositionTitle", applicantPositionTitle);
	}

	/**
	 * @return Returns the applicantStepTitle.
	 */
	public String getApplicantStepTitle() {
		return getString("applicantStepTitle");
	}

	/**
	 * @param applicantStepTitle
	 *            The applicantStepTitle to set.
	 */
	public void setApplicantStepTitle(String applicantStepTitle) {
		setAttribute("applicantStepTitle", applicantStepTitle);
	}

	/**
	 * @return Returns the appointmentSubject.
	 */
	public String getAppointmentSubject() {
		return getString("appointmentSubject");
	}

	/**
	 * @param appointmentSubject
	 *            The appointmentSubject to set.
	 */
	public void setAppointmentSubject(String appointmentSubject) {
		setAttribute("appointmentSubject", appointmentSubject);
	}

	/**
	 * @return Returns the remindMeTemplate.
	 */
	public String getRemindMeTemplate() {
		return getString("remindMeTemplate");
	}

	/**
	 * @param remindMeTemplate
	 *            The remindMeTemplate to set.
	 */
	public void setRemindMeTemplate(String remindMeTemplate) {
		setAttribute("remindMeTemplate", remindMeTemplate);
	}

	/**
	 * @return Returns the remindInterviewerTemplate.
	 */
	public String getRemindInterviewerTemplate() {
		return getString("remindInterviewerTemplate");
	}

	/**
	 * @param remindInterviewerTemplate
	 *            The remindInterviewerTemplate to set.
	 */
	public void setRemindInterviewersTemplate(String remindInterviewerTemplate) {
		setAttribute("remindInterviewerTemplate", remindInterviewerTemplate);
	}

	/**
	 * @return Returns the remindCandidateTemplate.
	 */
	public String getRemindCandidateTemplate() {
		return getString("remindCandidateTemplate");
	}

	/**
	 * @param remindCandidateTemplate
	 *            The remindCandidateTemplate to set.
	 */
	public void setRemindCandidateTemplate(String remindCandidateTemplate) {
		setAttribute("remindCandidateTemplate", remindCandidateTemplate);
	}
	
	/**
	 * @return Returns the remindMe.
	 */
	public String getRemindMe() {
		return getString("remindMe");
	}

	/**
	 * @param remindMe
	 *            The remindMe to set.
	 */
	public void setRemindMe(String remindMe) {
		setAttribute("remindMe", remindMe);
	}

	/**
	 * @return Returns the remindInterviewer.
	 */
	public String getRemindInterviewer() {
		return getString("remindInterviewer");
	}

	/**
	 * @param remindInterviewer
	 *            The remindInterviewer to set.
	 */
	public void setRemindInterviewers(String remindInterviewer) {
		setAttribute("remindInterviewer", remindInterviewer);
	}

	/**
	 * @return Returns the remindCandidate.
	 */
	public String getRemindCandidate() {
		return getString("remindCandidate");
	}

	/**
	 * @param remindCandidate
	 *            The remindCandidate to set.
	 */
	public void setRemindCandidate(String remindCandidate) {
		setAttribute("remindCandidate", remindCandidate);
	}

	/**
	 * @return Returns the appointmentCreatedBy.
	 */
	public String getAppointmentCreatedBy() {
		return getString("appointmentCreatedBy");
	}

	/**
	 * @param appointmentCreatedBy
	 *            The appointmentCreatedBy to set.
	 */
	public void setAppointmentCreatedBy(String appointmentCreatedBy) {
		setAttribute("appointmentCreatedBy", appointmentCreatedBy);
	}

	/**
	 * @return Returns the appointmentCreatorName.
	 */
	public String getAppointmentCreatorName() {
		return getString("appointmentCreatorName");
	}

	/**
	 * @param appointmentCreatorName
	 *            The appointmentCreatorName to set.
	 */
	public void setAppointmentCreatorName(String appointmentCreatorName) {
		setAttribute("appointmentCreatorName", appointmentCreatorName);
	}

	/**
	 * @return Returns the editable.
	 */
	public String getEditable() {
		return getString("editable");
	}

	/**
	 * @param editable
	 *            The editable to set.
	 */
	public void setEditable(String editable) {
		setAttribute("editable", editable);
	}

	/**
	 * @return Returns the appointmentFromDate.
	 */
	public String getAppointmentFromDate() {
		return getString("appointmentFromDate");
	}
	
	/**
	 * @return Returns the appointmentFromDateToDispaly.
	 */
	public String getAppointmentFromDateToDisplay() {
		try {
			return DateUtils.getSystemDateFormat(getDate("appointmentFromDate"));			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			return "";
		}
	}
	
	/**
	 * @return Returns the appointmentFromTimeToDispaly.
	 */
	public String getAppointmentFromTimeToDisplay() {
		try {
			return DateUtils.getSystemTimeFormat(getDate("appointmentFromDate"));			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			return "";
		}
	}
	
	

	/**
	 * @param appointmentFromDate
	 *            The appointmentFromDate to set.
	 */
	public void setAppointmentFromDate(String appointmentFromDate) {
		setAttribute("appointmentFromDate", appointmentFromDate);
	}
	
	/**
	 * @return Returns the appointmentFrom.
	 */
	public Date getAppointmentFrom() {
		return getDate("appointmentFromDate");
	}

	public Date getAppointmentFromDateTime() {
		return (Date) getTimestamp("appointmentFromDate");
	}

	public Date getAppointmentToDateTime() {
		return (Date) getTimestamp("appointmentToDate");
	}
	
	/**
	 * @return Returns the appointmentFromDateTime To Display in System Datetimeformat.
	 */
	public String getAppointmentFromDateTimeToDipslay() {
		return DateUtils.getSystemDateTimeFormat(getAppointmentFrom());
	}

	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return getString("status");
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(String status) {
		setAttribute("status", status);
	}

	/**
	 * @return Returns the source.
	 */
	public String getSource() {
		return getString("source");
	}

	/**
	 * @param source
	 *            The source to set.
	 */
	public void setSource(String source) {
		setAttribute("source", source);
	}

	/**
	 * @return Returns the isAppointmentFullyEditable.
	 */
	public String getIsAppointmentFullyEditable() {
		return getString("isAppointmentFullyEditable");
	}

	/**
	 * @param isAppointmentFullyEditable
	 *            The isAppointmentFullyEditable to set.
	 */
	public void setIsAppointmentFullyEditable(String isAppointmentFullyEditable) {
		setAttribute("isAppointmentFullyEditable", isAppointmentFullyEditable);
	}

	/**
	 * @return Returns the originalResumePath.
	 */
	public String getApplicantOriginalResumePath() {
		return getString("applicantOriginalResumePath");
	}

	/**
	 * @param originalResumePath
	 *            The originalResumePath to set.
	 */
	public void setApplicantOriginalResumePath(String applicantOriginalResumePath) {
		setAttribute("applicantOriginalResumePath", applicantOriginalResumePath);
	}

	public Date getAppointmentDateCreated() {
		return (Date) getTimestamp("appointmentDateCreated");
	}

	public void setAppointmentDateCreated(Date appointmentDateCreated) {
		setAttribute("appointmentDateCreated", appointmentDateCreated);
	}

	public Date getAppointmentDateModified() {
		return (Date) getTimestamp("appointmentDateModified");
	}

	public void setAppointmentDateModified(Date appointmentDateModified) {
		setAttribute("appointmentDateModified", appointmentDateModified);
	}

	/**
	 * @return Returns the smsRemindMeTemplate.
	 */
	public String getSMSRemindMeTemplate() {
		return getString("smsRemindMeTemplate");
	}

	/**
	 * @param smsRemindMeTemplate
	 *            The smsRemindMeTemplate to set.
	 */
	public void setSMSRemindMeTemplate(String smsRemindMeTemplate) {
		setAttribute("smsRemindMeTemplate", smsRemindMeTemplate);
	}

	/**
	 * @return Returns the smsRemindInterviewerTemplate.
	 */
	public String getSMSRemindInterviewerTemplate() {
		return getString("smsRemindInterviewerTemplate");
	}

	/**
	 * @param smsRemindInterviewerTemplate
	 *            The smsRemindInterviewerTemplate to set.
	 */
	public void setSMSRemindInterviewerTemplate(String smsRemindInterviewerTemplate) {
		setAttribute("smsRemindInterviewerTemplate", smsRemindInterviewerTemplate);
	}

	/**
	 * @return Returns the smsRemindCandidateTemplate.
	 */
	public String getSMSRemindCandidateTemplate() {
		return getString("smsRemindCandidateTemplate");
	}

	/**
	 * @param smsRemindCandidateTemplate
	 *            The smsRemindCandidateTemplate to set.
	 */
	public void setSMSRemindCandidateTemplate(String smsRemindCandidateTemplate) {
		setAttribute("smsRemindCandidateTemplate", smsRemindCandidateTemplate);
	}
	
	/**
	 * @return Returns the smsRemindMe.
	 */
	public String getSMSRemindMe() {
		return getString("smsRemindMe");
	}

	/**
	 * @param smsRemindMe
	 *            The smsRemindMe to set.
	 */
	public void setSMSRemindMe(String smsRemindMe) {
		setAttribute("smsRemindMe", smsRemindMe);
	}

	/**
	 * @return Returns the smsRemindInterviewer.
	 */
	public String getSMSRemindInterviewer() {
		return getString("smsRemindInterviewer");
	}

	/**
	 * @param smsRemindInterviewer
	 *            The smsRemindInterviewer to set.
	 */
	public void setSMSRemindInterviewer(String smsRemindInterviewer) {
		setAttribute("smsRemindInterviewer", smsRemindInterviewer);
	}

	/**
	 * @return Returns the smsRemindCandidate.
	 */
	public String getSMSRemindCandidate() {
		return getString("smsRemindCandidate");
	}

	/**
	 * @param smsRemindCandidate
	 *            The smsRemindCandidate to set.
	 */
	public void setSMSRemindCandidate(String smsRemindCandidate) {
		setAttribute("smsRemindCandidate", smsRemindCandidate);
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return getString("type");
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		setAttribute("type", type);
	}
	
	/**
	 * @return the interviewMode
	 */
	public String getInterviewMode() {
		return getString("interviewMode");
	}

	/**
	 * @param type the type to set
	 */
	public void setInterviewMode(String interviewMode) {
		setAttribute("interviewMode", interviewMode);
	}
	/**
	 * @return the detailsInterviewMode
	 */
	public String getDetailsInterviewMode() {
		return getString("detailsInterviewMode");
	}

	/**
	 * @param type the detailsInterviewMode to set
	 */
	public void setDetailsInterviewMode(String detailsInterviewMode) {
		setAttribute("detailsInterviewMode", detailsInterviewMode);
	}
}
