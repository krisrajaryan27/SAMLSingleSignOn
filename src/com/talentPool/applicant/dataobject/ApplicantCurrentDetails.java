/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.applicant.dataobject;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author PraveenK
 * @since  Apr 30, 2012
 */
public class ApplicantCurrentDetails extends SimpleDataObject {
	
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
	public String getCurrentCTC() {
		return getString("currentCTC");
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setCurrentCTC(String currentCTC) {
		setAttribute("currentCTC", currentCTC);
	}

	/**
	 * @return Returns the applicantName.
	 */
	public Integer getCurrentBasic() {
		return getInt("currentBasic");
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setCurrentBasic(Integer currentBasic) {
		setAttribute("currentBasic", currentBasic);
	}
	
	/**
	 * @return Returns the applicantName.
	 */
	public String getCurrentDesignation() {
		return getString("currentDesignation");
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setCurrentDesignation(String currentDesignation) {
		setAttribute("currentDesignation", currentDesignation);
	}
	
	/**
	 * @return Returns the applicantName.
	 */
	public String getCurrentLevel() {
		return getString("currentLevel");
	}

	/**
	 * @param applicantName
	 *            The applicantName to set.
	 */
	public void setCurrentLevel(String currentLevel) {
		setAttribute("currentLevel", currentLevel);
	}
}
