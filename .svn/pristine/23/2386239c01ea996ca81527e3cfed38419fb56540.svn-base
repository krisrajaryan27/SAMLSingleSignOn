package com.talentPool.employeeservice.dataobject;

import java.util.Date;
import java.util.Comparator;


public class EemploymentHistoryData {
	public EemploymentHistoryData(){		
	}

	public static final Comparator<EemploymentHistoryData> REVERSE_CHRONOLOGICAL =  new Comparator<EemploymentHistoryData>() {
				public int compare(EemploymentHistoryData e1, EemploymentHistoryData e2){
					return e2.getEmployerFromDate().compareTo(e1.getEmployerFromDate());
				}
	};
	
	/**
	 * @return the applicantId
	 */
	private String employerName;
	private String designationName;
	private Date employerToDate;
	private String employerExperience;
	private Date employerFromDate;
	private String designationId;
	private String employerId;
	private String grossSalary;
	private String allowance;
	private String dutiesInvolved;
	private String reasonForLeaving;
	
	public String getGrossSalary() {
		return grossSalary;
	}

	public void setGrossSalary(String grossSalary) {
		this.grossSalary = grossSalary;
	}

	public String getAllowance() {
		return allowance;
	}

	public void setAllowance(String allowance) {
		this.allowance = allowance;
	}

	public String getDutiesInvolved() {
		return dutiesInvolved;
	}

	public void setDutiesInvolved(String dutiesInvolved) {
		this.dutiesInvolved = dutiesInvolved;
	}

	public String getReasonForLeaving() {
		return reasonForLeaving;
	}

	public void setReasonForLeaving(String reasonForLeaving) {
		this.reasonForLeaving = reasonForLeaving;
	}

	

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getDesignationName() {
		return designationName;
	}

	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}

	
	
	public String getEmployerExperience() {
		return employerExperience;
	}

	public void setEmployerExperience(String employerExperience) {
		this.employerExperience = employerExperience;
	}


	public Date getEmployerToDate() {
		return employerToDate;
	}

	public void setEmployerToDate(Date employerToDate) {
		this.employerToDate = employerToDate;
	}

	public Date getEmployerFromDate() {
		return employerFromDate;
	}

	public void setEmployerFromDate(Date employerFromDate) {
		this.employerFromDate = employerFromDate;
	}

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getEmployerId() {
		return employerId;
	}

	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}

}
