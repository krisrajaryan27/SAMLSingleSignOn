/**
 * 
 */
package com.talentPool.userConfiguration.form;

import com.talentPool.common.base.TPActionForm;

/**
 * @author Ajeet
 *
 */
public class UserConfigurationForm extends TPActionForm{
	
	private String applicantId;
	

	/**
	 * @return the applicantId
	 */
	public String getApplicantId() {
		return applicantId;
	}

	/**
	 * @param applicantId the applicantId to set
	 */
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
}
