/**
 * Copyright 2009 - Nitman Software Pvt. Ltd. All Rights Reserved. 
 * This software is the proprietary information of Nitman Software Pvt. Ltd. 
 * Use is subject to license terms.
 */
package com.talentPool.selectionProcess.dataobject;

import java.util.Date;

import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.utils.DateUtils;

/**
 * @author PraveenK
 * @since  Aug 8, 2012
 */
public class RejectedCandidateData extends SimpleDataObject {
	
	private static final long serialVersionUID = 7442336695998489479L;

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
	 * @return
	 */
	public String getPositionTitle() {
		return getString("positionTitle");
	}

	/**
	 * @param positionTitle
	 */
	public void setPositionTitle(String positionTitle) {
		setAttribute("positionTitle", positionTitle);
	}
	
	public String getPositionCode() {
		return getString("positionCode");
	}

	public void setPositionCode(String positionCode) {
		setAttribute("positionCode", positionCode);
	}
	
	public String getStepTitle() {
		return getString("stepTitle");
	}

	public void setStepTitle(String stepTitle) {
		setAttribute("stepTitle", stepTitle);
	}
	
	public String getStepLevelName() {
		return getString("stepLevelName");
	}

	public void setStepLevelName(String stepLevelName) {
		setAttribute("stepLevelName", stepLevelName);
	}
	
	public String getRejectedBy() {
		return getString("rejectedBy");
	}

	public void setRejectedBy(String rejectedBy) {
		setAttribute("rejectedBy", rejectedBy);
	}
	
	public Date getRejectedDate() {
		return getDate("rejectedDate");
	}

	public void setRejectedDate(Date rejectedDate) {
		setAttribute("rejectedDate", rejectedDate);
	}
	
	public String getRejectedDateToDisplay() {
		return DateUtils.getSystemDateTimeFormat(getRejectedDate());
	}
	
}
