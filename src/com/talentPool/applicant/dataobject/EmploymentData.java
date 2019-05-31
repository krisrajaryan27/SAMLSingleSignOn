/**
 * 
 */
package com.talentPool.applicant.dataobject;

import java.sql.Date;

import com.talentPool.common.db.SimpleDataObject;

/**
 * @author shivprasad
 * 
 */
public class EmploymentData extends SimpleDataObject {
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
	 * @return Returns the employmentId.
	 */
	public int getEmploymentId() {
		return getInt("employmentId");
	}

	/**
	 * @param employmentId
	 *            The employmentId to set.
	 */
	public void setEmploymentId(int employmentId) {
		setAttribute("employmentId", new Integer(employmentId));
	}

	/**
	 * @return employmentFrom Date
	 */
	public Date getEmploymentFrom() {
		return getDate("employmentFrom");
	}

	/**
	 * @param employmentFrom
	 *            The employmentFrom to set.
	 */
	public void setEmploymentFrom(Date employmentFrom) {
		setAttribute("employmentFrom", employmentFrom);
	}

	/**
	 * @return employmentTo Date
	 */
	public Date getEmploymentTo() {
		return getDate("employmentTo");
	}

	/**
	 * @param employmentTo
	 *            The employmentTo to set.
	 */
	public void setEmploymentTo(Date employmentTo) {
		setAttribute("employmentTo", employmentTo);
	}

	/**
	 * @return employmentCompany
	 */
	public String getEmploymentCompany() {
		return getString("employmentCompany");
	}

	/**
	 * @param employmentCompany
	 */
	public void setEmploymentCompany(String employmentCompany) {
		setAttribute("employmentCompany", employmentCompany);
	}

	/**
	 * @return employmentResponsibilities
	 */
	public String getEmploymentResponsibilities() {
		return getString("employmentResponsibilities");
	}

	/**
	 * @param employmentResponsibilities
	 */
	public void setEmploymentResponsibilities(String employmentResponsibilities) {
		setAttribute("employmentResponsibilities", employmentResponsibilities);
	}

	/**
	 * @return Returns the employmentLocation.
	 */
	public String getEmploymentLocation() {
		return getString("employmentLocation");
	}

	/**
	 * @param employmentLocation
	 * 			The employmentLocation to set.
	 */
	public void setEmploymentLocation(String employmentLocation) {
		setAttribute("employmentLocation", employmentLocation);
	}
}
