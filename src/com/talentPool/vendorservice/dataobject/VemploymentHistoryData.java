package com.talentPool.vendorservice.dataobject;

public class VemploymentHistoryData {
	private String employerId;
	private String employerName;
	private String designationId;
	private String designationName;
	private String employerExperience;
	private String employerFromDate;
	private String employerToDate;
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

	public String getEmployerId() {
		return employerId;
	}
	public void setEmployerId(String employerId) {
		this.employerId = employerId;
	}
	public String getEmployerName() {
		return employerName;
	}
	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
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
	public String getEmployerFromDate() {
		return employerFromDate;
	}
	public void setEmployerFromDate(String employerFromDate) {
		this.employerFromDate = employerFromDate;
	}
	public String getEmployerToDate() {
		return employerToDate;
	}
	public void setEmployerToDate(String employerToDate) {
		this.employerToDate = employerToDate;
	}
}
