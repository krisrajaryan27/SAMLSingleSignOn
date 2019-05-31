/**
 * 
 */
package com.talentPool.otherApplications.rest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Shantanu
 *
 */
@XmlRootElement
public class ApplicantDetailModel {
	
	public ApplicantDetailModel(){		
	}
	
	public ApplicantDetailModel(boolean flag){
		applicantId="applicantId1";
		applicantName="applicantName1";
		applicantCellPhone="applicantCellPhone";
		applicantCellPhoneIsInvalid="applicantCellPhoneIsInvalid";
		applicantHomePhone="applicantHomePhone";
		applicantHomePhoneIsInvalid="applicantHomePhoneIsInvalid";
		applicantWorkPhone="applicantWorkPhone";
		applicantWorkPhoneIsInvalid="applicantWorkPhoneIsInvalid";
		applicantEmail1="applicantEmail1";
		applicantEmail2="applicantEmail2";
		applicantCity="applicantCity";
		applicantCurrentEmployer="applicantCurrentEmployer";		
		dateOfBirth="dateOfBirth";
		passportNumber="passportNumber";		
		applicantSourceId="applicantSourceId";
		applicantSourceTitle="applicantSourceTitle";		
		applicantDateJoined="applicantDateJoined";	
		applicantHRMSCode="applicantHRMSCode";		
		applicantJoined="applicantJoined";		
		applicantOriginalDocPath="applicantOriginalDocPath";
		applicantOriginalResumePath="applicantOriginalResumePath";
		applicantPositionId="applicantPositionId";
		applicantSkills="applicantSkills";		
		applicantStatus="applicantStatus";
		applicantStepId="applicantStepId";
		applicantWorkingSince="applicantWorkingSince";	
		basicOffered="basicOffered";
		ctcOffered="ctcOffered";
		currentCTC="currentCTC";		
		designationOffered="designationOffered";
		expectedCTC="expectedCTC";
		levelOffered="levelOffered";
		noticePeriod="noticePeriod";
		positionStepLevel="positionStepLevel";
		resumeType="resumeType";
		resumeTypeId="resumeTypeId";
		sourceTypeId="sourceTypeId";
		stepScheduled="stepScheduled";
		vendorId="vendorId";
		employeeCode="employeeCode";
		
		customFields = new ArrayList<CustomFieldDetailModel>();
		educationDetails = new ArrayList<EducationDetailModel>();
		salaryComponents = new ArrayList<SalaryComponentDetailModel>();
		employmentHistoryDetail = new ArrayList<EmploymentHistoryDetailModel>();
	}
	
	public List<CustomFieldDetailModel> customFields;
	public List<EducationDetailModel> educationDetails;
	public List<SalaryComponentDetailModel> salaryComponents; 
	public List<EmploymentHistoryDetailModel> employmentHistoryDetail;
	
	public String applicantId = "";
	public String applicantName = "";
	public String applicantCellPhone = "";
	public String applicantCellPhoneIsInvalid = "";
	public String applicantHomePhone = "";
	public String applicantHomePhoneIsInvalid = "";
	public String applicantWorkPhone = "";
	public String applicantWorkPhoneIsInvalid = "";
	public String applicantEmail1 = "";
	public String applicantEmail2 = "";
	public String applicantCity = "";
	public String applicantCurrentEmployer = "";
	
	public String dateOfBirth = "";
	public String passportNumber = "";
	
	public String applicantSourceId = "";
	public String applicantSourceTitle = "";
	
	public String applicantDateJoined = "";	
	public String applicantHRMSCode = "";
	
	public String applicantJoined = "";
	
	public String applicantOriginalDocPath = "";
	public String applicantOriginalResumePath = "";
	public String applicantPositionId = "";
	public String applicantSkills = "";
	
	public String applicantStatus = "";
	public String applicantStepId = "";
	public String applicantWorkingSince = "";	
	public String basicOffered = "";
	public String ctcOffered = "";
	public String currentCTC = "";
	
	public String designationOffered = "";
	public String expectedCTC = "";
	public String levelOffered = "";
	public String noticePeriod = "";
	public String positionStepLevel = "";
	public String resumeType = "";
	public String resumeTypeId = "";
	public String sourceTypeId = "";
	public String stepScheduled = "";
	public String vendorId = "";
	public String employeeCode = "";
	public String exportedDate = "";
}
