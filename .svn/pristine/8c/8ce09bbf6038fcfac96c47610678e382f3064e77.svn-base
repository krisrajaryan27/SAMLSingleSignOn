/**
 * 
 */
package com.talentPool.applicant.dataobject;

import java.sql.Date;
import java.util.ArrayList;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class ApplicantDuplicateSearchData extends SimpleDataObject {
	ArrayList<String> matchedFields = new ArrayList<String>();
	ArrayList<String> matchedFieldValues = new ArrayList<String>();

	/**
	 * @return Returns the applicantId.
	 */
	public String getApplicantId() {
		return getId("applicantId");
	}

	/**
	 * @param applicantId
	 *            The applicantId to set.
	 */
	public void setApplicantId(String applicantId) {
		setAttribute("applicantId", applicantId);
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

	public int getPriority() {
		return getInt("priority");
	}

	public void setPriority(int priority) {
		setAttribute("priority", priority);
	}

	/**
	 * @return the matchedFields
	 */
	public ArrayList<String> getMatchedFields() {
		return matchedFields;
	}
	
	/**
	 * @return the matchedFieldVales
	 */
	public ArrayList<String> getMatchedFieldValues() {
		return matchedFieldValues;
	}

	/**
	 * @return Returns the applicantDateImported.
	 */
	public Date getResumeDateUpdated() {
		return getDate("resumeDateUpdated");
	}

	/**
	 * @param applicantDateImported
	 *            The applicantDateImported to set.
	 */
	public void setResumeDateUpdated(Date resumeDateUpdated) {
		setAttribute("resumeDateUpdated", resumeDateUpdated);
	}

	/**
	 * @param matchedFields
	 *            the matchedFields to set
	 */
	public void setMatchedFields(ArrayList<String> matchedFields) {
		this.matchedFields = matchedFields;
	}
	
	/**
	 * @param matchedFieldValues
	 *            the matchedFieldValues to set
	 */
	public void setMatchedFieldValues(ArrayList<String> matchedFieldValues) {
		this.matchedFieldValues = matchedFieldValues;
	}	

	/**
	 * @param originalResumePath
	 */
	public void setOriginalResumePath(String originalResumePath) {
		setAttribute("originalResumePath", originalResumePath);
	}

	/**
	 * @param originalResumePath
	 * @return
	 */
	public String getOriginalResumePath() {
		return getString("originalResumePath");
	}
	
	public String getIsConfidential() {
		return getString("isConfidential");
	}
	
	public void setIsConfidential(String isConfidential) {
		setAttribute("isConfidential", isConfidential);
	}
	
	
	public void setApplicantStatus(String applicantStatus) {
		setAttribute("applicantStatus", applicantStatus);
	}
	
	public String getApplicantStatus() {
		return getString("applicantStatus");
	}
	
	public void setProcessStatus(String processStatus) {
		setAttribute("processStatus", processStatus);
	}
	
	public String getProcessStatus() {
		return getString("processStatus");
	}
	
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}
	
	public String getPositionTitle() {
		return getString("positionTitle");
	}
	
	public void setProcessMovedDate(Date processMovedDate) {
		setAttribute("processMovedDate", processMovedDate);
	}
	
	public Date getProcessMovedDate() {
		return getDate("processMovedDate");
	}
	
	public void setPositionCode(String positionCode) {
		setAttribute("positionCode", positionCode);
	}
	
	public String getPositionCode() {
		return getString("positionCode");
	}
}
