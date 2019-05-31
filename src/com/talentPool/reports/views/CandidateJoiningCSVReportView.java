/**
 * 
 */
package com.talentPool.reports.views;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author pallavi
 * 
 */
public class CandidateJoiningCSVReportView extends SimpleDataObject {

	public String getApplicantId() {
		return getString("applicantId");
	}

	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
	}

	public String getApplicantName() {
		return getString("applicantName");
	}

	public void setApplicantName(String applicantName) {
		setAttribute("applicantName", applicantName);
	}

	public String getApplicantCity() {
		return getString("applicantCity");
	}

	public void setApplicantCity(String applicantCity) {
		setAttribute("applicantCity", applicantCity);
	}

	public String getApplicantEmail() {
		return getString("applicantEmail");
	}

	public void setApplicantEmail(String applicantEmail) {
		setAttribute("applicantEmail", applicantEmail);
	}

	public String getApplicantEmail2() {
		return getString("applicantEmail2");
	}

	public void setApplicantEmail2(String applicantEmail2) {
		setAttribute("applicantEmail2", applicantEmail2);
	}

	public String getApplicantHomePhone() {
		return getString("applicantHomePhone");
	}

	public void setApplicantHomePhone(String applicantHomePhone) {
		setAttribute("applicantHomePhone", applicantHomePhone);
	}

	public String getApplicantCellPhone() {
		return getString("applicantCellPhone");
	}

	public void setApplicantCellPhone(String applicantCellPhone) {
		setAttribute("applicantCellPhone", applicantCellPhone);
	}

	public String getApplicantWorkPhone() {
		return getString("applicantWorkPhone");
	}

	public void setApplicantWorkPhone(String applicantWorkPhone) {
		setAttribute("applicantWorkPhone", applicantWorkPhone);
	}

	public String getApplicantWorkingSince() {
		return getString("applicantWorkingSince");
	}

	public void setApplicantWorkingSince(String applicantWorkingSince) {
		setAttribute("applicantWorkingSince", applicantWorkingSince);
	}

	public String getPositionTitle() {
		return getString("positionTitle");
	}

	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}

	public String getApplicantDateCreated() {
		return getString("applicantDateCreated");
	}

	public void setApplicantDateCreated(String applicantDateCreated) {
		setAttribute("applicantDateCreated", applicantDateCreated);
	}

	public String getSourceTitle() {
		return getString("sourceTitle");
	}

	public void setSourceTitle(String sourceTitle) {
		setAttribute("sourceTitle", sourceTitle);
	}

	public String getJoiningDate() {
		return getString("joiningDate");
	}

	public void setJoiningDate(String joiningDate) {
		setAttribute("joiningDate", joiningDate);
	}

	public String getApplicantCurrentEmployer() {
		return getString("applicantCurrentEmployer");
	}

	public void setApplicantCurrentEmployer(String applicantCurrentEmployer) {
		setAttribute("applicantCurrentEmployer", applicantCurrentEmployer);
	}

	public String getCurrentCtc() {
		return getString("currentCtc");
	}

	public void setCurrentCtc(String currentCtc) {
		setAttribute("currentCtc", currentCtc);
	}

	public String getExpectedCtc() {
		return getString("expectedCtc");
	}

	public void setExpectedCtc(String expectedCtc) {
		setAttribute("expectedCtc", expectedCtc);
	}

	public String getApplicantNoticePeriod() {
		return getString("applicantNoticePeriod");
	}

	public void setApplicantNoticePeriod(String applicantNoticePeriod) {
		setAttribute("applicantNoticePeriod", applicantNoticePeriod);
	}

	public String getLevelOffered() {
		return getString("levelOffered");
	}

	public void setLevelOffered(String levelOffered) {
		setAttribute("levelOffered", levelOffered);
	}

	public String getDesignationOffered() {
		return getString("designationOffered");
	}

	public void setDesignationOffered(
			String designationOffered) {
		setAttribute("designationOffered", designationOffered);
	}

	public String getOfferedCtc() {
		return getString("offeredCtc");
	}

	public void setOfferedCtc(String offeredCtc) {
		setAttribute("offeredCtc", offeredCtc);
	}

	public String getEmployeeCode() {
		return getString("employeeCode");
	}

	public void setEmployeeCode(String employeeCode) {
		setAttribute("employeeCode", employeeCode);
	}

}
