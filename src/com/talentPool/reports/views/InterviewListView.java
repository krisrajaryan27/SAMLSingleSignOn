/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 * @date May 11, 2007
 */
public class InterviewListView extends SimpleDataObject {	
	/**
	 * @return the date
	 */
	public String getDate() {
		return getString("date");
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		setAttribute("date", date);
	}
	/**
	 * @return the education
	 */
	public String getEducation() {
		return getString("education");
	}
	/**
	 * @param education the education to set
	 */
	public void setEducation(String education) {
		setAttribute("education", education);
	}
	/**
	 * @return the emailPhone
	 */
	public String getEmailPhone() {
		return getString("emailPhone");
	}
	/**
	 * @param emailPhone the emailPhone to set
	 */
	public void setEmailPhone(String emailPhone) {
		setAttribute("emailPhone", emailPhone);
	}
	/**
	 * @return the experienceCurrentEmployer
	 */
	public String getExperienceCurrentEmployer() {
		return getString("experienceCurrentEmployer");
	}
	/**
	 * @param experienceCurrentEmployer the experienceCurrentEmployer to set
	 */
	public void setExperienceCurrentEmployer(String experienceCurrentEmployer) {
		setAttribute("experienceCurrentEmployer", experienceCurrentEmployer);
	}
	/**
	 * @return the interviewers
	 */
	public String getInterviewers() {
		return getString("interviewers");
	}
	/**
	 * @param interviewers the interviewers to set
	 */
	public void setInterviewers(String interviewers) {
		setAttribute("interviewers", interviewers);
	}
	/**
	 * @return the nameLocation
	 */
	public String getNameLocation() {
		return getString("nameLocation");
	}
	/**
	 * @param nameLocation the nameLocation to set
	 */
	public void setNameLocation(String nameLocation) {
		setAttribute("nameLocation", nameLocation);
	}
	/**
	 * @return the positionDepartmentStep
	 */
	public String getPositionDepartmentStep() {
		return getString("positionDepartmentStep");
	}
	/**
	 * @param positionDepartmentStep the positionDepartmentStep to set
	 */
	public void setPositionDepartmentStep(String positionDepartmentStep) {
		setAttribute("positionDepartmentStep", positionDepartmentStep);
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return getString("time");
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		setAttribute("time", time);
	}
}