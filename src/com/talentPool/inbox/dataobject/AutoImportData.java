/**
 * 
 */
package com.talentPool.inbox.dataobject;

import java.util.ArrayList;
import java.util.Date;

import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.custom.dataobject.CustomFieldData;

/**
 * @author shivprasad
 * 
 */
public class AutoImportData extends SimpleDataObject {
	ArrayList<AutoImportEducationData> rawEducationDetails;
	ArrayList<EducationalData> educationalDetails;
	ArrayList<CustomFieldData> customFields;
	ArrayList<EmploymentHistoryData> employmentHistoryDetails;
	
	public void setName(String name) {
		setAttribute("name", name);
	}

	public String getName() {
		return getString("name");
	}

	public void setRegistrationCode(String registrationCode) {
		setAttribute("registrationCode", registrationCode);
	}

	public String getRegistrationCode() {
		return getString("registrationCode");
	}

	public void setSource(String source) {
		setAttribute("source", source);
	}

	public String getSource() {
		return getString("source");
	}

	public void setSourceId(String sourceId) {
		setAttribute("sourceId", sourceId);
	}

	public String getSourceId() {
		return getString("sourceId");
	}

	public void setWorkingSince(String workingSince) {
		setAttribute("workingSince", workingSince);
	}

	public String getWorkingSince() {
		return getString("workingSince");
	}

	public void setWorkingSinceDate(Date workingSinceDate) {
		setAttribute("workingSinceDate", workingSinceDate);
	}

	public Date getWorkingSinceDate() {
		return getDate("workingSinceDate");
	}

	public void setEmail1(String email1) {
		setAttribute("email1", email1);
	}

	public String getEmail1() {
		return getString("email1");
	}

	public void setEmail2(String email2) {
		setAttribute("email2", email2);
	}

	public String getEmail2() {
		return getString("email2");
	}

	public void setLocation(String location) {
		setAttribute("location", location);
	}

	public String getLocation() {
		return getString("location");
	}

	public void setCellPhone(String cellPhone) {
		setAttribute("cellPhone", cellPhone);
	}

	public String getCellPhone() {
		return getString("cellPhone");
	}

	public void setPhone1(String phone1) {
		setAttribute("phone1", phone1);
	}

	public String getPhone1() {
		return getString("phone1");
	}

	public void setPhone2(String phone2) {
		setAttribute("phone2", phone2);
	}

	public String getPhone2() {
		return getString("phone2");
	}

	public void setCurrentEmployer(String currentEmployer) {
		setAttribute("currentEmployer", currentEmployer);
	}

	public String getCurrentEmployer() {
		return getString("currentEmployer");
	}

	public void setCTC(String CTC) {
		setAttribute("CTC", CTC);
	}

	public String getCTC() {
		return getString("CTC");
	}

	public void setECTC(String ECTC) {
		setAttribute("ECTC", ECTC);
	}

	public String getECTC() {
		return getString("ECTC");
	}

	public void setSkills(String skills) {
		setAttribute("skills", skills);
	}

	public String getSkills() {
		return getString("skills");
	}

	public void setSkillIds(String skillIds) {
		setAttribute("skillIds", skillIds);
	}

	public String getSkillIds() {
		return getString("skillIds");
	}

	/**
	 * @return the educationalDetails
	 */
	public ArrayList<EducationalData> getEducationalDetails() {
		return educationalDetails;
	}

	/**
	 * @param educationalDetails
	 *            the educationalDetails to set
	 */
	public void setEducationalDetails(ArrayList<EducationalData> educationalDetails) {
		this.educationalDetails = educationalDetails;
	}
	
	/**
	 * @return the rawEducationDetails
	 */
	public ArrayList<AutoImportEducationData> getRawEducationDetails() {
		return rawEducationDetails;
	}

	/**
	 * @param rawEducationDetails
	 *            the rawEducationDetails to set
	 */
	public void setRawEducationDetails(ArrayList<AutoImportEducationData> rawEducationDetails) {
		this.rawEducationDetails = rawEducationDetails;
	}

	/**
	 * @return the customFields
	 */
	public ArrayList<CustomFieldData> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customFields
	 *            the customFields to set
	 */
	public void setCustomFields(ArrayList<CustomFieldData> customFields) {
		this.customFields = customFields;
	}

	public void setPositionCode(String positionCode) {
		setAttribute("positionCode", positionCode);
	}

	public String getPositionCode() {
		return getString("positionCode");
	}

	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}

	public String getPositionTitle() {
		return getString("positionTitle");
	}

	public void setNote(String note) {
		setAttribute("note", note);
	}

	public String getNote() {
		return getString("note");
	}
	
	public void setUserName(String userName) {
		setAttribute("userName", userName);
	}

	public String getUserName() {
		return getString("userName");
	}
	
	public void setNoticePeriod(String noticePeriod) {
		setAttribute("noticePeriod", noticePeriod);
	}

	public String getNoticePeriod() {
		return getString("noticePeriod");
	}

	/**
	 * @return the employeeCode
	 */
	public String getEmployeeCode() {
		return getString("employeeCode");
	}

	/**
	 * @param employeeCode the employeeCode to set
	 */
	public void setEmployeeCode(String employeeCode) {
		setAttribute("employeeCode", employeeCode);
	}
	
	public void setDateOfBirth(String dateOfBirth) {
		setAttribute("dateOfBirth", dateOfBirth);
	}

	public String getDateOfBirth() {
		return getString("dateOfBirth");
	}
	
	public void setPassport(String passport) {
		setAttribute("passport", passport);
	}

	public String getPassport() {
		return getString("passport");
	}
	
	public void setResumeTypeId(String resumeTypeId) {
		setAttribute("resumeTypeId", resumeTypeId);
	}

	public String getResumeTypeId() {
		return getString("resumeTypeId");
	}
	
	/**
	 * @return the employmentHistoryDetails
	 */
	public ArrayList<EmploymentHistoryData> getEmploymentHistoryDetails() {
		return employmentHistoryDetails;
	}

	/**
	 * @param employmentHistoryDetails the employmentHistoryDetails to set
	 */
	public void setEmploymentHistoryDetails(ArrayList<EmploymentHistoryData> employmentHistoryDetails) {
		this.employmentHistoryDetails = employmentHistoryDetails;
	}
	

}
