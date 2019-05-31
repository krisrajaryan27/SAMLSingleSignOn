/**
 * 
 */
package com.talentPool.websiteservice.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.talentPool.applicant.ApplicantConstants;
import com.talentPool.applicant.dataobject.ApplicantData;
import com.talentPool.applicant.dataobject.EducationalData;
import com.talentPool.applicant.dataobject.EmploymentHistoryData;
import com.talentPool.applicant.dataobject.ImportFieldData;
import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.db.DBPreparedQuery;
import com.talentPool.common.db.SimpleDataObject;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.common.utils.Utils;
import com.talentPool.custom.dataobject.CustomFieldData;
import com.talentPool.masters.manager.MastersManager;
import com.talentPool.socialNetwork.dataobject.EducationHistory;
import com.talentPool.socialNetwork.dataobject.Person;
import com.talentPool.socialNetwork.dataobject.WorkHistory;
import com.talentPool.websiteservice.dataobject.WapplicantData;
import com.talentPool.websiteservice.dataobject.WcustomFieldData;
import com.talentPool.websiteservice.dataobject.WeducationalData;
import com.talentPool.websiteservice.dataobject.WemploymentHistoryData;

/**
 * @author pallavi
 * 
 */
public class WebsiteApplicantManager {
	public void addApplicant(WapplicantData wapplicantData) {
		// set userId
		// set original resume path
		// set original doc path
		// set source
		// set confidential
	}
	
