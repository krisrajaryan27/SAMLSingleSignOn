/**
 * 
 */
package com.talentPool.desktop.dataobjects;

import java.util.ArrayList;
import java.util.Date;

import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.custom.dataobject.CustomFieldData;


/**
 * @author Ajeet
 * 
 */
public class BulkImportResultData extends SimpleDataObject {
	
	private ArrayList<CustomFieldData> customFields;
	private ArrayList<EducationalData> educationalDetails;
	
	public String getResultId() {
		return getString("resultId");
	}

	public void setResultId(String resultId) {
		setAttribute("resultId", resultId);
	}

	public String getSessionId() {
		return getString("sessionId");
	}

	public String getEmailId() {
		return getString("emailId");
	}

	public Date getDateCreated() {
		return getDate("dateCreated");
	}

	public String getParsedName() {
		return getString("parsedName");
	}

	public void setParsedName(String parsedName) {
		setAttribute("parsedName", parsedName);
	}

	public String getParsedEmail2() {
		return getString("parsedEmail2");
	}

	public void setParsedEmail2(String parsedEmail2) {
		setAttribute("parsedEmail2", parsedEmail2);
	}

	public String getParsedEmail1() {
		return getString("parsedEmail1");
	}

	public void setParsedEmail1(String parsedEmail1) {
		setAttribute("parsedEmail1", parsedEmail1);
	}

	public String getParsedCellPhone() {
		return getString("parsedCellPhone");
	}

	public void setParsedCellPhone(String parsedCellPhone) {
		setAttribute("parsedCellPhone", parsedCellPhone);
	}

	public String getParsedWorkPhone() {
		return getString("parsedWorkPhone");
	}

	public void setParsedWorkPhone(String parsedWorkPhone) {
		setAttribute("parsedWorkPhone", parsedWorkPhone);
	}

	public String getParsedHomePhone() {
		return getString("parsedHomePhone");
	}

	public void setParsedHomePhone(String parsedHomePhone) {
		setAttribute("parsedHomePhone", parsedHomePhone);
	}
	
	public String getParsedOriginalResumePath() {
		return getString("parsedOriginalResumePath");
	}

	public String getParsedOriginalDocPath() {
		return getString("parsedOriginalDocPath");
	}

	public String getParsedTextResume() {
		return getString("parsedTextResume");
	}

	public String getParsedSkills() {
		return getString("parsedSkills");
	}

	public void setParsedSkills(String parsedSkills) {
		setAttribute("parsedSkills", parsedSkills);
	}

	public String getSourceId() {
		return getString("sourceId");
	}

	public String getApplicantCity() {
		return getString("applicantCity");
	}

	public String getApplicantWorkingSince() {
		return getString("applicantWorkingSince");
	}

	public String getApplicantIsFresher() {
		return getString("applicantIsFresher");
	}
	
	public String getCurrentEmployer() {
		return getString("currentEmployer");
	}
	
	public String getCurrentCtc() {
		return getString("currentCtc");
	}
	
	public String getExpectedCtc() {
		return getString("expectedCtc");
	}
	
	public String getTimeToJoin() {
		return getString("timeToJoin");
	}
	
	public String getNote() {
		return getString("note");
	}


	public String getEmailFrom() {
		return getString("emailFrom");
	}

	public String getEmailSubject() {
		return getString("emailSubject");
	}

	public String getParsedSkillIds() {
		return getString("parsedSkillIds");
	}

	public void setParsedSkillIds(String parsedSkillIds) {
		setAttribute("parsedSkillIds", parsedSkillIds);
	}

	public String getFileName() {
		return getString("fileName");
	}
	
	public String getEntryId() {
		return getString("entryId");
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
	
	
	public void setSourceId(int sourceId) {
		setAttribute("sourceId", new Integer(sourceId));
	}
	
	public void setApplicantCity(String applicantCity) {
		setAttribute("applicantCity", applicantCity);
	}
	
	public void setApplicantWorkingSince(Date applicantWorkingSince) {
		setAttribute("applicantWorkingSince", applicantWorkingSince);
	}
		
	public void setApplicantIsFresher(String applicantIsFresher) {
		setAttribute("applicantIsFresher", applicantIsFresher);
	}
	
	public void setCurrentEmployer(String currentEmployer) {
		setAttribute("currentEmployer", currentEmployer);
	}
	
	public void setCurrentCtc(String currentCtc) {
		setAttribute("currentCtc", currentCtc);
	}

	public void setExpectedCtc(String expectedCtc) {
		setAttribute("expectedCtc", expectedCtc);
	}
	
	public void setTimeToJoin(String timeToJoin) {
		setAttribute("timeToJoin", timeToJoin);
	}
	
	public void setNote(String note) {
		setAttribute("note", note);
	}	
	
}
