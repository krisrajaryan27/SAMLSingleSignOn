/**
 * 
 */
package com.talentPool.reports.views.custom;

import java.util.Date;
import java.util.HashMap;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author ajeet
 *
 */
public class CandidateOffersView extends SimpleDataObject {	
	
	HashMap<String, String> customFields = new HashMap<String, String>();

	
	public String getApplicantId() {
		return getString("applicantId");
	}
	
	public String getApplicantName() {
		return getString("applicantName");
	}
	
	public String getApplicantDateJoined() {
		return getString("applicantDateJoined");
	}
	
	public String getApplicantEmail() {
		return getString("applicantEmail");
	}
	
	public String getApplicantCellPhone() {
		return getString("applicantCellPhone");
	}
	
	public String getApplicantCurrentEmployer() {
		return getString("applicantCurrentEmployer");
	}
	
	public String getCurrentCtc() {
		return getString("currentCtc");
	}
	
	public String getNoticePeriod() {
		return getString("noticePeriod");
	}
	
	public String getOfferedCtc() {
		return getString("offeredCtc");
	}
	
	public String getSource() {
		return getString("source");
	}
	
	public String getDegree() {
		return getString("degree");
	}
	
	public String getInstitute() {
		return getString("institute");
	}
	
	public String getPositionStepTitle() {
		return getString("positionStepTitle");
	}

	/**
	 * @return the customFields
	 */
	public HashMap<String, String> getCustomFields() {
		return customFields;
	}

	/**
	 * @param customFields the customFields to set
	 */
	public void setCustomFields(HashMap<String, String> customFields) {
		this.customFields = customFields;
	}

	
}