	public WapplicantData getWapplicantDataFromPerson(Person person){
		WapplicantData wapplicantData = new WapplicantData();
		
		wapplicantData.setApplicantEmail1(person.getEmail());
		wapplicantData.setCurrentLocation(person.getLocation());
		wapplicantData.setApplicantName(person.getName());
		List<EducationHistory> eduHistory = person.getEducationHistory();
		ArrayList<WeducationalData> eduData = new ArrayList<WeducationalData>();
		ArrayList<WemploymentHistoryData> empData = new ArrayList<WemploymentHistoryData>();
		for (EducationHistory e:eduHistory){
			WeducationalData eData = new WeducationalData();
			eData.setDegreeTitle(e.getDegree());
			eData.setInstitute(e.getInstitutionName());
			eData.setMajor(e.getSpecialization());
			if (!Utils.isBlankOrNull(e.getYearOfPassing())){
				Calendar cld = Calendar.getInstance();
				cld.set(Calendar.YEAR, Integer.parseInt(e.getYearOfPassing()));
				eData.setYearOfPassing(cld.getTime());
			}
			eduData.add(eData);
		}
		List<WorkHistory> workHistory = person.getWorkHistory();
		for (WorkHistory w:workHistory){
			WemploymentHistoryData hist = new WemploymentHistoryData();
			hist.setDesignationName(w.getRole());
			hist.setEmployerName(w.getEmployerName());
			hist.setEmployerFromDate(w.getFromDate());
			hist.setEmployerToDate(w.getToDate());
			empData.add(hist);
		}
		wapplicantData.setEducationalDetails(eduData);
		wapplicantData.setEmpHistoryData(empData);
		wapplicantData.setSkills(person.getSkills());
		//List<WorkHistory> workHistory = person.getWorkHistory();
		//ArrayList
		return wapplicantData;
	}
	public WapplicantData getWapplicantDataFromApplicantData(SimpleDataObject sDo) {
		WapplicantData wapplicantData = new WapplicantData();
		ApplicantData applicantData=(ApplicantData)sDo;
		try {
			wapplicantData.setApplicantId(sDo.getString("applicantId"));
			wapplicantData.setApplicantName(sDo.getString("applicantName"));
			wapplicantData.setCurrentLocation(sDo.getString("applicantCity"));
			
			wapplicantData.setOriginalResumePath(sDo.getString("applicantOriginalResumePath"));
			wapplicantData.setCurrentEmployer(sDo.getString("applicantCurrentEmployer"));
			if (sDo.getDate("applicantWorkingSince") != null) {
				wapplicantData.setApplicantWorkingSince(sDo.getDate("applicantWorkingSince"));
			}
			String applicantJoined=sDo.getString("applicantJoined");
			if(!Utils.isBlankOrNull(applicantJoined)){
			wapplicantData.setApplicantJoined(sDo.getString("applicantJoined").equals(ApplicantConstants.APPLICANT_JOINED) ? true : false);
			}
			wapplicantData.setCurrentCTC(sDo.getString("currentCTC"));
			wapplicantData.setExpectedCTC(sDo.getString("expectedCTC"));
			Date expectedCtcdate=sDo.getDate("expectedCTCDate");
			if(expectedCtcdate!=null){
			try {
				wapplicantData.setExpectedCTCDate(sDo.getDate("expectedCTCDate"));				
			} catch (ClassCastException cce) {
				TPLogger.getLogger().error(GlobalConstants.ERROR, cce);
			}
			}
			wapplicantData.setSkills(sDo.getString("skillIds"));

			wapplicantData.setApplicantEmail1(sDo.getString("applicantEmail1"));
			wapplicantData.setApplicantEmail2(sDo.getString("applicantEmail2"));
			wapplicantData.setApplicantHomePhone(sDo.getString("applicantHomePhone"));
			wapplicantData.setApplicantCellPhone(sDo.getString("applicantCellPhone"));
			wapplicantData.setApplicantWorkPhone(sDo.getString("applicantWorkPhone"));
			wapplicantData.setNoticePeriod(sDo.getString("noticePeriod"));
			
			if (!Utils.isBlankOrNull(sDo.getString("applicantPositionId"))) {
				wapplicantData.setApplicantPositionId(sDo.getString("applicantPositionId"));
			}
			if (!Utils.isBlankOrNull(sDo.getString("applicantStepId"))) {
				wapplicantData.setApplicantStepId(sDo.getString("applicantStepId"));
			}
			wapplicantData.setTotalExperience(applicantData.getApplicantExperience());
			Date d=applicantData.getDateOfBirth();
			if(d!=null){
			wapplicantData.setDateOfBirth(Utils.getDateConvertedToString(d,Utils.regDDMMMYYFormat));
			}
			wapplicantData.setPassport(applicantData.getPassportNumber());
			List<EducationalData> eduHistory = applicantData.getEducationalDetails();
			ArrayList<WeducationalData> eduData = new ArrayList<WeducationalData>();
			ArrayList<WemploymentHistoryData> empData = new ArrayList<WemploymentHistoryData>();
			if (!Utils.isListEmptyOrNull(eduHistory)){
				for (EducationalData e:eduHistory){
					WeducationalData eData = new WeducationalData();
					eData.setDegreeTitle(e.getDegreeTitle());
					eData.setInstitute(e.getInstitute());
					eData.setMajor(e.getMajor());
					if (e.getYearOfPassing()!=null){
						eData.setYearOfPassing(e.getYearOfPassing());
					}
					eduData.add(eData);
				}
			}
			List<EmploymentHistoryData> workHistory = applicantData.getEmploymentHistoryDetails();
			if (!Utils.isListEmptyOrNull(workHistory)){
				for (EmploymentHistoryData w:workHistory){
					WemploymentHistoryData hist = new WemploymentHistoryData();
					hist.setDesignationName(w.getDesignationName());
					hist.setEmployerName(w.getEmployerName());
					if(w.getEmployerFromDate()!=null){
					hist.setEmployerFromDate(Utils.getDateConvertedToString(w.getEmployerFromDate(), Utils.regMMMYYYYFormat));
					}
					if(w.getEmployerToDate()!=null){
						hist.setEmployerToDate(Utils.getDateConvertedToString(w.getEmployerToDate(), Utils.regMMMYYYYFormat));
						}
					empData.add(hist);
				}
			}
			wapplicantData.setEducationalDetails(eduData);
			wapplicantData.setEmpHistoryData(empData);
		} catch (Exception e) {
			TPLogger.getLogger().error(e);
		}
		return wapplicantData;
	}
	public ApplicantData convertWApplicantDataToApplicantData(WapplicantData wapplicantData){
		ApplicantData applicantData=new ApplicantData();
		if(wapplicantData!=null){
			String applicantId=wapplicantData.getApplicantId();
			String applicantName=wapplicantData.getApplicantName();
			String positionId=wapplicantData.getApplicantPositionId();
			String originalResumePath=wapplicantData.getOriginalResumePath();
			Date applicantWorkingSince=wapplicantData.getApplicantWorkingSince();
			String positionTitle=wapplicantData.getPositionTitle();
			String exp=wapplicantData.getTotalExperience();
			String currentCtc=wapplicantData.getCurrentCTC();
			String expectedCtc=wapplicantData.getExpectedCTC();
			String email1=wapplicantData.getApplicantEmail1();
			String email2=wapplicantData.getApplicantEmail2();
			String cellPhone=wapplicantData.getApplicantCellPhone();
			String homePhone=wapplicantData.getApplicantHomePhone();
			String passport=wapplicantData.getPassport();
			String noticePeriod=wapplicantData.getNoticePeriod();
			String skills = wapplicantData.getSkills();
			String sourceTitle=wapplicantData.getSource();
			String workPhone=wapplicantData.getApplicantWorkPhone();
			String currentEmployer=wapplicantData.getCurrentEmployer();
			String city=wapplicantData.getCurrentLocation();
			String dateOfBirth=wapplicantData.getDateOfBirth();
			String note=wapplicantData.getNote();
			String currentStatus=wapplicantData.getCurrentStatus();
			applicantData.setAttribute("uuid", wapplicantData.getUuid());
			applicantData.setResumeTypeId(wapplicantData.getResumeType());
			
			if(!Utils.isBlankOrNull(applicantId)){
				applicantData.setApplicantId(applicantId);
			}
			if(!Utils.isBlankOrNull(applicantName)){
				applicantData.setApplicantName(applicantName);
			}
			if(!Utils.isBlankOrNull(originalResumePath)){
				applicantData.setApplicantOriginalResumePath(originalResumePath);
			}
			if(!Utils.isBlankOrNull(positionId)){
				applicantData.setApplicantPositionId(positionId);
			}
			if(!Utils.isBlankOrNull(positionTitle)){
				applicantData.setApplicantPositionTitle(positionTitle);
			}
			if(!Utils.isBlankOrNull(sourceTitle)){
				applicantData.setApplicantSourceTitle(sourceTitle);
			}
			if(!Utils.isBlankOrNull(skills)){
				applicantData.setSkillIds(skills);
			}
			if(!Utils.isBlankOrNull(currentCtc)){
				applicantData.setCurrentCTC(currentCtc);
			}
			if(!Utils.isBlankOrNull(expectedCtc)){
				applicantData.setExpectedCTC(expectedCtc);
			}
			if(!Utils.isBlankOrNull(email1)){
				applicantData.setApplicantEmail1(email1);
			}
			if(!Utils.isBlankOrNull(email2)){
				applicantData.setApplicantEmail2(email2);
			}
			if(!Utils.isBlankOrNull(cellPhone)){
				applicantData.setApplicantCellPhone(cellPhone);
			}
			if(!Utils.isBlankOrNull(homePhone)){
				applicantData.setApplicantHomePhone(homePhone);
			}
			if(!Utils.isBlankOrNull(passport)){
				applicantData.setPassportNumber(passport);
			}
			if(!Utils.isBlankOrNull(exp)){
				applicantData.setApplicantExperience(exp);
			}
			if(!Utils.isBlankOrNull(currentEmployer)){
				applicantData.setApplicantCurrentEmployer(currentEmployer);
			}
			if(!Utils.isBlankOrNull(city)){
				applicantData.setApplicantCity(city);
			}
			if(!Utils.isBlankOrNull(workPhone)){
				applicantData.setApplicantWorkPhone(workPhone);
			}
			if(!Utils.isBlankOrNull(noticePeriod)){
				applicantData.setNoticePeriod(noticePeriod);
			}
			
			if(!Utils.isBlankOrNull(dateOfBirth)){
				applicantData.setDateOfBirth(Utils.convertToSQLDate(dateOfBirth, Utils.regEUDateFormat));
			}
			
			java.sql.Date dtWorkingFrom = null;
			if (!Utils.isBlankOrNull(wapplicantData.getTotalExperience())){
				Calendar calendar = Calendar.getInstance();
				if(wapplicantData.getTotalExperience().contains(".")){
					String[] yysmms = wapplicantData.getTotalExperience().split("\\.");
					calendar.add(Calendar.YEAR, -Integer.parseInt(yysmms[0]));
					calendar.add(Calendar.MONTH, -Integer.parseInt(yysmms[1]));
				}else{
					calendar.add(Calendar.YEAR, -Integer.parseInt(wapplicantData.getTotalExperience()));
				}
				dtWorkingFrom = Utils.convertDateToSQLDate(calendar.getTime());
				applicantData.setApplicantWorkingSince(dtWorkingFrom);
			}
			applicantData.setApplicantCity(wapplicantData.getCurrentLocation());
			applicantData.setNoticePeriod(wapplicantData.getNoticePeriod());
			ArrayList<EducationalData> educationalDetails=new ArrayList<EducationalData>();
			
			ArrayList<WeducationalData> eEducationalDetails=wapplicantData.getEducationalDetails();
			MastersManager mastermanager=new MastersManager();
			if (eEducationalDetails != null && eEducationalDetails.size() > 0) {
				int sz = eEducationalDetails.size();
				String[] eduYop = new String[sz];
				String[] eduInstitute = new String[sz];
				String[] eduDegree = new String[sz];
				String[] eduMajor = new String[sz];
				String[] eduGrades = new String[sz];
				for (int i = 0; i < sz; i++) {
					EducationalData eEducationalData=new EducationalData();
					WeducationalData eData = (WeducationalData) eEducationalDetails.get(i);
					String degreeTitle=eData.getDegreeTitle();
					String degreeId=null;
					if(!Utils.isBlankOrNull(degreeTitle)){
						try {
							eEducationalData.setDegreeId(Integer.parseInt(degreeTitle));
						} catch (NumberFormatException nfe){
							
						}
//						degreeId=mastermanager.getDegreeId(degreeTitle);
//						if(Utils.isInteger(degreeId)){
//							eEducationalData.setDegreeId(Integer.parseInt(degreeId));
//						}
					}
					eEducationalData.setInstitute(eData.getInstitute());
					
					
					eEducationalData.setGrade(eData.getGrade());
					eEducationalData.setYearOfPassing(Utils.convertDateToSQLDate(eData.getYearOfPassing()));
					String branchTitle=eData.getMajor();
					String branchId=null;
					if(!Utils.isBlankOrNull(branchTitle)){
						try {
							eEducationalData.setMajorId(Integer.parseInt(branchTitle));
						} catch (NumberFormatException nfe){
							
						}
//						branchId=mastermanager.getBranchId(degreeTitle);
//						if(Utils.isInteger(branchId)){
//							eEducationalData.setMajorId(Integer.parseInt(branchId));
//						}
					}
					
					educationalDetails.add(eEducationalData);
				}
				
			}
			
			applicantData.setEducationalDetails(educationalDetails);
			ArrayList<EmploymentHistoryData> employmentHistoryDetails=new ArrayList<EmploymentHistoryData>();
			
			ArrayList<WemploymentHistoryData> eemploymentHistoryDetails=wapplicantData.getEmpHistoryData();
			
			if (eemploymentHistoryDetails != null && eemploymentHistoryDetails.size() > 0) {
				int sz = eemploymentHistoryDetails.size();
				for (int i = 0; i < sz; i++) {
					EmploymentHistoryData eemploymentHistoryData=new EmploymentHistoryData();
					WemploymentHistoryData empData = (WemploymentHistoryData) eemploymentHistoryDetails.get(i);								
					String employerFromDate = empData.getEmployerFromDate();
					if (employerFromDate != null) {
						
						eemploymentHistoryData.setEmployerFromDate(Utils.convertToSQLDate(employerFromDate, Utils.regMMMYYYYFormat));
					}
					String employerId=empData.getEmployerId();
					if(!Utils.isBlankOrNull(employerId)){
						employerId=employerId.trim();
						eemploymentHistoryData.setEmployerName(employerId);
					}
					
					String designationId=empData.getDesignationId();
					if(!Utils.isBlankOrNull(designationId)){
						designationId=designationId.trim();
						eemploymentHistoryData.setDesignationName(designationId);
						
					}
					
						String empExp=empData.getEmployerExperience();
						if(!Utils.isBlankOrNull(empExp)){
							empExp=changeExpInYear(empExp);
							eemploymentHistoryData.setEmployerExperience(empExp);
						}
						
					eemploymentHistoryData.setEmployerToDate(Utils.convertToSQLDate(empData.getEmployerToDate(),Utils.regMMMYYYYFormat));
					employmentHistoryDetails.add(eemploymentHistoryData);
				}
				
			}
			
			applicantData.setEmploymentHistoryDetails(employmentHistoryDetails);
			
			ArrayList<WcustomFieldData> wCustomFieldList = wapplicantData.getCustomFields();
			ArrayList<CustomFieldData> customFieldList = new ArrayList<CustomFieldData>();
			if (!Utils.isListEmptyOrNull(wCustomFieldList)){
				for (WcustomFieldData wcustomFieldData:wCustomFieldList){
					CustomFieldData cData = new CustomFieldData();
					cData.setFieldName(wcustomFieldData.getFieldName());
					cData.setFieldDisplayName(wcustomFieldData.getFieldDisplayName());
					cData.setFieldRequired(wcustomFieldData.getFieldRequired());
					cData.setFieldValues(wcustomFieldData.getFieldValues());
					cData.setToValues(wcustomFieldData.getToValues());
					cData.setFieldAttributes(wcustomFieldData.getFieldAttributes());
					cData.setFieldEntityType(wcustomFieldData.getFieldEntityType());
					cData.setFieldOtherAttributes(wcustomFieldData.getFieldOtherAttributes());
					cData.setFieldId(wcustomFieldData.getFieldId());
					cData.setFieldInputAllowed(wcustomFieldData.getFieldInputAllowed());
					cData.setFieldOptions(wcustomFieldData.getFieldOptions());
					cData.setFieldRank(wcustomFieldData.getFieldRank());
					cData.setFieldSearchable(wcustomFieldData.getFieldSearchable());
					cData.setFieldType(wcustomFieldData.getFieldType());
					cData.setFieldDefaultValue(wcustomFieldData.getFieldDefaultValue());
					cData.setFieldStringValue(wcustomFieldData.getFieldStringValue());
					cData.setFieldNumberValue(wcustomFieldData.getFieldNumberValue());
					cData.setFieldDateValue(wcustomFieldData.getFieldDateValue());
					customFieldList.add(cData);
				}
			}
			applicantData.setCustomFields(customFieldList);
		}
		return applicantData;
	}
	public static String changeExpInYear(String experience ){
		  String [] s=experience.split(" ");
		  if (s.length>0 && s[0].indexOf("yrs")!=-1){
			  s[0]=s[0].substring(0, s[0].indexOf("yrs"));
			  experience = s[0];
		  }
		  if (s.length>1 && s[1].indexOf("months")!=-1){
			  s[1]=s[1].substring(0, s[1].indexOf("months"));
			  experience = experience +"."+s[1] ;
		  }
		  return experience;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ImportFieldData> getAllApplicantFieldsForCandidatePortal() {
		DBPreparedQuery dq = null;
		ArrayList<ImportFieldData> result = null;
		try{
			dq= new DBPreparedQuery("dImportConfigurationManager_GetAllFieldsForCandidatePortal");
			result = dq.getResult();
		} catch (SQLException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
		} finally {
			if (dq != null) {
				dq.releaseConnection();
			}
		}
		return result;
	}
	
}
