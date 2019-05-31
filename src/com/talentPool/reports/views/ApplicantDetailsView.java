/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 *
 */
public class ApplicantDetailsView extends SimpleDataObject {	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return getString("email");
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return getString("location");
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return getString("name");
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return getString("phone");
	}
	/**
	 * @return the qualification
	 */
	public String getQualification() {
		return getString("qualification");
	}
	/**
	 * @return the skills
	 */
	public String getSkills() {
		return getString("skills");
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		setAttribute("email", email);
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		setAttribute("location", location);
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		setAttribute("name", name);
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		setAttribute("phone", phone);
	}
	/**
	 * @param qualification the qualification to set
	 */
	public void setQualification(String qualification) {
		setAttribute("qualification", qualification);
	}
	/**
	 * @param skills the skills to set
	 */
	public void setSkills(String skills) {
		setAttribute("skills", skills);
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		setAttribute("source", source);
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return getString("source");
	}
	/**
	 * @param currentEmployer the currentEmployer to set
	 */
	public void setCurrentEmployer(String currentEmployer) {
		setAttribute("currentEmployer", currentEmployer);
	}
	/**
	 * @return the currentEmployer
	 */
	public String getCurrentEmployer() {
		return getString("currentEmployer");
	}
	/**
	 * @param experience the experience to set
	 */
	public void setExperience(String experience) {
		setAttribute("experience", experience);
	}
	/**
	 * @return the experience
	 */
	public String getExperience() {
		return getString("experience");
	}
	
	/**
	 * @param sourceCategory the sourceCategory to set
	 */
	public void setSourceCategory(String sourceCategory) {
		setAttribute("sourceCategory", sourceCategory);
	}
	/**
	 * @return the sourceCategory
	 */
	public String getSourceCategory() {
		return getString("sourceCategory");
	}
	
}
