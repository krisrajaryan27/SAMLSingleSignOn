/**
 * 
 */
package com.talentPool.rest.model;

/**
 * @author shantanu
 *
 */
public class EmploymentHistoryDetailModel {

	/**
	 * 
	 */
	public EmploymentHistoryDetailModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	public EmploymentHistoryDetailModel(String msg) {
		employerName=msg;
		designationName=msg;
		employerFromDate=msg;
		employerToDate=msg;
		employerExperience=msg;
	}
	
	public String employmentHistoryId;
	public String employerId;
	public String employerName;
	public String designationId;
	public String designationName;
	public String employerExperience;
	public String employerFromDate;
	public String employerToDate;
}
